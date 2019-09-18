package functionalj.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import functionalj.function.Func;
import functionalj.function.Func1;
import functionalj.function.Func2;
import functionalj.result.NoMoreResultException;
import lombok.val;

public interface StreamPlusWithSegment<DATA> {
    
    public <TARGET> StreamPlus<TARGET> map(
            Function<? super DATA, ? extends TARGET> mapper);
    
    public <T> StreamPlus<T> useIterator(Func1<IteratorPlus<DATA>, StreamPlus<T>> action);
    
    public void close();
    
    
    //== segment ==
    
    public default StreamPlus<StreamPlus<DATA>> segment(int count) {
        return segment(count, true);
    }
    public default StreamPlus<StreamPlus<DATA>> segment(int count, boolean includeTail) {
        val index = new AtomicInteger(0);
        return segment(data -> (index.getAndIncrement() % count) == 0, includeTail);
    }
    public default StreamPlus<StreamPlus<DATA>> segment(Predicate<DATA> startCondition) {
        return segment(startCondition, true);
    }
    public default StreamPlus<StreamPlus<DATA>> segment(Predicate<DATA> startCondition, boolean includeTail) {
        val list = new AtomicReference<>(new ArrayList<DATA>());
        val adding = new AtomicBoolean(false);
        
        val streamOrNull = (Function<DATA, StreamPlus<DATA>>)((DATA data) ->{
            if (startCondition.test(data)) {
                adding.set(true);
                val retList = list.getAndUpdate(l -> new ArrayList<DATA>());
                list.get().add(data);
                return retList.isEmpty()
                        ? null
                        : StreamPlus.from(retList.stream());
            }
            if (adding.get()) list.get().add(data);
            return null;
        });
        val mainStream = StreamPlus.from(map(streamOrNull)).filterNonNull();
        if (!includeTail)
            return mainStream;
        
        val mainSupplier = (Supplier<StreamPlus<StreamPlus<DATA>>>)()->mainStream;
        val tailSupplier = (Supplier<StreamPlus<StreamPlus<DATA>>>)()->{
            return StreamPlus.of(
                    StreamPlus.from(
                            list.get()
                            .stream()));
        };
        val resultStream
                = StreamPlus.of(mainSupplier, tailSupplier)
                .flatMap(Supplier::get);
        
        resultStream
        .onClose(()->StreamPlusWithSegment.this.close());
        
        return resultStream;
    }
    
    public default StreamPlus<StreamPlus<DATA>> segment(Predicate<DATA> startCondition, Predicate<DATA> endCondition) {
        return segment(startCondition, endCondition, true);
    }
    
    public default StreamPlus<StreamPlus<DATA>> segment(Predicate<DATA> startCondition, Predicate<DATA> endCondition, boolean includeTail) {
        val list = new AtomicReference<>(new ArrayList<DATA>());
        val adding = new AtomicBoolean(false);
        
        StreamPlus<StreamPlus<DATA>> resultStream 
            = StreamPlus.from(
                map(i ->{
                    if (startCondition.test(i)) {
                        adding.set(true);
                    }
                    if (includeTail && adding.get()) list.get().add(i);
                    if (endCondition.test(i)) {
                        adding.set(false);
                        val retList = list.getAndUpdate(l -> new ArrayList<DATA>());
                        return StreamPlus.from(retList.stream());
                    }
                    
                    if (!includeTail && adding.get()) list.get().add(i);
                    return null;
                }))
            .filterNonNull();
        
        resultStream
        .onClose(()->StreamPlusWithSegment.this.close());
        
        return resultStream;
    }
    
    @SuppressWarnings("unchecked")
    public default StreamPlus<StreamPlus<DATA>> segmentSize(Func1<DATA, Integer> segmentSize) {
        val listRef = new AtomicReference<List<DATA>>(new ArrayList<DATA>());
        val leftRef = new AtomicInteger(-1);
        
        val head 
            = map(each -> {
                int left = leftRef.get();
                if (left == -1) {
                    Integer newSize = segmentSize.apply(each);
                    if ((newSize == null) || (newSize == 0)) {
                        return StreamPlus.empty();
                    } else if (newSize == 1) {
                        return StreamPlus.of(each);
                    } else {
                        val list = listRef.get();
                        list.add(each);
                        leftRef.set(newSize - 1);
                    }
                } else if (left == 1) {
                    val list = listRef.getAndSet(new ArrayList<DATA>());
                    list.add(each);
                    
                    leftRef.set(-1);
                    
                    return StreamPlus.from(list.stream());
                    
                } else {
                    val list = listRef.get();
                    list.add(each);
                    leftRef.decrementAndGet();
                }
                return null;
            })
            .filterNonNull()
            ;
        
        StreamPlus<StreamPlus<DATA>> resultStream 
            = (StreamPlus<StreamPlus<DATA>>)StreamPlus.of(
                Func.f(()-> head),
                Func.f(()-> StreamPlus.of(StreamPlus.from(listRef.get().stream())))
            )
            .flatMap(s -> (StreamPlus<DATA>)s.get());
        
        resultStream
        .onClose(()->StreamPlusWithSegment.this.close());
        
        return resultStream;
    }
    
    // TODO - collapse(IntFunction<DATA> numbersToCollapse, Func2<DATA, DATA, DATA> concatFunc)
    
    @SuppressWarnings("unchecked")
    public default StreamPlus<DATA> collapse(Predicate<DATA> conditionToCollapse, Func2<DATA, DATA, DATA> concatFunc) {
        return useIterator(iterator -> {
            DATA first = null;
            try {
                first = iterator.next();
            } catch (NoSuchElementException e) {
                return StreamPlus.empty();
            }
            
            val prev = new AtomicReference<Object>(first);
            StreamPlus<DATA> resultStream = StreamPlus.generateWith(()->{
                if (prev.get() == StreamPlusHelper.dummy)
                    throw new NoMoreResultException();
                
                while(true) {
                    DATA next;
                    try {
                        next = iterator.next();
                    } catch (NoSuchElementException e) {
                        val yield = prev.get();
                        prev.set(StreamPlusHelper.dummy);
                        return (DATA)yield;
                    }
                    if (conditionToCollapse.test(next)) {
                        prev.set(concatFunc.apply((DATA)prev.get(), next));
                    } else {
                        val yield = prev.get();
                        prev.set(next);
                        return (DATA)yield;
                    }
                }
            });
            
            return resultStream;
        });
    }
    
    @SuppressWarnings("unchecked")
    public default StreamPlus<DATA> collapseSize(
            Func1<DATA, Integer>    segmentSize, 
            Func2<DATA, DATA, DATA> concatFunc) {
        val firstObj = new Object();
        return useIterator(iterator -> {
            val prev = new AtomicReference<Object>(firstObj);
            StreamPlus<DATA> resultStream = StreamPlus.generateWith(()->{
                if (prev.get() == StreamPlusHelper.dummy)
                    throw new NoMoreResultException();
                
                while(true) {
                    DATA next;
                    try {
                        next = iterator.next();
                    } catch (NoSuchElementException e) {
                        if (prev.get() == firstObj)
                            throw new NoMoreResultException();
                        
                        val yield = prev.get();
                        prev.set(StreamPlusHelper.dummy);
                        return (DATA)yield;
                    }
                    
                    Integer newSize = segmentSize.apply(next);
                    if ((newSize == null) || (newSize == 0)) {
                        continue;
                    }
                    
                    if (newSize == 1) {
                        return (DATA)next;
                    }
                    
                    prev.set(next);
                    for (int i = 0; i < (newSize - 1); i++) {
                        try {
                            next = iterator.next();
                            prev.set(concatFunc.apply((DATA)prev.get(), next));
                        } catch (NoSuchElementException e) {
                            val yield = prev.get();
                            prev.set(StreamPlusHelper.dummy);
                            return (DATA)yield;
                        }
                    }
                    
                    val yield = prev.get();
                    prev.set(firstObj);
                    return (DATA)yield;
                }
            });
            
            return resultStream;
        });
    }
    
    @SuppressWarnings("unchecked")
    public default <TARGET> StreamPlus<TARGET> collapseSize(
            Func1<DATA, Integer>          segmentSize, 
            Func1<DATA, TARGET>           mapper, 
            Func2<TARGET, TARGET, TARGET> concatFunc) {
        val firstObj = new Object();
        return useIterator(iterator -> {
            val prev = new AtomicReference<Object>(firstObj);
            StreamPlus<TARGET> resultStream = StreamPlus.generateWith(()->{
                if (prev.get() == StreamPlusHelper.dummy)
                    throw new NoMoreResultException();
                
                while(true) {
                    DATA next;
                    try {
                        next = iterator.next();
                    } catch (NoSuchElementException e) {
                        if (prev.get() == firstObj)
                            throw new NoMoreResultException();
                        
                        val yield = prev.get();
                        prev.set(StreamPlusHelper.dummy);
                        return (TARGET)yield;
                    }
                    
                    Integer newSize = segmentSize.apply(next);
                    if ((newSize == null) || (newSize == 0)) {
                        continue;
                    }
                    
                    if (newSize == 1) {
                        val target = (TARGET)mapper.apply((DATA)next);
                        return target;
                    }
                    
                    TARGET target = (TARGET)mapper.apply((DATA)next);
                    prev.set(target);
                    for (int i = 0; i < (newSize - 1); i++) {
                        try {
                            next   = iterator.next();
                            target = (TARGET)mapper.apply((DATA)next);
                            val prevValue = (TARGET)prev.get();
                            val newValue  = concatFunc.apply(prevValue, target);
                            prev.set(newValue);
                        } catch (NoSuchElementException e) {
                            val yield = prev.get();
                            prev.set(StreamPlusHelper.dummy);
                            return (TARGET)yield;
                        }
                    }
                    
                    val yield = prev.get();
                    prev.set(firstObj);
                    return (TARGET)yield;
                }
            });
            
            return resultStream;
        });
    }
}

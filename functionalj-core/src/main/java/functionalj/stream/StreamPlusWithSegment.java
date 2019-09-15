package functionalj.stream;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import functionalj.function.Func1;
import functionalj.function.Func2;
import functionalj.function.FuncUnit1;
import functionalj.result.NoMoreResultException;
import lombok.val;

public interface StreamPlusWithSegment<DATA> {
    
    public <TARGET> StreamPlus<TARGET> map(
            Function<? super DATA, ? extends TARGET> mapper);
    
    public <TARGET> TARGET terminate(Func1<Stream<DATA>, TARGET> action);
    
    public void terminate(FuncUnit1<Stream<DATA>> action);
    
    
    //-- segment --
    
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
        return resultStream;
    }
    
    public default StreamPlus<StreamPlus<DATA>> segment(Predicate<DATA> startCondition, Predicate<DATA> endCondition) {
        return segment(startCondition, endCondition, true);
    }
    
    public default StreamPlus<StreamPlus<DATA>> segment(Predicate<DATA> startCondition, Predicate<DATA> endCondition, boolean includeTail) {
        val list = new AtomicReference<>(new ArrayList<DATA>());
        val adding = new AtomicBoolean(false);
        
        return StreamPlus.from(
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
    }
    
    // TODO - collapse(IntFunction<DATA> numbersToCollapse, Func2<DATA, DATA, DATA> concatFunc)
    
    @SuppressWarnings("unchecked")
    public default StreamPlus<DATA> collapse(Predicate<DATA> conditionToCollapse, Func2<DATA, DATA, DATA> concatFunc) {
        return terminate(stream -> {
            val iterator = stream.iterator();
            
            DATA first = null;
            try {
                first = iterator.next();
            } catch (NoSuchElementException e) {
                return StreamPlus.empty();
            }
            
            val prev = new AtomicReference<Object>(first);
            return StreamPlus.generateBy(()->{
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
        });
    }
}

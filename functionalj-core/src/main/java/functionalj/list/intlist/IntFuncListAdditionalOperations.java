package functionalj.list.intlist;

import java.util.Collection;
import java.util.OptionalInt;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;

import functionalj.function.IntBiPredicatePrimitive;
import functionalj.function.IntIntBiFunction;
import functionalj.function.IntObjBiFunction;
import functionalj.function.ObjIntBiFunction;
import functionalj.list.FuncList;
import functionalj.stream.Streamable;
import functionalj.stream.intstream.AsIntStreamable;
import functionalj.stream.intstream.IntStreamPlus;
import functionalj.stream.intstream.IntStreamable;
import functionalj.tuple.IntIntTuple;
import functionalj.tuple.ObjIntTuple;

public interface IntFuncListAdditionalOperations {
    
    public IntStreamPlus intStream();
    
    public IntFuncList derive(IntStreamable streamable);
    
    public <TARGET> FuncList<TARGET> deriveToList(Streamable<TARGET> action);
    
    
    //--map with condition --
    
    public default IntFuncList mapOnly(
            IntPredicate     checker, 
            IntUnaryOperator mapper) {
        return derive(()-> {
            return intStream()
                    .mapOnly(checker, mapper);
        });
    }
    
    public default IntFuncList mapIf(
            IntPredicate     checker, 
            IntUnaryOperator mapper, 
            IntUnaryOperator elseMapper) {
        return derive(()->{
            return intStream()
                    .mapIf(checker, mapper, elseMapper);
        });
    }
    
    public default <T> FuncList<T> mapToObjIf(
            IntPredicate   checker, 
            IntFunction<T> mapper, 
            IntFunction<T> elseMapper) {
        return deriveToList(()->{
            return intStream()
                    .mapToObjIf(checker, mapper, elseMapper);
        });
    }
    
    //-- mapWithIndex --
    
    public default IntFuncList mapWithIndex(
            IntBinaryOperator mapper) {
        return derive(()->{
            return intStream()
                    .mapWithIndex(mapper);
        });
    }
    
    public default FuncList<IntIntTuple> mapWithIndex() {
        return deriveToList(()->{
            return intStream()
                    .mapWithIndex();
        });
    }
    
    public default <T> FuncList<T> mapToObjWithIndex(
            IntIntBiFunction<T> mapper) {
        return deriveToList(()->{
            return intStream()
                    .mapToObjWithIndex(mapper);
        });
    }
    
    public default <T1, T> FuncList<T> mapToObjWithIndex(
                IntFunction<? extends T1>       valueMapper,
                IntObjBiFunction<? super T1, T> combiner) {
        return deriveToList(()->{
            return intStream()
                    .mapToObjWithIndex(valueMapper, combiner);
        });
    }
    
    //-- mapWithPrev --
    
    public default FuncList<ObjIntTuple<OptionalInt>> mapWithPrev() {
        return deriveToList(()->{
            return intStream()
                    .mapWithPrev();
        });
    }
    
    public default <TARGET> FuncList<TARGET> mapWithPrev(
            ObjIntBiFunction<OptionalInt, ? extends TARGET> mapper) {
        return deriveToList(()->{
            return intStream()
                    .mapWithPrev(mapper);
        });
    }
    
    //-- Filter --
    
    public default IntFuncList filterIn(int ... array) {
        return derive(()->{
            return intStream()
                    .filterIn(array);
        });
    }
    
    public default IntFuncList filterIn(Collection<Integer> collection) {
        return derive(()->{
            return intStream()
                    .filterIn(collection);
        });
    }
    
    public default IntFuncList exclude(IntPredicate predicate) {
        return derive(()->{
            return intStream()
                    .exclude(predicate);
        });
    }
    
    public default IntFuncList excludeIn(int... array) {
        return derive(()->{
            return intStream()
                    .excludeIn(array);
        });
    }
    
    public default IntFuncList excludeIn(Collection<Integer> collection) {
        return derive(()->{
            return intStream()
                    .excludeIn(collection);
        });
    }
    
    public default IntFuncList filterWithIndex(
            IntBiPredicatePrimitive predicate) {
        return derive(()->{
            return intStream()
                    .filterWithIndex(predicate);
        });
    }
    
    //-- Peek --
    
    public default IntFuncList peek(IntPredicate selector, IntConsumer theConsumer) {
        return derive(()->{
            return intStream()
                    .peek(selector, theConsumer);
        });
    }
//    public default <T> IntFuncList peek(IntFunction<T> mapper, Consumer<? super T> theConsumer) {
//        return derive(()->{
//            return intStream()
//                    .peek(mapper, theConsumer);
//        });
//    }
//    
//    public default <T> IntFuncList peek(
//            IntFunction<T>       mapper, 
//            Predicate<? super T> selector, 
//            Consumer<? super T>  theConsumer) {
//        return derive(()->{
//            return intStream()
//                    .peek(mapper, selector, theConsumer);
//        });
//    }
    
    //-- FlatMap --
    
    public default IntFuncList flatMapOnly(
            IntPredicate                         checker, 
            IntFunction<? extends AsIntStreamable> mapper) {
        return derive(()->{
            return intStream()
                    .flatMapOnly(
                            checker,
                            item -> mapper.apply(item).intStreamable().intStream());
        });
    }
    public default IntFuncList flatMapIf(
            IntPredicate                            checker, 
            IntFunction<? extends AsIntStreamable> mapper, 
            IntFunction<? extends AsIntStreamable> elseMapper) {
        return derive(()->{
            return intStream()
                    .flatMapIf(
                            checker,
                            item -> mapper.apply(item).intStreamable().intStream(),
                            item -> elseMapper.apply(item).intStreamable().intStream());
        });
    }
    
    public default <T> FuncList<T> flatMapToObjIf(
            IntPredicate                         checker, 
            IntFunction<? extends Streamable<T>> mapper, 
            IntFunction<? extends Streamable<T>> elseMapper) {
        return deriveToList(()->{
            return intStream()
                    .flatMapToObjIf(
                            checker,
                            item -> ((Streamable<T>)mapper    .apply(item)).stream(),
                            item -> ((Streamable<T>)elseMapper.apply(item)).stream());
        });
    }
    
}

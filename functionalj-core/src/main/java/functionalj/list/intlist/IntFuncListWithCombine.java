package functionalj.list.intlist;

import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import functionalj.function.IntBiFunctionPrimitive;
import functionalj.function.IntBiPredicatePrimitive;
import functionalj.function.IntIntBiFunction;
import functionalj.function.IntObjBiFunction;
import functionalj.list.FuncList;
import functionalj.stream.Streamable;
import functionalj.stream.ZipWithOption;
import functionalj.stream.intstream.IntStreamPlus;
import functionalj.stream.intstream.IntStreamable;
import functionalj.stream.intstream.IntStreamableWithCombine;
import functionalj.tuple.IntIntTuple;
import functionalj.tuple.IntTuple2;
import lombok.val;

public interface IntFuncListWithCombine extends IntStreamableWithCombine {
        
    public IntFuncList derive(Function<IntStreamable, IntStream> action);
    
    public <TARGET> FuncList<TARGET> deriveToList(Function<IntStreamable, Stream<TARGET>> action);
    
    
//    public default IntFuncList concatWith(
//            IntStreamable tail) {
//        return derive(streamable -> {
//            val tailStream = tail.stream();
//            return streamable
//                    .stream()
//                    .concatWith(tailStream);
//        });
//    }
//    
//    public default IntFuncList concatWith(
//            int ... tail) {
//        return derive(streamable -> {
//            val tailStream = IntStreamPlus.of(tail);
//            return streamable
//                    .stream()
//                    .concatWith(tailStream);
//        });
//    }
//    
//    public default IntFuncList merge(
//            IntStreamable another) {
//        return derive(streamable -> {
//            val anotherStream = another.stream();
//            return streamable
//                    .stream()
//                    .merge(anotherStream);
//        });
//    }
//    
//    public default IntFuncList merge(
//            int ... anothers) {
//        return derive(streamable -> {
//            val anotherStream = IntStream.of(anothers);
//            return streamable
//                    .stream()
//                    .merge(anotherStream);
//        });
//    }
//    
//    // TODO - Allow mapping before combiner, selecting
//    //-- Zip --
//    
//    public default <ANOTHER, TARGET> FuncList<TARGET> combineWith(
//            Streamable<ANOTHER>               anotherStreamable, 
//            IntObjBiFunction<ANOTHER, TARGET> combinator) {
//        return deriveToList(streamable -> {
//            return streamable
//                    .combineWith(anotherStreamable, combinator)
//                    .stream();
//        });
//    }
//    
//    public default <ANOTHER, TARGET> FuncList<TARGET> combineWith(
//            Streamable<ANOTHER>               anotherStreamable, 
//            ZipWithOption                     option, 
//            IntObjBiFunction<ANOTHER, TARGET> combinator) {
//        return deriveToList(streamable -> {
//            return streamable
//                    .combineWith(anotherStreamable, option, combinator)
//                    .stream();
//        });
//    }
//    
//    public default <ANOTHER> FuncList<IntTuple2<ANOTHER>> zipWith(
//            Streamable<ANOTHER> anotherStreamable) {
//        return deriveToList(streamable -> {
//            return streamable
//                    .zipWith(anotherStreamable)
//                    .stream();
//        });
//    }
//    
//    public default <ANOTHER> FuncList<IntTuple2<ANOTHER>> zipWith(
//            Streamable<ANOTHER> anotherStreamable, 
//            ZipWithOption       option) {
//        return deriveToList(streamable -> {
//            return streamable
//                    .zipWith(anotherStreamable, option)
//                    .stream();
//        });
//    }
//    
//    public default <ANOTHER, TARGET> FuncList<TARGET> zipWith(
//            Streamable<ANOTHER>               anotherStreamable, 
//            IntObjBiFunction<ANOTHER, TARGET> merger) {
//        return deriveToList(streamable -> {
//            return streamable
//                    .zipWith(anotherStreamable, merger)
//                    .stream();
//        });
//    }
//    
//    public default <ANOTHER, TARGET> FuncList<TARGET> zipWith(
//            Streamable<ANOTHER>               anotherStreamable, 
//            ZipWithOption                     option,
//            IntObjBiFunction<ANOTHER, TARGET> merger) {
//        return deriveToList(streamable -> {
//            return streamable
//                    .zipWith(anotherStreamable, option, merger)
//                    .stream();
//        });
//    }
//    
//    public default FuncList<IntIntTuple> zipWith(
//            IntStreamable anotherStreamable) {
//        return deriveToList(streamable -> {
//            return streamable
//                    .zipWith(anotherStreamable)
//                    .stream();
//        });
//    }
//    
//    public default FuncList<IntIntTuple> zipWith(
//            IntStreamable anotherStreamable,
//            int           defaultValue) {
//        return deriveToList(streamable ->  {
//            return streamable
//                    .zipWith(anotherStreamable, defaultValue)
//                    .stream();
//        });
//    }
    
    public default IntFuncList zipWith(
            IntStreamable          anotherStreamable, 
            IntBiFunctionPrimitive merger) {
        return derive(streamable ->  {
            return streamable
                    .zipWith(anotherStreamable, merger)
                    .stream();
        });
    }
    
    public default IntFuncList zipWith(
            IntStreamable          anotherStreamable, 
            IntBiFunctionPrimitive merger,
            int                    defaultValue) {
        return derive(streamable ->  {
            return streamable
                    .zipWith(anotherStreamable, merger, defaultValue)
                    .stream();
        });
    }
    
//    public default <T> FuncList<T> zipToObjWith(
//            IntStreamable       anotherStreamable, 
//            IntIntBiFunction<T> merger) {
//        return deriveToList(streamable ->  {
//            return streamable
//                    .zipToObjWith(anotherStreamable, merger)
//                    .stream();
//        });
//    }
//    public default <T> FuncList<T> zipToObjWith(
//            IntStreamable       anotherStreamable, 
//            IntIntBiFunction<T> merger,
//            int                 defaultValue) {
//        return deriveToList(streamable ->  {
//            return streamable
//                    .zipToObjWith(anotherStreamable, merger, defaultValue)
//                    .stream();
//        });
//    }
//    
//    // TODO - this should be moved out to be a static method
//    public default IntFuncList choose(
//            IntStreamable           anotherStreamable, 
//            IntBiPredicatePrimitive selectThisNotAnother) {
//        return derive(streamable -> {
//            return streamable
//                    .choose(anotherStreamable, selectThisNotAnother)
//                    .stream();
//        });
//    }
}

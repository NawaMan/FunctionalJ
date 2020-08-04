package functionalj.list.intlist;

import functionalj.stream.intstream.IntStreamableWithCombine;


public interface IntFuncListWithCombine extends IntStreamableWithCombine {
    
    // public IntStreamPlus intStream();
    
    // public IntFuncList derive(IntStreamable streamable);
    
    // public <TARGET> FuncList<TARGET> deriveToList(Streamable<TARGET> streamable);
    
    // public default IntFuncList concatWith(
    //         IntFuncList tail) {
    //     return derive(() -> {
    //         val tailStream = tail.intStream();
    //         return intStream()
    //                 .concatWith(tailStream);
    //     });
    // }
    
    // public default IntFuncList concatWith(
    //         int ... tail) {
    //     return derive(() -> {
    //         val tailStream = IntStreamPlus.of(tail);
    //         return intStream()
    //                 .concatWith(tailStream);
    //     });
    // }
    
    // public default IntFuncList mergeWith(
    //         IntFuncList another) {
    //     return derive(() -> {
    //         val anotherStream = another.intStream();
    //         return intStream()
    //                 .mergeWith(anotherStream);
    //     });
    // }
    
    // public default IntFuncList mergeWith(
    //         int ... anothers) {
    //     return derive(() -> {
    //         val anotherStream = IntStream.of(anothers);
    //         return intStream()
    //                 .mergeWith(anotherStream);
    //     });
    // }
    
    // //-- Zip --
    
    // public default <ANOTHER> FuncList<IntTuple2<ANOTHER>> zipWith(
    //         FuncList<ANOTHER> anotherList) {
    //     return deriveToList(() -> {
    //         return intStream()
    //                 .zipWith(anotherList.stream());
    //     });
    // }
    
    // public default <ANOTHER> FuncList<IntTuple2<ANOTHER>> zipWith(
    //         int               defaultValue,
    //         FuncList<ANOTHER> anotherList) {
    //     return deriveToList(() -> {
    //         return intStream()
    //                 .zipWith(defaultValue, anotherList.stream());
    //     });
    // }
    
    // public default <ANOTHER, TARGET> FuncList<TARGET> zipWith(
    //         FuncList<ANOTHER>               anotherList, 
    //         IntObjBiFunction<ANOTHER, TARGET> merger) {
    //     return deriveToList(() -> {
    //         return intStream()
    //                 .zipWith(anotherList.stream(), merger);
    //     });
    // }
    
    // public default <ANOTHER, TARGET> FuncList<TARGET> zipWith(
    //         int                               defaultValue,
    //         FuncList<ANOTHER>                 anotherList, 
    //         IntObjBiFunction<ANOTHER, TARGET> merger) {
    //     return deriveToList(() -> {
    //         return intStream()
    //                 .zipWith(defaultValue, anotherList.stream(), merger);
    //     });
    // }
    
    // public default FuncList<IntIntTuple> zipWith(
    //         IntFuncList anotherList) {
    //     return deriveToList(() -> {
    //         return intStream()
    //                 .zipWith(anotherList.intStream());
    //     });
    // }
    
    // public default FuncList<IntIntTuple> zipWith(
    //         IntFuncList anotherList,
    //         int         defaultValue) {
    //     return deriveToList(() ->  {
    //         return intStream()
    //                 .zipWith(anotherList.intStream(), defaultValue);
    //     });
    // }
    
    // public default FuncList<IntIntTuple> zipWith(
    //         IntFuncList anotherList,
    //         int         defaultValue1,
    //         int         defaultValue2) {
    //     return deriveToList(() ->  {
    //         return intStream()
    //                 .zipWith(anotherList.intStream(), defaultValue1, defaultValue2);
    //     });
    // }
    
    // public default IntFuncList zipWith(
    //         IntFuncList            anotherList, 
    //         IntBiFunctionPrimitive merger) {
    //     return derive(() ->  {
    //         return intStream()
    //                 .zipWith(anotherList.intStream(), merger);
    //     });
    // }
    
    // public default IntFuncList zipWith(
    //         IntFuncList            anotherList, 
    //         int                    defaultValue,
    //         IntBiFunctionPrimitive merger) {
    //     return derive(() ->  {
    //         return intStream()
    //                 .zipWith(anotherList.intStream(), defaultValue, merger);
    //     });
    // }
    
    // public default IntFuncList zipWith(
    //         IntFuncList            anotherList, 
    //         int                    defaultValue1,
    //         int                    defaultValue2,
    //         IntBiFunctionPrimitive merger) {
    //     return derive(() ->  {
    //         return intStream()
    //                 .zipWith(anotherList.intStream(), defaultValue1, defaultValue2, merger);
    //     });
    // }
    
    // public default <T> FuncList<T> zipToObjWith(
    //         IntFuncList         anotherList, 
    //         IntIntBiFunction<T> merger) {
    //     return deriveToList(() ->  {
    //         return intStream()
    //                 .zipToObjWith(anotherList.intStream(), merger);
    //     });
    // }
    // public default <T> FuncList<T> zipToObjWith(
    //         IntFuncList         anotherList,
    //         int                 defaultValue, 
    //         IntIntBiFunction<T> merger) {
    //     return deriveToList(() ->  {
    //         return intStream()
    //                 .zipToObjWith(anotherList.intStream(), defaultValue, merger);
    //     });
    // }
    // public default <T> FuncList<T> zipToObjWith(
    //         IntFuncList         anotherList,
    //         int                 defaultValue1, 
    //         int                 defaultValue2, 
    //         IntIntBiFunction<T> merger) {
    //     return deriveToList(() ->  {
    //         return intStream()
    //                 .zipToObjWith(anotherList.intStream(), defaultValue1, defaultValue2, merger);
    //     });
    // }
    
    // // TODO - this should be moved out to be a static method
    // public default IntFuncList choose(
    //         IntFuncList             anotherList, 
    //         IntBiPredicatePrimitive selectThisNotAnother) {
    //     return derive(() -> {
    //         return intStream()
    //                 .choose(anotherList.intStream(), selectThisNotAnother);
    //     });
    // }
}

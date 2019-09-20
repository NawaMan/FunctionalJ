package functionalj.stream;

import static functionalj.function.Func.f;
import static functionalj.stream.ZipWithOption.AllowUnpaired;

import java.util.stream.Stream;

import functionalj.function.Func1;
import functionalj.function.Func2;
import functionalj.tuple.Tuple2;
import lombok.val;

public interface StreamPlusWithCombine<DATA> {
    
    public Stream<DATA> stream();
    public <T> StreamPlus<T> useIterator(Func1<IteratorPlus<DATA>, StreamPlus<T>> action);
    
    
    @SuppressWarnings("unchecked")
    public default StreamPlus<DATA> concatWith(
            Stream<DATA> tail) {
        return StreamPlus.concat(
                StreamPlus.of(this), 
                StreamPlus.of(tail)
               )
               .flatMap(s -> (StreamPlus<DATA>)s);
    }
    
    
    public default StreamPlus<DATA> merge(Stream<DATA> anotherStream) {
        val thisStream = stream();
        val iteratorA  = StreamPlusHelper.rawIterator(thisStream);
        val iteratorB  = StreamPlusHelper.rawIterator(anotherStream);
        
        val resultStream 
                = StreamPlusHelper
                .doMerge(iteratorA, iteratorB);
        
        resultStream
                .onClose(()->{
                    f(()->thisStream   .close()).runCarelessly();
                    f(()->anotherStream.close()).runCarelessly();
                });
        return resultStream;
    }
    
    //-- Zip --
    
    public default <B, TARGET> StreamPlus<TARGET> combineWith(Stream<B> anotherStream, Func2<DATA, B, TARGET> combinator) {
        return zipWith(anotherStream, ZipWithOption.RequireBoth)
                .map(combinator::applyTo);
    }
    public default <B, TARGET> StreamPlus<TARGET> combineWith(Stream<B> anotherStream, ZipWithOption option, Func2<DATA, B, TARGET> combinator) {
        return zipWith(anotherStream, option)
                .map(combinator::applyTo);
    }
    
    public default <B> StreamPlus<Tuple2<DATA,B>> zipWith(Stream<B> anotherStream) {
        return zipWith(anotherStream, ZipWithOption.RequireBoth, Tuple2::of);
    }
    public default <B> StreamPlus<Tuple2<DATA,B>> zipWith(Stream<B> anotherStream, ZipWithOption option) {
        return zipWith(anotherStream, option, Tuple2::of);
    }
    
    public default <B, C> StreamPlus<C> zipWith(Stream<B> anotherStream, Func2<DATA, B, C> merger) {
        return zipWith(anotherStream, ZipWithOption.RequireBoth, merger);
    }
    // https://stackoverflow.com/questions/24059837/iterate-two-java-8-streams-together?noredirect=1&lq=1
    public default <B, C> StreamPlus<C> zipWith(Stream<B> anotherStream, ZipWithOption option, Func2<DATA, B, C> merger) {
        return useIterator(iteratorA -> {
            return StreamPlus
                    .from(anotherStream)
                    .useIterator(iteratorB -> {
                        return StreamPlusHelper.doZipWith(option, merger, iteratorA, iteratorB);
                    });
        });
    }
    
    public default StreamPlus<DATA> choose(Stream<DATA> anotherStream, Func2<DATA, DATA, Boolean> selectThisNotAnother) {
        return zipWith(anotherStream, AllowUnpaired)
                .map(t -> {
                    val _1 = t._1();
                    val _2 = t._2();
                    if ((_1 != null) && _2 == null)
                        return _1;
                    if ((_1 == null) && _2 != null)
                        return _2;
                    if ((_1 == null) && _2 == null)
                        return null;
                    val which = selectThisNotAnother.applyTo(t);
                    return which ? _1 : _2;
                })
                .filterNonNull();
    }
    
}

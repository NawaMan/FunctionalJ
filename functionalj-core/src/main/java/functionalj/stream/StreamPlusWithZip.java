package functionalj.stream;

import static functionalj.stream.ZipWithOption.AllowUnpaired;

import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import functionalj.function.Func2;
import functionalj.tuple.Tuple2;
import lombok.val;

public interface StreamPlusWithZip<DATA> {
    
    public IteratorPlus<DATA> iterator();
    
    //-- Zip --
    
    public default <B, TARGET> StreamPlus<TARGET> combine(Stream<B> anotherStream, Func2<DATA, B, TARGET> combinator) {
        return zipWith(anotherStream, ZipWithOption.RequireBoth)
                .map(combinator::applyTo);
    }
    public default <B, TARGET> StreamPlus<TARGET> combine(Stream<B> anotherStream, ZipWithOption option, Func2<DATA, B, TARGET> combinator) {
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
        val iteratorA = this.iterator();
        val iteratorB = anotherStream.iterator();
        val iterable = new Iterable<C>() {
            @Override
            public Iterator<C> iterator() {
                return new Iterator<C>() {
                    private boolean hasNextA;
                    private boolean hasNextB;
                    
                    public boolean hasNext() {
                        hasNextA = iteratorA.hasNext();
                        hasNextB = iteratorB.hasNext();
                        return (option == ZipWithOption.RequireBoth)
                                ? (hasNextA && hasNextB)
                                : (hasNextA || hasNextB);
                    }
                    public C next() {
                        val nextA = hasNextA ? iteratorA.next() : null;
                        val nextB = hasNextB ? iteratorB.next() : null;
                        return merger.apply(nextA, nextB);
                    }
                };
            }
            
        };
        return StreamPlus.from(StreamSupport.stream(iterable.spliterator(), false));
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
    public default StreamPlus<DATA> merge(Stream<DATA> anotherStream) {
        val iteratorA = this.iterator();
        val iteratorB = anotherStream.iterator();
        val iterable = new Iterable<DATA>() {
            @Override
            public Iterator<DATA> iterator() {
                return new Iterator<DATA>() {
                    private boolean isA = true;
                    
                    public boolean hasNext() {
                        if (isA) {
                            if (iteratorA.hasNext()) return true;
                            isA = false;
                            if (iteratorB.hasNext()) return true;
                            return false;
                        }
                        
                        if (iteratorB.hasNext()) return true;
                        isA = true;
                        if (iteratorA.hasNext()) return true;
                        return false;
                    }
                    public DATA next() {
                        val next = isA ? iteratorA.next() : iteratorB.next();
                        isA = !isA;
                        return next;
                    }
                };
            }
            
        };
        return StreamPlus.from(StreamSupport.stream(iterable.spliterator(), false));
    }
    
}

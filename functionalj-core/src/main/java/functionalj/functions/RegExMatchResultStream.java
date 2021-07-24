package functionalj.functions;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import functionalj.function.IntObjBiFunction;
import functionalj.stream.StreamPlus;

@FunctionalInterface
public interface RegExMatchResultStream extends StreamPlus<RegExMatchResult> {
    
    public static final RegExMatchResultStream empty = RegExMatchResultStream.from(StreamPlus.empty());
    
    public static RegExMatchResultStream from(Stream<RegExMatchResult> stream) {
        if (stream == null)
            return empty;
        
        if (stream instanceof RegExMatchResultStream)
            return (RegExMatchResultStream)stream;
        
        return (RegExMatchResultStream)(()->StreamPlus.from(stream));
    }
    
    public StreamPlus<RegExMatchResult> stream();
    
    
    public default StreamPlus<String> texts() {
        return stream().map(RegExMatchResult::text);
    }
    
    
    //-- Filter --
    
    public default RegExMatchResultStream filter(Predicate<? super RegExMatchResult> predicate) {
        return (RegExMatchResultStream)(()->stream().filter(predicate));
    }
    public default RegExMatchResultStream filterNonNull() {
        return (RegExMatchResultStream)(()->stream().filterNonNull());
    }
    
    public default RegExMatchResultStream filterIn(Collection<? super RegExMatchResult> collection) {
        return (RegExMatchResultStream)(()->stream().filterIn(collection));
    }
    
    public default RegExMatchResultStream exclude(Predicate<? super RegExMatchResult> predicate) {
        return (RegExMatchResultStream)(()->stream().exclude(predicate));
    }
    
    public default RegExMatchResultStream excludeIn(Collection<? super RegExMatchResult> collection) {
        return (RegExMatchResultStream)(()->stream().excludeIn(collection));
    }
    
    public default <T> RegExMatchResultStream filter(Class<T> clzz) {
        return (RegExMatchResultStream)(()->stream().filter(clzz));
    }
    
    public default <T> RegExMatchResultStream filter(Class<T> clzz, Predicate<? super T> theCondition) {
        return (RegExMatchResultStream)(()->stream().filter(clzz, theCondition));
    }
    
    public default <T> RegExMatchResultStream filter(Function<? super RegExMatchResult, T> mapper, Predicate<? super T> theCondition) {
        return (RegExMatchResultStream)(()->stream().filter(mapper, theCondition));
    }
    
    public default RegExMatchResultStream filterWithIndex(IntObjBiFunction<? super RegExMatchResult, Boolean> predicate) {
        return (RegExMatchResultStream)(()->stream().filterWithIndex(predicate));
    }
    
    //-- Peek --
    
    public default RegExMatchResultStream peek(Consumer<? super RegExMatchResult> action) {
        return (RegExMatchResultStream)(()->stream().peek(action));
    }
    public default <T extends RegExMatchResult> RegExMatchResultStream peek(Class<T> clzz, Consumer<? super T> theConsumer) {
        return (RegExMatchResultStream)(()->stream().peek(clzz, theConsumer));
    }
    public default RegExMatchResultStream peekBy(Predicate<? super RegExMatchResult> selector, Consumer<? super RegExMatchResult> theConsumer) {
        return (RegExMatchResultStream)(()->stream().peekBy(selector, theConsumer));
    }
    public default <T> RegExMatchResultStream peekAsObj(Function<? super RegExMatchResult, T> mapper, Consumer<? super T> theConsumer) {
        return (RegExMatchResultStream)(()->stream().peekAs(mapper, theConsumer));
    }
    
    public default <T> RegExMatchResultStream peekAs(Function<? super RegExMatchResult, T> mapper, Predicate<? super T> selector, Consumer<? super T> theConsumer) {
        return (RegExMatchResultStream)(()->stream().peekAs(mapper, theConsumer));
    }
}

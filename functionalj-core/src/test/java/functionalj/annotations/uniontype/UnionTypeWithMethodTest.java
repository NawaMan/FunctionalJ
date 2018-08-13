package functionalj.annotations.uniontype;

import static functionalj.annotations.uniontype.MayBe.Just;
import static functionalj.annotations.uniontype.MayBe.None;
import static functionalj.annotations.uniontype.UnionTypes.Switch;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.junit.Test;

import functionalj.annotations.UnionType;

@SuppressWarnings("javadoc")
public class UnionTypeWithMethodTest {
    
    @UnionType(name="MayBe")
    public interface MayBeSpec<T> {
        void None();
        void Just(T value);
        
        static <T> MayBe<T> of(T value) {
            return (value == null)
                    ? MayBe.None()
                            : MayBe.Just(value);
        }
        default boolean equals(MayBe<T> mayBe, Object obj) {
            if (obj == null)
                return mayBe.isNone();
            if (!(obj instanceof MayBe))
                return false;
            return mayBe
                    .toString()
                    .equals(obj.toString());
        }
        default int hashCode(MayBe<T> mayBe) {
            return Switch(mayBe)
                    .none(0)
                    .just(j -> j.value().hashCode());
        }
        default String toString(MayBe<T> mayBe) {
            return Switch(mayBe)
                    .none("None")
                    .just(m -> "Just:" + m.value());
        }
        default boolean isPresent(MayBe<T> mayBe) {
            return mayBe.isJust();
        }
        default MayBe<T> ifPresent(MayBe<T> mayBe, Consumer<T> action) {
            return Switch(mayBe)
                    .none(mayBe)
                    .just(just -> {
                        action.accept(just.value());
                        return mayBe;
                    });
        }
        default <R> MayBe<R> map(MayBe<T> mayBe, Function<? super T, ? extends R> mapper) {
            return MayBe.of(
                    Switch(mayBe)
                    .none((R)null)
                    .just((Just<T> just) -> mapper.apply(just.value())));
        }
        @SuppressWarnings("unchecked")
        default <R> MayBe<R> flatMap(MayBe<T> mayBe, Function<? super T, ? extends MayBe<R>> mapper) {
            return Switch(mayBe)
                    .none((MayBe<R>)MayBe.None())
                    .just(just -> mapper.apply(just.value()));
        }
        default T get(MayBe<T> mayBe) {
            return Switch(mayBe)
                    .none((T)null)
                    .just(just -> just.value());
        }
        default T orElse(MayBe<T> mayBe, T elseValue) {
            return Switch(mayBe)
                    .none((T)elseValue)
                    .just(just -> just.value());
        }
        default T orElseGet(MayBe<T> mayBe, Supplier<T> elseSupplier) {
            return Switch(mayBe)
                    .none(elseSupplier)
                    .just(just -> just.value());
        }
    }
    
    @Test
    public void testMethod() {
        // NOTE: Default implementation of equals for any Union type will return false if the parameter is null.
        //       This implementation accept null as equals to None.
        assertTrue  (None().equals(null));
        assertEquals("None", None().toString());
        assertEquals(0,      None().hashCode());
        
        assertFalse (Just("Hey!").equals(null));
        assertEquals("Just:Hey!",       Just("Hey!").toString());
        assertEquals("Hey!".hashCode(), Just("Hey!").hashCode());
    }
    @Test
    public void testPresent() {
        assertFalse(None().isPresent());
        assertTrue (Just("Hey!").isPresent());
    }
    @Test
    public void testOf() {
        assertFalse(MayBe.of(null).isPresent());
        assertTrue (MayBe.of("Hey!").isPresent());
    }
    @Test
    public void testMap() {
        assertEquals("None",   "" + MayBe.of(null)  .map(String::valueOf).map(String::length));
        assertEquals("Just:4", "" + MayBe.of("Hey!").map(String::valueOf).map(String::length));
    }
    @Test
    public void testFlatMap() {
        assertEquals("None",   "" + MayBe.of(null)  .map(String::valueOf).flatMap(s -> MayBe.of(s.length())));
        assertEquals("Just:4", "" + MayBe.of("Hey!").map(String::valueOf).flatMap(s -> MayBe.of(s.length())));
    }
    @Test
    public void testGet() {
        assertEquals("null", "" + MayBe.of(null)  .get());
        assertEquals("Hey!", "" + MayBe.of("Hey!").get());
        
        assertEquals("N/A",  "" + MayBe.of(null)  .orElse("N/A"));
        assertEquals("Hey!", "" + MayBe.of("Hey!").orElse("N/A"));
        
        assertEquals("N/A",  "" + MayBe.of(null)  .orElseGet(()->"N/A"));
        assertEquals("Hey!", "" + MayBe.of("Hey!").orElseGet(()->"N/A"));
    }
    
}

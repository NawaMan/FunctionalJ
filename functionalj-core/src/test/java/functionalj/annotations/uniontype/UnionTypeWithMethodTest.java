package functionalj.annotations.uniontype;

import static functionalj.annotations.uniontype.MayBe.Just;
import static functionalj.annotations.uniontype.MayBe.None;
import static functionalj.annotations.uniontype.UnionTypes.Switch;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.function.Consumer;
import java.util.function.Supplier;

import org.junit.Test;

import functionalj.annotations.UnionType;

@SuppressWarnings("javadoc")
public class UnionTypeWithMethodTest {
    
    @UnionType(name="MayBe")
    public interface MayBeSpec<T> {
        void None();
        void Just(T value);
        
        static <T> Self1<T> of(T value) {
            return Self1.of(
                    (value == null)
                    ? MayBe.None()
                    : MayBe.Just(value));
        }
        default boolean equals(Self1<T> self, Object obj) {
            MayBe<T> mayBe = self.asMe();
            if (obj == null)
                return mayBe.isNone();
            if (!(obj instanceof MayBe))
                return false;
            return mayBe
                    .toString()
                    .equals(obj.toString());
        }
        default int hashCode(Self1<T> self) {
            MayBe<T> mayBe = self.asMe();
            return Switch(mayBe)
                    .none(0)
                    .just(j -> j.value().hashCode());
        }
        default String toString(Self1<T> self) {
            MayBe<T> mayBe = self.asMe();
            return Switch(mayBe)
                    .none("None")
                    .just(m -> "Just:" + m.value());
        }
        default boolean isPresent(Self1<T> self) {
            MayBe<T> mayBe = self.asMe();
            return mayBe.isJust();
        }
        default Self1<T> ifPresent(Self1<T> self, Consumer<T> action) {
            MayBe<T> mayBe = self.asMe();
            MayBe<T> resultMayBe = Switch(mayBe)
                    .none(mayBe)
                    .just(just -> {
                        action.accept(just.value());
                        return mayBe;
                    });
            return Self1.of(resultMayBe);
        }
//        default <R> Self1<R> map(Self1<T> self, Function<? super T, ? extends R> mapper) {
//            MayBe<T> mayBe = self.asMe();
//            return MayBe.of(
//                    Switch(mayBe)
//                    .none((R)null)
//                    .just((Just<T> just) -> mapper.apply(just.value())));
//        }
//        default <R> Self1<R> flatMap(Self1<T> self, Function<? super T, ? extends Self1<R>> mapper) {
////            MayBe<T> mayBe = self.asMe();
////            return Switch(mayBe)
////                    .none((MayBe<R>)MayBe.None())
////                    .just(just -> mapper.apply(just.value()));
//            return null;
//        }
        default T get(Self1<T> self) {
            MayBe<T> mayBe = self.asMe();
            return Switch(mayBe)
                    .none((T)null)
                    .just(just -> just.value());
        }
        default T orElse(Self1<T> self, T elseValue) {
            MayBe<T> mayBe = self.asMe();
            return Switch(mayBe)
                    .none((T)elseValue)
                    .just(just -> just.value());
        }
        default T orElseGet(Self1<T> self, Supplier<T> elseSupplier) {
            MayBe<T> mayBe = self.asMe();
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
//    @Test
//    public void testMap() {
//        assertEquals("None",   "" + MayBe.of(null)  .map(String::valueOf).map(String::length));
//        assertEquals("Just:4", "" + MayBe.of("Hey!").map(String::valueOf).map(String::length));
//    }
//    @Test
//    public void testFlatMap() {
//        assertEquals("None",   "" + MayBe.of(null)  .map(String::valueOf).flatMap(s -> MayBe.of(s.length())));
//        assertEquals("Just:4", "" + MayBe.of("Hey!").map(String::valueOf).flatMap(s -> MayBe.of(s.length())));
//    }
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

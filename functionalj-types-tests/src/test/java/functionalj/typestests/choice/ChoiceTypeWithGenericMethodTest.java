// ============================================================================
// Copyright (c) 2017-2025 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
// ----------------------------------------------------------------------------
// MIT License
// 
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
// 
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
// 
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.
// ============================================================================
package functionalj.typestests.choice;

import static functionalj.types.choice.ChoiceTypes.Match;
import static functionalj.typestests.choice.Option.None;
import static functionalj.typestests.choice.Option.Some;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import org.junit.Test;
import functionalj.types.Choice;
import functionalj.types.Self1;

public class ChoiceTypeWithGenericMethodTest {
    
    @Choice
    public interface OptionSpec<T> {
        
        void None();
        
        void Some(T value);
        
        static <T> Self1<T> of(T value) {
            return Self1.wrap((value == null) ? Option.None() : Option.Some(value));
        }
        
        default boolean equals(Self1<T> self, Object obj) {
            Option<T> option = self.unwrap();
            if (obj == null)
                return option.isNone();
            if (!(obj instanceof Option))
                return false;
            return option.toString().equals(obj.toString());
        }
        
        default int hashCode(Self1<T> self) {
            Option<T> option = self.unwrap();
            return Match(option).none(__ -> 0).some(some -> some.value().hashCode());
        }
        
        default String toString(Self1<T> self) {
            Option<T> option = self.unwrap();
            return Match(option).none(__ -> "None").some(m -> "Some:" + m.value());
        }
        
        default boolean isPresent(Self1<T> self) {
            Option<T> option = self.unwrap();
            return option.isSome();
        }
        
        default Self1<T> ifPresent(Self1<T> self, Consumer<T> action) {
            Option<T> option = self.unwrap();
            Option<T> resultOption = Match(option).none(__ -> option).some(some -> {
                action.accept(some.value());
                return option;
            });
            return Self1.wrap(resultOption);
        }
        
        default <R> Self1<R> map(Self1<T> self, Function<? super T, ? extends R> mapper) {
            Option<T> option = self.unwrap();
            return Self1.wrap(Option.of(Match(option).none(__ -> (R) null).some((Option.Some<T> some) -> mapper.apply(some.value()))));
        }
        
        @SuppressWarnings("unchecked")
        default <R> Self1<R> flatMap(Self1<T> self, Function<? super T, ? extends Self1<R>> mapper) {
            Option<T> option = self.unwrap();
            return Match(option).none(__ -> (Option<R>) Option.None()).some(some -> mapper.apply(some.value()).unwrap());
        }
        
        default T get(Self1<T> self) {
            Option<T> option = self.unwrap();
            return Match(option).none(__ -> (T) null).some(some -> some.value());
        }
        
        default T orElse(Self1<T> self, T elseValue) {
            Option<T> option = self.unwrap();
            return Match(option).none(__ -> (T) elseValue).some(some -> some.value());
        }
        
        default T orElseGet(Self1<T> self, Supplier<T> elseSupplier) {
            Option<T> option = self.unwrap();
            return Match(option).none(elseSupplier).some(some -> some.value());
        }
        
        default <E extends Exception> T orElseThrow(Self1<T> self, Supplier<E> exceptionSupplier) throws E {
            Option<T> option = self.unwrap();
            if (option.isNone())
                throw exceptionSupplier.get();
            return option.get();
        }
    }
    
    @Test
    public void testMethod() {
        // NOTE: Default implementation of equals for any Choice type will return false if the parameter is null.
        // This implementation accept null as equals to None.
        assertTrue(None().equals(null));
        assertEquals("None", None().toString());
        assertEquals(0, None().hashCode());
        assertFalse(Some("Hey!").equals(null));
        assertEquals("Some:Hey!", Some("Hey!").toString());
        assertEquals("Hey!".hashCode(), Some("Hey!").hashCode());
    }
    
    @Test
    public void testPresent() {
        assertFalse(None().isPresent());
        assertTrue(Some("Hey!").isPresent());
    }
    
    @Test
    public void testOf() {
        assertFalse(Option.of(null).isPresent());
        assertTrue(Option.of("Hey!").isPresent());
    }
    
    @Test
    public void testMap() {
        assertEquals("None", "" + Option.of(null).map(String::valueOf).map(String::length));
        assertEquals("Some:4", "" + Option.of("Hey!").map(String::valueOf).map(String::length));
    }
    
    @Test
    public void testFlatMap() {
        assertEquals("None", "" + Option.of(null).map(String::valueOf).flatMap(s -> Option.of(s.length())));
        assertEquals("Some:4", "" + Option.of("Hey!").map(String::valueOf).flatMap(s -> Option.of(s.length())));
    }
    
    @Test
    public void testGet() {
        assertEquals("null", "" + Option.of(null).get());
        assertEquals("Hey!", "" + Option.of("Hey!").get());
        assertEquals("N/A", "" + Option.of(null).orElse("N/A"));
        assertEquals("Hey!", "" + Option.of("Hey!").orElse("N/A"));
        assertEquals("N/A", "" + Option.of(null).orElseGet(() -> "N/A"));
        assertEquals("Hey!", "" + Option.of("Hey!").orElseGet(() -> "N/A"));
    }
}

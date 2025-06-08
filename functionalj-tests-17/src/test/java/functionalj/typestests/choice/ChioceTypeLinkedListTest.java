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

import static functionalj.TestHelper.assertAsString;
import static functionalj.function.Func.recusive;
import static functionalj.types.choice.ChoiceTypes.Match;
import static functionalj.typestests.choice.LinkedList.Nill;
import static functionalj.typestests.choice.LinkedList.Node;
import static org.junit.Assert.assertEquals;

import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;

import org.junit.Test;

import functionalj.types.Choice;
import functionalj.types.Self1;
import functionalj.typestests.choice.LinkedList.LinkedListFirstSwitch;
import lombok.val;

public class ChioceTypeLinkedListTest {
    
    @Choice
    public static interface LinkedListSpec<T> {
        
        void Nill();
        
        void Node(T value, Self1<T> rest);
        
        default int length(Self1<T> self) {
            LinkedList<T> list = Self1.unwrap(self);
            return Match(list).nill(l -> 0).node(l -> 1 + length(l.rest()));
        }
    }
    
    <T> String toStr(LinkedList<T> list) {
        return Match(list)
        		.nill(l -> "[]")
        		.node(l -> "[" + l.value() + "," + toStr(l.rest()) + "]");
    }

    @SuppressWarnings("unchecked")
    <T>String toStr2(LinkedList<T> list) {
        val noBracketToStr = recusive((f, l) -> {
            return Match((LinkedList<T>) l)
            		.nill(l2 -> "")
            		.node(l2 -> l2.value() 
            				    + ((LinkedListFirstSwitch<T>) Match(l2.rest()))
            				    	.nill(__ -> "")
            				    	.node(lr -> "," + f.apply(lr))
			    	);
        });
        return "[" + noBracketToStr.apply(list) + "]";
    }

    @SuppressWarnings("unchecked")
    <T> String toStrReverse(LinkedList<T> list) {
        val noBracketToStr = recusive((f, l) -> {
            return Match((LinkedList<T>) l)
            		.nill(l2 -> "")
            		.node(l2 -> ((LinkedListFirstSwitch<T>) Match(l2.rest()))
            						.nill(__ -> "")
            						.node(lr -> f.apply(lr) + ",") 
        						+ l2.value());
        });
        return "[" + noBracketToStr.apply(list) + "]";
    }
    
    <T> LinkedList<T> append(LinkedList<T> list, T value) {
        return Node(value, list);
    }
    
    @SuppressWarnings("unchecked")
	<T, D> LinkedList<D> map(LinkedList<T> list, Function<T, D> mapper) {
        return Match(list)
        		.toA(LinkedList.class)
        		.nill(Nill())
        		.node(l -> Node(mapper.apply(l.value()), map(l.rest(), mapper)));
    }

    @SuppressWarnings("unchecked")
    <T> LinkedList<T> filter(LinkedList<T> list, Predicate<T> filter) {
        return Match(list)
        		.toA(LinkedList.class)
        		.nill(Nill())
        		.node(l -> filter.test(l.value())
        					? Node(l.value(), filter(l.rest(), filter))
							: filter(l.rest(), filter));
    }
    
    @SuppressWarnings("unchecked")
	<T> T reduce(LinkedList<T> list, BinaryOperator<T> operator) {
        return (T) Match(list)
        		.toA(Object.class)
        		.nill(__ -> null)
        		.node(l -> l.rest().isNill() 
        					? l.value() 
        					: operator.apply(l.value(), reduce(l.rest(), operator)));
    }
    
    @Test
    public void testLength() {
        assertEquals(0, Nill().length());
        assertEquals(1, Node(5, Nill()).length());
        assertEquals(2, Node(5, Node(6, Nill())).length());
    }
    
    @Test
    public void testToString() {
        assertAsString("Nill",                 Nill());
        assertAsString("Node(5,Nill)",         Node(5, Nill()));
        assertAsString("Node(5,Node(6,Nill))", Node(5, Node(6, Nill())));
    }
    
    @Test
    public void testToStr() {
    	assertAsString("[]",         toStr(Nill()));
    	assertAsString("[5,[]]",     toStr(Node(5, Nill())));
    	assertAsString("[5,[6,[]]]", toStr(Node(5, Node(6, Nill()))));
    }
    
    @Test
    public void testToStr2() {
    	assertAsString("[]",    toStr2(Nill()));
    	assertAsString("[5]",   toStr2(Node(5, Nill())));
    	assertAsString("[5,6]", toStr2(Node(5, Node(6, Nill()))));
    }
    
    @Test
    public void testToStrReverse() {
        LinkedList<Integer> l = Nill();
        assertAsString("[]",      toStrReverse(l));
        assertAsString("[5]",     toStrReverse(l = append(l, 5)));
        assertAsString("[5,6]",   toStrReverse(l = append(l, 6)));
        assertAsString("[5,6,7]", toStrReverse(l = append(l, 7)));
    }
    
    @Test
    public void testMap() {
        LinkedList<String> l = Nill();
        l = append(l, "One");
        l = append(l, "Two");
        l = append(l, "Three");
        l = append(l, "Four");
        
        assertAsString("[One,Two,Three,Four]", toStrReverse(l));
        assertAsString("[3,3,5,4]",            toStrReverse(map(l, o -> ((String) o).length())));
    }
    
    @Test
    public void testFilter() {
        LinkedList<String> strs = Nill();
        strs = append(strs, "One");
        strs = append(strs, "Two");
        strs = append(strs, "Three");
        strs = append(strs, "Four");
        assertEquals("[One,Two,Three,Four]", toStrReverse(strs));
        
        var lengths = map(strs, str -> str.length());
        assertAsString("[3,3,5,4]", toStrReverse(lengths));
        assertAsString("[5,4]",     toStrReverse(filter(lengths, length -> length >= 4)));
    }
    
    @Test
    public void testReduce() {
        LinkedList<String> strs = Nill();
        strs = append(strs, "One");
        strs = append(strs, "Two");
        strs = append(strs, "Three");
        strs = append(strs, "Four");
        assertAsString("[One,Two,Three,Four]", toStrReverse(strs));
        
        var lengths = map(strs, str -> str.length());
        assertAsString("[3,3,5,4]", toStrReverse(lengths));
        assertAsString("15",        "" + reduce(lengths, (a, b) -> a + b));
    }
    
    @Test
    public void testPipeable() {
        LinkedList<String> strs = Nill();
        strs = append(strs, "One");
        strs = append(strs, "Two");
        strs = append(strs, "Three");
        strs = append(strs, "Four");

        var lengths = map(strs, str -> str.length());
        assertAsString("Node(4,Node(5,Node(3,Node(3,Nill))))", lengths.pipeTo(String::valueOf));
    }
}

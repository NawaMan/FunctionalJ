package functionalj.types.choice;

import static functionalj.function.Func.recusive;
import static functionalj.types.choice.ChoiceTypes.Match;
import static functionalj.types.choice.LinkedList.Nill;
import static functionalj.types.choice.LinkedList.Node;
import static org.junit.Assert.assertEquals;

import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;

import org.junit.Test;

import functionalj.types.Choice;
import functionalj.types.choice.LinkedList;
import lombok.val;

@SuppressWarnings("javadoc")
public class ChioceTypeLinkedListTest {
    
    @Choice
    public static interface LinkedListSpec {
        void Nill();
        void Node(Object value, LinkedList rest);
    }
    
    int length(LinkedList list) {
        return Match(list)
                .nill(l -> 0)
                .node(l -> 1 + length(l.rest()));
    }
    String toStr(LinkedList list) {
        return Match(list)
                .nill(l -> "[]")
                .node(l -> "[" + l.value() + "," + toStr(l.rest()) + "]");
    }
    String toStr2(LinkedList list) {
        val noBracketToStr = recusive((f, l) -> {
            return Match((LinkedList)l)
                     .nill(l2 -> "")
                     .node(l2 -> l2.value() + 
                             Match(l2.rest())
                                   .nill(__ -> "")
                                   .node(lr -> "," + f.apply(lr)));
        });
        return "[" + noBracketToStr.apply(list) + "]";
    }
    String toStrReverse(LinkedList list) {
        val noBracketToStr = recusive((f, l) -> {
            return Match((LinkedList)l)
                     .nill(l2 -> "")
                     .node(l2 -> Match(l2.rest())
                                   .nill(__ -> "")
                                   .node(lr -> f.apply(lr) + ",")
                                 + l2.value());
        });
        return "[" + noBracketToStr.apply(list) + "]";
    }
    LinkedList append(LinkedList list, Object value) {
        return Node(value, list);
    }
    LinkedList map(LinkedList list, Function<Object, Object> mapper) {
        return Match(list)
                .toA(LinkedList.class)
                .nill(      Nill())
                .node(l  -> Node(mapper.apply(l.value()), map(l.rest(), mapper)));
    }
    LinkedList filter(LinkedList list, Predicate<Object> filter) {
        return Match(list)
                .toA(LinkedList.class)
                .nill(      Nill())
                .node(l  -> (filter.test(l.value()) ? Node(l.value(), filter(l.rest(), filter)) : filter(l.rest(), filter)));
    }
    Object reduce(LinkedList list, BinaryOperator<Object> operator) {
        return Match(list)
                .toA(Object.class)
                .nill(__ -> null)
                .node(l  -> l.rest().isNill() ? l.value() : operator.apply(l.value(), reduce(l.rest(), operator)));
    }
    
    @Test
    public void testLength() {
        assertEquals(0, length(Nill()));
        assertEquals(1, length(Node(5, Nill())));
        assertEquals(2, length(Node(5, Node(6, Nill()))));
    }
    
    @Test
    public void testToString() {
        assertEquals("Nill", "" + Nill());
        assertEquals("Node(5,Nill)", "" + Node(5, Nill()));
        assertEquals("Node(5,Node(6,Nill))", "" + Node(5, Node(6, Nill())));
    }
    
    @Test
    public void testToStr() {
        assertEquals("[]", toStr(Nill()));
        assertEquals("[5,[]]", toStr(Node(5, Nill())));
        assertEquals("[5,[6,[]]]", toStr(Node(5, Node(6, Nill()))));
    }
    
    @Test
    public void testToStr2() {
        assertEquals("[]", toStr2(Nill()));
        assertEquals("[5]", toStr2(Node(5, Nill())));
        assertEquals("[5,6]", toStr2(Node(5, Node(6, Nill()))));
    }
    
    @Test
    public void testToStrReverse() {
        LinkedList l = Nill();
        assertEquals("[]",      toStrReverse(l));
        assertEquals("[5]",     toStrReverse(l = append(l, 5)));
        assertEquals("[5,6]",   toStrReverse(l = append(l, 6)));
        assertEquals("[5,6,7]", toStrReverse(l = append(l, 7)));
    }
    
    @Test
    public void testMap() {
        LinkedList l = Nill();
        l = append(l, "One");
        l = append(l, "Two");
        l = append(l, "Three");
        l = append(l, "Four");
        assertEquals("[One,Two,Three,Four]", toStrReverse(l));
        assertEquals("[3,3,5,4]",            toStrReverse(map(l, o -> ((String)o).length())));
    }
    
    @Test
    public void testFilter() {
        LinkedList l = Nill();
        l = append(l, "One");
        l = append(l, "Two");
        l = append(l, "Three");
        l = append(l, "Four");
        assertEquals("[One,Two,Three,Four]", toStrReverse(l));
        assertEquals("[3,3,5,4]",            toStrReverse(map(l, o -> ((String)o).length())));
        assertEquals("[5,4]",                toStrReverse(filter(map(l, o -> ((String)o).length()), o -> ((Integer)o) >= 4)));
    }
    
    @Test
    public void testReduce() {
        LinkedList l = Nill();
        l = append(l, "One");
        l = append(l, "Two");
        l = append(l, "Three");
        l = append(l, "Four");
        assertEquals("[One,Two,Three,Four]", toStrReverse(l));
        assertEquals("[3,3,5,4]",            toStrReverse(l = map(l, o -> ((String)o).length())));
        assertEquals("15",                   "" + reduce(l, (a,b)->((Integer)a)+((Integer)b)));
    }
    
    @Test
    public void testPipeable() {
        LinkedList l = Nill();
        l = append(l, "One");
        l = append(l, "Two");
        l = append(l, "Three");
        l = append(l, "Four");
        l = map(l, o -> ((String)o).length());
        assertEquals("Node(4,Node(5,Node(3,Node(3,Nill))))", l.pipe(String::valueOf));
    }
    
}

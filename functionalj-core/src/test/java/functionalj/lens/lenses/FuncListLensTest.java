package functionalj.lens.lenses;

import static functionalj.function.Func.f;
import static functionalj.lens.Access.theString;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import functionalj.lens.core.LensSpec;
import functionalj.lens.core.WriteLens;
import functionalj.list.FuncList;
import lombok.val;

public class FuncListLensTest {

    private final FuncListLens<FuncList<String>, String, StringLens<FuncList<String>>> listLens;

    public FuncListLensTest() {
        val readLens = f((FuncList<String> list) -> list);
        val writeLens = (WriteLens<FuncList<String>, FuncList<String>>) ((list, newList) -> newList);
        val subCreator = f((LensSpec<FuncList<String>, String> spec) -> StringLens.of(spec));
        listLens = FuncListLens.of(readLens, writeLens, subCreator);
    }

    @Test
    public void testAt() {
        val list = FuncList.of("Zero", "One", "Two", "Three", "Four");
        val at3 = listLens.at(3);
        assertEquals("Three", at3.apply(list).toString());
        assertEquals("[Zero, One, Two, Tri, Four]", at3.changeTo("Tri").apply(list).toString());
    }

    @Test
    public void testEach() {
        val list = FuncList.of("Zero", "One", "Two", "Three", "Four");
        val lengthStr = listLens.each().changeTo(s -> "" + s.length());
        assertEquals("[4, 3, 3, 5, 4]", lengthStr.apply(list).toString());
        val changeShort = listLens.eachOf(theString.length().thatLessThan(4)).changeToNull();
        assertEquals("[Zero, null, null, Three, Four]", changeShort.apply(list).toString());
        val addTail = listLens.eachOf(theString.length().thatLessThan(4)).changeTo(str -> (str + "~~~~").substring(0, 4));
        assertEquals("[Zero, One~, Two~, Three, Four]", addTail.apply(list).toString());
    }
}

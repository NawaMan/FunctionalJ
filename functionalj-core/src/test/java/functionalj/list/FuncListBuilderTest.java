package functionalj.list;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import lombok.val;



public class FuncListBuilderTest {
    
    @Test
    public void testListBuilder() {
        val map = FuncList.newList(String.class)
                .add("A")
                .add("B")
                .add("C")
                .add("D")
                .add("E")
                .add("F")
                .add("G")
                .add("H")
                .add("I")
                .add("J")
                .add("J")
                .add("L")
                .add("M")
                .add("N")
                .add("O")
                .add("P")
                .add("Q")
                .add("R")
                .add("S")
                .add("T")
                .add("U")
                .add("V")
                .add("W")
                .add("X")
                .add("Y")
                .add("Z")
                .build();
        assertEquals(
                "[A, B, C, D, E, F, G, H, I, J, J, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z]",
                map.toString());
    }
    
}

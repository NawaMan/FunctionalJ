package functionalj.list;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import lombok.val;

public class FuncListBuilderTest {
    
    @Test
    public void testListBuilder() {
        val map = FuncList.newList()
                .with("A")
                .with("B")
                .with("C")
                .with("D")
                .with("E")
                .with("F")
                .with("G")
                .with("H")
                .with("I")
                .with("J")
                .with("J")
                .with("L")
                .with("M")
                .with("N")
                .with("O")
                .with("P")
                .with("Q")
                .with("R")
                .with("S")
                .with("T")
                .with("U")
                .with("V")
                .with("W")
                .with("X")
                .with("Y")
                .with("Z")
                .build();
        assertEquals(
                "[A, B, C, D, E, F, G, H, I, J, J, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z]",
                map.toString());
    }
    
}

package functionalj.functions;

import static functionalj.lens.Access.theInt;
import static functionalj.stream.intstream.IntStreamPlus.iterate;
import lombok.val;

public class IntFuncs {
    
    private static int[] factors = iterate(1, theInt.time(10)).limit(10).toArray();
    
    public static int digitAt(int value, int digit) {
        if (digit < 0)
            return 0;
        if (digit > 9)
            return 0;
        val factor = factors[digit];
        return (value / factor) % 10;
    }
    
    public static int digitValueAt(int value, int digit) {
        if (digit < 0)
            return 0;
        if (digit > 9)
            return 0;
        val factor = factors[digit];
        return ((value / factor) % 10) * factor;
    }
    
    public static int factorValueAt(int value, int digit) {
        if (digit < 0)
            return 0;
        if (digit > 9)
            return 0;
        val factor = factors[digit];
        return (value / factor) == 0 ? 0 : factor;
    }
    
    public static int factorial(int value) {
        if (value <= 0) {
            return 1;
        }
        int factorial = 1;
        for (int i = 1; i <= value; i++) {
            factorial *= i;
        }
        return factorial;
    }
    
    public static int largestFactorOf(int value) {
        for (int index = factors.length; index-- > 0; ) {
            val factor = factors[index];
            if (factor < value) {
                return factor;
            }
        }
        return 0;
    }
    
    public static int largestFactorIndexOf(int value) {
        for (int index = factors.length; index-- > 0; ) {
            val factor = factors[index];
            if (factor < value) {
                return index;
            }
        }
        return 0;
    }
    // TODO - toBinary
    // TODO - toHex
    // TODO - toBase
}

package functionalj.functions;

public class LongFuncs {

    public static long factorial(long value) {
        if (value <= 0) {
            return 1;
        }
        long factorial = 1;
        for (long i = 1; i <= value; i++) {
            factorial *= i;
        }
        return factorial;
    }
    // TODO - toBinary
    // TODO - toHex
    // TODO - toBase
}

package functionalj.pipeable;

import functionalj.functions.Func1;

public interface NullSafeOperator<INPUT, OUTPUT> extends Func1<INPUT, OUTPUT> {
    
    public static <I, O> NullSafeOperator<I, O> of(Func1<I, O> func1) {
        return func1::applyUnsafe;
    }
    
}

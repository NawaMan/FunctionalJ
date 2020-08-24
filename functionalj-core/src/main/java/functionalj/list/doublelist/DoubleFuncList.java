package functionalj.list.doublelist;

import java.util.function.DoubleUnaryOperator;

public interface DoubleFuncList {
    
    DoubleFuncList append(double d);
    
    DoubleFuncList sorted();
    
    DoubleFuncList map(DoubleUnaryOperator operator);
    
    // TODO DoubleImmutableList
    DoubleFuncList toImmutableList();
    
    int size();
    
    double get(int i);

    void add(int size);

}

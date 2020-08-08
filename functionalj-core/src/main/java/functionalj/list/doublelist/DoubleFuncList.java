package functionalj.list.doublelist;

import java.util.function.DoubleUnaryOperator;

import functionalj.list.FuncList;
import functionalj.list.intlist.IntFuncList;

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

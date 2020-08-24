package functionalj.list;

import functionalj.streamable.AsStreamable;

public interface AsFuncList<DATA> extends AsStreamable<DATA> {
    
    public default FuncList<DATA> funcList() {
        return FuncList.from(this);
    }
    
}

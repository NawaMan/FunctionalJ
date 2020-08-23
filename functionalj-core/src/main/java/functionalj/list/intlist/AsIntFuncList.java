package functionalj.list.intlist;

import functionalj.streamable.intstreamable.AsIntStreamable;

public interface AsIntFuncList extends AsIntStreamable {
    
    public default IntFuncList intFuncList() {
        return IntFuncList.from(this);
    }
    
}

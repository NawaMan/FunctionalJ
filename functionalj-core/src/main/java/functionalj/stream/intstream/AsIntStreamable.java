package functionalj.stream.intstream;

import functionalj.stream.AsStreamable;

public interface AsIntStreamable extends AsStreamable<Integer> {
    
    public default IntStreamable intStreamable() {
        return ()->intStream();
    }
    
    public IntStreamPlus intStream();
    
}

package functionalj.stream.intstream;

import functionalj.stream.HasStreamable;

public interface HasIntStreamable extends HasStreamable<Integer> {
    
    public default IntStreamable intStreamable() {
        return ()->intStream();
    }
    
    public IntStreamPlus intStream();
    
}

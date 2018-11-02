package functionalj.ref;

@FunctionalInterface
public interface ProcessBody<INPUT, OUTPUT, EXCEPTION extends Exception> {
    
    public OUTPUT process(INPUT input) throws EXCEPTION;
    
}

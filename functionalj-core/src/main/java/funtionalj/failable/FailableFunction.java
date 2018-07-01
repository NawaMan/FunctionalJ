package funtionalj.failable;

@FunctionalInterface
public interface FailableFunction<INPUT, OUTPUT> {
    
    public OUTPUT apply(INPUT input) throws Exception;
    
}

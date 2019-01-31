package functionalj.types;

public interface IRule<D> {
    // TODO - Think about what to put in here. 
    // May be the spec or validator.
    
    public String   __dataName();
    public Class<D> __dataType();
    public D        __dataValue();
    public <R extends IRule<D>> Class<R> __superRule();
}

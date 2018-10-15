package functionalj.result;

public class ImmutableResult<DATA> extends Result<DATA> {
    
    private final Object data;
    
    public ImmutableResult(DATA data) {
        this(data, null);
    }
    ImmutableResult(DATA data, Exception exception) {
        this.data = (exception != null)
                ? new ExceptionHolder(exception)
                : data ;
    }
    
    @Override
    Object __valueData() {
        return data;
    }
    
}

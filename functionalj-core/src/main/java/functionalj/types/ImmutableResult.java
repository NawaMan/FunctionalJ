package functionalj.types;

public final class ImmutableResult<DATA> extends Result<DATA> {

    @Override
    public <TARGET> ImmutableResult<TARGET> _of(TARGET target) {
        return ImmutableResult.of(target);
    }
    
    private final Object data;
    
    public ImmutableResult(DATA value, Exception exception) {
        this.data = ((value == null) && (exception != null))
                ? new ExceptionHolder(exception)
                : value;
    }
    
    protected Object getData() {
        return data;
    }
    
    public ImmutableResult<DATA> toImmutable() {
        return this;
    }
    
}

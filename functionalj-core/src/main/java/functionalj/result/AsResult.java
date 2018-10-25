package functionalj.result;

@FunctionalInterface
public interface AsResult<DATA> extends HasResult<DATA> {

    public Result<DATA> asResult();
    
    public default Result<DATA> getResult() {
        return asResult();
    }
    
    // TODO - Add Result methods here. - so that class that implement this class automatically go those method.
    // For example, get, orElse, orGet
    
}

package functionalj.promise;

public class NamedPromise<DATA> extends Promise<DATA> {
    
    private final String name;
    
    NamedPromise(@SuppressWarnings("rawtypes") Promise parent, String name) {
        super(parent);
        this.name = name;
    }
    
    public String toString() {
        return name;
    }
    
}

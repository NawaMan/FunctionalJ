package functionalj.ref;

import static java.util.Objects.requireNonNull;

import functionalj.result.Result;
import lombok.val;

public class DictatedRef<DATA> extends Ref<DATA> {
    
    private final Ref<DATA> ref;
    
    public DictatedRef(Ref<DATA> ref) {
        super(ref.getDataType());
        this.ref = requireNonNull(ref);
    }
    
    @Override
    Result<DATA> findResult() {
        return ref.findResult();
    }
    
    Result<DATA> findOverrideResult() {
        return null;
    }
    
    @Override
    public int hashCode() {
        return ref.hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (this == obj)
            return true;
        if (ref == obj)
            return true;
        if (obj instanceof DictatedRef) {
            @SuppressWarnings("rawtypes")
            val dictatedRef = (DictatedRef)obj;
            if (ref.equals(dictatedRef.ref))
                return true;
            if (this.equals(dictatedRef.ref))
                return true;
        }
        return ref.equals(obj);
    }
    
}

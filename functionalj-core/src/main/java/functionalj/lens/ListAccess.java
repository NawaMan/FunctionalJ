package functionalj.lens;

import java.util.Collection;
import java.util.List;

@FunctionalInterface
public interface ListAccess<HOST, TYPE, LIST extends List<TYPE>> 
        extends CollectionAccess<HOST, TYPE, LIST> {
    
}

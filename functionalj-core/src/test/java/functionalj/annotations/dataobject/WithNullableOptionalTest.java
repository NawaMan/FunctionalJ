package functionalj.annotations.dataobject;

import java.util.Optional;

import functionalj.annotations.DataObject;
import nawaman.nullablej.nullable.Nullable;

@SuppressWarnings("javadoc")
public class WithNullableOptionalTest {

    @DataObject(name="ParentWithNullableOptional")
    public static interface IParent3 {
        
        public Nullable<String> nullableName();
        public Optional<String> optionalName();
        
    }
}

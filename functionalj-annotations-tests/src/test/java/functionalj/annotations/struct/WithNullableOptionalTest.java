package functionalj.annotations.struct;

import java.util.Optional;

import functionalj.types.Struct;
import nawaman.nullablej.nullable.Nullable;

@SuppressWarnings("javadoc")
public class WithNullableOptionalTest {

    @Struct(name="ParentWithNullableOptional")
    public static interface IParent3 {
        
        public Nullable<String> nullableName();
        public Optional<String> optionalName();
        
    }
}

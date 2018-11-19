package functionalj.annotations.record;

import java.util.Optional;

import functionalj.annotations.Record;
import nawaman.nullablej.nullable.Nullable;

@SuppressWarnings("javadoc")
public class WithNullableOptionalTest {

    @Record(name="ParentWithNullableOptional")
    public static interface IParent3 {
        
        public Nullable<String> nullableName();
        public Optional<String> optionalName();
        
    }
}

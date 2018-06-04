package functionalj.annotations;

import java.util.List;
import java.util.Optional;

import functionalj.types.MayBe;
import nawaman.nullablej.nullable.Nullable;

@SuppressWarnings("javadoc")
public class WithNullableOptionalMayBeTest {

    @DataObject(name="ParentWithNullableOptionalMayBe")
    public static interface IParent3 {
        
        public Nullable<String> nullableName();
        public Optional<String> optionalName();
        public MayBe<String>    mayBeName();
        
    }
}

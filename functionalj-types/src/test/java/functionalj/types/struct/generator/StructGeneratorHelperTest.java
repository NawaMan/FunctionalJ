package functionalj.types.struct.generator;

import static functionalj.types.struct.generator.StructGeneratorHelper.reqOnlyConstBody;
import static java.util.stream.Collectors.joining;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Test;

import functionalj.types.DefaultValue;
import functionalj.types.Type;

public class StructGeneratorHelperTest {
    
    @Test
    public void testReqOnlyConstBody() {
        Getter getter1 = new Getter("field1", Type.STRING);
        Getter getter2 = new Getter("field2", Type.BIGDECIMAL, false, DefaultValue.ZERO);
        Getter getter3 = new Getter("field3", Type.INT);
        
        List<Getter>   getters  = Arrays.asList(getter1, getter2, getter3);
        Stream<String> validate = Stream.empty();
        String         body     = reqOnlyConstBody(getters, validate).stream().collect(joining("\n"));
        assertEquals(
                "this($utils.notNull(field1), java.math.BigDecimal.ZERO, $utils.notNull(field3));\n"
              + "if (IPostConstruct.class.isInstance(this)) IPostConstruct.class.cast(this).postConstruct();", 
              body);
    }
    
}

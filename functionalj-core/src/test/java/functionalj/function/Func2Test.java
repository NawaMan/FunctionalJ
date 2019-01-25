package functionalj.function;

import static functionalj.function.Func.f;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import functionalj.promise.Promise;
import functionalj.result.Result;

public class Func2Test {

    private Func2<String, String, String> concat = f(String::concat);
    
    @Test
    public void testApplyBare() {
        assertEquals("Hello world!",                   "" + concat.applyTo("Hello",            " world!"));
        assertEquals("Result:{ Value: Hello world! }", "" + concat.applyTo(Result .valueOf("Hello"), Result .valueOf(" world!")));
        assertEquals("Result:{ Value: Hello world! }", "" + concat.applyTo(Promise.of("Hello"), Promise.of(" world!")).getResult());
    }

}

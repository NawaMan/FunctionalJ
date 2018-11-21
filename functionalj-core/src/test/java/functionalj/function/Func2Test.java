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
        assertEquals("Hello world!",                   "" + concat.apply("Hello",            " world!"));
        assertEquals("Result:{ Value: Hello world! }", "" + concat.apply(Result .of("Hello"), Result .of(" world!")));
        assertEquals("Result:{ Value: Hello world! }", "" + concat.apply(Promise.of("Hello"), Promise.of(" world!")).getResult());
    }

}

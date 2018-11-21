package functionalj.functions;

import static functionalj.function.Absent.__;
import static functionalj.function.Func.f;
import static functionalj.functions.TimeFuncs.Sleep;
import static functionalj.lens.Access.$I;
import static functionalj.pipeable.Pipeable.StartWtih;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import functionalj.function.Func;
import functionalj.promise.Promise;
import functionalj.result.Result;
import functionalj.stream.StreamPlus;
import lombok.val;

public class Func1Test {
    
    @Test
    public void testElevate() {
        val concat = Func.of(String::concat);
        val appendSpace       = concat.elevateWith(" ");
        val appendWorld       = concat.elevateWith("World");
        val appendExclamation = concat.bind(__, "!");
        val str = StartWtih("Hello").pipe(
                    appendSpace,
                    appendWorld,
                    appendExclamation
                );
        assertEquals("Hello World!", str);
    }
    
    @Test
    public void testDefer() {
        val startTime = System.currentTimeMillis();
        
        val length =   Sleep(50).then(String::valueOf).async().apply("Hello!").getPromise()
                .chain(Sleep(50).then(String::length ).async())
                .getResult();
        
        val duration = System.currentTimeMillis() - startTime;
        assertEquals(6, length.get().intValue());
        
        assertTrue(duration > 100);
    }
    
    @Test
    public void testApply() {
        val result  = Result.of("Hello");
        val promise = Promise.of("Hello");
        val stream  = f(()->StreamPlus.infiniteInt().limit(5).map($I.asString()));
        val list    = StreamPlus.infiniteInt().limit(5).map($I.asString()).toList();
        val map     = StreamPlus.infiniteInt().limit(5).toMap($I, $I.asString());
        val func    = f(String::length);
        assertEquals("Result:{ Value: Hello }",   "" + result);
        assertEquals("Result:{ Value: Hello }",   "" + promise.getResult());
        assertEquals("0, 1, 2, 3, 4",             "" + stream.get().joining(", "));
        assertEquals("[0, 1, 2, 3, 4]",           "" + list);
        assertEquals("{0:0, 1:1, 2:2, 3:3, 4:4}", "" + map);
        assertEquals("Result:{ Value: 5 }",       "" + func.apply(result));
        assertEquals("Result:{ Value: 5 }",       "" + func.apply(promise).getResult());
        assertEquals("1, 1, 1, 1, 1",             "" + func.apply(stream.get()).joining(", "));
        assertEquals("[1, 1, 1, 1, 1]",           "" + func.apply(list));
        assertEquals("{0:1, 1:1, 2:1, 3:1, 4:1}", "" + func.apply(map));
    }
    
}

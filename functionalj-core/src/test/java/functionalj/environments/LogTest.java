package functionalj.environments;

import static functionalj.ref.Run.With;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import functionalj.list.FuncList;
import functionalj.stream.StreamPlus;
import lombok.val;

public class LogTest {
    
    @Test
    public void testlog() {
        With(Env.refs.console.butWith(Console.Stub.instance))
        .run(()->{
            Log.log("One");
            Log.log("2: ", "Two", " --");
            Log.logEach("Three", "Four");
            Log.logEach("-->", FuncList.listOf("Three", "Four"), "<--");
            Log.logBy(()-> "42");
            
            val outLines = StreamPlus.from(Console.Stub.instance.outLines()).toList();
            assertEquals(
                    "[One, 2: Two --, Three, Four, -->Three<--, -->Four<--, 42]",
                    outLines.toString());
            
            Console.Stub.instance.clearOutLines();
        });
    }
    
}

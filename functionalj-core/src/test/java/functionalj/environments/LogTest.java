package functionalj.environments;

import static functionalj.ref.Run.With;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import functionalj.list.FuncList;
import functionalj.stream.StreamPlus;
import lombok.val;

public class LogTest {
    
    // TODO - Break this.
    @Test
    public void testLog() {
        val stub = new Console.Stub();
        With(Env.refs.console.butWith(stub))
        .run(()->{
            Log.log("One");
            Log.log("2: ", "Two", " --");
            Log.logEach("Three", "Four");
            Log.logEach("-->", FuncList.listOf("Three", "Four"), "<--");
            Log.logBy(()-> "42");
            
            val outLines = StreamPlus.from(stub.outLines()).toJavaList();
            assertEquals(
                    "[One, 2: Two --, Three, Four, -->Three<--, -->Four<--, 42]",
                    outLines.toString());
            
            stub.clearOutLines();
        });
    }
    @Test
    public void testLogErr() {
        val stub = new Console.Stub();
        With(Env.refs.console.butWith(stub))
        .run(()->{
            try {
                throw new NullPointerException("NULL!!!");
            } catch (Exception e) {
                Log.logErr("Error!", e);
            }
            val expected =
                    "Error!\n" + 
                    "java.lang.NullPointerException: NULL!!!";
            assertEquals(expected, stub.errLines().limit(2).joining("\n"));
        });
    }
    
}

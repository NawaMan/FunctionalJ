package functionalj.environments;

import static functionalj.ref.Run.With;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import functionalj.stream.StreamPlus;
import lombok.val;

public class ConsoleTest {
    
    @Test
    public void testOut() {
        val sysOut = System.out;
        val buffer = new ByteArrayOutputStream();
        val stream = new PrintStream(buffer);
        System.setOut(stream);
        try {
            Console
            .outPrintln("One")
            .outPrintln("Two");
        } finally {
            System.setOut(sysOut);
            assertEquals(
                    "One\n" + 
                    "Two\n",
                    buffer.toString());
        }
    }
    
    @Test
    public void testIn() {
        val sysIn = System.in;
        val stream = new ByteArrayInputStream("One\nTwo\n".getBytes());
        System.setIn(stream);
        try {
            val console = Env.console();
            assertEquals("One", console.readln());
            assertEquals("Two", console.readln());
        } finally {
            System.setIn(sysIn);
        }
    }
    
    @Test
    public void testStub_out() {
        val stub = new Console.Stub();
        With(Env.refs.console.butWith(stub))
        .run(()->{
            Console
            .println("One")
            .println("Two");
            
            val outLines = StreamPlus.from(stub.outLines()).toList();
            assertEquals(
                    "[One, Two]",
                    outLines.toString());
            
            stub.clearOutLines();
        });
    }
    
    @Test
    public void testStub_out2() {
        val stub = new Console.Stub();
        With(Env.refs.console.butWith(stub))
        .run(()->{
            Console
            .outPrintln("Three")
            .outPrintln("Four");
            
            val outLines = StreamPlus.from(stub.outLines()).toList();
            assertEquals(
                    "[Three, Four]",
                    outLines.toString());
            
            stub.clearOutLines();
        });
    }
    
    @Test
    public void testStub_err() {
        val stub = new Console.Stub();
        With(Env.refs.console.butWith(stub))
        .run(()->{
            stub.clear();
            
            Console
            .errPrintln("Five")
            .errPrintln("Six");
            
            val outLines = StreamPlus.from(stub.errLines()).toList();
            assertEquals(
                    "[Five, Six]",
                    outLines.toString());
            
            stub.clear();
        });
    }
    
    @Test
    public void testStub_in() {
        val stub = new Console.Stub();
        With(Env.refs.console.butWith(stub))
        .run(()->{
            stub.addInLines("One", "Two");
            
            assertEquals("One", Console.readln());
            assertEquals("Two", Console.readln());
            assertEquals(0L,    stub.inLines().count());
            
            stub.clearInLines();
        });
    }
    
}

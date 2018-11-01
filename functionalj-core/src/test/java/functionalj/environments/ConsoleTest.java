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
    public void testStub() {
        With(Env.refs.console.butWith(Console.Stub.instance))
        .run(()->{
            Console
            .println("One")
            .println("Two");
            
            val outLines = StreamPlus.from(Console.Stub.instance.outLines()).toList();
            assertEquals(
                    "[One, Two]",
                    outLines.toString());
            
            Console.Stub.instance.clearOutLines();
        });
        
        With(Env.refs.console.butWith(Console.Stub.instance))
        .run(()->{
            Console
            .outPrintln("Three")
            .outPrintln("Four");
            
            val outLines = StreamPlus.from(Console.Stub.instance.outLines()).toList();
            assertEquals(
                    "[Three, Four]",
                    outLines.toString());
            
            Console.Stub.instance.clearOutLines();
        });
        
        With(Env.refs.console.butWith(Console.Stub.instance))
        .run(()->{
            Console.Stub.instance.clear();
            
            Console
            .errPrintln("Five")
            .errPrintln("Six");
            
            val outLines = StreamPlus.from(Console.Stub.instance.errLines()).toList();
            assertEquals(
                    "[Five, Six]",
                    outLines.toString());
            
            Console.Stub.instance.clear();
        });
        
        With(Env.refs.console.butWith(Console.Stub.instance))
        .run(()->{
            Console.Stub.instance.addInLines("One", "Two");
            
            assertEquals("One", Console.readln());
            assertEquals("Two", Console.readln());
            assertEquals(0L,    Console.Stub.instance.inLines().count());
            
            Console.Stub.instance.clearInLines();
        });
    }
    
}

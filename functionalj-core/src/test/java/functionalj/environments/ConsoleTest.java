package functionalj.environments;

import static functionalj.ref.Run.With;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Test;

import functionalj.promise.DeferAction;
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
            
            val outLines = StreamPlus.from(stub.outLines()).toJavaList();
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
            
            val outLines = StreamPlus.from(stub.outLines()).toJavaList();
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
            
            val outLines = StreamPlus.from(stub.errLines()).toJavaList();
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
            stub.endInStream();
            
            assertEquals("One", Console.readln());
            assertEquals("Two", Console.readln());
            assertEquals(0L,    stub.remainingInLines().count());
            
            stub.clearInLines();
        });
    }
    
    @Test
    public void testUseStub_Done() {
        val records = Console.useStub(StreamPlus.of("One", "Two", "Three", "Four"), ()->{
            Console.outPrint(Console.readln());
            Console.errPrint(Console.readln());
            Console.outPrintln(Console.readln());
            Console.errPrintln(Console.readln());
        });
        assertEquals(
                "++++++++++++++++++++\n" + 
                "Data: null\n" + 
                "outLines(1): \n" + 
                "    OneThree\n" + 
                "errLines(1): \n" + 
                "    TwoFour\n" + 
                "inLines(4): \n" + 
                "    One\n" + 
                "    Two\n" + 
                "    Three\n" + 
                "    Four\n" + 
                "--------------------",
                records.toString());
    }
    
    @Test
    public void testUseStub_Delay() {
        val queue = new ConsoleInQueue();
        queue.add("One");
        queue.add("Two");
        
        new Thread(()->{
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
            }
            queue.add("Three");
            queue.add("Four");
            queue.end();
        }).start();
        
        val records = Console.useStub(queue, ()->{
            Console.outPrint(Console.readln());
            Console.errPrint(Console.readln());
            Console.outPrintln(Console.readln());
            Console.errPrintln(Console.readln());
        });
        assertEquals(
                "++++++++++++++++++++\n" + 
                "Data: null\n" + 
                "outLines(1): \n" + 
                "    OneThree\n" + 
                "errLines(1): \n" + 
                "    TwoFour\n" + 
                "inLines(4): \n" + 
                "    One\n" + 
                "    Two\n" + 
                "    Three\n" + 
                "    Four\n" + 
                "--------------------",
                records.toString());
    }
    
    @Test
    public void testUseStub_NoIn() {
        val records = Console.useStub(()->{
            Console.outPrintln("out.");
            Console.errPrintln("ERR!");
        });
        assertEquals(
                "++++++++++++++++++++\n" + 
                "Data: null\n" + 
                "outLines(1): \n" + 
                "    out.\n" + 
                "errLines(1): \n" + 
                "    ERR!\n" + 
                "inLines(0): \n" + 
                "    \n" + 
                "--------------------",
                records.toString());
    }
    
    @Test
    public void testUseStub_Holder() {
        // Umm - Not as easy to use as first thought. -- We have to wait until the queue is set.
        val inQueue = new AtomicReference<ConsoleInQueue>();
        new Thread(()->{
            try {
                Thread.sleep(50);
                while (inQueue.get() == null) {
                    Thread.sleep(1);
                }
            } catch (InterruptedException e) {
            }
            val queue = inQueue.get();
            queue.add("Three");
            queue.add("Four");
            queue.end();
        }).start();
        
        val records = Console.useStub(inQueue::set, ()->{
            Console.outPrintln("out.");
            Console.errPrintln("ERR!");
            Console.outPrintln(Console.readln());
            Console.errPrintln(Console.readln());
        });
        assertEquals(
                "++++++++++++++++++++\n" + 
                "Data: null\n" + 
                "outLines(2): \n" + 
                "    out.\n" + 
                "    Three\n" + 
                "errLines(2): \n" + 
                "    ERR!\n" + 
                "    Four\n" + 
                "inLines(2): \n" + 
                "    Three\n" + 
                "    Four\n" + 
                "--------------------",
                records.toString());
    }
    
    @Test
    public void testUseStub_Holder_Promise() {
        // This might be a bit more useful as we can have UI interact with this.
        val action = DeferAction.of(ConsoleInQueue.class).subscribe(inQueueResult -> {
            inQueueResult.ifPresent(inQueue -> {
                new Thread(()->{
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                    }
                    inQueue.add("Three");
                    inQueue.add("Four");
                    inQueue.end();
                }).start();
            });
        })
        .start();
        
        val records = Console.useStub(action::complete, ()->{
            Console.outPrintln("out.");
            Console.errPrintln("ERR!");
            Console.outPrintln(Console.readln());
            Console.errPrintln(Console.readln());
        });
        assertEquals(
                "++++++++++++++++++++\n" + 
                "Data: null\n" + 
                "outLines(2): \n" + 
                "    out.\n" + 
                "    Three\n" + 
                "errLines(2): \n" + 
                "    ERR!\n" + 
                "    Four\n" + 
                "inLines(2): \n" + 
                "    Three\n" + 
                "    Four\n" + 
                "--------------------",
                records.toString());
    }
    
}

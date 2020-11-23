package example.functionalj.effect;

import static functionalj.function.Func.f;
import static functionalj.functions.RegExMatchResult.theResults;
import static functionalj.functions.StrFuncs.matches;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import org.junit.Test;

import functionalj.list.FuncList;
import functionalj.list.ImmutableList;
import functionalj.promise.DeferAction;
import functionalj.store.Store;
import functionalj.stream.StreamPlus;
import functionalj.task.Task;
import lombok.val;


public class EffectExamples {
    
    @Test
    public void basicEffect() throws InterruptedException {
        val logs = new ArrayList<String>();
        
        // The preparation
        val loadFile = f((String fileName) -> {
            return DeferAction
                .from(()->Files.readAllBytes(Paths.get(fileName)))
                .start()
                .getPromise();
        });
        
        val latch = new CountDownLatch(1);
        
        // Use it in the business logic code
        loadFile.apply("fileNotFound.txt")
        .onComplete(result -> {
            // Process result
            logs.add(result.toString());
            
            latch.countDown();
        });
        
        latch.await();
        
        // Notice the error is sent in the same channel
        assertEquals("[Result:{ Exception: java.nio.file.NoSuchFileException: fileNotFound.txt }]", logs.toString());
    }
    
    @Test
    public void basicTask() throws IOException {
        // Define task
        val wordCountOf = f((String fileName) -> 
                Task.from(()->Files.readAllBytes(Paths.get(fileName)))
                .map(String::new)
                .map(matches("[a-zA-Z]+"))
                .map(theResults.texts)
                .map(StreamPlus::size)
        );
        // Define operations -> Notice that this is a generic operation -- no mention of Task.
        val compareWordCount = f((Integer count1, Integer count2) -> {
            return (count1 == count2) ? "Same size."
                 : (count1 >  count2) ? "First file is larger."
                                      : "Second file is larger.";
        });
        
        // Declare tasks
        val task1 = wordCountOf.apply("../LICENSE");
        val task2 = wordCountOf.apply("../.travis.yml");
        // Compose the tasks
        val compareTask = compareWordCount.applyTo(task1, task2);
        
        // At this point, nothing is run.
        
        // Actually run
        assertEquals("First file is larger.", compareTask.createAction().getResult().get());
    }
    
    @Test
    public void storeExample() {
        val apppend = f((String str, ImmutableList<String> list)-> list.append(str).toImmutableList());
        
        val list = FuncList.of("One", "Two");
        val store = new Store<>(list);
        
        assertEquals("[One, Two]", store.value().toString());
        
        store
        .change(
            apppend.applyTo("Three"),
            apppend.applyTo("Four"),
            apppend.applyTo("Five"),
            apppend.applyTo("Six")
        );
        assertEquals("[One, Two, Three, Four, Five, Six]", store.value().toString());
    }
    
}

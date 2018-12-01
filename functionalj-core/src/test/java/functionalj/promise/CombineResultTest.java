package functionalj.promise;

import static functionalj.list.FuncList.listOf;
import static java.util.stream.Collectors.joining;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import functionalj.result.Result;
import lombok.val;

public class CombineResultTest {

    @Test
    public void testMerge() {
        val logs = new ArrayList<String>();
        
        val action1  = DeferAction.createNew().start();
        val action2  = DeferAction.createNew().start();
        val action3  = DeferAction.createNew().start();
        val combiner = new CombineResult<>(
                listOf(action1, action2, action3),
                l -> Result.of(l.toString()));
        
        val combine = combiner.getDeferAction()
        .subscribe(result -> {
            logs.add(result.toString());
        });
        
        logs.add("Done prepare");
        
        logs.add("About to do one: " + action1.getPromise().getStatus());
        action1.complete("One");
        
        logs.add("About to do two: " + action2.getPromise().getStatus());
        action2.complete("Two");
        
        logs.add("About to do three: " + action3.getPromise().getStatus());
        action3.complete("Three");
        
        // Abort ... but after done so no effect.
        combine.abort();
        logs.add(combine.getResult().toString());
        
        assertEquals(
                "Done prepare\n" + 
                "About to do one: PENDING\n" + 
                "About to do two: PENDING\n" + 
                "About to do three: PENDING\n" + 
                "Result:{ Value: [Result:{ Value: One }, Result:{ Value: Two }, Result:{ Value: Three }] }\n" + 
                "Result:{ Value: [Result:{ Value: One }, Result:{ Value: Two }, Result:{ Value: Three }] }",
                logs.stream().collect(joining("\n")));
    }
}

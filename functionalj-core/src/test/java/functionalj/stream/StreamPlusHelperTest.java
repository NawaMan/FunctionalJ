package functionalj.stream;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

import org.junit.Test;

import functionalj.stream.intstream.IntStreamPlus;
import lombok.val;


public class StreamPlusHelperTest {

    @Test
    public void testSequential() {
        val stream = IntStreamPlus.infinite().limit(1000).boxed();

        val s1 = stream.parallel();
        assertTrue(s1.isParallel());

        val ss = new AtomicReference<Stream<Integer>>();
        val s2 = StreamPlusHelper.sequential(stream, s -> {
            ss.set(s);
            assertFalse(s.isParallel());
            return s;
        });

        assertTrue(s2.isParallel());

        s2.close();
    }

}

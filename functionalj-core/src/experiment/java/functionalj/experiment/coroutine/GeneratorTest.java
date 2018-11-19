package functionalj.experiment.coroutine;

import static functionalj.annotations.sealed.SealedClasses.Switch;
import static functionalj.experiment.coroutine.GeneratorEntry.Last;
import static functionalj.experiment.coroutine.GeneratorEntry.Next;
import static functionalj.experiment.coroutine.GeneratorFinish.Stop;
import static functionalj.experiment.coroutine.GeneratorTest.Generator.e;
import static org.junit.Assert.assertEquals;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Test;

import functionalj.annotations.Sealed;
import functionalj.function.Func0;
import functionalj.function.Func1;
import functionalj.stream.IteratorPlus;
import functionalj.stream.StreamPlus;
import lombok.val;

// 2018-11-14 - WIP - `Finish` is not done.
public class GeneratorTest {
    
    @Sealed(name="GeneratorEntry")
    public interface GeneratorEntrySpec<D> {
        
        void Next(Func0<D> body, Func1<D, GeneratorEntry<D>> more);
        void Last(Func0<D> body);
    
    }
    
    @Sealed(name="GeneratorFinish")
    public interface GeneratorFinishSpec<D> {
        void RepeatAll();
        void RepeatLast();
        void UseLast();
        void UseValue(D returnValue);
        void Stop();
    }
    
    public static class Generator<D> implements Func0<D>{
        private final GeneratorEntry<D>                            firstEntry;
        private final GeneratorFinish<D>                           finish;
        private final AtomicReference<D>                           prevRef      = new AtomicReference<D>();
        private final AtomicReference<Func1<D, GeneratorEntry<D>>> nextEntryRef = new AtomicReference<>();
        
        public Generator(GeneratorEntry<D> firstEntry) {
            this(firstEntry, Stop());
        }
        public Generator(GeneratorEntry<D> firstEntry, GeneratorFinish<D> finish) {
            this.firstEntry = firstEntry;
            this.finish     = (finish != null) ? finish : Stop();
            this.nextEntryRef.set(__ -> firstEntry);
        }
        
        public Generator<D> newInstance() {
            return new Generator<>(firstEntry);
        }
        
        public Iterable<D> iterable() {
            val iterator = new GeneratorIterator<>(newInstance());
            return () -> iterator;
        }
        
        public StreamPlus<D> stream() {
            return iterator().stream();
        }
        
        public IteratorPlus<D> iterator() {
            return new GeneratorIterator<>(this);
        }
        
        @Override
        public D applyUnsafe() throws Exception {
            if (nextEntryRef.get() == null) {
                if (finish.isUseValue()) {
                    val returnValue = finish.asUseValue().value().returnValue();
                    prevRef.set(returnValue);
                } else if (finish.isStop()) {
                    prevRef.set(null);
                }
                
                return prevRef.get();
            }
            
            val entry = nextEntryRef.get().apply(prevRef.get());
            return Switch(entry)
                    .next((Next<D> n) -> {
                        val value = n.body().apply();
                        prevRef.set(value);
                        
                        val nextE = n.more();
                        nextEntryRef.set(nextE);
                        if (nextE == null)
                            doAfterLast(n);
                        return value;
                    })
                    .last((Last<D> l) -> {
                        val value = l.body().apply();
                        prevRef.set(value);
                        
                        doAfterLast(l);
                        return value;
                    });
        }
        
        private void doAfterLast(GeneratorEntry<D> l) {
            if (finish.isRepeatLast()) {
                nextEntryRef.set(prev->l);
            } else if (finish.isRepeatAll()) {
                nextEntryRef.set(prev->firstEntry);
            } else if (finish.isStop() || finish.isUseLast() || finish.isUseValue()) {
                nextEntryRef.set(null);
            }
        }
        
        public static <D> GeneratorEntry<D> e(Func0<D> body) {
            return Last(body);
        }
        public static <D> GeneratorEntry<D> e(Func0<D> body, Func1<D, GeneratorEntry<D>> more) {
            return Next(body, more);
        }
        public static <D> GeneratorEntry<D> e(Func0<D> body, Func0<GeneratorEntry<D>> more) {
            return Next(body, __ -> more.apply());
        }
        
        public static class GeneratorIterator<D> implements IteratorPlus<D> {
            private final Generator<D> generator;
            private D       nextValue = null;
            private boolean hasNext   = true;
            public GeneratorIterator(Generator<D> generator) {
                this.generator = generator;
            }
            @Override
            public boolean hasNext() {
                if (!hasNext && generator.finish.isStop())
                    return false;
                
                hasNext   = generator.nextEntryRef.get() != null;
                nextValue = generator.apply();
                return hasNext;
            }
            @Override
            public D next() {
                return nextValue;
            }
            @Override
            public Iterator<D> asIterator() {
                return this;
            }
        }
        
    }
    
    @Test
    public void test() {
        val g1 = new Generator<String>(
                e(()->"Last"));
        assertEquals("Last", g1.get());
        
        val g2 = new Generator<String>(
                   e(()-> "First" ,               ()
                -> e(()-> "Second",               second
                -> e(()-> second + "-Last"))));
        assertEquals("First, Second, Second-Last", g2.stream().joining(", "));
        assertEquals("",                           g2.stream().joining(", "));
        assertEquals("First, Second, Second-Last", g2.newInstance().stream().joining(", "));
    }
//    @Test
//    public void testUseLast() {
//        val g = new Generator<Integer>(
//                e(()-> 1,            first
//                -> e(()-> first + 1, prev
//                -> e(()-> prev  + 1))),
//                UseValue(-1));
//        assertEquals("1, 2, 3, -1, -1, -1, -1", g.stream().limit(10).joining(", "));
//    }

}

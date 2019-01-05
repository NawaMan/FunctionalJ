package functionalj.stream;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicReference;

import functionalj.functions.ThrowFuncs;
import lombok.val;

public class BlockingQueueIteratorPlus<DATA> implements IteratorPlus<DATA> {
    
    private final DATA                  endData;
    private final BlockingQueue<DATA>   queue;
    private final AtomicReference<DATA> next = new AtomicReference<>(null);
    
    public BlockingQueueIteratorPlus(DATA endData, BlockingQueue<DATA> queue) {
        this.endData = requireNonNull(endData);
        this.queue   = requireNonNull(queue);
    }
    
    @Override
    public Iterator<DATA> asIterator() {
        return this;
    }
    
    @Override
    public boolean hasNext() {
        next.updateAndGet(old -> {
            try {
                return queue.take();
            } catch (InterruptedException e) {
                throw ThrowFuncs.exceptionTransformer.get().apply(e);
            }
        });
        return !Objects.equals(next.get(), endData);
    }
    
    @Override
    public DATA next() {
        return next.get();
    }
    
    public StreamPlus<DATA> remainingValues() {
        val list = new ArrayList<DATA>();
        queue.drainTo(list);
        return StreamPlus.from(list.stream()).exclude(endData::equals);
    }
    
}

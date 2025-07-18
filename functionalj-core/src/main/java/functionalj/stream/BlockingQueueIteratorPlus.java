// ============================================================================
// Copyright (c) 2017-2025 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
// ----------------------------------------------------------------------------
// MIT License
// 
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
// 
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
// 
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.
// ============================================================================
package functionalj.stream;

import static java.util.Objects.requireNonNull;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicReference;

import functionalj.exception.Throwables;
import lombok.val;

public class BlockingQueueIteratorPlus<DATA> implements IteratorPlus<DATA> {
    
    private final DATA endData;
    
    private final BlockingQueue<DATA> queue;
    
    private final AtomicReference<DATA> next = new AtomicReference<>(null);
    
    public BlockingQueueIteratorPlus(DATA endData, BlockingQueue<DATA> queue) {
        this.endData = requireNonNull(endData);
        this.queue = requireNonNull(queue);
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
                throw Throwables.exceptionTransformer.get().apply(e);
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

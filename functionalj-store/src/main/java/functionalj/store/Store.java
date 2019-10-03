// ============================================================================
// Copyright (c) 2017-2019 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.store;

import static functionalj.store.ResultStatus.Accepted;
import static functionalj.store.ResultStatus.Failed;
import static functionalj.store.ResultStatus.NotAllowed;

import java.util.concurrent.atomic.AtomicReference;

import functionalj.function.Func0;
import functionalj.function.Func1;
import functionalj.function.Func2;
import functionalj.function.FuncUnit1;
import functionalj.result.Result;
import functionalj.stream.StreamPlus;
import lombok.val;

// TODO - Generate Store that immitate an immutable type and have the changes store inside.
// TODO - Must mention that this is not thread safe.

public class Store<DATA> implements Func0<DATA> {
    
    private final AtomicReference<DATA>                                     dataRef = new AtomicReference<DATA>();
    private final Func2<DATA, Func1<DATA, DATA>, ChangeNotAllowedException> approver;
    private final Func2<DATA, Result<DATA>, ChangeResult<DATA>>             accepter;
    
    // Add onChange?, lock?
    
    public Store(DATA data) {
        this(data, null, null);
    }
    public Store(
            DATA data,
            Func2<DATA, Result<DATA>, ChangeResult<DATA>> accepter) {
        this(data, accepter, null);
    }
    public Store(
            DATA data,
            Func2<DATA, Result<DATA>, ChangeResult<DATA>>             accepter, 
            Func2<DATA, Func1<DATA, DATA>, ChangeNotAllowedException> approver) {
        this.dataRef.set(data);
        this.approver = (approver != null) ? approver : this::defaultApprover;
        this.accepter = (accepter != null) ? accepter : this::defaultAcceptor;
    }
    
    private ChangeNotAllowedException defaultApprover(DATA oldData, Func1<DATA, DATA> changer) {
        return null;
    }
    
    private ChangeResult<DATA> defaultAcceptor(DATA originalData, Result<DATA> newResult) {
        if (newResult.isValue()) {
            val changeResult = new ChangeResult<DATA>(this, originalData, Accepted(newResult.value()));
            return changeResult;
        }
        val exception  = newResult.getException();
        val failResult = new ChangeResult<DATA>(this, originalData, Failed(new ChangeFailException(exception)));
        return failResult;
    }
    private ChangeResult<DATA> ensureStore(ChangeResult<DATA> changeResult) {
        if (changeResult.store() == this)
            return changeResult;
        
        return new ChangeResult<DATA>(this, changeResult.originalData(), changeResult.status());
    }
    
    public ChangeResult<DATA> change(Func1<DATA, DATA> changer) {
        val originalData  = dataRef.get();
        val approveResult = approver.applySafely(originalData, changer);
        if (approveResult.isPresent()) {
            return new ChangeResult<DATA>(this, originalData, NotAllowed(approveResult.get()));
        }
        val newResult = changer
                .applySafely(originalData)
                .pipeTo(
                    accepter.applyTo(originalData),
                    this::ensureStore
                );
        val result = newResult.result();
        if (result.isValue()) {
            val newValue = result.value();
            val isSuccess = dataRef.compareAndSet(originalData, newValue);
            if (!isSuccess) {
                val dataAlreadyChanged = new IllegalStateException(
                        "The data in the store has already changed: "
                        + "originalData=" + originalData + ", "
                        + "currentData="  + dataRef.get() + ", "
                        + "proposedData=" + newValue);
                return new ChangeResult<DATA>(this, originalData, Failed(new ChangeFailException(dataAlreadyChanged)));
            }
        }
        
        return newResult;
    }
    
    @SafeVarargs
    public final ChangeResult<DATA> change(Func1<DATA, DATA> changer, Func1<DATA, DATA> ... moreChangers) {
        val result = new AtomicReference<>(this.change(changer));
        StreamPlus
        .of(moreChangers)
        .filterNonNull()
        .forEach(c -> {
            val prevResult = result.get();
            val newResult  = prevResult.change(c);
            result.set(newResult);
        });
        return result.get();
    }
    
    public Store<DATA> use(FuncUnit1<DATA> consumer) {
        if (consumer == null)
            return this;
        
        val value = dataRef.get();
        consumer.accept(value);
        return this;
    }
    
    @Override
    public DATA applyUnsafe() throws Exception {
        return dataRef.get();
    }
    public DATA value() {
        return dataRef.get();
    }
    
    public Result<DATA> extract() {
        return Result.valueOf(dataRef.get());
    }
    
    @Override
    public String toString() {
        return "Store [data=" + dataRef + "]";
    }
    
}

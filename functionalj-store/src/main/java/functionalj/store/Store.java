package functionalj.store;

import static functionalj.store.ResultStatus.Accepted;
import static functionalj.store.ResultStatus.Failed;
import static functionalj.store.ResultStatus.NotAllowed;

import java.util.concurrent.atomic.AtomicReference;

import functionalj.function.Func1;
import functionalj.function.Func2;
import functionalj.function.FuncUnit1;
import functionalj.result.Result;
import lombok.val;
import nawaman.nullablej.nullable.Nullable;

// TODO - Generate Store that immitate an immutable type and have the changes store inside.

public class Store<DATA> {
    
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
        this.approver = Nullable.of(approver).orElse(this::defaultApprover);
        this.accepter = Nullable.of(accepter).orElse(this::defaultAcceptor);
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
                .pipe(
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
    
    public Store<DATA> use(FuncUnit1<DATA> consumer) {
        if (consumer == null)
            return this;
        
        val value = dataRef.get();
        consumer.accept(value);
        return this;
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

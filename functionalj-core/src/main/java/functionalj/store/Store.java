package functionalj.store;

import static functionalj.store.ChangeResult.Accepted;
import static functionalj.store.ChangeResult.Adjusted;
import static functionalj.store.ChangeResult.Failed;
import static functionalj.store.ChangeResult.NotAllowed;
import static functionalj.store.ChangeResult.Rejected;

import java.util.concurrent.atomic.AtomicReference;

import functionalj.function.Func1;
import functionalj.function.Func2;
import functionalj.result.Result;
import lombok.val;
import nawaman.nullablej.nullable.Nullable;

public class Store<DATA> {
    
    private final AtomicReference<DATA>                                     dataRef = new AtomicReference<DATA>();
    private final Func2<DATA, Func1<DATA, DATA>, ChangeNotAllowedException> approver;
    private final Func2<DATA, Result<DATA>, ChangeResult<DATA>>             accepter;
    
    // Add onChange?, use?, lock?
    
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
            val changeResult = Accepted(this, originalData, newResult.value());
            return changeResult;
        }
        val exception  = newResult.getException();
        val failResult = Failed(this, originalData, new ChangeFailException(exception));
        return failResult;
    }
    @SuppressWarnings("unchecked")
    private ChangeResult<DATA> ensureStore(ChangeResult<DATA> changeResult) {
        if (changeResult.store() == this)
            return changeResult;
        
        return (ChangeResult<DATA>)
                changeResult.match()
                .toA(ChangeResult.class)
                .notAllowed(n -> NotAllowed(this, n.originalData(), n.reason()))
                .accepted  (a -> Accepted  (this, a.originalData(), a.newData()))
                .adjusted  (a -> Adjusted  (this, a.originalData(), a.proposedData(), a.adjustedData()))
                .rejected  (r -> Rejected  (this, r.originalData(), r.propose(), r.rollback(), r.reason()))
                .failed    (f -> Failed    (this, f.originalData(), f.problem()));
    }
    
    public ChangeResult<DATA> change(Func1<DATA, DATA> changer) {
        val originalData  = dataRef.get();
        val approveResult = approver.applySafely(originalData, changer);
        if (approveResult.isPresent()) {
            return NotAllowed(this, originalData, approveResult.get());
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
                return Failed(this, originalData, new ChangeFailException(dataAlreadyChanged));
            }
        }
        
        return newResult;
    }
    
    public DATA value() {
        return dataRef.get();
    }
    
    public Result<DATA> extract() {
        return Result.of(dataRef.get());
    }
    
    @Override
    public String toString() {
        return "Store [data=" + dataRef + "]";
    }
    
}

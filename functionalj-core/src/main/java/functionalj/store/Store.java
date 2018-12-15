package functionalj.store;

import java.util.concurrent.atomic.AtomicReference;

import functionalj.annotations.Choice;
import functionalj.annotations.choice.Self1;
import functionalj.function.Func1;
import functionalj.function.Func2;
import functionalj.result.Result;
import lombok.val;
import nawaman.nullablej.nullable.Nullable;

public class Store<DATA> {
    
    @Choice
    static interface ChangeResultSpec<D> {
        void NotAllowed(ChangeNotAllowedException reason);
        void Accepted(D oldData, D newData);
        void Adjusted(D oldData, D proposedData, D adjustedData);
        void Rejected(D propose, D rollback, ChangeRejectedException reason);
        void Failed  (ChangeFailException prolems);
        
        default boolean hasChanged(Self1<D> self) {
            ChangeResult<D> result = self.asMe();
            return result.match()
                    .notAllowed(false)
                    .accepted  (true)
                    .adjusted  (true)
                    .orElse    (false);
        }
        @SuppressWarnings("unchecked")
        default Result<D> getNewData(Self1<D> self) {
            ChangeResult<D> result = self.asMe();
            return result.match()
                    .notAllowed(n -> (Result<D>)Result.ofNotExist())
                    .accepted  (a -> Result.of(a.newData()))
                    .adjusted  (a -> Result.of(a.adjustedData()))
                    .orElse    (     (Result<D>)Result.ofNotExist());
        }
    }
    
    private final AtomicReference<DATA>                                     dataRef = new AtomicReference<DATA>();
    private final Func2<DATA, Func1<DATA, DATA>, ChangeNotAllowedException> approver;
    private final Func2<DATA, Result<DATA>, ChangeResult<DATA>>             accepter;
    
    public Store(DATA data) {
        this(data, null, null);
    }
    public Store(
            DATA data, 
            Func2<DATA, Func1<DATA, DATA>, ChangeNotAllowedException> approver,
            Func2<DATA, Result<DATA>, ChangeResult<DATA>>             accepter) {
        this.dataRef.set(data);
        this.approver = Nullable.of(approver).orElse(this::defaultApprover);
        this.accepter = Nullable.of(accepter).orElse(this::defaultAcceptor);
    }
    
    private ChangeNotAllowedException defaultApprover(DATA oldData, Func1<DATA, DATA> changer) {
        return null;
    }
    
    private ChangeResult<DATA> defaultAcceptor(DATA oldData, Result<DATA> newResult) {
        if (newResult.isValue()) {
            val changeResult = ChangeResult.Accepted(oldData, newResult.value());
            return changeResult;
        }
        val exception  = newResult.getException();
        val failResult = ChangeResult.<DATA>Failed(new ChangeFailException(exception));
        return failResult;
    }
    
    public ChangeResult<DATA> change(Func1<DATA, DATA> changer) {
        val oldData       = dataRef.get();
        val approveResult = approver.applySafely(oldData, changer);
        if (approveResult.isPresent()) {
            return ChangeResult.NotAllowed(approveResult.get());
        }
        val newResult = changer
                .applySafely(oldData)
                .pipe(accepter.applyTo(oldData));
        val newData = newResult.getNewData();
        if (newData.isValue()) {
            val newValue = newData.value();
            val isSuccess = dataRef.compareAndSet(oldData, newValue);
            if (!isSuccess) {
                val dataAlreadyChanged = new IllegalStateException(
                        "The data in the store has already changed: "
                        + "oldData=" + oldData + ", "
                        + "currentData=" + dataRef.get() + ", "
                        + "proposedData=" + newValue);
                return ChangeResult.Failed(new ChangeFailException(dataAlreadyChanged));
            }
        }
        
        return newResult;
    }
    
    @Override
    public String toString() {
        return "Store [data=" + dataRef + "]";
    }
    
}

package functionalj.store;

public class Store<DATA> {
//    
//    @Choice
//    static interface ChangeResultSpec<D> {
//        void NotAllowed(ChangeNotAllowedException reason);
//        void Accepted(D oldData, D newData);
//        void Adjusted(D oldData, D proposedData, D adjustedData);
//        void Rejected(D propose, D rollback, ChangeRejectedException reason);
//        void Failed  (ChangeFailException prolems);
//    }
//    
//    private final AtomicReference<DATA>                                     dataRef = new AtomicReference<DATA>();
//    private final Func2<DATA, Func1<DATA, DATA>, ChangeNotAllowedException> approver;
//    private final Func2<DATA, Result<DATA>, ChangeResult<DATA>>             accepter;
//    
//    public Store(DATA data) {
//        this(data, null, null);
//    }
//    public Store(
//            DATA data, 
//            Func2<DATA, Func1<DATA, DATA>, ChangeNotAllowedException> approver,
//            Func2<DATA, Result<DATA>, ChangeResult<DATA>>             accepter) {
//        this.dataRef.set(data);
//        this.approver = Nullable.of(approver).orElse(this::defaultApprover);
//        this.accepter = Nullable.of(accepter).orElse(this::defaultAcceptor);
//    }
//    
//    private ChangeNotAllowedException defaultApprover(DATA oldData, Func1<DATA, DATA> changer) {
//        return null;
//    }
//    
//    private ChangeResult<DATA> defaultAcceptor(DATA oldData, Result<DATA> newResult) {
//        if (newResult.isValue()) {
//            val changeResult = ChangeResult.Accepted(oldData, newResult.value());
//            return changeResult;
//        }
//        val exception  = newResult.getException();
//        val failResult = ChangeResult.<DATA>Failed(new ChangeFailException(exception));
//        return failResult;
//    }
    
//    public ChangeResultSpec<DATA> change(Func1<DATA, DATA> changer) {
//        val oldData       = dataRef.get();
//        val approveResult = approver.applySafely(oldData, changer);
//        if (approveResult.isPresent()) {
//            return approveResult.get();
//        }
//        dataRef.updateAndGet(data -> {
//            val newData = changer
//                    .applySafely(data).pipe(r -> accepter.apply(data, r));
//            return newData.orElse(data);
//        });
//        return this;
//    }
//    
//    @Override
//    public String toString() {
//        return "Store [data=" + dataRef + "]";
//    }
    
}

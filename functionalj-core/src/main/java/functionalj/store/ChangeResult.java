package functionalj.store;

import functionalj.annotations.Absent;
import functionalj.annotations.choice.AbstractChoiceClass;
import functionalj.annotations.choice.ChoiceTypeSwitch;
import functionalj.annotations.choice.Self1;
import functionalj.lens.core.LensSpec;
import functionalj.lens.lenses.*;
import functionalj.pipeable.Pipeable;
import functionalj.result.Result;
import functionalj.store.ChangeFailException;
import functionalj.store.ChangeNotAllowedException;
import functionalj.store.ChangeRejectedException;
import functionalj.store.Specs.ChangeResultSpec;
import functionalj.store.Store;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import nawaman.utils.reflection.UProxy;

@SuppressWarnings({"javadoc", "rawtypes", "unchecked"})
public abstract class ChangeResult<D extends Object> extends AbstractChoiceClass<ChangeResult.ChangeResultFirstSwitch<D>> implements Pipeable<ChangeResult<D>>, Self1<D> {
    
    public static final <D extends Object> NotAllowed<D> NotAllowed(Store<D> store, D originalData, ChangeNotAllowedException reason) {
        return new NotAllowed<D>(store, originalData, reason);
    }
    public static final <D extends Object> Accepted<D> Accepted(Store<D> store, D originalData, D newData) {
        return new Accepted<D>(store, originalData, newData);
    }
    public static final <D extends Object> Adjusted<D> Adjusted(Store<D> store, D originalData, D proposedData, D adjustedData) {
        return new Adjusted<D>(store, originalData, proposedData, adjustedData);
    }
    public static final <D extends Object> Rejected<D> Rejected(Store<D> store, D originalData, D propose, D rollback, ChangeRejectedException reason) {
        return new Rejected<D>(store, originalData, propose, rollback, reason);
    }
    public static final <D extends Object> Failed<D> Failed(Store<D> store, D originalData, ChangeFailException problem) {
        return new Failed<D>(store, originalData, problem);
    }
    
    private final ChangeResultSpec<D> __spec = UProxy.createDefaultProxy(ChangeResultSpec.class);
    
    public static final ChangeResultLens<ChangeResult> theChangeResult = new ChangeResultLens<>(LensSpec.of(ChangeResult.class));
    public static class ChangeResultLens<HOST> extends ObjectLensImpl<HOST, ChangeResult> {

        public final BooleanAccess<ChangeResult> isNotAllowed = ChangeResult::isNotAllowed;
        public final BooleanAccess<ChangeResult> isAccepted = ChangeResult::isAccepted;
        public final BooleanAccess<ChangeResult> isAdjusted = ChangeResult::isAdjusted;
        public final BooleanAccess<ChangeResult> isRejected = ChangeResult::isRejected;
        public final BooleanAccess<ChangeResult> isFailed = ChangeResult::isFailed;
        public final ResultAccess<HOST, NotAllowed, NotAllowed.NotAllowedLens<HOST>> asNotAllowed = createSubResultLens(ChangeResult::asNotAllowed, null, NotAllowed.NotAllowedLens::new);
        public final ResultAccess<HOST, Accepted, Accepted.AcceptedLens<HOST>> asAccepted = createSubResultLens(ChangeResult::asAccepted, null, Accepted.AcceptedLens::new);
        public final ResultAccess<HOST, Adjusted, Adjusted.AdjustedLens<HOST>> asAdjusted = createSubResultLens(ChangeResult::asAdjusted, null, Adjusted.AdjustedLens::new);
        public final ResultAccess<HOST, Rejected, Rejected.RejectedLens<HOST>> asRejected = createSubResultLens(ChangeResult::asRejected, null, Rejected.RejectedLens::new);
        public final ResultAccess<HOST, Failed, Failed.FailedLens<HOST>> asFailed = createSubResultLens(ChangeResult::asFailed, null, Failed.FailedLens::new);
        public ChangeResultLens(LensSpec<HOST, ChangeResult> spec) {
            super(spec);
        }
    }
    
    private ChangeResult() {}
    public ChangeResult<D> __data() throws Exception { return this; }
    public Result<ChangeResult<D>> toResult() { return Result.of(this); }
    
    public static final class NotAllowed<D extends Object> extends ChangeResult<D> {
        public static final NotAllowedLens<NotAllowed> theNotAllowed = new NotAllowedLens<>(LensSpec.of(NotAllowed.class));
        private Store<D> store;
        private D originalData;
        private ChangeNotAllowedException reason;
        private NotAllowed(Store<D> store, D originalData, ChangeNotAllowedException reason) {
            this.store = $utils.notNull(store);
            this.originalData = $utils.notNull(originalData);
            this.reason = $utils.notNull(reason);
        }
        public Store<D> store() { return store; }
        public D originalData() { return originalData; }
        public ChangeNotAllowedException reason() { return reason; }
        public NotAllowed<D> withStore(Store<D> store) { return new NotAllowed<D>(store, originalData, reason); }
        public NotAllowed<D> withOriginalData(D originalData) { return new NotAllowed<D>(store, originalData, reason); }
        public NotAllowed<D> withReason(ChangeNotAllowedException reason) { return new NotAllowed<D>(store, originalData, reason); }
        public static class NotAllowedLens<HOST> extends ObjectLensImpl<HOST, ChangeResult.NotAllowed> {
            
            public final ObjectLens<HOST, Object> store = (ObjectLens)createSubLens(ChangeResult.NotAllowed::store, ChangeResult.NotAllowed::withStore, ObjectLens::of);
            public final ObjectLens<HOST, Object> originalData = (ObjectLens)createSubLens(ChangeResult.NotAllowed::originalData, ChangeResult.NotAllowed::withOriginalData, ObjectLens::of);
            public final ObjectLens<HOST, Object> reason = (ObjectLens)createSubLens(ChangeResult.NotAllowed::reason, ChangeResult.NotAllowed::withReason, ObjectLens::of);
            
            public NotAllowedLens(LensSpec<HOST, ChangeResult.NotAllowed> spec) {
                super(spec);
            }
            
        }
    }
    public static final class Accepted<D extends Object> extends ChangeResult<D> {
        public static final AcceptedLens<Accepted> theAccepted = new AcceptedLens<>(LensSpec.of(Accepted.class));
        private Store<D> store;
        private D originalData;
        private D newData;
        private Accepted(Store<D> store, D originalData, D newData) {
            this.store = $utils.notNull(store);
            this.originalData = $utils.notNull(originalData);
            this.newData = $utils.notNull(newData);
        }
        public Store<D> store() { return store; }
        public D originalData() { return originalData; }
        public D newData() { return newData; }
        public Accepted<D> withStore(Store<D> store) { return new Accepted<D>(store, originalData, newData); }
        public Accepted<D> withOriginalData(D originalData) { return new Accepted<D>(store, originalData, newData); }
        public Accepted<D> withNewData(D newData) { return new Accepted<D>(store, originalData, newData); }
        public static class AcceptedLens<HOST> extends ObjectLensImpl<HOST, ChangeResult.Accepted> {
            
            public final ObjectLens<HOST, Object> store = (ObjectLens)createSubLens(ChangeResult.Accepted::store, ChangeResult.Accepted::withStore, ObjectLens::of);
            public final ObjectLens<HOST, Object> originalData = (ObjectLens)createSubLens(ChangeResult.Accepted::originalData, ChangeResult.Accepted::withOriginalData, ObjectLens::of);
            public final ObjectLens<HOST, Object> newData = (ObjectLens)createSubLens(ChangeResult.Accepted::newData, ChangeResult.Accepted::withNewData, ObjectLens::of);
            
            public AcceptedLens(LensSpec<HOST, ChangeResult.Accepted> spec) {
                super(spec);
            }
            
        }
    }
    public static final class Adjusted<D extends Object> extends ChangeResult<D> {
        public static final AdjustedLens<Adjusted> theAdjusted = new AdjustedLens<>(LensSpec.of(Adjusted.class));
        private Store<D> store;
        private D originalData;
        private D proposedData;
        private D adjustedData;
        private Adjusted(Store<D> store, D originalData, D proposedData, D adjustedData) {
            this.store = $utils.notNull(store);
            this.originalData = $utils.notNull(originalData);
            this.proposedData = $utils.notNull(proposedData);
            this.adjustedData = $utils.notNull(adjustedData);
        }
        public Store<D> store() { return store; }
        public D originalData() { return originalData; }
        public D proposedData() { return proposedData; }
        public D adjustedData() { return adjustedData; }
        public Adjusted<D> withStore(Store<D> store) { return new Adjusted<D>(store, originalData, proposedData, adjustedData); }
        public Adjusted<D> withOriginalData(D originalData) { return new Adjusted<D>(store, originalData, proposedData, adjustedData); }
        public Adjusted<D> withProposedData(D proposedData) { return new Adjusted<D>(store, originalData, proposedData, adjustedData); }
        public Adjusted<D> withAdjustedData(D adjustedData) { return new Adjusted<D>(store, originalData, proposedData, adjustedData); }
        public static class AdjustedLens<HOST> extends ObjectLensImpl<HOST, ChangeResult.Adjusted> {
            
            public final ObjectLens<HOST, Object> store = (ObjectLens)createSubLens(ChangeResult.Adjusted::store, ChangeResult.Adjusted::withStore, ObjectLens::of);
            public final ObjectLens<HOST, Object> originalData = (ObjectLens)createSubLens(ChangeResult.Adjusted::originalData, ChangeResult.Adjusted::withOriginalData, ObjectLens::of);
            public final ObjectLens<HOST, Object> proposedData = (ObjectLens)createSubLens(ChangeResult.Adjusted::proposedData, ChangeResult.Adjusted::withProposedData, ObjectLens::of);
            public final ObjectLens<HOST, Object> adjustedData = (ObjectLens)createSubLens(ChangeResult.Adjusted::adjustedData, ChangeResult.Adjusted::withAdjustedData, ObjectLens::of);
            
            public AdjustedLens(LensSpec<HOST, ChangeResult.Adjusted> spec) {
                super(spec);
            }
            
        }
    }
    public static final class Rejected<D extends Object> extends ChangeResult<D> {
        public static final RejectedLens<Rejected> theRejected = new RejectedLens<>(LensSpec.of(Rejected.class));
        private Store<D> store;
        private D originalData;
        private D propose;
        private D rollback;
        private ChangeRejectedException reason;
        private Rejected(Store<D> store, D originalData, D propose, D rollback, ChangeRejectedException reason) {
            this.store = $utils.notNull(store);
            this.originalData = $utils.notNull(originalData);
            this.propose = $utils.notNull(propose);
            this.rollback = $utils.notNull(rollback);
            this.reason = $utils.notNull(reason);
        }
        public Store<D> store() { return store; }
        public D originalData() { return originalData; }
        public D propose() { return propose; }
        public D rollback() { return rollback; }
        public ChangeRejectedException reason() { return reason; }
        public Rejected<D> withStore(Store<D> store) { return new Rejected<D>(store, originalData, propose, rollback, reason); }
        public Rejected<D> withOriginalData(D originalData) { return new Rejected<D>(store, originalData, propose, rollback, reason); }
        public Rejected<D> withPropose(D propose) { return new Rejected<D>(store, originalData, propose, rollback, reason); }
        public Rejected<D> withRollback(D rollback) { return new Rejected<D>(store, originalData, propose, rollback, reason); }
        public Rejected<D> withReason(ChangeRejectedException reason) { return new Rejected<D>(store, originalData, propose, rollback, reason); }
        public static class RejectedLens<HOST> extends ObjectLensImpl<HOST, ChangeResult.Rejected> {
            
            public final ObjectLens<HOST, Object> store = (ObjectLens)createSubLens(ChangeResult.Rejected::store, ChangeResult.Rejected::withStore, ObjectLens::of);
            public final ObjectLens<HOST, Object> originalData = (ObjectLens)createSubLens(ChangeResult.Rejected::originalData, ChangeResult.Rejected::withOriginalData, ObjectLens::of);
            public final ObjectLens<HOST, Object> propose = (ObjectLens)createSubLens(ChangeResult.Rejected::propose, ChangeResult.Rejected::withPropose, ObjectLens::of);
            public final ObjectLens<HOST, Object> rollback = (ObjectLens)createSubLens(ChangeResult.Rejected::rollback, ChangeResult.Rejected::withRollback, ObjectLens::of);
            public final ObjectLens<HOST, Object> reason = (ObjectLens)createSubLens(ChangeResult.Rejected::reason, ChangeResult.Rejected::withReason, ObjectLens::of);
            
            public RejectedLens(LensSpec<HOST, ChangeResult.Rejected> spec) {
                super(spec);
            }
            
        }
    }
    public static final class Failed<D extends Object> extends ChangeResult<D> {
        public static final FailedLens<Failed> theFailed = new FailedLens<>(LensSpec.of(Failed.class));
        private Store<D> store;
        private D originalData;
        private ChangeFailException problem;
        private Failed(Store<D> store, D originalData, ChangeFailException problem) {
            this.store = $utils.notNull(store);
            this.originalData = $utils.notNull(originalData);
            this.problem = $utils.notNull(problem);
        }
        public Store<D> store() { return store; }
        public D originalData() { return originalData; }
        public ChangeFailException problem() { return problem; }
        public Failed<D> withStore(Store<D> store) { return new Failed<D>(store, originalData, problem); }
        public Failed<D> withOriginalData(D originalData) { return new Failed<D>(store, originalData, problem); }
        public Failed<D> withProblem(ChangeFailException problem) { return new Failed<D>(store, originalData, problem); }
        public static class FailedLens<HOST> extends ObjectLensImpl<HOST, ChangeResult.Failed> {
            
            public final ObjectLens<HOST, Object> store = (ObjectLens)createSubLens(ChangeResult.Failed::store, ChangeResult.Failed::withStore, ObjectLens::of);
            public final ObjectLens<HOST, Object> originalData = (ObjectLens)createSubLens(ChangeResult.Failed::originalData, ChangeResult.Failed::withOriginalData, ObjectLens::of);
            public final ObjectLens<HOST, Object> problem = (ObjectLens)createSubLens(ChangeResult.Failed::problem, ChangeResult.Failed::withProblem, ObjectLens::of);
            
            public FailedLens(LensSpec<HOST, ChangeResult.Failed> spec) {
                super(spec);
            }
            
        }
    }
    
    private final ChangeResultFirstSwitch<D> __switch = new ChangeResultFirstSwitch<D>(this);
    @Override public ChangeResultFirstSwitch<D> match() {
         return __switch;
    }
    
    
    @Override
    public int hashCode() {
        return toString().hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ChangeResult))
            return false;
        
        if (this == obj)
            return true;
        
        String objToString  = obj.toString();
        String thisToString = this.toString();
        return thisToString.equals(objToString);
    }
    
    public boolean hasChanged() {
        return __spec.hasChanged(Self1.of(this));
    }
    public functionalj.store.Store<D> store() {
        return __spec.store(Self1.of(this));
    }
    public functionalj.result.Result<D> result() {
        return __spec.result(Self1.of(this));
    }
    public D value() {
        return __spec.value(Self1.of(this));
    }
    public functionalj.store.ChangeResult<D> change(functionalj.function.Func1<D,D> changer) {
        return Self1.getAsMe(__spec.change(Self1.of(this), changer));
    }
    public java.lang.String toString() {
        return __spec.toString(Self1.of(this));
    }
    
    public boolean isNotAllowed() { return this instanceof NotAllowed; }
    public Result<NotAllowed<D>> asNotAllowed() { return Result.of(this).filter(NotAllowed.class).map(NotAllowed.class::cast); }
    public ChangeResult<D> ifNotAllowed(Consumer<NotAllowed<D>> action) { if (isNotAllowed()) action.accept((NotAllowed<D>)this); return this; }
    public ChangeResult<D> ifNotAllowed(Runnable action) { if (isNotAllowed()) action.run(); return this; }
    public boolean isAccepted() { return this instanceof Accepted; }
    public Result<Accepted<D>> asAccepted() { return Result.of(this).filter(Accepted.class).map(Accepted.class::cast); }
    public ChangeResult<D> ifAccepted(Consumer<Accepted<D>> action) { if (isAccepted()) action.accept((Accepted<D>)this); return this; }
    public ChangeResult<D> ifAccepted(Runnable action) { if (isAccepted()) action.run(); return this; }
    public boolean isAdjusted() { return this instanceof Adjusted; }
    public Result<Adjusted<D>> asAdjusted() { return Result.of(this).filter(Adjusted.class).map(Adjusted.class::cast); }
    public ChangeResult<D> ifAdjusted(Consumer<Adjusted<D>> action) { if (isAdjusted()) action.accept((Adjusted<D>)this); return this; }
    public ChangeResult<D> ifAdjusted(Runnable action) { if (isAdjusted()) action.run(); return this; }
    public boolean isRejected() { return this instanceof Rejected; }
    public Result<Rejected<D>> asRejected() { return Result.of(this).filter(Rejected.class).map(Rejected.class::cast); }
    public ChangeResult<D> ifRejected(Consumer<Rejected<D>> action) { if (isRejected()) action.accept((Rejected<D>)this); return this; }
    public ChangeResult<D> ifRejected(Runnable action) { if (isRejected()) action.run(); return this; }
    public boolean isFailed() { return this instanceof Failed; }
    public Result<Failed<D>> asFailed() { return Result.of(this).filter(Failed.class).map(Failed.class::cast); }
    public ChangeResult<D> ifFailed(Consumer<Failed<D>> action) { if (isFailed()) action.accept((Failed<D>)this); return this; }
    public ChangeResult<D> ifFailed(Runnable action) { if (isFailed()) action.run(); return this; }
    
    public static class ChangeResultFirstSwitch<D extends Object> {
        private ChangeResult<D> $value;
        private ChangeResultFirstSwitch(ChangeResult<D> theValue) { this.$value = theValue; }
        public <TARGET> ChangeResultFirstSwitchTyped<TARGET, D> toA(Class<TARGET> clzz) {
            return new ChangeResultFirstSwitchTyped<TARGET, D>($value);
        }
        
        public <TARGET> ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> notAllowed(Function<? super NotAllowed<D>, TARGET> theAction) {
            Function<ChangeResult<D>, TARGET> $action = null;
            Function<ChangeResult<D>, TARGET> oldAction = (Function<ChangeResult<D>, TARGET>)$action;
            Function<ChangeResult<D>, TARGET> newAction =
                ($action != null)
                ? oldAction : 
                    ($value instanceof NotAllowed)
                    ? (Function<ChangeResult<D>, TARGET>)(d -> theAction.apply((NotAllowed<D>)d))
                    : oldAction;
            
            return new ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D>($value, newAction);
        }
        public <TARGET> ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> notAllowed(Supplier<TARGET> theSupplier) {
            return notAllowed(d->theSupplier.get());
        }
        public <TARGET> ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> notAllowed(TARGET theValue) {
            return notAllowed(d->theValue);
        }
        
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowed(Predicate<NotAllowed<D>> check, Function<? super NotAllowed<D>, TARGET> theAction) {
            Function<ChangeResult<D>, TARGET> $action = null;
            Function<ChangeResult<D>, TARGET> oldAction = (Function<ChangeResult<D>, TARGET>)$action;
            Function<ChangeResult<D>, TARGET> newAction =
                ($action != null)
                ? oldAction : 
                    (($value instanceof NotAllowed) && check.test((NotAllowed<D>)$value))
                    ? (Function<ChangeResult<D>, TARGET>)(d -> theAction.apply((NotAllowed<D>)d))
                    : oldAction;
            
            return new ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D>($value, newAction);
        }
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowed(Predicate<NotAllowed<D>> check, Supplier<TARGET> theSupplier) {
            return notAllowed(check, d->theSupplier.get());
        }
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowed(Predicate<NotAllowed<D>> check, TARGET theValue) {
            return notAllowed(check, d->theValue);
        }
        
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, Absent originalData, Absent reason, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store), theAction);
        }
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, Absent originalData, Absent reason, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store), theSupplier);
        }
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, Absent originalData, Absent reason, TARGET theValue) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store), theValue);
        }
        
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, Absent originalData, Absent reason, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store), theAction);
        }
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, Absent originalData, Absent reason, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store), theSupplier);
        }
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, Absent originalData, Absent reason, TARGET theValue) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store), theValue);
        }
        
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, D aOriginalData, Absent reason, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> $utils.checkEquals(aOriginalData, notAllowed.originalData), theAction);
        }
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, D aOriginalData, Absent reason, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> $utils.checkEquals(aOriginalData, notAllowed.originalData), theSupplier);
        }
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, D aOriginalData, Absent reason, TARGET theValue) {
            return notAllowed(notAllowed -> $utils.checkEquals(aOriginalData, notAllowed.originalData), theValue);
        }
        
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, D aOriginalData, Absent reason, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && $utils.checkEquals(aOriginalData, notAllowed.originalData), theAction);
        }
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, D aOriginalData, Absent reason, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && $utils.checkEquals(aOriginalData, notAllowed.originalData), theSupplier);
        }
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, D aOriginalData, Absent reason, TARGET theValue) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && $utils.checkEquals(aOriginalData, notAllowed.originalData), theValue);
        }
        
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, D aOriginalData, Absent reason, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && $utils.checkEquals(aOriginalData, notAllowed.originalData), theAction);
        }
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, D aOriginalData, Absent reason, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && $utils.checkEquals(aOriginalData, notAllowed.originalData), theSupplier);
        }
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, D aOriginalData, Absent reason, TARGET theValue) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && $utils.checkEquals(aOriginalData, notAllowed.originalData), theValue);
        }
        
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, Predicate<D> originalDataCheck, Absent reason, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> originalDataCheck.test(notAllowed.originalData), theAction);
        }
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, Predicate<D> originalDataCheck, Absent reason, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> originalDataCheck.test(notAllowed.originalData), theSupplier);
        }
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, Predicate<D> originalDataCheck, Absent reason, TARGET theValue) {
            return notAllowed(notAllowed -> originalDataCheck.test(notAllowed.originalData), theValue);
        }
        
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, Predicate<D> originalDataCheck, Absent reason, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && originalDataCheck.test(notAllowed.originalData), theAction);
        }
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, Predicate<D> originalDataCheck, Absent reason, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && originalDataCheck.test(notAllowed.originalData), theSupplier);
        }
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, Predicate<D> originalDataCheck, Absent reason, TARGET theValue) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && originalDataCheck.test(notAllowed.originalData), theValue);
        }
        
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Absent reason, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && originalDataCheck.test(notAllowed.originalData), theAction);
        }
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Absent reason, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && originalDataCheck.test(notAllowed.originalData), theSupplier);
        }
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Absent reason, TARGET theValue) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && originalDataCheck.test(notAllowed.originalData), theValue);
        }
        
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, Absent originalData, ChangeNotAllowedException aReason, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> $utils.checkEquals(aReason, notAllowed.reason), theAction);
        }
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, Absent originalData, ChangeNotAllowedException aReason, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> $utils.checkEquals(aReason, notAllowed.reason), theSupplier);
        }
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, Absent originalData, ChangeNotAllowedException aReason, TARGET theValue) {
            return notAllowed(notAllowed -> $utils.checkEquals(aReason, notAllowed.reason), theValue);
        }
        
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, Absent originalData, ChangeNotAllowedException aReason, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && $utils.checkEquals(aReason, notAllowed.reason), theAction);
        }
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, Absent originalData, ChangeNotAllowedException aReason, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && $utils.checkEquals(aReason, notAllowed.reason), theSupplier);
        }
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, Absent originalData, ChangeNotAllowedException aReason, TARGET theValue) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && $utils.checkEquals(aReason, notAllowed.reason), theValue);
        }
        
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, Absent originalData, ChangeNotAllowedException aReason, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && $utils.checkEquals(aReason, notAllowed.reason), theAction);
        }
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, Absent originalData, ChangeNotAllowedException aReason, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && $utils.checkEquals(aReason, notAllowed.reason), theSupplier);
        }
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, Absent originalData, ChangeNotAllowedException aReason, TARGET theValue) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && $utils.checkEquals(aReason, notAllowed.reason), theValue);
        }
        
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, D aOriginalData, ChangeNotAllowedException aReason, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> $utils.checkEquals(aOriginalData, notAllowed.originalData) && $utils.checkEquals(aReason, notAllowed.reason), theAction);
        }
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, D aOriginalData, ChangeNotAllowedException aReason, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> $utils.checkEquals(aOriginalData, notAllowed.originalData) && $utils.checkEquals(aReason, notAllowed.reason), theSupplier);
        }
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, D aOriginalData, ChangeNotAllowedException aReason, TARGET theValue) {
            return notAllowed(notAllowed -> $utils.checkEquals(aOriginalData, notAllowed.originalData) && $utils.checkEquals(aReason, notAllowed.reason), theValue);
        }
        
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, D aOriginalData, ChangeNotAllowedException aReason, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && $utils.checkEquals(aOriginalData, notAllowed.originalData) && $utils.checkEquals(aReason, notAllowed.reason), theAction);
        }
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, D aOriginalData, ChangeNotAllowedException aReason, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && $utils.checkEquals(aOriginalData, notAllowed.originalData) && $utils.checkEquals(aReason, notAllowed.reason), theSupplier);
        }
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, D aOriginalData, ChangeNotAllowedException aReason, TARGET theValue) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && $utils.checkEquals(aOriginalData, notAllowed.originalData) && $utils.checkEquals(aReason, notAllowed.reason), theValue);
        }
        
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, D aOriginalData, ChangeNotAllowedException aReason, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && $utils.checkEquals(aOriginalData, notAllowed.originalData) && $utils.checkEquals(aReason, notAllowed.reason), theAction);
        }
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, D aOriginalData, ChangeNotAllowedException aReason, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && $utils.checkEquals(aOriginalData, notAllowed.originalData) && $utils.checkEquals(aReason, notAllowed.reason), theSupplier);
        }
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, D aOriginalData, ChangeNotAllowedException aReason, TARGET theValue) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && $utils.checkEquals(aOriginalData, notAllowed.originalData) && $utils.checkEquals(aReason, notAllowed.reason), theValue);
        }
        
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, Predicate<D> originalDataCheck, ChangeNotAllowedException aReason, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> originalDataCheck.test(notAllowed.originalData) && $utils.checkEquals(aReason, notAllowed.reason), theAction);
        }
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, Predicate<D> originalDataCheck, ChangeNotAllowedException aReason, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> originalDataCheck.test(notAllowed.originalData) && $utils.checkEquals(aReason, notAllowed.reason), theSupplier);
        }
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, Predicate<D> originalDataCheck, ChangeNotAllowedException aReason, TARGET theValue) {
            return notAllowed(notAllowed -> originalDataCheck.test(notAllowed.originalData) && $utils.checkEquals(aReason, notAllowed.reason), theValue);
        }
        
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, Predicate<D> originalDataCheck, ChangeNotAllowedException aReason, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && originalDataCheck.test(notAllowed.originalData) && $utils.checkEquals(aReason, notAllowed.reason), theAction);
        }
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, Predicate<D> originalDataCheck, ChangeNotAllowedException aReason, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && originalDataCheck.test(notAllowed.originalData) && $utils.checkEquals(aReason, notAllowed.reason), theSupplier);
        }
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, Predicate<D> originalDataCheck, ChangeNotAllowedException aReason, TARGET theValue) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && originalDataCheck.test(notAllowed.originalData) && $utils.checkEquals(aReason, notAllowed.reason), theValue);
        }
        
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, ChangeNotAllowedException aReason, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && originalDataCheck.test(notAllowed.originalData) && $utils.checkEquals(aReason, notAllowed.reason), theAction);
        }
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, ChangeNotAllowedException aReason, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && originalDataCheck.test(notAllowed.originalData) && $utils.checkEquals(aReason, notAllowed.reason), theSupplier);
        }
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, ChangeNotAllowedException aReason, TARGET theValue) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && originalDataCheck.test(notAllowed.originalData) && $utils.checkEquals(aReason, notAllowed.reason), theValue);
        }
        
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, Absent originalData, Predicate<ChangeNotAllowedException> reasonCheck, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> reasonCheck.test(notAllowed.reason), theAction);
        }
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, Absent originalData, Predicate<ChangeNotAllowedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> reasonCheck.test(notAllowed.reason), theSupplier);
        }
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, Absent originalData, Predicate<ChangeNotAllowedException> reasonCheck, TARGET theValue) {
            return notAllowed(notAllowed -> reasonCheck.test(notAllowed.reason), theValue);
        }
        
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, Absent originalData, Predicate<ChangeNotAllowedException> reasonCheck, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && reasonCheck.test(notAllowed.reason), theAction);
        }
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, Absent originalData, Predicate<ChangeNotAllowedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && reasonCheck.test(notAllowed.reason), theSupplier);
        }
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, Absent originalData, Predicate<ChangeNotAllowedException> reasonCheck, TARGET theValue) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && reasonCheck.test(notAllowed.reason), theValue);
        }
        
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, Absent originalData, Predicate<ChangeNotAllowedException> reasonCheck, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && reasonCheck.test(notAllowed.reason), theAction);
        }
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, Absent originalData, Predicate<ChangeNotAllowedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && reasonCheck.test(notAllowed.reason), theSupplier);
        }
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, Absent originalData, Predicate<ChangeNotAllowedException> reasonCheck, TARGET theValue) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && reasonCheck.test(notAllowed.reason), theValue);
        }
        
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, D aOriginalData, Predicate<ChangeNotAllowedException> reasonCheck, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> $utils.checkEquals(aOriginalData, notAllowed.originalData) && reasonCheck.test(notAllowed.reason), theAction);
        }
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, D aOriginalData, Predicate<ChangeNotAllowedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> $utils.checkEquals(aOriginalData, notAllowed.originalData) && reasonCheck.test(notAllowed.reason), theSupplier);
        }
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, D aOriginalData, Predicate<ChangeNotAllowedException> reasonCheck, TARGET theValue) {
            return notAllowed(notAllowed -> $utils.checkEquals(aOriginalData, notAllowed.originalData) && reasonCheck.test(notAllowed.reason), theValue);
        }
        
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, D aOriginalData, Predicate<ChangeNotAllowedException> reasonCheck, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && $utils.checkEquals(aOriginalData, notAllowed.originalData) && reasonCheck.test(notAllowed.reason), theAction);
        }
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, D aOriginalData, Predicate<ChangeNotAllowedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && $utils.checkEquals(aOriginalData, notAllowed.originalData) && reasonCheck.test(notAllowed.reason), theSupplier);
        }
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, D aOriginalData, Predicate<ChangeNotAllowedException> reasonCheck, TARGET theValue) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && $utils.checkEquals(aOriginalData, notAllowed.originalData) && reasonCheck.test(notAllowed.reason), theValue);
        }
        
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, D aOriginalData, Predicate<ChangeNotAllowedException> reasonCheck, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && $utils.checkEquals(aOriginalData, notAllowed.originalData) && reasonCheck.test(notAllowed.reason), theAction);
        }
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, D aOriginalData, Predicate<ChangeNotAllowedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && $utils.checkEquals(aOriginalData, notAllowed.originalData) && reasonCheck.test(notAllowed.reason), theSupplier);
        }
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, D aOriginalData, Predicate<ChangeNotAllowedException> reasonCheck, TARGET theValue) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && $utils.checkEquals(aOriginalData, notAllowed.originalData) && reasonCheck.test(notAllowed.reason), theValue);
        }
        
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, Predicate<D> originalDataCheck, Predicate<ChangeNotAllowedException> reasonCheck, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> originalDataCheck.test(notAllowed.originalData) && reasonCheck.test(notAllowed.reason), theAction);
        }
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, Predicate<D> originalDataCheck, Predicate<ChangeNotAllowedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> originalDataCheck.test(notAllowed.originalData) && reasonCheck.test(notAllowed.reason), theSupplier);
        }
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, Predicate<D> originalDataCheck, Predicate<ChangeNotAllowedException> reasonCheck, TARGET theValue) {
            return notAllowed(notAllowed -> originalDataCheck.test(notAllowed.originalData) && reasonCheck.test(notAllowed.reason), theValue);
        }
        
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, Predicate<D> originalDataCheck, Predicate<ChangeNotAllowedException> reasonCheck, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && originalDataCheck.test(notAllowed.originalData) && reasonCheck.test(notAllowed.reason), theAction);
        }
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, Predicate<D> originalDataCheck, Predicate<ChangeNotAllowedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && originalDataCheck.test(notAllowed.originalData) && reasonCheck.test(notAllowed.reason), theSupplier);
        }
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, Predicate<D> originalDataCheck, Predicate<ChangeNotAllowedException> reasonCheck, TARGET theValue) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && originalDataCheck.test(notAllowed.originalData) && reasonCheck.test(notAllowed.reason), theValue);
        }
        
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Predicate<ChangeNotAllowedException> reasonCheck, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && originalDataCheck.test(notAllowed.originalData) && reasonCheck.test(notAllowed.reason), theAction);
        }
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Predicate<ChangeNotAllowedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && originalDataCheck.test(notAllowed.originalData) && reasonCheck.test(notAllowed.reason), theSupplier);
        }
        public <TARGET> ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Predicate<ChangeNotAllowedException> reasonCheck, TARGET theValue) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && originalDataCheck.test(notAllowed.originalData) && reasonCheck.test(notAllowed.reason), theValue);
        }
    }
    public static class ChangeResultFirstSwitchTyped<TARGET, D extends Object> {
        private ChangeResult<D> $value;
        private ChangeResultFirstSwitchTyped(ChangeResult<D> theValue) { this.$value = theValue; }
        
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> notAllowed(Function<? super NotAllowed<D>, TARGET> theAction) {
            Function<ChangeResult<D>, TARGET> $action = null;
            Function<ChangeResult<D>, TARGET> oldAction = (Function<ChangeResult<D>, TARGET>)$action;
            Function<ChangeResult<D>, TARGET> newAction =
                ($action != null)
                ? oldAction : 
                    ($value instanceof NotAllowed)
                    ? (Function<ChangeResult<D>, TARGET>)(d -> theAction.apply((NotAllowed<D>)d))
                    : oldAction;
            
            return new ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D>($value, newAction);
        }
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> notAllowed(Supplier<TARGET> theSupplier) {
            return notAllowed(d->theSupplier.get());
        }
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> notAllowed(TARGET theValue) {
            return notAllowed(d->theValue);
        }
        
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowed(Predicate<NotAllowed<D>> check, Function<? super NotAllowed<D>, TARGET> theAction) {
            Function<ChangeResult<D>, TARGET> $action = null;
            Function<ChangeResult<D>, TARGET> oldAction = (Function<ChangeResult<D>, TARGET>)$action;
            Function<ChangeResult<D>, TARGET> newAction =
                ($action != null)
                ? oldAction : 
                    (($value instanceof NotAllowed) && check.test((NotAllowed<D>)$value))
                    ? (Function<ChangeResult<D>, TARGET>)(d -> theAction.apply((NotAllowed<D>)d))
                    : oldAction;
            
            return new ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D>($value, newAction);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowed(Predicate<NotAllowed<D>> check, Supplier<TARGET> theSupplier) {
            return notAllowed(check, d->theSupplier.get());
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowed(Predicate<NotAllowed<D>> check, TARGET theValue) {
            return notAllowed(check, d->theValue);
        }
        
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, Absent originalData, Absent reason, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store), theAction);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, Absent originalData, Absent reason, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store), theSupplier);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, Absent originalData, Absent reason, TARGET theValue) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store), theValue);
        }
        
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, Absent originalData, Absent reason, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store), theAction);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, Absent originalData, Absent reason, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store), theSupplier);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, Absent originalData, Absent reason, TARGET theValue) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store), theValue);
        }
        
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, D aOriginalData, Absent reason, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> $utils.checkEquals(aOriginalData, notAllowed.originalData), theAction);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, D aOriginalData, Absent reason, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> $utils.checkEquals(aOriginalData, notAllowed.originalData), theSupplier);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, D aOriginalData, Absent reason, TARGET theValue) {
            return notAllowed(notAllowed -> $utils.checkEquals(aOriginalData, notAllowed.originalData), theValue);
        }
        
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, D aOriginalData, Absent reason, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && $utils.checkEquals(aOriginalData, notAllowed.originalData), theAction);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, D aOriginalData, Absent reason, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && $utils.checkEquals(aOriginalData, notAllowed.originalData), theSupplier);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, D aOriginalData, Absent reason, TARGET theValue) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && $utils.checkEquals(aOriginalData, notAllowed.originalData), theValue);
        }
        
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, D aOriginalData, Absent reason, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && $utils.checkEquals(aOriginalData, notAllowed.originalData), theAction);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, D aOriginalData, Absent reason, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && $utils.checkEquals(aOriginalData, notAllowed.originalData), theSupplier);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, D aOriginalData, Absent reason, TARGET theValue) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && $utils.checkEquals(aOriginalData, notAllowed.originalData), theValue);
        }
        
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, Predicate<D> originalDataCheck, Absent reason, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> originalDataCheck.test(notAllowed.originalData), theAction);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, Predicate<D> originalDataCheck, Absent reason, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> originalDataCheck.test(notAllowed.originalData), theSupplier);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, Predicate<D> originalDataCheck, Absent reason, TARGET theValue) {
            return notAllowed(notAllowed -> originalDataCheck.test(notAllowed.originalData), theValue);
        }
        
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, Predicate<D> originalDataCheck, Absent reason, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && originalDataCheck.test(notAllowed.originalData), theAction);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, Predicate<D> originalDataCheck, Absent reason, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && originalDataCheck.test(notAllowed.originalData), theSupplier);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, Predicate<D> originalDataCheck, Absent reason, TARGET theValue) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && originalDataCheck.test(notAllowed.originalData), theValue);
        }
        
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Absent reason, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && originalDataCheck.test(notAllowed.originalData), theAction);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Absent reason, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && originalDataCheck.test(notAllowed.originalData), theSupplier);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Absent reason, TARGET theValue) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && originalDataCheck.test(notAllowed.originalData), theValue);
        }
        
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, Absent originalData, ChangeNotAllowedException aReason, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> $utils.checkEquals(aReason, notAllowed.reason), theAction);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, Absent originalData, ChangeNotAllowedException aReason, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> $utils.checkEquals(aReason, notAllowed.reason), theSupplier);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, Absent originalData, ChangeNotAllowedException aReason, TARGET theValue) {
            return notAllowed(notAllowed -> $utils.checkEquals(aReason, notAllowed.reason), theValue);
        }
        
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, Absent originalData, ChangeNotAllowedException aReason, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && $utils.checkEquals(aReason, notAllowed.reason), theAction);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, Absent originalData, ChangeNotAllowedException aReason, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && $utils.checkEquals(aReason, notAllowed.reason), theSupplier);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, Absent originalData, ChangeNotAllowedException aReason, TARGET theValue) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && $utils.checkEquals(aReason, notAllowed.reason), theValue);
        }
        
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, Absent originalData, ChangeNotAllowedException aReason, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && $utils.checkEquals(aReason, notAllowed.reason), theAction);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, Absent originalData, ChangeNotAllowedException aReason, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && $utils.checkEquals(aReason, notAllowed.reason), theSupplier);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, Absent originalData, ChangeNotAllowedException aReason, TARGET theValue) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && $utils.checkEquals(aReason, notAllowed.reason), theValue);
        }
        
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, D aOriginalData, ChangeNotAllowedException aReason, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> $utils.checkEquals(aOriginalData, notAllowed.originalData) && $utils.checkEquals(aReason, notAllowed.reason), theAction);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, D aOriginalData, ChangeNotAllowedException aReason, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> $utils.checkEquals(aOriginalData, notAllowed.originalData) && $utils.checkEquals(aReason, notAllowed.reason), theSupplier);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, D aOriginalData, ChangeNotAllowedException aReason, TARGET theValue) {
            return notAllowed(notAllowed -> $utils.checkEquals(aOriginalData, notAllowed.originalData) && $utils.checkEquals(aReason, notAllowed.reason), theValue);
        }
        
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, D aOriginalData, ChangeNotAllowedException aReason, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && $utils.checkEquals(aOriginalData, notAllowed.originalData) && $utils.checkEquals(aReason, notAllowed.reason), theAction);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, D aOriginalData, ChangeNotAllowedException aReason, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && $utils.checkEquals(aOriginalData, notAllowed.originalData) && $utils.checkEquals(aReason, notAllowed.reason), theSupplier);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, D aOriginalData, ChangeNotAllowedException aReason, TARGET theValue) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && $utils.checkEquals(aOriginalData, notAllowed.originalData) && $utils.checkEquals(aReason, notAllowed.reason), theValue);
        }
        
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, D aOriginalData, ChangeNotAllowedException aReason, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && $utils.checkEquals(aOriginalData, notAllowed.originalData) && $utils.checkEquals(aReason, notAllowed.reason), theAction);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, D aOriginalData, ChangeNotAllowedException aReason, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && $utils.checkEquals(aOriginalData, notAllowed.originalData) && $utils.checkEquals(aReason, notAllowed.reason), theSupplier);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, D aOriginalData, ChangeNotAllowedException aReason, TARGET theValue) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && $utils.checkEquals(aOriginalData, notAllowed.originalData) && $utils.checkEquals(aReason, notAllowed.reason), theValue);
        }
        
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, Predicate<D> originalDataCheck, ChangeNotAllowedException aReason, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> originalDataCheck.test(notAllowed.originalData) && $utils.checkEquals(aReason, notAllowed.reason), theAction);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, Predicate<D> originalDataCheck, ChangeNotAllowedException aReason, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> originalDataCheck.test(notAllowed.originalData) && $utils.checkEquals(aReason, notAllowed.reason), theSupplier);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, Predicate<D> originalDataCheck, ChangeNotAllowedException aReason, TARGET theValue) {
            return notAllowed(notAllowed -> originalDataCheck.test(notAllowed.originalData) && $utils.checkEquals(aReason, notAllowed.reason), theValue);
        }
        
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, Predicate<D> originalDataCheck, ChangeNotAllowedException aReason, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && originalDataCheck.test(notAllowed.originalData) && $utils.checkEquals(aReason, notAllowed.reason), theAction);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, Predicate<D> originalDataCheck, ChangeNotAllowedException aReason, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && originalDataCheck.test(notAllowed.originalData) && $utils.checkEquals(aReason, notAllowed.reason), theSupplier);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, Predicate<D> originalDataCheck, ChangeNotAllowedException aReason, TARGET theValue) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && originalDataCheck.test(notAllowed.originalData) && $utils.checkEquals(aReason, notAllowed.reason), theValue);
        }
        
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, ChangeNotAllowedException aReason, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && originalDataCheck.test(notAllowed.originalData) && $utils.checkEquals(aReason, notAllowed.reason), theAction);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, ChangeNotAllowedException aReason, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && originalDataCheck.test(notAllowed.originalData) && $utils.checkEquals(aReason, notAllowed.reason), theSupplier);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, ChangeNotAllowedException aReason, TARGET theValue) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && originalDataCheck.test(notAllowed.originalData) && $utils.checkEquals(aReason, notAllowed.reason), theValue);
        }
        
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, Absent originalData, Predicate<ChangeNotAllowedException> reasonCheck, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> reasonCheck.test(notAllowed.reason), theAction);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, Absent originalData, Predicate<ChangeNotAllowedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> reasonCheck.test(notAllowed.reason), theSupplier);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, Absent originalData, Predicate<ChangeNotAllowedException> reasonCheck, TARGET theValue) {
            return notAllowed(notAllowed -> reasonCheck.test(notAllowed.reason), theValue);
        }
        
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, Absent originalData, Predicate<ChangeNotAllowedException> reasonCheck, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && reasonCheck.test(notAllowed.reason), theAction);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, Absent originalData, Predicate<ChangeNotAllowedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && reasonCheck.test(notAllowed.reason), theSupplier);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, Absent originalData, Predicate<ChangeNotAllowedException> reasonCheck, TARGET theValue) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && reasonCheck.test(notAllowed.reason), theValue);
        }
        
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, Absent originalData, Predicate<ChangeNotAllowedException> reasonCheck, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && reasonCheck.test(notAllowed.reason), theAction);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, Absent originalData, Predicate<ChangeNotAllowedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && reasonCheck.test(notAllowed.reason), theSupplier);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, Absent originalData, Predicate<ChangeNotAllowedException> reasonCheck, TARGET theValue) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && reasonCheck.test(notAllowed.reason), theValue);
        }
        
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, D aOriginalData, Predicate<ChangeNotAllowedException> reasonCheck, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> $utils.checkEquals(aOriginalData, notAllowed.originalData) && reasonCheck.test(notAllowed.reason), theAction);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, D aOriginalData, Predicate<ChangeNotAllowedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> $utils.checkEquals(aOriginalData, notAllowed.originalData) && reasonCheck.test(notAllowed.reason), theSupplier);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, D aOriginalData, Predicate<ChangeNotAllowedException> reasonCheck, TARGET theValue) {
            return notAllowed(notAllowed -> $utils.checkEquals(aOriginalData, notAllowed.originalData) && reasonCheck.test(notAllowed.reason), theValue);
        }
        
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, D aOriginalData, Predicate<ChangeNotAllowedException> reasonCheck, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && $utils.checkEquals(aOriginalData, notAllowed.originalData) && reasonCheck.test(notAllowed.reason), theAction);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, D aOriginalData, Predicate<ChangeNotAllowedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && $utils.checkEquals(aOriginalData, notAllowed.originalData) && reasonCheck.test(notAllowed.reason), theSupplier);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, D aOriginalData, Predicate<ChangeNotAllowedException> reasonCheck, TARGET theValue) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && $utils.checkEquals(aOriginalData, notAllowed.originalData) && reasonCheck.test(notAllowed.reason), theValue);
        }
        
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, D aOriginalData, Predicate<ChangeNotAllowedException> reasonCheck, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && $utils.checkEquals(aOriginalData, notAllowed.originalData) && reasonCheck.test(notAllowed.reason), theAction);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, D aOriginalData, Predicate<ChangeNotAllowedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && $utils.checkEquals(aOriginalData, notAllowed.originalData) && reasonCheck.test(notAllowed.reason), theSupplier);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, D aOriginalData, Predicate<ChangeNotAllowedException> reasonCheck, TARGET theValue) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && $utils.checkEquals(aOriginalData, notAllowed.originalData) && reasonCheck.test(notAllowed.reason), theValue);
        }
        
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, Predicate<D> originalDataCheck, Predicate<ChangeNotAllowedException> reasonCheck, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> originalDataCheck.test(notAllowed.originalData) && reasonCheck.test(notAllowed.reason), theAction);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, Predicate<D> originalDataCheck, Predicate<ChangeNotAllowedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> originalDataCheck.test(notAllowed.originalData) && reasonCheck.test(notAllowed.reason), theSupplier);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, Predicate<D> originalDataCheck, Predicate<ChangeNotAllowedException> reasonCheck, TARGET theValue) {
            return notAllowed(notAllowed -> originalDataCheck.test(notAllowed.originalData) && reasonCheck.test(notAllowed.reason), theValue);
        }
        
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, Predicate<D> originalDataCheck, Predicate<ChangeNotAllowedException> reasonCheck, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && originalDataCheck.test(notAllowed.originalData) && reasonCheck.test(notAllowed.reason), theAction);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, Predicate<D> originalDataCheck, Predicate<ChangeNotAllowedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && originalDataCheck.test(notAllowed.originalData) && reasonCheck.test(notAllowed.reason), theSupplier);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, Predicate<D> originalDataCheck, Predicate<ChangeNotAllowedException> reasonCheck, TARGET theValue) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && originalDataCheck.test(notAllowed.originalData) && reasonCheck.test(notAllowed.reason), theValue);
        }
        
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Predicate<ChangeNotAllowedException> reasonCheck, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && originalDataCheck.test(notAllowed.originalData) && reasonCheck.test(notAllowed.reason), theAction);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Predicate<ChangeNotAllowedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && originalDataCheck.test(notAllowed.originalData) && reasonCheck.test(notAllowed.reason), theSupplier);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Predicate<ChangeNotAllowedException> reasonCheck, TARGET theValue) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && originalDataCheck.test(notAllowed.originalData) && reasonCheck.test(notAllowed.reason), theValue);
        }
    }
    public static class ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D extends Object> extends ChoiceTypeSwitch<ChangeResult<D>, TARGET> {
        private ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed(ChangeResult<D> theValue, Function<ChangeResult<D>, TARGET> theAction) { super(theValue, theAction); }
        
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> notAllowed(Function<? super NotAllowed<D>, TARGET> theAction) {
            Function<ChangeResult<D>, TARGET> oldAction = (Function<ChangeResult<D>, TARGET>)$action;
            Function<ChangeResult<D>, TARGET> newAction =
                ($action != null)
                ? oldAction : 
                    ($value instanceof NotAllowed)
                    ? (Function<ChangeResult<D>, TARGET>)(d -> theAction.apply((NotAllowed<D>)d))
                    : oldAction;
            
            return new ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D>($value, newAction);
        }
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> notAllowed(Supplier<TARGET> theSupplier) {
            return notAllowed(d->theSupplier.get());
        }
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> notAllowed(TARGET theValue) {
            return notAllowed(d->theValue);
        }
        
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowed(Predicate<NotAllowed<D>> check, Function<? super NotAllowed<D>, TARGET> theAction) {
            Function<ChangeResult<D>, TARGET> oldAction = (Function<ChangeResult<D>, TARGET>)$action;
            Function<ChangeResult<D>, TARGET> newAction =
                ($action != null)
                ? oldAction : 
                    (($value instanceof NotAllowed) && check.test((NotAllowed<D>)$value))
                    ? (Function<ChangeResult<D>, TARGET>)(d -> theAction.apply((NotAllowed<D>)d))
                    : oldAction;
            
            return new ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D>($value, newAction);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowed(Predicate<NotAllowed<D>> check, Supplier<TARGET> theSupplier) {
            return notAllowed(check, d->theSupplier.get());
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowed(Predicate<NotAllowed<D>> check, TARGET theValue) {
            return notAllowed(check, d->theValue);
        }
        
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, Absent originalData, Absent reason, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store), theAction);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, Absent originalData, Absent reason, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store), theSupplier);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, Absent originalData, Absent reason, TARGET theValue) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store), theValue);
        }
        
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, Absent originalData, Absent reason, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store), theAction);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, Absent originalData, Absent reason, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store), theSupplier);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, Absent originalData, Absent reason, TARGET theValue) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store), theValue);
        }
        
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, D aOriginalData, Absent reason, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> $utils.checkEquals(aOriginalData, notAllowed.originalData), theAction);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, D aOriginalData, Absent reason, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> $utils.checkEquals(aOriginalData, notAllowed.originalData), theSupplier);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, D aOriginalData, Absent reason, TARGET theValue) {
            return notAllowed(notAllowed -> $utils.checkEquals(aOriginalData, notAllowed.originalData), theValue);
        }
        
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, D aOriginalData, Absent reason, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && $utils.checkEquals(aOriginalData, notAllowed.originalData), theAction);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, D aOriginalData, Absent reason, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && $utils.checkEquals(aOriginalData, notAllowed.originalData), theSupplier);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, D aOriginalData, Absent reason, TARGET theValue) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && $utils.checkEquals(aOriginalData, notAllowed.originalData), theValue);
        }
        
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, D aOriginalData, Absent reason, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && $utils.checkEquals(aOriginalData, notAllowed.originalData), theAction);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, D aOriginalData, Absent reason, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && $utils.checkEquals(aOriginalData, notAllowed.originalData), theSupplier);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, D aOriginalData, Absent reason, TARGET theValue) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && $utils.checkEquals(aOriginalData, notAllowed.originalData), theValue);
        }
        
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, Predicate<D> originalDataCheck, Absent reason, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> originalDataCheck.test(notAllowed.originalData), theAction);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, Predicate<D> originalDataCheck, Absent reason, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> originalDataCheck.test(notAllowed.originalData), theSupplier);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, Predicate<D> originalDataCheck, Absent reason, TARGET theValue) {
            return notAllowed(notAllowed -> originalDataCheck.test(notAllowed.originalData), theValue);
        }
        
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, Predicate<D> originalDataCheck, Absent reason, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && originalDataCheck.test(notAllowed.originalData), theAction);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, Predicate<D> originalDataCheck, Absent reason, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && originalDataCheck.test(notAllowed.originalData), theSupplier);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, Predicate<D> originalDataCheck, Absent reason, TARGET theValue) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && originalDataCheck.test(notAllowed.originalData), theValue);
        }
        
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Absent reason, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && originalDataCheck.test(notAllowed.originalData), theAction);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Absent reason, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && originalDataCheck.test(notAllowed.originalData), theSupplier);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Absent reason, TARGET theValue) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && originalDataCheck.test(notAllowed.originalData), theValue);
        }
        
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, Absent originalData, ChangeNotAllowedException aReason, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> $utils.checkEquals(aReason, notAllowed.reason), theAction);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, Absent originalData, ChangeNotAllowedException aReason, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> $utils.checkEquals(aReason, notAllowed.reason), theSupplier);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, Absent originalData, ChangeNotAllowedException aReason, TARGET theValue) {
            return notAllowed(notAllowed -> $utils.checkEquals(aReason, notAllowed.reason), theValue);
        }
        
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, Absent originalData, ChangeNotAllowedException aReason, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && $utils.checkEquals(aReason, notAllowed.reason), theAction);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, Absent originalData, ChangeNotAllowedException aReason, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && $utils.checkEquals(aReason, notAllowed.reason), theSupplier);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, Absent originalData, ChangeNotAllowedException aReason, TARGET theValue) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && $utils.checkEquals(aReason, notAllowed.reason), theValue);
        }
        
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, Absent originalData, ChangeNotAllowedException aReason, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && $utils.checkEquals(aReason, notAllowed.reason), theAction);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, Absent originalData, ChangeNotAllowedException aReason, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && $utils.checkEquals(aReason, notAllowed.reason), theSupplier);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, Absent originalData, ChangeNotAllowedException aReason, TARGET theValue) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && $utils.checkEquals(aReason, notAllowed.reason), theValue);
        }
        
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, D aOriginalData, ChangeNotAllowedException aReason, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> $utils.checkEquals(aOriginalData, notAllowed.originalData) && $utils.checkEquals(aReason, notAllowed.reason), theAction);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, D aOriginalData, ChangeNotAllowedException aReason, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> $utils.checkEquals(aOriginalData, notAllowed.originalData) && $utils.checkEquals(aReason, notAllowed.reason), theSupplier);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, D aOriginalData, ChangeNotAllowedException aReason, TARGET theValue) {
            return notAllowed(notAllowed -> $utils.checkEquals(aOriginalData, notAllowed.originalData) && $utils.checkEquals(aReason, notAllowed.reason), theValue);
        }
        
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, D aOriginalData, ChangeNotAllowedException aReason, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && $utils.checkEquals(aOriginalData, notAllowed.originalData) && $utils.checkEquals(aReason, notAllowed.reason), theAction);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, D aOriginalData, ChangeNotAllowedException aReason, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && $utils.checkEquals(aOriginalData, notAllowed.originalData) && $utils.checkEquals(aReason, notAllowed.reason), theSupplier);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, D aOriginalData, ChangeNotAllowedException aReason, TARGET theValue) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && $utils.checkEquals(aOriginalData, notAllowed.originalData) && $utils.checkEquals(aReason, notAllowed.reason), theValue);
        }
        
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, D aOriginalData, ChangeNotAllowedException aReason, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && $utils.checkEquals(aOriginalData, notAllowed.originalData) && $utils.checkEquals(aReason, notAllowed.reason), theAction);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, D aOriginalData, ChangeNotAllowedException aReason, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && $utils.checkEquals(aOriginalData, notAllowed.originalData) && $utils.checkEquals(aReason, notAllowed.reason), theSupplier);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, D aOriginalData, ChangeNotAllowedException aReason, TARGET theValue) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && $utils.checkEquals(aOriginalData, notAllowed.originalData) && $utils.checkEquals(aReason, notAllowed.reason), theValue);
        }
        
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, Predicate<D> originalDataCheck, ChangeNotAllowedException aReason, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> originalDataCheck.test(notAllowed.originalData) && $utils.checkEquals(aReason, notAllowed.reason), theAction);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, Predicate<D> originalDataCheck, ChangeNotAllowedException aReason, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> originalDataCheck.test(notAllowed.originalData) && $utils.checkEquals(aReason, notAllowed.reason), theSupplier);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, Predicate<D> originalDataCheck, ChangeNotAllowedException aReason, TARGET theValue) {
            return notAllowed(notAllowed -> originalDataCheck.test(notAllowed.originalData) && $utils.checkEquals(aReason, notAllowed.reason), theValue);
        }
        
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, Predicate<D> originalDataCheck, ChangeNotAllowedException aReason, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && originalDataCheck.test(notAllowed.originalData) && $utils.checkEquals(aReason, notAllowed.reason), theAction);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, Predicate<D> originalDataCheck, ChangeNotAllowedException aReason, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && originalDataCheck.test(notAllowed.originalData) && $utils.checkEquals(aReason, notAllowed.reason), theSupplier);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, Predicate<D> originalDataCheck, ChangeNotAllowedException aReason, TARGET theValue) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && originalDataCheck.test(notAllowed.originalData) && $utils.checkEquals(aReason, notAllowed.reason), theValue);
        }
        
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, ChangeNotAllowedException aReason, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && originalDataCheck.test(notAllowed.originalData) && $utils.checkEquals(aReason, notAllowed.reason), theAction);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, ChangeNotAllowedException aReason, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && originalDataCheck.test(notAllowed.originalData) && $utils.checkEquals(aReason, notAllowed.reason), theSupplier);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, ChangeNotAllowedException aReason, TARGET theValue) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && originalDataCheck.test(notAllowed.originalData) && $utils.checkEquals(aReason, notAllowed.reason), theValue);
        }
        
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, Absent originalData, Predicate<ChangeNotAllowedException> reasonCheck, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> reasonCheck.test(notAllowed.reason), theAction);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, Absent originalData, Predicate<ChangeNotAllowedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> reasonCheck.test(notAllowed.reason), theSupplier);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, Absent originalData, Predicate<ChangeNotAllowedException> reasonCheck, TARGET theValue) {
            return notAllowed(notAllowed -> reasonCheck.test(notAllowed.reason), theValue);
        }
        
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, Absent originalData, Predicate<ChangeNotAllowedException> reasonCheck, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && reasonCheck.test(notAllowed.reason), theAction);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, Absent originalData, Predicate<ChangeNotAllowedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && reasonCheck.test(notAllowed.reason), theSupplier);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, Absent originalData, Predicate<ChangeNotAllowedException> reasonCheck, TARGET theValue) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && reasonCheck.test(notAllowed.reason), theValue);
        }
        
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, Absent originalData, Predicate<ChangeNotAllowedException> reasonCheck, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && reasonCheck.test(notAllowed.reason), theAction);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, Absent originalData, Predicate<ChangeNotAllowedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && reasonCheck.test(notAllowed.reason), theSupplier);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, Absent originalData, Predicate<ChangeNotAllowedException> reasonCheck, TARGET theValue) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && reasonCheck.test(notAllowed.reason), theValue);
        }
        
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, D aOriginalData, Predicate<ChangeNotAllowedException> reasonCheck, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> $utils.checkEquals(aOriginalData, notAllowed.originalData) && reasonCheck.test(notAllowed.reason), theAction);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, D aOriginalData, Predicate<ChangeNotAllowedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> $utils.checkEquals(aOriginalData, notAllowed.originalData) && reasonCheck.test(notAllowed.reason), theSupplier);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, D aOriginalData, Predicate<ChangeNotAllowedException> reasonCheck, TARGET theValue) {
            return notAllowed(notAllowed -> $utils.checkEquals(aOriginalData, notAllowed.originalData) && reasonCheck.test(notAllowed.reason), theValue);
        }
        
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, D aOriginalData, Predicate<ChangeNotAllowedException> reasonCheck, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && $utils.checkEquals(aOriginalData, notAllowed.originalData) && reasonCheck.test(notAllowed.reason), theAction);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, D aOriginalData, Predicate<ChangeNotAllowedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && $utils.checkEquals(aOriginalData, notAllowed.originalData) && reasonCheck.test(notAllowed.reason), theSupplier);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, D aOriginalData, Predicate<ChangeNotAllowedException> reasonCheck, TARGET theValue) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && $utils.checkEquals(aOriginalData, notAllowed.originalData) && reasonCheck.test(notAllowed.reason), theValue);
        }
        
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, D aOriginalData, Predicate<ChangeNotAllowedException> reasonCheck, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && $utils.checkEquals(aOriginalData, notAllowed.originalData) && reasonCheck.test(notAllowed.reason), theAction);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, D aOriginalData, Predicate<ChangeNotAllowedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && $utils.checkEquals(aOriginalData, notAllowed.originalData) && reasonCheck.test(notAllowed.reason), theSupplier);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, D aOriginalData, Predicate<ChangeNotAllowedException> reasonCheck, TARGET theValue) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && $utils.checkEquals(aOriginalData, notAllowed.originalData) && reasonCheck.test(notAllowed.reason), theValue);
        }
        
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, Predicate<D> originalDataCheck, Predicate<ChangeNotAllowedException> reasonCheck, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> originalDataCheck.test(notAllowed.originalData) && reasonCheck.test(notAllowed.reason), theAction);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, Predicate<D> originalDataCheck, Predicate<ChangeNotAllowedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> originalDataCheck.test(notAllowed.originalData) && reasonCheck.test(notAllowed.reason), theSupplier);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Absent store, Predicate<D> originalDataCheck, Predicate<ChangeNotAllowedException> reasonCheck, TARGET theValue) {
            return notAllowed(notAllowed -> originalDataCheck.test(notAllowed.originalData) && reasonCheck.test(notAllowed.reason), theValue);
        }
        
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, Predicate<D> originalDataCheck, Predicate<ChangeNotAllowedException> reasonCheck, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && originalDataCheck.test(notAllowed.originalData) && reasonCheck.test(notAllowed.reason), theAction);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, Predicate<D> originalDataCheck, Predicate<ChangeNotAllowedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && originalDataCheck.test(notAllowed.originalData) && reasonCheck.test(notAllowed.reason), theSupplier);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Store aStore, Predicate<D> originalDataCheck, Predicate<ChangeNotAllowedException> reasonCheck, TARGET theValue) {
            return notAllowed(notAllowed -> $utils.checkEquals(aStore, notAllowed.store) && originalDataCheck.test(notAllowed.originalData) && reasonCheck.test(notAllowed.reason), theValue);
        }
        
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Predicate<ChangeNotAllowedException> reasonCheck, Function<NotAllowed<D>, TARGET> theAction) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && originalDataCheck.test(notAllowed.originalData) && reasonCheck.test(notAllowed.reason), theAction);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Predicate<ChangeNotAllowedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && originalDataCheck.test(notAllowed.originalData) && reasonCheck.test(notAllowed.reason), theSupplier);
        }
        public ChangeResultSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Predicate<ChangeNotAllowedException> reasonCheck, TARGET theValue) {
            return notAllowed(notAllowed -> storeCheck.test(notAllowed.store) && originalDataCheck.test(notAllowed.originalData) && reasonCheck.test(notAllowed.reason), theValue);
        }
    }
    public static class ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D extends Object> extends ChoiceTypeSwitch<ChangeResult<D>, TARGET> {
        private ChangeResultSwitchAcceptedAdjustedRejectedFailed(ChangeResult<D> theValue, Function<ChangeResult<D>, TARGET> theAction) { super(theValue, theAction); }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> accepted(Function<? super Accepted<D>, TARGET> theAction) {
            Function<ChangeResult<D>, TARGET> oldAction = (Function<ChangeResult<D>, TARGET>)$action;
            Function<ChangeResult<D>, TARGET> newAction =
                ($action != null)
                ? oldAction : 
                    ($value instanceof Accepted)
                    ? (Function<ChangeResult<D>, TARGET>)(d -> theAction.apply((Accepted<D>)d))
                    : oldAction;
            
            return new ChangeResultSwitchAdjustedRejectedFailed<TARGET, D>($value, newAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> accepted(Supplier<TARGET> theSupplier) {
            return accepted(d->theSupplier.get());
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> accepted(TARGET theValue) {
            return accepted(d->theValue);
        }
        
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> accepted(Predicate<Accepted<D>> check, Function<? super Accepted<D>, TARGET> theAction) {
            Function<ChangeResult<D>, TARGET> oldAction = (Function<ChangeResult<D>, TARGET>)$action;
            Function<ChangeResult<D>, TARGET> newAction =
                ($action != null)
                ? oldAction : 
                    (($value instanceof Accepted) && check.test((Accepted<D>)$value))
                    ? (Function<ChangeResult<D>, TARGET>)(d -> theAction.apply((Accepted<D>)d))
                    : oldAction;
            
            return new ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D>($value, newAction);
        }
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> accepted(Predicate<Accepted<D>> check, Supplier<TARGET> theSupplier) {
            return accepted(check, d->theSupplier.get());
        }
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> accepted(Predicate<Accepted<D>> check, TARGET theValue) {
            return accepted(check, d->theValue);
        }
        
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Store aStore, Absent originalData, Absent newData, Function<Accepted<D>, TARGET> theAction) {
            return accepted(accepted -> $utils.checkEquals(aStore, accepted.store), theAction);
        }
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Store aStore, Absent originalData, Absent newData, Supplier<TARGET> theSupplier) {
            return accepted(accepted -> $utils.checkEquals(aStore, accepted.store), theSupplier);
        }
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Store aStore, Absent originalData, Absent newData, TARGET theValue) {
            return accepted(accepted -> $utils.checkEquals(aStore, accepted.store), theValue);
        }
        
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Predicate<Store> storeCheck, Absent originalData, Absent newData, Function<Accepted<D>, TARGET> theAction) {
            return accepted(accepted -> storeCheck.test(accepted.store), theAction);
        }
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Predicate<Store> storeCheck, Absent originalData, Absent newData, Supplier<TARGET> theSupplier) {
            return accepted(accepted -> storeCheck.test(accepted.store), theSupplier);
        }
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Predicate<Store> storeCheck, Absent originalData, Absent newData, TARGET theValue) {
            return accepted(accepted -> storeCheck.test(accepted.store), theValue);
        }
        
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Absent store, D aOriginalData, Absent newData, Function<Accepted<D>, TARGET> theAction) {
            return accepted(accepted -> $utils.checkEquals(aOriginalData, accepted.originalData), theAction);
        }
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Absent store, D aOriginalData, Absent newData, Supplier<TARGET> theSupplier) {
            return accepted(accepted -> $utils.checkEquals(aOriginalData, accepted.originalData), theSupplier);
        }
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Absent store, D aOriginalData, Absent newData, TARGET theValue) {
            return accepted(accepted -> $utils.checkEquals(aOriginalData, accepted.originalData), theValue);
        }
        
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Store aStore, D aOriginalData, Absent newData, Function<Accepted<D>, TARGET> theAction) {
            return accepted(accepted -> $utils.checkEquals(aStore, accepted.store) && $utils.checkEquals(aOriginalData, accepted.originalData), theAction);
        }
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Store aStore, D aOriginalData, Absent newData, Supplier<TARGET> theSupplier) {
            return accepted(accepted -> $utils.checkEquals(aStore, accepted.store) && $utils.checkEquals(aOriginalData, accepted.originalData), theSupplier);
        }
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Store aStore, D aOriginalData, Absent newData, TARGET theValue) {
            return accepted(accepted -> $utils.checkEquals(aStore, accepted.store) && $utils.checkEquals(aOriginalData, accepted.originalData), theValue);
        }
        
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Predicate<Store> storeCheck, D aOriginalData, Absent newData, Function<Accepted<D>, TARGET> theAction) {
            return accepted(accepted -> storeCheck.test(accepted.store) && $utils.checkEquals(aOriginalData, accepted.originalData), theAction);
        }
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Predicate<Store> storeCheck, D aOriginalData, Absent newData, Supplier<TARGET> theSupplier) {
            return accepted(accepted -> storeCheck.test(accepted.store) && $utils.checkEquals(aOriginalData, accepted.originalData), theSupplier);
        }
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Predicate<Store> storeCheck, D aOriginalData, Absent newData, TARGET theValue) {
            return accepted(accepted -> storeCheck.test(accepted.store) && $utils.checkEquals(aOriginalData, accepted.originalData), theValue);
        }
        
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Absent store, Predicate<D> originalDataCheck, Absent newData, Function<Accepted<D>, TARGET> theAction) {
            return accepted(accepted -> originalDataCheck.test(accepted.originalData), theAction);
        }
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Absent store, Predicate<D> originalDataCheck, Absent newData, Supplier<TARGET> theSupplier) {
            return accepted(accepted -> originalDataCheck.test(accepted.originalData), theSupplier);
        }
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Absent store, Predicate<D> originalDataCheck, Absent newData, TARGET theValue) {
            return accepted(accepted -> originalDataCheck.test(accepted.originalData), theValue);
        }
        
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Store aStore, Predicate<D> originalDataCheck, Absent newData, Function<Accepted<D>, TARGET> theAction) {
            return accepted(accepted -> $utils.checkEquals(aStore, accepted.store) && originalDataCheck.test(accepted.originalData), theAction);
        }
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Store aStore, Predicate<D> originalDataCheck, Absent newData, Supplier<TARGET> theSupplier) {
            return accepted(accepted -> $utils.checkEquals(aStore, accepted.store) && originalDataCheck.test(accepted.originalData), theSupplier);
        }
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Store aStore, Predicate<D> originalDataCheck, Absent newData, TARGET theValue) {
            return accepted(accepted -> $utils.checkEquals(aStore, accepted.store) && originalDataCheck.test(accepted.originalData), theValue);
        }
        
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Absent newData, Function<Accepted<D>, TARGET> theAction) {
            return accepted(accepted -> storeCheck.test(accepted.store) && originalDataCheck.test(accepted.originalData), theAction);
        }
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Absent newData, Supplier<TARGET> theSupplier) {
            return accepted(accepted -> storeCheck.test(accepted.store) && originalDataCheck.test(accepted.originalData), theSupplier);
        }
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Absent newData, TARGET theValue) {
            return accepted(accepted -> storeCheck.test(accepted.store) && originalDataCheck.test(accepted.originalData), theValue);
        }
        
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Absent store, Absent originalData, D aNewData, Function<Accepted<D>, TARGET> theAction) {
            return accepted(accepted -> $utils.checkEquals(aNewData, accepted.newData), theAction);
        }
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Absent store, Absent originalData, D aNewData, Supplier<TARGET> theSupplier) {
            return accepted(accepted -> $utils.checkEquals(aNewData, accepted.newData), theSupplier);
        }
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Absent store, Absent originalData, D aNewData, TARGET theValue) {
            return accepted(accepted -> $utils.checkEquals(aNewData, accepted.newData), theValue);
        }
        
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Store aStore, Absent originalData, D aNewData, Function<Accepted<D>, TARGET> theAction) {
            return accepted(accepted -> $utils.checkEquals(aStore, accepted.store) && $utils.checkEquals(aNewData, accepted.newData), theAction);
        }
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Store aStore, Absent originalData, D aNewData, Supplier<TARGET> theSupplier) {
            return accepted(accepted -> $utils.checkEquals(aStore, accepted.store) && $utils.checkEquals(aNewData, accepted.newData), theSupplier);
        }
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Store aStore, Absent originalData, D aNewData, TARGET theValue) {
            return accepted(accepted -> $utils.checkEquals(aStore, accepted.store) && $utils.checkEquals(aNewData, accepted.newData), theValue);
        }
        
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Predicate<Store> storeCheck, Absent originalData, D aNewData, Function<Accepted<D>, TARGET> theAction) {
            return accepted(accepted -> storeCheck.test(accepted.store) && $utils.checkEquals(aNewData, accepted.newData), theAction);
        }
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Predicate<Store> storeCheck, Absent originalData, D aNewData, Supplier<TARGET> theSupplier) {
            return accepted(accepted -> storeCheck.test(accepted.store) && $utils.checkEquals(aNewData, accepted.newData), theSupplier);
        }
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Predicate<Store> storeCheck, Absent originalData, D aNewData, TARGET theValue) {
            return accepted(accepted -> storeCheck.test(accepted.store) && $utils.checkEquals(aNewData, accepted.newData), theValue);
        }
        
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Absent store, D aOriginalData, D aNewData, Function<Accepted<D>, TARGET> theAction) {
            return accepted(accepted -> $utils.checkEquals(aOriginalData, accepted.originalData) && $utils.checkEquals(aNewData, accepted.newData), theAction);
        }
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Absent store, D aOriginalData, D aNewData, Supplier<TARGET> theSupplier) {
            return accepted(accepted -> $utils.checkEquals(aOriginalData, accepted.originalData) && $utils.checkEquals(aNewData, accepted.newData), theSupplier);
        }
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Absent store, D aOriginalData, D aNewData, TARGET theValue) {
            return accepted(accepted -> $utils.checkEquals(aOriginalData, accepted.originalData) && $utils.checkEquals(aNewData, accepted.newData), theValue);
        }
        
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Store aStore, D aOriginalData, D aNewData, Function<Accepted<D>, TARGET> theAction) {
            return accepted(accepted -> $utils.checkEquals(aStore, accepted.store) && $utils.checkEquals(aOriginalData, accepted.originalData) && $utils.checkEquals(aNewData, accepted.newData), theAction);
        }
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Store aStore, D aOriginalData, D aNewData, Supplier<TARGET> theSupplier) {
            return accepted(accepted -> $utils.checkEquals(aStore, accepted.store) && $utils.checkEquals(aOriginalData, accepted.originalData) && $utils.checkEquals(aNewData, accepted.newData), theSupplier);
        }
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Store aStore, D aOriginalData, D aNewData, TARGET theValue) {
            return accepted(accepted -> $utils.checkEquals(aStore, accepted.store) && $utils.checkEquals(aOriginalData, accepted.originalData) && $utils.checkEquals(aNewData, accepted.newData), theValue);
        }
        
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Predicate<Store> storeCheck, D aOriginalData, D aNewData, Function<Accepted<D>, TARGET> theAction) {
            return accepted(accepted -> storeCheck.test(accepted.store) && $utils.checkEquals(aOriginalData, accepted.originalData) && $utils.checkEquals(aNewData, accepted.newData), theAction);
        }
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Predicate<Store> storeCheck, D aOriginalData, D aNewData, Supplier<TARGET> theSupplier) {
            return accepted(accepted -> storeCheck.test(accepted.store) && $utils.checkEquals(aOriginalData, accepted.originalData) && $utils.checkEquals(aNewData, accepted.newData), theSupplier);
        }
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Predicate<Store> storeCheck, D aOriginalData, D aNewData, TARGET theValue) {
            return accepted(accepted -> storeCheck.test(accepted.store) && $utils.checkEquals(aOriginalData, accepted.originalData) && $utils.checkEquals(aNewData, accepted.newData), theValue);
        }
        
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Absent store, Predicate<D> originalDataCheck, D aNewData, Function<Accepted<D>, TARGET> theAction) {
            return accepted(accepted -> originalDataCheck.test(accepted.originalData) && $utils.checkEquals(aNewData, accepted.newData), theAction);
        }
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Absent store, Predicate<D> originalDataCheck, D aNewData, Supplier<TARGET> theSupplier) {
            return accepted(accepted -> originalDataCheck.test(accepted.originalData) && $utils.checkEquals(aNewData, accepted.newData), theSupplier);
        }
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Absent store, Predicate<D> originalDataCheck, D aNewData, TARGET theValue) {
            return accepted(accepted -> originalDataCheck.test(accepted.originalData) && $utils.checkEquals(aNewData, accepted.newData), theValue);
        }
        
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Store aStore, Predicate<D> originalDataCheck, D aNewData, Function<Accepted<D>, TARGET> theAction) {
            return accepted(accepted -> $utils.checkEquals(aStore, accepted.store) && originalDataCheck.test(accepted.originalData) && $utils.checkEquals(aNewData, accepted.newData), theAction);
        }
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Store aStore, Predicate<D> originalDataCheck, D aNewData, Supplier<TARGET> theSupplier) {
            return accepted(accepted -> $utils.checkEquals(aStore, accepted.store) && originalDataCheck.test(accepted.originalData) && $utils.checkEquals(aNewData, accepted.newData), theSupplier);
        }
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Store aStore, Predicate<D> originalDataCheck, D aNewData, TARGET theValue) {
            return accepted(accepted -> $utils.checkEquals(aStore, accepted.store) && originalDataCheck.test(accepted.originalData) && $utils.checkEquals(aNewData, accepted.newData), theValue);
        }
        
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, D aNewData, Function<Accepted<D>, TARGET> theAction) {
            return accepted(accepted -> storeCheck.test(accepted.store) && originalDataCheck.test(accepted.originalData) && $utils.checkEquals(aNewData, accepted.newData), theAction);
        }
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, D aNewData, Supplier<TARGET> theSupplier) {
            return accepted(accepted -> storeCheck.test(accepted.store) && originalDataCheck.test(accepted.originalData) && $utils.checkEquals(aNewData, accepted.newData), theSupplier);
        }
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, D aNewData, TARGET theValue) {
            return accepted(accepted -> storeCheck.test(accepted.store) && originalDataCheck.test(accepted.originalData) && $utils.checkEquals(aNewData, accepted.newData), theValue);
        }
        
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Absent store, Absent originalData, Predicate<D> newDataCheck, Function<Accepted<D>, TARGET> theAction) {
            return accepted(accepted -> newDataCheck.test(accepted.newData), theAction);
        }
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Absent store, Absent originalData, Predicate<D> newDataCheck, Supplier<TARGET> theSupplier) {
            return accepted(accepted -> newDataCheck.test(accepted.newData), theSupplier);
        }
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Absent store, Absent originalData, Predicate<D> newDataCheck, TARGET theValue) {
            return accepted(accepted -> newDataCheck.test(accepted.newData), theValue);
        }
        
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Store aStore, Absent originalData, Predicate<D> newDataCheck, Function<Accepted<D>, TARGET> theAction) {
            return accepted(accepted -> $utils.checkEquals(aStore, accepted.store) && newDataCheck.test(accepted.newData), theAction);
        }
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Store aStore, Absent originalData, Predicate<D> newDataCheck, Supplier<TARGET> theSupplier) {
            return accepted(accepted -> $utils.checkEquals(aStore, accepted.store) && newDataCheck.test(accepted.newData), theSupplier);
        }
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Store aStore, Absent originalData, Predicate<D> newDataCheck, TARGET theValue) {
            return accepted(accepted -> $utils.checkEquals(aStore, accepted.store) && newDataCheck.test(accepted.newData), theValue);
        }
        
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Predicate<Store> storeCheck, Absent originalData, Predicate<D> newDataCheck, Function<Accepted<D>, TARGET> theAction) {
            return accepted(accepted -> storeCheck.test(accepted.store) && newDataCheck.test(accepted.newData), theAction);
        }
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Predicate<Store> storeCheck, Absent originalData, Predicate<D> newDataCheck, Supplier<TARGET> theSupplier) {
            return accepted(accepted -> storeCheck.test(accepted.store) && newDataCheck.test(accepted.newData), theSupplier);
        }
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Predicate<Store> storeCheck, Absent originalData, Predicate<D> newDataCheck, TARGET theValue) {
            return accepted(accepted -> storeCheck.test(accepted.store) && newDataCheck.test(accepted.newData), theValue);
        }
        
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Absent store, D aOriginalData, Predicate<D> newDataCheck, Function<Accepted<D>, TARGET> theAction) {
            return accepted(accepted -> $utils.checkEquals(aOriginalData, accepted.originalData) && newDataCheck.test(accepted.newData), theAction);
        }
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Absent store, D aOriginalData, Predicate<D> newDataCheck, Supplier<TARGET> theSupplier) {
            return accepted(accepted -> $utils.checkEquals(aOriginalData, accepted.originalData) && newDataCheck.test(accepted.newData), theSupplier);
        }
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Absent store, D aOriginalData, Predicate<D> newDataCheck, TARGET theValue) {
            return accepted(accepted -> $utils.checkEquals(aOriginalData, accepted.originalData) && newDataCheck.test(accepted.newData), theValue);
        }
        
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Store aStore, D aOriginalData, Predicate<D> newDataCheck, Function<Accepted<D>, TARGET> theAction) {
            return accepted(accepted -> $utils.checkEquals(aStore, accepted.store) && $utils.checkEquals(aOriginalData, accepted.originalData) && newDataCheck.test(accepted.newData), theAction);
        }
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Store aStore, D aOriginalData, Predicate<D> newDataCheck, Supplier<TARGET> theSupplier) {
            return accepted(accepted -> $utils.checkEquals(aStore, accepted.store) && $utils.checkEquals(aOriginalData, accepted.originalData) && newDataCheck.test(accepted.newData), theSupplier);
        }
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Store aStore, D aOriginalData, Predicate<D> newDataCheck, TARGET theValue) {
            return accepted(accepted -> $utils.checkEquals(aStore, accepted.store) && $utils.checkEquals(aOriginalData, accepted.originalData) && newDataCheck.test(accepted.newData), theValue);
        }
        
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Predicate<Store> storeCheck, D aOriginalData, Predicate<D> newDataCheck, Function<Accepted<D>, TARGET> theAction) {
            return accepted(accepted -> storeCheck.test(accepted.store) && $utils.checkEquals(aOriginalData, accepted.originalData) && newDataCheck.test(accepted.newData), theAction);
        }
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Predicate<Store> storeCheck, D aOriginalData, Predicate<D> newDataCheck, Supplier<TARGET> theSupplier) {
            return accepted(accepted -> storeCheck.test(accepted.store) && $utils.checkEquals(aOriginalData, accepted.originalData) && newDataCheck.test(accepted.newData), theSupplier);
        }
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Predicate<Store> storeCheck, D aOriginalData, Predicate<D> newDataCheck, TARGET theValue) {
            return accepted(accepted -> storeCheck.test(accepted.store) && $utils.checkEquals(aOriginalData, accepted.originalData) && newDataCheck.test(accepted.newData), theValue);
        }
        
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Absent store, Predicate<D> originalDataCheck, Predicate<D> newDataCheck, Function<Accepted<D>, TARGET> theAction) {
            return accepted(accepted -> originalDataCheck.test(accepted.originalData) && newDataCheck.test(accepted.newData), theAction);
        }
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Absent store, Predicate<D> originalDataCheck, Predicate<D> newDataCheck, Supplier<TARGET> theSupplier) {
            return accepted(accepted -> originalDataCheck.test(accepted.originalData) && newDataCheck.test(accepted.newData), theSupplier);
        }
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Absent store, Predicate<D> originalDataCheck, Predicate<D> newDataCheck, TARGET theValue) {
            return accepted(accepted -> originalDataCheck.test(accepted.originalData) && newDataCheck.test(accepted.newData), theValue);
        }
        
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Store aStore, Predicate<D> originalDataCheck, Predicate<D> newDataCheck, Function<Accepted<D>, TARGET> theAction) {
            return accepted(accepted -> $utils.checkEquals(aStore, accepted.store) && originalDataCheck.test(accepted.originalData) && newDataCheck.test(accepted.newData), theAction);
        }
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Store aStore, Predicate<D> originalDataCheck, Predicate<D> newDataCheck, Supplier<TARGET> theSupplier) {
            return accepted(accepted -> $utils.checkEquals(aStore, accepted.store) && originalDataCheck.test(accepted.originalData) && newDataCheck.test(accepted.newData), theSupplier);
        }
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Store aStore, Predicate<D> originalDataCheck, Predicate<D> newDataCheck, TARGET theValue) {
            return accepted(accepted -> $utils.checkEquals(aStore, accepted.store) && originalDataCheck.test(accepted.originalData) && newDataCheck.test(accepted.newData), theValue);
        }
        
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Predicate<D> newDataCheck, Function<Accepted<D>, TARGET> theAction) {
            return accepted(accepted -> storeCheck.test(accepted.store) && originalDataCheck.test(accepted.originalData) && newDataCheck.test(accepted.newData), theAction);
        }
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Predicate<D> newDataCheck, Supplier<TARGET> theSupplier) {
            return accepted(accepted -> storeCheck.test(accepted.store) && originalDataCheck.test(accepted.originalData) && newDataCheck.test(accepted.newData), theSupplier);
        }
        public ChangeResultSwitchAcceptedAdjustedRejectedFailed<TARGET, D> acceptedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Predicate<D> newDataCheck, TARGET theValue) {
            return accepted(accepted -> storeCheck.test(accepted.store) && originalDataCheck.test(accepted.originalData) && newDataCheck.test(accepted.newData), theValue);
        }
    }
    public static class ChangeResultSwitchAdjustedRejectedFailed<TARGET, D extends Object> extends ChoiceTypeSwitch<ChangeResult<D>, TARGET> {
        private ChangeResultSwitchAdjustedRejectedFailed(ChangeResult<D> theValue, Function<ChangeResult<D>, TARGET> theAction) { super(theValue, theAction); }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> adjusted(Function<? super Adjusted<D>, TARGET> theAction) {
            Function<ChangeResult<D>, TARGET> oldAction = (Function<ChangeResult<D>, TARGET>)$action;
            Function<ChangeResult<D>, TARGET> newAction =
                ($action != null)
                ? oldAction : 
                    ($value instanceof Adjusted)
                    ? (Function<ChangeResult<D>, TARGET>)(d -> theAction.apply((Adjusted<D>)d))
                    : oldAction;
            
            return new ChangeResultSwitchRejectedFailed<TARGET, D>($value, newAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> adjusted(Supplier<TARGET> theSupplier) {
            return adjusted(d->theSupplier.get());
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> adjusted(TARGET theValue) {
            return adjusted(d->theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjusted(Predicate<Adjusted<D>> check, Function<? super Adjusted<D>, TARGET> theAction) {
            Function<ChangeResult<D>, TARGET> oldAction = (Function<ChangeResult<D>, TARGET>)$action;
            Function<ChangeResult<D>, TARGET> newAction =
                ($action != null)
                ? oldAction : 
                    (($value instanceof Adjusted) && check.test((Adjusted<D>)$value))
                    ? (Function<ChangeResult<D>, TARGET>)(d -> theAction.apply((Adjusted<D>)d))
                    : oldAction;
            
            return new ChangeResultSwitchAdjustedRejectedFailed<TARGET, D>($value, newAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjusted(Predicate<Adjusted<D>> check, Supplier<TARGET> theSupplier) {
            return adjusted(check, d->theSupplier.get());
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjusted(Predicate<Adjusted<D>> check, TARGET theValue) {
            return adjusted(check, d->theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, Absent originalData, Absent proposedData, Absent adjustedData, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, Absent originalData, Absent proposedData, Absent adjustedData, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, Absent originalData, Absent proposedData, Absent adjustedData, TARGET theValue) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, Absent originalData, Absent proposedData, Absent adjustedData, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, Absent originalData, Absent proposedData, Absent adjustedData, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, Absent originalData, Absent proposedData, Absent adjustedData, TARGET theValue) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, D aOriginalData, Absent proposedData, Absent adjustedData, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> $utils.checkEquals(aOriginalData, adjusted.originalData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, D aOriginalData, Absent proposedData, Absent adjustedData, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> $utils.checkEquals(aOriginalData, adjusted.originalData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, D aOriginalData, Absent proposedData, Absent adjustedData, TARGET theValue) {
            return adjusted(adjusted -> $utils.checkEquals(aOriginalData, adjusted.originalData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, D aOriginalData, Absent proposedData, Absent adjustedData, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && $utils.checkEquals(aOriginalData, adjusted.originalData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, D aOriginalData, Absent proposedData, Absent adjustedData, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && $utils.checkEquals(aOriginalData, adjusted.originalData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, D aOriginalData, Absent proposedData, Absent adjustedData, TARGET theValue) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && $utils.checkEquals(aOriginalData, adjusted.originalData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, D aOriginalData, Absent proposedData, Absent adjustedData, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && $utils.checkEquals(aOriginalData, adjusted.originalData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, D aOriginalData, Absent proposedData, Absent adjustedData, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && $utils.checkEquals(aOriginalData, adjusted.originalData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, D aOriginalData, Absent proposedData, Absent adjustedData, TARGET theValue) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && $utils.checkEquals(aOriginalData, adjusted.originalData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, Predicate<D> originalDataCheck, Absent proposedData, Absent adjustedData, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> originalDataCheck.test(adjusted.originalData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, Predicate<D> originalDataCheck, Absent proposedData, Absent adjustedData, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> originalDataCheck.test(adjusted.originalData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, Predicate<D> originalDataCheck, Absent proposedData, Absent adjustedData, TARGET theValue) {
            return adjusted(adjusted -> originalDataCheck.test(adjusted.originalData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, Predicate<D> originalDataCheck, Absent proposedData, Absent adjustedData, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && originalDataCheck.test(adjusted.originalData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, Predicate<D> originalDataCheck, Absent proposedData, Absent adjustedData, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && originalDataCheck.test(adjusted.originalData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, Predicate<D> originalDataCheck, Absent proposedData, Absent adjustedData, TARGET theValue) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && originalDataCheck.test(adjusted.originalData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Absent proposedData, Absent adjustedData, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && originalDataCheck.test(adjusted.originalData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Absent proposedData, Absent adjustedData, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && originalDataCheck.test(adjusted.originalData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Absent proposedData, Absent adjustedData, TARGET theValue) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && originalDataCheck.test(adjusted.originalData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, Absent originalData, D aProposedData, Absent adjustedData, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> $utils.checkEquals(aProposedData, adjusted.proposedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, Absent originalData, D aProposedData, Absent adjustedData, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> $utils.checkEquals(aProposedData, adjusted.proposedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, Absent originalData, D aProposedData, Absent adjustedData, TARGET theValue) {
            return adjusted(adjusted -> $utils.checkEquals(aProposedData, adjusted.proposedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, Absent originalData, D aProposedData, Absent adjustedData, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && $utils.checkEquals(aProposedData, adjusted.proposedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, Absent originalData, D aProposedData, Absent adjustedData, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && $utils.checkEquals(aProposedData, adjusted.proposedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, Absent originalData, D aProposedData, Absent adjustedData, TARGET theValue) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && $utils.checkEquals(aProposedData, adjusted.proposedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, Absent originalData, D aProposedData, Absent adjustedData, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && $utils.checkEquals(aProposedData, adjusted.proposedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, Absent originalData, D aProposedData, Absent adjustedData, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && $utils.checkEquals(aProposedData, adjusted.proposedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, Absent originalData, D aProposedData, Absent adjustedData, TARGET theValue) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && $utils.checkEquals(aProposedData, adjusted.proposedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, D aOriginalData, D aProposedData, Absent adjustedData, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> $utils.checkEquals(aOriginalData, adjusted.originalData) && $utils.checkEquals(aProposedData, adjusted.proposedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, D aOriginalData, D aProposedData, Absent adjustedData, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> $utils.checkEquals(aOriginalData, adjusted.originalData) && $utils.checkEquals(aProposedData, adjusted.proposedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, D aOriginalData, D aProposedData, Absent adjustedData, TARGET theValue) {
            return adjusted(adjusted -> $utils.checkEquals(aOriginalData, adjusted.originalData) && $utils.checkEquals(aProposedData, adjusted.proposedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, D aOriginalData, D aProposedData, Absent adjustedData, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && $utils.checkEquals(aOriginalData, adjusted.originalData) && $utils.checkEquals(aProposedData, adjusted.proposedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, D aOriginalData, D aProposedData, Absent adjustedData, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && $utils.checkEquals(aOriginalData, adjusted.originalData) && $utils.checkEquals(aProposedData, adjusted.proposedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, D aOriginalData, D aProposedData, Absent adjustedData, TARGET theValue) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && $utils.checkEquals(aOriginalData, adjusted.originalData) && $utils.checkEquals(aProposedData, adjusted.proposedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, D aOriginalData, D aProposedData, Absent adjustedData, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && $utils.checkEquals(aOriginalData, adjusted.originalData) && $utils.checkEquals(aProposedData, adjusted.proposedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, D aOriginalData, D aProposedData, Absent adjustedData, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && $utils.checkEquals(aOriginalData, adjusted.originalData) && $utils.checkEquals(aProposedData, adjusted.proposedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, D aOriginalData, D aProposedData, Absent adjustedData, TARGET theValue) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && $utils.checkEquals(aOriginalData, adjusted.originalData) && $utils.checkEquals(aProposedData, adjusted.proposedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, Predicate<D> originalDataCheck, D aProposedData, Absent adjustedData, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> originalDataCheck.test(adjusted.originalData) && $utils.checkEquals(aProposedData, adjusted.proposedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, Predicate<D> originalDataCheck, D aProposedData, Absent adjustedData, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> originalDataCheck.test(adjusted.originalData) && $utils.checkEquals(aProposedData, adjusted.proposedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, Predicate<D> originalDataCheck, D aProposedData, Absent adjustedData, TARGET theValue) {
            return adjusted(adjusted -> originalDataCheck.test(adjusted.originalData) && $utils.checkEquals(aProposedData, adjusted.proposedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, Predicate<D> originalDataCheck, D aProposedData, Absent adjustedData, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && originalDataCheck.test(adjusted.originalData) && $utils.checkEquals(aProposedData, adjusted.proposedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, Predicate<D> originalDataCheck, D aProposedData, Absent adjustedData, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && originalDataCheck.test(adjusted.originalData) && $utils.checkEquals(aProposedData, adjusted.proposedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, Predicate<D> originalDataCheck, D aProposedData, Absent adjustedData, TARGET theValue) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && originalDataCheck.test(adjusted.originalData) && $utils.checkEquals(aProposedData, adjusted.proposedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, D aProposedData, Absent adjustedData, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && originalDataCheck.test(adjusted.originalData) && $utils.checkEquals(aProposedData, adjusted.proposedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, D aProposedData, Absent adjustedData, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && originalDataCheck.test(adjusted.originalData) && $utils.checkEquals(aProposedData, adjusted.proposedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, D aProposedData, Absent adjustedData, TARGET theValue) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && originalDataCheck.test(adjusted.originalData) && $utils.checkEquals(aProposedData, adjusted.proposedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, Absent originalData, Predicate<D> proposedDataCheck, Absent adjustedData, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> proposedDataCheck.test(adjusted.proposedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, Absent originalData, Predicate<D> proposedDataCheck, Absent adjustedData, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> proposedDataCheck.test(adjusted.proposedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, Absent originalData, Predicate<D> proposedDataCheck, Absent adjustedData, TARGET theValue) {
            return adjusted(adjusted -> proposedDataCheck.test(adjusted.proposedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, Absent originalData, Predicate<D> proposedDataCheck, Absent adjustedData, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && proposedDataCheck.test(adjusted.proposedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, Absent originalData, Predicate<D> proposedDataCheck, Absent adjustedData, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && proposedDataCheck.test(adjusted.proposedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, Absent originalData, Predicate<D> proposedDataCheck, Absent adjustedData, TARGET theValue) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && proposedDataCheck.test(adjusted.proposedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, Absent originalData, Predicate<D> proposedDataCheck, Absent adjustedData, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && proposedDataCheck.test(adjusted.proposedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, Absent originalData, Predicate<D> proposedDataCheck, Absent adjustedData, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && proposedDataCheck.test(adjusted.proposedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, Absent originalData, Predicate<D> proposedDataCheck, Absent adjustedData, TARGET theValue) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && proposedDataCheck.test(adjusted.proposedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, D aOriginalData, Predicate<D> proposedDataCheck, Absent adjustedData, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> $utils.checkEquals(aOriginalData, adjusted.originalData) && proposedDataCheck.test(adjusted.proposedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, D aOriginalData, Predicate<D> proposedDataCheck, Absent adjustedData, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> $utils.checkEquals(aOriginalData, adjusted.originalData) && proposedDataCheck.test(adjusted.proposedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, D aOriginalData, Predicate<D> proposedDataCheck, Absent adjustedData, TARGET theValue) {
            return adjusted(adjusted -> $utils.checkEquals(aOriginalData, adjusted.originalData) && proposedDataCheck.test(adjusted.proposedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, D aOriginalData, Predicate<D> proposedDataCheck, Absent adjustedData, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && $utils.checkEquals(aOriginalData, adjusted.originalData) && proposedDataCheck.test(adjusted.proposedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, D aOriginalData, Predicate<D> proposedDataCheck, Absent adjustedData, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && $utils.checkEquals(aOriginalData, adjusted.originalData) && proposedDataCheck.test(adjusted.proposedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, D aOriginalData, Predicate<D> proposedDataCheck, Absent adjustedData, TARGET theValue) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && $utils.checkEquals(aOriginalData, adjusted.originalData) && proposedDataCheck.test(adjusted.proposedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, D aOriginalData, Predicate<D> proposedDataCheck, Absent adjustedData, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && $utils.checkEquals(aOriginalData, adjusted.originalData) && proposedDataCheck.test(adjusted.proposedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, D aOriginalData, Predicate<D> proposedDataCheck, Absent adjustedData, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && $utils.checkEquals(aOriginalData, adjusted.originalData) && proposedDataCheck.test(adjusted.proposedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, D aOriginalData, Predicate<D> proposedDataCheck, Absent adjustedData, TARGET theValue) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && $utils.checkEquals(aOriginalData, adjusted.originalData) && proposedDataCheck.test(adjusted.proposedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, Predicate<D> originalDataCheck, Predicate<D> proposedDataCheck, Absent adjustedData, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> originalDataCheck.test(adjusted.originalData) && proposedDataCheck.test(adjusted.proposedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, Predicate<D> originalDataCheck, Predicate<D> proposedDataCheck, Absent adjustedData, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> originalDataCheck.test(adjusted.originalData) && proposedDataCheck.test(adjusted.proposedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, Predicate<D> originalDataCheck, Predicate<D> proposedDataCheck, Absent adjustedData, TARGET theValue) {
            return adjusted(adjusted -> originalDataCheck.test(adjusted.originalData) && proposedDataCheck.test(adjusted.proposedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, Predicate<D> originalDataCheck, Predicate<D> proposedDataCheck, Absent adjustedData, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && originalDataCheck.test(adjusted.originalData) && proposedDataCheck.test(adjusted.proposedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, Predicate<D> originalDataCheck, Predicate<D> proposedDataCheck, Absent adjustedData, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && originalDataCheck.test(adjusted.originalData) && proposedDataCheck.test(adjusted.proposedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, Predicate<D> originalDataCheck, Predicate<D> proposedDataCheck, Absent adjustedData, TARGET theValue) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && originalDataCheck.test(adjusted.originalData) && proposedDataCheck.test(adjusted.proposedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Predicate<D> proposedDataCheck, Absent adjustedData, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && originalDataCheck.test(adjusted.originalData) && proposedDataCheck.test(adjusted.proposedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Predicate<D> proposedDataCheck, Absent adjustedData, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && originalDataCheck.test(adjusted.originalData) && proposedDataCheck.test(adjusted.proposedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Predicate<D> proposedDataCheck, Absent adjustedData, TARGET theValue) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && originalDataCheck.test(adjusted.originalData) && proposedDataCheck.test(adjusted.proposedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, Absent originalData, Absent proposedData, D aAdjustedData, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, Absent originalData, Absent proposedData, D aAdjustedData, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, Absent originalData, Absent proposedData, D aAdjustedData, TARGET theValue) {
            return adjusted(adjusted -> $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, Absent originalData, Absent proposedData, D aAdjustedData, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, Absent originalData, Absent proposedData, D aAdjustedData, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, Absent originalData, Absent proposedData, D aAdjustedData, TARGET theValue) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, Absent originalData, Absent proposedData, D aAdjustedData, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, Absent originalData, Absent proposedData, D aAdjustedData, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, Absent originalData, Absent proposedData, D aAdjustedData, TARGET theValue) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, D aOriginalData, Absent proposedData, D aAdjustedData, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> $utils.checkEquals(aOriginalData, adjusted.originalData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, D aOriginalData, Absent proposedData, D aAdjustedData, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> $utils.checkEquals(aOriginalData, adjusted.originalData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, D aOriginalData, Absent proposedData, D aAdjustedData, TARGET theValue) {
            return adjusted(adjusted -> $utils.checkEquals(aOriginalData, adjusted.originalData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, D aOriginalData, Absent proposedData, D aAdjustedData, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && $utils.checkEquals(aOriginalData, adjusted.originalData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, D aOriginalData, Absent proposedData, D aAdjustedData, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && $utils.checkEquals(aOriginalData, adjusted.originalData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, D aOriginalData, Absent proposedData, D aAdjustedData, TARGET theValue) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && $utils.checkEquals(aOriginalData, adjusted.originalData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, D aOriginalData, Absent proposedData, D aAdjustedData, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && $utils.checkEquals(aOriginalData, adjusted.originalData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, D aOriginalData, Absent proposedData, D aAdjustedData, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && $utils.checkEquals(aOriginalData, adjusted.originalData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, D aOriginalData, Absent proposedData, D aAdjustedData, TARGET theValue) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && $utils.checkEquals(aOriginalData, adjusted.originalData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, Predicate<D> originalDataCheck, Absent proposedData, D aAdjustedData, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> originalDataCheck.test(adjusted.originalData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, Predicate<D> originalDataCheck, Absent proposedData, D aAdjustedData, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> originalDataCheck.test(adjusted.originalData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, Predicate<D> originalDataCheck, Absent proposedData, D aAdjustedData, TARGET theValue) {
            return adjusted(adjusted -> originalDataCheck.test(adjusted.originalData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, Predicate<D> originalDataCheck, Absent proposedData, D aAdjustedData, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && originalDataCheck.test(adjusted.originalData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, Predicate<D> originalDataCheck, Absent proposedData, D aAdjustedData, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && originalDataCheck.test(adjusted.originalData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, Predicate<D> originalDataCheck, Absent proposedData, D aAdjustedData, TARGET theValue) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && originalDataCheck.test(adjusted.originalData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Absent proposedData, D aAdjustedData, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && originalDataCheck.test(adjusted.originalData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Absent proposedData, D aAdjustedData, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && originalDataCheck.test(adjusted.originalData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Absent proposedData, D aAdjustedData, TARGET theValue) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && originalDataCheck.test(adjusted.originalData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, Absent originalData, D aProposedData, D aAdjustedData, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> $utils.checkEquals(aProposedData, adjusted.proposedData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, Absent originalData, D aProposedData, D aAdjustedData, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> $utils.checkEquals(aProposedData, adjusted.proposedData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, Absent originalData, D aProposedData, D aAdjustedData, TARGET theValue) {
            return adjusted(adjusted -> $utils.checkEquals(aProposedData, adjusted.proposedData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, Absent originalData, D aProposedData, D aAdjustedData, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && $utils.checkEquals(aProposedData, adjusted.proposedData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, Absent originalData, D aProposedData, D aAdjustedData, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && $utils.checkEquals(aProposedData, adjusted.proposedData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, Absent originalData, D aProposedData, D aAdjustedData, TARGET theValue) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && $utils.checkEquals(aProposedData, adjusted.proposedData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, Absent originalData, D aProposedData, D aAdjustedData, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && $utils.checkEquals(aProposedData, adjusted.proposedData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, Absent originalData, D aProposedData, D aAdjustedData, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && $utils.checkEquals(aProposedData, adjusted.proposedData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, Absent originalData, D aProposedData, D aAdjustedData, TARGET theValue) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && $utils.checkEquals(aProposedData, adjusted.proposedData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, D aOriginalData, D aProposedData, D aAdjustedData, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> $utils.checkEquals(aOriginalData, adjusted.originalData) && $utils.checkEquals(aProposedData, adjusted.proposedData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, D aOriginalData, D aProposedData, D aAdjustedData, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> $utils.checkEquals(aOriginalData, adjusted.originalData) && $utils.checkEquals(aProposedData, adjusted.proposedData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, D aOriginalData, D aProposedData, D aAdjustedData, TARGET theValue) {
            return adjusted(adjusted -> $utils.checkEquals(aOriginalData, adjusted.originalData) && $utils.checkEquals(aProposedData, adjusted.proposedData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, D aOriginalData, D aProposedData, D aAdjustedData, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && $utils.checkEquals(aOriginalData, adjusted.originalData) && $utils.checkEquals(aProposedData, adjusted.proposedData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, D aOriginalData, D aProposedData, D aAdjustedData, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && $utils.checkEquals(aOriginalData, adjusted.originalData) && $utils.checkEquals(aProposedData, adjusted.proposedData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, D aOriginalData, D aProposedData, D aAdjustedData, TARGET theValue) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && $utils.checkEquals(aOriginalData, adjusted.originalData) && $utils.checkEquals(aProposedData, adjusted.proposedData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, D aOriginalData, D aProposedData, D aAdjustedData, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && $utils.checkEquals(aOriginalData, adjusted.originalData) && $utils.checkEquals(aProposedData, adjusted.proposedData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, D aOriginalData, D aProposedData, D aAdjustedData, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && $utils.checkEquals(aOriginalData, adjusted.originalData) && $utils.checkEquals(aProposedData, adjusted.proposedData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, D aOriginalData, D aProposedData, D aAdjustedData, TARGET theValue) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && $utils.checkEquals(aOriginalData, adjusted.originalData) && $utils.checkEquals(aProposedData, adjusted.proposedData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, Predicate<D> originalDataCheck, D aProposedData, D aAdjustedData, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> originalDataCheck.test(adjusted.originalData) && $utils.checkEquals(aProposedData, adjusted.proposedData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, Predicate<D> originalDataCheck, D aProposedData, D aAdjustedData, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> originalDataCheck.test(adjusted.originalData) && $utils.checkEquals(aProposedData, adjusted.proposedData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, Predicate<D> originalDataCheck, D aProposedData, D aAdjustedData, TARGET theValue) {
            return adjusted(adjusted -> originalDataCheck.test(adjusted.originalData) && $utils.checkEquals(aProposedData, adjusted.proposedData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, Predicate<D> originalDataCheck, D aProposedData, D aAdjustedData, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && originalDataCheck.test(adjusted.originalData) && $utils.checkEquals(aProposedData, adjusted.proposedData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, Predicate<D> originalDataCheck, D aProposedData, D aAdjustedData, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && originalDataCheck.test(adjusted.originalData) && $utils.checkEquals(aProposedData, adjusted.proposedData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, Predicate<D> originalDataCheck, D aProposedData, D aAdjustedData, TARGET theValue) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && originalDataCheck.test(adjusted.originalData) && $utils.checkEquals(aProposedData, adjusted.proposedData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, D aProposedData, D aAdjustedData, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && originalDataCheck.test(adjusted.originalData) && $utils.checkEquals(aProposedData, adjusted.proposedData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, D aProposedData, D aAdjustedData, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && originalDataCheck.test(adjusted.originalData) && $utils.checkEquals(aProposedData, adjusted.proposedData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, D aProposedData, D aAdjustedData, TARGET theValue) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && originalDataCheck.test(adjusted.originalData) && $utils.checkEquals(aProposedData, adjusted.proposedData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, Absent originalData, Predicate<D> proposedDataCheck, D aAdjustedData, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> proposedDataCheck.test(adjusted.proposedData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, Absent originalData, Predicate<D> proposedDataCheck, D aAdjustedData, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> proposedDataCheck.test(adjusted.proposedData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, Absent originalData, Predicate<D> proposedDataCheck, D aAdjustedData, TARGET theValue) {
            return adjusted(adjusted -> proposedDataCheck.test(adjusted.proposedData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, Absent originalData, Predicate<D> proposedDataCheck, D aAdjustedData, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && proposedDataCheck.test(adjusted.proposedData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, Absent originalData, Predicate<D> proposedDataCheck, D aAdjustedData, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && proposedDataCheck.test(adjusted.proposedData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, Absent originalData, Predicate<D> proposedDataCheck, D aAdjustedData, TARGET theValue) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && proposedDataCheck.test(adjusted.proposedData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, Absent originalData, Predicate<D> proposedDataCheck, D aAdjustedData, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && proposedDataCheck.test(adjusted.proposedData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, Absent originalData, Predicate<D> proposedDataCheck, D aAdjustedData, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && proposedDataCheck.test(adjusted.proposedData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, Absent originalData, Predicate<D> proposedDataCheck, D aAdjustedData, TARGET theValue) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && proposedDataCheck.test(adjusted.proposedData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, D aOriginalData, Predicate<D> proposedDataCheck, D aAdjustedData, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> $utils.checkEquals(aOriginalData, adjusted.originalData) && proposedDataCheck.test(adjusted.proposedData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, D aOriginalData, Predicate<D> proposedDataCheck, D aAdjustedData, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> $utils.checkEquals(aOriginalData, adjusted.originalData) && proposedDataCheck.test(adjusted.proposedData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, D aOriginalData, Predicate<D> proposedDataCheck, D aAdjustedData, TARGET theValue) {
            return adjusted(adjusted -> $utils.checkEquals(aOriginalData, adjusted.originalData) && proposedDataCheck.test(adjusted.proposedData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, D aOriginalData, Predicate<D> proposedDataCheck, D aAdjustedData, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && $utils.checkEquals(aOriginalData, adjusted.originalData) && proposedDataCheck.test(adjusted.proposedData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, D aOriginalData, Predicate<D> proposedDataCheck, D aAdjustedData, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && $utils.checkEquals(aOriginalData, adjusted.originalData) && proposedDataCheck.test(adjusted.proposedData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, D aOriginalData, Predicate<D> proposedDataCheck, D aAdjustedData, TARGET theValue) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && $utils.checkEquals(aOriginalData, adjusted.originalData) && proposedDataCheck.test(adjusted.proposedData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, D aOriginalData, Predicate<D> proposedDataCheck, D aAdjustedData, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && $utils.checkEquals(aOriginalData, adjusted.originalData) && proposedDataCheck.test(adjusted.proposedData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, D aOriginalData, Predicate<D> proposedDataCheck, D aAdjustedData, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && $utils.checkEquals(aOriginalData, adjusted.originalData) && proposedDataCheck.test(adjusted.proposedData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, D aOriginalData, Predicate<D> proposedDataCheck, D aAdjustedData, TARGET theValue) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && $utils.checkEquals(aOriginalData, adjusted.originalData) && proposedDataCheck.test(adjusted.proposedData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, Predicate<D> originalDataCheck, Predicate<D> proposedDataCheck, D aAdjustedData, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> originalDataCheck.test(adjusted.originalData) && proposedDataCheck.test(adjusted.proposedData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, Predicate<D> originalDataCheck, Predicate<D> proposedDataCheck, D aAdjustedData, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> originalDataCheck.test(adjusted.originalData) && proposedDataCheck.test(adjusted.proposedData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, Predicate<D> originalDataCheck, Predicate<D> proposedDataCheck, D aAdjustedData, TARGET theValue) {
            return adjusted(adjusted -> originalDataCheck.test(adjusted.originalData) && proposedDataCheck.test(adjusted.proposedData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, Predicate<D> originalDataCheck, Predicate<D> proposedDataCheck, D aAdjustedData, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && originalDataCheck.test(adjusted.originalData) && proposedDataCheck.test(adjusted.proposedData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, Predicate<D> originalDataCheck, Predicate<D> proposedDataCheck, D aAdjustedData, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && originalDataCheck.test(adjusted.originalData) && proposedDataCheck.test(adjusted.proposedData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, Predicate<D> originalDataCheck, Predicate<D> proposedDataCheck, D aAdjustedData, TARGET theValue) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && originalDataCheck.test(adjusted.originalData) && proposedDataCheck.test(adjusted.proposedData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Predicate<D> proposedDataCheck, D aAdjustedData, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && originalDataCheck.test(adjusted.originalData) && proposedDataCheck.test(adjusted.proposedData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Predicate<D> proposedDataCheck, D aAdjustedData, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && originalDataCheck.test(adjusted.originalData) && proposedDataCheck.test(adjusted.proposedData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Predicate<D> proposedDataCheck, D aAdjustedData, TARGET theValue) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && originalDataCheck.test(adjusted.originalData) && proposedDataCheck.test(adjusted.proposedData) && $utils.checkEquals(aAdjustedData, adjusted.adjustedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, Absent originalData, Absent proposedData, Predicate<D> adjustedDataCheck, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> adjustedDataCheck.test(adjusted.adjustedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, Absent originalData, Absent proposedData, Predicate<D> adjustedDataCheck, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> adjustedDataCheck.test(adjusted.adjustedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, Absent originalData, Absent proposedData, Predicate<D> adjustedDataCheck, TARGET theValue) {
            return adjusted(adjusted -> adjustedDataCheck.test(adjusted.adjustedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, Absent originalData, Absent proposedData, Predicate<D> adjustedDataCheck, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && adjustedDataCheck.test(adjusted.adjustedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, Absent originalData, Absent proposedData, Predicate<D> adjustedDataCheck, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && adjustedDataCheck.test(adjusted.adjustedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, Absent originalData, Absent proposedData, Predicate<D> adjustedDataCheck, TARGET theValue) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && adjustedDataCheck.test(adjusted.adjustedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, Absent originalData, Absent proposedData, Predicate<D> adjustedDataCheck, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && adjustedDataCheck.test(adjusted.adjustedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, Absent originalData, Absent proposedData, Predicate<D> adjustedDataCheck, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && adjustedDataCheck.test(adjusted.adjustedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, Absent originalData, Absent proposedData, Predicate<D> adjustedDataCheck, TARGET theValue) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && adjustedDataCheck.test(adjusted.adjustedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, D aOriginalData, Absent proposedData, Predicate<D> adjustedDataCheck, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> $utils.checkEquals(aOriginalData, adjusted.originalData) && adjustedDataCheck.test(adjusted.adjustedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, D aOriginalData, Absent proposedData, Predicate<D> adjustedDataCheck, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> $utils.checkEquals(aOriginalData, adjusted.originalData) && adjustedDataCheck.test(adjusted.adjustedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, D aOriginalData, Absent proposedData, Predicate<D> adjustedDataCheck, TARGET theValue) {
            return adjusted(adjusted -> $utils.checkEquals(aOriginalData, adjusted.originalData) && adjustedDataCheck.test(adjusted.adjustedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, D aOriginalData, Absent proposedData, Predicate<D> adjustedDataCheck, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && $utils.checkEquals(aOriginalData, adjusted.originalData) && adjustedDataCheck.test(adjusted.adjustedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, D aOriginalData, Absent proposedData, Predicate<D> adjustedDataCheck, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && $utils.checkEquals(aOriginalData, adjusted.originalData) && adjustedDataCheck.test(adjusted.adjustedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, D aOriginalData, Absent proposedData, Predicate<D> adjustedDataCheck, TARGET theValue) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && $utils.checkEquals(aOriginalData, adjusted.originalData) && adjustedDataCheck.test(adjusted.adjustedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, D aOriginalData, Absent proposedData, Predicate<D> adjustedDataCheck, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && $utils.checkEquals(aOriginalData, adjusted.originalData) && adjustedDataCheck.test(adjusted.adjustedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, D aOriginalData, Absent proposedData, Predicate<D> adjustedDataCheck, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && $utils.checkEquals(aOriginalData, adjusted.originalData) && adjustedDataCheck.test(adjusted.adjustedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, D aOriginalData, Absent proposedData, Predicate<D> adjustedDataCheck, TARGET theValue) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && $utils.checkEquals(aOriginalData, adjusted.originalData) && adjustedDataCheck.test(adjusted.adjustedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, Predicate<D> originalDataCheck, Absent proposedData, Predicate<D> adjustedDataCheck, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> originalDataCheck.test(adjusted.originalData) && adjustedDataCheck.test(adjusted.adjustedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, Predicate<D> originalDataCheck, Absent proposedData, Predicate<D> adjustedDataCheck, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> originalDataCheck.test(adjusted.originalData) && adjustedDataCheck.test(adjusted.adjustedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, Predicate<D> originalDataCheck, Absent proposedData, Predicate<D> adjustedDataCheck, TARGET theValue) {
            return adjusted(adjusted -> originalDataCheck.test(adjusted.originalData) && adjustedDataCheck.test(adjusted.adjustedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, Predicate<D> originalDataCheck, Absent proposedData, Predicate<D> adjustedDataCheck, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && originalDataCheck.test(adjusted.originalData) && adjustedDataCheck.test(adjusted.adjustedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, Predicate<D> originalDataCheck, Absent proposedData, Predicate<D> adjustedDataCheck, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && originalDataCheck.test(adjusted.originalData) && adjustedDataCheck.test(adjusted.adjustedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, Predicate<D> originalDataCheck, Absent proposedData, Predicate<D> adjustedDataCheck, TARGET theValue) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && originalDataCheck.test(adjusted.originalData) && adjustedDataCheck.test(adjusted.adjustedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Absent proposedData, Predicate<D> adjustedDataCheck, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && originalDataCheck.test(adjusted.originalData) && adjustedDataCheck.test(adjusted.adjustedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Absent proposedData, Predicate<D> adjustedDataCheck, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && originalDataCheck.test(adjusted.originalData) && adjustedDataCheck.test(adjusted.adjustedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Absent proposedData, Predicate<D> adjustedDataCheck, TARGET theValue) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && originalDataCheck.test(adjusted.originalData) && adjustedDataCheck.test(adjusted.adjustedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, Absent originalData, D aProposedData, Predicate<D> adjustedDataCheck, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> $utils.checkEquals(aProposedData, adjusted.proposedData) && adjustedDataCheck.test(adjusted.adjustedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, Absent originalData, D aProposedData, Predicate<D> adjustedDataCheck, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> $utils.checkEquals(aProposedData, adjusted.proposedData) && adjustedDataCheck.test(adjusted.adjustedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, Absent originalData, D aProposedData, Predicate<D> adjustedDataCheck, TARGET theValue) {
            return adjusted(adjusted -> $utils.checkEquals(aProposedData, adjusted.proposedData) && adjustedDataCheck.test(adjusted.adjustedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, Absent originalData, D aProposedData, Predicate<D> adjustedDataCheck, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && $utils.checkEquals(aProposedData, adjusted.proposedData) && adjustedDataCheck.test(adjusted.adjustedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, Absent originalData, D aProposedData, Predicate<D> adjustedDataCheck, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && $utils.checkEquals(aProposedData, adjusted.proposedData) && adjustedDataCheck.test(adjusted.adjustedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, Absent originalData, D aProposedData, Predicate<D> adjustedDataCheck, TARGET theValue) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && $utils.checkEquals(aProposedData, adjusted.proposedData) && adjustedDataCheck.test(adjusted.adjustedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, Absent originalData, D aProposedData, Predicate<D> adjustedDataCheck, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && $utils.checkEquals(aProposedData, adjusted.proposedData) && adjustedDataCheck.test(adjusted.adjustedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, Absent originalData, D aProposedData, Predicate<D> adjustedDataCheck, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && $utils.checkEquals(aProposedData, adjusted.proposedData) && adjustedDataCheck.test(adjusted.adjustedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, Absent originalData, D aProposedData, Predicate<D> adjustedDataCheck, TARGET theValue) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && $utils.checkEquals(aProposedData, adjusted.proposedData) && adjustedDataCheck.test(adjusted.adjustedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, D aOriginalData, D aProposedData, Predicate<D> adjustedDataCheck, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> $utils.checkEquals(aOriginalData, adjusted.originalData) && $utils.checkEquals(aProposedData, adjusted.proposedData) && adjustedDataCheck.test(adjusted.adjustedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, D aOriginalData, D aProposedData, Predicate<D> adjustedDataCheck, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> $utils.checkEquals(aOriginalData, adjusted.originalData) && $utils.checkEquals(aProposedData, adjusted.proposedData) && adjustedDataCheck.test(adjusted.adjustedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, D aOriginalData, D aProposedData, Predicate<D> adjustedDataCheck, TARGET theValue) {
            return adjusted(adjusted -> $utils.checkEquals(aOriginalData, adjusted.originalData) && $utils.checkEquals(aProposedData, adjusted.proposedData) && adjustedDataCheck.test(adjusted.adjustedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, D aOriginalData, D aProposedData, Predicate<D> adjustedDataCheck, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && $utils.checkEquals(aOriginalData, adjusted.originalData) && $utils.checkEquals(aProposedData, adjusted.proposedData) && adjustedDataCheck.test(adjusted.adjustedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, D aOriginalData, D aProposedData, Predicate<D> adjustedDataCheck, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && $utils.checkEquals(aOriginalData, adjusted.originalData) && $utils.checkEquals(aProposedData, adjusted.proposedData) && adjustedDataCheck.test(adjusted.adjustedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, D aOriginalData, D aProposedData, Predicate<D> adjustedDataCheck, TARGET theValue) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && $utils.checkEquals(aOriginalData, adjusted.originalData) && $utils.checkEquals(aProposedData, adjusted.proposedData) && adjustedDataCheck.test(adjusted.adjustedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, D aOriginalData, D aProposedData, Predicate<D> adjustedDataCheck, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && $utils.checkEquals(aOriginalData, adjusted.originalData) && $utils.checkEquals(aProposedData, adjusted.proposedData) && adjustedDataCheck.test(adjusted.adjustedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, D aOriginalData, D aProposedData, Predicate<D> adjustedDataCheck, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && $utils.checkEquals(aOriginalData, adjusted.originalData) && $utils.checkEquals(aProposedData, adjusted.proposedData) && adjustedDataCheck.test(adjusted.adjustedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, D aOriginalData, D aProposedData, Predicate<D> adjustedDataCheck, TARGET theValue) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && $utils.checkEquals(aOriginalData, adjusted.originalData) && $utils.checkEquals(aProposedData, adjusted.proposedData) && adjustedDataCheck.test(adjusted.adjustedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, Predicate<D> originalDataCheck, D aProposedData, Predicate<D> adjustedDataCheck, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> originalDataCheck.test(adjusted.originalData) && $utils.checkEquals(aProposedData, adjusted.proposedData) && adjustedDataCheck.test(adjusted.adjustedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, Predicate<D> originalDataCheck, D aProposedData, Predicate<D> adjustedDataCheck, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> originalDataCheck.test(adjusted.originalData) && $utils.checkEquals(aProposedData, adjusted.proposedData) && adjustedDataCheck.test(adjusted.adjustedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, Predicate<D> originalDataCheck, D aProposedData, Predicate<D> adjustedDataCheck, TARGET theValue) {
            return adjusted(adjusted -> originalDataCheck.test(adjusted.originalData) && $utils.checkEquals(aProposedData, adjusted.proposedData) && adjustedDataCheck.test(adjusted.adjustedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, Predicate<D> originalDataCheck, D aProposedData, Predicate<D> adjustedDataCheck, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && originalDataCheck.test(adjusted.originalData) && $utils.checkEquals(aProposedData, adjusted.proposedData) && adjustedDataCheck.test(adjusted.adjustedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, Predicate<D> originalDataCheck, D aProposedData, Predicate<D> adjustedDataCheck, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && originalDataCheck.test(adjusted.originalData) && $utils.checkEquals(aProposedData, adjusted.proposedData) && adjustedDataCheck.test(adjusted.adjustedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, Predicate<D> originalDataCheck, D aProposedData, Predicate<D> adjustedDataCheck, TARGET theValue) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && originalDataCheck.test(adjusted.originalData) && $utils.checkEquals(aProposedData, adjusted.proposedData) && adjustedDataCheck.test(adjusted.adjustedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, D aProposedData, Predicate<D> adjustedDataCheck, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && originalDataCheck.test(adjusted.originalData) && $utils.checkEquals(aProposedData, adjusted.proposedData) && adjustedDataCheck.test(adjusted.adjustedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, D aProposedData, Predicate<D> adjustedDataCheck, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && originalDataCheck.test(adjusted.originalData) && $utils.checkEquals(aProposedData, adjusted.proposedData) && adjustedDataCheck.test(adjusted.adjustedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, D aProposedData, Predicate<D> adjustedDataCheck, TARGET theValue) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && originalDataCheck.test(adjusted.originalData) && $utils.checkEquals(aProposedData, adjusted.proposedData) && adjustedDataCheck.test(adjusted.adjustedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, Absent originalData, Predicate<D> proposedDataCheck, Predicate<D> adjustedDataCheck, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> proposedDataCheck.test(adjusted.proposedData) && adjustedDataCheck.test(adjusted.adjustedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, Absent originalData, Predicate<D> proposedDataCheck, Predicate<D> adjustedDataCheck, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> proposedDataCheck.test(adjusted.proposedData) && adjustedDataCheck.test(adjusted.adjustedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, Absent originalData, Predicate<D> proposedDataCheck, Predicate<D> adjustedDataCheck, TARGET theValue) {
            return adjusted(adjusted -> proposedDataCheck.test(adjusted.proposedData) && adjustedDataCheck.test(adjusted.adjustedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, Absent originalData, Predicate<D> proposedDataCheck, Predicate<D> adjustedDataCheck, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && proposedDataCheck.test(adjusted.proposedData) && adjustedDataCheck.test(adjusted.adjustedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, Absent originalData, Predicate<D> proposedDataCheck, Predicate<D> adjustedDataCheck, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && proposedDataCheck.test(adjusted.proposedData) && adjustedDataCheck.test(adjusted.adjustedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, Absent originalData, Predicate<D> proposedDataCheck, Predicate<D> adjustedDataCheck, TARGET theValue) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && proposedDataCheck.test(adjusted.proposedData) && adjustedDataCheck.test(adjusted.adjustedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, Absent originalData, Predicate<D> proposedDataCheck, Predicate<D> adjustedDataCheck, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && proposedDataCheck.test(adjusted.proposedData) && adjustedDataCheck.test(adjusted.adjustedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, Absent originalData, Predicate<D> proposedDataCheck, Predicate<D> adjustedDataCheck, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && proposedDataCheck.test(adjusted.proposedData) && adjustedDataCheck.test(adjusted.adjustedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, Absent originalData, Predicate<D> proposedDataCheck, Predicate<D> adjustedDataCheck, TARGET theValue) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && proposedDataCheck.test(adjusted.proposedData) && adjustedDataCheck.test(adjusted.adjustedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, D aOriginalData, Predicate<D> proposedDataCheck, Predicate<D> adjustedDataCheck, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> $utils.checkEquals(aOriginalData, adjusted.originalData) && proposedDataCheck.test(adjusted.proposedData) && adjustedDataCheck.test(adjusted.adjustedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, D aOriginalData, Predicate<D> proposedDataCheck, Predicate<D> adjustedDataCheck, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> $utils.checkEquals(aOriginalData, adjusted.originalData) && proposedDataCheck.test(adjusted.proposedData) && adjustedDataCheck.test(adjusted.adjustedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, D aOriginalData, Predicate<D> proposedDataCheck, Predicate<D> adjustedDataCheck, TARGET theValue) {
            return adjusted(adjusted -> $utils.checkEquals(aOriginalData, adjusted.originalData) && proposedDataCheck.test(adjusted.proposedData) && adjustedDataCheck.test(adjusted.adjustedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, D aOriginalData, Predicate<D> proposedDataCheck, Predicate<D> adjustedDataCheck, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && $utils.checkEquals(aOriginalData, adjusted.originalData) && proposedDataCheck.test(adjusted.proposedData) && adjustedDataCheck.test(adjusted.adjustedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, D aOriginalData, Predicate<D> proposedDataCheck, Predicate<D> adjustedDataCheck, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && $utils.checkEquals(aOriginalData, adjusted.originalData) && proposedDataCheck.test(adjusted.proposedData) && adjustedDataCheck.test(adjusted.adjustedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, D aOriginalData, Predicate<D> proposedDataCheck, Predicate<D> adjustedDataCheck, TARGET theValue) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && $utils.checkEquals(aOriginalData, adjusted.originalData) && proposedDataCheck.test(adjusted.proposedData) && adjustedDataCheck.test(adjusted.adjustedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, D aOriginalData, Predicate<D> proposedDataCheck, Predicate<D> adjustedDataCheck, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && $utils.checkEquals(aOriginalData, adjusted.originalData) && proposedDataCheck.test(adjusted.proposedData) && adjustedDataCheck.test(adjusted.adjustedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, D aOriginalData, Predicate<D> proposedDataCheck, Predicate<D> adjustedDataCheck, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && $utils.checkEquals(aOriginalData, adjusted.originalData) && proposedDataCheck.test(adjusted.proposedData) && adjustedDataCheck.test(adjusted.adjustedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, D aOriginalData, Predicate<D> proposedDataCheck, Predicate<D> adjustedDataCheck, TARGET theValue) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && $utils.checkEquals(aOriginalData, adjusted.originalData) && proposedDataCheck.test(adjusted.proposedData) && adjustedDataCheck.test(adjusted.adjustedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, Predicate<D> originalDataCheck, Predicate<D> proposedDataCheck, Predicate<D> adjustedDataCheck, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> originalDataCheck.test(adjusted.originalData) && proposedDataCheck.test(adjusted.proposedData) && adjustedDataCheck.test(adjusted.adjustedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, Predicate<D> originalDataCheck, Predicate<D> proposedDataCheck, Predicate<D> adjustedDataCheck, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> originalDataCheck.test(adjusted.originalData) && proposedDataCheck.test(adjusted.proposedData) && adjustedDataCheck.test(adjusted.adjustedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Absent store, Predicate<D> originalDataCheck, Predicate<D> proposedDataCheck, Predicate<D> adjustedDataCheck, TARGET theValue) {
            return adjusted(adjusted -> originalDataCheck.test(adjusted.originalData) && proposedDataCheck.test(adjusted.proposedData) && adjustedDataCheck.test(adjusted.adjustedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, Predicate<D> originalDataCheck, Predicate<D> proposedDataCheck, Predicate<D> adjustedDataCheck, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && originalDataCheck.test(adjusted.originalData) && proposedDataCheck.test(adjusted.proposedData) && adjustedDataCheck.test(adjusted.adjustedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, Predicate<D> originalDataCheck, Predicate<D> proposedDataCheck, Predicate<D> adjustedDataCheck, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && originalDataCheck.test(adjusted.originalData) && proposedDataCheck.test(adjusted.proposedData) && adjustedDataCheck.test(adjusted.adjustedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Store aStore, Predicate<D> originalDataCheck, Predicate<D> proposedDataCheck, Predicate<D> adjustedDataCheck, TARGET theValue) {
            return adjusted(adjusted -> $utils.checkEquals(aStore, adjusted.store) && originalDataCheck.test(adjusted.originalData) && proposedDataCheck.test(adjusted.proposedData) && adjustedDataCheck.test(adjusted.adjustedData), theValue);
        }
        
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Predicate<D> proposedDataCheck, Predicate<D> adjustedDataCheck, Function<Adjusted<D>, TARGET> theAction) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && originalDataCheck.test(adjusted.originalData) && proposedDataCheck.test(adjusted.proposedData) && adjustedDataCheck.test(adjusted.adjustedData), theAction);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Predicate<D> proposedDataCheck, Predicate<D> adjustedDataCheck, Supplier<TARGET> theSupplier) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && originalDataCheck.test(adjusted.originalData) && proposedDataCheck.test(adjusted.proposedData) && adjustedDataCheck.test(adjusted.adjustedData), theSupplier);
        }
        public ChangeResultSwitchAdjustedRejectedFailed<TARGET, D> adjustedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Predicate<D> proposedDataCheck, Predicate<D> adjustedDataCheck, TARGET theValue) {
            return adjusted(adjusted -> storeCheck.test(adjusted.store) && originalDataCheck.test(adjusted.originalData) && proposedDataCheck.test(adjusted.proposedData) && adjustedDataCheck.test(adjusted.adjustedData), theValue);
        }
    }
    public static class ChangeResultSwitchRejectedFailed<TARGET, D extends Object> extends ChoiceTypeSwitch<ChangeResult<D>, TARGET> {
        private ChangeResultSwitchRejectedFailed(ChangeResult<D> theValue, Function<ChangeResult<D>, TARGET> theAction) { super(theValue, theAction); }
        
        public ChangeResultSwitchFailed<TARGET, D> rejected(Function<? super Rejected<D>, TARGET> theAction) {
            Function<ChangeResult<D>, TARGET> oldAction = (Function<ChangeResult<D>, TARGET>)$action;
            Function<ChangeResult<D>, TARGET> newAction =
                ($action != null)
                ? oldAction : 
                    ($value instanceof Rejected)
                    ? (Function<ChangeResult<D>, TARGET>)(d -> theAction.apply((Rejected<D>)d))
                    : oldAction;
            
            return new ChangeResultSwitchFailed<TARGET, D>($value, newAction);
        }
        public ChangeResultSwitchFailed<TARGET, D> rejected(Supplier<TARGET> theSupplier) {
            return rejected(d->theSupplier.get());
        }
        public ChangeResultSwitchFailed<TARGET, D> rejected(TARGET theValue) {
            return rejected(d->theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejected(Predicate<Rejected<D>> check, Function<? super Rejected<D>, TARGET> theAction) {
            Function<ChangeResult<D>, TARGET> oldAction = (Function<ChangeResult<D>, TARGET>)$action;
            Function<ChangeResult<D>, TARGET> newAction =
                ($action != null)
                ? oldAction : 
                    (($value instanceof Rejected) && check.test((Rejected<D>)$value))
                    ? (Function<ChangeResult<D>, TARGET>)(d -> theAction.apply((Rejected<D>)d))
                    : oldAction;
            
            return new ChangeResultSwitchRejectedFailed<TARGET, D>($value, newAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejected(Predicate<Rejected<D>> check, Supplier<TARGET> theSupplier) {
            return rejected(check, d->theSupplier.get());
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejected(Predicate<Rejected<D>> check, TARGET theValue) {
            return rejected(check, d->theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, Absent propose, Absent rollback, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, Absent propose, Absent rollback, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, Absent propose, Absent rollback, Absent reason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, Absent propose, Absent rollback, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, Absent propose, Absent rollback, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, Absent propose, Absent rollback, Absent reason, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, Absent propose, Absent rollback, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, Absent propose, Absent rollback, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, Absent propose, Absent rollback, Absent reason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, Absent propose, Absent rollback, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, Absent propose, Absent rollback, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, Absent propose, Absent rollback, Absent reason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, Absent propose, Absent rollback, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, Absent propose, Absent rollback, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, Absent propose, Absent rollback, Absent reason, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, Absent propose, Absent rollback, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, Absent propose, Absent rollback, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, Absent propose, Absent rollback, Absent reason, TARGET theValue) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, Absent propose, Absent rollback, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, Absent propose, Absent rollback, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, Absent propose, Absent rollback, Absent reason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Absent propose, Absent rollback, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Absent propose, Absent rollback, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Absent propose, Absent rollback, Absent reason, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, D aPropose, Absent rollback, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aPropose, rejected.propose), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, D aPropose, Absent rollback, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aPropose, rejected.propose), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, D aPropose, Absent rollback, Absent reason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aPropose, rejected.propose), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, D aPropose, Absent rollback, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aPropose, rejected.propose), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, D aPropose, Absent rollback, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aPropose, rejected.propose), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, D aPropose, Absent rollback, Absent reason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aPropose, rejected.propose), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, D aPropose, Absent rollback, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aPropose, rejected.propose), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, D aPropose, Absent rollback, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aPropose, rejected.propose), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, D aPropose, Absent rollback, Absent reason, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aPropose, rejected.propose), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, D aPropose, Absent rollback, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, D aPropose, Absent rollback, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, D aPropose, Absent rollback, Absent reason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, D aPropose, Absent rollback, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, D aPropose, Absent rollback, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, D aPropose, Absent rollback, Absent reason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, D aPropose, Absent rollback, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, D aPropose, Absent rollback, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, D aPropose, Absent rollback, Absent reason, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, D aPropose, Absent rollback, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, D aPropose, Absent rollback, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, D aPropose, Absent rollback, Absent reason, TARGET theValue) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, D aPropose, Absent rollback, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, D aPropose, Absent rollback, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, D aPropose, Absent rollback, Absent reason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, D aPropose, Absent rollback, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, D aPropose, Absent rollback, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, D aPropose, Absent rollback, Absent reason, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, Predicate<D> proposeCheck, Absent rollback, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> proposeCheck.test(rejected.propose), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, Predicate<D> proposeCheck, Absent rollback, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> proposeCheck.test(rejected.propose), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, Predicate<D> proposeCheck, Absent rollback, Absent reason, TARGET theValue) {
            return rejected(rejected -> proposeCheck.test(rejected.propose), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, Predicate<D> proposeCheck, Absent rollback, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && proposeCheck.test(rejected.propose), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, Predicate<D> proposeCheck, Absent rollback, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && proposeCheck.test(rejected.propose), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, Predicate<D> proposeCheck, Absent rollback, Absent reason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && proposeCheck.test(rejected.propose), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, Predicate<D> proposeCheck, Absent rollback, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && proposeCheck.test(rejected.propose), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, Predicate<D> proposeCheck, Absent rollback, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && proposeCheck.test(rejected.propose), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, Predicate<D> proposeCheck, Absent rollback, Absent reason, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && proposeCheck.test(rejected.propose), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, Predicate<D> proposeCheck, Absent rollback, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, Predicate<D> proposeCheck, Absent rollback, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, Predicate<D> proposeCheck, Absent rollback, Absent reason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, Predicate<D> proposeCheck, Absent rollback, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, Predicate<D> proposeCheck, Absent rollback, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, Predicate<D> proposeCheck, Absent rollback, Absent reason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, Predicate<D> proposeCheck, Absent rollback, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, Predicate<D> proposeCheck, Absent rollback, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, Predicate<D> proposeCheck, Absent rollback, Absent reason, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, Absent rollback, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, Absent rollback, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, Absent rollback, Absent reason, TARGET theValue) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, Absent rollback, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, Absent rollback, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, Absent rollback, Absent reason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, Absent rollback, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, Absent rollback, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, Absent rollback, Absent reason, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, Absent propose, D aRollback, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aRollback, rejected.rollback), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, Absent propose, D aRollback, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aRollback, rejected.rollback), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, Absent propose, D aRollback, Absent reason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aRollback, rejected.rollback), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, Absent propose, D aRollback, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aRollback, rejected.rollback), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, Absent propose, D aRollback, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aRollback, rejected.rollback), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, Absent propose, D aRollback, Absent reason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aRollback, rejected.rollback), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, Absent propose, D aRollback, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aRollback, rejected.rollback), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, Absent propose, D aRollback, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aRollback, rejected.rollback), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, Absent propose, D aRollback, Absent reason, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aRollback, rejected.rollback), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, Absent propose, D aRollback, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aRollback, rejected.rollback), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, Absent propose, D aRollback, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aRollback, rejected.rollback), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, Absent propose, D aRollback, Absent reason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aRollback, rejected.rollback), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, Absent propose, D aRollback, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aRollback, rejected.rollback), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, Absent propose, D aRollback, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aRollback, rejected.rollback), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, Absent propose, D aRollback, Absent reason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aRollback, rejected.rollback), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, Absent propose, D aRollback, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aRollback, rejected.rollback), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, Absent propose, D aRollback, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aRollback, rejected.rollback), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, Absent propose, D aRollback, Absent reason, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aRollback, rejected.rollback), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, Absent propose, D aRollback, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aRollback, rejected.rollback), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, Absent propose, D aRollback, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aRollback, rejected.rollback), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, Absent propose, D aRollback, Absent reason, TARGET theValue) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aRollback, rejected.rollback), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, Absent propose, D aRollback, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aRollback, rejected.rollback), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, Absent propose, D aRollback, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aRollback, rejected.rollback), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, Absent propose, D aRollback, Absent reason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aRollback, rejected.rollback), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Absent propose, D aRollback, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aRollback, rejected.rollback), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Absent propose, D aRollback, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aRollback, rejected.rollback), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Absent propose, D aRollback, Absent reason, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aRollback, rejected.rollback), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, D aPropose, D aRollback, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, D aPropose, D aRollback, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, D aPropose, D aRollback, Absent reason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, D aPropose, D aRollback, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, D aPropose, D aRollback, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, D aPropose, D aRollback, Absent reason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, D aPropose, D aRollback, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, D aPropose, D aRollback, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, D aPropose, D aRollback, Absent reason, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, D aPropose, D aRollback, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, D aPropose, D aRollback, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, D aPropose, D aRollback, Absent reason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, D aPropose, D aRollback, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, D aPropose, D aRollback, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, D aPropose, D aRollback, Absent reason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, D aPropose, D aRollback, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, D aPropose, D aRollback, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, D aPropose, D aRollback, Absent reason, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, D aPropose, D aRollback, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, D aPropose, D aRollback, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, D aPropose, D aRollback, Absent reason, TARGET theValue) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, D aPropose, D aRollback, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, D aPropose, D aRollback, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, D aPropose, D aRollback, Absent reason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, D aPropose, D aRollback, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, D aPropose, D aRollback, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, D aPropose, D aRollback, Absent reason, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, Predicate<D> proposeCheck, D aRollback, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, Predicate<D> proposeCheck, D aRollback, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, Predicate<D> proposeCheck, D aRollback, Absent reason, TARGET theValue) {
            return rejected(rejected -> proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, Predicate<D> proposeCheck, D aRollback, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, Predicate<D> proposeCheck, D aRollback, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, Predicate<D> proposeCheck, D aRollback, Absent reason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, Predicate<D> proposeCheck, D aRollback, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, Predicate<D> proposeCheck, D aRollback, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, Predicate<D> proposeCheck, D aRollback, Absent reason, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, Predicate<D> proposeCheck, D aRollback, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, Predicate<D> proposeCheck, D aRollback, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, Predicate<D> proposeCheck, D aRollback, Absent reason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, Predicate<D> proposeCheck, D aRollback, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, Predicate<D> proposeCheck, D aRollback, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, Predicate<D> proposeCheck, D aRollback, Absent reason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, Predicate<D> proposeCheck, D aRollback, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, Predicate<D> proposeCheck, D aRollback, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, Predicate<D> proposeCheck, D aRollback, Absent reason, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, D aRollback, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, D aRollback, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, D aRollback, Absent reason, TARGET theValue) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, D aRollback, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, D aRollback, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, D aRollback, Absent reason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, D aRollback, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, D aRollback, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, D aRollback, Absent reason, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, Absent propose, Predicate<D> rollbackCheck, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> rollbackCheck.test(rejected.rollback), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, Absent propose, Predicate<D> rollbackCheck, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> rollbackCheck.test(rejected.rollback), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, Absent propose, Predicate<D> rollbackCheck, Absent reason, TARGET theValue) {
            return rejected(rejected -> rollbackCheck.test(rejected.rollback), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, Absent propose, Predicate<D> rollbackCheck, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && rollbackCheck.test(rejected.rollback), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, Absent propose, Predicate<D> rollbackCheck, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && rollbackCheck.test(rejected.rollback), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, Absent propose, Predicate<D> rollbackCheck, Absent reason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && rollbackCheck.test(rejected.rollback), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, Absent propose, Predicate<D> rollbackCheck, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && rollbackCheck.test(rejected.rollback), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, Absent propose, Predicate<D> rollbackCheck, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && rollbackCheck.test(rejected.rollback), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, Absent propose, Predicate<D> rollbackCheck, Absent reason, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && rollbackCheck.test(rejected.rollback), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, Absent propose, Predicate<D> rollbackCheck, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && rollbackCheck.test(rejected.rollback), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, Absent propose, Predicate<D> rollbackCheck, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && rollbackCheck.test(rejected.rollback), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, Absent propose, Predicate<D> rollbackCheck, Absent reason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && rollbackCheck.test(rejected.rollback), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, Absent propose, Predicate<D> rollbackCheck, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && rollbackCheck.test(rejected.rollback), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, Absent propose, Predicate<D> rollbackCheck, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && rollbackCheck.test(rejected.rollback), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, Absent propose, Predicate<D> rollbackCheck, Absent reason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && rollbackCheck.test(rejected.rollback), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, Absent propose, Predicate<D> rollbackCheck, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && rollbackCheck.test(rejected.rollback), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, Absent propose, Predicate<D> rollbackCheck, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && rollbackCheck.test(rejected.rollback), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, Absent propose, Predicate<D> rollbackCheck, Absent reason, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && rollbackCheck.test(rejected.rollback), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, Absent propose, Predicate<D> rollbackCheck, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && rollbackCheck.test(rejected.rollback), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, Absent propose, Predicate<D> rollbackCheck, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && rollbackCheck.test(rejected.rollback), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, Absent propose, Predicate<D> rollbackCheck, Absent reason, TARGET theValue) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && rollbackCheck.test(rejected.rollback), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, Absent propose, Predicate<D> rollbackCheck, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && rollbackCheck.test(rejected.rollback), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, Absent propose, Predicate<D> rollbackCheck, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && rollbackCheck.test(rejected.rollback), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, Absent propose, Predicate<D> rollbackCheck, Absent reason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && rollbackCheck.test(rejected.rollback), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Absent propose, Predicate<D> rollbackCheck, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && rollbackCheck.test(rejected.rollback), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Absent propose, Predicate<D> rollbackCheck, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && rollbackCheck.test(rejected.rollback), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Absent propose, Predicate<D> rollbackCheck, Absent reason, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && rollbackCheck.test(rejected.rollback), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, D aPropose, Predicate<D> rollbackCheck, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, D aPropose, Predicate<D> rollbackCheck, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, D aPropose, Predicate<D> rollbackCheck, Absent reason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, D aPropose, Predicate<D> rollbackCheck, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, D aPropose, Predicate<D> rollbackCheck, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, D aPropose, Predicate<D> rollbackCheck, Absent reason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, D aPropose, Predicate<D> rollbackCheck, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, D aPropose, Predicate<D> rollbackCheck, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, D aPropose, Predicate<D> rollbackCheck, Absent reason, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, D aPropose, Predicate<D> rollbackCheck, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, D aPropose, Predicate<D> rollbackCheck, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, D aPropose, Predicate<D> rollbackCheck, Absent reason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, D aPropose, Predicate<D> rollbackCheck, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, D aPropose, Predicate<D> rollbackCheck, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, D aPropose, Predicate<D> rollbackCheck, Absent reason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, D aPropose, Predicate<D> rollbackCheck, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, D aPropose, Predicate<D> rollbackCheck, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, D aPropose, Predicate<D> rollbackCheck, Absent reason, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, D aPropose, Predicate<D> rollbackCheck, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, D aPropose, Predicate<D> rollbackCheck, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, D aPropose, Predicate<D> rollbackCheck, Absent reason, TARGET theValue) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, D aPropose, Predicate<D> rollbackCheck, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, D aPropose, Predicate<D> rollbackCheck, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, D aPropose, Predicate<D> rollbackCheck, Absent reason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, D aPropose, Predicate<D> rollbackCheck, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, D aPropose, Predicate<D> rollbackCheck, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, D aPropose, Predicate<D> rollbackCheck, Absent reason, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, Absent reason, TARGET theValue) {
            return rejected(rejected -> proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, Absent reason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, Absent reason, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, Absent reason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, Absent reason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, Absent reason, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, Absent reason, TARGET theValue) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, Absent reason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, Absent reason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, Absent reason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, Absent reason, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, Absent propose, Absent rollback, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, Absent propose, Absent rollback, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, Absent propose, Absent rollback, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, Absent propose, Absent rollback, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, Absent propose, Absent rollback, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, Absent propose, Absent rollback, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, Absent propose, Absent rollback, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, Absent propose, Absent rollback, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, Absent propose, Absent rollback, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, Absent propose, Absent rollback, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, Absent propose, Absent rollback, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, Absent propose, Absent rollback, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, Absent propose, Absent rollback, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, Absent propose, Absent rollback, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, Absent propose, Absent rollback, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, Absent propose, Absent rollback, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, Absent propose, Absent rollback, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, Absent propose, Absent rollback, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, Absent propose, Absent rollback, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, Absent propose, Absent rollback, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, Absent propose, Absent rollback, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, Absent propose, Absent rollback, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, Absent propose, Absent rollback, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, Absent propose, Absent rollback, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Absent propose, Absent rollback, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Absent propose, Absent rollback, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Absent propose, Absent rollback, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, D aPropose, Absent rollback, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, D aPropose, Absent rollback, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, D aPropose, Absent rollback, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, D aPropose, Absent rollback, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, D aPropose, Absent rollback, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, D aPropose, Absent rollback, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, D aPropose, Absent rollback, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, D aPropose, Absent rollback, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, D aPropose, Absent rollback, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, D aPropose, Absent rollback, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, D aPropose, Absent rollback, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, D aPropose, Absent rollback, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, D aPropose, Absent rollback, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, D aPropose, Absent rollback, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, D aPropose, Absent rollback, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, D aPropose, Absent rollback, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, D aPropose, Absent rollback, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, D aPropose, Absent rollback, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, D aPropose, Absent rollback, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, D aPropose, Absent rollback, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, D aPropose, Absent rollback, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, D aPropose, Absent rollback, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, D aPropose, Absent rollback, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, D aPropose, Absent rollback, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, D aPropose, Absent rollback, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, D aPropose, Absent rollback, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, D aPropose, Absent rollback, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, Predicate<D> proposeCheck, Absent rollback, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> proposeCheck.test(rejected.propose) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, Predicate<D> proposeCheck, Absent rollback, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> proposeCheck.test(rejected.propose) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, Predicate<D> proposeCheck, Absent rollback, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> proposeCheck.test(rejected.propose) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, Predicate<D> proposeCheck, Absent rollback, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, Predicate<D> proposeCheck, Absent rollback, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, Predicate<D> proposeCheck, Absent rollback, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, Predicate<D> proposeCheck, Absent rollback, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, Predicate<D> proposeCheck, Absent rollback, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, Predicate<D> proposeCheck, Absent rollback, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, Predicate<D> proposeCheck, Absent rollback, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, Predicate<D> proposeCheck, Absent rollback, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, Predicate<D> proposeCheck, Absent rollback, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, Predicate<D> proposeCheck, Absent rollback, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, Predicate<D> proposeCheck, Absent rollback, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, Predicate<D> proposeCheck, Absent rollback, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, Predicate<D> proposeCheck, Absent rollback, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, Predicate<D> proposeCheck, Absent rollback, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, Predicate<D> proposeCheck, Absent rollback, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, Absent rollback, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, Absent rollback, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, Absent rollback, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, Absent rollback, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, Absent rollback, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, Absent rollback, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, Absent rollback, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, Absent rollback, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, Absent rollback, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, Absent propose, D aRollback, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, Absent propose, D aRollback, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, Absent propose, D aRollback, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, Absent propose, D aRollback, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, Absent propose, D aRollback, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, Absent propose, D aRollback, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, Absent propose, D aRollback, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, Absent propose, D aRollback, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, Absent propose, D aRollback, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, Absent propose, D aRollback, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, Absent propose, D aRollback, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, Absent propose, D aRollback, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, Absent propose, D aRollback, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, Absent propose, D aRollback, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, Absent propose, D aRollback, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, Absent propose, D aRollback, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, Absent propose, D aRollback, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, Absent propose, D aRollback, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, Absent propose, D aRollback, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, Absent propose, D aRollback, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, Absent propose, D aRollback, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, Absent propose, D aRollback, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, Absent propose, D aRollback, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, Absent propose, D aRollback, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Absent propose, D aRollback, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Absent propose, D aRollback, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Absent propose, D aRollback, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, D aPropose, D aRollback, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, D aPropose, D aRollback, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, D aPropose, D aRollback, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, D aPropose, D aRollback, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, D aPropose, D aRollback, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, D aPropose, D aRollback, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, D aPropose, D aRollback, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, D aPropose, D aRollback, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, D aPropose, D aRollback, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, D aPropose, D aRollback, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, D aPropose, D aRollback, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, D aPropose, D aRollback, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, D aPropose, D aRollback, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, D aPropose, D aRollback, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, D aPropose, D aRollback, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, D aPropose, D aRollback, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, D aPropose, D aRollback, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, D aPropose, D aRollback, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, D aPropose, D aRollback, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, D aPropose, D aRollback, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, D aPropose, D aRollback, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, D aPropose, D aRollback, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, D aPropose, D aRollback, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, D aPropose, D aRollback, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, D aPropose, D aRollback, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, D aPropose, D aRollback, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, D aPropose, D aRollback, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, Predicate<D> proposeCheck, D aRollback, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, Predicate<D> proposeCheck, D aRollback, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, Predicate<D> proposeCheck, D aRollback, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, Predicate<D> proposeCheck, D aRollback, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, Predicate<D> proposeCheck, D aRollback, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, Predicate<D> proposeCheck, D aRollback, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, Predicate<D> proposeCheck, D aRollback, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, Predicate<D> proposeCheck, D aRollback, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, Predicate<D> proposeCheck, D aRollback, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, Predicate<D> proposeCheck, D aRollback, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, Predicate<D> proposeCheck, D aRollback, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, Predicate<D> proposeCheck, D aRollback, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, Predicate<D> proposeCheck, D aRollback, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, Predicate<D> proposeCheck, D aRollback, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, Predicate<D> proposeCheck, D aRollback, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, Predicate<D> proposeCheck, D aRollback, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, Predicate<D> proposeCheck, D aRollback, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, Predicate<D> proposeCheck, D aRollback, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, D aRollback, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, D aRollback, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, D aRollback, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, D aRollback, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, D aRollback, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, D aRollback, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, D aRollback, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, D aRollback, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, D aRollback, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, Absent propose, Predicate<D> rollbackCheck, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, Absent propose, Predicate<D> rollbackCheck, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, Absent propose, Predicate<D> rollbackCheck, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, Absent propose, Predicate<D> rollbackCheck, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, Absent propose, Predicate<D> rollbackCheck, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, Absent propose, Predicate<D> rollbackCheck, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, Absent propose, Predicate<D> rollbackCheck, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, Absent propose, Predicate<D> rollbackCheck, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, Absent propose, Predicate<D> rollbackCheck, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, Absent propose, Predicate<D> rollbackCheck, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, Absent propose, Predicate<D> rollbackCheck, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, Absent propose, Predicate<D> rollbackCheck, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, Absent propose, Predicate<D> rollbackCheck, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, Absent propose, Predicate<D> rollbackCheck, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, Absent propose, Predicate<D> rollbackCheck, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, Absent propose, Predicate<D> rollbackCheck, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, Absent propose, Predicate<D> rollbackCheck, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, Absent propose, Predicate<D> rollbackCheck, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, Absent propose, Predicate<D> rollbackCheck, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, Absent propose, Predicate<D> rollbackCheck, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, Absent propose, Predicate<D> rollbackCheck, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, Absent propose, Predicate<D> rollbackCheck, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, Absent propose, Predicate<D> rollbackCheck, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, Absent propose, Predicate<D> rollbackCheck, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Absent propose, Predicate<D> rollbackCheck, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Absent propose, Predicate<D> rollbackCheck, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Absent propose, Predicate<D> rollbackCheck, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, D aPropose, Predicate<D> rollbackCheck, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, D aPropose, Predicate<D> rollbackCheck, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, D aPropose, Predicate<D> rollbackCheck, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, D aPropose, Predicate<D> rollbackCheck, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, D aPropose, Predicate<D> rollbackCheck, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, D aPropose, Predicate<D> rollbackCheck, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, D aPropose, Predicate<D> rollbackCheck, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, D aPropose, Predicate<D> rollbackCheck, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, D aPropose, Predicate<D> rollbackCheck, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, D aPropose, Predicate<D> rollbackCheck, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, D aPropose, Predicate<D> rollbackCheck, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, D aPropose, Predicate<D> rollbackCheck, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, D aPropose, Predicate<D> rollbackCheck, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, D aPropose, Predicate<D> rollbackCheck, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, D aPropose, Predicate<D> rollbackCheck, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, D aPropose, Predicate<D> rollbackCheck, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, D aPropose, Predicate<D> rollbackCheck, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, D aPropose, Predicate<D> rollbackCheck, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, D aPropose, Predicate<D> rollbackCheck, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, D aPropose, Predicate<D> rollbackCheck, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, D aPropose, Predicate<D> rollbackCheck, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, D aPropose, Predicate<D> rollbackCheck, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, D aPropose, Predicate<D> rollbackCheck, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, D aPropose, Predicate<D> rollbackCheck, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, D aPropose, Predicate<D> rollbackCheck, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, D aPropose, Predicate<D> rollbackCheck, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, D aPropose, Predicate<D> rollbackCheck, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, ChangeRejectedException aReason, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, ChangeRejectedException aReason, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, ChangeRejectedException aReason, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback) && $utils.checkEquals(aReason, rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, Absent propose, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, Absent propose, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, Absent propose, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, Absent propose, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, Absent propose, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, Absent propose, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, Absent propose, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, Absent propose, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, Absent propose, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, Absent propose, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, Absent propose, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, Absent propose, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, Absent propose, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, Absent propose, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, Absent propose, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, Absent propose, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, Absent propose, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, Absent propose, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, Absent propose, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, Absent propose, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, Absent propose, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, Absent propose, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, Absent propose, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, Absent propose, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Absent propose, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Absent propose, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Absent propose, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, D aPropose, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aPropose, rejected.propose) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, D aPropose, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aPropose, rejected.propose) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, D aPropose, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aPropose, rejected.propose) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, D aPropose, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aPropose, rejected.propose) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, D aPropose, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aPropose, rejected.propose) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, D aPropose, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aPropose, rejected.propose) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, D aPropose, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aPropose, rejected.propose) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, D aPropose, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aPropose, rejected.propose) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, D aPropose, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aPropose, rejected.propose) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, D aPropose, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, D aPropose, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, D aPropose, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, D aPropose, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, D aPropose, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, D aPropose, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, D aPropose, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, D aPropose, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, D aPropose, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, D aPropose, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, D aPropose, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, D aPropose, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, D aPropose, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, D aPropose, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, D aPropose, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, D aPropose, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, D aPropose, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, D aPropose, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, Predicate<D> proposeCheck, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> proposeCheck.test(rejected.propose) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, Predicate<D> proposeCheck, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> proposeCheck.test(rejected.propose) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, Predicate<D> proposeCheck, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> proposeCheck.test(rejected.propose) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, Predicate<D> proposeCheck, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && proposeCheck.test(rejected.propose) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, Predicate<D> proposeCheck, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && proposeCheck.test(rejected.propose) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, Predicate<D> proposeCheck, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && proposeCheck.test(rejected.propose) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, Predicate<D> proposeCheck, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && proposeCheck.test(rejected.propose) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, Predicate<D> proposeCheck, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && proposeCheck.test(rejected.propose) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, Predicate<D> proposeCheck, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && proposeCheck.test(rejected.propose) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, Predicate<D> proposeCheck, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, Predicate<D> proposeCheck, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, Predicate<D> proposeCheck, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, Predicate<D> proposeCheck, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, Predicate<D> proposeCheck, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, Predicate<D> proposeCheck, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, Predicate<D> proposeCheck, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, Predicate<D> proposeCheck, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, Predicate<D> proposeCheck, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, Absent rollback, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, Absent propose, D aRollback, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, Absent propose, D aRollback, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, Absent propose, D aRollback, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, Absent propose, D aRollback, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, Absent propose, D aRollback, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, Absent propose, D aRollback, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, Absent propose, D aRollback, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, Absent propose, D aRollback, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, Absent propose, D aRollback, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, Absent propose, D aRollback, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, Absent propose, D aRollback, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, Absent propose, D aRollback, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, Absent propose, D aRollback, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, Absent propose, D aRollback, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, Absent propose, D aRollback, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, Absent propose, D aRollback, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, Absent propose, D aRollback, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, Absent propose, D aRollback, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, Absent propose, D aRollback, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, Absent propose, D aRollback, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, Absent propose, D aRollback, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, Absent propose, D aRollback, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, Absent propose, D aRollback, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, Absent propose, D aRollback, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Absent propose, D aRollback, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Absent propose, D aRollback, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Absent propose, D aRollback, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, D aPropose, D aRollback, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, D aPropose, D aRollback, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, D aPropose, D aRollback, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, D aPropose, D aRollback, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, D aPropose, D aRollback, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, D aPropose, D aRollback, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, D aPropose, D aRollback, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, D aPropose, D aRollback, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, D aPropose, D aRollback, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, D aPropose, D aRollback, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, D aPropose, D aRollback, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, D aPropose, D aRollback, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, D aPropose, D aRollback, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, D aPropose, D aRollback, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, D aPropose, D aRollback, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, D aPropose, D aRollback, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, D aPropose, D aRollback, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, D aPropose, D aRollback, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, D aPropose, D aRollback, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, D aPropose, D aRollback, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, D aPropose, D aRollback, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, D aPropose, D aRollback, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, D aPropose, D aRollback, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, D aPropose, D aRollback, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, D aPropose, D aRollback, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, D aPropose, D aRollback, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, D aPropose, D aRollback, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, Predicate<D> proposeCheck, D aRollback, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, Predicate<D> proposeCheck, D aRollback, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, Predicate<D> proposeCheck, D aRollback, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, Predicate<D> proposeCheck, D aRollback, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, Predicate<D> proposeCheck, D aRollback, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, Predicate<D> proposeCheck, D aRollback, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, Predicate<D> proposeCheck, D aRollback, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, Predicate<D> proposeCheck, D aRollback, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, Predicate<D> proposeCheck, D aRollback, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, Predicate<D> proposeCheck, D aRollback, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, Predicate<D> proposeCheck, D aRollback, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, Predicate<D> proposeCheck, D aRollback, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, Predicate<D> proposeCheck, D aRollback, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, Predicate<D> proposeCheck, D aRollback, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, Predicate<D> proposeCheck, D aRollback, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, Predicate<D> proposeCheck, D aRollback, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, Predicate<D> proposeCheck, D aRollback, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, Predicate<D> proposeCheck, D aRollback, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, D aRollback, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, D aRollback, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, D aRollback, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, D aRollback, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, D aRollback, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, D aRollback, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, D aRollback, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, D aRollback, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, D aRollback, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && $utils.checkEquals(aRollback, rejected.rollback) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, Absent propose, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, Absent propose, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, Absent propose, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, Absent propose, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, Absent propose, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, Absent propose, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, Absent propose, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, Absent propose, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, Absent propose, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, Absent propose, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, Absent propose, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, Absent propose, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, Absent propose, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, Absent propose, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, Absent propose, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, Absent propose, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, Absent propose, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, Absent propose, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, Absent propose, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, Absent propose, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, Absent propose, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, Absent propose, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, Absent propose, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, Absent propose, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Absent propose, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Absent propose, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Absent propose, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, D aPropose, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, D aPropose, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, D aPropose, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, D aPropose, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, D aPropose, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, D aPropose, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, D aPropose, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, D aPropose, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, D aPropose, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, D aPropose, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, D aPropose, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, D aPropose, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, D aPropose, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, D aPropose, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, D aPropose, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, D aPropose, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, D aPropose, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, D aPropose, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, D aPropose, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, D aPropose, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, D aPropose, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, D aPropose, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, D aPropose, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, D aPropose, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, D aPropose, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, D aPropose, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, D aPropose, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && $utils.checkEquals(aPropose, rejected.propose) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Absent originalData, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Absent originalData, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Absent originalData, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, D aOriginalData, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, D aOriginalData, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, D aOriginalData, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && $utils.checkEquals(aOriginalData, rejected.originalData) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Absent store, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Store aStore, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> $utils.checkEquals(aStore, rejected.store) && originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theValue);
        }
        
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, Function<Rejected<D>, TARGET> theAction) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theAction);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, Supplier<TARGET> theSupplier) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theSupplier);
        }
        public ChangeResultSwitchRejectedFailed<TARGET, D> rejectedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Predicate<D> proposeCheck, Predicate<D> rollbackCheck, Predicate<ChangeRejectedException> reasonCheck, TARGET theValue) {
            return rejected(rejected -> storeCheck.test(rejected.store) && originalDataCheck.test(rejected.originalData) && proposeCheck.test(rejected.propose) && rollbackCheck.test(rejected.rollback) && reasonCheck.test(rejected.reason), theValue);
        }
    }
    public static class ChangeResultSwitchFailed<TARGET, D extends Object> extends ChoiceTypeSwitch<ChangeResult<D>, TARGET> {
        private ChangeResultSwitchFailed(ChangeResult<D> theValue, Function<ChangeResult<D>, TARGET> theAction) { super(theValue, theAction); }
        
        public TARGET failed(Function<? super Failed<D>, TARGET> theAction) {
            Function<ChangeResult<D>, TARGET> oldAction = (Function<ChangeResult<D>, TARGET>)$action;
            Function<ChangeResult<D>, TARGET> newAction =
                ($action != null)
                ? oldAction : 
                    ($value instanceof Failed)
                    ? (Function<ChangeResult<D>, TARGET>)(d -> theAction.apply((Failed<D>)d))
                    : oldAction;
            
            return newAction.apply($value);
        }
        public TARGET failed(Supplier<TARGET> theSupplier) {
            return failed(d->theSupplier.get());
        }
        public TARGET failed(TARGET theValue) {
            return failed(d->theValue);
        }
        
        public ChangeResultSwitchFailed<TARGET, D> failed(Predicate<Failed<D>> check, Function<? super Failed<D>, TARGET> theAction) {
            Function<ChangeResult<D>, TARGET> oldAction = (Function<ChangeResult<D>, TARGET>)$action;
            Function<ChangeResult<D>, TARGET> newAction =
                ($action != null)
                ? oldAction : 
                    (($value instanceof Failed) && check.test((Failed<D>)$value))
                    ? (Function<ChangeResult<D>, TARGET>)(d -> theAction.apply((Failed<D>)d))
                    : oldAction;
            
            return new ChangeResultSwitchFailed<TARGET, D>($value, newAction);
        }
        public ChangeResultSwitchFailed<TARGET, D> failed(Predicate<Failed<D>> check, Supplier<TARGET> theSupplier) {
            return failed(check, d->theSupplier.get());
        }
        public ChangeResultSwitchFailed<TARGET, D> failed(Predicate<Failed<D>> check, TARGET theValue) {
            return failed(check, d->theValue);
        }
        
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Store aStore, Absent originalData, Absent problem, Function<Failed<D>, TARGET> theAction) {
            return failed(failed -> $utils.checkEquals(aStore, failed.store), theAction);
        }
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Store aStore, Absent originalData, Absent problem, Supplier<TARGET> theSupplier) {
            return failed(failed -> $utils.checkEquals(aStore, failed.store), theSupplier);
        }
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Store aStore, Absent originalData, Absent problem, TARGET theValue) {
            return failed(failed -> $utils.checkEquals(aStore, failed.store), theValue);
        }
        
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Predicate<Store> storeCheck, Absent originalData, Absent problem, Function<Failed<D>, TARGET> theAction) {
            return failed(failed -> storeCheck.test(failed.store), theAction);
        }
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Predicate<Store> storeCheck, Absent originalData, Absent problem, Supplier<TARGET> theSupplier) {
            return failed(failed -> storeCheck.test(failed.store), theSupplier);
        }
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Predicate<Store> storeCheck, Absent originalData, Absent problem, TARGET theValue) {
            return failed(failed -> storeCheck.test(failed.store), theValue);
        }
        
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Absent store, D aOriginalData, Absent problem, Function<Failed<D>, TARGET> theAction) {
            return failed(failed -> $utils.checkEquals(aOriginalData, failed.originalData), theAction);
        }
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Absent store, D aOriginalData, Absent problem, Supplier<TARGET> theSupplier) {
            return failed(failed -> $utils.checkEquals(aOriginalData, failed.originalData), theSupplier);
        }
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Absent store, D aOriginalData, Absent problem, TARGET theValue) {
            return failed(failed -> $utils.checkEquals(aOriginalData, failed.originalData), theValue);
        }
        
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Store aStore, D aOriginalData, Absent problem, Function<Failed<D>, TARGET> theAction) {
            return failed(failed -> $utils.checkEquals(aStore, failed.store) && $utils.checkEquals(aOriginalData, failed.originalData), theAction);
        }
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Store aStore, D aOriginalData, Absent problem, Supplier<TARGET> theSupplier) {
            return failed(failed -> $utils.checkEquals(aStore, failed.store) && $utils.checkEquals(aOriginalData, failed.originalData), theSupplier);
        }
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Store aStore, D aOriginalData, Absent problem, TARGET theValue) {
            return failed(failed -> $utils.checkEquals(aStore, failed.store) && $utils.checkEquals(aOriginalData, failed.originalData), theValue);
        }
        
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Predicate<Store> storeCheck, D aOriginalData, Absent problem, Function<Failed<D>, TARGET> theAction) {
            return failed(failed -> storeCheck.test(failed.store) && $utils.checkEquals(aOriginalData, failed.originalData), theAction);
        }
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Predicate<Store> storeCheck, D aOriginalData, Absent problem, Supplier<TARGET> theSupplier) {
            return failed(failed -> storeCheck.test(failed.store) && $utils.checkEquals(aOriginalData, failed.originalData), theSupplier);
        }
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Predicate<Store> storeCheck, D aOriginalData, Absent problem, TARGET theValue) {
            return failed(failed -> storeCheck.test(failed.store) && $utils.checkEquals(aOriginalData, failed.originalData), theValue);
        }
        
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Absent store, Predicate<D> originalDataCheck, Absent problem, Function<Failed<D>, TARGET> theAction) {
            return failed(failed -> originalDataCheck.test(failed.originalData), theAction);
        }
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Absent store, Predicate<D> originalDataCheck, Absent problem, Supplier<TARGET> theSupplier) {
            return failed(failed -> originalDataCheck.test(failed.originalData), theSupplier);
        }
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Absent store, Predicate<D> originalDataCheck, Absent problem, TARGET theValue) {
            return failed(failed -> originalDataCheck.test(failed.originalData), theValue);
        }
        
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Store aStore, Predicate<D> originalDataCheck, Absent problem, Function<Failed<D>, TARGET> theAction) {
            return failed(failed -> $utils.checkEquals(aStore, failed.store) && originalDataCheck.test(failed.originalData), theAction);
        }
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Store aStore, Predicate<D> originalDataCheck, Absent problem, Supplier<TARGET> theSupplier) {
            return failed(failed -> $utils.checkEquals(aStore, failed.store) && originalDataCheck.test(failed.originalData), theSupplier);
        }
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Store aStore, Predicate<D> originalDataCheck, Absent problem, TARGET theValue) {
            return failed(failed -> $utils.checkEquals(aStore, failed.store) && originalDataCheck.test(failed.originalData), theValue);
        }
        
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Absent problem, Function<Failed<D>, TARGET> theAction) {
            return failed(failed -> storeCheck.test(failed.store) && originalDataCheck.test(failed.originalData), theAction);
        }
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Absent problem, Supplier<TARGET> theSupplier) {
            return failed(failed -> storeCheck.test(failed.store) && originalDataCheck.test(failed.originalData), theSupplier);
        }
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Absent problem, TARGET theValue) {
            return failed(failed -> storeCheck.test(failed.store) && originalDataCheck.test(failed.originalData), theValue);
        }
        
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Absent store, Absent originalData, ChangeFailException aProblem, Function<Failed<D>, TARGET> theAction) {
            return failed(failed -> $utils.checkEquals(aProblem, failed.problem), theAction);
        }
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Absent store, Absent originalData, ChangeFailException aProblem, Supplier<TARGET> theSupplier) {
            return failed(failed -> $utils.checkEquals(aProblem, failed.problem), theSupplier);
        }
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Absent store, Absent originalData, ChangeFailException aProblem, TARGET theValue) {
            return failed(failed -> $utils.checkEquals(aProblem, failed.problem), theValue);
        }
        
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Store aStore, Absent originalData, ChangeFailException aProblem, Function<Failed<D>, TARGET> theAction) {
            return failed(failed -> $utils.checkEquals(aStore, failed.store) && $utils.checkEquals(aProblem, failed.problem), theAction);
        }
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Store aStore, Absent originalData, ChangeFailException aProblem, Supplier<TARGET> theSupplier) {
            return failed(failed -> $utils.checkEquals(aStore, failed.store) && $utils.checkEquals(aProblem, failed.problem), theSupplier);
        }
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Store aStore, Absent originalData, ChangeFailException aProblem, TARGET theValue) {
            return failed(failed -> $utils.checkEquals(aStore, failed.store) && $utils.checkEquals(aProblem, failed.problem), theValue);
        }
        
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Predicate<Store> storeCheck, Absent originalData, ChangeFailException aProblem, Function<Failed<D>, TARGET> theAction) {
            return failed(failed -> storeCheck.test(failed.store) && $utils.checkEquals(aProblem, failed.problem), theAction);
        }
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Predicate<Store> storeCheck, Absent originalData, ChangeFailException aProblem, Supplier<TARGET> theSupplier) {
            return failed(failed -> storeCheck.test(failed.store) && $utils.checkEquals(aProblem, failed.problem), theSupplier);
        }
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Predicate<Store> storeCheck, Absent originalData, ChangeFailException aProblem, TARGET theValue) {
            return failed(failed -> storeCheck.test(failed.store) && $utils.checkEquals(aProblem, failed.problem), theValue);
        }
        
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Absent store, D aOriginalData, ChangeFailException aProblem, Function<Failed<D>, TARGET> theAction) {
            return failed(failed -> $utils.checkEquals(aOriginalData, failed.originalData) && $utils.checkEquals(aProblem, failed.problem), theAction);
        }
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Absent store, D aOriginalData, ChangeFailException aProblem, Supplier<TARGET> theSupplier) {
            return failed(failed -> $utils.checkEquals(aOriginalData, failed.originalData) && $utils.checkEquals(aProblem, failed.problem), theSupplier);
        }
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Absent store, D aOriginalData, ChangeFailException aProblem, TARGET theValue) {
            return failed(failed -> $utils.checkEquals(aOriginalData, failed.originalData) && $utils.checkEquals(aProblem, failed.problem), theValue);
        }
        
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Store aStore, D aOriginalData, ChangeFailException aProblem, Function<Failed<D>, TARGET> theAction) {
            return failed(failed -> $utils.checkEquals(aStore, failed.store) && $utils.checkEquals(aOriginalData, failed.originalData) && $utils.checkEquals(aProblem, failed.problem), theAction);
        }
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Store aStore, D aOriginalData, ChangeFailException aProblem, Supplier<TARGET> theSupplier) {
            return failed(failed -> $utils.checkEquals(aStore, failed.store) && $utils.checkEquals(aOriginalData, failed.originalData) && $utils.checkEquals(aProblem, failed.problem), theSupplier);
        }
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Store aStore, D aOriginalData, ChangeFailException aProblem, TARGET theValue) {
            return failed(failed -> $utils.checkEquals(aStore, failed.store) && $utils.checkEquals(aOriginalData, failed.originalData) && $utils.checkEquals(aProblem, failed.problem), theValue);
        }
        
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Predicate<Store> storeCheck, D aOriginalData, ChangeFailException aProblem, Function<Failed<D>, TARGET> theAction) {
            return failed(failed -> storeCheck.test(failed.store) && $utils.checkEquals(aOriginalData, failed.originalData) && $utils.checkEquals(aProblem, failed.problem), theAction);
        }
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Predicate<Store> storeCheck, D aOriginalData, ChangeFailException aProblem, Supplier<TARGET> theSupplier) {
            return failed(failed -> storeCheck.test(failed.store) && $utils.checkEquals(aOriginalData, failed.originalData) && $utils.checkEquals(aProblem, failed.problem), theSupplier);
        }
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Predicate<Store> storeCheck, D aOriginalData, ChangeFailException aProblem, TARGET theValue) {
            return failed(failed -> storeCheck.test(failed.store) && $utils.checkEquals(aOriginalData, failed.originalData) && $utils.checkEquals(aProblem, failed.problem), theValue);
        }
        
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Absent store, Predicate<D> originalDataCheck, ChangeFailException aProblem, Function<Failed<D>, TARGET> theAction) {
            return failed(failed -> originalDataCheck.test(failed.originalData) && $utils.checkEquals(aProblem, failed.problem), theAction);
        }
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Absent store, Predicate<D> originalDataCheck, ChangeFailException aProblem, Supplier<TARGET> theSupplier) {
            return failed(failed -> originalDataCheck.test(failed.originalData) && $utils.checkEquals(aProblem, failed.problem), theSupplier);
        }
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Absent store, Predicate<D> originalDataCheck, ChangeFailException aProblem, TARGET theValue) {
            return failed(failed -> originalDataCheck.test(failed.originalData) && $utils.checkEquals(aProblem, failed.problem), theValue);
        }
        
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Store aStore, Predicate<D> originalDataCheck, ChangeFailException aProblem, Function<Failed<D>, TARGET> theAction) {
            return failed(failed -> $utils.checkEquals(aStore, failed.store) && originalDataCheck.test(failed.originalData) && $utils.checkEquals(aProblem, failed.problem), theAction);
        }
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Store aStore, Predicate<D> originalDataCheck, ChangeFailException aProblem, Supplier<TARGET> theSupplier) {
            return failed(failed -> $utils.checkEquals(aStore, failed.store) && originalDataCheck.test(failed.originalData) && $utils.checkEquals(aProblem, failed.problem), theSupplier);
        }
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Store aStore, Predicate<D> originalDataCheck, ChangeFailException aProblem, TARGET theValue) {
            return failed(failed -> $utils.checkEquals(aStore, failed.store) && originalDataCheck.test(failed.originalData) && $utils.checkEquals(aProblem, failed.problem), theValue);
        }
        
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, ChangeFailException aProblem, Function<Failed<D>, TARGET> theAction) {
            return failed(failed -> storeCheck.test(failed.store) && originalDataCheck.test(failed.originalData) && $utils.checkEquals(aProblem, failed.problem), theAction);
        }
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, ChangeFailException aProblem, Supplier<TARGET> theSupplier) {
            return failed(failed -> storeCheck.test(failed.store) && originalDataCheck.test(failed.originalData) && $utils.checkEquals(aProblem, failed.problem), theSupplier);
        }
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, ChangeFailException aProblem, TARGET theValue) {
            return failed(failed -> storeCheck.test(failed.store) && originalDataCheck.test(failed.originalData) && $utils.checkEquals(aProblem, failed.problem), theValue);
        }
        
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Absent store, Absent originalData, Predicate<ChangeFailException> problemCheck, Function<Failed<D>, TARGET> theAction) {
            return failed(failed -> problemCheck.test(failed.problem), theAction);
        }
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Absent store, Absent originalData, Predicate<ChangeFailException> problemCheck, Supplier<TARGET> theSupplier) {
            return failed(failed -> problemCheck.test(failed.problem), theSupplier);
        }
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Absent store, Absent originalData, Predicate<ChangeFailException> problemCheck, TARGET theValue) {
            return failed(failed -> problemCheck.test(failed.problem), theValue);
        }
        
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Store aStore, Absent originalData, Predicate<ChangeFailException> problemCheck, Function<Failed<D>, TARGET> theAction) {
            return failed(failed -> $utils.checkEquals(aStore, failed.store) && problemCheck.test(failed.problem), theAction);
        }
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Store aStore, Absent originalData, Predicate<ChangeFailException> problemCheck, Supplier<TARGET> theSupplier) {
            return failed(failed -> $utils.checkEquals(aStore, failed.store) && problemCheck.test(failed.problem), theSupplier);
        }
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Store aStore, Absent originalData, Predicate<ChangeFailException> problemCheck, TARGET theValue) {
            return failed(failed -> $utils.checkEquals(aStore, failed.store) && problemCheck.test(failed.problem), theValue);
        }
        
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Predicate<Store> storeCheck, Absent originalData, Predicate<ChangeFailException> problemCheck, Function<Failed<D>, TARGET> theAction) {
            return failed(failed -> storeCheck.test(failed.store) && problemCheck.test(failed.problem), theAction);
        }
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Predicate<Store> storeCheck, Absent originalData, Predicate<ChangeFailException> problemCheck, Supplier<TARGET> theSupplier) {
            return failed(failed -> storeCheck.test(failed.store) && problemCheck.test(failed.problem), theSupplier);
        }
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Predicate<Store> storeCheck, Absent originalData, Predicate<ChangeFailException> problemCheck, TARGET theValue) {
            return failed(failed -> storeCheck.test(failed.store) && problemCheck.test(failed.problem), theValue);
        }
        
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Absent store, D aOriginalData, Predicate<ChangeFailException> problemCheck, Function<Failed<D>, TARGET> theAction) {
            return failed(failed -> $utils.checkEquals(aOriginalData, failed.originalData) && problemCheck.test(failed.problem), theAction);
        }
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Absent store, D aOriginalData, Predicate<ChangeFailException> problemCheck, Supplier<TARGET> theSupplier) {
            return failed(failed -> $utils.checkEquals(aOriginalData, failed.originalData) && problemCheck.test(failed.problem), theSupplier);
        }
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Absent store, D aOriginalData, Predicate<ChangeFailException> problemCheck, TARGET theValue) {
            return failed(failed -> $utils.checkEquals(aOriginalData, failed.originalData) && problemCheck.test(failed.problem), theValue);
        }
        
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Store aStore, D aOriginalData, Predicate<ChangeFailException> problemCheck, Function<Failed<D>, TARGET> theAction) {
            return failed(failed -> $utils.checkEquals(aStore, failed.store) && $utils.checkEquals(aOriginalData, failed.originalData) && problemCheck.test(failed.problem), theAction);
        }
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Store aStore, D aOriginalData, Predicate<ChangeFailException> problemCheck, Supplier<TARGET> theSupplier) {
            return failed(failed -> $utils.checkEquals(aStore, failed.store) && $utils.checkEquals(aOriginalData, failed.originalData) && problemCheck.test(failed.problem), theSupplier);
        }
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Store aStore, D aOriginalData, Predicate<ChangeFailException> problemCheck, TARGET theValue) {
            return failed(failed -> $utils.checkEquals(aStore, failed.store) && $utils.checkEquals(aOriginalData, failed.originalData) && problemCheck.test(failed.problem), theValue);
        }
        
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Predicate<Store> storeCheck, D aOriginalData, Predicate<ChangeFailException> problemCheck, Function<Failed<D>, TARGET> theAction) {
            return failed(failed -> storeCheck.test(failed.store) && $utils.checkEquals(aOriginalData, failed.originalData) && problemCheck.test(failed.problem), theAction);
        }
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Predicate<Store> storeCheck, D aOriginalData, Predicate<ChangeFailException> problemCheck, Supplier<TARGET> theSupplier) {
            return failed(failed -> storeCheck.test(failed.store) && $utils.checkEquals(aOriginalData, failed.originalData) && problemCheck.test(failed.problem), theSupplier);
        }
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Predicate<Store> storeCheck, D aOriginalData, Predicate<ChangeFailException> problemCheck, TARGET theValue) {
            return failed(failed -> storeCheck.test(failed.store) && $utils.checkEquals(aOriginalData, failed.originalData) && problemCheck.test(failed.problem), theValue);
        }
        
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Absent store, Predicate<D> originalDataCheck, Predicate<ChangeFailException> problemCheck, Function<Failed<D>, TARGET> theAction) {
            return failed(failed -> originalDataCheck.test(failed.originalData) && problemCheck.test(failed.problem), theAction);
        }
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Absent store, Predicate<D> originalDataCheck, Predicate<ChangeFailException> problemCheck, Supplier<TARGET> theSupplier) {
            return failed(failed -> originalDataCheck.test(failed.originalData) && problemCheck.test(failed.problem), theSupplier);
        }
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Absent store, Predicate<D> originalDataCheck, Predicate<ChangeFailException> problemCheck, TARGET theValue) {
            return failed(failed -> originalDataCheck.test(failed.originalData) && problemCheck.test(failed.problem), theValue);
        }
        
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Store aStore, Predicate<D> originalDataCheck, Predicate<ChangeFailException> problemCheck, Function<Failed<D>, TARGET> theAction) {
            return failed(failed -> $utils.checkEquals(aStore, failed.store) && originalDataCheck.test(failed.originalData) && problemCheck.test(failed.problem), theAction);
        }
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Store aStore, Predicate<D> originalDataCheck, Predicate<ChangeFailException> problemCheck, Supplier<TARGET> theSupplier) {
            return failed(failed -> $utils.checkEquals(aStore, failed.store) && originalDataCheck.test(failed.originalData) && problemCheck.test(failed.problem), theSupplier);
        }
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Store aStore, Predicate<D> originalDataCheck, Predicate<ChangeFailException> problemCheck, TARGET theValue) {
            return failed(failed -> $utils.checkEquals(aStore, failed.store) && originalDataCheck.test(failed.originalData) && problemCheck.test(failed.problem), theValue);
        }
        
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Predicate<ChangeFailException> problemCheck, Function<Failed<D>, TARGET> theAction) {
            return failed(failed -> storeCheck.test(failed.store) && originalDataCheck.test(failed.originalData) && problemCheck.test(failed.problem), theAction);
        }
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Predicate<ChangeFailException> problemCheck, Supplier<TARGET> theSupplier) {
            return failed(failed -> storeCheck.test(failed.store) && originalDataCheck.test(failed.originalData) && problemCheck.test(failed.problem), theSupplier);
        }
        public ChangeResultSwitchFailed<TARGET, D> failedOf(Predicate<Store> storeCheck, Predicate<D> originalDataCheck, Predicate<ChangeFailException> problemCheck, TARGET theValue) {
            return failed(failed -> storeCheck.test(failed.store) && originalDataCheck.test(failed.originalData) && problemCheck.test(failed.problem), theValue);
        }
    }
    
    
    
}

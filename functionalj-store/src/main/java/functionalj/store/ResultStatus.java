package functionalj.store;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import functionalj.lens.core.LensSpec;
import functionalj.lens.lenses.BooleanAccessPrimitive;
import functionalj.lens.lenses.ObjectLens;
import functionalj.lens.lenses.ObjectLensImpl;
import functionalj.lens.lenses.ResultAccess;
import functionalj.pipeable.Pipeable;
import functionalj.result.Result;
import functionalj.types.Type;
import functionalj.types.choice.ChoiceTypeSwitch;
import functionalj.types.choice.IChoice;

// functionalj.store.Specs.ResultStatusSpec

@SuppressWarnings({"rawtypes", "unchecked"})
public abstract class ResultStatus<D extends Object> implements IChoice<ResultStatus.ResultStatusFirstSwitch<D>>, Pipeable<ResultStatus<D>> {
    
    public static final <D extends Object> NotAllowed<D> NotAllowed(ChangeNotAllowedException reason) {
        return new NotAllowed<D>(reason);
    }
    public static final <D extends Object> Accepted<D> Accepted(D newData) {
        return new Accepted<D>(newData);
    }
    public static final <D extends Object> Adjusted<D> Adjusted(D proposedData, D adjustedData) {
        return new Adjusted<D>(proposedData, adjustedData);
    }
    public static final <D extends Object> Rejected<D> Rejected(D propose, D rollback, ChangeRejectedException reason) {
        return new Rejected<D>(propose, rollback, reason);
    }
    public static final <D extends Object> Failed<D> Failed(ChangeFailException problem) {
        return new Failed<D>(problem);
    }
    
    
    public static final ResultStatusLens<ResultStatus> theResultStatus = new ResultStatusLens<>(LensSpec.of(ResultStatus.class));
    public static class ResultStatusLens<HOST> extends ObjectLensImpl<HOST, ResultStatus> {

        public final BooleanAccessPrimitive<ResultStatus> isNotAllowed = ResultStatus::isNotAllowed;
        public final BooleanAccessPrimitive<ResultStatus> isAccepted = ResultStatus::isAccepted;
        public final BooleanAccessPrimitive<ResultStatus> isAdjusted = ResultStatus::isAdjusted;
        public final BooleanAccessPrimitive<ResultStatus> isRejected = ResultStatus::isRejected;
        public final BooleanAccessPrimitive<ResultStatus> isFailed = ResultStatus::isFailed;
        public final ResultAccess<HOST, NotAllowed, NotAllowed.NotAllowedLens<HOST>> asNotAllowed = createSubResultLens(ResultStatus::asNotAllowed, null, spec -> new NotAllowed.NotAllowedLens(spec));
        public final ResultAccess<HOST, Accepted, Accepted.AcceptedLens<HOST>> asAccepted = createSubResultLens(ResultStatus::asAccepted, null, spec -> new Accepted.AcceptedLens(spec));
        public final ResultAccess<HOST, Adjusted, Adjusted.AdjustedLens<HOST>> asAdjusted = createSubResultLens(ResultStatus::asAdjusted, null, spec -> new Adjusted.AdjustedLens(spec));
        public final ResultAccess<HOST, Rejected, Rejected.RejectedLens<HOST>> asRejected = createSubResultLens(ResultStatus::asRejected, null, spec -> new Rejected.RejectedLens(spec));
        public final ResultAccess<HOST, Failed, Failed.FailedLens<HOST>> asFailed = createSubResultLens(ResultStatus::asFailed, null, spec -> new Failed.FailedLens(spec));
        public ResultStatusLens(LensSpec<HOST, ResultStatus> spec) {
            super(spec);
        }
    }
    
    private ResultStatus() {}
    public ResultStatus<D> __data() throws Exception { return this; }
    public Result<ResultStatus<D>> toResult() { return Result.valueOf(this); }
    
    public static <T extends ResultStatus> T fromMap(java.util.Map<String, Object> map) {
        String __tagged = (String)map.get("__tagged");
        if ("NotAllowed".equals(__tagged))
            return (T)NotAllowed.caseFromMap(map);
        if ("Accepted".equals(__tagged))
            return (T)Accepted.caseFromMap(map);
        if ("Adjusted".equals(__tagged))
            return (T)Adjusted.caseFromMap(map);
        if ("Rejected".equals(__tagged))
            return (T)Rejected.caseFromMap(map);
        if ("Failed".equals(__tagged))
            return (T)Failed.caseFromMap(map);
        throw new IllegalArgumentException("Tagged value does not represent a valid type: " + __tagged);
    }
    
    public java.util.Map<String, java.util.Map<String, functionalj.types.choice.generator.model.CaseParam>> __getSchema() {
        return getChoiceSchema();
    }
    
    static private functionalj.map.FuncMap<String, java.util.Map<String, functionalj.types.choice.generator.model.CaseParam>> __schema__ = functionalj.map.FuncMap.<String, java.util.Map<String, functionalj.types.choice.generator.model.CaseParam>>newMap()
        .with("NotAllowed", NotAllowed.getCaseSchema())
        .with("Accepted", Accepted.getCaseSchema())
        .with("Adjusted", Adjusted.getCaseSchema())
        .with("Rejected", Rejected.getCaseSchema())
        .with("Failed", Failed.getCaseSchema())
        .build();
    public static java.util.Map<String, java.util.Map<String, functionalj.types.choice.generator.model.CaseParam>> getChoiceSchema() {
        return __schema__;
    }
    
    public static final class NotAllowed<D extends Object> extends ResultStatus<D> {
        public static final NotAllowed.NotAllowedLens<NotAllowed> theNotAllowed = new NotAllowed.NotAllowedLens<>(LensSpec.of(NotAllowed.class));
        private ChangeNotAllowedException reason;
        private NotAllowed(ChangeNotAllowedException reason) {
            this.reason = reason;
        }
        public ChangeNotAllowedException reason() { return reason; }
        public NotAllowed<D> withReason(ChangeNotAllowedException reason) { return new NotAllowed<D>(reason); }
        public static class NotAllowedLens<HOST> extends ObjectLensImpl<HOST, ResultStatus.NotAllowed> {
            
            public final ObjectLens<HOST, Object> reason = (ObjectLens)createSubLens(ResultStatus.NotAllowed::reason, ResultStatus.NotAllowed::withReason, ObjectLens::of);
            
            public NotAllowedLens(LensSpec<HOST, ResultStatus.NotAllowed> spec) {
                super(spec);
            }
            
        }
        public java.util.Map<String, Object> __toMap() {
            java.util.Map<String, Object> map = new java.util.HashMap<>();
            map.put("__tagged", functionalj.types.IData.$utils.toMapValueObject("NotAllowed"));
            map.put("reason", this.reason);
            return map;
        }
        static private functionalj.map.FuncMap<String, functionalj.types.choice.generator.model.CaseParam> __schema__ = functionalj.map.FuncMap.<String, functionalj.types.choice.generator.model.CaseParam>newMap()
            .with("reason", new functionalj.types.choice.generator.model.CaseParam("reason", new Type("functionalj.store", null, "ChangeNotAllowedException", java.util.Collections.emptyList()), true, null))
            .build();
        public static java.util.Map<String, functionalj.types.choice.generator.model.CaseParam> getCaseSchema() {
            return __schema__;
        }
        public static NotAllowed caseFromMap(java.util.Map<String, Object> map) {
            return NotAllowed(
                $utils.extractPropertyFromMap(NotAllowed.class, ChangeNotAllowedException.class, map, __schema__, "reason")
            );
        }
    }
    public static final class Accepted<D extends Object> extends ResultStatus<D> {
        public static final Accepted.AcceptedLens<Accepted> theAccepted = new Accepted.AcceptedLens<>(LensSpec.of(Accepted.class));
        private D newData;
        private Accepted(D newData) {
            this.newData = newData;
        }
        public D newData() { return newData; }
        public Accepted<D> withNewData(D newData) { return new Accepted<D>(newData); }
        public static class AcceptedLens<HOST> extends ObjectLensImpl<HOST, ResultStatus.Accepted> {
            
            public final ObjectLens<HOST, Object> newData = (ObjectLens)createSubLens(ResultStatus.Accepted::newData, ResultStatus.Accepted::withNewData, ObjectLens::of);
            
            public AcceptedLens(LensSpec<HOST, ResultStatus.Accepted> spec) {
                super(spec);
            }
            
        }
        public java.util.Map<String, Object> __toMap() {
            java.util.Map<String, Object> map = new java.util.HashMap<>();
            map.put("__tagged", functionalj.types.IData.$utils.toMapValueObject("Accepted"));
            map.put("newData", this.newData);
            return map;
        }
        static private functionalj.map.FuncMap<String, functionalj.types.choice.generator.model.CaseParam> __schema__ = functionalj.map.FuncMap.<String, functionalj.types.choice.generator.model.CaseParam>newMap()
            .with("newData", new functionalj.types.choice.generator.model.CaseParam("newData", new Type(null, null, "D", java.util.Collections.emptyList()), true, null))
            .build();
        public static java.util.Map<String, functionalj.types.choice.generator.model.CaseParam> getCaseSchema() {
            return __schema__;
        }
        public static Accepted caseFromMap(java.util.Map<String, Object> map) {
            return Accepted(
                $utils.extractPropertyFromMap(Accepted.class, Object.class, map, __schema__, "newData")
            );
        }
    }
    public static final class Adjusted<D extends Object> extends ResultStatus<D> {
        public static final Adjusted.AdjustedLens<Adjusted> theAdjusted = new Adjusted.AdjustedLens<>(LensSpec.of(Adjusted.class));
        private D proposedData;
        private D adjustedData;
        private Adjusted(D proposedData, D adjustedData) {
            this.proposedData = proposedData;
            this.adjustedData = adjustedData;
        }
        public D proposedData() { return proposedData; }
        public D adjustedData() { return adjustedData; }
        public Adjusted<D> withProposedData(D proposedData) { return new Adjusted<D>(proposedData, adjustedData); }
        public Adjusted<D> withAdjustedData(D adjustedData) { return new Adjusted<D>(proposedData, adjustedData); }
        public static class AdjustedLens<HOST> extends ObjectLensImpl<HOST, ResultStatus.Adjusted> {
            
            public final ObjectLens<HOST, Object> proposedData = (ObjectLens)createSubLens(ResultStatus.Adjusted::proposedData, ResultStatus.Adjusted::withProposedData, ObjectLens::of);
            public final ObjectLens<HOST, Object> adjustedData = (ObjectLens)createSubLens(ResultStatus.Adjusted::adjustedData, ResultStatus.Adjusted::withAdjustedData, ObjectLens::of);
            
            public AdjustedLens(LensSpec<HOST, ResultStatus.Adjusted> spec) {
                super(spec);
            }
            
        }
        public java.util.Map<String, Object> __toMap() {
            java.util.Map<String, Object> map = new java.util.HashMap<>();
            map.put("__tagged", functionalj.types.IData.$utils.toMapValueObject("Adjusted"));
            map.put("proposedData", this.proposedData);
            map.put("adjustedData", this.adjustedData);
            return map;
        }
        static private functionalj.map.FuncMap<String, functionalj.types.choice.generator.model.CaseParam> __schema__ = functionalj.map.FuncMap.<String, functionalj.types.choice.generator.model.CaseParam>newMap()
            .with("proposedData", new functionalj.types.choice.generator.model.CaseParam("proposedData", new Type(null, null, "D", java.util.Collections.emptyList()), true, null))
            .with("adjustedData", new functionalj.types.choice.generator.model.CaseParam("adjustedData", new Type(null, null, "D", java.util.Collections.emptyList()), true, null))
            .build();
        public static java.util.Map<String, functionalj.types.choice.generator.model.CaseParam> getCaseSchema() {
            return __schema__;
        }
        public static Adjusted caseFromMap(java.util.Map<String, Object> map) {
            return Adjusted(
                $utils.extractPropertyFromMap(Adjusted.class, Object.class, map, __schema__, "proposedData"),
                $utils.extractPropertyFromMap(Adjusted.class, Object.class, map, __schema__, "adjustedData")
            );
        }
    }
    public static final class Rejected<D extends Object> extends ResultStatus<D> {
        public static final Rejected.RejectedLens<Rejected> theRejected = new Rejected.RejectedLens<>(LensSpec.of(Rejected.class));
        private D propose;
        private D rollback;
        private ChangeRejectedException reason;
        private Rejected(D propose, D rollback, ChangeRejectedException reason) {
            this.propose = propose;
            this.rollback = rollback;
            this.reason = reason;
        }
        public D propose() { return propose; }
        public D rollback() { return rollback; }
        public ChangeRejectedException reason() { return reason; }
        public Rejected<D> withPropose(D propose) { return new Rejected<D>(propose, rollback, reason); }
        public Rejected<D> withRollback(D rollback) { return new Rejected<D>(propose, rollback, reason); }
        public Rejected<D> withReason(ChangeRejectedException reason) { return new Rejected<D>(propose, rollback, reason); }
        public static class RejectedLens<HOST> extends ObjectLensImpl<HOST, ResultStatus.Rejected> {
            
            public final ObjectLens<HOST, Object> propose = (ObjectLens)createSubLens(ResultStatus.Rejected::propose, ResultStatus.Rejected::withPropose, ObjectLens::of);
            public final ObjectLens<HOST, Object> rollback = (ObjectLens)createSubLens(ResultStatus.Rejected::rollback, ResultStatus.Rejected::withRollback, ObjectLens::of);
            public final ObjectLens<HOST, Object> reason = (ObjectLens)createSubLens(ResultStatus.Rejected::reason, ResultStatus.Rejected::withReason, ObjectLens::of);
            
            public RejectedLens(LensSpec<HOST, ResultStatus.Rejected> spec) {
                super(spec);
            }
            
        }
        public java.util.Map<String, Object> __toMap() {
            java.util.Map<String, Object> map = new java.util.HashMap<>();
            map.put("__tagged", functionalj.types.IData.$utils.toMapValueObject("Rejected"));
            map.put("propose", this.propose);
            map.put("rollback", this.rollback);
            map.put("reason", this.reason);
            return map;
        }
        static private functionalj.map.FuncMap<String, functionalj.types.choice.generator.model.CaseParam> __schema__ = functionalj.map.FuncMap.<String, functionalj.types.choice.generator.model.CaseParam>newMap()
            .with("propose", new functionalj.types.choice.generator.model.CaseParam("propose", new Type(null, null, "D", java.util.Collections.emptyList()), true, null))
            .with("rollback", new functionalj.types.choice.generator.model.CaseParam("rollback", new Type(null, null, "D", java.util.Collections.emptyList()), true, null))
            .with("reason", new functionalj.types.choice.generator.model.CaseParam("reason", new Type("functionalj.store", null, "ChangeRejectedException", java.util.Collections.emptyList()), true, null))
            .build();
        public static java.util.Map<String, functionalj.types.choice.generator.model.CaseParam> getCaseSchema() {
            return __schema__;
        }
        public static Rejected caseFromMap(java.util.Map<String, Object> map) {
            return Rejected(
                $utils.extractPropertyFromMap(Rejected.class, Object.class,                  map, __schema__, "propose"),
                $utils.extractPropertyFromMap(Rejected.class, Object.class,                  map, __schema__, "rollback"),
                $utils.extractPropertyFromMap(Rejected.class, ChangeRejectedException.class, map, __schema__, "reason")
            );
        }
    }
    public static final class Failed<D extends Object> extends ResultStatus<D> {
        public static final Failed.FailedLens<Failed> theFailed = new Failed.FailedLens<>(LensSpec.of(Failed.class));
        private ChangeFailException problem;
        private Failed(ChangeFailException problem) {
            this.problem = problem;
        }
        public ChangeFailException problem() { return problem; }
        public Failed<D> withProblem(ChangeFailException problem) { return new Failed<D>(problem); }
        public static class FailedLens<HOST> extends ObjectLensImpl<HOST, ResultStatus.Failed> {
            
            public final ObjectLens<HOST, Object> problem = (ObjectLens)createSubLens(ResultStatus.Failed::problem, ResultStatus.Failed::withProblem, ObjectLens::of);
            
            public FailedLens(LensSpec<HOST, ResultStatus.Failed> spec) {
                super(spec);
            }
            
        }
        public java.util.Map<String, Object> __toMap() {
            java.util.Map<String, Object> map = new java.util.HashMap<>();
            map.put("__tagged", functionalj.types.IData.$utils.toMapValueObject("Failed"));
            map.put("problem", this.problem);
            return map;
        }
        static private functionalj.map.FuncMap<String, functionalj.types.choice.generator.model.CaseParam> __schema__ = functionalj.map.FuncMap.<String, functionalj.types.choice.generator.model.CaseParam>newMap()
            .with("problem", new functionalj.types.choice.generator.model.CaseParam("problem", new Type("functionalj.store", null, "ChangeFailException", java.util.Collections.emptyList()), true, null))
            .build();
        public static java.util.Map<String, functionalj.types.choice.generator.model.CaseParam> getCaseSchema() {
            return __schema__;
        }
        public static Failed caseFromMap(java.util.Map<String, Object> map) {
            return Failed(
                $utils.extractPropertyFromMap(Failed.class, ChangeFailException.class, map, __schema__, "problem")
            );
        }
    }
    
    private final ResultStatusFirstSwitch<D> __switch = new ResultStatusFirstSwitch<D>(this);
    @Override public ResultStatusFirstSwitch<D> match() {
         return __switch;
    }
    
    private volatile String toString = null;
    @Override
    public String toString() {
        if (toString != null)
            return toString;
        synchronized(this) {
            if (toString != null)
                return toString;
            toString = $utils.Match(this)
                    .notAllowed(notAllowed -> "NotAllowed(" + String.format("%1$s", notAllowed.reason) + ")")
                    .accepted(accepted -> "Accepted(" + String.format("%1$s", accepted.newData) + ")")
                    .adjusted(adjusted -> "Adjusted(" + String.format("%1$s,%2$s", adjusted.proposedData,adjusted.adjustedData) + ")")
                    .rejected(rejected -> "Rejected(" + String.format("%1$s,%2$s,%3$s", rejected.propose,rejected.rollback,rejected.reason) + ")")
                    .failed(failed -> "Failed(" + String.format("%1$s", failed.problem) + ")")
            ;
            return toString;
        }
    }
    
    @Override
    public int hashCode() {
        return toString().hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ResultStatus))
            return false;
        
        if (this == obj)
            return true;
        
        String objToString  = obj.toString();
        String thisToString = this.toString();
        return thisToString.equals(objToString);
    }
    
    
    public boolean isNotAllowed() { return this instanceof NotAllowed; }
    public Result<NotAllowed<D>> asNotAllowed() { return Result.valueOf(this).filter(NotAllowed.class).map(NotAllowed.class::cast); }
    public ResultStatus<D> ifNotAllowed(Consumer<NotAllowed<D>> action) { if (isNotAllowed()) action.accept((NotAllowed<D>)this); return this; }
    public ResultStatus<D> ifNotAllowed(Runnable action) { if (isNotAllowed()) action.run(); return this; }
    public boolean isAccepted() { return this instanceof Accepted; }
    public Result<Accepted<D>> asAccepted() { return Result.valueOf(this).filter(Accepted.class).map(Accepted.class::cast); }
    public ResultStatus<D> ifAccepted(Consumer<Accepted<D>> action) { if (isAccepted()) action.accept((Accepted<D>)this); return this; }
    public ResultStatus<D> ifAccepted(Runnable action) { if (isAccepted()) action.run(); return this; }
    public boolean isAdjusted() { return this instanceof Adjusted; }
    public Result<Adjusted<D>> asAdjusted() { return Result.valueOf(this).filter(Adjusted.class).map(Adjusted.class::cast); }
    public ResultStatus<D> ifAdjusted(Consumer<Adjusted<D>> action) { if (isAdjusted()) action.accept((Adjusted<D>)this); return this; }
    public ResultStatus<D> ifAdjusted(Runnable action) { if (isAdjusted()) action.run(); return this; }
    public boolean isRejected() { return this instanceof Rejected; }
    public Result<Rejected<D>> asRejected() { return Result.valueOf(this).filter(Rejected.class).map(Rejected.class::cast); }
    public ResultStatus<D> ifRejected(Consumer<Rejected<D>> action) { if (isRejected()) action.accept((Rejected<D>)this); return this; }
    public ResultStatus<D> ifRejected(Runnable action) { if (isRejected()) action.run(); return this; }
    public boolean isFailed() { return this instanceof Failed; }
    public Result<Failed<D>> asFailed() { return Result.valueOf(this).filter(Failed.class).map(Failed.class::cast); }
    public ResultStatus<D> ifFailed(Consumer<Failed<D>> action) { if (isFailed()) action.accept((Failed<D>)this); return this; }
    public ResultStatus<D> ifFailed(Runnable action) { if (isFailed()) action.run(); return this; }
    
    public static class ResultStatusFirstSwitch<D extends Object> {
        private ResultStatus<D> $value;
        private ResultStatusFirstSwitch(ResultStatus<D> theValue) { this.$value = theValue; }
        public <TARGET> ResultStatusFirstSwitchTyped<TARGET, D> toA(Class<TARGET> clzz) {
            return new ResultStatusFirstSwitchTyped<TARGET, D>($value);
        }
        
        public <TARGET> ResultStatusSwitchAcceptedAdjustedRejectedFailed<TARGET, D> notAllowed(Function<? super NotAllowed<D>, ? extends TARGET> theAction) {
            Function<ResultStatus<D>, TARGET> $action = nullValue();
            Function<ResultStatus<D>, TARGET> oldAction = (Function<ResultStatus<D>, TARGET>)$action;
            Function<ResultStatus<D>, TARGET> newAction =
                ($action != null)
                ? oldAction : 
                    ($value instanceof NotAllowed)
                    ? (Function<ResultStatus<D>, TARGET>)(d -> theAction.apply((NotAllowed<D>)d))
                    : oldAction;
            
            return new ResultStatusSwitchAcceptedAdjustedRejectedFailed<TARGET, D>($value, newAction);
        }
        public <TARGET> ResultStatusSwitchAcceptedAdjustedRejectedFailed<TARGET, D> notAllowed(Supplier<? extends TARGET> theSupplier) {
            return notAllowed(d->theSupplier.get());
        }
        public <TARGET> ResultStatusSwitchAcceptedAdjustedRejectedFailed<TARGET, D> notAllowed(TARGET theValue) {
            return notAllowed(d->theValue);
        }
        
        public <TARGET> ResultStatusSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowed(Predicate<NotAllowed<D>> check, Function<? super NotAllowed<D>, ? extends TARGET> theAction) {
            Function<ResultStatus<D>, TARGET> $action = nullValue();
            Function<ResultStatus<D>, TARGET> oldAction = (Function<ResultStatus<D>, TARGET>)$action;
            Function<ResultStatus<D>, TARGET> newAction =
                ($action != null)
                ? oldAction : 
                    (($value instanceof NotAllowed) && check.test((NotAllowed<D>)$value))
                    ? (Function<ResultStatus<D>, TARGET>)(d -> theAction.apply((NotAllowed<D>)d))
                    : oldAction;
            
            return new ResultStatusSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D>($value, newAction);
        }
        private <T> T nullValue() {
            return (T)null;
        }
        public <TARGET> ResultStatusSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowed(Predicate<NotAllowed<D>> check, Supplier<? extends TARGET> theSupplier) {
            return notAllowed(check, d->theSupplier.get());
        }
        public <TARGET> ResultStatusSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowed(Predicate<NotAllowed<D>> check, TARGET theValue) {
            return notAllowed(check, d->theValue);
        }
    }
    public static class ResultStatusFirstSwitchTyped<TARGET, D extends Object> {
        private ResultStatus<D> $value;
        private ResultStatusFirstSwitchTyped(ResultStatus<D> theValue) { this.$value = theValue; }
        
        public ResultStatusSwitchAcceptedAdjustedRejectedFailed<TARGET, D> notAllowed(Function<? super NotAllowed<D>, ? extends TARGET> theAction) {
            Function<ResultStatus<D>, TARGET> $action = nullValue();
            Function<ResultStatus<D>, TARGET> oldAction = (Function<ResultStatus<D>, TARGET>)$action;
            Function<ResultStatus<D>, TARGET> newAction =
                ($action != null)
                ? oldAction : 
                    ($value instanceof NotAllowed)
                    ? (Function<ResultStatus<D>, TARGET>)(d -> theAction.apply((NotAllowed<D>)d))
                    : oldAction;
            
            return new ResultStatusSwitchAcceptedAdjustedRejectedFailed<TARGET, D>($value, newAction);
        }
        public ResultStatusSwitchAcceptedAdjustedRejectedFailed<TARGET, D> notAllowed(Supplier<? extends TARGET> theSupplier) {
            return notAllowed(d->theSupplier.get());
        }
        public ResultStatusSwitchAcceptedAdjustedRejectedFailed<TARGET, D> notAllowed(TARGET theValue) {
            return notAllowed(d->theValue);
        }
        
        public ResultStatusSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowed(Predicate<NotAllowed<D>> check, Function<? super NotAllowed<D>, ? extends TARGET> theAction) {
            Function<ResultStatus<D>, TARGET> $action = nullValue();
            Function<ResultStatus<D>, TARGET> oldAction = (Function<ResultStatus<D>, TARGET>)$action;
            Function<ResultStatus<D>, TARGET> newAction =
                ($action != null)
                ? oldAction : 
                    (($value instanceof NotAllowed) && check.test((NotAllowed<D>)$value))
                    ? (Function<ResultStatus<D>, TARGET>)(d -> theAction.apply((NotAllowed<D>)d))
                    : oldAction;
            
            return new ResultStatusSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D>($value, newAction);
        }
        public ResultStatusSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowed(Predicate<NotAllowed<D>> check, Supplier<? extends TARGET> theSupplier) {
            return notAllowed(check, d->theSupplier.get());
        }
        public ResultStatusSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowed(Predicate<NotAllowed<D>> check, TARGET theValue) {
            return notAllowed(check, d->theValue);
        }
        
        private <T> T nullValue() {
            return (T)null;
        }
    }
    public static class ResultStatusSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D extends Object> extends ChoiceTypeSwitch<ResultStatus<D>, TARGET> {
        private ResultStatusSwitchNotAllowedAcceptedAdjustedRejectedFailed(ResultStatus<D> theValue, Function<ResultStatus<D>, ? extends TARGET> theAction) { super(theValue, theAction); }
        
        public ResultStatusSwitchAcceptedAdjustedRejectedFailed<TARGET, D> notAllowed(Function<? super NotAllowed<D>, ? extends TARGET> theAction) {
            Function<ResultStatus<D>, TARGET> oldAction = (Function<ResultStatus<D>, TARGET>)$action;
            Function<ResultStatus<D>, TARGET> newAction =
                ($action != null)
                ? oldAction : 
                    ($value instanceof NotAllowed)
                    ? (Function<ResultStatus<D>, TARGET>)(d -> theAction.apply((NotAllowed<D>)d))
                    : oldAction;
            
            return new ResultStatusSwitchAcceptedAdjustedRejectedFailed<TARGET, D>($value, newAction);
        }
        public ResultStatusSwitchAcceptedAdjustedRejectedFailed<TARGET, D> notAllowed(Supplier<? extends TARGET> theSupplier) {
            return notAllowed(d->theSupplier.get());
        }
        public ResultStatusSwitchAcceptedAdjustedRejectedFailed<TARGET, D> notAllowed(TARGET theValue) {
            return notAllowed(d->theValue);
        }
        
        public ResultStatusSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowed(Predicate<NotAllowed<D>> check, Function<? super NotAllowed<D>, ? extends TARGET> theAction) {
            Function<ResultStatus<D>, TARGET> oldAction = (Function<ResultStatus<D>, TARGET>)$action;
            Function<ResultStatus<D>, TARGET> newAction =
                ($action != null)
                ? oldAction : 
                    (($value instanceof NotAllowed) && check.test((NotAllowed<D>)$value))
                    ? (Function<ResultStatus<D>, TARGET>)(d -> theAction.apply((NotAllowed<D>)d))
                    : oldAction;
            
            return new ResultStatusSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D>($value, newAction);
        }
        public ResultStatusSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowed(Predicate<NotAllowed<D>> check, Supplier<? extends TARGET> theSupplier) {
            return notAllowed(check, d->theSupplier.get());
        }
        public ResultStatusSwitchNotAllowedAcceptedAdjustedRejectedFailed<TARGET, D> notAllowed(Predicate<NotAllowed<D>> check, TARGET theValue) {
            return notAllowed(check, d->theValue);
        }
    }
    public static class ResultStatusSwitchAcceptedAdjustedRejectedFailed<TARGET, D extends Object> extends ChoiceTypeSwitch<ResultStatus<D>, TARGET> {
        private ResultStatusSwitchAcceptedAdjustedRejectedFailed(ResultStatus<D> theValue, Function<ResultStatus<D>, ? extends TARGET> theAction) { super(theValue, theAction); }
        
        public ResultStatusSwitchAdjustedRejectedFailed<TARGET, D> accepted(Function<? super Accepted<D>, ? extends TARGET> theAction) {
            Function<ResultStatus<D>, TARGET> oldAction = (Function<ResultStatus<D>, TARGET>)$action;
            Function<ResultStatus<D>, TARGET> newAction =
                ($action != null)
                ? oldAction : 
                    ($value instanceof Accepted)
                    ? (Function<ResultStatus<D>, TARGET>)(d -> theAction.apply((Accepted<D>)d))
                    : oldAction;
            
            return new ResultStatusSwitchAdjustedRejectedFailed<TARGET, D>($value, newAction);
        }
        public ResultStatusSwitchAdjustedRejectedFailed<TARGET, D> accepted(Supplier<? extends TARGET> theSupplier) {
            return accepted(d->theSupplier.get());
        }
        public ResultStatusSwitchAdjustedRejectedFailed<TARGET, D> accepted(TARGET theValue) {
            return accepted(d->theValue);
        }
        
        public ResultStatusSwitchAcceptedAdjustedRejectedFailed<TARGET, D> accepted(Predicate<Accepted<D>> check, Function<? super Accepted<D>, ? extends TARGET> theAction) {
            Function<ResultStatus<D>, TARGET> oldAction = (Function<ResultStatus<D>, TARGET>)$action;
            Function<ResultStatus<D>, TARGET> newAction =
                ($action != null)
                ? oldAction : 
                    (($value instanceof Accepted) && check.test((Accepted<D>)$value))
                    ? (Function<ResultStatus<D>, TARGET>)(d -> theAction.apply((Accepted<D>)d))
                    : oldAction;
            
            return new ResultStatusSwitchAcceptedAdjustedRejectedFailed<TARGET, D>($value, newAction);
        }
        public ResultStatusSwitchAcceptedAdjustedRejectedFailed<TARGET, D> accepted(Predicate<Accepted<D>> check, Supplier<? extends TARGET> theSupplier) {
            return accepted(check, d->theSupplier.get());
        }
        public ResultStatusSwitchAcceptedAdjustedRejectedFailed<TARGET, D> accepted(Predicate<Accepted<D>> check, TARGET theValue) {
            return accepted(check, d->theValue);
        }
    }
    public static class ResultStatusSwitchAdjustedRejectedFailed<TARGET, D extends Object> extends ChoiceTypeSwitch<ResultStatus<D>, TARGET> {
        private ResultStatusSwitchAdjustedRejectedFailed(ResultStatus<D> theValue, Function<ResultStatus<D>, ? extends TARGET> theAction) { super(theValue, theAction); }
        
        public ResultStatusSwitchRejectedFailed<TARGET, D> adjusted(Function<? super Adjusted<D>, ? extends TARGET> theAction) {
            Function<ResultStatus<D>, TARGET> oldAction = (Function<ResultStatus<D>, TARGET>)$action;
            Function<ResultStatus<D>, TARGET> newAction =
                ($action != null)
                ? oldAction : 
                    ($value instanceof Adjusted)
                    ? (Function<ResultStatus<D>, TARGET>)(d -> theAction.apply((Adjusted<D>)d))
                    : oldAction;
            
            return new ResultStatusSwitchRejectedFailed<TARGET, D>($value, newAction);
        }
        public ResultStatusSwitchRejectedFailed<TARGET, D> adjusted(Supplier<? extends TARGET> theSupplier) {
            return adjusted(d->theSupplier.get());
        }
        public ResultStatusSwitchRejectedFailed<TARGET, D> adjusted(TARGET theValue) {
            return adjusted(d->theValue);
        }
        
        public ResultStatusSwitchAdjustedRejectedFailed<TARGET, D> adjusted(Predicate<Adjusted<D>> check, Function<? super Adjusted<D>, ? extends TARGET> theAction) {
            Function<ResultStatus<D>, TARGET> oldAction = (Function<ResultStatus<D>, TARGET>)$action;
            Function<ResultStatus<D>, TARGET> newAction =
                ($action != null)
                ? oldAction : 
                    (($value instanceof Adjusted) && check.test((Adjusted<D>)$value))
                    ? (Function<ResultStatus<D>, TARGET>)(d -> theAction.apply((Adjusted<D>)d))
                    : oldAction;
            
            return new ResultStatusSwitchAdjustedRejectedFailed<TARGET, D>($value, newAction);
        }
        public ResultStatusSwitchAdjustedRejectedFailed<TARGET, D> adjusted(Predicate<Adjusted<D>> check, Supplier<? extends TARGET> theSupplier) {
            return adjusted(check, d->theSupplier.get());
        }
        public ResultStatusSwitchAdjustedRejectedFailed<TARGET, D> adjusted(Predicate<Adjusted<D>> check, TARGET theValue) {
            return adjusted(check, d->theValue);
        }
    }
    public static class ResultStatusSwitchRejectedFailed<TARGET, D extends Object> extends ChoiceTypeSwitch<ResultStatus<D>, TARGET> {
        private ResultStatusSwitchRejectedFailed(ResultStatus<D> theValue, Function<ResultStatus<D>, ? extends TARGET> theAction) { super(theValue, theAction); }
        
        public ResultStatusSwitchFailed<TARGET, D> rejected(Function<? super Rejected<D>, ? extends TARGET> theAction) {
            Function<ResultStatus<D>, TARGET> oldAction = (Function<ResultStatus<D>, TARGET>)$action;
            Function<ResultStatus<D>, TARGET> newAction =
                ($action != null)
                ? oldAction : 
                    ($value instanceof Rejected)
                    ? (Function<ResultStatus<D>, TARGET>)(d -> theAction.apply((Rejected<D>)d))
                    : oldAction;
            
            return new ResultStatusSwitchFailed<TARGET, D>($value, newAction);
        }
        public ResultStatusSwitchFailed<TARGET, D> rejected(Supplier<? extends TARGET> theSupplier) {
            return rejected(d->theSupplier.get());
        }
        public ResultStatusSwitchFailed<TARGET, D> rejected(TARGET theValue) {
            return rejected(d->theValue);
        }
        
        public ResultStatusSwitchRejectedFailed<TARGET, D> rejected(Predicate<Rejected<D>> check, Function<? super Rejected<D>, ? extends TARGET> theAction) {
            Function<ResultStatus<D>, TARGET> oldAction = (Function<ResultStatus<D>, TARGET>)$action;
            Function<ResultStatus<D>, TARGET> newAction =
                ($action != null)
                ? oldAction : 
                    (($value instanceof Rejected) && check.test((Rejected<D>)$value))
                    ? (Function<ResultStatus<D>, TARGET>)(d -> theAction.apply((Rejected<D>)d))
                    : oldAction;
            
            return new ResultStatusSwitchRejectedFailed<TARGET, D>($value, newAction);
        }
        public ResultStatusSwitchRejectedFailed<TARGET, D> rejected(Predicate<Rejected<D>> check, Supplier<? extends TARGET> theSupplier) {
            return rejected(check, d->theSupplier.get());
        }
        public ResultStatusSwitchRejectedFailed<TARGET, D> rejected(Predicate<Rejected<D>> check, TARGET theValue) {
            return rejected(check, d->theValue);
        }
    }
    public static class ResultStatusSwitchFailed<TARGET, D extends Object> extends ChoiceTypeSwitch<ResultStatus<D>, TARGET> {
        private ResultStatusSwitchFailed(ResultStatus<D> theValue, Function<ResultStatus<D>, ? extends TARGET> theAction) { super(theValue, theAction); }
        
        public TARGET failed(Function<? super Failed<D>, ? extends TARGET> theAction) {
            Function<ResultStatus<D>, TARGET> oldAction = (Function<ResultStatus<D>, TARGET>)$action;
            Function<ResultStatus<D>, TARGET> newAction =
                ($action != null)
                ? oldAction : 
                    ($value instanceof Failed)
                    ? (Function<ResultStatus<D>, TARGET>)(d -> theAction.apply((Failed<D>)d))
                    : oldAction;
            
            return newAction.apply($value);
        }
        public TARGET failed(Supplier<? extends TARGET> theSupplier) {
            return failed(d->theSupplier.get());
        }
        public TARGET failed(TARGET theValue) {
            return failed(d->theValue);
        }
        
        public ResultStatusSwitchFailed<TARGET, D> failed(Predicate<Failed<D>> check, Function<? super Failed<D>, ? extends TARGET> theAction) {
            Function<ResultStatus<D>, TARGET> oldAction = (Function<ResultStatus<D>, TARGET>)$action;
            Function<ResultStatus<D>, TARGET> newAction =
                ($action != null)
                ? oldAction : 
                    (($value instanceof Failed) && check.test((Failed<D>)$value))
                    ? (Function<ResultStatus<D>, TARGET>)(d -> theAction.apply((Failed<D>)d))
                    : oldAction;
            
            return new ResultStatusSwitchFailed<TARGET, D>($value, newAction);
        }
        public ResultStatusSwitchFailed<TARGET, D> failed(Predicate<Failed<D>> check, Supplier<? extends TARGET> theSupplier) {
            return failed(check, d->theSupplier.get());
        }
        public ResultStatusSwitchFailed<TARGET, D> failed(Predicate<Failed<D>> check, TARGET theValue) {
            return failed(check, d->theValue);
        }
    }
    
}

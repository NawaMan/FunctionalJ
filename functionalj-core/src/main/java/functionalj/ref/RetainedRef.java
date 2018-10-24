package functionalj.ref;

import static java.util.Objects.requireNonNull;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import functionalj.Env;
import functionalj.functions.Func0;
import functionalj.result.Result;
import lombok.val;

public class RetainedRef<DATA> extends RefOf<DATA> implements RetainChecker {
    
    private static final Object NONE = new Object();
    
    private final Ref<DATA>     sourceRef;
    private final RetainChecker checker;
    
    private final AtomicReference<Object> data = new AtomicReference<>();
    
    public RetainedRef(Ref<DATA> sourceRef, RetainChecker checker) {
        super(sourceRef.getDataType());
        this.sourceRef = sourceRef;
        this.checker   = checker;
        data.set(NONE);
    }
    
    @Override
    public boolean stillValid() {
        return checker.stillValid();
    }
    
    @Override
    protected Result<DATA> findResult() {
        Object  oldData    = data.get();
        boolean noData     = oldData.equals(NONE);
        boolean requireNew = !stillValid();
        if (noData || requireNew) {
            val newResult = sourceRef.asResult();
            data.compareAndSet(oldData, newResult);
        }
        @SuppressWarnings("unchecked")
        val currentData = (Result<DATA>)data.get();
        return currentData;
    }
    
    //== Aux Class ==
    
    public static class Builder<DATA> {
        
        private final Ref<DATA> sourceRef;
        
        public Builder(Ref<DATA> sourceRef) {
            this.sourceRef = requireNonNull(sourceRef);
        }
        
        public RetainedRef<DATA> forever() {
            return new RetainedRef<DATA>(sourceRef, RetainChecker.forever);
        }
        public RetainedRef<DATA> never() {
            return new RetainedRef<DATA>(sourceRef, RetainChecker.never);
        }
        
        public <STATE> WhileBuilder<STATE, DATA> when(Ref<STATE> stateSupplier) {
            return new WhileBuilder<>(sourceRef, stateSupplier);
        }
        public <STATE> RetainedRef<DATA> when(Ref<STATE> stateSupplier, BiPredicate<STATE, STATE> whenStateChange) {
            val updateOnChange  = new RetainChecker.UpdateOnChanged<STATE>();
            val valueSupplier   = stateSupplier.valueSupplier();
            val checker         = new SuppliedValueCheck<STATE>(
                                        valueSupplier,
                                        valueSupplier,
                                        whenStateChange,
                                        updateOnChange);
            val ref = new RetainedRef<>(sourceRef, checker);
            return ref;
        }
        public ForPeriodBuilder<DATA> withIn(long period) {
            return new ForPeriodBuilder<DATA>(sourceRef, period);
        }
    }
    
    public static class WhileBuilder<STATE, DATA> {
        
        private final Ref<DATA>  sourceRef;
        private final Ref<STATE> stateSupplier;
        
        public WhileBuilder(Ref<DATA> sourceRef, Ref<STATE> stateSupplier) {
            this.sourceRef     = requireNonNull(sourceRef);
            this.stateSupplier = requireNonNull(stateSupplier);
        }
        public RetainedRef<DATA> same() {
            val whenStateChange = new RetainChecker.WhenNotSame<STATE>();
            val updateOnChange  = new RetainChecker.UpdateOnChanged<STATE>();
            val valueSupplier   = stateSupplier.valueSupplier();
            val checker         = new SuppliedValueCheck<STATE>(
                                        valueSupplier,
                                        valueSupplier,
                                        whenStateChange,
                                        updateOnChange);
            val ref = new RetainedRef<>(sourceRef, checker);
            return ref;
        }
        public RetainedRef<DATA> equals() {
            val whenStateChange = new RetainChecker.WhenNotEqual<STATE>();
            val updateOnChange  = new RetainChecker.UpdateOnChanged<STATE>();
            val valueSupplier   = stateSupplier.valueSupplier();
            val checker         = new SuppliedValueCheck<STATE>(
                                        valueSupplier,
                                        valueSupplier,
                                        whenStateChange,
                                        updateOnChange);
            val ref = new RetainedRef<>(sourceRef, checker);
            return ref;
        }
        public RetainedRef<DATA> match(Predicate<STATE> matcher) {
            val whenStateChange = (BiPredicate<STATE, STATE>)((STATE oldState, STATE newState) -> !matcher.test(newState));
            val updateOnChange  = new RetainChecker.UpdateOnChanged<STATE>();
            val valueSupplier   = stateSupplier.valueSupplier();
            val checker         = new SuppliedValueCheck<STATE>(
                                        valueSupplier,
                                        valueSupplier,
                                        whenStateChange,
                                        updateOnChange);
            val ref = new RetainedRef<>(sourceRef, checker);
            return ref;
        }
    }
    
    public static class ForPeriodBuilder<DATA> {
        private final Ref<DATA> sourceRef;
        private final long      period;

        public ForPeriodBuilder(Ref<DATA> sourceRef, long period) {
            this.sourceRef = requireNonNull(sourceRef);
            this.period    = period;
        }
        public RetainedRef<DATA> milliSeconds() {
            return milliSeconds(period);
        }
        public RetainedRef<DATA> seconds() {
            return milliSeconds(period * 1000L);
        }
        public RetainedRef<DATA> minutes() {
            return milliSeconds(period * 60 * 1000L);
        }
        public RetainedRef<DATA> hours() {
            return milliSeconds(period * 60 * 60 * 1000L);
        }
        public RetainedRef<DATA> days() {
            return milliSeconds(period * 24 * 60 * 60 * 1000L);
        }
        public RetainedRef<DATA> weeks() {
            return milliSeconds(period * 7 * 24 * 60 * 60 * 1000L);
        }
        private <STATE> RetainedRef<DATA> milliSeconds(long milliSeconds) {
            val whenStateChange = (BiPredicate<Long, Long>)((Long oldState, Long newState) -> {
                long oldTime = oldState.longValue();
                long newTime = newState.longValue();
                return oldTime + milliSeconds <= newTime;
            });
            val updateOnChange = new RetainChecker.UpdateOnChanged<Long>();
            val valueSupplier  = (Func0<Long>)Env::currentMilliSecond;
            val checker        = new SuppliedValueCheck<Long>(
                                        valueSupplier,
                                        valueSupplier,
                                        whenStateChange,
                                        updateOnChange);
            val ref = new RetainedRef<>(sourceRef, checker);
            return ref;
        }
    }
    
}

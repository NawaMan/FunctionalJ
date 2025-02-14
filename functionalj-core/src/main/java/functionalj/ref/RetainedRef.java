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
package functionalj.ref;

import static java.util.Objects.requireNonNull;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import functionalj.environments.Env;
import functionalj.function.Func0;
import functionalj.function.Traced;
import functionalj.result.Result;
import functionalj.supportive.CallerId;
import lombok.val;

public class RetainedRef<DATA> extends RefOf<DATA> implements RetainChecker {
    
    private static final Object NONE = new Object();
    
    private final Ref<DATA> sourceRef;
    
    private final RetainChecker checker;
    
    private final Holder<Object> data;
    
    public RetainedRef(Ref<DATA> sourceRef, RetainChecker checker, boolean isLocal) {
        this(CallerId.instance.trace(Traced::extractLocationString) + ":" + "Ref<" + Utils.name(sourceRef.dataClass) + ">", sourceRef, checker, isLocal);
    }
    
    public RetainedRef(String toString, Ref<DATA> sourceRef, RetainChecker checker, boolean isLocal) {
        super((toString != null)
                ? toString
                : (CallerId.instance.trace(Traced::extractLocationString) + ":" + "Ref<" + Utils.name(sourceRef.dataClass) + ">"), 
              sourceRef.getDataType());
        this.sourceRef = sourceRef;
        this.checker = checker;
        this.data = new Holder<>(isLocal);
        this.data.set(NONE, NONE);
    }
    
    @Override
    public boolean stillValid() {
        return checker.stillValid();
    }
    
    @Override
    protected Result<DATA> findResult() {
        val oldData = data.get();
        val noData = Objects.equals(oldData, NONE);
        val requireNew = !stillValid();
        if (noData || requireNew) {
            val newResult = sourceRef.asResult().getResult();
            data.set(oldData, newResult);
        }
        @SuppressWarnings("unchecked")
        val currentData = (Result<DATA>) data.get();
        return currentData;
    }
    
    final Ref<DATA> whenAbsent(Func0<DATA> defaultSupplier) {
        val newSourceRef = sourceRef.whenAbsentGet(defaultSupplier);
        if (newSourceRef == sourceRef)
            return this;
        return new RetainedRef<>(newSourceRef, checker, data.isLocal());
    }
    
    // == Aux Class ==
    public static class Builder<DATA> {
        
        private final Ref<DATA> sourceRef;
        
        private final boolean isLocal;
        
        public Builder(Ref<DATA> sourceRef, boolean isLocal) {
            this.sourceRef = requireNonNull(sourceRef);
            this.isLocal = isLocal;
        }
        
        public Builder<DATA> globally() {
            if (!isLocal)
                return this;
            return new Builder<>(sourceRef, false);
        }
        
        public Builder<DATA> locally() {
            if (isLocal)
                return this;
            return new Builder<>(sourceRef, true);
        }
        
        public RetainedRef<DATA> forever() {
            return new RetainedRef<DATA>(sourceRef, RetainChecker.forever, isLocal);
        }
        
        public RetainedRef<DATA> never() {
            return new RetainedRef<DATA>(sourceRef, RetainChecker.never, isLocal);
        }
        
        public <STATE> WhileBuilder<STATE, DATA> when(Ref<STATE> stateSupplier) {
            return new WhileBuilder<>(sourceRef, stateSupplier, isLocal);
        }
        
        public <STATE> RetainedRef<DATA> when(Ref<STATE> stateSupplier, BiPredicate<STATE, STATE> whenStateChange) {
            val updateOnChange = new RetainChecker.UpdateOnChanged<STATE>();
            val valueSupplier = stateSupplier.valueSupplier();
            val checker = new SuppliedValueCheck<STATE>(isLocal, valueSupplier, valueSupplier, whenStateChange, updateOnChange);
            val ref = new RetainedRef<>(sourceRef, checker, isLocal);
            return ref;
        }
        
        public ForPeriodBuilder<DATA> withIn(long period) {
            return new ForPeriodBuilder<DATA>(sourceRef, period, isLocal);
        }
        
        public Builder<DATA> localThread() {
            return this;
        }
    }
    
    public static class WhileBuilder<STATE, DATA> {
        
        private final Ref<DATA> sourceRef;
        
        private final Ref<STATE> stateSupplier;
        
        private final boolean isLocal;
        
        public WhileBuilder(Ref<DATA> sourceRef, Ref<STATE> stateSupplier, boolean isLocal) {
            this.sourceRef = requireNonNull(sourceRef);
            this.stateSupplier = requireNonNull(stateSupplier);
            this.isLocal = isLocal;
        }
        
        public RetainedRef<DATA> same() {
            val whenStateChange = new RetainChecker.WhenNotSame<STATE>();
            val updateOnChange = new RetainChecker.UpdateOnChanged<STATE>();
            val valueSupplier = stateSupplier.valueSupplier();
            val checker = new SuppliedValueCheck<STATE>(isLocal, valueSupplier, valueSupplier, whenStateChange, updateOnChange);
            val ref = new RetainedRef<>(sourceRef, checker, isLocal);
            return ref;
        }
        
        public RetainedRef<DATA> equals() {
            val whenStateChange = new RetainChecker.WhenNotEqual<STATE>();
            val updateOnChange = new RetainChecker.UpdateOnChanged<STATE>();
            val valueSupplier = stateSupplier.valueSupplier();
            val checker = new SuppliedValueCheck<STATE>(isLocal, valueSupplier, valueSupplier, whenStateChange, updateOnChange);
            val ref = new RetainedRef<>(sourceRef, checker, isLocal);
            return ref;
        }
        
        public RetainedRef<DATA> match(Predicate<STATE> matcher) {
            val whenStateChange = (BiPredicate<STATE, STATE>) ((STATE oldState, STATE newState) -> !matcher.test(newState));
            val updateOnChange = new RetainChecker.UpdateOnChanged<STATE>();
            val valueSupplier = stateSupplier.valueSupplier();
            val checker = new SuppliedValueCheck<STATE>(isLocal, valueSupplier, valueSupplier, whenStateChange, updateOnChange);
            val ref = new RetainedRef<>(sourceRef, checker, isLocal);
            return ref;
        }
    }
    
    public static class ForPeriodBuilder<DATA> {
        
        private final Ref<DATA> sourceRef;
        
        private final long period;
        
        private final boolean isLocal;
        
        public ForPeriodBuilder(Ref<DATA> sourceRef, long period, boolean isLocal) {
            this.sourceRef = requireNonNull(sourceRef);
            this.period = period;
            this.isLocal = isLocal;
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
            val whenStateChange = (BiPredicate<Long, Long>) ((Long oldState, Long newState) -> {
                long oldTime = (oldState == null) ? 0 : oldState.longValue();
                long newTime = (newState == null) ? 0 : newState.longValue();
                return oldTime + milliSeconds <= newTime;
            });
            val updateOnChange = new RetainChecker.UpdateOnChanged<Long>();
            val valueSupplier = (Func0<Long>) Env.time()::currentMilliSecond;
            val checker = new SuppliedValueCheck<Long>(isLocal, valueSupplier, valueSupplier, whenStateChange, updateOnChange);
            val ref = new RetainedRef<>(sourceRef, checker, isLocal);
            return ref;
        }
    }
}

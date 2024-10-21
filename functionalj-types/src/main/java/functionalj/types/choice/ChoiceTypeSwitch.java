// ============================================================================
// Copyright (c) 2017-2024 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.types.choice;

import java.util.function.Function;
import java.util.function.Supplier;

public abstract class ChoiceTypeSwitch<D, T> {
    
    protected final D $value;
    
    protected final Function<? super D, ? extends T> $action;
    
    protected ChoiceTypeSwitch(D theValue, Function<? super D, ? extends T> theAction) {
        this.$value = theValue;
        this.$action = theAction;
    }
    
    public T orElse(T elseValue) {
        return ($action != null) ? $action.apply($value) : elseValue;
    }
    
    public T orGet(Supplier<T> valueSupplier) {
        return ($action != null) ? $action.apply($value) : valueSupplier.get();
    }
    
    public T orGet(Function<? super D, T> valueMapper) {
        @SuppressWarnings("unchecked")
        Function<? super D, T> newAction = (Function<? super D, T>) (($action != null) ? $action : valueMapper);
        return newAction.apply($value);
    }
    
    public T orElseGet(Supplier<T> valueSupplier) {
        return orGet(valueSupplier);
    }
    
    public T orElseGet(Function<? super D, T> valueMapper) {
        return orGet(valueMapper);
    }
    
    public static class ChoiceTypeSwitchData<D, T> {
        
        protected final D value;
        
        protected final Function<D, T> action;
        
        public ChoiceTypeSwitchData(D value) {
            this(value, null);
        }
        
        public ChoiceTypeSwitchData(D value, Function<D, T> action) {
            this.value = value;
            this.action = action;
        }
        
        public D value() {
            return value;
        }
        
        public Function<D, T> action() {
            return action;
        }
        
        public ChoiceTypeSwitchData<D, T> withValue(D value) {
            return new ChoiceTypeSwitchData<D, T>(value, action);
        }
        
        public ChoiceTypeSwitchData<D, T> withAction(Function<D, T> action) {
            return new ChoiceTypeSwitchData<D, T>(value, action);
        }
    }
}

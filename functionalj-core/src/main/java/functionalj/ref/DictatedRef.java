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
package functionalj.ref;

import static java.util.Objects.requireNonNull;
import functionalj.function.Func0;
import functionalj.function.Traced;
import functionalj.result.Result;
import functionalj.supportive.CallerId;
import lombok.val;

public class DictatedRef<DATA> extends Ref<DATA> {
    
    private final Ref<DATA> ref;
    
    DictatedRef(String toString, Ref<DATA> ref) {
        super((toString != null)
                ? toString
                : (CallerId.instance.trace(Traced::extractLocationString) + ":" + "Ref<" + Utils.name(ref.dataClass) + ">"), 
              ref.getDataType(), 
              ref.getElseSupplier());
        this.ref = requireNonNull(ref);
    }
    
    @Override
    Result<DATA> findResult() {
        return ref.findResult();
    }
    
    final Result<DATA> findOverrideResult() {
        return null;
    }
    
    final Ref<DATA> whenAbsent(Func0<DATA> defaultValue) {
        // No effect
        return this;
    }
    
    @Override
    public int hashCode() {
        return ref.hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (this == obj)
            return true;
        if (ref == obj)
            return true;
        if (obj instanceof DictatedRef) {
            @SuppressWarnings("rawtypes")
            val dictatedRef = (DictatedRef) obj;
            if (ref.equals(dictatedRef.ref))
                return true;
            if (this.equals(dictatedRef.ref))
                return true;
        }
        return ref.equals(obj);
    }
}

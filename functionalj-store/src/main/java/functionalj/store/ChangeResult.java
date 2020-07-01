// ============================================================================
// Copyright (c) 2017-2020 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.store;

import functionalj.function.Func1;
import functionalj.function.FuncUnit1;
import functionalj.pipeable.Pipeable;
import functionalj.result.Result;

@SuppressWarnings({"rawtypes"})
public class ChangeResult<D> implements Pipeable<ChangeResult<D>> {
    
    private Store<D> store;
    private D originalData;
    private ResultStatus<D> status;
    
    public ChangeResult(D originalData, ResultStatus<D> status) {
        this(null, originalData, status);
    }
    
    ChangeResult(Store<D> store, D originalData, ResultStatus<D> status) {
        this.store = store;
        this.originalData = originalData;
        this.status = status;
    }
    
    public Store<D> store() {
        return store;
    }
    public D originalData() {
        return originalData;
    }
    public ResultStatus<D> status() {
        return status;
    }
    
    public ChangeResult<D> __data() throws Exception {
        return this;
    }
    
    @Override
    public String toString() {
        return "ChangeResult [store=" + store + ", originalData=" + originalData + ", status=" + status + "]";
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((originalData == null) ? 0 : originalData.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        result = prime * result + ((store == null) ? 0 : store.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ChangeResult other = (ChangeResult) obj;
        if (originalData == null) {
            if (other.originalData != null)
                return false;
        } else if (!originalData.equals(other.originalData))
            return false;
        if (status == null) {
            if (other.status != null)
                return false;
        } else if (!status.equals(other.status))
            return false;
        if (store == null) {
            if (other.store != null)
                return false;
        } else if (!store.equals(other.store))
            return false;
        return true;
    }
    
    public boolean hasChanged() {
        return status.match()
                .notAllowed(false)
                .accepted  (true)
                .adjusted  (true)
                .rejected  (false)
                .failed    (false);
    }
    
    public Result<D> result() {
        return status.match()
                .notAllowed(n -> Result.<D>ofNotExist())
                .accepted  (a -> Result.valueOf(a.newData()))
                .adjusted  (a -> Result.valueOf(a.adjustedData()))
                .rejected  (()-> Result.<D>ofNotExist())
                .failed    (()-> Result.<D>ofNotExist());
    }
    
    public D value() {
        return result().value();
    }
    
    public ChangeResult<D> change(Func1<D, D> changer) {
        return hasChanged()
                ? store.change(changer)
                : this;
    }
    public ChangeResult<D> use(FuncUnit1<D> consumer) {
        store.use(consumer);
        return this;
    }
    
}

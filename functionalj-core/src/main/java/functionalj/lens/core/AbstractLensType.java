// ============================================================================
// Copyright (c) 2017-2019 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.lens.core;

import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.AnyLens;

@SuppressWarnings("javadoc")
public abstract class AbstractLensType<H, T, TA extends AnyAccess<H, T>, TL extends AnyLens<H, T>> 
        implements LensType<H, T, TA, TL> {
    
    private final Class<T>  dataClass;
    private final Class<TA> accessClass;
    private final Class<TL> lensClass;
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	protected AbstractLensType(Class<T> dataClass, Class<? extends AnyAccess> accessClass, Class<? extends AnyLens> lensClass) {
        this.dataClass   = dataClass;
        this.accessClass = (Class)accessClass;
        this.lensClass   = (Class)lensClass;
    }

    @Override
    public Class<T> getDataClass() {
        return dataClass;
    }

    @Override
    public Class<TA> getAccessClass() {
        return accessClass;
    }

    @Override
    public Class<TL> getLensClass() {
        return lensClass;
    }
    
}
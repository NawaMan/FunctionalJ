// ============================================================================
// Copyright (c) 2017-2021 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.lens;

import java.util.function.BiFunction;
import java.util.function.Function;
import functionalj.lens.core.AbstractLensType;
import functionalj.lens.core.LensSpec;
import functionalj.lens.core.LensType;
import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.AnyLens;
import functionalj.lens.lenses.ObjectAccess;
import functionalj.lens.lenses.ObjectLens;
import functionalj.lens.lenses.StringAccess;
import functionalj.lens.lenses.StringLens;

public interface LensTypes {
    
    @SuppressWarnings("rawtypes")
    public static <H, T, TA extends AnyAccess<H, T>, TL extends AnyLens<H, T>> LensType<H, T, TA, TL> of(Class<T> dataClass, Class<? extends AnyAccess> accessClass, Class<? extends AnyLens> lensClass, Function<Function<H, T>, TA> accessCreator, BiFunction<String, LensSpec<H, T>, TL> lensCreator) {
        return new AbstractLensType<H, T, TA, TL>(dataClass, accessClass, lensClass) {
        
            @Override
            public TL newLens(String name, LensSpec<H, T> spec) {
                return lensCreator.apply(name, spec);
            }
        
            @Override
            public TA newAccess(Function<H, T> accessToValue) {
                return accessCreator.apply(accessToValue);
            }
        };
    }
    
    public static <H> LensType<H, Object, AnyAccess<H, Object>, AnyLens<H, Object>> OBJECT() {
        @SuppressWarnings({ "unchecked", "rawtypes" })
        LensType<H, Object, AnyAccess<H, Object>, AnyLens<H, Object>> type = (LensType<H, Object, AnyAccess<H, Object>, AnyLens<H, Object>>) (LensType) __internal__.objectLensType;
        return type;
    }
    
    public static <H> LensType<H, String, StringAccess<H>, StringLens<H>> STRING() {
        @SuppressWarnings({ "unchecked", "rawtypes" })
        LensType<H, String, StringAccess<H>, StringLens<H>> type = (LensType<H, String, StringAccess<H>, StringLens<H>>) (LensType) __internal__.stringLensType;
        return type;
    }
    
    public static final class __internal__ {
        
        static final LensType<Object, Object, ObjectAccess<Object, Object>, ObjectLens<Object, Object>> objectLensType = LensTypes.of(Object.class, ObjectAccess.class, ObjectLens.class, access -> access::apply, ObjectLens::of);
        
        static final LensType<Object, String, StringAccess<Object>, StringLens<Object>> stringLensType = LensTypes.of(String.class, StringAccess.class, StringLens.class, access -> access::apply, StringLens::of);
    }
}

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
package functionalj.lens;

import static functionalj.lens.core.LensSpec.selfRead;
import static functionalj.lens.core.LensSpec.selfWrite;
import java.util.List;
import functionalj.lens.core.LensSpec;
import functionalj.lens.core.LensSpecParameterized;
import functionalj.lens.core.LensSpecParameterized2;
import functionalj.lens.core.LensType;
import functionalj.lens.core.LensUtils;
import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.AnyLens;
import functionalj.lens.lenses.FuncListLens;
import functionalj.lens.lenses.ListLens;
import functionalj.lens.lenses.ObjectLens;
import functionalj.lens.lenses.Tuple2Lens;
import functionalj.list.FuncList;
import functionalj.tuple.Tuple2;

public class Accesses {
    
    // == Internal use only ==
    
    public static class TheListLens implements ListLens<List<?>, Object, ObjectLens<List<?>, Object>> {
        
        private static final LensSpecParameterized<List<?>, List<?>, Object, ObjectLens<List<?>, Object>> common = LensUtils.createLensSpecParameterized("theList", selfRead(), selfWrite(), ObjectLens::of);
        
        public <T, SA extends AnyAccess<List<T>, T>, SL extends AnyLens<List<T>, T>> ListLens<List<T>, T, SL> of(LensType<List<T>, T, SA, SL> type) {
            LensSpecParameterized<List<T>, List<T>, T, SL> spec = LensUtils.createLensSpecParameterized(LensSpec.selfRead(), LensSpec.selfWrite(), s -> type.newLens(null, s));
            ListLens<List<T>, T, SL> listLens = ListLens.of(spec);
            return listLens;
        }
        
        @SuppressWarnings({ "rawtypes", "unchecked" })
        @Override
        public LensSpecParameterized<List<?>, List<Object>, Object, ObjectLens<List<?>, Object>> lensSpecParameterized() {
            return (LensSpecParameterized) common;
        }
    }
    
    public static class TheFuncListLens implements FuncListLens<FuncList<?>, Object, ObjectLens<FuncList<?>, Object>> {
        
        private static final LensSpecParameterized<FuncList<?>, FuncList<?>, Object, ObjectLens<FuncList<?>, Object>> common = LensUtils.createLensSpecParameterized("theFuncList", selfRead(), selfWrite(), ObjectLens::of);
        
        public <T, SA extends AnyAccess<FuncList<T>, T>, SL extends AnyLens<FuncList<T>, T>> FuncListLens<FuncList<T>, T, SL> of(LensType<FuncList<T>, T, SA, SL> type) {
            LensSpecParameterized<FuncList<T>, FuncList<T>, T, SL> spec = LensUtils.createLensSpecParameterized(LensSpec.selfRead(), LensSpec.selfWrite(), s -> type.newLens(null, s));
            FuncListLens<FuncList<T>, T, SL> listLens = FuncListLens.of(spec);
            return listLens;
        }
        
        @SuppressWarnings({ "rawtypes", "unchecked" })
        @Override
        public LensSpecParameterized<FuncList<?>, FuncList<Object>, Object, ObjectLens<FuncList<?>, Object>> lensSpecParameterized() {
            return (LensSpecParameterized) common;
        }
    }
    
    // -- Tuple2 --
    public static class TheTuple2Lens implements Tuple2Lens<Tuple2<Object, Object>, Object, Object, ObjectLens<Tuple2<Object, Object>, Object>, ObjectLens<Tuple2<Object, Object>, Object>> {
        
        // I am tired .... just goes with this.
        private static final LensSpecParameterized2<Tuple2<Object, Object>, Tuple2<Object, Object>, Object, Object, ObjectLens<Tuple2<Object, Object>, Object>, ObjectLens<Tuple2<Object, Object>, Object>> common = new LensSpecParameterized2<Tuple2<Object, Object>, Tuple2<Object, Object>, Object, Object, ObjectLens<Tuple2<Object, Object>, Object>, ObjectLens<Tuple2<Object, Object>, Object>>() {
        
            @Override
            public LensSpec<Tuple2<Object, Object>, Tuple2<Object, Object>> getSpec() {
                return LensSpec.of(selfRead(), selfWrite());
            }
        
            @Override
            public ObjectLens<Tuple2<Object, Object>, Object> createSubLens1(String subName, LensSpec<Tuple2<Object, Object>, Object> subSpec) {
                return ObjectLens.of(subName, subSpec);
            }
        
            @Override
            public ObjectLens<Tuple2<Object, Object>, Object> createSubLens2(String subName, LensSpec<Tuple2<Object, Object>, Object> subSpec) {
                return ObjectLens.of(subName, subSpec);
            }
        };
        
        public <T1, T2, T1ACCESS extends AnyAccess<Tuple2<T1, T2>, T1>, T2ACCESS extends AnyAccess<Tuple2<T1, T2>, T2>, T1LENS extends AnyLens<Tuple2<T1, T2>, T1>, T2LENS extends AnyLens<Tuple2<T1, T2>, T2>> Tuple2Lens<Tuple2<T1, T2>, T1, T2, T1LENS, T2LENS> of(LensType<Tuple2<T1, T2>, T1, T1ACCESS, T1LENS> t1Type, LensType<Tuple2<T1, T2>, T2, T2ACCESS, T2LENS> t2Type) {
            return Tuple2Lens.of(selfRead(), selfWrite(), t1Type, t2Type);
        }
        
        @SuppressWarnings({ "rawtypes", "unchecked" })
        @Override
        public LensSpecParameterized2<Tuple2<Object, Object>, Tuple2<Object, Object>, Object, Object, ObjectLens<Tuple2<Object, Object>, Object>, ObjectLens<Tuple2<Object, Object>, Object>> lensSpecParameterized2() {
            return (LensSpecParameterized2) common;
        }
    }
}

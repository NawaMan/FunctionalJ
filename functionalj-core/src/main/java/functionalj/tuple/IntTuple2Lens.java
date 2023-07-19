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
package functionalj.tuple;

import static functionalj.functions.StrFuncs.joinNonNull;
import static functionalj.functions.StrFuncs.whenBlank;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import functionalj.lens.core.AccessParameterized;
import functionalj.lens.core.LensSpec;
import functionalj.lens.core.LensSpecParameterized;
import functionalj.lens.core.LensUtils;
import functionalj.lens.core.WriteLens;
import functionalj.lens.lenses.AnyLens;
import functionalj.lens.lenses.IntegerLens;
import functionalj.lens.lenses.ObjectLens;
import functionalj.lens.lenses.ObjectLensImpl;
import lombok.val;

public interface IntTuple2Lens<HOST, T2, T2LENS extends AnyLens<HOST, T2>> extends ObjectLens<HOST, IntTuple2<T2>>, IntTuple2Access<HOST, T2, T2LENS> {
    
    public static class Impl<HOST, T2, T2LENS extends AnyLens<HOST, T2>> extends ObjectLensImpl<HOST, IntTuple2<T2>> implements IntTuple2Lens<HOST, T2, T2LENS> {
        
        private LensSpecParameterized<HOST, IntTuple2<T2>, T2, T2LENS> lensSpecParameterized;
        
        public Impl(String name, LensSpecParameterized<HOST, IntTuple2<T2>, T2, T2LENS> lensSpecParameterized) {
            super(name, lensSpecParameterized.getSpec());
        }
        
        @Override
        public LensSpecParameterized<HOST, IntTuple2<T2>, T2, T2LENS> lensSpecParameterized() {
            return lensSpecParameterized;
        }
    }
    
    public static <HOST, T2, T2LENS extends AnyLens<HOST, T2>> IntTuple2Lens<HOST, T2, T2LENS> of(String name, Function<HOST, IntTuple2<T2>> read, WriteLens<HOST, IntTuple2<T2>> write, BiFunction<String, LensSpec<HOST, T2>, T2LENS> valueLensCreator) {
        val spec = new LensSpecParameterized<HOST, IntTuple2<T2>, T2, T2LENS>() {
        
            @Override
            public LensSpec<HOST, IntTuple2<T2>> getSpec() {
                return LensSpec.of(read, write);
            }
        
            @Override
            public T2LENS createSubLens(String subName, LensSpec<HOST, T2> subSpec) {
                val lensName = whenBlank(joinNonNull(".", name, subName), (String) null);
                return valueLensCreator.apply(lensName, subSpec);
            }
        };
        return new IntTuple2Lens.Impl<HOST, T2, T2LENS>(name, spec);
    }
    
    public static <HOST, T2, T2LENS extends AnyLens<HOST, T2>> IntTuple2Lens<HOST, T2, T2LENS> of(Function<HOST, IntTuple2<T2>> read, WriteLens<HOST, IntTuple2<T2>> write, BiFunction<String, LensSpec<HOST, T2>, T2LENS> valueLensCreator) {
        return of(null, read, write, valueLensCreator);
    }
    
    public static <HOST, T2, T2LENS extends AnyLens<HOST, T2>> IntTuple2Lens<HOST, T2, T2LENS> of(Function<HOST, IntTuple2<T2>> read, WriteLens<HOST, IntTuple2<T2>> write, Function<LensSpec<HOST, T2>, T2LENS> valueLensCreator) {
        return of(null, read, write, (__, spec) -> valueLensCreator.apply(spec));
    }
    
    public LensSpecParameterized<HOST, IntTuple2<T2>, T2, T2LENS> lensSpecParameterized();
    
    @Override
    public default AccessParameterized<HOST, IntTuple2<T2>, T2, T2LENS> accessParameterized() {
        return lensSpecParameterized();
    }
    
    @Override
    public default LensSpec<HOST, IntTuple2<T2>> lensSpec() {
        return lensSpecParameterized().getSpec();
    }
    
    @Override
    default IntTuple2<T2> applyUnsafe(HOST host) throws Exception {
        return IntTuple2Access.super.apply(host);
    }
    
    public default IntegerLens<HOST> _1() {
        WriteLens<IntTuple2<T2>, Integer> write = (tuple, _1) -> new IntTuple2<T2>(_1, tuple._2);
        Function<IntTuple2<T2>, Integer> read = IntTuple2::_1;
        return LensUtils.createSubLens(this, "_1", read, write, IntegerLens::of);
    }
    
    public default T2LENS T2() {
        val write = (WriteLens<IntTuple2<T2>, T2>) ((tuple, T2) -> new IntTuple2<T2>(tuple._1, T2));
        val read = (Function<IntTuple2<T2>, T2>) IntTuple2::_2;
        return LensUtils.createSubLens(this, read, write, lensSpecParameterized()::createSubLens);
    }
    
    public default Function<HOST, HOST> change1To(int _1value) {
        return host -> {
            val newTuple = new IntTuple2<>(_1value, apply(host)._2);
            return apply(host, newTuple);
        };
    }
    
    public default Function<HOST, HOST> change2To(T2 T2value) {
        return host -> {
            val newTuple = new IntTuple2<>(apply(host)._1, T2value);
            return apply(host, newTuple);
        };
    }
    
    public default Function<HOST, HOST> change1By(Supplier<Integer> _1valueSupplier) {
        return host -> {
            val newTuple = new IntTuple2<>(_1valueSupplier.get(), apply(host)._2);
            return apply(host, newTuple);
        };
    }
    
    public default Function<HOST, HOST> change1By(IntSupplier _1valueSupplier) {
        return host -> {
            val newTuple = new IntTuple2<>(_1valueSupplier.getAsInt(), apply(host)._2);
            return apply(host, newTuple);
        };
    }
    
    public default Function<HOST, HOST> change2By(Supplier<T2> T2valueSupplier) {
        return host -> {
            val newTuple = new IntTuple2<>(apply(host)._1, T2valueSupplier.get());
            return apply(host, newTuple);
        };
    }
    
    public default Function<HOST, HOST> change1By(Function<Integer, Integer> _1valueTransformer) {
        return host -> {
            val oldTuple = apply(host);
            val new_1 = _1valueTransformer.apply(oldTuple._1);
            val newTuple = new IntTuple2<>(new_1, oldTuple._2);
            return apply(host, newTuple);
        };
    }
    
    public default Function<HOST, HOST> change1By(IntFunction<Integer> _1valueTransformer) {
        return host -> {
            val oldTuple = apply(host);
            val new_1 = _1valueTransformer.apply(oldTuple._1);
            val newTuple = new IntTuple2<>(new_1, oldTuple._2);
            return apply(host, newTuple);
        };
    }
    
    public default Function<HOST, HOST> change1By(ToIntFunction<Integer> _1valueTransformer) {
        return host -> {
            val oldTuple = apply(host);
            val new_1 = _1valueTransformer.applyAsInt(oldTuple._1);
            val newTuple = new IntTuple2<>(new_1, oldTuple._2);
            return apply(host, newTuple);
        };
    }
    
    public default Function<HOST, HOST> change2By(Function<T2, T2> T2valueTransformer) {
        return host -> {
            val oldTuple = apply(host);
            val newT2 = T2valueTransformer.apply(oldTuple._2);
            val newTuple = new IntTuple2<>(oldTuple._1, newT2);
            return apply(host, newTuple);
        };
    }
    
    public default Function<HOST, HOST> change1By(BiFunction<Integer, T2, Integer> _1valueTransformer) {
        return host -> {
            val oldTuple = apply(host);
            val new_1 = _1valueTransformer.apply(oldTuple._1, oldTuple._2);
            val newTuple = new IntTuple2<>(new_1, oldTuple._2);
            return apply(host, newTuple);
        };
    }
    
    public default Function<HOST, HOST> change2By(BiFunction<Integer, T2, T2> T2valueTransformer) {
        return host -> {
            val oldTuple = apply(host);
            val newT2 = T2valueTransformer.apply(oldTuple._1, oldTuple._2);
            val newTuple = new IntTuple2<>(oldTuple._1, newT2);
            return apply(host, newTuple);
        };
    }
}

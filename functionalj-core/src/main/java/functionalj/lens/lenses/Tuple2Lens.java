// ============================================================================
// Copyright (c) 2017-2023 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.lens.lenses;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import functionalj.lens.core.AccessParameterized2;
import functionalj.lens.core.LensSpec;
import functionalj.lens.core.LensSpecParameterized2;
import functionalj.lens.core.LensType;
import functionalj.lens.core.LensUtils;
import functionalj.lens.core.WriteLens;
import functionalj.tuple.ImmutableTuple2;
import functionalj.tuple.Tuple2;
import lombok.val;

@FunctionalInterface
public interface Tuple2Lens<HOST, T1, T2, T1LENS extends AnyLens<HOST, T1>, T2LENS extends AnyLens<HOST, T2>> extends ObjectLens<HOST, Tuple2<T1, T2>>, Tuple2Access<HOST, T1, T2, T1LENS, T2LENS> {
    
    public static class Impl<HOST, T1, T2, T1LENS extends AnyLens<HOST, T1>, T2LENS extends AnyLens<HOST, T2>> extends ObjectLens.Impl<HOST, Tuple2<T1, T2>> implements Tuple2Lens<HOST, T1, T2, T1LENS, T2LENS> {
        
        private LensSpecParameterized2<HOST, Tuple2<T1, T2>, T1, T2, T1LENS, T2LENS> spec;
        
        public Impl(String name, LensSpecParameterized2<HOST, Tuple2<T1, T2>, T1, T2, T1LENS, T2LENS> spec) {
            super(name, spec.getSpec());
            this.spec = spec;
        }
        
        @Override
        public LensSpecParameterized2<HOST, Tuple2<T1, T2>, T1, T2, T1LENS, T2LENS> lensSpecParameterized2() {
            return spec;
        }
    }
    
    public static <H, _1, _2, _1ACCESS extends AnyAccess<H, _1>, _2ACCESS extends AnyAccess<H, _2>, _1LENS extends AnyLens<H, _1>, _2LENS extends AnyLens<H, _2>> Tuple2Lens<H, _1, _2, _1LENS, _2LENS> of(String name, Function<H, Tuple2<_1, _2>> read, WriteLens<H, Tuple2<_1, _2>> write, LensType<H, _1, _1ACCESS, _1LENS> _1Type, LensType<H, _2, _2ACCESS, _2LENS> _2Type) {
        val spec = new LensSpecParameterized2<H, Tuple2<_1, _2>, _1, _2, _1LENS, _2LENS>() {
        
            @Override
            public LensSpec<H, Tuple2<_1, _2>> getSpec() {
                return LensSpec.of(read, write);
            }
        
            @Override
            public _1LENS createSubLens1(String subName, LensSpec<H, _1> subSpec) {
                return _1Type.newLens(subName, subSpec);
            }
        
            @Override
            public _2LENS createSubLens2(String subName, LensSpec<H, _2> subSpec) {
                return _2Type.newLens(subName, subSpec);
            }
        };
        return new Impl<>(name, spec);
    }
    
    public static <H, _1, _2, _1ACCESS extends AnyAccess<H, _1>, _2ACCESS extends AnyAccess<H, _2>, _1LENS extends AnyLens<H, _1>, _2LENS extends AnyLens<H, _2>> Tuple2Lens<H, _1, _2, _1LENS, _2LENS> of(Function<H, Tuple2<_1, _2>> read, WriteLens<H, Tuple2<_1, _2>> write, LensType<H, _1, _1ACCESS, _1LENS> _1Type, LensType<H, _2, _2ACCESS, _2LENS> _2Type) {
        return of(null, read, write, _1Type, _2Type);
    }
    
    public LensSpecParameterized2<HOST, Tuple2<T1, T2>, T1, T2, T1LENS, T2LENS> lensSpecParameterized2();
    
    @Override
    public default AccessParameterized2<HOST, Tuple2<T1, T2>, T1, T2, T1LENS, T2LENS> accessParameterized2() {
        return lensSpecParameterized2();
    }
    
    @Override
    public default LensSpec<HOST, Tuple2<T1, T2>> lensSpec() {
        return lensSpecParameterized2().getSpec();
    }
    
    @Override
    default Tuple2<T1, T2> applyUnsafe(HOST host) throws Exception {
        return Tuple2Access.super.applyUnsafe(host);
    }
    
    public default T1LENS _1() {
        WriteLens<Tuple2<T1, T2>, T1> write = (tuple, _1) -> new ImmutableTuple2<T1, T2>(_1, tuple._2());
        Function<Tuple2<T1, T2>, T1> read = Tuple2::_1;
        return LensUtils.createSubLens(this, read, write, lensSpecParameterized2()::createSubLens1);
    }
    
    public default T2LENS _2() {
        WriteLens<Tuple2<T1, T2>, T2> write = (tuple, _2) -> new ImmutableTuple2<T1, T2>(tuple._1(), _2);
        Function<Tuple2<T1, T2>, T2> read = Tuple2::_2;
        return LensUtils.createSubLens(this, read, write, lensSpecParameterized2()::createSubLens2);
    }
    
    public default T1LENS first() {
        return _1();
    }
    
    public default T2LENS second() {
        return _2();
    }
    
    public default Function<HOST, HOST> change1To(T1 _1value) {
        return host -> {
            Tuple2<T1, T2> newTuple = new ImmutableTuple2<>(_1value, apply(host)._2());
            return apply(host, newTuple);
        };
    }
    
    public default Function<HOST, HOST> change2To(T2 _2value) {
        return host -> {
            Tuple2<T1, T2> newTuple = new ImmutableTuple2<>(apply(host)._1(), _2value);
            return apply(host, newTuple);
        };
    }
    
    public default Function<HOST, HOST> change1By(Supplier<T1> _1valueSupplier) {
        return host -> {
            val newTuple = new ImmutableTuple2<>(_1valueSupplier.get(), apply(host)._2());
            return apply(host, newTuple);
        };
    }
    
    public default Function<HOST, HOST> change2By(Supplier<T2> _2valueSupplier) {
        return host -> {
            val newTuple = new ImmutableTuple2<>(apply(host)._1(), _2valueSupplier.get());
            return apply(host, newTuple);
        };
    }
    
    public default Function<HOST, HOST> change1By(Function<T1, T1> _1valueTransformer) {
        return host -> {
            val oldTuple = apply(host);
            val new_1 = _1valueTransformer.apply(oldTuple._1());
            val newTuple = new ImmutableTuple2<>(new_1, oldTuple._2());
            return apply(host, newTuple);
        };
    }
    
    public default Function<HOST, HOST> change2By(Function<T2, T2> _2valueTransformer) {
        return host -> {
            val oldTuple = apply(host);
            val new_2 = _2valueTransformer.apply(oldTuple._2());
            val newTuple = new ImmutableTuple2<>(oldTuple._1(), new_2);
            return apply(host, newTuple);
        };
    }
    
    public default Function<HOST, HOST> change1By(BiFunction<T1, T2, T1> _1valueTransformer) {
        return host -> {
            val oldTuple = apply(host);
            val new_1 = _1valueTransformer.apply(oldTuple._1(), oldTuple._2());
            val newTuple = new ImmutableTuple2<>(new_1, oldTuple._2());
            return apply(host, newTuple);
        };
    }
    
    public default Function<HOST, HOST> change2By(BiFunction<T1, T2, T2> _2valueTransformer) {
        return host -> {
            val oldTuple = apply(host);
            val new_2 = _2valueTransformer.apply(oldTuple._1(), oldTuple._2());
            val newTuple = new ImmutableTuple2<>(oldTuple._1(), new_2);
            return apply(host, newTuple);
        };
    }
    
    public default Function<HOST, HOST> changeFirstTo(T1 _1value) {
        return change1To(_1value);
    }
    
    public default Function<HOST, HOST> changeSecondTo(T2 _2value) {
        return change2To(_2value);
    }
    
    public default Function<HOST, HOST> changeFirstBy(Supplier<T1> _1valueSupplier) {
        return change1By(_1valueSupplier);
    }
    
    public default Function<HOST, HOST> changeSecondBy(Supplier<T2> _2valueSupplier) {
        return change2By(_2valueSupplier);
    }
    
    public default Function<HOST, HOST> changeFirstBy(Function<T1, T1> _1valueTransformer) {
        return change1By(_1valueTransformer);
    }
    
    public default Function<HOST, HOST> changeSecondBy(Function<T2, T2> _2valueTransformer) {
        return change2By(_2valueTransformer);
    }
    
    public default Function<HOST, HOST> changeFirstBy(BiFunction<T1, T2, T1> _1valueTransformer) {
        return change1By(_1valueTransformer);
    }
    
    public default Function<HOST, HOST> changeSecondBy(BiFunction<T1, T2, T2> _2valueTransformer) {
        return change2By(_2valueTransformer);
    }
}

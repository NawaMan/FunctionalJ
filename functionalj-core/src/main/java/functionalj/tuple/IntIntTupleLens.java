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

import java.util.function.Function;
import java.util.function.IntSupplier;
import java.util.function.IntUnaryOperator;

import functionalj.function.IntBiFunctionPrimitive;
import functionalj.lens.core.LensSpec;
import functionalj.lens.core.LensUtils;
import functionalj.lens.core.WriteLens;
import functionalj.lens.lenses.IntegerLens;
import functionalj.lens.lenses.ObjectLens;
import lombok.val;



public interface IntIntTupleLens<HOST>
        extends
            ObjectLens<HOST, IntIntTuple>,
            IntIntTupleAccess<HOST> {
    
    public static <HOST>
            IntIntTupleLens<HOST> of(
                    Function<HOST,  IntIntTuple> read,
                    WriteLens<HOST, IntIntTuple> write) {
        return () -> LensSpec.of(read, write);
    }
    
    public LensSpec<HOST, IntIntTuple> lensSpec();
    
    @Override
    public default IntIntTuple applyUnsafe(HOST host) throws Exception {
        return lensSpec().apply(host);
    }
    
    @Override
    public default IntegerLens<HOST> _1() {
        WriteLens<IntIntTuple, Integer> write = (tuple, _1) -> new IntIntTuple(_1, tuple._2);
        Function <IntIntTuple, Integer> read  = IntIntTuple::_1;
        return LensUtils.createSubLens(this, "_1", read, write, IntegerLens::of);
    }
    
    @Override
    public default IntegerLens<HOST> _2() {
        WriteLens<IntIntTuple, Integer> write = (tuple, _2) -> new IntIntTuple(tuple._1, _2);
        Function <IntIntTuple, Integer> read  = IntIntTuple::_1;
        return LensUtils.createSubLens(this, "_2", read, write, IntegerLens::of);
    }
    
    public default Function<HOST, HOST> change1To(int _1value) {
        return host -> {
            val newTuple = new IntIntTuple(_1value, apply(host)._2);
            return apply(host, newTuple);
        };
    }
    
    public default Function<HOST, HOST> change2To(int _2value) {
        return host -> {
            val newTuple = new IntIntTuple(apply(host)._1, _2value);
            return apply(host, newTuple);
        };
    }
    
    public default Function<HOST, HOST> change1By(IntSupplier _1valueSupplier) {
        return host -> {
            val newTuple = new IntIntTuple(_1valueSupplier.getAsInt(), apply(host)._2);
            return apply(host, newTuple);
        };
    }
    
    public default Function<HOST, HOST> change2By(IntSupplier _2valueSupplier) {
        return host -> {
            val newTuple = new IntIntTuple(apply(host)._1, _2valueSupplier.getAsInt());
            return apply(host, newTuple);
        };
    }
    
    public default Function<HOST, HOST> change1By(IntUnaryOperator _1valueTransformer) {
        return host -> {
            val oldTuple = apply(host);
            val new_1    = _1valueTransformer.applyAsInt(oldTuple._1);
            val newTuple = new IntIntTuple(new_1, oldTuple._2);
            return apply(host, newTuple);
        };
    }
    
    public default Function<HOST, HOST> change2By(IntUnaryOperator _2valueTransformer) {
        return host -> {
            val oldTuple = apply(host);
            val new_2    = _2valueTransformer.applyAsInt(oldTuple._2);
            val newTuple = new IntIntTuple(oldTuple._1, new_2);
            return apply(host, newTuple);
        };
    }
    
    public default Function<HOST, HOST> change1By(IntBiFunctionPrimitive _1valueTransformer) {
        return host -> {
            val oldTuple = apply(host);
            val new_1    = _1valueTransformer.applyAsIntAndInt(oldTuple._1, oldTuple._2);
            val newTuple = new IntIntTuple(new_1, oldTuple._2);
            return apply(host, newTuple);
        };
    }
    
    public default Function<HOST, HOST> change2By(IntBiFunctionPrimitive _2valueTransformer) {
        return host -> {
            val oldTuple = apply(host);
            val new_2    = _2valueTransformer.applyAsIntAndInt(oldTuple._1, oldTuple._2);
            val newTuple = new IntIntTuple(oldTuple._1, new_2);
            return apply(host, newTuple);
        };
    }
}
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
package functionalj.lens.core;

import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import functionalj.lens.lenses.PrimitiveLensSpecs;

public class LensSpec<HOST, DATA> implements Function<HOST, DATA> {

    public static <DATA> Function<DATA, DATA> selfRead() {
        return self -> self;
    }

    public static <DATA> WriteLens<DATA, DATA> selfWrite() {
        return (self, newValue) -> newValue;
    }

    private final Function<HOST, DATA> read;

    private final WriteLens<HOST, DATA> write;

    // May regret this later .. but WTH.
    private final BooleanSupplier isNullSafe;

    public Function<HOST, DATA> getRead() {
        return read;
    }

    public WriteLens<HOST, DATA> getWrite() {
        return write;
    }

    public BooleanSupplier getIsNullSafe() {
        return isNullSafe;
    }

    public static final BooleanSupplier SUPPLY_TRUE = new BooleanSupplier() {

        @Override
        public boolean getAsBoolean() {
            return true;
        }

        @Override
        public String toString() {
            return "BooleanSupplier(true)";
        }
    };

    public static final BooleanSupplier SUPPLY_FALSE = new BooleanSupplier() {

        @Override
        public boolean getAsBoolean() {
            return false;
        }

        @Override
        public String toString() {
            return "BooleanSupplier(false)";
        }
    };

    public static final BooleanSupplier booleanSupplierOf(boolean bool) {
        return bool ? SUPPLY_TRUE : SUPPLY_FALSE;
    }

    public static <DATA> LensSpec<DATA, DATA> of(Class<DATA> dataClass) {
        return new LensSpec<DATA, DATA>(selfRead(), selfWrite());
    }

    public static <HOST, DATA> LensSpec<HOST, DATA> of(Function<HOST, DATA> read) {
        return of(read::apply, (WriteLens<HOST, DATA>) null);
    }

    public static <HOST, DATA> LensSpec<HOST, DATA> of(Function<HOST, DATA> read, WriteLens<HOST, DATA> write) {
        if (write == null)
            return new LensSpec<HOST, DATA>(read::apply, null);
        return new LensSpec<HOST, DATA>(read::apply, write::apply);
    }

    public static <HOST, DATA> LensSpec<HOST, DATA> of(Function<HOST, DATA> read, WriteLens<HOST, DATA> write, boolean isNullSafe) {
        return new LensSpec<HOST, DATA>(read::apply, write::apply, booleanSupplierOf(isNullSafe));
    }

    public static <HOST, DATA> LensSpec<HOST, DATA> of(Function<HOST, DATA> read, WriteLens<HOST, DATA> write, BooleanSupplier isNullSafe) {
        return new LensSpec<HOST, DATA>(read::apply, write::apply, isNullSafe);
    }

    public static <HOST> PrimitiveLensSpecs.IntegerLensSpecPrimitive<HOST> ofPrimitive(ToIntFunction<HOST> readInt, WriteLens.PrimitiveInt<HOST> writeInt) {
        return new PrimitiveLensSpecs.IntegerLensSpecPrimitive<HOST>(readInt, writeInt);
    }

    public static <HOST> PrimitiveLensSpecs.LongLensSpecPrimitive<HOST> ofPrimitive(ToLongFunction<HOST> readLong, WriteLens.PrimitiveLong<HOST> writeLong) {
        return new PrimitiveLensSpecs.LongLensSpecPrimitive<HOST>(readLong, writeLong);
    }

    public static <HOST> PrimitiveLensSpecs.DoubleLensSpecPrimitive<HOST> ofPrimitive(ToDoubleFunction<HOST> readDouble, WriteLens.PrimitiveDouble<HOST> writeDouble) {
        return new PrimitiveLensSpecs.DoubleLensSpecPrimitive<HOST>(readDouble, writeDouble);
    }

    public static <HOST> PrimitiveLensSpecs.BooleanLensSpecPrimitive<HOST> ofPrimitive(Predicate<HOST> readBoolean, WriteLens.PrimitiveBoolean<HOST> writeBoolean) {
        return new PrimitiveLensSpecs.BooleanLensSpecPrimitive<HOST>(readBoolean, writeBoolean);
    }

    public LensSpec(Function<HOST, DATA> read, WriteLens<HOST, DATA> write) {
        this(read, write, SUPPLY_TRUE);
    }

    public LensSpec(Function<HOST, DATA> read, WriteLens<HOST, DATA> write, BooleanSupplier isNullSafe) {
        this.read = read;
        this.write = write;
        this.isNullSafe = isNullSafe;
    }

    public DATA apply(HOST host) {
        return read.apply(host);
    }

    public boolean isNullSafe() {
        return isNullSafe.getAsBoolean();
    }

    public LensSpec<HOST, DATA> withNullSafety(boolean isNullSafe) {
        return (isNullSafe && SUPPLY_TRUE.equals(this.isNullSafe)) ? this : new LensSpec<>(read, write, isNullSafe ? SUPPLY_TRUE : SUPPLY_FALSE);
    }

    public LensSpec<HOST, DATA> toNullSafe() {
        return SUPPLY_TRUE.equals(this.isNullSafe) ? this : new LensSpec<>(read, write, SUPPLY_TRUE);
    }

    public LensSpec<HOST, DATA> toNullUnsafe() {
        return SUPPLY_FALSE.equals(this.isNullSafe) ? this : new LensSpec<>(read, write, SUPPLY_FALSE);
    }

    public <SUB> LensSpec<HOST, SUB> then(LensSpec<DATA, SUB> sub) {
        return LensSpec.of(LensUtils.createSubRead(read, sub.read, isNullSafe), LensUtils.createSubWrite(read, write, sub.write, isNullSafe), isNullSafe);
    }

    public PrimitiveLensSpecs.IntegerLensSpecPrimitive<HOST> thenPrimitive(PrimitiveLensSpecs.IntegerLensSpecPrimitive<DATA> sub) {
        ToIntFunction<DATA> subReadInt = sub.getReadInt();
        WriteLens.PrimitiveInt<DATA> subWriteInt = sub.getWriteInt();
        ToIntFunction<HOST> readInt = LensUtils.createSubReadInt(read, subReadInt);
        WriteLens.PrimitiveInt<HOST> writeInt = LensUtils.createSubWriteInt(read, write, subWriteInt);
        return LensSpec.ofPrimitive(readInt, writeInt);
    }

    public PrimitiveLensSpecs.LongLensSpecPrimitive<HOST> thenPrimitive(PrimitiveLensSpecs.LongLensSpecPrimitive<DATA> sub) {
        ToLongFunction<DATA> subReadLong = sub.getReadLong();
        WriteLens.PrimitiveLong<DATA> subWriteLong = sub.getWriteLong();
        ToLongFunction<HOST> readLong = LensUtils.createSubReadLong(read, subReadLong);
        WriteLens.PrimitiveLong<HOST> writeLong = LensUtils.createSubWriteLong(read, write, subWriteLong);
        return LensSpec.ofPrimitive(readLong, writeLong);
    }

    public PrimitiveLensSpecs.DoubleLensSpecPrimitive<HOST> thenPrimitive(PrimitiveLensSpecs.DoubleLensSpecPrimitive<DATA> sub) {
        ToDoubleFunction<DATA> subReadDouble = sub.getReadDouble();
        WriteLens.PrimitiveDouble<DATA> subWriteDouble = sub.getWriteDouble();
        ToDoubleFunction<HOST> readDouble = LensUtils.createSubReadDouble(read, subReadDouble);
        WriteLens.PrimitiveDouble<HOST> writeDouble = LensUtils.createSubWriteDouble(read, write, subWriteDouble);
        return LensSpec.ofPrimitive(readDouble, writeDouble);
    }

    public PrimitiveLensSpecs.BooleanLensSpecPrimitive<HOST> thenPrimitive(PrimitiveLensSpecs.BooleanLensSpecPrimitive<DATA> sub) {
        Predicate<DATA> subReadBoolean = sub.getReadBoolean();
        WriteLens.PrimitiveBoolean<DATA> subWriteBoolean = sub.getWriteBoolean();
        Predicate<HOST> readBoolean = LensUtils.createSubReadBoolean(read, subReadBoolean);
        WriteLens.PrimitiveBoolean<HOST> writeBoolean = LensUtils.createSubWriteBoolean(read, write, subWriteBoolean);
        return LensSpec.ofPrimitive(readBoolean, writeBoolean);
    }
}

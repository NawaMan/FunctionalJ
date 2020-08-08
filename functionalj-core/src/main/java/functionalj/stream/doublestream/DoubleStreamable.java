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
package functionalj.stream.doublestream;

import java.util.function.Function;
import java.util.stream.DoubleStream;

import functionalj.stream.StreamPlus;
import functionalj.stream.intstream.IntStreamPlus;
import functionalj.stream.longstream.AsLongStreamable;
import functionalj.stream.longstream.LongStreamPlus;
import functionalj.streamable.AsStreamable;
import functionalj.streamable.Streamable;
import functionalj.streamable.intstreamable.AsIntStreamable;
import lombok.val;

public interface DoubleStreamable {

    //== Constructor ==

    public static DoubleStreamable from(DoubleStreamPlus mapToDouble) {
        //TODO Auto-generated method stub
        return null;
    }

    ////-- Derive --

    /** Create a Streamable from the given Streamable. */
    public static <SOURCE> DoubleStreamable deriveFrom(
            AsStreamable<SOURCE>                       asStreamable,
            Function<StreamPlus<SOURCE>, DoubleStream> action) {
        return () -> {
            val sourceStream = asStreamable.stream();
            val targetStream = action.apply(sourceStream);
            return DoubleStreamPlus.from(targetStream);
        };
    }

    /** Create a Streamable from the given IntStreamable. */
    public static <TARGET> DoubleStreamable deriveFrom(
            AsIntStreamable                       asStreamable,
            Function<IntStreamPlus, DoubleStream> action) {
        return () -> {
            val sourceStream = asStreamable.intStream();
            val targetStream = action.apply(sourceStream);
            return DoubleStreamPlus.from(targetStream);
        };
    }

    /** Create a Streamable from the given LongStreamable. */
    public static <TARGET> DoubleStreamable deriveFrom(
            AsLongStreamable                       asStreamable,
            Function<LongStreamPlus, DoubleStream> action) {
        return () -> {
            val sourceStream = asStreamable.longStream();
            val targetStream = action.apply(sourceStream);
            return DoubleStreamPlus.from(targetStream);
        };
    }

    /** Create a Streamable from the given LongStreamable. */
    public static <TARGET> DoubleStreamable deriveFrom(
            AsDoubleStreamable                       asStreamable,
            Function<DoubleStreamPlus, DoubleStream> action) {
        return () -> {
            val sourceStream = asStreamable.doubleStream();
            val targetStream = action.apply(sourceStream);
            return DoubleStreamPlus.from(targetStream);
        };
    }

    ///** Create a Streamable from another streamable. */
    //public static <SOURCE> IntStreamable deriveToInt(
    //        AsDoubleStreamable                   asStreamable,
    //        Function<DoubleStreamPlus, IntStream> action) {
    //    return IntStreamable.deriveFrom(asStreamable, action);
    //}

    ///** Create a Streamable from another streamable. */
    //public static <SOURCE> LongStreamable deriveToLong(
    //        AsDoubleStreamable                     asStreamable,
    //        Function<DoubleStreamPlus, LongStream> action) {
    //    return LongStreamable.deriveFrom(asStreamable, action);
    //}

    ///** Create a Streamable from another streamable. */
    //public static <SOURCE> DoubleStreamable deriveToDouble(
    //        AsDoubleStreamable                      asStreamable,
    //        Function<DoubleStreamPlus, DoubleStream> action) {
    //    return DoubleStreamable.deriveFrom(asStreamable, action);
    //}

    ///** Create a Streamable from another streamable. */
    //public static <SOURCE, TARGET> Streamable<TARGET> deriveToObj(
    //        AsDoubleStreamable                        asStreamable,
    //        Function<DoubleStreamPlus, Stream<TARGET>> action) {
    //    return Streamable.deriveFrom(asStreamable, action);
    //}

    //== Core ==

    public default DoubleStreamable doubleStreamable() {
        return this;
    }

    public default Streamable<Double> streamable() {
        return boxed();
    }

    public DoubleStreamPlus doubleStream();

    public default StreamPlus<Double> stream() {
        return doubleStream().boxed();
    }

    public default Streamable<Double> boxed() {
        return ()->StreamPlus.from(doubleStream().boxed());
    }

}

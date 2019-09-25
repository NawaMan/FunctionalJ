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
package functionalj.list;

import java.util.function.Function;
import java.util.stream.Stream;

import functionalj.function.Func2;
import functionalj.stream.StreamPlus;
import functionalj.stream.ZipWithOption;
import functionalj.tuple.Tuple2;
import lombok.val;

public interface FuncListWithCombine<DATA> {
    
    public <TARGET> FuncList<TARGET> deriveWith(Function<Stream<DATA>, Stream<TARGET>> action);
    
    public default FuncList<DATA> concatWith(
            FuncList<DATA> tail) {
        return deriveWith(stream -> {
            return StreamPlus
                    .from      (stream)
                    .concatWith(tail.stream());
        });
    }
    
    public default FuncList<DATA> merge(
            FuncList<DATA> anotherFuncList) {
        return deriveWith(stream -> {
            val anotherStream = anotherFuncList.stream();
            return StreamPlus
                    .from      (stream)
                    .concatWith(anotherStream);
        });
    }
    
    //-- Zip --
    
    public default <B, TARGET> FuncList<TARGET> combineWith(
            FuncList<B>            anotherFuncList, 
            Func2<DATA, B, TARGET> combinator) {
        return deriveWith(stream -> {
            val anotherStream = anotherFuncList.stream();
            return StreamPlus
                    .from       (stream)
                    .combineWith(anotherStream, combinator);
        });
    }
    public default <B, TARGET> FuncList<TARGET> combineWith(
            FuncList<B>            anotherFuncList, 
            ZipWithOption          option, 
            Func2<DATA, B, TARGET> combinator) {
        return deriveWith(stream -> {
            val anotherStream = anotherFuncList.stream();
            return StreamPlus
                    .from       (stream)
                    .combineWith(anotherStream, option, combinator);
        });
    }
    
    public default <B> FuncList<Tuple2<DATA,B>> zipWith(
            FuncList<B> anotherFuncList) {
        return deriveWith(stream -> {
            val anotherStream = anotherFuncList.stream();
            return StreamPlus
                    .from   (stream)
                    .zipWith(anotherStream);
        });
    }
    public default <B> FuncList<Tuple2<DATA,B>> zipWith(
            FuncList<B>   anotherFuncList, 
            ZipWithOption option) {
        return deriveWith(stream -> {
            val anotherStream = anotherFuncList.stream();
            return StreamPlus
                    .from   (stream)
                    .zipWith(anotherStream, option);
        });
    }
    
    public default <B, C> FuncList<C> zipWith(
            FuncList<B>       anotherFuncList, 
            Func2<DATA, B, C> merger) {
        return deriveWith(stream -> {
            val anotherStream = anotherFuncList.stream();
            return StreamPlus
                    .from   (stream)
                    .zipWith(anotherStream, merger);
        });
    }
    public default <B, C> FuncList<C> zipWith(
            FuncList<B>       anotherFuncList, 
            ZipWithOption     option, 
            Func2<DATA, B, C> merger) {
        return deriveWith(stream -> {
            val anotherStream = anotherFuncList.stream();
            return StreamPlus
                    .from   (stream)
                    .zipWith(anotherStream, option, merger);
        });
    }
    
    public default FuncList<DATA> choose(
            FuncList<DATA>             anotherFuncList, 
            Func2<DATA, DATA, Boolean> selectThisNotAnother) {
        return deriveWith(stream -> {
            val anotherStream = anotherFuncList.stream();
            return StreamPlus
                    .from  (stream)
                    .choose(anotherStream, selectThisNotAnother);
        });
    }
}

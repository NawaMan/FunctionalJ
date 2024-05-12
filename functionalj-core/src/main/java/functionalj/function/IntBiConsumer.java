// ============================================================================
// Copyright (c) 2017-2024 Nawapunth Manusitthipol (NawaMan - http://nawaman.net)
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
package functionalj.function;

import java.util.function.BiConsumer;

@FunctionalInterface
public interface IntBiConsumer extends IntObjBiConsumer<Integer> {
    
    public void acceptAsIntInt(int input1, int input2);
    
    public default void acceptAsInt(int input1, Integer input2) {
        acceptAsIntInt(input1, input2);
    }
    
    public default void acceptUnsafe(Integer input1, Integer input2) throws Exception {
        acceptAsIntInt(input1, input2);
    }
    
    @SuppressWarnings("unchecked")
    public static void accept(BiConsumer<? super Integer, ? super Integer> consumer, int input1, int input2) {
        if (consumer instanceof IntBiConsumer) {
            ((IntBiConsumer) consumer).acceptAsIntInt(input1, input2);
        } else if (consumer instanceof IntObjBiConsumer) {
            ((IntObjBiConsumer<Integer>) consumer).acceptAsInt(input1, input2);
        } else {
            consumer.accept(input1, input2);
        }
    }
}

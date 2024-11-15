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
package functionalj.stream.longstream;

import java.util.OptionalLong;
import functionalj.list.longlist.LongFuncList;
import functionalj.stream.StreamPlus;
import lombok.val;

// TODO - This is NOT thread safe (not even try to be).
public final class GrowOnlyLongArray {
    
    private static int ARRAY_COUNT = 8;
    
    private static int ARRAY_LENGTH = 100;
    
    private long[][] arrays;
    
    private int length = 0;
    
    public GrowOnlyLongArray() {
        arrays = new long[ARRAY_COUNT][];
    }
    
    public GrowOnlyLongArray(long... values) {
        int actualCount = (int) Math.ceil(1.0 * values.length / ARRAY_LENGTH);
        int arrayCount = Math.max(actualCount, ARRAY_COUNT);
        arrays = new long[arrayCount][];
        length = values.length;
        int offset = 0;
        for (int i = 0; i < actualCount; i++) {
            arrays[i] = new long[ARRAY_LENGTH];
            int eachLength = Math.min(ARRAY_LENGTH, length - offset);
            System.arraycopy(values, offset, arrays[i], 0, eachLength);
            offset = offset + ARRAY_LENGTH;
        }
    }
    
    public void add(long i) {
        int next = length;
        int aIndex = next / ARRAY_LENGTH;
        int residue = next % ARRAY_LENGTH;
        if (aIndex >= arrays.length) {
            long[][] newArrays = new long[arrays.length + ARRAY_COUNT][];
            System.arraycopy(arrays, 0, newArrays, 0, arrays.length);
            arrays = newArrays;
        }
        if (arrays[aIndex] == null) {
            arrays[aIndex] = new long[ARRAY_LENGTH];
        }
        arrays[aIndex][residue] = i;
        length++;
    }
    
    public int length() {
        return length;
    }
    
    public boolean isEmpty() {
        return length == 0;
    }
    
    public LongStreamPlus stream() {
        int aCount = length / ARRAY_LENGTH;
        int residue = length % ARRAY_LENGTH;
        LongStreamPlus head = StreamPlus.of(arrays).limit(aCount).flatMapToLong(a -> LongStreamPlus.of(a));
        LongStreamPlus tail = ((aCount >= arrays.length) || (arrays[aCount] == null)) ? LongStreamPlus.empty() : LongStreamPlus.of(arrays[aCount]).limit(residue);
        LongStreamPlus total = head.appendWith(tail);
        return total;
    }
    
    public LongFuncList toFuncList() {
        return LongFuncList.from(stream());
    }
    
    public long[] toArray() {
        return stream().toArray();
    }
    
    public long get(int i) {
        if (i < 0 || i >= length)
            throw new ArrayIndexOutOfBoundsException(i);
        int aIndex = i / ARRAY_LENGTH;
        int residue = i % ARRAY_LENGTH;
        return arrays[aIndex][residue];
    }
    
    public OptionalLong at(int i) {
        if (i < 0 || i >= length)
            return OptionalLong.empty();
        int aIndex = i / ARRAY_LENGTH;
        int residue = i % ARRAY_LENGTH;
        return OptionalLong.of(arrays[aIndex][residue]);
    }
    
    public String toString() {
        return stream().toListString();
    }
    
    public int hashCode() {
        return Long.hashCode(stream().reduce(43, (p, c) -> p * 43 + c));
    }
    
    public boolean equals(GrowOnlyLongArray array) {
        val score = stream().zipWith(array.stream(), (a, b) -> a == b ? 1 : 0).acceptUntil(i -> i == 0).sum();
        return score == length;
    }
}

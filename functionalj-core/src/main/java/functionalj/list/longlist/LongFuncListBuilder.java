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
package functionalj.list.longlist;

import java.util.Objects;
import java.util.OptionalLong;
import functionalj.stream.longstream.GrowOnlyLongArray;
import functionalj.stream.longstream.LongStreamPlus;

public class LongFuncListBuilder {
    
    private final GrowOnlyLongArray list;
    
    // This constructor must not be public or protected.
    LongFuncListBuilder(GrowOnlyLongArray list) {
        this.list = list;
    }
    
    public LongFuncListBuilder() {
        list = new GrowOnlyLongArray();
    }
    
    public LongFuncListBuilder(long... values) {
        list = new GrowOnlyLongArray(values);
    }
    
    public LongFuncListBuilder add(long data) {
        list.add(data);
        return this;
    }
    
    public LongFuncList build() {
        int length = list.length();
        return LongFuncList.from(() -> list.stream().limit(length));
    }
    
    public LongFuncList toFuncList() {
        return build();
    }
    
    public int size() {
        return list.length();
    }
    
    public boolean isEmpty() {
        return size() == 0;
    }
    
    public LongStreamPlus stream() {
        return LongStreamPlus.from(list.stream());
    }
    
    public long get(int i) {
        return list.get(i);
    }
    
    public OptionalLong at(int i) {
        if (i < 0 || i >= list.length())
            return OptionalLong.empty();
        return OptionalLong.of(list.get(i));
    }
    
    public String toString() {
        return list.toString();
    }
    
    public int hashCode() {
        return list.hashCode();
    }
    
    public boolean equals(LongFuncListBuilder array) {
        return Objects.equals(list, array.list);
    }
}

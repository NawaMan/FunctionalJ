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
package functionalj.list.intlist;

import java.util.Objects;
import java.util.OptionalInt;
import functionalj.stream.intstream.GrowOnlyIntArray;
import functionalj.stream.intstream.IntStreamPlus;

public class IntFuncListBuilder {

    private final GrowOnlyIntArray list;

    // This constructor must not be public or protected.
    IntFuncListBuilder(GrowOnlyIntArray list) {
        this.list = list;
    }

    public IntFuncListBuilder() {
        list = new GrowOnlyIntArray();
    }

    public IntFuncListBuilder(int... values) {
        list = new GrowOnlyIntArray(values);
    }

    public IntFuncListBuilder add(int data) {
        list.add(data);
        return this;
    }

    public IntFuncList build() {
        int length = list.length();
        return IntFuncList.from(() -> list.stream().limit(length));
    }

    public IntFuncList toFuncList() {
        return build();
    }

    public int size() {
        return list.length();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public IntStreamPlus stream() {
        return IntStreamPlus.from(list.stream());
    }

    public int get(int i) {
        return list.get(i);
    }

    public OptionalInt at(int i) {
        if (i < 0 || i >= list.length())
            return OptionalInt.empty();
        return OptionalInt.of(list.get(i));
    }

    public String toString() {
        return list.toString();
    }

    public int hashCode() {
        return list.hashCode();
    }

    public boolean equals(IntFuncListBuilder array) {
        return Objects.equals(list, array.list);
    }
}

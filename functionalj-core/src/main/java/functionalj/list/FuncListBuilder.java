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
package functionalj.list;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import functionalj.stream.StreamPlus;

/**
 * Builder for FuncList.
 *
 * @param <DATA>  the data type.
 * 
 * @author nawaman
 */
public class FuncListBuilder<DATA> {
    
    private final List<DATA> list;
    
    // This constructor must not be public or protected.
    FuncListBuilder(ArrayList<DATA> list) {
        this.list = list;
    }
    
    public FuncListBuilder() {
        list = new ArrayList<DATA>();
    }
    
    @SafeVarargs
    public FuncListBuilder(DATA ... values) {
        list = new ArrayList<DATA>((values != null) ? values.length : 0);
        if (values != null) {
            for (DATA value : values) {
                list.add(value);
            }
        }
    }
    
    public FuncListBuilder<DATA> add(DATA data) {
        list.add(data);
        return this;
    }
    
    public FuncList<DATA> build() {
        int length = list.size();
        return () -> {
            return () -> list.stream().limit(length);
        };
    }
    
    public FuncList<DATA> toFuncList() {
        return build();
    }
    
    public int size() {
        return list.size();
    }
    public boolean isEmpty() {
        return size() == 0;
    }
    
    public StreamPlus<DATA> stream() {
        return StreamPlus.from(list.stream());
    }
    
    public DATA get(int i) {
        return list.get(i);
    }
    
    public Optional<DATA> at(int i) {
        if (i < 0 || i >= list.size())
            return Optional.empty();
        
        return Optional.ofNullable(list.get(i));
    }
    
    public String toString() {
        return list.toString();
    }
    
    public int hashCode() {
        return list.hashCode();
    }
    
    public boolean equals(FuncListBuilder<DATA> array) {
        return Objects.equals(list, array.list);
    }
}

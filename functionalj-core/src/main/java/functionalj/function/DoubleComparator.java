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
package functionalj.function;

import java.util.Comparator;
import java.util.Objects;

@FunctionalInterface
public interface DoubleComparator extends Comparator<Double> {
    
    public int compareDouble(double o1, double o2);
    
    public default int compare(Double o1, Double o2) {
        return compareDouble(o1, o2);
    }
    
    public default DoubleComparator reverse() {
        return (o1, o2) -> compareDouble(o2, o1);
    }
    
    public static DoubleComparator compare(DoubleComparator comparator) {
        return Objects.requireNonNull(comparator);
    }
    
    public static int compare(double a, double b, Comparator<Double> comparator) {
        if (!(comparator instanceof DoubleComparator)) {
            return comparator.compare(a, b);
        }
        return ((DoubleComparator) comparator).compareDouble(a, b);
    }
}

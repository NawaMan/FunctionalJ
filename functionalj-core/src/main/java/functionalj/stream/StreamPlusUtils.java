// ============================================================================
// Copyright (c) 2017-2025 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.stream;

import static functionalj.functions.ObjFuncs.notEqual;
import static functionalj.stream.ZipWithOption.AllowUnpaired;
import static java.lang.Boolean.TRUE;

public class StreamPlusUtils {
    
    public static <T> boolean equals(AsStreamPlus<T> stream1, AsStreamPlus<T> stream2) {
        return !stream1.streamPlus().zipWith(stream2.streamPlus(), AllowUnpaired, notEqual()).filter(TRUE::equals).findAny().isPresent();
    }
    
    public static <T> int hashCode(AsStreamPlus<T> stream) {
        return stream.streamPlus().mapToInt(e -> (e == null) ? 0 : e.hashCode()).reduce(1, (h, eh) -> 31 * h + eh);
    }
    
    public static <T> String toString(AsStreamPlus<T> stream) {
        return "[" + stream.join(", ") + "]";
    }
}

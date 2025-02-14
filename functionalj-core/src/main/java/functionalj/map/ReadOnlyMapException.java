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
package functionalj.map;

import java.util.Objects;
import java.util.stream.Collectors;

public class ReadOnlyMapException extends UnsupportedOperationException {
    
    private static final long serialVersionUID = 3110853798441402736L;
    
    public ReadOnlyMapException(@SuppressWarnings("rawtypes") ReadOnlyMap map) {
        super(message(map));
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static String message(ReadOnlyMap map) {
        Objects.requireNonNull(map);
        String mapToString = null;
        try {
            if (map.size() <= 3) {
                mapToString = "Map " + map;
            } else {
                mapToString = "Map starting with" + map.entrySet().stream().limit(3).map(e -> e.toString()).collect(Collectors.joining(","));
            }
        } catch (Exception e) {
            try {
                mapToString = "Unprintable map (@" + map.hashCode() + ")";
            } catch (Exception e2) {
                mapToString = "An unprintable map ";
            }
        }
        return mapToString + " is read only.";
    }
}

// ============================================================================
// Copyright (c) 2017-2023 Nawapunth Manusitthipol (NawaMan - http://nawaman.net)
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
package functionalj.types.elm.processor;

/**
 * Generic utility class.
 *
 * @author NawaMan -- nawa@nawaman.net
 */
public class Utils {
    
    public static String toTitleCase(String str) {
        if ((str == null) || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
    
    public static String toCamelCase(String str) {
        if ((str == null) || str.isEmpty()) {
            return str;
        }
        if (str.equals(str.toUpperCase()))
            return str.toLowerCase();
        if (str.length() <= 2)
            return str.toLowerCase();
        String firstTwo = str.substring(0, 2);
        if (firstTwo.equals(firstTwo.toUpperCase())) {
            String first = str.replaceAll("^([A-Z]+)([A-Z][^A-Z]*)$", "$1");
            String rest  = str.substring(first.length());
            return first.toLowerCase() + rest;
        } else {
            String first = str.replaceAll("^([A-Z]+[^A-Z])(.*)$", "$1");
            String rest  = str.substring(first.length());
            return first.toLowerCase() + rest;
        }
    }
}

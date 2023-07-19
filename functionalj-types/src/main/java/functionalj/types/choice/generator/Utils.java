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
package functionalj.types.choice.generator;

import static java.util.stream.Collectors.joining;
import static java.util.stream.IntStream.range;

import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import functionalj.types.choice.generator.model.Case;

public class Utils {
    
    public static String templateRange(int fromInclusive, int toExclusive, String delimiter) {
        return range(fromInclusive, toExclusive).mapToObj(i -> "%" + i + "$s").collect(joining(delimiter));
    }
    
    public static String toTitleCase(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
    
    public static String toCamelCase(String str) {
        if (str.equals(str.toUpperCase()))
            return str.toLowerCase();
        if (str.length() <= 2)
            return str.toLowerCase();
        String firstTwo = str.substring(0, 2);
        if (firstTwo.equals(firstTwo.toUpperCase())) {
            String first = str.replaceAll("^([A-Z]+)([A-Z][^A-Z]*)$", "$1");
            String rest = str.substring(first.length());
            return first.toLowerCase() + rest;
        } else {
            String first = str.replaceAll("^([A-Z]+[^A-Z])(.*)$", "$1");
            String rest = str.substring(first.length());
            return first.toLowerCase() + rest;
        }
    }
    
    public static String switchClassName(String targetName, List<Case> choices) {
        return targetName + "Switch" + choices.stream().map(c -> c.name).collect(joining());
    }
    
    public static String switchClassName(String targetName, List<Case> choices, int skip) {
        return targetName + "Switch" + choices.stream().skip(skip).map(c -> c.name).collect(joining());
    }
    
    public static <D> String toListCode(List<D> list, Function<D, String> toCode) {
        if (list == null)
            return "null";
        if (list.isEmpty())
            return "java.util.Collections.emptyList()";
        String str = list.stream().map(toCode).collect(joining(", "));
        return "java.util.Arrays.asList(" + str + ")";
    }
    
    private static final Pattern pattern = Pattern.compile("([\"\'\b\r\n]||\\\\)");
    
    public static String toStringLiteral(String str) {
        if (str == null)
            return "null";
        if (str.isEmpty())
            return "\"\"";
        Matcher      matcher = pattern.matcher(str);
        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            String original = matcher.group();
            if (original.length() == 0)
                continue;
            String replacement = findReplacement(original);
            matcher.appendReplacement(buffer, replacement);
        }
        matcher.appendTail(buffer);
        return "\"" + buffer.toString() + "\"";
    }
    
    private static String findReplacement(String original) {
        switch(original) {
            case "\"":
                return "\\\\\"";
            case "\'":
                return "\\\\\'";
            case "\\\\":
                return "\\\\";
            case "\b":
                return "\\\\b";
            case "\r":
                return "\\\\r";
            case "\n":
                return "\\\\n";
        }
        throw new UnsupportedOperationException("Unknown string escape: " + original);
    }
}

package functionalj.annotations.choice.generator;

import static java.util.stream.Collectors.joining;
import static java.util.stream.IntStream.range;

import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;

import functionalj.annotations.choice.generator.model.Case;
import lombok.val;

public class Utils {

    public static String templateRange(int fromInclusive, int toExclusive, String delimiter) {
        return range(fromInclusive, toExclusive).mapToObj(i -> "%" + i + "$s") .collect(joining(delimiter));
    }
    
    public static String toTitleCase(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
    public static String toCamelCase(String str) {
        if (str.equals(str.toUpperCase()))
            return str.toLowerCase();
        
        if (str.length() <= 2)
            return str.toLowerCase();
        
        val firstTwo = str.substring(0, 2);
        if (firstTwo.equals(firstTwo.toUpperCase())) {
            val first = str.replaceAll("^([A-Z]+)([A-Z][^A-Z]*)$", "$1");
            val rest = str.substring(first.length());
            return first.toLowerCase() + rest;
        } else {
            val first = str.replaceAll("^([A-Z]+[^A-Z])(.*)$", "$1");
            val rest = str.substring(first.length());
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
        
        val str = list.stream().map(toCode).collect(joining(", "));
        return "java.util.Arrays.asList(" + str + ")";
    }
    
    private static final Pattern pattern = Pattern.compile("([\"\'\b\r\n]||\\\\)");
    
    public static String toStringLiteral(String str) {
        if (str == null)
            return "null";
        if (str.isEmpty())
            return "\"\"";
        
        val matcher = pattern.matcher(str);
        val buffer  = new StringBuffer();
        while (matcher.find()) {
            val original = matcher.group();
            if(original.length() == 0)
                continue;
            
            val replacement = findReplacement(original);
            matcher.appendReplacement(buffer, replacement);
        }
        matcher.appendTail(buffer);
        return "\"" + buffer.toString() + "\"";
    }
    
    private static String findReplacement(String original) {
        switch (original) {
            case "\"":   return "\\\\\"";
            case "\'":   return "\\\\\'";
            case "\\\\": return "\\\\";
            case "\b":   return "\\\\b";
            case "\r":   return "\\\\r";
            case "\n":   return "\\\\n";
        }
        throw new UnsupportedOperationException("Unknown string escape: " + original);
    }
    
}

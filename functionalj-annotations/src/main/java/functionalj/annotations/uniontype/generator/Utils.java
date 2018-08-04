package functionalj.annotations.uniontype.generator;

import static java.util.stream.Collectors.joining;

import java.util.List;

import lombok.val;

public class Utils {
    
    public static String toTitleCase(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
    public static String toCamelCase(String str) {
        if (str.equals(str.toUpperCase()))
            return str.toLowerCase();
        
        val first = str.replaceAll("^([A-Z]+)[A-Z][^A-Z].*$", "$1");
        if (first.equals(str))
            return first.toLowerCase();
        
        val rest = str.replaceAll("^[A-Z]+([A-Z][^A-Z].*)$", "$1");
        return first.toLowerCase() + rest;
    }
    
    public static String switchClassName(String targetName, List<Choice> choices) {
        return targetName + "Switch" + choices.stream().map(c -> c.name).collect(joining());
    }
    public static String switchClassName(String targetName, List<Choice> choices, int skip) {
        return targetName + "Switch" + choices.stream().skip(skip).map(c -> c.name).collect(joining());
    }
    
}

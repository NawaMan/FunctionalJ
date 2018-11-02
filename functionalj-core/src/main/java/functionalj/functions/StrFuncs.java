package functionalj.functions;

import static functionalj.function.Absent.__;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

import functionalj.function.Func1;
import functionalj.function.Func2;
import functionalj.function.Func3;
import functionalj.function.Func4;
import functionalj.function.Func5;
import functionalj.function.Func6;

@SuppressWarnings("javadoc")
public class StrFuncs {
    
    @SuppressWarnings("unused")
	private static final Map<Integer, String> indentTabs = new ConcurrentHashMap<>();
    
    /**
     * Return the string representation of the given object or null if the object is null.
     * 
     * @param inputObject the input objects.
     * @return the string representation of the input object.
     */
    public static String toStr(Object inputObject) {
        return (inputObject == null) ? null : String.valueOf(inputObject);
    }
    
    /**
     * Returns a function that return the string representation of the given object or null if the object is null.
     * 
     * @param <I>  the input data type.
     * @return the function.
     */
    public static <I> Func1<I, String> toStr() {
        return (i) -> toStr(i);
    }
    
    /**
     * Returns a predicate to check if the given string is not null and not empty.
     * 
     * @return the predicate.
     */
    public static Predicate<? super String> strNotNullOrEmpty() {
        return str -> (str != null) && !str.isEmpty();
    }
    
    public static <I1, I2> Func2<I1, I2, String> concat2() {
        return (i1, i2) -> toStr(i1) + toStr(i2);
    }
    public static <I1, I2, I3> Func3<I1, I2, I3, String> concat3() {
        return (i1, i2, i3) -> toStr(i1) + toStr(i2) + toStr(i3);
    }
    public static <I1, I2, I3, I4> Func4<I1, I2, I3, I4, String> concat4() {
        return (i1, i2, i3, i4) -> toStr(i1) + toStr(i2) + toStr(i3) + toStr(i4);
    }
    public static <I1, I2, I3, I4, I5> Func5<I1, I2, I3, I4, I5, String> concat5() {
        return (i1, i2, i3, i4, i5) -> toStr(i1) + toStr(i2) + toStr(i3) + toStr(i4) + toStr(i5);
    }
    public static <I1, I2, I3, I4, I5, I6> Func6<I1, I2, I3, I4, I5, I6, String> concat6() {
        return (i1, i2, i3, i4, i5, i6) -> toStr(i1) + toStr(i2) + toStr(i3) + toStr(i4) + toStr(i5) + toStr(i6);
    }
    
    public static <I1, I2> Func2<I1, I2, String> join2(String delimiter) {
        return (i1, i2) -> 
                    toStr(i1) + delimiter + 
                    toStr(i2);
    }
    public static <I1, I2, I3> Func3<I1, I2, I3, String> join3(String delimiter) {
        return (i1, i2, i3) -> 
                    toStr(i1) + delimiter + 
                    toStr(i2) + delimiter + 
                    toStr(i3);
    }
    public static <I1, I2, I3, I4> Func4<I1, I2, I3, I4, String> join4(String delimiter) {
        return (i1, i2, i3, i4) -> 
                    toStr(i1) + delimiter + 
                    toStr(i2) + delimiter + 
                    toStr(i3) + delimiter + 
                    toStr(i4);
    }
    public static <I1, I2, I3, I4, I5> Func5<I1, I2, I3, I4, I5, String> join5(String delimiter) {
        return (i1, i2, i3, i4, i5) -> 
                    toStr(i1) + delimiter + 
                    toStr(i2) + delimiter + 
                    toStr(i3) + delimiter + 
                    toStr(i4) + delimiter + 
                    toStr(i5);
    }
    public static <I1, I2, I3, I4, I5, I6> Func6<I1, I2, I3, I4, I5, I6, String> join6(String delimiter) {
        return (i1, i2, i3, i4, i5, i6) -> 
                    toStr(i1) + delimiter + 
                    toStr(i2) + delimiter + 
                    toStr(i3) + delimiter + 
                    toStr(i4) + delimiter + 
                    toStr(i5) + delimiter + 
                    toStr(i6);
    }
    
    /**
     * Returns a function that add prefix and suffix to the given input.
     * 
     * @param <I>  the input data type.
     * @param prefix  the prefix.
     * @param suffix  the suffix.
     * @return  the function.
     */
    @SuppressWarnings("unchecked")
    public static <I> Func1<I, String> wrapWith(String prefix, String suffix) {
        return (Func1<I, String>)concat3().bind(prefix, __, suffix);
    }
    
    /**
     * Returns a function that add prefix to the given input.
     * 
     * @param <I>  the input data type.
     * @param prefix  the prefix.
     * @return  the function.
     */
    @SuppressWarnings("unchecked")
    public static <I> Func1<I, String> prependWith(String prefix) {
        return (Func1<I, String>)concat3().bind(prefix, __, "");
    }
    
    /**
     * Returns a function that add suffix to the given input.
     * 
     * @param <I>  the input data type.
     * @param suffix  the suffix.
     * @return  the function.
     */
    @SuppressWarnings("unchecked")
    public static <I> Func1<I, String> appendWith(String suffix) {
        return (Func1<I, String>)concat3().bind("", __, suffix);
    }
    
    public static <I1> Func1<I1, String> formatWith1(String template) {
        return (i1) -> String.format(template, i1);
    }
    
    public static <I1, I2> Func2<I1, I2, String> formatWith2(String template) {
        return (i1, i2) -> String.format(template, i1, i2);
    }
    
    public static <I1, I2, I3> Func3<I1, I2, I3, String> formatWith3(String template) {
        return (i1, i2, i3) -> String.format(template, i1, i2, i3);
    }
    
    public static <I1, I2, I3, I4> Func4<I1, I2, I3, I4, String> formatWith4(String template) {
        return (i1, i2, i3, i4) -> String.format(template, i1, i2, i3, i4);
    }
    
    public static <I1, I2, I3, I4, I5> Func5<I1, I2, I3, I4, I5, String> formatWith5(String template) {
        return (i1, i2, i3, i4, i5) -> String.format(template, i1, i2, i3, i4, i5);
    }
    
    public static <I1, I2, I3, I4, I5, I6> Func6<I1, I2, I3, I4, I5, I6, String> formatWith6(String template) {
        return (i1, i2, i3, i4, i5, i6) -> String.format(template, i1, i2, i3, i4, i5, i6);
    }
    
    public static <I1> Func2<String, I1, String> strFormat1() {
        return (template, i1) -> String.format(template, i1);
    }
    
    public static <I1, I2> Func3<String, I1, I2, String> strFormat2() {
        return (template, i1, i2) -> String.format(template, i1, i2);
    }
    public static <I1, I2, I3> Func4<String, I1, I2, I3, String> strFormat3() {
        return (template, i1, i2, i3) -> String.format(template, i1, i2, i3);
    }
    
    public static <I1, I2, I3, I4> Func5<String, I1, I2, I3, I4, String> strFormat4() {
        return (template, i1, i2, i3, i4) -> String.format(template, i1, i2, i3, i4);
    }
    
    public static <I1, I2, I3, I4, I5> Func6<String, I1, I2, I3, I4, I5, String> strFormat5() {
        return (template, i1, i2, i3, i4, i5) -> String.format(template, i1, i2, i3, i4, i5);
    }
    
    // TODO Rethink this at some point ..... should this be constance when no generic?
    
    public static Func3<String, String, String, String> replaceAll() {
        return (str, regex, replacement) -> str.replaceAll(regex, replacement);
    }
    public static Func1<String, String> replaceAll(String regex, String replacement) {
        return (str) -> str.replaceAll(regex, replacement);
    }
    
    // TODO Improve this with template() ...
    
    // Repeat
    // Indent
    // Padding
    // match
    
}

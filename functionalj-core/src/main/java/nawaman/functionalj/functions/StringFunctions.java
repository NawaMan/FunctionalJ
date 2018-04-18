package nawaman.functionalj.functions;

import static nawaman.functionalj.FunctionalStrings.stringOf;
import static nawaman.functionalj.functions.Absent.__;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.val;
import lombok.experimental.UtilityClass;
import nawaman.functionalj.FunctionalStrings;

@UtilityClass
public class StringFunctions {
    
    private static final Map<Integer, String> indentTabs = new ConcurrentHashMap<>();
    
    public <I> Func1<I, String> toStr() {
        return (i) -> stringOf(i);
    }
    public <I1, I2> Func2<I1, I2, String> concat2() {
        return (i1, i2) -> stringOf(i1) + stringOf(i2);
    }
    public <I1, I2, I3> Func3<I1, I2, I3, String> concat3() {
        return (i1, i2, i3) -> stringOf(i1) + stringOf(i2) + stringOf(i3);
    }
    @SuppressWarnings("unchecked")
    public <I> Func1<I, String> wrapWith(String prefix, String suffix) {
        return (Func1<I, String>)concat3().apply(prefix, __, suffix);
    }
    @SuppressWarnings("unchecked")
    public <I> Func1<I, String> prependWith(String prefix) {
        return (Func1<I, String>)concat3().apply(prefix, __, "");
    }
    @SuppressWarnings("unchecked")
    public <I> Func1<I, String> appendWith(String suffix) {
        return (Func1<I, String>)concat3().apply("", __, suffix);
    }
    
    public <I1> Func1<I1, String> format1With(String template) {
        return (i1) -> String.format(template, i1);
    }
    
    public <I1> Func2<String, I1, String> strFormat1() {
        return (template, i1) -> String.format(template, i1);
    }
    
    public <I1, I2> Func2<I1, I2, String> format2With(String template) {
        return (i1, i2) -> String.format(template, i1, i2);
    }
    
    public <I1, I2> Func3<String, I1, I2, String> strFormat2() {
        return (template, i1, i2) -> String.format(template, i1, i2);
    }
    
    // TODO - More format
    // Repeat
    // Indent
    // Padding
    
}

package nawaman.functionalj.functions;

import static nawaman.functionalj.functions.Absent.__;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StringFuncs {
    
    public <I1, I2> Func2<I1, I2, String> strConcat2() {
        return (i1, i2) -> String.valueOf(i1) + String.valueOf(i2);
    }
    public <I1, I2, I3> Func3<I1, I2, I3, String> strConcat3() {
        return (i1, i2, i3) -> String.valueOf(i1) + String.valueOf(i2) + String.valueOf(i3);
    }
    @SuppressWarnings("unchecked")
    public <I> Func1<I, String> wrapWith(String prefix, String suffix) {
        return (Func1<I, String>)strConcat3().apply(prefix, __, suffix);
    }
    
    public <I1> Func1<I1, String> strFormat(String template) {
        return (i1) -> String.format(template, i1);
    }
    
    public <I1> Func2<String, I1, String> strFormat() {
        return (template, i1) -> String.format(template, i1);
    }
    
    public <I1, I2> Func2<I1, I2, String> strFormat2(String template) {
        return (i1, i2) -> String.format(template, i1, i2);
    }
    
    public <I1, I2> Func3<String, I1, I2, String> strFormat2() {
        return (template, i1, i2) -> String.format(template, i1, i2);
    }
    
}

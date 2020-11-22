package functionalj.lens.lenses.java.time;

import java.time.chrono.Era;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.function.Function;

import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.IntegerAccessPrimitive;
import functionalj.lens.lenses.StringAccess;
import lombok.val;

@FunctionalInterface
public interface EraAccess<HOST, ERA extends Era>
                    extends AnyAccess             <HOST, ERA>
                    ,       TemporalAccessorAccess<HOST, ERA>
                    ,       TemporalAdjusterAccess<HOST, ERA> {
    
    public static <H, E extends Era> EraAccess<H, E> of(Function<H, E> func) {
        return func::apply;
    }
    
    public default EraAccess<HOST, ERA> newAccess(Function<HOST, ERA> accessToValue) {
        return host -> accessToValue.apply(host);
    }
    
    public default IntegerAccessPrimitive<HOST> getValue() {
        return host -> {
            var value = apply(host);
            return value.getValue();
        };
    }
    public default StringAccess<HOST> getDisplayName(TextStyle style, Locale locale) {
        return host -> {
            var value = apply(host);
            return value.getDisplayName(style, locale);
        };
    }
    
}

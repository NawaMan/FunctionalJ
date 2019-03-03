package functionalj.lens.lenses.java.time;

import java.time.chrono.Era;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.function.Function;

import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.IntegerAccess;
import functionalj.lens.lenses.StringAccess;
import lombok.val;

@FunctionalInterface
public interface EraAccess<HOST>
                    extends
                        AnyAccess<HOST, Era>,
                        TemporalAccessorAccess<HOST, Era>,
                        TemporalAdjusterAccess<HOST, Era> {
    
    public default EraAccess<HOST> newAccess(Function<HOST, Era> accessToValue) {
        return host -> accessToValue.apply(host);
    }
    
    public default IntegerAccess<HOST> getValue() {
        return host -> {
            val value = apply(host);
            return value.getValue();
        };
    }
    public default StringAccess<HOST> getDisplayName(TextStyle style, Locale locale) {
        return host -> {
            val value = apply(host);
            return value.getDisplayName(style, locale);
        };
    }
    
}

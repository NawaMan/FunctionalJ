package functionalj.lens.lenses.java.time;

import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.function.Function;

import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.StringAccess;
import lombok.val;

@FunctionalInterface
public interface ZoneIdAccess<HOST, ZONE_ID extends ZoneId>
                    extends AnyAccess<HOST, ZONE_ID> {
    
    public static <H, Z extends ZoneId> ZoneIdAccess<H, Z> of(Function<H, Z> func) {
        return func::apply;
    }
    
    public default StringAccess<HOST> getId() {
        return host -> {
            val value = apply(host);
            return value.getId();
        };
    }
    public default StringAccess<HOST> getDisplayName(TextStyle style, Locale locale) {
        return host -> {
            val value = apply(host);
            return value.getDisplayName(style, locale);
        };
    }
    
    public default ZoneRulesAccess<HOST> getRules() {
        return host -> {
            val value = apply(host);
            return value.getRules();
        };
    }
    
    public default ZoneIdAccess<HOST, ZoneId> normalized() {
        return host -> {
            val value = apply(host);
            return value.normalized();
        };
    }

}

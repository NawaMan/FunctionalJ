package functionalj.lens.lenses.time;

import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Locale;

import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.StringAccess;
import lombok.val;

@FunctionalInterface
public interface ZoneIdAccess<HOST, ZONE_ID extends ZoneId>
                    extends AnyAccess<HOST, ZONE_ID> {
                        
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
    
    // TODO
//    public abstract ZoneRules getRules();
    
    public default ZoneIdAccess<HOST, ZoneId> normalized() {
        return host -> {
            val value = apply(host);
            return value.normalized();
        };
    }

}

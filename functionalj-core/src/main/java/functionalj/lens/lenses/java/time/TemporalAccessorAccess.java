package functionalj.lens.lenses.java.time;

import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalQuery;
import java.util.function.Function;

import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.BooleanAccess;
import functionalj.lens.lenses.IntegerAccessPrimitive;
import functionalj.lens.lenses.LongAccess;
import lombok.val;

public interface TemporalAccessorAccess<HOST, TEMPORAL_ACCESSOR extends TemporalAccessor>
                    extends AnyAccess<HOST, TEMPORAL_ACCESSOR> {
    
    public static <H, T extends TemporalAccessor> TemporalAccessorAccess<H, T> of(Function<H, T> func) {
        return func::apply;
    }
    
    public default BooleanAccess<HOST> thatIsSupported(TemporalField field) {
        return host -> {
            val value = apply(host);
            return value.isSupported(field);
        };
    }
    
    public default ValueRangeAccess<HOST> range(TemporalField field) {
        return host -> {
            val value = apply(host);
            return value.range(field);
        };
    }
    public default IntegerAccessPrimitive<HOST> get(TemporalField field) {
        return host -> {
            val value = apply(host);
            return value.get(field);
        };
    }
    public default LongAccess<HOST> getLong(TemporalField field) {
        return host -> {
            val value = apply(host);
            return value.getLong(field);
        };
    }
    
    public default <R> AnyAccess<HOST, R> query(TemporalQuery<R> query) {
        return host -> {
            val value = apply(host);
            return value.query(query);
        };
    }
    
}

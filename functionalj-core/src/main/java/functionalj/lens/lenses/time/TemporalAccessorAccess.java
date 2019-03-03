package functionalj.lens.lenses.time;

import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalQuery;

import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.BooleanAccess;
import functionalj.lens.lenses.IntegerAccess;
import functionalj.lens.lenses.LongAccess;
import lombok.val;

public interface TemporalAccessorAccess<HOST, TEMPORAL_ACCESSOR extends TemporalAccessor>
                    extends
                        AnyAccess<HOST, TEMPORAL_ACCESSOR> {
    
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
    public default IntegerAccess<HOST> get(TemporalField field) {
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

package functionalj.lens.lenses.java.time;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.zone.ZoneOffsetTransitionRule.TimeDefinition;
import java.util.function.Function;
import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.BooleanAccess;
import functionalj.lens.lenses.BooleanAccessPrimitive;
import functionalj.lens.lenses.ConcreteAccess;
import functionalj.lens.lenses.IntegerAccessPrimitive;
import lombok.val;

@FunctionalInterface
public interface TimeDefinitionAccess<HOST> extends AnyAccess<HOST, TimeDefinition>, ConcreteAccess<HOST, TimeDefinition, TimeDefinitionAccess<HOST>> {
    
    public static <H> TimeDefinitionAccess<H> of(Function<H, TimeDefinition> func) {
        return func::apply;
    }
    
    public default TimeDefinitionAccess<HOST> newAccess(Function<HOST, TimeDefinition> accessToValue) {
        return host -> accessToValue.apply(host);
    }
    
    public default BooleanAccessPrimitive<HOST> isUtc() {
        return host -> {
            val value = apply(host);
            return value == TimeDefinition.UTC;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> isWall() {
        return host -> {
            val value = apply(host);
            return value == TimeDefinition.WALL;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> isStandard() {
        return host -> {
            val value = apply(host);
            return value == TimeDefinition.STANDARD;
        };
    }
    
    public default LocalDateTimeAccess<HOST> createDateTime(LocalDateTime dateTime, ZoneOffset standardOffset, ZoneOffset wallOffset) {
        return host -> {
            val value = apply(host);
            return value.createDateTime(dateTime, standardOffset, wallOffset);
        };
    }
    
    public default IntegerAccessPrimitive<HOST> compareTo(TimeDefinition other) {
        return host -> {
            val value = apply(host);
            return value.compareTo(other);
        };
    }
    
    public default BooleanAccess<HOST> thatGreaterThan(TimeDefinition anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) > 0);
    }
    
    public default BooleanAccess<HOST> thatLessThan(TimeDefinition anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) < 0);
    }
    
    public default BooleanAccess<HOST> thatGreaterThanOrEqualsTo(TimeDefinition anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) >= 0);
    }
    
    public default BooleanAccess<HOST> thatLessThanOrEqualsTo(TimeDefinition anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) <= 0);
    }
}

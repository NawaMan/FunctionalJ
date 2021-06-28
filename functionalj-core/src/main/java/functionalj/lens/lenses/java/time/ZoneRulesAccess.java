package functionalj.lens.lenses.java.time;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.zone.ZoneOffsetTransition;
import java.time.zone.ZoneOffsetTransitionRule;
import java.time.zone.ZoneRules;
import java.util.function.Function;

import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.BooleanAccessPrimitive;
import functionalj.lens.lenses.ConcreteAccess;
import functionalj.lens.lenses.ListAccess;
import lombok.val;


@FunctionalInterface
public interface ZoneRulesAccess<HOST>
                    extends AnyAccess     <HOST, ZoneRules>
                    ,       ConcreteAccess<HOST, ZoneRules, ZoneRulesAccess<HOST>> {
    
    public static <H> ZoneRulesAccess<H> of(Function<H, ZoneRules> func) {
        return func::apply;
    }
    
    public default ZoneRulesAccess<HOST> newAccess(Function<HOST, ZoneRules> accessToValue) {
        return accessToValue::apply;
    }
    
    public default BooleanAccessPrimitive<HOST> isFixedOffset() {
        return host -> {
            val value = apply(host);
            return value.isFixedOffset();
        };
    }
    public default ZoneOffsetAccess<HOST> getOffset(Instant instant) {
        return host -> {
            val value = apply(host);
            return value.getOffset(instant);
        };
    }
    
    public default ZoneOffsetAccess<HOST> getOffset(LocalDateTime localDateTime) {
        return host -> {
            val value = apply(host);
            return value.getOffset(localDateTime);
        };
    }
    
    public default ListAccess<HOST, ZoneOffset, ZoneOffsetAccess<HOST>> getValidOffsets(LocalDateTime localDateTime) {
        return ListAccess.of(
                (HOST host) -> ZoneRulesAccess.this.apply(host).getValidOffsets(localDateTime),
                ZoneOffsetAccess::of);
    }

    public default ZoneOffsetTransitionAccess<HOST> getTransition(LocalDateTime localDateTime) {
        return host -> {
            val value = apply(host);
            return value.getTransition(localDateTime);
        };
    }
    public default ZoneOffsetAccess<HOST> getStandardOffset(Instant instant) {
        return host -> {
            val value = apply(host);
            return value.getStandardOffset(instant);
        };
    }
    public default DurationAccess<HOST> getDaylightSavings(Instant instant) {
        return host -> {
            val value = apply(host);
            return value.getDaylightSavings(instant);
        };
    }
    public default BooleanAccessPrimitive<HOST> isDaylightSavings(Instant instant) {
        return host -> {
            val value = apply(host);
            return value.isDaylightSavings(instant);
        };
    }
    public default BooleanAccessPrimitive<HOST> isValidOffset(LocalDateTime localDateTime, ZoneOffset offset) {
        return host -> {
            val value = apply(host);
            return value.isValidOffset(localDateTime, offset);
        };
    }
    public default ZoneOffsetTransitionAccess<HOST> nextTransition(Instant instant) {
        return host -> {
            val value = apply(host);
            return value.nextTransition(instant);
        };
    }
    public default ZoneOffsetTransitionAccess<HOST> previousTransition(Instant instant) {
        return host -> {
            val value = apply(host);
            return value.previousTransition(instant);
        };
    }
    public default ListAccess<HOST, ZoneOffsetTransition, ZoneOffsetTransitionAccess<HOST>> getTransitions() {
        return ListAccess.of(
                (HOST host) -> ZoneRulesAccess.this.apply(host).getTransitions(),
                ZoneOffsetTransitionAccess::of);
    }
    public default ListAccess<HOST, ZoneOffsetTransitionRule, ZoneOffsetTransitionRuleAccess<HOST>> getTransitionRules() {
        return ListAccess.of(
                (HOST host) -> ZoneRulesAccess.this.apply(host).getTransitionRules(),
                ZoneOffsetTransitionRuleAccess::of);
    }
}

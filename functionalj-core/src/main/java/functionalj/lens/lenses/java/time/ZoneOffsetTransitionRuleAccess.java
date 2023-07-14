package functionalj.lens.lenses.java.time;

import java.time.zone.ZoneOffsetTransitionRule;
import java.util.function.Function;
import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.BooleanAccessPrimitive;
import functionalj.lens.lenses.ConcreteAccess;
import functionalj.lens.lenses.IntegerAccessPrimitive;
import lombok.val;

public interface ZoneOffsetTransitionRuleAccess<HOST> extends AnyAccess<HOST, ZoneOffsetTransitionRule>, ConcreteAccess<HOST, ZoneOffsetTransitionRule, ZoneOffsetTransitionRuleAccess<HOST>> {

    public static <H> ZoneOffsetTransitionRuleAccess<H> of(Function<H, ZoneOffsetTransitionRule> func) {
        return func::apply;
    }

    public default ZoneOffsetTransitionRuleAccess<HOST> newAccess(Function<HOST, ZoneOffsetTransitionRule> accessToValue) {
        return ZoneOffsetTransitionRuleAccess.of(accessToValue);
    }

    public default MonthAccess<HOST> getMonth() {
        return host -> {
            val value = apply(host);
            return value.getMonth();
        };
    }

    public default IntegerAccessPrimitive<HOST> getDayOfMonthIndicator() {
        return host -> {
            val value = apply(host);
            return value.getDayOfMonthIndicator();
        };
    }

    public default DayOfWeekAccess<HOST> getDayOfWeek() {
        return host -> {
            val value = apply(host);
            return value.getDayOfWeek();
        };
    }

    public default LocalTimeAccess<HOST> getLocalTime() {
        return host -> {
            val value = apply(host);
            return value.getLocalTime();
        };
    }

    public default BooleanAccessPrimitive<HOST> isMidnightEndOfDay() {
        return host -> {
            val value = apply(host);
            return value.isMidnightEndOfDay();
        };
    }

    public default TimeDefinitionAccess<HOST> getTimeDefinition() {
        return host -> {
            val value = apply(host);
            return value.getTimeDefinition();
        };
    }

    public default ZoneOffsetAccess<HOST> getStandardOffset() {
        return host -> {
            val value = apply(host);
            return value.getStandardOffset();
        };
    }

    public default ZoneOffsetAccess<HOST> getOffsetBefore() {
        return host -> {
            val value = apply(host);
            return value.getOffsetBefore();
        };
    }

    public default ZoneOffsetAccess<HOST> getOffsetAfter() {
        return host -> {
            val value = apply(host);
            return value.getOffsetAfter();
        };
    }

    public default ZoneOffsetTransitionAccess<HOST> createTransition(int year) {
        return host -> {
            val value = apply(host);
            return value.createTransition(year);
        };
    }
}

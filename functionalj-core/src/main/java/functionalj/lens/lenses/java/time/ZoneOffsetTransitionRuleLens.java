package functionalj.lens.lenses.java.time;

import java.time.zone.ZoneOffsetTransitionRule;
import functionalj.lens.core.LensSpec;
import functionalj.lens.lenses.ObjectLensImpl;

public class ZoneOffsetTransitionRuleLens<HOST> extends ObjectLensImpl<HOST, ZoneOffsetTransitionRule> implements ZoneOffsetTransitionRuleAccess<HOST> {
    
    public static final ZoneOffsetTransitionRuleLens<ZoneOffsetTransitionRule> theZoneOffsetTransitionRule = new ZoneOffsetTransitionRuleLens<ZoneOffsetTransitionRule>(LensSpec.of(ZoneOffsetTransitionRule.class));
    
    public static <H> ZoneOffsetTransitionRuleLens<H> of(String name, LensSpec<H, ZoneOffsetTransitionRule> spec) {
        return new ZoneOffsetTransitionRuleLens<H>(name, spec);
    }
    
    public static <H> ZoneOffsetTransitionRuleLens<H> of(LensSpec<H, ZoneOffsetTransitionRule> spec) {
        return new ZoneOffsetTransitionRuleLens<H>(spec);
    }
    
    public ZoneOffsetTransitionRuleLens(String name, LensSpec<HOST, ZoneOffsetTransitionRule> spec) {
        super(name, spec);
    }
    
    public ZoneOffsetTransitionRuleLens(LensSpec<HOST, ZoneOffsetTransitionRule> spec) {
        this(null, spec);
    }
    // TODO - Really don't think we need these ...
    // 
    // /**
    // * The month of the month-day of the first day of the cutover week.
    // * The actual date will be adjusted by the dowChange field.
    // */
    // private final Month month;
    // /**
    // * The day-of-month of the month-day of the cutover week.
    // * If positive, it is the start of the week where the cutover can occur.
    // * If negative, it represents the end of the week where cutover can occur.
    // * The value is the number of days from the end of the month, such that
    // * {@code -1} is the last day of the month, {@code -2} is the second
    // * to last day, and so on.
    // */
    // private final byte dom;
    // /**
    // * The cutover day-of-week, null to retain the day-of-month.
    // */
    // private final DayOfWeek dow;
    // /**
    // * The cutover time in the 'before' offset.
    // */
    // private final LocalTime time;
    // /**
    // * Whether the cutover time is midnight at the end of day.
    // */
    // private final boolean timeEndOfDay;
    // /**
    // * The definition of how the local time should be interpreted.
    // */
    // private final TimeDefinition timeDefinition;
    // /**
    // * The standard offset at the cutover.
    // */
    // private final ZoneOffset standardOffset;
    // /**
    // * The offset before the cutover.
    // */
    // private final ZoneOffset offsetBefore;
    // /**
    // * The offset after the cutover.
    // */
    // private final ZoneOffset offsetAfter;
}

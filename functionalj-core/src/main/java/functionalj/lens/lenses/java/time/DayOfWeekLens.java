package functionalj.lens.lenses.java.time;

import java.time.DayOfWeek;
import functionalj.function.Func1;
import functionalj.lens.core.LensSpec;
import functionalj.lens.lenses.ObjectLensImpl;

public class DayOfWeekLens<HOST> extends ObjectLensImpl<HOST, DayOfWeek> implements DayOfWeekAccess<HOST> {

    public static final DayOfWeekLens<DayOfWeek> theDayOfWeek = new DayOfWeekLens<DayOfWeek>(LensSpec.of(DayOfWeek.class));

    public static <H> DayOfWeekLens<H> of(String name, LensSpec<H, DayOfWeek> spec) {
        return new DayOfWeekLens<H>(name, spec);
    }

    public static <H> DayOfWeekLens<H> of(LensSpec<H, DayOfWeek> spec) {
        return new DayOfWeekLens<H>(spec);
    }

    public DayOfWeekLens(String name, LensSpec<HOST, DayOfWeek> spec) {
        super(name, spec);
    }

    public DayOfWeekLens(LensSpec<HOST, DayOfWeek> spec) {
        this(null, spec);
    }

    public final Func1<HOST, HOST> toSunday = changeTo(DayOfWeek.SUNDAY);

    public final Func1<HOST, HOST> toMonday = changeTo(DayOfWeek.MONDAY);

    public final Func1<HOST, HOST> toTuesday = changeTo(DayOfWeek.TUESDAY);

    public final Func1<HOST, HOST> toWednesday = changeTo(DayOfWeek.WEDNESDAY);

    public final Func1<HOST, HOST> toThursday = changeTo(DayOfWeek.THURSDAY);

    public final Func1<HOST, HOST> toFriday = changeTo(DayOfWeek.FRIDAY);

    public final Func1<HOST, HOST> toSaturday = changeTo(DayOfWeek.SATURDAY);
}

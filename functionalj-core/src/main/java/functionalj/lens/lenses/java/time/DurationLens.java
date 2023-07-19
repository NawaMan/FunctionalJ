package functionalj.lens.lenses.java.time;

import java.time.DayOfWeek;
import java.time.Duration;
import functionalj.lens.core.LensSpec;
import functionalj.lens.lenses.IntegerLens;
import functionalj.lens.lenses.LongLens;
import functionalj.lens.lenses.ObjectLensImpl;

public class DurationLens<HOST> extends ObjectLensImpl<HOST, Duration> implements DurationAccess<HOST> {
    
    public static final DurationLens<Duration> theDuration = new DurationLens<Duration>(LensSpec.of(Duration.class));
    
    public final LongLens<HOST> seconds = createSubLens(Duration::getSeconds, Duration::withSeconds, LongLens::of);
    
    public final IntegerLens<HOST> nanos = createSubLens(Duration::getNano, Duration::withNanos, IntegerLens::of);
    
    public static <H> DayOfWeekLens<H> of(String name, LensSpec<H, DayOfWeek> spec) {
        return new DayOfWeekLens<H>(name, spec);
    }
    
    public static <H> DayOfWeekLens<H> of(LensSpec<H, DayOfWeek> spec) {
        return new DayOfWeekLens<H>(spec);
    }
    
    public DurationLens(String name, LensSpec<HOST, Duration> spec) {
        super(name, spec);
    }
    
    public DurationLens(LensSpec<HOST, Duration> spec) {
        this(null, spec);
    }
}

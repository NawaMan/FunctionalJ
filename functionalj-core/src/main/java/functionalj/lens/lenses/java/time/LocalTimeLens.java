package functionalj.lens.lenses.java.time;

import java.time.LocalTime;
import functionalj.lens.core.LensSpec;
import functionalj.lens.lenses.IntegerLens;
import functionalj.lens.lenses.ObjectLensImpl;

public class LocalTimeLens<HOST> extends ObjectLensImpl<HOST, LocalTime> implements LocalTimeAccess<HOST> {
    
    public static final LocalTimeLens<LocalTime> theLocalTime = new LocalTimeLens<LocalTime>(LensSpec.of(LocalTime.class));
    
    public final IntegerLens<HOST> hour = createSubLens(LocalTime::getHour, LocalTime::withHour, IntegerLens::of);
    
    public final IntegerLens<HOST> minute = createSubLens(LocalTime::getMinute, LocalTime::withMinute, IntegerLens::of);
    
    public final IntegerLens<HOST> second = createSubLens(LocalTime::getSecond, LocalTime::withSecond, IntegerLens::of);
    
    public final IntegerLens<HOST> nano = createSubLens(LocalTime::getNano, LocalTime::withNano, IntegerLens::of);
    
    public static <H> LocalTimeLens<H> of(String name, LensSpec<H, LocalTime> spec) {
        return new LocalTimeLens<H>(name, spec);
    }
    
    public static <H> LocalTimeLens<H> of(LensSpec<H, LocalTime> spec) {
        return new LocalTimeLens<H>(spec);
    }
    
    public LocalTimeLens(String name, LensSpec<HOST, LocalTime> spec) {
        super(name, spec);
    }
    
    public LocalTimeLens(LensSpec<HOST, LocalTime> spec) {
        this(null, spec);
    }
}

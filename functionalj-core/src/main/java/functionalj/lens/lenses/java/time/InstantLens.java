package functionalj.lens.lenses.java.time;

import java.time.Instant;
import java.time.temporal.ChronoField;
import functionalj.lens.core.LensSpec;
import functionalj.lens.lenses.IntegerLens;
import functionalj.lens.lenses.LongLens;
import functionalj.lens.lenses.ObjectLensImpl;

public class InstantLens<HOST> extends ObjectLensImpl<HOST, Instant> implements InstantAccess<HOST> {

    public static final InstantLens<Instant> theInstant = new InstantLens<Instant>(LensSpec.of(Instant.class));

    public final LongLens<HOST> seconds = createSubLens(Instant::getEpochSecond, (inst, secs) -> inst.with(ChronoField.INSTANT_SECONDS, secs), LongLens::of);

    public final IntegerLens<HOST> nanos = createSubLens(Instant::getNano, (inst, secs) -> inst.with(ChronoField.NANO_OF_SECOND, secs), IntegerLens::of);

    public static <H> InstantLens<H> of(String name, LensSpec<H, Instant> spec) {
        return new InstantLens<H>(name, spec);
    }

    public static <H> InstantLens<H> of(LensSpec<H, Instant> spec) {
        return new InstantLens<H>(spec);
    }

    public InstantLens(String name, LensSpec<HOST, Instant> spec) {
        super(name, spec);
    }

    public InstantLens(LensSpec<HOST, Instant> spec) {
        this(null, spec);
    }
}

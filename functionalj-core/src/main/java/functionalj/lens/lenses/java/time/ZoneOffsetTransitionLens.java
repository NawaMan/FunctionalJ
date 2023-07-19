package functionalj.lens.lenses.java.time;

import java.time.zone.ZoneOffsetTransition;
import functionalj.lens.core.LensSpec;
import functionalj.lens.lenses.ObjectLensImpl;

public class ZoneOffsetTransitionLens<HOST> extends ObjectLensImpl<HOST, ZoneOffsetTransition> implements ZoneOffsetTransitionAccess<HOST> {
    
    public static final ZoneOffsetTransitionLens<ZoneOffsetTransition> theZoneOffsetTransition = new ZoneOffsetTransitionLens<ZoneOffsetTransition>(LensSpec.of(ZoneOffsetTransition.class));
    
    public static <H> ZoneOffsetTransitionLens<H> of(String name, LensSpec<H, ZoneOffsetTransition> spec) {
        return new ZoneOffsetTransitionLens<H>(name, spec);
    }
    
    public static <H> ZoneOffsetTransitionLens<H> of(LensSpec<H, ZoneOffsetTransition> spec) {
        return new ZoneOffsetTransitionLens<H>(spec);
    }
    
    public ZoneOffsetTransitionLens(String name, LensSpec<HOST, ZoneOffsetTransition> spec) {
        super(name, spec);
    }
    
    public ZoneOffsetTransitionLens(LensSpec<HOST, ZoneOffsetTransition> spec) {
        this(null, spec);
    }
    // TODO - Really don't think we will need this.
    // /**
    // * The local transition date-time at the transition.
    // */
    // private final LocalDateTime transition;
    // /**
    // * The offset before transition.
    // */
    // private final ZoneOffset offsetBefore;
    // /**
    // * The offset after transition.
    // */
    // private final ZoneOffset offsetAfter;
}

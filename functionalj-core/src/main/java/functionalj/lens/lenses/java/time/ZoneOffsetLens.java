package functionalj.lens.lenses.java.time;

import java.time.ZoneOffset;

import functionalj.lens.core.LensSpec;
import functionalj.lens.lenses.ObjectLensImpl;


public class ZoneOffsetLens<HOST>
                extends    ObjectLensImpl<HOST, ZoneOffset>
                implements ZoneOffsetAccess<HOST> {
                
    public static final ZoneOffsetLens<ZoneOffset> theZoneOffset = new ZoneOffsetLens<ZoneOffset>(LensSpec.of(ZoneOffset.class));
    
    public static <H> ZoneOffsetLens<H> of(String name, LensSpec<H, ZoneOffset> spec) {
        return new ZoneOffsetLens<H>(name, spec);
    }
    public static <H> ZoneOffsetLens<H> of(LensSpec<H, ZoneOffset> spec) {
        return new ZoneOffsetLens<H>(spec);
    }
    
    public ZoneOffsetLens(String name, LensSpec<HOST, ZoneOffset> spec) {
        super(name, spec);
    }
    public ZoneOffsetLens(LensSpec<HOST, ZoneOffset> spec) {
        this(null, spec);
    }

}

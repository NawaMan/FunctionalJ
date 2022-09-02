package functionalj.lens.lenses.java.time;

import java.time.ZoneId;

import functionalj.lens.core.LensSpec;
import functionalj.lens.lenses.ObjectLensImpl;


public class ZoneIdLens<HOST>
                extends    ObjectLensImpl<HOST, ZoneId>
                implements ZoneIdAccess  <HOST, ZoneId> {
                
    public static final ZoneIdLens<ZoneId> theZoneId = new ZoneIdLens<ZoneId>(LensSpec.of(ZoneId.class));
    
    public static <H> ZoneIdLens<H> of(LensSpec<H, ZoneId> spec) {
        return new ZoneIdLens<H>(spec);
    }
    
    public ZoneIdLens(String name, LensSpec<HOST, ZoneId> spec) {
        super(name, spec);
    }
    public ZoneIdLens(LensSpec<HOST, ZoneId> spec) {
        this(null, spec);
    }

}

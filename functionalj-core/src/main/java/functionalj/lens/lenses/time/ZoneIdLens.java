package functionalj.lens.lenses.time;

import java.time.ZoneId;

import functionalj.lens.core.LensSpec;
import functionalj.lens.lenses.ObjectLensImpl;


public class ZoneIdLens<HOST>
                extends
                    ObjectLensImpl<HOST, ZoneId>
                implements
                    ZoneIdAccess<HOST, ZoneId> {
                
    public static final ZoneIdLens<ZoneId> theZoneId = new ZoneIdLens<ZoneId>(LensSpec.of(ZoneId.class));
    
    public ZoneIdLens(LensSpec<HOST, ZoneId> spec) {
        super(spec);
    }

}

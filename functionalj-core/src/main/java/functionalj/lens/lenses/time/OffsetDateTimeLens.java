package functionalj.lens.lenses.time;

import java.time.OffsetDateTime;

import functionalj.lens.core.LensSpec;
import functionalj.lens.lenses.ObjectLensImpl;

public class OffsetDateTimeLens<HOST>
                extends
                    ObjectLensImpl<HOST, OffsetDateTime>
                implements
                    OffsetDateTimeAccess<HOST> {
                
    public static final OffsetDateTimeLens<OffsetDateTime> theOffsetDateTime = new OffsetDateTimeLens<OffsetDateTime>(LensSpec.of(OffsetDateTime.class));
    
    // TODO
//    private final LocalDateTime dateTime;
//    private final ZoneOffset offset;
    
    public OffsetDateTimeLens(LensSpec<HOST, OffsetDateTime> spec) {
        super(spec);
    }
    
}

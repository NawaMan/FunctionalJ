package functionalj.lens.lenses.time;

import java.time.ZonedDateTime;

import functionalj.lens.core.LensSpec;
import functionalj.lens.lenses.ObjectLensImpl;


public class ZonedDateTimeLens<HOST>
                extends
                    ObjectLensImpl<HOST, ZonedDateTime>
                implements
                    ZonedDateTimeAccess<HOST> {
                
    public static final ZonedDateTimeLens<ZonedDateTime> theZonedDateTime = new ZonedDateTimeLens<ZonedDateTime>(LensSpec.of(ZonedDateTime.class));
    
    // TODO
//    private final LocalDateTime dateTime;
//    private final ZoneOffset offset;
//    private final ZoneId zone;
    
    public ZonedDateTimeLens(LensSpec<HOST, ZonedDateTime> spec) {
        super(spec);
    }

}

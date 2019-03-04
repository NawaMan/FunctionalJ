package functionalj.lens.lenses.java.time;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import functionalj.lens.core.LensSpec;
import functionalj.lens.lenses.ObjectLensImpl;


public class ZonedDateTimeLens<HOST>
                extends
                    ObjectLensImpl<HOST, ZonedDateTime>
                implements
                    ZonedDateTimeAccess<HOST> {
                
    public static final ZonedDateTimeLens<ZonedDateTime> theZonedDateTime
                    = new ZonedDateTimeLens<ZonedDateTime>(LensSpec.of(ZonedDateTime.class));
    
    public final LocalDateTimeLens<HOST> dateTime
                    = createSubLens(
                            ZonedDateTime::toLocalDateTime,
                            (ZonedDateTime zdt, LocalDateTime dt) -> ZonedDateTime.of(dt, zdt.getZone()),
                            LocalDateTimeLens::new);
    
    // NOTE: Not sure if this 'withXXX' is right.
    public final ZoneOffsetLens<HOST> offset
                    = createSubLens(
                            ZonedDateTime::getOffset,
                            (ZonedDateTime zdt, ZoneOffset zo) -> ZonedDateTime.ofLocal(zdt.toLocalDateTime(), zdt.getZone(), zo),
                            ZoneOffsetLens::new);
    
    public final ZoneIdLens<HOST> zone
                    = createSubLens(
                            ZonedDateTime::getZone,
                            (ZonedDateTime zdt, ZoneId z) ->ZonedDateTime.of(zdt.toLocalDateTime(), z),
                            ZoneIdLens::new);
    
    public ZonedDateTimeLens(LensSpec<HOST, ZonedDateTime> spec) {
        super(spec);
    }

}

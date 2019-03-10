package functionalj.lens.lenses.java.time;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import functionalj.lens.core.LensSpec;
import functionalj.lens.lenses.ObjectLensImpl;

public class OffsetDateTimeLens<HOST>
                extends    ObjectLensImpl<HOST, OffsetDateTime>
                implements OffsetDateTimeAccess<HOST> {
                
    public static final OffsetDateTimeLens<OffsetDateTime> theOffsetDateTime
                            = new OffsetDateTimeLens<OffsetDateTime>(LensSpec.of(OffsetDateTime.class));
    
    public final LocalDateTimeLens<HOST> dateTime 
                    = createSubLens(
                            OffsetDateTime::toLocalDateTime, 
                            (OffsetDateTime odt, LocalDateTime dt) -> OffsetDateTime.of(dt, odt.getOffset()),
                            LocalDateTimeLens::new);
    
    public final ZoneOffsetLens<HOST> offset
                    = createSubLens(
                            OffsetDateTime::getOffset,
                            (OffsetDateTime odt, ZoneOffset zo) -> OffsetDateTime.of(odt.toLocalDateTime(), zo),
                            ZoneOffsetLens::new);
    
    public static <H> OffsetDateTimeLens<H> of(LensSpec<H, OffsetDateTime> spec) {
        return new OffsetDateTimeLens<H>(spec);
    }
    
    public OffsetDateTimeLens(LensSpec<HOST, OffsetDateTime> spec) {
        super(spec);
    }
    
}

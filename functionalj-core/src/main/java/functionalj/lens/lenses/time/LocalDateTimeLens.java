package functionalj.lens.lenses.time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

import functionalj.lens.core.LensSpec;
import functionalj.lens.lenses.IntegerLens;
import functionalj.lens.lenses.ObjectLensImpl;

public class LocalDateTimeLens<HOST>
                    extends
                        ObjectLensImpl<HOST, LocalDateTime>
                    implements
                        LocalDateTimeAccess<HOST> {
    
    public static final LocalDateTimeLens<LocalDateTime> theLocalDateTime = new LocalDateTimeLens<LocalDateTime>(LensSpec.of(LocalDateTime.class));
    
    public final LocalDateLens<HOST> date  = createSubLens(LocalDateTime::toLocalDate, (LocalDateTime dt, LocalDate d) -> LocalDateTime.of(d.getYear(),  d.getMonthValue(),  d.getDayOfMonth(),  dt.getHour(), dt.getMinute(), dt.getSecond(), dt.getNano()), LocalDateLens::new);
    public final LocalTimeLens<HOST> time = createSubLens(LocalDateTime::toLocalTime, (LocalDateTime dt, LocalTime t) -> LocalDateTime.of(dt.getYear(), dt.getMonthValue(), dt.getDayOfMonth(), t.getHour(),  t.getMinute(),  t.getSecond(),  t.getNano()),  LocalTimeLens::new);
    
    public final IntegerLens<HOST> year  = createSubLens(LocalDateTime::getYear,       LocalDateTime::withYear,                                IntegerLens::of);
    public final MonthLens<HOST>   month = createSubLens(LocalDateTime::getMonth,      (dt, month) -> dt.withMonth(((Month)month).getValue()), MonthLens::new);
    public final IntegerLens<HOST> day   = createSubLens(LocalDateTime::getDayOfMonth, LocalDateTime::withDayOfMonth,                          IntegerLens::of);
    
    public final IntegerLens<HOST> monthValue = createSubLens(LocalDateTime::getMonthValue, LocalDateTime::withMonth,     IntegerLens::of);
    public final IntegerLens<HOST> dayOfYear  = createSubLens(LocalDateTime::getDayOfYear,  LocalDateTime::withDayOfYear, IntegerLens::of);
    
    public final IntegerLens<HOST> hour   = createSubLens(LocalDateTime::getHour,   LocalDateTime::withHour,   IntegerLens::of);
    public final IntegerLens<HOST> minute = createSubLens(LocalDateTime::getMinute, LocalDateTime::withMinute, IntegerLens::of);
    public final IntegerLens<HOST> second = createSubLens(LocalDateTime::getSecond, LocalDateTime::withSecond, IntegerLens::of);
    public final IntegerLens<HOST> nano   = createSubLens(LocalDateTime::getNano,   LocalDateTime::withNano,   IntegerLens::of);
    
    
    public LocalDateTimeLens(LensSpec<HOST, LocalDateTime> spec) {
        super(spec);
    }
    
}

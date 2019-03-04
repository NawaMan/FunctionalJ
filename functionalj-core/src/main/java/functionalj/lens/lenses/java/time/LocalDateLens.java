package functionalj.lens.lenses.java.time;

import java.time.LocalDate;
import java.time.Month;

import functionalj.lens.core.LensSpec;
import functionalj.lens.lenses.IntegerLens;
import functionalj.lens.lenses.ObjectLensImpl;

public class LocalDateLens<HOST>
                    extends
                        ObjectLensImpl<HOST, LocalDate>
                    implements
                        LocalDateAccess<HOST> {
    
    public static final LocalDateLens<LocalDate> theLocalDate = new LocalDateLens<LocalDate>(LensSpec.of(LocalDate.class));
    
    public final IntegerLens<HOST> year  = createSubLens(LocalDate::getYear,       (inst, year)  -> inst.withYear(year),                       IntegerLens::of);
    public final MonthLens<HOST>   month = createSubLens(LocalDate::getMonth,      (inst, month) -> inst.withMonth(((Month)month).getValue()), MonthLens::new);
    public final IntegerLens<HOST> day   = createSubLens(LocalDate::getDayOfMonth, (inst, day)   -> inst.withDayOfMonth(day),                  IntegerLens::of);
    
    public final IntegerLens<HOST> monthValue = createSubLens(LocalDate::getMonthValue, (inst, month) -> inst.withMonth(month),   IntegerLens::of);
    public final IntegerLens<HOST> dayOfYear  = createSubLens(LocalDate::getDayOfYear,  (inst, day)   -> inst.withDayOfYear(day), IntegerLens::of);
    
    public LocalDateLens(LensSpec<HOST, LocalDate> spec) {
        super(spec);
    }
}

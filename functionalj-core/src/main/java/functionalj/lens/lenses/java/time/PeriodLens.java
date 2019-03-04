package functionalj.lens.lenses.java.time;

import java.time.Period;

import functionalj.lens.core.LensSpec;
import functionalj.lens.lenses.IntegerLens;
import functionalj.lens.lenses.ObjectLensImpl;

public class PeriodLens<HOST>
                extends
                    ObjectLensImpl<HOST, Period>
                implements
                    PeriodAccess<HOST> {
                
    public static final PeriodLens<Period> thePeriod = new PeriodLens<Period>(LensSpec.of(Period.class));
    
    public final IntegerLens<HOST> years  = createSubLens(Period::getYears,  Period::withYears,  IntegerLens::of);
    public final IntegerLens<HOST> months = createSubLens(Period::getMonths, Period::withMonths, IntegerLens::of);
    public final IntegerLens<HOST> days   = createSubLens(Period::getDays,   Period::withDays,   IntegerLens::of);
    
    public PeriodLens(LensSpec<HOST, Period> spec) {
        super(spec);
    }
    
}

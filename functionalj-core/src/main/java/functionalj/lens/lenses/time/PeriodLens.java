package functionalj.lens.lenses.time;

import java.time.Period;

import functionalj.lens.core.LensSpec;
import functionalj.lens.lenses.ObjectLensImpl;

public class PeriodLens<HOST>
                extends
                    ObjectLensImpl<HOST, Period>
                implements
                    PeriodAccess<HOST> {
                
    public static final PeriodLens<Period> thePeriod = new PeriodLens<Period>(LensSpec.of(Period.class));
    
    // TODO
//    private final int years;
//    private final int months;
//    private final int days;
//    
    public PeriodLens(LensSpec<HOST, Period> spec) {
        super(spec);
    }
    
}

package functionalj.lens.lenses.time;

import java.time.Month;

import functionalj.lens.core.LensSpec;
import functionalj.lens.lenses.ObjectLensImpl;

public class MonthLens<HOST>
            extends
                ObjectLensImpl<HOST, Month>
            implements
                MonthAccess<HOST> {
            
    public static final MonthLens<Month> theMonth = new MonthLens<Month>(LensSpec.of(Month.class));
    
    public MonthLens(LensSpec<HOST, Month> spec) {
        super(spec);
    }
    
}

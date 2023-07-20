// ============================================================================
// Copyright (c) 2017-2023 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
// ----------------------------------------------------------------------------
// MIT License
// 
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
// 
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
// 
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.
// ============================================================================
package functionalj.lens.lenses.java.time;

import java.time.Period;
import functionalj.lens.core.LensSpec;
import functionalj.lens.lenses.IntegerLens;
import functionalj.lens.lenses.ObjectLensImpl;

public class PeriodLens<HOST> extends ObjectLensImpl<HOST, Period> implements PeriodAccess<HOST> {
    
    public static final PeriodLens<Period> thePeriod = new PeriodLens<Period>(LensSpec.of(Period.class));
    
    public final IntegerLens<HOST> years = createSubLens(Period::getYears, Period::withYears, IntegerLens::of);
    
    public final IntegerLens<HOST> months = createSubLens(Period::getMonths, Period::withMonths, IntegerLens::of);
    
    public final IntegerLens<HOST> days = createSubLens(Period::getDays, Period::withDays, IntegerLens::of);
    
    public static <H> PeriodLens<H> of(String name, LensSpec<H, Period> spec) {
        return new PeriodLens<H>(name, spec);
    }
    
    public static <H> PeriodLens<H> of(LensSpec<H, Period> spec) {
        return new PeriodLens<H>(spec);
    }
    
    public PeriodLens(String name, LensSpec<HOST, Period> spec) {
        super(name, spec);
    }
    
    public PeriodLens(LensSpec<HOST, Period> spec) {
        this(null, spec);
    }
}

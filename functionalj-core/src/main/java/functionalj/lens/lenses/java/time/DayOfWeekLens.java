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

import java.time.DayOfWeek;
import functionalj.function.Func1;
import functionalj.lens.core.LensSpec;
import functionalj.lens.lenses.ObjectLensImpl;

public class DayOfWeekLens<HOST> extends ObjectLensImpl<HOST, DayOfWeek> implements DayOfWeekAccess<HOST> {
    
    public static final DayOfWeekLens<DayOfWeek> theDayOfWeek = new DayOfWeekLens<DayOfWeek>(LensSpec.of(DayOfWeek.class));
    
    public static <H> DayOfWeekLens<H> of(String name, LensSpec<H, DayOfWeek> spec) {
        return new DayOfWeekLens<H>(name, spec);
    }
    
    public static <H> DayOfWeekLens<H> of(LensSpec<H, DayOfWeek> spec) {
        return new DayOfWeekLens<H>(spec);
    }
    
    public DayOfWeekLens(String name, LensSpec<HOST, DayOfWeek> spec) {
        super(name, spec);
    }
    
    public DayOfWeekLens(LensSpec<HOST, DayOfWeek> spec) {
        this(null, spec);
    }
    
    public final Func1<HOST, HOST> toSunday = changeTo(DayOfWeek.SUNDAY);
    
    public final Func1<HOST, HOST> toMonday = changeTo(DayOfWeek.MONDAY);
    
    public final Func1<HOST, HOST> toTuesday = changeTo(DayOfWeek.TUESDAY);
    
    public final Func1<HOST, HOST> toWednesday = changeTo(DayOfWeek.WEDNESDAY);
    
    public final Func1<HOST, HOST> toThursday = changeTo(DayOfWeek.THURSDAY);
    
    public final Func1<HOST, HOST> toFriday = changeTo(DayOfWeek.FRIDAY);
    
    public final Func1<HOST, HOST> toSaturday = changeTo(DayOfWeek.SATURDAY);
}

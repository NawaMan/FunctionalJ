// ============================================================================
// Copyright (c) 2017-2024 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import static java.time.Month.APRIL;
import static java.time.Month.AUGUST;
import static java.time.Month.DECEMBER;
import static java.time.Month.FEBRUARY;
import static java.time.Month.JANUARY;
import static java.time.Month.JULY;
import static java.time.Month.JUNE;
import static java.time.Month.MARCH;
import static java.time.Month.MAY;
import static java.time.Month.NOVEMBER;
import static java.time.Month.OCTOBER;
import static java.time.Month.SEPTEMBER;
import java.time.Month;
import functionalj.function.Func1;
import functionalj.lens.core.LensSpec;
import functionalj.lens.lenses.ObjectLensImpl;

public class MonthLens<HOST> extends ObjectLensImpl<HOST, Month> implements MonthAccess<HOST> {
    
    public static final MonthLens<Month> theMonth = new MonthLens<Month>(LensSpec.of(Month.class));
    
    public static <H> MonthLens<H> of(String name, LensSpec<H, Month> spec) {
        return new MonthLens<H>(name, spec);
    }
    
    public static <H> MonthLens<H> of(LensSpec<H, Month> spec) {
        return new MonthLens<H>(spec);
    }
    
    public MonthLens(String name, LensSpec<HOST, Month> spec) {
        super(name, spec);
    }
    
    public MonthLens(LensSpec<HOST, Month> spec) {
        this(null, spec);
    }
    
    public final Func1<HOST, HOST> toJanuary = changeTo(JANUARY);
    
    public final Func1<HOST, HOST> toFebruary = changeTo(FEBRUARY);
    
    public final Func1<HOST, HOST> toMarch = changeTo(MARCH);
    
    public final Func1<HOST, HOST> toApril = changeTo(APRIL);
    
    public final Func1<HOST, HOST> toMay = changeTo(MAY);
    
    public final Func1<HOST, HOST> toJune = changeTo(JUNE);
    
    public final Func1<HOST, HOST> toJuly = changeTo(JULY);
    
    public final Func1<HOST, HOST> toAugust = changeTo(AUGUST);
    
    public final Func1<HOST, HOST> toSeptember = changeTo(SEPTEMBER);
    
    public final Func1<HOST, HOST> toOctober = changeTo(OCTOBER);
    
    public final Func1<HOST, HOST> toNovember = changeTo(NOVEMBER);
    
    public final Func1<HOST, HOST> toDecember = changeTo(DECEMBER);
}

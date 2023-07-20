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

import java.time.LocalDate;
import java.time.Month;
import functionalj.lens.core.LensSpec;
import functionalj.lens.lenses.IntegerLens;
import functionalj.lens.lenses.ObjectLensImpl;

public class LocalDateLens<HOST> extends ObjectLensImpl<HOST, LocalDate> implements LocalDateAccess<HOST> {
    
    public static final LocalDateLens<LocalDate> theLocalDate = new LocalDateLens<LocalDate>(LensSpec.of(LocalDate.class));
    
    public final IntegerLens<HOST> year = createSubLens(LocalDate::getYear, (inst, year) -> inst.withYear(year), IntegerLens::of);
    
    public final MonthLens<HOST> month = createSubLens(LocalDate::getMonth, (inst, month) -> inst.withMonth(((Month) month).getValue()), MonthLens::new);
    
    public final IntegerLens<HOST> day = createSubLens(LocalDate::getDayOfMonth, (inst, day) -> inst.withDayOfMonth(day), IntegerLens::of);
    
    public final IntegerLens<HOST> monthValue = createSubLens(LocalDate::getMonthValue, (inst, month) -> inst.withMonth(month), IntegerLens::of);
    
    public final IntegerLens<HOST> dayOfYear = createSubLens(LocalDate::getDayOfYear, (inst, day) -> inst.withDayOfYear(day), IntegerLens::of);
    
    public static <H> LocalDateLens<H> of(String name, LensSpec<H, LocalDate> spec) {
        return new LocalDateLens<H>(name, spec);
    }
    
    public static <H> LocalDateLens<H> of(LensSpec<H, LocalDate> spec) {
        return new LocalDateLens<H>(spec);
    }
    
    public LocalDateLens(String name, LensSpec<HOST, LocalDate> spec) {
        super(spec);
    }
    
    public LocalDateLens(LensSpec<HOST, LocalDate> spec) {
        this(null, spec);
    }
}

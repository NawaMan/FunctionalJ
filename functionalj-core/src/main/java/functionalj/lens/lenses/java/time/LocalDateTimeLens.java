// ============================================================================
// Copyright (c) 2017-2025 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import functionalj.lens.core.LensSpec;
import functionalj.lens.lenses.IntegerLens;
import functionalj.lens.lenses.ObjectLensImpl;

public class LocalDateTimeLens<HOST> extends ObjectLensImpl<HOST, LocalDateTime> implements LocalDateTimeAccess<HOST> {
    
    public static final LocalDateTimeLens<LocalDateTime> theLocalDateTime = new LocalDateTimeLens<LocalDateTime>(LensSpec.of(LocalDateTime.class));
    
    public final LocalDateLens<HOST> date = createSubLens(LocalDateTime::toLocalDate, (LocalDateTime dt, LocalDate d) -> LocalDateTime.of(d.getYear(), d.getMonthValue(), d.getDayOfMonth(), dt.getHour(), dt.getMinute(), dt.getSecond(), dt.getNano()), LocalDateLens::new);
    
    public final LocalTimeLens<HOST> time = createSubLens(LocalDateTime::toLocalTime, (LocalDateTime dt, LocalTime t) -> LocalDateTime.of(dt.getYear(), dt.getMonthValue(), dt.getDayOfMonth(), t.getHour(), t.getMinute(), t.getSecond(), t.getNano()), LocalTimeLens::new);
    
    public final IntegerLens<HOST> year = createSubLens(LocalDateTime::getYear, LocalDateTime::withYear, IntegerLens::of);
    
    public final MonthLens<HOST> month = createSubLens(LocalDateTime::getMonth, (dt, month) -> dt.withMonth(((Month) month).getValue()), MonthLens::new);
    
    public final IntegerLens<HOST> day = createSubLens(LocalDateTime::getDayOfMonth, LocalDateTime::withDayOfMonth, IntegerLens::of);
    
    public final IntegerLens<HOST> monthValue = createSubLens(LocalDateTime::getMonthValue, LocalDateTime::withMonth, IntegerLens::of);
    
    public final IntegerLens<HOST> dayOfYear = createSubLens(LocalDateTime::getDayOfYear, LocalDateTime::withDayOfYear, IntegerLens::of);
    
    public final IntegerLens<HOST> hour = createSubLens(LocalDateTime::getHour, LocalDateTime::withHour, IntegerLens::of);
    
    public final IntegerLens<HOST> minute = createSubLens(LocalDateTime::getMinute, LocalDateTime::withMinute, IntegerLens::of);
    
    public final IntegerLens<HOST> second = createSubLens(LocalDateTime::getSecond, LocalDateTime::withSecond, IntegerLens::of);
    
    public final IntegerLens<HOST> nano = createSubLens(LocalDateTime::getNano, LocalDateTime::withNano, IntegerLens::of);
    
    public static <H> LocalDateTimeLens<H> of(String name, LensSpec<H, LocalDateTime> spec) {
        return new LocalDateTimeLens<H>(name, spec);
    }
    
    public static <H> LocalDateTimeLens<H> of(LensSpec<H, LocalDateTime> spec) {
        return new LocalDateTimeLens<H>(spec);
    }
    
    public LocalDateTimeLens(String name, LensSpec<HOST, LocalDateTime> spec) {
        super(name, spec);
    }
    
    public LocalDateTimeLens(LensSpec<HOST, LocalDateTime> spec) {
        this(null, spec);
    }
}

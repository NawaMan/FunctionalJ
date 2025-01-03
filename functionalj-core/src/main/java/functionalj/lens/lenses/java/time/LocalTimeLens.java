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

import java.time.LocalTime;
import functionalj.lens.core.LensSpec;
import functionalj.lens.lenses.IntegerLens;
import functionalj.lens.lenses.ObjectLensImpl;

public class LocalTimeLens<HOST> extends ObjectLensImpl<HOST, LocalTime> implements LocalTimeAccess<HOST> {
    
    public static final LocalTimeLens<LocalTime> theLocalTime = new LocalTimeLens<LocalTime>(LensSpec.of(LocalTime.class));
    
    public final IntegerLens<HOST> hour = createSubLens(LocalTime::getHour, LocalTime::withHour, IntegerLens::of);
    
    public final IntegerLens<HOST> minute = createSubLens(LocalTime::getMinute, LocalTime::withMinute, IntegerLens::of);
    
    public final IntegerLens<HOST> second = createSubLens(LocalTime::getSecond, LocalTime::withSecond, IntegerLens::of);
    
    public final IntegerLens<HOST> nano = createSubLens(LocalTime::getNano, LocalTime::withNano, IntegerLens::of);
    
    public static <H> LocalTimeLens<H> of(String name, LensSpec<H, LocalTime> spec) {
        return new LocalTimeLens<H>(name, spec);
    }
    
    public static <H> LocalTimeLens<H> of(LensSpec<H, LocalTime> spec) {
        return new LocalTimeLens<H>(spec);
    }
    
    public LocalTimeLens(String name, LensSpec<HOST, LocalTime> spec) {
        super(name, spec);
    }
    
    public LocalTimeLens(LensSpec<HOST, LocalTime> spec) {
        this(null, spec);
    }
}

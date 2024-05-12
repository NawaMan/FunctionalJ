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

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import functionalj.lens.core.LensSpec;
import functionalj.lens.lenses.ObjectLensImpl;

public class OffsetDateTimeLens<HOST> extends ObjectLensImpl<HOST, OffsetDateTime> implements OffsetDateTimeAccess<HOST> {
    
    public static final OffsetDateTimeLens<OffsetDateTime> theOffsetDateTime = new OffsetDateTimeLens<OffsetDateTime>(LensSpec.of(OffsetDateTime.class));
    
    public final LocalDateTimeLens<HOST> dateTime = createSubLens(OffsetDateTime::toLocalDateTime, (OffsetDateTime odt, LocalDateTime dt) -> OffsetDateTime.of(dt, odt.getOffset()), LocalDateTimeLens::new);
    
    public final ZoneOffsetLens<HOST> offset = createSubLens(OffsetDateTime::getOffset, (OffsetDateTime odt, ZoneOffset zo) -> OffsetDateTime.of(odt.toLocalDateTime(), zo), ZoneOffsetLens::new);
    
    public static <H> OffsetDateTimeLens<H> of(String name, LensSpec<H, OffsetDateTime> spec) {
        return new OffsetDateTimeLens<H>(name, spec);
    }
    
    public static <H> OffsetDateTimeLens<H> of(LensSpec<H, OffsetDateTime> spec) {
        return new OffsetDateTimeLens<H>(spec);
    }
    
    public OffsetDateTimeLens(String name, LensSpec<HOST, OffsetDateTime> spec) {
        super(name, spec);
    }
    
    public OffsetDateTimeLens(LensSpec<HOST, OffsetDateTime> spec) {
        this(null, spec);
    }
}

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
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import functionalj.lens.core.LensSpec;
import functionalj.lens.lenses.ObjectLensImpl;

public class ZonedDateTimeLens<HOST> extends ObjectLensImpl<HOST, ZonedDateTime> implements ZonedDateTimeAccess<HOST> {
    
    public static final ZonedDateTimeLens<ZonedDateTime> theZonedDateTime = new ZonedDateTimeLens<ZonedDateTime>(LensSpec.of(ZonedDateTime.class));
    
    public final LocalDateTimeLens<HOST> dateTime = createSubLens(ZonedDateTime::toLocalDateTime, (ZonedDateTime zdt, LocalDateTime dt) -> ZonedDateTime.of(dt, zdt.getZone()), LocalDateTimeLens::new);
    
    // NOTE: Not sure if this 'withXXX' is right.
    public final ZoneOffsetLens<HOST> offset = createSubLens(ZonedDateTime::getOffset, (ZonedDateTime zdt, ZoneOffset zo) -> ZonedDateTime.ofLocal(zdt.toLocalDateTime(), zdt.getZone(), zo), ZoneOffsetLens::new);
    
    public final ZoneIdLens<HOST> zone = createSubLens(ZonedDateTime::getZone, (ZonedDateTime zdt, ZoneId z) -> ZonedDateTime.of(zdt.toLocalDateTime(), z), ZoneIdLens::new);
    
    public static <H> ZonedDateTimeLens<H> of(String name, LensSpec<H, ZonedDateTime> spec) {
        return new ZonedDateTimeLens<H>(name, spec);
    }
    
    public static <H> ZonedDateTimeLens<H> of(LensSpec<H, ZonedDateTime> spec) {
        return new ZonedDateTimeLens<H>(spec);
    }
    
    public ZonedDateTimeLens(String name, LensSpec<HOST, ZonedDateTime> spec) {
        super(name, spec);
    }
    
    public ZonedDateTimeLens(LensSpec<HOST, ZonedDateTime> spec) {
        this(null, spec);
    }
}

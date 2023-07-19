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

import java.time.Instant;
import java.time.temporal.ChronoField;
import functionalj.lens.core.LensSpec;
import functionalj.lens.lenses.IntegerLens;
import functionalj.lens.lenses.LongLens;
import functionalj.lens.lenses.ObjectLensImpl;

public class InstantLens<HOST> extends ObjectLensImpl<HOST, Instant> implements InstantAccess<HOST> {
    
    public static final InstantLens<Instant> theInstant = new InstantLens<Instant>(LensSpec.of(Instant.class));
    
    public final LongLens<HOST> seconds = createSubLens(Instant::getEpochSecond, (inst, secs) -> inst.with(ChronoField.INSTANT_SECONDS, secs), LongLens::of);
    
    public final IntegerLens<HOST> nanos = createSubLens(Instant::getNano, (inst, secs) -> inst.with(ChronoField.NANO_OF_SECOND, secs), IntegerLens::of);
    
    public static <H> InstantLens<H> of(String name, LensSpec<H, Instant> spec) {
        return new InstantLens<H>(name, spec);
    }
    
    public static <H> InstantLens<H> of(LensSpec<H, Instant> spec) {
        return new InstantLens<H>(spec);
    }
    
    public InstantLens(String name, LensSpec<HOST, Instant> spec) {
        super(name, spec);
    }
    
    public InstantLens(LensSpec<HOST, Instant> spec) {
        this(null, spec);
    }
}

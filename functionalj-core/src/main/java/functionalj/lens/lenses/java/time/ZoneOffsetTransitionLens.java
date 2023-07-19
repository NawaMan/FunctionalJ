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

import java.time.zone.ZoneOffsetTransition;
import functionalj.lens.core.LensSpec;
import functionalj.lens.lenses.ObjectLensImpl;

public class ZoneOffsetTransitionLens<HOST> extends ObjectLensImpl<HOST, ZoneOffsetTransition> implements ZoneOffsetTransitionAccess<HOST> {
    
    public static final ZoneOffsetTransitionLens<ZoneOffsetTransition> theZoneOffsetTransition = new ZoneOffsetTransitionLens<ZoneOffsetTransition>(LensSpec.of(ZoneOffsetTransition.class));
    
    public static <H> ZoneOffsetTransitionLens<H> of(String name, LensSpec<H, ZoneOffsetTransition> spec) {
        return new ZoneOffsetTransitionLens<H>(name, spec);
    }
    
    public static <H> ZoneOffsetTransitionLens<H> of(LensSpec<H, ZoneOffsetTransition> spec) {
        return new ZoneOffsetTransitionLens<H>(spec);
    }
    
    public ZoneOffsetTransitionLens(String name, LensSpec<HOST, ZoneOffsetTransition> spec) {
        super(name, spec);
    }
    
    public ZoneOffsetTransitionLens(LensSpec<HOST, ZoneOffsetTransition> spec) {
        this(null, spec);
    }
    // TODO - Really don't think we will need this.
    // /**
    // * The local transition date-time at the transition.
    // */
    // private final LocalDateTime transition;
    // /**
    // * The offset before transition.
    // */
    // private final ZoneOffset offsetBefore;
    // /**
    // * The offset after transition.
    // */
    // private final ZoneOffset offsetAfter;
}

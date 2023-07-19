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

import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.function.Function;
import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.StringAccess;
import lombok.val;

@FunctionalInterface
public interface ZoneIdAccess<HOST, ZONE_ID extends ZoneId> extends AnyAccess<HOST, ZONE_ID> {
    
    public static <H, Z extends ZoneId> ZoneIdAccess<H, Z> of(Function<H, Z> func) {
        return func::apply;
    }
    
    public default StringAccess<HOST> getId() {
        return host -> {
            val value = apply(host);
            return value.getId();
        };
    }
    
    public default StringAccess<HOST> getDisplayName(TextStyle style, Locale locale) {
        return host -> {
            val value = apply(host);
            return value.getDisplayName(style, locale);
        };
    }
    
    public default ZoneRulesAccess<HOST> getRules() {
        return host -> {
            val value = apply(host);
            return value.getRules();
        };
    }
    
    public default ZoneIdAccess<HOST, ZoneId> normalized() {
        return host -> {
            val value = apply(host);
            return value.normalized();
        };
    }
}

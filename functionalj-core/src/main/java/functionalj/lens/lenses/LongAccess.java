// ============================================================================
// Copyright (c) 2017-2019 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.lens.lenses;

import static java.util.Objects.requireNonNull;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.function.Function;
import java.util.function.ToLongFunction;

import functionalj.function.Func1;
import functionalj.lens.lenses.java.time.InstantAccess;
import functionalj.lens.lenses.java.time.LocalDateTimeAccess;
import lombok.val;

@SuppressWarnings("javadoc")
public interface LongAccess<HOST> 
        extends 
            NumberAccess<HOST, Long, LongAccess<HOST>>, 
            ToLongFunction<HOST>,
            ConcreteAccess<HOST, Long, LongAccess<HOST>> {
    
    
    public static <H> LongAccess<H> of(Function<H, Long> accessToValue) {
        requireNonNull(accessToValue);
        
        if (accessToValue instanceof LongAccess) {
            return (LongAccess<H>)accessToValue;
        }
        
        if (accessToValue instanceof ToLongFunction) {
            @SuppressWarnings("unchecked")
            val func1  = (ToLongFunction<H>)accessToValue;
            val access = ofPrimitive(func1);
            return access;
        }
        
        if (accessToValue instanceof Func1) {
            val func1  = (Func1<H, Long>)accessToValue;
            val access = (LongAccessBoxed<H>)func1::applyUnsafe;
            return access;
        }

        val func   = (Function<H, Long>)accessToValue;
        val access = (LongAccessBoxed<H>)(host -> func.apply(host));
        return access;
    }
    
    public static <H> LongAccess<H> ofPrimitive(ToLongFunction<H> accessToValue) {
        requireNonNull(accessToValue);
        val access = (LongAccessPrimitive<H>)accessToValue::applyAsLong;
        return access;
    }
    
    
    
    
    public long applyAsLong(HOST host);
    
    public Long applyUnsafe(HOST host) throws Exception;
    
    
    @Override
    public default LongAccess<HOST> newAccess(Function<HOST, Long> accessToValue) {
        return of(accessToValue);
    }
    
    public default InstantAccess<HOST> toInstant() {
        return host -> {
            long timestampMilliSecond = apply(host);
            return Instant.ofEpochMilli(timestampMilliSecond);
        };
    }
    
    public default LocalDateTimeAccess<HOST> toLocalDateTime() {
        return toLocalDateTime(ZoneId.systemDefault());
    }
    
    public default LocalDateTimeAccess<HOST> toLocalDateTime(ZoneId zone) {
        return host -> {
            val timestampMilliSecond = apply(host);
            val instant = Instant.ofEpochMilli(timestampMilliSecond);
            return LocalDateTime.ofInstant(instant, zone);
        };
    }
    
    public default MathOperators<Long> __mathOperators() {
        return LongMathOperators.instance;
    }
    
}

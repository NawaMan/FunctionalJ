// ============================================================================
// Copyright (c) 2017-2021 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import static functionalj.functions.StrFuncs.toStr;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import functionalj.function.Func1;
import functionalj.lens.lenses.java.time.LocalDateAccess;
import functionalj.lens.lenses.java.time.LocalDateTimeAccess;
import functionalj.list.FuncList;
import functionalj.result.Result;
import lombok.val;


class StringAccessHelper {
    
    static StringAccess<String> $(Object ... objs) {
        return str -> {
            val eachToString = stringFrom(str);
            return Stream.of(objs).map(eachToString).collect(joining());
        };
    }
    
    static Func1<Object, String> stringFrom(String str) {
        return each -> stringFrom(each, str);
    }
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static String stringFrom(Object each, String str) {
        if (each instanceof Supplier) {
            Supplier supplier = (Supplier)each;
            Object   newValue = supplier.get();
            return toStr(newValue);
        }
        if (each instanceof Function) {
            Function function = (Function)each;
            Object   newValue = function.apply(str);
            return toStr(newValue);
        }
        return toStr(each);
    }
    
}



/**
 * Classes implementing this interface know how to access to a String value.
 **/
@FunctionalInterface
public interface StringAccess<HOST> 
                    extends 
                        ObjectAccess<HOST, String>, 
                        ConcreteAccess<HOST, String, StringAccess<HOST>> {
    
    public static <H> StringAccess<H> of(Function<H, String> accessToValue) {
        requireNonNull(accessToValue);
        
        if (accessToValue instanceof StringAccess) {
            return (StringAccess<H>)accessToValue;
        }
        
        if (accessToValue instanceof Func1) {
            val func1  = (Func1<H, String>)accessToValue;
            val access = (StringAccess<H>)func1::applyUnsafe;
            return access;
        }
        
        val func   = (Function<H, String>)accessToValue;
        val access = (StringAccess<H>)(host -> func.apply(host));
        return access;
    }
    
    @Override
    public default StringAccess<HOST> newAccess(Function<HOST, String> access) {
        return access::apply;
    }
    
    //== functionalities ==
    
    public default CharacterAccessPrimitive<HOST> charAt(int index) {
        return host -> {
            val  strValue  = apply(host);
            char charValue = strValue.charAt(index);
            return charValue;
        };
    }
    
    // Extra
    
    public default BooleanAccess<HOST> thatIsBlank() {
        return booleanAccess(true, str->str.trim().isEmpty());
    }
    
    public default BooleanAccess<HOST> thatIsNotBlank() {
        return booleanAccess(true, str->!str.trim().isEmpty());
    }
    
    // java.lang.String
    
    public default IntegerAccess<HOST> compareToIgnoreCase(String anotherString) {
        return intPrimitiveAccess(-1, str->str.compareToIgnoreCase(anotherString));
    }
    
    public default StringAccess<HOST> concat(Object ... suffixes) {
        return stringAccess("", str -> {
            val eachToString = StringAccessHelper.stringFrom(str);
            String suffix = Stream.of(suffixes).map(eachToString).collect(joining());
            return str + suffix;
        });
    }
    
    public default StringAccess<HOST> withPrefix(Object ... prefixes) {
        return stringAccess("", str -> {
            val eachToString = StringAccessHelper.stringFrom(str);
            String prefix = Stream.of(prefixes).map(eachToString).collect(joining());
            return prefix + str;
        });
    }
    public default StringAccess<HOST> withSuffix(Object ... suffixes) {
        return stringAccess("", str -> {
            val eachToString = StringAccessHelper.stringFrom(str);
            String prefix = Stream.of(suffixes).map(eachToString).collect(joining());
            return str + prefix;
        });
    }
    public default StringAccess<HOST> wrapBy(Object prefix, Object suffix) {
        return stringAccess("", str -> { 
            val eachToString = StringAccessHelper.stringFrom(str);
            String prefixStr = eachToString.apply(prefix);
            String suffixStr = eachToString.apply(suffix);
            return prefixStr + str + suffixStr;
        });
    }
    
    public default BooleanAccess<HOST> thatContentEquals(CharSequence charSequence) {
        return booleanAccess(false, str->str.contentEquals(charSequence));
    }
    public default BooleanAccess<HOST> thatContains(CharSequence charSequence) {
        return booleanAccess(false, str->str.contains(charSequence));
    }
    public default BooleanAccess<HOST> thatNotContains(CharSequence charSequence) {
        return booleanAccess(false, str->!str.contains(charSequence));
    }
    public default BooleanAccess<HOST> thatContainsIgnoreCase(CharSequence charSequence) {
        return booleanAccess(false, str->str.toLowerCase().contains(charSequence.toString().toLowerCase()));
    }
    public default BooleanAccess<HOST> thatNotContainsIgnoreCase(CharSequence charSequence) {
        return booleanAccess(false, str->!str.toLowerCase().contains(charSequence.toString().toLowerCase()));
    }
    
    public default BooleanAccess<HOST> thatEndsWith(String suffix) {
        boolean isSuffixEmpty = (suffix == null) || suffix.isEmpty();
        return booleanAccess(isSuffixEmpty, str->str.endsWith(suffix));
    }
    
    public default StringAccess<HOST> format(String format, Object... args) {
        return stringAccess(null, str->{
            val eachToString = StringAccessHelper.stringFrom(str);
            val argStrs      = Stream.of(args).map(eachToString).toArray();
            return String.format(format, (Object[])argStrs);
        });
    }
    
    public default StringAccess<HOST> formatedBy(String format) {
        return stringAccess(null, str->{
            return String.format(format, str);
        });
    }
    
    public default BooleanAccess<HOST> thatEqualsIgnoreCase(String anotherString) {
        boolean isAnotherStringEmpty = (anotherString == null) || anotherString.isEmpty();
        return booleanAccess(isAnotherStringEmpty, str->str.equalsIgnoreCase(anotherString));
    }
    
    public default IntegerAccess<HOST> indexOf(int ch) {
        return intPrimitiveAccess(-1, str->str.indexOf(ch));
    }
    
    public default IntegerAccess<HOST> indexOf(int ch, int fromIndex) {
        return intPrimitiveAccess(-1, str->str.indexOf(ch, fromIndex));
    }
    
    public default IntegerAccess<HOST> indexOf(String needle) {
        return intPrimitiveAccess(-1, str->str.indexOf(needle));
    }
    
    public default IntegerAccess<HOST> indexOf(String needle, int fromIndex) {
        return intPrimitiveAccess(-1, str->str.indexOf(needle, fromIndex));
    }
    
    public default BooleanAccess<HOST> thatIsEmpty() {
        return booleanAccess(true, str->str.isEmpty());
    }
    public default BooleanAccess<HOST> thatIsNotEmpty() {
        return booleanAccess(true, str->!str.isEmpty());
    }
    
    public default IntegerAccess<HOST> lastIndexOf(int ch) {
        return intPrimitiveAccess(-1, str->str.lastIndexOf(ch));
    }
    
    public default IntegerAccess<HOST> lastIndexOf(int ch, int fromIndex) {
        return intPrimitiveAccess(-1, str->str.lastIndexOf(ch, fromIndex));
    }
    
    public default IntegerAccess<HOST> lastIndexOf(String needle) {
        return intPrimitiveAccess(-1, str->str.lastIndexOf(needle));
    }
    
    public default IntegerAccess<HOST> lastIndexOf(String needle, int fromIndex) {
        return intPrimitiveAccess(-1, str->str.lastIndexOf(needle, fromIndex));
    }
    
    public default IntegerAccess<HOST> length() {
        return intPrimitiveAccess(0, str->str.length());
    }
    
    public default BooleanAccess<HOST> thatMatches(String regex) {
        return booleanAccess(false, str->str.matches(regex));
    }
    
    public default BooleanAccess<HOST> thatMatchesIgnoreCase(String regex) {
        return booleanAccess(false, str->str.toLowerCase().matches(regex.toLowerCase()));
    }
    
    public default StringAccess<HOST> replace(char oldChar, char newChar) {
        return stringAccess(null, str->str.replace(oldChar, newChar));
    }
    
    public default StringAccess<HOST> replace(CharSequence target, CharSequence replacement) {
        return stringAccess(null, str->str.replace(target, replacement));
    }
    
    public default StringAccess<HOST> replaceAll(String regEx, String replacement) {
        return stringAccess(null, str->str.replaceAll(regEx, replacement));
    }
    
    public default StringAccess<HOST> replaceFirst(String regEx, String replacement) {
        return stringAccess(null, str->str.replaceFirst(regEx, replacement));
    }
    
    public default FuncListAccess<HOST, String, StringAccess<HOST>> split(String regex) {
        return FuncListAccess.of(host -> {
            val strValue = StringAccess.this.apply(host);
            return FuncList.from(strValue.split(regex));
        }, func -> StringAccess.this.newAccess(func));
    }
    
    public default BooleanAccess<HOST> thatStartsWith(String prefix) {
        return booleanAccess(false, str->str.startsWith(prefix));
    }
    
    public default BooleanAccess<HOST> thatStartsWith(String prefix, int offset) {
        return booleanAccess(false, str->str.startsWith(prefix, offset));
    }
    
    public default StringAccess<HOST> substring(int beginIndex) {
        return stringAccess(null, str->str.substring(beginIndex));
    }
    
    public default StringAccess<HOST> substring(int beginIndex, int endIndex) {
        return stringAccess(null, str->str.substring(beginIndex, endIndex));
    }
    
    public default StringAccess<HOST> toLowerCase() {
        return stringAccess(null, str->str.toLowerCase());
    }
    
    public default StringAccess<HOST> toLowerCase(Locale locale) {
        return stringAccess(null, str->str.toLowerCase(locale));
    }
    public default StringAccess<HOST> toUpperCase() {
        return stringAccess(null, str->str.toUpperCase());
    }
    
    public default StringAccess<HOST> toUpperCase(Locale locale) {
        return stringAccess(null, str->str.toUpperCase(locale));
    }
    
    public default StringAccess<HOST> trim() {
        return stringAccess(null, str->str.trim());
    }
    
    //== parse ==
    
    //-- integer --
    
    public default IntegerAccessPrimitive<HOST> asInteger() {
        return host -> {
            val valueStr = apply(host);
            return Integer.parseInt(valueStr);
        };
    }
    public default IntegerAccessPrimitive<HOST> asInteger(int radix) {
        return host -> {
            val valueStr = apply(host);
            return Integer.parseInt(valueStr, radix);
        };
    }
    
    public default ResultAccess<HOST, Integer, IntegerAccess<HOST>> parseInteger() {
        return ResultAccess.of(host -> {
            val valueStr = apply(host);
            return Result.from(()->Integer.parseInt(valueStr));
        }, func -> (IntegerAccessBoxed<HOST>)(func::apply));
    }
    public default ResultAccess<HOST, Integer, IntegerAccess<HOST>> parseInteger(int radix) {
        return ResultAccess.of(host -> {
            val valueStr = apply(host);
            return Result.from(()->Integer.parseInt(valueStr, radix));
        }, func -> (IntegerAccessBoxed<HOST>)(func::apply));
    }
    
    //-- long --
    
    public default LongAccessPrimitive<HOST> asLong() {
        return host -> {
            val valueStr = apply(host);
            return Long.parseLong(valueStr);
        };
    }
    public default LongAccessPrimitive<HOST> asLong(int radix) {
        return host -> {
            val valueStr = apply(host);
            return Long.parseLong(valueStr, radix);
        };
    }
    
    public default ResultAccess<HOST, Long, LongAccess<HOST>> parseLong() {
        return ResultAccess.of(host -> {
            val valueStr = apply(host);
            return Result.from(()->Long.parseLong(valueStr));
        }, func -> (LongAccessBoxed<HOST>)(func::apply));
    }
    public default ResultAccess<HOST, Long, LongAccess<HOST>> parseLong(int radix) {
        return ResultAccess.of(host -> {
            val valueStr = apply(host);
            return Result.from(()->Long.parseLong(valueStr, radix));
        }, func -> (LongAccessBoxed<HOST>)(func::apply));
    }
    
    //-- double --
    
    public default DoubleAccessPrimitive<HOST> asDouble() {
        return host -> {
            val valueStr = apply(host);
            return Double.parseDouble(valueStr);
        };
    }
    public default ResultAccess<HOST, Double, DoubleAccess<HOST>> parseDouble() {
        return ResultAccess.of(host -> {
            val valueStr = apply(host);
            return Result.from(()->Double.parseDouble(valueStr));
        }, func -> (DoubleAccessBoxed<HOST>)(func::apply));
    }
    
    //-- big integer --
    
    public default BigIntegerAccess<HOST> asBigInteger() {
        return host -> {
            val valueStr = apply(host);
            return new BigInteger(valueStr);
        };
    }
    public default ResultAccess<HOST, BigInteger, BigIntegerAccess<HOST>> parseBigInteger() {
        return ResultAccess.of(host -> {
            val valueStr = apply(host);
            return Result.from(()->new BigInteger(valueStr));
        }, func -> (BigIntegerAccess<HOST>)(func::apply));
    }
    
    //-- big decimal --
    
    public default BigDecimalAccess<HOST> asBigDecimal() {
        return host -> {
            val valueStr = apply(host);
            return new BigDecimal(valueStr);
        };
    }
    public default ResultAccess<HOST, BigDecimal, BigDecimalAccess<HOST>> parseBigDecimal() {
        return ResultAccess.of(host -> {
            val valueStr = apply(host);
            return Result.from(()->new BigDecimal(valueStr));
        }, func -> (BigDecimalAccess<HOST>)(func::apply));
    }
    
    //-- local date --
    
    public default LocalDateAccess<HOST> asLocalDate() {
        return host -> {
            val valueStr = apply(host);
            return LocalDate.parse(valueStr);
        };
    }
    public default LocalDateAccess<HOST> asLocalDate(DateTimeFormatter formatter) {
        return host -> {
            val valueStr = apply(host);
            return LocalDate.parse(valueStr, formatter);
        };
    }
    public default LocalDateAccess<HOST> asLocalDate(String formatterPattern) {
        val formatter = DateTimeFormatter.ofPattern(formatterPattern);
        return asLocalDate(formatter);
    }
    
    public default ResultAccess<HOST, LocalDate, LocalDateAccess<HOST>> parseLocalDate() {
        return ResultAccess.of(host -> {
            val valueStr = apply(host);
            return Result.from(()->LocalDate.parse(valueStr));
        }, func -> (LocalDateAccess<HOST>)(func::apply));
    }
    public default ResultAccess<HOST, LocalDate, LocalDateAccess<HOST>> parseLocalDate(DateTimeFormatter formatter) {
        return ResultAccess.of(host -> {
            val valueStr = apply(host);
            return Result.from(()->LocalDate.parse(valueStr, formatter));
        }, func -> (LocalDateAccess<HOST>)(func::apply));
    }
    public default ResultAccess<HOST, LocalDate, LocalDateAccess<HOST>> parseLocalDate(String formatterPattern) {
        val formatter = DateTimeFormatter.ofPattern(formatterPattern);
        return parseLocalDate(formatter);
    }
    
    //-- local date time --
    
    public default LocalDateTimeAccess<HOST> asLocalDateTime() {
        return host -> {
            val valueStr = apply(host);
            return LocalDateTime.parse(valueStr);
        };
    }
    public default LocalDateTimeAccess<HOST> asLocalDateTime(DateTimeFormatter formatter) {
        return host -> {
            val valueStr = apply(host);
            return LocalDateTime.parse(valueStr, formatter);
        };
    }
    public default LocalDateTimeAccess<HOST> asLocalDateTime(String formatterPattern) {
        val formatter = DateTimeFormatter.ofPattern(formatterPattern);
        return asLocalDateTime(formatter);
    }
    
    public default ResultAccess<HOST, LocalDateTime, LocalDateTimeAccess<HOST>> parseLocalDateTime() {
        return ResultAccess.of(host -> {
            val valueStr = apply(host);
            return Result.from(()->LocalDateTime.parse(valueStr));
        }, func -> (LocalDateTimeAccess<HOST>)(func::apply));
    }
    public default ResultAccess<HOST, LocalDateTime, LocalDateTimeAccess<HOST>> parseLocalDateTime(DateTimeFormatter formatter) {
        return ResultAccess.of(host -> {
            val valueStr = apply(host);
            return Result.from(()->LocalDateTime.parse(valueStr, formatter));
        }, func -> (LocalDateTimeAccess<HOST>)(func::apply));
    }
    public default ResultAccess<HOST, LocalDateTime, LocalDateTimeAccess<HOST>> parseLocalDateTime(String formatterPattern) {
        val formatter = DateTimeFormatter.ofPattern(formatterPattern);
        return parseLocalDateTime(formatter);
    }
    
    //-- TODO Add Zoned date and stuff.
    
}

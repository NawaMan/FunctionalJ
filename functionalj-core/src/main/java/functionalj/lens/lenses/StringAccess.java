package functionalj.lens.lenses;

import static functionalj.functions.StrFuncs.toStr;
import static java.util.stream.Collectors.joining;

import java.util.Locale;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import functionalj.function.Func1;
import lombok.val;

@SuppressWarnings("javadoc")
@FunctionalInterface
public interface StringAccess<HOST> 
        extends 
            ObjectAccess<HOST, String>, 
            ConcreteAccess<HOST, String, StringAccess<HOST>> {
    
    @Override
    public default StringAccess<HOST> newAccess(Function<HOST, String> access) {
        return access::apply;
    }
    
    // Extra
    
    public default BooleanAccess<HOST> thatIsBlank() {
        return booleanAccess(true, str->str.trim().isEmpty());
    }
    
    // java.lang.String
    
    public default IntegerAccess<HOST> compareToIgnoreCase(String anotherString) {
        return intAccess(-1, str->str.compareToIgnoreCase(anotherString));
    }
    
    public default StringAccess<HOST> concat(Object ... suffixes) {
        return stringAccess("", str -> {
            val eachToString = __internal__.stringFrom(str);
            String suffix = Stream.of(suffixes).map(eachToString).collect(joining());
            return str + suffix;
        });
    }
    
    public default StringAccess<HOST> prefix(Object ... prefixes) {
        return stringAccess("", str -> {
            val eachToString = __internal__.stringFrom(str);
            String prefix = Stream.of(prefixes).map(eachToString).collect(joining());
            return prefix + str;
        });
    }
    public default StringAccess<HOST> suffix(Object ... suffixes) {
        return stringAccess("", str -> {
            val eachToString = __internal__.stringFrom(str);
            String prefix = Stream.of(suffixes).map(eachToString).collect(joining());
            return str + prefix;
        });
    }
    public default StringAccess<HOST> wrapBy(Object prefix, Object suffix) {
        return stringAccess("", str -> { 
            val eachToString = __internal__.stringFrom(str);
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
    
    public default BooleanAccess<HOST> thatEndsWith(String suffix) {
        boolean isSuffixEmpty = (suffix == null) || suffix.isEmpty();
        return booleanAccess(isSuffixEmpty, str->str.endsWith(suffix));
    }
    
	public default StringAccess<HOST> format(String format, Object... args) {
        return stringAccess(null, str->{
            val eachToString = __internal__.stringFrom(str);
            val argStrs      = Stream.of(args).map(eachToString).toArray();
            return String.format(format, (Object[])argStrs);
        });
    }
    
    public default BooleanAccess<HOST> thatEqualsIgnoreCase(String anotherString) {
        boolean isAnotherStringEmpty = (anotherString == null) || anotherString.isEmpty();
        return booleanAccess(isAnotherStringEmpty, str->str.equalsIgnoreCase(anotherString));
    }
    
    public default IntegerAccess<HOST> indexOf(int ch) {
        return intAccess(-1, str->str.indexOf(ch));
    }
    
    public default IntegerAccess<HOST> indexOf(int ch, int fromIndex) {
        return intAccess(-1, str->str.indexOf(ch, fromIndex));
    }
    
    public default IntegerAccess<HOST> indexOf(String needle) {
        return intAccess(-1, str->str.indexOf(needle));
    }
    
    public default IntegerAccess<HOST> indexOf(String needle, int fromIndex) {
        return intAccess(-1, str->str.indexOf(needle, fromIndex));
    }
    
    public default BooleanAccess<HOST> thatIsEmpty() {
        return booleanAccess(true, str->str.isEmpty());
    }
    
    public default IntegerAccess<HOST> lastIndexOf(int ch) {
        return intAccess(-1, str->str.lastIndexOf(ch));
    }
    
    public default IntegerAccess<HOST> lastIndexOf(int ch, int fromIndex) {
        return intAccess(-1, str->str.lastIndexOf(ch, fromIndex));
    }
    
    public default IntegerAccess<HOST> lastIndexOf(String needle) {
        return intAccess(-1, str->str.lastIndexOf(needle));
    }
    
    public default IntegerAccess<HOST> lastIndexOf(String needle, int fromIndex) {
        return intAccess(-1, str->str.lastIndexOf(needle, fromIndex));
    }
    
    public default IntegerAccess<HOST> length() {
        return intAccess(0, str->str.length());
    }
    
    public default BooleanAccess<HOST> thatMatches(String regex) {
        return booleanAccess(false, str->str.matches(regex));
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
    
    // TODO Split
    
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
    
    public static StringAccess<String> $(Object ... objs) {
        return str -> {
            val eachToString = __internal__.stringFrom(str);
            return Stream.of(objs).map(eachToString).collect(joining());
        };
    }
    
    public static final class __internal__ {
        private static Func1<Object, String> stringFrom(String str) {
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
    
}
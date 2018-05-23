package functionalj.lens;

import static functionalj.FunctionalStrings.stringOf;

import java.util.Locale;
import java.util.function.Function;

@FunctionalInterface
public  interface StringAccess<HOST> extends ObjectAccess<HOST,String> {
    
    // Extra
    
    public default BooleanAccess<HOST> thatIsBlank() {
        return booleanAccess(true, str->str.trim().isEmpty());
    }
    
    // java.lang.String
    
    public default IntegerAccess<HOST> compareToIgnoreCase(String anotherString) {
        return intAccess(-1, str->str.compareToIgnoreCase(anotherString));
    }
    
    public default StringAccess<HOST> concat(Object suffix) {
        return stringAccess(stringOf(suffix), str -> str + stringOf(suffix));
    }
    public default StringAccess<HOST> prefix(Object prefix) {
        return stringAccess(stringOf(prefix), str -> stringOf(prefix) + str);
    }
    public default StringAccess<HOST> wrapBy(Object prefix, Object suffix) {
        return stringAccess(stringOf(prefix) + stringOf(suffix), str -> stringOf(prefix) + str + stringOf(suffix));
    }
    
    public default BooleanAccess<HOST> thatContentEquals(CharSequence charSequence) {
        return booleanAccess(false, str->str.contentEquals(charSequence));
    }
    public default BooleanAccess<HOST> thatContains(CharSequence charSequence) {
        return booleanAccess(false, str->str.contains(charSequence));
    }
    
    public default BooleanAccess<HOST> thatEndsWith(String suffix) {
        boolean isSuffixEmpty = (suffix == null) || suffix.isEmpty();
        return booleanAccess(isSuffixEmpty, str->str.endsWith(suffix));
    }
    
    public default StringAccess<HOST> format(String format, Object... args) {
        return stringAccess(null, str->str.format(format, (Object[])args));
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

    public default Function<HOST, HOST> changeTo(String string) {
        return null;
    }
    
}
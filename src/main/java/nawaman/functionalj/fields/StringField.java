package nawaman.functionalj.fields;

import java.util.Locale;

@FunctionalInterface
public  interface StringField<HOST> extends ObjectField<HOST,String> {
    
    // Extra
    
    public default BooleanField<HOST> isBlank() {
        return booleanField(true, str->str.trim().isEmpty());
    }
    
    // java.lang.String
    
    public default IntegerField<HOST> compareToIgnoreCase(String anotherString) {
        return intField(-1, str->str.compareToIgnoreCase(anotherString));
    }
    
    public default StringField<HOST> concat(String suffix) {
        return stringField(suffix, str->str.concat(suffix));
    }
    
    public default BooleanField<HOST> contentEquals(CharSequence charSequence) {
        return booleanField(false, str->str.contentEquals(charSequence));
    }
    
    public default BooleanField<HOST> endsWith(String suffix) {
        boolean isSuffixEmpty = (suffix == null) || suffix.isEmpty();
        return booleanField(isSuffixEmpty, str->str.endsWith(suffix));
    }
    
    public default StringField<HOST> format(String format, Object... args) {
        return stringField(null, str->str.format(format, (Object[])args));
    }
    
    public default BooleanField<HOST> equalsIgnoreCase(String anotherString) {
        boolean isAnotherStringEmpty = (anotherString == null) || anotherString.isEmpty();
        return booleanField(isAnotherStringEmpty, str->str.equalsIgnoreCase(anotherString));
    }
    
    public default IntegerField<HOST> indexOf(int ch) {
        return intField(-1, str->str.indexOf(ch));
    }
    
    public default IntegerField<HOST> indexOf(int ch, int fromIndex) {
        return intField(-1, str->str.indexOf(ch, fromIndex));
    }
    
    public default IntegerField<HOST> indexOf(String needle) {
        return intField(-1, str->str.indexOf(needle));
    }
    
    public default IntegerField<HOST> indexOf(String needle, int fromIndex) {
        return intField(-1, str->str.indexOf(needle, fromIndex));
    }
    
    public default BooleanField<HOST> isEmpty() {
        return booleanField(true, str->str.isEmpty());
    }
    
    public default IntegerField<HOST> lastIndexOf(int ch) {
        return intField(-1, str->str.lastIndexOf(ch));
    }
    
    public default IntegerField<HOST> lastIndexOf(int ch, int fromIndex) {
        return intField(-1, str->str.lastIndexOf(ch, fromIndex));
    }
    
    public default IntegerField<HOST> lastIndexOf(String needle) {
        return intField(-1, str->str.lastIndexOf(needle));
    }
    
    public default IntegerField<HOST> lastIndexOf(String needle, int fromIndex) {
        return intField(-1, str->str.lastIndexOf(needle, fromIndex));
    }
    
    public default IntegerField<HOST> length() {
        return intField(-1, str->str.length());
    }
    
    public default BooleanField<HOST> matches(String regex) {
        return booleanField(false, str->str.matches(regex));
    }
    
    public default StringField<HOST> replace(char oldChar, char newChar) {
        return stringField(null, str->str.replace(oldChar, newChar));
    }
    
    public default StringField<HOST> replace(CharSequence target, CharSequence replacement) {
        return stringField(null, str->str.replace(target, replacement));
    }
    
    public default StringField<HOST> replaceAll(String regEx, String replacement) {
        return stringField(null, str->str.replaceAll(regEx, replacement));
    }
    
    public default StringField<HOST> replaceFirst(String regEx, String replacement) {
        return stringField(null, str->str.replaceFirst(regEx, replacement));
    }
    
    // TODO Split
    
    public default BooleanField<HOST> startsWith(String prefix) {
        return booleanField(false, str->str.startsWith(prefix));
    }
    
    public default BooleanField<HOST> startsWith(String prefix, int offset) {
        return booleanField(false, str->str.startsWith(prefix, offset));
    }
    
    public default StringField<HOST> substring(int beginIndex) {
        return stringField(null, str->str.substring(beginIndex));
    }
    
    public default StringField<HOST> substring(int beginIndex, int endIndex) {
        return stringField(null, str->str.substring(beginIndex, endIndex));
    }
    
    // TODO: subSequence == 
    // TODO: toCharArray()
    // TODO: getBytes()
    
    public default StringField<HOST> toLowerCase() {
        return stringField(null, str->str.toLowerCase());
    }
    
    public default StringField<HOST> toLowerCase(Locale locale) {
        return stringField(null, str->str.toLowerCase(locale));
    }
    public default StringField<HOST> toUpperCase() {
        return stringField(null, str->str.toUpperCase());
    }
    
    public default StringField<HOST> toUpperCase(Locale locale) {
        return stringField(null, str->str.toUpperCase(locale));
    }
    
    public default StringField<HOST> trim() {
        return stringField(null, str->str.trim());
    }
    
}
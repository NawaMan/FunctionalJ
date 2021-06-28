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

import static java.util.Objects.requireNonNull;

import java.util.function.Function;

import functionalj.function.CharSupplier;
import functionalj.function.Func1;
import functionalj.function.ToCharBiCharFunction;
import functionalj.function.ToCharFunction;
import lombok.val;



public interface CharacterAccess<HOST>
        extends 
            ObjectAccess<HOST, Character>,
            ToCharFunction<HOST>,
            Func1<HOST, Character> {
    
    public static <H> CharacterAccess<H> of(Function<H, Character> accessToValue) {
        requireNonNull(accessToValue);
        
        if (accessToValue instanceof CharacterAccess) {
            return (CharacterAccess<H>)accessToValue;
        }
        
        if (accessToValue instanceof ToCharFunction) {
            @SuppressWarnings("unchecked")
            val func1  = (ToCharFunction<H>)accessToValue;
            val access = ofPrimitive(func1);
            return access;
        }
        
        if (accessToValue instanceof Func1) {
            val func1  = (Func1<H, Character>)accessToValue;
            val access = (CharacterAccessBoxed<H>)func1::applyUnsafe;
            return access;
        }
        
        val func   = (Function<H, Character>)accessToValue;
        val access = (CharacterAccessBoxed<H>)(host -> func.apply(host));
        return access;
    }
    
    public static <H> CharacterAccess<H> ofPrimitive(ToCharFunction<H> accessToValue) {
        requireNonNull(accessToValue);
        val access = (CharacterAccessPrimitive<H>)accessToValue::applyAsChar;
        return access;
    }
    
    
    public char applyAsChar(HOST host);
    
    public Character applyUnsafe(HOST host) throws Exception;
    
    
    @Override
    public default CharacterAccess<HOST> newAccess(Function<HOST, Character> accessToValue) {
        return of(accessToValue);
    }
    
    public default IntegerAccessPrimitive<HOST> asInteger() {
        return host -> {
            char charValue = applyAsChar(host);
            return (int)charValue;
        };
    }
    
    
    public default IntegerAccessPrimitive<HOST> compareTo(char anotherValue) {
        return host -> {
            char charValue = applyAsChar(host);
            int compare  = Character.compare(charValue, anotherValue);
            return compare;
        };
    }
    public default IntegerAccessPrimitive<HOST> compareTo(CharSupplier anotherSupplier) {
        return host -> {
            char charValue    = applyAsChar(host);
            char anotherValue = anotherSupplier.getAsChar();
            int compare       = Integer.compare(charValue, anotherValue);
            return compare;
        };
    }
    public default IntegerAccessPrimitive<HOST> compareTo(CharacterAccess<HOST> anotherAccess) {
        return host -> {
            char charValue    = applyAsChar(host);
            char anotherValue = anotherAccess.applyAsChar(host);
            int compare      = Integer.compare(charValue, anotherValue);
            return compare;
        };
    }
    public default IntegerAccessPrimitive<HOST> compareTo(ToCharBiCharFunction<HOST> anotherFunction) {
        return host -> {
            char charValue    = applyAsChar(host);
            char anotherValue = anotherFunction.applyAsChar(host, charValue);
            int compare       = Character.compare(charValue, anotherValue);
            return compare;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatEquals(char anotherValue) {
        return host -> {
            char charValue = applyAsChar(host);
            return charValue == anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatEquals(CharSupplier anotherSupplier) {
        return host -> {
            char charValue = applyAsChar(host);
            char anotherValue = anotherSupplier.getAsChar();
            return charValue == anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatEquals(CharacterAccess<HOST> anotherAccess) {
        return host -> {
            char charValue = applyAsChar(host);
            char anotherValue = anotherAccess.applyAsChar(host);
            return charValue == anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatEquals(ToCharBiCharFunction<HOST> anotherFunction) {
        return host -> {
            char charValue = applyAsChar(host);
            char anotherValue = anotherFunction.applyAsChar(host, charValue);
            return charValue == anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatNotEquals(int anotherValue) {
        return host -> {
            char charValue = applyAsChar(host);
            return charValue != anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatNotEquals(CharSupplier anotherSupplier) {
        return host -> {
            char charValue    = applyAsChar(host);
            char anotherValue = anotherSupplier.getAsChar();
            return charValue != anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatNotEquals(CharacterAccess<HOST> anotherAccess) {
        return host -> {
            char charValue    = applyAsChar(host);
            char anotherValue = anotherAccess.applyAsChar(host);
            return charValue != anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatNotEquals(ToCharBiCharFunction<HOST> anotherFunction) {
        return host -> {
            char charValue    = applyAsChar(host);
            char anotherValue = anotherFunction.applyAsChar(host, charValue);
            return charValue != anotherValue;
        };
    }
    
    // boolean isHighSurrogate(char ch)
    // boolean isLowSurrogate(char ch)
    // boolean isSurrogate(char ch)
    // boolean isLowerCase(char ch)
    // boolean isUpperCase(char ch)
    // boolean isTitleCase(char ch)
    // boolean isDigit(char ch)
    // boolean isDefined(char ch)
    // boolean isLetter(char ch)
    // boolean isLetterOrDigit(char ch)
    // boolean isJavaIdentifierStart(char ch)
    // boolean isJavaIdentifierPart(char ch)
    // boolean isUnicodeIdentifierStart(char ch)
    // boolean isUnicodeIdentifierPart(char ch)
    // boolean isIdentifierIgnorable(char ch)
    
    public default CharacterAccessPrimitive<HOST> toLowerCase() {
        return host -> {
            char charValue = applyAsChar(host);
            return Character.toLowerCase(charValue);
        };
    }
    
    public default CharacterAccessPrimitive<HOST> toUpperCase() {
        return host -> {
            char charValue = applyAsChar(host);
            return Character.toUpperCase(charValue);
        };
    }
    
    public default CharacterAccessPrimitive<HOST> toTitleCase() {
        return host -> {
            char charValue = applyAsChar(host);
            return Character.toTitleCase(charValue);
        };
    }
    
    // int getNumericValue(char ch)
    // boolean isSpaceChar(char ch)
    // boolean isWhitespace(char ch)
    // boolean isISOControl(char ch)
    
//    * @see     Character#COMBINING_SPACING_MARK
//    * @see     Character#CONNECTOR_PUNCTUATION
//    * @see     Character#CONTROL
//    * @see     Character#CURRENCY_SYMBOL
//    * @see     Character#DASH_PUNCTUATION
//    * @see     Character#DECIMAL_DIGIT_NUMBER
//    * @see     Character#ENCLOSING_MARK
//    * @see     Character#END_PUNCTUATION
//    * @see     Character#FINAL_QUOTE_PUNCTUATION
//    * @see     Character#FORMAT
//    * @see     Character#INITIAL_QUOTE_PUNCTUATION
//    * @see     Character#LETTER_NUMBER
//    * @see     Character#LINE_SEPARATOR
//    * @see     Character#LOWERCASE_LETTER
//    * @see     Character#MATH_SYMBOL
//    * @see     Character#MODIFIER_LETTER
//    * @see     Character#MODIFIER_SYMBOL
//    * @see     Character#NON_SPACING_MARK
//    * @see     Character#OTHER_LETTER
//    * @see     Character#OTHER_NUMBER
//    * @see     Character#OTHER_PUNCTUATION
//    * @see     Character#OTHER_SYMBOL
//    * @see     Character#PARAGRAPH_SEPARATOR
//    * @see     Character#PRIVATE_USE
//    * @see     Character#SPACE_SEPARATOR
//    * @see     Character#START_PUNCTUATION
//    * @see     Character#SURROGATE
//    * @see     Character#TITLECASE_LETTER
//    * @see     Character#UNASSIGNED
//    * @see     Character#UPPERCASE_LETTER
    // int getType(char ch)
    
//    * @see Character#DIRECTIONALITY_UNDEFINED
//    * @see Character#DIRECTIONALITY_LEFT_TO_RIGHT
//    * @see Character#DIRECTIONALITY_RIGHT_TO_LEFT
//    * @see Character#DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC
//    * @see Character#DIRECTIONALITY_EUROPEAN_NUMBER
//    * @see Character#DIRECTIONALITY_EUROPEAN_NUMBER_SEPARATOR
//    * @see Character#DIRECTIONALITY_EUROPEAN_NUMBER_TERMINATOR
//    * @see Character#DIRECTIONALITY_ARABIC_NUMBER
//    * @see Character#DIRECTIONALITY_COMMON_NUMBER_SEPARATOR
//    * @see Character#DIRECTIONALITY_NONSPACING_MARK
//    * @see Character#DIRECTIONALITY_BOUNDARY_NEUTRAL
//    * @see Character#DIRECTIONALITY_PARAGRAPH_SEPARATOR
//    * @see Character#DIRECTIONALITY_SEGMENT_SEPARATOR
//    * @see Character#DIRECTIONALITY_WHITESPACE
//    * @see Character#DIRECTIONALITY_OTHER_NEUTRALS
//    * @see Character#DIRECTIONALITY_LEFT_TO_RIGHT_EMBEDDING
//    * @see Character#DIRECTIONALITY_LEFT_TO_RIGHT_OVERRIDE
//    * @see Character#DIRECTIONALITY_RIGHT_TO_LEFT_EMBEDDING
//    * @see Character#DIRECTIONALITY_RIGHT_TO_LEFT_OVERRIDE
//    * @see Character#DIRECTIONALITY_POP_DIRECTIONAL_FORMAT
    // int getDirectionality(char ch)
    
    // boolean isMirrored(char ch)
    // char reverseBytes(char ch)
    
}

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
package functionalj.functions;

import static functionalj.function.Absent.__;
import static java.util.stream.Collectors.joining;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import functionalj.function.Func;
import functionalj.function.Func1;
import functionalj.function.Func2;
import functionalj.function.Func3;
import functionalj.function.Func4;
import functionalj.function.Func5;
import functionalj.function.Func6;
import functionalj.functions.RegExMatchResult.RegExMatchResultStreamAccess;
import functionalj.lens.lenses.FuncListAccess;
import functionalj.lens.lenses.FuncMapAccess;
import functionalj.lens.lenses.StringAccess;
import functionalj.list.FuncList;
import functionalj.map.FuncMap;
import functionalj.stream.AsStreamPlus;
import functionalj.stream.IteratorPlus;
import functionalj.stream.StreamPlus;
import lombok.val;


// TODO - Should only contains methods that return functions or constance of functions

public class StrFuncs {
    
    public static Pattern GROUP_NAME_PATTERN = Pattern.compile("\\(\\?<([a-zA-Z][a-zA-Z0-9]*)>");
    
    @SuppressWarnings("unused")
    private static final Map<Integer, String> indentTabs = new ConcurrentHashMap<>();
    
    /**
     * Return the string representation of the given object or null if the object is null.
     * 
     * @param inputObject the input objects.
     * @return the string representation of the input object.
     */
    public static String toStr(Object inputObject) {
        return (inputObject == null) ? null : String.valueOf(inputObject);
    }
    
    public static boolean isEmpty(String str) {
        return (str == null) || str.isEmpty();
    }
    
    public static boolean isBlank(String str) {
        return (str == null) || str.isEmpty() || str.trim().isEmpty();
    }
    
    public static String whenEmpty(String str, String elseValue) {
        return isEmpty(str) ? elseValue : str;
    }
    
    public static String whenBlank(String str, String elseValue) {
        return isBlank(str) ? elseValue : str;
    }
    
    public static String whenEmpty(String str, Supplier<String> elseSupplier) {
        return isEmpty(str) ? elseSupplier.get() : str;
    }
    
    public static String whenBlank(String str, Supplier<String> elseSupplier) {
        return isBlank(str) ? elseSupplier.get() : str;
    }

    public static Func1<String, String> escapeJava() {
        return StrFuncs::escapeJava;
    }
    public static String escapeJava(String s) {
        return s.replace("\\", "\\\\")
                .replace("\t", "\\t")
                .replace("\b", "\\b")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\f", "\\f")
                .replace("\'", "\\'")
                .replace("\"", "\\\"");
    }
    
    /**
     * Returns a function that return the string representation of the given object or null if the object is null.
     * 
     * @param <I>  the input data type.
     * @return the function.
     */
    public static <I> Function<I, String> toStr() {
        return (i) -> toStr(i);
    }
    
    /**
     * Returns a predicate to check if the given string is not null and not empty.
     * 
     * @return the predicate.
     */
    public static Predicate<? super String> strNotNullOrEmpty() {
        return str -> (str != null) && !str.isEmpty();
    }
    
    public static <I> Function<AsStreamPlus<I>, String> concat(Class<I> clz) {
        return concat();
    }
    
    public static <I> Function<AsStreamPlus<I>, String> concat() {
        return strings -> strings.streamPlus().map(StrFuncs::toStr).join();
    }
    
    public static <I1, I2> BiFunction<I1, I2, String> concat2() {
        return (i1, i2) -> toStr(i1) + toStr(i2);
    }
    public static <I1, I2, I3> Func3<I1, I2, I3, String> concat3() {
        return (i1, i2, i3) -> toStr(i1) + toStr(i2) + toStr(i3);
    }
    public static <I1, I2, I3, I4> Func4<I1, I2, I3, I4, String> concat4() {
        return (i1, i2, i3, i4) -> toStr(i1) + toStr(i2) + toStr(i3) + toStr(i4);
    }
    public static <I1, I2, I3, I4, I5> Func5<I1, I2, I3, I4, I5, String> concat5() {
        return (i1, i2, i3, i4, i5) -> toStr(i1) + toStr(i2) + toStr(i3) + toStr(i4) + toStr(i5);
    }
    public static <I1, I2, I3, I4, I5, I6> Func6<I1, I2, I3, I4, I5, I6, String> concat6() {
        return (i1, i2, i3, i4, i5, i6) -> toStr(i1) + toStr(i2) + toStr(i3) + toStr(i4) + toStr(i5) + toStr(i6);
    }
    
    public static String joinNonNull(String delimiter, String ... parts) {
        return Stream.of(parts).filter(Objects::nonNull).collect(joining(delimiter));
    }
    
    public static <I> Func1<AsStreamPlus<I>, String> join(Class<I> clz, String delimiter) {
        return join(delimiter);
    }
    
    public static <I> Func1<AsStreamPlus<I>, String> join(String delimiter) {
        return strings -> strings.streamPlus().map(StrFuncs::toStr).join(delimiter);
    }
    
    public static <I1, I2> Func2<I1, I2, String> join2(String delimiter) {
        return (i1, i2) -> 
                    toStr(i1) + delimiter + 
                    toStr(i2);
    }
    public static <I1, I2, I3> Func3<I1, I2, I3, String> join3(String delimiter) {
        return (i1, i2, i3) -> 
                    toStr(i1) + delimiter + 
                    toStr(i2) + delimiter + 
                    toStr(i3);
    }
    public static <I1, I2, I3, I4> Func4<I1, I2, I3, I4, String> join4(String delimiter) {
        return (i1, i2, i3, i4) -> 
                    toStr(i1) + delimiter + 
                    toStr(i2) + delimiter + 
                    toStr(i3) + delimiter + 
                    toStr(i4);
    }
    public static <I1, I2, I3, I4, I5> Func5<I1, I2, I3, I4, I5, String> join5(String delimiter) {
        return (i1, i2, i3, i4, i5) -> 
                    toStr(i1) + delimiter + 
                    toStr(i2) + delimiter + 
                    toStr(i3) + delimiter + 
                    toStr(i4) + delimiter + 
                    toStr(i5);
    }
    public static <I1, I2, I3, I4, I5, I6> Func6<I1, I2, I3, I4, I5, I6, String> join6(String delimiter) {
        return (i1, i2, i3, i4, i5, i6) -> 
                    toStr(i1) + delimiter + 
                    toStr(i2) + delimiter + 
                    toStr(i3) + delimiter + 
                    toStr(i4) + delimiter + 
                    toStr(i5) + delimiter + 
                    toStr(i6);
    }
    
    /**
     * Returns a function that add prefix and suffix to the given input.
     * 
     * @param <I>  the input data type.
     * @param prefix  the prefix.
     * @param suffix  the suffix.
     * @return  the function.
     */
    @SuppressWarnings("unchecked")
    public static <I> Func1<I, String> wrapWith(String prefix, String suffix) {
        return (Func1<I, String>)concat3().bind(prefix, __, suffix);
    }
    
    /**
     * Returns a function that add prefix to the given input.
     * 
     * @param <I>  the input data type.
     * @param prefix  the prefix.
     * @return  the function.
     */
    @SuppressWarnings("unchecked")
    public static <I> Func1<I, String> prependWith(String prefix) {
        return (Func1<I, String>)concat3().bind(prefix, __, "");
    }
    
    /**
     * Returns a function that add suffix to the given input.
     * 
     * @param <I>  the input data type.
     * @param suffix  the suffix.
     * @return  the function.
     */
    @SuppressWarnings("unchecked")
    public static <I> Func1<I, String> appendWith(String suffix) {
        return (Func1<I, String>)concat3().bind("", __, suffix);
    }
    
    public static <I> Func1<I, String> withPrefix(String prefix) {
        return input -> prefix + input;
    }
    
    public static <I> Func1<I, String> withSuffix(String suffix) {
        return input -> input + suffix;
    }
    
    public static <I> Func1<I, String> withWrap(String prefix, String suffix) {
        return input -> prefix + input + suffix;
    }
    
    public static <I> Func1<I, String> withFormat(String format) {
        return input -> String.format(format, input);
    }
    
    public static <I> Func1<I, String> withFormat(MessageFormat format) {
        return input -> format.format(input);
    }
    
    public static <I> Func1<I, String> withPattern(Pattern pattern, String defaultValue) {
        return input -> {
            val inputStr = String.valueOf(input);
            val matcher  = pattern.matcher(inputStr);
            return matcher.find()
                    ? matcher.group()
                    : defaultValue;
        };
    }
    public static <I> Func1<I, String> withPattern(Pattern pattern, Supplier<String> defaultValueSupplier) {
        return input -> {
            val inputStr = String.valueOf(input);
            val matcher  = pattern.matcher(inputStr);
            return matcher.find()
                    ? matcher.group()
                    : defaultValueSupplier.get();
        };
    }
    public static <I> Func1<I, String> withPattern(Pattern pattern, Function<String, String> defaultValueFunction) {
        return input -> {
            val inputStr = String.valueOf(input);
            val matcher  = pattern.matcher(inputStr);
            return matcher.find()
                    ? matcher.group()
                    : defaultValueFunction.apply(inputStr);
        };
    }
    public static <I> Func1<I, String> withPath(Pattern pattern, BiFunction<String, Matcher, String> defaultValueFunction) {
        return input -> {
            val inputStr = String.valueOf(input);
            val matcher  = pattern.matcher(inputStr);
            return matcher.find()
                    ? matcher.group()
                    : defaultValueFunction.apply(inputStr, matcher);
        };
    }
    
    public static <I1> Func1<I1, Object> formatWith1(String template) {
        return (i1) -> String.format(template, i1);
    }
    
    public static <I1, I2> Func2<I1, I2, Object> formatWith2(String template) {
        return (i1, i2) -> String.format(template, i1, i2);
    }
    
    public static <I1, I2, I3> Func3<I1, I2, I3, Object> formatWith3(String template) {
        return (i1, i2, i3) -> String.format(template, i1, i2, i3);
    }
    
    public static <I1, I2, I3, I4> Func4<I1, I2, I3, I4, Object> formatWith4(String template) {
        return (i1, i2, i3, i4) -> String.format(template, i1, i2, i3, i4);
    }
    
    public static <I1, I2, I3, I4, I5> Func5<I1, I2, I3, I4, I5, Object> formatWith5(String template) {
        return (i1, i2, i3, i4, i5) -> String.format(template, i1, i2, i3, i4, i5);
    }
    
    public static <I1, I2, I3, I4, I5, I6> Func6<I1, I2, I3, I4, I5, I6, Object> formatWith6(String template) {
        return (i1, i2, i3, i4, i5, i6) -> String.format(template, i1, i2, i3, i4, i5, i6);
    }
    
    public static <I1> Func2<String, I1, String> strFormat1() {
        return (template, i1) -> String.format(template, i1);
    }
    
    public static <I1, I2> Func3<String, I1, I2, String> strFormat2() {
        return (template, i1, i2) -> String.format(template, i1, i2);
    }
    public static <I1, I2, I3> Func4<String, I1, I2, I3, String> strFormat3() {
        return (template, i1, i2, i3) -> String.format(template, i1, i2, i3);
    }
    
    public static <I1, I2, I3, I4> Func5<String, I1, I2, I3, I4, String> strFormat4() {
        return (template, i1, i2, i3, i4) -> String.format(template, i1, i2, i3, i4);
    }
    
    public static <I1, I2, I3, I4, I5> Func6<String, I1, I2, I3, I4, I5, String> strFormat5() {
        return (template, i1, i2, i3, i4, i5) -> String.format(template, i1, i2, i3, i4, i5);
    }
    
    // TODO Rethink this at some point ..... should this be constance when no generic?
    
    public static Func3<String, String, String, String> replaceAll() {
        return (str, regex, replacement) -> str.replaceAll(regex, replacement);
    }
    public static Func1<String, String> replaceAll(String regex, String replacement) {
        return (str) -> str.replaceAll(regex, replacement);
    }
    
    public static String repeat(char chr, int count) {
        if (count <= 0)
            return "";
        val buffer = new StringBuffer();
        for (int i = 0; i < count; i++)
            buffer.append(chr);
        return buffer.toString();
    }
    public static String repeat(String str, int count) {
        if (count <= 0)
            return "";
        val buffer = new StringBuffer();
        for (int i = 0; i < count; i++)
            buffer.append(str);
        return buffer.toString();
    }
    
    public static StreamPlus<String> split(String str, String regexDelimiter) {
        return split((CharSequence)str, regexDelimiter, -1);
    }
    public static StreamPlus<String> split(CharSequence str, String regexDelimiter) {
        return split((CharSequence)str, regexDelimiter, -1);
    }
    public static StreamPlus<String> split(String str, String regexDelimiter, RegExFlag flags) {
        return split((CharSequence)str, regexDelimiter, (flags != null) ? flags.getIntValue() : -1);
    }
    public static StreamPlus<String> split(CharSequence str, String regexDelimiter, RegExFlag flags) {
        return split((CharSequence)str, regexDelimiter, (flags != null) ? flags.getIntValue() : -1);
    }
    public static StreamPlus<String> split(String str, String regexDelimiter, int flags) {
        return split((CharSequence)str, regexDelimiter, flags);
    }
    public static StreamPlus<String> split(CharSequence str, String regexDelimiter, int flags) {
        if (str == null || (str.length() == 0))
            return StreamPlus.empty();
        
        val pattern = (flags < 0) ? Pattern.compile(regexDelimiter) : Pattern.compile(regexDelimiter, flags);
        val matcher = pattern.matcher(str);
        val offset  = new AtomicInteger(0);
        val isLast  = new AtomicReference<Boolean>(null);
        Iterable<String> iterable = new Iterable<String>() {
            @Override
            public Iterator<String> iterator() {
                return new Iterator<String>() {
                    @Override
                    public boolean hasNext() {
                        val find = matcher.find();
                        if (!find) {
                            if (isLast.get() == null) {
                                isLast.set(true);
                                return true;
                            } else {
                                return false;
                            }
                        }
                        return find;
                    }
                    @Override
                    public String next() {
                        val isLastBoolean = isLast.get();
                        if (isLastBoolean == null) {
                            val start = matcher.start();
                            val end   = matcher.end();
                            val next  = str.subSequence(offset.get(), start);
                            offset.set(end);
                            return next.toString();
                        }
                        if (isLastBoolean == true) {
                            val next  = str.subSequence(offset.get(), str.length());
                            return next.toString();
                        }
                        return null;
                    }
                };
            }
        };
        return StreamPlus.from(StreamSupport.stream(iterable.spliterator(), false));
    }
    public static StreamPlus<String> lines(String str) {
        return split(str, "(\n|\r\n?)");
    }
    
    public static String indent(String str) {
        if (str == null || str.isEmpty())
            return "";
        return "\t" + str.replaceAll("(\n|\r\n?)", "$1\t");
    }
    
    public static String leftPadding(String str, char prefix, int width) {
        if (str == null || str.isEmpty())
            return repeat(prefix, width);
        
        if (str.length() >= prefix)
            return str;
        
        return repeat(prefix, width - str.length()) + str;
    }
    
    public static <TEXT extends CharSequence> FuncListAccess<TEXT, String, StringAccess<TEXT>> grab(String regex) {
        return grab(regex, -1);
    }
    
    public static <TEXT extends CharSequence> FuncListAccess<TEXT, String, StringAccess<TEXT>> grab(String regex, RegExFlag flags) {
        return grab(regex, flags);
    }
    
    public static <TEXT extends CharSequence> FuncListAccess<TEXT, String, StringAccess<TEXT>> grab(String regex, int patternFlags) {
        return FuncListAccess.of(strValue -> grab(strValue, regex, patternFlags), StringAccess::of);
    }
    
    public static <TEXT extends CharSequence> FuncListAccess<TEXT, String, StringAccess<TEXT>> grab(Pattern pattern) {
        return FuncListAccess.of(strValue -> grab(strValue, pattern), StringAccess::of);
    }
    
    public static FuncList<String> grab(CharSequence strValue, String regex) {
        return grab(strValue, regex, 0);
    }
    
    public static FuncList<String> grab(CharSequence strValue, String regex, RegExFlag flags) {
        return grab(strValue, regex, flags.getIntValue());
    }
    
    public static FuncList<String> grab(CharSequence strValue, String regex, int flags) {
        val pattern = (flags < 0) ? Pattern.compile(regex) : Pattern.compile(regex, flags);
        return grab(strValue, pattern);
    }
    
    public static FuncList<String> grab(CharSequence strValue, Pattern pattern) {
        return FuncList.from(FuncList.from(()->{
            val matcher  = pattern.matcher(strValue);
            try (val iterator = createMatchIterator(matcher)) {
                return iterator.stream();
            }
        }));
    }
    
    private static IteratorPlus<String> createMatchIterator(final java.util.regex.Matcher matcher) {
        return new IteratorPlus<String>() {
            @Override
            public Iterator<String> asIterator() {
                return new Iterator<String>() {
                    @Override
                    public boolean hasNext() {
                        return matcher.find();
                    }
                    @Override
                    public String next() {
                        return matcher.group();
                    }
                };
            }
        };
    }
    
    public static <TEXT extends CharSequence> RegExMatchResultStreamAccess<TEXT> matches(String regex) {
        return matches(regex, -1);
    }
    public static <TEXT extends CharSequence> RegExMatchResultStreamAccess<TEXT> matches(String regex, RegExFlag flags) {
        return matches(regex, flags.getIntValue());
    }
    public static <TEXT extends CharSequence> RegExMatchResultStreamAccess<TEXT> matches(String regex, int flags) {
        return new RegExMatchResultStreamAccess<>(str -> matches(str, regex, -1));
    }
    public static <TEXT extends CharSequence> RegExMatchResultStreamAccess<TEXT> matches(Pattern pattern) {
        return new RegExMatchResultStreamAccess<>(str -> matches(str, pattern));
    }
    public static RegExMatchResultStream matches(CharSequence str, String regex) {
        return matches(str, regex, -1);
    }
    public static RegExMatchResultStream matches(CharSequence str, String regex, RegExFlag flags) {
        return matches(str, regex, flags.getIntValue());
    }
    public static RegExMatchResultStream matches(CharSequence str, String regex, int flags) {
        if (str == null || (str.length() == 0))
            return RegExMatchResultStream.empty;
        
        val pattern = (flags < 0) ? Pattern.compile(regex) : Pattern.compile(regex, flags);
        return matches(str, pattern);
    }
    public static RegExMatchResultStream matches(CharSequence str, Pattern pattern) {
        if (str == null || (str.length() == 0))
            return RegExMatchResultStream.empty;
        
        val matcher = pattern.matcher(str);
        val source  = Func.lazy(()->str.toString());
        val index   = new AtomicInteger();
        
        Iterable<RegExMatchResult> iterable = new Iterable<RegExMatchResult>() {
            @Override
            public Iterator<RegExMatchResult> iterator() {
                return new Iterator<RegExMatchResult>() {
                    @Override
                    public boolean hasNext() {
                        return matcher.find();
                    }
                    @Override
                    public RegExMatchResult next() {
                        return new RegExMatchResult(source, pattern, index.getAndIncrement(), matcher.toMatchResult());
                    }
                };
            }
        };
        return RegExMatchResultStream.from(StreamSupport.stream(iterable.spliterator(), false));
    }
    
    /**
     * Create a string using the given template and the replacer.
     * 
     * The replacement will be done by the name of the capture group denotated by -- "$" for example "$name". 
     * 
     * Examples:
     * <ol>
     * <li>assertEquals("--hello--there-$SS-",  template("--$Hello--$There-$$SS-",  str -> str.toLowerCase()));</li>
     * <li>assertEquals("--hello--there-$$SS-", template("--$Hello--$There-$$$SS-", str -> str.toLowerCase()));</li>
     * <li>assertEquals("--hello--there-$0S-",  template("--$Hello--$There-$0S-",   str -> str.toLowerCase()))</li>
     * </ol>
     **/
    public static String template(String str, Func1<String, Object> replacer) {
        return template((CharSequence)str, replacer);
    }
    
    public static String template(CharSequence str, Func1<String, Object> replacer) {
        if (str == null || (str.length() == 0))
            return "";
        
        StringBuffer buffer = new StringBuffer();
        val pattern = Pattern.compile("(?<!\\$)\\$\\{(?<capture>[a-zA-Z][a-zA-Z0-9_]+[ ]*)\\}");
        val matcher = pattern.matcher(str);
        while (matcher.find()) {
            val capture     = matcher.group("capture");
            val name        = capture.trim();
            val replacement = String.valueOf(replacer.apply(name));
            matcher.appendReplacement(buffer, replacement);
        }
        matcher.appendTail(buffer);
        return buffer.toString()
                    .replace("$$", "$");
    }
    
    
    public static <TEXT extends CharSequence> 
            FuncMapAccess<TEXT, String, String, StringAccess<TEXT>, StringAccess<TEXT>> capture(String regex) {
        return capture(regex, -1);
    }
    
    public static <TEXT extends CharSequence> 
            FuncMapAccess<TEXT, String, String, StringAccess<TEXT>, StringAccess<TEXT>> capture(String regex, RegExFlag flags) {
        return capture(regex, flags.getIntValue());
    }
    
    public static <TEXT extends CharSequence> 
            FuncMapAccess<TEXT, String, String, StringAccess<TEXT>, StringAccess<TEXT>> capture(String regex, int flags) {
        val pattern = (flags < 0) ? Pattern.compile(regex.toString()) : Pattern.compile(regex.toString(), flags);
        return capture(pattern);
    }
    
    public static <TEXT extends CharSequence> 
            FuncMapAccess<TEXT, String, String, StringAccess<TEXT>, StringAccess<TEXT>> capture(Pattern pattern) {
        return FuncMapAccess.of(strValue -> capture(strValue, pattern), StringAccess::of, StringAccess::of);
    }
    
    public static Map<String, String> capture(CharSequence text, String regex) {
        return capture(text, regex, -1);
    }
    
    public static Map<String, String> capture(CharSequence text, String regex, RegExFlag flags) {
        return capture(text, regex, flags.getIntValue());
    }
    
    public static Map<String, String> capture(CharSequence text, String regex, int flags) {
        if (text == null || (text.length() == 0))
            return FuncMap.empty();
        
        val pattern = (flags < 0) ? Pattern.compile(regex) : Pattern.compile(regex, flags);
        return capture(text, pattern);
    }
    
    public static Map<String, String> capture(CharSequence text, Pattern pattern) {
        if (text == null || (text.length() == 0))
            return FuncMap.empty();
        
        val matcher = pattern.matcher(text);
        if (!matcher.find())
            return FuncMap.empty();
        
        return grab(pattern.pattern(), GROUP_NAME_PATTERN)
                .map  (each -> each.substring(3, each.length() - 1))
                .toMap(name -> name,
                       name -> matcher.group(name));
    }
    
}

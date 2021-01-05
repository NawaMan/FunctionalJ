// ============================================================================
// Copyright(c) 2017-2021 Nawapunth Manusitthipol (NawaMan - http://nawaman.net)
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
package functionalj.types.struct.generator;

import static functionalj.types.struct.generator.utils.delimitWith;
import static functionalj.types.struct.generator.utils.prependWith;
import static functionalj.types.struct.generator.utils.strNotNullOrEmpty;
import static functionalj.types.struct.generator.utils.themAll;
import static functionalj.types.struct.generator.utils.toStr;
import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

import functionalj.types.IRequireTypes;
import functionalj.types.Type;
import lombok.val;


/**
 * Classes implementing this interface can turns itself into lines.
 *
 * @author NawaMan -- nawa@nawaman.net
 */
public interface ILines extends IRequireTypes {
    
    public static final Function<String, ILines> toLine = string -> ILines.line(string);
    
    /** An empty line */
    public static final ILines emptyLine = ()->Stream.of("");
    /** A no line */
    public static final ILines noLine = ()->Stream.empty();
    
    /** @return  the lines. **/
    public Stream<String> lines();
    
    /**
     * Returns the lines as one string (each line separated by '\n').
     *
     * @return  the whole string.
     */
    public default String toText() {
        return lines().collect(joining("\n"));
    }
    
    @Override
    public default Stream<Type> requiredTypes() {
        return Stream.empty();
    }
    
    public default Stream<ILines> toStream() {
        return Stream.of(this);
    }
    
    // == Factory methods ==
    
    /**
     * Create and return an ILines from the given lines. Any of the line that is null will be ignored.
     *
     * @param lines  the lines.
     * @return       the ILines.
     */
    public static ILines line(String ... lines) {
        if (lines != null && lines.length == 0)
            return noLine;
        
        return ()->Stream.of(lines)
                .filter(Objects::nonNull);
    }
    /**
     * Returns lines of the given stream of string.
     *
     * @param stream  the stream.
     * @return        the lines.
     */
    public static ILines line(Stream<String> stream) {
        return ()->stream.filter(Objects::nonNull);
    }
    /**
     * Returns lines of the given list of string.
     *
     * @param list  the list.
     * @return      the lines.
     */
    public static ILines line(List<String> list) {
        return ()->list.stream().filter(Objects::nonNull);
    }
    
    /**
     * Create a clean (no-null line) of the given lines.
     *
     * @param lines  the lines.
     * @return       the lines.
     */
    public static ILines of(ILines lines) {
        return () -> Stream.of(lines)
                .flatMap(ILines::lines)
                .filter(Objects::nonNull);
    }
    
    /**
     * Create a combine clean lines of the given stream of lines.
     *
     * @param stream  the stream of lines.
     * @return        the lines.
     */
    public static ILines linesOf(Stream<ILines> stream) {
        return ()-> Stream.of(stream)
                .flatMap(themAll())
                .flatMap(ILines::lines)
                .filter (Objects::nonNull);
    }
    
    /**
     * Create a combine clean lines of the given list of lines.
     *
     * @param list  the list of lines.
     * @return      the lines.
     */
    public static ILines linesOf(List<ILines> list) {
        return linesOf(list.stream());
    }
    
    /**
     * Create a combine clean lines of the given array of lines.
     *
     * @param array  the array of lines.
     * @return       the lines.
     */
    public static ILines linesOf(ILines ... array) {
        return linesOf(stream(array));
    }
    
    /**
     * Create a combine clean lines of the given list of ILines.
     *
     * @param listOfILines  the list of ILines.
     * @return             the lines.
     */
    @SafeVarargs
    public static ILines linesOf(List<ILines> ... listOfILines) {
        return ILines.linesOf(
                stream  (listOfILines)
                .filter (list -> !list.isEmpty())
                .flatMap(delimitWith(asList(ILines.emptyLine)))
                .flatMap(List::stream)
                .map    (ILines.class::cast));
    }
    
    /**
     * Create indented lines (4 spaces) for the given lines.
     *
     * @param lines  the lines.
     * @return       the lines.
     */
    public static ILines indent(ILines lines) {
        return indent(Stream.of(lines));
    }
    
    /**
     * Create indented lines (4 spaces) for the given list of lines.
     *
     * @param <LIST>  the list type of the list.
     * @param list    the list of lines.
     * @return        the lines.
     */
    public static <LIST extends List<? extends ILines>> ILines indent(LIST list) {
        return indent(list.stream());
    }
    
    /**
     * Create indented empty line as an ILines.
     *
     * @return  the lines.
     */
    public static ILines indent() {
        return indent(Stream.of(line("")));
    }
    public default ILines indent(int count) {
        ILines lines = this;
        for (int i = 0; i < count; i++)
            lines = indent(lines);
        return lines;
    }
    /**
     * Create indented empty lines from the given stream of lines.
     *
     * @param stream  the stream of lines.
     * @return        the result lines.
     */
    public static ILines indent(Stream<? extends ILines> stream) {
        return ()-> Stream.of(stream)
                .flatMap(themAll())
                .flatMap(ILines::lines)
                .filter (Objects::nonNull)
                .map    (prependWith("    "));
    }
    
    /**
     * Combines the parts into one line each separated by a space - null or empty part is ignored.
     *
     * @param parts  the parts.
     * @return       the combined line.
     */
    public static String oneLineOf(Object ... parts) {
        return stream(parts)
            .map     (toStr())
            .filter  (strNotNullOrEmpty())
            .collect (joining(" "));
    }
    
    /**
     * Given an array of lines, create one (clean) single ILines.
     *
     * @param arrayOflines  the array.
     * @return              the result lines.
     */
    public static ILines flatenLines(ILines ... arrayOflines) {
        return flatenLines(
                stream(arrayOflines));
    }
    
    /**
     * Given an stream of lines, create one (clean) single ILines.
     *
     * @param streamOflines  the stream.
     * @return               the result lines.
     */
    public static ILines flatenLines(Stream<ILines> streamOflines) {
        return ILines.line(
                streamOflines
                .flatMap(ILines::lines)
                .filter(Objects::nonNull));
    }
    
    /**
     * Create a combine stream of ILines given the ILines separate each one with an empty indented line.
     *
     * @param   iLines  the input lines.
     * @return          the result lines.
     */
    public static Stream<ILines> withSeparateIndentedSpace(ILines ... iLines) {
        return Stream.of(iLines)
                .flatMap(delimitWith(()->indent()));
    }
    
    public default ILines append(String line) {
        return ILines.linesOf(Stream.concat(this.lines(), Stream.of(line)).map(toLine));
    }
    public default ILines append(ILines lines) {
        return ILines.linesOf(Stream.concat(this.lines(), lines.lines()).map(toLine));
    }
    public default ILines containWith(String prefix, String delimiter, String suffix) {
        val lines     = this.lines().collect(toList());
        val firstLine = lines.stream().limit(1).map(line -> prefix    + " " + line).collect(toList());
        val restLines = lines.stream().skip (1).map(line -> delimiter + " " + line).collect(toList());
        val lastLine  = (suffix == null) ? null : Stream.of(suffix);
        val stream
            = Stream.of(
                firstLine.stream(),
                restLines.stream(),
                lastLine)
            .filter (Objects::nonNull)
            .flatMap(identity())
            .map    (String::trim)
            .map    (ILines::line);
        return ILines.linesOf(stream);
    }
    
}

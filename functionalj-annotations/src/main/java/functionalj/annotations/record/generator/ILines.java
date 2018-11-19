//  ========================================================================
//  Copyright (c) 2017 Nawapunth Manusitthipol (NawaMan).
//  ------------------------------------------------------------------------
//  All rights reserved. This program and the accompanying materials
//  are made available under the terms of the Eclipse Public License v1.0
//  and Apache License v2.0 which accompanies this distribution.
//
//      The Eclipse Public License is available at
//      http://www.eclipse.org/legal/epl-v10.html
//
//      The Apache License v2.0 is available at
//      http://www.opensource.org/licenses/apache2.0.php
//
//  You may elect to redistribute this code under either of these licenses.
//  ========================================================================
package functionalj.annotations.record.generator;

import static functionalj.annotations.record.generator.utils.delimitWith;
import static functionalj.annotations.record.generator.utils.prependWith;
import static functionalj.annotations.record.generator.utils.strNotNullOrEmpty;
import static functionalj.annotations.record.generator.utils.themAll;
import static functionalj.annotations.record.generator.utils.toStr;
import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Classes implementing this interface can turns itself into lines.
 * 
 * @author NawaMan -- nawa@nawaman.net
 */
public interface ILines {

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
    
}
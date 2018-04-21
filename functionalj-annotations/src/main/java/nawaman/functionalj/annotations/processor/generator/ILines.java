package nawaman.functionalj.annotations.processor.generator;

import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static nawaman.functionalj.FunctionalJ.themAll;
import static nawaman.functionalj.functions.StringFunctions.prependWith;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public interface ILines {

    public static final ILines       emptyLine     = ILines.line();
    public static final List<ILines> emptyLineList = asList(emptyLine);
    
    public Stream<String> lines();
    
    public static ILines line(String ... lines) {
        if (lines != null && lines.length == 0)
            return ()->Stream.of("");
        
        return ()->Stream.of(lines).filter(Objects::nonNull);
    }
    public static ILines line(Stream<String> stream) {
        return ()->stream.filter(Objects::nonNull);
    }
    public static ILines of(String ... lines) {
        if (lines != null && lines.length == 0)
            return ()->Stream.of("");
        
        return ()->Stream.of(lines).filter(Objects::nonNull);
    }
    public static ILines of(Stream<String> stream) {
        return ()->stream.filter(Objects::nonNull);
    }
    public static ILines linesOf(ILines lines) {
        return () -> Stream.of(lines)
                .flatMap(ILines::lines)
                .filter(Objects::nonNull);
    }
    
    public static ILines linesOf(Stream<ILines> streams) {
        return ()-> Stream.of(streams).flatMap(themAll()).flatMap(ILines::lines).filter(Objects::nonNull);
    }
    public static ILines linesOf(List<ILines> lines) {
        return linesOf(lines.stream());
    }
    public static ILines indent(ILines lines) {
        return indent(Stream.of(lines));
    }
    public static ILines indent(Stream<? extends ILines> streams) {
        return ()-> Stream.of(streams)
                .flatMap(themAll())
                .flatMap(ILines::lines)
                .filter(Objects::nonNull)
                .map(prependWith("    "));
    }
    
    public static <L extends List<? extends ILines>> ILines indent(L contents) {
        return indent(contents.stream());
    }
    
    public static ILines indent() {
        return indent(Stream.of(line("")));
    }
    
    public static ILines flatenLines(ILines ... arrayOflines) {
        return ILines.line(
                stream(arrayOflines)
                .flatMap(ILines::lines)
                .filter(Objects::nonNull));
    }
}
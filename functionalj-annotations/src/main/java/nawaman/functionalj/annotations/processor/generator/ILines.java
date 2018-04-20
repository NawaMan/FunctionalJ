package nawaman.functionalj.annotations.processor.generator;

import static nawaman.functionalj.FunctionalJ.themAll;
import static nawaman.functionalj.functions.StringFunctions.prependWith;

import java.util.Objects;
import java.util.stream.Stream;

public interface ILines {
    
    public Stream<String> lines();
    
    public static ILines line(String ... lines) {
        if (lines != null && lines.length == 0)
            return ()->Stream.of("");
        
        return ()->Stream.of(lines).filter(Objects::nonNull);
    }
    public static ILines of(ILines lines) {
        return () -> Stream.of(lines)
                .flatMap(ILines::lines)
                .filter(Objects::nonNull);
    }
    
    public static ILines of(Stream<ILines> streams) {
        return ()-> Stream.of(streams).flatMap(themAll()).flatMap(ILines::lines).filter(Objects::nonNull);
    }
    public static ILines indent(ILines lines) {
        return indent(Stream.of(lines));
    }
    public static ILines indent(Stream<ILines> streams) {
        return ()-> Stream.of(streams)
                .flatMap(themAll())
                .flatMap(ILines::lines)
                .filter(Objects::nonNull)
                .map(prependWith("    "));
    }
    
    public static ILines indent() {
        return indent(Stream.of(line("")));
    }
}
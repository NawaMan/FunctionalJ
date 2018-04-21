package nawaman.functionalj.annotations.processor.generator.model;

import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static nawaman.functionalj.FunctionalJ.allLists;
import static nawaman.functionalj.FunctionalJ.themAll;
import static nawaman.functionalj.annotations.processor.generator.ILines.flatenLines;
import static nawaman.functionalj.annotations.processor.generator.ILines.indent;
import static nawaman.functionalj.annotations.processor.generator.ILines.line;
import static nawaman.functionalj.annotations.processor.generator.ILines.oneLineOf;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import lombok.Value;
import lombok.val;
import lombok.experimental.Accessors;
import lombok.experimental.Wither;
import nawaman.functionalj.annotations.processor.generator.ILines;
import nawaman.functionalj.annotations.processor.generator.IRequireTypes;
import nawaman.functionalj.annotations.processor.generator.Type;

@Value
@Wither
@Accessors(fluent=true)
public class GenClass implements IRequireTypes {
    
    private final Accessibility accessibility;
    private final Scope         scope;
    private final Modifiability modifiability;
    
    private final Type       type;
    private final String     generic;
    private final List<Type> extendeds;
    private final List<Type> implementeds;
    private final List<GenConstructor> constructors;
    private final List<GenField>       fields;
    private final List<GenMethod>      methods;
    private final List<GenClass>      innerClasses;
    private final List<ILines>         mores;
    
    @Override
    public Stream<Type> requiredTypes() {
        return asList(
                        asList((IRequireTypes)(()->Stream.of(type))),
                        asList((IRequireTypes)(()->extendeds.stream())),
                        asList((IRequireTypes)(()->implementeds.stream())),
                        constructors,
                        fields,
                        mores
                ).stream()
                .flatMap(allLists())
                .map(IRequireTypes.class::cast)
                .map(IRequireTypes::requiredTypes)
                .flatMap(themAll());
    }
    
    /**
     * Create and return the definition of this class.
     * 
     * @return the definition of the this class.
     */
    public ILines toDefinition() {
        // TODO - Should be good to be able to trim to null and add prefix or suffix when not null easily.
        val extendedList    = extendeds()   .stream().map(Type::simpleNameWithGeneric).collect(joining(",")).trim();
        val implementedList = implementeds().stream().map(Type::simpleNameWithGeneric).collect(joining(",")).trim();
        
        val fieldDefs       = fields()      .stream().map(GenField      ::toDefinition).collect(toList());
        val constructorDefs = constructors().stream().map(GenConstructor::toDefinition).collect(toList());
        val methodDefs      = methods()     .stream().map(GenMethod     ::toDefinition).collect(toList());
        val innerClassDefs  = innerClasses().stream().map(GenClass      ::toDefinition).collect(toList());
        val moreDefs        = mores()       .stream().collect(toList());
        
        val className = type().simpleNameWithGeneric();
        val firstLine
                = oneLineOf(
                    accessibility, scope, modifiability, "class", className,
                    prefixWith(extendedList,    "extends "),
                    prefixWith(implementedList, "implements "),
                    "{");
        
        val lastLine = "}";
        
        val componentLines
                = getComponentLines(
                    fieldDefs,
                    constructorDefs,
                    methodDefs,
                    innerClassDefs,
                    moreDefs);
        
        val lines
                = flatenLines(
                    line(firstLine),
                    indent(),
                    indent(componentLines),
                    indent(),
                    line(lastLine));
        return lines;
    }
    
    @SafeVarargs
    private static List<ILines> getComponentLines(List<ILines> ... components) {
        val contents = new ArrayList<ILines>();
        stream(components)
        .filter (list  -> !list.isEmpty())
        .forEach(lines -> {
            contents.addAll(lines);
            contents.add(ILines.emptyLine);
        });
        contents.remove(contents.size() - 1);
        return contents;
    }
    
    private static String prefixWith(String str, String prefix) {
        if (str == null)
            return null;
        if (str.isEmpty())
            return null;
        return prefix + str;
    }
}
package functionalj.annotations.uniontype.generator;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

import java.util.List;
import java.util.Objects;
import java.util.TreeSet;

import lombok.val;


public class TargetClass implements Lines {
    public final SourceSpec spec;
    public final Type       type;
    
    public TargetClass(SourceSpec spec) {
        this.spec = spec;
        this.type = new Type(spec.sourceType.pckg, spec.targetName);
    }
    
    @Override
    public List<String> lines() {
        val imports     = new TreeSet<String>();
        imports.add("java.util.function.Function");
        imports.add("java.util.function.Consumer");
        imports.add("java.util.function.Predicate");
        imports.add("java.util.function.Supplier");
        imports.add("functionalj.annotations.Absent");
        imports.add("functionalj.annotations.uniontype.UnionTypeSwitch");
        imports.add("functionalj.annotations.uniontype.IUnionType");
        imports.add("functionalj.types.result.Result");
        imports.add("functionalj.pipeable.Pipeable");
        
        spec.choices.stream()
            .map   (c -> c.validationMethod)
            .filter(m -> m != null)
            .findAny()
            .ifPresent(s -> {
                imports.add(spec.sourceType.pckg + "." + spec.sourceType.encloseClass);
            });
        
        val subClassConstructors 
                = spec.choices.stream()
                .flatMap(choice -> new SubClassConstructor(this, choice).lines().stream())
                .filter(Objects::nonNull)
                .map("    "::concat)
                .collect(toList());
        
        val subClassDefinitions
                = spec.choices.stream()
                .flatMap(choice -> new SubClassDefinition(this, choice).lines().stream())
                .filter(Objects::nonNull)
                .map("    "::concat)
                .collect(toList());
        
        val targetGeneral
                = new TargetTypeGeneral(this, spec.choices)
                .lines().stream()
                .map("    "::concat)
                .collect(toList());
        
        val targetCheckMethods
                = new SubCheckMethod(this, spec.choices)
                .lines().stream()
                .map("    "::concat)
                .collect(toList());
        
        val switchClasses = range(0, spec.choices.size())
                .mapToObj(index   -> spec.choices.stream().skip(index).collect(toList()))
                .flatMap (choices -> new SwitchClass(this, (choices.size() == spec.choices.size()), choices).lines().stream())
                .filter(Objects::nonNull)
                .map("    "::concat)
                .collect(toList())
                ;
        
        val pckgName    = spec.sourceType.pckg;
        val importLines = imports.stream().map(i -> "import " + i + ";").collect(toList());
        return asList(
                asList(format("package %s;", pckgName)),
                asList(format("")),
                importLines,
                asList(format("")),
                asList(format("@SuppressWarnings(\"javadoc\")")),
                asList(format("public abstract class %1$s extends IUnionType<%1$s.%1$sFirstSwitch> implements Pipeable<%1$s> {", type.name)),
                asList(format("    ")),
                subClassConstructors,
                asList(format("    ")),
                asList(format("    private %s() {}", type.name)),
                asList(format("    public %1$s __data() throws Exception { return this; }", type.name)),
                asList(format("    ")),
                subClassDefinitions,
                asList(format("    ")),
                targetGeneral,
                targetCheckMethods,
                asList(format("    ")),
                switchClasses,
                asList(format("    ")),
                asList(format("}"))
            ).stream()
            .flatMap(List::stream)
            .collect(toList());
    }
}
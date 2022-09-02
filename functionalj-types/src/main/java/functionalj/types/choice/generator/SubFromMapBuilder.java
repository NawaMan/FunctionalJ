package functionalj.types.choice.generator;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Stream;

import functionalj.types.choice.generator.model.Case;
import lombok.val;


public class SubFromMapBuilder implements Lines {
    
    private final TargetClass targetClass;
    private final Case choice;
    
    public SubFromMapBuilder(TargetClass targetClass, Case choice) {
        this.targetClass = targetClass;
        this.choice      = choice;
    }
    
    private Stream<String> body() {
        val paramIndex = new AtomicInteger(choice.params.size());
        Supplier<String> comma = ()-> (paramIndex.decrementAndGet() != 0) ? "," : "";
        return Stream.of(
                Stream.of("    return " + choice.name + "("),
                choice
                    .params.stream()
                    .map(param -> {
                        val indent        = "            ";
                        val fieldType     = param.type();
                        val isGenericType =  !fieldType.simpleName().contains(".") 
                                          && (fieldType.packageName() == null)
                                          && targetClass.type.generics().stream()
                                             .filter(generic -> generic.name.equals(fieldType.simpleName()))
                                             .findAny().isPresent();
                        val fieldTypeName = isGenericType 
                                          ? targetClass.type.generics().stream()
                                                .filter (generic -> generic.name.equals(fieldType.simpleName()))
                                                .flatMap(generic -> generic.boundTypes.stream())
                                                .map    (type    -> type.simpleName())
                                                .findFirst()
                                                .orElse(fieldType.simpleName())
                                          : fieldType.simpleName();
                        val fieldTypeFull = isGenericType ? fieldTypeName : fieldType.simpleNameWithGeneric();
                        val extraction 
                                = format(
                                    "%s(%s)$utils.extractPropertyFromMap(%s.class, %s.class, map, __schema__, \"%s\")%s",
                                    indent,
                                    fieldTypeFull,
                                    choice.name,
                                    fieldTypeName,
                                    param.name(),
                                    comma.get()
                                );
                        return extraction;
                        
                        
                        
//                        return "        $utils.extractPropertyFromMap(map, __schema__, \"" + param.name() + "\")" + comma.get();
                    }),
                Stream.of("    );")
            )
            .flatMap(allLines -> allLines);
    }
    
    @Override
    public List<String> lines() {
        return Stream.of(
                Stream.of("public static " + choice.name + " caseFromMap(java.util.Map<String, ? extends Object> map) {"),
                body(),
                Stream.of("}")
            )
            .flatMap(allLines -> allLines)
            .collect(toList());
    }
    
}

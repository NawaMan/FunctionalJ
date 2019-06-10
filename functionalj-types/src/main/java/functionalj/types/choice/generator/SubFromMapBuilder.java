package functionalj.types.choice.generator;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Stream;

import functionalj.types.choice.generator.model.Case;
import lombok.val;

public class SubFromMapBuilder implements Lines {
    
    private final Case choice;
    
    public SubFromMapBuilder(Case choice) {
        this.choice = choice;
    }
    
    private Stream<String> body() {
        val paramIndex = new AtomicInteger(choice.params.size());
        Supplier<String> comma = ()-> (paramIndex.decrementAndGet() != 0) ? "," : "";
        return Stream.of(
                Stream.of("    return " + choice.name + "("),
                choice
                    .params.stream()
                    .map(param -> "        $utils.propertyFromMap(map, __schema__, \"" + param.name + "\")" + comma.get()),
                Stream.of("    );")
            )
            .flatMap(allLines -> allLines);
    }
    
    @Override
    public List<String> lines() {
        return Stream.of(
                Stream.of("public static " + choice.name + " caseFromMap(java.util.Map<String, Object> map) {"),
                body(),
                Stream.of("}")
            )
            .flatMap(allLines -> allLines)
            .collect(toList());
    }

}

package functionalj.types.choice.generator;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.Stream;

public class FromMapBuilder implements Lines {
    
    private final TargetClass targetClass;
    
    public FromMapBuilder(TargetClass targetClass) {
        this.targetClass = targetClass;
    }
    
    private Stream<String> body() {
        return Stream.of(
                Stream.of("    String __tagged = (String)map.get(\"" + targetClass.spec.tagMapKeyName + "\");"),
                targetClass.spec
                    .choices.stream()
                    .flatMap(choice -> Stream.of(
                            "    if (\"" +  choice.name + "\".equals(__tagged))",
                            "        return (T)" + choice.name + ".caseFromMap(map);"
                    )),
                Stream.of("    throw new IllegalArgumentException(\"Tagged value does not represent a valid type: \" + __tagged);")
            )
            .flatMap(allLines -> allLines);
    }
    
    @Override
    public List<String> lines() {
        return Stream.of(
                Stream.of("public static <T extends " + targetClass.type.simpleName() + "> T fromMap(java.util.Map<String, Object> map) {"),
                body(),
                Stream.of("}")
            )
            .flatMap(allLines -> allLines)
            .collect(toList());
    }

}

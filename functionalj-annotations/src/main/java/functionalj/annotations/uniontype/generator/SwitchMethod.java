package functionalj.annotations.uniontype.generator;

import static functionalj.annotations.uniontype.generator.Utils.switchClassName;
import static java.lang.String.format;
import static java.util.Arrays.asList;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.val;

@AllArgsConstructor
public class SwitchMethod implements Lines {
    public final TargetClass  targetClass;
    public final List<Choice> choices;
    
    @Override
    public List<String> lines() {
        val targetName      = targetClass.type.name;
        val switchClassName = switchClassName(targetName, choices);
        return asList(
            format(""),
            format("public <T> %1$s<T> switchMap() {",    switchClassName),
            format("    return switchMapTo(this, null);", switchClassName),
            format("}"),
            format("public <T> %1$s<T> switchMapTo(%2$s<T> clzz) {", switchClassName),
            format("    return new %1$s<T>(this, null);",             switchClassName),
            format("}"),
            format("public static <T> %1$s<T> switchMap(%2$s value) {", switchClassName, targetName),
            format("    return switchMapTo(value, null);"),
            format("}"),
            format("public static <T> %1$s<T> switchMapTo(%2$s value, Class<T> clzz) {", switchClassName, targetName),
            format("    return new %1$s<T>(value, null);"),
            format("}")
        );
    }
}
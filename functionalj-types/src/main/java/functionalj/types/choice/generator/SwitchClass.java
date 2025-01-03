// ============================================================================
// Copyright (c) 2017-2025 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.types.choice.generator;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import functionalj.types.choice.ChoiceTypeSwitch;
import functionalj.types.choice.generator.model.Case;
import lombok.experimental.ExtensionMethod;

@ExtensionMethod(Utils.class)
public class SwitchClass implements Lines {
    
    public final TargetClass targetClass;
    
    public final boolean expandPartial;
    
    public final boolean isFirst;
    
    public final List<Case> choices;
    
    public SwitchClass(TargetClass targetClass, boolean isFirst, List<Case> choices) {
        this(targetClass, true, isFirst, choices);
    }
    
    public SwitchClass(TargetClass targetClass, boolean expandPartial, boolean isFirst, List<Case> choices) {
        this.targetClass = targetClass;
        this.expandPartial = expandPartial;
        this.isFirst = isFirst;
        this.choices = choices;
    }
    
    @Override
    public List<String> lines() {
        String targetName      = targetClass.type.simpleName();
        String switchClassName = Utils.switchClassName(targetName, choices);
        String mapTargetType   = "TARGET";
        boolean isLast         = choices.size() <= 1;
        String nextName        = Utils.switchClassName(targetName, choices, 1);
        String retType         = isLast ? mapTargetType : nextName + "<" + mapTargetType + (targetClass.getType().genericParams().isEmpty() ? "" : (", " + targetClass.getType().genericParams())) + ">";
        String retStmt         = isLast ? "return newAction.apply($value);" : "return new " + nextName + "<" + mapTargetType + (targetClass.getType().genericParams().isEmpty() ? "" : (", " + targetClass.getType().genericParams())) + ">($value, newAction);";
        Case thisChoice        = choices.get(0);
        String thisName        = thisChoice.name;
        String camelName       = Utils.toCamelCase(thisChoice.name);
        
        List<String> firstSwitchLines     = !isFirst ? (List<String>) new ArrayList<String>() : asList(asList(format("public static class %1$sFirstSwitch%2$s {", targetName, targetClass.getType().genericDef())), asList(format("    private %s $value;", targetClass.getType().typeWithGenerics())), asList(format("    private %sFirstSwitch(%s theValue) { this.$value = theValue; }", targetName, targetClass.getType().typeWithGenerics())), asList(format("    public <%2$s> %1$sFirstSwitchTyped<%2$s%3$s> toA(Class<%2$s> clzz) {", targetName, mapTargetType, (targetClass.getType().genericParams().isEmpty() ? "" : ", " + targetClass.getType().genericParams()))), asList(format("        return new %1$sFirstSwitchTyped<%2$s%3$s>($value);", targetName, mapTargetType, (targetClass.getType().genericParams().isEmpty() ? "" : ", " + targetClass.getType().genericParams()))), asList(format("    }")), asList(format("    ")), createCasesComplete(true, false, thisName, camelName, targetName, retType, retStmt, mapTargetType), createCasesPartial(true, false, thisChoice, thisName, camelName, switchClassName, targetName, mapTargetType), asList(format("}"))).stream().flatMap(List::stream).collect(toList());
        List<String> firstSwitchTypeLines = !isFirst ? (List<String>) new ArrayList<String>() : asList(asList(format("public static class %1$sFirstSwitchTyped<%2$s> {", targetName, mapTargetType + (targetClass.getType().genericDefParams().isEmpty() ? "" : ", " + targetClass.getType().genericDefParams()))), asList(format("    private %s $value;", targetClass.getType().typeWithGenerics())), asList(format("    private %sFirstSwitchTyped(%s theValue) { this.$value = theValue; }", targetName, targetClass.getType().typeWithGenerics())), asList(format("    ")), createCasesComplete(true, true, thisName, camelName, targetName, retType, retStmt, mapTargetType), createCasesPartial(true, true, thisChoice, thisName, camelName, switchClassName, targetName, mapTargetType), asList(format("}"))).stream().flatMap(List::stream).collect(toList());
        List<String> casesComplete        = createCasesComplete(false, true, thisName, camelName, targetName, retType, retStmt, mapTargetType);
        List<String> casesPartial         = createCasesPartial(false, true, thisChoice, thisName, camelName, switchClassName, targetName, mapTargetType);
        
        List<List<String>> switchLinesList = new ArrayList<List<String>>();
        switchLinesList.add(asList(format("public static class %1$s<%3$s> extends %5$s<%2$s, %4$s> {", switchClassName, targetName + (targetClass.getType().genericsString().isEmpty() ? "" : targetClass.getType().genericsString()), mapTargetType + (targetClass.getType().genericDefParams().isEmpty() ? "" : ", " + targetClass.getType().genericDefParams()), mapTargetType, ChoiceTypeSwitch.class.getSimpleName())));
        switchLinesList.add(asList(format("    private %1$s(%2$s theValue, Function<%2$s, ? extends %3$s> theAction) { super(theValue, theAction); }", switchClassName, targetName + (targetClass.getType().genericsString().isEmpty() ? "" : targetClass.getType().genericsString()), mapTargetType)));
        switchLinesList.add(asList(format("    ")));
        switchLinesList.add(casesComplete);
        switchLinesList.add(casesPartial);
        switchLinesList.add(asList(format("}")));
        
        List<String> switchLines = switchLinesList.stream().flatMap(List::stream).collect(toList());
        List<String> lines       = new ArrayList<String>();
        lines.addAll(firstSwitchLines);
        lines.addAll(firstSwitchTypeLines);
        lines.addAll(switchLines);
        return lines;
    }
    
    private List<String> createCasesComplete(boolean isFirst, boolean typed, String thisName, String camelName, String targetName, String retType, String retStmt, String mapTargetType) {
        String methodGeneric = typed   ? "" : (isFirst ? "<" + mapTargetType + "> " : "");
        String lineBF        = isFirst ? "    Function<" + targetName + targetClass.getType().genericsString() + ", " + mapTargetType + "> $action = null;" : null;
        return asList(format("public %1$s%2$s %3$s(Function<? super %4$s, ? extends %5$s> theAction) {", methodGeneric, retType, camelName, thisName + targetClass.getType().genericsString(), mapTargetType), lineBF, format("    Function<%1$s, %2$s> oldAction = (Function<%1$s, %2$s>)$action;", targetName + targetClass.getType().genericsString(), mapTargetType), format("    Function<%1$s, %2$s> newAction =", targetName + targetClass.getType().genericsString(), mapTargetType), format("        ($action != null)"), format("        ? oldAction : "), format("            ($value instanceof %1$s)", thisName), format("            ? (Function<%1$s, %3$s>)(d -> theAction.apply((%2$s)d))", targetName + targetClass.getType().genericsString(), thisName + targetClass.getType().genericsString(), mapTargetType), format("            : oldAction;"), format("    "), format("    " + retStmt), format("}"), format("public %1$s%2$s %3$s(Supplier<? extends %4$s> theSupplier) {", methodGeneric, retType, camelName, mapTargetType), format("    return %1$s(d->theSupplier.get());", camelName), format("}"), format("public %1$s%2$s %3$s(%4$s theValue) {", methodGeneric, retType, camelName, mapTargetType), format("    return %1$s(d->theValue);", camelName), format("}")).stream().filter(Objects::nonNull).map("    "::concat).collect(toList());
    }
    
    private List<String> createCasesPartial(boolean isFirst, boolean typed, Case thisChoice, String thisName, String camelName, String switchClassName, String targetName, String mapTargetType) {
        String methodGeneric = typed   ? "" : (isFirst ? "<" + mapTargetType + "> " : "");
        String lineBF        = isFirst ? "    Function<" + targetName + targetClass.getType().genericsString() + ", " + mapTargetType + "> $action = null;" : null;
        return !thisChoice.isParameterized() ? new ArrayList<String>() : asList(format(""), format("public %1$s%2$s<%5$s> %3$s(java.util.function.Predicate<%4$s> check, Function<? super %4$s, ? extends %6$s> theAction) {", methodGeneric, switchClassName, camelName, thisName + (targetClass.getType().genericsString().isEmpty() ? "" : targetClass.getType().genericsString()), mapTargetType + (targetClass.getType().genericDefParams().isEmpty() ? "" : ", " + targetClass.getType().genericParams()), mapTargetType), lineBF, format("    Function<%1$s, %2$s> oldAction = (Function<%1$s, %2$s>)$action;", targetName + targetClass.getType().genericsString(), mapTargetType), format("    Function<%1$s, %2$s> newAction =", targetName + targetClass.getType().genericsString(), mapTargetType), format("        ($action != null)"), format("        ? oldAction : "), format("            (($value instanceof %1$s) && check.test((%2$s)$value))", thisName, thisName + (targetClass.getType().genericsString().isEmpty() ? "" : targetClass.getType().genericsString())), format("            ? (Function<%1$s, %3$s>)(d -> theAction.apply((%2$s)d))", targetName + targetClass.getType().genericsString(), thisName + (targetClass.getType().genericsString().isEmpty() ? "" : targetClass.getType().genericsString()), mapTargetType), format("            : oldAction;"), format("    "), format("    return new %1$s<%2$s>($value, newAction);", switchClassName, mapTargetType + (targetClass.getType().genericParams().isEmpty() ? "" : ", " + targetClass.getType().genericParams())), format("}"), format("public %1$s%2$s<%5$s> %3$s(java.util.function.Predicate<%4$s> check, Supplier<? extends %6$s> theSupplier) {", methodGeneric, switchClassName, camelName, thisName + (targetClass.getType().genericsString().isEmpty() ? "" : targetClass.getType().genericsString()), mapTargetType + (targetClass.getType().genericDefParams().isEmpty() ? "" : ", " + targetClass.getType().genericParams()), mapTargetType), format("    return %1$s(check, d->theSupplier.get());", camelName), format("}"), format("public %1$s%2$s<%5$s> %3$s(java.util.function.Predicate<%4$s> check, %6$s theValue) {", methodGeneric, switchClassName, camelName, thisName + (targetClass.getType().genericsString().isEmpty() ? "" : targetClass.getType().genericsString()), mapTargetType + (targetClass.getType().genericDefParams().isEmpty() ? "" : ", " + targetClass.getType().genericParams()), mapTargetType), format("    return %1$s(check, d->theValue);", camelName), format("}")).stream().filter(Objects::nonNull).map("    "::concat).collect(toList());
    }
}

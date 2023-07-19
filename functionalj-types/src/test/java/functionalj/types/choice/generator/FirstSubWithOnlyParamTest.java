// ============================================================================
// Copyright (c) 2017-2023 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import static functionalj.types.TestHelper.assertAsString;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

import java.util.Objects;
import java.util.stream.Collectors;

import org.junit.Test;

import functionalj.types.Type;
import functionalj.types.choice.generator.model.Case;
import functionalj.types.choice.generator.model.CaseParam;
import functionalj.types.choice.generator.model.SourceSpec;
import lombok.val;

public class FirstSubWithOnlyParamTest {
    
    @Test
    public void test() {
        val spec = new SourceSpec("BasicColor", new Type(this.getClass().getPackage().getName(), "ChoiceTypeExampleTest", "ChoiceType1TypeSpec"), asList(new Case("RGB", "__validateRGB", asList(new CaseParam("r", new Type("int"), false))), new Case("White", "RGB(255,255,255)", emptyList())));
        val targetClass = new TargetClass(spec);
        val switchClass = new SwitchClass(targetClass, true, spec.choices);
        val lines = switchClass.lines().stream().filter(Objects::nonNull).collect(Collectors.joining("\n"));
        assertAsString("public static class BasicColorFirstSwitch {\n" + "    private BasicColor $value;\n" + "    private BasicColorFirstSwitch(BasicColor theValue) { this.$value = theValue; }\n" + "    public <TARGET> BasicColorFirstSwitchTyped<TARGET> toA(Class<TARGET> clzz) {\n" + "        return new BasicColorFirstSwitchTyped<TARGET>($value);\n" + "    }\n" + "    \n" + "    public <TARGET> BasicColorSwitchWhite<TARGET> rgb(Function<? super RGB, ? extends TARGET> theAction) {\n" + "        Function<BasicColor, TARGET> $action = null;\n" + "        Function<BasicColor, TARGET> oldAction = (Function<BasicColor, TARGET>)$action;\n" + "        Function<BasicColor, TARGET> newAction =\n" + "            ($action != null)\n" + "            ? oldAction : \n" + "                ($value instanceof RGB)\n" + "                ? (Function<BasicColor, TARGET>)(d -> theAction.apply((RGB)d))\n" + "                : oldAction;\n" + "        \n" + "        return new BasicColorSwitchWhite<TARGET>($value, newAction);\n" + "    }\n" + "    public <TARGET> BasicColorSwitchWhite<TARGET> rgb(Supplier<? extends TARGET> theSupplier) {\n" + "        return rgb(d->theSupplier.get());\n" + "    }\n" + "    public <TARGET> BasicColorSwitchWhite<TARGET> rgb(TARGET theValue) {\n" + "        return rgb(d->theValue);\n" + "    }\n" + "    \n" + "    public <TARGET> BasicColorSwitchRGBWhite<TARGET> rgb(java.util.function.Predicate<RGB> check, Function<? super RGB, ? extends TARGET> theAction) {\n" + "        Function<BasicColor, TARGET> $action = null;\n" + "        Function<BasicColor, TARGET> oldAction = (Function<BasicColor, TARGET>)$action;\n" + "        Function<BasicColor, TARGET> newAction =\n" + "            ($action != null)\n" + "            ? oldAction : \n" + "                (($value instanceof RGB) && check.test((RGB)$value))\n" + "                ? (Function<BasicColor, TARGET>)(d -> theAction.apply((RGB)d))\n" + "                : oldAction;\n" + "        \n" + "        return new BasicColorSwitchRGBWhite<TARGET>($value, newAction);\n" + "    }\n" + "    public <TARGET> BasicColorSwitchRGBWhite<TARGET> rgb(java.util.function.Predicate<RGB> check, Supplier<? extends TARGET> theSupplier) {\n" + "        return rgb(check, d->theSupplier.get());\n" + "    }\n" + "    public <TARGET> BasicColorSwitchRGBWhite<TARGET> rgb(java.util.function.Predicate<RGB> check, TARGET theValue) {\n" + "        return rgb(check, d->theValue);\n" + "    }\n" + "}\n" + "public static class BasicColorFirstSwitchTyped<TARGET> {\n" + "    private BasicColor $value;\n" + "    private BasicColorFirstSwitchTyped(BasicColor theValue) { this.$value = theValue; }\n" + "    \n" + "    public BasicColorSwitchWhite<TARGET> rgb(Function<? super RGB, ? extends TARGET> theAction) {\n" + "        Function<BasicColor, TARGET> $action = null;\n" + "        Function<BasicColor, TARGET> oldAction = (Function<BasicColor, TARGET>)$action;\n" + "        Function<BasicColor, TARGET> newAction =\n" + "            ($action != null)\n" + "            ? oldAction : \n" + "                ($value instanceof RGB)\n" + "                ? (Function<BasicColor, TARGET>)(d -> theAction.apply((RGB)d))\n" + "                : oldAction;\n" + "        \n" + "        return new BasicColorSwitchWhite<TARGET>($value, newAction);\n" + "    }\n" + "    public BasicColorSwitchWhite<TARGET> rgb(Supplier<? extends TARGET> theSupplier) {\n" + "        return rgb(d->theSupplier.get());\n" + "    }\n" + "    public BasicColorSwitchWhite<TARGET> rgb(TARGET theValue) {\n" + "        return rgb(d->theValue);\n" + "    }\n" + "    \n" + "    public BasicColorSwitchRGBWhite<TARGET> rgb(java.util.function.Predicate<RGB> check, Function<? super RGB, ? extends TARGET> theAction) {\n" + "        Function<BasicColor, TARGET> $action = null;\n" + "        Function<BasicColor, TARGET> oldAction = (Function<BasicColor, TARGET>)$action;\n" + "        Function<BasicColor, TARGET> newAction =\n" + "            ($action != null)\n" + "            ? oldAction : \n" + "                (($value instanceof RGB) && check.test((RGB)$value))\n" + "                ? (Function<BasicColor, TARGET>)(d -> theAction.apply((RGB)d))\n" + "                : oldAction;\n" + "        \n" + "        return new BasicColorSwitchRGBWhite<TARGET>($value, newAction);\n" + "    }\n" + "    public BasicColorSwitchRGBWhite<TARGET> rgb(java.util.function.Predicate<RGB> check, Supplier<? extends TARGET> theSupplier) {\n" + "        return rgb(check, d->theSupplier.get());\n" + "    }\n" + "    public BasicColorSwitchRGBWhite<TARGET> rgb(java.util.function.Predicate<RGB> check, TARGET theValue) {\n" + "        return rgb(check, d->theValue);\n" + "    }\n" + "}\n" + "public static class BasicColorSwitchRGBWhite<TARGET> extends ChoiceTypeSwitch<BasicColor, TARGET> {\n" + "    private BasicColorSwitchRGBWhite(BasicColor theValue, Function<BasicColor, ? extends TARGET> theAction) { super(theValue, theAction); }\n" + "    \n" + "    public BasicColorSwitchWhite<TARGET> rgb(Function<? super RGB, ? extends TARGET> theAction) {\n" + "        Function<BasicColor, TARGET> oldAction = (Function<BasicColor, TARGET>)$action;\n" + "        Function<BasicColor, TARGET> newAction =\n" + "            ($action != null)\n" + "            ? oldAction : \n" + "                ($value instanceof RGB)\n" + "                ? (Function<BasicColor, TARGET>)(d -> theAction.apply((RGB)d))\n" + "                : oldAction;\n" + "        \n" + "        return new BasicColorSwitchWhite<TARGET>($value, newAction);\n" + "    }\n" + "    public BasicColorSwitchWhite<TARGET> rgb(Supplier<? extends TARGET> theSupplier) {\n" + "        return rgb(d->theSupplier.get());\n" + "    }\n" + "    public BasicColorSwitchWhite<TARGET> rgb(TARGET theValue) {\n" + "        return rgb(d->theValue);\n" + "    }\n" + "    \n" + "    public BasicColorSwitchRGBWhite<TARGET> rgb(java.util.function.Predicate<RGB> check, Function<? super RGB, ? extends TARGET> theAction) {\n" + "        Function<BasicColor, TARGET> oldAction = (Function<BasicColor, TARGET>)$action;\n" + "        Function<BasicColor, TARGET> newAction =\n" + "            ($action != null)\n" + "            ? oldAction : \n" + "                (($value instanceof RGB) && check.test((RGB)$value))\n" + "                ? (Function<BasicColor, TARGET>)(d -> theAction.apply((RGB)d))\n" + "                : oldAction;\n" + "        \n" + "        return new BasicColorSwitchRGBWhite<TARGET>($value, newAction);\n" + "    }\n" + "    public BasicColorSwitchRGBWhite<TARGET> rgb(java.util.function.Predicate<RGB> check, Supplier<? extends TARGET> theSupplier) {\n" + "        return rgb(check, d->theSupplier.get());\n" + "    }\n" + "    public BasicColorSwitchRGBWhite<TARGET> rgb(java.util.function.Predicate<RGB> check, TARGET theValue) {\n" + "        return rgb(check, d->theValue);\n" + "    }\n" + "}", lines);
    }
}

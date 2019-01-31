package functionalj.types.choice.generator;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;

import java.util.Objects;
import java.util.stream.Collectors;

import org.junit.Test;

import functionalj.types.choice.generator.SwitchClass;
import functionalj.types.choice.generator.TargetClass;
import functionalj.types.choice.generator.model.Case;
import functionalj.types.choice.generator.model.CaseParam;
import functionalj.types.choice.generator.model.SourceSpec;
import functionalj.types.choice.generator.model.Type;
import lombok.val;

@SuppressWarnings("javadoc")
public class FirstSubWithOnlyParamTest {
    
    @Test
    public void test() {
        val spec = new SourceSpec(
                "BasicColor",
                new Type(this.getClass().getPackage().getName(), "ChoiceTypeExampleTest", "ChoiceType1TypeSpec"),
                asList(
                    new Case("RGB", "__validateRGB", asList(
                        new CaseParam("r", new Type("int"), false)
                    )),
                    new Case("White", "RGB(255,255,255)", emptyList())));
        val targetClass = new TargetClass(spec);
        val switchClass = new SwitchClass(targetClass, true, spec.choices);
        val lines  = switchClass.lines().stream().filter(Objects::nonNull).collect(Collectors.joining("\n"));
        assertEquals(
                "public static class BasicColorFirstSwitch {\n" + 
                "    private BasicColor $value;\n" + 
                "    private BasicColorFirstSwitch(BasicColor theValue) { this.$value = theValue; }\n" + 
                "    public <TARGET> BasicColorFirstSwitchTyped<TARGET> toA(Class<TARGET> clzz) {\n" + 
                "        return new BasicColorFirstSwitchTyped<TARGET>($value);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public <TARGET> BasicColorSwitchWhite<TARGET> rgb(Function<? super RGB, ? extends TARGET> theAction) {\n" + 
                "        Function<BasicColor, TARGET> $action = null;\n" + 
                "        Function<BasicColor, TARGET> oldAction = (Function<BasicColor, TARGET>)$action;\n" + 
                "        Function<BasicColor, TARGET> newAction =\n" + 
                "            ($action != null)\n" + 
                "            ? oldAction : \n" + 
                "                ($value instanceof RGB)\n" + 
                "                ? (Function<BasicColor, TARGET>)(d -> theAction.apply((RGB)d))\n" + 
                "                : oldAction;\n" + 
                "        \n" + 
                "        return new BasicColorSwitchWhite<TARGET>($value, newAction);\n" + 
                "    }\n" + 
                "    public <TARGET> BasicColorSwitchWhite<TARGET> rgb(Supplier<? extends TARGET> theSupplier) {\n" + 
                "        return rgb(d->theSupplier.get());\n" + 
                "    }\n" + 
                "    public <TARGET> BasicColorSwitchWhite<TARGET> rgb(TARGET theValue) {\n" + 
                "        return rgb(d->theValue);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public <TARGET> BasicColorSwitchRGBWhite<TARGET> rgb(Predicate<RGB> check, Function<? super RGB, ? extends TARGET> theAction) {\n" + 
                "        Function<BasicColor, TARGET> $action = null;\n" + 
                "        Function<BasicColor, TARGET> oldAction = (Function<BasicColor, TARGET>)$action;\n" + 
                "        Function<BasicColor, TARGET> newAction =\n" + 
                "            ($action != null)\n" + 
                "            ? oldAction : \n" + 
                "                (($value instanceof RGB) && check.test((RGB)$value))\n" + 
                "                ? (Function<BasicColor, TARGET>)(d -> theAction.apply((RGB)d))\n" + 
                "                : oldAction;\n" + 
                "        \n" + 
                "        return new BasicColorSwitchRGBWhite<TARGET>($value, newAction);\n" + 
                "    }\n" + 
                "    public <TARGET> BasicColorSwitchRGBWhite<TARGET> rgb(Predicate<RGB> check, Supplier<? extends TARGET> theSupplier) {\n" + 
                "        return rgb(check, d->theSupplier.get());\n" + 
                "    }\n" + 
                "    public <TARGET> BasicColorSwitchRGBWhite<TARGET> rgb(Predicate<RGB> check, TARGET theValue) {\n" + 
                "        return rgb(check, d->theValue);\n" + 
                "    }\n" + 
                "}\n" + 
                "public static class BasicColorFirstSwitchTyped<TARGET> {\n" + 
                "    private BasicColor $value;\n" + 
                "    private BasicColorFirstSwitchTyped(BasicColor theValue) { this.$value = theValue; }\n" + 
                "    \n" + 
                "    public BasicColorSwitchWhite<TARGET> rgb(Function<? super RGB, ? extends TARGET> theAction) {\n" + 
                "        Function<BasicColor, TARGET> $action = null;\n" + 
                "        Function<BasicColor, TARGET> oldAction = (Function<BasicColor, TARGET>)$action;\n" + 
                "        Function<BasicColor, TARGET> newAction =\n" + 
                "            ($action != null)\n" + 
                "            ? oldAction : \n" + 
                "                ($value instanceof RGB)\n" + 
                "                ? (Function<BasicColor, TARGET>)(d -> theAction.apply((RGB)d))\n" + 
                "                : oldAction;\n" + 
                "        \n" + 
                "        return new BasicColorSwitchWhite<TARGET>($value, newAction);\n" + 
                "    }\n" + 
                "    public BasicColorSwitchWhite<TARGET> rgb(Supplier<? extends TARGET> theSupplier) {\n" + 
                "        return rgb(d->theSupplier.get());\n" + 
                "    }\n" + 
                "    public BasicColorSwitchWhite<TARGET> rgb(TARGET theValue) {\n" + 
                "        return rgb(d->theValue);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public BasicColorSwitchRGBWhite<TARGET> rgb(Predicate<RGB> check, Function<? super RGB, ? extends TARGET> theAction) {\n" + 
                "        Function<BasicColor, TARGET> $action = null;\n" + 
                "        Function<BasicColor, TARGET> oldAction = (Function<BasicColor, TARGET>)$action;\n" + 
                "        Function<BasicColor, TARGET> newAction =\n" + 
                "            ($action != null)\n" + 
                "            ? oldAction : \n" + 
                "                (($value instanceof RGB) && check.test((RGB)$value))\n" + 
                "                ? (Function<BasicColor, TARGET>)(d -> theAction.apply((RGB)d))\n" + 
                "                : oldAction;\n" + 
                "        \n" + 
                "        return new BasicColorSwitchRGBWhite<TARGET>($value, newAction);\n" + 
                "    }\n" + 
                "    public BasicColorSwitchRGBWhite<TARGET> rgb(Predicate<RGB> check, Supplier<? extends TARGET> theSupplier) {\n" + 
                "        return rgb(check, d->theSupplier.get());\n" + 
                "    }\n" + 
                "    public BasicColorSwitchRGBWhite<TARGET> rgb(Predicate<RGB> check, TARGET theValue) {\n" + 
                "        return rgb(check, d->theValue);\n" + 
                "    }\n" + 
                "}\n" + 
                "public static class BasicColorSwitchRGBWhite<TARGET> extends ChoiceTypeSwitch<BasicColor, TARGET> {\n" + 
                "    private BasicColorSwitchRGBWhite(BasicColor theValue, Function<BasicColor, ? extends TARGET> theAction) { super(theValue, theAction); }\n" + 
                "    \n" + 
                "    public BasicColorSwitchWhite<TARGET> rgb(Function<? super RGB, ? extends TARGET> theAction) {\n" + 
                "        Function<BasicColor, TARGET> oldAction = (Function<BasicColor, TARGET>)$action;\n" + 
                "        Function<BasicColor, TARGET> newAction =\n" + 
                "            ($action != null)\n" + 
                "            ? oldAction : \n" + 
                "                ($value instanceof RGB)\n" + 
                "                ? (Function<BasicColor, TARGET>)(d -> theAction.apply((RGB)d))\n" + 
                "                : oldAction;\n" + 
                "        \n" + 
                "        return new BasicColorSwitchWhite<TARGET>($value, newAction);\n" + 
                "    }\n" + 
                "    public BasicColorSwitchWhite<TARGET> rgb(Supplier<? extends TARGET> theSupplier) {\n" + 
                "        return rgb(d->theSupplier.get());\n" + 
                "    }\n" + 
                "    public BasicColorSwitchWhite<TARGET> rgb(TARGET theValue) {\n" + 
                "        return rgb(d->theValue);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public BasicColorSwitchRGBWhite<TARGET> rgb(Predicate<RGB> check, Function<? super RGB, ? extends TARGET> theAction) {\n" + 
                "        Function<BasicColor, TARGET> oldAction = (Function<BasicColor, TARGET>)$action;\n" + 
                "        Function<BasicColor, TARGET> newAction =\n" + 
                "            ($action != null)\n" + 
                "            ? oldAction : \n" + 
                "                (($value instanceof RGB) && check.test((RGB)$value))\n" + 
                "                ? (Function<BasicColor, TARGET>)(d -> theAction.apply((RGB)d))\n" + 
                "                : oldAction;\n" + 
                "        \n" + 
                "        return new BasicColorSwitchRGBWhite<TARGET>($value, newAction);\n" + 
                "    }\n" + 
                "    public BasicColorSwitchRGBWhite<TARGET> rgb(Predicate<RGB> check, Supplier<? extends TARGET> theSupplier) {\n" + 
                "        return rgb(check, d->theSupplier.get());\n" + 
                "    }\n" + 
                "    public BasicColorSwitchRGBWhite<TARGET> rgb(Predicate<RGB> check, TARGET theValue) {\n" + 
                "        return rgb(check, d->theValue);\n" + 
                "    }\n" + 
                "}",
                lines);
    }
    
}

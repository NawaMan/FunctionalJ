package functionalj.annotations.uniontype.generator;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;

import java.util.Objects;
import java.util.stream.Collectors;

import org.junit.Test;

import lombok.val;

public class FirstSubWithOnlyParamTest {
    
    @Test
    public void test() {
        val spec = new SourceSpec(
                "BasicColor",
                new Type("functionalj.annotations.uniontype.generator", "UnionTypeExampleTest", "Union1TypeSpec"),
                asList(
                    new Choice("RGB", "validateRGB", asList(
                        new ChoiceParam("r", new Type("int"))
                    )),
                    new Choice("White", null, null, "RGB(255,255,255)", emptyList())));
        val targetClass = new TargetClass(spec);
        val switchClass = new SwitchClass(targetClass, true, spec.choices);
        val lines  = switchClass.lines().stream().filter(Objects::nonNull).collect(Collectors.joining("\n"));
        assertEquals(
                "public static class BasicColorFirstSwitch {\n" + 
                "    private BasicColor value;\n" + 
                "    private BasicColorFirstSwitch(BasicColor value) { this.value = value; }\n" + 
                "    \n" + 
                "    public <T> BasicColorSwitchWhite<T> rgb(T theValue) {\n" + 
                "        return rgb(d->theValue);\n" + 
                "    }\n" + 
                "    public <T> BasicColorSwitchWhite<T> rgb(Supplier<T> theSupplier) {\n" + 
                "        return rgb(d->theSupplier.get());\n" + 
                "    }\n" + 
                "    public <T> BasicColorSwitchWhite<T> rgb(Function<? super RGB, T> theAction) {\n" + 
                "        Function<BasicColor, T> action = null;\n" + 
                "        Function<BasicColor, T> oldAction = (Function<BasicColor, T>)action;\n" + 
                "        Function<BasicColor, T> newAction =\n" + 
                "            (action != null)\n" + 
                "            ? oldAction : \n" + 
                "                (value instanceof RGB)\n" + 
                "                ? (Function<BasicColor, T>)(d -> theAction.apply((RGB)d))\n" + 
                "                : oldAction;\n" + 
                "        \n" + 
                "        return new BasicColorSwitchWhite <T>(value, newAction);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public <T> BasicColorSwitchRGBWhite<T> rgb(Predicate<RGB> check, T theValue) {\n" + 
                "        return rgb(check, d->theValue);\n" + 
                "    }\n" + 
                "    public <T> BasicColorSwitchRGBWhite<T> rgb(Predicate<RGB> check, Supplier<T> theSupplier) {\n" + 
                "        return rgb(check, d->theSupplier.get());\n" + 
                "    }\n" + 
                "    public <T> BasicColorSwitchRGBWhite<T> rgb(Predicate<RGB> check, Function<? super RGB, T> theAction) {\n" + 
                "        Function<BasicColor, T> action = null;\n" + 
                "        Function<BasicColor, T> oldAction = (Function<BasicColor, T>)action;\n" + 
                "        Function<BasicColor, T> newAction =\n" + 
                "            (action != null)\n" + 
                "            ? oldAction : \n" + 
                "                ((value instanceof RGB) && check.test((RGB)value))\n" + 
                "                ? (Function<BasicColor, T>)(d -> theAction.apply((RGB)d))\n" + 
                "                : oldAction;\n" + 
                "        \n" + 
                "        return new BasicColorSwitchRGBWhite<T>(value, newAction);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public <T> BasicColorSwitchRGBWhite<T> rgbOf(int r, T theValue) {\n" + 
                "        return rgb(rgb -> checkEquals(r, rgb.r), theValue);\n" + 
                "    }\n" + 
                "    public <T> BasicColorSwitchRGBWhite<T> rgbOf(int r, Supplier<T> theSupplier) {\n" + 
                "        return rgb(rgb -> checkEquals(r, rgb.r), theSupplier);\n" + 
                "    }\n" + 
                "    public <T> BasicColorSwitchRGBWhite<T> rgbOf(int r, Function<RGB, T> theAction) {\n" + 
                "        return rgb(rgb -> checkEquals(r, rgb.r), theAction);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public <T> BasicColorSwitchRGBWhite<T> rgbOf(Predicate<Integer> rCheck, T theValue) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r), theValue);\n" + 
                "    }\n" + 
                "    public <T> BasicColorSwitchRGBWhite<T> rgbOf(Predicate<Integer> rCheck, Supplier<T> theSupplier) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r), theSupplier);\n" + 
                "    }\n" + 
                "    public <T> BasicColorSwitchRGBWhite<T> rgbOf(Predicate<Integer> rCheck, Function<RGB, T> theAction) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r), theAction);\n" + 
                "    }\n" + 
                "}\n" + 
                "public static class BasicColorSwitchRGBWhite<T> extends UnionTypeSwitch<BasicColor, T> {\n" + 
                "    private BasicColorSwitchRGBWhite(BasicColor value, Function<BasicColor, T> action) { super(value, action); }\n" + 
                "    \n" + 
                "    public BasicColorSwitchWhite<T> rgb(T theValue) {\n" + 
                "        return rgb(d->theValue);\n" + 
                "    }\n" + 
                "    public BasicColorSwitchWhite<T> rgb(Supplier<T> theSupplier) {\n" + 
                "        return rgb(d->theSupplier.get());\n" + 
                "    }\n" + 
                "    public BasicColorSwitchWhite<T> rgb(Function<? super RGB, T> theAction) {\n" + 
                "        @SuppressWarnings(\"unchecked\")\n" + 
                "        Function<BasicColor, T> oldAction = (Function<BasicColor, T>)action;\n" + 
                "        Function<BasicColor, T> newAction =\n" + 
                "            (action != null)\n" + 
                "            ? oldAction : \n" + 
                "                (value instanceof RGB)\n" + 
                "                ? (Function<BasicColor, T>)(d -> theAction.apply((RGB)d))\n" + 
                "                : oldAction;\n" + 
                "        \n" + 
                "        return new BasicColorSwitchWhite <T>(value, newAction);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public BasicColorSwitchRGBWhite<T> rgb(Predicate<RGB> check, T theValue) {\n" + 
                "        return rgb(check, d->theValue);\n" + 
                "    }\n" + 
                "    public BasicColorSwitchRGBWhite<T> rgb(Predicate<RGB> check, Supplier<T> theSupplier) {\n" + 
                "        return rgb(check, d->theSupplier.get());\n" + 
                "    }\n" + 
                "    public BasicColorSwitchRGBWhite<T> rgb(Predicate<RGB> check, Function<? super RGB, T> theAction) {\n" + 
                "        @SuppressWarnings(\"unchecked\")\n" + 
                "        Function<BasicColor, T> oldAction = (Function<BasicColor, T>)action;\n" + 
                "        Function<BasicColor, T> newAction =\n" + 
                "            (action != null)\n" + 
                "            ? oldAction : \n" + 
                "                ((value instanceof RGB) && check.test((RGB)value))\n" + 
                "                ? (Function<BasicColor, T>)(d -> theAction.apply((RGB)d))\n" + 
                "                : oldAction;\n" + 
                "        \n" + 
                "        return new BasicColorSwitchRGBWhite<T>(value, newAction);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public BasicColorSwitchRGBWhite<T> rgbOf(int r, T theValue) {\n" + 
                "        return rgb(rgb -> checkEquals(r, rgb.r), theValue);\n" + 
                "    }\n" + 
                "    public BasicColorSwitchRGBWhite<T> rgbOf(int r, Supplier<T> theSupplier) {\n" + 
                "        return rgb(rgb -> checkEquals(r, rgb.r), theSupplier);\n" + 
                "    }\n" + 
                "    public BasicColorSwitchRGBWhite<T> rgbOf(int r, Function<RGB, T> theAction) {\n" + 
                "        return rgb(rgb -> checkEquals(r, rgb.r), theAction);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public BasicColorSwitchRGBWhite<T> rgbOf(Predicate<Integer> rCheck, T theValue) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r), theValue);\n" + 
                "    }\n" + 
                "    public BasicColorSwitchRGBWhite<T> rgbOf(Predicate<Integer> rCheck, Supplier<T> theSupplier) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r), theSupplier);\n" + 
                "    }\n" + 
                "    public BasicColorSwitchRGBWhite<T> rgbOf(Predicate<Integer> rCheck, Function<RGB, T> theAction) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r), theAction);\n" + 
                "    }\n" + 
                "}",
                lines);
    }
    
}

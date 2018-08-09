package functionalj.annotations.uniontype.generator;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;

import java.util.Objects;
import java.util.stream.Collectors;

import org.junit.Test;

import lombok.val;
import lombok.experimental.ExtensionMethod;


@ExtensionMethod(functionalj.annotations.uniontype.generator.Utils.class)
@SuppressWarnings("javadoc")
public class GeneratorTest {
    
    @Test
    public void testToCamelCase() {
        assertEquals("rgbColor", "RGBColor".toCamelCase());
        assertEquals("rgb",      "RGB"     .toCamelCase());
        assertEquals("white",    "White"   .toCamelCase());
    }
    
    @Test
    public void testSubClassConstructor_noParams() {
        val target = new TargetClass(new SourceSpec("Color", new Type("p1.p2", "ColorSpec"), emptyList()));
        val sub    = new SubClassConstructor(target, new Choice("White"));
        val lines  = sub.lines().stream().filter(Objects::nonNull).collect(Collectors.joining("\n"));
        assertEquals(
                "public static final Color White() {\n" +
                "    return White.instance;\n" +
                "}", lines);
    }
    
    @Test
    public void testSubClassConstructor_withParams() {
        val target = new TargetClass(new SourceSpec("Color", new Type("p1.p2", "EncloserClass", "ColorSpec"), emptyList()));
        val sub    = new SubClassConstructor(target, 
                new Choice("RGB",   "validateRGB", asList(
                    new ChoiceParam("r", new Type("int")),
                    new ChoiceParam("g", new Type("int")),
                    new ChoiceParam("b", new Type("int"))
                )));
        val lines  = sub.lines().stream().filter(Objects::nonNull).collect(Collectors.joining("\n"));
        assertEquals(
                "public static final Color RGB(int r, int g, int b) {\n" + 
                "    EncloserClass.ColorSpec.validateRGB(r, g, b);\n" + 
                "    return new RGB(r, g, b);\n" + 
                "}", lines);
    }
    
    @Test
    public void testSubClassDefinition_noParams() {
        val target = new TargetClass(new SourceSpec("Color", new Type("p1.p2", "ColorSpec"), emptyList()));
        val sub    = new SubClassDefinition(target, new Choice("White"));
        val lines  = sub.lines().stream().filter(Objects::nonNull).collect(Collectors.joining("\n"));
        assertEquals(
                "public static final class White extends Color {\n" + 
                "    private static final White instance = new White();\n" + 
                "    private White() {}\n" + 
                "}", lines);
    }
    
    @Test
    public void testSubClassDefinition_withParams() {
        val target = new TargetClass(new SourceSpec("Color", new Type("p1.p2", "ColorSpec"), emptyList()));
        val sub    = new SubClassDefinition(target, 
                new Choice("RGB",   "validateRGB", asList(
                    new ChoiceParam("r", new Type("int")),
                    new ChoiceParam("g", new Type("int")),
                    new ChoiceParam("b", new Type("int"))
                )));
        val lines  = sub.lines().stream().filter(Objects::nonNull).collect(Collectors.joining("\n"));
        assertEquals(
                "public static final class RGB extends Color {\n" + 
                "    private int r;\n" + 
                "    private int g;\n" + 
                "    private int b;\n" + 
                "    private RGB(int r, int g, int b) {\n" + 
                "        this.r = r;\n" + 
                "        this.g = g;\n" + 
                "        this.b = b;\n" + 
                "    }\n" + 
                "    public int r() { return r; }\n" + 
                "    public int g() { return g; }\n" + 
                "    public int b() { return b; }\n" + 
                "    public RGB withR(int r) { return new RGB(r, g, b); }\n" + 
                "    public RGB withG(int g) { return new RGB(r, g, b); }\n" + 
                "    public RGB withB(int b) { return new RGB(r, g, b); }\n" + 
                "}", lines);
    }
    
    @Test
    public void testSwitchClass_simple_notLast() {
        val target = new TargetClass(new SourceSpec("Color", new Type("p1.p2", "ColorSpec"), emptyList()));
        val sub    = new SwitchClass(target, false, asList(
                        new Choice("White"),
                        new Choice("Black"),
                        new Choice("RGB",   "validateRGB", asList(
                            new ChoiceParam("r", new Type("int")),
                            new ChoiceParam("g", new Type("int")),
                            new ChoiceParam("b", new Type("int"))
                        ))));
        val lines  = sub.lines().stream().filter(Objects::nonNull).collect(Collectors.joining("\n"));
        assertEquals(
                "public static class ColorSwitchWhiteBlackRGB<TARGET> extends UnionTypeSwitch<Color, TARGET> {\n" + 
                "    private ColorSwitchWhiteBlackRGB(Color value, Function<Color, TARGET> action) { super(value, action); }\n" + 
                "    \n" + 
                "    public ColorSwitchBlackRGB<TARGET> white(TARGET theValue) {\n" + 
                "        return white(d->theValue);\n" + 
                "    }\n" + 
                "    public ColorSwitchBlackRGB<TARGET> white(Supplier<TARGET> theSupplier) {\n" + 
                "        return white(d->theSupplier.get());\n" + 
                "    }\n" + 
                "    public ColorSwitchBlackRGB<TARGET> white(Function<? super White, TARGET> theAction) {\n" + 
                "        Function<Color, TARGET> oldAction = (Function<Color, TARGET>)action;\n" + 
                "        Function<Color, TARGET> newAction =\n" + 
                "            (action != null)\n" + 
                "            ? oldAction : \n" + 
                "                (value instanceof White)\n" + 
                "                ? (Function<Color, TARGET>)(d -> theAction.apply((White)d))\n" + 
                "                : oldAction;\n" + 
                "        \n" + 
                "        return new ColorSwitchBlackRGB<TARGET>(value, newAction);\n" + 
                "    }\n" + 
                "}", lines);
    }
    
    @Test
    public void testSwitchClass_simple_last() {
        val target = new TargetClass(new SourceSpec("Color", new Type("p1.p2", "ColorSpec"), emptyList()));
        val sub    = new SwitchClass(target, false, asList(new Choice("White")));
        val lines  = sub.lines().stream().filter(Objects::nonNull).collect(Collectors.joining("\n"));
        assertEquals(
                "public static class ColorSwitchWhite<TARGET> extends UnionTypeSwitch<Color, TARGET> {\n" + 
                "    private ColorSwitchWhite(Color value, Function<Color, TARGET> action) { super(value, action); }\n" + 
                "    \n" + 
                "    public TARGET white(TARGET theValue) {\n" + 
                "        return white(d->theValue);\n" + 
                "    }\n" + 
                "    public TARGET white(Supplier<TARGET> theSupplier) {\n" + 
                "        return white(d->theSupplier.get());\n" + 
                "    }\n" + 
                "    public TARGET white(Function<? super White, TARGET> theAction) {\n" + 
                "        Function<Color, TARGET> oldAction = (Function<Color, TARGET>)action;\n" + 
                "        Function<Color, TARGET> newAction =\n" + 
                "            (action != null)\n" + 
                "            ? oldAction : \n" + 
                "                (value instanceof White)\n" + 
                "                ? (Function<Color, TARGET>)(d -> theAction.apply((White)d))\n" + 
                "                : oldAction;\n" + 
                "        \n" + 
                "        return newAction.apply(value);\n" + 
                "    }\n" + 
                "}", lines);
    }
    
    @Test
    public void testSubCheckMethod() {
        val target = new TargetClass(new SourceSpec("Color", new Type("p1.p2", "ColorSpec"), emptyList()));
        val sub    = new SubCheckMethod(target, asList(
                        new Choice("White", emptyList()),
                        new Choice("Black", emptyList()),
                        new Choice("RGB",   "validateRGB", asList(
                                new ChoiceParam("r", new Type("int")),
                                new ChoiceParam("g", new Type("int")),
                                new ChoiceParam("b", new Type("int"))
                                ))));
        val lines = sub.lines().stream().filter(Objects::nonNull).collect(Collectors.joining("\n"));
        assertEquals(
                "public boolean isWhite() { return this instanceof White; }\n" + 
                "public Result<White> asWhite() { return Result.of(this).filter(White.class).map(White.class::cast); }\n" + 
                "public Color ifWhite(Consumer<White> action) { if (isWhite()) action.accept((White)this); return this; }\n" + 
                "public Color ifWhite(Runnable action) { if (isWhite()) action.run(); return this; }\n" + 
                "public boolean isBlack() { return this instanceof Black; }\n" + 
                "public Result<Black> asBlack() { return Result.of(this).filter(Black.class).map(Black.class::cast); }\n" + 
                "public Color ifBlack(Consumer<Black> action) { if (isBlack()) action.accept((Black)this); return this; }\n" + 
                "public Color ifBlack(Runnable action) { if (isBlack()) action.run(); return this; }\n" + 
                "public boolean isRGB() { return this instanceof RGB; }\n" + 
                "public Result<RGB> asRGB() { return Result.of(this).filter(RGB.class).map(RGB.class::cast); }\n" + 
                "public Color ifRGB(Consumer<RGB> action) { if (isRGB()) action.accept((RGB)this); return this; }\n" + 
                "public Color ifRGB(Runnable action) { if (isRGB()) action.run(); return this; }",
                lines);
    }
    
    @Test
    public void testTargetTypeGeneral_expand() {
        val target = new TargetClass(new SourceSpec("Color", new Type("p1.p2", "ColorSpec"), emptyList()));
        val sub    = new TargetTypeGeneral(target, asList(
                        new Choice("White", emptyList()),
                        new Choice("Black", emptyList()),
                        new Choice("RGB",   "validateRGB", asList(
                                new ChoiceParam("r", new Type("int")),
                                new ChoiceParam("g", new Type("int")),
                                new ChoiceParam("b", new Type("int"))
                                ))));
        val lines = sub.lines().stream().filter(Objects::nonNull).collect(Collectors.joining("\n"));
        assertEquals(
                "public final ColorFirstSwitch mapSwitch = new ColorFirstSwitch(this);\n" + 
                "@Override public ColorFirstSwitch __switch() { return mapSwitch; }\n" + 
                "\n" + 
                "private volatile String toString = null;\n" + 
                "@Override\n" + 
                "public String toString() {\n" + 
                "    if (toString != null)\n" + 
                "        return toString;\n" + 
                "    synchronized(this) {\n" + 
                "        if (toString != null)\n" + 
                "            return toString;\n" + 
                "        toString = Switch(this)\n" + 
                "                .white(\"White\")\n" + 
                "                .black(\"Black\")\n" + 
                "                .rgb(rgb -> \"RGB(\" + String.format(\"%1$s,%2$s,%3$s\", rgb.r,rgb.g,rgb.b) + \")\")\n" + 
                "        ;\n" + 
                "        return toString;\n" + 
                "    }\n" + 
                "}\n" + 
                "@Override\n" + 
                "public int hashCode() {\n" + 
                "    return toString().hashCode();\n" + 
                "}\n" + 
                "@Override\n" + 
                "public boolean equals(Object obj) {\n" + 
                "    if (!(obj instanceof Color))\n" + 
                "        return false;\n" + 
                "    \n" + 
                "    if (this == obj)\n" + 
                "        return true;\n" + 
                "    \n" + 
                "    String objToString  = obj.toString();\n" + 
                "    String thisToString = this.toString();\n" + 
                "    if (thisToString.equals(objToString))\n" + 
                "        return true;\n" + 
                "    \n" + 
                "    String objAlternative  = ((Color)obj).alternativeString();\n" + 
                "    String thisAlternative = this.alternativeString();\n" + 
                "    return thisAlternative.equals(objAlternative);\n" + 
                "}\n" + 
                "",
                lines);
    }
    
    @Test
    public void testSwitchClass_expand() {
        val target = new TargetClass(new SourceSpec("Color", new Type("p1.p2", "ColorSpec"), emptyList()));
        val sub    = new SwitchClass(target, false, asList(
                        new Choice("RGB",   "validateRGB", asList(
                            new ChoiceParam("r", new Type("int")),
                            new ChoiceParam("g", new Type("int")),
                            new ChoiceParam("b", new Type("int"))
                        ))));
        val lines  = sub.lines().stream().filter(Objects::nonNull).collect(Collectors.joining("\n"));
        assertEquals(
                "public static class ColorSwitchRGB<TARGET> extends UnionTypeSwitch<Color, TARGET> {\n" + 
                "    private ColorSwitchRGB(Color value, Function<Color, TARGET> action) { super(value, action); }\n" + 
                "    \n" + 
                "    public TARGET rgb(TARGET theValue) {\n" + 
                "        return rgb(d->theValue);\n" + 
                "    }\n" + 
                "    public TARGET rgb(Supplier<TARGET> theSupplier) {\n" + 
                "        return rgb(d->theSupplier.get());\n" + 
                "    }\n" + 
                "    public TARGET rgb(Function<? super RGB, TARGET> theAction) {\n" + 
                "        Function<Color, TARGET> oldAction = (Function<Color, TARGET>)action;\n" + 
                "        Function<Color, TARGET> newAction =\n" + 
                "            (action != null)\n" + 
                "            ? oldAction : \n" + 
                "                (value instanceof RGB)\n" + 
                "                ? (Function<Color, TARGET>)(d -> theAction.apply((RGB)d))\n" + 
                "                : oldAction;\n" + 
                "        \n" + 
                "        return newAction.apply(value);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<TARGET> rgb(Predicate<RGB> check, TARGET theValue) {\n" + 
                "        return rgb(check, d->theValue);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgb(Predicate<RGB> check, Supplier<TARGET> theSupplier) {\n" + 
                "        return rgb(check, d->theSupplier.get());\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgb(Predicate<RGB> check, Function<? super RGB, TARGET> theAction) {\n" + 
                "        Function<Color, TARGET> oldAction = (Function<Color, TARGET>)action;\n" + 
                "        Function<Color, TARGET> newAction =\n" + 
                "            (action != null)\n" + 
                "            ? oldAction : \n" + 
                "                ((value instanceof RGB) && check.test((RGB)value))\n" + 
                "                ? (Function<Color, TARGET>)(d -> theAction.apply((RGB)d))\n" + 
                "                : oldAction;\n" + 
                "        \n" + 
                "        return new ColorSwitchRGB<TARGET>(value, newAction);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(int r, Absent g, Absent b, TARGET theValue) {\n" + 
                "        return rgb(rgb -> checkEquals(r, rgb.r), theValue);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(int r, Absent g, Absent b, Supplier<TARGET> theSupplier) {\n" + 
                "        return rgb(rgb -> checkEquals(r, rgb.r), theSupplier);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(int r, Absent g, Absent b, Function<RGB, TARGET> theAction) {\n" + 
                "        return rgb(rgb -> checkEquals(r, rgb.r), theAction);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Predicate<Integer> rCheck, Absent g, Absent b, TARGET theValue) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r), theValue);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Predicate<Integer> rCheck, Absent g, Absent b, Supplier<TARGET> theSupplier) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r), theSupplier);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Predicate<Integer> rCheck, Absent g, Absent b, Function<RGB, TARGET> theAction) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r), theAction);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Absent r, int g, Absent b, TARGET theValue) {\n" + 
                "        return rgb(rgb -> checkEquals(g, rgb.g), theValue);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Absent r, int g, Absent b, Supplier<TARGET> theSupplier) {\n" + 
                "        return rgb(rgb -> checkEquals(g, rgb.g), theSupplier);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Absent r, int g, Absent b, Function<RGB, TARGET> theAction) {\n" + 
                "        return rgb(rgb -> checkEquals(g, rgb.g), theAction);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(int r, int g, Absent b, TARGET theValue) {\n" + 
                "        return rgb(rgb -> checkEquals(r, rgb.r) && checkEquals(g, rgb.g), theValue);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(int r, int g, Absent b, Supplier<TARGET> theSupplier) {\n" + 
                "        return rgb(rgb -> checkEquals(r, rgb.r) && checkEquals(g, rgb.g), theSupplier);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(int r, int g, Absent b, Function<RGB, TARGET> theAction) {\n" + 
                "        return rgb(rgb -> checkEquals(r, rgb.r) && checkEquals(g, rgb.g), theAction);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Predicate<Integer> rCheck, int g, Absent b, TARGET theValue) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r) && checkEquals(g, rgb.g), theValue);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Predicate<Integer> rCheck, int g, Absent b, Supplier<TARGET> theSupplier) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r) && checkEquals(g, rgb.g), theSupplier);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Predicate<Integer> rCheck, int g, Absent b, Function<RGB, TARGET> theAction) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r) && checkEquals(g, rgb.g), theAction);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Absent r, Predicate<Integer> gCheck, Absent b, TARGET theValue) {\n" + 
                "        return rgb(rgb -> gCheck.test(rgb.g), theValue);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Absent r, Predicate<Integer> gCheck, Absent b, Supplier<TARGET> theSupplier) {\n" + 
                "        return rgb(rgb -> gCheck.test(rgb.g), theSupplier);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Absent r, Predicate<Integer> gCheck, Absent b, Function<RGB, TARGET> theAction) {\n" + 
                "        return rgb(rgb -> gCheck.test(rgb.g), theAction);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(int r, Predicate<Integer> gCheck, Absent b, TARGET theValue) {\n" + 
                "        return rgb(rgb -> checkEquals(r, rgb.r) && gCheck.test(rgb.g), theValue);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(int r, Predicate<Integer> gCheck, Absent b, Supplier<TARGET> theSupplier) {\n" + 
                "        return rgb(rgb -> checkEquals(r, rgb.r) && gCheck.test(rgb.g), theSupplier);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(int r, Predicate<Integer> gCheck, Absent b, Function<RGB, TARGET> theAction) {\n" + 
                "        return rgb(rgb -> checkEquals(r, rgb.r) && gCheck.test(rgb.g), theAction);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Predicate<Integer> rCheck, Predicate<Integer> gCheck, Absent b, TARGET theValue) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r) && gCheck.test(rgb.g), theValue);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Predicate<Integer> rCheck, Predicate<Integer> gCheck, Absent b, Supplier<TARGET> theSupplier) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r) && gCheck.test(rgb.g), theSupplier);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Predicate<Integer> rCheck, Predicate<Integer> gCheck, Absent b, Function<RGB, TARGET> theAction) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r) && gCheck.test(rgb.g), theAction);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Absent r, Absent g, int b, TARGET theValue) {\n" + 
                "        return rgb(rgb -> checkEquals(b, rgb.b), theValue);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Absent r, Absent g, int b, Supplier<TARGET> theSupplier) {\n" + 
                "        return rgb(rgb -> checkEquals(b, rgb.b), theSupplier);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Absent r, Absent g, int b, Function<RGB, TARGET> theAction) {\n" + 
                "        return rgb(rgb -> checkEquals(b, rgb.b), theAction);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(int r, Absent g, int b, TARGET theValue) {\n" + 
                "        return rgb(rgb -> checkEquals(r, rgb.r) && checkEquals(b, rgb.b), theValue);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(int r, Absent g, int b, Supplier<TARGET> theSupplier) {\n" + 
                "        return rgb(rgb -> checkEquals(r, rgb.r) && checkEquals(b, rgb.b), theSupplier);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(int r, Absent g, int b, Function<RGB, TARGET> theAction) {\n" + 
                "        return rgb(rgb -> checkEquals(r, rgb.r) && checkEquals(b, rgb.b), theAction);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Predicate<Integer> rCheck, Absent g, int b, TARGET theValue) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r) && checkEquals(b, rgb.b), theValue);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Predicate<Integer> rCheck, Absent g, int b, Supplier<TARGET> theSupplier) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r) && checkEquals(b, rgb.b), theSupplier);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Predicate<Integer> rCheck, Absent g, int b, Function<RGB, TARGET> theAction) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r) && checkEquals(b, rgb.b), theAction);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Absent r, int g, int b, TARGET theValue) {\n" + 
                "        return rgb(rgb -> checkEquals(g, rgb.g) && checkEquals(b, rgb.b), theValue);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Absent r, int g, int b, Supplier<TARGET> theSupplier) {\n" + 
                "        return rgb(rgb -> checkEquals(g, rgb.g) && checkEquals(b, rgb.b), theSupplier);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Absent r, int g, int b, Function<RGB, TARGET> theAction) {\n" + 
                "        return rgb(rgb -> checkEquals(g, rgb.g) && checkEquals(b, rgb.b), theAction);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(int r, int g, int b, TARGET theValue) {\n" + 
                "        return rgb(rgb -> checkEquals(r, rgb.r) && checkEquals(g, rgb.g) && checkEquals(b, rgb.b), theValue);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(int r, int g, int b, Supplier<TARGET> theSupplier) {\n" + 
                "        return rgb(rgb -> checkEquals(r, rgb.r) && checkEquals(g, rgb.g) && checkEquals(b, rgb.b), theSupplier);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(int r, int g, int b, Function<RGB, TARGET> theAction) {\n" + 
                "        return rgb(rgb -> checkEquals(r, rgb.r) && checkEquals(g, rgb.g) && checkEquals(b, rgb.b), theAction);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Predicate<Integer> rCheck, int g, int b, TARGET theValue) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r) && checkEquals(g, rgb.g) && checkEquals(b, rgb.b), theValue);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Predicate<Integer> rCheck, int g, int b, Supplier<TARGET> theSupplier) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r) && checkEquals(g, rgb.g) && checkEquals(b, rgb.b), theSupplier);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Predicate<Integer> rCheck, int g, int b, Function<RGB, TARGET> theAction) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r) && checkEquals(g, rgb.g) && checkEquals(b, rgb.b), theAction);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Absent r, Predicate<Integer> gCheck, int b, TARGET theValue) {\n" + 
                "        return rgb(rgb -> gCheck.test(rgb.g) && checkEquals(b, rgb.b), theValue);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Absent r, Predicate<Integer> gCheck, int b, Supplier<TARGET> theSupplier) {\n" + 
                "        return rgb(rgb -> gCheck.test(rgb.g) && checkEquals(b, rgb.b), theSupplier);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Absent r, Predicate<Integer> gCheck, int b, Function<RGB, TARGET> theAction) {\n" + 
                "        return rgb(rgb -> gCheck.test(rgb.g) && checkEquals(b, rgb.b), theAction);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(int r, Predicate<Integer> gCheck, int b, TARGET theValue) {\n" + 
                "        return rgb(rgb -> checkEquals(r, rgb.r) && gCheck.test(rgb.g) && checkEquals(b, rgb.b), theValue);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(int r, Predicate<Integer> gCheck, int b, Supplier<TARGET> theSupplier) {\n" + 
                "        return rgb(rgb -> checkEquals(r, rgb.r) && gCheck.test(rgb.g) && checkEquals(b, rgb.b), theSupplier);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(int r, Predicate<Integer> gCheck, int b, Function<RGB, TARGET> theAction) {\n" + 
                "        return rgb(rgb -> checkEquals(r, rgb.r) && gCheck.test(rgb.g) && checkEquals(b, rgb.b), theAction);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Predicate<Integer> rCheck, Predicate<Integer> gCheck, int b, TARGET theValue) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r) && gCheck.test(rgb.g) && checkEquals(b, rgb.b), theValue);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Predicate<Integer> rCheck, Predicate<Integer> gCheck, int b, Supplier<TARGET> theSupplier) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r) && gCheck.test(rgb.g) && checkEquals(b, rgb.b), theSupplier);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Predicate<Integer> rCheck, Predicate<Integer> gCheck, int b, Function<RGB, TARGET> theAction) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r) && gCheck.test(rgb.g) && checkEquals(b, rgb.b), theAction);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Absent r, Absent g, Predicate<Integer> bCheck, TARGET theValue) {\n" + 
                "        return rgb(rgb -> bCheck.test(rgb.b), theValue);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Absent r, Absent g, Predicate<Integer> bCheck, Supplier<TARGET> theSupplier) {\n" + 
                "        return rgb(rgb -> bCheck.test(rgb.b), theSupplier);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Absent r, Absent g, Predicate<Integer> bCheck, Function<RGB, TARGET> theAction) {\n" + 
                "        return rgb(rgb -> bCheck.test(rgb.b), theAction);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(int r, Absent g, Predicate<Integer> bCheck, TARGET theValue) {\n" + 
                "        return rgb(rgb -> checkEquals(r, rgb.r) && bCheck.test(rgb.b), theValue);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(int r, Absent g, Predicate<Integer> bCheck, Supplier<TARGET> theSupplier) {\n" + 
                "        return rgb(rgb -> checkEquals(r, rgb.r) && bCheck.test(rgb.b), theSupplier);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(int r, Absent g, Predicate<Integer> bCheck, Function<RGB, TARGET> theAction) {\n" + 
                "        return rgb(rgb -> checkEquals(r, rgb.r) && bCheck.test(rgb.b), theAction);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Predicate<Integer> rCheck, Absent g, Predicate<Integer> bCheck, TARGET theValue) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r) && bCheck.test(rgb.b), theValue);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Predicate<Integer> rCheck, Absent g, Predicate<Integer> bCheck, Supplier<TARGET> theSupplier) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r) && bCheck.test(rgb.b), theSupplier);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Predicate<Integer> rCheck, Absent g, Predicate<Integer> bCheck, Function<RGB, TARGET> theAction) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r) && bCheck.test(rgb.b), theAction);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Absent r, int g, Predicate<Integer> bCheck, TARGET theValue) {\n" + 
                "        return rgb(rgb -> checkEquals(g, rgb.g) && bCheck.test(rgb.b), theValue);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Absent r, int g, Predicate<Integer> bCheck, Supplier<TARGET> theSupplier) {\n" + 
                "        return rgb(rgb -> checkEquals(g, rgb.g) && bCheck.test(rgb.b), theSupplier);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Absent r, int g, Predicate<Integer> bCheck, Function<RGB, TARGET> theAction) {\n" + 
                "        return rgb(rgb -> checkEquals(g, rgb.g) && bCheck.test(rgb.b), theAction);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(int r, int g, Predicate<Integer> bCheck, TARGET theValue) {\n" + 
                "        return rgb(rgb -> checkEquals(r, rgb.r) && checkEquals(g, rgb.g) && bCheck.test(rgb.b), theValue);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(int r, int g, Predicate<Integer> bCheck, Supplier<TARGET> theSupplier) {\n" + 
                "        return rgb(rgb -> checkEquals(r, rgb.r) && checkEquals(g, rgb.g) && bCheck.test(rgb.b), theSupplier);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(int r, int g, Predicate<Integer> bCheck, Function<RGB, TARGET> theAction) {\n" + 
                "        return rgb(rgb -> checkEquals(r, rgb.r) && checkEquals(g, rgb.g) && bCheck.test(rgb.b), theAction);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Predicate<Integer> rCheck, int g, Predicate<Integer> bCheck, TARGET theValue) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r) && checkEquals(g, rgb.g) && bCheck.test(rgb.b), theValue);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Predicate<Integer> rCheck, int g, Predicate<Integer> bCheck, Supplier<TARGET> theSupplier) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r) && checkEquals(g, rgb.g) && bCheck.test(rgb.b), theSupplier);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Predicate<Integer> rCheck, int g, Predicate<Integer> bCheck, Function<RGB, TARGET> theAction) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r) && checkEquals(g, rgb.g) && bCheck.test(rgb.b), theAction);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Absent r, Predicate<Integer> gCheck, Predicate<Integer> bCheck, TARGET theValue) {\n" + 
                "        return rgb(rgb -> gCheck.test(rgb.g) && bCheck.test(rgb.b), theValue);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Absent r, Predicate<Integer> gCheck, Predicate<Integer> bCheck, Supplier<TARGET> theSupplier) {\n" + 
                "        return rgb(rgb -> gCheck.test(rgb.g) && bCheck.test(rgb.b), theSupplier);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Absent r, Predicate<Integer> gCheck, Predicate<Integer> bCheck, Function<RGB, TARGET> theAction) {\n" + 
                "        return rgb(rgb -> gCheck.test(rgb.g) && bCheck.test(rgb.b), theAction);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(int r, Predicate<Integer> gCheck, Predicate<Integer> bCheck, TARGET theValue) {\n" + 
                "        return rgb(rgb -> checkEquals(r, rgb.r) && gCheck.test(rgb.g) && bCheck.test(rgb.b), theValue);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(int r, Predicate<Integer> gCheck, Predicate<Integer> bCheck, Supplier<TARGET> theSupplier) {\n" + 
                "        return rgb(rgb -> checkEquals(r, rgb.r) && gCheck.test(rgb.g) && bCheck.test(rgb.b), theSupplier);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(int r, Predicate<Integer> gCheck, Predicate<Integer> bCheck, Function<RGB, TARGET> theAction) {\n" + 
                "        return rgb(rgb -> checkEquals(r, rgb.r) && gCheck.test(rgb.g) && bCheck.test(rgb.b), theAction);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Predicate<Integer> rCheck, Predicate<Integer> gCheck, Predicate<Integer> bCheck, TARGET theValue) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r) && gCheck.test(rgb.g) && bCheck.test(rgb.b), theValue);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Predicate<Integer> rCheck, Predicate<Integer> gCheck, Predicate<Integer> bCheck, Supplier<TARGET> theSupplier) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r) && gCheck.test(rgb.g) && bCheck.test(rgb.b), theSupplier);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Predicate<Integer> rCheck, Predicate<Integer> gCheck, Predicate<Integer> bCheck, Function<RGB, TARGET> theAction) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r) && gCheck.test(rgb.g) && bCheck.test(rgb.b), theAction);\n" + 
                "    }\n" + 
                "}", lines);
    }
}

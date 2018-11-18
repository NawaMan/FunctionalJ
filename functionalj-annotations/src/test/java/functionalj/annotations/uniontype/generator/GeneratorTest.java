package functionalj.annotations.uniontype.generator;

import static functionalj.annotations.uniontype.generator.model.Method.Kind.DEFAULT;
import static functionalj.annotations.uniontype.generator.model.Method.Kind.STATIC;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.joining;
import static org.junit.Assert.assertEquals;

import java.util.Objects;
import java.util.stream.Collectors;

import org.junit.Test;

import functionalj.annotations.uniontype.generator.model.Choice;
import functionalj.annotations.uniontype.generator.model.ChoiceParam;
import functionalj.annotations.uniontype.generator.model.Generic;
import functionalj.annotations.uniontype.generator.model.Method;
import functionalj.annotations.uniontype.generator.model.Method.Kind;
import functionalj.annotations.uniontype.generator.model.MethodParam;
import functionalj.annotations.uniontype.generator.model.SourceSpec;
import functionalj.annotations.uniontype.generator.model.Type;
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
        val sourceSpec = new SourceSpec("Color", new Type("p1.p2", "ColorSpec"), emptyList());
        val target     = new TargetClass(sourceSpec);
        val sub        = new SubClassConstructor(target, new Choice("White"));
        val lines      = sub.lines().stream().filter(Objects::nonNull).collect(Collectors.joining("\n"));
        assertEquals(
                "public static final Color White() {\n" +
                "    return White.instance;\n" +
                "}", lines);
        assertEquals(
                "new functionalj.annotations.uniontype.generator.model.SourceSpec("
                    + "\"Color\", "
                    + "new functionalj.annotations.uniontype.generator.model.Type("
                        + "\"p1.p2\", "
                        + "null, "
                        + "\"ColorSpec\", "
                        + "java.util.Collections.emptyList()), "
                    + "null, "
                    + "false, "
                    + "java.util.Collections.emptyList(), "
                    + "java.util.Collections.emptyList(), "
                    + "java.util.Collections.emptyList())", sourceSpec.toCode());
    }
    
    @Test
    public void testSubClassConstructor_withParams() {
        val sourceSpec = new SourceSpec("Color", new Type("p1.p2", "EncloserClass", "ColorSpec"), emptyList());
        val target     = new TargetClass(sourceSpec);
        val sub        = new SubClassConstructor(target, 
                new Choice("RGB",   "__validateRGB", asList(
                    new ChoiceParam("r", new Type("int")),
                    new ChoiceParam("g", new Type("int")),
                    new ChoiceParam("b", new Type("int"))
                )));
        val lines  = sub.lines().stream().filter(Objects::nonNull).collect(Collectors.joining("\n"));
        assertEquals(
                "public static final Color RGB(int r, int g, int b) {\n" + 
                "    ColorSpec.__validateRGB(r, g, b);\n" + 
                "    return new RGB(r, g, b);\n" + 
                "}", lines);
        assertEquals(
                "new functionalj.annotations.uniontype.generator.model.SourceSpec("
                + "\"Color\", "
                + "new functionalj.annotations.uniontype.generator.model.Type("
                    + "\"p1.p2\", "
                    + "\"EncloserClass\", "
                    + "\"ColorSpec\", "
                    + "java.util.Collections.emptyList()), "
                + "null, "
                + "false, "
                + "java.util.Collections.emptyList(), "
                + "java.util.Collections.emptyList(), "
                + "java.util.Collections.emptyList()"
                + ")", sourceSpec.toCode());
    }
    @Test
    public void testSubClassConstructor_withParams_withGeneric() {
        val sourceType = new Type("p1.p2", null, "Next", asList(new Generic("D")));
        val sourceSpec = new SourceSpec("Coroutine", sourceType, "spec", false, asList(new Generic("D")), emptyList(), emptyList());
        val target = new TargetClass(sourceSpec);
        val sub    = new SubClassDefinition(target, 
                new Choice("Next", asList(
                    new ChoiceParam("next", new Type("functionalj.function", null, "Func1", asList(new Generic("D"), new Generic("Coroutine<D>"))))
                )));
        val lines  = sub.lines().stream().filter(Objects::nonNull).collect(Collectors.joining("\n"));
        assertEquals(
                "public static final class Next<D> extends Coroutine<D> {\n" + 
                "    private Func1<D,Coroutine<D>> next;\n" + 
                "    private Next(Func1<D,Coroutine<D>> next) {\n" + 
                "        this.next = next;\n" + 
                "    }\n" + 
                "    public Func1<D,Coroutine<D>> next() { return next; }\n" + 
                "    public Next<D> withNext(Func1<D,Coroutine<D>> next) { return new Next<D>(next); }\n" + 
                "}", lines);
        assertEquals(
                "new functionalj.annotations.uniontype.generator.model.SourceSpec(\"Coroutine\", new functionalj.annotations.uniontype.generator.model.Type(\"p1.p2\", null, \"Next\", java.util.Arrays.asList(new functionalj.annotations.uniontype.generator.model.Generic(\"D\", \"D\", null))), \"spec\", false, java.util.Arrays.asList(new functionalj.annotations.uniontype.generator.model.Generic(\"D\", \"D\", null)), java.util.Collections.emptyList(), java.util.Collections.emptyList())", sourceSpec.toCode());
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
                new Choice("RGB",   "__validateRGB", asList(
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
    public void testSubClassDefinition_withParams_withGeneric() {
        val sourceType = new Type("p1.p2", null, "Next", asList(new Generic("D")));
        val target = new TargetClass(new SourceSpec("Coroutine", sourceType, "spec", false, asList(new Generic("D")), emptyList(), emptyList()));
        val sub    = new SubClassDefinition(target, 
                new Choice("Next", asList(
                    new ChoiceParam("next", new Type("functionalj.function", null, "Func1", asList(new Generic("D"), new Generic("Coroutine<D>"))))
                )));
        val lines  = sub.lines().stream().filter(Objects::nonNull).collect(Collectors.joining("\n"));
        assertEquals(
                "public static final class Next<D> extends Coroutine<D> {\n" + 
                "    private Func1<D,Coroutine<D>> next;\n" + 
                "    private Next(Func1<D,Coroutine<D>> next) {\n" + 
                "        this.next = next;\n" + 
                "    }\n" + 
                "    public Func1<D,Coroutine<D>> next() { return next; }\n" + 
                "    public Next<D> withNext(Func1<D,Coroutine<D>> next) { return new Next<D>(next); }\n" + 
                "}", lines);
    }
    
    @Test
    public void testSwitchClass_simple_notLast() {
        val target = new TargetClass(new SourceSpec("Color", new Type("p1.p2", "ColorSpec"), emptyList()));
        val sub    = new SwitchClass(target, false, asList(
                        new Choice("White"),
                        new Choice("Black"),
                        new Choice("RGB",   "__validateRGB", asList(
                            new ChoiceParam("r", new Type("int")),
                            new ChoiceParam("g", new Type("int")),
                            new ChoiceParam("b", new Type("int"))
                        ))));
        val lines  = sub.lines().stream().filter(Objects::nonNull).collect(Collectors.joining("\n"));
        assertEquals(
                "public static class ColorSwitchWhiteBlackRGB<TARGET> extends UnionTypeSwitch<Color, TARGET> {\n" + 
                "    private ColorSwitchWhiteBlackRGB(Color theValue, Function<Color, TARGET> theAction) { super(theValue, theAction); }\n" + 
                "    \n" + 
                "    public ColorSwitchBlackRGB<TARGET> white(Function<? super White, TARGET> theAction) {\n" + 
                "        Function<Color, TARGET> oldAction = (Function<Color, TARGET>)$action;\n" + 
                "        Function<Color, TARGET> newAction =\n" + 
                "            ($action != null)\n" + 
                "            ? oldAction : \n" + 
                "                ($value instanceof White)\n" + 
                "                ? (Function<Color, TARGET>)(d -> theAction.apply((White)d))\n" + 
                "                : oldAction;\n" + 
                "        \n" + 
                "        return new ColorSwitchBlackRGB<TARGET>($value, newAction);\n" + 
                "    }\n" + 
                "    public ColorSwitchBlackRGB<TARGET> white(Supplier<TARGET> theSupplier) {\n" + 
                "        return white(d->theSupplier.get());\n" + 
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
                "    private ColorSwitchWhite(Color theValue, Function<Color, TARGET> theAction) { super(theValue, theAction); }\n" + 
                "    \n" + 
                "    public TARGET white(Function<? super White, TARGET> theAction) {\n" + 
                "        Function<Color, TARGET> oldAction = (Function<Color, TARGET>)$action;\n" + 
                "        Function<Color, TARGET> newAction =\n" + 
                "            ($action != null)\n" + 
                "            ? oldAction : \n" + 
                "                ($value instanceof White)\n" + 
                "                ? (Function<Color, TARGET>)(d -> theAction.apply((White)d))\n" + 
                "                : oldAction;\n" + 
                "        \n" + 
                "        return newAction.apply($value);\n" + 
                "    }\n" + 
                "    public TARGET white(Supplier<TARGET> theSupplier) {\n" + 
                "        return white(d->theSupplier.get());\n" + 
                "    }\n" + 
                "}", lines);
    }
    
    @Test
    public void testSubCheckMethod() {
        val target = new TargetClass(new SourceSpec("Color", new Type("p1.p2", "ColorSpec"), emptyList()));
        val sub    = new SubCheckMethod(target, asList(
                        new Choice("White", emptyList()),
                        new Choice("Black", emptyList()),
                        new Choice("RGB",   "__validateRGB", asList(
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
    public void testSourceMethods() {
        val target = new TargetClass(
                    new SourceSpec("Color", new Type("p1.p2", "ColorSpec"), "spec", true,
                    emptyList(),
                    emptyList(),
                    asList(
                            new Method(Kind.DEFAULT, "equals", new Type("boolean"), 
                                asList(
                                    new MethodParam("c", new Type("p1.p2", "Color")),
                                    new MethodParam("obj", Type.OBJECT)
                                )
                            ),
                            new Method(DEFAULT, "thisName", Type.STRING, 
                                asList(
                                    new MethodParam("c",  new Type("p1.p2", null, "Color")),
                                    new MethodParam("c2", new Type("p1.p2", null, "Color")),
                                    new MethodParam("s",  Type.STRING)
                                ),
                                asList(
                                    new Generic("T", "T extends Exception", asList(new Type("Exception")))
                                ),
                                asList(
                                    new Type("T")
                                )
                            ),
                            new Method(DEFAULT, "thisSelf", new Type("p1.p2", null, "Color"), 
                                asList(
                                    new MethodParam("c",  new Type("p1.p2", null, "Color")),
                                    new MethodParam("c2", new Type("p1.p2", null, "Color")),
                                    new MethodParam("s",  Type.STRING)
                                )
                            ),
                            new Method(Kind.STATIC, "toRGBString", new Type("boolean"), 
                                    asList(new MethodParam("c", new Type("p1.p2", "Color")))
                                )
                        )));
        val sub    = new SourceMethod(target);
        val lines = sub.lines().stream().filter(Objects::nonNull).collect(Collectors.joining("\n"));
        assertEquals(
                "public boolean equals(Object obj) {\n" + 
                "    return __spec.equals(Self.of(this), obj);\n" + 
                "}\n" + 
                "public <T extends Exception> String thisName(p1.p2.Color c2, String s) throws T {\n" + 
                "    return __spec.thisName(Self.of(this), Self.of(c2), s);\n" + 
                "}\n" + 
                "public p1.p2.Color thisSelf(p1.p2.Color c2, String s) {\n" + 
                "    return Self.getAsMe(__spec.thisSelf(Self.of(this), Self.of(c2), s));\n" + 
                "}\n" + 
                "public static boolean toRGBString(p1.p2.Color c) {\n" + 
                "    return ColorSpec.toRGBString(c);\n" + 
                "}",
                lines);
    }
    
    @Test
    public void testTargetTypeGeneral_expand() {
        val target = new TargetClass(new SourceSpec("Color", new Type("p1.p2", "ColorSpec"), emptyList()));
        val sub    = new TargetTypeGeneral(target, asList(
                        new Choice("White", emptyList()),
                        new Choice("Black", emptyList()),
                        new Choice("RGB",   "__validateRGB", asList(
                                new ChoiceParam("r", new Type("int")),
                                new ChoiceParam("g", new Type("int")),
                                new ChoiceParam("b", new Type("int"))
                                ))));
        val lines = sub.lines().stream().filter(Objects::nonNull).collect(Collectors.joining("\n"));
        assertEquals(
                "public final ColorFirstSwitch mapSwitch = new ColorFirstSwitch(this);\n" + 
                "@Override public ColorFirstSwitch __switch() {\n" + 
                "     return mapSwitch;\n" + 
                "}\n" + 
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
                "                .white(__ -> \"White\")\n" + 
                "                .black(__ -> \"Black\")\n" + 
                "                .rgb(rgb -> \"RGB(\" + String.format(\"%1$s,%2$s,%3$s\", rgb.r,rgb.g,rgb.b) + \")\")\n" + 
                "        ;\n" + 
                "        return toString;\n" + 
                "    }\n" + 
                "}\n" + 
                "\n" + 
                "@Override\n" + 
                "public int hashCode() {\n" + 
                "    return toString().hashCode();\n" + 
                "}\n" + 
                "\n" + 
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
                "    return thisToString.equals(objToString);\n" + 
                "}",
                lines);
    }
    
    @Test
    public void testTargetTypeGeneral_withMethods() {
        val colorType = new Type("p1.p2", "Color");
        val target = new TargetClass(
                    new SourceSpec("Color", new Type("p1.p2", "ColorSpec"), "spec", true,
                    emptyList(),
                    emptyList(),
                    asList(
                        new Method(DEFAULT, "equals", new Type("boolean"), 
                            asList(new MethodParam("c", colorType), new MethodParam("obj", Type.OBJECT))
                        ),
                        new Method(DEFAULT, "toString", Type.STRING, 
                            asList(new MethodParam("c", colorType))
                        ),
                        new Method(DEFAULT, "hashCode", new Type("int"), 
                            asList(new MethodParam("c", colorType))
                        ),
                        new Method(DEFAULT, "thisMethod", Type.STRING, 
                            asList(
                                new MethodParam("c",  colorType),
                                new MethodParam("c2", colorType),
                                new MethodParam("s",  Type.STRING)
                            )
                        ),
                        new Method(DEFAULT, "thisString", Type.STRING, asList(new MethodParam("s",  Type.STRING))),
                        new Method(STATIC, "staticName", Type.STRING, 
                            asList(
                                new MethodParam("c",  colorType),
                                new MethodParam("c2", colorType),
                                new MethodParam("s",  Type.STRING)
                            )
                        )
                    )));
        val choices = asList(
                        new Choice("White", emptyList()),
                        new Choice("Black", emptyList()),
                        new Choice("RGB", asList(
                                new ChoiceParam("r", new Type("int")),
                                new ChoiceParam("g", new Type("int")),
                                new ChoiceParam("b", new Type("int"))
                                )));
        assertEquals(
                "boolean equals(p1.p2.Color, Object)\n" + 
                "String toString(p1.p2.Color)\n" + 
                "int hashCode(p1.p2.Color)\n" + 
                "String thisMethod(p1.p2.Color, p1.p2.Color, String)\n" + 
                "String thisString(String)\n" + 
                "static String staticName(p1.p2.Color, p1.p2.Color, String)",
                target.spec.methods.stream().map(m -> m.signature).collect(joining("\n")));
        
        assertEquals(
                "public final ColorFirstSwitch mapSwitch = new ColorFirstSwitch(this);\n" + 
                "@Override public ColorFirstSwitch __switch() {\n" + 
                "     return mapSwitch;\n" + 
                "}\n" + 
                "\n" + 
                "\n" + 
                "",
                new TargetTypeGeneral(target, choices)
                .lines().stream().filter(Objects::nonNull).collect(Collectors.joining("\n")));
        
        assertEquals(
                "public boolean equals(Object obj) {\n" + 
                "    return __spec.equals(Self.of(this), obj);\n" + 
                "}\n" + 
                "public String toString() {\n" + 
                "    return __spec.toString(Self.of(this));\n" + 
                "}\n" + 
                "public int hashCode() {\n" + 
                "    return __spec.hashCode(Self.of(this));\n" + 
                "}\n" + 
                "public String thisMethod(p1.p2.Color c2, String s) {\n" + 
                "    return __spec.thisMethod(Self.of(this), Self.of(c2), s);\n" + 
                "}\n" + 
                "public String thisString(String s) {\n" + 
                "    return __spec.thisString(s);\n" + 
                "}\n" + 
                "public static String staticName(p1.p2.Color c, p1.p2.Color c2, String s) {\n" + 
                "    return ColorSpec.staticName(c, c2, s);\n" + 
                "}",
                new SourceMethod(target)
                .lines().stream().filter(Objects::nonNull).collect(Collectors.joining("\n")));
    }
    
    @Test
    public void testSubClassDefinition_withPublicField() {
        val sourceType = new Type("p1.p2", null, "Next", asList(new Generic("D")));
        val target = new TargetClass(new SourceSpec("Coroutine", sourceType, "spec", true, asList(new Generic("D")), emptyList(), emptyList()));
        val sub    = new SubClassDefinition(target, 
                new Choice("Next", asList(
                    new ChoiceParam("next", new Type("functionalj.function", null, "Func1", asList(new Generic("D"), new Generic("Coroutine<D>"))))
                )));
        val lines  = sub.lines().stream().filter(Objects::nonNull).collect(Collectors.joining("\n"));
        assertEquals(
                "public static final class Next<D> extends Coroutine<D> {\n" + 
                "    public Func1<D,Coroutine<D>> next;\n" + 
                "    private Next(Func1<D,Coroutine<D>> next) {\n" + 
                "        this.next = next;\n" + 
                "    }\n" + 
                "    public Func1<D,Coroutine<D>> next() { return next; }\n" + 
                "    public Next<D> withNext(Func1<D,Coroutine<D>> next) { return new Next<D>(next); }\n" + 
                "}", lines);
    }
    
    @Test
    public void testSwitchClass_expand() {
        val target = new TargetClass(new SourceSpec("Color", new Type("p1.p2", "ColorSpec"), emptyList()));
        val sub    = new SwitchClass(target, false, asList(
                        new Choice("RGB",   "__validateRGB", asList(
                            new ChoiceParam("r", new Type("int")),
                            new ChoiceParam("g", new Type("int")),
                            new ChoiceParam("b", new Type("int"))
                        ))));
        val lines  = sub.lines().stream().filter(Objects::nonNull).collect(Collectors.joining("\n"));
        assertEquals(
                "public static class ColorSwitchRGB<TARGET> extends UnionTypeSwitch<Color, TARGET> {\n" + 
                "    private ColorSwitchRGB(Color theValue, Function<Color, TARGET> theAction) { super(theValue, theAction); }\n" + 
                "    \n" + 
                "    public TARGET rgb(Function<? super RGB, TARGET> theAction) {\n" + 
                "        Function<Color, TARGET> oldAction = (Function<Color, TARGET>)$action;\n" + 
                "        Function<Color, TARGET> newAction =\n" + 
                "            ($action != null)\n" + 
                "            ? oldAction : \n" + 
                "                ($value instanceof RGB)\n" + 
                "                ? (Function<Color, TARGET>)(d -> theAction.apply((RGB)d))\n" + 
                "                : oldAction;\n" + 
                "        \n" + 
                "        return newAction.apply($value);\n" + 
                "    }\n" + 
                "    public TARGET rgb(Supplier<TARGET> theSupplier) {\n" + 
                "        return rgb(d->theSupplier.get());\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<TARGET> rgb(Predicate<RGB> check, Function<? super RGB, TARGET> theAction) {\n" + 
                "        Function<Color, TARGET> oldAction = (Function<Color, TARGET>)$action;\n" + 
                "        Function<Color, TARGET> newAction =\n" + 
                "            ($action != null)\n" + 
                "            ? oldAction : \n" + 
                "                (($value instanceof RGB) && check.test((RGB)$value))\n" + 
                "                ? (Function<Color, TARGET>)(d -> theAction.apply((RGB)d))\n" + 
                "                : oldAction;\n" + 
                "        \n" + 
                "        return new ColorSwitchRGB<TARGET>($value, newAction);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgb(Predicate<RGB> check, Supplier<TARGET> theSupplier) {\n" + 
                "        return rgb(check, d->theSupplier.get());\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(int aR, Absent g, Absent b, Function<RGB, TARGET> theAction) {\n" + 
                "        return rgb(rgb -> checkEquals(aR, rgb.r), theAction);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(int aR, Absent g, Absent b, Supplier<TARGET> theSupplier) {\n" + 
                "        return rgb(rgb -> checkEquals(aR, rgb.r), theSupplier);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Predicate<Integer> rCheck, Absent g, Absent b, Function<RGB, TARGET> theAction) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r), theAction);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Predicate<Integer> rCheck, Absent g, Absent b, Supplier<TARGET> theSupplier) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r), theSupplier);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Absent r, int aG, Absent b, Function<RGB, TARGET> theAction) {\n" + 
                "        return rgb(rgb -> checkEquals(aG, rgb.g), theAction);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Absent r, int aG, Absent b, Supplier<TARGET> theSupplier) {\n" + 
                "        return rgb(rgb -> checkEquals(aG, rgb.g), theSupplier);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(int aR, int aG, Absent b, Function<RGB, TARGET> theAction) {\n" + 
                "        return rgb(rgb -> checkEquals(aR, rgb.r) && checkEquals(aG, rgb.g), theAction);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(int aR, int aG, Absent b, Supplier<TARGET> theSupplier) {\n" + 
                "        return rgb(rgb -> checkEquals(aR, rgb.r) && checkEquals(aG, rgb.g), theSupplier);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Predicate<Integer> rCheck, int aG, Absent b, Function<RGB, TARGET> theAction) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r) && checkEquals(aG, rgb.g), theAction);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Predicate<Integer> rCheck, int aG, Absent b, Supplier<TARGET> theSupplier) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r) && checkEquals(aG, rgb.g), theSupplier);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Absent r, Predicate<Integer> gCheck, Absent b, Function<RGB, TARGET> theAction) {\n" + 
                "        return rgb(rgb -> gCheck.test(rgb.g), theAction);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Absent r, Predicate<Integer> gCheck, Absent b, Supplier<TARGET> theSupplier) {\n" + 
                "        return rgb(rgb -> gCheck.test(rgb.g), theSupplier);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(int aR, Predicate<Integer> gCheck, Absent b, Function<RGB, TARGET> theAction) {\n" + 
                "        return rgb(rgb -> checkEquals(aR, rgb.r) && gCheck.test(rgb.g), theAction);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(int aR, Predicate<Integer> gCheck, Absent b, Supplier<TARGET> theSupplier) {\n" + 
                "        return rgb(rgb -> checkEquals(aR, rgb.r) && gCheck.test(rgb.g), theSupplier);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Predicate<Integer> rCheck, Predicate<Integer> gCheck, Absent b, Function<RGB, TARGET> theAction) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r) && gCheck.test(rgb.g), theAction);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Predicate<Integer> rCheck, Predicate<Integer> gCheck, Absent b, Supplier<TARGET> theSupplier) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r) && gCheck.test(rgb.g), theSupplier);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Absent r, Absent g, int aB, Function<RGB, TARGET> theAction) {\n" + 
                "        return rgb(rgb -> checkEquals(aB, rgb.b), theAction);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Absent r, Absent g, int aB, Supplier<TARGET> theSupplier) {\n" + 
                "        return rgb(rgb -> checkEquals(aB, rgb.b), theSupplier);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(int aR, Absent g, int aB, Function<RGB, TARGET> theAction) {\n" + 
                "        return rgb(rgb -> checkEquals(aR, rgb.r) && checkEquals(aB, rgb.b), theAction);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(int aR, Absent g, int aB, Supplier<TARGET> theSupplier) {\n" + 
                "        return rgb(rgb -> checkEquals(aR, rgb.r) && checkEquals(aB, rgb.b), theSupplier);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Predicate<Integer> rCheck, Absent g, int aB, Function<RGB, TARGET> theAction) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r) && checkEquals(aB, rgb.b), theAction);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Predicate<Integer> rCheck, Absent g, int aB, Supplier<TARGET> theSupplier) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r) && checkEquals(aB, rgb.b), theSupplier);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Absent r, int aG, int aB, Function<RGB, TARGET> theAction) {\n" + 
                "        return rgb(rgb -> checkEquals(aG, rgb.g) && checkEquals(aB, rgb.b), theAction);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Absent r, int aG, int aB, Supplier<TARGET> theSupplier) {\n" + 
                "        return rgb(rgb -> checkEquals(aG, rgb.g) && checkEquals(aB, rgb.b), theSupplier);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(int aR, int aG, int aB, Function<RGB, TARGET> theAction) {\n" + 
                "        return rgb(rgb -> checkEquals(aR, rgb.r) && checkEquals(aG, rgb.g) && checkEquals(aB, rgb.b), theAction);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(int aR, int aG, int aB, Supplier<TARGET> theSupplier) {\n" + 
                "        return rgb(rgb -> checkEquals(aR, rgb.r) && checkEquals(aG, rgb.g) && checkEquals(aB, rgb.b), theSupplier);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Predicate<Integer> rCheck, int aG, int aB, Function<RGB, TARGET> theAction) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r) && checkEquals(aG, rgb.g) && checkEquals(aB, rgb.b), theAction);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Predicate<Integer> rCheck, int aG, int aB, Supplier<TARGET> theSupplier) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r) && checkEquals(aG, rgb.g) && checkEquals(aB, rgb.b), theSupplier);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Absent r, Predicate<Integer> gCheck, int aB, Function<RGB, TARGET> theAction) {\n" + 
                "        return rgb(rgb -> gCheck.test(rgb.g) && checkEquals(aB, rgb.b), theAction);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Absent r, Predicate<Integer> gCheck, int aB, Supplier<TARGET> theSupplier) {\n" + 
                "        return rgb(rgb -> gCheck.test(rgb.g) && checkEquals(aB, rgb.b), theSupplier);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(int aR, Predicate<Integer> gCheck, int aB, Function<RGB, TARGET> theAction) {\n" + 
                "        return rgb(rgb -> checkEquals(aR, rgb.r) && gCheck.test(rgb.g) && checkEquals(aB, rgb.b), theAction);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(int aR, Predicate<Integer> gCheck, int aB, Supplier<TARGET> theSupplier) {\n" + 
                "        return rgb(rgb -> checkEquals(aR, rgb.r) && gCheck.test(rgb.g) && checkEquals(aB, rgb.b), theSupplier);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Predicate<Integer> rCheck, Predicate<Integer> gCheck, int aB, Function<RGB, TARGET> theAction) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r) && gCheck.test(rgb.g) && checkEquals(aB, rgb.b), theAction);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Predicate<Integer> rCheck, Predicate<Integer> gCheck, int aB, Supplier<TARGET> theSupplier) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r) && gCheck.test(rgb.g) && checkEquals(aB, rgb.b), theSupplier);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Absent r, Absent g, Predicate<Integer> bCheck, Function<RGB, TARGET> theAction) {\n" + 
                "        return rgb(rgb -> bCheck.test(rgb.b), theAction);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Absent r, Absent g, Predicate<Integer> bCheck, Supplier<TARGET> theSupplier) {\n" + 
                "        return rgb(rgb -> bCheck.test(rgb.b), theSupplier);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(int aR, Absent g, Predicate<Integer> bCheck, Function<RGB, TARGET> theAction) {\n" + 
                "        return rgb(rgb -> checkEquals(aR, rgb.r) && bCheck.test(rgb.b), theAction);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(int aR, Absent g, Predicate<Integer> bCheck, Supplier<TARGET> theSupplier) {\n" + 
                "        return rgb(rgb -> checkEquals(aR, rgb.r) && bCheck.test(rgb.b), theSupplier);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Predicate<Integer> rCheck, Absent g, Predicate<Integer> bCheck, Function<RGB, TARGET> theAction) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r) && bCheck.test(rgb.b), theAction);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Predicate<Integer> rCheck, Absent g, Predicate<Integer> bCheck, Supplier<TARGET> theSupplier) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r) && bCheck.test(rgb.b), theSupplier);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Absent r, int aG, Predicate<Integer> bCheck, Function<RGB, TARGET> theAction) {\n" + 
                "        return rgb(rgb -> checkEquals(aG, rgb.g) && bCheck.test(rgb.b), theAction);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Absent r, int aG, Predicate<Integer> bCheck, Supplier<TARGET> theSupplier) {\n" + 
                "        return rgb(rgb -> checkEquals(aG, rgb.g) && bCheck.test(rgb.b), theSupplier);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(int aR, int aG, Predicate<Integer> bCheck, Function<RGB, TARGET> theAction) {\n" + 
                "        return rgb(rgb -> checkEquals(aR, rgb.r) && checkEquals(aG, rgb.g) && bCheck.test(rgb.b), theAction);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(int aR, int aG, Predicate<Integer> bCheck, Supplier<TARGET> theSupplier) {\n" + 
                "        return rgb(rgb -> checkEquals(aR, rgb.r) && checkEquals(aG, rgb.g) && bCheck.test(rgb.b), theSupplier);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Predicate<Integer> rCheck, int aG, Predicate<Integer> bCheck, Function<RGB, TARGET> theAction) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r) && checkEquals(aG, rgb.g) && bCheck.test(rgb.b), theAction);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Predicate<Integer> rCheck, int aG, Predicate<Integer> bCheck, Supplier<TARGET> theSupplier) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r) && checkEquals(aG, rgb.g) && bCheck.test(rgb.b), theSupplier);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Absent r, Predicate<Integer> gCheck, Predicate<Integer> bCheck, Function<RGB, TARGET> theAction) {\n" + 
                "        return rgb(rgb -> gCheck.test(rgb.g) && bCheck.test(rgb.b), theAction);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Absent r, Predicate<Integer> gCheck, Predicate<Integer> bCheck, Supplier<TARGET> theSupplier) {\n" + 
                "        return rgb(rgb -> gCheck.test(rgb.g) && bCheck.test(rgb.b), theSupplier);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(int aR, Predicate<Integer> gCheck, Predicate<Integer> bCheck, Function<RGB, TARGET> theAction) {\n" + 
                "        return rgb(rgb -> checkEquals(aR, rgb.r) && gCheck.test(rgb.g) && bCheck.test(rgb.b), theAction);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(int aR, Predicate<Integer> gCheck, Predicate<Integer> bCheck, Supplier<TARGET> theSupplier) {\n" + 
                "        return rgb(rgb -> checkEquals(aR, rgb.r) && gCheck.test(rgb.g) && bCheck.test(rgb.b), theSupplier);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Predicate<Integer> rCheck, Predicate<Integer> gCheck, Predicate<Integer> bCheck, Function<RGB, TARGET> theAction) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r) && gCheck.test(rgb.g) && bCheck.test(rgb.b), theAction);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<TARGET> rgbOf(Predicate<Integer> rCheck, Predicate<Integer> gCheck, Predicate<Integer> bCheck, Supplier<TARGET> theSupplier) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r) && gCheck.test(rgb.g) && bCheck.test(rgb.b), theSupplier);\n" + 
                "    }\n" + 
                "}", lines);
    }
}

// ============================================================================
// Copyright (c) 2017-2019 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import static functionalj.types.choice.generator.model.Method.Kind.DEFAULT;
import static functionalj.types.choice.generator.model.Method.Kind.STATIC;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.joining;
import static org.junit.Assert.assertEquals;

import java.util.Objects;
import java.util.stream.Collectors;

import org.junit.Test;

import functionalj.types.Generic;
import functionalj.types.Type;
import functionalj.types.choice.generator.model.Case;
import functionalj.types.choice.generator.model.CaseParam;
import functionalj.types.choice.generator.model.Method;
import functionalj.types.choice.generator.model.Method.Kind;
import functionalj.types.choice.generator.model.MethodParam;
import functionalj.types.choice.generator.model.SourceSpec;
import lombok.val;
import lombok.experimental.ExtensionMethod;


@ExtensionMethod(functionalj.types.choice.generator.Utils.class)
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
        val sub        = new SubClassConstructor(target, new Case("White"));
        val lines      = sub.lines().stream().filter(Objects::nonNull).collect(Collectors.joining("\n"));
        assertEquals(
                "public static final White white = White.instance;\n" + 
                "public static final White White() {\n" + 
                "    return White.instance;\n" + 
                "}", lines);
        assertEquals(
                "new functionalj.types.choice.generator.model.SourceSpec("
                    + "\"Color\", "
                    + "new functionalj.types.Type("
                        + "\"p1.p2\", "
                        + "null, "
                        + "\"ColorSpec\", "
                        + "java.util.Collections.emptyList()), "
                    + "null, "
                    + "false, "
                    + "\"__tagged\", "
                    + "java.util.Collections.emptyList(), "
                    + "java.util.Collections.emptyList(), "
                    + "java.util.Collections.emptyList(), "
                    + "java.util.Collections.emptyList())", sourceSpec.toCode());
    }
    
    @Test
    public void testSubClassConstructor_withParams() {
        val sourceSpec = new SourceSpec("Color", new Type("p1.p2", "EncloserClass", "ColorSpec"), emptyList());
        val target     = new TargetClass(sourceSpec);
        val sub        = new SubClassConstructor(target, 
                new Case("RGB",   "__validateRGB", asList(
                    new CaseParam("r", new Type("int"), false),
                    new CaseParam("g", new Type("int"), false),
                    new CaseParam("b", new Type("int"), false)
                )));
        val lines  = sub.lines().stream().filter(Objects::nonNull).collect(Collectors.joining("\n"));
        assertEquals(
                "public static final RGB RGB(int r, int g, int b) {\n" + 
                "    ColorSpec.__validateRGB(r, g, b);\n" + 
                "    return new RGB(r, g, b);\n" + 
                "}", lines);
        assertEquals(
                "new functionalj.types.choice.generator.model.SourceSpec("
                + "\"Color\", "
                + "new functionalj.types.Type("
                    + "\"p1.p2\", "
                    + "\"EncloserClass\", "
                    + "\"ColorSpec\", "
                    + "java.util.Collections.emptyList()), "
                + "null, "
                + "false, "
                + "\"__tagged\", "
                + "java.util.Collections.emptyList(), "
                + "java.util.Collections.emptyList(), "
                + "java.util.Collections.emptyList(), "
                + "java.util.Collections.emptyList()"
                + ")", sourceSpec.toCode());
    }
    @Test
    public void testSubClassConstructor_withParams_withGeneric() {
        val sourceType = new Type("p1.p2", null, "Next", asList(new Generic("D")));
        val sourceSpec = new SourceSpec("Coroutine", sourceType, "spec", false, null, asList(new Generic("D")), emptyList(), emptyList(), emptyList());
        val target = new TargetClass(sourceSpec);
        val sub    = new SubClassDefinition(target, 
                new Case("Next", asList(
                    new CaseParam("next", new Type("functionalj.function", null, "Func1", asList(new Generic("D"), new Generic("Coroutine<D>"))), false)
                )));
        val lines  = sub.lines().stream().filter(Objects::nonNull).collect(Collectors.joining("\n"));
        assertEquals(
                "public static final class Next<D> extends Coroutine<D> {\n" + 
                "    public static final Next.NextLens<Next> theNext = new Next.NextLens<>(LensSpec.of(Next.class));\n" + 
                "    public static final Next.NextLens<Next> eachNext = theNext;\n" + 
                "    private Func1<D,Coroutine<D>> next;\n" + 
                "    private Next(Func1<D,Coroutine<D>> next) {\n" + 
                "        this.next = $utils.notNull(next);\n" + 
                "    }\n" + 
                "    public Func1<D,Coroutine<D>> next() { return next; }\n" + 
                "    public Next<D> withNext(Func1<D,Coroutine<D>> next) { return new Next<D>(next); }\n" + 
                "    public static class NextLens<HOST> extends ObjectLensImpl<HOST, Coroutine.Next> {\n" + 
                "        \n" + 
                "        public final ObjectLens<HOST, Object> next = (ObjectLens)createSubLens(Coroutine.Next::next, Coroutine.Next::withNext, ObjectLens::of);\n" + 
                "        \n" + 
                "        public NextLens(LensSpec<HOST, Coroutine.Next> spec) {\n" + 
                "            super(spec);\n" + 
                "        }\n" + 
                "        \n" + 
                "    }\n" + 
                "    public java.util.Map<String, Object> __toMap() {\n" + 
                "        java.util.Map<String, Object> map = new java.util.HashMap<>();\n" + 
                "        map.put(\"__tagged\", functionalj.types.IData.$utils.toMapValueObject(\"Next\"));\n" + 
                "        map.put(\"next\", this.next);\n" + 
                "        return map;\n" + 
                "    }\n" + 
                "    static private functionalj.map.FuncMap<String, functionalj.types.choice.generator.model.CaseParam> __schema__ = functionalj.map.FuncMap.<String, functionalj.types.choice.generator.model.CaseParam>newMap()\n" + 
                "        .with(\"next\", new functionalj.types.choice.generator.model.CaseParam(\"next\", new functionalj.types.Type(\"functionalj.function\", null, \"Func1\", java.util.Arrays.asList(new functionalj.types.Generic(\"D\", \"D\", java.util.Arrays.asList(new functionalj.types.Type(null, null, \"D\", java.util.Collections.emptyList()))), new functionalj.types.Generic(\"Coroutine<D>\", \"Coroutine<D>\", java.util.Arrays.asList(new functionalj.types.Type(null, null, \"Coroutine<D>\", java.util.Collections.emptyList()))))), false, null))\n" + 
                "        .build();\n" + 
                "    public static java.util.Map<String, functionalj.types.choice.generator.model.CaseParam> getCaseSchema() {\n" + 
                "        return __schema__;\n" + 
                "    }\n" + 
                "    public static Next caseFromMap(java.util.Map<String, Object> map) {\n" + 
                "        return Next(\n" + 
                "            $utils.propertyFromMap(map, __schema__, \"next\")\n" + 
                "        );\n" + 
                "    }\n" + 
                "}", lines);
        assertEquals(
                "new functionalj.types.choice.generator.model.SourceSpec(\"Coroutine\", new functionalj.types.Type(\"p1.p2\", null, \"Next\", java.util.Arrays.asList(new functionalj.types.Generic(\"D\", \"D\", java.util.Arrays.asList(new functionalj.types.Type(null, null, \"D\", java.util.Collections.emptyList()))))), \"spec\", false, \"__tagged\", java.util.Arrays.asList(new functionalj.types.Generic(\"D\", \"D\", java.util.Arrays.asList(new functionalj.types.Type(null, null, \"D\", java.util.Collections.emptyList())))), java.util.Collections.emptyList(), java.util.Collections.emptyList(), java.util.Collections.emptyList())", sourceSpec.toCode());
    }
    
    @Test
    public void testSubClassDefinition_noParams() {
        val target = new TargetClass(new SourceSpec("Color", new Type("p1.p2", "ColorSpec"), emptyList()));
        val sub    = new SubClassDefinition(target, new Case("White"));
        val lines  = sub.lines().stream().filter(Objects::nonNull).collect(Collectors.joining("\n"));
        assertEquals(
                "public static final class White extends Color {\n" + 
                "    public static final White.WhiteLens<White> theWhite = new White.WhiteLens<>(LensSpec.of(White.class));\n" + 
                "    public static final White.WhiteLens<White> eachWhite = theWhite;\n" + 
                "    private static final White instance = new White();\n" + 
                "    private White() {}\n" + 
                "    public static class WhiteLens<HOST> extends ObjectLensImpl<HOST, Color.White> {\n" + 
                "        \n" + 
                "        public WhiteLens(LensSpec<HOST, Color.White> spec) {\n" + 
                "            super(spec);\n" + 
                "        }\n" + 
                "        \n" + 
                "    }\n" + 
                "    public java.util.Map<String, Object> __toMap() {\n" + 
                "        return functionalj.map.FuncMap.empty();\n" + 
                "    }\n" + 
                "    static private functionalj.map.FuncMap<String, functionalj.types.choice.generator.model.CaseParam> __schema__ = functionalj.map.FuncMap.<String, functionalj.types.choice.generator.model.CaseParam>empty();\n" + 
                "    public static java.util.Map<String, functionalj.types.choice.generator.model.CaseParam> getCaseSchema() {\n" + 
                "        return __schema__;\n" + 
                "    }\n" + 
                "    public static White caseFromMap(java.util.Map<String, Object> map) {\n" + 
                "        return White(\n" + 
                "        );\n" + 
                "    }\n" + 
                "}", lines);
    }
    
    @Test
    public void testSubClassDefinition_withParams() {
        val target = new TargetClass(new SourceSpec("Color", new Type("p1.p2", "ColorSpec"), emptyList()));
        val sub    = new SubClassDefinition(target, 
                new Case("RGB",   "__validateRGB", asList(
                    new CaseParam("r", new Type("int"), false),
                    new CaseParam("g", new Type("int"), false),
                    new CaseParam("b", new Type("int"), false)
                )));
        val lines  = sub.lines().stream().filter(Objects::nonNull).collect(Collectors.joining("\n"));
        assertEquals(
                "public static final class RGB extends Color {\n" + 
                "    public static final RGB.RGBLens<RGB> theRGB = new RGB.RGBLens<>(LensSpec.of(RGB.class));\n" + 
                "    public static final RGB.RGBLens<RGB> eachRGB = theRGB;\n" + 
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
                "    public static class RGBLens<HOST> extends ObjectLensImpl<HOST, Color.RGB> {\n" + 
                "        \n" + 
                "        public final IntegerLens<HOST> r = createSubLensPrimitive(Color.RGB::r, Color.RGB::withR);\n" + 
                "        public final IntegerLens<HOST> g = createSubLensPrimitive(Color.RGB::g, Color.RGB::withG);\n" + 
                "        public final IntegerLens<HOST> b = createSubLensPrimitive(Color.RGB::b, Color.RGB::withB);\n" + 
                "        \n" + 
                "        public RGBLens(LensSpec<HOST, Color.RGB> spec) {\n" + 
                "            super(spec);\n" + 
                "        }\n" + 
                "        \n" + 
                "    }\n" + 
                "    public java.util.Map<String, Object> __toMap() {\n" + 
                "        java.util.Map<String, Object> map = new java.util.HashMap<>();\n" + 
                "        map.put(\"__tagged\", functionalj.types.IData.$utils.toMapValueObject(\"RGB\"));\n" + 
                "        map.put(\"r\", this.r);\n" + 
                "        map.put(\"g\", this.g);\n" + 
                "        map.put(\"b\", this.b);\n" + 
                "        return map;\n" + 
                "    }\n" + 
                "    static private functionalj.map.FuncMap<String, functionalj.types.choice.generator.model.CaseParam> __schema__ = functionalj.map.FuncMap.<String, functionalj.types.choice.generator.model.CaseParam>newMap()\n" + 
                "        .with(\"r\", new functionalj.types.choice.generator.model.CaseParam(\"r\", new functionalj.types.Type(null, null, \"int\", java.util.Collections.emptyList()), false, null))\n" + 
                "        .with(\"g\", new functionalj.types.choice.generator.model.CaseParam(\"g\", new functionalj.types.Type(null, null, \"int\", java.util.Collections.emptyList()), false, null))\n" + 
                "        .with(\"b\", new functionalj.types.choice.generator.model.CaseParam(\"b\", new functionalj.types.Type(null, null, \"int\", java.util.Collections.emptyList()), false, null))\n" + 
                "        .build();\n" + 
                "    public static java.util.Map<String, functionalj.types.choice.generator.model.CaseParam> getCaseSchema() {\n" + 
                "        return __schema__;\n" + 
                "    }\n" + 
                "    public static RGB caseFromMap(java.util.Map<String, Object> map) {\n" + 
                "        return RGB(\n" + 
                "            $utils.propertyFromMap(map, __schema__, \"r\"),\n" + 
                "            $utils.propertyFromMap(map, __schema__, \"g\"),\n" + 
                "            $utils.propertyFromMap(map, __schema__, \"b\")\n" + 
                "        );\n" + 
                "    }\n" + 
                "}", lines);
    }
    
    @Test
    public void testSubClassDefinition_withParams_withGeneric() {
        val sourceType = new Type("p1.p2", null, "Next", asList(new Generic("D")));
        val target = new TargetClass(new SourceSpec("Coroutine", sourceType, "spec", false, null, asList(new Generic("D")), emptyList(), emptyList(), emptyList()));
        val sub    = new SubClassDefinition(target, 
                new Case("Next", asList(
                    new CaseParam("next", new Type("functionalj.function", null, "Func1", asList(new Generic("D"), new Generic("Coroutine<D>"))), false)
                )));
        val lines  = sub.lines().stream().filter(Objects::nonNull).collect(Collectors.joining("\n"));
        assertEquals(
                "public static final class Next<D> extends Coroutine<D> {\n" + 
                "    public static final Next.NextLens<Next> theNext = new Next.NextLens<>(LensSpec.of(Next.class));\n" + 
                "    public static final Next.NextLens<Next> eachNext = theNext;\n" + 
                "    private Func1<D,Coroutine<D>> next;\n" + 
                "    private Next(Func1<D,Coroutine<D>> next) {\n" + 
                "        this.next = $utils.notNull(next);\n" + 
                "    }\n" + 
                "    public Func1<D,Coroutine<D>> next() { return next; }\n" + 
                "    public Next<D> withNext(Func1<D,Coroutine<D>> next) { return new Next<D>(next); }\n" + 
                "    public static class NextLens<HOST> extends ObjectLensImpl<HOST, Coroutine.Next> {\n" + 
                "        \n" + 
                "        public final ObjectLens<HOST, Object> next = (ObjectLens)createSubLens(Coroutine.Next::next, Coroutine.Next::withNext, ObjectLens::of);\n" + 
                "        \n" + 
                "        public NextLens(LensSpec<HOST, Coroutine.Next> spec) {\n" + 
                "            super(spec);\n" + 
                "        }\n" + 
                "        \n" + 
                "    }\n" + 
                "    public java.util.Map<String, Object> __toMap() {\n" + 
                "        java.util.Map<String, Object> map = new java.util.HashMap<>();\n" + 
                "        map.put(\"__tagged\", functionalj.types.IData.$utils.toMapValueObject(\"Next\"));\n" + 
                "        map.put(\"next\", this.next);\n" + 
                "        return map;\n" + 
                "    }\n" + 
                "    static private functionalj.map.FuncMap<String, functionalj.types.choice.generator.model.CaseParam> __schema__ = functionalj.map.FuncMap.<String, functionalj.types.choice.generator.model.CaseParam>newMap()\n" + 
                "        .with(\"next\", new functionalj.types.choice.generator.model.CaseParam(\"next\", new functionalj.types.Type(\"functionalj.function\", null, \"Func1\", java.util.Arrays.asList(new functionalj.types.Generic(\"D\", \"D\", java.util.Arrays.asList(new functionalj.types.Type(null, null, \"D\", java.util.Collections.emptyList()))), new functionalj.types.Generic(\"Coroutine<D>\", \"Coroutine<D>\", java.util.Arrays.asList(new functionalj.types.Type(null, null, \"Coroutine<D>\", java.util.Collections.emptyList()))))), false, null))\n" + 
                "        .build();\n" + 
                "    public static java.util.Map<String, functionalj.types.choice.generator.model.CaseParam> getCaseSchema() {\n" + 
                "        return __schema__;\n" + 
                "    }\n" + 
                "    public static Next caseFromMap(java.util.Map<String, Object> map) {\n" + 
                "        return Next(\n" + 
                "            $utils.propertyFromMap(map, __schema__, \"next\")\n" + 
                "        );\n" + 
                "    }\n" + 
                "}", lines);
    }
    
    @Test
    public void testSwitchClass_simple_notLast() {
        val target = new TargetClass(new SourceSpec("Color", new Type("p1.p2", "ColorSpec"), emptyList()));
        val sub    = new SwitchClass(target, false, asList(
                        new Case("White"),
                        new Case("Black"),
                        new Case("RGB",   "__validateRGB", asList(
                            new CaseParam("r", new Type("int"), false),
                            new CaseParam("g", new Type("int"), false),
                            new CaseParam("b", new Type("int"), false)
                        ))));
        val lines  = sub.lines().stream().filter(Objects::nonNull).collect(Collectors.joining("\n"));
        assertEquals(
                "public static class ColorSwitchWhiteBlackRGB<TARGET> extends ChoiceTypeSwitch<Color, TARGET> {\n" + 
                "    private ColorSwitchWhiteBlackRGB(Color theValue, Function<Color, ? extends TARGET> theAction) { super(theValue, theAction); }\n" + 
                "    \n" + 
                "    public ColorSwitchBlackRGB<TARGET> white(Function<? super White, ? extends TARGET> theAction) {\n" + 
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
                "    public ColorSwitchBlackRGB<TARGET> white(Supplier<? extends TARGET> theSupplier) {\n" + 
                "        return white(d->theSupplier.get());\n" + 
                "    }\n" + 
                "    public ColorSwitchBlackRGB<TARGET> white(TARGET theValue) {\n" + 
                "        return white(d->theValue);\n" + 
                "    }\n" + 
                "}", lines);
    }
    
    @Test
    public void testSwitchClass_simple_last() {
        val target = new TargetClass(new SourceSpec("Color", new Type("p1.p2", "ColorSpec"), emptyList()));
        val sub    = new SwitchClass(target, false, asList(new Case("White")));
        val lines  = sub.lines().stream().filter(Objects::nonNull).collect(Collectors.joining("\n"));
        assertEquals(
                "public static class ColorSwitchWhite<TARGET> extends ChoiceTypeSwitch<Color, TARGET> {\n" + 
                "    private ColorSwitchWhite(Color theValue, Function<Color, ? extends TARGET> theAction) { super(theValue, theAction); }\n" + 
                "    \n" + 
                "    public TARGET white(Function<? super White, ? extends TARGET> theAction) {\n" + 
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
                "    public TARGET white(Supplier<? extends TARGET> theSupplier) {\n" + 
                "        return white(d->theSupplier.get());\n" + 
                "    }\n" + 
                "    public TARGET white(TARGET theValue) {\n" + 
                "        return white(d->theValue);\n" + 
                "    }\n" + 
                "}", lines);
    }
    
    @Test
    public void testSubCheckMethod() {
        val target = new TargetClass(new SourceSpec("Color", new Type("p1.p2", "ColorSpec"), emptyList()));
        val sub    = new SubCheckMethod(target, asList(
                        new Case("White", emptyList()),
                        new Case("Black", emptyList()),
                        new Case("RGB",   "__validateRGB", asList(
                                new CaseParam("r", new Type("int"), false),
                                new CaseParam("g", new Type("int"), false),
                                new CaseParam("b", new Type("int"), false)
                                ))));
        val lines = sub.lines().stream().filter(Objects::nonNull).collect(Collectors.joining("\n"));
        assertEquals(
                "public boolean isWhite() { return this instanceof White; }\n" + 
                "public Result<White> asWhite() { return Result.valueOf(this).filter(White.class).map(White.class::cast); }\n" + 
                "public Color ifWhite(Consumer<White> action) { if (isWhite()) action.accept((White)this); return this; }\n" + 
                "public Color ifWhite(Runnable action) { if (isWhite()) action.run(); return this; }\n" + 
                "public boolean isBlack() { return this instanceof Black; }\n" + 
                "public Result<Black> asBlack() { return Result.valueOf(this).filter(Black.class).map(Black.class::cast); }\n" + 
                "public Color ifBlack(Consumer<Black> action) { if (isBlack()) action.accept((Black)this); return this; }\n" + 
                "public Color ifBlack(Runnable action) { if (isBlack()) action.run(); return this; }\n" + 
                "public boolean isRGB() { return this instanceof RGB; }\n" + 
                "public Result<RGB> asRGB() { return Result.valueOf(this).filter(RGB.class).map(RGB.class::cast); }\n" + 
                "public Color ifRGB(Consumer<RGB> action) { if (isRGB()) action.accept((RGB)this); return this; }\n" + 
                "public Color ifRGB(Runnable action) { if (isRGB()) action.run(); return this; }",
                lines);
    }
    
    @Test
    public void testSourceMethods() {
        val target = new TargetClass(
                    new SourceSpec("Color", new Type("p1.p2", "ColorSpec"), "spec", true, null,
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
                        ), emptyList()));
        val sub    = new SourceMethod(target);
        val lines = sub.lines().stream().filter(Objects::nonNull).collect(Collectors.joining("\n"));
        assertEquals(
                "public boolean equals(java.lang.Object obj) {\n" + 
                "    return __spec.equals(Self.wrap(this), obj);\n" + 
                "}\n" + 
                "public <T extends Exception> java.lang.String thisName(p1.p2.Color c2, java.lang.String s) throws T {\n" + 
                "    return __spec.thisName(Self.wrap(this), Self.wrap(c2), s);\n" + 
                "}\n" + 
                "public p1.p2.Color thisSelf(p1.p2.Color c2, java.lang.String s) {\n" + 
                "    return functionalj.types.choice.Self.unwrap(__spec.thisSelf(Self.wrap(this), Self.wrap(c2), s));\n" + 
                "}\n" + 
                "public static boolean toRGBString(p1.p2.Color c) {\n" + 
                "    return p1.p2.ColorSpec.toRGBString(c);\n" + 
                "}",
                lines);
    }
    
    @Test
    public void testTargetTypeGeneral_expand() {
        val target = new TargetClass(new SourceSpec("Color", new Type("p1.p2", "ColorSpec"), emptyList()));
        val sub    = new TargetTypeGeneral(target, asList(
                        new Case("White", emptyList()),
                        new Case("Black", emptyList()),
                        new Case("RGB",   "__validateRGB", asList(
                                new CaseParam("r", new Type("int"), false),
                                new CaseParam("g", new Type("int"), false),
                                new CaseParam("b", new Type("int"), false)
                                ))));
        val lines = sub.lines().stream().filter(Objects::nonNull).collect(Collectors.joining("\n"));
        assertEquals(
                "public java.util.Map<java.lang.String, java.util.Map<java.lang.String, functionalj.types.choice.generator.model.CaseParam>> __getSchema() {\n" + 
                "    return getChoiceSchema();\n" + 
                "}\n" + 
                "\n" + 
                "private final ColorFirstSwitch __switch = new ColorFirstSwitch(this);\n" + 
                "@Override public ColorFirstSwitch match() {\n" + 
                "     return __switch;\n" + 
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
                "        toString = $utils.Match(this)\n" + 
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
                    new SourceSpec("Color", new Type("p1.p2", "ColorSpec"), "spec", true, null,
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
                    ), emptyList()));
        val choices = asList(
                        new Case("White", emptyList()),
                        new Case("Black", emptyList()),
                        new Case("RGB", asList(
                                new CaseParam("r", new Type("int"), false),
                                new CaseParam("g", new Type("int"), false),
                                new CaseParam("b", new Type("int"), false)
                                )));
        assertEquals(
                "boolean equals(p1.p2.Color, java.lang.Object)\n" + 
                "java.lang.String toString(p1.p2.Color)\n" + 
                "int hashCode(p1.p2.Color)\n" + 
                "java.lang.String thisMethod(p1.p2.Color, p1.p2.Color, java.lang.String)\n" + 
                "java.lang.String thisString(java.lang.String)\n" + 
                "static java.lang.String staticName(p1.p2.Color, p1.p2.Color, java.lang.String)",
                target.spec.methods.stream().map(m -> m.signature).collect(joining("\n")));
        
        assertEquals(
                "public java.util.Map<java.lang.String, java.util.Map<java.lang.String, functionalj.types.choice.generator.model.CaseParam>> __getSchema() {\n" + 
                "    return getChoiceSchema();\n" + 
                "}\n" + 
                "\n" + 
                "private final ColorFirstSwitch __switch = new ColorFirstSwitch(this);\n" + 
                "@Override public ColorFirstSwitch match() {\n" + 
                "     return __switch;\n" + 
                "}\n" + 
                "\n" + 
                "\n" + 
                "",
                new TargetTypeGeneral(target, choices)
                .lines().stream().filter(Objects::nonNull).collect(Collectors.joining("\n")));
        
        assertEquals(
                "public boolean equals(java.lang.Object obj) {\n" + 
                "    return __spec.equals(Self.wrap(this), obj);\n" + 
                "}\n" + 
                "public java.lang.String toString() {\n" + 
                "    return __spec.toString(Self.wrap(this));\n" + 
                "}\n" + 
                "public int hashCode() {\n" + 
                "    return __spec.hashCode(Self.wrap(this));\n" + 
                "}\n" + 
                "public java.lang.String thisMethod(p1.p2.Color c2, java.lang.String s) {\n" + 
                "    return __spec.thisMethod(Self.wrap(this), Self.wrap(c2), s);\n" + 
                "}\n" + 
                "public java.lang.String thisString(java.lang.String s) {\n" + 
                "    return __spec.thisString(s);\n" + 
                "}\n" + 
                "public static java.lang.String staticName(p1.p2.Color c, p1.p2.Color c2, java.lang.String s) {\n" + 
                "    return p1.p2.ColorSpec.staticName(c, c2, s);\n" + 
                "}",
                new SourceMethod(target)
                .lines().stream().filter(Objects::nonNull).collect(Collectors.joining("\n")));
    }
    
    @Test
    public void testSubClassDefinition_withPublicField() {
        val sourceType = new Type("p1.p2", null, "Next", asList(new Generic("D")));
        val target = new TargetClass(new SourceSpec("Coroutine", sourceType, "spec", true, null, asList(new Generic("D")), emptyList(), emptyList(), emptyList()));
        val sub    = new SubClassDefinition(target, 
                new Case("Next", asList(
                    new CaseParam("next", new Type("functionalj.function", null, "Func1", asList(new Generic("D"), new Generic("Coroutine<D>"))), false)
                )));
        val lines  = sub.lines().stream().filter(Objects::nonNull).collect(Collectors.joining("\n"));
        assertEquals(
                "public static final class Next<D> extends Coroutine<D> {\n" + 
                "    public static final Next.NextLens<Next> theNext = new Next.NextLens<>(LensSpec.of(Next.class));\n" + 
                "    public static final Next.NextLens<Next> eachNext = theNext;\n" + 
                "    public Func1<D,Coroutine<D>> next;\n" + 
                "    private Next(Func1<D,Coroutine<D>> next) {\n" + 
                "        this.next = $utils.notNull(next);\n" + 
                "    }\n" + 
                "    public Func1<D,Coroutine<D>> next() { return next; }\n" + 
                "    public Next<D> withNext(Func1<D,Coroutine<D>> next) { return new Next<D>(next); }\n" + 
                "    public static class NextLens<HOST> extends ObjectLensImpl<HOST, Coroutine.Next> {\n" + 
                "        \n" + 
                "        public final ObjectLens<HOST, Object> next = (ObjectLens)createSubLens(Coroutine.Next::next, Coroutine.Next::withNext, ObjectLens::of);\n" + 
                "        \n" + 
                "        public NextLens(LensSpec<HOST, Coroutine.Next> spec) {\n" + 
                "            super(spec);\n" + 
                "        }\n" + 
                "        \n" + 
                "    }\n" + 
                "    public java.util.Map<String, Object> __toMap() {\n" + 
                "        java.util.Map<String, Object> map = new java.util.HashMap<>();\n" + 
                "        map.put(\"__tagged\", functionalj.types.IData.$utils.toMapValueObject(\"Next\"));\n" + 
                "        map.put(\"next\", this.next);\n" + 
                "        return map;\n" + 
                "    }\n" + 
                "    static private functionalj.map.FuncMap<String, functionalj.types.choice.generator.model.CaseParam> __schema__ = functionalj.map.FuncMap.<String, functionalj.types.choice.generator.model.CaseParam>newMap()\n" + 
                "        .with(\"next\", new functionalj.types.choice.generator.model.CaseParam(\"next\", new functionalj.types.Type(\"functionalj.function\", null, \"Func1\", java.util.Arrays.asList(new functionalj.types.Generic(\"D\", \"D\", java.util.Arrays.asList(new functionalj.types.Type(null, null, \"D\", java.util.Collections.emptyList()))), new functionalj.types.Generic(\"Coroutine<D>\", \"Coroutine<D>\", java.util.Arrays.asList(new functionalj.types.Type(null, null, \"Coroutine<D>\", java.util.Collections.emptyList()))))), false, null))\n" + 
                "        .build();\n" + 
                "    public static java.util.Map<String, functionalj.types.choice.generator.model.CaseParam> getCaseSchema() {\n" + 
                "        return __schema__;\n" + 
                "    }\n" + 
                "    public static Next caseFromMap(java.util.Map<String, Object> map) {\n" + 
                "        return Next(\n" + 
                "            $utils.propertyFromMap(map, __schema__, \"next\")\n" + 
                "        );\n" + 
                "    }\n" + 
                "}", lines);
    }
}

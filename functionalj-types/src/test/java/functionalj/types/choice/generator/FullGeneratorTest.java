//// ============================================================================
//// Copyright (c) 2017-2019 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
//// ----------------------------------------------------------------------------
//// MIT License
//// 
//// Permission is hereby granted, free of charge, to any person obtaining a copy
//// of this software and associated documentation files (the "Software"), to deal
//// in the Software without restriction, including without limitation the rights
//// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//// copies of the Software, and to permit persons to whom the Software is
//// furnished to do so, subject to the following conditions:
//// 
//// The above copyright notice and this permission notice shall be included in all
//// copies or substantial portions of the Software.
//// 
//// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
//// SOFTWARE.
//// ============================================================================
//package functionalj.types.choice.generator;
//
//import static java.util.Arrays.asList;
//import static java.util.Collections.emptyList;
//import static org.junit.Assert.assertEquals;
//
//import java.util.Objects;
//import java.util.stream.Collectors;
//
//import org.junit.Test;
//
//import functionalj.types.Type;
//import functionalj.types.choice.generator.model.Case;
//import functionalj.types.choice.generator.model.CaseParam;
//import functionalj.types.choice.generator.model.Method;
//import functionalj.types.choice.generator.model.Method.Kind;
//import functionalj.types.choice.generator.model.MethodParam;
//import functionalj.types.choice.generator.model.SourceSpec;
//import lombok.val;
//
//@SuppressWarnings("javadoc")
//public class FullGeneratorTest {
//
////  public static interface ChohiceType1TypeSpec {
////      void White();
////      void Black();
////      void RGB(int r, int g, int b);
////      
////      static void __validateRGB(int r, int g, int b) {
////          if ((r < 0) || (r > 255)) throw new IllegalArgumentException("r: " + r);
////          if ((g < 0) || (g > 255)) throw new IllegalArgumentException("g: " + g);
////          if ((b < 0) || (b > 255)) throw new IllegalArgumentException("b: " + b);
////      }
////  }
//  
//  public static void main(String[] args) {
//      val generator = new Generator(
//              "BasicColor",
//              new Type(FullGeneratorTest.class.getPackage().getName(), "ChoiceTypeExampleTest", "ChoiceType1TypeSpec"),
//              "spec",
//              true,
//              null,
//              asList(),
//              asList(
//                  new Case("White", emptyList()),
//                  new Case("Black", emptyList()),
//                  new Case("RGB", "__validateRGB", asList(
//                      new CaseParam("r", new Type("int"), false),
//                      new CaseParam("g", new Type("int"), false),
//                      new CaseParam("b", new Type("int"), false)
//                  ))),
//              asList(
//                  new Method(Kind.DEFAULT, "equals", Type.BOOLEAN, 
//                      asList(
//                          new MethodParam("c", new Type("functionalj.types.choice.generator", "BasicColor")),
//                          new MethodParam("obj", Type.OBJECT)
//                      )
//                  ),
//                  new Method(Kind.STATIC, "toRGBString", Type.BOOLEAN, 
//                      asList(new MethodParam("c", new Type("functionalj.types.sealed.generator", "BasicColor"))
//                  )
//              )),
//              emptyList());
//      generator.lines().forEach(System.out::println);
//  }
//  
//  @Test
//  public void testGenerator() {
//      val spec = new SourceSpec(
//              "BasicColor",
//              new Type(FullGeneratorTest.class.getPackage().getName(), "ChoiceTypeExampleTest", "ChoiceType1TypeSpec"),
//              asList(
//                  new Case("White", emptyList()),
//                  new Case("Black", emptyList()),
//                  new Case("RGB", "__validateRGB", asList(
//                      new CaseParam("r", new Type("int"), false),
//                      new CaseParam("g", new Type("int"), false),
//                      new CaseParam("b", new Type("int"), false)
//                  ))));
//      val target = new TargetClass(spec);
//      val lines  = target.lines().stream().filter(Objects::nonNull).collect(Collectors.joining("\n"));
//      assertEquals(
//              "package functionalj.types.choice.generator;\n" + 
//              "\n" + 
//              "import functionalj.lens.core.LensSpec;\n" + 
//              "import functionalj.lens.lenses.*;\n" + 
//              "import functionalj.pipeable.Pipeable;\n" + 
//              "import functionalj.result.Result;\n" + 
//              "import functionalj.types.Absent;\n" + 
//              "import functionalj.types.choice.ChoiceTypeSwitch;\n" + 
//              "import functionalj.types.choice.IChoice;\n" + 
//              "import functionalj.types.choice.generator.ChoiceTypeExampleTest.ChoiceType1TypeSpec;\n" + 
//              "import java.util.function.Consumer;\n" + 
//              "import java.util.function.Function;\n" + 
//              "import java.util.function.Predicate;\n" + 
//              "import java.util.function.Supplier;\n" + 
//              "\n" + 
//              "// functionalj.types.choice.generator.ChoiceTypeExampleTest.ChoiceType1TypeSpec\n" + 
//              "\n" + 
//              "@SuppressWarnings({\"javadoc\", \"rawtypes\", \"unchecked\"})\n" + 
//              "public abstract class BasicColor implements IChoice<BasicColor.BasicColorFirstSwitch>, Pipeable<BasicColor> {\n" + 
//              "    \n" + 
//              "    public static final White white = White.instance;\n" + 
//              "    public static final White White() {\n" + 
//              "        return White.instance;\n" + 
//              "    }\n" + 
//              "    public static final Black black = Black.instance;\n" + 
//              "    public static final Black Black() {\n" + 
//              "        return Black.instance;\n" + 
//              "    }\n" + 
//              "    public static final RGB RGB(int r, int g, int b) {\n" + 
//              "        ChoiceType1TypeSpec.__validateRGB(r, g, b);\n" + 
//              "        return new RGB(r, g, b);\n" + 
//              "    }\n" + 
//              "    \n" + 
//              "    \n" + 
//              "    public static final BasicColorLens<BasicColor> theBasicColor = new BasicColorLens<>(LensSpec.of(BasicColor.class));\n" + 
//              "    public static class BasicColorLens<HOST> extends ObjectLensImpl<HOST, BasicColor> {\n" + 
//              "\n" + 
//              "        public final BooleanAccess<BasicColor> isWhite = BasicColor::isWhite;\n" + 
//              "        public final BooleanAccess<BasicColor> isBlack = BasicColor::isBlack;\n" + 
//              "        public final BooleanAccess<BasicColor> isRGB = BasicColor::isRGB;\n" + 
//              "        public final ResultAccess<HOST, White, White.WhiteLens<HOST>> asWhite = createSubResultLens(BasicColor::asWhite, null, White.WhiteLens::new);\n" + 
//              "        public final ResultAccess<HOST, Black, Black.BlackLens<HOST>> asBlack = createSubResultLens(BasicColor::asBlack, null, Black.BlackLens::new);\n" + 
//              "        public final ResultAccess<HOST, RGB, RGB.RGBLens<HOST>> asRGB = createSubResultLens(BasicColor::asRGB, null, RGB.RGBLens::new);\n" + 
//              "        public BasicColorLens(LensSpec<HOST, BasicColor> spec) {\n" + 
//              "            super(spec);\n" + 
//              "        }\n" + 
//              "    }\n" + 
//              "    \n" + 
//              "    private BasicColor() {}\n" + 
//              "    public BasicColor __data() throws Exception { return this; }\n" + 
//              "    public Result<BasicColor> toResult() { return Result.valueOf(this); }\n" + 
//              "    \n" + 
//              "    public static <T extends BasicColor> T fromMap(java.util.Map<String, Object> map) {\n" + 
//              "        String __tagged = (String)map.get(\"__tagged\");\n" + 
//              "        if (\"White\".equals(__tagged))\n" + 
//              "            return (T)White.caseFromMap(map);\n" + 
//              "        if (\"Black\".equals(__tagged))\n" + 
//              "            return (T)Black.caseFromMap(map);\n" + 
//              "        if (\"RGB\".equals(__tagged))\n" + 
//              "            return (T)RGB.caseFromMap(map);\n" + 
//              "        throw new IllegalArgumentException(\"Tagged value does not represent a valid type: \" + __tagged);\n" + 
//              "    }\n" + 
//              "    \n" + 
//              "    static private functionalj.map.FuncMap<String, java.util.Map<String, functionalj.types.choice.generator.model.CaseParam>> __schema__ = functionalj.map.FuncMap.<String, java.util.Map<String, functionalj.types.choice.generator.model.CaseParam>>newMap()\n" + 
//              "        .with(\"White\", White.getCaseSchema())\n" + 
//              "        .with(\"Black\", Black.getCaseSchema())\n" + 
//              "        .with(\"RGB\", RGB.getCaseSchema())\n" + 
//              "        .build();\n" + 
//              "    public static java.util.Map<String, java.util.Map<String, functionalj.types.choice.generator.model.CaseParam>> getChoiceSchema() {\n" + 
//              "        return __schema__;\n" + 
//              "    }\n" + 
//              "    \n" + 
//              "    public static final class White extends BasicColor {\n" + 
//              "        public static final White.WhiteLens<White> theWhite = new White.WhiteLens<>(LensSpec.of(White.class));\n" + 
//              "        private static final White instance = new White();\n" + 
//              "        private White() {}\n" + 
//              "        public static class WhiteLens<HOST> extends ObjectLensImpl<HOST, BasicColor.White> {\n" + 
//              "            \n" + 
//              "            public WhiteLens(LensSpec<HOST, BasicColor.White> spec) {\n" + 
//              "                super(spec);\n" + 
//              "            }\n" + 
//              "            \n" + 
//              "        }\n" + 
//              "        public java.util.Map<String, Object> __toMap() {\n" + 
//              "            return functionalj.map.FuncMap.empty();\n" + 
//              "        }\n" + 
//              "        static private functionalj.map.FuncMap<String, functionalj.types.choice.generator.model.CaseParam> __schema__ = functionalj.map.FuncMap.<String, functionalj.types.choice.generator.model.CaseParam>empty();\n" + 
//              "        public static java.util.Map<String, functionalj.types.choice.generator.model.CaseParam> getCaseSchema() {\n" + 
//              "            return __schema__;\n" + 
//              "        }\n" + 
//              "        public static White caseFromMap(java.util.Map<String, Object> map) {\n" + 
//              "            return White(\n" + 
//              "            );\n" + 
//              "        }\n" + 
//              "    }\n" + 
//              "    public static final class Black extends BasicColor {\n" + 
//              "        public static final Black.BlackLens<Black> theBlack = new Black.BlackLens<>(LensSpec.of(Black.class));\n" + 
//              "        private static final Black instance = new Black();\n" + 
//              "        private Black() {}\n" + 
//              "        public static class BlackLens<HOST> extends ObjectLensImpl<HOST, BasicColor.Black> {\n" + 
//              "            \n" + 
//              "            public BlackLens(LensSpec<HOST, BasicColor.Black> spec) {\n" + 
//              "                super(spec);\n" + 
//              "            }\n" + 
//              "            \n" + 
//              "        }\n" + 
//              "        public java.util.Map<String, Object> __toMap() {\n" + 
//              "            return functionalj.map.FuncMap.empty();\n" + 
//              "        }\n" + 
//              "        static private functionalj.map.FuncMap<String, functionalj.types.choice.generator.model.CaseParam> __schema__ = functionalj.map.FuncMap.<String, functionalj.types.choice.generator.model.CaseParam>empty();\n" + 
//              "        public static java.util.Map<String, functionalj.types.choice.generator.model.CaseParam> getCaseSchema() {\n" + 
//              "            return __schema__;\n" + 
//              "        }\n" + 
//              "        public static Black caseFromMap(java.util.Map<String, Object> map) {\n" + 
//              "            return Black(\n" + 
//              "            );\n" + 
//              "        }\n" + 
//              "    }\n" + 
//              "    public static final class RGB extends BasicColor {\n" + 
//              "        public static final RGB.RGBLens<RGB> theRGB = new RGB.RGBLens<>(LensSpec.of(RGB.class));\n" + 
//              "        private int r;\n" + 
//              "        private int g;\n" + 
//              "        private int b;\n" + 
//              "        private RGB(int r, int g, int b) {\n" + 
//              "            this.r = r;\n" + 
//              "            this.g = g;\n" + 
//              "            this.b = b;\n" + 
//              "        }\n" + 
//              "        public int r() { return r; }\n" + 
//              "        public int g() { return g; }\n" + 
//              "        public int b() { return b; }\n" + 
//              "        public RGB withR(int r) { return new RGB(r, g, b); }\n" + 
//              "        public RGB withG(int g) { return new RGB(r, g, b); }\n" + 
//              "        public RGB withB(int b) { return new RGB(r, g, b); }\n" + 
//              "        public static class RGBLens<HOST> extends ObjectLensImpl<HOST, BasicColor.RGB> {\n" + 
//              "            \n" + 
//              "            public final IntegerLens<HOST> r = (IntegerLens)createSubLens(BasicColor.RGB::r, BasicColor.RGB::withR, IntegerLens::of);\n" + 
//              "            public final IntegerLens<HOST> g = (IntegerLens)createSubLens(BasicColor.RGB::g, BasicColor.RGB::withG, IntegerLens::of);\n" + 
//              "            public final IntegerLens<HOST> b = (IntegerLens)createSubLens(BasicColor.RGB::b, BasicColor.RGB::withB, IntegerLens::of);\n" + 
//              "            \n" + 
//              "            public RGBLens(LensSpec<HOST, BasicColor.RGB> spec) {\n" + 
//              "                super(spec);\n" + 
//              "            }\n" + 
//              "            \n" + 
//              "        }\n" + 
//              "        public java.util.Map<String, Object> __toMap() {\n" + 
//              "            java.util.Map<String, Object> map = new java.util.HashMap<>();\n" + 
//              "            map.put(\"__tagged\", functionalj.types.IData.$utils.toMapValueObject(\"RGB\"));\n" + 
//              "            map.put(\"r\", this.r);\n" + 
//              "            map.put(\"g\", this.g);\n" + 
//              "            map.put(\"b\", this.b);\n" + 
//              "            return map;\n" + 
//              "        }\n" + 
//              "        static private functionalj.map.FuncMap<String, functionalj.types.choice.generator.model.CaseParam> __schema__ = functionalj.map.FuncMap.<String, functionalj.types.choice.generator.model.CaseParam>newMap()\n" + 
//              "            .with(\"r\", new functionalj.types.choice.generator.model.CaseParam(\"r\", new functionalj.types.Type(null, null, \"int\", java.util.Collections.emptyList()), false, null))\n" + 
//              "            .with(\"g\", new functionalj.types.choice.generator.model.CaseParam(\"g\", new functionalj.types.Type(null, null, \"int\", java.util.Collections.emptyList()), false, null))\n" + 
//              "            .with(\"b\", new functionalj.types.choice.generator.model.CaseParam(\"b\", new functionalj.types.Type(null, null, \"int\", java.util.Collections.emptyList()), false, null))\n" + 
//              "            .build();\n" + 
//              "        public static java.util.Map<String, functionalj.types.choice.generator.model.CaseParam> getCaseSchema() {\n" + 
//              "            return __schema__;\n" + 
//              "        }\n" + 
//              "        public static RGB caseFromMap(java.util.Map<String, Object> map) {\n" + 
//              "            return RGB(\n" + 
//              "                $utils.propertyFromMap(map, __schema__, \"r\"),\n" + 
//              "                $utils.propertyFromMap(map, __schema__, \"g\"),\n" + 
//              "                $utils.propertyFromMap(map, __schema__, \"b\")\n" + 
//              "            );\n" + 
//              "        }\n" + 
//              "    }\n" + 
//              "    \n" + 
//              "    public java.util.Map<java.lang.String, java.util.Map<java.lang.String, functionalj.types.choice.generator.model.CaseParam>> __getSchema() {\n" + 
//              "        return getChoiceSchema();\n" + 
//              "    }\n" + 
//              "    \n" + 
//              "    private final BasicColorFirstSwitch __switch = new BasicColorFirstSwitch(this);\n" + 
//              "    @Override public BasicColorFirstSwitch match() {\n" + 
//              "         return __switch;\n" + 
//              "    }\n" + 
//              "    \n" + 
//              "    private volatile String toString = null;\n" + 
//              "    @Override\n" + 
//              "    public String toString() {\n" + 
//              "        if (toString != null)\n" + 
//              "            return toString;\n" + 
//              "        synchronized(this) {\n" + 
//              "            if (toString != null)\n" + 
//              "                return toString;\n" + 
//              "            toString = $utils.Match(this)\n" + 
//              "                    .white(__ -> \"White\")\n" + 
//              "                    .black(__ -> \"Black\")\n" + 
//              "                    .rgb(rgb -> \"RGB(\" + String.format(\"%1$s,%2$s,%3$s\", rgb.r,rgb.g,rgb.b) + \")\")\n" + 
//              "            ;\n" + 
//              "            return toString;\n" + 
//              "        }\n" + 
//              "    }\n" + 
//              "    \n" + 
//              "    @Override\n" + 
//              "    public int hashCode() {\n" + 
//              "        return toString().hashCode();\n" + 
//              "    }\n" + 
//              "    \n" + 
//              "    @Override\n" + 
//              "    public boolean equals(Object obj) {\n" + 
//              "        if (!(obj instanceof BasicColor))\n" + 
//              "            return false;\n" + 
//              "        \n" + 
//              "        if (this == obj)\n" + 
//              "            return true;\n" + 
//              "        \n" + 
//              "        String objToString  = obj.toString();\n" + 
//              "        String thisToString = this.toString();\n" + 
//              "        return thisToString.equals(objToString);\n" + 
//              "    }\n" + 
//              "    \n" + 
//              "    \n" + 
//              "    public boolean isWhite() { return this instanceof White; }\n" + 
//              "    public Result<White> asWhite() { return Result.valueOf(this).filter(White.class).map(White.class::cast); }\n" + 
//              "    public BasicColor ifWhite(Consumer<White> action) { if (isWhite()) action.accept((White)this); return this; }\n" + 
//              "    public BasicColor ifWhite(Runnable action) { if (isWhite()) action.run(); return this; }\n" + 
//              "    public boolean isBlack() { return this instanceof Black; }\n" + 
//              "    public Result<Black> asBlack() { return Result.valueOf(this).filter(Black.class).map(Black.class::cast); }\n" + 
//              "    public BasicColor ifBlack(Consumer<Black> action) { if (isBlack()) action.accept((Black)this); return this; }\n" + 
//              "    public BasicColor ifBlack(Runnable action) { if (isBlack()) action.run(); return this; }\n" + 
//              "    public boolean isRGB() { return this instanceof RGB; }\n" + 
//              "    public Result<RGB> asRGB() { return Result.valueOf(this).filter(RGB.class).map(RGB.class::cast); }\n" + 
//              "    public BasicColor ifRGB(Consumer<RGB> action) { if (isRGB()) action.accept((RGB)this); return this; }\n" + 
//              "    public BasicColor ifRGB(Runnable action) { if (isRGB()) action.run(); return this; }\n" + 
//              "    \n" + 
//              "    public static class BasicColorFirstSwitch {\n" + 
//              "        private BasicColor $value;\n" + 
//              "        private BasicColorFirstSwitch(BasicColor theValue) { this.$value = theValue; }\n" + 
//              "        public <TARGET> BasicColorFirstSwitchTyped<TARGET> toA(Class<TARGET> clzz) {\n" + 
//              "            return new BasicColorFirstSwitchTyped<TARGET>($value);\n" + 
//              "        }\n" + 
//              "        \n" + 
//              "        public <TARGET> BasicColorSwitchBlackRGB<TARGET> white(Function<? super White, ? extends TARGET> theAction) {\n" + 
//              "            Function<BasicColor, TARGET> $action = null;\n" + 
//              "            Function<BasicColor, TARGET> oldAction = (Function<BasicColor, TARGET>)$action;\n" + 
//              "            Function<BasicColor, TARGET> newAction =\n" + 
//              "                ($action != null)\n" + 
//              "                ? oldAction : \n" + 
//              "                    ($value instanceof White)\n" + 
//              "                    ? (Function<BasicColor, TARGET>)(d -> theAction.apply((White)d))\n" + 
//              "                    : oldAction;\n" + 
//              "            \n" + 
//              "            return new BasicColorSwitchBlackRGB<TARGET>($value, newAction);\n" + 
//              "        }\n" + 
//              "        public <TARGET> BasicColorSwitchBlackRGB<TARGET> white(Supplier<? extends TARGET> theSupplier) {\n" + 
//              "            return white(d->theSupplier.get());\n" + 
//              "        }\n" + 
//              "        public <TARGET> BasicColorSwitchBlackRGB<TARGET> white(TARGET theValue) {\n" + 
//              "            return white(d->theValue);\n" + 
//              "        }\n" + 
//              "    }\n" + 
//              "    public static class BasicColorFirstSwitchTyped<TARGET> {\n" + 
//              "        private BasicColor $value;\n" + 
//              "        private BasicColorFirstSwitchTyped(BasicColor theValue) { this.$value = theValue; }\n" + 
//              "        \n" + 
//              "        public BasicColorSwitchBlackRGB<TARGET> white(Function<? super White, ? extends TARGET> theAction) {\n" + 
//              "            Function<BasicColor, TARGET> $action = null;\n" + 
//              "            Function<BasicColor, TARGET> oldAction = (Function<BasicColor, TARGET>)$action;\n" + 
//              "            Function<BasicColor, TARGET> newAction =\n" + 
//              "                ($action != null)\n" + 
//              "                ? oldAction : \n" + 
//              "                    ($value instanceof White)\n" + 
//              "                    ? (Function<BasicColor, TARGET>)(d -> theAction.apply((White)d))\n" + 
//              "                    : oldAction;\n" + 
//              "            \n" + 
//              "            return new BasicColorSwitchBlackRGB<TARGET>($value, newAction);\n" + 
//              "        }\n" + 
//              "        public BasicColorSwitchBlackRGB<TARGET> white(Supplier<? extends TARGET> theSupplier) {\n" + 
//              "            return white(d->theSupplier.get());\n" + 
//              "        }\n" + 
//              "        public BasicColorSwitchBlackRGB<TARGET> white(TARGET theValue) {\n" + 
//              "            return white(d->theValue);\n" + 
//              "        }\n" + 
//              "    }\n" + 
//              "    public static class BasicColorSwitchWhiteBlackRGB<TARGET> extends ChoiceTypeSwitch<BasicColor, TARGET> {\n" + 
//              "        private BasicColorSwitchWhiteBlackRGB(BasicColor theValue, Function<BasicColor, ? extends TARGET> theAction) { super(theValue, theAction); }\n" + 
//              "        \n" + 
//              "        public BasicColorSwitchBlackRGB<TARGET> white(Function<? super White, ? extends TARGET> theAction) {\n" + 
//              "            Function<BasicColor, TARGET> oldAction = (Function<BasicColor, TARGET>)$action;\n" + 
//              "            Function<BasicColor, TARGET> newAction =\n" + 
//              "                ($action != null)\n" + 
//              "                ? oldAction : \n" + 
//              "                    ($value instanceof White)\n" + 
//              "                    ? (Function<BasicColor, TARGET>)(d -> theAction.apply((White)d))\n" + 
//              "                    : oldAction;\n" + 
//              "            \n" + 
//              "            return new BasicColorSwitchBlackRGB<TARGET>($value, newAction);\n" + 
//              "        }\n" + 
//              "        public BasicColorSwitchBlackRGB<TARGET> white(Supplier<? extends TARGET> theSupplier) {\n" + 
//              "            return white(d->theSupplier.get());\n" + 
//              "        }\n" + 
//              "        public BasicColorSwitchBlackRGB<TARGET> white(TARGET theValue) {\n" + 
//              "            return white(d->theValue);\n" + 
//              "        }\n" + 
//              "    }\n" + 
//              "    public static class BasicColorSwitchBlackRGB<TARGET> extends ChoiceTypeSwitch<BasicColor, TARGET> {\n" + 
//              "        private BasicColorSwitchBlackRGB(BasicColor theValue, Function<BasicColor, ? extends TARGET> theAction) { super(theValue, theAction); }\n" + 
//              "        \n" + 
//              "        public BasicColorSwitchRGB<TARGET> black(Function<? super Black, ? extends TARGET> theAction) {\n" + 
//              "            Function<BasicColor, TARGET> oldAction = (Function<BasicColor, TARGET>)$action;\n" + 
//              "            Function<BasicColor, TARGET> newAction =\n" + 
//              "                ($action != null)\n" + 
//              "                ? oldAction : \n" + 
//              "                    ($value instanceof Black)\n" + 
//              "                    ? (Function<BasicColor, TARGET>)(d -> theAction.apply((Black)d))\n" + 
//              "                    : oldAction;\n" + 
//              "            \n" + 
//              "            return new BasicColorSwitchRGB<TARGET>($value, newAction);\n" + 
//              "        }\n" + 
//              "        public BasicColorSwitchRGB<TARGET> black(Supplier<? extends TARGET> theSupplier) {\n" + 
//              "            return black(d->theSupplier.get());\n" + 
//              "        }\n" + 
//              "        public BasicColorSwitchRGB<TARGET> black(TARGET theValue) {\n" + 
//              "            return black(d->theValue);\n" + 
//              "        }\n" + 
//              "    }\n" + 
//              "    public static class BasicColorSwitchRGB<TARGET> extends ChoiceTypeSwitch<BasicColor, TARGET> {\n" + 
//              "        private BasicColorSwitchRGB(BasicColor theValue, Function<BasicColor, ? extends TARGET> theAction) { super(theValue, theAction); }\n" + 
//              "        \n" + 
//              "        public TARGET rgb(Function<? super RGB, ? extends TARGET> theAction) {\n" + 
//              "            Function<BasicColor, TARGET> oldAction = (Function<BasicColor, TARGET>)$action;\n" + 
//              "            Function<BasicColor, TARGET> newAction =\n" + 
//              "                ($action != null)\n" + 
//              "                ? oldAction : \n" + 
//              "                    ($value instanceof RGB)\n" + 
//              "                    ? (Function<BasicColor, TARGET>)(d -> theAction.apply((RGB)d))\n" + 
//              "                    : oldAction;\n" + 
//              "            \n" + 
//              "            return newAction.apply($value);\n" + 
//              "        }\n" + 
//              "        public TARGET rgb(Supplier<? extends TARGET> theSupplier) {\n" + 
//              "            return rgb(d->theSupplier.get());\n" + 
//              "        }\n" + 
//              "        public TARGET rgb(TARGET theValue) {\n" + 
//              "            return rgb(d->theValue);\n" + 
//              "        }\n" + 
//              "        \n" + 
//              "        public BasicColorSwitchRGB<TARGET> rgb(Predicate<RGB> check, Function<? super RGB, ? extends TARGET> theAction) {\n" + 
//              "            Function<BasicColor, TARGET> oldAction = (Function<BasicColor, TARGET>)$action;\n" + 
//              "            Function<BasicColor, TARGET> newAction =\n" + 
//              "                ($action != null)\n" + 
//              "                ? oldAction : \n" + 
//              "                    (($value instanceof RGB) && check.test((RGB)$value))\n" + 
//              "                    ? (Function<BasicColor, TARGET>)(d -> theAction.apply((RGB)d))\n" + 
//              "                    : oldAction;\n" + 
//              "            \n" + 
//              "            return new BasicColorSwitchRGB<TARGET>($value, newAction);\n" + 
//              "        }\n" + 
//              "        public BasicColorSwitchRGB<TARGET> rgb(Predicate<RGB> check, Supplier<? extends TARGET> theSupplier) {\n" + 
//              "            return rgb(check, d->theSupplier.get());\n" + 
//              "        }\n" + 
//              "        public BasicColorSwitchRGB<TARGET> rgb(Predicate<RGB> check, TARGET theValue) {\n" + 
//              "            return rgb(check, d->theValue);\n" + 
//              "        }\n" + 
//              "    }\n" + 
//              "    \n" + 
//              "    \n" + 
//              "    \n" + 
//              "}",
//              lines);
//  }
//  
//    
//}

package functionalj.annotations.uniontype.generator;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;

import java.util.Objects;
import java.util.stream.Collectors;

import org.junit.Test;

import functionalj.annotations.uniontype.generator.model.Choice;
import functionalj.annotations.uniontype.generator.model.ChoiceParam;
import functionalj.annotations.uniontype.generator.model.Method;
import functionalj.annotations.uniontype.generator.model.Method.Kind;
import functionalj.annotations.uniontype.generator.model.MethodParam;
import functionalj.annotations.uniontype.generator.model.SourceSpec;
import functionalj.annotations.uniontype.generator.model.Type;
import lombok.val;

@SuppressWarnings("javadoc")
public class FullGeneratorTest {

//  public static interface Union1TypeSpec {
//      void White();
//      void Black();
//      void RGB(int r, int g, int b);
//      
//      static void __validateRGB(int r, int g, int b) {
//          if ((r < 0) || (r > 255)) throw new IllegalArgumentException("r: " + r);
//          if ((g < 0) || (g > 255)) throw new IllegalArgumentException("g: " + g);
//          if ((b < 0) || (b > 255)) throw new IllegalArgumentException("b: " + b);
//      }
//  }
  
  public static void main(String[] args) {
      val generator = new Generator(
              "BasicColor",
              new Type("functionalj.annotations.uniontype.generator", "UnionTypeExampleTest", "Union1TypeSpec"),
              "spec",
              asList(),
              asList(
                  new Choice("White", emptyList()),
                  new Choice("Black", emptyList()),
                  new Choice("RGB", "__validateRGB", asList(
                      new ChoiceParam("r", new Type("int")),
                      new ChoiceParam("g", new Type("int")),
                      new ChoiceParam("b", new Type("int"))
                  ))),
              asList(
                  new Method(Kind.DEFAULT, "equals", Type.BOOLEAN, 
                      asList(
                          new MethodParam("c", new Type("functionalj.annotations.uniontype.generator", "BasicColor")),
                          new MethodParam("obj", Type.OBJECT)
                      )
                  ),
                  new Method(Kind.STATIC, "toRGBString", Type.BOOLEAN, 
                      asList(new MethodParam("c", new Type("functionalj.annotations.uniontype.generator", "BasicColor"))
                  )
              )));
      generator.lines().forEach(System.out::println);
  }
  
  @Test
  public void testGenerator() {
      val spec = new SourceSpec(
              "BasicColor",
              new Type("functionalj.annotations.uniontype.generator", "UnionTypeExampleTest", "Union1TypeSpec"),
              asList(
                  new Choice("White", emptyList()),
                  new Choice("Black", emptyList()),
                  new Choice("RGB", "__validateRGB", asList(
                      new ChoiceParam("r", new Type("int")),
                      new ChoiceParam("g", new Type("int")),
                      new ChoiceParam("b", new Type("int"))
                  ))));
      val target = new TargetClass(spec);
      val lines  = target.lines().stream().filter(Objects::nonNull).collect(Collectors.joining("\n"));
      assertEquals(
              "package functionalj.annotations.uniontype.generator;\n" + 
              "\n" + 
              "import functionalj.annotations.Absent;\n" + 
              "import functionalj.annotations.uniontype.AbstractUnionType;\n" + 
              "import functionalj.annotations.uniontype.UnionTypeSwitch;\n" + 
              "import functionalj.annotations.uniontype.generator.UnionTypeExampleTest.Union1TypeSpec;\n" + 
              "import functionalj.pipeable.Pipeable;\n" + 
              "import functionalj.result.Result;\n" + 
              "import java.util.function.Consumer;\n" + 
              "import java.util.function.Function;\n" + 
              "import java.util.function.Predicate;\n" + 
              "import java.util.function.Supplier;\n" + 
              "import static functionalj.annotations.uniontype.CheckEquals.checkEquals;\n" + 
              "import static functionalj.annotations.uniontype.UnionTypes.Switch;\n" + 
              "\n" + 
              "@SuppressWarnings({\"javadoc\", \"rawtypes\", \"unchecked\"})\n" + 
              "public abstract class BasicColor extends AbstractUnionType<BasicColor.BasicColorFirstSwitch> implements Pipeable<BasicColor> {\n" + 
              "    \n" + 
              "    public static final BasicColor White() {\n" + 
              "        return White.instance;\n" + 
              "    }\n" + 
              "    public static final BasicColor Black() {\n" + 
              "        return Black.instance;\n" + 
              "    }\n" + 
              "    public static final BasicColor RGB(int r, int g, int b) {\n" + 
              "        Union1TypeSpec.__validateRGB(r, g, b);\n" + 
              "        return new RGB(r, g, b);\n" + 
              "    }\n" + 
              "    \n" + 
              "    \n" + 
              "    private BasicColor() {}\n" + 
              "    public BasicColor __data() throws Exception { return this; }\n" + 
              "    public Result<BasicColor> toResult() { return Result.of(this); }\n" + 
              "    \n" + 
              "    public static final class White extends BasicColor {\n" + 
              "        private static final White instance = new White();\n" + 
              "        private White() {}\n" + 
              "    }\n" + 
              "    public static final class Black extends BasicColor {\n" + 
              "        private static final Black instance = new Black();\n" + 
              "        private Black() {}\n" + 
              "    }\n" + 
              "    public static final class RGB extends BasicColor {\n" + 
              "        private int r;\n" + 
              "        private int g;\n" + 
              "        private int b;\n" + 
              "        private RGB(int r, int g, int b) {\n" + 
              "            this.r = r;\n" + 
              "            this.g = g;\n" + 
              "            this.b = b;\n" + 
              "        }\n" + 
              "        public int r() { return r; }\n" + 
              "        public int g() { return g; }\n" + 
              "        public int b() { return b; }\n" + 
              "        public RGB withR(int r) { return new RGB(r, g, b); }\n" + 
              "        public RGB withG(int g) { return new RGB(r, g, b); }\n" + 
              "        public RGB withB(int b) { return new RGB(r, g, b); }\n" + 
              "    }\n" + 
              "    \n" + 
              "    public final BasicColorFirstSwitch mapSwitch = new BasicColorFirstSwitch(this);\n" + 
              "    @Override public BasicColorFirstSwitch __switch() {\n" + 
              "         return mapSwitch;\n" + 
              "    }\n" + 
              "    \n" + 
              "    private volatile String toString = null;\n" + 
              "    @Override\n" + 
              "    public String toString() {\n" + 
              "        if (toString != null)\n" + 
              "            return toString;\n" + 
              "        synchronized(this) {\n" + 
              "            if (toString != null)\n" + 
              "                return toString;\n" + 
              "            toString = Switch(this)\n" + 
              "                    .white(__ -> \"White\")\n" + 
              "                    .black(__ -> \"Black\")\n" + 
              "                    .rgb(rgb -> \"RGB(\" + String.format(\"%1$s,%2$s,%3$s\", rgb.r,rgb.g,rgb.b) + \")\")\n" + 
              "            ;\n" + 
              "            return toString;\n" + 
              "        }\n" + 
              "    }\n" + 
              "    \n" + 
              "    @Override\n" + 
              "    public int hashCode() {\n" + 
              "        return toString().hashCode();\n" + 
              "    }\n" + 
              "    \n" + 
              "    @Override\n" + 
              "    public boolean equals(Object obj) {\n" + 
              "        if (!(obj instanceof BasicColor))\n" + 
              "            return false;\n" + 
              "        \n" + 
              "        if (this == obj)\n" + 
              "            return true;\n" + 
              "        \n" + 
              "        String objToString  = obj.toString();\n" + 
              "        String thisToString = this.toString();\n" + 
              "        return thisToString.equals(objToString);\n" + 
              "    }\n" + 
              "    \n" + 
              "    \n" + 
              "    public boolean isWhite() { return this instanceof White; }\n" + 
              "    public Result<White> asWhite() { return Result.of(this).filter(White.class).map(White.class::cast); }\n" + 
              "    public BasicColor ifWhite(Consumer<White> action) { if (isWhite()) action.accept((White)this); return this; }\n" + 
              "    public BasicColor ifWhite(Runnable action) { if (isWhite()) action.run(); return this; }\n" + 
              "    public boolean isBlack() { return this instanceof Black; }\n" + 
              "    public Result<Black> asBlack() { return Result.of(this).filter(Black.class).map(Black.class::cast); }\n" + 
              "    public BasicColor ifBlack(Consumer<Black> action) { if (isBlack()) action.accept((Black)this); return this; }\n" + 
              "    public BasicColor ifBlack(Runnable action) { if (isBlack()) action.run(); return this; }\n" + 
              "    public boolean isRGB() { return this instanceof RGB; }\n" + 
              "    public Result<RGB> asRGB() { return Result.of(this).filter(RGB.class).map(RGB.class::cast); }\n" + 
              "    public BasicColor ifRGB(Consumer<RGB> action) { if (isRGB()) action.accept((RGB)this); return this; }\n" + 
              "    public BasicColor ifRGB(Runnable action) { if (isRGB()) action.run(); return this; }\n" + 
              "    \n" + 
              "    public static class BasicColorFirstSwitch {\n" + 
              "        private BasicColor $value;\n" + 
              "        private BasicColorFirstSwitch(BasicColor theValue) { this.$value = theValue; }\n" + 
              "        \n" + 
              "        public <TARGET> BasicColorSwitchBlackRGB<TARGET> white(Function<? super White, TARGET> theAction) {\n" + 
              "            Function<BasicColor, TARGET> $action = null;\n" + 
              "            Function<BasicColor, TARGET> oldAction = (Function<BasicColor, TARGET>)$action;\n" + 
              "            Function<BasicColor, TARGET> newAction =\n" + 
              "                ($action != null)\n" + 
              "                ? oldAction : \n" + 
              "                    ($value instanceof White)\n" + 
              "                    ? (Function<BasicColor, TARGET>)(d -> theAction.apply((White)d))\n" + 
              "                    : oldAction;\n" + 
              "            \n" + 
              "            return new BasicColorSwitchBlackRGB<TARGET>($value, newAction);\n" + 
              "        }\n" + 
              "        public <TARGET> BasicColorSwitchBlackRGB<TARGET> white(Supplier<TARGET> theSupplier) {\n" + 
              "            return white(d->theSupplier.get());\n" + 
              "        }\n" + 
              "    }\n" + 
              "    public static class BasicColorSwitchWhiteBlackRGB<TARGET> extends UnionTypeSwitch<BasicColor, TARGET> {\n" + 
              "        private BasicColorSwitchWhiteBlackRGB(BasicColor theValue, Function<BasicColor, TARGET> theAction) { super(theValue, theAction); }\n" + 
              "        \n" + 
              "        public BasicColorSwitchBlackRGB<TARGET> white(Function<? super White, TARGET> theAction) {\n" + 
              "            Function<BasicColor, TARGET> oldAction = (Function<BasicColor, TARGET>)$action;\n" + 
              "            Function<BasicColor, TARGET> newAction =\n" + 
              "                ($action != null)\n" + 
              "                ? oldAction : \n" + 
              "                    ($value instanceof White)\n" + 
              "                    ? (Function<BasicColor, TARGET>)(d -> theAction.apply((White)d))\n" + 
              "                    : oldAction;\n" + 
              "            \n" + 
              "            return new BasicColorSwitchBlackRGB<TARGET>($value, newAction);\n" + 
              "        }\n" + 
              "        public BasicColorSwitchBlackRGB<TARGET> white(Supplier<TARGET> theSupplier) {\n" + 
              "            return white(d->theSupplier.get());\n" + 
              "        }\n" + 
              "    }\n" + 
              "    public static class BasicColorSwitchBlackRGB<TARGET> extends UnionTypeSwitch<BasicColor, TARGET> {\n" + 
              "        private BasicColorSwitchBlackRGB(BasicColor theValue, Function<BasicColor, TARGET> theAction) { super(theValue, theAction); }\n" + 
              "        \n" + 
              "        public BasicColorSwitchRGB<TARGET> black(Function<? super Black, TARGET> theAction) {\n" + 
              "            Function<BasicColor, TARGET> oldAction = (Function<BasicColor, TARGET>)$action;\n" + 
              "            Function<BasicColor, TARGET> newAction =\n" + 
              "                ($action != null)\n" + 
              "                ? oldAction : \n" + 
              "                    ($value instanceof Black)\n" + 
              "                    ? (Function<BasicColor, TARGET>)(d -> theAction.apply((Black)d))\n" + 
              "                    : oldAction;\n" + 
              "            \n" + 
              "            return new BasicColorSwitchRGB<TARGET>($value, newAction);\n" + 
              "        }\n" + 
              "        public BasicColorSwitchRGB<TARGET> black(Supplier<TARGET> theSupplier) {\n" + 
              "            return black(d->theSupplier.get());\n" + 
              "        }\n" + 
              "    }\n" + 
              "    public static class BasicColorSwitchRGB<TARGET> extends UnionTypeSwitch<BasicColor, TARGET> {\n" + 
              "        private BasicColorSwitchRGB(BasicColor theValue, Function<BasicColor, TARGET> theAction) { super(theValue, theAction); }\n" + 
              "        \n" + 
              "        public TARGET rgb(Function<? super RGB, TARGET> theAction) {\n" + 
              "            Function<BasicColor, TARGET> oldAction = (Function<BasicColor, TARGET>)$action;\n" + 
              "            Function<BasicColor, TARGET> newAction =\n" + 
              "                ($action != null)\n" + 
              "                ? oldAction : \n" + 
              "                    ($value instanceof RGB)\n" + 
              "                    ? (Function<BasicColor, TARGET>)(d -> theAction.apply((RGB)d))\n" + 
              "                    : oldAction;\n" + 
              "            \n" + 
              "            return newAction.apply($value);\n" + 
              "        }\n" + 
              "        public TARGET rgb(Supplier<TARGET> theSupplier) {\n" + 
              "            return rgb(d->theSupplier.get());\n" + 
              "        }\n" + 
              "        \n" + 
              "        public BasicColorSwitchRGB<TARGET> rgb(Predicate<RGB> check, Function<? super RGB, TARGET> theAction) {\n" + 
              "            Function<BasicColor, TARGET> oldAction = (Function<BasicColor, TARGET>)$action;\n" + 
              "            Function<BasicColor, TARGET> newAction =\n" + 
              "                ($action != null)\n" + 
              "                ? oldAction : \n" + 
              "                    (($value instanceof RGB) && check.test((RGB)$value))\n" + 
              "                    ? (Function<BasicColor, TARGET>)(d -> theAction.apply((RGB)d))\n" + 
              "                    : oldAction;\n" + 
              "            \n" + 
              "            return new BasicColorSwitchRGB<TARGET>($value, newAction);\n" + 
              "        }\n" + 
              "        public BasicColorSwitchRGB<TARGET> rgb(Predicate<RGB> check, Supplier<TARGET> theSupplier) {\n" + 
              "            return rgb(check, d->theSupplier.get());\n" + 
              "        }\n" + 
              "        \n" + 
              "        public BasicColorSwitchRGB<TARGET> rgbOf(int aR, Absent g, Absent b, Function<RGB, TARGET> theAction) {\n" + 
              "            return rgb(rgb -> checkEquals(aR, rgb.r), theAction);\n" + 
              "        }\n" + 
              "        public BasicColorSwitchRGB<TARGET> rgbOf(int aR, Absent g, Absent b, Supplier<TARGET> theSupplier) {\n" + 
              "            return rgb(rgb -> checkEquals(aR, rgb.r), theSupplier);\n" + 
              "        }\n" + 
              "        \n" + 
              "        public BasicColorSwitchRGB<TARGET> rgbOf(Predicate<Integer> rCheck, Absent g, Absent b, Function<RGB, TARGET> theAction) {\n" + 
              "            return rgb(rgb -> rCheck.test(rgb.r), theAction);\n" + 
              "        }\n" + 
              "        public BasicColorSwitchRGB<TARGET> rgbOf(Predicate<Integer> rCheck, Absent g, Absent b, Supplier<TARGET> theSupplier) {\n" + 
              "            return rgb(rgb -> rCheck.test(rgb.r), theSupplier);\n" + 
              "        }\n" + 
              "        \n" + 
              "        public BasicColorSwitchRGB<TARGET> rgbOf(Absent r, int aG, Absent b, Function<RGB, TARGET> theAction) {\n" + 
              "            return rgb(rgb -> checkEquals(aG, rgb.g), theAction);\n" + 
              "        }\n" + 
              "        public BasicColorSwitchRGB<TARGET> rgbOf(Absent r, int aG, Absent b, Supplier<TARGET> theSupplier) {\n" + 
              "            return rgb(rgb -> checkEquals(aG, rgb.g), theSupplier);\n" + 
              "        }\n" + 
              "        \n" + 
              "        public BasicColorSwitchRGB<TARGET> rgbOf(int aR, int aG, Absent b, Function<RGB, TARGET> theAction) {\n" + 
              "            return rgb(rgb -> checkEquals(aR, rgb.r) && checkEquals(aG, rgb.g), theAction);\n" + 
              "        }\n" + 
              "        public BasicColorSwitchRGB<TARGET> rgbOf(int aR, int aG, Absent b, Supplier<TARGET> theSupplier) {\n" + 
              "            return rgb(rgb -> checkEquals(aR, rgb.r) && checkEquals(aG, rgb.g), theSupplier);\n" + 
              "        }\n" + 
              "        \n" + 
              "        public BasicColorSwitchRGB<TARGET> rgbOf(Predicate<Integer> rCheck, int aG, Absent b, Function<RGB, TARGET> theAction) {\n" + 
              "            return rgb(rgb -> rCheck.test(rgb.r) && checkEquals(aG, rgb.g), theAction);\n" + 
              "        }\n" + 
              "        public BasicColorSwitchRGB<TARGET> rgbOf(Predicate<Integer> rCheck, int aG, Absent b, Supplier<TARGET> theSupplier) {\n" + 
              "            return rgb(rgb -> rCheck.test(rgb.r) && checkEquals(aG, rgb.g), theSupplier);\n" + 
              "        }\n" + 
              "        \n" + 
              "        public BasicColorSwitchRGB<TARGET> rgbOf(Absent r, Predicate<Integer> gCheck, Absent b, Function<RGB, TARGET> theAction) {\n" + 
              "            return rgb(rgb -> gCheck.test(rgb.g), theAction);\n" + 
              "        }\n" + 
              "        public BasicColorSwitchRGB<TARGET> rgbOf(Absent r, Predicate<Integer> gCheck, Absent b, Supplier<TARGET> theSupplier) {\n" + 
              "            return rgb(rgb -> gCheck.test(rgb.g), theSupplier);\n" + 
              "        }\n" + 
              "        \n" + 
              "        public BasicColorSwitchRGB<TARGET> rgbOf(int aR, Predicate<Integer> gCheck, Absent b, Function<RGB, TARGET> theAction) {\n" + 
              "            return rgb(rgb -> checkEquals(aR, rgb.r) && gCheck.test(rgb.g), theAction);\n" + 
              "        }\n" + 
              "        public BasicColorSwitchRGB<TARGET> rgbOf(int aR, Predicate<Integer> gCheck, Absent b, Supplier<TARGET> theSupplier) {\n" + 
              "            return rgb(rgb -> checkEquals(aR, rgb.r) && gCheck.test(rgb.g), theSupplier);\n" + 
              "        }\n" + 
              "        \n" + 
              "        public BasicColorSwitchRGB<TARGET> rgbOf(Predicate<Integer> rCheck, Predicate<Integer> gCheck, Absent b, Function<RGB, TARGET> theAction) {\n" + 
              "            return rgb(rgb -> rCheck.test(rgb.r) && gCheck.test(rgb.g), theAction);\n" + 
              "        }\n" + 
              "        public BasicColorSwitchRGB<TARGET> rgbOf(Predicate<Integer> rCheck, Predicate<Integer> gCheck, Absent b, Supplier<TARGET> theSupplier) {\n" + 
              "            return rgb(rgb -> rCheck.test(rgb.r) && gCheck.test(rgb.g), theSupplier);\n" + 
              "        }\n" + 
              "        \n" + 
              "        public BasicColorSwitchRGB<TARGET> rgbOf(Absent r, Absent g, int aB, Function<RGB, TARGET> theAction) {\n" + 
              "            return rgb(rgb -> checkEquals(aB, rgb.b), theAction);\n" + 
              "        }\n" + 
              "        public BasicColorSwitchRGB<TARGET> rgbOf(Absent r, Absent g, int aB, Supplier<TARGET> theSupplier) {\n" + 
              "            return rgb(rgb -> checkEquals(aB, rgb.b), theSupplier);\n" + 
              "        }\n" + 
              "        \n" + 
              "        public BasicColorSwitchRGB<TARGET> rgbOf(int aR, Absent g, int aB, Function<RGB, TARGET> theAction) {\n" + 
              "            return rgb(rgb -> checkEquals(aR, rgb.r) && checkEquals(aB, rgb.b), theAction);\n" + 
              "        }\n" + 
              "        public BasicColorSwitchRGB<TARGET> rgbOf(int aR, Absent g, int aB, Supplier<TARGET> theSupplier) {\n" + 
              "            return rgb(rgb -> checkEquals(aR, rgb.r) && checkEquals(aB, rgb.b), theSupplier);\n" + 
              "        }\n" + 
              "        \n" + 
              "        public BasicColorSwitchRGB<TARGET> rgbOf(Predicate<Integer> rCheck, Absent g, int aB, Function<RGB, TARGET> theAction) {\n" + 
              "            return rgb(rgb -> rCheck.test(rgb.r) && checkEquals(aB, rgb.b), theAction);\n" + 
              "        }\n" + 
              "        public BasicColorSwitchRGB<TARGET> rgbOf(Predicate<Integer> rCheck, Absent g, int aB, Supplier<TARGET> theSupplier) {\n" + 
              "            return rgb(rgb -> rCheck.test(rgb.r) && checkEquals(aB, rgb.b), theSupplier);\n" + 
              "        }\n" + 
              "        \n" + 
              "        public BasicColorSwitchRGB<TARGET> rgbOf(Absent r, int aG, int aB, Function<RGB, TARGET> theAction) {\n" + 
              "            return rgb(rgb -> checkEquals(aG, rgb.g) && checkEquals(aB, rgb.b), theAction);\n" + 
              "        }\n" + 
              "        public BasicColorSwitchRGB<TARGET> rgbOf(Absent r, int aG, int aB, Supplier<TARGET> theSupplier) {\n" + 
              "            return rgb(rgb -> checkEquals(aG, rgb.g) && checkEquals(aB, rgb.b), theSupplier);\n" + 
              "        }\n" + 
              "        \n" + 
              "        public BasicColorSwitchRGB<TARGET> rgbOf(int aR, int aG, int aB, Function<RGB, TARGET> theAction) {\n" + 
              "            return rgb(rgb -> checkEquals(aR, rgb.r) && checkEquals(aG, rgb.g) && checkEquals(aB, rgb.b), theAction);\n" + 
              "        }\n" + 
              "        public BasicColorSwitchRGB<TARGET> rgbOf(int aR, int aG, int aB, Supplier<TARGET> theSupplier) {\n" + 
              "            return rgb(rgb -> checkEquals(aR, rgb.r) && checkEquals(aG, rgb.g) && checkEquals(aB, rgb.b), theSupplier);\n" + 
              "        }\n" + 
              "        \n" + 
              "        public BasicColorSwitchRGB<TARGET> rgbOf(Predicate<Integer> rCheck, int aG, int aB, Function<RGB, TARGET> theAction) {\n" + 
              "            return rgb(rgb -> rCheck.test(rgb.r) && checkEquals(aG, rgb.g) && checkEquals(aB, rgb.b), theAction);\n" + 
              "        }\n" + 
              "        public BasicColorSwitchRGB<TARGET> rgbOf(Predicate<Integer> rCheck, int aG, int aB, Supplier<TARGET> theSupplier) {\n" + 
              "            return rgb(rgb -> rCheck.test(rgb.r) && checkEquals(aG, rgb.g) && checkEquals(aB, rgb.b), theSupplier);\n" + 
              "        }\n" + 
              "        \n" + 
              "        public BasicColorSwitchRGB<TARGET> rgbOf(Absent r, Predicate<Integer> gCheck, int aB, Function<RGB, TARGET> theAction) {\n" + 
              "            return rgb(rgb -> gCheck.test(rgb.g) && checkEquals(aB, rgb.b), theAction);\n" + 
              "        }\n" + 
              "        public BasicColorSwitchRGB<TARGET> rgbOf(Absent r, Predicate<Integer> gCheck, int aB, Supplier<TARGET> theSupplier) {\n" + 
              "            return rgb(rgb -> gCheck.test(rgb.g) && checkEquals(aB, rgb.b), theSupplier);\n" + 
              "        }\n" + 
              "        \n" + 
              "        public BasicColorSwitchRGB<TARGET> rgbOf(int aR, Predicate<Integer> gCheck, int aB, Function<RGB, TARGET> theAction) {\n" + 
              "            return rgb(rgb -> checkEquals(aR, rgb.r) && gCheck.test(rgb.g) && checkEquals(aB, rgb.b), theAction);\n" + 
              "        }\n" + 
              "        public BasicColorSwitchRGB<TARGET> rgbOf(int aR, Predicate<Integer> gCheck, int aB, Supplier<TARGET> theSupplier) {\n" + 
              "            return rgb(rgb -> checkEquals(aR, rgb.r) && gCheck.test(rgb.g) && checkEquals(aB, rgb.b), theSupplier);\n" + 
              "        }\n" + 
              "        \n" + 
              "        public BasicColorSwitchRGB<TARGET> rgbOf(Predicate<Integer> rCheck, Predicate<Integer> gCheck, int aB, Function<RGB, TARGET> theAction) {\n" + 
              "            return rgb(rgb -> rCheck.test(rgb.r) && gCheck.test(rgb.g) && checkEquals(aB, rgb.b), theAction);\n" + 
              "        }\n" + 
              "        public BasicColorSwitchRGB<TARGET> rgbOf(Predicate<Integer> rCheck, Predicate<Integer> gCheck, int aB, Supplier<TARGET> theSupplier) {\n" + 
              "            return rgb(rgb -> rCheck.test(rgb.r) && gCheck.test(rgb.g) && checkEquals(aB, rgb.b), theSupplier);\n" + 
              "        }\n" + 
              "        \n" + 
              "        public BasicColorSwitchRGB<TARGET> rgbOf(Absent r, Absent g, Predicate<Integer> bCheck, Function<RGB, TARGET> theAction) {\n" + 
              "            return rgb(rgb -> bCheck.test(rgb.b), theAction);\n" + 
              "        }\n" + 
              "        public BasicColorSwitchRGB<TARGET> rgbOf(Absent r, Absent g, Predicate<Integer> bCheck, Supplier<TARGET> theSupplier) {\n" + 
              "            return rgb(rgb -> bCheck.test(rgb.b), theSupplier);\n" + 
              "        }\n" + 
              "        \n" + 
              "        public BasicColorSwitchRGB<TARGET> rgbOf(int aR, Absent g, Predicate<Integer> bCheck, Function<RGB, TARGET> theAction) {\n" + 
              "            return rgb(rgb -> checkEquals(aR, rgb.r) && bCheck.test(rgb.b), theAction);\n" + 
              "        }\n" + 
              "        public BasicColorSwitchRGB<TARGET> rgbOf(int aR, Absent g, Predicate<Integer> bCheck, Supplier<TARGET> theSupplier) {\n" + 
              "            return rgb(rgb -> checkEquals(aR, rgb.r) && bCheck.test(rgb.b), theSupplier);\n" + 
              "        }\n" + 
              "        \n" + 
              "        public BasicColorSwitchRGB<TARGET> rgbOf(Predicate<Integer> rCheck, Absent g, Predicate<Integer> bCheck, Function<RGB, TARGET> theAction) {\n" + 
              "            return rgb(rgb -> rCheck.test(rgb.r) && bCheck.test(rgb.b), theAction);\n" + 
              "        }\n" + 
              "        public BasicColorSwitchRGB<TARGET> rgbOf(Predicate<Integer> rCheck, Absent g, Predicate<Integer> bCheck, Supplier<TARGET> theSupplier) {\n" + 
              "            return rgb(rgb -> rCheck.test(rgb.r) && bCheck.test(rgb.b), theSupplier);\n" + 
              "        }\n" + 
              "        \n" + 
              "        public BasicColorSwitchRGB<TARGET> rgbOf(Absent r, int aG, Predicate<Integer> bCheck, Function<RGB, TARGET> theAction) {\n" + 
              "            return rgb(rgb -> checkEquals(aG, rgb.g) && bCheck.test(rgb.b), theAction);\n" + 
              "        }\n" + 
              "        public BasicColorSwitchRGB<TARGET> rgbOf(Absent r, int aG, Predicate<Integer> bCheck, Supplier<TARGET> theSupplier) {\n" + 
              "            return rgb(rgb -> checkEquals(aG, rgb.g) && bCheck.test(rgb.b), theSupplier);\n" + 
              "        }\n" + 
              "        \n" + 
              "        public BasicColorSwitchRGB<TARGET> rgbOf(int aR, int aG, Predicate<Integer> bCheck, Function<RGB, TARGET> theAction) {\n" + 
              "            return rgb(rgb -> checkEquals(aR, rgb.r) && checkEquals(aG, rgb.g) && bCheck.test(rgb.b), theAction);\n" + 
              "        }\n" + 
              "        public BasicColorSwitchRGB<TARGET> rgbOf(int aR, int aG, Predicate<Integer> bCheck, Supplier<TARGET> theSupplier) {\n" + 
              "            return rgb(rgb -> checkEquals(aR, rgb.r) && checkEquals(aG, rgb.g) && bCheck.test(rgb.b), theSupplier);\n" + 
              "        }\n" + 
              "        \n" + 
              "        public BasicColorSwitchRGB<TARGET> rgbOf(Predicate<Integer> rCheck, int aG, Predicate<Integer> bCheck, Function<RGB, TARGET> theAction) {\n" + 
              "            return rgb(rgb -> rCheck.test(rgb.r) && checkEquals(aG, rgb.g) && bCheck.test(rgb.b), theAction);\n" + 
              "        }\n" + 
              "        public BasicColorSwitchRGB<TARGET> rgbOf(Predicate<Integer> rCheck, int aG, Predicate<Integer> bCheck, Supplier<TARGET> theSupplier) {\n" + 
              "            return rgb(rgb -> rCheck.test(rgb.r) && checkEquals(aG, rgb.g) && bCheck.test(rgb.b), theSupplier);\n" + 
              "        }\n" + 
              "        \n" + 
              "        public BasicColorSwitchRGB<TARGET> rgbOf(Absent r, Predicate<Integer> gCheck, Predicate<Integer> bCheck, Function<RGB, TARGET> theAction) {\n" + 
              "            return rgb(rgb -> gCheck.test(rgb.g) && bCheck.test(rgb.b), theAction);\n" + 
              "        }\n" + 
              "        public BasicColorSwitchRGB<TARGET> rgbOf(Absent r, Predicate<Integer> gCheck, Predicate<Integer> bCheck, Supplier<TARGET> theSupplier) {\n" + 
              "            return rgb(rgb -> gCheck.test(rgb.g) && bCheck.test(rgb.b), theSupplier);\n" + 
              "        }\n" + 
              "        \n" + 
              "        public BasicColorSwitchRGB<TARGET> rgbOf(int aR, Predicate<Integer> gCheck, Predicate<Integer> bCheck, Function<RGB, TARGET> theAction) {\n" + 
              "            return rgb(rgb -> checkEquals(aR, rgb.r) && gCheck.test(rgb.g) && bCheck.test(rgb.b), theAction);\n" + 
              "        }\n" + 
              "        public BasicColorSwitchRGB<TARGET> rgbOf(int aR, Predicate<Integer> gCheck, Predicate<Integer> bCheck, Supplier<TARGET> theSupplier) {\n" + 
              "            return rgb(rgb -> checkEquals(aR, rgb.r) && gCheck.test(rgb.g) && bCheck.test(rgb.b), theSupplier);\n" + 
              "        }\n" + 
              "        \n" + 
              "        public BasicColorSwitchRGB<TARGET> rgbOf(Predicate<Integer> rCheck, Predicate<Integer> gCheck, Predicate<Integer> bCheck, Function<RGB, TARGET> theAction) {\n" + 
              "            return rgb(rgb -> rCheck.test(rgb.r) && gCheck.test(rgb.g) && bCheck.test(rgb.b), theAction);\n" + 
              "        }\n" + 
              "        public BasicColorSwitchRGB<TARGET> rgbOf(Predicate<Integer> rCheck, Predicate<Integer> gCheck, Predicate<Integer> bCheck, Supplier<TARGET> theSupplier) {\n" + 
              "            return rgb(rgb -> rCheck.test(rgb.r) && gCheck.test(rgb.g) && bCheck.test(rgb.b), theSupplier);\n" + 
              "        }\n" + 
              "    }\n" + 
              "    \n" + 
              "    \n" + 
              "}",
              lines);
  }
  
    
}

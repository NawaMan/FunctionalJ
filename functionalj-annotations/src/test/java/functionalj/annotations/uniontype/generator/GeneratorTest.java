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
    
//    public static interface Union1TypeSpec {
//        void White();
//        void Black();
//        void RGB(int r, int g, int b);
//        
//        static void validateRGB(int r, int g, int b) {
//            if ((r < 0) || (r > 255)) throw new IllegalArgumentException("r: " + r);
//            if ((g < 0) || (g > 255)) throw new IllegalArgumentException("g: " + g);
//            if ((b < 0) || (b > 255)) throw new IllegalArgumentException("b: " + b);
//        }
//    }
    
    public static void main(String[] args) {
        val generator = new Generator("BasicColor",
                new Type("functionalj.annotations.uniontype.generator", "UnionTypeExampleTest", "Union1TypeSpec"),
                asList(
                    new Choice("White", null, null, "RGB(255,255,255)", emptyList()),
                    new Choice("Black", null, null, "RGB(0,0,0)",       emptyList()),
                    new Choice("RGB", "validateRGB", asList(
                        new ChoiceParam("r", new Type("int")),
                        new ChoiceParam("g", new Type("int")),
                        new ChoiceParam("b", new Type("int"))
                    ))));
        generator.lines().forEach(System.out::println);
    }
    
    @Test
    public void testGenerator() {
        val spec = new SourceSpec(
                "BasicColor",
                new Type("functionalj.annotations.uniontype.generator", "UnionTypeExampleTest", "Union1TypeSpec"),
                asList(
                    new Choice("White", null, null, "RGB(255,255,255)", emptyList()),
                    new Choice("Black", null, null, "RGB(0,0,0)",       emptyList()),
                    new Choice("RGB", "validateRGB", asList(
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
                "import functionalj.annotations.uniontype.IUnionType;\n" + 
                "import functionalj.annotations.uniontype.UnionTypeSwitch;\n" + 
                "import functionalj.annotations.uniontype.generator.UnionTypeExampleTest;\n" + 
                "import functionalj.pipeable.Pipeable;\n" + 
                "import functionalj.types.result.Result;\n" + 
                "import java.util.function.Consumer;\n" + 
                "import java.util.function.Function;\n" + 
                "import java.util.function.Predicate;\n" + 
                "import java.util.function.Supplier;\n" + 
                "\n" + 
                "@SuppressWarnings(\"javadoc\")\n" + 
                "public abstract class BasicColor extends IUnionType<BasicColor.BasicColorFirstSwitch> implements Pipeable<BasicColor> {\n" + 
                "    \n" + 
                "    public static final BasicColor White() { return White.instance; }\n" + 
                "    public static final BasicColor Black() { return Black.instance; }\n" + 
                "    public static final BasicColor RGB(int r, int g, int b) {\n" + 
                "        UnionTypeExampleTest.Union1TypeSpec.validateRGB(r, g, b);\n" + 
                "        return new RGB(r, g, b);\n" + 
                "    }\n" + 
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
                "    @Override public BasicColorFirstSwitch __switch() { return mapSwitch; }\n" + 
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
                "                    .white(\"White\")\n" + 
                "                    .black(\"Black\")\n" + 
                "                    .rgb(rgb -> \"RGB(\" + String.format(\"%1$s,%2$s,%3$s\", rgb.r,rgb.g,rgb.b) + \")\")\n" + 
                "            ;\n" + 
                "            return toString;\n" + 
                "        }\n" + 
                "    }\n" + 
                "    public String alternativeString() {\n" + 
                "        return Switch(this)\n" + 
                "                    .white(\"RGB(255,255,255)\")\n" + 
                "                    .black(\"RGB(0,0,0)\")\n" + 
                "                    .rgb(it -> it.toString())\n" + 
                "        ;\n" + 
                "    }\n" + 
                "    @Override\n" + 
                "    public int hashCode() {\n" + 
                "        return toString().hashCode();\n" + 
                "    }\n" + 
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
                "        if (thisToString.equals(objToString))\n" + 
                "            return true;\n" + 
                "        \n" + 
                "        String objAlternative  = ((BasicColor)obj).alternativeString();\n" + 
                "        String thisAlternative = this.alternativeString();\n" + 
                "        return thisAlternative.equals(objAlternative);\n" + 
                "    }\n" + 
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
                "        private BasicColor value;\n" + 
                "        private BasicColorFirstSwitch(BasicColor value) { this.value = value; }\n" + 
                "        \n" + 
                "        public <T> BasicColorSwitchBlackRGB<T> white(T theValue) {\n" + 
                "            return white(d->theValue);\n" + 
                "        }\n" + 
                "        public <T> BasicColorSwitchBlackRGB<T> white(Supplier<T> theSupplier) {\n" + 
                "            return white(d->theSupplier.get());\n" + 
                "        }\n" + 
                "        public <T> BasicColorSwitchBlackRGB<T> white(Function<? super White, T> theAction) {\n" + 
                "            Function<BasicColor, T> action = null;\n" + 
                "            Function<BasicColor, T> oldAction = (Function<BasicColor, T>)action;\n" + 
                "            Function<BasicColor, T> newAction =\n" + 
                "                (action != null)\n" + 
                "                ? oldAction : \n" + 
                "                    (value instanceof White)\n" + 
                "                    ? (Function<BasicColor, T>)(d -> theAction.apply((White)d))\n" + 
                "                    : oldAction;\n" + 
                "            \n" + 
                "            return new BasicColorSwitchBlackRGB <T>(value, newAction);\n" + 
                "        }\n" + 
                "    }\n" + 
                "    public static class BasicColorSwitchWhiteBlackRGB<T> extends UnionTypeSwitch<BasicColor, T> {\n" + 
                "        private BasicColorSwitchWhiteBlackRGB(BasicColor value, Function<BasicColor, T> action) { super(value, action); }\n" + 
                "        \n" + 
                "        public BasicColorSwitchBlackRGB<T> white(T theValue) {\n" + 
                "            return white(d->theValue);\n" + 
                "        }\n" + 
                "        public BasicColorSwitchBlackRGB<T> white(Supplier<T> theSupplier) {\n" + 
                "            return white(d->theSupplier.get());\n" + 
                "        }\n" + 
                "        public BasicColorSwitchBlackRGB<T> white(Function<? super White, T> theAction) {\n" + 
                "            @SuppressWarnings(\"unchecked\")\n" + 
                "            Function<BasicColor, T> oldAction = (Function<BasicColor, T>)action;\n" + 
                "            Function<BasicColor, T> newAction =\n" + 
                "                (action != null)\n" + 
                "                ? oldAction : \n" + 
                "                    (value instanceof White)\n" + 
                "                    ? (Function<BasicColor, T>)(d -> theAction.apply((White)d))\n" + 
                "                    : oldAction;\n" + 
                "            \n" + 
                "            return new BasicColorSwitchBlackRGB <T>(value, newAction);\n" + 
                "        }\n" + 
                "    }\n" + 
                "    public static class BasicColorSwitchBlackRGB<T> extends UnionTypeSwitch<BasicColor, T> {\n" + 
                "        private BasicColorSwitchBlackRGB(BasicColor value, Function<BasicColor, T> action) { super(value, action); }\n" + 
                "        \n" + 
                "        public BasicColorSwitchRGB<T> black(T theValue) {\n" + 
                "            return black(d->theValue);\n" + 
                "        }\n" + 
                "        public BasicColorSwitchRGB<T> black(Supplier<T> theSupplier) {\n" + 
                "            return black(d->theSupplier.get());\n" + 
                "        }\n" + 
                "        public BasicColorSwitchRGB<T> black(Function<? super Black, T> theAction) {\n" + 
                "            @SuppressWarnings(\"unchecked\")\n" + 
                "            Function<BasicColor, T> oldAction = (Function<BasicColor, T>)action;\n" + 
                "            Function<BasicColor, T> newAction =\n" + 
                "                (action != null)\n" + 
                "                ? oldAction : \n" + 
                "                    (value instanceof Black)\n" + 
                "                    ? (Function<BasicColor, T>)(d -> theAction.apply((Black)d))\n" + 
                "                    : oldAction;\n" + 
                "            \n" + 
                "            return new BasicColorSwitchRGB <T>(value, newAction);\n" + 
                "        }\n" + 
                "    }\n" + 
                "    public static class BasicColorSwitchRGB<T> extends UnionTypeSwitch<BasicColor, T> {\n" + 
                "        private BasicColorSwitchRGB(BasicColor value, Function<BasicColor, T> action) { super(value, action); }\n" + 
                "        \n" + 
                "        public T rgb(T theValue) {\n" + 
                "            return rgb(d->theValue);\n" + 
                "        }\n" + 
                "        public T rgb(Supplier<T> theSupplier) {\n" + 
                "            return rgb(d->theSupplier.get());\n" + 
                "        }\n" + 
                "        public T rgb(Function<? super RGB, T> theAction) {\n" + 
                "            @SuppressWarnings(\"unchecked\")\n" + 
                "            Function<BasicColor, T> oldAction = (Function<BasicColor, T>)action;\n" + 
                "            Function<BasicColor, T> newAction =\n" + 
                "                (action != null)\n" + 
                "                ? oldAction : \n" + 
                "                    (value instanceof RGB)\n" + 
                "                    ? (Function<BasicColor, T>)(d -> theAction.apply((RGB)d))\n" + 
                "                    : oldAction;\n" + 
                "            \n" + 
                "            return newAction.apply(value);\n" + 
                "        }\n" + 
                "        \n" + 
                "        public BasicColorSwitchRGB<T> rgb(Predicate<RGB> check, T theValue) {\n" + 
                "            return rgb(check, d->theValue);\n" + 
                "        }\n" + 
                "        public BasicColorSwitchRGB<T> rgb(Predicate<RGB> check, Supplier<T> theSupplier) {\n" + 
                "            return rgb(check, d->theSupplier.get());\n" + 
                "        }\n" + 
                "        public BasicColorSwitchRGB<T> rgb(Predicate<RGB> check, Function<? super RGB, T> theAction) {\n" + 
                "            @SuppressWarnings(\"unchecked\")\n" + 
                "            Function<BasicColor, T> oldAction = (Function<BasicColor, T>)action;\n" + 
                "            Function<BasicColor, T> newAction =\n" + 
                "                (action != null)\n" + 
                "                ? oldAction : \n" + 
                "                    ((value instanceof RGB) && check.test((RGB)value))\n" + 
                "                    ? (Function<BasicColor, T>)(d -> theAction.apply((RGB)d))\n" + 
                "                    : oldAction;\n" + 
                "            \n" + 
                "            return new BasicColorSwitchRGB<T>(value, newAction);\n" + 
                "        }\n" + 
                "        \n" + 
                "        public BasicColorSwitchRGB<T> rgb(int r, Absent g, Absent b, T theValue) {\n" + 
                "            return rgb(rgb -> checkEquals(r, rgb.r), theValue);\n" + 
                "        }\n" + 
                "        public BasicColorSwitchRGB<T> rgb(int r, Absent g, Absent b, Supplier<T> theSupplier) {\n" + 
                "            return rgb(rgb -> checkEquals(r, rgb.r), theSupplier);\n" + 
                "        }\n" + 
                "        public BasicColorSwitchRGB<T> rgb(int r, Absent g, Absent b, Function<RGB, T> theAction) {\n" + 
                "            return rgb(rgb -> checkEquals(r, rgb.r), theAction);\n" + 
                "        }\n" + 
                "        \n" + 
                "        public BasicColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, Absent g, Absent b, T theValue) {\n" + 
                "            return rgb(rgb -> rCheck.test(rgb.r), theValue);\n" + 
                "        }\n" + 
                "        public BasicColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, Absent g, Absent b, Supplier<T> theSupplier) {\n" + 
                "            return rgb(rgb -> rCheck.test(rgb.r), theSupplier);\n" + 
                "        }\n" + 
                "        public BasicColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, Absent g, Absent b, Function<RGB, T> theAction) {\n" + 
                "            return rgb(rgb -> rCheck.test(rgb.r), theAction);\n" + 
                "        }\n" + 
                "        \n" + 
                "        public BasicColorSwitchRGB<T> rgb(Absent r, int g, Absent b, T theValue) {\n" + 
                "            return rgb(rgb -> checkEquals(g, rgb.g), theValue);\n" + 
                "        }\n" + 
                "        public BasicColorSwitchRGB<T> rgb(Absent r, int g, Absent b, Supplier<T> theSupplier) {\n" + 
                "            return rgb(rgb -> checkEquals(g, rgb.g), theSupplier);\n" + 
                "        }\n" + 
                "        public BasicColorSwitchRGB<T> rgb(Absent r, int g, Absent b, Function<RGB, T> theAction) {\n" + 
                "            return rgb(rgb -> checkEquals(g, rgb.g), theAction);\n" + 
                "        }\n" + 
                "        \n" + 
                "        public BasicColorSwitchRGB<T> rgb(int r, int g, Absent b, T theValue) {\n" + 
                "            return rgb(rgb -> checkEquals(r, rgb.r) && checkEquals(g, rgb.g), theValue);\n" + 
                "        }\n" + 
                "        public BasicColorSwitchRGB<T> rgb(int r, int g, Absent b, Supplier<T> theSupplier) {\n" + 
                "            return rgb(rgb -> checkEquals(r, rgb.r) && checkEquals(g, rgb.g), theSupplier);\n" + 
                "        }\n" + 
                "        public BasicColorSwitchRGB<T> rgb(int r, int g, Absent b, Function<RGB, T> theAction) {\n" + 
                "            return rgb(rgb -> checkEquals(r, rgb.r) && checkEquals(g, rgb.g), theAction);\n" + 
                "        }\n" + 
                "        \n" + 
                "        public BasicColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, int g, Absent b, T theValue) {\n" + 
                "            return rgb(rgb -> rCheck.test(rgb.r) && checkEquals(g, rgb.g), theValue);\n" + 
                "        }\n" + 
                "        public BasicColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, int g, Absent b, Supplier<T> theSupplier) {\n" + 
                "            return rgb(rgb -> rCheck.test(rgb.r) && checkEquals(g, rgb.g), theSupplier);\n" + 
                "        }\n" + 
                "        public BasicColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, int g, Absent b, Function<RGB, T> theAction) {\n" + 
                "            return rgb(rgb -> rCheck.test(rgb.r) && checkEquals(g, rgb.g), theAction);\n" + 
                "        }\n" + 
                "        \n" + 
                "        public BasicColorSwitchRGB<T> rgb(Absent r, Predicate<Integer> gCheck, Absent b, T theValue) {\n" + 
                "            return rgb(rgb -> gCheck.test(rgb.g), theValue);\n" + 
                "        }\n" + 
                "        public BasicColorSwitchRGB<T> rgb(Absent r, Predicate<Integer> gCheck, Absent b, Supplier<T> theSupplier) {\n" + 
                "            return rgb(rgb -> gCheck.test(rgb.g), theSupplier);\n" + 
                "        }\n" + 
                "        public BasicColorSwitchRGB<T> rgb(Absent r, Predicate<Integer> gCheck, Absent b, Function<RGB, T> theAction) {\n" + 
                "            return rgb(rgb -> gCheck.test(rgb.g), theAction);\n" + 
                "        }\n" + 
                "        \n" + 
                "        public BasicColorSwitchRGB<T> rgb(int r, Predicate<Integer> gCheck, Absent b, T theValue) {\n" + 
                "            return rgb(rgb -> checkEquals(r, rgb.r) && gCheck.test(rgb.g), theValue);\n" + 
                "        }\n" + 
                "        public BasicColorSwitchRGB<T> rgb(int r, Predicate<Integer> gCheck, Absent b, Supplier<T> theSupplier) {\n" + 
                "            return rgb(rgb -> checkEquals(r, rgb.r) && gCheck.test(rgb.g), theSupplier);\n" + 
                "        }\n" + 
                "        public BasicColorSwitchRGB<T> rgb(int r, Predicate<Integer> gCheck, Absent b, Function<RGB, T> theAction) {\n" + 
                "            return rgb(rgb -> checkEquals(r, rgb.r) && gCheck.test(rgb.g), theAction);\n" + 
                "        }\n" + 
                "        \n" + 
                "        public BasicColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, Predicate<Integer> gCheck, Absent b, T theValue) {\n" + 
                "            return rgb(rgb -> rCheck.test(rgb.r) && gCheck.test(rgb.g), theValue);\n" + 
                "        }\n" + 
                "        public BasicColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, Predicate<Integer> gCheck, Absent b, Supplier<T> theSupplier) {\n" + 
                "            return rgb(rgb -> rCheck.test(rgb.r) && gCheck.test(rgb.g), theSupplier);\n" + 
                "        }\n" + 
                "        public BasicColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, Predicate<Integer> gCheck, Absent b, Function<RGB, T> theAction) {\n" + 
                "            return rgb(rgb -> rCheck.test(rgb.r) && gCheck.test(rgb.g), theAction);\n" + 
                "        }\n" + 
                "        \n" + 
                "        public BasicColorSwitchRGB<T> rgb(Absent r, Absent g, int b, T theValue) {\n" + 
                "            return rgb(rgb -> checkEquals(b, rgb.b), theValue);\n" + 
                "        }\n" + 
                "        public BasicColorSwitchRGB<T> rgb(Absent r, Absent g, int b, Supplier<T> theSupplier) {\n" + 
                "            return rgb(rgb -> checkEquals(b, rgb.b), theSupplier);\n" + 
                "        }\n" + 
                "        public BasicColorSwitchRGB<T> rgb(Absent r, Absent g, int b, Function<RGB, T> theAction) {\n" + 
                "            return rgb(rgb -> checkEquals(b, rgb.b), theAction);\n" + 
                "        }\n" + 
                "        \n" + 
                "        public BasicColorSwitchRGB<T> rgb(int r, Absent g, int b, T theValue) {\n" + 
                "            return rgb(rgb -> checkEquals(r, rgb.r) && checkEquals(b, rgb.b), theValue);\n" + 
                "        }\n" + 
                "        public BasicColorSwitchRGB<T> rgb(int r, Absent g, int b, Supplier<T> theSupplier) {\n" + 
                "            return rgb(rgb -> checkEquals(r, rgb.r) && checkEquals(b, rgb.b), theSupplier);\n" + 
                "        }\n" + 
                "        public BasicColorSwitchRGB<T> rgb(int r, Absent g, int b, Function<RGB, T> theAction) {\n" + 
                "            return rgb(rgb -> checkEquals(r, rgb.r) && checkEquals(b, rgb.b), theAction);\n" + 
                "        }\n" + 
                "        \n" + 
                "        public BasicColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, Absent g, int b, T theValue) {\n" + 
                "            return rgb(rgb -> rCheck.test(rgb.r) && checkEquals(b, rgb.b), theValue);\n" + 
                "        }\n" + 
                "        public BasicColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, Absent g, int b, Supplier<T> theSupplier) {\n" + 
                "            return rgb(rgb -> rCheck.test(rgb.r) && checkEquals(b, rgb.b), theSupplier);\n" + 
                "        }\n" + 
                "        public BasicColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, Absent g, int b, Function<RGB, T> theAction) {\n" + 
                "            return rgb(rgb -> rCheck.test(rgb.r) && checkEquals(b, rgb.b), theAction);\n" + 
                "        }\n" + 
                "        \n" + 
                "        public BasicColorSwitchRGB<T> rgb(Absent r, int g, int b, T theValue) {\n" + 
                "            return rgb(rgb -> checkEquals(g, rgb.g) && checkEquals(b, rgb.b), theValue);\n" + 
                "        }\n" + 
                "        public BasicColorSwitchRGB<T> rgb(Absent r, int g, int b, Supplier<T> theSupplier) {\n" + 
                "            return rgb(rgb -> checkEquals(g, rgb.g) && checkEquals(b, rgb.b), theSupplier);\n" + 
                "        }\n" + 
                "        public BasicColorSwitchRGB<T> rgb(Absent r, int g, int b, Function<RGB, T> theAction) {\n" + 
                "            return rgb(rgb -> checkEquals(g, rgb.g) && checkEquals(b, rgb.b), theAction);\n" + 
                "        }\n" + 
                "        \n" + 
                "        public BasicColorSwitchRGB<T> rgb(int r, int g, int b, T theValue) {\n" + 
                "            return rgb(rgb -> checkEquals(r, rgb.r) && checkEquals(g, rgb.g) && checkEquals(b, rgb.b), theValue);\n" + 
                "        }\n" + 
                "        public BasicColorSwitchRGB<T> rgb(int r, int g, int b, Supplier<T> theSupplier) {\n" + 
                "            return rgb(rgb -> checkEquals(r, rgb.r) && checkEquals(g, rgb.g) && checkEquals(b, rgb.b), theSupplier);\n" + 
                "        }\n" + 
                "        public BasicColorSwitchRGB<T> rgb(int r, int g, int b, Function<RGB, T> theAction) {\n" + 
                "            return rgb(rgb -> checkEquals(r, rgb.r) && checkEquals(g, rgb.g) && checkEquals(b, rgb.b), theAction);\n" + 
                "        }\n" + 
                "        \n" + 
                "        public BasicColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, int g, int b, T theValue) {\n" + 
                "            return rgb(rgb -> rCheck.test(rgb.r) && checkEquals(g, rgb.g) && checkEquals(b, rgb.b), theValue);\n" + 
                "        }\n" + 
                "        public BasicColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, int g, int b, Supplier<T> theSupplier) {\n" + 
                "            return rgb(rgb -> rCheck.test(rgb.r) && checkEquals(g, rgb.g) && checkEquals(b, rgb.b), theSupplier);\n" + 
                "        }\n" + 
                "        public BasicColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, int g, int b, Function<RGB, T> theAction) {\n" + 
                "            return rgb(rgb -> rCheck.test(rgb.r) && checkEquals(g, rgb.g) && checkEquals(b, rgb.b), theAction);\n" + 
                "        }\n" + 
                "        \n" + 
                "        public BasicColorSwitchRGB<T> rgb(Absent r, Predicate<Integer> gCheck, int b, T theValue) {\n" + 
                "            return rgb(rgb -> gCheck.test(rgb.g) && checkEquals(b, rgb.b), theValue);\n" + 
                "        }\n" + 
                "        public BasicColorSwitchRGB<T> rgb(Absent r, Predicate<Integer> gCheck, int b, Supplier<T> theSupplier) {\n" + 
                "            return rgb(rgb -> gCheck.test(rgb.g) && checkEquals(b, rgb.b), theSupplier);\n" + 
                "        }\n" + 
                "        public BasicColorSwitchRGB<T> rgb(Absent r, Predicate<Integer> gCheck, int b, Function<RGB, T> theAction) {\n" + 
                "            return rgb(rgb -> gCheck.test(rgb.g) && checkEquals(b, rgb.b), theAction);\n" + 
                "        }\n" + 
                "        \n" + 
                "        public BasicColorSwitchRGB<T> rgb(int r, Predicate<Integer> gCheck, int b, T theValue) {\n" + 
                "            return rgb(rgb -> checkEquals(r, rgb.r) && gCheck.test(rgb.g) && checkEquals(b, rgb.b), theValue);\n" + 
                "        }\n" + 
                "        public BasicColorSwitchRGB<T> rgb(int r, Predicate<Integer> gCheck, int b, Supplier<T> theSupplier) {\n" + 
                "            return rgb(rgb -> checkEquals(r, rgb.r) && gCheck.test(rgb.g) && checkEquals(b, rgb.b), theSupplier);\n" + 
                "        }\n" + 
                "        public BasicColorSwitchRGB<T> rgb(int r, Predicate<Integer> gCheck, int b, Function<RGB, T> theAction) {\n" + 
                "            return rgb(rgb -> checkEquals(r, rgb.r) && gCheck.test(rgb.g) && checkEquals(b, rgb.b), theAction);\n" + 
                "        }\n" + 
                "        \n" + 
                "        public BasicColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, Predicate<Integer> gCheck, int b, T theValue) {\n" + 
                "            return rgb(rgb -> rCheck.test(rgb.r) && gCheck.test(rgb.g) && checkEquals(b, rgb.b), theValue);\n" + 
                "        }\n" + 
                "        public BasicColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, Predicate<Integer> gCheck, int b, Supplier<T> theSupplier) {\n" + 
                "            return rgb(rgb -> rCheck.test(rgb.r) && gCheck.test(rgb.g) && checkEquals(b, rgb.b), theSupplier);\n" + 
                "        }\n" + 
                "        public BasicColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, Predicate<Integer> gCheck, int b, Function<RGB, T> theAction) {\n" + 
                "            return rgb(rgb -> rCheck.test(rgb.r) && gCheck.test(rgb.g) && checkEquals(b, rgb.b), theAction);\n" + 
                "        }\n" + 
                "        \n" + 
                "        public BasicColorSwitchRGB<T> rgb(Absent r, Absent g, Predicate<Integer> bCheck, T theValue) {\n" + 
                "            return rgb(rgb -> bCheck.test(rgb.b), theValue);\n" + 
                "        }\n" + 
                "        public BasicColorSwitchRGB<T> rgb(Absent r, Absent g, Predicate<Integer> bCheck, Supplier<T> theSupplier) {\n" + 
                "            return rgb(rgb -> bCheck.test(rgb.b), theSupplier);\n" + 
                "        }\n" + 
                "        public BasicColorSwitchRGB<T> rgb(Absent r, Absent g, Predicate<Integer> bCheck, Function<RGB, T> theAction) {\n" + 
                "            return rgb(rgb -> bCheck.test(rgb.b), theAction);\n" + 
                "        }\n" + 
                "        \n" + 
                "        public BasicColorSwitchRGB<T> rgb(int r, Absent g, Predicate<Integer> bCheck, T theValue) {\n" + 
                "            return rgb(rgb -> checkEquals(r, rgb.r) && bCheck.test(rgb.b), theValue);\n" + 
                "        }\n" + 
                "        public BasicColorSwitchRGB<T> rgb(int r, Absent g, Predicate<Integer> bCheck, Supplier<T> theSupplier) {\n" + 
                "            return rgb(rgb -> checkEquals(r, rgb.r) && bCheck.test(rgb.b), theSupplier);\n" + 
                "        }\n" + 
                "        public BasicColorSwitchRGB<T> rgb(int r, Absent g, Predicate<Integer> bCheck, Function<RGB, T> theAction) {\n" + 
                "            return rgb(rgb -> checkEquals(r, rgb.r) && bCheck.test(rgb.b), theAction);\n" + 
                "        }\n" + 
                "        \n" + 
                "        public BasicColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, Absent g, Predicate<Integer> bCheck, T theValue) {\n" + 
                "            return rgb(rgb -> rCheck.test(rgb.r) && bCheck.test(rgb.b), theValue);\n" + 
                "        }\n" + 
                "        public BasicColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, Absent g, Predicate<Integer> bCheck, Supplier<T> theSupplier) {\n" + 
                "            return rgb(rgb -> rCheck.test(rgb.r) && bCheck.test(rgb.b), theSupplier);\n" + 
                "        }\n" + 
                "        public BasicColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, Absent g, Predicate<Integer> bCheck, Function<RGB, T> theAction) {\n" + 
                "            return rgb(rgb -> rCheck.test(rgb.r) && bCheck.test(rgb.b), theAction);\n" + 
                "        }\n" + 
                "        \n" + 
                "        public BasicColorSwitchRGB<T> rgb(Absent r, int g, Predicate<Integer> bCheck, T theValue) {\n" + 
                "            return rgb(rgb -> checkEquals(g, rgb.g) && bCheck.test(rgb.b), theValue);\n" + 
                "        }\n" + 
                "        public BasicColorSwitchRGB<T> rgb(Absent r, int g, Predicate<Integer> bCheck, Supplier<T> theSupplier) {\n" + 
                "            return rgb(rgb -> checkEquals(g, rgb.g) && bCheck.test(rgb.b), theSupplier);\n" + 
                "        }\n" + 
                "        public BasicColorSwitchRGB<T> rgb(Absent r, int g, Predicate<Integer> bCheck, Function<RGB, T> theAction) {\n" + 
                "            return rgb(rgb -> checkEquals(g, rgb.g) && bCheck.test(rgb.b), theAction);\n" + 
                "        }\n" + 
                "        \n" + 
                "        public BasicColorSwitchRGB<T> rgb(int r, int g, Predicate<Integer> bCheck, T theValue) {\n" + 
                "            return rgb(rgb -> checkEquals(r, rgb.r) && checkEquals(g, rgb.g) && bCheck.test(rgb.b), theValue);\n" + 
                "        }\n" + 
                "        public BasicColorSwitchRGB<T> rgb(int r, int g, Predicate<Integer> bCheck, Supplier<T> theSupplier) {\n" + 
                "            return rgb(rgb -> checkEquals(r, rgb.r) && checkEquals(g, rgb.g) && bCheck.test(rgb.b), theSupplier);\n" + 
                "        }\n" + 
                "        public BasicColorSwitchRGB<T> rgb(int r, int g, Predicate<Integer> bCheck, Function<RGB, T> theAction) {\n" + 
                "            return rgb(rgb -> checkEquals(r, rgb.r) && checkEquals(g, rgb.g) && bCheck.test(rgb.b), theAction);\n" + 
                "        }\n" + 
                "        \n" + 
                "        public BasicColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, int g, Predicate<Integer> bCheck, T theValue) {\n" + 
                "            return rgb(rgb -> rCheck.test(rgb.r) && checkEquals(g, rgb.g) && bCheck.test(rgb.b), theValue);\n" + 
                "        }\n" + 
                "        public BasicColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, int g, Predicate<Integer> bCheck, Supplier<T> theSupplier) {\n" + 
                "            return rgb(rgb -> rCheck.test(rgb.r) && checkEquals(g, rgb.g) && bCheck.test(rgb.b), theSupplier);\n" + 
                "        }\n" + 
                "        public BasicColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, int g, Predicate<Integer> bCheck, Function<RGB, T> theAction) {\n" + 
                "            return rgb(rgb -> rCheck.test(rgb.r) && checkEquals(g, rgb.g) && bCheck.test(rgb.b), theAction);\n" + 
                "        }\n" + 
                "        \n" + 
                "        public BasicColorSwitchRGB<T> rgb(Absent r, Predicate<Integer> gCheck, Predicate<Integer> bCheck, T theValue) {\n" + 
                "            return rgb(rgb -> gCheck.test(rgb.g) && bCheck.test(rgb.b), theValue);\n" + 
                "        }\n" + 
                "        public BasicColorSwitchRGB<T> rgb(Absent r, Predicate<Integer> gCheck, Predicate<Integer> bCheck, Supplier<T> theSupplier) {\n" + 
                "            return rgb(rgb -> gCheck.test(rgb.g) && bCheck.test(rgb.b), theSupplier);\n" + 
                "        }\n" + 
                "        public BasicColorSwitchRGB<T> rgb(Absent r, Predicate<Integer> gCheck, Predicate<Integer> bCheck, Function<RGB, T> theAction) {\n" + 
                "            return rgb(rgb -> gCheck.test(rgb.g) && bCheck.test(rgb.b), theAction);\n" + 
                "        }\n" + 
                "        \n" + 
                "        public BasicColorSwitchRGB<T> rgb(int r, Predicate<Integer> gCheck, Predicate<Integer> bCheck, T theValue) {\n" + 
                "            return rgb(rgb -> checkEquals(r, rgb.r) && gCheck.test(rgb.g) && bCheck.test(rgb.b), theValue);\n" + 
                "        }\n" + 
                "        public BasicColorSwitchRGB<T> rgb(int r, Predicate<Integer> gCheck, Predicate<Integer> bCheck, Supplier<T> theSupplier) {\n" + 
                "            return rgb(rgb -> checkEquals(r, rgb.r) && gCheck.test(rgb.g) && bCheck.test(rgb.b), theSupplier);\n" + 
                "        }\n" + 
                "        public BasicColorSwitchRGB<T> rgb(int r, Predicate<Integer> gCheck, Predicate<Integer> bCheck, Function<RGB, T> theAction) {\n" + 
                "            return rgb(rgb -> checkEquals(r, rgb.r) && gCheck.test(rgb.g) && bCheck.test(rgb.b), theAction);\n" + 
                "        }\n" + 
                "        \n" + 
                "        public BasicColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, Predicate<Integer> gCheck, Predicate<Integer> bCheck, T theValue) {\n" + 
                "            return rgb(rgb -> rCheck.test(rgb.r) && gCheck.test(rgb.g) && bCheck.test(rgb.b), theValue);\n" + 
                "        }\n" + 
                "        public BasicColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, Predicate<Integer> gCheck, Predicate<Integer> bCheck, Supplier<T> theSupplier) {\n" + 
                "            return rgb(rgb -> rCheck.test(rgb.r) && gCheck.test(rgb.g) && bCheck.test(rgb.b), theSupplier);\n" + 
                "        }\n" + 
                "        public BasicColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, Predicate<Integer> gCheck, Predicate<Integer> bCheck, Function<RGB, T> theAction) {\n" + 
                "            return rgb(rgb -> rCheck.test(rgb.r) && gCheck.test(rgb.g) && bCheck.test(rgb.b), theAction);\n" + 
                "        }\n" + 
                "    }\n" + 
                "    \n" + 
                "}",
                lines);
    }
    
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
        assertEquals("public static final Color White() { return White.instance; }", lines);
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
                "public static class ColorSwitchWhiteBlackRGB<T> extends UnionTypeSwitch<Color, T> {\n" + 
                "    private ColorSwitchWhiteBlackRGB(Color value, Function<Color, T> action) { super(value, action); }\n" + 
                "    \n" + 
                "    public ColorSwitchBlackRGB<T> white(T theValue) {\n" + 
                "        return white(d->theValue);\n" + 
                "    }\n" + 
                "    public ColorSwitchBlackRGB<T> white(Supplier<T> theSupplier) {\n" + 
                "        return white(d->theSupplier.get());\n" + 
                "    }\n" + 
                "    public ColorSwitchBlackRGB<T> white(Function<? super White, T> theAction) {\n" + 
                "        @SuppressWarnings(\"unchecked\")\n" + 
                "        Function<Color, T> oldAction = (Function<Color, T>)action;\n" + 
                "        Function<Color, T> newAction =\n" + 
                "            (action != null)\n" + 
                "            ? oldAction : \n" + 
                "                (value instanceof White)\n" + 
                "                ? (Function<Color, T>)(d -> theAction.apply((White)d))\n" + 
                "                : oldAction;\n" + 
                "        \n" + 
                "        return new ColorSwitchBlackRGB <T>(value, newAction);\n" + 
                "    }\n" + 
                "}", lines);
    }
    
    @Test
    public void testSwitchClass_simple_last() {
        val target = new TargetClass(new SourceSpec("Color", new Type("p1.p2", "ColorSpec"), emptyList()));
        val sub    = new SwitchClass(target, false, asList(new Choice("White")));
        val lines  = sub.lines().stream().filter(Objects::nonNull).collect(Collectors.joining("\n"));
        assertEquals(
                "public static class ColorSwitchWhite<T> extends UnionTypeSwitch<Color, T> {\n" + 
                "    private ColorSwitchWhite(Color value, Function<Color, T> action) { super(value, action); }\n" + 
                "    \n" + 
                "    public T white(T theValue) {\n" + 
                "        return white(d->theValue);\n" + 
                "    }\n" + 
                "    public T white(Supplier<T> theSupplier) {\n" + 
                "        return white(d->theSupplier.get());\n" + 
                "    }\n" + 
                "    public T white(Function<? super White, T> theAction) {\n" + 
                "        @SuppressWarnings(\"unchecked\")\n" + 
                "        Function<Color, T> oldAction = (Function<Color, T>)action;\n" + 
                "        Function<Color, T> newAction =\n" + 
                "            (action != null)\n" + 
                "            ? oldAction : \n" + 
                "                (value instanceof White)\n" + 
                "                ? (Function<Color, T>)(d -> theAction.apply((White)d))\n" + 
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
                        new Choice("White", null, null, "RGB(255,255,255)", emptyList()),
                        new Choice("Black", null, null, "RGB(0,0,0)",       emptyList()),
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
                        new Choice("White", null, null, "RGB(255,255,255)", emptyList()),
                        new Choice("Black", null, null, "RGB(0,0,0)",       emptyList()),
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
                "public String alternativeString() {\n" + 
                "    return Switch(this)\n" + 
                "                .white(\"RGB(255,255,255)\")\n" + 
                "                .black(\"RGB(0,0,0)\")\n" + 
                "                .rgb(it -> it.toString())\n" + 
                "    ;\n" + 
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
                "public static class ColorSwitchRGB<T> extends UnionTypeSwitch<Color, T> {\n" + 
                "    private ColorSwitchRGB(Color value, Function<Color, T> action) { super(value, action); }\n" + 
                "    \n" + 
                "    public T rgb(T theValue) {\n" + 
                "        return rgb(d->theValue);\n" + 
                "    }\n" + 
                "    public T rgb(Supplier<T> theSupplier) {\n" + 
                "        return rgb(d->theSupplier.get());\n" + 
                "    }\n" + 
                "    public T rgb(Function<? super RGB, T> theAction) {\n" + 
                "        @SuppressWarnings(\"unchecked\")\n" + 
                "        Function<Color, T> oldAction = (Function<Color, T>)action;\n" + 
                "        Function<Color, T> newAction =\n" + 
                "            (action != null)\n" + 
                "            ? oldAction : \n" + 
                "                (value instanceof RGB)\n" + 
                "                ? (Function<Color, T>)(d -> theAction.apply((RGB)d))\n" + 
                "                : oldAction;\n" + 
                "        \n" + 
                "        return newAction.apply(value);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<T> rgb(Predicate<RGB> check, T theValue) {\n" + 
                "        return rgb(check, d->theValue);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<T> rgb(Predicate<RGB> check, Supplier<T> theSupplier) {\n" + 
                "        return rgb(check, d->theSupplier.get());\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<T> rgb(Predicate<RGB> check, Function<? super RGB, T> theAction) {\n" + 
                "        @SuppressWarnings(\"unchecked\")\n" + 
                "        Function<Color, T> oldAction = (Function<Color, T>)action;\n" + 
                "        Function<Color, T> newAction =\n" + 
                "            (action != null)\n" + 
                "            ? oldAction : \n" + 
                "                ((value instanceof RGB) && check.test((RGB)value))\n" + 
                "                ? (Function<Color, T>)(d -> theAction.apply((RGB)d))\n" + 
                "                : oldAction;\n" + 
                "        \n" + 
                "        return new ColorSwitchRGB<T>(value, newAction);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<T> rgb(int r, Absent g, Absent b, T theValue) {\n" + 
                "        return rgb(rgb -> checkEquals(r, rgb.r), theValue);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<T> rgb(int r, Absent g, Absent b, Supplier<T> theSupplier) {\n" + 
                "        return rgb(rgb -> checkEquals(r, rgb.r), theSupplier);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<T> rgb(int r, Absent g, Absent b, Function<RGB, T> theAction) {\n" + 
                "        return rgb(rgb -> checkEquals(r, rgb.r), theAction);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, Absent g, Absent b, T theValue) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r), theValue);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, Absent g, Absent b, Supplier<T> theSupplier) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r), theSupplier);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, Absent g, Absent b, Function<RGB, T> theAction) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r), theAction);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<T> rgb(Absent r, int g, Absent b, T theValue) {\n" + 
                "        return rgb(rgb -> checkEquals(g, rgb.g), theValue);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<T> rgb(Absent r, int g, Absent b, Supplier<T> theSupplier) {\n" + 
                "        return rgb(rgb -> checkEquals(g, rgb.g), theSupplier);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<T> rgb(Absent r, int g, Absent b, Function<RGB, T> theAction) {\n" + 
                "        return rgb(rgb -> checkEquals(g, rgb.g), theAction);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<T> rgb(int r, int g, Absent b, T theValue) {\n" + 
                "        return rgb(rgb -> checkEquals(r, rgb.r) && checkEquals(g, rgb.g), theValue);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<T> rgb(int r, int g, Absent b, Supplier<T> theSupplier) {\n" + 
                "        return rgb(rgb -> checkEquals(r, rgb.r) && checkEquals(g, rgb.g), theSupplier);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<T> rgb(int r, int g, Absent b, Function<RGB, T> theAction) {\n" + 
                "        return rgb(rgb -> checkEquals(r, rgb.r) && checkEquals(g, rgb.g), theAction);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, int g, Absent b, T theValue) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r) && checkEquals(g, rgb.g), theValue);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, int g, Absent b, Supplier<T> theSupplier) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r) && checkEquals(g, rgb.g), theSupplier);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, int g, Absent b, Function<RGB, T> theAction) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r) && checkEquals(g, rgb.g), theAction);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<T> rgb(Absent r, Predicate<Integer> gCheck, Absent b, T theValue) {\n" + 
                "        return rgb(rgb -> gCheck.test(rgb.g), theValue);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<T> rgb(Absent r, Predicate<Integer> gCheck, Absent b, Supplier<T> theSupplier) {\n" + 
                "        return rgb(rgb -> gCheck.test(rgb.g), theSupplier);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<T> rgb(Absent r, Predicate<Integer> gCheck, Absent b, Function<RGB, T> theAction) {\n" + 
                "        return rgb(rgb -> gCheck.test(rgb.g), theAction);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<T> rgb(int r, Predicate<Integer> gCheck, Absent b, T theValue) {\n" + 
                "        return rgb(rgb -> checkEquals(r, rgb.r) && gCheck.test(rgb.g), theValue);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<T> rgb(int r, Predicate<Integer> gCheck, Absent b, Supplier<T> theSupplier) {\n" + 
                "        return rgb(rgb -> checkEquals(r, rgb.r) && gCheck.test(rgb.g), theSupplier);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<T> rgb(int r, Predicate<Integer> gCheck, Absent b, Function<RGB, T> theAction) {\n" + 
                "        return rgb(rgb -> checkEquals(r, rgb.r) && gCheck.test(rgb.g), theAction);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, Predicate<Integer> gCheck, Absent b, T theValue) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r) && gCheck.test(rgb.g), theValue);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, Predicate<Integer> gCheck, Absent b, Supplier<T> theSupplier) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r) && gCheck.test(rgb.g), theSupplier);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, Predicate<Integer> gCheck, Absent b, Function<RGB, T> theAction) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r) && gCheck.test(rgb.g), theAction);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<T> rgb(Absent r, Absent g, int b, T theValue) {\n" + 
                "        return rgb(rgb -> checkEquals(b, rgb.b), theValue);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<T> rgb(Absent r, Absent g, int b, Supplier<T> theSupplier) {\n" + 
                "        return rgb(rgb -> checkEquals(b, rgb.b), theSupplier);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<T> rgb(Absent r, Absent g, int b, Function<RGB, T> theAction) {\n" + 
                "        return rgb(rgb -> checkEquals(b, rgb.b), theAction);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<T> rgb(int r, Absent g, int b, T theValue) {\n" + 
                "        return rgb(rgb -> checkEquals(r, rgb.r) && checkEquals(b, rgb.b), theValue);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<T> rgb(int r, Absent g, int b, Supplier<T> theSupplier) {\n" + 
                "        return rgb(rgb -> checkEquals(r, rgb.r) && checkEquals(b, rgb.b), theSupplier);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<T> rgb(int r, Absent g, int b, Function<RGB, T> theAction) {\n" + 
                "        return rgb(rgb -> checkEquals(r, rgb.r) && checkEquals(b, rgb.b), theAction);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, Absent g, int b, T theValue) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r) && checkEquals(b, rgb.b), theValue);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, Absent g, int b, Supplier<T> theSupplier) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r) && checkEquals(b, rgb.b), theSupplier);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, Absent g, int b, Function<RGB, T> theAction) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r) && checkEquals(b, rgb.b), theAction);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<T> rgb(Absent r, int g, int b, T theValue) {\n" + 
                "        return rgb(rgb -> checkEquals(g, rgb.g) && checkEquals(b, rgb.b), theValue);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<T> rgb(Absent r, int g, int b, Supplier<T> theSupplier) {\n" + 
                "        return rgb(rgb -> checkEquals(g, rgb.g) && checkEquals(b, rgb.b), theSupplier);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<T> rgb(Absent r, int g, int b, Function<RGB, T> theAction) {\n" + 
                "        return rgb(rgb -> checkEquals(g, rgb.g) && checkEquals(b, rgb.b), theAction);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<T> rgb(int r, int g, int b, T theValue) {\n" + 
                "        return rgb(rgb -> checkEquals(r, rgb.r) && checkEquals(g, rgb.g) && checkEquals(b, rgb.b), theValue);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<T> rgb(int r, int g, int b, Supplier<T> theSupplier) {\n" + 
                "        return rgb(rgb -> checkEquals(r, rgb.r) && checkEquals(g, rgb.g) && checkEquals(b, rgb.b), theSupplier);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<T> rgb(int r, int g, int b, Function<RGB, T> theAction) {\n" + 
                "        return rgb(rgb -> checkEquals(r, rgb.r) && checkEquals(g, rgb.g) && checkEquals(b, rgb.b), theAction);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, int g, int b, T theValue) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r) && checkEquals(g, rgb.g) && checkEquals(b, rgb.b), theValue);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, int g, int b, Supplier<T> theSupplier) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r) && checkEquals(g, rgb.g) && checkEquals(b, rgb.b), theSupplier);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, int g, int b, Function<RGB, T> theAction) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r) && checkEquals(g, rgb.g) && checkEquals(b, rgb.b), theAction);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<T> rgb(Absent r, Predicate<Integer> gCheck, int b, T theValue) {\n" + 
                "        return rgb(rgb -> gCheck.test(rgb.g) && checkEquals(b, rgb.b), theValue);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<T> rgb(Absent r, Predicate<Integer> gCheck, int b, Supplier<T> theSupplier) {\n" + 
                "        return rgb(rgb -> gCheck.test(rgb.g) && checkEquals(b, rgb.b), theSupplier);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<T> rgb(Absent r, Predicate<Integer> gCheck, int b, Function<RGB, T> theAction) {\n" + 
                "        return rgb(rgb -> gCheck.test(rgb.g) && checkEquals(b, rgb.b), theAction);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<T> rgb(int r, Predicate<Integer> gCheck, int b, T theValue) {\n" + 
                "        return rgb(rgb -> checkEquals(r, rgb.r) && gCheck.test(rgb.g) && checkEquals(b, rgb.b), theValue);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<T> rgb(int r, Predicate<Integer> gCheck, int b, Supplier<T> theSupplier) {\n" + 
                "        return rgb(rgb -> checkEquals(r, rgb.r) && gCheck.test(rgb.g) && checkEquals(b, rgb.b), theSupplier);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<T> rgb(int r, Predicate<Integer> gCheck, int b, Function<RGB, T> theAction) {\n" + 
                "        return rgb(rgb -> checkEquals(r, rgb.r) && gCheck.test(rgb.g) && checkEquals(b, rgb.b), theAction);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, Predicate<Integer> gCheck, int b, T theValue) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r) && gCheck.test(rgb.g) && checkEquals(b, rgb.b), theValue);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, Predicate<Integer> gCheck, int b, Supplier<T> theSupplier) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r) && gCheck.test(rgb.g) && checkEquals(b, rgb.b), theSupplier);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, Predicate<Integer> gCheck, int b, Function<RGB, T> theAction) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r) && gCheck.test(rgb.g) && checkEquals(b, rgb.b), theAction);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<T> rgb(Absent r, Absent g, Predicate<Integer> bCheck, T theValue) {\n" + 
                "        return rgb(rgb -> bCheck.test(rgb.b), theValue);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<T> rgb(Absent r, Absent g, Predicate<Integer> bCheck, Supplier<T> theSupplier) {\n" + 
                "        return rgb(rgb -> bCheck.test(rgb.b), theSupplier);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<T> rgb(Absent r, Absent g, Predicate<Integer> bCheck, Function<RGB, T> theAction) {\n" + 
                "        return rgb(rgb -> bCheck.test(rgb.b), theAction);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<T> rgb(int r, Absent g, Predicate<Integer> bCheck, T theValue) {\n" + 
                "        return rgb(rgb -> checkEquals(r, rgb.r) && bCheck.test(rgb.b), theValue);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<T> rgb(int r, Absent g, Predicate<Integer> bCheck, Supplier<T> theSupplier) {\n" + 
                "        return rgb(rgb -> checkEquals(r, rgb.r) && bCheck.test(rgb.b), theSupplier);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<T> rgb(int r, Absent g, Predicate<Integer> bCheck, Function<RGB, T> theAction) {\n" + 
                "        return rgb(rgb -> checkEquals(r, rgb.r) && bCheck.test(rgb.b), theAction);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, Absent g, Predicate<Integer> bCheck, T theValue) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r) && bCheck.test(rgb.b), theValue);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, Absent g, Predicate<Integer> bCheck, Supplier<T> theSupplier) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r) && bCheck.test(rgb.b), theSupplier);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, Absent g, Predicate<Integer> bCheck, Function<RGB, T> theAction) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r) && bCheck.test(rgb.b), theAction);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<T> rgb(Absent r, int g, Predicate<Integer> bCheck, T theValue) {\n" + 
                "        return rgb(rgb -> checkEquals(g, rgb.g) && bCheck.test(rgb.b), theValue);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<T> rgb(Absent r, int g, Predicate<Integer> bCheck, Supplier<T> theSupplier) {\n" + 
                "        return rgb(rgb -> checkEquals(g, rgb.g) && bCheck.test(rgb.b), theSupplier);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<T> rgb(Absent r, int g, Predicate<Integer> bCheck, Function<RGB, T> theAction) {\n" + 
                "        return rgb(rgb -> checkEquals(g, rgb.g) && bCheck.test(rgb.b), theAction);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<T> rgb(int r, int g, Predicate<Integer> bCheck, T theValue) {\n" + 
                "        return rgb(rgb -> checkEquals(r, rgb.r) && checkEquals(g, rgb.g) && bCheck.test(rgb.b), theValue);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<T> rgb(int r, int g, Predicate<Integer> bCheck, Supplier<T> theSupplier) {\n" + 
                "        return rgb(rgb -> checkEquals(r, rgb.r) && checkEquals(g, rgb.g) && bCheck.test(rgb.b), theSupplier);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<T> rgb(int r, int g, Predicate<Integer> bCheck, Function<RGB, T> theAction) {\n" + 
                "        return rgb(rgb -> checkEquals(r, rgb.r) && checkEquals(g, rgb.g) && bCheck.test(rgb.b), theAction);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, int g, Predicate<Integer> bCheck, T theValue) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r) && checkEquals(g, rgb.g) && bCheck.test(rgb.b), theValue);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, int g, Predicate<Integer> bCheck, Supplier<T> theSupplier) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r) && checkEquals(g, rgb.g) && bCheck.test(rgb.b), theSupplier);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, int g, Predicate<Integer> bCheck, Function<RGB, T> theAction) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r) && checkEquals(g, rgb.g) && bCheck.test(rgb.b), theAction);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<T> rgb(Absent r, Predicate<Integer> gCheck, Predicate<Integer> bCheck, T theValue) {\n" + 
                "        return rgb(rgb -> gCheck.test(rgb.g) && bCheck.test(rgb.b), theValue);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<T> rgb(Absent r, Predicate<Integer> gCheck, Predicate<Integer> bCheck, Supplier<T> theSupplier) {\n" + 
                "        return rgb(rgb -> gCheck.test(rgb.g) && bCheck.test(rgb.b), theSupplier);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<T> rgb(Absent r, Predicate<Integer> gCheck, Predicate<Integer> bCheck, Function<RGB, T> theAction) {\n" + 
                "        return rgb(rgb -> gCheck.test(rgb.g) && bCheck.test(rgb.b), theAction);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<T> rgb(int r, Predicate<Integer> gCheck, Predicate<Integer> bCheck, T theValue) {\n" + 
                "        return rgb(rgb -> checkEquals(r, rgb.r) && gCheck.test(rgb.g) && bCheck.test(rgb.b), theValue);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<T> rgb(int r, Predicate<Integer> gCheck, Predicate<Integer> bCheck, Supplier<T> theSupplier) {\n" + 
                "        return rgb(rgb -> checkEquals(r, rgb.r) && gCheck.test(rgb.g) && bCheck.test(rgb.b), theSupplier);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<T> rgb(int r, Predicate<Integer> gCheck, Predicate<Integer> bCheck, Function<RGB, T> theAction) {\n" + 
                "        return rgb(rgb -> checkEquals(r, rgb.r) && gCheck.test(rgb.g) && bCheck.test(rgb.b), theAction);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public ColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, Predicate<Integer> gCheck, Predicate<Integer> bCheck, T theValue) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r) && gCheck.test(rgb.g) && bCheck.test(rgb.b), theValue);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, Predicate<Integer> gCheck, Predicate<Integer> bCheck, Supplier<T> theSupplier) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r) && gCheck.test(rgb.g) && bCheck.test(rgb.b), theSupplier);\n" + 
                "    }\n" + 
                "    public ColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, Predicate<Integer> gCheck, Predicate<Integer> bCheck, Function<RGB, T> theAction) {\n" + 
                "        return rgb(rgb -> rCheck.test(rgb.r) && gCheck.test(rgb.g) && bCheck.test(rgb.b), theAction);\n" + 
                "    }\n" + 
                "}", lines);
    }
}

package functionalj.annotations.uniontype.generator;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;

import java.util.stream.Collectors;

import org.junit.Ignore;
import org.junit.Test;

import functionalj.annotations.uniontype.generator.model.Choice;
import functionalj.annotations.uniontype.generator.model.ChoiceParam;
import functionalj.annotations.uniontype.generator.model.SourceSpec;
import functionalj.annotations.uniontype.generator.model.Type;
import lombok.val;


public class GenericChoiceTest {
//    
//    @UnionType(specField="spec")
//    public static interface MayBe {
//            void Nill();
//        <T> void Just(T data);
//    }
    
    public static final SourceSpec spec = new SourceSpec(
            "MayBe", 
            new Type("functionalj.annotations.uniontype", "MayBeTest", "MayBe", emptyList()), 
            "spec", 
            false, 
            emptyList(), 
            asList(
                    new Choice("Nill", null, emptyList()), 
                    new Choice("Just", null, asList(new ChoiceParam("data", new Type(null, null, "T", emptyList()))))
            ),
            emptyList()
        );
    
    @Ignore("Have not implement.")
    @Test
    public void test() {
        val targetClass = new TargetClass(spec);
        val code = targetClass.lines().stream().collect(Collectors.joining("\n"));
        val expect = 
                "package functionalj.annotations.uniontype;\n" + 
                "\n" + 
                "import functionalj.annotations.uniontype.AbstractUnionType;\n" + 
                "import functionalj.annotations.uniontype.UnionTypeSwitch;\n" + 
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
                "public abstract class MayBe extends AbstractUnionType<MayBe.MayBeFirstSwitch> implements Pipeable<MayBe> {\n" + 
                "    \n" + 
                "    public static final Nill nill = Nill.instance;\n" + 
                "    public static final Nill Nill() {\n" + 
                "        return Nill.instance;\n" + 
                "    }\n" + 
                "    public static final <T> Just Just(T data) {\n" + 
                "        return new Just(data);\n" + 
                "    }\n" + 
                "    \n" + 
                "    \n" + 
                "    private MayBe() {}\n" + 
                "    public MayBe __data() throws Exception { return this; }\n" + 
                "    public Result<MayBe> toResult() { return Result.of(this); }\n" + 
                "    \n" + 
                "    public static final class Nill extends MayBe {\n" + 
                "        private static final Nill instance = new Nill();\n" + 
                "        private Nill() {}\n" + 
                "    }\n" + 
                "    public static final class Just<T> extends MayBe {\n" + 
                "        private T data;\n" + 
                "        private Just(T data) {\n" + 
                "            this.data = data;\n" + 
                "        }\n" + 
                "        public T data() { return data; }\n" + 
                "        public Just withData(T data) { return new Just(data); }\n" + 
                "    }\n" + 
                "    \n" + 
                "    public final MayBeFirstSwitch mapSwitch = new MayBeFirstSwitch(this);\n" + 
                "    @Override public MayBeFirstSwitch __switch() {\n" + 
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
                "                    .nill(__ -> \"Nill\")\n" + 
                "                    .just(just -> \"Just(\" + String.format(\"%1$s\", just.data) + \")\")\n" + 
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
                "        if (!(obj instanceof MayBe))\n" + 
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
                "    public boolean isNill() { return this instanceof Nill; }\n" + 
                "    public Result<Nill> asNill() { return Result.of(this).filter(Nill.class).map(Nill.class::cast); }\n" + 
                "    public MayBe ifNill(Consumer<Nill> action) { if (isNill()) action.accept((Nill)this); return this; }\n" + 
                "    public MayBe ifNill(Runnable action) { if (isNill()) action.run(); return this; }\n" + 
                "    public boolean isJust() { return this instanceof Just; }\n" + 
                "    public Result<Just> asJust() { return Result.of(this).filter(Just.class).map(Just.class::cast); }\n" + 
                "    public MayBe ifJust(Consumer<Just> action) { if (isJust()) action.accept((Just)this); return this; }\n" + 
                "    public MayBe ifJust(Runnable action) { if (isJust()) action.run(); return this; }\n" + 
                "    \n" + 
                "    public static class MayBeFirstSwitch {\n" + 
                "        private MayBe $value;\n" + 
                "        private MayBeFirstSwitch(MayBe theValue) { this.$value = theValue; }\n" + 
                "        \n" + 
                "        public <TARGET> MayBeSwitchJust<TARGET> nill(Function<? super Nill, TARGET> theAction) {\n" + 
                "            Function<MayBe, TARGET> $action = null;\n" + 
                "            Function<MayBe, TARGET> oldAction = (Function<MayBe, TARGET>)$action;\n" + 
                "            Function<MayBe, TARGET> newAction =\n" + 
                "                ($action != null)\n" + 
                "                ? oldAction : \n" + 
                "                    ($value instanceof Nill)\n" + 
                "                    ? (Function<MayBe, TARGET>)(d -> theAction.apply((Nill)d))\n" + 
                "                    : oldAction;\n" + 
                "            \n" + 
                "            return new MayBeSwitchJust<TARGET>($value, newAction);\n" + 
                "        }\n" + 
                "        public <TARGET> MayBeSwitchJust<TARGET> nill(Supplier<TARGET> theSupplier) {\n" + 
                "            return nill(d->theSupplier.get());\n" + 
                "        }\n" + 
                "    }\n" + 
                "    public static class MayBeSwitchNillJust<TARGET> extends UnionTypeSwitch<MayBe, TARGET> {\n" + 
                "        private MayBeSwitchNillJust(MayBe theValue, Function<MayBe, TARGET> theAction) { super(theValue, theAction); }\n" + 
                "        \n" + 
                "        public MayBeSwitchJust<TARGET> nill(Function<? super Nill, TARGET> theAction) {\n" + 
                "            Function<MayBe, TARGET> oldAction = (Function<MayBe, TARGET>)$action;\n" + 
                "            Function<MayBe, TARGET> newAction =\n" + 
                "                ($action != null)\n" + 
                "                ? oldAction : \n" + 
                "                    ($value instanceof Nill)\n" + 
                "                    ? (Function<MayBe, TARGET>)(d -> theAction.apply((Nill)d))\n" + 
                "                    : oldAction;\n" + 
                "            \n" + 
                "            return new MayBeSwitchJust<TARGET>($value, newAction);\n" + 
                "        }\n" + 
                "        public MayBeSwitchJust<TARGET> nill(Supplier<TARGET> theSupplier) {\n" + 
                "            return nill(d->theSupplier.get());\n" + 
                "        }\n" + 
                "    }\n" + 
                "    public static class MayBeSwitchJust<TARGET> extends UnionTypeSwitch<MayBe, TARGET> {\n" + 
                "        private MayBeSwitchJust(MayBe theValue, Function<MayBe, TARGET> theAction) { super(theValue, theAction); }\n" + 
                "        \n" + 
                "        public TARGET just(Function<? super Just, TARGET> theAction) {\n" + 
                "            Function<MayBe, TARGET> oldAction = (Function<MayBe, TARGET>)$action;\n" + 
                "            Function<MayBe, TARGET> newAction =\n" + 
                "                ($action != null)\n" + 
                "                ? oldAction : \n" + 
                "                    ($value instanceof Just)\n" + 
                "                    ? (Function<MayBe, TARGET>)(d -> theAction.apply((Just)d))\n" + 
                "                    : oldAction;\n" + 
                "            \n" + 
                "            return newAction.apply($value);\n" + 
                "        }\n" + 
                "        public TARGET just(Supplier<TARGET> theSupplier) {\n" + 
                "            return just(d->theSupplier.get());\n" + 
                "        }\n" + 
                "        \n" + 
                "        public <T> MayBeSwitchJust<TARGET> just(Predicate<Just<T>> check, Function<? super Just, TARGET> theAction) {\n" + 
                "            Function<MayBe, TARGET> oldAction = (Function<MayBe, TARGET>)$action;\n" + 
                "            Function<MayBe, TARGET> newAction =\n" + 
                "                ($action != null)\n" + 
                "                ? oldAction : \n" + 
                "                    (($value instanceof Just) && check.test((Just)$value))\n" + 
                "                    ? (Function<MayBe, TARGET>)(d -> theAction.apply((Just)d))\n" + 
                "                    : oldAction;\n" + 
                "            \n" + 
                "            return new MayBeSwitchJust<TARGET>($value, newAction);\n" + 
                "        }\n" + 
                "        public <T> MayBeSwitchJust<TARGET> just(Predicate<Just<T>> check, Supplier<TARGET> theSupplier) {\n" + 
                "            return just(check, d->theSupplier.get());\n" + 
                "        }\n" + 
                "        \n" + 
                "        public <T> MayBeSwitchJust<TARGET> justOf(T aData, Function<Just, TARGET> theAction) {\n" + 
                "            return just(just -> checkEquals(aData, just.data), theAction);\n" + 
                "        }\n" + 
                "        public <T> MayBeSwitchJust<TARGET> justOf(T aData, Supplier<TARGET> theSupplier) {\n" + 
                "            return just(just -> checkEquals(aData, just.data), theSupplier);\n" + 
                "        }\n" + 
                "        \n" + 
                "        public <T> MayBeSwitchJust<TARGET> justOf(Predicate<T> dataCheck, Function<Just, TARGET> theAction) {\n" + 
                "            return just(just -> dataCheck.test(just.data), theAction);\n" + 
                "        }\n" + 
                "        public <T> MayBeSwitchJust<TARGET> justOf(Predicate<T> dataCheck, Supplier<TARGET> theSupplier) {\n" + 
                "            return just(just -> dataCheck.test(just.data), theSupplier);\n" + 
                "        }\n" + 
                "    }\n" + 
                "    \n" + 
                "    public static final SourceSpec spec = new SourceSpec(\"MayBe\", new Type(\"functionalj.annotations.uniontype\", \"MayBeTest\", \"MayBe\", emptyList()), \"spec\", false, emptyList(), java.util.Arrays.asList(new Choice(\"Nill\", null, emptyList()), new Choice(\"Just\", null, java.util.Arrays.asList(new ChoiceParam(\"data\", new Type(null, null, \"T\", emptyList()))))), emptyList());\n" + 
                "    \n" + 
                "}\n" + 
                "";
        assertEquals(expect, code);
    }

}

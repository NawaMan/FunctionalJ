// ============================================================================
// Copyright (c) 2017-2020 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;

import java.util.stream.Collectors;

import org.junit.Test;

import functionalj.types.Type;
import functionalj.types.choice.generator.model.Case;
import functionalj.types.choice.generator.model.CaseParam;
import functionalj.types.choice.generator.model.SourceSpec;
import lombok.val;


public class GenericChoiceTest {
    
    public static final SourceSpec spec = new SourceSpec(
            "MayBe", 
            new Type(FullGeneratorTest.class.getPackage().getName(), "MayBeTest", "MayBe"),
            "spec",
            false,
            null,
            emptyList(), 
            asList(
                    new Case("Nill", null, emptyList()), 
                    new Case("Just", null, asList(new CaseParam("data", new Type(null, null, "T", emptyList()), false)))
            ),
            emptyList(),
            emptyList()
        );
    
    @Test
    public void test() {
        val targetClass = new TargetClass(spec);
        val code = targetClass.lines().stream().collect(Collectors.joining("\n"));
        val expect = 
                "package functionalj.types.choice.generator;\n" + 
                "\n" + 
                "import functionalj.lens.core.LensSpec;\n" + 
                "import functionalj.lens.lenses.*;\n" + 
                "import functionalj.pipeable.Pipeable;\n" + 
                "import functionalj.result.Result;\n" + 
                "import functionalj.types.choice.ChoiceTypeSwitch;\n" + 
                "import functionalj.types.choice.IChoice;\n" + 
                "import java.util.function.Consumer;\n" + 
                "import java.util.function.Function;\n" + 
                "import java.util.function.Predicate;\n" + 
                "import java.util.function.Supplier;\n" + 
                "\n" + 
                "// functionalj.types.choice.generator.MayBeTest.MayBe\n" + 
                "\n" + 
                "@SuppressWarnings({\"javadoc\", \"rawtypes\", \"unchecked\"})\n" + 
                "public abstract class MayBe implements IChoice<MayBe.MayBeFirstSwitch>, Pipeable<MayBe> {\n" + 
                "    \n" + 
                "    public static final Nill nill = Nill.instance;\n" + 
                "    public static final Nill Nill() {\n" + 
                "        return Nill.instance;\n" + 
                "    }\n" + 
                "    public static final Just Just(T data) {\n" + 
                "        return new Just(data);\n" + 
                "    }\n" + 
                "    \n" + 
                "    \n" + 
                "    public static final MayBeLens<MayBe> theMayBe = new MayBeLens<>(LensSpec.of(MayBe.class));\n" + 
                "    public static final MayBeLens<MayBe> eachMayBe = theMayBe;\n" + 
                "    public static class MayBeLens<HOST> extends ObjectLensImpl<HOST, MayBe> {\n" + 
                "\n" + 
                "        public final BooleanAccessPrimitive<MayBe> isNill = MayBe::isNill;\n" + 
                "        public final BooleanAccessPrimitive<MayBe> isJust = MayBe::isJust;\n" + 
                "        public final ResultAccess<HOST, Nill, Nill.NillLens<HOST>> asNill = createSubResultLens(MayBe::asNill, (functionalj.lens.core.WriteLens<MayBe,Result<Nill>>)null, Nill.NillLens::new);\n" + 
                "        public final ResultAccess<HOST, Just, Just.JustLens<HOST>> asJust = createSubResultLens(MayBe::asJust, (functionalj.lens.core.WriteLens<MayBe,Result<Just>>)null, Just.JustLens::new);\n" + 
                "        public MayBeLens(LensSpec<HOST, MayBe> spec) {\n" + 
                "            super(spec);\n" + 
                "        }\n" + 
                "    }\n" + 
                "    \n" + 
                "    private MayBe() {}\n" + 
                "    public MayBe __data() throws Exception { return this; }\n" + 
                "    public Result<MayBe> toResult() { return Result.valueOf(this); }\n" + 
                "    \n" + 
                "    public static <T extends MayBe> T fromMap(java.util.Map<String, Object> map) {\n" + 
                "        String __tagged = (String)map.get(\"__tagged\");\n" + 
                "        if (\"Nill\".equals(__tagged))\n" + 
                "            return (T)Nill.caseFromMap(map);\n" + 
                "        if (\"Just\".equals(__tagged))\n" + 
                "            return (T)Just.caseFromMap(map);\n" + 
                "        throw new IllegalArgumentException(\"Tagged value does not represent a valid type: \" + __tagged);\n" + 
                "    }\n" + 
                "    \n" + 
                "    static private functionalj.map.FuncMap<String, java.util.Map<String, functionalj.types.choice.generator.model.CaseParam>> __schema__ = functionalj.map.FuncMap.<String, java.util.Map<String, functionalj.types.choice.generator.model.CaseParam>>newMap()\n" + 
                "        .with(\"Nill\", Nill.getCaseSchema())\n" + 
                "        .with(\"Just\", Just.getCaseSchema())\n" + 
                "        .build();\n" + 
                "    public static java.util.Map<String, java.util.Map<String, functionalj.types.choice.generator.model.CaseParam>> getChoiceSchema() {\n" + 
                "        return __schema__;\n" + 
                "    }\n" + 
                "    \n" + 
                "    public static final class Nill extends MayBe {\n" + 
                "        public static final Nill.NillLens<Nill> theNill = new Nill.NillLens<>(LensSpec.of(Nill.class));\n" + 
                "        public static final Nill.NillLens<Nill> eachNill = theNill;\n" + 
                "        private static final Nill instance = new Nill();\n" + 
                "        private Nill() {}\n" + 
                "        public static class NillLens<HOST> extends ObjectLensImpl<HOST, MayBe.Nill> {\n" + 
                "            \n" + 
                "            public NillLens(LensSpec<HOST, MayBe.Nill> spec) {\n" + 
                "                super(spec);\n" + 
                "            }\n" + 
                "            \n" + 
                "        }\n" + 
                "        public java.util.Map<String, Object> __toMap() {\n" + 
                "            return functionalj.map.FuncMap.empty();\n" + 
                "        }\n" + 
                "        static private functionalj.map.FuncMap<String, functionalj.types.choice.generator.model.CaseParam> __schema__ = functionalj.map.FuncMap.<String, functionalj.types.choice.generator.model.CaseParam>empty();\n" + 
                "        public static java.util.Map<String, functionalj.types.choice.generator.model.CaseParam> getCaseSchema() {\n" + 
                "            return __schema__;\n" + 
                "        }\n" + 
                "        public static Nill caseFromMap(java.util.Map<String, Object> map) {\n" + 
                "            return Nill(\n" + 
                "            );\n" + 
                "        }\n" + 
                "    }\n" + 
                "    public static final class Just extends MayBe {\n" + 
                "        public static final Just.JustLens<Just> theJust = new Just.JustLens<>(LensSpec.of(Just.class));\n" + 
                "        public static final Just.JustLens<Just> eachJust = theJust;\n" + 
                "        private T data;\n" + 
                "        private Just(T data) {\n" + 
                "            this.data = $utils.notNull(data);\n" + 
                "        }\n" + 
                "        public T data() { return data; }\n" + 
                "        public Just withData(T data) { return new Just(data); }\n" + 
                "        public static class JustLens<HOST> extends ObjectLensImpl<HOST, MayBe.Just> {\n" + 
                "            \n" + 
                "            public final ObjectLens<HOST, Object> data = (ObjectLens)createSubLens(MayBe.Just::data, MayBe.Just::withData, ObjectLens::of);\n" + 
                "            \n" + 
                "            public JustLens(LensSpec<HOST, MayBe.Just> spec) {\n" + 
                "                super(spec);\n" + 
                "            }\n" + 
                "            \n" + 
                "        }\n" + 
                "        public java.util.Map<String, Object> __toMap() {\n" + 
                "            java.util.Map<String, Object> map = new java.util.HashMap<>();\n" + 
                "            map.put(\"__tagged\", functionalj.types.IData.$utils.toMapValueObject(\"Just\"));\n" + 
                "            map.put(\"data\", this.data);\n" + 
                "            return map;\n" + 
                "        }\n" + 
                "        static private functionalj.map.FuncMap<String, functionalj.types.choice.generator.model.CaseParam> __schema__ = functionalj.map.FuncMap.<String, functionalj.types.choice.generator.model.CaseParam>newMap()\n" + 
                "            .with(\"data\", new functionalj.types.choice.generator.model.CaseParam(\"data\", new functionalj.types.Type(null, null, \"T\", java.util.Collections.emptyList()), false, null))\n" + 
                "            .build();\n" + 
                "        public static java.util.Map<String, functionalj.types.choice.generator.model.CaseParam> getCaseSchema() {\n" + 
                "            return __schema__;\n" + 
                "        }\n" + 
                "        public static Just caseFromMap(java.util.Map<String, Object> map) {\n" + 
                "            return Just(\n" + 
                "                $utils.propertyFromMap(map, __schema__, \"data\")\n" + 
                "            );\n" + 
                "        }\n" + 
                "    }\n" + 
                "    \n" + 
                "    public java.util.Map<java.lang.String, java.util.Map<java.lang.String, functionalj.types.choice.generator.model.CaseParam>> __getSchema() {\n" + 
                "        return getChoiceSchema();\n" + 
                "    }\n" + 
                "    \n" + 
                "    private final MayBeFirstSwitch __switch = new MayBeFirstSwitch(this);\n" + 
                "    @Override public MayBeFirstSwitch match() {\n" + 
                "         return __switch;\n" + 
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
                "            toString = $utils.Match(this)\n" + 
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
                "    public Result<Nill> asNill() { return Result.valueOf(this).filter(Nill.class).map(Nill.class::cast); }\n" + 
                "    public MayBe ifNill(Consumer<Nill> action) { if (isNill()) action.accept((Nill)this); return this; }\n" + 
                "    public MayBe ifNill(Runnable action) { if (isNill()) action.run(); return this; }\n" + 
                "    public boolean isJust() { return this instanceof Just; }\n" + 
                "    public Result<Just> asJust() { return Result.valueOf(this).filter(Just.class).map(Just.class::cast); }\n" + 
                "    public MayBe ifJust(Consumer<Just> action) { if (isJust()) action.accept((Just)this); return this; }\n" + 
                "    public MayBe ifJust(Runnable action) { if (isJust()) action.run(); return this; }\n" + 
                "    \n" + 
                "    public static class MayBeFirstSwitch {\n" + 
                "        private MayBe $value;\n" + 
                "        private MayBeFirstSwitch(MayBe theValue) { this.$value = theValue; }\n" + 
                "        public <TARGET> MayBeFirstSwitchTyped<TARGET> toA(Class<TARGET> clzz) {\n" + 
                "            return new MayBeFirstSwitchTyped<TARGET>($value);\n" + 
                "        }\n" + 
                "        \n" + 
                "        public <TARGET> MayBeSwitchJust<TARGET> nill(Function<? super Nill, ? extends TARGET> theAction) {\n" + 
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
                "        public <TARGET> MayBeSwitchJust<TARGET> nill(Supplier<? extends TARGET> theSupplier) {\n" + 
                "            return nill(d->theSupplier.get());\n" + 
                "        }\n" + 
                "        public <TARGET> MayBeSwitchJust<TARGET> nill(TARGET theValue) {\n" + 
                "            return nill(d->theValue);\n" + 
                "        }\n" + 
                "    }\n" + 
                "    public static class MayBeFirstSwitchTyped<TARGET> {\n" + 
                "        private MayBe $value;\n" + 
                "        private MayBeFirstSwitchTyped(MayBe theValue) { this.$value = theValue; }\n" + 
                "        \n" + 
                "        public MayBeSwitchJust<TARGET> nill(Function<? super Nill, ? extends TARGET> theAction) {\n" + 
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
                "        public MayBeSwitchJust<TARGET> nill(Supplier<? extends TARGET> theSupplier) {\n" + 
                "            return nill(d->theSupplier.get());\n" + 
                "        }\n" + 
                "        public MayBeSwitchJust<TARGET> nill(TARGET theValue) {\n" + 
                "            return nill(d->theValue);\n" + 
                "        }\n" + 
                "    }\n" + 
                "    public static class MayBeSwitchNillJust<TARGET> extends ChoiceTypeSwitch<MayBe, TARGET> {\n" + 
                "        private MayBeSwitchNillJust(MayBe theValue, Function<MayBe, ? extends TARGET> theAction) { super(theValue, theAction); }\n" + 
                "        \n" + 
                "        public MayBeSwitchJust<TARGET> nill(Function<? super Nill, ? extends TARGET> theAction) {\n" + 
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
                "        public MayBeSwitchJust<TARGET> nill(Supplier<? extends TARGET> theSupplier) {\n" + 
                "            return nill(d->theSupplier.get());\n" + 
                "        }\n" + 
                "        public MayBeSwitchJust<TARGET> nill(TARGET theValue) {\n" + 
                "            return nill(d->theValue);\n" + 
                "        }\n" + 
                "    }\n" + 
                "    public static class MayBeSwitchJust<TARGET> extends ChoiceTypeSwitch<MayBe, TARGET> {\n" + 
                "        private MayBeSwitchJust(MayBe theValue, Function<MayBe, ? extends TARGET> theAction) { super(theValue, theAction); }\n" + 
                "        \n" + 
                "        public TARGET just(Function<? super Just, ? extends TARGET> theAction) {\n" + 
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
                "        public TARGET just(Supplier<? extends TARGET> theSupplier) {\n" + 
                "            return just(d->theSupplier.get());\n" + 
                "        }\n" + 
                "        public TARGET just(TARGET theValue) {\n" + 
                "            return just(d->theValue);\n" + 
                "        }\n" + 
                "        \n" + 
                "        public MayBeSwitchJust<TARGET> just(Predicate<Just> check, Function<? super Just, ? extends TARGET> theAction) {\n" + 
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
                "        public MayBeSwitchJust<TARGET> just(Predicate<Just> check, Supplier<? extends TARGET> theSupplier) {\n" + 
                "            return just(check, d->theSupplier.get());\n" + 
                "        }\n" + 
                "        public MayBeSwitchJust<TARGET> just(Predicate<Just> check, TARGET theValue) {\n" + 
                "            return just(check, d->theValue);\n" + 
                "        }\n" + 
                "    }\n" + 
                "    \n" + 
                "    public static final functionalj.types.choice.generator.model.SourceSpec spec = new functionalj.types.choice.generator.model.SourceSpec(\"MayBe\", new functionalj.types.Type(\"functionalj.types.choice.generator\", \"MayBeTest\", \"MayBe\", java.util.Collections.emptyList()), \"spec\", false, \"__tagged\", java.util.Collections.emptyList(), java.util.Arrays.asList(new functionalj.types.choice.generator.model.Case(\"Nill\", null, java.util.Collections.emptyList()), new functionalj.types.choice.generator.model.Case(\"Just\", null, java.util.Arrays.asList(new functionalj.types.choice.generator.model.CaseParam(\"data\", new functionalj.types.Type(null, null, \"T\", java.util.Collections.emptyList()), false, null)))), java.util.Collections.emptyList(), java.util.Collections.emptyList());\n" + 
                "    \n" + 
                "}";
        assertEquals(expect, code);
    }

}

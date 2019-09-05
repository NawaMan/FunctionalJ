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

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;

import java.util.Objects;
import java.util.stream.Collectors;

import org.junit.Test;

import functionalj.types.Generic;
import functionalj.types.Type;
import functionalj.types.choice.generator.model.Case;
import functionalj.types.choice.generator.model.CaseParam;
import lombok.val;

@SuppressWarnings("javadoc")
public class GenericSupportTest {
    
    @Test
    public void test() {
        val generator = new Generator("Option",
                new Type(FullGeneratorTest.class.getPackage().getName(), "GenericSupportTest", "OptionSpec"),
                "spec", false, null,
                asList(
                   new Generic("T", "T extends Number", asList(new Type("java.lang", "Number"), new Type("java.io", "Serializable")))
                ),
                asList(
                    new Case("None"),
                    new Case("Some", asList(
                        new CaseParam("value", new Type("T"), false)
                    ))),
                asList(),
                emptyList());
        
        val lines  = generator.lines().stream().filter(Objects::nonNull).collect(Collectors.joining("\n"));
        assertEquals(expected, lines);
    }
    
    private static final String expected =
            "package functionalj.types.choice.generator;\n" + 
            "\n" + 
            "import functionalj.lens.core.LensSpec;\n" + 
            "import functionalj.lens.lenses.*;\n" + 
            "import functionalj.pipeable.Pipeable;\n" + 
            "import functionalj.result.Result;\n" + 
            "import functionalj.types.choice.ChoiceTypeSwitch;\n" + 
            "import functionalj.types.choice.IChoice;\n" + 
            "import java.io.Serializable;\n" + 
            "import java.util.function.Consumer;\n" + 
            "import java.util.function.Function;\n" + 
            "import java.util.function.Predicate;\n" + 
            "import java.util.function.Supplier;\n" + 
            "\n" + 
            "// functionalj.types.choice.generator.GenericSupportTest.OptionSpec\n" + 
            "\n" + 
            "@SuppressWarnings({\"javadoc\", \"rawtypes\", \"unchecked\"})\n" + 
            "public abstract class Option<T extends Number> implements IChoice<Option.OptionFirstSwitch<T>>, Pipeable<Option<T>> {\n" + 
            "    \n" + 
            "    \n" + 
            "    public static final <T extends Number> None<T> None() {\n" + 
            "        return None.instance;\n" + 
            "    }\n" + 
            "    public static final <T extends Number> Some<T> Some(T value) {\n" + 
            "        return new Some<T>(value);\n" + 
            "    }\n" + 
            "    \n" + 
            "    \n" + 
            "    public static final OptionLens<Option> theOption = new OptionLens<>(LensSpec.of(Option.class));\n" + 
            "    public static class OptionLens<HOST> extends ObjectLensImpl<HOST, Option> {\n" + 
            "\n" + 
            "        public final BooleanAccess<Option> isNone = Option::isNone;\n" + 
            "        public final BooleanAccess<Option> isSome = Option::isSome;\n" + 
            "        public final ResultAccess<HOST, None, None.NoneLens<HOST>> asNone = createSubResultLens(Option::asNone, null, None.NoneLens::new);\n" + 
            "        public final ResultAccess<HOST, Some, Some.SomeLens<HOST>> asSome = createSubResultLens(Option::asSome, null, Some.SomeLens::new);\n" + 
            "        public OptionLens(LensSpec<HOST, Option> spec) {\n" + 
            "            super(spec);\n" + 
            "        }\n" + 
            "    }\n" + 
            "    \n" + 
            "    private Option() {}\n" + 
            "    public Option<T> __data() throws Exception { return this; }\n" + 
            "    public Result<Option<T>> toResult() { return Result.valueOf(this); }\n" + 
            "    \n" + 
            "    public static <T extends Option> T fromMap(java.util.Map<String, Object> map) {\n" + 
            "        String __tagged = (String)map.get(\"__tagged\");\n" + 
            "        if (\"None\".equals(__tagged))\n" + 
            "            return (T)None.caseFromMap(map);\n" + 
            "        if (\"Some\".equals(__tagged))\n" + 
            "            return (T)Some.caseFromMap(map);\n" + 
            "        throw new IllegalArgumentException(\"Tagged value does not represent a valid type: \" + __tagged);\n" + 
            "    }\n" + 
            "    \n" + 
            "    static private functionalj.map.FuncMap<String, java.util.Map<String, functionalj.types.choice.generator.model.CaseParam>> __schema__ = functionalj.map.FuncMap.<String, java.util.Map<String, functionalj.types.choice.generator.model.CaseParam>>newMap()\n" + 
            "        .with(\"None\", None.getCaseSchema())\n" + 
            "        .with(\"Some\", Some.getCaseSchema())\n" + 
            "        .build();\n" + 
            "    public static java.util.Map<String, java.util.Map<String, functionalj.types.choice.generator.model.CaseParam>> getChoiceSchema() {\n" + 
            "        return __schema__;\n" + 
            "    }\n" + 
            "    \n" + 
            "    public static final class None<T extends Number> extends Option<T> {\n" + 
            "        public static final None.NoneLens<None> theNone = new None.NoneLens<>(LensSpec.of(None.class));\n" + 
            "        private static final None instance = new None();\n" + 
            "        private None() {}\n" + 
            "        public static class NoneLens<HOST> extends ObjectLensImpl<HOST, Option.None> {\n" + 
            "            \n" + 
            "            public NoneLens(LensSpec<HOST, Option.None> spec) {\n" + 
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
            "        public static None caseFromMap(java.util.Map<String, Object> map) {\n" + 
            "            return None(\n" + 
            "            );\n" + 
            "        }\n" + 
            "    }\n" + 
            "    public static final class Some<T extends Number> extends Option<T> {\n" + 
            "        public static final Some.SomeLens<Some> theSome = new Some.SomeLens<>(LensSpec.of(Some.class));\n" + 
            "        private T value;\n" + 
            "        private Some(T value) {\n" + 
            "            this.value = $utils.notNull(value);\n" + 
            "        }\n" + 
            "        public T value() { return value; }\n" + 
            "        public Some<T> withValue(T value) { return new Some<T>(value); }\n" + 
            "        public static class SomeLens<HOST> extends ObjectLensImpl<HOST, Option.Some> {\n" + 
            "            \n" + 
            "            public final ObjectLens<HOST, Object> value = (ObjectLens)createSubLens(Option.Some::value, Option.Some::withValue, ObjectLens::of);\n" + 
            "            \n" + 
            "            public SomeLens(LensSpec<HOST, Option.Some> spec) {\n" + 
            "                super(spec);\n" + 
            "            }\n" + 
            "            \n" + 
            "        }\n" + 
            "        public java.util.Map<String, Object> __toMap() {\n" + 
            "            java.util.Map<String, Object> map = new java.util.HashMap<>();\n" + 
            "            map.put(\"__tagged\", functionalj.types.IData.$utils.toMapValueObject(\"Some\"));\n" + 
            "            map.put(\"value\", this.value);\n" + 
            "            return map;\n" + 
            "        }\n" + 
            "        static private functionalj.map.FuncMap<String, functionalj.types.choice.generator.model.CaseParam> __schema__ = functionalj.map.FuncMap.<String, functionalj.types.choice.generator.model.CaseParam>newMap()\n" + 
            "            .with(\"value\", new functionalj.types.choice.generator.model.CaseParam(\"value\", new functionalj.types.Type(null, null, \"T\", java.util.Collections.emptyList()), false, null))\n" + 
            "            .build();\n" + 
            "        public static java.util.Map<String, functionalj.types.choice.generator.model.CaseParam> getCaseSchema() {\n" + 
            "            return __schema__;\n" + 
            "        }\n" + 
            "        public static Some caseFromMap(java.util.Map<String, Object> map) {\n" + 
            "            return Some(\n" + 
            "                $utils.propertyFromMap(map, __schema__, \"value\")\n" + 
            "            );\n" + 
            "        }\n" + 
            "    }\n" + 
            "    \n" + 
            "    public java.util.Map<java.lang.String, java.util.Map<java.lang.String, functionalj.types.choice.generator.model.CaseParam>> __getSchema() {\n" + 
            "        return getChoiceSchema();\n" + 
            "    }\n" + 
            "    \n" + 
            "    private final OptionFirstSwitch<T> __switch = new OptionFirstSwitch<T>(this);\n" + 
            "    @Override public OptionFirstSwitch<T> match() {\n" + 
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
            "                    .none(__ -> \"None\")\n" + 
            "                    .some(some -> \"Some(\" + String.format(\"%1$s\", some.value) + \")\")\n" + 
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
            "        if (!(obj instanceof Option))\n" + 
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
            "    public boolean isNone() { return this instanceof None; }\n" + 
            "    public Result<None<T>> asNone() { return Result.valueOf(this).filter(None.class).map(None.class::cast); }\n" + 
            "    public Option<T> ifNone(Consumer<None<T>> action) { if (isNone()) action.accept((None<T>)this); return this; }\n" + 
            "    public Option<T> ifNone(Runnable action) { if (isNone()) action.run(); return this; }\n" + 
            "    public boolean isSome() { return this instanceof Some; }\n" + 
            "    public Result<Some<T>> asSome() { return Result.valueOf(this).filter(Some.class).map(Some.class::cast); }\n" + 
            "    public Option<T> ifSome(Consumer<Some<T>> action) { if (isSome()) action.accept((Some<T>)this); return this; }\n" + 
            "    public Option<T> ifSome(Runnable action) { if (isSome()) action.run(); return this; }\n" + 
            "    \n" + 
            "    public static class OptionFirstSwitch<T extends Number> {\n" + 
            "        private Option<T> $value;\n" + 
            "        private OptionFirstSwitch(Option<T> theValue) { this.$value = theValue; }\n" + 
            "        public <TARGET> OptionFirstSwitchTyped<TARGET, T> toA(Class<TARGET> clzz) {\n" + 
            "            return new OptionFirstSwitchTyped<TARGET, T>($value);\n" + 
            "        }\n" + 
            "        \n" + 
            "        public <TARGET> OptionSwitchSome<TARGET, T> none(Function<? super None<T>, ? extends TARGET> theAction) {\n" + 
            "            Function<Option<T>, TARGET> $action = null;\n" + 
            "            Function<Option<T>, TARGET> oldAction = (Function<Option<T>, TARGET>)$action;\n" + 
            "            Function<Option<T>, TARGET> newAction =\n" + 
            "                ($action != null)\n" + 
            "                ? oldAction : \n" + 
            "                    ($value instanceof None)\n" + 
            "                    ? (Function<Option<T>, TARGET>)(d -> theAction.apply((None<T>)d))\n" + 
            "                    : oldAction;\n" + 
            "            \n" + 
            "            return new OptionSwitchSome<TARGET, T>($value, newAction);\n" + 
            "        }\n" + 
            "        public <TARGET> OptionSwitchSome<TARGET, T> none(Supplier<? extends TARGET> theSupplier) {\n" + 
            "            return none(d->theSupplier.get());\n" + 
            "        }\n" + 
            "        public <TARGET> OptionSwitchSome<TARGET, T> none(TARGET theValue) {\n" + 
            "            return none(d->theValue);\n" + 
            "        }\n" + 
            "    }\n" + 
            "    public static class OptionFirstSwitchTyped<TARGET, T extends Number> {\n" + 
            "        private Option<T> $value;\n" + 
            "        private OptionFirstSwitchTyped(Option<T> theValue) { this.$value = theValue; }\n" + 
            "        \n" + 
            "        public OptionSwitchSome<TARGET, T> none(Function<? super None<T>, ? extends TARGET> theAction) {\n" + 
            "            Function<Option<T>, TARGET> $action = null;\n" + 
            "            Function<Option<T>, TARGET> oldAction = (Function<Option<T>, TARGET>)$action;\n" + 
            "            Function<Option<T>, TARGET> newAction =\n" + 
            "                ($action != null)\n" + 
            "                ? oldAction : \n" + 
            "                    ($value instanceof None)\n" + 
            "                    ? (Function<Option<T>, TARGET>)(d -> theAction.apply((None<T>)d))\n" + 
            "                    : oldAction;\n" + 
            "            \n" + 
            "            return new OptionSwitchSome<TARGET, T>($value, newAction);\n" + 
            "        }\n" + 
            "        public OptionSwitchSome<TARGET, T> none(Supplier<? extends TARGET> theSupplier) {\n" + 
            "            return none(d->theSupplier.get());\n" + 
            "        }\n" + 
            "        public OptionSwitchSome<TARGET, T> none(TARGET theValue) {\n" + 
            "            return none(d->theValue);\n" + 
            "        }\n" + 
            "    }\n" + 
            "    public static class OptionSwitchNoneSome<TARGET, T extends Number> extends ChoiceTypeSwitch<Option<T>, TARGET> {\n" + 
            "        private OptionSwitchNoneSome(Option<T> theValue, Function<Option<T>, ? extends TARGET> theAction) { super(theValue, theAction); }\n" + 
            "        \n" + 
            "        public OptionSwitchSome<TARGET, T> none(Function<? super None<T>, ? extends TARGET> theAction) {\n" + 
            "            Function<Option<T>, TARGET> oldAction = (Function<Option<T>, TARGET>)$action;\n" + 
            "            Function<Option<T>, TARGET> newAction =\n" + 
            "                ($action != null)\n" + 
            "                ? oldAction : \n" + 
            "                    ($value instanceof None)\n" + 
            "                    ? (Function<Option<T>, TARGET>)(d -> theAction.apply((None<T>)d))\n" + 
            "                    : oldAction;\n" + 
            "            \n" + 
            "            return new OptionSwitchSome<TARGET, T>($value, newAction);\n" + 
            "        }\n" + 
            "        public OptionSwitchSome<TARGET, T> none(Supplier<? extends TARGET> theSupplier) {\n" + 
            "            return none(d->theSupplier.get());\n" + 
            "        }\n" + 
            "        public OptionSwitchSome<TARGET, T> none(TARGET theValue) {\n" + 
            "            return none(d->theValue);\n" + 
            "        }\n" + 
            "    }\n" + 
            "    public static class OptionSwitchSome<TARGET, T extends Number> extends ChoiceTypeSwitch<Option<T>, TARGET> {\n" + 
            "        private OptionSwitchSome(Option<T> theValue, Function<Option<T>, ? extends TARGET> theAction) { super(theValue, theAction); }\n" + 
            "        \n" + 
            "        public TARGET some(Function<? super Some<T>, ? extends TARGET> theAction) {\n" + 
            "            Function<Option<T>, TARGET> oldAction = (Function<Option<T>, TARGET>)$action;\n" + 
            "            Function<Option<T>, TARGET> newAction =\n" + 
            "                ($action != null)\n" + 
            "                ? oldAction : \n" + 
            "                    ($value instanceof Some)\n" + 
            "                    ? (Function<Option<T>, TARGET>)(d -> theAction.apply((Some<T>)d))\n" + 
            "                    : oldAction;\n" + 
            "            \n" + 
            "            return newAction.apply($value);\n" + 
            "        }\n" + 
            "        public TARGET some(Supplier<? extends TARGET> theSupplier) {\n" + 
            "            return some(d->theSupplier.get());\n" + 
            "        }\n" + 
            "        public TARGET some(TARGET theValue) {\n" + 
            "            return some(d->theValue);\n" + 
            "        }\n" + 
            "        \n" + 
            "        public OptionSwitchSome<TARGET, T> some(Predicate<Some<T>> check, Function<? super Some<T>, ? extends TARGET> theAction) {\n" + 
            "            Function<Option<T>, TARGET> oldAction = (Function<Option<T>, TARGET>)$action;\n" + 
            "            Function<Option<T>, TARGET> newAction =\n" + 
            "                ($action != null)\n" + 
            "                ? oldAction : \n" + 
            "                    (($value instanceof Some) && check.test((Some<T>)$value))\n" + 
            "                    ? (Function<Option<T>, TARGET>)(d -> theAction.apply((Some<T>)d))\n" + 
            "                    : oldAction;\n" + 
            "            \n" + 
            "            return new OptionSwitchSome<TARGET, T>($value, newAction);\n" + 
            "        }\n" + 
            "        public OptionSwitchSome<TARGET, T> some(Predicate<Some<T>> check, Supplier<? extends TARGET> theSupplier) {\n" + 
            "            return some(check, d->theSupplier.get());\n" + 
            "        }\n" + 
            "        public OptionSwitchSome<TARGET, T> some(Predicate<Some<T>> check, TARGET theValue) {\n" + 
            "            return some(check, d->theValue);\n" + 
            "        }\n" + 
            "    }\n" + 
            "    \n" + 
            "    public static final functionalj.types.choice.generator.model.SourceSpec spec = new functionalj.types.choice.generator.model.SourceSpec(\"Option\", new functionalj.types.Type(\"functionalj.types.choice.generator\", \"GenericSupportTest\", \"OptionSpec\", java.util.Collections.emptyList()), \"spec\", false, \"__tagged\", java.util.Arrays.asList(new functionalj.types.Generic(\"T\", \"T extends Number\", java.util.Arrays.asList(new functionalj.types.Type(\"java.lang\", null, \"Number\", java.util.Collections.emptyList()), new functionalj.types.Type(\"java.io\", null, \"Serializable\", java.util.Collections.emptyList())))), java.util.Arrays.asList(new functionalj.types.choice.generator.model.Case(\"None\", null, java.util.Collections.emptyList()), new functionalj.types.choice.generator.model.Case(\"Some\", null, java.util.Arrays.asList(new functionalj.types.choice.generator.model.CaseParam(\"value\", new functionalj.types.Type(null, null, \"T\", java.util.Collections.emptyList()), false, null)))), java.util.Collections.emptyList(), java.util.Collections.emptyList());\n" + 
            "    \n" + 
            "}";
    
}

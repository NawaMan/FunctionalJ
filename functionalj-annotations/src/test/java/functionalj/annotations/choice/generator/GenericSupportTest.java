package functionalj.annotations.choice.generator;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;

import java.util.Objects;
import java.util.stream.Collectors;

import org.junit.Test;

import functionalj.annotations.choice.generator.model.Case;
import functionalj.annotations.choice.generator.model.CaseParam;
import functionalj.annotations.choice.generator.model.Generic;
import functionalj.annotations.choice.generator.model.Type;
import lombok.val;

@SuppressWarnings("javadoc")
public class GenericSupportTest {
    
    @Test
    public void test() {
        val generator = new Generator("Option",
                new Type(FullGeneratorTest.class.getPackage().getName(), "GenericSupportTest", "OptionSpec"),
                "spec", false,
                asList(
                   new Generic("T", "T extends Number", asList(new Type("java.lang", "Number"), new Type("java.io", "Serializable")))
                ),
                asList(
                    new Case("None"),
                    new Case("Some", asList(
                        new CaseParam("value", new Type("T"))
                    ))),
                asList(),
                emptyList());
        
        val lines  = generator.lines().stream().filter(Objects::nonNull).collect(Collectors.joining("\n"));
        assertEquals(expected, lines);
    }
    
    private static final String expected =
            "package functionalj.annotations.choice.generator;\n" + 
            "\n" + 
            "import functionalj.annotations.choice.AbstractChoiceClass;\n" + 
            "import functionalj.annotations.choice.ChoiceTypeSwitch;\n" + 
            "import functionalj.lens.core.LensSpec;\n" + 
            "import functionalj.lens.lenses.*;\n" + 
            "import functionalj.pipeable.Pipeable;\n" + 
            "import functionalj.result.Result;\n" + 
            "import java.io.Serializable;\n" + 
            "import java.util.function.Consumer;\n" + 
            "import java.util.function.Function;\n" + 
            "import java.util.function.Predicate;\n" + 
            "import java.util.function.Supplier;\n" + 
            "import static functionalj.annotations.choice.CheckEquals.checkEquals;\n" + 
            "import static functionalj.annotations.choice.ChoiceTypes.Switch;\n" + 
            "\n" + 
            "@SuppressWarnings({\"javadoc\", \"rawtypes\", \"unchecked\"})\n" + 
            "public abstract class Option<T extends Number> extends AbstractChoiceClass<Option.OptionFirstSwitch<T>> implements Pipeable<Option<T>> {\n" + 
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
            "    public Result<Option<T>> toResult() { return Result.of(this); }\n" + 
            "    \n" + 
            "    public static final class None<T extends Number> extends Option<T> {\n" + 
            "        public static final NoneLens<None> theNone = new NoneLens<>(LensSpec.of(None.class));\n" + 
            "        private static final None instance = new None();\n" + 
            "        private None() {}\n" + 
            "        public static class NoneLens<HOST> extends ObjectLensImpl<HOST, Option.None> {\n" + 
            "            \n" + 
            "            public NoneLens(LensSpec<HOST, Option.None> spec) {\n" + 
            "                super(spec);\n" + 
            "            }\n" + 
            "            \n" + 
            "        }\n" + 
            "    }\n" + 
            "    public static final class Some<T extends Number> extends Option<T> {\n" + 
            "        public static final SomeLens<Some> theSome = new SomeLens<>(LensSpec.of(Some.class));\n" + 
            "        private T value;\n" + 
            "        private Some(T value) {\n" + 
            "            this.value = value;\n" + 
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
            "    }\n" + 
            "    \n" + 
            "    public final OptionFirstSwitch<T> mapSwitch = new OptionFirstSwitch<T>(this);\n" + 
            "    @Override public OptionFirstSwitch<T> __switch() {\n" + 
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
            "    public Result<None<T>> asNone() { return Result.of(this).filter(None.class).map(None.class::cast); }\n" + 
            "    public Option<T> ifNone(Consumer<None<T>> action) { if (isNone()) action.accept((None<T>)this); return this; }\n" + 
            "    public Option<T> ifNone(Runnable action) { if (isNone()) action.run(); return this; }\n" + 
            "    public boolean isSome() { return this instanceof Some; }\n" + 
            "    public Result<Some<T>> asSome() { return Result.of(this).filter(Some.class).map(Some.class::cast); }\n" + 
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
            "        public <TARGET> OptionSwitchSome<TARGET, T> none(Function<? super None<T>, TARGET> theAction) {\n" + 
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
            "        public <TARGET> OptionSwitchSome<TARGET, T> none(Supplier<TARGET> theSupplier) {\n" + 
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
            "        public OptionSwitchSome<TARGET, T> none(Function<? super None<T>, TARGET> theAction) {\n" + 
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
            "        public OptionSwitchSome<TARGET, T> none(Supplier<TARGET> theSupplier) {\n" + 
            "            return none(d->theSupplier.get());\n" + 
            "        }\n" + 
            "        public OptionSwitchSome<TARGET, T> none(TARGET theValue) {\n" + 
            "            return none(d->theValue);\n" + 
            "        }\n" + 
            "    }\n" + 
            "    public static class OptionSwitchNoneSome<TARGET, T extends Number> extends ChoiceTypeSwitch<Option<T>, TARGET> {\n" + 
            "        private OptionSwitchNoneSome(Option<T> theValue, Function<Option<T>, TARGET> theAction) { super(theValue, theAction); }\n" + 
            "        \n" + 
            "        public OptionSwitchSome<TARGET, T> none(Function<? super None<T>, TARGET> theAction) {\n" + 
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
            "        public OptionSwitchSome<TARGET, T> none(Supplier<TARGET> theSupplier) {\n" + 
            "            return none(d->theSupplier.get());\n" + 
            "        }\n" + 
            "        public OptionSwitchSome<TARGET, T> none(TARGET theValue) {\n" + 
            "            return none(d->theValue);\n" + 
            "        }\n" + 
            "    }\n" + 
            "    public static class OptionSwitchSome<TARGET, T extends Number> extends ChoiceTypeSwitch<Option<T>, TARGET> {\n" + 
            "        private OptionSwitchSome(Option<T> theValue, Function<Option<T>, TARGET> theAction) { super(theValue, theAction); }\n" + 
            "        \n" + 
            "        public TARGET some(Function<? super Some<T>, TARGET> theAction) {\n" + 
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
            "        public TARGET some(Supplier<TARGET> theSupplier) {\n" + 
            "            return some(d->theSupplier.get());\n" + 
            "        }\n" + 
            "        public TARGET some(TARGET theValue) {\n" + 
            "            return some(d->theValue);\n" + 
            "        }\n" + 
            "        \n" + 
            "        public OptionSwitchSome<TARGET, T> some(Predicate<Some<T>> check, Function<? super Some<T>, TARGET> theAction) {\n" + 
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
            "        public OptionSwitchSome<TARGET, T> some(Predicate<Some<T>> check, Supplier<TARGET> theSupplier) {\n" + 
            "            return some(check, d->theSupplier.get());\n" + 
            "        }\n" + 
            "        public OptionSwitchSome<TARGET, T> some(Predicate<Some<T>> check, TARGET theValue) {\n" + 
            "            return some(check, d->theValue);\n" + 
            "        }\n" + 
            "        \n" + 
            "        public OptionSwitchSome<TARGET, T> someOf(T aValue, Function<Some<T>, TARGET> theAction) {\n" + 
            "            return some(some -> checkEquals(aValue, some.value), theAction);\n" + 
            "        }\n" + 
            "        public OptionSwitchSome<TARGET, T> someOf(T aValue, Supplier<TARGET> theSupplier) {\n" + 
            "            return some(some -> checkEquals(aValue, some.value), theSupplier);\n" + 
            "        }\n" + 
            "        public OptionSwitchSome<TARGET, T> someOf(T aValue, TARGET theValue) {\n" + 
            "            return some(some -> checkEquals(aValue, some.value), theValue);\n" + 
            "        }\n" + 
            "        \n" + 
            "        public OptionSwitchSome<TARGET, T> someOf(Predicate<T> valueCheck, Function<Some<T>, TARGET> theAction) {\n" + 
            "            return some(some -> valueCheck.test(some.value), theAction);\n" + 
            "        }\n" + 
            "        public OptionSwitchSome<TARGET, T> someOf(Predicate<T> valueCheck, Supplier<TARGET> theSupplier) {\n" + 
            "            return some(some -> valueCheck.test(some.value), theSupplier);\n" + 
            "        }\n" + 
            "        public OptionSwitchSome<TARGET, T> someOf(Predicate<T> valueCheck, TARGET theValue) {\n" + 
            "            return some(some -> valueCheck.test(some.value), theValue);\n" + 
            "        }\n" + 
            "    }\n" + 
            "    \n" + 
            "    public static final functionalj.annotations.choice.generator.model.SourceSpec spec = new functionalj.annotations.choice.generator.model.SourceSpec(\"Option\", new functionalj.annotations.choice.generator.model.Type(\"functionalj.annotations.choice.generator\", \"GenericSupportTest\", \"OptionSpec\", java.util.Collections.emptyList()), \"spec\", false, java.util.Arrays.asList(new functionalj.annotations.choice.generator.model.Generic(\"T\", \"T extends Number\", java.util.Arrays.asList(new functionalj.annotations.choice.generator.model.Type(\"java.lang\", null, \"Number\", java.util.Collections.emptyList()), new functionalj.annotations.choice.generator.model.Type(\"java.io\", null, \"Serializable\", java.util.Collections.emptyList())))), java.util.Arrays.asList(new functionalj.annotations.choice.generator.model.Case(\"None\", null, java.util.Collections.emptyList()), new functionalj.annotations.choice.generator.model.Case(\"Some\", null, java.util.Arrays.asList(new functionalj.annotations.choice.generator.model.CaseParam(\"value\", new functionalj.annotations.choice.generator.model.Type(null, null, \"T\", java.util.Collections.emptyList()))))), java.util.Collections.emptyList(), java.util.Collections.emptyList());\n" + 
            "    \n" + 
            "}";
    
}

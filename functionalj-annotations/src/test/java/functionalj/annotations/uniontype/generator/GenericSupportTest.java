package functionalj.annotations.uniontype.generator;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import java.util.Objects;
import java.util.stream.Collectors;

import org.junit.Test;

import functionalj.annotations.uniontype.generator.model.Choice;
import functionalj.annotations.uniontype.generator.model.ChoiceParam;
import functionalj.annotations.uniontype.generator.model.Generic;
import functionalj.annotations.uniontype.generator.model.Type;
import lombok.val;

@SuppressWarnings("javadoc")
public class GenericSupportTest {
    
    @Test
    public void test() {
        val generator = new Generator("Option",
                new Type("functionalj.annotations.uniontype", "GenericSupportTest", "OptionSpec"),
                "spec",
                asList(
                   new Generic("T", "T extends Number", asList(new Type("java.lang", "Number"), new Type("java.io", "Serializable")))
                ),
                asList(
                    new Choice("None"),
                    new Choice("Some", asList(
                        new ChoiceParam("value", new Type("T"))
                    ))),
                asList());
        
        val lines  = generator.lines().stream().filter(Objects::nonNull).collect(Collectors.joining("\n"));
        assertEquals(expected, lines);
    }
    
    private static final String expected =
            "package functionalj.annotations.uniontype;\n" + 
            "\n" + 
            "import functionalj.annotations.uniontype.AbstractUnionType;\n" + 
            "import functionalj.annotations.uniontype.UnionTypeSwitch;\n" + 
            "import functionalj.pipeable.Pipeable;\n" + 
            "import functionalj.result.Result;\n" + 
            "import java.io.Serializable;\n" + 
            "import java.util.function.Consumer;\n" + 
            "import java.util.function.Function;\n" + 
            "import java.util.function.Predicate;\n" + 
            "import java.util.function.Supplier;\n" + 
            "import static functionalj.annotations.uniontype.CheckEquals.checkEquals;\n" + 
            "import static functionalj.annotations.uniontype.UnionTypes.Switch;\n" + 
            "\n" + 
            "@SuppressWarnings({\"javadoc\", \"rawtypes\", \"unchecked\"})\n" + 
            "public abstract class Option<T extends Number> extends AbstractUnionType<Option.OptionFirstSwitch<T>> implements Pipeable<Option<T>> {\n" + 
            "    \n" + 
            "    public static final <T extends Number> Option<T> None() {\n" + 
            "        return None.instance;\n" + 
            "    }\n" + 
            "    public static final <T extends Number> Option<T> Some(T value) {\n" + 
            "        return new Some<T>(value);\n" + 
            "    }\n" + 
            "    \n" + 
            "    \n" + 
            "    private Option() {}\n" + 
            "    public Option<T> __data() throws Exception { return this; }\n" + 
            "    public Result<Option<T>> toResult() { return Result.of(this); }\n" + 
            "    \n" + 
            "    public static final class None<T extends Number> extends Option<T> {\n" + 
            "        private static final None instance = new None();\n" + 
            "        private None() {}\n" + 
            "    }\n" + 
            "    public static final class Some<T extends Number> extends Option<T> {\n" + 
            "        private T value;\n" + 
            "        private Some(T value) {\n" + 
            "            this.value = value;\n" + 
            "        }\n" + 
            "        public T value() { return value; }\n" + 
            "        public Some<T> withValue(T value) { return new Some<T>(value); }\n" + 
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
            "    }\n" + 
            "    public static class OptionSwitchNoneSome<TARGET, T extends Number> extends UnionTypeSwitch<Option<T>, TARGET> {\n" + 
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
            "    }\n" + 
            "    public static class OptionSwitchSome<TARGET, T extends Number> extends UnionTypeSwitch<Option<T>, TARGET> {\n" + 
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
            "        \n" + 
            "        public OptionSwitchSome<TARGET, T> someOf(T aValue, Function<Some<T>, TARGET> theAction) {\n" + 
            "            return some(some -> checkEquals(aValue, some.value), theAction);\n" + 
            "        }\n" + 
            "        public OptionSwitchSome<TARGET, T> someOf(T aValue, Supplier<TARGET> theSupplier) {\n" + 
            "            return some(some -> checkEquals(aValue, some.value), theSupplier);\n" + 
            "        }\n" + 
            "        \n" + 
            "        public OptionSwitchSome<TARGET, T> someOf(Predicate<T> valueCheck, Function<Some<T>, TARGET> theAction) {\n" + 
            "            return some(some -> valueCheck.test(some.value), theAction);\n" + 
            "        }\n" + 
            "        public OptionSwitchSome<TARGET, T> someOf(Predicate<T> valueCheck, Supplier<TARGET> theSupplier) {\n" + 
            "            return some(some -> valueCheck.test(some.value), theSupplier);\n" + 
            "        }\n" + 
            "    }\n" + 
            "    \n" + 
            "    public static final functionalj.annotations.uniontype.generator.model.SourceSpec spec = new functionalj.annotations.uniontype.generator.model.SourceSpec(\"Option\", new functionalj.annotations.uniontype.generator.model.Type(\"functionalj.annotations.uniontype\", \"GenericSupportTest\", \"OptionSpec\", Collections.emptyList()), \"spec\", java.util.Arrays.asList(new functionalj.annotations.uniontype.generator.model.Generic(\"T\", \"T extends Number\", java.util.Arrays.asList(new functionalj.annotations.uniontype.generator.model.Type(\"java.lang\", null, \"Number\", Collections.emptyList()), new functionalj.annotations.uniontype.generator.model.Type(\"java.io\", null, \"Serializable\", Collections.emptyList())))), java.util.Arrays.asList(new functionalj.annotations.uniontype.generator.model.Choice(\"None\", null, Collections.emptyList()), new functionalj.annotations.uniontype.generator.model.Choice(\"Some\", null, java.util.Arrays.asList(new functionalj.annotations.uniontype.generator.model.ChoiceParam(\"value\", new functionalj.annotations.uniontype.generator.model.Type(null, null, \"T\", Collections.emptyList()))))), Collections.emptyList());\n" + 
            "    \n" + 
            "}";
    
}

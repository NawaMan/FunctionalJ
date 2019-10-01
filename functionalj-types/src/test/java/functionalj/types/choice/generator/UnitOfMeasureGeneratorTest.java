package functionalj.types.choice.generator;

import static functionalj.types.choice.generator.model.Method.Kind.STATIC;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;

import java.util.stream.Collectors;

import org.junit.Test;

import functionalj.types.Type;
import functionalj.types.choice.generator.model.Case;
import functionalj.types.choice.generator.model.CaseParam;
import functionalj.types.choice.generator.model.Method;
import lombok.val;

public class UnitOfMeasureGeneratorTest {
    
    public static final functionalj.types.choice.generator.model.SourceSpec spec 
            = new functionalj.types.choice.generator.model.SourceSpec(
                    "Temperature", 
                    new Type(
                            "functionalj.types.choice",
                            "UnitOfMeatureTest",
                            "TemperatureSpec",
                            emptyList()),
                    null,
                    false,
                    null,
                    emptyList(),
                    asList(
                            new Case(
                                    "Celsius",
                                    null,
                                    asList(
                                            new CaseParam(
                                                    "celsius",
                                                    new Type(
                                                        null,
                                                        null,
                                                        "double",
                                                        emptyList()),
                                                    false))),
                            new Case(
                                    "Fahrenheit",
                                    null,
                                    asList(
                                            new CaseParam(
                                                    "fahrenheit",
                                                    new Type(
                                                            null,
                                                            null,
                                                            "double",
                                                            emptyList()),
                                                    false)))),
                    asList(
                            new Method(
                                    STATIC,
                                    "toTemperature",
                                    new Type(
                                            "functionalj.types.choice",
                                            null,
                                            "Temperature",
                                            emptyList()),
                                    emptyList(),
                                    emptyList(),
                                    emptyList()),
                            new Method(
                                    STATIC,
                                    "toFahrenheit",
                                    new Type(
                                            "functionalj.types.choice",
                                            "Temperature",
                                            "Fahrenheit",
                                            emptyList()),
                                    emptyList(),
                                    emptyList(),
                                    emptyList())),
                    emptyList());
    
    @Test
    public void test() {
        val targetClass = new TargetClass(spec);
        val code = targetClass.lines().stream().collect(Collectors.joining("\n"));
        val expect = 
                "package functionalj.types.choice;\n" + 
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
                "// functionalj.types.choice.UnitOfMeatureTest.TemperatureSpec\n" + 
                "\n" + 
                "@SuppressWarnings({\"javadoc\", \"rawtypes\", \"unchecked\"})\n" + 
                "public abstract class Temperature implements IChoice<Temperature.TemperatureFirstSwitch>, Pipeable<Temperature> {\n" + 
                "    \n" + 
                "    public static final Celsius Celsius(double celsius) {\n" + 
                "        return new Celsius(celsius);\n" + 
                "    }\n" + 
                "    public static final Fahrenheit Fahrenheit(double fahrenheit) {\n" + 
                "        return new Fahrenheit(fahrenheit);\n" + 
                "    }\n" + 
                "    \n" + 
                "    \n" + 
                "    public static final TemperatureLens<Temperature> theTemperature = new TemperatureLens<>(LensSpec.of(Temperature.class));\n" + 
                "    public static final TemperatureLens<Temperature> eachTemperature = theTemperature;\n" + 
                "    public static class TemperatureLens<HOST> extends ObjectLensImpl<HOST, Temperature> {\n" + 
                "\n" + 
                "        public final BooleanAccessPrimitive<Temperature> isCelsius = Temperature::isCelsius;\n" + 
                "        public final BooleanAccessPrimitive<Temperature> isFahrenheit = Temperature::isFahrenheit;\n" + 
                "        public final ResultAccess<HOST, Celsius, Celsius.CelsiusLens<HOST>> asCelsius = createSubResultLens(Temperature::asCelsius, (functionalj.lens.core.WriteLens<Temperature,Result<Celsius>>)null, Celsius.CelsiusLens::new);\n" + 
                "        public final ResultAccess<HOST, Fahrenheit, Fahrenheit.FahrenheitLens<HOST>> asFahrenheit = createSubResultLens(Temperature::asFahrenheit, (functionalj.lens.core.WriteLens<Temperature,Result<Fahrenheit>>)null, Fahrenheit.FahrenheitLens::new);\n" + 
                "        public TemperatureLens(LensSpec<HOST, Temperature> spec) {\n" + 
                "            super(spec);\n" + 
                "        }\n" + 
                "    }\n" + 
                "    \n" + 
                "    private Temperature() {}\n" + 
                "    public Temperature __data() throws Exception { return this; }\n" + 
                "    public Result<Temperature> toResult() { return Result.valueOf(this); }\n" + 
                "    \n" + 
                "    public static <T extends Temperature> T fromMap(java.util.Map<String, Object> map) {\n" + 
                "        String __tagged = (String)map.get(\"__tagged\");\n" + 
                "        if (\"Celsius\".equals(__tagged))\n" + 
                "            return (T)Celsius.caseFromMap(map);\n" + 
                "        if (\"Fahrenheit\".equals(__tagged))\n" + 
                "            return (T)Fahrenheit.caseFromMap(map);\n" + 
                "        throw new IllegalArgumentException(\"Tagged value does not represent a valid type: \" + __tagged);\n" + 
                "    }\n" + 
                "    \n" + 
                "    static private functionalj.map.FuncMap<String, java.util.Map<String, functionalj.types.choice.generator.model.CaseParam>> __schema__ = functionalj.map.FuncMap.<String, java.util.Map<String, functionalj.types.choice.generator.model.CaseParam>>newMap()\n" + 
                "        .with(\"Celsius\", Celsius.getCaseSchema())\n" + 
                "        .with(\"Fahrenheit\", Fahrenheit.getCaseSchema())\n" + 
                "        .build();\n" + 
                "    public static java.util.Map<String, java.util.Map<String, functionalj.types.choice.generator.model.CaseParam>> getChoiceSchema() {\n" + 
                "        return __schema__;\n" + 
                "    }\n" + 
                "    \n" + 
                "    public static final class Celsius extends Temperature {\n" + 
                "        public static final Celsius.CelsiusLens<Celsius> theCelsius = new Celsius.CelsiusLens<>(LensSpec.of(Celsius.class));\n" + 
                "        public static final Celsius.CelsiusLens<Celsius> eachCelsius = theCelsius;\n" + 
                "        private double celsius;\n" + 
                "        private Celsius(double celsius) {\n" + 
                "            this.celsius = celsius;\n" + 
                "        }\n" + 
                "        public double celsius() { return celsius; }\n" + 
                "        public Celsius withCelsius(double celsius) { return new Celsius(celsius); }\n" + 
                "        public static class CelsiusLens<HOST> extends ObjectLensImpl<HOST, Temperature.Celsius> {\n" + 
                "            \n" + 
                "            public final DoubleLens<HOST> celsius = createSubLensPrimitive(Temperature.Celsius::celsius, Temperature.Celsius::withCelsius);\n" + 
                "            \n" + 
                "            public CelsiusLens(LensSpec<HOST, Temperature.Celsius> spec) {\n" + 
                "                super(spec);\n" + 
                "            }\n" + 
                "            \n" + 
                "        }\n" + 
                "        public java.util.Map<String, Object> __toMap() {\n" + 
                "            java.util.Map<String, Object> map = new java.util.HashMap<>();\n" + 
                "            map.put(\"__tagged\", functionalj.types.IData.$utils.toMapValueObject(\"Celsius\"));\n" + 
                "            map.put(\"celsius\", this.celsius);\n" + 
                "            return map;\n" + 
                "        }\n" + 
                "        static private functionalj.map.FuncMap<String, functionalj.types.choice.generator.model.CaseParam> __schema__ = functionalj.map.FuncMap.<String, functionalj.types.choice.generator.model.CaseParam>newMap()\n" + 
                "            .with(\"celsius\", new functionalj.types.choice.generator.model.CaseParam(\"celsius\", new functionalj.types.Type(null, null, \"double\", java.util.Collections.emptyList()), false, null))\n" + 
                "            .build();\n" + 
                "        public static java.util.Map<String, functionalj.types.choice.generator.model.CaseParam> getCaseSchema() {\n" + 
                "            return __schema__;\n" + 
                "        }\n" + 
                "        public static Celsius caseFromMap(java.util.Map<String, Object> map) {\n" + 
                "            return Celsius(\n" + 
                "                $utils.propertyFromMap(map, __schema__, \"celsius\")\n" + 
                "            );\n" + 
                "        }\n" + 
                "    }\n" + 
                "    public static final class Fahrenheit extends Temperature {\n" + 
                "        public static final Fahrenheit.FahrenheitLens<Fahrenheit> theFahrenheit = new Fahrenheit.FahrenheitLens<>(LensSpec.of(Fahrenheit.class));\n" + 
                "        public static final Fahrenheit.FahrenheitLens<Fahrenheit> eachFahrenheit = theFahrenheit;\n" + 
                "        private double fahrenheit;\n" + 
                "        private Fahrenheit(double fahrenheit) {\n" + 
                "            this.fahrenheit = fahrenheit;\n" + 
                "        }\n" + 
                "        public double fahrenheit() { return fahrenheit; }\n" + 
                "        public Fahrenheit withFahrenheit(double fahrenheit) { return new Fahrenheit(fahrenheit); }\n" + 
                "        public static class FahrenheitLens<HOST> extends ObjectLensImpl<HOST, Temperature.Fahrenheit> {\n" + 
                "            \n" + 
                "            public final DoubleLens<HOST> fahrenheit = createSubLensPrimitive(Temperature.Fahrenheit::fahrenheit, Temperature.Fahrenheit::withFahrenheit);\n" + 
                "            \n" + 
                "            public FahrenheitLens(LensSpec<HOST, Temperature.Fahrenheit> spec) {\n" + 
                "                super(spec);\n" + 
                "            }\n" + 
                "            \n" + 
                "        }\n" + 
                "        public java.util.Map<String, Object> __toMap() {\n" + 
                "            java.util.Map<String, Object> map = new java.util.HashMap<>();\n" + 
                "            map.put(\"__tagged\", functionalj.types.IData.$utils.toMapValueObject(\"Fahrenheit\"));\n" + 
                "            map.put(\"fahrenheit\", this.fahrenheit);\n" + 
                "            return map;\n" + 
                "        }\n" + 
                "        static private functionalj.map.FuncMap<String, functionalj.types.choice.generator.model.CaseParam> __schema__ = functionalj.map.FuncMap.<String, functionalj.types.choice.generator.model.CaseParam>newMap()\n" + 
                "            .with(\"fahrenheit\", new functionalj.types.choice.generator.model.CaseParam(\"fahrenheit\", new functionalj.types.Type(null, null, \"double\", java.util.Collections.emptyList()), false, null))\n" + 
                "            .build();\n" + 
                "        public static java.util.Map<String, functionalj.types.choice.generator.model.CaseParam> getCaseSchema() {\n" + 
                "            return __schema__;\n" + 
                "        }\n" + 
                "        public static Fahrenheit caseFromMap(java.util.Map<String, Object> map) {\n" + 
                "            return Fahrenheit(\n" + 
                "                $utils.propertyFromMap(map, __schema__, \"fahrenheit\")\n" + 
                "            );\n" + 
                "        }\n" + 
                "    }\n" + 
                "    \n" + 
                "    public java.util.Map<java.lang.String, java.util.Map<java.lang.String, functionalj.types.choice.generator.model.CaseParam>> __getSchema() {\n" + 
                "        return getChoiceSchema();\n" + 
                "    }\n" + 
                "    \n" + 
                "    private final TemperatureFirstSwitch __switch = new TemperatureFirstSwitch(this);\n" + 
                "    @Override public TemperatureFirstSwitch match() {\n" + 
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
                "                    .celsius(celsius -> \"Celsius(\" + String.format(\"%1$s\", celsius.celsius) + \")\")\n" + 
                "                    .fahrenheit(fahrenheit -> \"Fahrenheit(\" + String.format(\"%1$s\", fahrenheit.fahrenheit) + \")\")\n" + 
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
                "        if (!(obj instanceof Temperature))\n" + 
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
                "    public static functionalj.types.choice.Temperature toTemperature() {\n" + 
                "        return functionalj.types.choice.Self.unwrap(functionalj.types.choice.UnitOfMeatureTest.TemperatureSpec.toTemperature());\n" + 
                "    }\n" + 
                "    public static functionalj.types.choice.Temperature.Fahrenheit toFahrenheit() {\n" + 
                "        return functionalj.types.choice.UnitOfMeatureTest.TemperatureSpec.toFahrenheit();\n" + 
                "    }\n" + 
                "    \n" + 
                "    public boolean isCelsius() { return this instanceof Celsius; }\n" + 
                "    public Result<Celsius> asCelsius() { return Result.valueOf(this).filter(Celsius.class).map(Celsius.class::cast); }\n" + 
                "    public Temperature ifCelsius(Consumer<Celsius> action) { if (isCelsius()) action.accept((Celsius)this); return this; }\n" + 
                "    public Temperature ifCelsius(Runnable action) { if (isCelsius()) action.run(); return this; }\n" + 
                "    public boolean isFahrenheit() { return this instanceof Fahrenheit; }\n" + 
                "    public Result<Fahrenheit> asFahrenheit() { return Result.valueOf(this).filter(Fahrenheit.class).map(Fahrenheit.class::cast); }\n" + 
                "    public Temperature ifFahrenheit(Consumer<Fahrenheit> action) { if (isFahrenheit()) action.accept((Fahrenheit)this); return this; }\n" + 
                "    public Temperature ifFahrenheit(Runnable action) { if (isFahrenheit()) action.run(); return this; }\n" + 
                "    \n" + 
                "    public static class TemperatureFirstSwitch {\n" + 
                "        private Temperature $value;\n" + 
                "        private TemperatureFirstSwitch(Temperature theValue) { this.$value = theValue; }\n" + 
                "        public <TARGET> TemperatureFirstSwitchTyped<TARGET> toA(Class<TARGET> clzz) {\n" + 
                "            return new TemperatureFirstSwitchTyped<TARGET>($value);\n" + 
                "        }\n" + 
                "        \n" + 
                "        public <TARGET> TemperatureSwitchFahrenheit<TARGET> celsius(Function<? super Celsius, ? extends TARGET> theAction) {\n" + 
                "            Function<Temperature, TARGET> $action = null;\n" + 
                "            Function<Temperature, TARGET> oldAction = (Function<Temperature, TARGET>)$action;\n" + 
                "            Function<Temperature, TARGET> newAction =\n" + 
                "                ($action != null)\n" + 
                "                ? oldAction : \n" + 
                "                    ($value instanceof Celsius)\n" + 
                "                    ? (Function<Temperature, TARGET>)(d -> theAction.apply((Celsius)d))\n" + 
                "                    : oldAction;\n" + 
                "            \n" + 
                "            return new TemperatureSwitchFahrenheit<TARGET>($value, newAction);\n" + 
                "        }\n" + 
                "        public <TARGET> TemperatureSwitchFahrenheit<TARGET> celsius(Supplier<? extends TARGET> theSupplier) {\n" + 
                "            return celsius(d->theSupplier.get());\n" + 
                "        }\n" + 
                "        public <TARGET> TemperatureSwitchFahrenheit<TARGET> celsius(TARGET theValue) {\n" + 
                "            return celsius(d->theValue);\n" + 
                "        }\n" + 
                "        \n" + 
                "        public <TARGET> TemperatureSwitchCelsiusFahrenheit<TARGET> celsius(Predicate<Celsius> check, Function<? super Celsius, ? extends TARGET> theAction) {\n" + 
                "            Function<Temperature, TARGET> $action = null;\n" + 
                "            Function<Temperature, TARGET> oldAction = (Function<Temperature, TARGET>)$action;\n" + 
                "            Function<Temperature, TARGET> newAction =\n" + 
                "                ($action != null)\n" + 
                "                ? oldAction : \n" + 
                "                    (($value instanceof Celsius) && check.test((Celsius)$value))\n" + 
                "                    ? (Function<Temperature, TARGET>)(d -> theAction.apply((Celsius)d))\n" + 
                "                    : oldAction;\n" + 
                "            \n" + 
                "            return new TemperatureSwitchCelsiusFahrenheit<TARGET>($value, newAction);\n" + 
                "        }\n" + 
                "        public <TARGET> TemperatureSwitchCelsiusFahrenheit<TARGET> celsius(Predicate<Celsius> check, Supplier<? extends TARGET> theSupplier) {\n" + 
                "            return celsius(check, d->theSupplier.get());\n" + 
                "        }\n" + 
                "        public <TARGET> TemperatureSwitchCelsiusFahrenheit<TARGET> celsius(Predicate<Celsius> check, TARGET theValue) {\n" + 
                "            return celsius(check, d->theValue);\n" + 
                "        }\n" + 
                "    }\n" + 
                "    public static class TemperatureFirstSwitchTyped<TARGET> {\n" + 
                "        private Temperature $value;\n" + 
                "        private TemperatureFirstSwitchTyped(Temperature theValue) { this.$value = theValue; }\n" + 
                "        \n" + 
                "        public TemperatureSwitchFahrenheit<TARGET> celsius(Function<? super Celsius, ? extends TARGET> theAction) {\n" + 
                "            Function<Temperature, TARGET> $action = null;\n" + 
                "            Function<Temperature, TARGET> oldAction = (Function<Temperature, TARGET>)$action;\n" + 
                "            Function<Temperature, TARGET> newAction =\n" + 
                "                ($action != null)\n" + 
                "                ? oldAction : \n" + 
                "                    ($value instanceof Celsius)\n" + 
                "                    ? (Function<Temperature, TARGET>)(d -> theAction.apply((Celsius)d))\n" + 
                "                    : oldAction;\n" + 
                "            \n" + 
                "            return new TemperatureSwitchFahrenheit<TARGET>($value, newAction);\n" + 
                "        }\n" + 
                "        public TemperatureSwitchFahrenheit<TARGET> celsius(Supplier<? extends TARGET> theSupplier) {\n" + 
                "            return celsius(d->theSupplier.get());\n" + 
                "        }\n" + 
                "        public TemperatureSwitchFahrenheit<TARGET> celsius(TARGET theValue) {\n" + 
                "            return celsius(d->theValue);\n" + 
                "        }\n" + 
                "        \n" + 
                "        public TemperatureSwitchCelsiusFahrenheit<TARGET> celsius(Predicate<Celsius> check, Function<? super Celsius, ? extends TARGET> theAction) {\n" + 
                "            Function<Temperature, TARGET> $action = null;\n" + 
                "            Function<Temperature, TARGET> oldAction = (Function<Temperature, TARGET>)$action;\n" + 
                "            Function<Temperature, TARGET> newAction =\n" + 
                "                ($action != null)\n" + 
                "                ? oldAction : \n" + 
                "                    (($value instanceof Celsius) && check.test((Celsius)$value))\n" + 
                "                    ? (Function<Temperature, TARGET>)(d -> theAction.apply((Celsius)d))\n" + 
                "                    : oldAction;\n" + 
                "            \n" + 
                "            return new TemperatureSwitchCelsiusFahrenheit<TARGET>($value, newAction);\n" + 
                "        }\n" + 
                "        public TemperatureSwitchCelsiusFahrenheit<TARGET> celsius(Predicate<Celsius> check, Supplier<? extends TARGET> theSupplier) {\n" + 
                "            return celsius(check, d->theSupplier.get());\n" + 
                "        }\n" + 
                "        public TemperatureSwitchCelsiusFahrenheit<TARGET> celsius(Predicate<Celsius> check, TARGET theValue) {\n" + 
                "            return celsius(check, d->theValue);\n" + 
                "        }\n" + 
                "    }\n" + 
                "    public static class TemperatureSwitchCelsiusFahrenheit<TARGET> extends ChoiceTypeSwitch<Temperature, TARGET> {\n" + 
                "        private TemperatureSwitchCelsiusFahrenheit(Temperature theValue, Function<Temperature, ? extends TARGET> theAction) { super(theValue, theAction); }\n" + 
                "        \n" + 
                "        public TemperatureSwitchFahrenheit<TARGET> celsius(Function<? super Celsius, ? extends TARGET> theAction) {\n" + 
                "            Function<Temperature, TARGET> oldAction = (Function<Temperature, TARGET>)$action;\n" + 
                "            Function<Temperature, TARGET> newAction =\n" + 
                "                ($action != null)\n" + 
                "                ? oldAction : \n" + 
                "                    ($value instanceof Celsius)\n" + 
                "                    ? (Function<Temperature, TARGET>)(d -> theAction.apply((Celsius)d))\n" + 
                "                    : oldAction;\n" + 
                "            \n" + 
                "            return new TemperatureSwitchFahrenheit<TARGET>($value, newAction);\n" + 
                "        }\n" + 
                "        public TemperatureSwitchFahrenheit<TARGET> celsius(Supplier<? extends TARGET> theSupplier) {\n" + 
                "            return celsius(d->theSupplier.get());\n" + 
                "        }\n" + 
                "        public TemperatureSwitchFahrenheit<TARGET> celsius(TARGET theValue) {\n" + 
                "            return celsius(d->theValue);\n" + 
                "        }\n" + 
                "        \n" + 
                "        public TemperatureSwitchCelsiusFahrenheit<TARGET> celsius(Predicate<Celsius> check, Function<? super Celsius, ? extends TARGET> theAction) {\n" + 
                "            Function<Temperature, TARGET> oldAction = (Function<Temperature, TARGET>)$action;\n" + 
                "            Function<Temperature, TARGET> newAction =\n" + 
                "                ($action != null)\n" + 
                "                ? oldAction : \n" + 
                "                    (($value instanceof Celsius) && check.test((Celsius)$value))\n" + 
                "                    ? (Function<Temperature, TARGET>)(d -> theAction.apply((Celsius)d))\n" + 
                "                    : oldAction;\n" + 
                "            \n" + 
                "            return new TemperatureSwitchCelsiusFahrenheit<TARGET>($value, newAction);\n" + 
                "        }\n" + 
                "        public TemperatureSwitchCelsiusFahrenheit<TARGET> celsius(Predicate<Celsius> check, Supplier<? extends TARGET> theSupplier) {\n" + 
                "            return celsius(check, d->theSupplier.get());\n" + 
                "        }\n" + 
                "        public TemperatureSwitchCelsiusFahrenheit<TARGET> celsius(Predicate<Celsius> check, TARGET theValue) {\n" + 
                "            return celsius(check, d->theValue);\n" + 
                "        }\n" + 
                "    }\n" + 
                "    public static class TemperatureSwitchFahrenheit<TARGET> extends ChoiceTypeSwitch<Temperature, TARGET> {\n" + 
                "        private TemperatureSwitchFahrenheit(Temperature theValue, Function<Temperature, ? extends TARGET> theAction) { super(theValue, theAction); }\n" + 
                "        \n" + 
                "        public TARGET fahrenheit(Function<? super Fahrenheit, ? extends TARGET> theAction) {\n" + 
                "            Function<Temperature, TARGET> oldAction = (Function<Temperature, TARGET>)$action;\n" + 
                "            Function<Temperature, TARGET> newAction =\n" + 
                "                ($action != null)\n" + 
                "                ? oldAction : \n" + 
                "                    ($value instanceof Fahrenheit)\n" + 
                "                    ? (Function<Temperature, TARGET>)(d -> theAction.apply((Fahrenheit)d))\n" + 
                "                    : oldAction;\n" + 
                "            \n" + 
                "            return newAction.apply($value);\n" + 
                "        }\n" + 
                "        public TARGET fahrenheit(Supplier<? extends TARGET> theSupplier) {\n" + 
                "            return fahrenheit(d->theSupplier.get());\n" + 
                "        }\n" + 
                "        public TARGET fahrenheit(TARGET theValue) {\n" + 
                "            return fahrenheit(d->theValue);\n" + 
                "        }\n" + 
                "        \n" + 
                "        public TemperatureSwitchFahrenheit<TARGET> fahrenheit(Predicate<Fahrenheit> check, Function<? super Fahrenheit, ? extends TARGET> theAction) {\n" + 
                "            Function<Temperature, TARGET> oldAction = (Function<Temperature, TARGET>)$action;\n" + 
                "            Function<Temperature, TARGET> newAction =\n" + 
                "                ($action != null)\n" + 
                "                ? oldAction : \n" + 
                "                    (($value instanceof Fahrenheit) && check.test((Fahrenheit)$value))\n" + 
                "                    ? (Function<Temperature, TARGET>)(d -> theAction.apply((Fahrenheit)d))\n" + 
                "                    : oldAction;\n" + 
                "            \n" + 
                "            return new TemperatureSwitchFahrenheit<TARGET>($value, newAction);\n" + 
                "        }\n" + 
                "        public TemperatureSwitchFahrenheit<TARGET> fahrenheit(Predicate<Fahrenheit> check, Supplier<? extends TARGET> theSupplier) {\n" + 
                "            return fahrenheit(check, d->theSupplier.get());\n" + 
                "        }\n" + 
                "        public TemperatureSwitchFahrenheit<TARGET> fahrenheit(Predicate<Fahrenheit> check, TARGET theValue) {\n" + 
                "            return fahrenheit(check, d->theValue);\n" + 
                "        }\n" + 
                "    }\n" + 
                "    \n" + 
                "    \n" + 
                "    \n" + 
                "}";
        assertEquals(expect, code);
    }

}

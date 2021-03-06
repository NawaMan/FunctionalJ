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
import functionalj.types.choice.generator.model.SourceSpec;
import lombok.val;

public class ChoiceWtihOptionalParameterTest {

    public static final SourceSpec spec = new SourceSpec("LoginStatus", 
            new Type("example.functionalj.elm", "ElmExamples", "LoginStatus", emptyList()), 
            null, 
            false, 
            "__tagged", 
            emptyList(), 
            asList(new Case("Loggined", null, asList(
                        new CaseParam(
                                "wealth", 
                                new Type("java.util", null, "Optional", asList(new Generic("java.lang.Double", "java.lang.Double", null))), 
                                true, 
                                null)
                   )), 
                   new Case("LoggedOut", null, emptyList())
            ), 
            emptyList(), 
            asList("User"));
    
    @Test
    public void testChoiceWithOptionalDouble() {
        val target = new TargetClass(spec);
        val lines  = target.lines().stream().filter(Objects::nonNull).collect(Collectors.joining("\n"));
        assertEquals(expected, lines);
    }
    
    private static final String expected = 
            "package example.functionalj.elm;\n" + 
            "\n" + 
            "import functionalj.lens.core.LensSpec;\n" + 
            "import functionalj.lens.lenses.*;\n" + 
            "import functionalj.pipeable.Pipeable;\n" + 
            "import functionalj.result.Result;\n" + 
            "import functionalj.types.choice.ChoiceTypeSwitch;\n" + 
            "import functionalj.types.choice.IChoice;\n" + 
            "import java.util.Optional;\n" + 
            "import java.util.function.Consumer;\n" + 
            "import java.util.function.Function;\n" + 
            "import java.util.function.Predicate;\n" + 
            "import java.util.function.Supplier;\n" + 
            "\n" + 
            "// example.functionalj.elm.ElmExamples.LoginStatus\n" + 
            "\n" + 
            "@SuppressWarnings({\"javadoc\", \"rawtypes\", \"unchecked\"})\n" + 
            "public abstract class LoginStatus implements IChoice<LoginStatus.LoginStatusFirstSwitch>, Pipeable<LoginStatus> {\n" + 
            "    \n" + 
            "    public static final Loggined Loggined(Optional<java.lang.Double> wealth) {\n" + 
            "        return new Loggined(wealth);\n" + 
            "    }\n" + 
            "    public static final LoggedOut loggedOut = LoggedOut.instance;\n" + 
            "    public static final LoggedOut LoggedOut() {\n" + 
            "        return LoggedOut.instance;\n" + 
            "    }\n" + 
            "    \n" + 
            "    \n" + 
            "    public static final LoginStatusLens<LoginStatus> theLoginStatus = new LoginStatusLens<>(LensSpec.of(LoginStatus.class));\n" + 
            "    public static final LoginStatusLens<LoginStatus> eachLoginStatus = theLoginStatus;\n" + 
            "    public static class LoginStatusLens<HOST> extends ObjectLensImpl<HOST, LoginStatus> {\n" + 
            "\n" + 
            "        public final BooleanAccess<LoginStatus> isLoggined = LoginStatus::isLoggined;\n" + 
            "        public final BooleanAccess<LoginStatus> isLoggedOut = LoginStatus::isLoggedOut;\n" + 
            "        public final ResultAccess<HOST, Loggined, Loggined.LogginedLens<HOST>> asLoggined = createSubResultLens(LoginStatus::asLoggined, (functionalj.lens.core.WriteLens<LoginStatus,Result<Loggined>>)null, Loggined.LogginedLens::new);\n" + 
            "        public final ResultAccess<HOST, LoggedOut, LoggedOut.LoggedOutLens<HOST>> asLoggedOut = createSubResultLens(LoginStatus::asLoggedOut, (functionalj.lens.core.WriteLens<LoginStatus,Result<LoggedOut>>)null, LoggedOut.LoggedOutLens::new);\n" + 
            "        public LoginStatusLens(LensSpec<HOST, LoginStatus> spec) {\n" + 
            "            super(spec);\n" + 
            "        }\n" + 
            "    }\n" + 
            "    \n" + 
            "    private LoginStatus() {}\n" + 
            "    public LoginStatus __data() throws Exception { return this; }\n" + 
            "    public Result<LoginStatus> toResult() { return Result.valueOf(this); }\n" + 
            "    \n" + 
            "    public static <T extends LoginStatus> T fromMap(java.util.Map<String, Object> map) {\n" + 
            "        String __tagged = (String)map.get(\"__tagged\");\n" + 
            "        if (\"Loggined\".equals(__tagged))\n" + 
            "            return (T)Loggined.caseFromMap(map);\n" + 
            "        if (\"LoggedOut\".equals(__tagged))\n" + 
            "            return (T)LoggedOut.caseFromMap(map);\n" + 
            "        throw new IllegalArgumentException(\"Tagged value does not represent a valid type: \" + __tagged);\n" + 
            "    }\n" + 
            "    \n" + 
            "    static private functionalj.map.FuncMap<String, java.util.Map<String, functionalj.types.choice.generator.model.CaseParam>> __schema__ = functionalj.map.FuncMap.<String, java.util.Map<String, functionalj.types.choice.generator.model.CaseParam>>newMap()\n" + 
            "        .with(\"Loggined\", Loggined.getCaseSchema())\n" + 
            "        .with(\"LoggedOut\", LoggedOut.getCaseSchema())\n" + 
            "        .build();\n" + 
            "    public static java.util.Map<String, java.util.Map<String, functionalj.types.choice.generator.model.CaseParam>> getChoiceSchema() {\n" + 
            "        return __schema__;\n" + 
            "    }\n" + 
            "    \n" + 
            "    public static final class Loggined extends LoginStatus {\n" + 
            "        public static final Loggined.LogginedLens<Loggined> theLoggined = new Loggined.LogginedLens<>(LensSpec.of(Loggined.class));\n" + 
            "        public static final Loggined.LogginedLens<Loggined> eachLoggined = theLoggined;\n" + 
            "        private Optional<java.lang.Double> wealth;\n" + 
            "        private Loggined(Optional<java.lang.Double> wealth) {\n" + 
            "            this.wealth = wealth;\n" + 
            "        }\n" + 
            "        public Optional<java.lang.Double> wealth() { return wealth; }\n" + 
            "        public Loggined withWealth(Optional<java.lang.Double> wealth) { return new Loggined(wealth); }\n" + 
            "        public static class LogginedLens<HOST> extends ObjectLensImpl<HOST, LoginStatus.Loggined> {\n" + 
            "            \n" + 
            "            public final OptionalLens<HOST, java.lang.Double, DoubleLens<HOST>> wealth = createSubOptionalLens(LoginStatus.Loggined::wealth, LoginStatus.Loggined::withWealth, DoubleLens::of);\n" + 
            "            \n" + 
            "            public LogginedLens(LensSpec<HOST, LoginStatus.Loggined> spec) {\n" + 
            "                super(spec);\n" + 
            "            }\n" + 
            "            \n" + 
            "        }\n" + 
            "        public java.util.Map<String, Object> __toMap() {\n" + 
            "            java.util.Map<String, Object> map = new java.util.HashMap<>();\n" + 
            "            map.put(\"__tagged\", functionalj.types.IData.$utils.toMapValueObject(\"Loggined\"));\n" + 
            "            map.put(\"wealth\", this.wealth);\n" + 
            "            return map;\n" + 
            "        }\n" + 
            "        static private functionalj.map.FuncMap<String, functionalj.types.choice.generator.model.CaseParam> __schema__ = functionalj.map.FuncMap.<String, functionalj.types.choice.generator.model.CaseParam>newMap()\n" + 
            "            .with(\"wealth\", new functionalj.types.choice.generator.model.CaseParam(\"wealth\", new functionalj.types.Type(\"java.util\", null, \"Optional\", java.util.Arrays.asList(new functionalj.types.Generic(\"java.lang.Double\", \"java.lang.Double\", java.util.Collections.emptyList()))), true, null))\n" + 
            "            .build();\n" + 
            "        public static java.util.Map<String, functionalj.types.choice.generator.model.CaseParam> getCaseSchema() {\n" + 
            "            return __schema__;\n" + 
            "        }\n" + 
            "        public static Loggined caseFromMap(java.util.Map<String, Object> map) {\n" + 
            "            return Loggined(\n" + 
            "                $utils.propertyFromMap(map, __schema__, \"wealth\")\n" + 
            "            );\n" + 
            "        }\n" + 
            "    }\n" + 
            "    public static final class LoggedOut extends LoginStatus {\n" + 
            "        public static final LoggedOut.LoggedOutLens<LoggedOut> theLoggedOut = new LoggedOut.LoggedOutLens<>(LensSpec.of(LoggedOut.class));\n" + 
            "        public static final LoggedOut.LoggedOutLens<LoggedOut> eachLoggedOut = theLoggedOut;\n" + 
            "        private static final LoggedOut instance = new LoggedOut();\n" + 
            "        private LoggedOut() {}\n" + 
            "        public static class LoggedOutLens<HOST> extends ObjectLensImpl<HOST, LoginStatus.LoggedOut> {\n" + 
            "            \n" + 
            "            public LoggedOutLens(LensSpec<HOST, LoginStatus.LoggedOut> spec) {\n" + 
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
            "        public static LoggedOut caseFromMap(java.util.Map<String, Object> map) {\n" + 
            "            return LoggedOut(\n" + 
            "            );\n" + 
            "        }\n" + 
            "    }\n" + 
            "    \n" + 
            "    public java.util.Map<java.lang.String, java.util.Map<java.lang.String, functionalj.types.choice.generator.model.CaseParam>> __getSchema() {\n" + 
            "        return getChoiceSchema();\n" + 
            "    }\n" + 
            "    \n" + 
            "    private final LoginStatusFirstSwitch __switch = new LoginStatusFirstSwitch(this);\n" + 
            "    @Override public LoginStatusFirstSwitch match() {\n" + 
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
            "                    .loggined(loggined -> \"Loggined(\" + String.format(\"%1$s\", loggined.wealth) + \")\")\n" + 
            "                    .loggedOut(__ -> \"LoggedOut\")\n" + 
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
            "        if (!(obj instanceof LoginStatus))\n" + 
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
            "    public boolean isLoggined() { return this instanceof Loggined; }\n" + 
            "    public Result<Loggined> asLoggined() { return Result.valueOf(this).filter(Loggined.class).map(Loggined.class::cast); }\n" + 
            "    public LoginStatus ifLoggined(Consumer<Loggined> action) { if (isLoggined()) action.accept((Loggined)this); return this; }\n" + 
            "    public LoginStatus ifLoggined(Runnable action) { if (isLoggined()) action.run(); return this; }\n" + 
            "    public boolean isLoggedOut() { return this instanceof LoggedOut; }\n" + 
            "    public Result<LoggedOut> asLoggedOut() { return Result.valueOf(this).filter(LoggedOut.class).map(LoggedOut.class::cast); }\n" + 
            "    public LoginStatus ifLoggedOut(Consumer<LoggedOut> action) { if (isLoggedOut()) action.accept((LoggedOut)this); return this; }\n" + 
            "    public LoginStatus ifLoggedOut(Runnable action) { if (isLoggedOut()) action.run(); return this; }\n" + 
            "    \n" + 
            "    public static class LoginStatusFirstSwitch {\n" + 
            "        private LoginStatus $value;\n" + 
            "        private LoginStatusFirstSwitch(LoginStatus theValue) { this.$value = theValue; }\n" + 
            "        public <TARGET> LoginStatusFirstSwitchTyped<TARGET> toA(Class<TARGET> clzz) {\n" + 
            "            return new LoginStatusFirstSwitchTyped<TARGET>($value);\n" + 
            "        }\n" + 
            "        \n" + 
            "        public <TARGET> LoginStatusSwitchLoggedOut<TARGET> loggined(Function<? super Loggined, ? extends TARGET> theAction) {\n" + 
            "            Function<LoginStatus, TARGET> $action = null;\n" + 
            "            Function<LoginStatus, TARGET> oldAction = (Function<LoginStatus, TARGET>)$action;\n" + 
            "            Function<LoginStatus, TARGET> newAction =\n" + 
            "                ($action != null)\n" + 
            "                ? oldAction : \n" + 
            "                    ($value instanceof Loggined)\n" + 
            "                    ? (Function<LoginStatus, TARGET>)(d -> theAction.apply((Loggined)d))\n" + 
            "                    : oldAction;\n" + 
            "            \n" + 
            "            return new LoginStatusSwitchLoggedOut<TARGET>($value, newAction);\n" + 
            "        }\n" + 
            "        public <TARGET> LoginStatusSwitchLoggedOut<TARGET> loggined(Supplier<? extends TARGET> theSupplier) {\n" + 
            "            return loggined(d->theSupplier.get());\n" + 
            "        }\n" + 
            "        public <TARGET> LoginStatusSwitchLoggedOut<TARGET> loggined(TARGET theValue) {\n" + 
            "            return loggined(d->theValue);\n" + 
            "        }\n" + 
            "        \n" + 
            "        public <TARGET> LoginStatusSwitchLogginedLoggedOut<TARGET> loggined(Predicate<Loggined> check, Function<? super Loggined, ? extends TARGET> theAction) {\n" + 
            "            Function<LoginStatus, TARGET> $action = null;\n" + 
            "            Function<LoginStatus, TARGET> oldAction = (Function<LoginStatus, TARGET>)$action;\n" + 
            "            Function<LoginStatus, TARGET> newAction =\n" + 
            "                ($action != null)\n" + 
            "                ? oldAction : \n" + 
            "                    (($value instanceof Loggined) && check.test((Loggined)$value))\n" + 
            "                    ? (Function<LoginStatus, TARGET>)(d -> theAction.apply((Loggined)d))\n" + 
            "                    : oldAction;\n" + 
            "            \n" + 
            "            return new LoginStatusSwitchLogginedLoggedOut<TARGET>($value, newAction);\n" + 
            "        }\n" + 
            "        public <TARGET> LoginStatusSwitchLogginedLoggedOut<TARGET> loggined(Predicate<Loggined> check, Supplier<? extends TARGET> theSupplier) {\n" + 
            "            return loggined(check, d->theSupplier.get());\n" + 
            "        }\n" + 
            "        public <TARGET> LoginStatusSwitchLogginedLoggedOut<TARGET> loggined(Predicate<Loggined> check, TARGET theValue) {\n" + 
            "            return loggined(check, d->theValue);\n" + 
            "        }\n" + 
            "    }\n" + 
            "    public static class LoginStatusFirstSwitchTyped<TARGET> {\n" + 
            "        private LoginStatus $value;\n" + 
            "        private LoginStatusFirstSwitchTyped(LoginStatus theValue) { this.$value = theValue; }\n" + 
            "        \n" + 
            "        public LoginStatusSwitchLoggedOut<TARGET> loggined(Function<? super Loggined, ? extends TARGET> theAction) {\n" + 
            "            Function<LoginStatus, TARGET> $action = null;\n" + 
            "            Function<LoginStatus, TARGET> oldAction = (Function<LoginStatus, TARGET>)$action;\n" + 
            "            Function<LoginStatus, TARGET> newAction =\n" + 
            "                ($action != null)\n" + 
            "                ? oldAction : \n" + 
            "                    ($value instanceof Loggined)\n" + 
            "                    ? (Function<LoginStatus, TARGET>)(d -> theAction.apply((Loggined)d))\n" + 
            "                    : oldAction;\n" + 
            "            \n" + 
            "            return new LoginStatusSwitchLoggedOut<TARGET>($value, newAction);\n" + 
            "        }\n" + 
            "        public LoginStatusSwitchLoggedOut<TARGET> loggined(Supplier<? extends TARGET> theSupplier) {\n" + 
            "            return loggined(d->theSupplier.get());\n" + 
            "        }\n" + 
            "        public LoginStatusSwitchLoggedOut<TARGET> loggined(TARGET theValue) {\n" + 
            "            return loggined(d->theValue);\n" + 
            "        }\n" + 
            "        \n" + 
            "        public LoginStatusSwitchLogginedLoggedOut<TARGET> loggined(Predicate<Loggined> check, Function<? super Loggined, ? extends TARGET> theAction) {\n" + 
            "            Function<LoginStatus, TARGET> $action = null;\n" + 
            "            Function<LoginStatus, TARGET> oldAction = (Function<LoginStatus, TARGET>)$action;\n" + 
            "            Function<LoginStatus, TARGET> newAction =\n" + 
            "                ($action != null)\n" + 
            "                ? oldAction : \n" + 
            "                    (($value instanceof Loggined) && check.test((Loggined)$value))\n" + 
            "                    ? (Function<LoginStatus, TARGET>)(d -> theAction.apply((Loggined)d))\n" + 
            "                    : oldAction;\n" + 
            "            \n" + 
            "            return new LoginStatusSwitchLogginedLoggedOut<TARGET>($value, newAction);\n" + 
            "        }\n" + 
            "        public LoginStatusSwitchLogginedLoggedOut<TARGET> loggined(Predicate<Loggined> check, Supplier<? extends TARGET> theSupplier) {\n" + 
            "            return loggined(check, d->theSupplier.get());\n" + 
            "        }\n" + 
            "        public LoginStatusSwitchLogginedLoggedOut<TARGET> loggined(Predicate<Loggined> check, TARGET theValue) {\n" + 
            "            return loggined(check, d->theValue);\n" + 
            "        }\n" + 
            "    }\n" + 
            "    public static class LoginStatusSwitchLogginedLoggedOut<TARGET> extends ChoiceTypeSwitch<LoginStatus, TARGET> {\n" + 
            "        private LoginStatusSwitchLogginedLoggedOut(LoginStatus theValue, Function<LoginStatus, ? extends TARGET> theAction) { super(theValue, theAction); }\n" + 
            "        \n" + 
            "        public LoginStatusSwitchLoggedOut<TARGET> loggined(Function<? super Loggined, ? extends TARGET> theAction) {\n" + 
            "            Function<LoginStatus, TARGET> oldAction = (Function<LoginStatus, TARGET>)$action;\n" + 
            "            Function<LoginStatus, TARGET> newAction =\n" + 
            "                ($action != null)\n" + 
            "                ? oldAction : \n" + 
            "                    ($value instanceof Loggined)\n" + 
            "                    ? (Function<LoginStatus, TARGET>)(d -> theAction.apply((Loggined)d))\n" + 
            "                    : oldAction;\n" + 
            "            \n" + 
            "            return new LoginStatusSwitchLoggedOut<TARGET>($value, newAction);\n" + 
            "        }\n" + 
            "        public LoginStatusSwitchLoggedOut<TARGET> loggined(Supplier<? extends TARGET> theSupplier) {\n" + 
            "            return loggined(d->theSupplier.get());\n" + 
            "        }\n" + 
            "        public LoginStatusSwitchLoggedOut<TARGET> loggined(TARGET theValue) {\n" + 
            "            return loggined(d->theValue);\n" + 
            "        }\n" + 
            "        \n" + 
            "        public LoginStatusSwitchLogginedLoggedOut<TARGET> loggined(Predicate<Loggined> check, Function<? super Loggined, ? extends TARGET> theAction) {\n" + 
            "            Function<LoginStatus, TARGET> oldAction = (Function<LoginStatus, TARGET>)$action;\n" + 
            "            Function<LoginStatus, TARGET> newAction =\n" + 
            "                ($action != null)\n" + 
            "                ? oldAction : \n" + 
            "                    (($value instanceof Loggined) && check.test((Loggined)$value))\n" + 
            "                    ? (Function<LoginStatus, TARGET>)(d -> theAction.apply((Loggined)d))\n" + 
            "                    : oldAction;\n" + 
            "            \n" + 
            "            return new LoginStatusSwitchLogginedLoggedOut<TARGET>($value, newAction);\n" + 
            "        }\n" + 
            "        public LoginStatusSwitchLogginedLoggedOut<TARGET> loggined(Predicate<Loggined> check, Supplier<? extends TARGET> theSupplier) {\n" + 
            "            return loggined(check, d->theSupplier.get());\n" + 
            "        }\n" + 
            "        public LoginStatusSwitchLogginedLoggedOut<TARGET> loggined(Predicate<Loggined> check, TARGET theValue) {\n" + 
            "            return loggined(check, d->theValue);\n" + 
            "        }\n" + 
            "    }\n" + 
            "    public static class LoginStatusSwitchLoggedOut<TARGET> extends ChoiceTypeSwitch<LoginStatus, TARGET> {\n" + 
            "        private LoginStatusSwitchLoggedOut(LoginStatus theValue, Function<LoginStatus, ? extends TARGET> theAction) { super(theValue, theAction); }\n" + 
            "        \n" + 
            "        public TARGET loggedOut(Function<? super LoggedOut, ? extends TARGET> theAction) {\n" + 
            "            Function<LoginStatus, TARGET> oldAction = (Function<LoginStatus, TARGET>)$action;\n" + 
            "            Function<LoginStatus, TARGET> newAction =\n" + 
            "                ($action != null)\n" + 
            "                ? oldAction : \n" + 
            "                    ($value instanceof LoggedOut)\n" + 
            "                    ? (Function<LoginStatus, TARGET>)(d -> theAction.apply((LoggedOut)d))\n" + 
            "                    : oldAction;\n" + 
            "            \n" + 
            "            return newAction.apply($value);\n" + 
            "        }\n" + 
            "        public TARGET loggedOut(Supplier<? extends TARGET> theSupplier) {\n" + 
            "            return loggedOut(d->theSupplier.get());\n" + 
            "        }\n" + 
            "        public TARGET loggedOut(TARGET theValue) {\n" + 
            "            return loggedOut(d->theValue);\n" + 
            "        }\n" + 
            "    }\n" + 
            "    \n" + 
            "    \n" + 
            "    \n" + 
            "}";
    
}

package example.functionalj.elm;

import example.functionalj.elm.User;
import functionalj.lens.core.LensSpec;
import functionalj.lens.lenses.*;
import functionalj.list.FuncList;
import functionalj.pipeable.Pipeable;
import functionalj.result.Result;
import functionalj.types.Absent;
import functionalj.types.choice.ChoiceTypeSwitch;
import functionalj.types.choice.IChoice;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

// example.functionalj.elm.ElmExamples.LoginStatus

@SuppressWarnings({"javadoc", "rawtypes", "unchecked"})
public abstract class LoginStatus implements IChoice<LoginStatus.LoginStatusFirstSwitch>, Pipeable<LoginStatus> {
    
    public static final Loggined Loggined(String name, int age, FuncList<java.lang.Integer> years, Optional<java.lang.Double> wealth, User user) {
        return new Loggined(name, age, years, wealth, user);
    }
    public static final LoggedOut loggedOut = LoggedOut.instance;
    public static final LoggedOut LoggedOut() {
        return LoggedOut.instance;
    }
    
    
    public static final LoginStatusLens<LoginStatus> theLoginStatus = new LoginStatusLens<>(LensSpec.of(LoginStatus.class));
    public static class LoginStatusLens<HOST> extends ObjectLensImpl<HOST, LoginStatus> {

        public final BooleanAccess<LoginStatus> isLoggined = LoginStatus::isLoggined;
        public final BooleanAccess<LoginStatus> isLoggedOut = LoginStatus::isLoggedOut;
        public final ResultAccess<HOST, Loggined, Loggined.LogginedLens<HOST>> asLoggined = createSubResultLens(LoginStatus::asLoggined, null, Loggined.LogginedLens::new);
        public final ResultAccess<HOST, LoggedOut, LoggedOut.LoggedOutLens<HOST>> asLoggedOut = createSubResultLens(LoginStatus::asLoggedOut, null, LoggedOut.LoggedOutLens::new);
        public LoginStatusLens(LensSpec<HOST, LoginStatus> spec) {
            super(spec);
        }
    }
    
    private LoginStatus() {}
    public LoginStatus __data() throws Exception { return this; }
    public Result<LoginStatus> toResult() { return Result.valueOf(this); }
    
    public static <T extends LoginStatus> T fromMap(java.util.Map<String, Object> map) {
        String __tagged = (String)map.get("__tagged");
        if ("Loggined".equals(__tagged))
            return (T)Loggined.caseFromMap(map);
        if ("LoggedOut".equals(__tagged))
            return (T)LoggedOut.caseFromMap(map);
        throw new IllegalArgumentException("Tagged value does not represent a valid type: " + __tagged);
    }
    
    static private functionalj.map.FuncMap<String, java.util.Map<String, functionalj.types.choice.generator.model.CaseParam>> __schema__ = functionalj.map.FuncMap.<String, java.util.Map<String, functionalj.types.choice.generator.model.CaseParam>>newMap()
        .with("Loggined", Loggined.getCaseSchema())
        .with("LoggedOut", LoggedOut.getCaseSchema())
        .build();
    public static java.util.Map<String, java.util.Map<String, functionalj.types.choice.generator.model.CaseParam>> getChoiceSchema() {
        return __schema__;
    }
    
    public static final class Loggined extends LoginStatus {
        public static final Loggined.LogginedLens<Loggined> theLoggined = new Loggined.LogginedLens<>(LensSpec.of(Loggined.class));
        private String name;
        private int age;
        private FuncList<java.lang.Integer> years;
        private Optional<java.lang.Double> wealth;
        private User user;
        private Loggined(String name, int age, FuncList<java.lang.Integer> years, Optional<java.lang.Double> wealth, User user) {
            this.name = name;
            this.age = age;
            this.years = years;
            this.wealth = wealth;
            this.user = user;
        }
        public String name() { return name; }
        public int age() { return age; }
        public FuncList<java.lang.Integer> years() { return years; }
        public Optional<java.lang.Double> wealth() { return wealth; }
        public User user() { return user; }
        public Loggined withName(String name) { return new Loggined(name, age, years, wealth, user); }
        public Loggined withAge(int age) { return new Loggined(name, age, years, wealth, user); }
        public Loggined withYears(FuncList<java.lang.Integer> years) { return new Loggined(name, age, years, wealth, user); }
        public Loggined withWealth(Optional<java.lang.Double> wealth) { return new Loggined(name, age, years, wealth, user); }
        public Loggined withUser(User user) { return new Loggined(name, age, years, wealth, user); }
        public static class LogginedLens<HOST> extends ObjectLensImpl<HOST, LoginStatus.Loggined> {
            
            public final StringLens<HOST> name = (StringLens)createSubLens(LoginStatus.Loggined::name, LoginStatus.Loggined::withName, StringLens::of);
            public final IntegerLens<HOST> age = (IntegerLens)createSubLens(LoginStatus.Loggined::age, LoginStatus.Loggined::withAge, IntegerLens::of);
            public final FuncListLens<HOST, java.lang.Integer, IntegerLens<HOST>> years = createSubFuncListLens(LoginStatus.Loggined::years, LoginStatus.Loggined::withYears, IntegerLens::of);
            public final OptionalLens<HOST, java.lang.Double, DoubleLens<HOST>> wealth = createSubOptionalLens(LoginStatus.Loggined::wealth, LoginStatus.Loggined::withWealth, DoubleLens::of);
            public final User.UserLens<HOST> user = (User.UserLens)createSubLens(LoginStatus.Loggined::user, LoginStatus.Loggined::withUser, User.UserLens::new);
            
            public LogginedLens(LensSpec<HOST, LoginStatus.Loggined> spec) {
                super(spec);
            }
            
        }
        public java.util.Map<String, Object> __toMap() {
            java.util.Map<String, Object> map = new java.util.HashMap<>();
            map.put("__tagged", functionalj.types.IData.$utils.toMapValueObject("Loggined"));
            map.put("name", this.name);
            map.put("age", this.age);
            map.put("years", this.years);
            map.put("wealth", this.wealth);
            map.put("user", this.user);
            return map;
        }
        static private functionalj.map.FuncMap<String, functionalj.types.choice.generator.model.CaseParam> __schema__ = functionalj.map.FuncMap.<String, functionalj.types.choice.generator.model.CaseParam>newMap()
            .with("name", new functionalj.types.choice.generator.model.CaseParam("name", new functionalj.types.Type("java.lang", null, "String", java.util.Collections.emptyList()), true, null))
            .with("age", new functionalj.types.choice.generator.model.CaseParam("age", new functionalj.types.Type(null, null, "int", java.util.Collections.emptyList()), true, null))
            .with("years", new functionalj.types.choice.generator.model.CaseParam("years", new functionalj.types.Type("functionalj.list", null, "FuncList", java.util.Arrays.asList(new functionalj.types.Generic("java.lang.Integer", "java.lang.Integer", java.util.Arrays.asList(new functionalj.types.Type(null, null, "java.lang.Integer", java.util.Collections.emptyList()))))), true, null))
            .with("wealth", new functionalj.types.choice.generator.model.CaseParam("wealth", new functionalj.types.Type("java.util", null, "Optional", java.util.Arrays.asList(new functionalj.types.Generic("java.lang.Double", "java.lang.Double", java.util.Arrays.asList(new functionalj.types.Type(null, null, "java.lang.Double", java.util.Collections.emptyList()))))), true, null))
            .with("user", new functionalj.types.choice.generator.model.CaseParam("user", new functionalj.types.Type("example.functionalj.elm", null, "User", java.util.Collections.emptyList()), true, null))
            .build();
        public static java.util.Map<String, functionalj.types.choice.generator.model.CaseParam> getCaseSchema() {
            return __schema__;
        }
        public static Loggined caseFromMap(java.util.Map<String, Object> map) {
            return Loggined(
                $utils.propertyFromMap(map, __schema__, "name"),
                $utils.propertyFromMap(map, __schema__, "age"),
                $utils.propertyFromMap(map, __schema__, "years"),
                $utils.propertyFromMap(map, __schema__, "wealth"),
                $utils.propertyFromMap(map, __schema__, "user")
            );
        }
    }
    public static final class LoggedOut extends LoginStatus {
        public static final LoggedOut.LoggedOutLens<LoggedOut> theLoggedOut = new LoggedOut.LoggedOutLens<>(LensSpec.of(LoggedOut.class));
        private static final LoggedOut instance = new LoggedOut();
        private LoggedOut() {}
        public static class LoggedOutLens<HOST> extends ObjectLensImpl<HOST, LoginStatus.LoggedOut> {
            
            public LoggedOutLens(LensSpec<HOST, LoginStatus.LoggedOut> spec) {
                super(spec);
            }
            
        }
        public java.util.Map<String, Object> __toMap() {
            return functionalj.map.FuncMap.empty();
        }
        static private functionalj.map.FuncMap<String, functionalj.types.choice.generator.model.CaseParam> __schema__ = functionalj.map.FuncMap.<String, functionalj.types.choice.generator.model.CaseParam>empty();
        public static java.util.Map<String, functionalj.types.choice.generator.model.CaseParam> getCaseSchema() {
            return __schema__;
        }
        public static LoggedOut caseFromMap(java.util.Map<String, Object> map) {
            return LoggedOut(
            );
        }
    }
    
    public java.util.Map<java.lang.String, java.util.Map<java.lang.String, functionalj.types.choice.generator.model.CaseParam>> __getSchema() {
        return getChoiceSchema();
    }
    
    private final LoginStatusFirstSwitch __switch = new LoginStatusFirstSwitch(this);
    @Override public LoginStatusFirstSwitch match() {
         return __switch;
    }
    
    private volatile String toString = null;
    @Override
    public String toString() {
        if (toString != null)
            return toString;
        synchronized(this) {
            if (toString != null)
                return toString;
            toString = $utils.Match(this)
                    .loggined(loggined -> "Loggined(" + String.format("%1$s,%2$s,%3$s,%4$s,%5$s", loggined.name,loggined.age,loggined.years,loggined.wealth,loggined.user) + ")")
                    .loggedOut(__ -> "LoggedOut")
            ;
            return toString;
        }
    }
    
    @Override
    public int hashCode() {
        return toString().hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof LoginStatus))
            return false;
        
        if (this == obj)
            return true;
        
        String objToString  = obj.toString();
        String thisToString = this.toString();
        return thisToString.equals(objToString);
    }
    
    
    public boolean isLoggined() { return this instanceof Loggined; }
    public Result<Loggined> asLoggined() { return Result.valueOf(this).filter(Loggined.class).map(Loggined.class::cast); }
    public LoginStatus ifLoggined(Consumer<Loggined> action) { if (isLoggined()) action.accept((Loggined)this); return this; }
    public LoginStatus ifLoggined(Runnable action) { if (isLoggined()) action.run(); return this; }
    public boolean isLoggedOut() { return this instanceof LoggedOut; }
    public Result<LoggedOut> asLoggedOut() { return Result.valueOf(this).filter(LoggedOut.class).map(LoggedOut.class::cast); }
    public LoginStatus ifLoggedOut(Consumer<LoggedOut> action) { if (isLoggedOut()) action.accept((LoggedOut)this); return this; }
    public LoginStatus ifLoggedOut(Runnable action) { if (isLoggedOut()) action.run(); return this; }
    
    public static class LoginStatusFirstSwitch {
        private LoginStatus $value;
        private LoginStatusFirstSwitch(LoginStatus theValue) { this.$value = theValue; }
        public <TARGET> LoginStatusFirstSwitchTyped<TARGET> toA(Class<TARGET> clzz) {
            return new LoginStatusFirstSwitchTyped<TARGET>($value);
        }
        
        public <TARGET> LoginStatusSwitchLoggedOut<TARGET> loggined(Function<? super Loggined, ? extends TARGET> theAction) {
            Function<LoginStatus, TARGET> $action = null;
            Function<LoginStatus, TARGET> oldAction = (Function<LoginStatus, TARGET>)$action;
            Function<LoginStatus, TARGET> newAction =
                ($action != null)
                ? oldAction : 
                    ($value instanceof Loggined)
                    ? (Function<LoginStatus, TARGET>)(d -> theAction.apply((Loggined)d))
                    : oldAction;
            
            return new LoginStatusSwitchLoggedOut<TARGET>($value, newAction);
        }
        public <TARGET> LoginStatusSwitchLoggedOut<TARGET> loggined(Supplier<? extends TARGET> theSupplier) {
            return loggined(d->theSupplier.get());
        }
        public <TARGET> LoginStatusSwitchLoggedOut<TARGET> loggined(TARGET theValue) {
            return loggined(d->theValue);
        }
        
        public <TARGET> LoginStatusSwitchLogginedLoggedOut<TARGET> loggined(Predicate<Loggined> check, Function<? super Loggined, ? extends TARGET> theAction) {
            Function<LoginStatus, TARGET> $action = null;
            Function<LoginStatus, TARGET> oldAction = (Function<LoginStatus, TARGET>)$action;
            Function<LoginStatus, TARGET> newAction =
                ($action != null)
                ? oldAction : 
                    (($value instanceof Loggined) && check.test((Loggined)$value))
                    ? (Function<LoginStatus, TARGET>)(d -> theAction.apply((Loggined)d))
                    : oldAction;
            
            return new LoginStatusSwitchLogginedLoggedOut<TARGET>($value, newAction);
        }
        public <TARGET> LoginStatusSwitchLogginedLoggedOut<TARGET> loggined(Predicate<Loggined> check, Supplier<? extends TARGET> theSupplier) {
            return loggined(check, d->theSupplier.get());
        }
        public <TARGET> LoginStatusSwitchLogginedLoggedOut<TARGET> loggined(Predicate<Loggined> check, TARGET theValue) {
            return loggined(check, d->theValue);
        }
    }
    public static class LoginStatusFirstSwitchTyped<TARGET> {
        private LoginStatus $value;
        private LoginStatusFirstSwitchTyped(LoginStatus theValue) { this.$value = theValue; }
        
        public LoginStatusSwitchLoggedOut<TARGET> loggined(Function<? super Loggined, ? extends TARGET> theAction) {
            Function<LoginStatus, TARGET> $action = null;
            Function<LoginStatus, TARGET> oldAction = (Function<LoginStatus, TARGET>)$action;
            Function<LoginStatus, TARGET> newAction =
                ($action != null)
                ? oldAction : 
                    ($value instanceof Loggined)
                    ? (Function<LoginStatus, TARGET>)(d -> theAction.apply((Loggined)d))
                    : oldAction;
            
            return new LoginStatusSwitchLoggedOut<TARGET>($value, newAction);
        }
        public LoginStatusSwitchLoggedOut<TARGET> loggined(Supplier<? extends TARGET> theSupplier) {
            return loggined(d->theSupplier.get());
        }
        public LoginStatusSwitchLoggedOut<TARGET> loggined(TARGET theValue) {
            return loggined(d->theValue);
        }
        
        public LoginStatusSwitchLogginedLoggedOut<TARGET> loggined(Predicate<Loggined> check, Function<? super Loggined, ? extends TARGET> theAction) {
            Function<LoginStatus, TARGET> $action = null;
            Function<LoginStatus, TARGET> oldAction = (Function<LoginStatus, TARGET>)$action;
            Function<LoginStatus, TARGET> newAction =
                ($action != null)
                ? oldAction : 
                    (($value instanceof Loggined) && check.test((Loggined)$value))
                    ? (Function<LoginStatus, TARGET>)(d -> theAction.apply((Loggined)d))
                    : oldAction;
            
            return new LoginStatusSwitchLogginedLoggedOut<TARGET>($value, newAction);
        }
        public LoginStatusSwitchLogginedLoggedOut<TARGET> loggined(Predicate<Loggined> check, Supplier<? extends TARGET> theSupplier) {
            return loggined(check, d->theSupplier.get());
        }
        public LoginStatusSwitchLogginedLoggedOut<TARGET> loggined(Predicate<Loggined> check, TARGET theValue) {
            return loggined(check, d->theValue);
        }
    }
    public static class LoginStatusSwitchLogginedLoggedOut<TARGET> extends ChoiceTypeSwitch<LoginStatus, TARGET> {
        private LoginStatusSwitchLogginedLoggedOut(LoginStatus theValue, Function<LoginStatus, ? extends TARGET> theAction) { super(theValue, theAction); }
        
        public LoginStatusSwitchLoggedOut<TARGET> loggined(Function<? super Loggined, ? extends TARGET> theAction) {
            Function<LoginStatus, TARGET> oldAction = (Function<LoginStatus, TARGET>)$action;
            Function<LoginStatus, TARGET> newAction =
                ($action != null)
                ? oldAction : 
                    ($value instanceof Loggined)
                    ? (Function<LoginStatus, TARGET>)(d -> theAction.apply((Loggined)d))
                    : oldAction;
            
            return new LoginStatusSwitchLoggedOut<TARGET>($value, newAction);
        }
        public LoginStatusSwitchLoggedOut<TARGET> loggined(Supplier<? extends TARGET> theSupplier) {
            return loggined(d->theSupplier.get());
        }
        public LoginStatusSwitchLoggedOut<TARGET> loggined(TARGET theValue) {
            return loggined(d->theValue);
        }
        
        public LoginStatusSwitchLogginedLoggedOut<TARGET> loggined(Predicate<Loggined> check, Function<? super Loggined, ? extends TARGET> theAction) {
            Function<LoginStatus, TARGET> oldAction = (Function<LoginStatus, TARGET>)$action;
            Function<LoginStatus, TARGET> newAction =
                ($action != null)
                ? oldAction : 
                    (($value instanceof Loggined) && check.test((Loggined)$value))
                    ? (Function<LoginStatus, TARGET>)(d -> theAction.apply((Loggined)d))
                    : oldAction;
            
            return new LoginStatusSwitchLogginedLoggedOut<TARGET>($value, newAction);
        }
        public LoginStatusSwitchLogginedLoggedOut<TARGET> loggined(Predicate<Loggined> check, Supplier<? extends TARGET> theSupplier) {
            return loggined(check, d->theSupplier.get());
        }
        public LoginStatusSwitchLogginedLoggedOut<TARGET> loggined(Predicate<Loggined> check, TARGET theValue) {
            return loggined(check, d->theValue);
        }
    }
    public static class LoginStatusSwitchLoggedOut<TARGET> extends ChoiceTypeSwitch<LoginStatus, TARGET> {
        private LoginStatusSwitchLoggedOut(LoginStatus theValue, Function<LoginStatus, ? extends TARGET> theAction) { super(theValue, theAction); }
        
        public TARGET loggedOut(Function<? super LoggedOut, ? extends TARGET> theAction) {
            Function<LoginStatus, TARGET> oldAction = (Function<LoginStatus, TARGET>)$action;
            Function<LoginStatus, TARGET> newAction =
                ($action != null)
                ? oldAction : 
                    ($value instanceof LoggedOut)
                    ? (Function<LoginStatus, TARGET>)(d -> theAction.apply((LoggedOut)d))
                    : oldAction;
            
            return newAction.apply($value);
        }
        public TARGET loggedOut(Supplier<? extends TARGET> theSupplier) {
            return loggedOut(d->theSupplier.get());
        }
        public TARGET loggedOut(TARGET theValue) {
            return loggedOut(d->theValue);
        }
    }
    
    
    
}

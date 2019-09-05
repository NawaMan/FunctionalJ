package example.functionalj.choice;

import functionalj.lens.core.LensSpec;
import functionalj.lens.lenses.*;
import functionalj.pipeable.Pipeable;
import functionalj.result.Result;
import functionalj.types.choice.ChoiceTypeSwitch;
import functionalj.types.choice.IChoice;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

// example.functionalj.choice.ChoiceTypeExamples.LoginStatusSpec

@SuppressWarnings({"javadoc", "rawtypes", "unchecked"})
public abstract class LoginStatus implements IChoice<LoginStatus.LoginStatusFirstSwitch>, Pipeable<LoginStatus> {
    
    public static final Login Login(String userName) {
        return new Login(userName);
    }
    public static final Logout logout = Logout.instance;
    public static final Logout Logout() {
        return Logout.instance;
    }
    
    
    public static final LoginStatusLens<LoginStatus> theLoginStatus = new LoginStatusLens<>(LensSpec.of(LoginStatus.class));
    public static class LoginStatusLens<HOST> extends ObjectLensImpl<HOST, LoginStatus> {

        public final BooleanAccess<LoginStatus> isLogin = LoginStatus::isLogin;
        public final BooleanAccess<LoginStatus> isLogout = LoginStatus::isLogout;
        public final ResultAccess<HOST, Login, Login.LoginLens<HOST>> asLogin = createSubResultLens(LoginStatus::asLogin, null, Login.LoginLens::new);
        public final ResultAccess<HOST, Logout, Logout.LogoutLens<HOST>> asLogout = createSubResultLens(LoginStatus::asLogout, null, Logout.LogoutLens::new);
        public LoginStatusLens(LensSpec<HOST, LoginStatus> spec) {
            super(spec);
        }
    }
    
    private LoginStatus() {}
    public LoginStatus __data() throws Exception { return this; }
    public Result<LoginStatus> toResult() { return Result.valueOf(this); }
    
    public static <T extends LoginStatus> T fromMap(java.util.Map<String, Object> map) {
        String __tagged = (String)map.get("__tagged");
        if ("Login".equals(__tagged))
            return (T)Login.caseFromMap(map);
        if ("Logout".equals(__tagged))
            return (T)Logout.caseFromMap(map);
        throw new IllegalArgumentException("Tagged value does not represent a valid type: " + __tagged);
    }
    
    static private functionalj.map.FuncMap<String, java.util.Map<String, functionalj.types.choice.generator.model.CaseParam>> __schema__ = functionalj.map.FuncMap.<String, java.util.Map<String, functionalj.types.choice.generator.model.CaseParam>>newMap()
        .with("Login", Login.getCaseSchema())
        .with("Logout", Logout.getCaseSchema())
        .build();
    public static java.util.Map<String, java.util.Map<String, functionalj.types.choice.generator.model.CaseParam>> getChoiceSchema() {
        return __schema__;
    }
    
    public static final class Login extends LoginStatus {
        public static final Login.LoginLens<Login> theLogin = new Login.LoginLens<>(LensSpec.of(Login.class));
        private String userName;
        private Login(String userName) {
            this.userName = userName;
        }
        public String userName() { return userName; }
        public Login withUserName(String userName) { return new Login(userName); }
        public static class LoginLens<HOST> extends ObjectLensImpl<HOST, LoginStatus.Login> {
            
            public final StringLens<HOST> userName = (StringLens)createSubLens(LoginStatus.Login::userName, LoginStatus.Login::withUserName, StringLens::of);
            
            public LoginLens(LensSpec<HOST, LoginStatus.Login> spec) {
                super(spec);
            }
            
        }
        public java.util.Map<String, Object> __toMap() {
            java.util.Map<String, Object> map = new java.util.HashMap<>();
            map.put("__tagged", functionalj.types.IData.$utils.toMapValueObject("Login"));
            map.put("userName", this.userName);
            return map;
        }
        static private functionalj.map.FuncMap<String, functionalj.types.choice.generator.model.CaseParam> __schema__ = functionalj.map.FuncMap.<String, functionalj.types.choice.generator.model.CaseParam>newMap()
            .with("userName", new functionalj.types.choice.generator.model.CaseParam("userName", new functionalj.types.Type("java.lang", null, "String", java.util.Collections.emptyList()), true, null))
            .build();
        public static java.util.Map<String, functionalj.types.choice.generator.model.CaseParam> getCaseSchema() {
            return __schema__;
        }
        public static Login caseFromMap(java.util.Map<String, Object> map) {
            return Login(
                $utils.propertyFromMap(map, __schema__, "userName")
            );
        }
    }
    public static final class Logout extends LoginStatus {
        public static final Logout.LogoutLens<Logout> theLogout = new Logout.LogoutLens<>(LensSpec.of(Logout.class));
        private static final Logout instance = new Logout();
        private Logout() {}
        public static class LogoutLens<HOST> extends ObjectLensImpl<HOST, LoginStatus.Logout> {
            
            public LogoutLens(LensSpec<HOST, LoginStatus.Logout> spec) {
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
        public static Logout caseFromMap(java.util.Map<String, Object> map) {
            return Logout(
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
                    .login(login -> "Login(" + String.format("%1$s", login.userName) + ")")
                    .logout(__ -> "Logout")
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
    
    
    public boolean isLogin() { return this instanceof Login; }
    public Result<Login> asLogin() { return Result.valueOf(this).filter(Login.class).map(Login.class::cast); }
    public LoginStatus ifLogin(Consumer<Login> action) { if (isLogin()) action.accept((Login)this); return this; }
    public LoginStatus ifLogin(Runnable action) { if (isLogin()) action.run(); return this; }
    public boolean isLogout() { return this instanceof Logout; }
    public Result<Logout> asLogout() { return Result.valueOf(this).filter(Logout.class).map(Logout.class::cast); }
    public LoginStatus ifLogout(Consumer<Logout> action) { if (isLogout()) action.accept((Logout)this); return this; }
    public LoginStatus ifLogout(Runnable action) { if (isLogout()) action.run(); return this; }
    
    public static class LoginStatusFirstSwitch {
        private LoginStatus $value;
        private LoginStatusFirstSwitch(LoginStatus theValue) { this.$value = theValue; }
        public <TARGET> LoginStatusFirstSwitchTyped<TARGET> toA(Class<TARGET> clzz) {
            return new LoginStatusFirstSwitchTyped<TARGET>($value);
        }
        
        public <TARGET> LoginStatusSwitchLogout<TARGET> login(Function<? super Login, ? extends TARGET> theAction) {
            Function<LoginStatus, TARGET> $action = null;
            Function<LoginStatus, TARGET> oldAction = (Function<LoginStatus, TARGET>)$action;
            Function<LoginStatus, TARGET> newAction =
                ($action != null)
                ? oldAction : 
                    ($value instanceof Login)
                    ? (Function<LoginStatus, TARGET>)(d -> theAction.apply((Login)d))
                    : oldAction;
            
            return new LoginStatusSwitchLogout<TARGET>($value, newAction);
        }
        public <TARGET> LoginStatusSwitchLogout<TARGET> login(Supplier<? extends TARGET> theSupplier) {
            return login(d->theSupplier.get());
        }
        public <TARGET> LoginStatusSwitchLogout<TARGET> login(TARGET theValue) {
            return login(d->theValue);
        }
        
        public <TARGET> LoginStatusSwitchLoginLogout<TARGET> login(Predicate<Login> check, Function<? super Login, ? extends TARGET> theAction) {
            Function<LoginStatus, TARGET> $action = null;
            Function<LoginStatus, TARGET> oldAction = (Function<LoginStatus, TARGET>)$action;
            Function<LoginStatus, TARGET> newAction =
                ($action != null)
                ? oldAction : 
                    (($value instanceof Login) && check.test((Login)$value))
                    ? (Function<LoginStatus, TARGET>)(d -> theAction.apply((Login)d))
                    : oldAction;
            
            return new LoginStatusSwitchLoginLogout<TARGET>($value, newAction);
        }
        public <TARGET> LoginStatusSwitchLoginLogout<TARGET> login(Predicate<Login> check, Supplier<? extends TARGET> theSupplier) {
            return login(check, d->theSupplier.get());
        }
        public <TARGET> LoginStatusSwitchLoginLogout<TARGET> login(Predicate<Login> check, TARGET theValue) {
            return login(check, d->theValue);
        }
    }
    public static class LoginStatusFirstSwitchTyped<TARGET> {
        private LoginStatus $value;
        private LoginStatusFirstSwitchTyped(LoginStatus theValue) { this.$value = theValue; }
        
        public LoginStatusSwitchLogout<TARGET> login(Function<? super Login, ? extends TARGET> theAction) {
            Function<LoginStatus, TARGET> $action = null;
            Function<LoginStatus, TARGET> oldAction = (Function<LoginStatus, TARGET>)$action;
            Function<LoginStatus, TARGET> newAction =
                ($action != null)
                ? oldAction : 
                    ($value instanceof Login)
                    ? (Function<LoginStatus, TARGET>)(d -> theAction.apply((Login)d))
                    : oldAction;
            
            return new LoginStatusSwitchLogout<TARGET>($value, newAction);
        }
        public LoginStatusSwitchLogout<TARGET> login(Supplier<? extends TARGET> theSupplier) {
            return login(d->theSupplier.get());
        }
        public LoginStatusSwitchLogout<TARGET> login(TARGET theValue) {
            return login(d->theValue);
        }
        
        public LoginStatusSwitchLoginLogout<TARGET> login(Predicate<Login> check, Function<? super Login, ? extends TARGET> theAction) {
            Function<LoginStatus, TARGET> $action = null;
            Function<LoginStatus, TARGET> oldAction = (Function<LoginStatus, TARGET>)$action;
            Function<LoginStatus, TARGET> newAction =
                ($action != null)
                ? oldAction : 
                    (($value instanceof Login) && check.test((Login)$value))
                    ? (Function<LoginStatus, TARGET>)(d -> theAction.apply((Login)d))
                    : oldAction;
            
            return new LoginStatusSwitchLoginLogout<TARGET>($value, newAction);
        }
        public LoginStatusSwitchLoginLogout<TARGET> login(Predicate<Login> check, Supplier<? extends TARGET> theSupplier) {
            return login(check, d->theSupplier.get());
        }
        public LoginStatusSwitchLoginLogout<TARGET> login(Predicate<Login> check, TARGET theValue) {
            return login(check, d->theValue);
        }
    }
    public static class LoginStatusSwitchLoginLogout<TARGET> extends ChoiceTypeSwitch<LoginStatus, TARGET> {
        private LoginStatusSwitchLoginLogout(LoginStatus theValue, Function<LoginStatus, ? extends TARGET> theAction) { super(theValue, theAction); }
        
        public LoginStatusSwitchLogout<TARGET> login(Function<? super Login, ? extends TARGET> theAction) {
            Function<LoginStatus, TARGET> oldAction = (Function<LoginStatus, TARGET>)$action;
            Function<LoginStatus, TARGET> newAction =
                ($action != null)
                ? oldAction : 
                    ($value instanceof Login)
                    ? (Function<LoginStatus, TARGET>)(d -> theAction.apply((Login)d))
                    : oldAction;
            
            return new LoginStatusSwitchLogout<TARGET>($value, newAction);
        }
        public LoginStatusSwitchLogout<TARGET> login(Supplier<? extends TARGET> theSupplier) {
            return login(d->theSupplier.get());
        }
        public LoginStatusSwitchLogout<TARGET> login(TARGET theValue) {
            return login(d->theValue);
        }
        
        public LoginStatusSwitchLoginLogout<TARGET> login(Predicate<Login> check, Function<? super Login, ? extends TARGET> theAction) {
            Function<LoginStatus, TARGET> oldAction = (Function<LoginStatus, TARGET>)$action;
            Function<LoginStatus, TARGET> newAction =
                ($action != null)
                ? oldAction : 
                    (($value instanceof Login) && check.test((Login)$value))
                    ? (Function<LoginStatus, TARGET>)(d -> theAction.apply((Login)d))
                    : oldAction;
            
            return new LoginStatusSwitchLoginLogout<TARGET>($value, newAction);
        }
        public LoginStatusSwitchLoginLogout<TARGET> login(Predicate<Login> check, Supplier<? extends TARGET> theSupplier) {
            return login(check, d->theSupplier.get());
        }
        public LoginStatusSwitchLoginLogout<TARGET> login(Predicate<Login> check, TARGET theValue) {
            return login(check, d->theValue);
        }
    }
    public static class LoginStatusSwitchLogout<TARGET> extends ChoiceTypeSwitch<LoginStatus, TARGET> {
        private LoginStatusSwitchLogout(LoginStatus theValue, Function<LoginStatus, ? extends TARGET> theAction) { super(theValue, theAction); }
        
        public TARGET logout(Function<? super Logout, ? extends TARGET> theAction) {
            Function<LoginStatus, TARGET> oldAction = (Function<LoginStatus, TARGET>)$action;
            Function<LoginStatus, TARGET> newAction =
                ($action != null)
                ? oldAction : 
                    ($value instanceof Logout)
                    ? (Function<LoginStatus, TARGET>)(d -> theAction.apply((Logout)d))
                    : oldAction;
            
            return newAction.apply($value);
        }
        public TARGET logout(Supplier<? extends TARGET> theSupplier) {
            return logout(d->theSupplier.get());
        }
        public TARGET logout(TARGET theValue) {
            return logout(d->theValue);
        }
    }
    
    
    
}

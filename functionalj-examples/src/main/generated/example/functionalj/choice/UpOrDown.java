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

// example.functionalj.choice.ChoiceTypeExamples.UpOrDownSpec

@SuppressWarnings({"javadoc", "rawtypes", "unchecked"})
public abstract class UpOrDown implements IChoice<UpOrDown.UpOrDownFirstSwitch>, Pipeable<UpOrDown> {
    
    public static final Up up = Up.instance;
    public static final Up Up() {
        return Up.instance;
    }
    public static final Down down = Down.instance;
    public static final Down Down() {
        return Down.instance;
    }
    
    
    public static final UpOrDownLens<UpOrDown> theUpOrDown = new UpOrDownLens<>(LensSpec.of(UpOrDown.class));
    public static final UpOrDownLens<UpOrDown> eachUpOrDown = theUpOrDown;
    public static class UpOrDownLens<HOST> extends ObjectLensImpl<HOST, UpOrDown> {

        public final BooleanAccessPrimitive<UpOrDown> isUp = UpOrDown::isUp;
        public final BooleanAccessPrimitive<UpOrDown> isDown = UpOrDown::isDown;
        public final ResultAccess<HOST, Up, Up.UpLens<HOST>> asUp = createSubResultLens(UpOrDown::asUp, (functionalj.lens.core.WriteLens<UpOrDown,Result<Up>>)null, Up.UpLens::new);
        public final ResultAccess<HOST, Down, Down.DownLens<HOST>> asDown = createSubResultLens(UpOrDown::asDown, (functionalj.lens.core.WriteLens<UpOrDown,Result<Down>>)null, Down.DownLens::new);
        public UpOrDownLens(LensSpec<HOST, UpOrDown> spec) {
            super(spec);
        }
    }
    
    private UpOrDown() {}
    public UpOrDown __data() throws Exception { return this; }
    public Result<UpOrDown> toResult() { return Result.valueOf(this); }
    
    public static <T extends UpOrDown> T fromMap(java.util.Map<String, Object> map) {
        String __tagged = (String)map.get("__tagged");
        if ("Up".equals(__tagged))
            return (T)Up.caseFromMap(map);
        if ("Down".equals(__tagged))
            return (T)Down.caseFromMap(map);
        throw new IllegalArgumentException("Tagged value does not represent a valid type: " + __tagged);
    }
    
    static private functionalj.map.FuncMap<String, java.util.Map<String, functionalj.types.choice.generator.model.CaseParam>> __schema__ = functionalj.map.FuncMap.<String, java.util.Map<String, functionalj.types.choice.generator.model.CaseParam>>newMap()
        .with("Up", Up.getCaseSchema())
        .with("Down", Down.getCaseSchema())
        .build();
    public static java.util.Map<String, java.util.Map<String, functionalj.types.choice.generator.model.CaseParam>> getChoiceSchema() {
        return __schema__;
    }
    
    public static final class Up extends UpOrDown {
        public static final Up.UpLens<Up> theUp = new Up.UpLens<>(LensSpec.of(Up.class));
        public static final Up.UpLens<Up> eachUp = theUp;
        private static final Up instance = new Up();
        private Up() {}
        public static class UpLens<HOST> extends ObjectLensImpl<HOST, UpOrDown.Up> {
            
            public UpLens(LensSpec<HOST, UpOrDown.Up> spec) {
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
        public static Up caseFromMap(java.util.Map<String, Object> map) {
            return Up(
            );
        }
    }
    public static final class Down extends UpOrDown {
        public static final Down.DownLens<Down> theDown = new Down.DownLens<>(LensSpec.of(Down.class));
        public static final Down.DownLens<Down> eachDown = theDown;
        private static final Down instance = new Down();
        private Down() {}
        public static class DownLens<HOST> extends ObjectLensImpl<HOST, UpOrDown.Down> {
            
            public DownLens(LensSpec<HOST, UpOrDown.Down> spec) {
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
        public static Down caseFromMap(java.util.Map<String, Object> map) {
            return Down(
            );
        }
    }
    
    public java.util.Map<java.lang.String, java.util.Map<java.lang.String, functionalj.types.choice.generator.model.CaseParam>> __getSchema() {
        return getChoiceSchema();
    }
    
    private final UpOrDownFirstSwitch __switch = new UpOrDownFirstSwitch(this);
    @Override public UpOrDownFirstSwitch match() {
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
                    .up(__ -> "Up")
                    .down(__ -> "Down")
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
        if (!(obj instanceof UpOrDown))
            return false;
        
        if (this == obj)
            return true;
        
        String objToString  = obj.toString();
        String thisToString = this.toString();
        return thisToString.equals(objToString);
    }
    
    
    public boolean isUp() { return this instanceof Up; }
    public Result<Up> asUp() { return Result.valueOf(this).filter(Up.class).map(Up.class::cast); }
    public UpOrDown ifUp(Consumer<Up> action) { if (isUp()) action.accept((Up)this); return this; }
    public UpOrDown ifUp(Runnable action) { if (isUp()) action.run(); return this; }
    public boolean isDown() { return this instanceof Down; }
    public Result<Down> asDown() { return Result.valueOf(this).filter(Down.class).map(Down.class::cast); }
    public UpOrDown ifDown(Consumer<Down> action) { if (isDown()) action.accept((Down)this); return this; }
    public UpOrDown ifDown(Runnable action) { if (isDown()) action.run(); return this; }
    
    public static class UpOrDownFirstSwitch {
        private UpOrDown $value;
        private UpOrDownFirstSwitch(UpOrDown theValue) { this.$value = theValue; }
        public <TARGET> UpOrDownFirstSwitchTyped<TARGET> toA(Class<TARGET> clzz) {
            return new UpOrDownFirstSwitchTyped<TARGET>($value);
        }
        
        public <TARGET> UpOrDownSwitchDown<TARGET> up(Function<? super Up, ? extends TARGET> theAction) {
            Function<UpOrDown, TARGET> $action = null;
            Function<UpOrDown, TARGET> oldAction = (Function<UpOrDown, TARGET>)$action;
            Function<UpOrDown, TARGET> newAction =
                ($action != null)
                ? oldAction : 
                    ($value instanceof Up)
                    ? (Function<UpOrDown, TARGET>)(d -> theAction.apply((Up)d))
                    : oldAction;
            
            return new UpOrDownSwitchDown<TARGET>($value, newAction);
        }
        public <TARGET> UpOrDownSwitchDown<TARGET> up(Supplier<? extends TARGET> theSupplier) {
            return up(d->theSupplier.get());
        }
        public <TARGET> UpOrDownSwitchDown<TARGET> up(TARGET theValue) {
            return up(d->theValue);
        }
    }
    public static class UpOrDownFirstSwitchTyped<TARGET> {
        private UpOrDown $value;
        private UpOrDownFirstSwitchTyped(UpOrDown theValue) { this.$value = theValue; }
        
        public UpOrDownSwitchDown<TARGET> up(Function<? super Up, ? extends TARGET> theAction) {
            Function<UpOrDown, TARGET> $action = null;
            Function<UpOrDown, TARGET> oldAction = (Function<UpOrDown, TARGET>)$action;
            Function<UpOrDown, TARGET> newAction =
                ($action != null)
                ? oldAction : 
                    ($value instanceof Up)
                    ? (Function<UpOrDown, TARGET>)(d -> theAction.apply((Up)d))
                    : oldAction;
            
            return new UpOrDownSwitchDown<TARGET>($value, newAction);
        }
        public UpOrDownSwitchDown<TARGET> up(Supplier<? extends TARGET> theSupplier) {
            return up(d->theSupplier.get());
        }
        public UpOrDownSwitchDown<TARGET> up(TARGET theValue) {
            return up(d->theValue);
        }
    }
    public static class UpOrDownSwitchUpDown<TARGET> extends ChoiceTypeSwitch<UpOrDown, TARGET> {
        private UpOrDownSwitchUpDown(UpOrDown theValue, Function<UpOrDown, ? extends TARGET> theAction) { super(theValue, theAction); }
        
        public UpOrDownSwitchDown<TARGET> up(Function<? super Up, ? extends TARGET> theAction) {
            Function<UpOrDown, TARGET> oldAction = (Function<UpOrDown, TARGET>)$action;
            Function<UpOrDown, TARGET> newAction =
                ($action != null)
                ? oldAction : 
                    ($value instanceof Up)
                    ? (Function<UpOrDown, TARGET>)(d -> theAction.apply((Up)d))
                    : oldAction;
            
            return new UpOrDownSwitchDown<TARGET>($value, newAction);
        }
        public UpOrDownSwitchDown<TARGET> up(Supplier<? extends TARGET> theSupplier) {
            return up(d->theSupplier.get());
        }
        public UpOrDownSwitchDown<TARGET> up(TARGET theValue) {
            return up(d->theValue);
        }
    }
    public static class UpOrDownSwitchDown<TARGET> extends ChoiceTypeSwitch<UpOrDown, TARGET> {
        private UpOrDownSwitchDown(UpOrDown theValue, Function<UpOrDown, ? extends TARGET> theAction) { super(theValue, theAction); }
        
        public TARGET down(Function<? super Down, ? extends TARGET> theAction) {
            Function<UpOrDown, TARGET> oldAction = (Function<UpOrDown, TARGET>)$action;
            Function<UpOrDown, TARGET> newAction =
                ($action != null)
                ? oldAction : 
                    ($value instanceof Down)
                    ? (Function<UpOrDown, TARGET>)(d -> theAction.apply((Down)d))
                    : oldAction;
            
            return newAction.apply($value);
        }
        public TARGET down(Supplier<? extends TARGET> theSupplier) {
            return down(d->theSupplier.get());
        }
        public TARGET down(TARGET theValue) {
            return down(d->theValue);
        }
    }
    
    
    
}

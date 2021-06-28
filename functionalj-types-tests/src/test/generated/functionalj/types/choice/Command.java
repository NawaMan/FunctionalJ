package functionalj.types.choice;

import functionalj.lens.core.LensSpec;
import functionalj.lens.lenses.*;
import functionalj.pipeable.Pipeable;
import functionalj.result.Result;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Generated;

@Generated(value = "FunctionalJ",date = "2021-06-27T07:56:33.547221", comments = "functionalj.types.choice.ChoiceToMapTest.CommandModel")
@SuppressWarnings({"unchecked"})
public abstract class Command implements IChoice<Command.CommandFirstSwitch>, Pipeable<Command> {
    
    public static final Rotate Rotate(int degree) {
        return new Rotate(degree);
    }
    public static final Move Move(int distance) {
        return new Move(distance);
    }
    
    
    public static final CommandLens<Command> theCommand = new CommandLens<>(LensSpec.of(Command.class));
    public static final CommandLens<Command> eachCommand = theCommand;
    public static class CommandLens<HOST> extends ObjectLensImpl<HOST, Command> {

        public final BooleanAccessPrimitive<Command> isRotate = Command::isRotate;
        public final BooleanAccessPrimitive<Command> isMove = Command::isMove;
        public final ResultAccess<HOST, Rotate, Rotate.RotateLens<HOST>> asRotate = createSubResultLens(Command::asRotate, (functionalj.lens.core.WriteLens<Command,Result<Rotate>>)null, Rotate.RotateLens::new);
        public final ResultAccess<HOST, Move, Move.MoveLens<HOST>> asMove = createSubResultLens(Command::asMove, (functionalj.lens.core.WriteLens<Command,Result<Move>>)null, Move.MoveLens::new);
        public CommandLens(LensSpec<HOST, Command> spec) {
            super(spec);
        }
    }
    
    private Command() {}
    public Command __data() throws Exception { return this; }
    public Result<Command> toResult() { return Result.valueOf(this); }
    
    public static <T extends Command> T fromMap(java.util.Map<String, ? extends Object> map) {
        String __tagged = (String)map.get("type");
        if ("Rotate".equals(__tagged))
            return (T)Rotate.caseFromMap(map);
        if ("Move".equals(__tagged))
            return (T)Move.caseFromMap(map);
        throw new IllegalArgumentException("Tagged value does not represent a valid type: " + __tagged);
    }
    
    static private functionalj.map.FuncMap<String, java.util.Map<String, functionalj.types.choice.generator.model.CaseParam>> __schema__ = functionalj.map.FuncMap.<String, java.util.Map<String, functionalj.types.choice.generator.model.CaseParam>>newMap()
        .with("Rotate", Rotate.getCaseSchema())
        .with("Move", Move.getCaseSchema())
        .build();
    public static java.util.Map<String, java.util.Map<String, functionalj.types.choice.generator.model.CaseParam>> getChoiceSchema() {
        return __schema__;
    }
    
    public static final class Rotate extends Command {
        public static final Rotate.RotateLens<Rotate> theRotate = new Rotate.RotateLens<>(LensSpec.of(Rotate.class));
        public static final Rotate.RotateLens<Rotate> eachRotate = theRotate;
        private int degree;
        private Rotate(int degree) {
            this.degree = degree;
        }
        public int degree() { return degree; }
        public Rotate withDegree(int degree) { return new Rotate(degree); }
        public static class RotateLens<HOST> extends ObjectLensImpl<HOST, Command.Rotate> {
            
            public final IntegerLens<HOST> degree = createSubLensInt(Command.Rotate::degree, Command.Rotate::withDegree);
            
            public RotateLens(LensSpec<HOST, Command.Rotate> spec) {
                super(spec);
            }
            
        }
        public java.util.Map<String, Object> __toMap() {
            java.util.Map<String, Object> map = new java.util.HashMap<>();
            map.put("type", functionalj.types.IData.$utils.toMapValueObject("Rotate"));
            map.put("degree", this.degree);
            return map;
        }
        static private functionalj.map.FuncMap<String, functionalj.types.choice.generator.model.CaseParam> __schema__ = functionalj.map.FuncMap.<String, functionalj.types.choice.generator.model.CaseParam>newMap()
            .with("degree", new functionalj.types.choice.generator.model.CaseParam("degree", new functionalj.types.Type(null, null, "int", java.util.Collections.emptyList()), true, null))
            .build();
        public static java.util.Map<String, functionalj.types.choice.generator.model.CaseParam> getCaseSchema() {
            return __schema__;
        }
        public static Rotate caseFromMap(java.util.Map<String, ? extends Object> map) {
            return Rotate(
                $utils.propertyFromMap(map, __schema__, "degree")
            );
        }
    }
    public static final class Move extends Command {
        public static final Move.MoveLens<Move> theMove = new Move.MoveLens<>(LensSpec.of(Move.class));
        public static final Move.MoveLens<Move> eachMove = theMove;
        private int distance;
        private Move(int distance) {
            this.distance = distance;
        }
        public int distance() { return distance; }
        public Move withDistance(int distance) { return new Move(distance); }
        public static class MoveLens<HOST> extends ObjectLensImpl<HOST, Command.Move> {
            
            public final IntegerLens<HOST> distance = createSubLensInt(Command.Move::distance, Command.Move::withDistance);
            
            public MoveLens(LensSpec<HOST, Command.Move> spec) {
                super(spec);
            }
            
        }
        public java.util.Map<String, Object> __toMap() {
            java.util.Map<String, Object> map = new java.util.HashMap<>();
            map.put("type", functionalj.types.IData.$utils.toMapValueObject("Move"));
            map.put("distance", this.distance);
            return map;
        }
        static private functionalj.map.FuncMap<String, functionalj.types.choice.generator.model.CaseParam> __schema__ = functionalj.map.FuncMap.<String, functionalj.types.choice.generator.model.CaseParam>newMap()
            .with("distance", new functionalj.types.choice.generator.model.CaseParam("distance", new functionalj.types.Type(null, null, "int", java.util.Collections.emptyList()), true, null))
            .build();
        public static java.util.Map<String, functionalj.types.choice.generator.model.CaseParam> getCaseSchema() {
            return __schema__;
        }
        public static Move caseFromMap(java.util.Map<String, ? extends Object> map) {
            return Move(
                $utils.propertyFromMap(map, __schema__, "distance")
            );
        }
    }
    
    public java.util.Map<java.lang.String, java.util.Map<java.lang.String, functionalj.types.choice.generator.model.CaseParam>> __getSchema() {
        return getChoiceSchema();
    }
    
    private final CommandFirstSwitch __switch = new CommandFirstSwitch(this);
    @Override public CommandFirstSwitch match() {
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
                    .rotate(rotate -> "Rotate(" + String.format("%1$s", rotate.degree) + ")")
                    .move(move -> "Move(" + String.format("%1$s", move.distance) + ")")
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
        if (!(obj instanceof Command))
            return false;
        
        if (this == obj)
            return true;
        
        String objToString  = obj.toString();
        String thisToString = this.toString();
        return thisToString.equals(objToString);
    }
    
    
    public boolean isRotate() { return this instanceof Rotate; }
    public Result<Rotate> asRotate() { return Result.valueOf(this).filter(Rotate.class).map(Rotate.class::cast); }
    public Command ifRotate(Consumer<Rotate> action) { if (isRotate()) action.accept((Rotate)this); return this; }
    public Command ifRotate(Runnable action) { if (isRotate()) action.run(); return this; }
    public boolean isMove() { return this instanceof Move; }
    public Result<Move> asMove() { return Result.valueOf(this).filter(Move.class).map(Move.class::cast); }
    public Command ifMove(Consumer<Move> action) { if (isMove()) action.accept((Move)this); return this; }
    public Command ifMove(Runnable action) { if (isMove()) action.run(); return this; }
    
    public static class CommandFirstSwitch {
        private Command $value;
        private CommandFirstSwitch(Command theValue) { this.$value = theValue; }
        public <TARGET> CommandFirstSwitchTyped<TARGET> toA(Class<TARGET> clzz) {
            return new CommandFirstSwitchTyped<TARGET>($value);
        }
        
        public <TARGET> CommandSwitchMove<TARGET> rotate(Function<? super Rotate, ? extends TARGET> theAction) {
            Function<Command, TARGET> $action = null;
            Function<Command, TARGET> oldAction = (Function<Command, TARGET>)$action;
            Function<Command, TARGET> newAction =
                ($action != null)
                ? oldAction : 
                    ($value instanceof Rotate)
                    ? (Function<Command, TARGET>)(d -> theAction.apply((Rotate)d))
                    : oldAction;
            
            return new CommandSwitchMove<TARGET>($value, newAction);
        }
        public <TARGET> CommandSwitchMove<TARGET> rotate(Supplier<? extends TARGET> theSupplier) {
            return rotate(d->theSupplier.get());
        }
        public <TARGET> CommandSwitchMove<TARGET> rotate(TARGET theValue) {
            return rotate(d->theValue);
        }
        
        public <TARGET> CommandSwitchRotateMove<TARGET> rotate(java.util.function.Predicate<Rotate> check, Function<? super Rotate, ? extends TARGET> theAction) {
            Function<Command, TARGET> $action = null;
            Function<Command, TARGET> oldAction = (Function<Command, TARGET>)$action;
            Function<Command, TARGET> newAction =
                ($action != null)
                ? oldAction : 
                    (($value instanceof Rotate) && check.test((Rotate)$value))
                    ? (Function<Command, TARGET>)(d -> theAction.apply((Rotate)d))
                    : oldAction;
            
            return new CommandSwitchRotateMove<TARGET>($value, newAction);
        }
        public <TARGET> CommandSwitchRotateMove<TARGET> rotate(java.util.function.Predicate<Rotate> check, Supplier<? extends TARGET> theSupplier) {
            return rotate(check, d->theSupplier.get());
        }
        public <TARGET> CommandSwitchRotateMove<TARGET> rotate(java.util.function.Predicate<Rotate> check, TARGET theValue) {
            return rotate(check, d->theValue);
        }
    }
    public static class CommandFirstSwitchTyped<TARGET> {
        private Command $value;
        private CommandFirstSwitchTyped(Command theValue) { this.$value = theValue; }
        
        public CommandSwitchMove<TARGET> rotate(Function<? super Rotate, ? extends TARGET> theAction) {
            Function<Command, TARGET> $action = null;
            Function<Command, TARGET> oldAction = (Function<Command, TARGET>)$action;
            Function<Command, TARGET> newAction =
                ($action != null)
                ? oldAction : 
                    ($value instanceof Rotate)
                    ? (Function<Command, TARGET>)(d -> theAction.apply((Rotate)d))
                    : oldAction;
            
            return new CommandSwitchMove<TARGET>($value, newAction);
        }
        public CommandSwitchMove<TARGET> rotate(Supplier<? extends TARGET> theSupplier) {
            return rotate(d->theSupplier.get());
        }
        public CommandSwitchMove<TARGET> rotate(TARGET theValue) {
            return rotate(d->theValue);
        }
        
        public CommandSwitchRotateMove<TARGET> rotate(java.util.function.Predicate<Rotate> check, Function<? super Rotate, ? extends TARGET> theAction) {
            Function<Command, TARGET> $action = null;
            Function<Command, TARGET> oldAction = (Function<Command, TARGET>)$action;
            Function<Command, TARGET> newAction =
                ($action != null)
                ? oldAction : 
                    (($value instanceof Rotate) && check.test((Rotate)$value))
                    ? (Function<Command, TARGET>)(d -> theAction.apply((Rotate)d))
                    : oldAction;
            
            return new CommandSwitchRotateMove<TARGET>($value, newAction);
        }
        public CommandSwitchRotateMove<TARGET> rotate(java.util.function.Predicate<Rotate> check, Supplier<? extends TARGET> theSupplier) {
            return rotate(check, d->theSupplier.get());
        }
        public CommandSwitchRotateMove<TARGET> rotate(java.util.function.Predicate<Rotate> check, TARGET theValue) {
            return rotate(check, d->theValue);
        }
    }
    public static class CommandSwitchRotateMove<TARGET> extends ChoiceTypeSwitch<Command, TARGET> {
        private CommandSwitchRotateMove(Command theValue, Function<Command, ? extends TARGET> theAction) { super(theValue, theAction); }
        
        public CommandSwitchMove<TARGET> rotate(Function<? super Rotate, ? extends TARGET> theAction) {
            Function<Command, TARGET> oldAction = (Function<Command, TARGET>)$action;
            Function<Command, TARGET> newAction =
                ($action != null)
                ? oldAction : 
                    ($value instanceof Rotate)
                    ? (Function<Command, TARGET>)(d -> theAction.apply((Rotate)d))
                    : oldAction;
            
            return new CommandSwitchMove<TARGET>($value, newAction);
        }
        public CommandSwitchMove<TARGET> rotate(Supplier<? extends TARGET> theSupplier) {
            return rotate(d->theSupplier.get());
        }
        public CommandSwitchMove<TARGET> rotate(TARGET theValue) {
            return rotate(d->theValue);
        }
        
        public CommandSwitchRotateMove<TARGET> rotate(java.util.function.Predicate<Rotate> check, Function<? super Rotate, ? extends TARGET> theAction) {
            Function<Command, TARGET> oldAction = (Function<Command, TARGET>)$action;
            Function<Command, TARGET> newAction =
                ($action != null)
                ? oldAction : 
                    (($value instanceof Rotate) && check.test((Rotate)$value))
                    ? (Function<Command, TARGET>)(d -> theAction.apply((Rotate)d))
                    : oldAction;
            
            return new CommandSwitchRotateMove<TARGET>($value, newAction);
        }
        public CommandSwitchRotateMove<TARGET> rotate(java.util.function.Predicate<Rotate> check, Supplier<? extends TARGET> theSupplier) {
            return rotate(check, d->theSupplier.get());
        }
        public CommandSwitchRotateMove<TARGET> rotate(java.util.function.Predicate<Rotate> check, TARGET theValue) {
            return rotate(check, d->theValue);
        }
    }
    public static class CommandSwitchMove<TARGET> extends ChoiceTypeSwitch<Command, TARGET> {
        private CommandSwitchMove(Command theValue, Function<Command, ? extends TARGET> theAction) { super(theValue, theAction); }
        
        public TARGET move(Function<? super Move, ? extends TARGET> theAction) {
            Function<Command, TARGET> oldAction = (Function<Command, TARGET>)$action;
            Function<Command, TARGET> newAction =
                ($action != null)
                ? oldAction : 
                    ($value instanceof Move)
                    ? (Function<Command, TARGET>)(d -> theAction.apply((Move)d))
                    : oldAction;
            
            return newAction.apply($value);
        }
        public TARGET move(Supplier<? extends TARGET> theSupplier) {
            return move(d->theSupplier.get());
        }
        public TARGET move(TARGET theValue) {
            return move(d->theValue);
        }
        
        public CommandSwitchMove<TARGET> move(java.util.function.Predicate<Move> check, Function<? super Move, ? extends TARGET> theAction) {
            Function<Command, TARGET> oldAction = (Function<Command, TARGET>)$action;
            Function<Command, TARGET> newAction =
                ($action != null)
                ? oldAction : 
                    (($value instanceof Move) && check.test((Move)$value))
                    ? (Function<Command, TARGET>)(d -> theAction.apply((Move)d))
                    : oldAction;
            
            return new CommandSwitchMove<TARGET>($value, newAction);
        }
        public CommandSwitchMove<TARGET> move(java.util.function.Predicate<Move> check, Supplier<? extends TARGET> theSupplier) {
            return move(check, d->theSupplier.get());
        }
        public CommandSwitchMove<TARGET> move(java.util.function.Predicate<Move> check, TARGET theValue) {
            return move(check, d->theValue);
        }
    }
    
    
    
}

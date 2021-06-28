package functionalj.types.choice;

import functionalj.lens.core.LensSpec;
import functionalj.lens.lenses.FuncListLens;
import functionalj.lens.lenses.ObjectLens;
import functionalj.lens.lenses.ObjectLensImpl;
import functionalj.lens.lenses.java.time.LocalDateTimeLens;
import functionalj.list.FuncList;
import functionalj.pipeable.Pipeable;
import functionalj.types.IPostConstruct;
import functionalj.types.IStruct;
import functionalj.types.struct.generator.Getter;
import java.lang.Exception;
import java.lang.Object;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Generated;

@Generated(value = "FunctionalJ",date = "2021-06-27T07:56:33.559234", comments = "functionalj.types.choice.ChoiceToMapTest")

public class TimedAction implements IStruct,Pipeable<TimedAction> {
    
    public static final TimedAction.TimedActionLens<TimedAction> theTimedAction = new TimedAction.TimedActionLens<>(LensSpec.of(TimedAction.class));
    public static final TimedAction.TimedActionLens<TimedAction> eachTimedAction = theTimedAction;
    public final LocalDateTime time;
    public final FuncList<Command> commands;
    
    public TimedAction(LocalDateTime time, FuncList<Command> commands) {
        this.time = $utils.notNull(time);
        this.commands = functionalj.list.ImmutableFuncList.from(commands);
        if (this instanceof IPostConstruct) ((IPostConstruct)this).postConstruct();
    }
    
    public TimedAction __data() throws Exception  {
        return this;
    }
    public LocalDateTime time() {
        return time;
    }
    public FuncList<Command> commands() {
        return commands;
    }
    public TimedAction withTime(LocalDateTime time) {
        return new TimedAction(time, commands);
    }
    public TimedAction withTime(Supplier<LocalDateTime> time) {
        return new TimedAction(time.get(), commands);
    }
    public TimedAction withTime(Function<LocalDateTime, LocalDateTime> time) {
        return new TimedAction(time.apply(this.time), commands);
    }
    public TimedAction withTime(BiFunction<TimedAction, LocalDateTime, LocalDateTime> time) {
        return new TimedAction(time.apply(this, this.time), commands);
    }
    public TimedAction withCommands(Command ... commands) {
        return new TimedAction(time, functionalj.list.ImmutableFuncList.of(commands));
    }
    public TimedAction withCommands(FuncList<Command> commands) {
        return new TimedAction(time, commands);
    }
    public TimedAction withCommands(Supplier<FuncList<Command>> commands) {
        return new TimedAction(time, commands.get());
    }
    public TimedAction withCommands(Function<FuncList<Command>, FuncList<Command>> commands) {
        return new TimedAction(time, commands.apply(this.commands));
    }
    public TimedAction withCommands(BiFunction<TimedAction, FuncList<Command>, FuncList<Command>> commands) {
        return new TimedAction(time, commands.apply(this, this.commands));
    }
    public static TimedAction fromMap(Map<String, ? extends Object> map) {
        Map<String, Getter> $schema = getStructSchema();
        @SuppressWarnings("unchecked")
        TimedAction obj = new TimedAction(
                    (LocalDateTime)$utils.fromMapValue(map.get("time"), $schema.get("time")),
                    (FuncList<Command>)$utils.fromMapValue(map.get("commands"), $schema.get("commands"))
                );
        return obj;
    }
    public Map<String, Object> __toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("time", functionalj.types.IStruct.$utils.toMapValueObject(time));
        map.put("commands", functionalj.types.IStruct.$utils.toMapValueObject(commands));
        return map;
    }
    public Map<String, Getter> __getSchema() {
        return getStructSchema();
    }
    public static Map<String, Getter> getStructSchema() {
        java.util.Map<String, functionalj.types.struct.generator.Getter> map = new java.util.HashMap<>();
        map.put("time", new functionalj.types.struct.generator.Getter("time", new functionalj.types.Type("java.time", null, "LocalDateTime", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED));
        map.put("commands", new functionalj.types.struct.generator.Getter("commands", new functionalj.types.Type("functionalj.list", null, "FuncList", java.util.Arrays.asList(new functionalj.types.Generic("functionalj.types.choice.Command", null, java.util.Arrays.asList(new functionalj.types.Type("functionalj.types.choice", null, "Command", java.util.Collections.emptyList()))))), false, functionalj.types.DefaultValue.REQUIRED));
        return map;
    }
    public String toString() {
        return "TimedAction[" + "time: " + time() + ", " + "commands: " + commands() + "]";
    }
    public int hashCode() {
        return toString().hashCode();
    }
    public boolean equals(Object another) {
        return (another == this) || ((another != null) && (getClass().equals(another.getClass())) && java.util.Objects.equals(toString(), another.toString()));
    }
    
    public static class TimedActionLens<HOST> extends ObjectLensImpl<HOST, TimedAction> {
        
        public final LocalDateTimeLens<HOST> time = createSubLens(TimedAction::time, TimedAction::withTime, LocalDateTimeLens::of);
        public final FuncListLens<HOST, Command, ObjectLens<HOST, Command>> commands = createSubFuncListLens(TimedAction::commands, TimedAction::withCommands, ObjectLens::of);
        
        public TimedActionLens(LensSpec<HOST, TimedAction> spec) {
            super(spec);
        }
        
    }
    public static final class Builder {
        
        public final TimedActionBuilder_withoutCommands time(LocalDateTime time) {
            return (FuncList<Command> commands)->{
            return ()->{
                return new TimedAction(
                    time,
                    commands
                );
            };
            };
        }
        
        public static interface TimedActionBuilder_withoutCommands {
            
            public TimedActionBuilder_ready commands(FuncList<Command> commands);
            
        }
        public static interface TimedActionBuilder_ready {
            
            public TimedAction build();
            
            
            
        }
        
        
    }
    
}
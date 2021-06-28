package functionalj.types.choice;

import functionalj.lens.core.LensSpec;
import functionalj.lens.lenses.FuncMapLens;
import functionalj.lens.lenses.ObjectLens;
import functionalj.lens.lenses.ObjectLensImpl;
import functionalj.lens.lenses.StringLens;
import functionalj.lens.lenses.java.time.LocalDateTimeLens;
import functionalj.map.FuncMap;
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

@Generated(value = "FunctionalJ",date = "2021-06-27T22:28:43.072399", comments = "functionalj.types.choice.ChoiceToMapTest")

public class MapTimedAction implements IStruct,Pipeable<MapTimedAction> {
    
    public static final MapTimedAction.MapTimedActionLens<MapTimedAction> theMapTimedAction = new MapTimedAction.MapTimedActionLens<>(LensSpec.of(MapTimedAction.class));
    public static final MapTimedAction.MapTimedActionLens<MapTimedAction> eachMapTimedAction = theMapTimedAction;
    public final LocalDateTime time;
    public final FuncMap<String, Command> commands;
    
    public MapTimedAction(LocalDateTime time, FuncMap<String, Command> commands) {
        this.time = $utils.notNull(time);
        this.commands = functionalj.map.ImmutableFuncMap.from(commands);
        if (this instanceof IPostConstruct) ((IPostConstruct)this).postConstruct();
    }
    
    public MapTimedAction __data() throws Exception  {
        return this;
    }
    public LocalDateTime time() {
        return time;
    }
    public FuncMap<String, Command> commands() {
        return commands;
    }
    public MapTimedAction withTime(LocalDateTime time) {
        return new MapTimedAction(time, commands);
    }
    public MapTimedAction withTime(Supplier<LocalDateTime> time) {
        return new MapTimedAction(time.get(), commands);
    }
    public MapTimedAction withTime(Function<LocalDateTime, LocalDateTime> time) {
        return new MapTimedAction(time.apply(this.time), commands);
    }
    public MapTimedAction withTime(BiFunction<MapTimedAction, LocalDateTime, LocalDateTime> time) {
        return new MapTimedAction(time.apply(this, this.time), commands);
    }
    public MapTimedAction withCommands(FuncMap<String, Command> commands) {
        return new MapTimedAction(time, commands);
    }
    public MapTimedAction withCommands(Supplier<FuncMap<String, Command>> commands) {
        return new MapTimedAction(time, commands.get());
    }
    public MapTimedAction withCommands(Function<FuncMap<String, Command>, FuncMap<String, Command>> commands) {
        return new MapTimedAction(time, commands.apply(this.commands));
    }
    public MapTimedAction withCommands(BiFunction<MapTimedAction, FuncMap<String, Command>, FuncMap<String, Command>> commands) {
        return new MapTimedAction(time, commands.apply(this, this.commands));
    }
    public static MapTimedAction fromMap(Map<String, ? extends Object> map) {
        Map<String, Getter> $schema = getStructSchema();
        @SuppressWarnings("unchecked")
        MapTimedAction obj = new MapTimedAction(
                    (LocalDateTime)$utils.fromMapValue(map.get("time"), $schema.get("time")),
                    (FuncMap<String, Command>)$utils.fromMapValue(map.get("commands"), $schema.get("commands"))
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
        map.put("commands", new functionalj.types.struct.generator.Getter("commands", new functionalj.types.Type("functionalj.map", null, "FuncMap", java.util.Arrays.asList(new functionalj.types.Generic("java.lang.String", null, java.util.Arrays.asList(new functionalj.types.Type("java.lang", null, "String", java.util.Collections.emptyList()))), new functionalj.types.Generic("functionalj.types.choice.Command", null, java.util.Arrays.asList(new functionalj.types.Type("functionalj.types.choice", null, "Command", java.util.Collections.emptyList()))))), false, functionalj.types.DefaultValue.REQUIRED));
        return map;
    }
    public String toString() {
        return "MapTimedAction[" + "time: " + time() + ", " + "commands: " + commands() + "]";
    }
    public int hashCode() {
        return toString().hashCode();
    }
    public boolean equals(Object another) {
        return (another == this) || ((another != null) && (getClass().equals(another.getClass())) && java.util.Objects.equals(toString(), another.toString()));
    }
    
    public static class MapTimedActionLens<HOST> extends ObjectLensImpl<HOST, MapTimedAction> {
        
        public final LocalDateTimeLens<HOST> time = createSubLens(MapTimedAction::time, MapTimedAction::withTime, LocalDateTimeLens::of);
        public final FuncMapLens<HOST, String, Command, StringLens<HOST>, ObjectLens<HOST, Command>> commands = createSubFuncMapLens(MapTimedAction::commands, MapTimedAction::withCommands, StringLens::of, ObjectLens::of);
        
        public MapTimedActionLens(LensSpec<HOST, MapTimedAction> spec) {
            super(spec);
        }
        
    }
    public static final class Builder {
        
        public final MapTimedActionBuilder_withoutCommands time(LocalDateTime time) {
            return (FuncMap<String, Command> commands)->{
            return ()->{
                return new MapTimedAction(
                    time,
                    commands
                );
            };
            };
        }
        
        public static interface MapTimedActionBuilder_withoutCommands {
            
            public MapTimedActionBuilder_ready commands(FuncMap<String, Command> commands);
            
        }
        public static interface MapTimedActionBuilder_ready {
            
            public MapTimedAction build();
            
            
            
        }
        
        
    }
    
}
package example.functionalj.structtype;

import functionalj.lens.core.LensSpec;
import functionalj.lens.lenses.IntegerLens;
import functionalj.lens.lenses.ObjectLensImpl;
import functionalj.pipeable.Pipeable;
import functionalj.types.IPostConstruct;
import functionalj.types.IStruct;
import functionalj.types.Type;
import functionalj.types.struct.generator.Getter;
import java.lang.Exception;
import java.lang.Object;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

// example.functionalj.structtype.StructTypeExamples.null

public class Circle3 implements IStruct,Pipeable<Circle3> {
    
    public static final Circle3.Circle3Lens<Circle3> theCircle3 = new Circle3.Circle3Lens<>(LensSpec.of(Circle3.class));
    public static final Circle3.Circle3Lens<Circle3> eachCircle3 = theCircle3;
    public final int x;
    public final int y;
    public final int radius;
    
    public Circle3(int x, int y, int radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        functionalj.result.ValidationException.ensure(example.functionalj.structtype.StructTypeExamples.Circle3(x,y,radius), this);
        if (this instanceof IPostConstruct) ((IPostConstruct)this).postConstruct();
    }
    
    public Circle3 __data() throws Exception  {
        return this;
    }
    public int x() {
        return x;
    }
    public int y() {
        return y;
    }
    public int radius() {
        return radius;
    }
    public Circle3 withX(int x) {
        return new Circle3(x, y, radius);
    }
    public Circle3 withX(Supplier<Integer> x) {
        return new Circle3(x.get(), y, radius);
    }
    public Circle3 withX(Function<Integer, Integer> x) {
        return new Circle3(x.apply(this.x), y, radius);
    }
    public Circle3 withX(BiFunction<Circle3, Integer, Integer> x) {
        return new Circle3(x.apply(this, this.x), y, radius);
    }
    public Circle3 withY(int y) {
        return new Circle3(x, y, radius);
    }
    public Circle3 withY(Supplier<Integer> y) {
        return new Circle3(x, y.get(), radius);
    }
    public Circle3 withY(Function<Integer, Integer> y) {
        return new Circle3(x, y.apply(this.y), radius);
    }
    public Circle3 withY(BiFunction<Circle3, Integer, Integer> y) {
        return new Circle3(x, y.apply(this, this.y), radius);
    }
    public Circle3 withRadius(int radius) {
        return new Circle3(x, y, radius);
    }
    public Circle3 withRadius(Supplier<Integer> radius) {
        return new Circle3(x, y, radius.get());
    }
    public Circle3 withRadius(Function<Integer, Integer> radius) {
        return new Circle3(x, y, radius.apply(this.radius));
    }
    public Circle3 withRadius(BiFunction<Circle3, Integer, Integer> radius) {
        return new Circle3(x, y, radius.apply(this, this.radius));
    }
    public static Circle3 fromMap(Map<String, Object> map) {
        Map<String, Getter> $schema = getStructSchema();
        
        Circle3 obj = new Circle3(
                    (int)$utils.fromMapValue(map.get("x"), $schema.get("x")),
                    (int)$utils.fromMapValue(map.get("y"), $schema.get("y")),
                    (int)$utils.fromMapValue(map.get("radius"), $schema.get("radius"))
                );
        return obj;
    }
    public Map<String, Object> __toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("x", functionalj.types.IStruct.$utils.toMapValueObject(x));
        map.put("y", functionalj.types.IStruct.$utils.toMapValueObject(y));
        map.put("radius", functionalj.types.IStruct.$utils.toMapValueObject(radius));
        return map;
    }
    public Map<String, Getter> __getSchema() {
        return getStructSchema();
    }
    public static Map<String, Getter> getStructSchema() {
        Map<String, Getter> map = new HashMap<>();
        map.put("x", new functionalj.types.struct.generator.Getter("x", new functionalj.types.Type(null, null, "int", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED));
        map.put("y", new functionalj.types.struct.generator.Getter("y", new functionalj.types.Type(null, null, "int", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED));
        map.put("radius", new functionalj.types.struct.generator.Getter("radius", new functionalj.types.Type(null, null, "int", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED));
        return map;
    }
    public String toString() {
        return "Circle3[" + "x: " + x() + ", " + "y: " + y() + ", " + "radius: " + radius() + "]";
    }
    public int hashCode() {
        return toString().hashCode();
    }
    public boolean equals(Object another) {
        return (another == this) || ((another != null) && (getClass().equals(another.getClass())) && java.util.Objects.equals(toString(), another.toString()));
    }
    
    public static class Circle3Lens<HOST> extends ObjectLensImpl<HOST, Circle3> {
        
        public final IntegerLens<HOST> x = createSubLensPrimitive(Circle3::x, Circle3::withX);
        public final IntegerLens<HOST> y = createSubLensPrimitive(Circle3::y, Circle3::withY);
        public final IntegerLens<HOST> radius = createSubLensPrimitive(Circle3::radius, Circle3::withRadius);
        
        public Circle3Lens(LensSpec<HOST, Circle3> spec) {
            super(spec);
        }
        
    }
    public static final class Builder {
        
        public final Circle3Builder_withoutY x(int x) {
            return (int y)->{
            return (int radius)->{
            return ()->{
                return new Circle3(
                    x,
                    y,
                    radius
                );
            };
            };
            };
        }
        
        public static interface Circle3Builder_withoutY {
            
            public Circle3Builder_withoutRadius y(int y);
            
        }
        public static interface Circle3Builder_withoutRadius {
            
            public Circle3Builder_ready radius(int radius);
            
        }
        public static interface Circle3Builder_ready {
            
            public Circle3 build();
            
            
            
        }
        
        
    }
    
}
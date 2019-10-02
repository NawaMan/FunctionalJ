package functionalj.types.struct;

import functionalj.lens.core.LensSpec;
import functionalj.lens.lenses.IntegerLens;
import functionalj.lens.lenses.ObjectLensImpl;
import functionalj.lens.lenses.StringLens;
import functionalj.pipeable.Pipeable;
import functionalj.types.IPostConstruct;
import functionalj.types.IStruct;
import functionalj.types.Type;
import functionalj.types.struct.generator.Getter;
import functionalj.types.struct.generator.SourceSpec;
import java.lang.Exception;
import java.lang.Object;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

// functionalj.types.struct.FromMethodTest.null

public class Car implements IStruct,Pipeable<Car> {
    
    public static final Car.CarLens<Car> theCar = new Car.CarLens<>(LensSpec.of(Car.class));
    public static final Car.CarLens<Car> eachCar = theCar;
    public final String make;
    public final int year;
    public final String color;
    public static final SourceSpec spec = new functionalj.types.struct.generator.SourceSpec(null, "functionalj.types.struct", "FromMethodTest", "Car", "functionalj.types.struct", null, "spec", "Car", new functionalj.types.struct.generator.SourceSpec.Configurations(true, false, true, true, true, true, true, true, ""), java.util.Arrays.asList(new functionalj.types.struct.generator.Getter("make", new functionalj.types.Type("java.lang", null, "String", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED), new functionalj.types.struct.generator.Getter("year", new functionalj.types.Type(null, null, "int", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED), new functionalj.types.struct.generator.Getter("color", new functionalj.types.Type("java.lang", null, "String", java.util.Collections.emptyList()), true, functionalj.types.DefaultValue.NULL)), java.util.Arrays.asList("Car", "Inventory", "Price", "CarForSale"));
    
    public Car(String make, int year) {
        this.make = $utils.notNull(make);
        this.year = $utils.notNull(year);
        this.color = null;
        functionalj.result.ValidationException.ensure(functionalj.types.struct.FromMethodTest.Car($utils.notNull(make),$utils.notNull(year),null), this);
        if (this instanceof IPostConstruct) ((IPostConstruct)this).postConstruct();
    }
    public Car(String make, int year, String color) {
        this.make = $utils.notNull(make);
        this.year = year;
        this.color = java.util.Optional.ofNullable(color).orElseGet(()->null);
        functionalj.result.ValidationException.ensure(functionalj.types.struct.FromMethodTest.Car(make,year,color), this);
        if (this instanceof IPostConstruct) ((IPostConstruct)this).postConstruct();
    }
    
    public Car __data() throws Exception  {
        return this;
    }
    public String make() {
        return make;
    }
    public int year() {
        return year;
    }
    public String color() {
        return color;
    }
    public Car withMake(String make) {
        return new Car(make, year, color);
    }
    public Car withMake(Supplier<String> make) {
        return new Car(make.get(), year, color);
    }
    public Car withMake(Function<String, String> make) {
        return new Car(make.apply(this.make), year, color);
    }
    public Car withMake(BiFunction<Car, String, String> make) {
        return new Car(make.apply(this, this.make), year, color);
    }
    public Car withYear(int year) {
        return new Car(make, year, color);
    }
    public Car withYear(Supplier<Integer> year) {
        return new Car(make, year.get(), color);
    }
    public Car withYear(Function<Integer, Integer> year) {
        return new Car(make, year.apply(this.year), color);
    }
    public Car withYear(BiFunction<Car, Integer, Integer> year) {
        return new Car(make, year.apply(this, this.year), color);
    }
    public Car withColor(String color) {
        return new Car(make, year, color);
    }
    public Car withColor(Supplier<String> color) {
        return new Car(make, year, color.get());
    }
    public Car withColor(Function<String, String> color) {
        return new Car(make, year, color.apply(this.color));
    }
    public Car withColor(BiFunction<Car, String, String> color) {
        return new Car(make, year, color.apply(this, this.color));
    }
    public static Car fromMap(Map<String, Object> map) {
        Map<String, Getter> $schema = getStructSchema();
        
        Car obj = new Car(
                    (String)$utils.fromMapValue(map.get("make"), $schema.get("make")),
                    (int)$utils.fromMapValue(map.get("year"), $schema.get("year")),
                    (String)$utils.fromMapValue(map.get("color"), $schema.get("color"))
                );
        return obj;
    }
    public Map<String, Object> __toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("make", functionalj.types.IStruct.$utils.toMapValueObject(make));
        map.put("year", functionalj.types.IStruct.$utils.toMapValueObject(year));
        map.put("color", functionalj.types.IStruct.$utils.toMapValueObject(color));
        return map;
    }
    public Map<String, Getter> __getSchema() {
        return getStructSchema();
    }
    public static Map<String, Getter> getStructSchema() {
        Map<String, Getter> map = new HashMap<>();
        map.put("make", new functionalj.types.struct.generator.Getter("make", new functionalj.types.Type("java.lang", null, "String", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED));
        map.put("year", new functionalj.types.struct.generator.Getter("year", new functionalj.types.Type(null, null, "int", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED));
        map.put("color", new functionalj.types.struct.generator.Getter("color", new functionalj.types.Type("java.lang", null, "String", java.util.Collections.emptyList()), true, functionalj.types.DefaultValue.NULL));
        return map;
    }
    public String toString() {
        return "Car[" + "make: " + make() + ", " + "year: " + year() + ", " + "color: " + color() + "]";
    }
    public int hashCode() {
        return toString().hashCode();
    }
    public boolean equals(Object another) {
        return (another == this) || ((another != null) && (getClass().equals(another.getClass())) && java.util.Objects.equals(toString(), another.toString()));
    }
    
    public static class CarLens<HOST> extends ObjectLensImpl<HOST, Car> {
        
        public final StringLens<HOST> make = createSubLens(Car::make, Car::withMake, StringLens::of);
        public final IntegerLens<HOST> year = createSubLensInt(Car::year, Car::withYear);
        public final StringLens<HOST> color = createSubLens(Car::color, Car::withColor, StringLens::of);
        
        public CarLens(LensSpec<HOST, Car> spec) {
            super(spec);
        }
        
    }
    public static final class Builder {
        
        public final CarBuilder_withoutYear make(String make) {
            return (int year)->{
            return (String color)->{
            return ()->{
                return new Car(
                    make,
                    year,
                    color
                );
            };
            };
            };
        }
        
        public static interface CarBuilder_withoutYear {
            
            public CarBuilder_withoutColor year(int year);
            
        }
        public static interface CarBuilder_withoutColor {
            
            public CarBuilder_ready color(String color);
            
            public default Car build() {
                return color(null).build();
            }
            
        }
        public static interface CarBuilder_ready {
            
            public Car build();
            
            
            
        }
        
        
    }
    
}
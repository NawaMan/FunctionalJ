package functionalj.types.struct;

import functionalj.lens.core.LensSpec;
import functionalj.lens.lenses.ObjectLensImpl;
import functionalj.pipeable.Pipeable;
import functionalj.types.IPostConstruct;
import functionalj.types.IStruct;
import functionalj.types.Type;
import functionalj.types.struct.Car;
import functionalj.types.struct.Car.CarLens;
import functionalj.types.struct.Price;
import functionalj.types.struct.Price.PriceLens;
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

public class CarForSale implements IStruct,Pipeable<CarForSale> {
    
    public static final CarForSale.CarForSaleLens<CarForSale> theCarForSale = new CarForSale.CarForSaleLens<>(LensSpec.of(CarForSale.class));
    public static final CarForSale.CarForSaleLens<CarForSale> eachCarForSale = theCarForSale;
    public final Car car;
    public final Price price;
    public static final SourceSpec spec = new functionalj.types.struct.generator.SourceSpec(null, "functionalj.types.struct", "FromMethodTest", "CarForSale", "functionalj.types.struct", null, "spec", null, new functionalj.types.struct.generator.SourceSpec.Configurations(true, false, true, true, true, true, true, true, ""), java.util.Arrays.asList(new functionalj.types.struct.generator.Getter("car", new functionalj.types.Type("functionalj.types.struct", null, "Car", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED), new functionalj.types.struct.generator.Getter("price", new functionalj.types.Type("functionalj.types.struct", null, "Price", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED)), java.util.Arrays.asList("Car", "Inventory", "Price", "CarForSale"));
    
    public CarForSale(Car car, Price price) {
        this.car = $utils.notNull(car);
        this.price = $utils.notNull(price);
        if (this instanceof IPostConstruct) ((IPostConstruct)this).postConstruct();
    }
    
    public CarForSale __data() throws Exception  {
        return this;
    }
    public Car car() {
        return car;
    }
    public Price price() {
        return price;
    }
    public CarForSale withCar(Car car) {
        return new CarForSale(car, price);
    }
    public CarForSale withCar(Supplier<Car> car) {
        return new CarForSale(car.get(), price);
    }
    public CarForSale withCar(Function<Car, Car> car) {
        return new CarForSale(car.apply(this.car), price);
    }
    public CarForSale withCar(BiFunction<CarForSale, Car, Car> car) {
        return new CarForSale(car.apply(this, this.car), price);
    }
    public CarForSale withPrice(Price price) {
        return new CarForSale(car, price);
    }
    public CarForSale withPrice(Supplier<Price> price) {
        return new CarForSale(car, price.get());
    }
    public CarForSale withPrice(Function<Price, Price> price) {
        return new CarForSale(car, price.apply(this.price));
    }
    public CarForSale withPrice(BiFunction<CarForSale, Price, Price> price) {
        return new CarForSale(car, price.apply(this, this.price));
    }
    public static CarForSale fromMap(Map<String, Object> map) {
        Map<String, Getter> $schema = getStructSchema();
        
        CarForSale obj = new CarForSale(
                    (Car)$utils.fromMapValue(map.get("car"), $schema.get("car")),
                    (Price)$utils.fromMapValue(map.get("price"), $schema.get("price"))
                );
        return obj;
    }
    public Map<String, Object> __toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("car", functionalj.types.IStruct.$utils.toMapValueObject(car));
        map.put("price", functionalj.types.IStruct.$utils.toMapValueObject(price));
        return map;
    }
    public Map<String, Getter> __getSchema() {
        return getStructSchema();
    }
    public static Map<String, Getter> getStructSchema() {
        Map<String, Getter> map = new HashMap<>();
        map.put("car", new functionalj.types.struct.generator.Getter("car", new functionalj.types.Type("functionalj.types.struct", null, "Car", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED));
        map.put("price", new functionalj.types.struct.generator.Getter("price", new functionalj.types.Type("functionalj.types.struct", null, "Price", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED));
        return map;
    }
    public String toString() {
        return "CarForSale[" + "car: " + car() + ", " + "price: " + price() + "]";
    }
    public int hashCode() {
        return toString().hashCode();
    }
    public boolean equals(Object another) {
        return (another == this) || ((another != null) && (getClass().equals(another.getClass())) && java.util.Objects.equals(toString(), another.toString()));
    }
    
    public static class CarForSaleLens<HOST> extends ObjectLensImpl<HOST, CarForSale> {
        
        public final Car.CarLens<HOST> car = createSubLens(CarForSale::car, CarForSale::withCar, Car.CarLens::new);
        public final Price.PriceLens<HOST> price = createSubLens(CarForSale::price, CarForSale::withPrice, Price.PriceLens::new);
        
        public CarForSaleLens(LensSpec<HOST, CarForSale> spec) {
            super(spec);
        }
        
    }
    public static final class Builder {
        
        public final CarForSaleBuilder_withoutPrice car(Car car) {
            return (Price price)->{
            return ()->{
                return new CarForSale(
                    car,
                    price
                );
            };
            };
        }
        
        public static interface CarForSaleBuilder_withoutPrice {
            
            public CarForSaleBuilder_ready price(Price price);
            
        }
        public static interface CarForSaleBuilder_ready {
            
            public CarForSale build();
            
            
            
        }
        
        
    }
    
}
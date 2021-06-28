package functionalj.types.struct;

import functionalj.lens.core.LensSpec;
import functionalj.lens.lenses.FuncListLens;
import functionalj.lens.lenses.ObjectLensImpl;
import functionalj.list.FuncList;
import functionalj.pipeable.Pipeable;
import functionalj.types.IPostConstruct;
import functionalj.types.IStruct;
import functionalj.types.struct.generator.Getter;
import functionalj.types.struct.generator.SourceSpec;
import java.lang.Exception;
import java.lang.Object;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Generated;

@Generated(value = "FunctionalJ",date = "2021-06-27T22:28:42.518754", comments = "functionalj.types.struct.FromMethodTest")

public class Inventory implements IStruct,Pipeable<Inventory> {
    
    public static final Inventory.InventoryLens<Inventory> theInventory = new Inventory.InventoryLens<>(LensSpec.of(Inventory.class));
    public static final Inventory.InventoryLens<Inventory> eachInventory = theInventory;
    public final FuncList<CarForSale> cars;
    public static final SourceSpec spec = new functionalj.types.struct.generator.SourceSpec(null, "functionalj.types.struct", "FromMethodTest", "Inventory", "functionalj.types.struct", null, "spec", null, new functionalj.types.struct.generator.SourceSpec.Configurations(true, false, true, true, true, true, true, true, ""), java.util.Arrays.asList(new functionalj.types.struct.generator.Getter("cars", new functionalj.types.Type("functionalj.list", null, "FuncList", java.util.Arrays.asList(new functionalj.types.Generic("functionalj.types.struct.CarForSale", null, java.util.Arrays.asList(new functionalj.types.Type("functionalj.types.struct", null, "CarForSale", java.util.Collections.emptyList()))))), true, functionalj.types.DefaultValue.EMPTY)), java.util.Arrays.asList("Car", "Inventory", "Price", "CarForSale"));
    
    public Inventory() {
        this.cars = functionalj.list.FuncList.empty();
        if (this instanceof IPostConstruct) ((IPostConstruct)this).postConstruct();
    }
    public Inventory(FuncList<CarForSale> cars) {
        this.cars = java.util.Optional.ofNullable(cars).orElseGet(()->functionalj.list.FuncList.empty());
        if (this instanceof IPostConstruct) ((IPostConstruct)this).postConstruct();
    }
    
    public Inventory __data() throws Exception  {
        return this;
    }
    public FuncList<CarForSale> cars() {
        return cars;
    }
    public Inventory withCars(CarForSale ... cars) {
        return new Inventory(functionalj.list.ImmutableFuncList.of(cars));
    }
    public Inventory withCars(FuncList<CarForSale> cars) {
        return new Inventory(cars);
    }
    public Inventory withCars(Supplier<FuncList<CarForSale>> cars) {
        return new Inventory(cars.get());
    }
    public Inventory withCars(Function<FuncList<CarForSale>, FuncList<CarForSale>> cars) {
        return new Inventory(cars.apply(this.cars));
    }
    public Inventory withCars(BiFunction<Inventory, FuncList<CarForSale>, FuncList<CarForSale>> cars) {
        return new Inventory(cars.apply(this, this.cars));
    }
    public static Inventory fromMap(Map<String, ? extends Object> map) {
        Map<String, Getter> $schema = getStructSchema();
        @SuppressWarnings("unchecked")
        Inventory obj = new Inventory(
                    (FuncList<CarForSale>)$utils.fromMapValue(map.get("cars"), $schema.get("cars"))
                );
        return obj;
    }
    public Map<String, Object> __toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("cars", functionalj.types.IStruct.$utils.toMapValueObject(cars));
        return map;
    }
    public Map<String, Getter> __getSchema() {
        return getStructSchema();
    }
    public static Map<String, Getter> getStructSchema() {
        java.util.Map<String, functionalj.types.struct.generator.Getter> map = new java.util.HashMap<>();
        map.put("cars", new functionalj.types.struct.generator.Getter("cars", new functionalj.types.Type("functionalj.list", null, "FuncList", java.util.Arrays.asList(new functionalj.types.Generic("functionalj.types.struct.CarForSale", null, java.util.Arrays.asList(new functionalj.types.Type("functionalj.types.struct", null, "CarForSale", java.util.Collections.emptyList()))))), true, functionalj.types.DefaultValue.EMPTY));
        return map;
    }
    public String toString() {
        return "Inventory[" + "cars: " + cars() + "]";
    }
    public int hashCode() {
        return toString().hashCode();
    }
    public boolean equals(Object another) {
        return (another == this) || ((another != null) && (getClass().equals(another.getClass())) && java.util.Objects.equals(toString(), another.toString()));
    }
    
    public static class InventoryLens<HOST> extends ObjectLensImpl<HOST, Inventory> {
        
        public final FuncListLens<HOST, CarForSale, CarForSale.CarForSaleLens<HOST>> cars = createSubFuncListLens(Inventory::cars, Inventory::withCars, CarForSale.CarForSaleLens::new);
        
        public InventoryLens(LensSpec<HOST, Inventory> spec) {
            super(spec);
        }
        
    }
    public static final class Builder {
        
        public final InventoryBuilder_ready cars(FuncList<CarForSale> cars) {
            return ()->{
                return new Inventory(
                    cars
                );
            };
        }
        
        public static interface InventoryBuilder_ready {
            
            public Inventory build();
            
            
            
        }
        
        
        public Inventory build() {
            return cars(functionalj.list.FuncList.empty()).build();
        }
        
    }
    
}
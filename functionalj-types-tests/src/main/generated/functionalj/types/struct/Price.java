package functionalj.types.struct;

import functionalj.lens.core.LensSpec;
import functionalj.lens.lenses.IntegerLens;
import functionalj.lens.lenses.ObjectLensImpl;
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

public class Price implements IStruct,Pipeable<Price> {
    
    public static final Price.PriceLens<Price> thePrice = new Price.PriceLens<>(LensSpec.of(Price.class));
    public static final Price.PriceLens<Price> eachPrice = thePrice;
    public final int price;
    public final int discountPercent;
    public static final SourceSpec spec = new functionalj.types.struct.generator.SourceSpec(null, "functionalj.types.struct", "FromMethodTest", "Price", "functionalj.types.struct", null, "spec", null, new functionalj.types.struct.generator.SourceSpec.Configurations(true, false, true, true, true, true, true, true, ""), java.util.Arrays.asList(new functionalj.types.struct.generator.Getter("price", new functionalj.types.Type(null, null, "int", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED), new functionalj.types.struct.generator.Getter("discountPercent", new functionalj.types.Type(null, null, "int", java.util.Collections.emptyList()), true, functionalj.types.DefaultValue.ZERO)), java.util.Arrays.asList("Car", "Inventory", "Price", "CarForSale"));
    
    public Price(int price) {
        this.price = $utils.notNull(price);
        this.discountPercent = 0;
        if (this instanceof IPostConstruct) ((IPostConstruct)this).postConstruct();
    }
    public Price(int price, int discountPercent) {
        this.price = price;
        this.discountPercent = java.util.Optional.ofNullable(discountPercent).orElseGet(()->0);
        if (this instanceof IPostConstruct) ((IPostConstruct)this).postConstruct();
    }
    
    public Price __data() throws Exception  {
        return this;
    }
    public int price() {
        return price;
    }
    public int discountPercent() {
        return discountPercent;
    }
    public Price withPrice(int price) {
        return new Price(price, discountPercent);
    }
    public Price withPrice(Supplier<Integer> price) {
        return new Price(price.get(), discountPercent);
    }
    public Price withPrice(Function<Integer, Integer> price) {
        return new Price(price.apply(this.price), discountPercent);
    }
    public Price withPrice(BiFunction<Price, Integer, Integer> price) {
        return new Price(price.apply(this, this.price), discountPercent);
    }
    public Price withDiscountPercent(int discountPercent) {
        return new Price(price, discountPercent);
    }
    public Price withDiscountPercent(Supplier<Integer> discountPercent) {
        return new Price(price, discountPercent.get());
    }
    public Price withDiscountPercent(Function<Integer, Integer> discountPercent) {
        return new Price(price, discountPercent.apply(this.discountPercent));
    }
    public Price withDiscountPercent(BiFunction<Price, Integer, Integer> discountPercent) {
        return new Price(price, discountPercent.apply(this, this.discountPercent));
    }
    public static Price fromMap(Map<String, Object> map) {
        Map<String, Getter> $schema = getStructSchema();
        
        Price obj = new Price(
                    (int)$utils.fromMapValue(map.get("price"), $schema.get("price")),
                    (int)$utils.fromMapValue(map.get("discountPercent"), $schema.get("discountPercent"))
                );
        return obj;
    }
    public Map<String, Object> __toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("price", functionalj.types.IStruct.$utils.toMapValueObject(price));
        map.put("discountPercent", functionalj.types.IStruct.$utils.toMapValueObject(discountPercent));
        return map;
    }
    public Map<String, Getter> __getSchema() {
        return getStructSchema();
    }
    public static Map<String, Getter> getStructSchema() {
        Map<String, Getter> map = new HashMap<>();
        map.put("price", new functionalj.types.struct.generator.Getter("price", new functionalj.types.Type(null, null, "int", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED));
        map.put("discountPercent", new functionalj.types.struct.generator.Getter("discountPercent", new functionalj.types.Type(null, null, "int", java.util.Collections.emptyList()), true, functionalj.types.DefaultValue.ZERO));
        return map;
    }
    public String toString() {
        return "Price[" + "price: " + price() + ", " + "discountPercent: " + discountPercent() + "]";
    }
    public int hashCode() {
        return toString().hashCode();
    }
    public boolean equals(Object another) {
        return (another == this) || ((another != null) && (getClass().equals(another.getClass())) && java.util.Objects.equals(toString(), another.toString()));
    }
    
    public static class PriceLens<HOST> extends ObjectLensImpl<HOST, Price> {
        
        public final IntegerLens<HOST> price = createSubLensInt(Price::price, Price::withPrice);
        public final IntegerLens<HOST> discountPercent = createSubLensInt(Price::discountPercent, Price::withDiscountPercent);
        
        public PriceLens(LensSpec<HOST, Price> spec) {
            super(spec);
        }
        
    }
    public static final class Builder {
        
        public final PriceBuilder_withoutDiscountPercent price(int price) {
            return (int discountPercent)->{
            return ()->{
                return new Price(
                    price,
                    discountPercent
                );
            };
            };
        }
        
        public static interface PriceBuilder_withoutDiscountPercent {
            
            public PriceBuilder_ready discountPercent(int discountPercent);
            
            public default Price build() {
                return discountPercent(0).build();
            }
            
        }
        public static interface PriceBuilder_ready {
            
            public Price build();
            
            
            
        }
        
        
    }
    
}
package functionalj.lens.lenses.java.time;

import java.time.ZoneOffset;
import java.util.function.Function;

import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.ConcreteAccess;
import functionalj.lens.lenses.IntegerAccessPrimitive;


@FunctionalInterface
public interface ZoneOffsetAccess<HOST>
                   extends AnyAccess             <HOST, ZoneOffset>
                   ,       ZoneIdAccess          <HOST, ZoneOffset>
                   ,       TemporalAccessorAccess<HOST, ZoneOffset>
                   ,       TemporalAdjusterAccess<HOST, ZoneOffset>
                   ,       ConcreteAccess        <HOST, ZoneOffset, ZoneOffsetAccess<HOST>> {
    
    public static <H> ZoneOffsetAccess<H> of(Function<H, ZoneOffset> func) {
        return func::apply;
    }
    
   public default ZoneOffsetAccess<HOST> newAccess(Function<HOST, ZoneOffset> accessToValue) {
       return accessToValue::apply;
   }
   
   public default IntegerAccessPrimitive<HOST> getTotalSeconds() {
       return host -> {
           var value = apply(host);
           return value.getTotalSeconds();
       };
   }
   
   public default ZoneRulesAccess<HOST> getRules() {
       return host -> {
           var value = apply(host);
           return value.getRules();
       };
   }
   
}

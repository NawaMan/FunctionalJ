package functionalj.lens.lenses.java.time;

import java.time.ZoneOffset;
import java.util.function.Function;

import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.ConcreteAccess;
import functionalj.lens.lenses.IntegerAccess;
import lombok.val;

@FunctionalInterface
public interface ZoneOffsetAccess<HOST>
                   extends
                       AnyAccess             <HOST, ZoneOffset>,
                       ZoneIdAccess          <HOST, ZoneOffset>,
                       TemporalAccessorAccess<HOST, ZoneOffset>,
                       TemporalAdjusterAccess<HOST, ZoneOffset>,
                       ConcreteAccess        <HOST, ZoneOffset, ZoneOffsetAccess<HOST>> {
                       
   public default ZoneOffsetAccess<HOST> newAccess(Function<HOST, ZoneOffset> accessToValue) {
       return accessToValue::apply;
   }
   
   public default IntegerAccess<HOST> getTotalSeconds() {
       return host -> {
           val value = apply(host);
           return value.getTotalSeconds();
       };
   }
   
   // TODO
//   public ZoneRules getRules();
   
}

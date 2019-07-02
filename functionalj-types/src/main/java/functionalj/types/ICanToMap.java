package functionalj.types;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import functionalj.types.choice.IChoice;
import functionalj.types.struct.generator.Type;
import lombok.val;

public interface ICanToMap {
    
    public Map<String, Object> __toMap();
    
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Object toMapValueObject(Object data) {
        if (data instanceof List) {
            return ((List)data).stream()
                    .map(ICanToMap::toMapValueObject)
                    .collect(toList());
        }
        
        return (data instanceof ICanToMap) 
                ? ((ICanToMap)data).__toMap() 
                : data;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <T> T fromMapValue(
            Object           obj, 
            Class<T>         clzz,
            DefaultValue     defaultValue,
            Supplier<Object> defaultValueSupplier) {
        if ((obj == null) && ((defaultValue == DefaultValue.UNSPECIFIED) || (defaultValue == DefaultValue.NULL)))
            return null;
        
        if ((obj instanceof Map) && IStruct.class.isAssignableFrom(clzz))
            return (T)IStruct.$utils.fromMap((Map)obj, (Class)clzz);
        if ((obj instanceof Map) && IChoice.class.isAssignableFrom(clzz))
            return (T)IChoice.$utils.fromMap((Map)obj, (Class)clzz);
        
        if (obj != null)
            return (T)obj;
        
        val value = defaultValueSupplier.get();
        return (T)value;
    }
    
    public static Supplier<Object> defaultValueOf(Type type, DefaultValue defaultValue) {
        return ()->DefaultValue.defaultValue(type, defaultValue);
    }

}

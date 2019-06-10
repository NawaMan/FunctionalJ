package functionalj.types;

import java.util.Map;

import functionalj.types.choice.AbstractChoiceClass;

public class FuncType {
    
    public static <T extends IStruct> T structFromMap(Map<String, Object> map, Class<T> clzz) {
        return (T)IStruct.$utils.fromMap(map, (Class<T>)clzz);
    }
    public static <T extends AbstractChoiceClass<T>> T choiceFromMap(Map<String, Object> map, Class<T> clzz) {
        return (T)AbstractChoiceClass.$utils.fromMap(map, (Class<T>)clzz);
    }
    
}

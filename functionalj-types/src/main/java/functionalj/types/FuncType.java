package functionalj.types;

import java.util.Map;

import functionalj.types.choice.IChoice;

public class FuncType {
    
    public static <T extends IStruct> T structFromMap(Map<String, Object> map, Class<T> clzz) {
        return (T)IStruct.$utils.fromMap(map, (Class<T>)clzz);
    }
    public static <T extends IChoice<T>> T choiceFromMap(Map<String, Object> map, Class<T> clzz) {
        return (T)IChoice.$utils.fromMap(map, (Class<T>)clzz);
    }
    
}

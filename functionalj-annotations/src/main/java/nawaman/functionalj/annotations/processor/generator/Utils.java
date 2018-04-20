package nawaman.functionalj.annotations.processor.generator;

import lombok.val;

class Utils {
    
    public static String withMethodName(Getter getter) {
        val name = getter.getName();
        return "with" + name.substring(0,1).toUpperCase() + name.substring(1);
    }

}

package functionalj.functions;

import java.nio.file.Path;
import java.nio.file.Paths;

public class PathFuncs {
    
    public static Func1<String, Path> strToPath() {
        return str -> Paths.get(str);
    }
    
    public static Func1<String, Path> strToPath(String basePath) {
        return str -> Paths.get(basePath, str);
    }
    
    public static Func1<String, Path> strToPath(Path basePath) {
        return str -> Paths.get(basePath.toString(), str);
    }
    
}

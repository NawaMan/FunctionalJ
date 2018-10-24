package functionalj;

import functionalj.ref.Ref;

public class Env {
    
    public static Ref<Env.Instance> ref = Ref.to(Env.Instance.class);
    
    public static long currentMilliSecond() {
        return ref
                .elseGet(Instance::instance)
                .mapTo  (Instance::currentMilliSecond)
                .orGet  (System  ::currentTimeMillis);
    }
    
    
    public static class Instance {
        
        public static Instance instance = new Instance();
        
        public static Instance instance() {
            return new Instance();
        }
        
        public long currentMilliSecond() {
            return System.currentTimeMillis();
        }
        
    }
    
}

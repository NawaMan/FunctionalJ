package functionalj.environments;

public final class Console {
    
    private Console() {
    }
    
    public static void println(Object line) {
        Env.console().println(line);
    }
    
    
    public static interface Instance {
        
        public void println(Object line);
        
    }
    
    public static class System implements Instance {
        
        public static final Console.Instance instance = new System();
        
        @Override
        public void println(Object line) {
            java.lang.System.out.println(line);
        }
        
    }
    
}

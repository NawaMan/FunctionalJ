package functionalj.environments;

import functionalj.ref.Ref;

public final class Env {
    
    public static final class refs {
        
        public static final Ref<Time.Instance>    time    = Ref.ofValue(Time.System.instance);
        public static final Ref<Console.Instance> console = Ref.ofValue(Console.System.instance);
        
        public static final Ref<AsyncRunner>   async = Ref.ofValue(AsyncRunner.threadFactory);
        
    }
    
    // TODO - AsyncComputer? (that returnsPromise)
    // TODO - File lister, reader - bytes,string,line. -> no-idea about seekable
    // TODO - Network
    // TODO - TimeFormatter
    // TODO - ObjectPrinter
    // TODO - Console
    // TODO - User
    // TODO - Log
    // TODO - Error handling
    
    public static Time.Instance time() {
        return Env.refs.time
                .orElse(Time.System.instance);
    }
    
    public static AsyncRunner async() {
        return Env.refs.async
                .orElse(AsyncRunner.threadFactory);
    }
    
    public static Console.Instance console() {
        return Env.refs.console
                .orElse(Console.System.instance);
    }
    
}

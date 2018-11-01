package functionalj.environments;

import functionalj.ref.Ref;

public final class Env {
    
    public static final class refs {
        
        public static final Ref<AsyncRunner>      async   = Ref.ofValue(AsyncRunner.threadFactory);
        public static final Ref<Console.Instance> console = Ref.ofValue(Console.System.instance);
        public static final Ref<Log.Instance>     log     = Ref.ofValue(Log.Instance.instance);
        public static final Ref<Time.Instance>    time    = Ref.ofValue(Time.System.instance);
        
    }
    
    // TODO - File lister, reader - bytes,string,line. -> no-idea about seekable
    // TODO - Network
    // TODO - TimeFormatter
    // TODO - Error handling
    // TODO - Random
    // TODO - Runtime
    
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
    public static Log.Instance log() {
        return Env.refs.log
                .orElse(Log.Instance.instance);
    }
    
}

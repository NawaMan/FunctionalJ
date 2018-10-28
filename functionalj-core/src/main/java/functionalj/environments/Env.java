package functionalj.environments;

import functionalj.ref.Ref;

public final class Env {
    
    public static final Ref<Time>        time        = Ref.ofValue(Time.instance);
    public static final Ref<AsyncRunner> asyncRunner = Ref.ofValue(AsyncRunner.threadFactory);
    // TODO - AsyncComputer? (that returnsPromise)
    // TODO - File lister, reader - bytes,string,line. -> no-idea about seekable
    // TODO - Network
    // TODO - TimeFormatter
    // TODO - ObjectPrinter
    // TODO - Console
    // TODO - User
    // TODO - Log
    // TODO - Error handling
    
    public static Time time() {
        return time.value();
    }
    
    public static AsyncRunner asyncRunner() {
        return asyncRunner
                .orElse(AsyncRunner.threadFactory);
    }
    
}

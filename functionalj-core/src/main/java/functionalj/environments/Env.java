package functionalj.environments;

import functionalj.ref.Ref;

public final class Env {
    
    public static final Ref<TimeKeeper>  timerKeeper = Ref.ofValue(TimeKeeper.instance);
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
    
    public static TimeKeeper timerKeeper() {
        return timerKeeper.value();
    }
    
    public static AsyncRunner asyncRunner() {
        return asyncRunner
                .orElse(AsyncRunner.threadFactory);
    }
    
}

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
    
    public static long currentMilliSecond() {
        return timerKeeper
                .elseUse(TimeKeeper.instance)
                .mapTo  (TimeKeeper::currentMilliSecond)
                .orGet  (System    ::currentTimeMillis);
    }
    
    public static AsyncRunner asyncRunner() {
        return asyncRunner
                .orElse(AsyncRunner.threadFactory);
    }
    
}

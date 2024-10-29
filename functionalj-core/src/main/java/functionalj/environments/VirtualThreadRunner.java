package functionalj.environments;

import lombok.val;

public class VirtualThreadRunner implements AsyncRunner {
    
    public static final VirtualThreadRunner instance = new VirtualThreadRunner();
    
    public static final boolean supportVirtualThread = supportVirtualThread();
    
    public static final AsyncRunner asAsyncRunner(AsyncRunner fallback) {
        return supportVirtualThread ? instance : fallback;
    }
            
    public static boolean supportVirtualThread() {
        try {
            instance.startVirtualThread(() -> {});
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }
    
    @Override
    public void acceptUnsafe(Runnable runnable) throws Exception {
        startVirtualThread(runnable);
    }
    
    public Thread startVirtualThread(Runnable runnable) {
        try {
            val startVirtualThreadMethod = Thread.class.getMethod("startVirtualThread", Runnable.class);
            return (Thread)startVirtualThreadMethod.invoke(null, runnable);
        } catch (NoSuchMethodException e) {
            val javaVersion = extractJavaVersion();
            if (javaVersion < 21) {
                throw new RuntimeException("Virtual thread is not supported in this version of Java: " + javaVersion);
            }
            
            throw new RuntimeException("Unable to create a virtual thread! No method 'startVirtualThread' found.", e);
        } catch (Exception e) {
            throw new RuntimeException("Unable to create a virtual thread!", e);
        }
    }
    
    private static int extractJavaVersion() {
        val javaVersion = System.getProperty("java.version");
        try {
            return Integer.parseInt(javaVersion.replaceAll("\\..+$", ""));
        } catch (Exception exception) {
            throw new RuntimeException(
                    "Unable to create a virtual thread! No method 'startVirtualThread' found. java.version=" + javaVersion,
                    exception);
        }
    }
    
}

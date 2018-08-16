package functionalj.types.promise;

@SuppressWarnings("javadoc")
public interface Wait{
    
    public static WaitForever forever() {
        return WaitForever.instance ;
    }
    
    public WaitSession newSession();
    
}

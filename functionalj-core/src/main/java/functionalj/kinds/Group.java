package functionalj.kinds;

@SuppressWarnings("javadoc")
public interface Group<TYPE, DATA> extends Monoid<TYPE, DATA> {
    
    public Group<TYPE, DATA> _invert();
    
}

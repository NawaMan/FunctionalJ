package functionalj.annotations.uniontype;

import static functionalj.annotations.uniontype.Option.Some;

import org.junit.Test;

import functionalj.annotations.UnionType;

public class UnionTypeWithGeneric {

    
    @UnionType(name="Option")
    public interface OptionSpec<T> {
        void None();
        void Some(T value);
    }
    
    @Test
    public void test() {
        Some(5);
        Some(6L);
    }
    
    @UnionType(name="OptionNumber")
    public interface OptionNumberSpec<T extends Number> {
        void None();
        void Some(T value);
    }
    
    @Test
    public void testWithWidecard() {
        OptionNumber.Some(5);
        OptionNumber.Some(6L);
    }
    
}

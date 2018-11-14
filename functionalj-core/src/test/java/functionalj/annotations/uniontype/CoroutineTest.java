package functionalj.annotations.uniontype;

import org.junit.Test;

import functionalj.function.Func;
import functionalj.function.Func0;
import functionalj.function.Func1;
import functionalj.map.FuncMap;
import functionalj.promise.Promise;
import lombok.val;

public class CoroutineTest {
    
    public static abstract class CR<IN, OUT> {
        
        public abstract OUT get();
        
    }
    
    public static class CRLast<IN, OUT> extends CR<IN, OUT> {
        private final Func0<OUT> f;
        public CRLast(Func0<OUT> f) {
            this.f = f;
        }
        
        public OUT get() {
            return f.get();
        }
    }
    
    public static class CRBetween<IN, MID, OUT> extends CR<IN, OUT> {
        private final Func0<MID> f;
        private final  Func1<MID, CR<MID, OUT>> n;
        public CRBetween(Func0<MID> f, Func1<MID, CR<MID, OUT>> n) {
            this.f = f;
            this.n = n;
        }
        
        public OUT get() {
            val fValue = f.get();
            val newCr = n.apply(fValue);
            return newCr.get();
        }
    }
    
    public static <I, M, O> CR<I, O> c(Func0<M> f, Func1<M, CR<M, O>> next) {
        return new CRBetween<I, M, O>(f, next);
    }
    
    public static <I, O> CR<I, O> c(Func0<O> supplier) {
        return new CRLast<I, O>(supplier);
    }
    
    public static class User {
        private final String id;
        private final String name;
        public User(String id, String name) {
            this.id = id;
            this.name = name;
        }
        public String getId() {
            return id;
        }
        public String getName() {
            return name;
        }
        
    }
    
    @Test
    public void test() {
        val c1 = c(()->"One");
        System.out.println(c1.get());
        
        
        val c2 = c(()-> "One",        one -> 
                 c(()-> one.length(), two ->
                 c(()-> two + 1)));
        System.out.println(c2.get());
        
        val m  = FuncMap.of(1, "One", 2, "Two", 3, "Three", 4, "Four", 5, "Five");
        val f1 = Func.F(String::length);
        val f2 = Func.F((Integer i) -> (String)m.get(i));
        
        val f1d = Func.F(String::length).defer();
        val f2d = Func.F((Integer i) -> (String)m.get(i)).defer();
        
        
        val c3 =    c(()-> Promise.ofValue("One"), one
                 -> c(()-> one.map(f1)          , two
                 -> c(()-> two.map(f2)          , three
                 -> c(()-> three.map(f1)))));
        
        System.out.println(((Promise<Integer>)c3.get()).getResult());
        
        val c4 =    c(()-> Promise.ofValue("One"), (Promise<String>    one)
                 -> c(()-> f1d.apply(one)        , (Promise<Integer>   two)
                 -> c(()-> f2d.apply(two)        , (Promise<String>  three)
                 -> c(()-> three.map(f1)))));
        System.out.println(c4.get().getResult());
        
        val c5 =    c(()-> Promise.ofValue("One"), (Promise<String>    one)
                 -> c(()-> f1d.apply(one)        , (Promise<Integer>   two)
                 -> c(()-> f2d.apply(two)        , (Promise<String>  three)
                 -> c(()-> three.map(f1)))));
        System.out.println(c5.get().getResult());
    }

}

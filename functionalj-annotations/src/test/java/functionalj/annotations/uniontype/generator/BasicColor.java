package functionalj.annotations.uniontype.generator;

import functionalj.annotations.Absent;
import functionalj.annotations.uniontype.UnionTypeSwitch;
import functionalj.annotations.uniontype.generator.UnionTypeExampleTest.Union1TypeSpec;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

@SuppressWarnings("javadoc")
public abstract class BasicColor {
    
    public static final BasicColor White() { return White.instance; }
    public static final BasicColor Black() { return Black.instance; }
    public static final BasicColor RGB(int r, int g, int b) {
        Union1TypeSpec.validateRGB(r, g, b);
        return new RGB(r, g, b);
    }
    
    private BasicColor() {}
    
    public static final class White extends BasicColor {
        private static final White instance = new White();
        private White() {}
    }
    public static final class Black extends BasicColor {
        private static final Black instance = new Black();
        private Black() {}
    }
    public static final class RGB extends BasicColor {
        private int r;
        private int g;
        private int b;
        private RGB(int r, int g, int b) {
            this.r = r;
            this.g = g;
            this.b = b;
        }
        public int r() { return r; }
        public int g() { return g; }
        public int b() { return b; }
        public RGB withR(int r) { return new RGB(r, g, b); }
        public RGB withG(int g) { return new RGB(r, g, b); }
        public RGB withB(int b) { return new RGB(r, g, b); }
    }
    
    public <T> BasicColorSwitchWhiteBlackRGB<T> switchMap() {
        return switchMapTo(this, null);
    }
    
    public <T> BasicColorSwitchWhiteBlackRGB<T> switchMapTo(Class<T> clzz) {
        return new BasicColorSwitchWhiteBlackRGB<T>(this, null);
    }
    
    public static <T> BasicColorSwitchWhiteBlackRGB<T> switchMap(BasicColor value) {
        return switchMapTo(value, null);
    }
    
    public static <T> BasicColorSwitchWhiteBlackRGB<T> switchMapTo(BasicColor value, Class<T> clzz) {
        return new BasicColorSwitchWhiteBlackRGB<T>(value, null);
    }
    
    private volatile String toString = null;
    @Override
    public String toString() {
        if (toString != null)
            return toString;
        synchronized(this) {
            if (toString != null)
                return toString;
            toString = this.switchMapTo(String.class)
                    .white("White")
                    .black("Black")
                    .rgb(rgb -> "RGB(" + String.format("%1$s,%2$s,%3$s", rgb.r,rgb.g,rgb.b) + ")")
            ;
            return toString;
        }
    }
    public String alternativeString() {
        return this.switchMapTo(String.class)
                    .white("RGB(255,255,255)")
                    .black("RGB(0,0,0)")
                    .rgb(it -> it.toString())
        ;
    }
    @Override
    public int hashCode() {
        return toString().hashCode();
    }
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BasicColor))
            return false;
        
        if (this == obj)
            return true;
        
        String objToString  = obj.toString();
        String thisToString = this.toString();
        if (thisToString.equals(objToString))
            return true;
        
        String objAlternative  = ((BasicColor)obj).alternativeString();
        String thisAlternative = this.alternativeString();
        return thisAlternative.equals(objAlternative);
    }
    
    
    public static class BasicColorSwitchWhiteBlackRGB<T> extends UnionTypeSwitch<BasicColor, T> {
        private BasicColorSwitchWhiteBlackRGB(BasicColor value, Function<BasicColor, T> action) { super(value, action); }
        
        public BasicColorSwitchBlackRGB<T> white(T action) {
            return white(d->action);
        }
        public BasicColorSwitchBlackRGB<T> white(Supplier<T> action) {
            return white(d->action.get());
        }
        public BasicColorSwitchBlackRGB<T> white(Function<? super White, T> theAction) {
            @SuppressWarnings("unchecked")
            Function<BasicColor, T> oldAction = (Function<BasicColor, T>)action;
            Function<BasicColor, T> newAction =
                (action != null)
                ? oldAction : 
                    (value instanceof White)
                    ? (Function<BasicColor, T>)(d -> theAction.apply((White)d))
                    : oldAction;
            
            return new BasicColorSwitchBlackRGB <T>(value, newAction);
        }
    }
    public static class BasicColorSwitchBlackRGB<T> extends UnionTypeSwitch<BasicColor, T> {
        private BasicColorSwitchBlackRGB(BasicColor value, Function<BasicColor, T> action) { super(value, action); }
        
        public BasicColorSwitchRGB<T> black(T action) {
            return black(d->action);
        }
        public BasicColorSwitchRGB<T> black(Supplier<T> action) {
            return black(d->action.get());
        }
        public BasicColorSwitchRGB<T> black(Function<? super Black, T> theAction) {
            @SuppressWarnings("unchecked")
            Function<BasicColor, T> oldAction = (Function<BasicColor, T>)action;
            Function<BasicColor, T> newAction =
                (action != null)
                ? oldAction : 
                    (value instanceof Black)
                    ? (Function<BasicColor, T>)(d -> theAction.apply((Black)d))
                    : oldAction;
            
            return new BasicColorSwitchRGB <T>(value, newAction);
        }
    }
    public static class BasicColorSwitchRGB<T> extends UnionTypeSwitch<BasicColor, T> {
        private BasicColorSwitchRGB(BasicColor value, Function<BasicColor, T> action) { super(value, action); }
        
        public T rgb(T action) {
            return rgb(d->action);
        }
        public T rgb(Supplier<T> action) {
            return rgb(d->action.get());
        }
        public T rgb(Function<? super RGB, T> theAction) {
            @SuppressWarnings("unchecked")
            Function<BasicColor, T> oldAction = (Function<BasicColor, T>)action;
            Function<BasicColor, T> newAction =
                (action != null)
                ? oldAction : 
                    (value instanceof RGB)
                    ? (Function<BasicColor, T>)(d -> theAction.apply((RGB)d))
                    : oldAction;
            
            return newAction.apply(value);
        }
        
        public BasicColorSwitchRGB<T> rgb(Predicate<RGB> check, T action) {
            return rgb(check, d->action);
        }
        public BasicColorSwitchRGB<T> rgb(Predicate<RGB> check, Supplier<T> action) {
            return rgb(check, d->action.get());
        }
        public BasicColorSwitchRGB<T> rgb(Predicate<RGB> check, Function<? super RGB, T> theAction) {
            @SuppressWarnings("unchecked")
            Function<BasicColor, T> oldAction = (Function<BasicColor, T>)action;
            Function<BasicColor, T> newAction =
                (action != null)
                ? oldAction : 
                    ((value instanceof RGB) && check.test((RGB)value))
                    ? (Function<BasicColor, T>)(d -> theAction.apply((RGB)d))
                    : oldAction;
            
            return new BasicColorSwitchRGB<T>(value, newAction);
        }
        
        public BasicColorSwitchRGB<T> rgb(int r, Absent g, Absent b, T value) {
            return rgb(rgb -> checkEquals(r, rgb.r), value);
        }
        public BasicColorSwitchRGB<T> rgb(int r, Absent g, Absent b, Supplier<T> supplier) {
            return rgb(rgb -> checkEquals(r, rgb.r), supplier);
        }
        public BasicColorSwitchRGB<T> rgb(int r, Absent g, Absent b, Function<RGB, T> action) {
            return rgb(rgb -> checkEquals(r, rgb.r), action);
        }
        
        public BasicColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, Absent g, Absent b, T value) {
            return rgb(rgb -> rCheck.test(rgb.r), value);
        }
        public BasicColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, Absent g, Absent b, Supplier<T> supplier) {
            return rgb(rgb -> rCheck.test(rgb.r), supplier);
        }
        public BasicColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, Absent g, Absent b, Function<RGB, T> action) {
            return rgb(rgb -> rCheck.test(rgb.r), action);
        }
        
        public BasicColorSwitchRGB<T> rgb(Absent r, int g, Absent b, T value) {
            return rgb(rgb -> checkEquals(g, rgb.g), value);
        }
        public BasicColorSwitchRGB<T> rgb(Absent r, int g, Absent b, Supplier<T> supplier) {
            return rgb(rgb -> checkEquals(g, rgb.g), supplier);
        }
        public BasicColorSwitchRGB<T> rgb(Absent r, int g, Absent b, Function<RGB, T> action) {
            return rgb(rgb -> checkEquals(g, rgb.g), action);
        }
        
        public BasicColorSwitchRGB<T> rgb(int r, int g, Absent b, T value) {
            return rgb(rgb -> checkEquals(r, rgb.r) && checkEquals(g, rgb.g), value);
        }
        public BasicColorSwitchRGB<T> rgb(int r, int g, Absent b, Supplier<T> supplier) {
            return rgb(rgb -> checkEquals(r, rgb.r) && checkEquals(g, rgb.g), supplier);
        }
        public BasicColorSwitchRGB<T> rgb(int r, int g, Absent b, Function<RGB, T> action) {
            return rgb(rgb -> checkEquals(r, rgb.r) && checkEquals(g, rgb.g), action);
        }
        
        public BasicColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, int g, Absent b, T value) {
            return rgb(rgb -> rCheck.test(rgb.r) && checkEquals(g, rgb.g), value);
        }
        public BasicColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, int g, Absent b, Supplier<T> supplier) {
            return rgb(rgb -> rCheck.test(rgb.r) && checkEquals(g, rgb.g), supplier);
        }
        public BasicColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, int g, Absent b, Function<RGB, T> action) {
            return rgb(rgb -> rCheck.test(rgb.r) && checkEquals(g, rgb.g), action);
        }
        
        public BasicColorSwitchRGB<T> rgb(Absent r, Predicate<Integer> gCheck, Absent b, T value) {
            return rgb(rgb -> gCheck.test(rgb.g), value);
        }
        public BasicColorSwitchRGB<T> rgb(Absent r, Predicate<Integer> gCheck, Absent b, Supplier<T> supplier) {
            return rgb(rgb -> gCheck.test(rgb.g), supplier);
        }
        public BasicColorSwitchRGB<T> rgb(Absent r, Predicate<Integer> gCheck, Absent b, Function<RGB, T> action) {
            return rgb(rgb -> gCheck.test(rgb.g), action);
        }
        
        public BasicColorSwitchRGB<T> rgb(int r, Predicate<Integer> gCheck, Absent b, T value) {
            return rgb(rgb -> checkEquals(r, rgb.r) && gCheck.test(rgb.g), value);
        }
        public BasicColorSwitchRGB<T> rgb(int r, Predicate<Integer> gCheck, Absent b, Supplier<T> supplier) {
            return rgb(rgb -> checkEquals(r, rgb.r) && gCheck.test(rgb.g), supplier);
        }
        public BasicColorSwitchRGB<T> rgb(int r, Predicate<Integer> gCheck, Absent b, Function<RGB, T> action) {
            return rgb(rgb -> checkEquals(r, rgb.r) && gCheck.test(rgb.g), action);
        }
        
        public BasicColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, Predicate<Integer> gCheck, Absent b, T value) {
            return rgb(rgb -> rCheck.test(rgb.r) && gCheck.test(rgb.g), value);
        }
        public BasicColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, Predicate<Integer> gCheck, Absent b, Supplier<T> supplier) {
            return rgb(rgb -> rCheck.test(rgb.r) && gCheck.test(rgb.g), supplier);
        }
        public BasicColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, Predicate<Integer> gCheck, Absent b, Function<RGB, T> action) {
            return rgb(rgb -> rCheck.test(rgb.r) && gCheck.test(rgb.g), action);
        }
        
        public BasicColorSwitchRGB<T> rgb(Absent r, Absent g, int b, T value) {
            return rgb(rgb -> checkEquals(b, rgb.b), value);
        }
        public BasicColorSwitchRGB<T> rgb(Absent r, Absent g, int b, Supplier<T> supplier) {
            return rgb(rgb -> checkEquals(b, rgb.b), supplier);
        }
        public BasicColorSwitchRGB<T> rgb(Absent r, Absent g, int b, Function<RGB, T> action) {
            return rgb(rgb -> checkEquals(b, rgb.b), action);
        }
        
        public BasicColorSwitchRGB<T> rgb(int r, Absent g, int b, T value) {
            return rgb(rgb -> checkEquals(r, rgb.r) && checkEquals(b, rgb.b), value);
        }
        public BasicColorSwitchRGB<T> rgb(int r, Absent g, int b, Supplier<T> supplier) {
            return rgb(rgb -> checkEquals(r, rgb.r) && checkEquals(b, rgb.b), supplier);
        }
        public BasicColorSwitchRGB<T> rgb(int r, Absent g, int b, Function<RGB, T> action) {
            return rgb(rgb -> checkEquals(r, rgb.r) && checkEquals(b, rgb.b), action);
        }
        
        public BasicColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, Absent g, int b, T value) {
            return rgb(rgb -> rCheck.test(rgb.r) && checkEquals(b, rgb.b), value);
        }
        public BasicColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, Absent g, int b, Supplier<T> supplier) {
            return rgb(rgb -> rCheck.test(rgb.r) && checkEquals(b, rgb.b), supplier);
        }
        public BasicColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, Absent g, int b, Function<RGB, T> action) {
            return rgb(rgb -> rCheck.test(rgb.r) && checkEquals(b, rgb.b), action);
        }
        
        public BasicColorSwitchRGB<T> rgb(Absent r, int g, int b, T value) {
            return rgb(rgb -> checkEquals(g, rgb.g) && checkEquals(b, rgb.b), value);
        }
        public BasicColorSwitchRGB<T> rgb(Absent r, int g, int b, Supplier<T> supplier) {
            return rgb(rgb -> checkEquals(g, rgb.g) && checkEquals(b, rgb.b), supplier);
        }
        public BasicColorSwitchRGB<T> rgb(Absent r, int g, int b, Function<RGB, T> action) {
            return rgb(rgb -> checkEquals(g, rgb.g) && checkEquals(b, rgb.b), action);
        }
        
        public BasicColorSwitchRGB<T> rgb(int r, int g, int b, T value) {
            return rgb(rgb -> checkEquals(r, rgb.r) && checkEquals(g, rgb.g) && checkEquals(b, rgb.b), value);
        }
        public BasicColorSwitchRGB<T> rgb(int r, int g, int b, Supplier<T> supplier) {
            return rgb(rgb -> checkEquals(r, rgb.r) && checkEquals(g, rgb.g) && checkEquals(b, rgb.b), supplier);
        }
        public BasicColorSwitchRGB<T> rgb(int r, int g, int b, Function<RGB, T> action) {
            return rgb(rgb -> checkEquals(r, rgb.r) && checkEquals(g, rgb.g) && checkEquals(b, rgb.b), action);
        }
        
        public BasicColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, int g, int b, T value) {
            return rgb(rgb -> rCheck.test(rgb.r) && checkEquals(g, rgb.g) && checkEquals(b, rgb.b), value);
        }
        public BasicColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, int g, int b, Supplier<T> supplier) {
            return rgb(rgb -> rCheck.test(rgb.r) && checkEquals(g, rgb.g) && checkEquals(b, rgb.b), supplier);
        }
        public BasicColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, int g, int b, Function<RGB, T> action) {
            return rgb(rgb -> rCheck.test(rgb.r) && checkEquals(g, rgb.g) && checkEquals(b, rgb.b), action);
        }
        
        public BasicColorSwitchRGB<T> rgb(Absent r, Predicate<Integer> gCheck, int b, T value) {
            return rgb(rgb -> gCheck.test(rgb.g) && checkEquals(b, rgb.b), value);
        }
        public BasicColorSwitchRGB<T> rgb(Absent r, Predicate<Integer> gCheck, int b, Supplier<T> supplier) {
            return rgb(rgb -> gCheck.test(rgb.g) && checkEquals(b, rgb.b), supplier);
        }
        public BasicColorSwitchRGB<T> rgb(Absent r, Predicate<Integer> gCheck, int b, Function<RGB, T> action) {
            return rgb(rgb -> gCheck.test(rgb.g) && checkEquals(b, rgb.b), action);
        }
        
        public BasicColorSwitchRGB<T> rgb(int r, Predicate<Integer> gCheck, int b, T value) {
            return rgb(rgb -> checkEquals(r, rgb.r) && gCheck.test(rgb.g) && checkEquals(b, rgb.b), value);
        }
        public BasicColorSwitchRGB<T> rgb(int r, Predicate<Integer> gCheck, int b, Supplier<T> supplier) {
            return rgb(rgb -> checkEquals(r, rgb.r) && gCheck.test(rgb.g) && checkEquals(b, rgb.b), supplier);
        }
        public BasicColorSwitchRGB<T> rgb(int r, Predicate<Integer> gCheck, int b, Function<RGB, T> action) {
            return rgb(rgb -> checkEquals(r, rgb.r) && gCheck.test(rgb.g) && checkEquals(b, rgb.b), action);
        }
        
        public BasicColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, Predicate<Integer> gCheck, int b, T value) {
            return rgb(rgb -> rCheck.test(rgb.r) && gCheck.test(rgb.g) && checkEquals(b, rgb.b), value);
        }
        public BasicColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, Predicate<Integer> gCheck, int b, Supplier<T> supplier) {
            return rgb(rgb -> rCheck.test(rgb.r) && gCheck.test(rgb.g) && checkEquals(b, rgb.b), supplier);
        }
        public BasicColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, Predicate<Integer> gCheck, int b, Function<RGB, T> action) {
            return rgb(rgb -> rCheck.test(rgb.r) && gCheck.test(rgb.g) && checkEquals(b, rgb.b), action);
        }
        
        public BasicColorSwitchRGB<T> rgb(Absent r, Absent g, Predicate<Integer> bCheck, T value) {
            return rgb(rgb -> bCheck.test(rgb.b), value);
        }
        public BasicColorSwitchRGB<T> rgb(Absent r, Absent g, Predicate<Integer> bCheck, Supplier<T> supplier) {
            return rgb(rgb -> bCheck.test(rgb.b), supplier);
        }
        public BasicColorSwitchRGB<T> rgb(Absent r, Absent g, Predicate<Integer> bCheck, Function<RGB, T> action) {
            return rgb(rgb -> bCheck.test(rgb.b), action);
        }
        
        public BasicColorSwitchRGB<T> rgb(int r, Absent g, Predicate<Integer> bCheck, T value) {
            return rgb(rgb -> checkEquals(r, rgb.r) && bCheck.test(rgb.b), value);
        }
        public BasicColorSwitchRGB<T> rgb(int r, Absent g, Predicate<Integer> bCheck, Supplier<T> supplier) {
            return rgb(rgb -> checkEquals(r, rgb.r) && bCheck.test(rgb.b), supplier);
        }
        public BasicColorSwitchRGB<T> rgb(int r, Absent g, Predicate<Integer> bCheck, Function<RGB, T> action) {
            return rgb(rgb -> checkEquals(r, rgb.r) && bCheck.test(rgb.b), action);
        }
        
        public BasicColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, Absent g, Predicate<Integer> bCheck, T value) {
            return rgb(rgb -> rCheck.test(rgb.r) && bCheck.test(rgb.b), value);
        }
        public BasicColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, Absent g, Predicate<Integer> bCheck, Supplier<T> supplier) {
            return rgb(rgb -> rCheck.test(rgb.r) && bCheck.test(rgb.b), supplier);
        }
        public BasicColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, Absent g, Predicate<Integer> bCheck, Function<RGB, T> action) {
            return rgb(rgb -> rCheck.test(rgb.r) && bCheck.test(rgb.b), action);
        }
        
        public BasicColorSwitchRGB<T> rgb(Absent r, int g, Predicate<Integer> bCheck, T value) {
            return rgb(rgb -> checkEquals(g, rgb.g) && bCheck.test(rgb.b), value);
        }
        public BasicColorSwitchRGB<T> rgb(Absent r, int g, Predicate<Integer> bCheck, Supplier<T> supplier) {
            return rgb(rgb -> checkEquals(g, rgb.g) && bCheck.test(rgb.b), supplier);
        }
        public BasicColorSwitchRGB<T> rgb(Absent r, int g, Predicate<Integer> bCheck, Function<RGB, T> action) {
            return rgb(rgb -> checkEquals(g, rgb.g) && bCheck.test(rgb.b), action);
        }
        
        public BasicColorSwitchRGB<T> rgb(int r, int g, Predicate<Integer> bCheck, T value) {
            return rgb(rgb -> checkEquals(r, rgb.r) && checkEquals(g, rgb.g) && bCheck.test(rgb.b), value);
        }
        public BasicColorSwitchRGB<T> rgb(int r, int g, Predicate<Integer> bCheck, Supplier<T> supplier) {
            return rgb(rgb -> checkEquals(r, rgb.r) && checkEquals(g, rgb.g) && bCheck.test(rgb.b), supplier);
        }
        public BasicColorSwitchRGB<T> rgb(int r, int g, Predicate<Integer> bCheck, Function<RGB, T> action) {
            return rgb(rgb -> checkEquals(r, rgb.r) && checkEquals(g, rgb.g) && bCheck.test(rgb.b), action);
        }
        
        public BasicColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, int g, Predicate<Integer> bCheck, T value) {
            return rgb(rgb -> rCheck.test(rgb.r) && checkEquals(g, rgb.g) && bCheck.test(rgb.b), value);
        }
        public BasicColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, int g, Predicate<Integer> bCheck, Supplier<T> supplier) {
            return rgb(rgb -> rCheck.test(rgb.r) && checkEquals(g, rgb.g) && bCheck.test(rgb.b), supplier);
        }
        public BasicColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, int g, Predicate<Integer> bCheck, Function<RGB, T> action) {
            return rgb(rgb -> rCheck.test(rgb.r) && checkEquals(g, rgb.g) && bCheck.test(rgb.b), action);
        }
        
        public BasicColorSwitchRGB<T> rgb(Absent r, Predicate<Integer> gCheck, Predicate<Integer> bCheck, T value) {
            return rgb(rgb -> gCheck.test(rgb.g) && bCheck.test(rgb.b), value);
        }
        public BasicColorSwitchRGB<T> rgb(Absent r, Predicate<Integer> gCheck, Predicate<Integer> bCheck, Supplier<T> supplier) {
            return rgb(rgb -> gCheck.test(rgb.g) && bCheck.test(rgb.b), supplier);
        }
        public BasicColorSwitchRGB<T> rgb(Absent r, Predicate<Integer> gCheck, Predicate<Integer> bCheck, Function<RGB, T> action) {
            return rgb(rgb -> gCheck.test(rgb.g) && bCheck.test(rgb.b), action);
        }
        
        public BasicColorSwitchRGB<T> rgb(int r, Predicate<Integer> gCheck, Predicate<Integer> bCheck, T value) {
            return rgb(rgb -> checkEquals(r, rgb.r) && gCheck.test(rgb.g) && bCheck.test(rgb.b), value);
        }
        public BasicColorSwitchRGB<T> rgb(int r, Predicate<Integer> gCheck, Predicate<Integer> bCheck, Supplier<T> supplier) {
            return rgb(rgb -> checkEquals(r, rgb.r) && gCheck.test(rgb.g) && bCheck.test(rgb.b), supplier);
        }
        public BasicColorSwitchRGB<T> rgb(int r, Predicate<Integer> gCheck, Predicate<Integer> bCheck, Function<RGB, T> action) {
            return rgb(rgb -> checkEquals(r, rgb.r) && gCheck.test(rgb.g) && bCheck.test(rgb.b), action);
        }
        
        public BasicColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, Predicate<Integer> gCheck, Predicate<Integer> bCheck, T value) {
            return rgb(rgb -> rCheck.test(rgb.r) && gCheck.test(rgb.g) && bCheck.test(rgb.b), value);
        }
        public BasicColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, Predicate<Integer> gCheck, Predicate<Integer> bCheck, Supplier<T> supplier) {
            return rgb(rgb -> rCheck.test(rgb.r) && gCheck.test(rgb.g) && bCheck.test(rgb.b), supplier);
        }
        public BasicColorSwitchRGB<T> rgb(Predicate<Integer> rCheck, Predicate<Integer> gCheck, Predicate<Integer> bCheck, Function<RGB, T> action) {
            return rgb(rgb -> rCheck.test(rgb.r) && gCheck.test(rgb.g) && bCheck.test(rgb.b), action);
        }
    }
    
}

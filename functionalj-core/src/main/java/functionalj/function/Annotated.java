// ============================================================================
// Copyright (c) 2017-2019 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
// ----------------------------------------------------------------------------
// MIT License
// 
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
// 
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
// 
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.
// ============================================================================
package functionalj.function;

public abstract class Annotated implements Named, Traced {
    
    private final String toString;
    
    protected Annotated(String type, String name) {
        this(type, name, null);
    }
    protected Annotated(String type, String name, String location) {
        this.toString = type + prefixWhenNotBlank("::", name) + prefixWhenNotBlank("@", location);
    }
    private String prefixWhenNotBlank(String prefix, String location) {
        return ((location != null) && !location.isEmpty()) ? prefix + location : "";
    }
    
    public final String getName() {
        return this.toString.replaceFirst("^.*::", "").replaceFirst("^(.*)(@.*)$", "$1");
    }
    public final String getLocation() {
        return this.toString.replaceFirst("^.*::", "").replaceFirst("^.*@", "");
    }
    
    public final String toString() {
        return toString;
    }
    
    
    /** Named predicate. **/
    static class Predicate<T> extends Annotated implements java.util.function.Predicate<T> {
        private final java.util.function.Predicate<T> check;
        
        /** Constructors. */
        public Predicate(String name, String location, java.util.function.Predicate<T> check) {
            super("Predicate", name, location);
            this.check = check;
        }
        /** Constructors. */
        public Predicate(String name, java.util.function.Predicate<T> check) {
            super("Predicate", name);
            this.check = check;
        }
        
        @Override
        public boolean test(T t) {
            return check.test(t);
        }
    }
    /** Named predicate. **/
    static class BiPredicate<T, U> extends Annotated implements java.util.function.BiPredicate<T, U> {
        private final java.util.function.BiPredicate<T, U> check;
        
        /** Constructors. */
        public BiPredicate(String name, String location, java.util.function.BiPredicate<T, U> check) {
            super("BiPredicate", name, location);
            this.check = check;
        }
        /** Constructors. */
        public BiPredicate(String name, java.util.function.BiPredicate<T, U> check) {
            super("BiPredicate", name);
            this.check = check;
        }
        
        @Override
        public boolean test(T t, U u) {
            return check.test(t, u);
        }
    }
    
    /** Named runnable. **/
    static class Runnable extends Annotated implements java.lang.Runnable {
        private final java.lang.Runnable runnable;
        
        /** Constructors. */
        public Runnable(String name, String location, java.lang.Runnable runnable) {
            super("Runnable", name, location);
            this.runnable = runnable;
        }
        /** Constructors. */
        public Runnable(String name, java.lang.Runnable runnable) {
            super("Runnable", name);
            this.runnable = runnable;
        }
        
        @Override
        public void run() {
            runnable.run();
        }
    }
    
    /** Named supplier */
    static class Supplier<T> extends Annotated implements java.util.function.Supplier<T> {
        private final java.util.function.Supplier<T> supplier;
        
        /** Constructors. */
        public Supplier(String name, String location, java.util.function.Supplier<T> supplier) {
            super("Supplier", name, location);
            this.supplier = supplier;
        }
        /** Constructors. */
        public Supplier(String name, java.util.function.Supplier<T> supplier) {
            super("Supplier", name);
            this.supplier = supplier;
        }
        
        @Override
        public T get() {
            return supplier.get();
        }
        
    }
    
    /** Named consumer. **/
    static class Consumer<T> extends Annotated implements java.util.function.Consumer<T> {
        private final java.util.function.Consumer<T> consumer;
        
        /** Constructors. */
        public Consumer(String name, String location, java.util.function.Consumer<T> consumer) {
            super("Consumer", name, location);
            this.consumer = consumer;
        }
        /** Constructors. */
        public Consumer(String name, java.util.function.Consumer<T> consumer) {
            super("Consumer", name);
            this.consumer = consumer;
        }
        
        @Override
        public void accept(T value) {
            consumer.accept(value);
        }
        
    }
    
    static class Func0<OUTPUT> extends Annotated implements functionalj.function.Func0<OUTPUT> {
        private final functionalj.function.Func0<OUTPUT> func;
        
        /** Constructors. */
        public Func0(String name, String location, functionalj.function.Func0<OUTPUT> func) {
            super("F0", name, location);
            this.func = func;
        }
        public Func0(String name, functionalj.function.Func0<OUTPUT> func) {
            super("F0", name);
            this.func = func;
        }
        
        @Override
        public OUTPUT applyUnsafe() throws Exception {
            return func.applyUnsafe();
        }
    }
    
    static class Func1<INPUT, OUTPUT> extends Annotated implements functionalj.function.Func1<INPUT, OUTPUT> {
        private final functionalj.function.Func1<INPUT, OUTPUT> func;
        
        /** Constructors. */
        public Func1(String name, String location, functionalj.function.Func1<INPUT, OUTPUT> func) {
            super("F1", name, location);
            this.func = func;
        }
        public Func1(String name, functionalj.function.Func1<INPUT, OUTPUT> func) {
            super("F1", name);
            this.func = func;
        }
        
        @Override
        public OUTPUT applyUnsafe(INPUT input) throws Exception {
            return func.applyUnsafe(input);
        }
    }
    
    static class Func2<INPUT1, INPUT2, OUTPUT> extends Annotated 
            implements functionalj.function.Func2<INPUT1, INPUT2, OUTPUT> {
        private final functionalj.function.Func2<INPUT1, INPUT2, OUTPUT> func;
        
        /** Constructors. */
        public Func2(String name, String location, functionalj.function.Func2<INPUT1, INPUT2, OUTPUT> func) {
            super("F2", name, location);
            this.func = func;
        }
        public Func2(String name, functionalj.function.Func2<INPUT1, INPUT2, OUTPUT> func) {
            super("F2", name);
            this.func = func;
        }
        
        @Override
        public OUTPUT applyUnsafe(INPUT1 input1, INPUT2 input2) throws Exception {
            return func.applyUnsafe(input1, input2);
        }
    }
    
    static class Func3<INPUT1, INPUT2, INPUT3, OUTPUT> extends Annotated 
            implements functionalj.function.Func3<INPUT1, INPUT2, INPUT3, OUTPUT> {
        private final functionalj.function.Func3<INPUT1, INPUT2, INPUT3, OUTPUT> func;
        
        /** Constructors. */
        public Func3(String name, String location, functionalj.function.Func3<INPUT1, INPUT2, INPUT3, OUTPUT> func) {
            super("F3", name, location);
            this.func = func;
        }
        public Func3(String name, functionalj.function.Func3<INPUT1, INPUT2, INPUT3, OUTPUT> func) {
            super("F3", name);
            this.func = func;
        }
        
        @Override
        public OUTPUT applyUnsafe(INPUT1 input1, INPUT2 input2, INPUT3 input3) throws Exception {
            return func.applyUnsafe(input1, input2, input3);
        }
    }
    
    static class Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> extends Annotated 
            implements functionalj.function.Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> {
        private final functionalj.function.Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> func;
        
        /** Constructors. */
        public Func4(String name, String location, functionalj.function.Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> func) {
            super("F3", name, location);
            this.func = func;
        }
        public Func4(String name, functionalj.function.Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> func) {
            super("F3", name);
            this.func = func;
        }
        
        @Override
        public OUTPUT applyUnsafe(INPUT1 input1, INPUT2 input2, INPUT3 input3, INPUT4 input4) throws Exception {
            return func.applyUnsafe(input1, input2, input3, input4);
        }
    }
    
    static class Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> extends Annotated 
            implements functionalj.function.Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> {
        private final functionalj.function.Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> func;
        
        /** Constructors. */
        public Func5(String name, String location, functionalj.function.Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> func) {
            super("F5", name, location);
            this.func = func;
        }
        public Func5(String name, functionalj.function.Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> func) {
            super("F5", name);
            this.func = func;
        }
        
        @Override
        public OUTPUT applyUnsafe(INPUT1 input1, INPUT2 input2, INPUT3 input3, INPUT4 input4, INPUT5 input5) throws Exception {
            return func.applyUnsafe(input1, input2, input3, input4, input5);
        }
    }
    
    static class Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> extends Annotated 
            implements functionalj.function.Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> {
        private final functionalj.function.Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func;
        
        /** Constructors. */
        public Func6(String name, String location, functionalj.function.Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func) {
            super("F6", name, location);
            this.func = func;
        }
        public Func6(String name, functionalj.function.Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func) {
            super("F6", name);
            this.func = func;
        }
        
        @Override
        public OUTPUT applyUnsafe(INPUT1 input1, INPUT2 input2, INPUT3 input3, INPUT4 input4, INPUT5 input5, INPUT6 input6) throws Exception {
            return func.applyUnsafe(input1, input2, input3, input4, input5, input6);
        }
    }
    
    static class FuncUnit0 extends Annotated implements functionalj.function.FuncUnit0 {
        private final functionalj.function.FuncUnit0 func;
        
        /** Constructors. */
        public FuncUnit0(String name, String location, functionalj.function.FuncUnit0 func) {
            super("FU0", name, location);
            this.func = func;
        }
        public FuncUnit0(String name, functionalj.function.FuncUnit0 func) {
            super("FU0", name);
            this.func = func;
        }
        
        @Override
        public void runUnsafe() throws Exception {
            func.runUnsafe();
        }
    }
    
    static class FuncUnit1<INPUT> extends Annotated implements functionalj.function.FuncUnit1<INPUT> {
        private final functionalj.function.FuncUnit1<INPUT> func;
        
        /** Constructors. */
        public FuncUnit1(String name, String location, functionalj.function.FuncUnit1<INPUT> func) {
            super("FU1", name, location);
            this.func = func;
        }
        public FuncUnit1(String name, functionalj.function.FuncUnit1<INPUT> func) {
            super("FU1", name);
            this.func = func;
        }
        
        @Override
        public void acceptUnsafe(INPUT input) throws Exception {
            func.acceptUnsafe(input);
        }
    }
    
    static class FuncUnit2<INPUT1, INPUT2> extends Annotated 
            implements functionalj.function.FuncUnit2<INPUT1, INPUT2> {
        private final functionalj.function.FuncUnit2<INPUT1, INPUT2> func;
        
        /** Constructors. */
        public FuncUnit2(String name, String location, functionalj.function.FuncUnit2<INPUT1, INPUT2> func) {
            super("FU2", name, location);
            this.func = func;
        }
        public FuncUnit2(String name, functionalj.function.FuncUnit2<INPUT1, INPUT2> func) {
            super("FU2", name);
            this.func = func;
        }
        
        @Override
        public void acceptUnsafe(INPUT1 input1, INPUT2 input2) throws Exception {
            func.acceptUnsafe(input1, input2);
        }
    }
    
    static class FuncUnit3<INPUT1, INPUT2, INPUT3> extends Annotated 
            implements functionalj.function.FuncUnit3<INPUT1, INPUT2, INPUT3> {
        private final functionalj.function.FuncUnit3<INPUT1, INPUT2, INPUT3> func;
        
        /** Constructors. */
        public FuncUnit3(String name, String location, functionalj.function.FuncUnit3<INPUT1, INPUT2, INPUT3> func) {
            super("FU3", name, location);
            this.func = func;
        }
        public FuncUnit3(String name, functionalj.function.FuncUnit3<INPUT1, INPUT2, INPUT3> func) {
            super("FU3", name);
            this.func = func;
        }
        
        @Override
        public void acceptUnsafe(INPUT1 input1, INPUT2 input2, INPUT3 input3) throws Exception {
            func.acceptUnsafe(input1, input2, input3);
        }
    }
}

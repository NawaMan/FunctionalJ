// ============================================================================
// Copyright (c) 2017-2021 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.ref;

import java.util.List;

import functionalj.function.Func;
import functionalj.function.Func0;
import functionalj.function.Func1;
import functionalj.function.Func2;
import functionalj.function.Func3;
import functionalj.function.Func4;
import functionalj.function.Func5;
import functionalj.function.Func6;
import functionalj.function.FuncUnit0;
import functionalj.function.FuncUnit1;
import functionalj.function.FuncUnit2;
import functionalj.function.FuncUnit3;
import functionalj.list.FuncList;
import functionalj.list.ImmutableFuncList;
import lombok.val;

public class Substitute {
    
    
    public static Substitute Using(Substitution<?> ... substitutions) {
        return Using(ImmutableFuncList.of(substitutions));
    }
    public static Substitute Using(List<Substitution<?>> substitutions) {
        return new Substitute(substitutions);
    }
    
    public static Substitute using(Substitution<?> ... substitutions) {
        return Using(ImmutableFuncList.of(substitutions));
    }
    
    public static Substitute using(List<Substitution<?>> substitutions) {
        return new Substitute(substitutions);
    }
    
    
    private final FuncList<Substitution<?>> substitutions;
    
    Substitute() {
        this.substitutions = FuncList.empty();
    }
    Substitute(List<Substitution<?>> substitutions) {
        this.substitutions = FuncList.from(substitutions);
    }
    
    public FuncList<Substitution<?>> substitutions() {
        return this.substitutions;
    }
    
    public Substitute and(Substitution<?> ... newSubstitutions) {
        return new Substitute(substitutions.appendAll(newSubstitutions));
    }
    public Substitute and(List<Substitution<?>> newSubstitutions) {
        return new Substitute(substitutions.appendAll(newSubstitutions));
    }
    
    //== Around ==
    
    public Runnable arround(Runnable runnable) {
        return () -> {
            val substitutions = substitutions();
            Ref.runWith(substitutions, () -> {
                runnable.run();
            });
        };
    }
    
    public <O> Func0<O> arround(Func0<O> supplier) {
        return Func.f(() -> {
            val substitutions = substitutions();
            return Ref.runWith(substitutions, () -> supplier.applyUnsafe());
        });
    }
    
    public <I, O> Func1<I, O> arround(Func1<I, O> function) {
        return Func.f(input -> {
            val substitutions = substitutions();
            return Ref.runWith(substitutions, () -> function.applyUnsafe(input));
        });
    }
    
    public <I1, I2, O> Func2<I1, I2, O> arround(Func2<I1, I2, O> function) {
        return Func.f((input1, input2) -> {
            val substitutions = substitutions();
            return Ref.runWith(substitutions, () -> function.applyUnsafe(input1, input2));
        });
    }
    
    public <I1, I2, I3, O> Func3<I1, I2, I3, O> arround(Func3<I1, I2, I3, O> function) {
        return Func.f((input1, input2, input3) -> {
            val substitutions = substitutions();
            return Ref.runWith(substitutions, () -> function.applyUnsafe(input1, input2, input3));
        });
    }
    
    public <I1, I2, I3, I4, O> Func4<I1, I2, I3, I4, O> arround(Func4<I1, I2, I3, I4, O> function) {
        return Func.f((input1, input2, input3, input4) -> {
            val substitutions = substitutions();
            return Ref.runWith(substitutions, () -> function.applyUnsafe(input1, input2, input3, input4));
        });
    }
    
    public <I1, I2, I3, I4, I5, O> Func5<I1, I2, I3, I4, I5, O> arround(Func5<I1, I2, I3, I4, I5, O> function) {
        return Func.f((input1, input2, input3, input4, input5) -> {
            val substitutions = substitutions();
            return Ref.runWith(substitutions, () -> function.applyUnsafe(input1, input2, input3, input4, input5));
        });
    }
    
    public <I1, I2, I3, I4, I5, I6, O> Func6<I1, I2, I3, I4, I5, I6, O> arround(Func6<I1, I2, I3, I4, I5, I6, O> function) {
        return Func.f((input1, input2, input3, input4, input5, input6) -> {
            val substitutions = substitutions();
            return Ref.runWith(substitutions, () -> function.applyUnsafe(input1, input2, input3, input4, input5, input6));
        });
    }
    
    public FuncUnit0 arround(FuncUnit0 runnable) {
        return Func.f(() -> {
            val substitutions = substitutions();
            Ref.runWith(substitutions, () -> runnable.runUnsafe());
        });
    }
    
    public <I> FuncUnit1<I> arround(FuncUnit1<I> function) {
        return Func.f(input -> {
            val substitutions = substitutions();
            Ref.runWith(substitutions, () -> function.acceptUnsafe(input));
        });
    }
    
    public <I1, I2> FuncUnit2<I1, I2> arround(FuncUnit2<I1, I2> function) {
        return Func.f((input1, input2) -> {
            val substitutions = substitutions();
            Ref.runWith(substitutions, () -> function.acceptUnsafe(input1, input2));
        });
    }
    
    public <I1, I2, I3> FuncUnit3<I1, I2, I3> arround(FuncUnit3<I1, I2, I3> function) {
        return Func.f((input1, input2, input3) -> {
            val substitutions = substitutions();
            Ref.runWith(substitutions, () -> function.acceptUnsafe(input1, input2, input3));
        });
    }
    
    //== Within ==
    
    public <O> Func0<O> withIn(Func0<O> supplier) {
        return Func.f(() -> {
            val substitutions = substitutions();
            return Ref.runWith(substitutions, () -> supplier.applyUnsafe());
        });
    }
    
    public <I, O> Func1<I, O> withIn(Func1<I, O> function) {
        return Func.f(input -> {
            val substitutions = substitutions();
            return Ref.runWith(substitutions, () -> function.applyUnsafe(input));
        });
    }
    
    public <I1, I2, O> Func2<I1, I2, O> withIn(Func2<I1, I2, O> function) {
        return Func.f((input1, input2) -> {
            val substitutions = substitutions();
            return Ref.runWith(substitutions, () -> function.applyUnsafe(input1, input2));
        });
    }
    
    public <I1, I2, I3, O> Func3<I1, I2, I3, O> withIn(Func3<I1, I2, I3, O> function) {
        return Func.f((input1, input2, input3) -> {
            val substitutions = substitutions();
            return Ref.runWith(substitutions, () -> function.applyUnsafe(input1, input2, input3));
        });
    }
    
    public <I1, I2, I3, I4, O> Func4<I1, I2, I3, I4, O> withIn(Func4<I1, I2, I3, I4, O> function) {
        return Func.f((input1, input2, input3, input4) -> {
            val substitutions = substitutions();
            return Ref.runWith(substitutions, () -> function.applyUnsafe(input1, input2, input3, input4));
        });
    }
    
    public <I1, I2, I3, I4, I5, O> Func5<I1, I2, I3, I4, I5, O> withIn(Func5<I1, I2, I3, I4, I5, O> function) {
        return Func.f((input1, input2, input3, input4, input5) -> {
            val substitutions = substitutions();
            return Ref.runWith(substitutions, () -> function.applyUnsafe(input1, input2, input3, input4, input5));
        });
    }
    
    public <I1, I2, I3, I4, I5, I6, O> Func6<I1, I2, I3, I4, I5, I6, O> withIn(Func6<I1, I2, I3, I4, I5, I6, O> function) {
        return Func.f((input1, input2, input3, input4, input5, input6) -> {
            val substitutions = substitutions();
            return Ref.runWith(substitutions, () -> function.applyUnsafe(input1, input2, input3, input4, input5, input6));
        });
    }
    
    public FuncUnit0 withIn(FuncUnit0 runnable) {
        return Func.f(() -> {
            val substitutions = substitutions();
            Ref.runWith(substitutions, () -> runnable.runUnsafe());
        });
    }
    
    public <I> FuncUnit1<I> withIn(FuncUnit1<I> function) {
        return Func.f(input -> {
            val substitutions = substitutions();
            Ref.runWith(substitutions, () -> function.acceptUnsafe(input));
        });
    }
    
    public <I1, I2> FuncUnit2<I1, I2> withIn(FuncUnit2<I1, I2> function) {
        return Func.f((input1, input2) -> {
            val substitutions = substitutions();
            Ref.runWith(substitutions, () -> function.acceptUnsafe(input1, input2));
        });
    }
    
    public <I1, I2, I3> FuncUnit3<I1, I2, I3> withIn(FuncUnit3<I1, I2, I3> function) {
        return Func.f((input1, input2, input3) -> {
            val substitutions = substitutions();
            Ref.runWith(substitutions, () -> function.acceptUnsafe(input1, input2, input3));
        });
    }
    
}

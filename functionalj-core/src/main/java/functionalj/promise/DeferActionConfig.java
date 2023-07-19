// ============================================================================
// Copyright (c) 2017-2023 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.promise;

import java.util.function.Consumer;
import functionalj.environments.AsyncRunner;
import functionalj.function.Func0;
import functionalj.function.FuncUnit0;
import functionalj.ref.Ref;
import functionalj.supportive.Default;

public class DeferActionConfig {
    
    @Default
    public static DeferActionConfig newInstance() {
        return new DeferActionConfig();
    }
    
    public static final Ref<DeferActionConfig> current = Ref.of(DeferActionConfig.class).orTypeDefaultOrGet(DeferActionConfig::new);
    
    private static final FuncUnit0 DO_NOTHING = () -> {
    };
    
    private boolean interruptOnCancel = true;
    
    private FuncUnit0 onStart = DO_NOTHING;
    
    private AsyncRunner runner = null;
    
    public boolean interruptOnCancel() {
        return interruptOnCancel;
    }
    
    public DeferActionConfig interruptOnCancel(boolean interruptOnCancel) {
        this.interruptOnCancel = interruptOnCancel;
        return this;
    }
    
    public FuncUnit0 onStart() {
        return onStart;
    }
    
    public DeferActionConfig onStart(FuncUnit0 onStart) {
        this.onStart = onStart;
        return this;
    }
    
    public Consumer<Runnable> runner() {
        return runner;
    }
    
    public DeferActionConfig runner(AsyncRunner runner) {
        this.runner = runner;
        return this;
    }
    
    public <D> DeferActionBuilder<D> createBuilder(Func0<D> supplier) {
        return new DeferActionBuilder<D>(supplier).interruptOnCancel(interruptOnCancel).onStart(onStart).runner(runner);
    }
    
    public DeferActionBuilder<Object> createBuilder(FuncUnit0 runnable) {
        return createBuilder(runnable.thenReturnNull());
    }
    
    public <D> DeferAction<D> createAction(Func0<D> supplier) {
        return createBuilder(supplier).build();
    }
    
    public DeferAction<Object> createAction(FuncUnit0 runnable) {
        return createBuilder(runnable.thenReturnNull()).build();
    }
}

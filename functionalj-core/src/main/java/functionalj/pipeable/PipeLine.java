package functionalj.pipeable;

import java.util.ArrayList;
import java.util.List;

import functionalj.functions.Func1;
import functionalj.pipeable.Pipeable.__internal;
import functionalj.types.ImmutableList;
import lombok.val;

public class PipeLine<INPUT, OUTPUT> implements Func1<INPUT, OUTPUT> {
    
    public static final EmptyBuilder starting = new EmptyBuilder();
    
    private final ImmutableList<Func1> functions;
    private final Catch                catchHandler;
    private PipeLine(List<Func1> functions, Catch catchHandler) {
        this.functions    = ImmutableList.of(functions);
        this.catchHandler = catchHandler;
    }
    
    @Override
    public OUTPUT applyUnsafe(INPUT input) throws Exception {
        try {
            Object data = input;
            for (int i = 0; i < functions.size(); i++) {
                Func1 func1 = functions.get(i);
                data = __internal.apply(func1, data);
            }
            if (catchHandler == null)
                return (OUTPUT)data;
            
            return (OUTPUT)catchHandler.doCatch(data, null);
            
        } catch (Exception e) {
            
            if (catchHandler == null)
                return (OUTPUT)null;
            return (OUTPUT)catchHandler.doCatch(null, e);
        }
    }
    
    public static class EmptyBuilder {
        
        public <I, O> Builder<I, O> with(Func1<I, O> func1) {
            val builder = new Builder<I, O>();
            builder.functions.add(func1);
            return builder;
        }
        
    }
    
    public static class Builder<INPUT, OUTPUT> {

        private final List<Func1> functions = new ArrayList<Func1>();
        
        private Builder() {
            
        }
        private Builder(List<Func1> functions, Func1 func1) {
            this.functions.addAll(functions);
            this.functions.add(func1);
        }
        
        public <O> Builder<INPUT, O> then(Func1<OUTPUT, O> func) {
            return new Builder<INPUT, O>(functions, func);
        }
        
        public PipeLine<INPUT, OUTPUT> thenReturn() {
            return new PipeLine<INPUT, OUTPUT>(functions, null);
        }
        
        public <FINALOUTPUT> PipeLine<INPUT, FINALOUTPUT> thenCatch(Catch<OUTPUT, FINALOUTPUT, ? extends Exception> handler) {
            return new PipeLine<INPUT, FINALOUTPUT>(functions, handler);
        }
        
    }
    
}

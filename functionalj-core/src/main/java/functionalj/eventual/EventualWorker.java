package functionalj.eventual;

import functionalj.function.Func2;

public class EventualWorker {
    
    public <I1, I2, D> Eventual<D> combine(Eventual<I1> e1, Eventual<I2> e2, Func2<I1, I2, D> combiner) {
        return null;
    }
    
    public <D> Eventual<D> race(Eventual<D> e1, Eventual<D> e2) {
        return null;
    }
    
}

package functionalj.functions;

@FunctionalInterface
public interface NamedExpression<DATA> {
    
    public DATA apply(Object dummy);
    
    public default DATA getExpression() {
        return apply(null);
    }
    
}

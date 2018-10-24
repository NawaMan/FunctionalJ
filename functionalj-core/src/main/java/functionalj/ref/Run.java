package functionalj.ref;

import java.util.List;

import functionalj.list.FuncList;
import lombok.val;

public class Run {
    
//    public static <V> V with(List<Substitution<?>> substitutions, ComputeBody<V, E> action) throws E {
//    }
//    
    private final FuncList<Substitution<?>> substitutions;
    
    public Run() {
        this.substitutions = FuncList.empty();
    }
    Run(List<Substitution<?>> substitutions) {
        this.substitutions = FuncList.from(substitutions);
    }
    
    @SafeVarargs
    public static Run With(Substitution<?> ... allSubstitutions) {
        val substitutions = new Run().substitutions.append(allSubstitutions);
        return new Run(substitutions);
    }
    @SafeVarargs
    public static Run with(Substitution<?> ... allSubstitutions) {
        val substitutions = new Run().substitutions.append(allSubstitutions);
        return new Run(substitutions);
    }
    public static Run With(List<Substitution<?>> allSubstitutions) {
        val substitutions = new Run().substitutions.appendAll(allSubstitutions);
        return new Run(substitutions);
    }
    public static Run with(List<Substitution<?>> allSubstitutions) {
        val substitutions = new Run().substitutions.appendAll(allSubstitutions);
        return new Run(substitutions);
    }
    
    public Run and(Substitution<?> ... allSubstitutions) {
        val substitutions = this.substitutions.append(allSubstitutions);
        return new Run(substitutions);
    }
    
    public FuncList<Substitution<?>> substitutions() {
        return this.substitutions;
    }
    
    public <E extends Exception> void run(RunBody<E> action) throws E {
        OverridableRef.runWith(substitutions, action);
    }
    public <V, E extends Exception> V run(ComputeBody<V, E> action) throws E {
        return OverridableRef.runWith(substitutions, action);
    }
    
}

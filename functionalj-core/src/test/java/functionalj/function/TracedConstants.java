package functionalj.function;

public class TracedConstants {
    
    public static final java.util.function.Predicate<String> namedPredicate  = Traced.predicate("Predicate1", s->true);
    public static final java.util.function.Predicate<String> nonamePredicate = Traced.predicate(s->true);
    
    public static final Func1<String, Boolean> namedNoTracedFunc1  = Func.f("F1", s->true);
    public static final Func1<String, Boolean> namedTracedFunc1    = Func.F("F2", s->true);
    public static final Func1<String, Boolean> nonameTracedFunc1   = Func.F(      s->true);
}

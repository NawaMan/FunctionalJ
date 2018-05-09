package functionalj.lens;

import java.util.function.Function;

@FunctionalInterface
public interface AccessCreator<HOST, TYPE, TYPELENS extends AnyAccess<HOST, TYPE>>
                    extends Function<Function<HOST, TYPE>, TYPELENS> {
    
}

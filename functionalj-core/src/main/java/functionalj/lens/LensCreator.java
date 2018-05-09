package functionalj.lens;

import java.util.function.Function;

@FunctionalInterface
public interface LensCreator<HOST, TYPE, TYPELENS extends Lens<HOST, TYPE>>
                    extends Function<LensSpec<HOST, TYPE>, TYPELENS> {
    
}

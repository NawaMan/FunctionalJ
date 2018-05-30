package functionalj.types;

import functionalj.kinds.Functor;
import functionalj.kinds.Monad;

public interface Either<LEFT, RIGHT> extends Functor<Either<?, ?>, LEFT>, Monad<Either<?, ?>, LEFT> {
    
    public LEFT getLeft();
    public RIGHT getRight();
    
}

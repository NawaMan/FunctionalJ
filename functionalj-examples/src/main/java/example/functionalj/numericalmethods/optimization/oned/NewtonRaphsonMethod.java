package example.functionalj.numericalmethods.optimization.oned;

import static functionalj.function.Apply.$;
import static functionalj.list.FuncList.iterate;
import static java.lang.Math.abs;

import functionalj.function.Func1;
import functionalj.types.Struct;
import lombok.val;


@Struct
interface IterationSpec {
    double x();
    double fx();
    double dfx();
}

interface DoubleFunc extends Func1<Double, Double> {
    default DiffertiableFunc withDiff(DoubleFunc diff) {
        return new DiffertiableFunc(this, diff);
    }
}

@Struct
interface DiffertiableFuncSpec extends Func1<Double, Iteration> {
    DoubleFunc func();
    DoubleFunc diff();
    
    @Override
    default Iteration applyUnsafe(Double x) throws Exception {
        return new Iteration(x, func().apply(x), diff().apply(x));
    }
}

public class NewtonRaphsonMethod {
    
    public static void main(String[] args) {
        val x0           = 2.0;
        val epsilon      = 1e-10;
        val maxLoop      = 1000;
        val learningRate = 0.05;
        
        val func     = (DoubleFunc) (x -> 1*x*x*x - 1*x*x       - 1);
        val function = func.withDiff(x ->           3*x*x - 2*x    );
        
        Iteration it0 = $(function, x0);
        Func1<Iteration, Double> newtonUpdate   = it -> it.x - it.fx / it.dfx;
        Func1<Iteration, Double> gradientUpdate = it -> it.x - learningRate*it.fx;
        
        val resultNR =
                iterate    (it0, $(function, newtonUpdate))
                .dropAfter (it -> abs(it.fx) < epsilon)
                .limit     (maxLoop)
                .toList()
                ;
        
        val resultGD =
                iterate    (it0, $(function, gradientUpdate))
                .takeUntil (it -> abs(it.fx) < epsilon)
                .limit     (maxLoop)
                .toList()
                ;
        
        System.out.printf("Newton (%3d iterations): %s\n", resultNR.size(), resultNR.lastResult());
        System.out.printf("GD     (%3d iterations): %s\n", resultGD.size(), resultGD.lastResult());
    }
    
}

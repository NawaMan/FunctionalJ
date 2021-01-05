package example.functionalj.numericalmethods.chart;

import static functionalj.stream.Step.StartAt;
import static java.lang.Math.abs;
import static java.lang.Math.exp;
import static java.lang.Math.pow;
import static java.util.Arrays.asList;

import java.util.function.DoubleUnaryOperator;

import lombok.val;

public class PlotChart {
    
    static interface Func extends DoubleUnaryOperator {}
    
    public static void main(String[] args) {
        val start  = -1.0;
        val stop   = 3.0;
        val actual = 18.81838;
        for (val n : asList(1, 2, 4, 8, 16, 200)) {
            val stepSize = (stop - start)/n;
            val function = (Func)((x) -> 3 + 2*pow(x,2) - pow(x,3) + pow(2,x) - exp(-x));
            
            val xs    = StartAt(start).step(stepSize).dropAfter(x -> x >= stop);
            val ys    = xs.map   (function);
            val area  = ys.mapTwo((value1, value2) -> (value1+value2)*stepSize/2).sum();
            val error = abs((actual - area)/actual)*100;
            System.out.printf("n = %d, I_%d = %f, error = %f%%\n", n, n, area, error);
        }
    }
    
}

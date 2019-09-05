package functionalj.functions;

import functionalj.function.Func1;
import functionalj.stream.DoubleStreamElementProcessor;
import functionalj.stream.IntStreamElementProcessor;
import functionalj.stream.LongStreamElementProcessor;
import functionalj.tuple.Tuple2;

public class StatFuncs {
    
    public static <T> Func1<Tuple2<T, Double>, Tuple2<T, Integer>> toIntPercentiles() {
        return tuple -> tuple.map2(Double::intValue);
    }
    
    public static <T> Func1<Tuple2<T, Double>, T> toPercentileElements() {
        return tuple -> tuple._1();
    }
    
    public static <T> Func1<Tuple2<T, Double>, Double> toPercentileValues() {
        return tuple -> tuple._2();
    }
    
    public static <T> Func1<Tuple2<T, Double>, Integer> toPercentileIntValues() {
        return tuple -> tuple._2().intValue();
    }
    
    // == Min ==
    
    public static IntStreamElementProcessor<Integer> minInt() {
        return new IntStreamElementProcessor<Integer>() {
            private Integer minValue = null;
            @Override
            public void processElement(long index, int element) {
                if (minValue == null)
                    minValue = Integer.MAX_VALUE;
                minValue = (minValue < element) ? minValue : element;
            }
            @Override
            public Integer processComplete(long count) {
                return minValue;
            }
        };
    }
    
    public static LongStreamElementProcessor<Long> minLong() {
        return new LongStreamElementProcessor<Long>() {
            private Long minValue = Long.MAX_VALUE;
            @Override
            public void processElement(long index, long element) {
                if (minValue == null)
                    minValue = Long.MAX_VALUE;
                minValue = (minValue < element) ? minValue : element;
            }
            @Override
            public Long processComplete(long count) {
                return minValue;
            }
        };
    }
    
    public static DoubleStreamElementProcessor<Double> minDouble() {
        return new DoubleStreamElementProcessor<Double>() {
            private Double minValue = Double.MAX_VALUE;
            @Override
            public void processElement(long index, double element) {
                if (minValue == null)
                    minValue = Double.MAX_VALUE;
                minValue = (minValue < element) ? minValue : element;
            }
            @Override
            public Double processComplete(long count) {
                return minValue;
            }
        };
    }
    
    // == Max ==
    
    public static IntStreamElementProcessor<Integer> maxInt() {
        return new IntStreamElementProcessor<Integer>() {
            private Integer maxValue = null;
            @Override
            public void processElement(long index, int element) {
                if (maxValue == null)
                    maxValue = Integer.MIN_VALUE;
                maxValue = (maxValue < element) ? maxValue : element;
            }
            @Override
            public Integer processComplete(long count) {
                return maxValue;
            }
        };
    }
    
    public static LongStreamElementProcessor<Long> maxLong() {
        return new LongStreamElementProcessor<Long>() {
            private Long maxValue = Long.MIN_VALUE;
            @Override
            public void processElement(long index, long element) {
                if (maxValue == null)
                    maxValue = Long.MAX_VALUE;
                maxValue = (maxValue < element) ? maxValue : element;
            }
            @Override
            public Long processComplete(long count) {
                return maxValue;
            }
        };
    }
    
    public static DoubleStreamElementProcessor<Double> maxDouble() {
        return new DoubleStreamElementProcessor<Double>() {
            private Double maxValue = -Double.MAX_VALUE;
            @Override
            public void processElement(long index, double element) {
                if (maxValue == null)
                    maxValue = Double.MAX_VALUE;
                maxValue = (maxValue < element) ? maxValue : element;
            }
            @Override
            public Double processComplete(long count) {
                return maxValue;
            }
        };
    }
    
    // TODO - Range, Sum, Mean, Medium, Mode, Variance, StandardDeviation, Quantiles
    
}

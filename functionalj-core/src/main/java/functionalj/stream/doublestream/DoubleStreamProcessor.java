package functionalj.stream.doublestream;

public interface DoubleStreamProcessor<TARGET> {
    
    public TARGET process(DoubleStreamPlus stream);
    
    
    // TODO - uncomment this
//    default StreamProcessor<Double, TARGET> ofDouble() {
//        return of(theDouble);
//    }
//    default <SOURCE> StreamProcessor<SOURCE, TARGET> of(ToDoubleFunction<SOURCE> mapper) {
//        return new StreamProcessor<SOURCE, TARGET>() {
//            @Override
//            public TARGET process(StreamPlus<SOURCE> stream) {
//                val dataStream = stream.mapToDouble(mapper);
//                val target     = DoubleStreamProcessor.this.process(dataStream);
//                return target;
//            }
//        };
//    }
}

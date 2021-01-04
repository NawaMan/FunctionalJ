package functionalj.stream.doublestream;

public interface DoubleStreamProcessor<TARGET> {
    
    public TARGET process(DoubleStreamPlus stream);
    
    
    // TODO - uncomment this
//    default StreamProcessor<? super Integer, TARGET> ofInteger() {
//        return of(i -> i);
//    }
//    default <SOURCE> StreamProcessor<? super SOURCE, TARGET> of(ToIntFunction<? super SOURCE> mapper) {
//        return new StreamProcessor<SOURCE, TARGET>() {
//            @Override
//            public TARGET process(StreamPlus<SOURCE> stream) {
//                val dataStream = stream.mapToInt(mapper);
//                val target     = IntStreamProcessor.this.process(dataStream);
//                return target;
//            }
//        };
//    }
}

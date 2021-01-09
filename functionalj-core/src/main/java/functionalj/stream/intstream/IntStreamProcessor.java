package functionalj.stream.intstream;

public interface IntStreamProcessor<TARGET> extends AsIntStreamProcessor<TARGET> {
    
    public TARGET process(IntStreamPlus stream);
    
    
    public default IntStreamProcessor<TARGET> asIntStreamProcessor() {
        return this;
    }
    
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

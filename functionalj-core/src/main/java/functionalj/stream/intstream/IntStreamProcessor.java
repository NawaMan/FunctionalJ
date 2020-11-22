package functionalj.stream.intstream;

public interface IntStreamProcessor<TARGET> {
    
    public TARGET process(IntStreamPlus stream);
    
    
    // TODO - uncomment this
//    default StreamProcessor<? super Integer, TARGET> ofInteger() {
//        return of(i -> i);
//    }
//    default <SOURCE> StreamProcessor<? super SOURCE, TARGET> of(ToIntFunction<? super SOURCE> mapper) {
//        return new StreamProcessor<SOURCE, TARGET>() {
//            @Override
//            public TARGET process(StreamPlus<SOURCE> stream) {
//                var dataStream = stream.mapToInt(mapper);
//                var target     = IntStreamProcessor.this.process(dataStream);
//                return target;
//            }
//        };
//    }
}

package functionalj.stream;

import functionalj.stream.longstream.LongStreamPlus;

public interface LongStreamProcessor<TARGET> {
    
    public TARGET process(LongStreamPlus stream);
    
    
    // TODO - uncomment this
//    default StreamProcessor<Long, TARGET> ofLong() {
//        return of(theLong);
//    }
//    default <SOURCE> StreamProcessor<SOURCE, TARGET> of(ToLongFunction<SOURCE> mapper) {
//        return new StreamProcessor<SOURCE, TARGET>() {
//            @Override
//            public TARGET process(StreamPlus<SOURCE> stream) {
//                val dataStream = stream.mapToLong(mapper);
//                val target     = LongStreamProcessor.this.process(dataStream);
//                return target;
//            }
//        };
//    }
}

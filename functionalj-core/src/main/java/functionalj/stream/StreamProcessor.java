package functionalj.stream;

public interface StreamProcessor<F, T> {
    void processElement (long index, F element);
    T    processComplete(long count);
}

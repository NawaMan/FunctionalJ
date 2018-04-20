package nawaman.functionalj.annotations.processor.generator;

import java.util.stream.Stream;

public interface IRequireTypes {
    public Stream<Type> getRequiredTypes();
}
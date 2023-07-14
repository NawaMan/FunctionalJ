package functionalj.types.struct.generator.model;

import functionalj.types.struct.generator.IGenerateTerm;

/**
 * Representation of concrecity (abstract, concrete).
 *
 * @author NawaMan -- nawa@nawaman.net
 */
public enum Concrecity implements IGenerateTerm {

    ABSTRACT, DEFAULT, CONCRETE;

    /**
     * @return {@code true}  for DEFAULT and CONCRETE
     */
    public boolean hasImplementation() {
        return this != ABSTRACT;
    }

    @Override
    public String toTerm(String currentPackage) {
        return (this == CONCRETE) ? null : name().toLowerCase();
    }

    @Override
    public String toString() {
        return toTerm(null);
    }
}

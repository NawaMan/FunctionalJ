package functionalj.promise;

import static java.util.Objects.requireNonNull;

/**
 * Exception thrown when a race operation fails to produce any successful result.
 *
 * <p>This exception provides access to the {@link RaceResult} that contains the 
 * details of the race, allowing further inspection of the outcome.</p>
 */
public class RaceFailedException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    private final RaceResult<?> raceResult;
    
    /**
     * Constructs a new {@code RaceFailedException} with the specified {@link RaceResult}.
     *
     * @param raceResult the result of the race operation, containing details of the failure
     * @throws NullPointerException if {@code raceResult} is {@code null}
     */
    public RaceFailedException(RaceResult<?> raceResult) {
        super("Race failed to produce any successful result.");
        this.raceResult = requireNonNull(raceResult, "RaceResult must be null.");
    }
    
    /**
     * Returns the {@link RaceResult} associated with this exception.
     *
     * <p>The {@link RaceResult} provides information about the unsuccessful race attempt,
     * including any intermediate results or errors encountered.</p>
     *
     * @param <DATA> the type of data contained within the {@link RaceResult}
     * @return the {@link RaceResult} associated with this exception
     */
    @SuppressWarnings("unchecked")
    public final <DATA> RaceResult<DATA> raceResult() {
        return (RaceResult<DATA>) raceResult;
    }
    
}

package functionalj.result;

public enum ResultStatus {
    
    NOTREADY,
    CANCELLED,
    PROBLEM,
    NOTEXIST,
    NOMORE,
    INVALID,
    NULL,
    PRESENT;
    
    public static ResultStatus getStatus(Object value, Exception exception) {
        if (value != null)
            return PRESENT;
        if (exception == null)
            return NULL;
        if (exception instanceof ValidationException)
            return INVALID;
        if (exception instanceof NoMoreResultException)
            return NOMORE;
        if (exception instanceof ResultNotExistException)
            return NOTEXIST;
        if (exception instanceof ResultCancelledException)
            return CANCELLED;
        if (exception instanceof ResultNotReadyException)
            return NOTREADY;
        return null;
    }
    
    public static final boolean isPresent  (ResultStatus status) { return  status == PRESENT; }
    public static final boolean isAbsent   (ResultStatus status) { return  status != PRESENT; }
    public static final boolean isNull     (ResultStatus status) { return  status == NULL;    }
    public static final boolean isValue    (ResultStatus status) { return (status == PRESENT) || (status == NULL); }
    public static final boolean isNotValue (ResultStatus status) { return !isValue(status); }
    public static final boolean isInvalid  (ResultStatus status) { return  status == ResultStatus.INVALID; }
    public static final boolean isNotExist (ResultStatus status) { return status == NOTEXIST; }
    public static final boolean isException(ResultStatus status) { return !isValue(status); }
    public static final boolean isCancelled(ResultStatus status) { return status == CANCELLED; }
    public static final boolean isReady    (ResultStatus status) { return status != NOTREADY; }
    public static final boolean isNotReady (ResultStatus status) { return status == NOTREADY; }
    public static final boolean isNoMore   (ResultStatus status) { return status == NOMORE; }
    
}

// ============================================================================
// Copyright (c) 2017-2021 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
// ----------------------------------------------------------------------------
// MIT License
// 
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
// 
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
// 
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.
// ============================================================================
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

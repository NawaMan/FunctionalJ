// ============================================================================
// Copyright (c) 2017-2025 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
    
    public static final boolean checkPresent(ResultStatus status) {
        return status == PRESENT;
    }
    
    public static final boolean checkAbsent(ResultStatus status) {
        return status != PRESENT;
    }
    
    public static final boolean checkNull(ResultStatus status) {
        return status == NULL;
    }
    
    public static final boolean checkValue(ResultStatus status) {
        return (status == PRESENT) || (status == NULL);
    }
    
    public static final boolean checkNotValue(ResultStatus status) {
        return !checkValue(status);
    }
    
    public static final boolean checkInvalid(ResultStatus status) {
        return status == ResultStatus.INVALID;
    }
    
    public static final boolean checkNotExist(ResultStatus status) {
        return status == NOTEXIST;
    }
    
    public static final boolean checkException(ResultStatus status) {
        return !checkValue(status);
    }
    
    public static final boolean checkCancelled(ResultStatus status) {
        return status == CANCELLED;
    }
    
    public static final boolean checkReady(ResultStatus status) {
        return status != NOTREADY;
    }
    
    public static final boolean checkNotReady(ResultStatus status) {
        return status == NOTREADY;
    }
    
    public static final boolean checkNoMore(ResultStatus status) {
        return status == NOMORE;
    }
    
    public final boolean isPresent() {
        return checkPresent(this);
    }
    
    public final boolean isAbsent() {
        return checkAbsent(this);
    }
    
    public final boolean isNull() {
        return checkNull(this);
    }
    
    public final boolean isValue() {
        return checkValue(this);
    }
    
    public final boolean isNotValue() {
        return checkNotValue(this);
    }
    
    public final boolean isInvalid() {
        return checkInvalid(this);
    }
    
    public final boolean isNotExist() {
        return checkNotExist(this);
    }
    
    public final boolean isException() {
        return checkException(this);
    }
    
    public final boolean isCancelled() {
        return checkCancelled(this);
    }
    
    public final boolean isReady() {
        return checkReady(this);
    }
    
    public final boolean isNotReady() {
        return checkNotReady(this);
    }
    
    public final boolean isNoMore() {
        return checkNoMore(this);
    }
    
}

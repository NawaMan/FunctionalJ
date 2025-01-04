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

import lombok.val;

public class ValidationException extends RuntimeException {
    
    private static final long serialVersionUID = 2317758566674598943L;
    
    public static <DATA> ValidationException from(boolean checkResult, String template, DATA data) {
        if (checkResult)
            return null;
        return ValidationException.from(template, data);
    }
    
    public static <DATA> ValidationException from(boolean checkResult, DATA data) {
        if (checkResult)
            return null;
        return ValidationException.from(((data == null) ? "Invalid value: " : data.getClass().getSimpleName() + ": ") + "%s", data);
    }
    
    public static <DATA> ValidationException from(String template, DATA data) {
        if (template == null)
            return null;
        return new ValidationException(String.format(template, data));
    }
    
    public static <DATA> ValidationException from(ValidationException validationException) {
        return validationException;
    }
    
    public static <DATA> void ensure(boolean checkResult, DATA data) {
        val exception = ValidationException.from(checkResult, data);
        if (exception != null)
            throw exception;
    }
    
    public static <DATA> void ensure(boolean checkResult, String template, DATA data) {
        val exception = ValidationException.from(checkResult, template, data);
        if (exception != null)
            throw exception;
    }
    
    public static <DATA> void ensure(String template, DATA data) {
        val exception = ValidationException.from(template, data);
        if (exception != null)
            throw exception;
    }
    
    public static <DATA> void ensure(ValidationException validationException, DATA data) {
        if (validationException != null)
            throw validationException;
    }
    
    public ValidationException(String message) {
        super(message);
    }
    
    public ValidationException(Exception cause) {
        super(cause);
    }
    
    public ValidationException(String message, Exception cause) {
        super(message, cause);
    }
    
    ValidationException() {
    }
    
    @Override
    public String toString() {
        val msg = this.getMessage();
        val cause = getCause();
        val causeMsg = ((msg != null) || (cause == null)) ? "" : ": " + cause.toString();
        return super.toString() + causeMsg;
    }
    
    public static ValidationException of(Exception e) {
        if (e instanceof ValidationException)
            return (ValidationException) e;
        return new ValidationException(e);
    }
}

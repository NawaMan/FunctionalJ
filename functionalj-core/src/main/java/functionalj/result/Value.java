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

import functionalj.list.FuncList;
import functionalj.validator.Validator;
import lombok.val;

public class Value<DATA> extends Result<DATA> {

    public static <D> Value<D> of(D data) {
        return new Value<D>(data);
    }

    private final Object data;

    public Value(DATA data) {
        this(data, (Exception) null);
    }

    Value(DATA data, Exception exception) {
        if (exception != null) {
            this.data = new ExceptionHolder(exception);
        } else {
            this.data = data;
        }
    }

    Value(DATA data, FuncList<Validator<? super DATA>> validators) {
        Object theData = data;
        if (validators != null) {
            for (val validator : validators) {
                try {
                    val result = validator.validate(data);
                    val exception = result.getException();
                    if (exception != null) {
                        if (exception instanceof ValidationException) {
                            theData = new ExceptionHolder(exception);
                        } else {
                            theData = new ExceptionHolder(new ValidationException(exception));
                        }
                        break;
                    }
                } catch (ValidationException exception) {
                    theData = new ExceptionHolder(exception);
                    break;
                } catch (Exception exception) {
                    theData = new ExceptionHolder(new ValidationException(exception));
                    break;
                }
            }
        }
        this.data = theData;
    }

    @Override
    Object __valueData() {
        return data;
    }
}

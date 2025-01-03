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
package functionalj.types.choice.generator.model;

import static functionalj.types.choice.generator.Utils.toListCode;
import static functionalj.types.choice.generator.Utils.toStringLiteral;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import lombok.ToString;

@ToString
public class Case {
    
    public final String name;
    
    public final String validationMethod;
    
    public final List<CaseParam> params;
    
    public Case(String name) {
        this(name, null, emptyList());
    }
    
    public Case(String name, List<CaseParam> params) {
        this(name, null, params);
    }
    
    public Case(String name, String validationMethod, List<CaseParam> params) {
        this.name = name;
        this.validationMethod = validationMethod;
        this.params = params;
    }
    
    public boolean isParameterized() {
        return (params != null) && !params.isEmpty();
    }
    
    public String mapJoinParams(Function<CaseParam, String> mapper, String delimiter) {
        if (params == null)
            return null;
        return params.stream().map(mapper).filter(Objects::nonNull).collect(joining(delimiter));
    }
    
    public List<String> mapParams(Function<CaseParam, String> mapper) {
        if (params == null)
            return null;
        return params.stream().map(mapper).filter(Objects::nonNull).collect(toList());
    }
    
    public String toCode() {
        List<String> parameters = asList(toStringLiteral(name), toStringLiteral(validationMethod), toListCode(params, CaseParam::toCode));
        return "new " + this.getClass().getCanonicalName() + "(" + parameters.stream().collect(joining(", ")) + ")";
    }
}

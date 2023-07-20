// ============================================================================
// Copyright (c) 2017-2023 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.types;

import static functionalj.types.choice.generator.Utils.toListCode;
import static functionalj.types.choice.generator.Utils.toStringLiteral;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.joining;
import java.util.List;

public class Generic {
    
    public final String name;
    
    public final String withBound;
    
    public final List<? extends Type> boundTypes;
    
    public Generic(String name) {
        this(name, name, asList(new Type(name)));
    }
    
    public Generic(Type type) {
        this(type.fullName(), null, asList(type));
    }
    
    public Generic(String name, String withBound, List<? extends Type> boundTypes) {
        this.name = name;
        // (withBound == null) ? name : withBound;
        this.withBound = withBound;
        this.boundTypes = (boundTypes == null) ? emptyList() : boundTypes;
    }
    
    public String getName() {
        return name;
    }
    
    public String withBound() {
        return withBound;
    }
    
    public List<? extends Type> getBoundTypes() {
        return boundTypes;
    }
    
    public Type toType() {
        if ((boundTypes != null) && (boundTypes.size() == 1))
            return boundTypes.get(0);
        return Type.OBJECT;
    }
    
    public String toCode() {
        List<String> params = asList(toStringLiteral(name), toStringLiteral(withBound), toListCode(boundTypes, Type::toCode));
        return "new " + this.getClass().getCanonicalName() + "(" + params.stream().collect(joining(", ")) + ")";
    }
    
    @Override
    public String toString() {
        return "Generic [name=" + name + ", withBound=" + withBound + ", boundTypes=" + boundTypes + "]";
    }
}

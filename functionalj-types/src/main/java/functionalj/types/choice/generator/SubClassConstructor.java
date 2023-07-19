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
package functionalj.types.choice.generator;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import java.util.List;
import functionalj.types.choice.generator.model.Case;

public class SubClassConstructor implements Lines {
    
    public final TargetClass targetClass;
    
    public final Case choice;
    
    public SubClassConstructor(TargetClass targetClass, Case choice) {
        super();
        this.targetClass = targetClass;
        this.choice = choice;
    }
    
    @Override
    public List<String> lines() {
        String sourceName = targetClass.spec.sourceType.simpleName();
        String name       = choice.name;
        String genericDef = targetClass.getType().genericDef().isEmpty() ? "" : targetClass.getType().genericDef() + " ";
        if (!choice.isParameterized()) {
            boolean isGeneric = !targetClass.getType().genericsString().isEmpty();
            String  instance  = isGeneric ? "" : "public static final " + name + " " + Utils.toCamelCase(name) + " = " + name + ".instance;";
            return asList(format(instance), format("public static final %1$s%2$s %3$s() {", genericDef, name + targetClass.getType().genericsString(), name), format("    return %3$s.instance;", targetClass.getType().genericDef(), targetClass.getType().typeWithGenerics(), name), format("}"));
        }
        // TODO - Allow boolean, String and exception as return type.
        String  validateName = choice.validationMethod;
        boolean isV          = (validateName != null);
        String  paramDefs    = choice.mapJoinParams(p -> p.type().typeWithGenerics() + " " + p.name(), ", ");
        String  paramCalls   = choice.mapJoinParams(p -> p.name(), ", ");
        return asList(format("public static final %1$s%2$s %3$s(%4$s) {", genericDef, name + targetClass.getType().genericsString(), name, paramDefs), isV ? format("    %1$s.%2$s(%3$s);", sourceName, validateName, paramCalls) : null, format("    return new %1$s%2$s(%3$s);", name, targetClass.getType().genericsString(), paramCalls), format("}"));
    }
}

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
package functionalj.types.choice.generator;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Stream;

import functionalj.types.Type;
import functionalj.types.choice.generator.model.Case;

public class SubFromMapBuilder implements Lines {
    
    private final TargetClass targetClass;
    
    private final Case choice;
    
    public SubFromMapBuilder(TargetClass targetClass, Case choice) {
        this.targetClass = targetClass;
        this.choice = choice;
    }
    
    private Stream<String> body() {
        AtomicInteger    paramIndex = new AtomicInteger(choice.params.size());
        Supplier<String> comma      = () -> (paramIndex.decrementAndGet() != 0) ? "," : "";
        return Stream.of(Stream.of("    return " + choice.name + "("), choice.params.stream().map(param -> {
            String  indent        = "            ";
            Type    fieldType     = param.type();
            boolean isGenericType = !fieldType.simpleName().contains(".") && (fieldType.packageName() == null) && targetClass.type.generics().stream().filter(generic -> generic.name.equals(fieldType.simpleName())).findAny().isPresent();
            String  fieldTypeName = isGenericType ? targetClass.type.generics().stream().filter(generic -> generic.name.equals(fieldType.simpleName())).flatMap(generic -> generic.boundTypes.stream()).map(type -> type.simpleName()).findFirst().orElse(fieldType.simpleName()) : fieldType.simpleName();
            
            // TODO: It is more complicated ...
            // If the generic is local, we should not use it ... as the method is static
            // If the generic is global (static), we should use it.
            // That said, it seems to be ok, id not used.
//            String  fieldTypeFull = isGenericType ? fieldTypeName : fieldType.simpleNameWithGeneric();
            
            String  extraction    = format("%s(%s)$utils.extractPropertyFromMap(%s.class, %s.class, map, __schema__, \"%s\")%s", 
            							indent, fieldTypeName, choice.name, fieldTypeName, param.name(), comma.get());
            return extraction;
        }), Stream.of("    );")).flatMap(allLines -> allLines);
    }
    
    @Override
    public List<String> lines() {
        return Stream.of(
        		Stream.of("public static " + choice.name + " caseFromMap(java.util.Map<String, ? extends Object> map) {"), 
        		body(), 
        		Stream.of("}")
    		)
    		.flatMap(allLines -> allLines)
    		.collect(toList());
    }
}

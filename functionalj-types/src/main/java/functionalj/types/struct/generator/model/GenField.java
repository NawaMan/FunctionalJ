// ============================================================================
// Copyright (c) 2017-2023 Nawapunth Manusitthipol (NawaMan - http://nawaman.net)
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
package functionalj.types.struct.generator.model;

import static functionalj.types.struct.generator.ILines.oneLineOf;

import java.util.Objects;
import java.util.stream.Stream;

import functionalj.types.Type;
import functionalj.types.struct.generator.IGenerateDefinition;
import functionalj.types.struct.generator.ILines;

/**
 * Representation of a generated field.
 *
 * @author NawaMan -- nawa@nawaman.net
 */
public class GenField implements IGenerateDefinition {
    
    private final Accessibility accessibility;
    
    private final Modifiability modifiability;
    
    private final Scope scope;
    
    private final String name;
    
    private final Type type;
    
    private final String defaultValue;
    
    public GenField(Accessibility accessibility, Modifiability modifiability, Scope scope, String name, Type type,
            String defaultValue) {
        super();
        this.accessibility = accessibility;
        this.modifiability = modifiability;
        this.scope = scope;
        this.name = name;
        this.type = type;
        this.defaultValue = defaultValue;
    }
    
    public Accessibility getAccessibility() {
        return accessibility;
    }
    
    public Modifiability getModifiability() {
        return modifiability;
    }
    
    public Scope getScope() {
        return scope;
    }
    
    public String getName() {
        return name;
    }
    
    public Type getType() {
        return type;
    }
    
    public String getDefaultValue() {
        return defaultValue;
    }
    
    public GenField withAccessibility(Accessibility accessibility) {
        return new GenField(accessibility, modifiability, scope, name, type, defaultValue);
    }
    
    public GenField withModifiability(Modifiability modifiability) {
        return new GenField(accessibility, modifiability, scope, name, type, defaultValue);
    }
    
    public GenField withScope(Scope scope) {
        return new GenField(accessibility, modifiability, scope, name, type, defaultValue);
    }
    
    public GenField withName(String name) {
        return new GenField(accessibility, modifiability, scope, name, type, defaultValue);
    }
    
    public GenField withType(Type type) {
        return new GenField(accessibility, modifiability, scope, name, type, defaultValue);
    }
    
    public GenField withDefaultValue(String defaultValue) {
        return new GenField(accessibility, modifiability, scope, name, type, defaultValue);
    }
    
    @Override
    public Stream<Type> requiredTypes() {
        return getType().requiredTypes();
    }
    
    @Override
    public ILines toDefinition(String currentPackage) {
        String def   = oneLineOf(accessibility, scope, modifiability, type.simpleNameWithGeneric(currentPackage), name);
        String value = (defaultValue != null) ? " = " + defaultValue : "";
        return () -> Stream.of(def + value + ";");
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        GenField other = (GenField) obj;
        return Objects.equals(accessibility, other.accessibility)
            && Objects.equals(defaultValue,  other.defaultValue)
            && Objects.equals(modifiability, other.modifiability)
            && Objects.equals(name,          other.name)
            && Objects.equals(scope,         other.scope)
            && Objects.equals(type,          other.type);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(accessibility, defaultValue, modifiability, name, scope, type);
    }
    
}

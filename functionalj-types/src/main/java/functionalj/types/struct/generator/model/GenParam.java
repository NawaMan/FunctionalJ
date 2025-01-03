// ============================================================================
// Copyright (c) 2017-2025 Nawapunth Manusitthipol (NawaMan - http://nawaman.net)
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

import java.util.stream.Stream;

import functionalj.types.Type;
import functionalj.types.struct.generator.IGenerateTerm;

/**
 * Representation of generated term.
 *
 * @author NawaMan -- nawa@nawaman.net
 */
public class GenParam implements IGenerateTerm {
    
    private final String name;
    
    private final Type type;
    
    public GenParam(String name, Type type) {
        this.name = name;
        this.type = type;
    }
    
    public String getName() {
        return name;
    }
    
    public GenParam withName(String name) {
        return new GenParam(name, type);
    }
    
    public Type getType() {
        return type;
    }
    
    public GenParam withType(Type type) {
        return new GenParam(name, type);
    }
    
    @Override
    public Stream<Type> requiredTypes() {
        return type.requiredTypes();
    }
    
    @Override
    public String toTerm(String currentPackage) {
        return type.simpleNameWithGeneric(currentPackage) + " " + name;
    }
    
    @Override
    public String toString() {
        return "GenParam [name=" + name + ", type=" + type + "]";
    }
    
}

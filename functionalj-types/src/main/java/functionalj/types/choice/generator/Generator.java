// ============================================================================
// Copyright (c) 2017-2024 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import java.util.ArrayList;
import java.util.List;

import functionalj.types.Generic;
import functionalj.types.Serialize;
import functionalj.types.Type;
import functionalj.types.choice.generator.model.Case;
import functionalj.types.choice.generator.model.Method;
import functionalj.types.choice.generator.model.SourceSpec;

// TODO
// - Proper import - when params requires extra type.
public class Generator implements Lines {
    
    public final SourceSpec sourceSpec;
    
    public final TargetClass targetClass;
    
    public Generator(String targetName, Type sourceType, boolean generateSealed, List<Case> choices) {
        this(targetName, sourceType, null, true, null, generateSealed, new ArrayList<Generic>(), choices, new ArrayList<Method>(), new ArrayList<String>());
    }
    
    public Generator(String targetName, Type sourceType, String specObjName, boolean publicFields, String tagMapKeyName, boolean generateSealed, List<Generic> generics, List<Case> choices, List<Method> methods, List<String> localTypeWithLens) {
        this(new SourceSpec(targetName, sourceType, specObjName, publicFields, tagMapKeyName, generateSealed, Serialize.To.NOTHING, generics, choices, methods, localTypeWithLens));
    }
    
    public Generator(SourceSpec sourceSpec) {
        this.sourceSpec = sourceSpec;
        this.targetClass = new TargetClass(sourceSpec);
    }
    
    public SourceSpec getSourceSpec() {
        return sourceSpec;
    }
    
    public TargetClass getTargetClass() {
        return targetClass;
    }
    
    @Override
    public List<String> lines() {
        return targetClass.lines();
    }
}

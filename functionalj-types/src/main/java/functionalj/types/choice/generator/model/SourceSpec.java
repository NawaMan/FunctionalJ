// ============================================================================
// Copyright (c) 2017-2020 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import functionalj.types.Generic;
import functionalj.types.Type;
import lombok.val;

public class SourceSpec {
    
    public static final String TAG_MAP_KEY_NAME = "__tagged";
    
    public final String        targetName;
    public final Type          sourceType;
    public final String        specObjName;
    public final boolean       publicFields;
    public final String        tagMapKeyName;
    public final List<Generic> generics;
    public final List<Case>    choices;
    public final List<Method>  methods;
    public final List<String>  localTypeWithLens;
    
    public SourceSpec(String targetName, Type sourceType, String specObjName, boolean publicFields, String tagMapKeyName,
            List<Generic> generics, List<Case> choices, List<Method> methods, List<String> localTypeWithLens) {
        this.targetName = targetName;
        this.sourceType = sourceType;
        this.specObjName = specObjName;
        this.publicFields = publicFields;
        this.tagMapKeyName = (tagMapKeyName != null) ? tagMapKeyName : TAG_MAP_KEY_NAME;
        this.generics = generics;
        this.choices = choices;
        this.methods = methods;
        this.localTypeWithLens = localTypeWithLens;
    }
    
    public SourceSpec(String targetName, Type sourceType, List<Case> choices) {
        this(targetName, sourceType, null, false, null, new ArrayList<Generic>(), choices, new ArrayList<>(), new ArrayList<>());
    }
    
    public String toCode() {
        val params = asList(
                toStringLiteral(targetName),
                sourceType.toCode(),
                toStringLiteral(specObjName),
                "" + publicFields,
                toStringLiteral(tagMapKeyName),
                toListCode     (generics, Generic::toCode),
                toListCode     (choices,  Case::toCode),
                toListCode     (methods,  Method::toCode),
                toListCode     (localTypeWithLens.stream().map(name -> toStringLiteral(name)).collect(toList()), Function.identity())
        );
        
        return "new " + this.getClass().getCanonicalName() + "("
                + params.stream().collect(joining(", "))
                + ")";
    }
    
}

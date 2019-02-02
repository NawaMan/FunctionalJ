// ============================================================================
// Copyright(c) 2017-2019 Nawapunth Manusitthipol (NawaMan - http://nawaman.net)
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
package functionalj.types.struct.generator;

import static functionalj.types.struct.generator.ILines.line;
import static functionalj.types.struct.generator.ILines.linesOf;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.joining;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import functionalj.types.DefaultValue;
import functionalj.types.struct.generator.model.Accessibility;
import functionalj.types.struct.generator.model.GenClass;
import functionalj.types.struct.generator.model.GenConstructor;
import functionalj.types.struct.generator.model.GenField;
import functionalj.types.struct.generator.model.GenMethod;
import functionalj.types.struct.generator.model.GenParam;
import functionalj.types.struct.generator.model.Modifiability;
import functionalj.types.struct.generator.model.Scope;
import lombok.val;

public class BuilderClassBuilder {
    
    private SourceSpec sourceSpec;
    
    /**
     * Construct a lens class builder.
     * 
     * @param sourceSpec  the source spec.
     */
    public BuilderClassBuilder(SourceSpec sourceSpec) {
        this.sourceSpec = sourceSpec;
    }
    
    private int nextRequireGetter(List<Getter> getters, int index) {
        for (int g = index; g < getters.size(); g++) {
            val getter = getters.get(g);
            if (getter.isRequired()) {
                return g;
            }
        }
        return getters.size();
    }
    
    /**
     * Build the class.
     * 
     * @return  the generated class.
     */
    public GenClass build() {
        val constructors = new ArrayList<GenConstructor>();
        val fields       = new ArrayList<GenField>();
        val methods      = new ArrayList<GenMethod>();
        val subClasses   = new ArrayList<GenClass>();
        val getters      = sourceSpec.getGetters();
        
        if (!getters.isEmpty()) {
            val nextRequiredGetter = nextRequireGetter(getters, 0);
            
            for (int g = 0; g < nextRequiredGetter + 1; g++) {
                if (g >= getters.size())
                    continue;
                
                val nextGetter = getters.get(g);
                val nextBuilderName = "Builder_" + getters.stream().limit(g + 1).map(Getter::getName).collect(joining("_"));
                val nextBuilderType = new Type(sourceSpec.getTargetClassName() + ".Builder", nextBuilderName, sourceSpec.getPackageName(), new String[0]);
                
                
                val line = (g == 0)
                         ? "return new " + "Builder_" + nextGetter.getName() + "(" + nextGetter.getName() + ");"
                         : "return " + getters.stream()
                                              .limit(g)
                                              .map(gt -> gt.getName() + "(" + gt.getType().defaultValue() + ")")
                                              .collect(joining(".")) 
                                     + "." + nextGetter.getName() + "(" + nextGetter.getName() + ");";
                val method = new GenMethod(
                        Accessibility.PUBLIC,
                        Scope.INSTANCE,
                        Modifiability.MODIFIABLE,
                        nextBuilderType,
                        nextGetter.getName(),
                        asList(new GenParam(nextGetter.getName(), nextGetter.getType())),
                        line(line));
                methods.add(method);
            }
        }
        
        for (int g = 0; g < getters.size(); g++) {
            val builderClass = createSubBuilder(sourceSpec, getters, g);
            subClasses.add(builderClass);
        }
        
        return new GenClass(
                Accessibility.PUBLIC,
                Scope.STATIC,
                Modifiability.MODIFIABLE,
                new Type(sourceSpec.getTargetClassName(), "Builder", sourceSpec.getPackageName(), new String[0]),
                null,
                emptyList(),
                emptyList(),
                constructors,
                fields,
                methods,
                subClasses,
                emptyList());
    }
    
    private GenClass createSubBuilder(SourceSpec sourceSpec, List<Getter> getters, int index) {
        val builderName = "Builder_" + getters.stream().limit(index + 1).map(Getter::getName).collect(joining("_"));
        val builderType = new Type(sourceSpec.getTargetClassName() + ".Builder", builderName, sourceSpec.getPackageName(), new String[0]);
        val constructors = new ArrayList<GenConstructor>();
        val fields       = new ArrayList<GenField>();
        val methods      = new ArrayList<GenMethod>();
        
        val thisGetter = getters.get(index);
        val valueField = new GenField(
                Accessibility.PRIVATE,
                Modifiability.FINAL,
                Scope.INSTANCE,
                thisGetter.getName(),
                thisGetter.getType(),
                null);
        
        val parentBuilderName = "Builder_" + getters.stream().limit(index).map(Getter::getName).collect(joining("_"));
        val parentBuilderType = new Type(sourceSpec.getTargetClassName() + ".Builder", parentBuilderName, sourceSpec.getPackageName(), new String[0]);
        
        val parentField = new GenField(
                Accessibility.PRIVATE,
                Modifiability.FINAL,
                Scope.INSTANCE,
                "parent",
                parentBuilderType,
                null);
        
        val valueParam   = new GenParam(thisGetter.getName(), thisGetter.getType());
        val parentParam  = (index == 0) ? null : new GenParam("parent", parentBuilderType);
        val constParams  = (index == 0) ? asList(valueParam) : asList(parentParam, valueParam);
        val assignParent = line("this.parent = parent;");
        val assignValue  = ILines.line(initGetterField(thisGetter));
        val constLines   = (index == 0) ? linesOf(assignValue) : linesOf(assignParent, assignValue);
        val constructor  = new GenConstructor(
                Accessibility.PRIVATE,
                builderName,
                constParams,
                constLines);
        
        if (index > 0) {
            getters.stream().limit(index)
            .map(getter -> {
                return new GenMethod(
                        Accessibility.PUBLIC,
                        Scope.INSTANCE,
                        Modifiability.MODIFIABLE,
                        getter.getType(),
                        getter.getName(),
                        emptyList(),
                        line("return parent." + getter.getName() + "();"));
            }).forEach(methods::add);
        }
        
        val readValue = new GenMethod(
                Accessibility.PUBLIC,
                Scope.INSTANCE,
                Modifiability.MODIFIABLE,
                thisGetter.getType(),
                thisGetter.getName(),
                emptyList(),
                line("return " + thisGetter.getName() + ";"));
        methods.add(readValue);
        
        if (index > 0) {
            getters.stream().limit(index)
            .map(getter -> {
                return new GenMethod(
                        Accessibility.PUBLIC,
                        Scope.INSTANCE,
                        Modifiability.MODIFIABLE,
                        builderType,
                        getter.getName(),
                        asList(new GenParam(getter.getName(), getter.getType())),
                        line("return parent." + getter.getName() + "(" + getter.getName() + ")." + thisGetter.getName() + "(" + thisGetter.getName() + ");"));
            }).forEach(methods::add);
        }
        
        val writeBody   = (index == 0)
                   ? "return new " + builderName + "(" + thisGetter.getName() + ");"
                   : "return parent." + thisGetter.getName() + "(" + thisGetter.getName() + ");";
        val writeValue = new GenMethod(
                Accessibility.PUBLIC,
                Scope.INSTANCE,
                Modifiability.MODIFIABLE,
                builderType,
                thisGetter.getName(),
                asList(new GenParam(thisGetter.getName(), thisGetter.getType())),
                line(writeBody));
        methods.add(writeValue);
        
        if (index < getters.size() - 1) {
            val nextBuilderName = "Builder_" + getters.stream().limit(index + 2).map(Getter::getName).collect(joining("_"));
            val nextBuilderType = new Type(sourceSpec.getTargetClassName() + ".Builder", nextBuilderName, sourceSpec.getPackageName(), new String[0]);
            
            val nextGetter = getters.get(index + 1);
            val writeNextValue = new GenMethod(
                    Accessibility.PUBLIC,
                    Scope.INSTANCE,
                    Modifiability.MODIFIABLE,
                    nextBuilderType,
                    nextGetter.getName(),
                    asList(new GenParam(nextGetter.getName(), nextGetter.getType())),
                    line("return new " + nextBuilderName + "(this, " + nextGetter.getName() + ");"));
            methods.add(writeNextValue);
        }
        
        val nextRequiredGetter = nextRequireGetter(getters, index + 1);
        for (int g = index + 1; g < nextRequiredGetter; g++) {
            if ((g + 1) >= getters.size())
                continue;
            
            val nextGetter = getters.get(g + 1);
            
            val nextBuilderName = "Builder_" + getters.stream().limit(index + 3).map(Getter::getName).collect(joining("_"));
            val nextBuilderType = new Type(sourceSpec.getTargetClassName() + ".Builder", nextBuilderName, sourceSpec.getPackageName(), new String[0]);
            
            val line = (g == 0)
                     ? "return new " + "Builder_" + nextGetter.getName() + "(" + nextGetter.getName() + ");"
                     : "return " + getters.get(g).getName() + "(" + getters.get(g).getType().defaultValue() + ")"
                                 + "." + nextGetter.getName() + "(" + nextGetter.getName() + ");";
            val method = new GenMethod(
                    Accessibility.PUBLIC,
                    Scope.INSTANCE,
                    Modifiability.MODIFIABLE,
                    nextBuilderType,
                    nextGetter.getName(),
                    asList(new GenParam(nextGetter.getName(), nextGetter.getType())),
                    line(line));
            methods.add(method);
        }
        
        if (index == getters.size() - 1) {
            val buildMethod = createBuildMethod(sourceSpec.getTargetClassName(), getters);
            methods.add(buildMethod);
        } else if ((nextRequiredGetter >= getters.size()) && (getters.size() != 0)) {
            val lastName = getters.get(getters.size() - 1).getName();
            val lastType = getters.get(getters.size() - 1).getType().defaultValue();
            val buildMethod = new GenMethod(
                    Accessibility.PUBLIC,
                    Scope.INSTANCE,
                    Modifiability.MODIFIABLE,
                    sourceSpec.getTargetType(),
                    "build",
                    emptyList(),
                    line("return " + lastName + "(" + lastType + ").build();"));
            methods.add(buildMethod);
        }
        
        if (index != 0)
            fields.add(parentField);
        
        constructors.add(constructor);
        fields.add(valueField);
        
        val builderClass = new GenClass(
                Accessibility.PUBLIC,
                Scope.STATIC,
                Modifiability.MODIFIABLE,
                new Type(sourceSpec.getTargetClassName() + ".Builder", builderName , sourceSpec.getPackageName(), new String[0]),
                null,
                emptyList(),
                emptyList(),
                constructors,
                fields,
                methods,
                emptyList(),
                emptyList());
        return builderClass;
    }
    
    private String initGetterField(Getter getter) {
        // TODO - some of these should be pushed to $utils
        val    getterName = getter.getName();
        val    getterType = getter.getType();
        String initValue  = null;
        if (getterType.isList()) {
            initValue = String.format("ImmutableList.from(%1$s)", getterName);
        } else if (getterType.isMap()) {
            initValue = String.format("ImmutableMap.from(%1$s)", getterName);
        } else if (getterType.isFuncList()) {
            initValue = String.format("ImmutableList.from(%1$s)", getterName);
        } else if (getterType.isFuncMap()) {
            initValue = String.format("ImmutableMap.from(%1$s)", getterName);
        } else if (getterType.isNullable()) {
            initValue = String.format("Nullable.of((%1$s == null) ? null : %1$s.get())", getterName);
        } else if (!getter.isNullable() && !getterType.isPrimitive()){
            initValue = String.format("$utils.notNull(%1$s)", getterName);
        } else {
            initValue = getterName;
        }
        
        if (!getter.isRequired()) {
            val defaultValue = DefaultValue.defaultValueCode(getterType, getter.getDefaultTo());
            initValue = String.format("java.util.Optional.ofNullable(%1$s).orElseGet(()->%2$s)", getterName, defaultValue);
        }
        
        return String.format("this.%1$s = %2$s;", getterName, initValue);
    }
    
    private GenMethod createBuildMethod(String targetName, List<Getter> getters) {
        return new GenMethod(
                Accessibility.PUBLIC,
                Scope.INSTANCE,
                Modifiability.MODIFIABLE,
                sourceSpec.getTargetType(),
                "build",
                emptyList(),
                line("return new " + targetName + "(" + 
                        getters.stream().map(Getter::getName).map(g ->g + "()").collect(Collectors.joining(", "))
                        + ");"));
    }

}

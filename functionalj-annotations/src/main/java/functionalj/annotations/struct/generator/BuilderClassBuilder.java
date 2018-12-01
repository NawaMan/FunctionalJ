package functionalj.annotations.struct.generator;

import static functionalj.annotations.struct.generator.ILines.line;
import static functionalj.annotations.struct.generator.ILines.linesOf;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.joining;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import functionalj.annotations.struct.generator.model.Accessibility;
import functionalj.annotations.struct.generator.model.GenClass;
import functionalj.annotations.struct.generator.model.GenConstructor;
import functionalj.annotations.struct.generator.model.GenField;
import functionalj.annotations.struct.generator.model.GenMethod;
import functionalj.annotations.struct.generator.model.GenParam;
import functionalj.annotations.struct.generator.model.Modifiability;
import functionalj.annotations.struct.generator.model.Scope;
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
        val notNull      = !thisGetter.isNullable() && !thisGetter.getType().isPrimitive();
        val assignValue  = notNull
                         ? line("this." + thisGetter.getName() + " = $utils.notNull(" + thisGetter.getName() + ");")
                         : line("this." + thisGetter.getName() + " = " +                thisGetter.getName() +  ";");
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

// ============================================================================
// Copyright (c) 2017-2024 Nawapunth Manusitthipol (NawaMan - http://nawaman.net)
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

import static functionalj.types.choice.generator.Utils.toListCode;
import static functionalj.types.choice.generator.Utils.toStringLiteral;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.function.Function;

import functionalj.types.JavaVersionInfo;
import functionalj.types.Serialize;
import functionalj.types.Serialize.To;
import functionalj.types.Type;
import functionalj.types.choice.generator.Utils;

/**
 * Source specification of the data object.
 *
 * @author NawaMan -- nawa@nawaman.net
 */
public class SourceSpec {
    
    private final JavaVersionInfo javaVersionInfo;
    
    private final String specName;
    
    private final String packageName;
    
    private final String encloseName;
    
    private final String targetClassName;
    
    private final String targetPackageName;
    
    private final Boolean isClass;
    
    private final Boolean isInterface;
    
    private final String specObjName;
    
    private final String validatorName;
    
    private final Configurations configurations;
    
    private final List<Getter> getters;
    
    private final List<Callable> methods;
    
    private final List<String> typeWithLens;
    
    public SourceSpec(
            JavaVersionInfo javaVersionInfo,
            String specName,
            String packageName,
            String encloseName,
            String targetClassName,
            String targetPackageName,
            Boolean isClass,
            Boolean isInterface,
            String specObjName,
            String validatorName,
            Configurations configurations,
            List<Getter> getters,
            List<Callable> methods,
            List<String> typeWithLens) {
        this.javaVersionInfo = javaVersionInfo;
        this.specName = specName;
        this.packageName = packageName;
        this.encloseName = encloseName;
        this.targetClassName = targetClassName;
        this.targetPackageName = targetPackageName;
        this.isClass = isClass;
        this.isInterface = isInterface;
        this.specObjName = specObjName;
        this.validatorName = validatorName;
        this.configurations = configurations;
        this.getters = getters;
        this.methods = methods;
        this.typeWithLens = typeWithLens;
    }
    
    public String getSpecName() {
        return specName;
    }
    
    public String getPackageName() {
        return packageName;
    }
    
    public String getEncloseName() {
        return encloseName;
    }
    
    public String getTargetClassName() {
        return targetClassName;
    }
    
    public String getTargetPackageName() {
        return targetPackageName;
    }
    
    public Boolean getIsClass() {
        return isClass;
    }
    
    public Boolean getIsInterface() {
        return isInterface;
    }
    
    public String getSpecObjName() {
        return specObjName;
    }
    
    public String getValidatorName() {
        return validatorName;
    }
    
    public Configurations getConfigures() {
        return configurations;
    }
    
    public List<Getter> getGetters() {
        return getters;
    }
    
    public List<Callable> getMethods() {
        return methods;
    }
    
    public List<String> getTypeWithLens() {
        return typeWithLens;
    }
    
    public SourceSpec withSpecName(String specName) {
        return new SourceSpec(javaVersionInfo, specName, packageName, encloseName, targetClassName, targetPackageName, isClass, isInterface, specObjName, validatorName, configurations, getters, methods, typeWithLens);
    }
    
    public SourceSpec withPackageName(String packageName) {
        return new SourceSpec(javaVersionInfo, specName, packageName, encloseName, targetClassName, targetPackageName, isClass, isInterface, specObjName, validatorName, configurations, getters, methods, typeWithLens);
    }
    
    public SourceSpec withEncloseName(String encloseName) {
        return new SourceSpec(javaVersionInfo, specName, packageName, encloseName, targetClassName, targetPackageName, isClass, isInterface, specObjName, validatorName, configurations, getters, methods, typeWithLens);
    }
    
    public SourceSpec withTargetClassName(String targetClassName) {
        return new SourceSpec(javaVersionInfo, specName, packageName, encloseName, targetClassName, targetPackageName, isClass, isInterface, specObjName, validatorName, configurations, getters, methods, typeWithLens);
    }
    
    public SourceSpec withTargetPackageName(String targetPackageName) {
        return new SourceSpec(javaVersionInfo, specName, packageName, encloseName, targetClassName, targetPackageName, isClass, isInterface, specObjName, validatorName, configurations, getters, methods, typeWithLens);
    }
    
    public SourceSpec withIsClass(Boolean isClass) {
        return new SourceSpec(javaVersionInfo, specName, packageName, encloseName, targetClassName, targetPackageName, isClass, isInterface, specObjName, validatorName, configurations, getters, methods, typeWithLens);
    }
    
    public SourceSpec withSpecObjName(String specObjName) {
        return new SourceSpec(javaVersionInfo, specName, packageName, encloseName, targetClassName, targetPackageName, isClass, isInterface, specObjName, validatorName, configurations, getters, methods, typeWithLens);
    }
    
    public SourceSpec withValidatorName(String validatorName) {
        return new SourceSpec(javaVersionInfo, specName, packageName, encloseName, targetClassName, targetPackageName, isClass, isInterface, specObjName, validatorName, configurations, getters, methods, typeWithLens);
    }
    
    public SourceSpec withConfigures(Configurations configures) {
        return new SourceSpec(javaVersionInfo, specName, packageName, encloseName, targetClassName, targetPackageName, isClass, isInterface, specObjName, validatorName, configures, getters, methods, typeWithLens);
    }
    
    public SourceSpec withGetters(List<Getter> getters) {
        return new SourceSpec(javaVersionInfo, specName, packageName, encloseName, targetClassName, targetPackageName, isClass, isInterface, specObjName, validatorName, configurations, getters, methods, typeWithLens);
    }
    
    public SourceSpec withMethods(List<Callable> methods) {
        return new SourceSpec(javaVersionInfo, specName, packageName, encloseName, targetClassName, targetPackageName, isClass, isInterface, specObjName, validatorName, configurations, getters, methods, typeWithLens);
    }
    
    public SourceSpec withTypeWithLens(List<String> typeWithLens) {
        return new SourceSpec(javaVersionInfo, specName, packageName, encloseName, targetClassName, targetPackageName, isClass, isInterface, specObjName, validatorName, configurations, getters, methods, typeWithLens);
    }
    
    /**
     * Configurations
     */
    public static class Configurations {
        
        /**
         * Should extends/implements with the definition class/interface
         */
        public boolean coupleWithDefinition = true;
        
        /**
         * Should the no-arguments constructor be created.
         */
        public boolean generateNoArgConstructor = false;
        
        /**
         * Should the required-field-only constructor be created.
         */
        public boolean generateRequiredOnlyConstructor = true;
        
        /**
         * Should the all-arguments constructor be created.
         */
        public boolean generateAllArgConstructor = true;
        
        /**
         * Should the lens class be generated.
         */
        public boolean generateLensClass = true;
        
        /**
         * Should the builder class be generated.
         */
        public boolean generateBuilderClass = true;
        
        /**
         * Should the fields be made public
         */
        public boolean publicFields = true;
        
        /**
         * Should the constructor be made public (other wise it will be package)
         */
        public boolean publicConstructor = true;
        
        /**
         * Should generate record instead of a regular class.
         */
        public Boolean generateRecord = null;
        
        /**
         * Template for toString. null for no toString generated, "" for auto-generate toString, or template
         */
        public String toStringTemplate = "";
        
        /** the target serialization type. */
        public Serialize.To serialize = Serialize.To.NOTHING;
        
        /**
         * The toString() returns the Java record format. For example, Point[x=3. y=4].
         *             NOTE: Only when toStringTemplate is "".
         **/
        public boolean recordToString = false;
        
        public Configurations() {
        }
        
        public Configurations(
                boolean coupleWithDefinition, 
                boolean generateNoArgConstructor, 
                boolean generateRequiredOnlyConstructor, 
                boolean generateAllArgConstructor, 
                boolean generateLensClass, 
                boolean generateBuilderClass, 
                boolean publicFields, 
                boolean publicConstructor, 
                String  toStringTemplate, 
                Serialize.To serialize,
                boolean      recordToString) {
            this.coupleWithDefinition = coupleWithDefinition;
            this.generateNoArgConstructor        = generateNoArgConstructor;
            this.generateRequiredOnlyConstructor = generateRequiredOnlyConstructor;
            this.generateAllArgConstructor       = generateAllArgConstructor;
            this.generateLensClass = generateLensClass;
            this.generateBuilderClass = generateBuilderClass;
            this.publicFields = publicFields;
            this.publicConstructor = publicConstructor;
            this.toStringTemplate = toStringTemplate;
            this.serialize = (serialize != null) ? serialize : To.NOTHING;
            this.recordToString = recordToString;
        }
        
        @Override
        public String toString() {
            return "Configurations ["
                    + "coupleWithDefinition=" + coupleWithDefinition + ", "
                    + "generateNoArgConstructor=" + generateNoArgConstructor + ", "
                    + "generateRequiredOnlyConstructor=" + generateRequiredOnlyConstructor + ", "
                    + "generateAllArgConstructor=" + generateAllArgConstructor + ", "
                    + "generateLensClass=" + generateLensClass + ", "
                    + "generateBuilderClass=" + generateBuilderClass + ", "
                    + "publicFields=" + publicFields + ", "
                    + "publicConstructor=" + publicConstructor + ", "
                    + "toStringTemplate=" + toStringTemplate + ", "
                    + "serialize=" + serialize + ", "
                    + "recordToString=" + recordToString
                    + "]";
        }
        
        public String toCode() {
            String       serializeCode = Serialize.class.getCanonicalName() + ".To." + serialize;
            List<Object> params        = asList(coupleWithDefinition, generateNoArgConstructor, generateRequiredOnlyConstructor, generateAllArgConstructor, generateLensClass, generateBuilderClass, publicFields, publicConstructor, Utils.toStringLiteral(toStringTemplate), serializeCode, recordToString);
            return "new " + Configurations.class.getCanonicalName() + "(" + params.stream().map(String::valueOf).collect(joining(", ")) + ")";
        }
    }
    
    /**
     * @return the target type.
     */
    public Type getTargetType() {
        return new Type(targetPackageName, targetClassName);
    }
    
    /**
     * @return the type of this source.
     */
    public Type toType() {
        return new Type(packageName, specName);
    }
    
    public Boolean isClass() {
        return isClass;
    }
    
    public Boolean isInterface() {
        return isInterface;
    }
    
    public boolean hasSpecField() {
        return (getSpecObjName() == null) || getSpecObjName().isEmpty();
    }
    
    public String toCode() {
        List<Object> params = asList(
                javaVersionInfo.toCode(),
                toStringLiteral(specName),
                toStringLiteral(packageName),
                toStringLiteral(encloseName),
                toStringLiteral(targetClassName),
                toStringLiteral(targetPackageName),
                isClass,
                isInterface,
                toStringLiteral(specObjName),
                toStringLiteral(validatorName),
                configurations.toCode(),
                toListCode(getters, Getter::toCode),
                toListCode(methods, Callable::toCode),
                toListCode(typeWithLens.stream().map(name -> toStringLiteral(name)).collect(toList()), Function.identity()));
        return "new " + SourceSpec.class.getCanonicalName() + "(" + params.stream().map(String::valueOf).collect(joining(", ")) + ")";
    }
    
}

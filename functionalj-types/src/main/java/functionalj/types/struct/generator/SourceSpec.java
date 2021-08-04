// ============================================================================
// Copyright (c) 2017-2021 Nawapunth Manusitthipol (NawaMan - http://nawaman.net)
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

import functionalj.types.Serialize;
import functionalj.types.Type;
import functionalj.types.Serialize.To;
import functionalj.types.choice.generator.Utils;
import lombok.Value;
import lombok.With;
import lombok.val;

/**
 * Source specification of the data object.
 *
 * @author NawaMan -- nawa@nawaman.net
 */
@Value
@With
public class SourceSpec {
    
    private String         specName;
    private String         packageName;
    private String         encloseName;
    private String         targetClassName;
    private String         targetPackageName;
    private Boolean        isClass;
    private String         specObjName;
    private String         validatorName;
    private Configurations configures;
    private List<Getter>   getters;
    private List<String>   typeWithLens;
    
    /** Configurations */
    public static class Configurations {
        
        /** Should extends/implements with the definition class/interface */
        public boolean coupleWithDefinition = true;
        /** Should the no-arguments constructor be created. */
        public boolean generateNoArgConstructor  = false;
        /** Should the required-field-only constructor be created. */
        public boolean generateRequiredOnlyConstructor = true;
        /** Should the all-arguments constructor be created. */
        public boolean generateAllArgConstructor  = true;
        /** Should the lens class be generated. */
        public boolean generateLensClass = true;
        /** Should the builder class be generated. */
        public boolean generateBuilderClass = true;
        /** Should the fields be made public */
        public boolean publicFields = true;
        /** Should the constructor be made public (other wise it will be package) */
        public boolean publicConstructor = true;
        /** Template for toString. null for no toString generated, "" for auto-generate toString, or template */
        public String toStringTemplate = "";
        /** @return the target serialization type. */
        public Serialize.To serialize = Serialize.To.NOTHING;
        
        public Configurations() {}
        public Configurations(
                boolean coupleWithDefinition,
                boolean generateNoArgConstructor,
                boolean generateRequiredOnlyConstructor,
                boolean generateAllArgConstructor,
                boolean generateLensClass,
                boolean generateBuilderClass,
                boolean publicFields,
                boolean publicConstructor,
                String toStringTemplate,
                Serialize.To serialize) {
            this.coupleWithDefinition            = coupleWithDefinition;
            this.generateNoArgConstructor        = generateNoArgConstructor;
            this.generateRequiredOnlyConstructor = generateRequiredOnlyConstructor;
            this.generateAllArgConstructor       = generateAllArgConstructor;
            this.generateLensClass               = generateLensClass;
            this.generateBuilderClass            = generateBuilderClass;
            this.publicFields                    = publicFields;
            this.publicConstructor               = publicConstructor;
            this.toStringTemplate                = toStringTemplate;
            this.serialize                       = (serialize != null) ? serialize : To.NOTHING;
        }
        
        @Override
        public String toString() {
            return "Configurations ["
                    + "coupleWithDefinition="            + coupleWithDefinition + ", "
                    + "generateNoArgConstructor="        + generateNoArgConstructor + ", "
                    + "generateRequiredOnlyConstructor=" + generateRequiredOnlyConstructor + ", "
                    + "generateAllArgConstructor="       + generateAllArgConstructor + ", "
                    + "generateLensClass="               + generateLensClass + ", "
                    + "generateBuilderClass="            + generateBuilderClass + ","
                    + "publicFields="                    + publicFields
                    + "publicConstructor="               + publicConstructor
                    + "toStringTemplate="                + toStringTemplate
                    + "serialize="                       + serialize
                    + "]";
        }
        public String toCode() {
            val serializeCode = Serialize.class.getCanonicalName() + ".To." + serialize;
            List<Object> params = asList(
                    coupleWithDefinition,
                    generateNoArgConstructor,
                    generateRequiredOnlyConstructor,
                    generateAllArgConstructor,
                    generateLensClass,
                    generateBuilderClass,
                    publicFields,
                    publicConstructor,
                    Utils.toStringLiteral(toStringTemplate),
                    serializeCode
            );
            return "new " + Configurations.class.getCanonicalName() + "("
                    + params.stream().map(String::valueOf).collect(joining(", "))
                    + ")";
        }
    }
    
    /** @return the target type. */
    public Type getTargetType() {
        return new Type(targetPackageName, targetClassName);
    }
    /** @return the type of this source. */
    public Type toType() {
        return new Type(packageName, specName);
    }
    public Boolean isClass() {
        return isClass;
    }
    
    public boolean hasSpecField() {
        return (getSpecObjName() == null) || getSpecObjName().isEmpty();
    }
    
    public String toCode() {
        List<Object> params = asList(
                toStringLiteral(specName),
                toStringLiteral(packageName),
                toStringLiteral(encloseName),
                toStringLiteral(targetClassName),
                toStringLiteral(targetPackageName),
                isClass,
                toStringLiteral(specObjName),
                toStringLiteral(validatorName),
                configures.toCode(),
                toListCode(getters, Getter::toCode),
                toListCode(typeWithLens.stream().map(name -> toStringLiteral(name)).collect(toList()), Function.identity())
        );
        return "new " + SourceSpec.class.getCanonicalName() + "("
                + params.stream().map(String::valueOf).collect(joining(", "))
                + ")";
    }
    
}

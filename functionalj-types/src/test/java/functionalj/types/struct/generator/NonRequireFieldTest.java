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
package functionalj.types.struct.generator;

import static functionalj.types.TestHelper.assertAsString;
import static functionalj.types.struct.SourceKind.INTERFACE;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

import java.util.List;

import org.junit.Test;

import functionalj.types.DefaultValue;
import functionalj.types.JavaVersionInfo;
import functionalj.types.Type;
import functionalj.types.struct.SourceKind;
import functionalj.types.struct.generator.SourceSpec.Configurations;
import functionalj.types.struct.generator.model.GenStruct;
import lombok.val;

public class NonRequireFieldTest {
    
    private Configurations configuration = new Configurations();
    
    {
        configuration.coupleWithDefinition = true;
        configuration.generateNoArgConstructor = true;
        configuration.generateAllArgConstructor = true;
        configuration.generateLensClass = true;
    }
    
    private String definitionClassName = "Definitions.DataDef";
    
    private String targetClassName = "Data";
    
    private String packageName = "me.test";
    
    private SourceKind sourceKind = INTERFACE;
    
    private List<Getter> getters = asList(new Getter("a", Type.INT), new Getter("b", Type.BOOL), new Getter("c", Type.STRING, true, DefaultValue.NULL), new Getter("d", Type.STRING), new Getter("e", Type.STRING, true, DefaultValue.NULL));
    
    @Test
    public void testBuilder() {
        val generated = generate();
        assertAsString("package me.test;\n"
              + "\n"
              + "import functionalj.lens.core.LensSpec;\n"
              + "import functionalj.lens.lenses.BooleanLens;\n"
              + "import functionalj.lens.lenses.IntegerLens;\n"
              + "import functionalj.lens.lenses.ObjectLensImpl;\n"
              + "import functionalj.lens.lenses.StringLens;\n"
              + "import functionalj.pipeable.Pipeable;\n"
              + "import functionalj.types.Generated;\n"
              + "import functionalj.types.IPostConstruct;\n"
              + "import functionalj.types.IStruct;\n"
              + "import functionalj.types.struct.generator.Getter;\n"
              + "import java.lang.Exception;\n"
              + "import java.lang.Object;\n"
              + "import java.util.HashMap;\n"
              + "import java.util.Map;\n"
              + "import java.util.function.BiFunction;\n"
              + "import java.util.function.Function;\n"
              + "import java.util.function.Supplier;\n"
              + "\n"
              + "@Generated(value = \"FunctionalJ\", date = \"\\E[^\"]+\\Q\", comments = \"me.test.null.Definitions.DataDef\")\n"
              + "@SuppressWarnings(\"all\")\n"
              + "\n"
              + "public class Data implements Definitions.DataDef,IStruct,Pipeable<Data> {\n"
              + "    \n"
              + "    public static final Data.DataLens<Data> theData = new Data.DataLens<>(\"theData\", LensSpec.of(Data.class));\n"
              + "    public static final Data.DataLens<Data> eachData = theData;\n"
              + "    private final int a;\n"
              + "    private final boolean b;\n"
              + "    private final String c;\n"
              + "    private final String d;\n"
              + "    private final String e;\n"
              + "    \n"
              + "    public Data() {\n"
              + "        this(0, false, null, null, null);\n"
              + "    }\n"
              + "    public Data(int a, boolean b, String d) {\n"
              + "        this($utils.notNull(a), $utils.notNull(b), null, $utils.notNull(d), null);\n"
              + "        if (IPostConstruct.class.isInstance(this)) IPostConstruct.class.cast(this).postConstruct();\n"
              + "    }\n"
              + "    public Data(int a, boolean b, String c, String d, String e) {\n"
              + "        this.a = a;\n"
              + "        this.b = b;\n"
              + "        this.c = java.util.Optional.ofNullable(c).orElseGet(()->null);\n"
              + "        this.d = $utils.notNull(d);\n"
              + "        this.e = java.util.Optional.ofNullable(e).orElseGet(()->null);\n"
              + "        if (IPostConstruct.class.isInstance(this)) IPostConstruct.class.cast(this).postConstruct();\n"
              + "    }\n"
              + "    \n"
              + "    public Data __data() throws Exception  {\n"
              + "        return this;\n"
              + "    }\n"
              + "    public int a() {\n"
              + "        return a;\n"
              + "    }\n"
              + "    public boolean b() {\n"
              + "        return b;\n"
              + "    }\n"
              + "    public String c() {\n"
              + "        return c;\n"
              + "    }\n"
              + "    public String d() {\n"
              + "        return d;\n"
              + "    }\n"
              + "    public String e() {\n"
              + "        return e;\n"
              + "    }\n"
              + "    public Data withA(int a) {\n"
              + "        return new Data(a, b, c, d, e);\n"
              + "    }\n"
              + "    public Data withA(Supplier<Integer> a) {\n"
              + "        return new Data(a.get(), b, c, d, e);\n"
              + "    }\n"
              + "    public Data withA(Function<Integer, Integer> a) {\n"
              + "        return new Data(a.apply(this.a), b, c, d, e);\n"
              + "    }\n"
              + "    public Data withA(BiFunction<Data, Integer, Integer> a) {\n"
              + "        return new Data(a.apply(this, this.a), b, c, d, e);\n"
              + "    }\n"
              + "    public Data withB(boolean b) {\n"
              + "        return new Data(a, b, c, d, e);\n"
              + "    }\n"
              + "    public Data withB(Supplier<Boolean> b) {\n"
              + "        return new Data(a, b.get(), c, d, e);\n"
              + "    }\n"
              + "    public Data withB(Function<Boolean, Boolean> b) {\n"
              + "        return new Data(a, b.apply(this.b), c, d, e);\n"
              + "    }\n"
              + "    public Data withB(BiFunction<Data, Boolean, Boolean> b) {\n"
              + "        return new Data(a, b.apply(this, this.b), c, d, e);\n"
              + "    }\n"
              + "    public Data withC(String c) {\n"
              + "        return new Data(a, b, c, d, e);\n"
              + "    }\n"
              + "    public Data withC(Supplier<String> c) {\n"
              + "        return new Data(a, b, c.get(), d, e);\n"
              + "    }\n"
              + "    public Data withC(Function<String, String> c) {\n"
              + "        return new Data(a, b, c.apply(this.c), d, e);\n"
              + "    }\n"
              + "    public Data withC(BiFunction<Data, String, String> c) {\n"
              + "        return new Data(a, b, c.apply(this, this.c), d, e);\n"
              + "    }\n"
              + "    public Data withD(String d) {\n"
              + "        return new Data(a, b, c, d, e);\n"
              + "    }\n"
              + "    public Data withD(Supplier<String> d) {\n"
              + "        return new Data(a, b, c, d.get(), e);\n"
              + "    }\n"
              + "    public Data withD(Function<String, String> d) {\n"
              + "        return new Data(a, b, c, d.apply(this.d), e);\n"
              + "    }\n"
              + "    public Data withD(BiFunction<Data, String, String> d) {\n"
              + "        return new Data(a, b, c, d.apply(this, this.d), e);\n"
              + "    }\n"
              + "    public Data withE(String e) {\n"
              + "        return new Data(a, b, c, d, e);\n"
              + "    }\n"
              + "    public Data withE(Supplier<String> e) {\n"
              + "        return new Data(a, b, c, d, e.get());\n"
              + "    }\n"
              + "    public Data withE(Function<String, String> e) {\n"
              + "        return new Data(a, b, c, d, e.apply(this.e));\n"
              + "    }\n"
              + "    public Data withE(BiFunction<Data, String, String> e) {\n"
              + "        return new Data(a, b, c, d, e.apply(this, this.e));\n"
              + "    }\n"
              + "    public static Data fromMap(Map<String, ? extends Object> map) {\n"
              + "        Map<String, Getter> $schema = getStructSchema();\n"
              + "        Data obj = new Data(\n"
              + "                    (int)$utils.extractPropertyFromMap(Data.class, int.class, map, $schema, \"a\"),\n"
              + "                    (boolean)$utils.extractPropertyFromMap(Data.class, boolean.class, map, $schema, \"b\"),\n"
              + "                    (String)$utils.extractPropertyFromMap(Data.class, String.class, map, $schema, \"c\"),\n"
              + "                    (String)$utils.extractPropertyFromMap(Data.class, String.class, map, $schema, \"d\"),\n"
              + "                    (String)$utils.extractPropertyFromMap(Data.class, String.class, map, $schema, \"e\")\n"
              + "                );\n"
              + "        return obj;\n"
              + "    }\n"
              + "    public Map<String, Object> __toMap() {\n"
              + "        Map<String, Object> map = new HashMap<>();\n"
              + "        map.put(\"a\", $utils.toMapValueObject(a));\n"
              + "        map.put(\"b\", $utils.toMapValueObject(b));\n"
              + "        map.put(\"c\", $utils.toMapValueObject(c));\n"
              + "        map.put(\"d\", $utils.toMapValueObject(d));\n"
              + "        map.put(\"e\", $utils.toMapValueObject(e));\n"
              + "        return map;\n"
              + "    }\n"
              + "    public Map<String, Getter> __getSchema() {\n"
              + "        return getStructSchema();\n"
              + "    }\n"
              + "    public static Map<String, Getter> getStructSchema() {\n"
              + "        java.util.Map<String, functionalj.types.struct.generator.Getter> map = new java.util.HashMap<>();\n"
              + "        map.put(\"a\", new functionalj.types.struct.generator.Getter(\"a\", new functionalj.types.Type(null, null, \"int\", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED));\n"
              + "        map.put(\"b\", new functionalj.types.struct.generator.Getter(\"b\", new functionalj.types.Type(null, null, \"boolean\", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED));\n"
              + "        map.put(\"c\", new functionalj.types.struct.generator.Getter(\"c\", new functionalj.types.Type(\"java.lang\", null, \"String\", java.util.Collections.emptyList()), true, functionalj.types.DefaultValue.NULL));\n"
              + "        map.put(\"d\", new functionalj.types.struct.generator.Getter(\"d\", new functionalj.types.Type(\"java.lang\", null, \"String\", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED));\n"
              + "        map.put(\"e\", new functionalj.types.struct.generator.Getter(\"e\", new functionalj.types.Type(\"java.lang\", null, \"String\", java.util.Collections.emptyList()), true, functionalj.types.DefaultValue.NULL));\n"
              + "        return map;\n"
              + "    }\n"
              + "    public String toString() {\n"
              + "        return \"Data[\" + \"a: \" + a() + \", \" + \"b: \" + b() + \", \" + \"c: \" + c() + \", \" + \"d: \" + d() + \", \" + \"e: \" + e() + \"]\";\n"
              + "    }\n"
              + "    public int hashCode() {\n"
              + "        return toString().hashCode();\n"
              + "    }\n"
              + "    public boolean equals(Object another) {\n"
              + "        return (another == this) || ((another != null) && (getClass().equals(another.getClass())) && java.util.Objects.equals(toString(), another.toString()));\n"
              + "    }\n"
              + "    \n"
              + "    public static class DataLens<HOST> extends ObjectLensImpl<HOST, Data> {\n"
              + "        \n"
              + "        public final IntegerLens<HOST> a = createSubLensInt(\"a\", Data::a, Data::withA);\n"
              + "        public final BooleanLens<HOST> b = createSubLensBoolean(\"b\", Data::b, Data::withB);\n"
              + "        public final StringLens<HOST> c = createSubLens(\"c\", Data::c, Data::withC, StringLens::of);\n"
              + "        public final StringLens<HOST> d = createSubLens(\"d\", Data::d, Data::withD, StringLens::of);\n"
              + "        public final StringLens<HOST> e = createSubLens(\"e\", Data::e, Data::withE, StringLens::of);\n"
              + "        \n"
              + "        public DataLens(String name, LensSpec<HOST, Data> spec) {\n"
              + "            super(name, spec);\n"
              + "        }\n"
              + "        \n"
              + "    }\n"
              + "    public static final class Builder {\n"
              + "        \n"
              + "        public final DataBuilder_withoutB a(int a) {\n"
              + "            return (boolean b)->{\n"
              + "            return (String c)->{\n"
              + "            return (String d)->{\n"
              + "            return (String e)->{\n"
              + "            return ()->{\n"
              + "                return new Data(\n"
              + "                    a,\n"
              + "                    b,\n"
              + "                    c,\n"
              + "                    d,\n"
              + "                    e\n"
              + "                );\n"
              + "            };\n"
              + "            };\n"
              + "            };\n"
              + "            };\n"
              + "            };\n"
              + "        }\n"
              + "        \n"
              + "        public static interface DataBuilder_withoutB {\n"
              + "            \n"
              + "            public DataBuilder_withoutC b(boolean b);\n"
              + "            \n"
              + "        }\n"
              + "        public static interface DataBuilder_withoutC {\n"
              + "            \n"
              + "            public DataBuilder_withoutD c(String c);\n"
              + "            \n"
              + "            public default DataBuilder_withoutE d(String d){\n"
              + "                return c(null).d(d);\n"
              + "            }\n"
              + "            \n"
              + "        }\n"
              + "        public static interface DataBuilder_withoutD {\n"
              + "            \n"
              + "            public DataBuilder_withoutE d(String d);\n"
              + "            \n"
              + "        }\n"
              + "        public static interface DataBuilder_withoutE {\n"
              + "            \n"
              + "            public DataBuilder_ready e(String e);\n"
              + "            \n"
              + "            public default Data build() {\n"
              + "                return e(null).build();\n"
              + "            }\n"
              + "            \n"
              + "        }\n"
              + "        public static interface DataBuilder_ready {\n"
              + "            \n"
              + "            public Data build();\n"
              + "            \n"
              + "            \n"
              + "            \n"
              + "        }\n"
              + "        \n"
              + "        \n"
              + "    }\n"
              + "    \n"
              + "}",
              generated);
    }
    
    private String generate() {
        return generate(null);
    }
    
    private String generate(Runnable setting) {
        if (setting != null)
            setting.run();
        SourceSpec sourceSpec = new SourceSpec(
                new JavaVersionInfo(8, 8),
                definitionClassName, // specClassName
                packageName,         // packageName
                null,                // encloseName
                targetClassName,     // targetClassName
                packageName,         // targetPackageName
                sourceKind,
                null,
                null,
                configuration,
                getters,
                emptyList(),
                emptyList());
        val dataObjSpec = new StructClassSpecBuilder(sourceSpec).build();
        val generated = new GenStruct(sourceSpec, dataObjSpec).toText();
        return generated;
    }
}

package functionalj.annotations.struct.generator;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import functionalj.annotations.DefaultValue;
import functionalj.annotations.struct.generator.SourceSpec.Configurations;
import functionalj.annotations.struct.generator.model.GenStruct;
import lombok.val;

public class NonRequireFieldTest {

    private Configurations configures = new Configurations();
    {
        configures.coupleWithDefinition      = true;
        configures.generateNoArgConstructor  = true;
        configures.generateAllArgConstructor = true;
        configures.generateLensClass         = true;
    }
    
    private String  definitionClassName = "Definitions.DataDef";
    private String  targetClassName     = "Data";
    private String  packageName         = "me.test";
    private boolean isClass             = false;
    
    private List<Getter> getters = asList(
            new Getter("a", Type.INT),
            new Getter("b", Type.BOOL),
            new Getter("c", Type.STRING, true, DefaultValue.NULL),
            new Getter("d", Type.STRING),
            new Getter("e", Type.STRING, true, DefaultValue.NULL)
    );
    
    @Test
    public void testBuilder() {
        val generated = generate();
        assertEquals(
                "package me.test;\n" + 
                "\n" + 
                "import functionalj.annotations.IPostConstruct;\n" + 
                "import functionalj.annotations.IStruct;\n" + 
                "import functionalj.annotations.struct.generator.Getter;\n" + 
                "import functionalj.annotations.struct.generator.Type;\n" + 
                "import functionalj.lens.core.LensSpec;\n" + 
                "import functionalj.lens.lenses.BooleanLens;\n" + 
                "import functionalj.lens.lenses.IntegerLens;\n" + 
                "import functionalj.lens.lenses.ObjectLensImpl;\n" + 
                "import functionalj.lens.lenses.StringLens;\n" + 
                "import java.lang.Object;\n" + 
                "import java.util.HashMap;\n" + 
                "import java.util.Map;\n" + 
                "import java.util.function.BiFunction;\n" + 
                "import java.util.function.Function;\n" + 
                "import java.util.function.Supplier;\n" + 
                "\n" + 
                "public class Data implements Definitions.DataDef,IStruct {\n" + 
                "    \n" + 
                "    public static final DataLens<Data> theData = new DataLens<>(LensSpec.of(Data.class));\n" + 
                "    private final int a;\n" + 
                "    private final boolean b;\n" + 
                "    private final String c;\n" + 
                "    private final String d;\n" + 
                "    private final String e;\n" + 
                "    \n" + 
                "    public Data() {\n" + 
                "        this(0, false, null, null, null);\n" + 
                "    }\n" + 
                "    public Data(int a, boolean b, String d) {\n" + 
                "        this.a=a;\n" + 
                "        this.b=b;\n" + 
                "        this.c=null;\n" + 
                "        this.d=d;\n" + 
                "        this.e=null;\n" + 
                "        if (this instanceof IPostConstruct) ((IPostConstruct)this).postConstruct();\n" + 
                "    }\n" + 
                "    public Data(int a, boolean b, String c, String d, String e) {\n" + 
                "        this.a = a;\n" + 
                "        this.b = b;\n" + 
                "        this.c = c;\n" + 
                "        this.d = $utils.notNull(d);\n" + 
                "        this.e = e;\n" + 
                "        if (this instanceof IPostConstruct) ((IPostConstruct)this).postConstruct();\n" + 
                "    }\n" + 
                "    \n" + 
                "    public int a() {\n" + 
                "        return a;\n" + 
                "    }\n" + 
                "    public boolean b() {\n" + 
                "        return b;\n" + 
                "    }\n" + 
                "    public String c() {\n" + 
                "        return c;\n" + 
                "    }\n" + 
                "    public String d() {\n" + 
                "        return d;\n" + 
                "    }\n" + 
                "    public String e() {\n" + 
                "        return e;\n" + 
                "    }\n" + 
                "    public Data withA(int a) {\n" + 
                "        return new Data(a, b, c, d, e);\n" + 
                "    }\n" + 
                "    public Data withA(Supplier<Integer> a) {\n" + 
                "        return new Data(a.get(), b, c, d, e);\n" + 
                "    }\n" + 
                "    public Data withA(Function<Integer, Integer> a) {\n" + 
                "        return new Data(a.apply(this.a), b, c, d, e);\n" + 
                "    }\n" + 
                "    public Data withA(BiFunction<Data, Integer, Integer> a) {\n" + 
                "        return new Data(a.apply(this, this.a), b, c, d, e);\n" + 
                "    }\n" + 
                "    public Data withB(boolean b) {\n" + 
                "        return new Data(a, b, c, d, e);\n" + 
                "    }\n" + 
                "    public Data withB(Supplier<Boolean> b) {\n" + 
                "        return new Data(a, b.get(), c, d, e);\n" + 
                "    }\n" + 
                "    public Data withB(Function<Boolean, Boolean> b) {\n" + 
                "        return new Data(a, b.apply(this.b), c, d, e);\n" + 
                "    }\n" + 
                "    public Data withB(BiFunction<Data, Boolean, Boolean> b) {\n" + 
                "        return new Data(a, b.apply(this, this.b), c, d, e);\n" + 
                "    }\n" + 
                "    public Data withC(String c) {\n" + 
                "        return new Data(a, b, c, d, e);\n" + 
                "    }\n" + 
                "    public Data withC(Supplier<String> c) {\n" + 
                "        return new Data(a, b, c.get(), d, e);\n" + 
                "    }\n" + 
                "    public Data withC(Function<String, String> c) {\n" + 
                "        return new Data(a, b, c.apply(this.c), d, e);\n" + 
                "    }\n" + 
                "    public Data withC(BiFunction<Data, String, String> c) {\n" + 
                "        return new Data(a, b, c.apply(this, this.c), d, e);\n" + 
                "    }\n" + 
                "    public Data withD(String d) {\n" + 
                "        return new Data(a, b, c, d, e);\n" + 
                "    }\n" + 
                "    public Data withD(Supplier<String> d) {\n" + 
                "        return new Data(a, b, c, d.get(), e);\n" + 
                "    }\n" + 
                "    public Data withD(Function<String, String> d) {\n" + 
                "        return new Data(a, b, c, d.apply(this.d), e);\n" + 
                "    }\n" + 
                "    public Data withD(BiFunction<Data, String, String> d) {\n" + 
                "        return new Data(a, b, c, d.apply(this, this.d), e);\n" + 
                "    }\n" + 
                "    public Data withE(String e) {\n" + 
                "        return new Data(a, b, c, d, e);\n" + 
                "    }\n" + 
                "    public Data withE(Supplier<String> e) {\n" + 
                "        return new Data(a, b, c, d, e.get());\n" + 
                "    }\n" + 
                "    public Data withE(Function<String, String> e) {\n" + 
                "        return new Data(a, b, c, d, e.apply(this.e));\n" + 
                "    }\n" + 
                "    public Data withE(BiFunction<Data, String, String> e) {\n" + 
                "        return new Data(a, b, c, d, e.apply(this, this.e));\n" + 
                "    }\n" + 
                "    public static Data fromMap(Map<String, Object> map) {\n" + 
                "        Map<String, Getter> $schema = getStructSchema();\n" + 
                "        \n" + 
                "        Data obj = new Data(\n" + 
                "                    (int)IStruct.fromMapValue(map.get(\"a\"), $schema.get(\"a\")),\n" + 
                "                    (boolean)IStruct.fromMapValue(map.get(\"b\"), $schema.get(\"b\")),\n" + 
                "                    (String)IStruct.fromMapValue(map.get(\"c\"), $schema.get(\"c\")),\n" + 
                "                    (String)IStruct.fromMapValue(map.get(\"d\"), $schema.get(\"d\")),\n" + 
                "                    (String)IStruct.fromMapValue(map.get(\"e\"), $schema.get(\"e\"))\n" + 
                "                );\n" + 
                "        return obj;\n" + 
                "    }\n" + 
                "    public Map<String, Object> toMap() {\n" + 
                "        Map<String, Object> map = new HashMap<>();\n" + 
                "        map.put(\"a\", IStruct.toMapValueObject(a));\n" + 
                "        map.put(\"b\", IStruct.toMapValueObject(b));\n" + 
                "        map.put(\"c\", IStruct.toMapValueObject(c));\n" + 
                "        map.put(\"d\", IStruct.toMapValueObject(d));\n" + 
                "        map.put(\"e\", IStruct.toMapValueObject(e));\n" + 
                "        return map;\n" + 
                "    }\n" + 
                "    public Map<String, Getter> getSchema() {\n" + 
                "        return getStructSchema();\n" + 
                "    }\n" + 
                "    public static Map<String, Getter> getStructSchema() {\n" + 
                "        Map<String, Getter> map = new HashMap<>();\n" + 
                "        map.put(\"a\", new functionalj.annotations.struct.generator.Getter(\"a\", new Type(null, \"int\", \"\", java.util.Collections.emptyList()), false, functionalj.annotations.DefaultValue.REQUIRED));\n" + 
                "        map.put(\"b\", new functionalj.annotations.struct.generator.Getter(\"b\", new Type(null, \"boolean\", \"\", java.util.Collections.emptyList()), false, functionalj.annotations.DefaultValue.REQUIRED));\n" + 
                "        map.put(\"c\", new functionalj.annotations.struct.generator.Getter(\"c\", new Type(null, \"String\", \"java.lang\", java.util.Collections.emptyList()), true, functionalj.annotations.DefaultValue.NULL));\n" + 
                "        map.put(\"d\", new functionalj.annotations.struct.generator.Getter(\"d\", new Type(null, \"String\", \"java.lang\", java.util.Collections.emptyList()), false, functionalj.annotations.DefaultValue.REQUIRED));\n" + 
                "        map.put(\"e\", new functionalj.annotations.struct.generator.Getter(\"e\", new Type(null, \"String\", \"java.lang\", java.util.Collections.emptyList()), true, functionalj.annotations.DefaultValue.NULL));\n" + 
                "        return map;\n" + 
                "    }\n" + 
                "    public String toString() {\n" + 
                "        return \"Data[\" + \"a: \" + a() + \", \" + \"b: \" + b() + \", \" + \"c: \" + c() + \", \" + \"d: \" + d() + \", \" + \"e: \" + e() + \"]\";\n" + 
                "    }\n" + 
                "    public int hashCode() {\n" + 
                "        return toString().hashCode();\n" + 
                "    }\n" + 
                "    public boolean equals(Object another) {\n" + 
                "        return (another == this) || ((another != null) && (getClass().equals(another.getClass())) && java.util.Objects.equals(toString(), another.toString()));\n" + 
                "    }\n" + 
                "    \n" + 
                "    public static class DataLens<HOST> extends ObjectLensImpl<HOST, Data> {\n" + 
                "        \n" + 
                "        public final IntegerLens<HOST> a = createSubLens(Data::a, Data::withA, IntegerLens::of);\n" + 
                "        public final BooleanLens<HOST> b = createSubLens(Data::b, Data::withB, BooleanLens::of);\n" + 
                "        public final StringLens<HOST> c = createSubLens(Data::c, Data::withC, StringLens::of);\n" + 
                "        public final StringLens<HOST> d = createSubLens(Data::d, Data::withD, StringLens::of);\n" + 
                "        public final StringLens<HOST> e = createSubLens(Data::e, Data::withE, StringLens::of);\n" + 
                "        \n" + 
                "        public DataLens(LensSpec<HOST, Data> spec) {\n" + 
                "            super(spec);\n" + 
                "        }\n" + 
                "        \n" + 
                "    }\n" + 
                "    public static class Builder {\n" + 
                "        \n" + 
                "        public Builder_a a(int a) {\n" + 
                "            return new Builder_a(a);\n" + 
                "        }\n" + 
                "        \n" + 
                "        public static class Builder_a {\n" + 
                "            \n" + 
                "            private final int a;\n" + 
                "            \n" + 
                "            private Builder_a(int a) {\n" + 
                "                this.a = a;\n" + 
                "            }\n" + 
                "            \n" + 
                "            public int a() {\n" + 
                "                return a;\n" + 
                "            }\n" + 
                "            public Builder_a a(int a) {\n" + 
                "                return new Builder_a(a);\n" + 
                "            }\n" + 
                "            public Builder_a_b b(boolean b) {\n" + 
                "                return new Builder_a_b(this, b);\n" + 
                "            }\n" + 
                "            \n" + 
                "        }\n" + 
                "        public static class Builder_a_b {\n" + 
                "            \n" + 
                "            private final Builder_a parent;\n" + 
                "            private final boolean b;\n" + 
                "            \n" + 
                "            private Builder_a_b(Builder_a parent, boolean b) {\n" + 
                "                this.parent = parent;\n" + 
                "                this.b = b;\n" + 
                "            }\n" + 
                "            \n" + 
                "            public int a() {\n" + 
                "                return parent.a();\n" + 
                "            }\n" + 
                "            public boolean b() {\n" + 
                "                return b;\n" + 
                "            }\n" + 
                "            public Builder_a_b a(int a) {\n" + 
                "                return parent.a(a).b(b);\n" + 
                "            }\n" + 
                "            public Builder_a_b b(boolean b) {\n" + 
                "                return parent.b(b);\n" + 
                "            }\n" + 
                "            public Builder_a_b_c c(String c) {\n" + 
                "                return new Builder_a_b_c(this, c);\n" + 
                "            }\n" + 
                "            public Builder_a_b_c_d d(String d) {\n" + 
                "                return c(null).d(d);\n" + 
                "            }\n" + 
                "            \n" + 
                "        }\n" + 
                "        public static class Builder_a_b_c {\n" + 
                "            \n" + 
                "            private final Builder_a_b parent;\n" + 
                "            private final String c;\n" + 
                "            \n" + 
                "            private Builder_a_b_c(Builder_a_b parent, String c) {\n" + 
                "                this.parent = parent;\n" + 
                "                this.c = c;\n" + 
                "            }\n" + 
                "            \n" + 
                "            public int a() {\n" + 
                "                return parent.a();\n" + 
                "            }\n" + 
                "            public boolean b() {\n" + 
                "                return parent.b();\n" + 
                "            }\n" + 
                "            public String c() {\n" + 
                "                return c;\n" + 
                "            }\n" + 
                "            public Builder_a_b_c a(int a) {\n" + 
                "                return parent.a(a).c(c);\n" + 
                "            }\n" + 
                "            public Builder_a_b_c b(boolean b) {\n" + 
                "                return parent.b(b).c(c);\n" + 
                "            }\n" + 
                "            public Builder_a_b_c c(String c) {\n" + 
                "                return parent.c(c);\n" + 
                "            }\n" + 
                "            public Builder_a_b_c_d d(String d) {\n" + 
                "                return new Builder_a_b_c_d(this, d);\n" + 
                "            }\n" + 
                "            \n" + 
                "        }\n" + 
                "        public static class Builder_a_b_c_d {\n" + 
                "            \n" + 
                "            private final Builder_a_b_c parent;\n" + 
                "            private final String d;\n" + 
                "            \n" + 
                "            private Builder_a_b_c_d(Builder_a_b_c parent, String d) {\n" + 
                "                this.parent = parent;\n" + 
                "                this.d = $utils.notNull(d);\n" + 
                "            }\n" + 
                "            \n" + 
                "            public int a() {\n" + 
                "                return parent.a();\n" + 
                "            }\n" + 
                "            public boolean b() {\n" + 
                "                return parent.b();\n" + 
                "            }\n" + 
                "            public String c() {\n" + 
                "                return parent.c();\n" + 
                "            }\n" + 
                "            public String d() {\n" + 
                "                return d;\n" + 
                "            }\n" + 
                "            public Builder_a_b_c_d a(int a) {\n" + 
                "                return parent.a(a).d(d);\n" + 
                "            }\n" + 
                "            public Builder_a_b_c_d b(boolean b) {\n" + 
                "                return parent.b(b).d(d);\n" + 
                "            }\n" + 
                "            public Builder_a_b_c_d c(String c) {\n" + 
                "                return parent.c(c).d(d);\n" + 
                "            }\n" + 
                "            public Builder_a_b_c_d d(String d) {\n" + 
                "                return parent.d(d);\n" + 
                "            }\n" + 
                "            public Builder_a_b_c_d_e e(String e) {\n" + 
                "                return new Builder_a_b_c_d_e(this, e);\n" + 
                "            }\n" + 
                "            public Data build() {\n" + 
                "                return e(null).build();\n" + 
                "            }\n" + 
                "            \n" + 
                "        }\n" + 
                "        public static class Builder_a_b_c_d_e {\n" + 
                "            \n" + 
                "            private final Builder_a_b_c_d parent;\n" + 
                "            private final String e;\n" + 
                "            \n" + 
                "            private Builder_a_b_c_d_e(Builder_a_b_c_d parent, String e) {\n" + 
                "                this.parent = parent;\n" + 
                "                this.e = e;\n" + 
                "            }\n" + 
                "            \n" + 
                "            public int a() {\n" + 
                "                return parent.a();\n" + 
                "            }\n" + 
                "            public boolean b() {\n" + 
                "                return parent.b();\n" + 
                "            }\n" + 
                "            public String c() {\n" + 
                "                return parent.c();\n" + 
                "            }\n" + 
                "            public String d() {\n" + 
                "                return parent.d();\n" + 
                "            }\n" + 
                "            public String e() {\n" + 
                "                return e;\n" + 
                "            }\n" + 
                "            public Builder_a_b_c_d_e a(int a) {\n" + 
                "                return parent.a(a).e(e);\n" + 
                "            }\n" + 
                "            public Builder_a_b_c_d_e b(boolean b) {\n" + 
                "                return parent.b(b).e(e);\n" + 
                "            }\n" + 
                "            public Builder_a_b_c_d_e c(String c) {\n" + 
                "                return parent.c(c).e(e);\n" + 
                "            }\n" + 
                "            public Builder_a_b_c_d_e d(String d) {\n" + 
                "                return parent.d(d).e(e);\n" + 
                "            }\n" + 
                "            public Builder_a_b_c_d_e e(String e) {\n" + 
                "                return parent.e(e);\n" + 
                "            }\n" + 
                "            public Data build() {\n" + 
                "                return new Data(a(), b(), c(), d(), e());\n" + 
                "            }\n" + 
                "            \n" + 
                "        }\n" + 
                "        \n" + 
                "    }\n" + 
                "    \n" + 
                "}",
                generated);
        
    }
    
    private String generate() {
        return generate(null);
    }
    
    private String generate(Runnable setting) {
        if (setting != null)
            setting.run();
        
        SourceSpec sourceSpec = new SourceSpec(
                    definitionClassName, // specClassName
                    packageName,         // packageName
                    null,                // encloseName
                    targetClassName,     // targetClassName
                    packageName,         // targetPackageName
                    isClass,             // isClass
                    null,
                    null,
                    configures,          // Configurations
                    getters,
                    emptyList());
        val dataObjSpec = new StructBuilder(sourceSpec).build();
        val generated   = new GenStruct(sourceSpec, dataObjSpec).toText();
        return generated;
    }
    
}

package functionalj.annotations.dataobject.generator;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import functionalj.annotations.dataobject.generator.SourceSpec.Configurations;
import lombok.val;

public class BuilderClassBuilderTest {
    
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
            new Getter("c", Type.STRING, false),
            new Getter("d", Type.STRING),
            new Getter("e", Type.STRING, false)
    );
    
    private SourceSpec getSpec() {
        return new SourceSpec(
                    definitionClassName, // specClassName
                    packageName,         // packageName
                    targetClassName,     // targetClassName
                    packageName,         // targetPackageName
                    isClass,             // isClass
                    null,
                    configures,          // Configurations
                    getters);
    }
    
    @Test
    public void testBuilder() {
        val sourceSpec = getSpec();
        val builder    = new BuilderClassBuilder(sourceSpec);
        assertEquals(
                "public static class Builder {\n" + 
                "    \n" + 
                "    public Builder_a a(int a) {\n" + 
                "        return new Builder_a(a);\n" + 
                "    }\n" + 
                "    \n" + 
                "    public static class Builder_a {\n" + 
                "        \n" + 
                "        private final int a;\n" + 
                "        \n" + 
                "        private Builder_a(int a) {\n" + 
                "            this.a = a;\n" + 
                "        }\n" + 
                "        \n" + 
                "        public int a() {\n" + 
                "            return a;\n" + 
                "        }\n" + 
                "        public Builder_a a(int a) {\n" + 
                "            return new Builder_a(a);\n" + 
                "        }\n" + 
                "        public Builder_a_b b(boolean b) {\n" + 
                "            return new Builder_a_b(this, b);\n" + 
                "        }\n" + 
                "        \n" + 
                "    }\n" + 
                "    public static class Builder_a_b {\n" + 
                "        \n" + 
                "        private final Builder_a parent;\n" + 
                "        private final boolean b;\n" + 
                "        \n" + 
                "        private Builder_a_b(Builder_a parent, boolean b) {\n" + 
                "            this.parent = parent;\n" + 
                "            this.b = b;\n" + 
                "        }\n" + 
                "        \n" + 
                "        public int a() {\n" + 
                "            return parent.a();\n" + 
                "        }\n" + 
                "        public boolean b() {\n" + 
                "            return b;\n" + 
                "        }\n" + 
                "        public Builder_a_b a(int a) {\n" + 
                "            return parent.a(a).b(b);\n" + 
                "        }\n" + 
                "        public Builder_a_b b(boolean b) {\n" + 
                "            return parent.b(b);\n" + 
                "        }\n" + 
                "        public Builder_a_b_c c(String c) {\n" + 
                "            return new Builder_a_b_c(this, c);\n" + 
                "        }\n" + 
                "        public Builder_a_b_c_d d(String d) {\n" + 
                "            return c(null).d(d);\n" + 
                "        }\n" + 
                "        \n" + 
                "    }\n" + 
                "    public static class Builder_a_b_c {\n" + 
                "        \n" + 
                "        private final Builder_a_b parent;\n" + 
                "        private final String c;\n" + 
                "        \n" + 
                "        private Builder_a_b_c(Builder_a_b parent, String c) {\n" + 
                "            this.parent = parent;\n" + 
                "            this.c = c;\n" + 
                "        }\n" + 
                "        \n" + 
                "        public int a() {\n" + 
                "            return parent.a();\n" + 
                "        }\n" + 
                "        public boolean b() {\n" + 
                "            return parent.b();\n" + 
                "        }\n" + 
                "        public String c() {\n" + 
                "            return c;\n" + 
                "        }\n" + 
                "        public Builder_a_b_c a(int a) {\n" + 
                "            return parent.a(a).c(c);\n" + 
                "        }\n" + 
                "        public Builder_a_b_c b(boolean b) {\n" + 
                "            return parent.b(b).c(c);\n" + 
                "        }\n" + 
                "        public Builder_a_b_c c(String c) {\n" + 
                "            return parent.c(c);\n" + 
                "        }\n" + 
                "        public Builder_a_b_c_d d(String d) {\n" + 
                "            return new Builder_a_b_c_d(this, d);\n" + 
                "        }\n" + 
                "        \n" + 
                "    }\n" + 
                "    public static class Builder_a_b_c_d {\n" + 
                "        \n" + 
                "        private final Builder_a_b_c parent;\n" + 
                "        private final String d;\n" + 
                "        \n" + 
                "        private Builder_a_b_c_d(Builder_a_b_c parent, String d) {\n" + 
                "            this.parent = parent;\n" + 
                "            this.d = d;\n" + 
                "        }\n" + 
                "        \n" + 
                "        public int a() {\n" + 
                "            return parent.a();\n" + 
                "        }\n" + 
                "        public boolean b() {\n" + 
                "            return parent.b();\n" + 
                "        }\n" + 
                "        public String c() {\n" + 
                "            return parent.c();\n" + 
                "        }\n" + 
                "        public String d() {\n" + 
                "            return d;\n" + 
                "        }\n" + 
                "        public Builder_a_b_c_d a(int a) {\n" + 
                "            return parent.a(a).d(d);\n" + 
                "        }\n" + 
                "        public Builder_a_b_c_d b(boolean b) {\n" + 
                "            return parent.b(b).d(d);\n" + 
                "        }\n" + 
                "        public Builder_a_b_c_d c(String c) {\n" + 
                "            return parent.c(c).d(d);\n" + 
                "        }\n" + 
                "        public Builder_a_b_c_d d(String d) {\n" + 
                "            return parent.d(d);\n" + 
                "        }\n" + 
                "        public Builder_a_b_c_d_e e(String e) {\n" + 
                "            return new Builder_a_b_c_d_e(this, e);\n" + 
                "        }\n" + 
                "        public Data build() {\n" + 
                "            return e(null).build();\n" + 
                "        }\n" + 
                "        \n" + 
                "    }\n" + 
                "    public static class Builder_a_b_c_d_e {\n" + 
                "        \n" + 
                "        private final Builder_a_b_c_d parent;\n" + 
                "        private final String e;\n" + 
                "        \n" + 
                "        private Builder_a_b_c_d_e(Builder_a_b_c_d parent, String e) {\n" + 
                "            this.parent = parent;\n" + 
                "            this.e = e;\n" + 
                "        }\n" + 
                "        \n" + 
                "        public int a() {\n" + 
                "            return parent.a();\n" + 
                "        }\n" + 
                "        public boolean b() {\n" + 
                "            return parent.b();\n" + 
                "        }\n" + 
                "        public String c() {\n" + 
                "            return parent.c();\n" + 
                "        }\n" + 
                "        public String d() {\n" + 
                "            return parent.d();\n" + 
                "        }\n" + 
                "        public String e() {\n" + 
                "            return e;\n" + 
                "        }\n" + 
                "        public Builder_a_b_c_d_e a(int a) {\n" + 
                "            return parent.a(a).e(e);\n" + 
                "        }\n" + 
                "        public Builder_a_b_c_d_e b(boolean b) {\n" + 
                "            return parent.b(b).e(e);\n" + 
                "        }\n" + 
                "        public Builder_a_b_c_d_e c(String c) {\n" + 
                "            return parent.c(c).e(e);\n" + 
                "        }\n" + 
                "        public Builder_a_b_c_d_e d(String d) {\n" + 
                "            return parent.d(d).e(e);\n" + 
                "        }\n" + 
                "        public Builder_a_b_c_d_e e(String e) {\n" + 
                "            return parent.e(e);\n" + 
                "        }\n" + 
                "        public Data build() {\n" + 
                "            return new Data(a(), b(), c(), d(), e());\n" + 
                "        }\n" + 
                "        \n" + 
                "    }\n" + 
                "    \n" + 
                "}",
                builder.build().toDefinition("pckg").toText());
        
    }
    
}

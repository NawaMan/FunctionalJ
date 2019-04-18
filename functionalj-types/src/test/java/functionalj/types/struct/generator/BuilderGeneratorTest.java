// ============================================================================
// Copyright (c) 2017-2019 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import functionalj.types.DefaultValue;
import functionalj.types.struct.generator.SourceSpec.Configurations;
import lombok.val;

public class BuilderGeneratorTest {
    
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
    
    @Test
    public void testMixed() {
        val getters = asList(
                new Getter("a", Type.INT, true, DefaultValue.MINUS_ONE),
                new Getter("b", Type.BOOL),
                new Getter("c", Type.STRING, true, DefaultValue.NULL),
                new Getter("d", Type.STRING),
                new Getter("e", Type.STRING, true, DefaultValue.NULL)
        );
        val sourceSpec = new SourceSpec(
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
        val builder    = new BuilderGenerator(sourceSpec);
        assertEquals(
                "public static final class Builder {\n" + 
                "    \n" + 
                "    public final DataBuilder_withoutB a(int a) {\n" + 
                "        return (boolean b)->{\n" + 
                "        return (String c)->{\n" + 
                "        return (String d)->{\n" + 
                "        return (String e)->{\n" + 
                "        return ()->{\n" + 
                "            return new Data(\n" + 
                "                a,\n" + 
                "                b,\n" + 
                "                c,\n" + 
                "                d,\n" + 
                "                e\n" + 
                "            );\n" + 
                "        };\n" + 
                "        };\n" + 
                "        };\n" + 
                "        };\n" + 
                "        };\n" + 
                "    }\n" + 
                "    \n" + 
                "    public static interface DataBuilder_withoutB {\n" + 
                "        \n" + 
                "        public DataBuilder_withoutC b(boolean b);\n" + 
                "        \n" + 
                "    }\n" + 
                "    public static interface DataBuilder_withoutC {\n" + 
                "        \n" + 
                "        public DataBuilder_withoutD c(String c);\n" + 
                "        \n" + 
                "        public default DataBuilder_withoutE d(String d){\n" + 
                "            return c(null).d(d);\n" + 
                "        }\n" + 
                "        \n" + 
                "    }\n" + 
                "    public static interface DataBuilder_withoutD {\n" + 
                "        \n" + 
                "        public DataBuilder_withoutE d(String d);\n" + 
                "        \n" + 
                "    }\n" + 
                "    public static interface DataBuilder_withoutE {\n" + 
                "        \n" + 
                "        public DataBuilder_ready e(String e);\n" + 
                "        \n" + 
                "        public default Data build() {\n" + 
                "            return e(null).build();\n" + 
                "        }\n" + 
                "        \n" + 
                "    }\n" + 
                "    public static interface DataBuilder_ready {\n" + 
                "        \n" + 
                "        public Data build();\n" + 
                "        \n" + 
                "        \n" + 
                "        \n" + 
                "    }\n" + 
                "    \n" + 
                "    \n" + 
                "    public DataBuilder_withoutC b(boolean b){\n" + 
                "        return a(-1).b(b);\n" + 
                "    }\n" + 
                "    \n" + 
                "}",
                builder.build().toDefinition("pckg").toText());
        
    }
    
    @Test
    public void testAllRequired() {
        val getters = asList(
                new Getter("a", Type.INT),
                new Getter("b", Type.BOOL),
                new Getter("c", Type.STRING),
                new Getter("d", Type.STRING),
                new Getter("e", Type.STRING)
        );
        val sourceSpec = new SourceSpec(
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
        val builder    = new BuilderGenerator(sourceSpec);
        assertEquals(
                "public static final class Builder {\n" + 
                "    \n" + 
                "    public final DataBuilder_withoutB a(int a) {\n" + 
                "        return (boolean b)->{\n" + 
                "        return (String c)->{\n" + 
                "        return (String d)->{\n" + 
                "        return (String e)->{\n" + 
                "        return ()->{\n" + 
                "            return new Data(\n" + 
                "                a,\n" + 
                "                b,\n" + 
                "                c,\n" + 
                "                d,\n" + 
                "                e\n" + 
                "            );\n" + 
                "        };\n" + 
                "        };\n" + 
                "        };\n" + 
                "        };\n" + 
                "        };\n" + 
                "    }\n" + 
                "    \n" + 
                "    public static interface DataBuilder_withoutB {\n" + 
                "        \n" + 
                "        public DataBuilder_withoutC b(boolean b);\n" + 
                "        \n" + 
                "    }\n" + 
                "    public static interface DataBuilder_withoutC {\n" + 
                "        \n" + 
                "        public DataBuilder_withoutD c(String c);\n" + 
                "        \n" + 
                "    }\n" + 
                "    public static interface DataBuilder_withoutD {\n" + 
                "        \n" + 
                "        public DataBuilder_withoutE d(String d);\n" + 
                "        \n" + 
                "    }\n" + 
                "    public static interface DataBuilder_withoutE {\n" + 
                "        \n" + 
                "        public DataBuilder_ready e(String e);\n" + 
                "        \n" + 
                "    }\n" + 
                "    public static interface DataBuilder_ready {\n" + 
                "        \n" + 
                "        public Data build();\n" + 
                "        \n" + 
                "        \n" + 
                "        \n" + 
                "    }\n" + 
                "    \n" + 
                "    \n" + 
                "}",
                builder.build().toDefinition("pckg").toText());
        
    }
    
    @Test
    public void testAllOptional() {
        val getters = asList(
                new Getter("a", Type.INT,    true, DefaultValue.MINUS_ONE),
                new Getter("b", Type.BOOL,   true, DefaultValue.FALSE),
                new Getter("c", Type.STRING, true, DefaultValue.NULL),
                new Getter("d", Type.STRING, true, DefaultValue.EMPTY),
                new Getter("e", Type.STRING, true, DefaultValue.NULL)
        );
        val sourceSpec = new SourceSpec(
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
        val builder    = new BuilderGenerator(sourceSpec);
        assertEquals(
                "public static final class Builder {\n" + 
                "    \n" + 
                "    public final DataBuilder_withoutB a(int a) {\n" + 
                "        return (boolean b)->{\n" + 
                "        return (String c)->{\n" + 
                "        return (String d)->{\n" + 
                "        return (String e)->{\n" + 
                "        return ()->{\n" + 
                "            return new Data(\n" + 
                "                a,\n" + 
                "                b,\n" + 
                "                c,\n" + 
                "                d,\n" + 
                "                e\n" + 
                "            );\n" + 
                "        };\n" + 
                "        };\n" + 
                "        };\n" + 
                "        };\n" + 
                "        };\n" + 
                "    }\n" + 
                "    \n" + 
                "    public static interface DataBuilder_withoutB {\n" + 
                "        \n" + 
                "        public DataBuilder_withoutC b(boolean b);\n" + 
                "        \n" + 
                "        public default DataBuilder_withoutD c(String c){\n" + 
                "            return b(false).c(c);\n" + 
                "        }\n" + 
                "        public default DataBuilder_withoutE d(String d){\n" + 
                "            return b(false).c(null).d(d);\n" + 
                "        }\n" + 
                "        public default DataBuilder_ready e(String e){\n" + 
                "            return b(false).c(null).d(\"\").e(e);\n" + 
                "        }\n" + 
                "        public default Data build() {\n" + 
                "            return b(false).c(null).d(\"\").e(null).build();\n" + 
                "        }\n" + 
                "        \n" + 
                "    }\n" + 
                "    public static interface DataBuilder_withoutC {\n" + 
                "        \n" + 
                "        public DataBuilder_withoutD c(String c);\n" + 
                "        \n" + 
                "        public default DataBuilder_withoutE d(String d){\n" + 
                "            return c(null).d(d);\n" + 
                "        }\n" + 
                "        public default DataBuilder_ready e(String e){\n" + 
                "            return c(null).d(\"\").e(e);\n" + 
                "        }\n" + 
                "        public default Data build() {\n" + 
                "            return c(null).d(\"\").e(null).build();\n" + 
                "        }\n" + 
                "        \n" + 
                "    }\n" + 
                "    public static interface DataBuilder_withoutD {\n" + 
                "        \n" + 
                "        public DataBuilder_withoutE d(String d);\n" + 
                "        \n" + 
                "        public default DataBuilder_ready e(String e){\n" + 
                "            return d(\"\").e(e);\n" + 
                "        }\n" + 
                "        public default Data build() {\n" + 
                "            return d(\"\").e(null).build();\n" + 
                "        }\n" + 
                "        \n" + 
                "    }\n" + 
                "    public static interface DataBuilder_withoutE {\n" + 
                "        \n" + 
                "        public DataBuilder_ready e(String e);\n" + 
                "        \n" + 
                "        public default Data build() {\n" + 
                "            return e(null).build();\n" + 
                "        }\n" + 
                "        \n" + 
                "    }\n" + 
                "    public static interface DataBuilder_ready {\n" + 
                "        \n" + 
                "        public Data build();\n" + 
                "        \n" + 
                "        \n" + 
                "        \n" + 
                "    }\n" + 
                "    \n" + 
                "    \n" + 
                "    public DataBuilder_withoutC b(boolean b){\n" + 
                "        return a(-1).b(b);\n" + 
                "    }\n" + 
                "    public DataBuilder_withoutD c(String c){\n" + 
                "        return a(-1).b(false).c(c);\n" + 
                "    }\n" + 
                "    public DataBuilder_withoutE d(String d){\n" + 
                "        return a(-1).b(false).c(null).d(d);\n" + 
                "    }\n" + 
                "    public DataBuilder_ready e(String e){\n" + 
                "        return a(-1).b(false).c(null).d(\"\").e(e);\n" + 
                "    }\n" + 
                "    public Data build() {\n" + 
                "        return a(-1).b(false).c(null).d(\"\").e(null).build();\n" + 
                "    }\n" + 
                "    \n" + 
                "}",
                builder.build().toDefinition("pckg").toText());
        
    }
    
    @Test
    public void testOneRequired() {
        val getters = asList(
                new Getter("a", Type.INT)
        );
        val sourceSpec = new SourceSpec(
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
        val builder    = new BuilderGenerator(sourceSpec);
        assertEquals(
                "public static final class Builder {\n" + 
                "    \n" + 
                "    public final DataBuilder_ready a(int a) {\n" + 
                "        return ()->{\n" + 
                "            return new Data(\n" + 
                "                a\n" + 
                "            );\n" + 
                "        };\n" + 
                "    }\n" + 
                "    \n" + 
                "    public static interface DataBuilder_ready {\n" + 
                "        \n" + 
                "        public Data build();\n" + 
                "        \n" + 
                "        \n" + 
                "        \n" + 
                "    }\n" + 
                "    \n" + 
                "    \n" + 
                "}",
                builder.build().toDefinition("pckg").toText());
        
    }
    
    @Test
    public void testOneOptional() {
        val getters = asList(
                new Getter("a", Type.INT, true, DefaultValue.MINUS_ONE)
        );
        val sourceSpec = new SourceSpec(
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
        val builder    = new BuilderGenerator(sourceSpec);
        assertEquals(
                "public static final class Builder {\n" + 
                "    \n" + 
                "    public final DataBuilder_ready a(int a) {\n" + 
                "        return ()->{\n" + 
                "            return new Data(\n" + 
                "                a\n" + 
                "            );\n" + 
                "        };\n" + 
                "    }\n" + 
                "    \n" + 
                "    public static interface DataBuilder_ready {\n" + 
                "        \n" + 
                "        public Data build();\n" + 
                "        \n" + 
                "        \n" + 
                "        \n" + 
                "    }\n" + 
                "    \n" + 
                "    \n" + 
                "    public Data build() {\n" + 
                "        return a(-1).build();\n" + 
                "    }\n" + 
                "    \n" + 
                "}",
                builder.build().toDefinition("pckg").toText());
        
    }
    
    @Test
    public void testNone() {
        List<Getter> getters = emptyList();
        val sourceSpec = new SourceSpec(
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
        val builder    = new BuilderGenerator(sourceSpec);
        assertEquals(
                "public static final class Builder implements DataBuilder_ready {\n" + 
                "    \n" + 
                "    public final Data build() {\n" + 
                "        return new Data();\n" + 
                "    }\n" + 
                "    \n" + 
                "}",
                builder.build().toDefinition("pckg").toText());
        
    }
    
}

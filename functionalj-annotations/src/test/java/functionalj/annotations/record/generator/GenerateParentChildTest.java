package functionalj.annotations.dataobject.generator;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import functionalj.annotations.dataobject.generator.SourceSpec.Configurations;
import functionalj.annotations.dataobject.generator.model.GenDataObject;
import lombok.val;

@SuppressWarnings("javadoc")
public class GenerateParentChildTest {
    
    private Configurations configures = new Configurations();
    {
        configures.coupleWithDefinition      = true;
        configures.generateNoArgConstructor  = true;
        configures.generateAllArgConstructor = true;
        configures.generateLensClass         = true;
    }
    
    private String  definitionClassName = "Definitions.ParentDef";
    private String  targetClassName     = "Parent";
    private String  packageName         = "me.test";
    private boolean isClass             = false;
    
    private List<Getter> getters = asList(
            new Getter("child", new Type.TypeBuilder()
                                .simpleName("Child")
                                .packageName("me.test")
                                .build())
    );
    
    @Test
    public void testParent() {
        val code = generate();
        assertEquals(
                "package me.test;\n" + 
                "\n" + 
                "import functionalj.annotations.IPostReConstruct;\n" + 
                "import functionalj.lens.core.LensSpec;\n" + 
                "import functionalj.lens.lenses.ObjectLensImpl;\n" + 
                "import java.lang.Object;\n" + 
                "import java.util.HashMap;\n" + 
                "import java.util.Map;\n" + 
                "import java.util.function.BiFunction;\n" + 
                "import java.util.function.Function;\n" + 
                "import java.util.function.Supplier;\n" + 
                "import me.test.Child;\n" + 
                "import me.test.Child.ChildLens;\n" + 
                "\n" + 
                "public class Parent implements Definitions.ParentDef {\n" + 
                "    \n" + 
                "    public static final ParentLens<Parent> theParent = new ParentLens<>(LensSpec.of(Parent.class));\n" + 
                "    private final Child child;\n" + 
                "    \n" + 
                "    public Parent() {\n" + 
                "        this(null);\n" + 
                "    }\n" + 
                "    public Parent(Child child) {\n" + 
                "        this.child = child;\n" + 
                "    }\n" + 
                "    \n" + 
                "    public Child child() {\n" + 
                "        return child;\n" + 
                "    }\n" + 
                "    public Parent withChild(Child child) {\n" + 
                "        return postReConstruct(new Parent(child));\n" + 
                "    }\n" + 
                "    public Parent withChild(Supplier<Child> child) {\n" + 
                "        return postReConstruct(new Parent(child.get()));\n" + 
                "    }\n" + 
                "    public Parent withChild(Function<Child, Child> child) {\n" + 
                "        return postReConstruct(new Parent(child.apply(this.child)));\n" + 
                "    }\n" + 
                "    public Parent withChild(BiFunction<Parent, Child, Child> child) {\n" + 
                "        return postReConstruct(new Parent(child.apply(this, this.child)));\n" + 
                "    }\n" + 
                "    private static Parent postReConstruct(Parent object) {\n" + 
                "        if (object instanceof IPostReConstruct)\n" + 
                "            ((IPostReConstruct)object).postReConstruct();\n" + 
                "        return object;\n" + 
                "    }\n" + 
                "    public static Parent fromMap(Map<String, Object> map) {\n" + 
                "        return new Parent(\n" + 
                "                    (Child)map.get(\"child\")\n" + 
                "                );\n" + 
                "    }\n" + 
                "    public Map<String, Object> toMap() {\n" + 
                "        java.util.Map<String, Object> map = new HashMap<>();\n" + 
                "        map.put(\"child\",  (Object)child);\n" + 
                "        return map;\n" + 
                "    }\n" + 
                "    public String toString() {\n" + 
                "        return \"Parent[\" + \"child: \" + child() + \"]\";\n" + 
                "    }\n" + 
                "    public int hashCode() {\n" + 
                "        return toString().hashCode();\n" + 
                "    }\n" + 
                "    public boolean equals(Object another) {\n" + 
                "        return (another == this) || ((another != null) && (getClass().equals(another.getClass())) && java.util.Objects.equals(toString(), another.toString()));\n" + 
                "    }\n" + 
                "    \n" + 
                "    public static class ParentLens<HOST> extends ObjectLensImpl<HOST, Parent> {\n" + 
                "        \n" + 
                "        public final ChildLens<HOST> child = createSubLens(Parent::child, Parent::withChild, ChildLens::new);\n" + 
                "        \n" + 
                "        public ParentLens(LensSpec<HOST, Parent> spec) {\n" + 
                "            super(spec);\n" + 
                "        }\n" + 
                "        \n" + 
                "    }\n" + 
                "    public static class Builder {\n" + 
                "        \n" + 
                "        public Builder_child child(Child child) {\n" + 
                "            return new Builder_child(child);\n" + 
                "        }\n" + 
                "        \n" + 
                "        public static class Builder_child {\n" + 
                "            \n" + 
                "            private final Child child;\n" + 
                "            \n" + 
                "            private Builder_child(Child child) {\n" + 
                "                this.child = child;\n" + 
                "            }\n" + 
                "            \n" + 
                "            public Child child() {\n" + 
                "                return child;\n" + 
                "            }\n" + 
                "            public Builder_child child(Child child) {\n" + 
                "                return new Builder_child(child);\n" + 
                "            }\n" + 
                "            public Parent build() {\n" + 
                "                return new Parent(child());\n" + 
                "            }\n" + 
                "            \n" + 
                "        }\n" + 
                "        \n" + 
                "    }\n" + 
                "    \n" + 
                "}", code);
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
                    targetClassName,     // targetClassName
                    packageName,         // targetPackageName
                    isClass,             // isClass
                    null,
                    configures,          // Configurations
                    getters);
        val dataObjSpec = new DataObjectBuilder(sourceSpec).build();
        val generated   = new GenDataObject(dataObjSpec).toText();
        return generated;
    }
    
}

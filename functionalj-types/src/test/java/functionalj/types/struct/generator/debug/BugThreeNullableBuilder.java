package functionalj.types.struct.generator.debug;

import static functionalj.types.DefaultValue.NULL;
import static functionalj.types.DefaultValue.REQUIRED;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import functionalj.types.struct.generator.Getter;
import functionalj.types.struct.generator.SourceSpec;
import functionalj.types.struct.generator.StructBuilder;
import functionalj.types.struct.generator.Type;
import functionalj.types.struct.generator.SourceSpec.Configurations;
import functionalj.types.struct.generator.model.GenStruct;
import lombok.val;

public class BugThreeNullableBuilder {
    
    @Test
    public void testParent() {
        val code = generate();
        assertEquals(
                "package ci.server;\n" + 
                "\n" + 
                "import ci.server.Brand.BrandLens;\n" + 
                "import functionalj.lens.core.LensSpec;\n" + 
                "import functionalj.lens.lenses.ObjectLensImpl;\n" + 
                "import functionalj.lens.lenses.StringLens;\n" + 
                "import functionalj.pipeable.Pipeable;\n" + 
                "import functionalj.types.IPostConstruct;\n" + 
                "import functionalj.types.IStruct;\n" + 
                "import functionalj.types.struct.generator.Getter;\n" + 
                "import functionalj.types.struct.generator.Type;\n" + 
                "import java.lang.Exception;\n" + 
                "import java.lang.Object;\n" + 
                "import java.util.HashMap;\n" + 
                "import java.util.Map;\n" + 
                "import java.util.function.BiFunction;\n" + 
                "import java.util.function.Function;\n" + 
                "import java.util.function.Supplier;\n" + 
                "\n" + 
                "// ci.server.DataModels.DataModels.BrandSpec\n" + 
                "\n" + 
                "public class Brand implements DataModels.BrandSpec,IStruct,Pipeable<Brand> {\n" + 
                "    \n" + 
                "    public static final Brand.BrandLens<Brand> theBrand = new Brand.BrandLens<>(LensSpec.of(Brand.class));\n" + 
                "    public final String id;\n" + 
                "    public final String name;\n" + 
                "    public final String owner;\n" + 
                "    public final String website;\n" + 
                "    public final String country;\n" + 
                "    public final String description;\n" + 
                "    \n" + 
                "    public Brand(String id, String name) {\n" + 
                "        this.id = $utils.notNull(id);\n" + 
                "        this.name = $utils.notNull(name);\n" + 
                "        this.owner = null;\n" + 
                "        this.website = null;\n" + 
                "        this.country = null;\n" + 
                "        this.description = null;\n" + 
                "        if (this instanceof IPostConstruct) ((IPostConstruct)this).postConstruct();\n" + 
                "    }\n" + 
                "    public Brand(String id, String name, String owner, String website, String country, String description) {\n" + 
                "        this.id = $utils.notNull(id);\n" + 
                "        this.name = $utils.notNull(name);\n" + 
                "        this.owner = java.util.Optional.ofNullable(owner).orElseGet(()->null);\n" + 
                "        this.website = java.util.Optional.ofNullable(website).orElseGet(()->null);\n" + 
                "        this.country = java.util.Optional.ofNullable(country).orElseGet(()->null);\n" + 
                "        this.description = java.util.Optional.ofNullable(description).orElseGet(()->null);\n" + 
                "        if (this instanceof IPostConstruct) ((IPostConstruct)this).postConstruct();\n" + 
                "    }\n" + 
                "    \n" + 
                "    public Brand __data() throws Exception  {\n" + 
                "        return this;\n" + 
                "    }\n" + 
                "    public String id() {\n" + 
                "        return id;\n" + 
                "    }\n" + 
                "    public String name() {\n" + 
                "        return name;\n" + 
                "    }\n" + 
                "    public String owner() {\n" + 
                "        return owner;\n" + 
                "    }\n" + 
                "    public String website() {\n" + 
                "        return website;\n" + 
                "    }\n" + 
                "    public String country() {\n" + 
                "        return country;\n" + 
                "    }\n" + 
                "    public String description() {\n" + 
                "        return description;\n" + 
                "    }\n" + 
                "    public Brand withId(String id) {\n" + 
                "        return new Brand(id, name, owner, website, country, description);\n" + 
                "    }\n" + 
                "    public Brand withId(Supplier<String> id) {\n" + 
                "        return new Brand(id.get(), name, owner, website, country, description);\n" + 
                "    }\n" + 
                "    public Brand withId(Function<String, String> id) {\n" + 
                "        return new Brand(id.apply(this.id), name, owner, website, country, description);\n" + 
                "    }\n" + 
                "    public Brand withId(BiFunction<Brand, String, String> id) {\n" + 
                "        return new Brand(id.apply(this, this.id), name, owner, website, country, description);\n" + 
                "    }\n" + 
                "    public Brand withName(String name) {\n" + 
                "        return new Brand(id, name, owner, website, country, description);\n" + 
                "    }\n" + 
                "    public Brand withName(Supplier<String> name) {\n" + 
                "        return new Brand(id, name.get(), owner, website, country, description);\n" + 
                "    }\n" + 
                "    public Brand withName(Function<String, String> name) {\n" + 
                "        return new Brand(id, name.apply(this.name), owner, website, country, description);\n" + 
                "    }\n" + 
                "    public Brand withName(BiFunction<Brand, String, String> name) {\n" + 
                "        return new Brand(id, name.apply(this, this.name), owner, website, country, description);\n" + 
                "    }\n" + 
                "    public Brand withOwner(String owner) {\n" + 
                "        return new Brand(id, name, owner, website, country, description);\n" + 
                "    }\n" + 
                "    public Brand withOwner(Supplier<String> owner) {\n" + 
                "        return new Brand(id, name, owner.get(), website, country, description);\n" + 
                "    }\n" + 
                "    public Brand withOwner(Function<String, String> owner) {\n" + 
                "        return new Brand(id, name, owner.apply(this.owner), website, country, description);\n" + 
                "    }\n" + 
                "    public Brand withOwner(BiFunction<Brand, String, String> owner) {\n" + 
                "        return new Brand(id, name, owner.apply(this, this.owner), website, country, description);\n" + 
                "    }\n" + 
                "    public Brand withWebsite(String website) {\n" + 
                "        return new Brand(id, name, owner, website, country, description);\n" + 
                "    }\n" + 
                "    public Brand withWebsite(Supplier<String> website) {\n" + 
                "        return new Brand(id, name, owner, website.get(), country, description);\n" + 
                "    }\n" + 
                "    public Brand withWebsite(Function<String, String> website) {\n" + 
                "        return new Brand(id, name, owner, website.apply(this.website), country, description);\n" + 
                "    }\n" + 
                "    public Brand withWebsite(BiFunction<Brand, String, String> website) {\n" + 
                "        return new Brand(id, name, owner, website.apply(this, this.website), country, description);\n" + 
                "    }\n" + 
                "    public Brand withCountry(String country) {\n" + 
                "        return new Brand(id, name, owner, website, country, description);\n" + 
                "    }\n" + 
                "    public Brand withCountry(Supplier<String> country) {\n" + 
                "        return new Brand(id, name, owner, website, country.get(), description);\n" + 
                "    }\n" + 
                "    public Brand withCountry(Function<String, String> country) {\n" + 
                "        return new Brand(id, name, owner, website, country.apply(this.country), description);\n" + 
                "    }\n" + 
                "    public Brand withCountry(BiFunction<Brand, String, String> country) {\n" + 
                "        return new Brand(id, name, owner, website, country.apply(this, this.country), description);\n" + 
                "    }\n" + 
                "    public Brand withDescription(String description) {\n" + 
                "        return new Brand(id, name, owner, website, country, description);\n" + 
                "    }\n" + 
                "    public Brand withDescription(Supplier<String> description) {\n" + 
                "        return new Brand(id, name, owner, website, country, description.get());\n" + 
                "    }\n" + 
                "    public Brand withDescription(Function<String, String> description) {\n" + 
                "        return new Brand(id, name, owner, website, country, description.apply(this.description));\n" + 
                "    }\n" + 
                "    public Brand withDescription(BiFunction<Brand, String, String> description) {\n" + 
                "        return new Brand(id, name, owner, website, country, description.apply(this, this.description));\n" + 
                "    }\n" + 
                "    public static Brand fromMap(Map<String, Object> map) {\n" + 
                "        Map<String, Getter> $schema = getStructSchema();\n" + 
                "        \n" + 
                "        Brand obj = new Brand(\n" + 
                "                    (String)$utils.fromMapValue(map.get(\"id\"), $schema.get(\"id\")),\n" + 
                "                    (String)$utils.fromMapValue(map.get(\"name\"), $schema.get(\"name\")),\n" + 
                "                    (String)$utils.fromMapValue(map.get(\"owner\"), $schema.get(\"owner\")),\n" + 
                "                    (String)$utils.fromMapValue(map.get(\"website\"), $schema.get(\"website\")),\n" + 
                "                    (String)$utils.fromMapValue(map.get(\"country\"), $schema.get(\"country\")),\n" + 
                "                    (String)$utils.fromMapValue(map.get(\"description\"), $schema.get(\"description\"))\n" + 
                "                );\n" + 
                "        return obj;\n" + 
                "    }\n" + 
                "    public Map<String, Object> __toMap() {\n" + 
                "        Map<String, Object> map = new HashMap<>();\n" + 
                "        map.put(\"id\", functionalj.types.IStruct.$utils.toMapValueObject(id));\n" + 
                "        map.put(\"name\", functionalj.types.IStruct.$utils.toMapValueObject(name));\n" + 
                "        map.put(\"owner\", functionalj.types.IStruct.$utils.toMapValueObject(owner));\n" + 
                "        map.put(\"website\", functionalj.types.IStruct.$utils.toMapValueObject(website));\n" + 
                "        map.put(\"country\", functionalj.types.IStruct.$utils.toMapValueObject(country));\n" + 
                "        map.put(\"description\", functionalj.types.IStruct.$utils.toMapValueObject(description));\n" + 
                "        return map;\n" + 
                "    }\n" + 
                "    public Map<String, Getter> __getSchema() {\n" + 
                "        return getStructSchema();\n" + 
                "    }\n" + 
                "    public static Map<String, Getter> getStructSchema() {\n" + 
                "        Map<String, Getter> map = new HashMap<>();\n" + 
                "        map.put(\"id\", new functionalj.types.struct.generator.Getter(\"id\", new Type(null, \"String\", \"java.lang\", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED));\n" + 
                "        map.put(\"name\", new functionalj.types.struct.generator.Getter(\"name\", new Type(null, \"String\", \"java.lang\", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED));\n" + 
                "        map.put(\"owner\", new functionalj.types.struct.generator.Getter(\"owner\", new Type(null, \"String\", \"java.lang\", java.util.Collections.emptyList()), true, functionalj.types.DefaultValue.NULL));\n" + 
                "        map.put(\"website\", new functionalj.types.struct.generator.Getter(\"website\", new Type(null, \"String\", \"java.lang\", java.util.Collections.emptyList()), true, functionalj.types.DefaultValue.NULL));\n" + 
                "        map.put(\"country\", new functionalj.types.struct.generator.Getter(\"country\", new Type(null, \"String\", \"java.lang\", java.util.Collections.emptyList()), true, functionalj.types.DefaultValue.NULL));\n" + 
                "        map.put(\"description\", new functionalj.types.struct.generator.Getter(\"description\", new Type(null, \"String\", \"java.lang\", java.util.Collections.emptyList()), true, functionalj.types.DefaultValue.NULL));\n" + 
                "        return map;\n" + 
                "    }\n" + 
                "    public String toString() {\n" + 
                "        return \"Brand[\" + \"id: \" + id() + \", \" + \"name: \" + name() + \", \" + \"owner: \" + owner() + \", \" + \"website: \" + website() + \", \" + \"country: \" + country() + \", \" + \"description: \" + description() + \"]\";\n" + 
                "    }\n" + 
                "    public int hashCode() {\n" + 
                "        return toString().hashCode();\n" + 
                "    }\n" + 
                "    public boolean equals(Object another) {\n" + 
                "        return (another == this) || ((another != null) && (getClass().equals(another.getClass())) && java.util.Objects.equals(toString(), another.toString()));\n" + 
                "    }\n" + 
                "    \n" + 
                "    public static class BrandLens<HOST> extends ObjectLensImpl<HOST, Brand> {\n" + 
                "        \n" + 
                "        public final StringLens<HOST> id = createSubLens(Brand::id, Brand::withId, StringLens::of);\n" + 
                "        public final StringLens<HOST> name = createSubLens(Brand::name, Brand::withName, StringLens::of);\n" + 
                "        public final StringLens<HOST> owner = createSubLens(Brand::owner, Brand::withOwner, StringLens::of);\n" + 
                "        public final StringLens<HOST> website = createSubLens(Brand::website, Brand::withWebsite, StringLens::of);\n" + 
                "        public final StringLens<HOST> country = createSubLens(Brand::country, Brand::withCountry, StringLens::of);\n" + 
                "        public final StringLens<HOST> description = createSubLens(Brand::description, Brand::withDescription, StringLens::of);\n" + 
                "        \n" + 
                "        public BrandLens(LensSpec<HOST, Brand> spec) {\n" + 
                "            super(spec);\n" + 
                "        }\n" + 
                "        \n" + 
                "    }\n" + 
                "    public static final class Builder {\n" + 
                "        \n" + 
                "        public final BrandBuilder_withoutName id(String id) {\n" + 
                "            return (String name)->{\n" + 
                "            return (String owner)->{\n" + 
                "            return (String website)->{\n" + 
                "            return (String country)->{\n" + 
                "            return (String description)->{\n" + 
                "            return ()->{\n" + 
                "                return new Brand(\n" + 
                "                    id,\n" + 
                "                    name,\n" + 
                "                    owner,\n" + 
                "                    website,\n" + 
                "                    country,\n" + 
                "                    description\n" + 
                "                );\n" + 
                "            };\n" + 
                "            };\n" + 
                "            };\n" + 
                "            };\n" + 
                "            };\n" + 
                "            };\n" + 
                "        }\n" + 
                "        \n" + 
                "        public static interface BrandBuilder_withoutName {\n" + 
                "            \n" + 
                "            public BrandBuilder_withoutOwner name(String name);\n" + 
                "            \n" + 
                "        }\n" + 
                "        public static interface BrandBuilder_withoutOwner {\n" + 
                "            \n" + 
                "            public BrandBuilder_withoutWebsite owner(String owner);\n" + 
                "            \n" + 
                "            public default BrandBuilder_withoutCountry website(String website){\n" + 
                "                return owner(null).website(website);\n" + 
                "            }\n" + 
                "            public default BrandBuilder_withoutDescription country(String country){\n" + 
                "                return owner(null).website(null).country(country);\n" + 
                "            }\n" + 
                "            public default BrandBuilder_ready description(String description){\n" + 
                "                return owner(null).website(null).country(null).description(description);\n" + 
                "            }\n" + 
                "            public default Brand build() {\n" + 
                "                return owner(null).website(null).country(null).description(null).build();\n" + 
                "            }\n" + 
                "            \n" + 
                "        }\n" + 
                "        public static interface BrandBuilder_withoutWebsite {\n" + 
                "            \n" + 
                "            public BrandBuilder_withoutCountry website(String website);\n" + 
                "            \n" + 
                "            public default BrandBuilder_withoutDescription country(String country){\n" + 
                "                return website(null).country(country);\n" + 
                "            }\n" + 
                "            public default BrandBuilder_ready description(String description){\n" + 
                "                return website(null).country(null).description(description);\n" + 
                "            }\n" + 
                "            public default Brand build() {\n" + 
                "                return website(null).country(null).description(null).build();\n" + 
                "            }\n" + 
                "            \n" + 
                "        }\n" + 
                "        public static interface BrandBuilder_withoutCountry {\n" + 
                "            \n" + 
                "            public BrandBuilder_withoutDescription country(String country);\n" + 
                "            \n" + 
                "            public default BrandBuilder_ready description(String description){\n" + 
                "                return country(null).description(description);\n" + 
                "            }\n" + 
                "            public default Brand build() {\n" + 
                "                return country(null).description(null).build();\n" + 
                "            }\n" + 
                "            \n" + 
                "        }\n" + 
                "        public static interface BrandBuilder_withoutDescription {\n" + 
                "            \n" + 
                "            public BrandBuilder_ready description(String description);\n" + 
                "            \n" + 
                "            public default Brand build() {\n" + 
                "                return description(null).build();\n" + 
                "            }\n" + 
                "            \n" + 
                "        }\n" + 
                "        public static interface BrandBuilder_ready {\n" + 
                "            \n" + 
                "            public Brand build();\n" + 
                "            \n" + 
                "            \n" + 
                "            \n" + 
                "        }\n" + 
                "        \n" + 
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
                "DataModels.BrandSpec", // specClassName
                "ci.server",            // packageName
                "DataModels",           // encloseName
                "Brand",                // targetClassName
                "ci.server",            // targetPackageName
                false,                  // isClass
                null,
                null,
                new Configurations(true, false, true, true, true, true, true, ""),  // Configurations
                asList(
                    new Getter("id",          new Type(null, "String", "java.lang", emptyList()), false, REQUIRED),
                    new Getter("name",        new Type(null, "String", "java.lang", emptyList()), false, REQUIRED),
                    new Getter("owner",       new Type(null, "String", "java.lang", emptyList()), true,  NULL),
                    new Getter("website",     new Type(null, "String", "java.lang", emptyList()), true,  NULL),
                    new Getter("country",     new Type(null, "String", "java.lang", emptyList()), true,  NULL),
                    new Getter("description", new Type(null, "String", "java.lang", emptyList()), true,  NULL)),
                asList("Brand", "Product"));
        val dataObjSpec = new StructBuilder(sourceSpec).build();
        val generated   = new GenStruct(sourceSpec, dataObjSpec).toText();
        return generated;
    }
    
}

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
package functionalj.types.struct.generator.debug;

import static functionalj.types.DefaultValue.NULL;
import static functionalj.types.DefaultValue.REQUIRED;
import static functionalj.types.StructToString.Legacy;
import static functionalj.types.TestHelper.assertAsString;
import static functionalj.types.struct.SourceKind.INTERFACE;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

import org.junit.Test;

import functionalj.types.JavaVersionInfo;
import functionalj.types.Serialize;
import functionalj.types.Type;
import functionalj.types.struct.generator.Getter;
import functionalj.types.struct.generator.SourceSpec;
import functionalj.types.struct.generator.SourceSpec.Configurations;
import functionalj.types.struct.generator.StructClassSpecBuilder;
import functionalj.types.struct.generator.model.GenStruct;
import lombok.val;

public class BugThreeNullableBuilder {
    
    @Test
    public void testParent() {
        val code = generate();
        assertAsString("package ci.server;\n"
              + "\n"
              + "import functionalj.lens.core.LensSpec;\n"
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
              + "@Generated(value = \"FunctionalJ\", date = \"\\E[^\"]+\\Q\", comments = \"ci.server.DataModels.DataModels.BrandSpec\")\n"
              + "@SuppressWarnings(\"all\")\n"
              + "\n"
              + "public class Brand implements DataModels.BrandSpec,IStruct,Pipeable<Brand> {\n"
              + "    \n"
              + "    public static final Brand.BrandLens<Brand> theBrand = new Brand.BrandLens<>(\"theBrand\", LensSpec.of(Brand.class));\n"
              + "    public static final Brand.BrandLens<Brand> eachBrand = theBrand;\n"
              + "    public final String id;\n"
              + "    public final String name;\n"
              + "    public final String owner;\n"
              + "    public final String website;\n"
              + "    public final String country;\n"
              + "    public final String description;\n"
              + "    \n"
              + "    public Brand(String id, String name) {\n"
              + "        this($utils.notNull(id), $utils.notNull(name), null, null, null, null);\n"
              + "        if (IPostConstruct.class.isInstance(this)) IPostConstruct.class.cast(this).postConstruct();\n"
              + "    }\n"
              + "    public Brand(String id, String name, String owner, String website, String country, String description) {\n"
              + "        this.id = $utils.notNull(id);\n"
              + "        this.name = $utils.notNull(name);\n"
              + "        this.owner = java.util.Optional.ofNullable(owner).orElseGet(()->null);\n"
              + "        this.website = java.util.Optional.ofNullable(website).orElseGet(()->null);\n"
              + "        this.country = java.util.Optional.ofNullable(country).orElseGet(()->null);\n"
              + "        this.description = java.util.Optional.ofNullable(description).orElseGet(()->null);\n"
              + "        if (IPostConstruct.class.isInstance(this)) IPostConstruct.class.cast(this).postConstruct();\n"
              + "    }\n"
              + "    \n"
              + "    public Brand __data() throws Exception  {\n"
              + "        return this;\n"
              + "    }\n"
              + "    public String id() {\n"
              + "        return id;\n"
              + "    }\n"
              + "    public String name() {\n"
              + "        return name;\n"
              + "    }\n"
              + "    public String owner() {\n"
              + "        return owner;\n"
              + "    }\n"
              + "    public String website() {\n"
              + "        return website;\n"
              + "    }\n"
              + "    public String country() {\n"
              + "        return country;\n"
              + "    }\n"
              + "    public String description() {\n"
              + "        return description;\n"
              + "    }\n"
              + "    public Brand withId(String id) {\n"
              + "        return new Brand(id, name, owner, website, country, description);\n"
              + "    }\n"
              + "    public Brand withId(Supplier<String> id) {\n"
              + "        return new Brand(id.get(), name, owner, website, country, description);\n"
              + "    }\n"
              + "    public Brand withId(Function<String, String> id) {\n"
              + "        return new Brand(id.apply(this.id), name, owner, website, country, description);\n"
              + "    }\n"
              + "    public Brand withId(BiFunction<Brand, String, String> id) {\n"
              + "        return new Brand(id.apply(this, this.id), name, owner, website, country, description);\n"
              + "    }\n"
              + "    public Brand withName(String name) {\n"
              + "        return new Brand(id, name, owner, website, country, description);\n"
              + "    }\n"
              + "    public Brand withName(Supplier<String> name) {\n"
              + "        return new Brand(id, name.get(), owner, website, country, description);\n"
              + "    }\n"
              + "    public Brand withName(Function<String, String> name) {\n"
              + "        return new Brand(id, name.apply(this.name), owner, website, country, description);\n"
              + "    }\n"
              + "    public Brand withName(BiFunction<Brand, String, String> name) {\n"
              + "        return new Brand(id, name.apply(this, this.name), owner, website, country, description);\n"
              + "    }\n"
              + "    public Brand withOwner(String owner) {\n"
              + "        return new Brand(id, name, owner, website, country, description);\n"
              + "    }\n"
              + "    public Brand withOwner(Supplier<String> owner) {\n"
              + "        return new Brand(id, name, owner.get(), website, country, description);\n"
              + "    }\n"
              + "    public Brand withOwner(Function<String, String> owner) {\n"
              + "        return new Brand(id, name, owner.apply(this.owner), website, country, description);\n"
              + "    }\n"
              + "    public Brand withOwner(BiFunction<Brand, String, String> owner) {\n"
              + "        return new Brand(id, name, owner.apply(this, this.owner), website, country, description);\n"
              + "    }\n"
              + "    public Brand withWebsite(String website) {\n"
              + "        return new Brand(id, name, owner, website, country, description);\n"
              + "    }\n"
              + "    public Brand withWebsite(Supplier<String> website) {\n"
              + "        return new Brand(id, name, owner, website.get(), country, description);\n"
              + "    }\n"
              + "    public Brand withWebsite(Function<String, String> website) {\n"
              + "        return new Brand(id, name, owner, website.apply(this.website), country, description);\n"
              + "    }\n"
              + "    public Brand withWebsite(BiFunction<Brand, String, String> website) {\n"
              + "        return new Brand(id, name, owner, website.apply(this, this.website), country, description);\n"
              + "    }\n"
              + "    public Brand withCountry(String country) {\n"
              + "        return new Brand(id, name, owner, website, country, description);\n"
              + "    }\n"
              + "    public Brand withCountry(Supplier<String> country) {\n"
              + "        return new Brand(id, name, owner, website, country.get(), description);\n"
              + "    }\n"
              + "    public Brand withCountry(Function<String, String> country) {\n"
              + "        return new Brand(id, name, owner, website, country.apply(this.country), description);\n"
              + "    }\n"
              + "    public Brand withCountry(BiFunction<Brand, String, String> country) {\n"
              + "        return new Brand(id, name, owner, website, country.apply(this, this.country), description);\n"
              + "    }\n"
              + "    public Brand withDescription(String description) {\n"
              + "        return new Brand(id, name, owner, website, country, description);\n"
              + "    }\n"
              + "    public Brand withDescription(Supplier<String> description) {\n"
              + "        return new Brand(id, name, owner, website, country, description.get());\n"
              + "    }\n"
              + "    public Brand withDescription(Function<String, String> description) {\n"
              + "        return new Brand(id, name, owner, website, country, description.apply(this.description));\n"
              + "    }\n"
              + "    public Brand withDescription(BiFunction<Brand, String, String> description) {\n"
              + "        return new Brand(id, name, owner, website, country, description.apply(this, this.description));\n"
              + "    }\n"
              + "    public static Brand fromMap(Map<String, ? extends Object> map) {\n"
              + "        Map<String, Getter> $schema = getStructSchema();\n"
              + "        Brand obj = new Brand(\n"
              + "                    (String)$utils.extractPropertyFromMap(Brand.class, String.class, map, $schema, \"id\"),\n"
              + "                    (String)$utils.extractPropertyFromMap(Brand.class, String.class, map, $schema, \"name\"),\n"
              + "                    (String)$utils.extractPropertyFromMap(Brand.class, String.class, map, $schema, \"owner\"),\n"
              + "                    (String)$utils.extractPropertyFromMap(Brand.class, String.class, map, $schema, \"website\"),\n"
              + "                    (String)$utils.extractPropertyFromMap(Brand.class, String.class, map, $schema, \"country\"),\n"
              + "                    (String)$utils.extractPropertyFromMap(Brand.class, String.class, map, $schema, \"description\")\n"
              + "                );\n"
              + "        return obj;\n"
              + "    }\n"
              + "    public Map<String, Object> __toMap() {\n"
              + "        Map<String, Object> map = new HashMap<>();\n"
              + "        map.put(\"id\", $utils.toMapValueObject(id));\n"
              + "        map.put(\"name\", $utils.toMapValueObject(name));\n"
              + "        map.put(\"owner\", $utils.toMapValueObject(owner));\n"
              + "        map.put(\"website\", $utils.toMapValueObject(website));\n"
              + "        map.put(\"country\", $utils.toMapValueObject(country));\n"
              + "        map.put(\"description\", $utils.toMapValueObject(description));\n"
              + "        return map;\n"
              + "    }\n"
              + "    public Map<String, Getter> __getSchema() {\n"
              + "        return getStructSchema();\n"
              + "    }\n"
              + "    public static Map<String, Getter> getStructSchema() {\n"
              + "        java.util.Map<String, functionalj.types.struct.generator.Getter> map = new java.util.HashMap<>();\n"
              + "        map.put(\"id\", new functionalj.types.struct.generator.Getter(\"id\", new functionalj.types.Type(\"java.lang\", null, \"String\", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED));\n"
              + "        map.put(\"name\", new functionalj.types.struct.generator.Getter(\"name\", new functionalj.types.Type(\"java.lang\", null, \"String\", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED));\n"
              + "        map.put(\"owner\", new functionalj.types.struct.generator.Getter(\"owner\", new functionalj.types.Type(\"java.lang\", null, \"String\", java.util.Collections.emptyList()), true, functionalj.types.DefaultValue.NULL));\n"
              + "        map.put(\"website\", new functionalj.types.struct.generator.Getter(\"website\", new functionalj.types.Type(\"java.lang\", null, \"String\", java.util.Collections.emptyList()), true, functionalj.types.DefaultValue.NULL));\n"
              + "        map.put(\"country\", new functionalj.types.struct.generator.Getter(\"country\", new functionalj.types.Type(\"java.lang\", null, \"String\", java.util.Collections.emptyList()), true, functionalj.types.DefaultValue.NULL));\n"
              + "        map.put(\"description\", new functionalj.types.struct.generator.Getter(\"description\", new functionalj.types.Type(\"java.lang\", null, \"String\", java.util.Collections.emptyList()), true, functionalj.types.DefaultValue.NULL));\n"
              + "        return map;\n"
              + "    }\n"
              + "    public String toString() {\n"
              + "        return \"Brand[\" + \"id: \" + id() + \", \" + \"name: \" + name() + \", \" + \"owner: \" + owner() + \", \" + \"website: \" + website() + \", \" + \"country: \" + country() + \", \" + \"description: \" + description() + \"]\";\n"
              + "    }\n"
              + "    public int hashCode() {\n"
              + "        return toString().hashCode();\n"
              + "    }\n"
              + "    public boolean equals(Object another) {\n"
              + "        return (another == this) || ((another != null) && (getClass().equals(another.getClass())) && java.util.Objects.equals(toString(), another.toString()));\n"
              + "    }\n"
              + "    \n"
              + "    public static class BrandLens<HOST> extends ObjectLensImpl<HOST, Brand> {\n"
              + "        \n"
              + "        public final StringLens<HOST> id = createSubLens(\"id\", Brand::id, Brand::withId, StringLens::of);\n"
              + "        public final StringLens<HOST> name = createSubLens(\"name\", Brand::name, Brand::withName, StringLens::of);\n"
              + "        public final StringLens<HOST> owner = createSubLens(\"owner\", Brand::owner, Brand::withOwner, StringLens::of);\n"
              + "        public final StringLens<HOST> website = createSubLens(\"website\", Brand::website, Brand::withWebsite, StringLens::of);\n"
              + "        public final StringLens<HOST> country = createSubLens(\"country\", Brand::country, Brand::withCountry, StringLens::of);\n"
              + "        public final StringLens<HOST> description = createSubLens(\"description\", Brand::description, Brand::withDescription, StringLens::of);\n"
              + "        \n"
              + "        public BrandLens(String name, LensSpec<HOST, Brand> spec) {\n"
              + "            super(name, spec);\n"
              + "        }\n"
              + "        \n"
              + "    }\n"
              + "    public static final class Builder {\n"
              + "        \n"
              + "        public final BrandBuilder_withoutName id(String id) {\n"
              + "            return (String name)->{\n"
              + "            return (String owner)->{\n"
              + "            return (String website)->{\n"
              + "            return (String country)->{\n"
              + "            return (String description)->{\n"
              + "            return ()->{\n"
              + "                return new Brand(\n"
              + "                    id,\n"
              + "                    name,\n"
              + "                    owner,\n"
              + "                    website,\n"
              + "                    country,\n"
              + "                    description\n"
              + "                );\n"
              + "            };\n"
              + "            };\n"
              + "            };\n"
              + "            };\n"
              + "            };\n"
              + "            };\n"
              + "        }\n"
              + "        \n"
              + "        public static interface BrandBuilder_withoutName {\n"
              + "            \n"
              + "            public BrandBuilder_withoutOwner name(String name);\n"
              + "            \n"
              + "        }\n"
              + "        public static interface BrandBuilder_withoutOwner {\n"
              + "            \n"
              + "            public BrandBuilder_withoutWebsite owner(String owner);\n"
              + "            \n"
              + "            public default BrandBuilder_withoutCountry website(String website){\n"
              + "                return owner(null).website(website);\n"
              + "            }\n"
              + "            public default BrandBuilder_withoutDescription country(String country){\n"
              + "                return owner(null).website(null).country(country);\n"
              + "            }\n"
              + "            public default BrandBuilder_ready description(String description){\n"
              + "                return owner(null).website(null).country(null).description(description);\n"
              + "            }\n"
              + "            public default Brand build() {\n"
              + "                return owner(null).website(null).country(null).description(null).build();\n"
              + "            }\n"
              + "            \n"
              + "        }\n"
              + "        public static interface BrandBuilder_withoutWebsite {\n"
              + "            \n"
              + "            public BrandBuilder_withoutCountry website(String website);\n"
              + "            \n"
              + "            public default BrandBuilder_withoutDescription country(String country){\n"
              + "                return website(null).country(country);\n"
              + "            }\n"
              + "            public default BrandBuilder_ready description(String description){\n"
              + "                return website(null).country(null).description(description);\n"
              + "            }\n"
              + "            public default Brand build() {\n"
              + "                return website(null).country(null).description(null).build();\n"
              + "            }\n"
              + "            \n"
              + "        }\n"
              + "        public static interface BrandBuilder_withoutCountry {\n"
              + "            \n"
              + "            public BrandBuilder_withoutDescription country(String country);\n"
              + "            \n"
              + "            public default BrandBuilder_ready description(String description){\n"
              + "                return country(null).description(description);\n"
              + "            }\n"
              + "            public default Brand build() {\n"
              + "                return country(null).description(null).build();\n"
              + "            }\n"
              + "            \n"
              + "        }\n"
              + "        public static interface BrandBuilder_withoutDescription {\n"
              + "            \n"
              + "            public BrandBuilder_ready description(String description);\n"
              + "            \n"
              + "            public default Brand build() {\n"
              + "                return description(null).build();\n"
              + "            }\n"
              + "            \n"
              + "        }\n"
              + "        public static interface BrandBuilder_ready {\n"
              + "            \n"
              + "            public Brand build();\n"
              + "            \n"
              + "            \n"
              + "            \n"
              + "        }\n"
              + "        \n"
              + "        \n"
              + "    }\n"
              + "    \n"
              + "}",
              code);
    }
    
    private String generate() {
        return generate(null);
    }
    
    private String generate(Runnable setting) {
        if (setting != null)
            setting.run();
        
        JavaVersionInfo javaVersionInfo = new JavaVersionInfo(8, 8);
        SourceSpec sourceSpec = new SourceSpec(
                javaVersionInfo,
                "DataModels.BrandSpec", // specClassName
                "ci.server",            // packageName
                "DataModels",           // encloseName
                "Brand",                // targetClassName
                "ci.server",            // targetPackageName
                INTERFACE,              // isInterface
                null, 
                null,
                new Configurations(true, null, false, true, true, true, true, true, true, Serialize.To.NOTHING, Legacy, ""),
                asList(
                    new Getter("id",          new Type("java.lang", null, "String", emptyList()), false, REQUIRED),
                    new Getter("name",        new Type("java.lang", null, "String", emptyList()), false, REQUIRED),
                    new Getter("owner",       new Type("java.lang", null, "String", emptyList()), true,  NULL),
                    new Getter("website",     new Type("java.lang", null, "String", emptyList()), true,  NULL),
                    new Getter("country",     new Type("java.lang", null, "String", emptyList()), true,  NULL),
                    new Getter("description", new Type("java.lang", null, "String", emptyList()), true,  NULL)
                ),
                emptyList(), 
                asList("Brand", "Product")
        );
        val dataObjSpec = new StructClassSpecBuilder(sourceSpec).build();
        val generated = new GenStruct(sourceSpec, dataObjSpec).toText();
        return generated;
    }
}

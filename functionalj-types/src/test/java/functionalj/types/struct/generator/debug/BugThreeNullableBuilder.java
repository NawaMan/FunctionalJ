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
                "    public Brand __data()  throws Exception  {\n" + 
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
                "                    (String)IStruct.fromMapValue(map.get(\"id\"), $schema.get(\"id\")),\n" + 
                "                    (String)IStruct.fromMapValue(map.get(\"name\"), $schema.get(\"name\")),\n" + 
                "                    (String)IStruct.fromMapValue(map.get(\"owner\"), $schema.get(\"owner\")),\n" + 
                "                    (String)IStruct.fromMapValue(map.get(\"website\"), $schema.get(\"website\")),\n" + 
                "                    (String)IStruct.fromMapValue(map.get(\"country\"), $schema.get(\"country\")),\n" + 
                "                    (String)IStruct.fromMapValue(map.get(\"description\"), $schema.get(\"description\"))\n" + 
                "                );\n" + 
                "        return obj;\n" + 
                "    }\n" + 
                "    public Map<String, Object> toMap() {\n" + 
                "        Map<String, Object> map = new HashMap<>();\n" + 
                "        map.put(\"id\", IStruct.toMapValueObject(id));\n" + 
                "        map.put(\"name\", IStruct.toMapValueObject(name));\n" + 
                "        map.put(\"owner\", IStruct.toMapValueObject(owner));\n" + 
                "        map.put(\"website\", IStruct.toMapValueObject(website));\n" + 
                "        map.put(\"country\", IStruct.toMapValueObject(country));\n" + 
                "        map.put(\"description\", IStruct.toMapValueObject(description));\n" + 
                "        return map;\n" + 
                "    }\n" + 
                "    public Map<String, Getter> getSchema() {\n" + 
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
                "    public static class Builder {\n" + 
                "        \n" + 
                "        public Builder_id id(String id) {\n" + 
                "            return new Builder_id(id);\n" + 
                "        }\n" + 
                "        \n" + 
                "        public static class Builder_id {\n" + 
                "            \n" + 
                "            private final String id;\n" + 
                "            \n" + 
                "            private Builder_id(String id) {\n" + 
                "                this.id = $utils.notNull(id);\n" + 
                "            }\n" + 
                "            \n" + 
                "            public String id() {\n" + 
                "                return id;\n" + 
                "            }\n" + 
                "            public Builder_id id(String id) {\n" + 
                "                return new Builder_id(id);\n" + 
                "            }\n" + 
                "            public Builder_id_name name(String name) {\n" + 
                "                return new Builder_id_name(this, name);\n" + 
                "            }\n" + 
                "            \n" + 
                "        }\n" + 
                "        public static class Builder_id_name {\n" + 
                "            \n" + 
                "            private final Builder_id parent;\n" + 
                "            private final String name;\n" + 
                "            \n" + 
                "            private Builder_id_name(Builder_id parent, String name) {\n" + 
                "                this.parent = parent;\n" + 
                "                this.name = $utils.notNull(name);\n" + 
                "            }\n" + 
                "            \n" + 
                "            public String id() {\n" + 
                "                return parent.id();\n" + 
                "            }\n" + 
                "            public String name() {\n" + 
                "                return name;\n" + 
                "            }\n" + 
                "            public Builder_id_name id(String id) {\n" + 
                "                return parent.id(id).name(name);\n" + 
                "            }\n" + 
                "            public Builder_id_name name(String name) {\n" + 
                "                return parent.name(name);\n" + 
                "            }\n" + 
                "            public Builder_id_name_owner owner(String owner) {\n" + 
                "                return new Builder_id_name_owner(this, owner);\n" + 
                "            }\n" + 
                "            public Builder_id_name_owner_website website(String website) {\n" + 
                "                return owner(null).website(website);\n" + 
                "            }\n" + 
                "            public Builder_id_name_owner_website_country country(String country) {\n" + 
                "                return website(null).country(country);\n" + 
                "            }\n" + 
                "            public Builder_id_name_owner_website_country_description description(String description) {\n" + 
                "                return country(null).description(description);\n" + 
                "            }\n" + 
                "            public Brand build() {\n" + 
                "                return description(null).build();\n" + 
                "            }\n" + 
                "            \n" + 
                "        }\n" + 
                "        public static class Builder_id_name_owner {\n" + 
                "            \n" + 
                "            private final Builder_id_name parent;\n" + 
                "            private final String owner;\n" + 
                "            \n" + 
                "            private Builder_id_name_owner(Builder_id_name parent, String owner) {\n" + 
                "                this.parent = parent;\n" + 
                "                this.owner = java.util.Optional.ofNullable(owner).orElseGet(()->null);\n" + 
                "            }\n" + 
                "            \n" + 
                "            public String id() {\n" + 
                "                return parent.id();\n" + 
                "            }\n" + 
                "            public String name() {\n" + 
                "                return parent.name();\n" + 
                "            }\n" + 
                "            public String owner() {\n" + 
                "                return owner;\n" + 
                "            }\n" + 
                "            public Builder_id_name_owner id(String id) {\n" + 
                "                return parent.id(id).owner(owner);\n" + 
                "            }\n" + 
                "            public Builder_id_name_owner name(String name) {\n" + 
                "                return parent.name(name).owner(owner);\n" + 
                "            }\n" + 
                "            public Builder_id_name_owner owner(String owner) {\n" + 
                "                return parent.owner(owner);\n" + 
                "            }\n" + 
                "            public Builder_id_name_owner_website website(String website) {\n" + 
                "                return new Builder_id_name_owner_website(this, website);\n" + 
                "            }\n" + 
                "            public Builder_id_name_owner_website_country country(String country) {\n" + 
                "                return website(null).country(country);\n" + 
                "            }\n" + 
                "            public Builder_id_name_owner_website_country_description description(String description) {\n" + 
                "                return country(null).description(description);\n" + 
                "            }\n" + 
                "            public Brand build() {\n" + 
                "                return description(null).build();\n" + 
                "            }\n" + 
                "            \n" + 
                "        }\n" + 
                "        public static class Builder_id_name_owner_website {\n" + 
                "            \n" + 
                "            private final Builder_id_name_owner parent;\n" + 
                "            private final String website;\n" + 
                "            \n" + 
                "            private Builder_id_name_owner_website(Builder_id_name_owner parent, String website) {\n" + 
                "                this.parent = parent;\n" + 
                "                this.website = java.util.Optional.ofNullable(website).orElseGet(()->null);\n" + 
                "            }\n" + 
                "            \n" + 
                "            public String id() {\n" + 
                "                return parent.id();\n" + 
                "            }\n" + 
                "            public String name() {\n" + 
                "                return parent.name();\n" + 
                "            }\n" + 
                "            public String owner() {\n" + 
                "                return parent.owner();\n" + 
                "            }\n" + 
                "            public String website() {\n" + 
                "                return website;\n" + 
                "            }\n" + 
                "            public Builder_id_name_owner_website id(String id) {\n" + 
                "                return parent.id(id).website(website);\n" + 
                "            }\n" + 
                "            public Builder_id_name_owner_website name(String name) {\n" + 
                "                return parent.name(name).website(website);\n" + 
                "            }\n" + 
                "            public Builder_id_name_owner_website owner(String owner) {\n" + 
                "                return parent.owner(owner).website(website);\n" + 
                "            }\n" + 
                "            public Builder_id_name_owner_website website(String website) {\n" + 
                "                return parent.website(website);\n" + 
                "            }\n" + 
                "            public Builder_id_name_owner_website_country country(String country) {\n" + 
                "                return new Builder_id_name_owner_website_country(this, country);\n" + 
                "            }\n" + 
                "            public Builder_id_name_owner_website_country_description description(String description) {\n" + 
                "                return country(null).description(description);\n" + 
                "            }\n" + 
                "            public Brand build() {\n" + 
                "                return description(null).build();\n" + 
                "            }\n" + 
                "            \n" + 
                "        }\n" + 
                "        public static class Builder_id_name_owner_website_country {\n" + 
                "            \n" + 
                "            private final Builder_id_name_owner_website parent;\n" + 
                "            private final String country;\n" + 
                "            \n" + 
                "            private Builder_id_name_owner_website_country(Builder_id_name_owner_website parent, String country) {\n" + 
                "                this.parent = parent;\n" + 
                "                this.country = java.util.Optional.ofNullable(country).orElseGet(()->null);\n" + 
                "            }\n" + 
                "            \n" + 
                "            public String id() {\n" + 
                "                return parent.id();\n" + 
                "            }\n" + 
                "            public String name() {\n" + 
                "                return parent.name();\n" + 
                "            }\n" + 
                "            public String owner() {\n" + 
                "                return parent.owner();\n" + 
                "            }\n" + 
                "            public String website() {\n" + 
                "                return parent.website();\n" + 
                "            }\n" + 
                "            public String country() {\n" + 
                "                return country;\n" + 
                "            }\n" + 
                "            public Builder_id_name_owner_website_country id(String id) {\n" + 
                "                return parent.id(id).country(country);\n" + 
                "            }\n" + 
                "            public Builder_id_name_owner_website_country name(String name) {\n" + 
                "                return parent.name(name).country(country);\n" + 
                "            }\n" + 
                "            public Builder_id_name_owner_website_country owner(String owner) {\n" + 
                "                return parent.owner(owner).country(country);\n" + 
                "            }\n" + 
                "            public Builder_id_name_owner_website_country website(String website) {\n" + 
                "                return parent.website(website).country(country);\n" + 
                "            }\n" + 
                "            public Builder_id_name_owner_website_country country(String country) {\n" + 
                "                return parent.country(country);\n" + 
                "            }\n" + 
                "            public Builder_id_name_owner_website_country_description description(String description) {\n" + 
                "                return new Builder_id_name_owner_website_country_description(this, description);\n" + 
                "            }\n" + 
                "            public Brand build() {\n" + 
                "                return description(null).build();\n" + 
                "            }\n" + 
                "            \n" + 
                "        }\n" + 
                "        public static class Builder_id_name_owner_website_country_description {\n" + 
                "            \n" + 
                "            private final Builder_id_name_owner_website_country parent;\n" + 
                "            private final String description;\n" + 
                "            \n" + 
                "            private Builder_id_name_owner_website_country_description(Builder_id_name_owner_website_country parent, String description) {\n" + 
                "                this.parent = parent;\n" + 
                "                this.description = java.util.Optional.ofNullable(description).orElseGet(()->null);\n" + 
                "            }\n" + 
                "            \n" + 
                "            public String id() {\n" + 
                "                return parent.id();\n" + 
                "            }\n" + 
                "            public String name() {\n" + 
                "                return parent.name();\n" + 
                "            }\n" + 
                "            public String owner() {\n" + 
                "                return parent.owner();\n" + 
                "            }\n" + 
                "            public String website() {\n" + 
                "                return parent.website();\n" + 
                "            }\n" + 
                "            public String country() {\n" + 
                "                return parent.country();\n" + 
                "            }\n" + 
                "            public String description() {\n" + 
                "                return description;\n" + 
                "            }\n" + 
                "            public Builder_id_name_owner_website_country_description id(String id) {\n" + 
                "                return parent.id(id).description(description);\n" + 
                "            }\n" + 
                "            public Builder_id_name_owner_website_country_description name(String name) {\n" + 
                "                return parent.name(name).description(description);\n" + 
                "            }\n" + 
                "            public Builder_id_name_owner_website_country_description owner(String owner) {\n" + 
                "                return parent.owner(owner).description(description);\n" + 
                "            }\n" + 
                "            public Builder_id_name_owner_website_country_description website(String website) {\n" + 
                "                return parent.website(website).description(description);\n" + 
                "            }\n" + 
                "            public Builder_id_name_owner_website_country_description country(String country) {\n" + 
                "                return parent.country(country).description(description);\n" + 
                "            }\n" + 
                "            public Builder_id_name_owner_website_country_description description(String description) {\n" + 
                "                return parent.description(description);\n" + 
                "            }\n" + 
                "            public Brand build() {\n" + 
                "                return new Brand(id(), name(), owner(), website(), country(), description());\n" + 
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

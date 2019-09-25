package example.functionalj.structtype;

import example.functionalj.structtype.Personel;
import example.functionalj.structtype.Personel.PersonelLens;
import functionalj.lens.core.LensSpec;
import functionalj.lens.lenses.FuncMapLens;
import functionalj.lens.lenses.IntegerLens;
import functionalj.lens.lenses.ObjectLensImpl;
import functionalj.lens.lenses.StringLens;
import functionalj.map.FuncMap;
import functionalj.map.ImmutableMap;
import functionalj.pipeable.Pipeable;
import functionalj.types.IPostConstruct;
import functionalj.types.IStruct;
import functionalj.types.Type;
import functionalj.types.struct.generator.Getter;
import java.lang.Exception;
import java.lang.Object;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

// example.functionalj.structtype.StructLensExample.null

public class Company implements IStruct,Pipeable<Company> {
    
    public static final Company.CompanyLens<Company> theCompany = new Company.CompanyLens<>(LensSpec.of(Company.class));
    public static final Company.CompanyLens<Company> eachCompany = theCompany;
    public final String name;
    public final FuncMap<Integer, Personel> employees;
    
    public Company(String name, FuncMap<Integer, Personel> employees) {
        this.name = $utils.notNull(name);
        this.employees = ImmutableMap.from(employees);
        if (this instanceof IPostConstruct) ((IPostConstruct)this).postConstruct();
    }
    
    public Company __data() throws Exception  {
        return this;
    }
    public String name() {
        return name;
    }
    public FuncMap<Integer, Personel> employees() {
        return employees;
    }
    public Company withName(String name) {
        return new Company(name, employees);
    }
    public Company withName(Supplier<String> name) {
        return new Company(name.get(), employees);
    }
    public Company withName(Function<String, String> name) {
        return new Company(name.apply(this.name), employees);
    }
    public Company withName(BiFunction<Company, String, String> name) {
        return new Company(name.apply(this, this.name), employees);
    }
    public Company withEmployees(FuncMap<Integer, Personel> employees) {
        return new Company(name, employees);
    }
    public Company withEmployees(Supplier<FuncMap<Integer, Personel>> employees) {
        return new Company(name, employees.get());
    }
    public Company withEmployees(Function<FuncMap<Integer, Personel>, FuncMap<Integer, Personel>> employees) {
        return new Company(name, employees.apply(this.employees));
    }
    public Company withEmployees(BiFunction<Company, FuncMap<Integer, Personel>, FuncMap<Integer, Personel>> employees) {
        return new Company(name, employees.apply(this, this.employees));
    }
    public static Company fromMap(Map<String, Object> map) {
        Map<String, Getter> $schema = getStructSchema();
        @SuppressWarnings("unchecked")
        Company obj = new Company(
                    (String)$utils.fromMapValue(map.get("name"), $schema.get("name")),
                    (FuncMap<Integer, Personel>)$utils.fromMapValue(map.get("employees"), $schema.get("employees"))
                );
        return obj;
    }
    public Map<String, Object> __toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", functionalj.types.IStruct.$utils.toMapValueObject(name));
        map.put("employees", functionalj.types.IStruct.$utils.toMapValueObject(employees));
        return map;
    }
    public Map<String, Getter> __getSchema() {
        return getStructSchema();
    }
    public static Map<String, Getter> getStructSchema() {
        Map<String, Getter> map = new HashMap<>();
        map.put("name", new functionalj.types.struct.generator.Getter("name", new functionalj.types.Type("java.lang", null, "String", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED));
        map.put("employees", new functionalj.types.struct.generator.Getter("employees", new functionalj.types.Type("functionalj.map", null, "FuncMap", java.util.Arrays.asList(new functionalj.types.Generic("java.lang.Integer", "java.lang.Integer", java.util.Arrays.asList(new functionalj.types.Type("java.lang", null, "Integer", java.util.Collections.emptyList()))), new functionalj.types.Generic("example.functionalj.structtype.Personel", "example.functionalj.structtype.Personel", java.util.Arrays.asList(new functionalj.types.Type("example.functionalj.structtype", null, "Personel", java.util.Collections.emptyList()))))), false, functionalj.types.DefaultValue.REQUIRED));
        return map;
    }
    public String toString() {
        return "Company[" + "name: " + name() + ", " + "employees: " + employees() + "]";
    }
    public int hashCode() {
        return toString().hashCode();
    }
    public boolean equals(Object another) {
        return (another == this) || ((another != null) && (getClass().equals(another.getClass())) && java.util.Objects.equals(toString(), another.toString()));
    }
    
    public static class CompanyLens<HOST> extends ObjectLensImpl<HOST, Company> {
        
        public final StringLens<HOST> name = createSubLens(Company::name, Company::withName, StringLens::of);
        public final FuncMapLens<HOST, Integer, Personel, IntegerLens<HOST>, Personel.PersonelLens<HOST>> employees = createSubFuncMapLens(Company::employees, Company::withEmployees, IntegerLens::of, Personel.PersonelLens::new);
        
        public CompanyLens(LensSpec<HOST, Company> spec) {
            super(spec);
        }
        
    }
    public static final class Builder {
        
        public final CompanyBuilder_withoutEmployees name(String name) {
            return (FuncMap<Integer, Personel> employees)->{
            return ()->{
                return new Company(
                    name,
                    employees
                );
            };
            };
        }
        
        public static interface CompanyBuilder_withoutEmployees {
            
            public CompanyBuilder_ready employees(FuncMap<Integer, Personel> employees);
            
        }
        public static interface CompanyBuilder_ready {
            
            public Company build();
            
            
            
        }
        
        
    }
    
}
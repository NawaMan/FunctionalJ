package example.functionalj.structtype;

import functionalj.lens.core.LensSpec;
import functionalj.lens.lenses.ObjectLensImpl;
import functionalj.lens.lenses.StringLens;
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

// example.functionalj.structtype.StructTypeExamples.null

public class Employee implements IStruct,Pipeable<Employee> {
    
    public static final Employee.EmployeeLens<Employee> theEmployee = new Employee.EmployeeLens<>(LensSpec.of(Employee.class));
    public final String firstName;
    public final String middleName;
    public final String lastName;
    
    public Employee(String firstName, String lastName) {
        this.firstName = $utils.notNull(firstName);
        this.middleName = null;
        this.lastName = $utils.notNull(lastName);
        if (this instanceof IPostConstruct) ((IPostConstruct)this).postConstruct();
    }
    public Employee(String firstName, String middleName, String lastName) {
        this.firstName = $utils.notNull(firstName);
        this.middleName = java.util.Optional.ofNullable(middleName).orElseGet(()->null);
        this.lastName = $utils.notNull(lastName);
        if (this instanceof IPostConstruct) ((IPostConstruct)this).postConstruct();
    }
    
    public Employee __data() throws Exception  {
        return this;
    }
    public String firstName() {
        return firstName;
    }
    public String middleName() {
        return middleName;
    }
    public String lastName() {
        return lastName;
    }
    public Employee withFirstName(String firstName) {
        return new Employee(firstName, middleName, lastName);
    }
    public Employee withFirstName(Supplier<String> firstName) {
        return new Employee(firstName.get(), middleName, lastName);
    }
    public Employee withFirstName(Function<String, String> firstName) {
        return new Employee(firstName.apply(this.firstName), middleName, lastName);
    }
    public Employee withFirstName(BiFunction<Employee, String, String> firstName) {
        return new Employee(firstName.apply(this, this.firstName), middleName, lastName);
    }
    public Employee withMiddleName(String middleName) {
        return new Employee(firstName, middleName, lastName);
    }
    public Employee withMiddleName(Supplier<String> middleName) {
        return new Employee(firstName, middleName.get(), lastName);
    }
    public Employee withMiddleName(Function<String, String> middleName) {
        return new Employee(firstName, middleName.apply(this.middleName), lastName);
    }
    public Employee withMiddleName(BiFunction<Employee, String, String> middleName) {
        return new Employee(firstName, middleName.apply(this, this.middleName), lastName);
    }
    public Employee withLastName(String lastName) {
        return new Employee(firstName, middleName, lastName);
    }
    public Employee withLastName(Supplier<String> lastName) {
        return new Employee(firstName, middleName, lastName.get());
    }
    public Employee withLastName(Function<String, String> lastName) {
        return new Employee(firstName, middleName, lastName.apply(this.lastName));
    }
    public Employee withLastName(BiFunction<Employee, String, String> lastName) {
        return new Employee(firstName, middleName, lastName.apply(this, this.lastName));
    }
    public static Employee fromMap(Map<String, Object> map) {
        Map<String, Getter> $schema = getStructSchema();
        
        Employee obj = new Employee(
                    (String)$utils.fromMapValue(map.get("firstName"), $schema.get("firstName")),
                    (String)$utils.fromMapValue(map.get("middleName"), $schema.get("middleName")),
                    (String)$utils.fromMapValue(map.get("lastName"), $schema.get("lastName"))
                );
        return obj;
    }
    public Map<String, Object> __toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("firstName", functionalj.types.IStruct.$utils.toMapValueObject(firstName));
        map.put("middleName", functionalj.types.IStruct.$utils.toMapValueObject(middleName));
        map.put("lastName", functionalj.types.IStruct.$utils.toMapValueObject(lastName));
        return map;
    }
    public Map<String, Getter> __getSchema() {
        return getStructSchema();
    }
    public static Map<String, Getter> getStructSchema() {
        Map<String, Getter> map = new HashMap<>();
        map.put("firstName", new functionalj.types.struct.generator.Getter("firstName", new functionalj.types.Type("java.lang", null, "String", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED));
        map.put("middleName", new functionalj.types.struct.generator.Getter("middleName", new functionalj.types.Type("java.lang", null, "String", java.util.Collections.emptyList()), true, functionalj.types.DefaultValue.NULL));
        map.put("lastName", new functionalj.types.struct.generator.Getter("lastName", new functionalj.types.Type("java.lang", null, "String", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED));
        return map;
    }
    public String toString() {
        return "Employee[" + "firstName: " + firstName() + ", " + "middleName: " + middleName() + ", " + "lastName: " + lastName() + "]";
    }
    public int hashCode() {
        return toString().hashCode();
    }
    public boolean equals(Object another) {
        return (another == this) || ((another != null) && (getClass().equals(another.getClass())) && java.util.Objects.equals(toString(), another.toString()));
    }
    
    public static class EmployeeLens<HOST> extends ObjectLensImpl<HOST, Employee> {
        
        public final StringLens<HOST> firstName = createSubLens(Employee::firstName, Employee::withFirstName, StringLens::of);
        public final StringLens<HOST> middleName = createSubLens(Employee::middleName, Employee::withMiddleName, StringLens::of);
        public final StringLens<HOST> lastName = createSubLens(Employee::lastName, Employee::withLastName, StringLens::of);
        
        public EmployeeLens(LensSpec<HOST, Employee> spec) {
            super(spec);
        }
        
    }
    public static final class Builder {
        
        public final EmployeeBuilder_withoutMiddleName firstName(String firstName) {
            return (String middleName)->{
            return (String lastName)->{
            return ()->{
                return new Employee(
                    firstName,
                    middleName,
                    lastName
                );
            };
            };
            };
        }
        
        public static interface EmployeeBuilder_withoutMiddleName {
            
            public EmployeeBuilder_withoutLastName middleName(String middleName);
            
            public default EmployeeBuilder_ready lastName(String lastName){
                return middleName(null).lastName(lastName);
            }
            
        }
        public static interface EmployeeBuilder_withoutLastName {
            
            public EmployeeBuilder_ready lastName(String lastName);
            
        }
        public static interface EmployeeBuilder_ready {
            
            public Employee build();
            
            
            
        }
        
        
    }
    
}
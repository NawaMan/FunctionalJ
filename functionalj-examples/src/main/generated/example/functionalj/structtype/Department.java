package example.functionalj.structtype;

import example.functionalj.structtype.Employee;
import example.functionalj.structtype.Employee.EmployeeLens;
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

public class Department implements IStruct,Pipeable<Department> {
    
    public static final Department.DepartmentLens<Department> theDepartment = new Department.DepartmentLens<>(LensSpec.of(Department.class));
    public final String name;
    public final Employee manager;
    
    public Department(String name, Employee manager) {
        this.name = $utils.notNull(name);
        this.manager = $utils.notNull(manager);
        if (this instanceof IPostConstruct) ((IPostConstruct)this).postConstruct();
    }
    
    public Department __data() throws Exception  {
        return this;
    }
    public String name() {
        return name;
    }
    public Employee manager() {
        return manager;
    }
    public Department withName(String name) {
        return new Department(name, manager);
    }
    public Department withName(Supplier<String> name) {
        return new Department(name.get(), manager);
    }
    public Department withName(Function<String, String> name) {
        return new Department(name.apply(this.name), manager);
    }
    public Department withName(BiFunction<Department, String, String> name) {
        return new Department(name.apply(this, this.name), manager);
    }
    public Department withManager(Employee manager) {
        return new Department(name, manager);
    }
    public Department withManager(Supplier<Employee> manager) {
        return new Department(name, manager.get());
    }
    public Department withManager(Function<Employee, Employee> manager) {
        return new Department(name, manager.apply(this.manager));
    }
    public Department withManager(BiFunction<Department, Employee, Employee> manager) {
        return new Department(name, manager.apply(this, this.manager));
    }
    public static Department fromMap(Map<String, Object> map) {
        Map<String, Getter> $schema = getStructSchema();
        
        Department obj = new Department(
                    (String)$utils.fromMapValue(map.get("name"), $schema.get("name")),
                    (Employee)$utils.fromMapValue(map.get("manager"), $schema.get("manager"))
                );
        return obj;
    }
    public Map<String, Object> __toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", functionalj.types.IStruct.$utils.toMapValueObject(name));
        map.put("manager", functionalj.types.IStruct.$utils.toMapValueObject(manager));
        return map;
    }
    public Map<String, Getter> __getSchema() {
        return getStructSchema();
    }
    public static Map<String, Getter> getStructSchema() {
        Map<String, Getter> map = new HashMap<>();
        map.put("name", new functionalj.types.struct.generator.Getter("name", new functionalj.types.Type("java.lang", null, "String", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED));
        map.put("manager", new functionalj.types.struct.generator.Getter("manager", new functionalj.types.Type("example.functionalj.structtype", null, "Employee", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED));
        return map;
    }
    public String toString() {
        return "Department[" + "name: " + name() + ", " + "manager: " + manager() + "]";
    }
    public int hashCode() {
        return toString().hashCode();
    }
    public boolean equals(Object another) {
        return (another == this) || ((another != null) && (getClass().equals(another.getClass())) && java.util.Objects.equals(toString(), another.toString()));
    }
    
    public static class DepartmentLens<HOST> extends ObjectLensImpl<HOST, Department> {
        
        public final StringLens<HOST> name = createSubLens(Department::name, Department::withName, StringLens::of);
        public final Employee.EmployeeLens<HOST> manager = createSubLens(Department::manager, Department::withManager, Employee.EmployeeLens::new);
        
        public DepartmentLens(LensSpec<HOST, Department> spec) {
            super(spec);
        }
        
    }
    public static final class Builder {
        
        public final DepartmentBuilder_withoutManager name(String name) {
            return (Employee manager)->{
            return ()->{
                return new Department(
                    name,
                    manager
                );
            };
            };
        }
        
        public static interface DepartmentBuilder_withoutManager {
            
            public DepartmentBuilder_ready manager(Employee manager);
            
        }
        public static interface DepartmentBuilder_ready {
            
            public Department build();
            
            
            
        }
        
        
    }
    
}
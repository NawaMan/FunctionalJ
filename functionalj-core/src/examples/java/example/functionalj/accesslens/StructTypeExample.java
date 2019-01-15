package example.functionalj.accesslens;

import static example.functionalj.accesslens.Department.theDepartment;
import static example.functionalj.accesslens.Employee.theEmployee;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import functionalj.annotations.DefaultTo;
import functionalj.annotations.DefaultValue;
import functionalj.annotations.Nullable;
import functionalj.annotations.Struct;
import functionalj.list.FuncList;
import lombok.val;

public class StructTypeExample {
    
    @Struct
    void Person(
            String firstName,
            @Nullable
            String middleName,
            String lastName,
            @DefaultTo(DefaultValue.MINUS_ONE)
            Integer age) {
    }
    
    @Struct
    void Employee(
            String firstName,
            @Nullable
            String middleName,
            String lastName) {}
            
    @Struct
    void Department(
            String   name,
            Employee manager) {};
    
    @Test
    public void example01_Basic() {
        val person = new Person("John", "Doe");
        assertEquals("Person[firstName: John, middleName: null, lastName: Doe, age: -1]", person.toString());
    }
    
    @Test
    public void example02_FieldRead() {
        val person = new Person("John", "Doe");
        assertEquals("John", person.firstName());
        assertEquals("Doe",  person.lastName());
    }
    
    @Test
    public void example03_FieldChange() {
        val person1 = new Person("John", "Doe");
        val person2 = person1.withLastName("Smith");
        assertEquals("Person[firstName: John, middleName: null, lastName: Doe, age: -1]",   person1.toString());
        assertEquals("Person[firstName: John, middleName: null, lastName: Smith, age: -1]", person2.toString());
    }
    
    @Test
    public void example03_HashEquals() {
        val person1 = new Person("John", "Doe");
        val person2 = new Person("John", "Doe");
        val person3 = new Person("John", "Smith");
        assertTrue (person1.hashCode() == person2.hashCode());
        assertTrue (person1.equals(person2));
        assertFalse(person1.hashCode() == person3.hashCode());
        assertFalse(person1.equals(person3));
    }
    
    @Test
    public void example04_Null() {
        try {
            new Person("John", null);
            fail("Expect an NPE.");
        } catch (NullPointerException e) {
        }
    }
    
    @Test
    public void example05_DefaultValue() {
        val person = new Person("John", "Doe");
        assertEquals("Person[firstName: John, middleName: null, lastName: Doe, age: -1]", person.toString());
    }
    
    @Test
    public void example06_DefaultValue_withValue() {
        // With value
        val person1 = new Person("John", null, "Doe", 30);
        assertEquals("Person[firstName: John, middleName: null, lastName: Doe, age: 30]", person1.toString());
        
        // With default value
        val person2 = new Person("John", null, "Doe", null);
        assertEquals("Person[firstName: John, middleName: null, lastName: Doe, age: -1]", person2.toString());
    }
    
    @Test
    public void example07_Lens_Person() {
        val employee1 = new Employee("John", "Doe");
        assertEquals("John", theEmployee.firstName.apply(employee1));
        assertEquals("Doe",  theEmployee.lastName .apply(employee1));
        
        val employee2 = theEmployee.firstName.changeTo("Jonathan").apply(employee1);
        assertEquals("Employee[firstName: Jonathan, middleName: null, lastName: Doe]", employee2.toString());
    }
    
    @Test
    public void example07_Lens_Department() {
        val employee   = new Employee("John", "Doe");
        val department = new Department("Sales", employee);
        assertEquals(
                "Department[name: Sales, manager: Employee[firstName: John, middleName: null, lastName: Doe]]",
                department.toString());
        
        assertEquals("John", theDepartment.manager.firstName.apply(department));
        assertEquals("Doe",  theDepartment.manager.lastName .apply(department));
        
        val department2 = theDepartment.manager.firstName.changeTo("Jonathan").apply(department);
        assertEquals(
                "Department[name: Sales, manager: Employee[firstName: Jonathan, middleName: null, lastName: Doe]]",
                department2.toString());
    }
    
    @Test
    public void example07_Lens_List() {
        val departments = FuncList.of(
                new Department("Sales",   new Employee("John", "Doe")),
                new Department("R&D",     new Employee("John", "Jackson")),
                new Department("Support", new Employee("Jack", "Johnson"))
        );
        assertEquals("[Doe, Jackson, Johnson]", departments.map(theDepartment.manager.lastName).toString());
    }
    
    @Test
    public void example08_Builder() {
        val person = new Person.Builder()
                .firstName("John")
                .lastName("Doe")
                .build();
        assertEquals("Person[firstName: John, middleName: null, lastName: Doe, age: -1]", person.toString());
    }
}

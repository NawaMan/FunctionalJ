// ============================================================================
// Copyright (c) 2017-2020 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package example.functionalj.structtype;

import static example.functionalj.structtype.Department.theDepartment;
import static example.functionalj.structtype.Employee.theEmployee;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import functionalj.list.FuncList;
import functionalj.result.ValidationException;
import functionalj.types.DefaultTo;
import functionalj.types.DefaultValue;
import functionalj.types.Nullable;
import functionalj.types.Struct;
import lombok.val;


public class StructTypeExamples {
    
    @Struct
    void Person(
            String firstName,
            @Nullable
            String middleName,
            String lastName,
            @DefaultTo(DefaultValue.MINUS_ONE)
            Integer age) {}
    
    @Struct
    void Employee(
            String firstName,
            @Nullable
            String middleName,
            String lastName) {}
            
    @Struct
    void Department(
            String   name,
            Employee manager) {}
    
    @Struct
    static boolean Circle1(int x, int y, int radius) {
        return radius > 0;
    }
    
    @Struct
    static String Circle2(int x, int y, int radius) {
        return radius > 0 ? null : "Radius cannot be less than zero: " + radius;
    }
    
    @Struct
    static ValidationException Circle3(int x, int y, int radius) {
        return radius > 0
                ? null
                : new NegativeRadiusException(radius);
    }
    
    @SuppressWarnings("serial")
    public static class NegativeRadiusException extends ValidationException {
        public NegativeRadiusException(int radius) {
            super("Radius: " + radius);
        }
    }
    
    
    @Test
    public void example01_Basic() {
        val person = new Person("John", "Doe");
        assertEquals("Person[firstName: John, middleName: null, lastName: Doe, age: -1]", person.toString());
    }
    
    @Test
    public void example02_FieldRead() {
        val person = new Person("John", "Doe");
        assertEquals("John", person.firstName);
        assertEquals("Doe",  person.lastName);
        assertEquals("John", person.firstName());
        assertEquals("Doe",  person.lastName());
    }
    
    @Test
    public void example03_FieldChange() {
        val person    = new Person("John", "Doe");
        val newperson = person.withLastName("Smith");
        assertEquals("Person[firstName: John, middleName: null, lastName: Doe, age: -1]",   person.toString());
        assertEquals("Person[firstName: John, middleName: null, lastName: Smith, age: -1]", newperson.toString());
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
    public void example08_Lens_List_Filter() {
        val departments = FuncList.of(
                new Department("Sales",   new Employee("John", "Doe")),
                new Department("R&D",     new Employee("John", "Jackson")),
                new Department("Support", new Employee("Jack", "Johnson"))
        );
        assertEquals("[(Sales,Doe), (R&D,Jackson)]",
                departments
                    .filter  (theDepartment.manager.firstName.thatEquals("John"))
                    .mapToTuple(theDepartment.name, theDepartment.manager.lastName)
                    .toString());
    }
    
    @Test
    public void example09_Builder() {
        val person = new Person.Builder()
                .firstName("John")
                .lastName ("Doe")
                .build();
        assertEquals("Person[firstName: John, middleName: null, lastName: Doe, age: -1]", person.toString());
    }
    
    @Test
    public void example10_Builder_notRequired() {
        val person = new Person.Builder()
                .firstName ("John")
                .middleName("F")
                .lastName  ("Kookies")
                .build();
        assertEquals("Person[firstName: John, middleName: F, lastName: Kookies, age: -1]", person.toString());
    }
    
    @Test
    public void example11_Validation_boolean() {
        val validCircle = new Circle1(10, 10, 10);
        assertEquals("Circle1[x: 10, y: 10, radius: 10]", validCircle.toString());
        try {
            new Circle1(10, 10, -10);
            fail("Except a ValidationException.");
        } catch (ValidationException e) {
            assertEquals(
                    "functionalj.result.ValidationException: Circle1: Circle1[x: 10, y: 10, radius: -10]", 
                    e.toString());
        }
    }
    
    @Test
    public void example12_Validation_String() {
        val validCircle = new Circle2(10, 10, 10);
        assertEquals("Circle2[x: 10, y: 10, radius: 10]", validCircle.toString());
        
        try {
            new Circle2(10, 10, -10);
            fail("Except a ValidationException.");
        } catch (ValidationException e) {
            assertEquals(
                    "functionalj.result.ValidationException: Radius cannot be less than zero: -10", 
                    e.toString());
        }
    }
    
    @Test
    public void example13_Validation_Exception() {
        val validCircle = new Circle3(10, 10, 10);
        assertEquals("Circle3[x: 10, y: 10, radius: 10]", validCircle.toString());
        
        try {
            new Circle3(10, 10, -10);
            fail("Except a ValidationException.");
        } catch (ValidationException e) {
            assertEquals(
                    "example.functionalj.structtype.StructTypeExamples$NegativeRadiusException: Radius: -10",
                    e.toString());
        }
    }
}

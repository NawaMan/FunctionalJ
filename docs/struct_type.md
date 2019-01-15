# Struct
Immutability is an important principle of functional programming.
Mutable objects hide changes.
Hidden changes can lead to chaos.

FunctionalJ has a mechanism to create immutable data objects using `@Struct` annotation.
This can be done in two form: a expand form and compact for.
For brevity, we will use compact form when possible.

```java
package pkg;
public class Model {
    @Struct
    void Person(String firstName, String lastName) {}
}
```

Notice that this is just a method and it can be in any class.

The above code results in a class called `pkg.Person` in the same package with this code.
This class has two fields: `fieldName` and `lastName`.

## Creating an Object
The following code shows how to create the object.

```java
    val person = new Person("John", "Doe");
    assertEquals("Person[firstName: John, lastName: Doe]", person.toString());
```

## Accessing a Field
The fields can be access using its getter which is just the method with the same name.

```java
    val person = new Person("John", "Doe");
    assertEquals("John", person.firstName());
    assertEquals("Doe",  person.lastName());
```

## Changing a Field Value
Since the object is immutable, there is no way to actually change the value of the field in the object.
So to change the field value, we create anther object with the new field value.
The method `withXXX(...)` can be used to do that.

```java
    val person1 = new Person("John", "Doe");
    val person2 = person1.withLastName("Smith");
    assertEquals("Person[firstName: John, lastName: Doe]",   person1.toString());
    assertEquals("Person[firstName: John, lastName: Smith]", person2.toString());
```

In the code above, person2 is person1 with changed last name.

## Common Methods

Common object methods such as `toString()`, `hashCode()` and `equals(...)` are automatically generated.

```java
    val person1 = new Person("John", "Doe");
    val person2 = new Person("John", "Doe");
    assertTrue(person1.hashCode() == person2.hashCode());
    assertTrue(person1.equals(person2));
    assertFalse(person1.hashCode() == person3.hashCode());
    assertFalse(person1.equals(person3));
```

## Null
By default, `null` is not allowed as the property value.
NullPointerException will be thrown of null is given as the field value.

```java
    try {
        new Person("John", null);
        fail("Expect an NPE.");
    } catch (NullPointerException e) {
    }
```

In order to allow the field to accept `null`,
  the field must be annotated with `@Nullable` (`functionalj.annotations.Nullable`).
So let say we add `middleName` field to the `Person` class and make it nullable.

```java
    @Struct
    void Person(String firstName, @Nullable String middleName, String lastName) {}
```

Now, you can use `null` to specify the middle name.

```java
    val person = new Person("John", null, "Doe");
    assertEquals("Person[firstName: John, middleName: null, lastName: Doe]", person.toString());
```

With this **nullable** field,
  we got another constructor that only have required fields.

```java
    val person = new Person("John", "Doe");
    assertEquals("Person[firstName: John, middleName: null, lastName: Doe]", person.toString());
```

We can also give them default value by annotation `DefaultTo(...)` and given it default value.
Let say we want to add age field to the Person type and default it to -1.

```java
    @Struct
    void Person(
            String firstName,
            @Nullable
            String middleName,
            String  lastName,
            @DefaultTo(DefaultValue.MINUS_ONE)
            Integer age) {
     }
```

So now we can create person with either a value or null (to use default value).

```java
    // With value
    val person1 = new Person("John", null, "Doe", 30);
    assertEquals("Person[firstName: John, middleName: null, lastName: Doe, age: 30]", person1.toString());
    
    // With default value
    val person2 = new Person("John", null, "Doe", null);
    assertEquals("Person[firstName: John, middleName: null, lastName: Doe, age: -1]", person2.toString());
```

## Lens

Lenses are functions that allow access to field for both read and change (using `withXXX(...)`).
Lens are composable so you can use it to access deep into the sub object.

```java
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
```

Now you can use the lens to access the field in person.

```java
    val employee1 = new Employee("John", "Doe");
    assertEquals("John", theEmployee.firstName.apply(employee1));
    assertEquals("Doe",  theEmployee.lastName .apply(employee1));
    
    val employee2 = theEmployee.firstName.changeTo("Jonathan").apply(employee1);
    assertEquals("Employee[firstName: Jonathan, middleName: null, lastName: Doe]", employee2.toString());
```

Notice `theEmployee` which is a static field in the generated `Employee` class.
Another word, lens is created as a static final field of the generated class.

With lens, it is possible to quickly access to employee from department.

```java
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
```

Notice `theDepartment` which is a static fields in the generated `Department` class.

This is more useful when using it with stream or `FuncList`.
The following code extract the list of manager family name.

```java
    val departments = FuncList.of(
            new Department("Sales",   new Employee("John", "Doe")),
            new Department("R&D",     new Employee("John", "Jackson")),
            new Department("Support", new Employee("Jack", "Johnson"))
    );
    assertEquals("[Doe, Jackson, Johnson]", departments.map(theDepartment.manager.lastName).toString());
```

## Builder
Struct is also comes with a builder.
This build is exhaustive meaning that all requires fields are required.

```java
    val person = new Person.Builder()
            .firstName("John")
            .lastName("Doe")
            .build();
    assertEquals("Person[firstName: John, middleName: null, lastName: Doe, age: -1]", person.toString());
```

# Struct
Immutability is an important principle of functional programming.
Mutable objects hide changes.
Hidden changes can lead to chaos.

FunctionalJ has a mechanism to create immutable data objects using `@Struct` annotation.
This can be done in two forms: a expand form and compact form.
The expand form allows an opportunity to add additional methods.
For brevity, we will use compact form when possible.
The following code show how to define a struct.

```java
package pkg;
public class Models {
    @Struct
    void Person(String firstName, String lastName) {}
}
```

Notice that `@Struct` is annotated on `Person` which is just a method.
This annotated method is called a specification method.
Specification methods can be in any class or interface.
In this case, we put `Person` method in class named `Models` which should make it is easy to locate.

With the above code, FunctionalJ generates a class called `Person` in the same package with this code (`pkg`).
This class has two fields: `firstName` and `lastName`.

## Creating a Struct
The following code shows how to create the object.

```java
    val person = new Person("John", "Doe");
    assertEquals("Person[firstName: John, lastName: Doe]", person.toString());
```

## Common Methods

Common object methods such as `toString()`, `hashCode()` and `equals(...)` are automatically generated.
The code above shows how `toString()` might return and the following code shows `hashCode()` and `equals(...)`.

```java
    val person1 = new Person("John", "Doe");
    val person2 = new Person("John", "Doe");
    assertTrue(person1.hashCode() == person2.hashCode());
    assertTrue(person1.equals(person2));
    assertFalse(person1.hashCode() == person3.hashCode());
    assertFalse(person1.equals(person3));
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
So to change the field value, we create another object with the new field value.
The method `withXXX(...)` can be used to do just that.

```java
    val person1 = new Person("John", "Doe");
    val person2 = person1.withLastName("Smith");
    assertEquals("Person[firstName: John, lastName: Doe]",   person1.toString());
    assertEquals("Person[firstName: John, lastName: Smith]", person2.toString());
```

In the code above, `person2` is `person1` with changed last name.

## Null
By default, `null` is not allowed as the property value.
`NullPointerException` will be thrown of `null` is given as the field value.

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

We can also give them default value by annotating with `DefaultTo(...)` and given it default value.
Let say we want to add age field to the Person type and default it to `-1`.

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

Of course, the constructors with only required field is still there.

```java
    val person = new Person("John", "Doe");
    assertEquals("Person[firstName: John, middleName: null, lastName: Doe]", person.toString());
```

## Lens

Lenses are functions that allow access to fields for both reading and changing (using `withXXX(...)`).
Lens are composable so you can use it to access deep into the sub object.
Consider the following code: 

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

Now you can use the lens to access the field in employee.

```java
    import static pkg.Employee.theEmployee;
    ...
    
    val employee1 = new Employee("John", "Doe");
    assertEquals("John", theEmployee.firstName.apply(employee1));
    assertEquals("Doe",  theEmployee.lastName .apply(employee1));
    
    val employee2 = theEmployee.firstName.changeTo("Jonathan").apply(employee1);
    assertEquals("Employee[firstName: Jonathan, middleName: null, lastName: Doe]", employee2.toString());
```

Notice the static import for `theEmployee`.
Another word, lens is created as a static final field of the generated class.

With lens, it is possible to quickly access to field in the employee from the department.

```java
    import static pkg.Department.theDepartment;
    import static pkg.Employee.theEmployee;
    ...
    
    val employee   = new Employee("John", "Doe");
    val department = new Department("Sales", employee);
    assertEquals(
            "Department[name: Sales, manager: Employee[firstName: John, middleName: null, lastName: Doe]]",
            department.toString());
    
    // Read
    assertEquals("John", theDepartment.manager.firstName.apply(department));
    assertEquals("Doe",  theDepartment.manager.lastName .apply(department));
    
    // Change
    val department2 = theDepartment.manager.firstName.changeTo("Jonathan").apply(department);
    assertEquals(
            "Department[name: Sales, manager: Employee[firstName: Jonathan, middleName: null, lastName: Doe]]",
            department2.toString());
```

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

Another example get the list of the department name with the manager last name but only when his name is "John".

```java
    val departments = FuncList.of(
            new Department("Sales",   new Employee("John", "Doe")),
            new Department("R&D",     new Employee("John", "Jackson")),
            new Department("Support", new Employee("Jack", "Johnson"))
    );
    assertEquals("[(Sales,Doe), (R&D,Jackson)]",
            departments
                .filter  (theDepartment.manager.firstName.thatEquals("John"))
                .mapTuple(theDepartment.name, theDepartment.manager.lastName)
                .toString());
```

See "Access and Lens" [▲](https://github.com/NawaMan/FunctionalJ/blob/master/docs/access_lens.md "Access and Lens") for more detail.

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

You can also put in non-required fields.

```java
    val person = new Person.Builder()
            .firstName ("John")
            .middleName("F")
            .lastName  ("Doe")
            .build();
    assertEquals("Person[firstName: John, middleName: F, lastName: Doe, age: -1]", person.toString());
```

One limitation of this is that the fields must be in order.

## Validation
Validation can be added to struct so that only valid ones can be created.
FunctionalJ provides 3 ways of doing validation for Struct.
These ways differ in the way the exception is created.

The first way is to have the spec method return boolean indicating if the parameters are all valid.

```java
    @Struct
    static boolean Circle(int x, int y, int radius) {
        return radius > 0;
    }
    
    val validCircle = new Circle(10, 10, 10);
    assertEquals("Circle[x: 10, y: 10, radius: 10]", validCircle.toString());
    
    try {
        val invalidCircle = new Circle(10, 10, -10);
        fail("Except a ValidationException.");
    } catch (ValidationException e) {
        assertEquals(
                "functionalj.result.ValidationException: Circle: Circle[x: 10, y: 10, radius: -10]", 
                e.toString());
    }
```

Notice that the specification method `Circle` now return `boolean` and made `static`.
It is made static because the generate class will call this method so it has to be made static.

If the radius is not negative, the circle is created without problem.
If the radius, on the other hand, is negative, a `ValidationException` is thrown with automatically generated message.
This should be sufficient in most cases.

If a custom message is needed, the second way can be used
  and that is to make the specification method returns String message of the problem or null when valid.


```java
    @Struct
    static String Circle(int x, int y, int radius) {
        return radius > 0 ? null : "Radius cannot be less than zero: " + radius;
    }
    
    try {
        new Circle(10, 10, -10);
        fail("Except a ValidationException.");
    } catch (ValidationException e) {
        assertEquals(
                "functionalj.result.ValidationException: Radius cannot be less than zero: -10", 
                e.toString());
    }
```

In this case, a `ValidationException` with the message returned by the specification method is thrown when the struct is invalid.
If this is still not enough, for example, you want to return custom exception type,
  the third way can be utilized.

```java
    @Struct
    static ValidationException Circle3(int x, int y, int radius) {
        return radius > 0
                ? null
                : new NegativeRadiusException(radius);
    }
    
    @SuppressWarnings("serial")
    public class NegativeRadiusException extends ValidationException {
        public NegativeRadiusException(int radius) {
            super("Radius: " + radius);
        }
    }
    
    try {
        new Circle3(10, 10, -10);
        fail("Except a ValidationException.");
    } catch (ValidationException e) {
        assertEquals(
                "pkg.NegativeRadiusException: Radius: -10",
                e.toString());
    }
```

You can also use this opportunity to return error if you so want.

## Additional Functionality
So far, we only generate struct class that only have value and default methods.
If there is need for additional methods or to make the generated class extending or implementing some class/interfaces,
we will need to use the extend form.

For example, a abstract class called `Greeter` that can greet people.

```java
    public abstract class Greeter {
        
        public abstract String greetWord();
        
        public String greeting(String name) {
            return greetWord() + " " + name + "!";
        }
    }
```

Then you can create a type spec that extends `Greeter`.

```java
    @Struct
    static abstract class FriendlyGuySpec extends Greeter {
        public abstract String greetWord();
    }
```

This will generate a class called `FriendlyGuy` in the same package (the name will be from the specification class name less "Spec" or "Model").
The generated class `FriendlyGuy` extends Greeter and inherits all methods.

```java
    Greeter fiendlyGuy = new FriendlyGuy("Hi");
    assertEquals("Hi Bruce Wayne!", fiendlyGuy.greeting("Bruce Wayne"));
```

New methods can be added to the generated class by just adding them to the specification class.

```java
    @Struct
    static abstract class FriendlyGuySpec extends Greeter {
        public abstract String greetWord();
        public void shakeHand() {
            ...
        }
    }
```

Basically, the generated class `FiendlyGuy` extends `FriendlyGuySpec` which intern extends `Greeter`.


**Note:** All the code can be find in the file `example.functionalj.structtype.StructTypeExamples.java`.

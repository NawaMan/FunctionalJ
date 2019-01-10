# Access and Lens

## Access
Accesses are functions used to read value out of an object field.
In Java, Access can be created as method reference to the field getter.

For example, given the class:
```Java
    public class User {
        public User(String name) { this.name = name; }
        private String name;
        public String getName() { return name; }
    }
```

The access for name will be `Function<User, String> getName = User::getName`.
With that you can access the name using `getName.apply(user1)`.

FunctionalJ introduces `Access` interface and its sub interfaces for common data types.
These access interfaces has child access already attach to it.
For example, `StringAccess` is itself a function but as also an object has many methods to access to properties of string.
One of them is `length`.
Here is how it can be used.

```Java
    StringAccess<User> userName = User::getName;
    IntegerAccess<User> userNameLength = userName.length();
    User user1 = new User("John");
    assertEquals("John", userName.apply(user1));
    assertEquals(4, userNameLength.apply(user1));
```

In the above code, `userNameLength` is a function of `User` to the length of its string.
`IntegerAccess` in turn has many sub access such as `thatGreaterThan(int value)`.

```Java
    StringAccess<User> userName = User::getName;
    assertFalse(userName.length().thatGreaterThan(4).apply(user1));
    asserTrue (userName.length().thatGreaterThan(4).apply(new User("NawaMan")));
```

You can also use it with stream.

```Java
    StringAccess<User> userName = User::getName;
    var userWithLongName = users.stream().filter(userName.length().thatGreaterThan(4)).collect(toList());
```

## Common accesses
Common accesses are defined as static elements in `functionalj.lens.Access`.
These are: `toString`, `toBoolean`, `theInteger`, `theLong`, `theDouble` and `theObject`.
As you can see, `theString`, for instance, is a function of `String` that return itself but with sub accesses.
There are also short hands for those: `$S`, `$B`, `$I`, `$L`, `$D` and `$O`.

This is how it might be used.

```Java
    static import functionalj.lens.Access.*;
    ...
    val names = List.of("John", "David", "Adam", "Ben");
    var shortNames = names.stream().filter(theString.length().thatLessThan(4)).collect(toList());
    var longNames = names.stream().filter($S.length().thatGreaterThan(4)).collect(toList());
```

## Lenses
Lenses are access functions used to create new instance with new field value.
Another word, a lens is two functions: read and change.
These duality make hard to make them composible in Java.
For now, let say we have lens ready to use.

```Java
    StringLens<User> userName = ... already made somewhere ...;
    assertEquals("John", userName.apply(new User1("John")));    // Read.
```

When lens is used to change property value,
  it takes the object and the new field value and return a new object with the new field value.

```Java
    StringLens<User> userName = ... already made somewhere ...;
    assertEquals(new User("Jack"), userName.apply(new User1("John"), "Jack"));    // Write.
```

Similar to accesses, lenses can be composed but it is quite tedious to do.
The best way to create lenses in FnctionalJ is to use `Struct` data type [▲](https://github.com/NawaMan/FunctionalJ/blob/master/docs/struct_type.md "Struct").

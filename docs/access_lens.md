# Access and Lens

## Access
Accesses are functions used to read value out of an object field.
In Java, Access can be created as method reference to the field getter.

For example, given the class:

```java
    public class User {
        private String name;
        
        public User(String name) {
            this.name = name;
        }
        public String name() {
            return name;
        }
        public String toString() {
            return "User(" + name + ")";
        }
    }
```

The access for name will be `Function<User, String> name = User::name`.
With that you can access the name using `name.apply(user1)`.

```java
    User user1 = new User("John");
    
    StringAccess<User> userName = User::name;
    
    assertEquals("John", userName.apply(user1));
```

FunctionalJ introduces `Access` interface and its sub interfaces for common data types.
These access interfaces has child access already attach to it.
For example, `StringAccess` is itself a function but as also an object has many methods to access to properties of string.
One of them is `length`.
Here is how it can be used.

```java
    User user1 = new User("John");
    
    StringAccess<User>  userName       = User::name;
    IntegerAccess<User> userNameLength = userName.length();
    
    assertEquals("John", userName.apply(user1));
    assertEquals(4, (int)userNameLength.apply(user1));
```

In the above code, `userNameLength` is a function of `User` to the length of its string.
`IntegerAccess` in turn has many sub access such as `thatGreaterThan(int value)`.

```java
    User user1 = new User("John");
    
    StringAccess<User> userName = User::name;
    
    assertFalse(userName.length().thatGreaterThan(4).apply(user1));
    assertTrue (userName.length().thatGreaterThan(4).apply(new User("NawaMan")));
```

You can also use it with stream.

```java
    val users = FuncList.of(
                new User("John"),
                new User("NawaMan"),
                new User("Jack")
            );
    
    StringAccess<User> userName = User::name;
    
    val usersWithLongName = users.filter(userName.length().thatGreaterThan(4));
    assertEquals("[User(NawaMan)]", usersWithLongName.toString());
```

## Common accesses
Common accesses are defined as static elements in `functionalj.lens.Access`.
These are: `toString`, `toBoolean`, `theInteger`, `theLong`, `theDouble` and `theObject`.
As you can see, `theString`, for instance, is a function of `String` that return itself but with sub accesses.
There are also short hands for those: `$S`, `$B`, `$I`, `$L`, `$D` and `$O`.

This is how it might be used.

```java
    val names = FuncList.of("John", "David", "Adam", "Ben");
    
    val shortNames = names.filter(theString.length().thatLessThan(4));
    val longNames  = names.filter($S.length().thatGreaterThan(4));
    
    assertEquals("[Ben]",   shortNames.toString());
    assertEquals("[David]", longNames.toString());
```

## Lenses
Lenses are access functions used to create new instance with new field value.
Another word, a lens is two functions: read and change.
These duality make hard to make them composible in Java.
For now, let say we have lens ready to use.

```java
    StringLens<User> userName = ... already made somewhere ...;
    assertEquals("John", userName.apply(new User1("John")));    // Read.
```

When lens is used to change property value,
  it takes the object and the new field value and return a new object with the new field value.

```java
    StringLens<User> userName = ... already made somewhere ...;
    
    User user1 = new User("John");
    User user2 = userName.apply(user1, "Jack");    // Write.
    assertEquals("User[name: John]", user1.toString());
    assertEquals("User[name: Jack]", user2.toString());
```

Similar to accesses, lenses can be composed but it is quite tedious to do.
The best way to create lenses in FnctionalJ is to use `Struct` data type [▲](https://github.com/NawaMan/FunctionalJ/blob/master/docs/struct_type.md "Struct").

There are four change options:
  - `changeTo(V)` - change the field to the given value,
  - `changeTo(Supplier<V>)` - change the field to the value from the given supplier,
  - `changeTo(Function<V,V>)` - change the field value by transforming it using the given function,
  - `changeTo(BiFunction<HOST,V,V>)` - change the field by consider the host value and the old value.

**Note:** All the code can be find in the file `example.functionalj.accesslens.AccessLensExamples.java`.

**TODO** Null handling.

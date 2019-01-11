# Choice

Choice data types are type of data that can be in any of the list value.

To define a choice type, you annotate an interface with `@Choice`.
The interface name must be suffixed by either `Spec` or `Model`.

```java
    package pkg;
    
    @Choice
    interface UpOrDownSpec {
        void Up();
        void Down();
    }
```

This will tell FunctionalJ to generate a class called `UpOrDown` in the same package with the spec class (package `pkg`).
Noted that, since spec interfaces have no use but to specify how choice type is generated,
 the spec interfaces does not have to be public and can be put inside any other class/interface.

As said, the code above create a abstract class called `pkg.UpOrDown`.
This class has two values: up and down.

```java
    UpOrDown direction1 = UpOrDown.up;
    UpOrDown direction2 = UpOrDown.down;
```

At first glance,
  it looks just like an enum,
  but the choice can contains a payload.
For example:

```java
    @Choice
    interface LoginStatusSpec {
        void Login(String userName);
        void Logout();
    }
```

In this case, the Login status can be in two states: Login with a user name and logout.

```java
    LoginStatus status1 = LoginStatus.Login("root");
    LoginStatus status2 = LoginStatus.Logout();
```

## Understand Choice Types
Choice types are implemented using sealed type pattern.
The generated class is made abstract with private constructor.
All the choices are generated as inner classes that implement the main choice class.

## Checking Choice
`isXXX` methods can be used to check what choice a choice object is.

```java
    LoginStatus status1 = LoginStatus.Login("root");
    LoginStatus status2 = LoginStatus.Logout();
    assertTrue(status1.isLogin());
    assertFalse(status1.isLogout());
    assertFalse(status2.isLogin());
    assertTrue(status2.isLogout());
```

## Using Choice
`asXXXX` methods return a `Result<XXXX>` containing the choice if the type match otherwise return result of null.

```java
    LoginStatus status1 = LoginStatus.Login("root");
    LoginStatus status2 = LoginStatus.Logout();
    assertEquals("Login(root)", status1.asLogin().map(String::valueOf).orElse("Not login"));
    assertEquals("Not login", status2.asLogin().map(String::valueOf).orElse("Not login"));
```

Another way to use the choice is the methods `ifXXX(...)` which has two overloads.
These methods run the given code (either a consumer or a runnable) if the type choice type match.

```java
    LoginStatus status = LoginStatus.Login("root");
    status
        .ifLogin(s -> System.out.println("user: " + s.userName()))
        .ifLogout(()->System.out.println("user: guess"));
    // This code will print out "user: root".
```

## Pattern matching
Pattern matching is a preferred way to use choice value as it ensures all the possible value is handled.
Another word, pattern matching is exhaustive.

Here is the basic example.

```java
    String currentUser = status.match()
        .login (s -> "User: " + s.userName()) 
        .logout("Guess");
```

In the above code, we pattern match the status.
If the status is `login`, the match returns the string "User: " and the user name.
If the status is logout, the match returns the string "Guess".

As mentioned, pattern matching is exhaustive so both the choices must be there for this to compile.
The cases must also be in the right order -- this is actually because of the current implementation.

The match can be expanded in to the payload so that different action can be taken while ensure exhaustion matching.
Consider the following code.

```java
    var moderators = Arrays.asList("Jack", "John");
    var user = status1.match()
        .loginOf("root",               "Administrator")
        .loginOf(moderators::contains, "Moderator")
        .login  (s ->                  "User: " + s.userName())
        .logout (                      "Guess");
```

If the user name is root, the string "Administrator" is returned.
If the user name is in the `moderators` list, the string "Moderator" is returned.
For other login users, the user name is returned.
If the status is not login (i.e., logout), the word "Guess" is returned.



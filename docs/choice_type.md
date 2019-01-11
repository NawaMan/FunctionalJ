# Choice

Choice data types are type of data that can be in any of the list value.

To define a choice type, you annotate an interface with `@Choice`.
The interface name must be suffixed by either `Spec` or `Model`.
```Java
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

```Java
    UpOrDown direction1 = UpOrDown.up;
    UpOrDown direction1 = UpOrDown.down;
```

At first glance,
  it looks just like an enum,
  but the choice can contains a payload.
For example:

```Java
@Choice
interface LoginStatus {
    void Login(String userName);
    void Logout();
}
```

In this case, the Login status can be in two states: Login with a user name and logout.

```Java
    LoginStatus status1 = LoginStatus.Login("root");
    LoginStatus status2 = LoginStatus.Logout();
```

## Understand Choice Types
Choice types are implemented using sealed type pattern.
The generated class is made abstract with private constructor.
All the choices are generated as inner classes that implement the main choice class.

## Working with choice types
Choice type comes with many way to work with it.
One of which is pattern matching which is discussed in more detail in the next section.

### Check choice
We

## Pattern matching
There are many way to access the value of this choice.
One of them is to use pattern matching -- well, kind of.

```Java
        var currentUser = status.match()
            .login (s -> "User: " + s.userName()) 
            .logout("Guess");
```

In the above code, we pattern match the status.
If the status is `login`, the match returns the string "User: " and the user name.
If the status is logout, the match returns the string "Guess".





I will write this -- promise.

Nawa - 2019-01-08
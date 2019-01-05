# FunctionalJ

FunctionalJ is a library for writing functional style code in Java.
Its focus is on the practicality aspect and less so on the theoretical aspect.

## Here are some features:
- Flexible uses of function and More ways to create and manipulate functions
- Accesses and Lenses
- Lazy functional list and map
- Addition to Stream and Iterator
- Pipeable and PipeLine (point free)
- Result (boxed object similar in concept with Optional)
- Algebraic data type - Struct and choice types
  -- Struct type - an immutable data with lens and exhaustive builder
  -- Choice type - a sum type with payload and pattern matching
- Rule type to constrain existing types.
- Ref for Implicit and dependency injection
- IO for side effect management
- Event (Work in progress)

This library works within the boundary of the language though pushing to the edge in some aspect. 
Therefore, it sometimes breaks coding convention.

## Functions
- FunctionalJ expands the function-representing objects to 6 parameters (`Func1`, ... `Func6`) and up to 3 parameters for no-return-value ones (`FuncUnit1`, ... `FuncUnit3`).
- Functions body can throw an exception. There are different application methods:
    - `applyUnsafe(...)` that throw the exception
    - `apply(...)` that wrap the exception with a runtime exception (FunctionInvocationException)
    - `applySafely(...)` that return `Result<>` (with the return value or exception inside). 
- Function objects can be created in the way that its `toString()` return a name or the location where it is defined.

### Creating functions
Functions can be created using the static method `Func.of(...)`. This method take lambda or method reference.

Static methods `Func.f(...)` and `Func.F(...)` can also be used.
Functions created using `F(...)` will return the location in the code where it is created from its `toString()`.

A name can be given to either `f(...)` or `F(...)` which will show up in `toString()` as well.

### Manipulate functions
- `safely()` makes the function returns `Result<...>` and do not throw exception.
- `optionally()` makes the function returns standard Java `Optional<>` and do not throw exception.
- `async()` makes the function returns `Promise<...>`.
- `ignoreResult()` changes function to be a consumer function (return void).
- `toPredicate()` creates a predicate from this function by checking if this function return `true` or not.
- `toPredicate(mapperToBoolean)` creates a predicate from this function using the conversion to convert the result to a boolean.
- `bind(...)` binds some parameters to the function. This is known as partial application. See the section: Partial Application for more information.
- `memoize()` to return a function that cache the result by the input.

### Default return
Since returning `null` and throwing exception can become problematic, there are methods to adjust the return value before returning.
- `orElse(...)` and `orGet(...)` applies the function with input and adjust the return value.
- `whenAbsentXXX(...)` specified how to adjust the return result in case of null or exception.

### Flexible inputs
Functions can handle multiple types of input.
The method `applyTo(...)` has many overload for input.
For example, `String::length` is a `Func1<String, Integer>`.
This function can take a regular string (returns `Integer`), `Result<String>` (returns `Result<Integer>`), `Promise<String>` (returns `Promise<Integer>`),
  `IO<String>` (returns `IO<Integer>`),
  `StreamPlus<String>` (returns `StreamPlus<Integer>`),
  `FuncList<String>` (returns `FuncList<Integer>`),
  `FuncMap<String>` (returns `FuncMap<Integer>`),
  `Supplier<String>`  (returns `Supplier<Integer>`) and
  `Function<T, String>` (returns `Function<T, Integer>`).
This enables functions to be reused without additional work.

In case of functions with more than one parameter, any tuple of the matching parameters is also accepted.

### Partial Application
Partial application is the process of applying part of the parameters to a function producing another function that will take the rest of the parameters.

In FunctionalJ, the methods `bind(...)` can be used to do partial application.

Additionally, any function can accept only the first parameter using `applyTo(...)` and returns another function if one or more parameter is needed. This is also know as curring.

### Composition
Composition is a processing of creating another function from two existing functions in the way that the result of the first function will be applied to the second function. This is done using method `then(...)`.

## Access and Lenses


## Usage

### Using FunctionalJ in a Gradle project

This project binary is published on [my maven repo](https://github.com/NawaMan/nawaman-maven-repository) hosted on GitHub.
So to use FunctionalJ you will need to ...

Add the maven repository ...

```Groovy
    maven { url 'https://raw.githubusercontent.com/nawmaman/nawaman-maven-repository/master/' }
```

and the dependencies to FunctionalJ.

```Groovy
    compile 'functionalj:functionalj-core:0.1.59.0' // Please lookup for the latest version.
```

[UseFunctionalJGradle](https://github.com/NawaMan/UseFunctionalJGradle) is an example project that use FunctionalJ.
You can use that as a starting point.
Just add the dependency to FunctionalJ to it.

### Using FunctionalJ in a Maven project

Adding the required maven repository (hosted by github).

```xml
<repository>
    <id>Nullable-mvn-repo</id>
    <url>https://raw.githubusercontent.com/nawaman/nawaman-maven-repository/master/</url>
    <snapshots>
        <enabled>true</enabled>
        <updatePolicy>always</updatePolicy>
    </snapshots>
</repository>
```

and the dependencies to FunctionalJ.

```xml

    <dependency>
        <groupId>functionalj</groupId>
        <artifactId>functionalj-core</artifactId>
        <version>0.1.59.0</version>
    </dependency>
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.11</version>
        <scope>test</scope>
    </dependency>
```

[UseFunctionalJMaven](https://github.com/NawaMan/UseFunctionalJMaven) is an example project that use FunctionalJ.
You can use that as a starting point.
Just add the dependency to FunctionalJ to it.

## Build

This project is developed as a gradle project on Eclipse
  so you can just clone and import it to your Eclipse.
Although, never tried, but I think it should be easy to import into IntelliJ.
Simply run `gradle clean build` to build the project (or use the build-in gradle wrapper).

## Versioning
The versioning of this project is not the commonly used semantic versioning.
Well, the last three digits are kind of semantic version.
But the first one represents a conceptual version of the library.
This is done this way as it was found that the version was updates too quickly
  and there is nothing indicates the fundamental change in concept or philosophy of the library.
  
- The first digit is the version of the concept - changed when there is a big changes across the library or in the fundamental ways.
- The second digit is the version of the API - changed when there is a breaking changes in the API.
- The third digit is the version of the implementation.
- The forth digit is the version of correction.

## Issues

Please use our [issues tracking page](https://github.com/NawaMan/FunctionalJ/issues) to report any issues.

## Take what you need

You can import and use this library as you needed.
But if you just need a small part of it, feel free to fork it or just copy the part that you need. :-)


## Contribute

Feel free to join in.
Report problems, suggest solutions, suggest more functionalities, making pull requests ... anything is appreciated (please do it in [issues tracking page](https://github.com/NawaMan/FunctionalJ/issues) or email me directly).

If this is useful to you and want to buy me a [coffee](https://www.paypal.me/NawaMan/2.00)
 or [lunch](https://www.paypal.me/NawaMan/10.00) or [help with my kids college fund](https://www.paypal.me/NawaMan/100.00) ... that would be great :-p


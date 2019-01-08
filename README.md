# FunctionalJ

FunctionalJ is a library for writing functional style code in Java.
It aims be a practical expansion to functional programming added in Java 8.
FunctionalJ is a pure Java library with all code written in Java
  so its usages will be just like other Java library.
No additional build steps or tools are required outside of adding dependencies.

## Here are some features:
- Flexible uses of and more ways to create and manipulate functions [:link:](http://./docs/functions.md)
- Accesses and Lenses
- Lazy functional list and map
- Addition to Stream and Iterator
- Pipeable and PipeLine (point-free style of programming)
- Result (boxed object similar in concept with Optional)
- Struct type - an immutable data with build-in lens and exhaustive builder
- Choice type - a sum type with payload and pattern matching
- Rule type to constrain existing types.
- Ref for dependency injection
- DeferAction, Promise and IO for side effect management
- Event (Work in progress)

## Examples

```Java

```

## Usage

** Using FunctionalJ in a Gradle project **

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

** Using FunctionalJ in a Maven project **

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
This project is build with Java 8 and make heavy use of Lombok `val` for type inference.

## Disclaimer

This library works within the boundary of the language though pushing to the edge in some aspect. 
Therefore, it sometimes breaks coding convention.
I would ask if you could look over this and see if this library can bring you any benefits.

## Versioning
This project use a modified semantic versioning.
Well, the last three digits are kind of semantic version.
But the first one represents a conceptual version of the library.
This is done this way as it was found that the version was updates too quickly
  and there is nothing indicates the fundamental change in concept or philosophy of the library.
  
- The first digit is the version of the concept - changed when there is a big changes across the library or in the fundamental ways and will reset the second digit.
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


# FunctionalJ

FunctionalJ is a library for writing functional style code in Java.
Its focus is on the practicality aspect and less so on the theoretical aspect.

## Here are some features:
- More ways to create and manipulate functions
- Useful predefined functions
- Accesses and Lenses
- Lazy functional list and map
- Addition to Stream and Iterator
- Pipeable (flow) and PipeLine (point free)
- Result (boxed object similar in concept with Optional)
- Struct type with automatically created lens and exhaustive builder
- Sealed type with pattern matching
- Ref for Implicit and dependency injection
- Promise
- Reactive (Work in progress)

This library works within the boundary of the language though pushing to the edge in some aspect. 
Therefore, it sometimes breaks convention.

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
    compile 'functionalj:functionalj-annotations:0.1.54.0' // Please lookup for the latest version.
```

[UseNullableJGradle](https://github.com/NawaMan/UseNullableJGradle) is an example project that use FunctionalJ.
You can use that as a starting point.
Just add the dependency to DefaultJ to it.

### Using DefaultJ in a Maven project

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

and the dependencies to DefaultJ.

```xml

    <dependency>
        <groupId>defaultj</groupId>
        <artifactId>defaultj-annotations</artifactId>
        <version>1.1.0.0</version>
    </dependency>
    <dependency>
        <groupId>defaultj</groupId>
        <artifactId>defaultj-core</artifactId>
        <version>1.1.0.0</version>
    </dependency>
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.11</version>
        <scope>test</scope>
    </dependency>
```

[UseNullableJMaven](https://github.com/NawaMan/UseNullableJMaven) is an example project that use NullableJ.
You can use that as a starting point.
Just add the dependency to DefaultJ to it.

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

Please use our [issues tracking page](https://github.com/NawaMan/DefaultJ/issues) to report any issues.

## Take what you need

You can import and use this library as you needed.
But if you just need a small part of it, feel free to fork it or just copy the part that you need. :-)


## Contribute

Feel free to join in.
Report problems, suggest solutions, suggest more functionalities, making pull requests ... anything is appreciated (please do it in [issues tracking page](https://github.com/NawaMan/DefaultJ/issues) or email me directly).

If this is useful to you and want to buy me a [coffee](https://www.paypal.me/NawaMan/2.00)
 or [lunch](https://www.paypal.me/NawaMan/10.00) or [help with my kids college fund](https://www.paypal.me/NawaMan/100.00) ... that would be great :-p


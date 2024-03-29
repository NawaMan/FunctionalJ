# Functions
Functions are, obviously, essential to functional programming.
FunctionalJ provides more function types as well as many way to use them.

FunctionalJ expands the function-representing objects to 6 parameters (`Func1`, ... `Func6`)
  and up to 3 parameters for no-return-value ones (`FuncUnit1`, ... `FuncUnit3`).

Since functions are functional interfaces, Java 8 method references can be used to create function.

```java
    public int toInt(String str) {
        return Integer.parseInt(str);
    }
    
    ...
        var toInt = (Func1<String, Integer>)this::toInt;
        assertEquals(42, (int)toInt.apply("42"));
    ...
```

## Exception Handling

Functions body can throw an exception. There are different application methods that handle exception differently:

- `applyUnsafe(...)` throws the exception as is.
- `apply(...)` wraps the exception with a runtime exception (`FunctionInvocationException`) or throw as is it is already a runtime exception.
- `applySafely(...)` returns `Result<...>` (with the value or exception inside). 

The code below demonstrate the differences.

```java
    public List<String> readLines(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName));
    }
    
    ...
        var toInt    = (Func1<String, List<String>>)this::readLines;
        var fileName = "FileNotExist.file";
        
        // This throws 'java.nio.file.NoSuchFileException: FileNotExist.file'
        toInt.applyUnsafe(fileName);
        
        // This throws 'functionalj.function.FunctionInvocationException: java.nio.file.NoSuchFileException: FileNotExist.file'
        toInt.applyUnsafe(fileName);
        
        // This return Result object that contains 'java.nio.file.NoSuchFileException: FileNotExist.file'
        toInt.applySafely(fileName);
    ...
```

## Creating functions
Functions can be created using the static method `Func.of(...)`. This method take lambda or method reference.

```java
    var toInt = Func.of(this::toInt);
    assertEquals(42, (int)toInt.apply("42"));
```

Static methods `Func.f(...)` and `Func.F(...)` can also be used.

```java
    import static functionalj.function.Func.f;
    
    ...
        var toInt = f(this::toInt);
        assertEquals(42, (int)toInt.apply("42"));
    ...
```

## Function `toString()`

One frustration of lambda is that its `toString()` is not very useful.
For example, the above `toInt.toString()` might be something like this: `example.functionalj.accesslens.FunctionExamples$$Lambda$2/460332449@726f3b58`.

Functions in FunctionalJ can be created with name so its `toString()` returns something useful.

```java
    var toInt = f("Str2Int", this::toInt);
    assertEquals("F1::Str2Int", toInt.toString());
```

You can also use `Func.F(...)` method to create functions that `toString()` points us  where in the code the function is created.

```java
    import static functionalj.function.Func.F;
    
        var toInt = F(this::toInt);
        assertEquals("F1@example.functionalj.accesslens.FunctionExamples#92", toInt.toString());
```
where `"example.functionalj.accesslens.FunctionExamples"` is the file name and `92` is the line number.

A name can be given to `F(...)` and have its `toString()` show both name and location.

```java
    var toInt = F("Str2Int", this::toInt);
    assertEquals("F1::Str2Int@example.functionalj.accesslens.FunctionExamples#98", toInt.toString());
```

## Manipulate functions
- `safely()` makes the function returns `Result<...>` and do not throw exception.

```java
    var readLines = f(this::readLines).safely();
    var lines     = readLines.apply("FileNotFound.txt");
    assertEquals(
            "Result:{ Exception: java.nio.file.NoSuchFileException: FileNotFound.txt }",
            lines.toString());
```

- `optionally()` makes the function returns standard Java `Optional<>` and do not throw exception.

```java
    var readLines = f(this::readLines).optionally();
    var lines     = readLines.apply("FileNotFound.txt");
    assertEquals("Optional.empty", lines.toString());
```

- `async()` makes the function returns `Promise<...>`.

```java
    var readLines = f(this::readLines).async();
    readLines
            .apply        ("FileNotFound.txt")
            .whenAbsentUse(FuncList.empty())
            .subscribe    (lines -> {
                assertEquals("[]", lines.toString());
                lock.countDown();
            });
```
- `ignoreResult()` changes function to be a consumer function (return void).
- `toPredicate()` creates a predicate from this function by checking if this function return `true` or not.
- `toPredicate(mapperToBoolean)` creates a predicate from this function using the conversion to convert the result to a boolean.
- `bind(...)` binds some parameters to the function. This is known as partial application. See the section: **Partial Application** for more information.
- `memoize()` to return a function that cache the result by the input.

## Default Return
Since returning `null` and throwing exception can become problematic, there are methods to adjust the return value before returning.
- `orElse(...)` and `orGet(...)` applies the function with input and adjust the return value when the return value is null or exception.

```java
    var readLines = f(this::readLines);
    var lines     = readLines.orElse("FileNotFound.txt", FuncList.empty());
    assertEquals("[]", lines.toString());
```

- `whenAbsentXXX(...)` specifies how to adjust the return result in case of null or exception.

```java
    var readLines = f(this::readLines).whenAbsentUse(FuncList.empty());
    var lines     = readLines.apply("FileNotFound.txt");
    assertEquals("[]", lines.toString());
```

In case you didn't catch it, `whenAbsentXXX(...)` just specified and not apply but `orElse(...)` and `orGet(...)` apply the input right away.

## Flexible Inputs (Functor agnostic)
Functions can handle multiple types of input.
The method `applyTo(...)` has many overload for input.
For example, `String::length` is a `Func1<String, Integer>`.
This function can take a regular string (returns `Integer`), `Result<String>` (returns `Result<Integer>`), `Promise<String>` (returns `Promise<Integer>`), `IO<String>` (returns `IO<Integer>`), `StreamPlus<String>` (returns `StreamPlus<Integer>`), `FuncList<String>` (returns `FuncList<Integer>`), `FuncMap<String>` (returns `FuncMap<Integer>`), `Supplier<String>`  (returns `Supplier<Integer>`) and `Function<T, String>` (returns `Function<T, Integer>`).
This enables functions to be reused without additional work.

It might be easier to see an example.

```java
    var toInt = f(this::toInt);
    
    assertEquals("42",      "" + toInt.applyTo("42"));
    assertEquals("[1, 42]", "" + toInt.applyTo(FuncList.of("1", "42")));
    
    assertEquals(
            "Result:{ Value: 42 }",
            "" + toInt.applyTo(Result  .of("42")));
    
    assertEquals(
            "{One:1, Forty-Two:42}",
            "" + toInt.applyTo(FuncMap.of("One", "1", "Forty-Two", "42")));
    
    var supplier = (Supplier<String>)()->"42";
    assertEquals("42", "" + toInt.applyTo(supplier).get());
    
    var function = (Function<Integer, String>)(i->("" + i));
    assertEquals("42", "" + toInt.applyTo(function).apply(42));
```

In case of functions with more than one parameter, any tuple of the matching parameters is also accepted.

```java
    var add = f((Integer a, Integer b)->a + b);
    var pair = Tuple.of(5, 7);
    assertEquals("12", "" + add.applyTo(pair));
```

## Partial Application
Partial application is the process of applying part of the parameters to a function producing another function that will take the rest of the parameters.

In FunctionalJ, the methods `bind(...)` can be used to do partial application.

```java
    var add = f((Integer a, Integer b)->a + b);
    var addFive_1 = add.bind1(5);      // Apply the first parameter in advance.
    var addFive_2 = add.bind2(5);      // Apply the second parameter in advance.
    var addFive_3 = add.bind(__, 5);   // Apply the first parameter in advance.
    var addFive_4 = add.bind(5, __);   // Apply the second parameter in advance.
```
all the `addFive_x` above is a function of ONE integer as another one is already given.

Additionally, any function can accept only the first parameter using `applyTo(...)` and returns another function if one or more parameter is needed. This is also know as currying.

```java
    var add = f((Integer a, Integer b)->a + b);
    assertEquals("12", "" + add.applyTo(5).applyTo(7));
```

## Composition
Composition is a processing of creating another function from two existing functions in the way that the result of the first function will be applied to the second function. This is done using method `then(...)`.

```java
    var add = f((Integer a, Integer b)->a + b);
    var sum = add.then(i -> "Sum: " + i);
    assertEquals("Sum: 12", "" + sum.applyTo(5, 7));
```

# List and Map
FunctionalJ introduces `FuncList` and `FuncMap` to implement functional lazy-evaluated list and map.
Both implements Java's `List` and `Map` interfaces so they can be used in place of those interfaces.

## List and Map

```java
    List<String>        list = FuncList.of("I", "Me", "Myself");
    Map<String, Double> map  = FuncMap .of("One", 1.0, "PI", 3.14159, "E", 2.71828);
    assertEquals("[I, Me, Myself]",                  list.toString());
    assertEquals("{One:1.0, PI:3.14159, E:2.71828}", map.toString());
```

## Read Only
Both `FuncList` and `FuncMap` are read only so direction modification is not allowed.
All other non-modify access are still there.

```java
    List<String>        list = FuncList.of("I", "Me", "Myself");
    Map<String, Double> map  = FuncMap .of("One", 1.0, "PI", 3.14159, "E", 2.71828);
    assertEquals(3,         list.size());
    assertEquals(3,         map.size());
    assertEquals("Me",      list.get(1).toString());
    assertEquals("3.14159", map.get("PI").toString());
```

## Immutable modification

But since they are read only, modifying methods such as `add`, `set`, `insert`, `put` will throw `UnsupportedOperationException`.

```java
    val list = FuncList.of("I", "Me", "Myself");
    try {
        list.add("We");
        fail("Expect an error!");
    } catch (UnsupportedOperationException e) {
    }
    
    val map = FuncMap.of("One", 1.0, "PI", 3.14159, "E", 2.71828);
    try {
        map.put("Ten", 10.0);
        fail("Expect an error!");
    } catch (UnsupportedOperationException e) {
    }
```

Both `FuncList` and `FuncMap` has method to allow immutable modification -- creating a new instance with the modification.
This is done using with `append` and `with`.

```java
    val newList = list.append("First-Person");
    val newMap  = map .with("Ten", 10.0);
    
    assertEquals("[I, Me, Myself]",                            list.toString());
    assertEquals("{One:1.0, PI:3.14159, E:2.71828}",           map .toString());
    assertEquals("[I, Me, Myself, First-Person]",              newList.toString());
    assertEquals("{One:1.0, PI:3.14159, E:2.71828, Ten:10.0}", newMap .toString());
```
Please read more further below on "Immutability".

## Functional
`FuncList` and `FuncMap` are functional meaning that it directly support functional collection operation.
Operation such as `map`, `filter`, `peek`, `forEach` are available.

```java
    val list = FuncList.of("I", "Me", "Myself");
    val map  = FuncMap .of("One", 1.0, "PI", 3.14159, "E", 2.71828);
    assertEquals("[1, 2, 6]",          list.map(String::length).toString());
    assertEquals("{One:1, PI:3, E:3}", map .map(Math::round)   .toString());
```

Here are some of the functionalities of `FuncList`.
  - `first()`, `rest()`, `last()` and `at(index)` access to element and return `Optional` value of those.
  - `select(Predicate)` returns a list of index+element that match the predicate.
  - `minIndexBy` and `maxIndexBy` find the min and max.
  - `append`, `appendAll`, `prepend`, `prependAll`, `insert`, `insertAll` to add more elements to the list
  - `with` immutable modify the element at the index.
  - `exclude()`, `excludeIn(...)`, `excludeAll(...)`, `excludeAt(int)`, `excludeFrom(int,int)`, `excludeBetween(int,int)` remove elements.
  - `subList` create sub list.
  - `sequential()`, `parallel()`, `unordered()`, `lazy()`, `eager()` configures the processing and materialization characteristic.
  - `map()`, `flatMap()`, `filter()`, `peek()` basic functional operations.
  - `limit()`, `skipXXX()`, `takeXXX` limit the processing elements.
  - `distinct()` uniqueifies the elements.
  - `sorted()`, `sortedBy()` order the elements.
  - `mapOnly(...)`, `mapIf(...)` conditional mapping.
  - `mapWithIndex(...)` map with index.
  - `mapWithPrev(...)` map an element with previous element.
  - `mapThen(...) map to multiple value and merge results.
  - `mapToMap(...)` map each elements to a map.
  - `filterNonNull()`, `filterIn()`, `filter(Class)`, `filter(Function,Predicate)`, `filterWithIndex(BiFunction,Predicate)` more filtering.
  - `peek(Class,Consumer)`, `peek(Predicate,Consumer)`, `peek(Function,Consumer)`, `peek(Function,Predicate,Consumer)` more peek.
  - `flatMapIf()` conditional flatmap.
  - `segment(...)` partition the list.
  - `zipWith(...)`, `choose(...)` combine with another list.
  - `merge(...)` - merge with anther list.

Here are some of the functionalities of `FuncMap`.
  - `findBy(...)` get the Optional value by key.
  - `select(...)` and `selectEntry(...)` get the list of values or entries by key predicate.
  - `with(...)` and `withAll(...)` immutably motify the value matching to the key.
  - `defaultXXX(..)` make the map return some value if the key was not there.
  - `filter(...)` and `exclude(...)` only select the entry desired.
  - `zipWith(...)` combine the value of the same key.

## Lazy and Eager
By default, `FuncList` and `FuncMap` are lazy.
That means, intermediate processing are not evaluated until terminal operation is invoked. 
If you know Java 8 Stream, this is exact same thing.
Lazy evaluation allow more efficient for both memory and clock cycle for more cases.
However, if this is not desired, `FuncList` and `FuncMap` has two mode: lazy and eager.
In eager mode, any operation result in the a immutable list or immutable map.

** Lazy mode **

```java
    val counter = new AtomicInteger(0);
    val value   = IntStreamPlus.range(0, 10).toFuncList()
                .map(i -> counter.getAndIncrement())
                .limit(4)
                .joining(", ");
    assertStrings("0, 1, 2, 3", value);
    assertStrings("4",          counter.get());
```

** Eager mode **

```java
    val counter = new AtomicInteger(0);
    val value   = IntStreamPlus.range(0, 10)
                .toFuncList()
                .eager()
                .map(i -> counter.getAndIncrement())
                .limit(4)
                .joining(", ");
    assertStrings("0, 1, 2, 3", value);
    assertStrings("10",          counter.get());
```

Notice that in the eager mode, the counter ends with 10 as oppose to 4 in lazy mode.
And that because, in the eager mode, the map operation is run for all elements.
   while, in the lazy mode, the limit is applied and both operations are only run the termination operation `joining` is apply.

## Lazyness and Immutability
`List` and `Map` are read-only BUT its elements are not necessary "never changes".
There are two reasons for this.
First, the element itself might change if it is not immutable.
Second, if the list or map is derived from other list or map,
  the derivation might not be pure so it might not always lead to the same result.
The following code highlights the behavior.

```java
    val cats         = FuncList.of("Kitty", "Tigger", "Striped", "Oreo", "Simba", "Scar", "Felix", "Pete", "Schrödinger's");
    val rand         = new Random();
    val deadNotAlive = Func.f((String s) -> rand.nextBoolean()).toPredicate();
    val deadCats     = cats.filter(deadNotAlive);
    assertNotEquals(deadCats, deadCats);
```

Notice that `deadCats` DOES NOT EQUALS TO `deadCats`!
And that because the filter (if a cat is dead or alive) is random.
`equals(...)` method is a termination method and cause the filtering to process.
As the filtering is random, you get random result; hence not equals to itself.

The method `toImmutableList()` or its alias `freeze()` can be used to create the final immutable list.
You can also change to eager mode using `eager()`.

```java
    val surelyDeadCats = deadCats.toImmutableList();
    assertEquals(surelyDeadCats, surelyDeadCats);
```

## Disclaimer on Parallelism
Many of the added functionalities to `FuncList` are not necessary parallel-safe.
The excuse is that I personally rarely use it in that environment.
So please test it first before relying on it.


**Note:** All the code can be find in the file `example.functionalj.list_map.LisMapExamples.java`.

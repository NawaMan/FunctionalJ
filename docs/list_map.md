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


I will write this -- promise.

Nawa - 2019-01-08
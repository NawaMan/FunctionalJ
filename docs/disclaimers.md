# Disclaimers

Ok, Here is where I put all my excuse why FunctionalJ is not perfect. :-p
But it needs to be stated as there are quite many actual/potential problems.
First excuse will be that this library is written by me alone (for now)
  and it initially was intended to solve the problem I have
  and later satisfy my curiosity.
So it is not a well grand design and I didn't have all time I need to clean the code.
:-)

Since Java is not a functional language,
  making it so requires pushing the language boundary, bending the language from its intent.
Breaking conventions and best practice is somewhat hard to avoid.
So you might see so many things in FunctionJ that is not conventional or smell bad,
  I would as if you would pay a little less intention to those and see the benefit that it might provide.
If you have way to reduce these while still maintains the benefit,
  I love to hear from you.

## So many methods!
Ok, if you looks at class like `FuncList`, `Result` or even `Func1`,
  you will find that it has so many methods.
I know every well and agree that this is a code smell.
However, without "Extension methods" the only way to have IDE auto complete for the possible function is to put it as a member method.

So in order to allow auto-completion and fluent code style,
  most of those functionality will have to be put in the class;
  hence so many methods.
Also initialy, I naively think that Lombok's extension methods or `Pipeable` can helps me solve this problem.
But Lombok's usages of undocumented functionality in `javac` and `ejc` is not very reliable.
Sometimes code just fail to compile in one of the compiler with little to pin point where the problem is.
This breaks my heart because I really want to use it.
It is a shame that it is 2018 and the language still don't have build in
  and the compilers refuse to make it easy to add. :-(
`Pipeable` is a common solution found in other languages but it has limit success.
If the object in the flow is of the type with generic,
  Java's generic erasure remove the type information and ... well things are mess up.

It is the trade-off that I have made.
I might regret it later but I think these methods are useful at least for me
  and the alternative (such as a separate function) has just so much problem -- mostly from Java generic and erasure.
So if in the future, Java handles generic better (rectify) and has extension methods,
  we can write it with less smell.

## Duplicate methods!
Next, you may notice that many methods found in `FuncList` also exists in `Streamable` which `FuncList` extends.
You might also notice that many of those methods also exists in `Result`.
Good examples are those `mapXXX(..)` which should be extracted to some abstract class/interface and no need to duplicate them.
That was what I thought too until reality kicks in.

There are two problems I faced: Type inference and the lack of type class.

For example: `map(...)` in `FuncList` can be made to behave exactly the same like the one in `Streamable`.
But I duplicate the definitions because I want the method to return `FuncList` and NOT `Streamable`.
Without duplicating the definition, Java will make it returns the originally defined return type.
If `map(...)` does not return `FuncList`,
  we cannot use any method specific to it in the flow.
That was really undesirable.
Initially, I simulate type class.
This is limited success but the code is much difficult to navigate.
So I decided to dissolve them all in its class.

## More ... can't thing of now.











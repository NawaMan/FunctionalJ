package nawaman.functionalj.compose;

import static java.util.Arrays.asList;
import static nawaman.functionalj.FunctionalJ.recusive;
import static nawaman.functionalj.FunctionalJ.streamConcat;
import static nawaman.functionalj.PointFree.pull;
import static nawaman.functionalj.PointFree.pull2;
import static nawaman.functionalj.functions.Absent.__;
import static nawaman.functionalj.functions.StringFuncs.strFormat;
import static nawaman.functionalj.functions.StringFuncs.strFormat2;
import static nawaman.functionalj.functions.StringFuncs.wrapWith;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

import lombok.Builder;
import lombok.val;
import nawaman.functionalj.functions.Func1;
import nawaman.functionalj.functions.Func2;
import nawaman.functionalj.functions.Func5;
import nawaman.functionalj.kinds.Functor;
import nawaman.functionalj.kinds.Monad;
import nawaman.functionalj.types.MayBe;

@SuppressWarnings("javadoc")
public class Generator {
    
    private static final String indent = "    ";
    
    private static final Func1<Object, String> withIndent = withPrefix(indent);
    
    private static final Func1<Integer, Stream<Integer>> startingFrom1 = n -> {
        return IntStream.range(1, n + 1).mapToObj(Integer::valueOf);
    };
    
    public static class Line {
        private List<String> pieces;
        public Line(String ...strings) {
            pieces = asList(strings);
        }
    }
    
    public static interface MethodDefs {
        public Stream<Stream<MethodDef>> methodDefs();
        
        public static MethodDefs of(MethodDef ... defs) {
            return (MethodDefs)(() -> Stream.of(Stream.of(defs)));
        }
        public static MethodDefs of(Stream<MethodDefs> mDs) {
            return (MethodDefs)(() -> mDs.flatMap(m->m.methodDefs()));
        }
        
        public default List<String> toLines() {
            val lines = new ArrayList<String>();
            methodDefs()
            .forEach(defs -> {
                defs.forEach(def -> {
                    def.toLines(indent)
                    .map(withIndent)
                    .forEach(lines::add);
                });
                lines.add("");
            });
            return lines;
        }
    }
    
    @Builder
    public static class MethodDef {
        private List<String> modifiers;
        private String genericDef;
        private String returnType;
        private String name;
        private List<String> params;
        private List<String> body;
        
        public Stream<String> toLines(String indent) {
            val hasBody = body != null;
            val parameters = params != null ? params.stream().collect(joining(", ")) : "";
            val firstLine = Stream.concat(
                    modifiers.stream(),
                    Stream.of(
                        (genericDef == null) ? null : "<" + genericDef + ">",
                        returnType,
                        name + "(" + parameters + (hasBody ? ")" : ");"),
                        hasBody ? "{" : null
                    ))
                    .filter(Objects::nonNull)
                    .collect(joining(" "));
            return streamConcat(
                        Stream.of(firstLine),
                        hasBody ? body.stream().map(withIndent) : null,
                        hasBody ? Stream.of("}")                : null
                    );
        }
    }
    
    public static class FList<A> implements Functor<FList<?>, A> {
        
        private final List<A> list;
        
        public FList(List<A> list) {
            super();
            this.list = list;
        }
        
        @Override
        public <B> FList<B> map(Func1<A, B> mapper) {
            return new FList<B>(list.stream().map(mapper).collect(Collectors.toList()));
        }
        
        public String toString() {
            return list.toString();
        }
        
    }
    
    public static <F, A, B> Functor<F, B> map(Functor<F, A> org, Func1<A, B> mapper) {
        return org.map(mapper);
    }
    public static <M, A, B> Monad<M, B> flatMap(Monad<M, A> org, Func1<A, Monad<M, B>> mapper) {
        return org.flatMap(mapper);
    }
    
    
    public static void main(String[] args) {
        val inputCount = 6;
        val ofMethod    = generateOfMethod();
        val applyMethod = generateApplyMethod();
        val curryMethod = generateCurryMethod();
        
//        val defs = n -> new FList<Func1<Integer, FList<MethodDef>>>(asList(
//                ofMethod, applyMethod, curryMethod
//        )));
//        System.out.println(defs);
        
        System.out.println(pull(new FList<Func1<Integer, String>>(asList(
                i->"I1 = " + i,
                i->"I2 = " + i*i
        )))
        .apply(2));
        
        ;
        System.out.println(pull2(new FList<FList<Func1<Integer, String>>>(asList(new FList<Func1<Integer, String>>(asList(
                i->"I1 = " + i,
                i->"I2 = " + i*i
        )), new FList<Func1<Integer, String>>(asList(
                i->"I1 = " + 2*i,
                i->"I2 = " + 2*i*i
        )))))
        .apply(2));
        
        System.out.println(pull2(new FList<MayBe<Func1<Integer, String>>>(asList(
                MayBe.of((Func1<Integer, String>)(i->"I1 = " + i)),
                MayBe.of((Func1<Integer, String>)(i->"I2 = " + i)),
                MayBe.of((Func1<Integer, String>)(null))
        )))
        .apply(2));
//        
//        // Try Dynamic procy and see if we can get the interface that is called.
//        
//        System.out.println(pull(new MayBe<Func1<Integer, String>>(i->"Hello: " + i)).apply(42));
//        System.out.println(pull(new MayBe<Func1<Integer, String>>(null)).apply(42));
//        
//        System.out.println(map(new MayBe<>(42), i->"The number is " + 42));
//        System.out.println(new MayBe<>(42).map(i->"The number is " + 42));
//        
//
////        System.out.println(map(new MayBe<>(42), i->"The number is " + 42));
//        System.out.println(new MayBe<>(42).flatMap(  i->new MayBe<>("The number is " + 42)));
//        System.out.println(new MayBe<>(null).flatMap(i->new MayBe<>("The number is " + 42)));
//        
//        System.out.println(flatMap(new MayBe<>(42),   i->new MayBe<>("The number is " + 42)));
//        System.out.println(flatMap(new MayBe<>(null), i->new MayBe<>("The number is " + 42)));
//        
//        
//        val applyEachMethod = generateApplyEachMethod();
//        
//        val eachAbsents = gnerateEachAbsent(inputCount, (paramCount, absentCount, fullList, absentList, inputList) -> {
//            String retType = "Func" + absentCount + "<" + absentList.stream().map(i -> "I" + i).collect(joining(",")) + ",R>";
//            val params = IntStream
//                .range(1, paramCount + 1)
//                .mapToObj(i -> absentList.contains(i) ? "Absent a" + i : "I"+i + " i"+i)
//                .collect(Collectors.toList());
//            val inputs = "(" + absentList.stream().map(i -> "i" + i).collect(joining(",")) + ")";
//            val callInputs = IntStream.range(1, paramCount + 1).mapToObj(i -> "i" + i).collect(joining(","));
//            
//            val method = new MethodDef.MethodDefBuilder()
//                          .modifiers(   asList("public", "default"))
//                          .returnType(  retType)
//                          .name(        "apply")
//                          .params(      params)
//                          .body(        asList("return " + inputs + " -> this.apply(" + callInputs +");"))
//                      .build();
//            
//            return method;
//        });
//        
//        val funcOfMethods
//        = Stream.of(
//            ofMethod,
//            applyMethod,
//            curryMethod
//        ).map(applyWith(inputCount));
//        
//        MethodDefs.of(funcOfMethods)
//        .toLines()
//        .forEach(System.out::println);
//        
//        IntStream
//        .range(1, inputCount + 1)
//        .mapToObj(i -> applyEachMethod.apply(__, i));
//        
//        IntStream
//        .range(1, inputCount + 1)
//        .forEach(i ->{
//            applyEachMethod.apply(inputCount, i)
//            .toLines(indent)
//            .map(withIndent)
//            .forEach(System.out::println);
//        });
//        
//        eachAbsents.forEach(methods -> {
//            methods.forEach(method -> {
//                method
//                .toLines(indent)
//                .forEach(System.out::println);
//                System.out.println();
//            });
//        });
//        
//        
//        
        
//        val factorial = recusive((Func1<Long, Long> self, Long n)->{
//            return (n <= 1) ? 1L : n * self.apply(n - 1);
//        });
//        LongStream
//            .range(1, 5)
//            .mapToObj(Long::valueOf)
//            .map(factorial)
//            .forEach(System.out::println);
//        
//        val fibonaci = recusive((Func1<Long, Long> self, Long n)->{
//            return (n <= 2) ? 1L : self.apply(n - 2) + self.apply(n - 1);
//        });
//        LongStream
//            .range(1, 30)
//            .mapToObj(Long::valueOf)
//            .map(fibonaci)
//            .forEach(System.out::println);
    }

    private static <I, O> Function<Func1<I, O>, O> applyWith(I inputCount) {
        return f->f.apply(inputCount);
    }
    
    private static Func1<Integer, FList<MethodDef>> generateOfMethod() {
        val ofMethod = Func1.of((Integer n) -> {
            val _I1toInR_
                    = startingFrom1
                    .andThen(mapEach(withPrefix("I")))
                    .andThen(appendWith("R"))
                    .andThen(joinAllBy(","))
                    .apply(n);
            val funcType  = "Func" + n + _I1toInR_;
            return new FList<MethodDef>(asList(
                    new MethodDef.MethodDefBuilder()
                        .modifiers(      asList("public", "static"))
                        .genericDef(     _I1toInR_)
                        .returnType(     funcType)
                        .name(           "of")
                        .params(asList(  funcType + " f"))
                        .body(asList(    "return f;"))
                        .build()));
        });
        return ofMethod;
    }
    
    private static Func1<Integer, FList<MethodDef>> generateApplyMethod() {
        val applyMethod = Func1.of((Integer n) -> {
            val _I1i1_to_Inin = startingFrom1
                    .andThen(mapEach(strFormat("I%1$s i%1$s")))
                    .andThen(toList())
                    .apply(n);
            return new FList<MethodDef>(asList(
                    new MethodDef.MethodDefBuilder()
                    .modifiers(    asList("public"))
                    .returnType(   "R")
                    .name(         "apply")
                    .params(       _I1i1_to_Inin)
                    .build()));
        });
        return applyMethod;
    }
    
    private static Func2<Integer, Integer, MethodDef> generateApplyEachMethod() {
        return Func2.of((Integer n, Integer a) -> {
            val returnThisApply = generateReturnThisApply();
            val retType
                    = startingFrom1
                    .andThen(selectOnly(onesThatAreNot(a)))
                    .andThen(mapEach(strFormat("I%s")))
                    .andThen(appendWith("R"))
                    .andThen(joinAllBy(","))
                    .andThen(wrapWith("Func" + (n - 1) + "<", ">"));
            val params = strFormat("I%1$s i%1$s").apply(a);
            val inputs
                    = startingFrom1
                    .andThen(selectOnly(onesThatAreNot(a)))
                    .andThen(mapEach(strFormat("i%s")))
                    .andThen(joinAllBy(","))
                    .andThen(wrapWith("(", ")"))
                    .apply(n);
            return new MethodDef.MethodDefBuilder()
                    .modifiers(   asList("public", "default"))
                    .returnType(  retType.apply(n))
                    .name(        withPrefix("apply").apply(a))
                    .params(      asList(params))
                    .body(        asList(returnThisApply.apply(n, inputs)))
                    .build();
        });
    }

    private static Func1<Integer, FList<MethodDef>> generateCurryMethod() {
        return Func1.of((Integer n) -> {
            val returnThisApply = generateReturnThisApply();
            val curryInput = startingFrom1
                    .andThen(mapEach(strFormat("i%s")))
                    .andThen(joinAllBy("->"))
                    .apply(n);
            return new FList<>(asList(
                    new MethodDef.MethodDefBuilder()
                    .modifiers(   asList("public", "default"))
                    .returnType(  curryReturnType().apply(n))
                    .name(        "curry")
                    .body(        asList(returnThisApply.apply(n, curryInput)))
                    .build()));
        });
    }

    private static Func2<Integer, String, String> generateReturnThisApply() {
        return Func2.of((Integer n, String input) -> {
            val params = startingFrom1
                    .andThen(mapEach(strFormat("i%s")))
                    .andThen(joinAllBy(", "))
                    .apply(n);
            return strFormat2("return %s -> this.apply(%s);").apply(input, params);
        });
    }

    private static List<List<MethodDef>> gnerateEachAbsent(int n, Func5<Integer, Integer, List<Integer>, List<Integer>, List<Integer>, MethodDef> processLists) {
        val methodList = new ArrayList<List<MethodDef>>();
        IntStream.range(1, n).forEach(i ->{
            val methods = new ArrayList<MethodDef>();
            
            val list0 = new ArrayList<Integer>(n);
            val list1 = new ArrayList<Integer>(i);
            for (int d = 0; d < n; d++) {
                list0.add(d + 1);
            }
            for (int d = 0; d < i; d++) {
                list1.add(0);
            }
            int all = (int)Math.round(Math.pow(n, i));
            for (int a = 0; a < all; a++) {
                boolean isValid = true;
                for (int d = 0; d < i; d++) {
                    int sc = (int)Math.round(Math.pow(n, d));
                    int e = ((a/sc) % n) + 1;
                    int idx = i - d - 1;
                    if ((idx < (i - 1)) && (list1.get(idx + 1) <= e)) {
                      isValid = false;
                      continue;
                    }
                    
                    list1.set(idx, e);
                }
                if (!isValid)
                    continue;
                
                val list2 = new ArrayList<Integer>();
                for (int d = 0; d < n; d++) {
                    if (list1.contains(d + 1))
                        continue;
                    
                    list2.add(d + 1);
                }
                
                val method = processLists.apply(n , i, list0, list1, list2);
                methods.add(method);
            }
            
            methodList.add(methods);
        });
        return methodList;
    }

    private static <T> Predicate<T> onesThatAreNot(T value) {
        return i -> !Objects.equals(i, value);
    }
    
    private static <T> Function<Stream<Integer>, ? extends Stream<Integer>> selectOnly(
            Predicate<? super Integer> thatIsNot) {
        return (Stream<Integer> s) -> s.filter(thatIsNot);
    }
    
    private static Function<Stream<String>, List<String>> toList() {
        return (Stream<String> s) -> (List<String>)s.collect(Collectors.toList());
    }
    
    private static <S extends Stream<String>> Func1<S, Stream<String>> appendWith(String returnType) {
        return (S s)->Stream.concat(s, Stream.of(returnType));
    }
    
    private static Func1<Object, String> withPrefix(String prefix) {
        return o -> prefix + o;
    }
    
    private static <S extends Stream<String>> Func1<S, String> joinAllBy(String delimiter) {
        return s->s.collect(joining(delimiter));
    }
    
    private static Function<? super Stream<Integer>, ? extends Stream<String>> mapEach(Func1<Object, String> mapper) {
        return s -> {
            return s.map(mapper);
        };
    }
    
    private static Func1<Integer, String> curryReturnType() {
        return recusive(__, 1, (self, n, idx)->{
            val returnType = (n == 1) ? "R" : self.apply(n - 1, idx + 1);
            return "Func1<I" + idx + "," + returnType + ">";
        });
    }
    
}

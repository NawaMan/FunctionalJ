//  Copyright (c) 2017 Nawapunth Manusitthipol (NawaMan).
//  ------------------------------------------------------------------------
//  All rights reserved. This program and the accompanying materials
//  are made available under the terms of the Eclipse Public License v1.0
//  and Apache License v2.0 which accompanies this distribution.
//
//      The Eclipse Public License is available at
//      http://www.eclipse.org/legal/epl-v10.html
//
//      The Apache License v2.0 is available at
//      http://www.opensource.org/licenses/apache2.0.php
//
//  You may elect to redistribute this code under either of these licenses.
//  ========================================================================
package functionalj;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toMap;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import functionalj.functions.Absent;
import functionalj.functions.Func1;
import functionalj.functions.Func2;
import functionalj.functions.Func3;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;
import lombok.val;
import lombok.experimental.Accessors;
import lombok.experimental.Wither;
import tuple.ImmutableTuple2;

/**
 * Collection of useful methods for functional programming.
 * 
 * @author NawaMan -- nawa@nawaman.net
 */
@SuppressWarnings("javadoc")
public class FunctionalJ {
    
    /**
     * A shorter way to use Function.identity() -- alias for itself() and themAll().
     * 
     * @param <TYPE> the type of it.
     * @return the function that take it and return it.
     **/
    public static <TYPE> Func1<TYPE, TYPE> it() {
        return it -> it;
    }
    /**
     * A shorter way to use Function.identity() -- alias for it() and themAll().
     * 
     * @param <TYPE> the type of it.
     * @return the function that take it and return it.
     **/
    public static <TYPE> Func1<TYPE, TYPE> itself() {
        return it -> it;
    }
    
    /**
     * A shorter way to use Function.identity() -- alias for it() and itself().
     * 
     * @param <TYPE> the type of it.
     * @return the function that take it and return it.
     **/
    public static <TYPE> Func1<TYPE, TYPE> themAll() {
        return it -> it;
    }
    
    public static <IN extends List<? extends OUT>, OUT> Func1<? super IN, Stream<? extends OUT>> allLists() {
        return it -> it.stream();
    }
    
    public static <IN, OUT> Func1<IN, Stream<OUT>> allList(Func1<IN, ? extends List<OUT>> mapper) {
        return it -> mapper.apply(it).stream();
    }
    
    public static <IN> Func1<IN, Stream<IN>> delimitWith(IN delimiter) {
        val isFirst = new AtomicBoolean(true);
        return in -> {
            if (isFirst.getAndSet(false))
                return Stream.of(in);
            return Stream.of(delimiter, in);
        };
    }
    
    public static <IN> Func1<IN, Stream<IN>> delimitWith(Supplier<? extends IN> delimiter) {
        val isFirst = new AtomicBoolean(true);
        return in -> {
            if (isFirst.getAndSet(false))
                return Stream.of(in);
            return Stream.of(delimiter.get(), in);
        };
    }
    
    public static <IN> Func1<IN, Stream<IN>> delimitWith(Func1<IN, ? extends IN> delimiter) {
        val isFirst = new AtomicBoolean(true);
        return in -> {
            if (isFirst.getAndSet(false))
                return Stream.of(in);
            return Stream.of(delimiter.apply(in), in);
        };
    }
    
    /**
     * My crazy obsession of making the code read sometimes make me do this kind of thing.
     * This useless class will make 
     * 
     **/
    public static class ByThat<THING> {
        public final THING that;
        public ByThat(THING that) {
            this.that = that;
        }
    }
    
    public static <FIELD> ByThat<FIELD> by(FIELD field) {
        return new ByThat<FIELD>(field);
    }
    
    public static <INPUT> Predicate<INPUT> only(Function<INPUT, Boolean> check) {
        return input -> check.apply(input);
    }
    
    public static <INPUT, OUTPUT> Function<INPUT, OUTPUT> withIndex(BiFunction<INPUT, Integer, OUTPUT> body) {
        val index = new AtomicInteger();
        return input -> body.apply(input, index.getAndIncrement());
    }
    
    public static <INPUT, OUTPUT> Function<INPUT, OUTPUT> usingIndex(Function<Integer, OUTPUT> body) {
        val index = new AtomicInteger();
        return input -> body.apply(index.getAndIncrement());
    }
    
    public static class WithIndex<DATA> extends ImmutableTuple2<DATA, Integer> {
        public WithIndex(DATA _1, Integer _2) {
            super(_1, _2);
        }
        public int  index() { return _2.intValue(); }
        public DATA value()  { return _1; }
        
        public String toString() {
            // TODO - Umm .. this feels useful but it will break Tuple2.toString() contract.
            return "[#" + _2 + ":" + _1 + "]";
        }
    }
    
    public static interface WithIndexFunction<INPUT, OUTPUT> extends Function<WithIndex<INPUT>, OUTPUT> {
        
    }
    
    public static <INPUT, OUTPUT> Function<INPUT, OUTPUT> withIndex(WithIndexFunction<INPUT, OUTPUT> body) {
        val index = new AtomicInteger();
        return input -> {
            val withIndex = new WithIndex<INPUT>(input, index.getAndIncrement());
            return body.apply(withIndex);
        };
    }
    
    public static <INPUT> Function<INPUT, WithIndex<INPUT>> withIndex() {
        val index = new AtomicInteger();
        return input -> new WithIndex<INPUT>(input, index.getAndIncrement());
    }
    
    public static <INPUT, OUTPUT> Consumer<INPUT> withIndex(BiConsumer<INPUT, Integer> body) {
        val index = new AtomicInteger();
        return input -> body.accept(input, index.getAndIncrement());
    }
    
    public static <INPUT, OUTPUT> Consumer<INPUT> withIndex(Consumer<Integer> body) {
        val index = new AtomicInteger();
        return input -> body.accept(index.getAndIncrement());
    }
    
    public static <INPUT, OUTPUT> Function<INPUT, OUTPUT> cacheFor(Function<INPUT, OUTPUT> inFunction) {
        val cache = new ConcurrentHashMap<INPUT, OUTPUT>();
        return in -> cache.computeIfAbsent(in, inFunction::apply);
    }
    public static <INPUT, OUTPUT> Function<INPUT, OUTPUT> cacheFor(long time, Function<INPUT, OUTPUT> inFunction) {
        val cache       = new ConcurrentHashMap<INPUT, OUTPUT>();
        val expiredTime = new ConcurrentHashMap<INPUT, Long>();
        return in -> {
            if (expiredTime.contains(in)
             && expiredTime.get(in) > System.currentTimeMillis()) {
                cache.remove(in);
            }
            return cache.computeIfAbsent(in, key->{
                expiredTime.put(key, System.currentTimeMillis() + time);
                return inFunction.apply(key);
            });
        };
    }
    public static <INPUT, OUTPUT> Func1<INPUT, OUTPUT> cacheFor(Func1<INPUT, OUTPUT> inFunction) {
        val cache = new ConcurrentHashMap<INPUT, OUTPUT>();
        return in -> cache.computeIfAbsent(in, inFunction::apply);
    }
    public static <INPUT, OUTPUT> Func1<INPUT, OUTPUT> cacheFor(long time, Func1<INPUT, OUTPUT> inFunction) {
        val cache       = new ConcurrentHashMap<INPUT, OUTPUT>();
        val expiredTime = new ConcurrentHashMap<INPUT, Long>();
        return in -> {
            if (expiredTime.contains(in)
             && expiredTime.get(in) > System.currentTimeMillis()) {
                cache.remove(in);
            }
            return cache.computeIfAbsent(in, key->{
                expiredTime.put(key, System.currentTimeMillis() + time);
                return inFunction.apply(key);
            });
        };
    }
    
    @SuppressWarnings("unchecked")
    public static <TYPE> Supplier<TYPE> lazy(Supplier<TYPE> supplier) {
        val reference = new AtomicReference<Object>();
        val startKey  = new Object();
        return ()->{
            if (reference.compareAndSet(null, startKey)) {
                try {
                    val value = supplier.get();
                    reference.set((Supplier<TYPE>)(()->value));
                } catch (RuntimeException e) {
                    reference.set(e);
                }
            }
            while (!(reference.get() instanceof Supplier)) {
                if (reference.get() instanceof RuntimeException)
                    throw (RuntimeException)reference.get();
            }
            return ((Supplier<TYPE>)reference.get()).get();
        };
    }
    
    public static <I, O1, I2> Predicate<I> only(
            java.util.function.Function<I,O1> head,
            java.util.function.BiPredicate<O1, I2> tail,
            I2 tailInput) {
        return i->tail.test(head.apply(i), tailInput);
    }
    
    @SafeVarargs
    public static <T> Stream<T> streamConcat(Stream<T> ...  streams) {
        return stream(streams)
                .filter(Objects::nonNull)
                .flatMap(themAll());
    }
    
    public static <I1, I2, R> Func1<I1, R> recusive(Absent absent, I2 i2, Func3<Func2<I1, I2, R>, I1, I2, R> func3) {
        Func2<I1, I2, R> grt = recusive(func3);
        return grt.apply(absent, i2);
    }
    public static <I1, I2, R> Func1<I2, R> recusive(I1 i1, Absent absent, Func3<Func2<I1, I2, R>, I1, I2, R> func3) {
        Func2<I1, I2, R> grt = recusive(func3);
        return grt.apply(i1, absent);
    }
    public static <I1, I2, R> Func2<I1, I2, R> recusive(
            Func3<Func2<I1, I2, R>, I1, I2, R> func3) {
        AtomicReference<Func2<I1, I2, R>> selfRef = new AtomicReference<>();
        Supplier<Func2<I1, I2, R>> self = selfRef::get;
        Func2<I1, I2, R> selfFunc = (i1, i2) -> func3.apply(self.get(), i1, i2);
        selfRef.set(selfFunc);
        return selfFunc;
    }
    public static <I, R> Func1<I, R> recusive(Func2<Func1<I, R>, I, R> func2) {
        AtomicReference<Func1<I, R>> selfRef = new AtomicReference<>();
        Supplier<Func1<I, R>> self = selfRef::get;
        Func1<I, R> selfFunc = (_i) -> func2.apply(self.get(), _i);
        selfRef.set(cacheFor(selfFunc));
        return selfFunc;
    }
    
    public static Consumer<String> throwThis(Function<String, ? extends RuntimeException> exceptionCreator) {
        return errMsg -> {
            if (errMsg == null)
                return;
            
            val exception = exceptionCreator.apply(errMsg);
            if (exception == null)
                return;
            
            throw exception;
        };
    }
    
    public static interface Data {
        @SuppressWarnings("unchecked")
		public default <D extends Data> D me() { return (D)this; }
    }
    
    public static interface Person extends Data {
        
        public String name();
        
    }
//    
//    public static interface PersonField<HOST> extends ObjectField<HOST, Person> {
//        public static PersonField<Person> thePerson = Person::me;
//        
//        public default StringField<HOST> name() {
//            return h->linkTo(Person::name).apply(h);
//        }
//    }
//    
    @Value
    @Builder
    @Accessors(fluent=true)
    @Wither
    public static class ImmutablePerson implements Person {
        private String name;
    }
    
    
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Accessors(fluent=true)
    public static class MutablePerson implements Person {
        @Setter
        private String name;
        
        public Person asImmutable() {
            return new Person() {
                @Override public String name() { return MutablePerson.this.name;  }
            };
        }
    }
    
    public static interface Company extends Data {
        
        public Person person();
        public List<Person> people();
        
    }
    
    @Value
    @Accessors(fluent=true)
    @Builder
    @Wither
    public static class ImmutableCompany implements Company {
        private Person person;
        private List<Person> people;
    }
//    
//    public static interface CompanyField<HOST> extends ObjectField<HOST, Company> {
//        
//        public static CompanyField<Company> theCompany = Company::me;
//        
//        public default PersonField<HOST> person() {
//            return h->linkTo(Company::person).apply(h);
//        }
//        public default CollectionField<HOST, Person, List<Person>> people() {
//            return h->linkTo(Company::people).apply(h);
//        }
//    }
//    
    public static void main(String[] args) {
        System.out.println(
                Stream.of("One", "Two", "Three")
                    .collect(toMap(it(), withIndex((s,index)->index))));
        System.out.println(
                Stream.of("One", "Two", "Three")
                    .collect(toMap(it(), usingIndex(it()))));
        
        Stream.of("One", "Two", "Three").forEach(withIndex((str, idx)->{
            System.out.println(idx + ": " + str);
        }));
//        
//        System.out.println(
//                Stream.of(new ImmutablePerson("John"), new ImmutablePerson("Jack"), new ImmutablePerson("James"), null)
//                .filter(by(thePerson.name()).that.startsWith("Ja")
//                   .and(by(thePerson.name()).that.matches("^.*s$")))
//                .map(thePerson.name().concat(" is the name."))
//                .collect(toMap(it(), withIndex(it()))));
//        
//        Stream.of(new ImmutableCompany(new ImmutablePerson("John"), Collections.emptyList()),
//                  new ImmutableCompany(new ImmutablePerson("Smith"), Collections.emptyList()))
//            .filter(theCompany.person().name().startsWith("J"))
//            .forEach(System.out::println);
//        
//        Stream.of(new ImmutableCompany(new ImmutablePerson("John"), Collections.emptyList()),
//                  new ImmutableCompany(new ImmutablePerson("Smith"), Collections.emptyList()))
//            .filter(theCompany.person().name().startsWith("S"))
//            .forEach(System.out::println);
//        
//        Stream.of(new ImmutableCompany(new ImmutablePerson("John"), Arrays.asList(new ImmutablePerson("Adam"))),
//                new ImmutableCompany(new ImmutablePerson("Smith"), Collections.emptyList()))
//          .filter(by(theCompany.people()).that.contains(thePerson.name().startsWith("A")))
//          .forEach(System.out::println);
    }
    
}

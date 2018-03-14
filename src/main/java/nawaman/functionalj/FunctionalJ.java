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
package nawaman.functionalj;

import static nawaman.functionalj.FunctionalJ.CompanyField.theCompany;
import static nawaman.functionalj.FunctionalJ.PersonField.thePerson;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
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

import org.hamcrest.core.IsNot;

import static java.util.stream.Collectors.toMap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;
import lombok.val;
import lombok.experimental.Accessors;
import lombok.experimental.Wither;
import nawaman.functionalj.fields.CollectionField;
import nawaman.functionalj.fields.ObjectField;
import nawaman.functionalj.fields.StringField;

/**
 * Collection of useful methods for functional programming.
 * 
 * @author NawaMan -- nawa@nawaman.net
 */
public class FunctionalJ {
    
    /**
     * A shorter way to use Function.identity().
     * 
     * @param <TYPE> the type of it.
     * @return the function that take it and return it.
     **/
    public static <TYPE> Function<TYPE, TYPE> it() {
        return it -> it;
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
    
    public static <INPUT, OUTPUT> Function<INPUT, OUTPUT> withIndex(BiFunction<INPUT, Integer, OUTPUT> body) {
        val index = new AtomicInteger();
        return input -> body.apply(input, index.getAndIncrement());
    }
    
    public static <INPUT, OUTPUT> Function<INPUT, OUTPUT> withIndex(Function<Integer, OUTPUT> body) {
        val index = new AtomicInteger();
        return input -> body.apply(index.getAndIncrement());
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
    
    public static <TYPE> Supplier<TYPE> lazy(Supplier<TYPE> supplier) {
        val isFirst   = new AtomicBoolean(true);
        val reference = new AtomicReference<Supplier<TYPE>>();
        val exception = new AtomicReference<Throwable>();
        return ()->{
            while(reference.get() == null) {
                if (exception.get() != null)
                    throw (RuntimeException)exception.get();
                
                if (isFirst.compareAndSet(true, false)) {
                    try {
                        val value = supplier.get();
                        reference.set(()->value);
                    } catch (RuntimeException e) {
                        exception.set(e);
                    }
                }
            }
            return reference.get().get();
        };
    }
    
    public static <I, O1, I2> Predicate<I> only(
            java.util.function.Function<I,O1> head,
            java.util.function.BiPredicate<O1, I2> tail,
            I2 tailInput) {
        return i->tail.test(head.apply(i), tailInput);
    }
    
    public static interface Data {
        public default <D extends Data> D me() { return (D)this; }
    }
    
    public static interface Person extends Data {
        
        public String name();
        
    }
    
    public static interface PersonField<HOST> extends ObjectField<HOST, Person> {
        public static PersonField<Person> thePerson = Person::me;
        
        public default StringField<HOST> name() {
            return h->linkTo(Person::name).apply(h);
        }
    }
    
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
    
    public static interface CompanyField<HOST> extends ObjectField<HOST, Company> {
        
        public static CompanyField<Company> theCompany = Company::me;
        
        public default PersonField<HOST> person() {
            return h->linkTo(Company::person).apply(h);
        }
        public default CollectionField<HOST, Person, List<Person>> people() {
            return h->linkTo(Company::people).apply(h);
        }
    }
    
    public static void main(String[] args) {
        System.out.println(
                Stream.of("One", "Two", "Three")
                    .collect(toMap(it(), withIndex((s,index)->index))));
        System.out.println(
                Stream.of("One", "Two", "Three")
                    .collect(toMap(it(), withIndex(it()))));
        
        Stream.of("One", "Two", "Three").forEach(withIndex((str, idx)->{
            System.out.println(idx + ": " + str);
        }));
        
        System.out.println(
                Stream.of(new ImmutablePerson("John"), new ImmutablePerson("Jack"), new ImmutablePerson("James"), null)
                .filter(by(thePerson.name()).that.startsWith("Ja")
                   .and(by(thePerson.name()).that.matches("^.*s$")))
                .map(thePerson.name().concat(" is the name."))
                .collect(toMap(it(), withIndex(it()))));
        
        Stream.of(new ImmutableCompany(new ImmutablePerson("John"), Collections.emptyList()),
                  new ImmutableCompany(new ImmutablePerson("Smith"), Collections.emptyList()))
            .filter(theCompany.person().name().startsWith("J"))
            .forEach(System.out::println);
        
        Stream.of(new ImmutableCompany(new ImmutablePerson("John"), Collections.emptyList()),
                  new ImmutableCompany(new ImmutablePerson("Smith"), Collections.emptyList()))
            .filter(theCompany.person().name().startsWith("S"))
            .forEach(System.out::println);
        
        Stream.of(new ImmutableCompany(new ImmutablePerson("John"), Arrays.asList(new ImmutablePerson("Adam"))),
                new ImmutableCompany(new ImmutablePerson("Smith"), Collections.emptyList()))
          .filter(by(theCompany.people()).that.contains(thePerson.name().startsWith("A")))
          .forEach(System.out::println);
    }
    
}

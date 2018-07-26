//  ========================================================================
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
package functionalj.types.list;

import static java.util.Collections.unmodifiableList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import functionalj.types.stream.Streamable;
import functionalj.types.tuple.Tuple;
import functionalj.types.tuple.Tuple2;
import functionalj.types.tuple.Tuple3;
import functionalj.types.tuple.Tuple4;
import functionalj.types.tuple.Tuple5;
import functionalj.types.tuple.Tuple6;
import lombok.val;

@SuppressWarnings("javadoc")
public final class ImmutableList<DATA> extends FunctionalList<DATA> {
    
    private final Function<Stream<DATA>, Stream<DATA>> noAction = Function.identity();
    
    private final static ImmutableList<?> EMPTY = new ImmutableList<>(Collections.emptyList());
    
    @SuppressWarnings("unchecked")
    public static final <T> ImmutableList<T> empty() {
        return (ImmutableList<T>)EMPTY;
    }
    public static <T> ImmutableList<T> of(Collection<T> data) {
        return new ImmutableList<T>(data);
    }
    @SafeVarargs
    public static <T> ImmutableList<T> of(T ... data) {
        return new ImmutableList<>(Arrays.asList(data));
    }
    @SuppressWarnings("unchecked")
    public static <T> ImmutableList<T> of(Streamable<T, ?> streamable) {
        if (streamable instanceof ImmutableList)
            return (ImmutableList<T>)streamable;
        if (streamable == null)
            return ImmutableList.empty();
        
        return new ImmutableList<T>(streamable.toList());
    }
    public static <T> ImmutableList<T> of(Stream<T> stream) {
        return new ImmutableList<T>(stream.collect(Collectors.toList()));
    }
    @SuppressWarnings("unchecked")
    public static <T> ImmutableList<T> of(ReadOnlyList<T, ?> readOnlyList) {
        if (readOnlyList instanceof ImmutableList)
            return (ImmutableList<T>)readOnlyList;
        if (readOnlyList == null)
            return ImmutableList.empty();
        
        return new ImmutableList<T>(readOnlyList.toList());
    }
    @SuppressWarnings("unchecked")
    public static <T> ImmutableList<T> of(IFunctionalList<T, ?> functionalList) {
        if (functionalList instanceof ImmutableList)
            return (ImmutableList<T>)functionalList;
        if (functionalList == null)
            return ImmutableList.empty();
        
        return new ImmutableList<T>(functionalList.toList());
    }
    public static <T> ImmutableList<T> of(FunctionalList<T> functionalList) {
        if (functionalList instanceof ImmutableList)
            return (ImmutableList<T>)functionalList;
        if (functionalList == null)
            return ImmutableList.empty();
        
        return new ImmutableList<T>(functionalList.toList());
    }
    @SafeVarargs
    public static <T> ImmutableList<T> listOf(T ... data) {
        return new ImmutableList<T>(Arrays.asList(data));
    }
    
    private final List<DATA> data;
    
    public ImmutableList(Collection<DATA> data) {
        if (data == null) {
            this.data = Collections.emptyList();
        } else if (data instanceof ImmutableList) {
            this.data = ((ImmutableList<DATA>)data).data;
        } else {
            val list = new ArrayList<DATA>();
            data.forEach(list::add);
            this.data = unmodifiableList(list);
        }
    }
    
    @Override
    public FunctionalList<DATA> streamFrom(Function<Supplier<Stream<DATA>>, Stream<DATA>> supplier) {
        return new FunctionalListStream<DATA, DATA>((Supplier<Stream<DATA>>) ()->{
                    return supplier.apply(()->{
                        return (Stream<DATA>)ImmutableList.this.stream();
                    });
                },
                noAction);
    }
    
    @Override
    public Stream<DATA> stream() {
        return data.stream();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public FunctionalList<DATA> subList(int fromIndexInclusive, int toIndexExclusive) {
        if (fromIndexInclusive < 0)
            throw new IndexOutOfBoundsException("fromIndexInclusive: " + fromIndexInclusive);
        if (toIndexExclusive < 0)
            throw new IndexOutOfBoundsException("toIndexExclusive: " + toIndexExclusive);
        if (fromIndexInclusive > toIndexExclusive)
            throw new IndexOutOfBoundsException("fromIndexInclusive: " + fromIndexInclusive + ", toIndexExclusive: " + toIndexExclusive);
        if (fromIndexInclusive == toIndexExclusive)
            return (FunctionalList<DATA>)ImmutableList.empty();
        if ((fromIndexInclusive == 0) && (toIndexExclusive == data.size()))
            return this;
        
        return stream(stream -> stream.skip(fromIndexInclusive).limit(toIndexExclusive - fromIndexInclusive));
    }
    
    @Override
    public ImmutableList<DATA> toImmutableList() {
        return this;
    }
    @Override
    public List<DATA> toList() {
        return this;
    }

    @Override
    public int size() {
        return data.size();
    }

    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }

    @Override
    public <TARGET> TARGET[] toArray(TARGET[] seed) {
        return data.toArray(seed);
    }

    @Override
    public DATA get(int index) {
        return data.get(index);
    }

    @Override
    public int indexOf(Object o) {
        return data.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return data.lastIndexOf(o);
    }

    @Override
    public ListIterator<DATA> listIterator() {
        return data.listIterator();
    }

    @Override
    public ListIterator<DATA> listIterator(int index) {
        return data.listIterator();
    }

    // TODO - Put a garantee on this
    // TODO - Extract these out ... but should still make it easy to call.
    //        Like ... Split(list).to(predicate, ...)
    
    public Tuple2<FunctionalList<DATA>, FunctionalList<DATA>> split(
            Predicate<? super DATA> predicate) {
        val temp = this.map(
                it -> predicate.test(it) ? 0 : 1,
                it -> it
        ).toImmutableList();
        val list1 = temp.filter(it -> it._1() == 0).map(it -> it._2()).toImmutableList();
        val list2 = temp.filter(it -> it._1() == 1).map(it -> it._2()).toImmutableList();
        return Tuple.of(
                list1,
                list2
        );
    }
    
    public Tuple3<FunctionalList<DATA>, FunctionalList<DATA>, FunctionalList<DATA>> split(
            Predicate<? super DATA> predicate1,
            Predicate<? super DATA> predicate2) {
        val temp = this.map(
                it -> predicate1.test(it) ? 0
                    : predicate2.test(it) ? 1
                    :                       2,
                it -> it
        ).toImmutableList();
        val list1 = temp.filter(it -> it._1() == 0).map(it -> it._2()).toImmutableList();
        val list2 = temp.filter(it -> it._1() == 1).map(it -> it._2()).toImmutableList();
        val list3 = temp.filter(it -> it._1() == 2).map(it -> it._2()).toImmutableList();
        return Tuple.of(
                list1,
                list2,
                list3
        );
    }

    public Tuple4<FunctionalList<DATA>, FunctionalList<DATA>, FunctionalList<DATA>, FunctionalList<DATA>> split(
            Predicate<? super DATA> predicate1,
            Predicate<? super DATA> predicate2,
            Predicate<? super DATA> predicate3) {
        val temp = this.map(
                it -> predicate1.test(it) ? 0
                    : predicate2.test(it) ? 1
                    : predicate3.test(it) ? 2
                    :                       3,
                it -> it
        ).toImmutableList();
        val list1 = temp.filter(it -> it._1() == 0).map(it -> it._2()).toImmutableList();
        val list2 = temp.filter(it -> it._1() == 1).map(it -> it._2()).toImmutableList();
        val list3 = temp.filter(it -> it._1() == 2).map(it -> it._2()).toImmutableList();
        val list4 = temp.filter(it -> it._1() == 3).map(it -> it._2()).toImmutableList();
        return Tuple.of(
                list1,
                list2,
                list3,
                list4
        );
    }

    public Tuple5<FunctionalList<DATA>, FunctionalList<DATA>, FunctionalList<DATA>, FunctionalList<DATA>, FunctionalList<DATA>> split(
            Predicate<? super DATA> predicate1,
            Predicate<? super DATA> predicate2,
            Predicate<? super DATA> predicate3,
            Predicate<? super DATA> predicate4) {
        val temp = this.map(
                it -> predicate1.test(it) ? 0
                    : predicate2.test(it) ? 1
                    : predicate3.test(it) ? 2
                    : predicate4.test(it) ? 3
                    :                       4,
                it -> it
        ).toImmutableList();
        val list1 = temp.filter(it -> it._1() == 0).map(it -> it._2()).toImmutableList();
        val list2 = temp.filter(it -> it._1() == 1).map(it -> it._2()).toImmutableList();
        val list3 = temp.filter(it -> it._1() == 2).map(it -> it._2()).toImmutableList();
        val list4 = temp.filter(it -> it._1() == 3).map(it -> it._2()).toImmutableList();
        val list5 = temp.filter(it -> it._1() == 4).map(it -> it._2()).toImmutableList();
        return Tuple.of(
                list1,
                list2,
                list3,
                list4,
                list5
        );
    }
    
    public Tuple6<FunctionalList<DATA>, FunctionalList<DATA>, FunctionalList<DATA>, FunctionalList<DATA>, FunctionalList<DATA>, FunctionalList<DATA>> split(
            Predicate<? super DATA> predicate1,
            Predicate<? super DATA> predicate2,
            Predicate<? super DATA> predicate3,
            Predicate<? super DATA> predicate4,
            Predicate<? super DATA> predicate5) {
        val temp = this.map(
                it -> predicate1.test(it) ? 0
                    : predicate2.test(it) ? 1
                    : predicate3.test(it) ? 2
                    : predicate4.test(it) ? 3
                    : predicate5.test(it) ? 4
                    :                       5,
                it -> it
        ).toImmutableList();
        val list1 = temp.filter(it -> it._1() == 0).map(it -> it._2()).toImmutableList();
        val list2 = temp.filter(it -> it._1() == 1).map(it -> it._2()).toImmutableList();
        val list3 = temp.filter(it -> it._1() == 2).map(it -> it._2()).toImmutableList();
        val list4 = temp.filter(it -> it._1() == 3).map(it -> it._2()).toImmutableList();
        val list5 = temp.filter(it -> it._1() == 4).map(it -> it._2()).toImmutableList();
        val list6 = temp.filter(it -> it._1() == 5).map(it -> it._2()).toImmutableList();
        return Tuple.of(
                list1,
                list2,
                list3,
                list4,
                list5,
                list6
        );
    }
    
    @Override
    public String toString() {
        return this.data.toString();
    }

    @Override
    public boolean equals(Object o) {
        return this.data.equals(o);
    }

    @Override
    public int hashCode() {
        return this.data.hashCode();
    }
    
    
    
}

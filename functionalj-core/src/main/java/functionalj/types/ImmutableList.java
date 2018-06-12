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
package functionalj.types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.Collections.unmodifiableList;

import lombok.val;

public class ImmutableList<DATA> extends FunctionalList<DATA> {
    
    private final Function<Stream<DATA>, Stream<DATA>> noAction = Function.identity();
    
    private final static ImmutableList EMPTY = new ImmutableList<>(Collections.emptyList());
    
    @SuppressWarnings("unchecked")
    public static final <T> ImmutableList<T> empty() {
        return (ImmutableList<T>)EMPTY;
    }
    
    public static <T> ImmutableList<T> of(Collection<T> data) {
        return new ImmutableList<T>(data);
    }
    
    public static <T> ImmutableList<T> of(T ... data) {
        return new ImmutableList<>(Arrays.asList(data));
    }
    
    @SuppressWarnings("unchecked")
    public static <T> ImmutableList<T> of(ICanStream<T, ?> icanStream) {
        if (icanStream instanceof ImmutableList)
            return (ImmutableList<T>)icanStream;
        if (icanStream == null)
            return ImmutableList.empty();
        
        return new ImmutableList<T>(icanStream.toList());
    }
    
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
            val list = new ArrayList<DATA>(data.size());
            list.addAll(data);
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
    
    @SuppressWarnings("unchecked")
    @Override
    public <TARGET, TARGET_SELF extends ICanStream<TARGET, TARGET_SELF>> TARGET_SELF stream(
            Function<Stream<DATA>, Stream<TARGET>> action) {
        return (TARGET_SELF)new FunctionalListStream<DATA, TARGET>(this, action);
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
    
    public ImmutableList<DATA> toImmutableList() {
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

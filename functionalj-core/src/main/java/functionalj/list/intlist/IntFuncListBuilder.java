package functionalj.list.intlist;

import java.util.Objects;
import java.util.OptionalInt;

import functionalj.stream.intstream.GrowOnlyIntArray;
import functionalj.stream.intstream.IntStreamPlus;
import functionalj.streamable.intstreamable.AsIntStreamable;

public class IntFuncListBuilder {

    private final GrowOnlyIntArray list;
    
    // This constructor must not be public or protected.
    IntFuncListBuilder(GrowOnlyIntArray list) {
        this.list = list;
    }
    
    public IntFuncListBuilder() {
        list = new GrowOnlyIntArray();
    }
    
    public IntFuncListBuilder(int ... values) {
        list = new GrowOnlyIntArray(values);
    }
    
    public IntFuncListBuilder add(int data) {
        list.add(data);
        return this;
    }
    
    public IntFuncList build() {
        int length = list.length();
        AsIntStreamable asStreamable = () -> {
            return list.stream().limit(length);
        };
        return IntFuncList.from(asStreamable);
    }
    
    public IntFuncList toFuncList() {
        return build();
    }
    
    public int size() {
        return list.length();
    }
    public boolean isEmpty() {
        return size() == 0;
    }
    
    public IntStreamPlus stream() {
        return IntStreamPlus.from(list.stream());
    }
    
    public int get(int i) {
        return list.get(i);
    }
    
    public OptionalInt at(int i) {
        if (i < 0 || i >= list.length())
            return OptionalInt.empty();
        
        return OptionalInt.of(list.get(i));
    }
    
    public String toString() {
        return list.toString();
    }
    
    public int hashCode() {
        return list.hashCode();
    }
    
    public boolean equals(IntFuncListBuilder array) {
        return Objects.equals(list, array.list);
    }
}

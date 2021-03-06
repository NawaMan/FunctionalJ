package functionalj.lens.lenses;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import functionalj.function.Func;
import functionalj.function.Func1;
import functionalj.lens.core.AccessParameterized;
import functionalj.list.FuncList;
import lombok.val;

public class ListLensEach <HOST, TYPE, TYPELENS extends AnyLens<HOST, TYPE>> 
                implements FuncListAccess<HOST, TYPE, TYPELENS> {
    
    private final FuncListLens<HOST, TYPE, TYPELENS> parentLens;
    private final Predicate<TYPE>                    checker;
    
    public ListLensEach(FuncListLens<HOST, TYPE, TYPELENS> parentLens, Predicate<TYPE> checker) {
        this.parentLens = parentLens;
        this.checker    = checker;
    }
    
    @Override
    public AccessParameterized<HOST, FuncList<TYPE>, TYPE, TYPELENS> accessParameterized() {
        val read = Func.f((HOST host) -> {
            return apply(host)
                    .filter(checker);
        });
        val specWithSub = new AccessParameterized<HOST, FuncList<TYPE>, TYPE, TYPELENS>() {
            @Override
            public FuncList<TYPE> applyUnsafe(HOST host) throws Exception {
                return read.apply(host);
            }
            @Override
            public TYPELENS createSubAccessFromHost(Function<HOST, TYPE> accessToParameter) {
                return parentLens.accessParameterized().createSubAccessFromHost(accessToParameter);
            }
        };
        return specWithSub;
    }
    
    public Func1<HOST, HOST> changeToNull() {
        return changeTo((TYPE)null);
    }
    
    public Func1<HOST, HOST> changeTo(TYPE newValue) {
        return host -> {
            val orgList = ListLensEach.this.parentLens.apply(host);
            val newList = orgList.mapOnly(checker, __ -> newValue);
            val newHost = ListLensEach.this.parentLens.changeTo(newList).apply(host);
            return newHost;
        };
    }
    
    public Func1<HOST, HOST> changeTo(Supplier<TYPE> supplier) {
        return host -> {
            val orgList = ListLensEach.this.parentLens.apply(host);
            val newList = orgList.mapOnly(checker, __ -> supplier.get());
            val newHost = ListLensEach.this.parentLens.changeTo(newList).apply(host);
            return newHost;
        };
    }
    
    public Func1<HOST, HOST> changeTo(Function<TYPE, TYPE> mapper) {
        return host -> {
            val orgList = ListLensEach.this.parentLens.apply(host);
            val newList = orgList.mapOnly(checker, mapper);
            val newHost = ListLensEach.this.parentLens.changeTo(newList).apply(host);
            return newHost;
        };
    }
    
}
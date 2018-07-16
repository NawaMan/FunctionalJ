package functionalj.lens.lenses;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import functionalj.lens.core.AccessParameterized2;
import functionalj.lens.core.LensSpec;
import functionalj.lens.core.LensSpecParameterized2;
import functionalj.lens.core.LensType;
import functionalj.lens.core.LensUtils;
import functionalj.lens.core.WriteLens;
import lombok.val;
import tuple.ImmutableTuple2;
import tuple.Tuple2;

@FunctionalInterface
public interface Tuple2Lens<HOST, T1, T2, T1LENS extends AnyLens<HOST,T1>, T2LENS extends AnyLens<HOST,T2>>
        extends
            ObjectLens<HOST, Tuple2<T1, T2>>,
            Tuple2Access<HOST, T1, T2, T1LENS, T2LENS> {

    public static <H, _1, _2, 
                   _1ACCESS extends AnyAccess<H,_1>, _2ACCESS extends AnyAccess<H,_2>, 
                   _1LENS extends AnyLens<H,_1>, _2LENS extends AnyLens<H,_2>>
            Tuple2Lens<H, _1, _2, _1LENS, _2LENS> of(
                    Function<H,  Tuple2<_1, _2>>      read,
                    WriteLens<H, Tuple2<_1, _2>>      write,
                    LensType<H, _1, _1ACCESS, _1LENS> _1Type,
                    LensType<H, _2, _2ACCESS, _2LENS> _2Type) {
        val spec = new LensSpecParameterized2<H, Tuple2<_1, _2>, _1, _2, _1LENS, _2LENS>() {
            @Override public LensSpec<H, Tuple2<_1, _2>> getSpec()          { return LensSpec.of(read, write); }
            @Override public _1LENS createSubLens1(LensSpec<H, _1> subSpec) { return _1Type.newLens(subSpec); }
            @Override public _2LENS createSubLens2(LensSpec<H, _2> subSpec) { return _2Type.newLens(subSpec); }
        };
        return ()->spec;
    }
    

    public LensSpecParameterized2<HOST, Tuple2<T1, T2>, T1, T2, T1LENS, T2LENS> lensSpecParameterized2();
    
    @Override
    public default AccessParameterized2<HOST, Tuple2<T1, T2>, T1, T2, T1LENS, T2LENS> accessParameterized2() {
        return lensSpecParameterized2();
    }

    @Override
    public default LensSpec<HOST, Tuple2<T1, T2>> lensSpec() {
        return lensSpecParameterized2().getSpec();
    }
    
    @Override
    default Tuple2<T1, T2> applyUnsafe(HOST host) throws Exception {
        return Tuple2Access.super.applyUnsafe(host);
    }
    
    public default T1LENS _1() {
        WriteLens<Tuple2<T1, T2>, T1> write = (tuple, _1) -> new ImmutableTuple2<T1, T2>(_1, tuple._2());
        Function<Tuple2<T1, T2>, T1>  read  = Tuple2::_1;
        return LensUtils.createSubLens(this, read, write, lensSpecParameterized2()::createSubLens1);
    }
    
    public default T2LENS _2() {
        WriteLens<Tuple2<T1, T2>, T2> write = (tuple, _2) -> new ImmutableTuple2<T1, T2>(tuple._1(), _2);
        Function<Tuple2<T1, T2>, T2>  read  = Tuple2::_2;
        return LensUtils.createSubLens(this, read, write, lensSpecParameterized2()::createSubLens2);
    }
    
    public default Function<HOST, HOST> change1To(T1 _1value) {
        return host -> {
            Tuple2<T1, T2> newTuple = new ImmutableTuple2<>(_1value, apply(host)._2());
            return apply(host, newTuple);
        };
    }
    
    public default Function<HOST, HOST> change2To(T2 _2value) {
        return host -> {
            Tuple2<T1, T2> newTuple = new ImmutableTuple2<>(apply(host)._1(), _2value);
            return apply(host, newTuple);
        };
    }
    
    public default Function<HOST, HOST> change1By(Supplier<T1> _1valueSupplier) {
        return host -> {
            val newTuple = new ImmutableTuple2<>(_1valueSupplier.get(), apply(host)._2());
            return apply(host, newTuple);
        };
    }
    
    public default Function<HOST, HOST> change2By(Supplier<T2> _2valueSupplier) {
        return host -> {
            val newTuple = new ImmutableTuple2<>(apply(host)._1(), _2valueSupplier.get());
            return apply(host, newTuple);
        };
    }
    
    public default Function<HOST, HOST> change1By(Function<T1, T1> _1valueTransformer) {
        return host -> {
            val oldTuple = apply(host);
            val new_1    = _1valueTransformer.apply(oldTuple._1());
            val newTuple = new ImmutableTuple2<>(new_1, oldTuple._2());
            return apply(host, newTuple);
        };
    }
    
    public default Function<HOST, HOST> change2By(Function<T2,T2> _2valueTransformer) {
        return host -> {
            val oldTuple = apply(host);
            val new_2    = _2valueTransformer.apply(oldTuple._2());
            val newTuple = new ImmutableTuple2<>(oldTuple._1(), new_2);
            return apply(host, newTuple);
        };
    }
    
    public default Function<HOST, HOST> change1By(BiFunction<T1,T2,T1> _1valueTransformer) {
        return host -> {
            val oldTuple = apply(host);
            val new_1    = _1valueTransformer.apply(oldTuple._1(), oldTuple._2());
            val newTuple = new ImmutableTuple2<>(new_1, oldTuple._2());
            return apply(host, newTuple);
        };
    }
    
    public default Function<HOST, HOST> change2By(BiFunction<T1,T2,T2> _2valueTransformer) {
        return host -> {
            val oldTuple = apply(host);
            val new_2    = _2valueTransformer.apply(oldTuple._1(), oldTuple._2());
            val newTuple = new ImmutableTuple2<>(oldTuple._1(), new_2);
            return apply(host, newTuple);
        };
    }
}
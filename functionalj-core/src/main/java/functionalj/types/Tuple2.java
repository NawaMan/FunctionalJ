package functionalj.types;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import functionalj.lens.AccessParameterized2;
import functionalj.lens.AnyLens;
import functionalj.lens.LensSpec;
import functionalj.lens.LensSpecParameterized2;
import functionalj.lens.LensUtils;
import functionalj.lens.ObjectLens;
import functionalj.lens.WriteLens;
import lombok.val;

public class Tuple2<T1, T2> implements ITuple2<T1, T2>, Map.Entry<T1, T2> {
    
    public final T1 _1;
    public final T2 _2;
    
    public Tuple2(T1 _1, T2 _2) {
        this._1 = _1;
        this._2 = _2;
    }
    
    public Tuple2(Map.Entry<? extends T1, ? extends T2> entry) {
        this._1 = entry.getKey();
        this._2 = entry.getValue();
    }
    
    public T1 _1() {
        return _1;
    }
    public T2 _2() {
        return _2;
    }
    
    public String toString() {
        return "[" + _1 + "," + _2 + "]";
    }

    @Override
    public T1 getKey() {
        return _1();
    }

    @Override
    public T2 getValue() {
        return _2();
    }

    @Override
    public T2 setValue(T2 value) {
        throw new UnsupportedOperationException();
    }
    
    
    
    // == Lens ==

    public static Tuple2Lens<Tuple2<Object,Object>, Object, Object, 
                    ObjectLens<Tuple2<Object,Object>, Object>, ObjectLens<Tuple2<Object,Object>, Object>>
                        genericTuple2 = createTheTuple(ObjectLens::of, ObjectLens::of);
    
    
    public static <T1,T2, T1LENS extends AnyLens<Tuple2<T1, T2>, T1>, T2LENS extends AnyLens<Tuple2<T1, T2>, T2>> 
            Tuple2Lens<Tuple2<T1,T2>, T1, T2, T1LENS, T2LENS> createTheTuple(
                Function<LensSpec<Tuple2<T1, T2>, T1>, T1LENS> keyLensCreator,
                Function<LensSpec<Tuple2<T1, T2>, T2>, T2LENS> valueLensCreator) {
        return Tuple2Lens.of(t -> t, (t, newT) -> newT, keyLensCreator, valueLensCreator);
    }
    
    @FunctionalInterface
    public static interface Tuple2Lens<HOST, T1, T2, 
                T1LENS extends AnyLens<HOST,T1>, 
                T2LENS extends AnyLens<HOST,T2>>
            extends
                ObjectLens<HOST, Tuple2<T1, T2>>,
                ITuple2.ITuple2Access<HOST, T1, T2, T1LENS, T2LENS> {

        public static <HOST, _1, _2, _1LENS extends AnyLens<HOST,_1>, _2LENS extends AnyLens<HOST,_2>>
                Tuple2Lens<HOST, _1, _2, _1LENS, _2LENS> of(
                        Function<HOST,  Tuple2<_1, _2>>      read,
                        WriteLens<HOST, Tuple2<_1, _2>>      write,
                        Function<LensSpec<HOST, _1>, _1LENS> t1LensCreator,
                        Function<LensSpec<HOST, _2>, _2LENS> t2LensCreator) {
            val spec = new LensSpecParameterized2<HOST, Tuple2<_1, _2>, _1, _2, _1LENS, _2LENS>() {
                @Override public LensSpec<HOST, Tuple2<_1, _2>> getSpec()          { return LensSpec.of(read, write);        }
                @Override public _1LENS createSubLens1(LensSpec<HOST, _1> subSpec) { return t1LensCreator.apply(subSpec);   }
                @Override public _2LENS createSubLens2(LensSpec<HOST, _2> subSpec) { return t2LensCreator.apply(subSpec); }
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
        default Tuple2<T1, T2> apply(HOST host) {
            return ITuple2Access.super.apply(host);
        }
        
        public default T1LENS _1() {
            WriteLens<Tuple2<T1, T2>, T1> write = (tuple, _1) -> new Tuple2<T1, T2>(_1, tuple._2);
            Function<Tuple2<T1, T2>, T1>  read  = Tuple2::_1;
            return LensUtils.createSubLens(this, read, write, lensSpecParameterized2()::createSubLens1);
        }
        
        public default T2LENS _2() {
            WriteLens<Tuple2<T1, T2>, T2> write = (tuple, _2) -> new Tuple2<T1, T2>(tuple._1, _2);
            Function<Tuple2<T1, T2>, T2>  read  = Tuple2::_2;
            return LensUtils.createSubLens(this, read, write, lensSpecParameterized2()::createSubLens2);
        }
        
        public default Function<HOST, HOST> change1To(T1 _1value) {
            return host -> {
                Tuple2<T1, T2> newTuple = new Tuple2<>(_1value, apply(host)._2);
                return apply(host, newTuple);
            };
        }
        
        public default Function<HOST, HOST> change2To(T2 _2value) {
            return host -> {
                Tuple2<T1, T2> newTuple = new Tuple2<>(apply(host)._1, _2value);
                return apply(host, newTuple);
            };
        }
        
        public default Function<HOST, HOST> change1By(Supplier<T1> _1valueSupplier) {
            return host -> {
                val newTuple = new Tuple2<>(_1valueSupplier.get(), apply(host)._2);
                return apply(host, newTuple);
            };
        }
        
        public default Function<HOST, HOST> change2By(Supplier<T2> _2valueSupplier) {
            return host -> {
                val newTuple = new Tuple2<>(apply(host)._1, _2valueSupplier.get());
                return apply(host, newTuple);
            };
        }
        
        public default Function<HOST, HOST> change1By(Function<T1, T1> _1valueTransformer) {
            return host -> {
                val oldTuple = apply(host);
                val new_1    = _1valueTransformer.apply(oldTuple._1);
                val newTuple = new Tuple2<>(new_1, oldTuple._2);
                return apply(host, newTuple);
            };
        }
        
        public default Function<HOST, HOST> change2By(Function<T2,T2> _2valueTransformer) {
            return host -> {
                val oldTuple = apply(host);
                val new_2    = _2valueTransformer.apply(oldTuple._2);
                val newTuple = new Tuple2<>(oldTuple._1, new_2);
                return apply(host, newTuple);
            };
        }
        
        public default Function<HOST, HOST> change1By(BiFunction<T1,T2,T1> _1valueTransformer) {
            return host -> {
                val oldTuple = apply(host);
                val new_1    = _1valueTransformer.apply(oldTuple._1, oldTuple._2);
                val newTuple = new Tuple2<>(new_1, oldTuple._2);
                return apply(host, newTuple);
            };
        }
        
        public default Function<HOST, HOST> change2By(BiFunction<T1,T2,T2> _2valueTransformer) {
            return host -> {
                val oldTuple = apply(host);
                val new_2    = _2valueTransformer.apply(oldTuple._1, oldTuple._2);
                val newTuple = new Tuple2<>(oldTuple._1, new_2);
                return apply(host, newTuple);
            };
        }
    }
    
}

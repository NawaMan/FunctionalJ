package functionalj.types;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

import functionalj.lens.AccessParameterized;
import functionalj.lens.AnyAccess;
import functionalj.lens.IntegerAccess;
import functionalj.lens.IntegerLens;
import functionalj.lens.Lens;
import functionalj.lens.LensSpec;
import functionalj.lens.LensSpecParameterized;
import functionalj.lens.Lenses;
import functionalj.lens.ObjectLens;
import functionalj.lens.WriteLens;
import lombok.val;

public class IntTuple2<T2> implements ITuple2<Integer, T2>, Map.Entry<Integer, T2> {

    public final int _1;
    public final T2  _2;
    
    public IntTuple2(int _1, T2 _2) {
        this._1 = _1;
        this._2 = _2;
    }

    public int _int() {
        return _1;
    }
    public Integer _1() {
        return _1;
    }
    public T2 _2() {
        return _2;
    }
    
    public String toString() {
        return "[" + _1 + "," + _2 + "]";
    }

    @Override
    public Integer getKey() {
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
    


    
    
    
    // == Access == 
    
    /**
     * @author manusitn
     *
     * @param <HOST>
     * @param <T2>
     * @param <T2ACCESS>
     */
    @FunctionalInterface
    public static interface IntTuple2Access<HOST, T2, T2ACCESS extends AnyAccess<HOST,T2>>
            extends AccessParameterized<HOST, IntTuple2<T2>, T2, T2ACCESS> {

        public AccessParameterized<HOST, IntTuple2<T2>, T2, T2ACCESS> accessParameterized();
        
        @Override
        public default IntTuple2<T2> apply(HOST host) {
            return accessParameterized().apply(host);
        }
        
        @Override
        public default T2ACCESS createSubAccess(Function<IntTuple2<T2>, T2> accessToParameter) {
            return accessParameterized().createSubAccess(IntTuple2::_2);
        }

        @Override
        public default T2ACCESS createSubAccessFromHost(Function<HOST, T2> accessToParameter) {
            return accessParameterized().createSubAccessFromHost(accessToParameter);
        }
        
        public default IntegerAccess<HOST> _1() {
            return intAccess(0, IntTuple2::_1);
        }
        public default T2ACCESS _2() {
            return accessParameterized().createSubAccess(IntTuple2::_2);
        }
    }
            
    // == Lens ==

    public static IntTuple2Lens<IntTuple2<Object>, Object, ObjectLens<IntTuple2<Object>, Object>>
                        genericIntTuple2 = createTheTuple(ObjectLens::of);
    
    public static <T2, T2LENS extends Lens<IntTuple2<T2>, T2>> 
            IntTuple2Lens<IntTuple2<T2>, T2, T2LENS> createTheTuple(
                Function<LensSpec<IntTuple2<T2>, T2>, T2LENS> _2LensCreator) {
        return IntTuple2Lens.of(t -> t, (t, newT) -> newT, _2LensCreator);
    }
    

    public static interface IntTuple2Lens<HOST, _2, _2LENS extends Lens<HOST,_2>>
            extends
                ObjectLens<HOST, IntTuple2<_2>>,
                IntTuple2Access<HOST, _2, _2LENS> {

        public static <HOST, _2, _2LENS extends Lens<HOST,_2>>
                IntTuple2Lens<HOST, _2, _2LENS> of(
                        Function<HOST,  IntTuple2<_2>>      read,
                        WriteLens<HOST, IntTuple2<_2>>      write,
                        Function<LensSpec<HOST, _2>, _2LENS> valueLensCreator) {
            val spec = new LensSpecParameterized<HOST, IntTuple2<_2>, _2, _2LENS>() {
                @Override public LensSpec<HOST, IntTuple2<_2>> getSpec()          { return LensSpec.of(read, write);        }
                @Override public _2LENS createSubLens(LensSpec<HOST, _2> subSpec) { return valueLensCreator.apply(subSpec); }
            };    
            return ()->spec;
        }

        public LensSpecParameterized<HOST, IntTuple2<_2>, _2, _2LENS> lensSpecParameterized();
        
        @Override
        public default AccessParameterized<HOST, IntTuple2<_2>, _2, _2LENS> accessParameterized() {
            return lensSpecParameterized();
        }

        @Override
        public default LensSpec<HOST, IntTuple2<_2>> lensSpec() {
            return lensSpecParameterized().getSpec();
        }
        
        @Override
        default IntTuple2<_2> apply(HOST host) {
            return IntTuple2Access.super.apply(host);
        }
        
        public default IntegerLens<HOST> _1() {
            WriteLens<IntTuple2<_2>, Integer> write = (tuple, _1) -> new IntTuple2<_2>(_1, tuple._2);
            Function <IntTuple2<_2>, Integer> read  = IntTuple2::_1;
            return Lenses.createSubLens(this, read, write, IntegerLens::of);
        }
        
        public default _2LENS _2() {
            val write = (WriteLens<IntTuple2<_2>, _2>)((tuple, _2) -> new IntTuple2<_2>(tuple._1, _2));
            val read  = (Function <IntTuple2<_2>, _2>)IntTuple2::_2;
            return Lenses.createSubLens(this, read, write, lensSpecParameterized()::createSubLens);
        }
        
        public default Function<HOST, HOST> change1To(int _1value) {
            return host -> {
                val newTuple = new IntTuple2<>(_1value, apply(host)._2);
                return apply(host, newTuple);
            };
        }
        
        public default Function<HOST, HOST> change2To(_2 _2value) {
            return host -> {
                val newTuple = new IntTuple2<>(apply(host)._1, _2value);
                return apply(host, newTuple);
            };
        }
        
        public default Function<HOST, HOST> change1By(Supplier<Integer> _1valueSupplier) {
            return host -> {
                val newTuple = new IntTuple2<>(_1valueSupplier.get(), apply(host)._2);
                return apply(host, newTuple);
            };
        }
        public default Function<HOST, HOST> change1By(IntSupplier _1valueSupplier) {
            return host -> {
                val newTuple = new IntTuple2<>(_1valueSupplier.getAsInt(), apply(host)._2);
                return apply(host, newTuple);
            };
        }
        
        public default Function<HOST, HOST> change2By(Supplier<_2> _2valueSupplier) {
            return host -> {
                val newTuple = new IntTuple2<>(apply(host)._1, _2valueSupplier.get());
                return apply(host, newTuple);
            };
        }
        
        public default Function<HOST, HOST> change1By(Function<Integer, Integer> _1valueTransformer) {
            return host -> {
                val oldTuple = apply(host);
                val new_1    = _1valueTransformer.apply(oldTuple._1);
                val newTuple = new IntTuple2<>(new_1, oldTuple._2);
                return apply(host, newTuple);
            };
        }
        public default Function<HOST, HOST> change1By(IntFunction<Integer> _1valueTransformer) {
            return host -> {
                val oldTuple = apply(host);
                val new_1    = _1valueTransformer.apply(oldTuple._1);
                val newTuple = new IntTuple2<>(new_1, oldTuple._2);
                return apply(host, newTuple);
            };
        }
        public default Function<HOST, HOST> change1By(ToIntFunction<Integer> _1valueTransformer) {
            return host -> {
                val oldTuple = apply(host);
                val new_1    = _1valueTransformer.applyAsInt(oldTuple._1);
                val newTuple = new IntTuple2<>(new_1, oldTuple._2);
                return apply(host, newTuple);
            };
        }
        
        public default Function<HOST, HOST> change2By(Function<_2,_2> _2valueTransformer) {
            return host -> {
                val oldTuple = apply(host);
                val new_2 = _2valueTransformer.apply(oldTuple._2);
                val newTuple = new IntTuple2<>(oldTuple._1, new_2);
                return apply(host, newTuple);
            };
        }
        
        public default Function<HOST, HOST> change1By(BiFunction<Integer,_2,Integer> _1valueTransformer) {
            return host -> {
                val oldTuple = apply(host);
                val new_1    = _1valueTransformer.apply(oldTuple._1, oldTuple._2);
                val newTuple = new IntTuple2<>(new_1, oldTuple._2);
                return apply(host, newTuple);
            };
        }
        
        public default Function<HOST, HOST> change2By(BiFunction<Integer,_2,_2> _2valueTransformer) {
            return host -> {
                val oldTuple = apply(host);
                val new_2    = _2valueTransformer.apply(oldTuple._1, oldTuple._2);
                val newTuple = new IntTuple2<>(oldTuple._1, new_2);
                return apply(host, newTuple);
            };
        }
    }
    
}
// ============================================================================
// Copyright (c) 2017-2025 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
// ----------------------------------------------------------------------------
// MIT License
// 
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
// 
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
// 
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.
// ============================================================================
package functionalj.lens.lenses;

import static functionalj.functions.StrFuncs.whenBlank;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import functionalj.function.Func1;
import functionalj.function.Named;
import functionalj.lens.core.LensSpec;
import functionalj.lens.core.WriteLens;
import functionalj.list.FuncList;
import lombok.val;

@FunctionalInterface
public interface AnyLens<HOST, DATA> extends AnyAccess<HOST, DATA>, WriteLens<HOST, DATA> {
    
    public static class Impl<H, D> implements Named, AnyLens<H, D> {
        
        private final String name;
        
        private final LensSpec<H, D> spec;
        
        public Impl(String name, LensSpec<H, D> spec) {
            this.name = name;
            this.spec = spec;
        }
        
        public String name() {
            return name;
        }
        
        @Override
        public LensSpec<H, D> lensSpec() {
            return spec;
        }
        
        @Override
        public String toString() {
            return whenBlank(name, () -> super.toString());
        }
    }
    
    public static <T> AnyLens<T, T> of(String name, LensSpec<T, T> spec) {
        return new Impl<>(name, spec);
    }
    
    public static <T> AnyLens<T, T> of(LensSpec<T, T> spec) {
        return of(null, spec);
    }
    
    public LensSpec<HOST, DATA> lensSpec();
    
    @Override
    public default DATA applyUnsafe(HOST host) throws Exception {
        val spec = lensSpec();
        if (spec.isNullSafe() && (host == null))
            return null;
        val read = spec.getRead();
        val value = read.apply(host);
        return value;
    }
    
    @Override
    default HOST apply(HOST host, DATA data) {
        val spec = lensSpec();
        if (spec.isNullSafe() && (host == null))
            return null;
        val write = spec.getWrite();
        val newValue = write.apply(host, data);
        return newValue;
    }
    
    default DATA read(HOST host) {
        return apply(host);
    }
    
    default Func1<HOST, HOST> changeTo(DATA data) {
        return host -> {
            return apply(host, data);
        };
    }
    
    default Func1<HOST, HOST> changeTo(Supplier<DATA> dataSupplier) {
        return host -> {
            val newValue = dataSupplier.get();
            return apply(host, newValue);
        };
    }
    
    default Func1<HOST, HOST> changeTo(Function<DATA, DATA> dataMapper) {
        return host -> {
            val oldValue = read(host);
            val newValue = dataMapper.apply(oldValue);
            return apply(host, newValue);
        };
    }
    
    default Func1<HOST, HOST> changeTo(BiFunction<HOST, DATA, DATA> mapper) {
        return host -> {
            val oldValue = read(host);
            val newValue = mapper.apply(host, oldValue);
            return apply(host, newValue);
        };
    }
    
    default Func1<HOST, HOST> changeWhen(Predicate<DATA> check, DATA data) {
        return host -> {
            val originalData = apply(host);
            val shouldChange = check.test(originalData);
            if (!shouldChange)
                return host;
            val newData = data;
            return apply(host, newData);
        };
    }
    
    default Func1<HOST, HOST> changeWhen(Predicate<DATA> check, Supplier<DATA> dataSupplier) {
        return host -> {
            val originalData = apply(host);
            val shouldChange = check.test(originalData);
            if (!shouldChange)
                return host;
            val newData = dataSupplier.get();
            return apply(host, newData);
        };
    }
    
    default Func1<HOST, HOST> changeWhen(Predicate<DATA> check, Function<DATA, DATA> dataMapper) {
        return host -> {
            val originalData = apply(host);
            val shouldChange = check.test(originalData);
            if (!shouldChange)
                return host;
            val newData = dataMapper.apply(originalData);
            return apply(host, newData);
        };
    }
    
    default Func1<HOST, HOST> changeWhen(Predicate<DATA> check, BiFunction<HOST, DATA, DATA> mapper) {
        return host -> {
            val originalData = apply(host);
            val shouldChange = check.test(originalData);
            if (!shouldChange)
                return host;
            val newData = mapper.apply(host, originalData);
            return apply(host, newData);
        };
    }
    
    //== As and To ==
    
    public default BooleanLens<HOST> asBoolean(BooleanLens<DATA> mapper) {
        val read = (Function<HOST, Boolean>)((HOST host) -> {
            return mapper.read(apply(host));
        });
        val write = (WriteLens<HOST, Boolean>)((HOST host, Boolean newValue) -> {
            val data = apply(host);
            val newData
                    = mapper.changeTo(newValue)
                    .apply(data);
            return changeTo(newData)
                    .apply(host);
        });
        return BooleanLens.of(new LensSpec<HOST, Boolean>(read, write));
    }
    
//    public default CharacterAccess<HOST> asCharacter(CharacterAccess<DATA> mapper) {
//        return characterAccess(' ', any -> {
//            return mapper.apply(any);
//        });
//    }
    
    public default IntegerLens<HOST> asInteger(IntegerLens<DATA> mapper) {
        val read = (Function<HOST, Integer>)((HOST host) -> {
            return mapper.read(apply(host));
        });
        val write = (WriteLens<HOST, Integer>)((HOST host, Integer newValue) -> {
            val data = apply(host);
            val newData
                    = mapper.changeTo(newValue)
                    .apply(data);
            return changeTo(newData)
                    .apply(host);
        });
        return IntegerLens.of(new LensSpec<HOST, Integer>(read, write));
    }
    
    public default LongLens<HOST> asLong(LongLens<DATA> mapper) {
        val read = (Function<HOST, Long>)((HOST host) -> {
            return mapper.read(apply(host));
        });
        val write = (WriteLens<HOST, Long>)((HOST host, Long newValue) -> {
            val data = apply(host);
            val newData
                    = mapper.changeTo(newValue)
                    .apply(data);
            return changeTo(newData)
                    .apply(host);
        });
        return LongLens.of(new LensSpec<HOST, Long>(read, write));
    }
    
    public default DoubleLens<HOST> asDouble(DoubleLens<DATA> mapper) {
        val read = (Function<HOST, Double>)((HOST host) -> {
            return mapper.read(apply(host));
        });
        val write = (WriteLens<HOST, Double>)((HOST host, Double newValue) -> {
            val data = apply(host);
            val newData
                    = mapper.changeTo(newValue)
                    .apply(data);
            return changeTo(newData)
                    .apply(host);
        });
        return DoubleLens.of(new LensSpec<HOST, Double>(read, write));
    }
    
    public default StringLens<HOST> asString(StringLens<DATA> mapper) {
        val read = (Function<HOST, String>)((HOST host) -> {
            return mapper.read(apply(host));
        });
        val write = (WriteLens<HOST, String>)((HOST host, String newValue) -> {
            val data = apply(host);
            val newData
                    = mapper.changeTo(newValue)
                    .apply(data);
            return changeTo(newData)
                    .apply(host);
        });
        return StringLens.of(new LensSpec<HOST, String>(read, write));
    }
    
    public default BigIntegerLens<HOST> asBigInteger(BigIntegerLens<DATA> mapper) {
        val read = (Function<HOST, BigInteger>)((HOST host) -> {
            return mapper.read(apply(host));
        });
        val write = (WriteLens<HOST, BigInteger>)((HOST host, BigInteger newValue) -> {
            val data = apply(host);
            val newData
                    = mapper.changeTo(newValue)
                    .apply(data);
            return changeTo(newData)
                    .apply(host);
        });
        return BigIntegerLens.of(new LensSpec<HOST, BigInteger>(read, write));
    }
    
    public default BigDecimalLens<HOST> asBigDecimal(BigDecimalLens<DATA> mapper) {
        val read = (Function<HOST, BigDecimal>)((HOST host) -> {
            return mapper.read(apply(host));
        });
        val write = (WriteLens<HOST, BigDecimal>)((HOST host, BigDecimal newValue) -> {
            val data = apply(host);
            val newData
                    = mapper.changeTo(newValue)
                    .apply(data);
            return changeTo(newData)
                    .apply(host);
        });
        return BigDecimalLens.of(new LensSpec<HOST, BigDecimal>(read, write));
    }
    
    public default <T, TL extends AnyLens<HOST, T>> ListAccess<HOST, T, TL> 
                    asList(Function<DATA, List<T>>         read,
                           WriteLens<DATA, List<T>>        write,
                           Function<LensSpec<HOST, T>, TL> subCreator) {
        Function<HOST, List<T>> readHost = host -> {
            val data    = apply(host);
            val newData = read.apply(data);
            return newData;
        };
        WriteLens<HOST, List<T>> writeHost = (host, newValue) -> {
            val data    = apply(host);
            val newData = write.apply(data, newValue);
            return changeTo(newData)
                    .apply(host);
        };
        return ListLens.of(readHost, writeHost, subCreator);
    }
    
    public default <T, TL extends AnyLens<HOST, T>> FuncListAccess<HOST, T, TL> 
                    asFuncList(Function<DATA, FuncList<T>>     read,
                               WriteLens<DATA, FuncList<T>>    write,
                               Function<LensSpec<HOST, T>, TL> subCreator) {
        Function<HOST, FuncList<T>> readHost = host -> {
            val data    = apply(host);
            val newData = read.apply(data);
            return newData;
        };
        WriteLens<HOST, FuncList<T>> writeHost = (host, newValue) -> {
            val data    = apply(host);
            val newData = write.apply(data, newValue);
            return changeTo(newData)
                    .apply(host);
        };
        return FuncListLens.of(readHost, writeHost, subCreator);
    }
    
}

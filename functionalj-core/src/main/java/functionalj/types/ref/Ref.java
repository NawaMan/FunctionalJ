package functionalj.types.ref;

import static java.util.Objects.requireNonNull;

import java.util.function.Supplier;

import functionalj.functions.Func0;
import functionalj.types.result.AsResult;
import functionalj.types.result.Result;
import lombok.val;

public abstract class Ref<DATA> implements Supplier<Result<DATA>>, AsResult<DATA> {
	
	public static <D> RefBuilder<D> of(Class<D> dataClass) {
		return new RefBuilder<D>(dataClass);
	}
	
	public static <D> RefOf<D> ofValue(D value) {
		@SuppressWarnings("unchecked")
		val dataClass = (Class<D>)value.getClass();
		val result    = Result.of(value);
		val ref       = new RefOf.Result<D>(dataClass, result);
		return ref;
	}
	
	private final Class<DATA> dataClass;
	
	Ref(Class<DATA> dataClass) {
		this.dataClass = requireNonNull(dataClass);
	}
	
	@Override
	public abstract Result<DATA> get();
	
	public final Class<DATA> getDataType() {
		return dataClass;
	}
	
	public final Result<DATA> getResult() {
		val result = get();
		if (result == null)
			return Result.ofNotAvailable();
		
		return result;
	}
	
	@Override
    public final Result<DATA> asResult() {
    	return get();
    }
	
	public final DATA value() {
		return get().value();
	}
	public final DATA orElse(DATA elseValue) {
		return get().orElse(elseValue);
	}
    public final DATA orElseGet(Supplier<? extends DATA> elseSupplier) {
		return get().orElseGet(elseSupplier);
	}
    
    public final Substitution<DATA> butWith(DATA value) {
    	return new Substitution.Value<DATA>(this, value);
    }
    
    public final Substitution<DATA> butFrom(Func0<DATA> supplier) {
    	return new Substitution.Supplier<DATA>(this, supplier);
    }
	
}

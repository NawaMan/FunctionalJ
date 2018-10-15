package functionalj.types.ref;

import functionalj.functions.Func0;
import functionalj.types.result.Result;
import lombok.val;

public class RefBuilder<DATA> {
	
	private final Class<DATA> dataClass;
	
	public RefBuilder(Class<DATA> dataClass) {
		this.dataClass = dataClass;
	}
	
	public RefOf<DATA> defaultToNull() {
		@SuppressWarnings("unchecked")
		val result = (Result<DATA>)Result.ofNull();
		val ref    = new RefOf.FromResult<>(dataClass, result);
		return ref;
	}
	public RefOf<DATA> defaultTo(DATA value) {
		val result = Result.of(value);
		val ref    = new RefOf.FromResult<>(dataClass, result);
		return ref;
	}
	public RefOf<DATA> defaultFrom(Func0<DATA> supplier) {
		val ref = new RefOf.FromSupplier<DATA>(dataClass, supplier);
		return ref;
	}
	@SuppressWarnings("unchecked")
	public RefOf<DATA> defaultToResult(Result<DATA> result) {
		val res = (result != null) ? result : (Result<DATA>)Result.ofNotAvailable();
		val ref = new RefOf.FromResult<DATA>(dataClass, res);
		return ref;
	}
	
}

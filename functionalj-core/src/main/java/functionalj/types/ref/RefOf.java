package functionalj.types.ref;

import java.util.Random;

import functionalj.functions.Func0;
import functionalj.types.result.Result;
import lombok.val;

public abstract class RefOf<DATA> extends Ref<DATA> {
	
	private static final Random random = new Random();
	
	private final int hashCode = random.nextInt();
	
	public RefOf(Class<DATA> dataClass) {
		super(dataClass);
	}
	
	public final int hashCode() {
		return hashCode;
	}
	
	public final boolean equals(Object another) {
		if (this == another)
			return true;
		if (another == null)
			return false;
		if (!(another instanceof RefOf))
			return false;
		
		@SuppressWarnings("unchecked")
		val anotherRef = (RefOf<DATA>)another;
		if (!anotherRef.getDataType().equals(this.getDataType()))
			return false;
		
		return this.hashCode() == another.hashCode();
	}
	
	//== Sub classes ==

	public static class FromResult<DATA> extends RefOf<DATA> {
	
		private final Result<DATA> result;
	
		public FromResult(Class<DATA> dataClass, Result<DATA> result) {
			super(dataClass);
			this.result = (result != null)
					? result
					: Result.ofNull();
		}
	
		@Override
		public Result<DATA> get() {
			return result;
		}
	
	}
	
	public static class FromSupplier<DATA> extends RefOf<DATA> {
		
		@SuppressWarnings("rawtypes")
		private static final Func0 notAvailable = ()->Result.ofNotAvailable();
		
		private final Func0<DATA> supplier;
	
		@SuppressWarnings("unchecked")
		public FromSupplier(Class<DATA> dataClass, Func0<DATA> supplier) {
			super(dataClass);
			this.supplier = (supplier != null)
					? supplier
					: notAvailable;
		}
	
		@Override
		public Result<DATA> get() {
			val result = Result.from(supplier);
			return result;
		}
	
	}

	
}

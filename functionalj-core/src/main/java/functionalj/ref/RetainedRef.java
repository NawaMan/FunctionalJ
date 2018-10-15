package functionalj.ref;

import static java.util.Objects.requireNonNull;

import java.util.concurrent.atomic.AtomicReference;

import functionalj.result.Result;
import lombok.val;

public class RetainedRef<DATA> extends RefOf<DATA> implements RetainChecker {
	
	private static final Object NONE = new Object();
	
	private final Ref<DATA>     sourceRef;
	private final RetainChecker checker;
	
	private final AtomicReference<Object> data = new AtomicReference<>();
	
	public RetainedRef(Ref<DATA> sourceRef, RetainChecker checker) {
		super(sourceRef.getDataType());
		this.sourceRef = sourceRef;
		this.checker   = checker;
		data.set(NONE);
	}

	@Override
	public boolean stillValid() {
		return checker.stillValid();
	}
	
	@Override
	public final Result<DATA> get() {
		Object  oldData    = data.get();
		boolean noData     = oldData.equals(NONE);
		boolean requireNew = !stillValid();
		if (noData || requireNew) {
			val newResult = sourceRef.asResult();
			data.compareAndSet(oldData, newResult);
		}
		@SuppressWarnings("unchecked")
		val currentData = (Result<DATA>)data.get();
		return currentData;
	}
	
	//== Aux Class ==
	
	public static class Builder<DATA> {
		
		private final Ref<DATA> sourceRef;

		public Builder(Ref<DATA> sourceRef) {
			this.sourceRef = requireNonNull(sourceRef);
		}
		
		public RetainedRef<DATA> forever() {
			return new RetainedRef<DATA>(sourceRef, RetainChecker.forever);
		}
		public RetainedRef<DATA> never() {
			return new RetainedRef<DATA>(sourceRef, RetainChecker.never);
		}
		
	}
	
}

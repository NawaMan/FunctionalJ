package functionalj.ref;

import static java.util.Objects.requireNonNull;

import functionalj.function.Func0;

public abstract class Substitution<DATA> {
	
	public static <D> Substitution<D> of(Ref<D> ref, D value) {
		return new Substitution.Value<D>(ref, value);
	}
	public static <D> Substitution<D> from(Ref<D> ref, Func0<D> value) {
		return new Substitution.Supplier<D>(ref, value);
	}
	
	private final Ref<DATA> ref;
	
	protected Substitution(Ref<DATA> ref) {
		this.ref = requireNonNull(ref);
	}
	
	public final Ref<DATA> ref() {
		return ref;
	}

	public abstract Func0<DATA> supplier();
	
	//== Sub classes ==
	
	public static class Value<DATA> extends Substitution<DATA> {
		
		private final DATA value;
		
		public Value(Ref<DATA> ref, DATA value) {
			super(ref);
			this.value = value;
		}
		
		public final Func0<DATA> supplier() {
			return () -> value;
		}
		
		@Override
		public String toString() {
			return "Value [value=" + value + ", ref()=" + ref() + "]";
		}
		
	}

	public static class Supplier<DATA> extends Substitution<DATA> {
		
		private final Func0<DATA> supplier;
		
		public Supplier(Ref<DATA> ref, Func0<DATA> supplier) {
			super(ref);
			this.supplier = (supplier != null) ? supplier : ()->null;
		}
		
		public final Func0<DATA> supplier() {
			return supplier;
		}
		
		@Override
		public String toString() {
			return "Supplier [ref()=" + ref() + "]";
		}
		
	}
	
}

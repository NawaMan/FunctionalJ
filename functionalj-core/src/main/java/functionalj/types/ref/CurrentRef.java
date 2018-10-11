package functionalj.types.ref;

import static java.util.Objects.requireNonNull;

import java.util.List;

import functionalj.functions.Func0;
import lombok.val;

public class CurrentRef<DATA> extends RefOf<DATA> {
	
	@SuppressWarnings("rawtypes")
	private static class Entry {
		
		private final Entry        parent;
		private final Substitution substitution;
		
		Entry(Entry parent, Substitution substitution) {
			this.parent       = parent;
			this.substitution = requireNonNull(substitution);
		}
		
		@SuppressWarnings("unchecked")
		public <D> Func0<D> findSupplier(Ref<D> ref) {
			if (ref == null)
				return null;
			
			if (ref.equals(this.substitution.ref())) {
				return substitution.supplier();
			}
			
			if (parent == null)
				return null;
			
			return parent.findSupplier(ref);
		}
		
		@Override
		public String toString() {
			return "Entry [parent=" + parent + ", substitution=" + substitution + "]";
		}
		
	}
	
	private static final ThreadLocal<Entry> refEntry = new ThreadLocal<Entry>();
	
	private final Ref<DATA> defaultRef;
	
	public CurrentRef(Ref<DATA> defaultRef) {
		super(defaultRef.getDataType());
		this.defaultRef = defaultRef;
	}
	
	@Override
	public final functionalj.types.result.Result<DATA> get() {
		val supplier = refEntry.get().findSupplier(this);
		if (supplier != null) {
			val result = functionalj.types.result.Result.from(supplier);
			return result;
		}
		if (defaultRef != null) {
			val result = defaultRef.get();
			return result;
		}
		
		return functionalj.types.result.Result.ofNotAvailable();
	}
	
	static final <V, E extends Exception> 
			V runWith(List<Substitution<?>> substitutions, RunBody<V, E> action) throws E {
		val map = refEntry.get();
		try {
			if (substitutions != null) {
				for (val substitution : substitutions) {
					if (substitution == null)
						continue;
					
					val newEntry = new Entry(map, substitution);
					refEntry.set(newEntry);
				}
			}
			
			return action.run();
		} finally {
			refEntry.set(map);
		}
	}
	
}

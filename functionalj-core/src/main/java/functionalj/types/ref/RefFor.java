// TODO - This can't be done without DefaultJ


//package functionalj.types.ref;
//
//import functionalj.types.result.Result;
//import lombok.val;
//
//public class RefFor<DATA> extends Ref<DATA> {
//
//	private final int hashCode;
//	
//	RefFor(Class<DATA> dataClass) {
//		super(dataClass);
//		hashCode = dataClass.hashCode();
//	}
//	
//	@Override
//	public Result<DATA> get() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//	
//	public final int hashCode() {
//		return hashCode;
//	}
//	
//	public final boolean equals(Object another) {
//		if (this == another)
//			return true;
//		if (another == null)
//			return false;
//		if (!(another instanceof RefOf))
//			return false;
//		
//		@SuppressWarnings("unchecked")
//		val anotherRef = (RefOf<DATA>)another;
//		if (!anotherRef.getDataType().equals(this.getDataType()))
//			return false;
//		
//		return this.hashCode() == another.hashCode();
//	}
//	
//}

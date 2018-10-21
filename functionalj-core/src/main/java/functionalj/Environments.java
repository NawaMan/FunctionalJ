package functionalj;

import java.util.function.Supplier;

import functionalj.ref.Ref;

public class Environments {
	
	public static Ref<Environments> ref = Ref.to(Environments.class);
	
	public static Ref<Supplier<Long>> currentMilliSecond 
			= Ref.ofValue(()->
					ref
					.asResult ()
					.map      (Environments::currentMilliSecond)
					.orElseGet(System::currentTimeMillis));
	
	public long currentMilliSecond() {
		return System.currentTimeMillis();
	}
	
}

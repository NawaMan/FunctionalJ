package functionalj.functions;

import static org.junit.Assert.*;

import org.junit.Test;

import lombok.val;

public class Func0Test {

	@Test
	@SuppressWarnings("null")
	public void testOrElse() throws Exception {
		val str  = (String)null;
		val func = (Func0<Integer>)(()->{
			return str.length();
		});
		assertTrue  (func.getSafely().isException());
		assertEquals(0, func.orElse(0).get().intValue());
	}

}

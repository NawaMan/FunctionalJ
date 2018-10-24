package functionalj.functions;

import static org.junit.Assert.*;

import org.junit.Test;

import lombok.val;

public class Func0Test {

	@Test
	@SuppressWarnings("null")
	public void testElseUse() throws Exception {
		val str  = (String)null;
		val func = (Func0<Integer>)(()->{
			return str.length();
		});
		assertTrue  (func.getSafely().isException());
		assertEquals(0, func.elseUse(0).get().intValue());
	}

}

package functionalj.types.ref;

import static functionalj.types.list.FuncList.listOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import lombok.val;

public class RefTest {

	@Test
	public void testNull() {
		val ref = Ref.of(String.class).defaultToNull();
		assertNull(ref.value());
	}
	
	@Test
	public void testValue() {
		val ref1 = Ref.ofValue("Value");
		assertEquals("Value", ref1.value());
		
		val ref2 = Ref.ofValue(42);
		assertEquals(42, (int)ref2.value());
	}
	
	@Test
	public void testFrom() {
		val ref1 = Ref.of(String.class).defaultFrom(()->"Value");
		assertEquals("Value", ref1.value());
		
		val ref2 = Ref.of(Integer.class).defaultFrom(()->42);
		assertEquals(42, (int)ref2.value());
	}
	
	@Test
	public void testCurrentRef() {
		val ref1 = new CurrentRef<String>(Ref.of(String.class).defaultFrom(()->"OrgValue"));
		assertEquals("OrgValue", ref1.value());
		
		CurrentRef.runWith(
				listOf(ref1.butWith("NewValue")),
				()->{
					assertEquals("NewValue", ref1.value());
					return null;
				});
		
		assertEquals("OrgValue", ref1.value());
	}

}

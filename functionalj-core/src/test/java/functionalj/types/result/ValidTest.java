package functionalj.types.result;

import static functionalj.FunctionalJ.it;
import static functionalj.lens.Access.theString;
import static org.junit.Assert.*;

import java.util.function.Predicate;

import org.junit.Assert;
import org.junit.Test;

import functionalj.FunctionalJ;
import functionalj.types.result.CheckedTest.NonNullNonEmptyString;
import lombok.Value;

public class ValidTest {
    
    @Value
    public static class Person extends Validatable.With<Person, Person.Checker> {
        private final String name;

        public Person(String name) {
            super(Person.Checker.class);
            this.name = name;
        }
        
        public static class Checker implements Predicate<Person> {
            @Override
            public boolean test(Person t) {
                return t.getName() != null;
            }
        }
    }
    
    @Test
    public void test() {
        Valid<Person> validPerson = Valid.valueOf(new Person("John"));
        assertTrue(validPerson.isValid());
        assertTrue(validPerson.getValue().getName().equals("John"));
        assertEquals("Result:{ Value: ValidTest.Person(name=John) }", validPerson.toString());

        Valid<Person> invalidPerson = Valid.valueOf(new Person(null));
        assertFalse(invalidPerson.isValid());
        assertEquals("Result:{ Exception: functionalj.types.result.ValidationException: The value failed to check: ValidTest.Person(name=null) }", invalidPerson.toString());
        
        Valid<Person> nullPerson = Valid.valueOf(null);
        assertFalse(nullPerson.isValid());
        assertEquals("Result:{ Exception: functionalj.types.result.ValidationException: java.lang.NullPointerException }", nullPerson.toString());
        
        Valid<Person> invalidPerson2 = Invalid.valueOf("No value!");
        assertFalse(invalidPerson2.isValid());
        assertEquals("Result:{ Exception: functionalj.types.result.ValidationException: No value! }", invalidPerson2.toString());
    }
    @Test
    public void testToValidate() {
        Valid<Person> validPerson = Result.of("John").map(Person::new).asValidValueOf(it());
        assertTrue(validPerson.isValid());
        assertTrue(validPerson.getValue().getName().equals("John"));
        assertEquals("Result:{ Value: ValidTest.Person(name=John) }", validPerson.toString());
        
        Valid<Person> invalidPerson = Result.of("John").filter(str -> false).asValidValueOf(Person::new);
        assertFalse(invalidPerson.isValid());
        assertEquals("Result:{ Exception: functionalj.types.result.ValidationException: The value failed to check: ValidTest.Person(name=null) }", invalidPerson.toString());
        
    }
    
    private Result<String> someWork(Valid<Person> person) {
        return person.map(Person::getName).otherwise("<NoName>");
    }
    private Result<String> someWork(Person person) {
        return someWork(Valid.valueOf(person));
    }
    
    @Test
    public void testParam() {
        assertEquals("Result:{ Value: John }",     "" + someWork(Valid.valueOf(new Person("John"))));
        assertEquals("Result:{ Value: <NoName> }", "" + someWork(Valid.valueOf(new Person(null))));
        assertEquals("Result:{ Value: <NoName> }", "" + someWork(Valid.valueOf((Person)null)));
        
        assertEquals("Result:{ Value: John }",     "" + someWork(new Person("John")));
        assertEquals("Result:{ Value: <NoName> }", "" + someWork(new Person(null)));
        assertEquals("Result:{ Value: <NoName> }", "" + someWork((Person)null));
        
        assertEquals("Result:{ Value: John }",     "" + someWork(new Person("John").toValidValue()));
        assertEquals("Result:{ Value: <NoName> }", "" + someWork(new Person(null).toValidValue()));
    }
    
}

package functionalj.lens;

import static functionalj.lens.Accesses.theItem;
import static functionalj.lens.Accesses.theString;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.junit.Test;

import lombok.val;

public class ListAccessTest {

    
    public class WithNames {
        
        private List<String> names = new ArrayList<>();
        
        public WithNames(List<String> names) {
            this.names.addAll(names);
        }
        
        public List<String> names() {
            return names;
        }
        
        public WithNames withNames(List<String> newNames) {
            return new WithNames(newNames);
        }
    }
    
    @Test
    public void testListAccess() {
        val accSub = new AccessParameterized<WithNames, List<String>, String, StringAccess<WithNames>>() {
            @Override
            public List<String> apply(WithNames input) {
                return input.names();
            }
            @Override
            public StringAccess<WithNames> createSubAccess(Function<List<String>, String> accessToSub) {
                return withNames -> accessToSub.apply(this.apply(withNames));
            }
        };
        val listAcc = new ListAccess<WithNames, String, StringAccess<WithNames>>() {
            @Override
            public AccessParameterized<WithNames, List<String>, String, StringAccess<WithNames>> accessParameterized() {
                // TODO Auto-generated method stub
                return accSub;
            }
        };
        
        assertEquals("One", listAcc.first().apply(new WithNames(asList("One", "Two"))));
        assertEquals("One", Optional.of(new WithNames(asList("One", "Two")))
                .map(listAcc.first())
                .get());
        assertEquals("[One, Two]", Optional.of(new WithNames(asList("One", "Two", "Three", "Four")))
                .map(listAcc.filter(theString.length().thatEquals(3)))
                .map(theItem().convertToString())
                .get());
        assertEquals("ONE", Optional.of(new WithNames(asList("One", "Two")))
                .map(listAcc.first())
                .map(theString.toUpperCase())
                .get());
    }
}

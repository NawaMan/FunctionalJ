package functionalj;

import static functionalj.FunctionalJ.cacheFor;
import static functionalj.FunctionalJ.delimitWith;
import static functionalj.FunctionalJ.it;
import static functionalj.FunctionalJ.only;
import static functionalj.FunctionalJ.withIndex;
import static functionalj.functions.Absent.__;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Function;

import org.junit.Test;

import functionalj.functions.Func;
import lombok.Value;
import lombok.val;

public class FunctionalJTest {
    
    @Test
    public void testOnly() {
        
        @Value
        class Person {
            private String name;
        }
        
        val persons = asList(new Person("John"), new Person("Jack"), new Person("Jim"));
        val startsWithTerm = (BiPredicate<String, String>)String::startsWith;
        assertEquals("[Person(name=John)]", 
                persons.stream()
                .filter(only(Person::getName, startsWithTerm, "Jo"))
                .collect(toList())
                .toString());
    }
    
    @Value
    public static class City {
        private String id;
        private String name;
        public City withName(String name) { return new City(id, name); }
    } 
    
    @Value
    public static class User {
        private String id;
        private String name;
        private String cityId;
    }

    public final List<String> logs = new ArrayList<>();
    
    public final Map<String, City> citites = new HashMap<String, City>() {
        @Override
        public City get(Object key) {
            logs.add("Cities: " + key);
            return super.get(key);
        }
    };

    {
        citites.putAll(asList(
            new City("R", "Regina"),
            new City("S", "Saskatoon"),
            new City("B", "Bangkok")).stream()
            .collect(toMap(City::getId, it())));
    }
    
    
    public final Map<String, User> users = new HashMap<String, User>() {
        @Override
        public User get(Object key) {
            User user = super.get(key);
            logs.add("Users: " + key + " @ " + user.cityId);
            return user;
        }
    };
    
    {
        users.putAll(asList(
            new User("001", "Adam",    "R"),
            new User("002", "Ben",     "S"),
            new User("003", "Charlie", "B"),
            new User("004", "Dan",     "R"),
            new User("005", "Frank",   "S"),
            new User("006", "Eva",     "B"))
            .stream()
            .collect(toMap(User::getId, it())));
    }
    
    public final List<String> winnerIds = asList("002", "003", "005");

    private final Function<String, String> cityName = cacheFor((Function<String, City>)citites::get).andThen(City::getName);
    
    public List<String> getWinners() {
        return winnerIds.stream()
            .map(users::get)
            .map(user -> user.getName() + " @ "+ cityName.apply(user.getCityId()))
            .collect(toList());
    }
    
    @Test
    public void testCache2() {
        assertEquals("["
                    + "Ben @ Saskatoon, "
                    + "Charlie @ Bangkok, "
                    + "Frank @ Saskatoon"
                + "]", getWinners().toString());
        assertEquals("["
                    + "Users: 002 @ S, "
                    + "Cities: S, "
                    + "Users: 003 @ B, "
                    + "Cities: B, "
                    + "Users: 005 @ S"
                + "]",
                logs.toString());
    }
    
    // Array, List, Map, Either, Reference lens
    
    private static int add3(int a, int b, int c) {
        return a+b+c;
    }
    
    @Test
    public void testF3() {
        val add3  = Func.of(FunctionalJTest::add3);
        val add_5 = add3.apply(__, 5, __);
        assertEquals(8, add_5.apply(1, 2).intValue());
    }
    
    @Test
    public void testWithIndexTuple() {
        val list = asList("One", "Two", "Three");
        assertEquals("0: One,1: Two,2: Three", 
                list.stream()
                .map(withIndex(item->{
                    // TODO - It will be nice, if this can be feed to Func2 directly.
                    return item.index()  + ": " + item.value();
                }))
                .collect(joining(",")));
    }
    
    @Test
    public void testCheckWithIndexTuple() {
        val list = asList("One", "Two", "Three");
        assertEquals("One,Three", 
                list.stream()
                .filter(only(withIndex(item-> item.index() != 1)))
                .collect(joining(",")));
    }
    
    @Test
    public void testMapCheckWithIndexTuple() {
        val list = asList("One", "Two", "Three");
        assertEquals("0: One,2: Three", 
                list.stream()
                .map(withIndex())
                .filter(item-> item.index() != 1)
                .map(item->item.index()  + ": " + item.value())
                .collect(joining(",")));
    }
    
    @Test
    public void testDelimitWith() {
        val list = asList("One", "Two", "Three");
        assertEquals("One-:-Two-:-Three", 
                list.stream()
                .flatMap(delimitWith(":"))
                .collect(joining("-")));
    }
    
    
}

package nawaman.functionalj;

import static java.util.Arrays.asList;
import static nawaman.functionalj.FunctionalJ.cacheFor;
import static nawaman.functionalj.FunctionalJ.it;
import static nawaman.functionalj.FunctionalJ.only;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Supplier;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;

import lombok.ToString;
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
        persons.stream()
        .filter(only(Person::getName, startsWithTerm, "Jo"))
        .forEach(System.out::println);
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
                + "]", getWinners());
        assertEquals("["
                    + "Users: 002 @ S, "
                    + "Cities: S, "
                    + "Users: 003 @ B, "
                    + "Cities: B, "
                    + "Users: 005 @ S"
                + "]",
                logs.toString());
    }
    
    @ToString
    public static class Person {
        private final String name;
        private final City city;
        private final int age;
        private final List<String> words;
        private final Map<String, String> phones;
        
        public Person(String name, City city, int age, List<String> words, Map<String, String> phones) {
            this.name = name;
            this.city = city;
            this.age = age;
            this.words = (words != null) ? words : emptyList();
            this.phones = (phones != null) ? phones : emptyMap();
        }
        
        public String name()        { return name; }
        public City city()          { return city; }
        public int age()            { return age;  }
        public List<String> words() { return words; }
        
        public Person withName(String name)                 { return new Person(name, city, age, words, phones); }
        public Person withCity(City city)                   { return new Person(name, city, age, words, phones); }
        public Person withAge(int age)                      { return new Person(name, city, age, words, phones); }
        public Person withWords(List<String> words)         { return new Person(name, city, age, words, phones); }
        public Person withPhone(Map<String, String> phones) { return new Person(name, city, age, words, phones); }
    }
    
    @Test
    public void testLense() {
        val personName = new Lense<>(Person::withName, Person::name);
        val personCity = new Lense<>(Person::withCity, Person::city);
        val cityName   = new Lense<>(City::withName,   City::getName);
        val personCityName = personCity.compose(cityName);
        
        val person1 = new Person("Nawa", new City("R", "Regina"), 18, asList("Yo"), singletonMap("home", "555-555-5555"));
        
        assertEquals("Regina", personCityName.get.apply(person1));
        
        assertEquals("FunctionalJTest.Person("
                + "name=Nawa, "
                + "city=FunctionalJTest.City("
                +   "id=R, "
                +   "name=Regina), "
                + "age=18, "
                + "words=[Yo], "
                + "phones={home=555-555-5555})",
                person1.toString());
        
        assertEquals("FunctionalJTest.Person("
                + "name=NawaMan, "
                + "city=FunctionalJTest.City("
                +   "id=R, "
                +   "name=Regina), "
                + "age=18, "
                + "words=[Yo], "
                + "phones={home=555-555-5555})",
                personName.set.apply(person1, "NawaMan").toString());
        
        assertEquals("FunctionalJTest.Person("
                + "name=Nawa, "
                + "city=FunctionalJTest.City("
                +   "id=R, "
                +   "name=Bangkok), "
                + "age=18, "
                + "words=[Yo], "
                + "phones={home=555-555-5555})",
                personCityName.set.apply(person1, "Bangkok").toString());
        assertEquals("FunctionalJTest.Person("
                + "name=Nawa, "
                + "city=FunctionalJTest.City("
                +   "id=R, "
                +   "name=City of Regina), "
                + "age=18, "
                + "words=[Yo], "
                + "phones={home=555-555-5555})",
                personCityName.map(n->"City of " + n).andThen(Object::toString).apply(person1));
        
        String[] s = new String[] { "One", "Two", "Three" };
        assertEquals("[One, 2, Three]", ArrayLense.at(1).set.andThen(Arrays::toString).apply(s, "2"));
        
        List<String> l = asList("One", "Two", "Three");
        assertEquals("[One, 2, Three]", ListLense.<String>at(1).set.andThen(List::toString).apply(l, "2"));
        
        val personWords = new Lense<>(Person::withWords, Person::words);
        val firstWord  = personWords.compose(ListLense.<String>at(0));
        assertEquals("FunctionalJTest.Person("
                + "name=Nawa, "
                + "city=FunctionalJTest.City("
                +   "id=R, "
                +   "name=Regina), "
                + "age=18, "
                + "words=[Yee], "
                + "phones={home=555-555-5555})",
                firstWord.set.andThen(Object::toString).apply(person1, "Yee"));
    }
    
    public static class ArrayLense {
        public static <TYPE> Function<TYPE[], TYPE> get(int index) {
            return a -> a[index];
        }
        public static <TYPE> BiFunction<TYPE[], TYPE, TYPE[]> set(int index) {
            return (a,t) -> {
                val newA = (TYPE[])Array.newInstance(a.getClass().getComponentType(), a.length);
                System.arraycopy(a, 0, newA, 0, a.length);
                newA[index] = t;
                return newA;
            };
        }
        public static <TYPE> Lense<TYPE[], TYPE> at(int index) {
            return new Lense<TYPE[], TYPE>(set(index), get(index));
        }
    }
    public static class ListLense {
        private static final Map<Class, Supplier<List>> newListSuppliers = new ConcurrentHashMap<>();
        public static <TYPE> Function<List<TYPE>, TYPE> get(int index) {
            return list -> list.get(index);
        }
        public static <TYPE> BiFunction<List<TYPE>, TYPE, List<TYPE>> set(int index) {
            return (list,t) -> {
                @SuppressWarnings("rawtypes")
                Class<? extends List> listClass = list.getClass();
                @SuppressWarnings("unchecked")
                List<TYPE> newList = newListSuppliers.computeIfAbsent(listClass, clzz->{
                    try {
                        listClass.newInstance();
                        return ()->{
                            try {
                                return listClass.newInstance();
                            } catch (InstantiationException | IllegalAccessException e) {
                                throw new RuntimeException(e);
                            }
                        };
                    } catch (InstantiationException | IllegalAccessException e) {
                        return ()-> new ArrayList<>();
                    }
                }).get();
                
                newList.addAll(list);
                newList.set(index, t);
                return Collections.unmodifiableList(newList);
            };
        }
        public static <TYPE> Lense<List<TYPE>, TYPE> at(int index) {
            return new Lense<List<TYPE>, TYPE>(set(index), get(index));
        }
    }
    
    public static class Lense<TYPE, SUB> {
        public final BiFunction<TYPE, SUB, TYPE> set;
        public final Function<TYPE, SUB>         get;
        public Lense(BiFunction<TYPE, SUB, TYPE> setter, Function<TYPE, SUB> getter) {
            this.set = setter;
            this.get = getter;
        }
        public Function<TYPE, TYPE> map(Function<SUB, SUB> mapper) {
            return t -> set.apply(t, get.andThen(mapper).apply(t));
        }
        public Function<TYPE, TYPE> peek(BiConsumer<TYPE,SUB> consume) {
            return t -> {
                consume.accept(t, get.apply(t));
                return t;
            };
        }
        public <SUBSUB> Lense<TYPE, SUBSUB> compose(Lense<SUB, SUBSUB> subLense) {
            return new Lense<TYPE, SUBSUB> (
                    (t,ss) ->{
                        return set.apply(t, subLense.set.apply(get.apply(t), ss));
                    },
                    get.andThen(subLense.get)
            );
        }
    }
    
    // Array, List, Map, Either, Reference lense
    
}

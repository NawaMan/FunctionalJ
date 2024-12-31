package functionalj.lens;

import static functionalj.TestHelper.assertAsString;
import static java.util.Arrays.asList;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;
import java.util.function.Function;

import org.junit.Test;

import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.StringAccess;
import functionalj.list.FuncList;
import functionalj.result.Result;
import lombok.val;
import nullablej.nullable.Nullable;

public class AccessTest {
    
    @Test
    public void testIdentity() {
        val identity = (AnyAccess<Object, Object>)(Object host) -> host;
        assertAsString("Hello", identity.apply("Hello"));
        assertAsString("42",    identity.apply(42));
    }
    
    @Test
    public void testAsXXX() {
        val identity = (AnyAccess<Object, Object>)(Object host) -> host;
        assertAsString("Hello",        identity.asString    ().apply("Hello"));
        assertAsString("Hello World!", identity.asString    ("Hello %s!").apply("World"));
        assertAsString("Hello World!", identity.asString    (str -> "Hello " + str + "!").apply("World"));
        
        assertAsString("true", identity.asBoolean   (obj -> Boolean.parseBoolean("" + obj)).apply("true"));
        assertAsString("t",    identity.asCharacter (obj -> ("" + obj).charAt(0)).apply("true"));
        assertAsString("42",   identity.asInteger   (obj -> Integer.parseInt   ("" + obj)).apply("42"));
        assertAsString("42",   identity.asLong      (obj -> Long   .parseLong  ("" + obj)).apply("42"));
        assertAsString("42.0", identity.asDouble    (obj -> Double .parseDouble("" + obj)).apply("42"));
        assertAsString("42",   identity.asBigInteger(obj -> new BigInteger("" + obj)).apply("42"));
        assertAsString("42",   identity.asBigDecimal(obj -> new BigDecimal("" + obj)).apply("42"));
        
        assertAsString("\0",    identity.asCharacter (obj -> (obj == null) ? null : ("" + obj).charAt(0)).apply(null));
        assertAsString("false", identity.asBoolean   (obj -> (obj == null) ? null : Boolean.parseBoolean("" + obj)).apply(null));
        assertAsString("0",     identity.asInteger   (obj -> (obj == null) ? null : Integer.parseInt   ("" + obj)).apply(null));
        assertAsString("0",     identity.asLong      (obj -> (obj == null) ? null : Long   .parseLong  ("" + obj)).apply(null));
        assertAsString("0.0",   identity.asDouble    (obj -> (obj == null) ? null : Double .parseDouble("" + obj)).apply(null));
        
        assertAsString("t",    identity.asCharacterPrimitive(obj -> ("" + obj).charAt(0)).apply("true"));
        assertAsString("true", identity.asBooleanPrimitive  (obj -> Boolean.parseBoolean("" + obj)).apply("true"));
        assertAsString("42",   identity.asIntegerPrimitive  (obj -> Integer.parseInt   ("" + obj)).apply("42"));
        assertAsString("42",   identity.asLongPrimitive     (obj -> Long   .parseLong  ("" + obj)).apply("42"));
        assertAsString("42.0", identity.asDoublePrimitive   (obj -> Double .parseDouble("" + obj)).apply("42"));
        
        assertAsString("[Hello]", identity.asList(obj -> asList(obj))                                                                 .apply("Hello"));
        assertAsString("[Hello]", identity.asList(obj -> asList(obj),      read -> (AnyAccess<Object, Object>)(Object host) -> host)  .apply("Hello"));
        assertAsString("[Hello]", identity.asList(obj -> asList("" + obj), read -> (StringAccess<Object>)((Object host) -> "" + host)).apply("Hello"));
        
        assertAsString("[Hello]", identity.asFuncList(obj -> FuncList.of(obj))                                                                 .apply("Hello"));
        assertAsString("[Hello]", identity.asFuncList(obj -> FuncList.of(obj),      read -> (AnyAccess<Object, Object>)(Object host) -> host)  .apply("Hello"));
        assertAsString("[Hello]", identity.asFuncList(obj -> FuncList.of("" + obj), read -> (StringAccess<Object>)((Object host) -> "" + host)).apply("Hello"));
        
        assertAsString("Nullable.of(Hello)",      identity.asNullable().apply("Hello"));
        assertAsString("Optional[Hello]",         identity.asOptional().apply("Hello"));
        assertAsString("Result:{ Value: Hello }", identity.asResult()  .apply("Hello"));
        
        assertAsString("Nullable.EMPTY",         identity.asNullable().apply(null));
        assertAsString("Optional.empty",         identity.asOptional().apply(null));
        assertAsString("Result:{ Value: null }", identity.asResult()  .apply(null));
        
        Function<Function<Object, String>, StringAccess<Object>> objToStringAccess = (Function<Object, String> read) -> {
            return (StringAccess<Object>)((Object host) -> read.apply(host));
        };
        assertAsString("Nullable.of(Hello)",      identity.asNullable(host -> Nullable.of(host)        .map(String::valueOf), objToStringAccess).apply("Hello"));
        assertAsString("Optional[Hello]",         identity.asOptional(host -> Optional.ofNullable(host).map(String::valueOf), objToStringAccess).apply("Hello"));
        assertAsString("Result:{ Value: Hello }", identity.asResult  (host -> Result.valueOf(host)     .map(String::valueOf), objToStringAccess).apply("Hello"));
    }
    
}

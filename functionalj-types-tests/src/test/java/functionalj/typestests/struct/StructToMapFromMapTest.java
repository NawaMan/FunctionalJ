package functionalj.typestests.struct;


import static functionalj.typestests.TestHelper.assertAsString;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;

import org.junit.Test;

import functionalj.result.Result;
import functionalj.result.ValidationException;
import functionalj.types.Rule;
import functionalj.types.Struct;
import lombok.val;
import nullablej.nullable.Nullable;

public class StructToMapFromMapTest {
    
    @Struct
    void MyPrimitiveStruct(
            byte       myByte1,
            Byte       myByte2,
            short      myShort1,
            Short      myShort2,
            int        myInt1,
            Integer    myInteger2,
            long       myLong1,
            Long       myLong2,
            float      myFloat1,
            Float      myFloat2,
            double     myDouble1,
            Double     myDouble2,
            boolean    myBoolean1,
            Boolean    myBoolean2,
            char       myChar1,
            Character  myCharacter2,
            BigInteger myBigInteger,
            BigDecimal myBigDecimal) {}
    
    /**
     * This test aims to check if string can be used with primitive values.
     **/
    @Test
    public void testPrimitiveFromString() {
        val myStruct1 = new MyPrimitiveStruct(
                        (byte)   1,     (byte)   2, 
                        (short)  3,     (short)  4, 
                        (int)    5,     (int)    6, 
                        (long)   7L,    (long)   8L, 
                        (float)  9.9f,  (float)  10.10f, 
                        (double) 11.11, (double) 12.12, 
                        (boolean)true,  Boolean.FALSE,
                        'c', '\u0061',
                        BigInteger.valueOf(13),
                        new BigDecimal("14.14"));
        // Established base line. If this one fail, it is either the struct fields or its toString() changed. 
        assertAsString("MyPrimitiveStruct["
                        + "myByte1: 1, "
                        + "myByte2: 2, "
                        + "myShort1: 3, "
                        + "myShort2: 4, "
                        + "myInt1: 5, "
                        + "myInteger2: 6, "
                        + "myLong1: 7, "
                        + "myLong2: 8, "
                        + "myFloat1: 9.9, "
                        + "myFloat2: 10.1, "
                        + "myDouble1: 11.11, "
                        + "myDouble2: 12.12, "
                        + "myBoolean1: true, "
                        + "myBoolean2: false, "
                        + "myChar1: c, "
                        + "myCharacter2: a, "
                        + "myBigInteger: 13, "
                        + "myBigDecimal: 14.14"
                        + "]",
                        myStruct1);
        
        Map<String, Object> map1 = myStruct1.__toMap();
        val myStruct2 = MyPrimitiveStruct.fromMap(map1);
        // Check that fromMap from toMap works. If this one fails, either __toMap() or fromMap() changes.
        assertAsString("MyPrimitiveStruct["
                        + "myByte1: 1, "
                        + "myByte2: 2, "
                        + "myShort1: 3, "
                        + "myShort2: 4, "
                        + "myInt1: 5, "
                        + "myInteger2: 6, "
                        + "myLong1: 7, "
                        + "myLong2: 8, "
                        + "myFloat1: 9.9, "
                        + "myFloat2: 10.1, "
                        + "myDouble1: 11.11, "
                        + "myDouble2: 12.12, "
                        + "myBoolean1: true, "
                        + "myBoolean2: false, "
                        + "myChar1: c, "
                        + "myCharacter2: a, "
                        + "myBigInteger: 13, "
                        + "myBigDecimal: 14.14"
                        + "]",
                        myStruct2);
        
        // Male all the values to String.
        Map<String, Object> map2 = new HashMap<>(map1);
        map2.put("myByte1",      "10");
        map2.put("myByte2",      "20");
        map2.put("myShort1",     "30");
        map2.put("myShort2",     "40");
        map2.put("myInt1",       "50");
        map2.put("myInteger2",   "60");
        map2.put("myLong1",      "70");
        map2.put("myLong2",      "80");
        map2.put("myFloat1",     "90.9");
        map2.put("myFloat2",     "110.11");
        map2.put("myDouble1",    "60.6");
        map2.put("myDouble2",    "120.12");
        map2.put("myBoolean1",   "false");
        map2.put("myBoolean2",   "TRUE");
        map2.put("myBigInteger", "13");
        map2.put("myBigDecimal", "14.14");
        
        val myStruct3 = MyPrimitiveStruct.fromMap(map2);
        assertAsString("MyPrimitiveStruct["
                        + "myByte1: 10, "
                        + "myByte2: 20, "
                        + "myShort1: 30, "
                        + "myShort2: 40, "
                        + "myInt1: 50, "
                        + "myInteger2: 60, "
                        + "myLong1: 70, "
                        + "myLong2: 80, "
                        + "myFloat1: 90.9, "
                        + "myFloat2: 110.11, "
                        + "myDouble1: 60.6, "
                        + "myDouble2: 120.12, "
                        + "myBoolean1: false, "
                        + "myBoolean2: true, "
                        + "myChar1: c, "
                        + "myCharacter2: a, "
                        + "myBigInteger: 13, "
                        + "myBigDecimal: 14.14"
                        + "]", 
                        myStruct3);
        
        // Test mix cases for boolean
        map2.put("myBoolean1", "False");
        map2.put("myBoolean2", "tRue");
        val myStruct4 = MyPrimitiveStruct.fromMap(map2);
        assertAsString("false", myStruct4.myBoolean1);
        assertAsString("true",  myStruct4.myBoolean2);
        
        // Test char from int and String
        map2.put("myChar1",      0x61);
        map2.put("myCharacter2", "C");
        val myStruct5 = MyPrimitiveStruct.fromMap(map2);
        assertAsString("a", myStruct5.myChar1);
        assertAsString("C",  myStruct5.myCharacter2);
    }
    
    @Struct
    void SubData(String strValue, Integer intValue) {}
    
    
    // Test optional
    
    @Struct
    void StructWithOptional(
            Optional<String> optStr, 
            Optional<Integer> optInt1, 
            OptionalInt optInt2, 
            Optional<SubData> 
            optSubData) {}
    
    @Test
    public void testOptional() {
        val myStruct = new StructWithOptional(Optional.of("String"), Optional.of(42), OptionalInt.of(10), Optional.of(new SubData("str", 45)));
        assertAsString(
                "StructWithOptional["
                    + "optStr: Optional[String], "
                    + "optInt1: Optional[42], "
                    + "optInt2: OptionalInt[10], "
                    + "optSubData: Optional["
                        + "SubData["
                            + "strValue: str, "
                            + "intValue: 45"
                        + "]"
                    + "]"
                + "]", myStruct);
        
        val myMap = myStruct.__toMap();
        assertAsString(
                "{"
                    + "optInt1=42, "
                    + "optInt2=10, "
                    + "optStr=String, "
                    + "optSubData={"
                        + "intValue=45, "
                        + "strValue=str"
                    + "}"
                + "}",
                myMap);
        
        assertAsString(
                "StructWithOptional["
                    + "optStr: Optional[String], "
                    + "optInt1: Optional[42], "
                    + "optInt2: OptionalInt[10], "
                    + "optSubData: Optional["
                        + "SubData["
                            + "strValue: str, "
                            + "intValue: 45]"
                    + "]"
                + "]", 
                StructWithOptional.fromMap(myMap));
        
        // String to Optional String.
        myMap.put("optStr", "Text");
        // String to optional int.
        myMap.put("optInt1", "69");
        myMap.put("optInt2", "169");
        // Value without optional
        myMap.put("optSubData", new SubData("text", 42));
        
        assertAsString(
                "StructWithOptional["
                    + "optStr: Optional[Text], "
                    + "optInt1: Optional[69], "
                    + "optInt2: OptionalInt[169], "
                    + "optSubData: Optional["
                        + "SubData["
                            + "strValue: text, "
                            + "intValue: 42"
                        + "]"
                    + "]"
                + "]", 
                StructWithOptional.fromMap(myMap));
        
        // String to Optional String.
        myMap.put("optStr", Optional.of("Text"));
        // String to optional int.
        myMap.put("optInt1", Optional.of("75"));
        myMap.put("optInt2", OptionalInt.of(175));
        // Value without optional
        myMap.put("optSubData", Optional.of(new SubData("text", 42)));
        
        assertAsString(
                "StructWithOptional["
                    + "optStr: Optional[Text], "
                    + "optInt1: Optional[75], "
                    + "optInt2: OptionalInt[175], "
                    + "optSubData: Optional["
                        + "SubData["
                            + "strValue: text, "
                            + "intValue: 42"
                        + "]"
                    + "]"
                + "]", 
                StructWithOptional.fromMap(myMap));
    }
    
    // Test nullable, result, promise
    
    @Struct
    void StructWithNullableResult(
            Nullable<String> optStr1, 
            Nullable<Integer> optInt1,
            Nullable<SubData> optSubData1,
            Result<String> optStr2, 
            Result<Integer> optInt2,
            Result<SubData> optSubData2) {}
    
    @Test
    public void testNullableResultPromise() {
        val myStruct = new StructWithNullableResult(
                        Nullable.of("One"),
                        Nullable.of(10),
                        Nullable.of(new SubData("str", 110)),
                        Result.ofValue("One"),
                        Result.ofValue(10),
                        Result.ofValue(new SubData("str", 110)));
        assertAsString(
                "StructWithNullableResult["
                    + "optStr1: Nullable.of(One), "
                    + "optInt1: Nullable.of(10), "
                    + "optSubData1: Nullable.of(SubData[strValue: str, intValue: 110]), "
                    + "optStr2: Result:{ Value: One }, "
                    + "optInt2: Result:{ Value: 10 }, "
                    + "optSubData2: Result:{ Value: SubData[strValue: str, intValue: 110] }"
                + "]", myStruct);
        
        val myMap = myStruct.__toMap();
        assertAsString(
                "{"
                    + "optInt1=10, "
                    + "optInt2=10, "
                    + "optStr1=One, "
                    + "optStr2=One, "
                    + "optSubData1=SubData[strValue: str, intValue: 110], "
                    + "optSubData2=SubData[strValue: str, intValue: 110]"
                + "}",
                myMap);
        
        assertAsString(
                "StructWithNullableResult["
                    + "optStr1: Nullable.of(One), "
                    + "optInt1: Nullable.of(10), "
                    + "optSubData1: Nullable.of(SubData[strValue: str, intValue: 110]), "
                    + "optStr2: Result:{ Value: One }, "
                    + "optInt2: Result:{ Value: 10 }, "
                    + "optSubData2: Result:{ Value: SubData[strValue: str, intValue: 110] }"
                + "]", 
                StructWithNullableResult.fromMap(myMap));
        
        // String to Optional String.
        myMap.put("optStr", "Text");
        // String to optional int.
        myMap.put("optInt1", "69");
        myMap.put("optInt2", "169");
        // Value without optional
        myMap.put("optSubData", new SubData("text", 42));
        
        assertAsString(
                "StructWithNullableResult["
                    + "optStr1: Nullable.of(One), "
                    + "optInt1: Nullable.of(69), "
                    + "optSubData1: Nullable.of(SubData[strValue: str, intValue: 110]), "
                    + "optStr2: Result:{ Value: One }, "
                    + "optInt2: Result:{ Value: 169 }, "
                    + "optSubData2: Result:{ Value: SubData[strValue: str, intValue: 110] }"
                + "]", 
                StructWithNullableResult.fromMap(myMap));
        
        // String to Optional String.
        myMap.put("optStr", Nullable.of("Text"));
        // String to optional int.
//        myMap.put("optInt1", Nullable.of("75"));// TODO - This one does not work yet.
        myMap.put("optInt2", OptionalInt.of(175));
        // Value without optional
        myMap.put("optSubData", Nullable.of(new SubData("text", 42)));
        
        assertAsString(
                "StructWithNullableResult["
                    + "optStr1: Nullable.of(One), "
                    + "optInt1: Nullable.of(69), "
                    + "optSubData1: Nullable.of(SubData[strValue: str, intValue: 110]), "
                    + "optStr2: Result:{ Value: One }, "
                    + "optInt2: Result:{ Value: OptionalInt[175] }, "
                    + "optSubData2: Result:{ Value: SubData[strValue: str, intValue: 110] }"
                + "]", 
                StructWithNullableResult.fromMap(myMap));
    }
    
    // Test validate
    
    @Struct
    static String StructValidate(String name, int age) {
        if (name.trim().isEmpty())
            return "The name cannot be blank.";
        if (age < 0)
            return "The age cannot be negative: " + age;
        return null;
    }
    
    @Test
    public void testValidate() {
        val myStruct = new StructValidate("name", 10);
        assertAsString("StructValidate[name: name, age: 10]", myStruct);
        
        val myMap = myStruct.__toMap();
        assertAsString("{age=10, name=name}", myMap);
        
        assertAsString("StructValidate[name: name, age: 10]", StructValidate.fromMap(myMap));
        
        try {
            myMap.put("name", " ");
            StructValidate.fromMap(myMap);
            fail("Expect an exeption.");
        } catch (ValidationException e) {
            assertAsString("functionalj.result.ValidationException: The name cannot be blank.", e);
        }
        
        try {
            myMap.put("name", "name");
            myMap.put("age", -5);
            assertAsString("", StructValidate.fromMap(myMap));
            fail("Expect an exeption.");
        } catch (ValidationException e) {
            assertAsString("functionalj.result.ValidationException: The age cannot be negative: -5", e);
        }
    }
    
    
    // Test Acceptable
    
    @Rule("Int value must be positive: %s")
    static boolean IntPositive(int intValue) { return intValue >= 0; }
    
    @Struct
    void StructWithAcceptable(IntPositive myInt) {}
    
    @Test
    public void testAcceptable() {
        val myStruct =  new StructWithAcceptable(new IntPositive(42));
        
        assertAsString("StructWithAcceptable[myInt: IntPositive:{ Value: 42 }]", myStruct);
        
        val myMap = myStruct.__toMap();
        assertAsString("{myInt=42}", myMap);
        
        myMap.put("myInt", 42);
        assertAsString(
                "StructWithAcceptable[myInt: IntPositive:{ Value: 42 }]",
                StructWithAcceptable.fromMap(myMap));
        
        myMap.put("myInt", -5);
        assertAsString(
                "StructWithAcceptable[myInt: IntPositive:{ Invalid: Int value must be positive: -5 }]",
                StructWithAcceptable.fromMap(myMap));
        
        // Unacceptable value cannot be changed to map.
        val unacceptable = StructWithAcceptable.fromMap(myMap);
        try {
            unacceptable.__toMap();
            fail("Expect an exeption.");
        } catch (ValidationException e) {
            assertAsString("functionalj.result.ValidationException: Int value must be positive: -5", e);
        }
    }
    
//    @Struct
//    void MyTimeStruct() {}
//    
//    
    
}

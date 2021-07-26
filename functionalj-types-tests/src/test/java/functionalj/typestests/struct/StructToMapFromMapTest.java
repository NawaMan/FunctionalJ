package functionalj.typestests.struct;


import static functionalj.typestests.TestHelper.assertAsString;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import functionalj.types.Struct;
import lombok.val;

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
    
    
//    @Struct
//    void MyTimeStruct() {}
    
    
    
    // Test optional
    
    
    // Test nullable
    
    
    // Test result
    
    
    // Test promise
    
    
    // Test validate
    
}

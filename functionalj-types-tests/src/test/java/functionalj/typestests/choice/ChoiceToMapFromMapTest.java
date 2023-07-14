package functionalj.typestests.choice;

import static functionalj.typestests.TestHelper.assertAsString;
import java.math.BigDecimal;
import java.math.BigInteger;
import org.junit.Test;
import functionalj.types.Choice;
import lombok.val;

public class ChoiceToMapFromMapTest {

    @Choice
    interface MyPrimitiveChoiceSpec {

        void ByteValue(byte myByte1, Byte myByte2);

        void ShortValue(short myShort1, Short myShort2);

        void IntValue(int myInt1, Integer myInteger2);

        void LongValue(long myLong1, Long myLong2);

        void FloatValue(float myFloat1, Float myFloat2);

        void DoubleValue(double myDouble1, Double myDouble2);

        void BooleanValue(boolean myBoolean1, Boolean myBoolean2);

        void CharValue(char myChar1, Character myCharacter2);

        void BigIntegerValue(BigInteger myBigInteger);

        void BigDecimalValue(BigDecimal myBigDecimal);
    }

    /**
     * This test aims to check if string can be used with primitive values.
     */
    @Test
    public void testPrimitiveFromString() {
        val myChoiceByte1 = MyPrimitiveChoice.ByteValue((byte) 1, (byte) 2);
        val myChoiceShort1 = MyPrimitiveChoice.ShortValue((short) 3, (short) 4);
        val myChoiceInt1 = MyPrimitiveChoice.IntValue((int) 5, (int) 6);
        val myChoiceLong1 = MyPrimitiveChoice.LongValue((long) 7, (long) 8);
        val myChoiceFloat1 = MyPrimitiveChoice.FloatValue((float) 9.9, (float) 10.10);
        val myChoiceDouble1 = MyPrimitiveChoice.DoubleValue((double) 11.11, (double) 12.12);
        val myChoiceBoolean1 = MyPrimitiveChoice.BooleanValue(true, Boolean.FALSE);
        val myChoiceChar1 = MyPrimitiveChoice.CharValue('c', '\u0061');
        val myChoiceBigInt1 = MyPrimitiveChoice.BigIntegerValue(BigInteger.valueOf(13));
        val myChoiceBigDec1 = MyPrimitiveChoice.BigDecimalValue(new BigDecimal("14.14"));
        // Established base line. If this one fail, it is either the struct fields or its toString() changed.
        assertAsString("ByteValue(1,2)", myChoiceByte1);
        assertAsString("ShortValue(3,4)", myChoiceShort1);
        assertAsString("IntValue(5,6)", myChoiceInt1);
        assertAsString("LongValue(7,8)", myChoiceLong1);
        assertAsString("FloatValue(9.9,10.1)", myChoiceFloat1);
        assertAsString("DoubleValue(11.11,12.12)", myChoiceDouble1);
        assertAsString("BooleanValue(true,false)", myChoiceBoolean1);
        assertAsString("CharValue(c,a)", myChoiceChar1);
        assertAsString("BigIntegerValue(13)", myChoiceBigInt1);
        assertAsString("BigDecimalValue(14.14)", myChoiceBigDec1);
        // toMap
        val myChoiceByte1Map = myChoiceByte1.__toMap();
        val myChoiceShort1Map = myChoiceShort1.__toMap();
        val myChoiceInt1Map = myChoiceInt1.__toMap();
        val myChoiceLong1Map = myChoiceLong1.__toMap();
        val myChoiceFloat1Map = myChoiceFloat1.__toMap();
        val myChoiceDouble1Map = myChoiceDouble1.__toMap();
        val myChoiceBoolean1Map = myChoiceBoolean1.__toMap();
        val myChoiceChar1Map = myChoiceChar1.__toMap();
        val myChoiceBigInt1Map = myChoiceBigInt1.__toMap();
        val myChoiceBigDec1Map = myChoiceBigDec1.__toMap();
        assertAsString("{__tagged=ByteValue, myByte1=1, myByte2=2}", myChoiceByte1Map);
        assertAsString("{__tagged=ShortValue, myShort1=3, myShort2=4}", myChoiceShort1Map);
        assertAsString("{__tagged=IntValue, myInt1=5, myInteger2=6}", myChoiceInt1Map);
        assertAsString("{__tagged=LongValue, myLong1=7, myLong2=8}", myChoiceLong1Map);
        assertAsString("{__tagged=FloatValue, myFloat1=9.9, myFloat2=10.1}", myChoiceFloat1Map);
        assertAsString("{__tagged=DoubleValue, myDouble1=11.11, myDouble2=12.12}", myChoiceDouble1Map);
        assertAsString("{__tagged=BooleanValue, myBoolean1=true, myBoolean2=false}", myChoiceBoolean1Map);
        assertAsString("{__tagged=CharValue, myChar1=c, myCharacter2=a}", myChoiceChar1Map);
        assertAsString("{__tagged=BigIntegerValue, myBigInteger=13}", myChoiceBigInt1Map);
        assertAsString("{__tagged=BigDecimalValue, myBigDecimal=14.14}", myChoiceBigDec1Map);
        // Check that fromMap from toMap works. If this one fails, either __toMap() or fromMap() changes.
        assertAsString("ByteValue(1,2)", MyPrimitiveChoice.fromMap(myChoiceByte1Map));
        assertAsString("ShortValue(3,4)", MyPrimitiveChoice.fromMap(myChoiceShort1Map));
        assertAsString("IntValue(5,6)", MyPrimitiveChoice.fromMap(myChoiceInt1Map));
        assertAsString("LongValue(7,8)", MyPrimitiveChoice.fromMap(myChoiceLong1Map));
        assertAsString("FloatValue(9.9,10.1)", MyPrimitiveChoice.fromMap(myChoiceFloat1Map));
        assertAsString("DoubleValue(11.11,12.12)", MyPrimitiveChoice.fromMap(myChoiceDouble1Map));
        assertAsString("BooleanValue(true,false)", MyPrimitiveChoice.fromMap(myChoiceBoolean1Map));
        assertAsString("CharValue(c,a)", MyPrimitiveChoice.fromMap(myChoiceChar1Map));
        assertAsString("BigIntegerValue(13)", MyPrimitiveChoice.fromMap(myChoiceBigInt1Map));
        assertAsString("BigDecimalValue(14.14)", MyPrimitiveChoice.fromMap(myChoiceBigDec1Map));
        // Male all the values to String.
        myChoiceByte1Map.put("myByte1", "10");
        myChoiceByte1Map.put("myByte2", "20");
        myChoiceShort1Map.put("myShort1", "30");
        myChoiceShort1Map.put("myShort2", "40");
        myChoiceInt1Map.put("myInt1", "50");
        myChoiceInt1Map.put("myInteger2", "60");
        myChoiceLong1Map.put("myLong1", "70");
        myChoiceLong1Map.put("myLong2", "80");
        myChoiceFloat1Map.put("myFloat1", "90.9");
        myChoiceFloat1Map.put("myFloat2", "100.1");
        myChoiceDouble1Map.put("myDouble1", "110.11");
        myChoiceDouble1Map.put("myDouble2", "120.12");
        myChoiceBoolean1Map.put("myBoolean1", "false");
        myChoiceBoolean1Map.put("myBoolean2", "TRUE");
        myChoiceBigInt1Map.put("myBigInteger", "130");
        myChoiceBigDec1Map.put("myBigDecimal", "140.14");
        // Check that fromMap from toMap works. If this one fails, either __toMap() or fromMap() changes.
        assertAsString("ByteValue(10,20)", MyPrimitiveChoice.fromMap(myChoiceByte1Map));
        assertAsString("ShortValue(30,40)", MyPrimitiveChoice.fromMap(myChoiceShort1Map));
        assertAsString("IntValue(50,60)", MyPrimitiveChoice.fromMap(myChoiceInt1Map));
        assertAsString("LongValue(70,80)", MyPrimitiveChoice.fromMap(myChoiceLong1Map));
        assertAsString("FloatValue(90.9,100.1)", MyPrimitiveChoice.fromMap(myChoiceFloat1Map));
        assertAsString("DoubleValue(110.11,120.12)", MyPrimitiveChoice.fromMap(myChoiceDouble1Map));
        assertAsString("BooleanValue(false,true)", MyPrimitiveChoice.fromMap(myChoiceBoolean1Map));
        assertAsString("BigIntegerValue(130)", MyPrimitiveChoice.fromMap(myChoiceBigInt1Map));
        assertAsString("BigDecimalValue(140.14)", MyPrimitiveChoice.fromMap(myChoiceBigDec1Map));
        // Test mix cases for boolean
        myChoiceBoolean1Map.put("myBoolean1", "False");
        myChoiceBoolean1Map.put("myBoolean2", "tRue");
        assertAsString("BooleanValue(false,true)", MyPrimitiveChoice.fromMap(myChoiceBoolean1Map));
        // Test char from int and String
        myChoiceChar1Map.put("myChar1", 0x61);
        myChoiceChar1Map.put("myCharacter2", "C");
        assertAsString("CharValue(a,C)", MyPrimitiveChoice.fromMap(myChoiceChar1Map));
    }
    // There is no need for further testing ... this should indicates
    // that toMap/fromMap for Choice is using the same code with Struct.
}

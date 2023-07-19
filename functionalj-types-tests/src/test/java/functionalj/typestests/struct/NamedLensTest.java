package functionalj.typestests.struct;

import static functionalj.typestests.TestHelper.assertAsString;
import static functionalj.typestests.struct.StructNamedComplex.theStructNamedComplex;
import static functionalj.typestests.struct.StructNamedFuncList.theStructNamedFuncList;
import static functionalj.typestests.struct.StructNamedFuncMap.theStructNamedFuncMap;
import static functionalj.typestests.struct.StructNamedList.theStructNamedList;
import static functionalj.typestests.struct.StructNamedMap.theStructNamedMap;
import static functionalj.typestests.struct.StructNamedOptionalNullableResult.theStructNamedOptionalNullableResult;
import static functionalj.typestests.struct.StructNamedPrimitive.theStructNamedPrimitive;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.Test;
import functionalj.list.FuncList;
import functionalj.map.FuncMap;
import functionalj.result.Result;
import functionalj.types.Struct;
import lombok.val;
import nullablej.nullable.Nullable;

public class NamedLensTest {
    
    @Struct
    void StructNamedPrimitive(int intValue, long longValue, double doubleValue, boolean booleanValue) {
    }
    
    @Test
    public void testPrimitiveLens() {
        val struct = new StructNamedPrimitive(42, 75, 3.14, false);
        assertAsString("StructNamedPrimitive[intValue: 42, longValue: 75, doubleValue: 3.14, booleanValue: false]", struct);
        assertAsString("theStructNamedPrimitive", theStructNamedPrimitive);
        assertAsString("theStructNamedPrimitive.intValue", theStructNamedPrimitive.intValue);
        assertAsString("theStructNamedPrimitive.longValue", theStructNamedPrimitive.longValue);
        assertAsString("theStructNamedPrimitive.doubleValue", theStructNamedPrimitive.doubleValue);
        assertAsString("theStructNamedPrimitive.booleanValue", theStructNamedPrimitive.booleanValue);
    }
    
    @Struct
    void StructNamedComplex(int intValue, StructNamedPrimitive primitiveValue) {
    }
    
    @Test
    public void testComplexLens() {
        val primitiveStruct = new StructNamedPrimitive(42, 75, 3.14, false);
        val complexStruct = new StructNamedComplex(42, primitiveStruct);
        assertAsString("StructNamedComplex[" + "intValue: 42, " + "primitiveValue: StructNamedPrimitive[" + "intValue: 42, " + "longValue: 75, " + "doubleValue: 3.14, " + "booleanValue: false" + "]" + "]", complexStruct);
        assertAsString("theStructNamedComplex", theStructNamedComplex);
        assertAsString("theStructNamedComplex.intValue", theStructNamedComplex.intValue);
        assertAsString("theStructNamedComplex.primitiveValue", theStructNamedComplex.primitiveValue);
        assertAsString("theStructNamedComplex.primitiveValue.intValue", theStructNamedComplex.primitiveValue.intValue);
        assertAsString("theStructNamedComplex.primitiveValue.longValue", theStructNamedComplex.primitiveValue.longValue);
        assertAsString("theStructNamedComplex.primitiveValue.doubleValue", theStructNamedComplex.primitiveValue.doubleValue);
        assertAsString("theStructNamedComplex.primitiveValue.booleanValue", theStructNamedComplex.primitiveValue.booleanValue);
    }
    
    @Struct
    void StructNamedOptionalNullableResult(Optional<String> optText, Nullable<String> nullText, Result<String> resText) {
    }
    
    @Test
    public void testStructNamedOptionalNullableResult() {
        val struct = new StructNamedOptionalNullableResult(Optional.of("One"), Nullable.of("Two"), Result.valueOf("Three"));
        assertAsString("StructNamedOptionalNullableResult[" + "optText: Optional[One], " + "nullText: Nullable.of(Two), " + "resText: Result:{ Value: Three }" + "]", struct);
        assertAsString("theStructNamedOptionalNullableResult", theStructNamedOptionalNullableResult);
        assertAsString("theStructNamedOptionalNullableResult.optText", theStructNamedOptionalNullableResult.optText);
        assertAsString("theStructNamedOptionalNullableResult.nullText", theStructNamedOptionalNullableResult.nullText);
        assertAsString("theStructNamedOptionalNullableResult.resText", theStructNamedOptionalNullableResult.resText);
    }
    
    @Struct
    void StructNamedFuncList(int intValue, FuncList<String> texts) {
    }
    
    @Test
    public void testFuncListLens() {
        val struct = new StructNamedFuncList(42, FuncList.of("One", "Two"));
        assertAsString("StructNamedFuncList[intValue: 42, texts: [One, Two]]", struct);
        assertAsString("theStructNamedFuncList", theStructNamedFuncList);
        assertAsString("theStructNamedFuncList.intValue", theStructNamedFuncList.intValue);
        assertAsString("theStructNamedFuncList.texts", theStructNamedFuncList.texts);
        assertAsString("theStructNamedFuncList.texts.first()", theStructNamedFuncList.texts.first());
        assertAsString("theStructNamedFuncList.texts.last()", theStructNamedFuncList.texts.last());
        assertAsString("theStructNamedFuncList.texts.at(2)", theStructNamedFuncList.texts.at(2));
    }
    
    @Struct
    void StructNamedList(int intValue, List<String> texts) {
    }
    
    @Test
    public void testListLens() {
        val struct = new StructNamedList(42, FuncList.of("One", "Two"));
        assertAsString("StructNamedList[intValue: 42, texts: [One, Two]]", struct);
        assertAsString("theStructNamedList", theStructNamedList);
        assertAsString("theStructNamedList.intValue", theStructNamedList.intValue);
        assertAsString("theStructNamedList.texts", theStructNamedList.texts);
        assertAsString("theStructNamedList.texts.first()", theStructNamedList.texts.first());
        assertAsString("theStructNamedList.texts.last()", theStructNamedList.texts.last());
        assertAsString("theStructNamedList.texts.at(2)", theStructNamedList.texts.at(2));
    }
    
    @Struct
    void StructNamedFuncMap(int intValue, FuncMap<String, Integer> ints) {
    }
    
    @Test
    public void testFuncMapLens() {
        val struct = new StructNamedFuncMap(42, FuncMap.of("One", 1, "Two", 2));
        assertAsString("StructNamedFuncMap[intValue: 42, ints: {One:1, Two:2}]", struct);
        assertAsString("theStructNamedFuncMap", theStructNamedFuncMap);
        assertAsString("theStructNamedFuncMap.intValue", theStructNamedFuncMap.intValue);
        assertAsString("theStructNamedFuncMap.ints", theStructNamedFuncMap.ints);
        assertAsString("theStructNamedFuncMap.ints.get(\"One\")", theStructNamedFuncMap.ints.get("One"));
    }
    
    @Struct
    void StructNamedMap(int intValue, Map<String, Integer> ints) {
    }
    
    @Test
    public void testMapLens() {
        val struct = new StructNamedMap(42, FuncMap.of("One", 1, "Two", 2));
        assertAsString("StructNamedMap[intValue: 42, ints: {One:1, Two:2}]", struct);
        assertAsString("theStructNamedMap", theStructNamedMap);
        assertAsString("theStructNamedMap.intValue", theStructNamedMap.intValue);
        assertAsString("theStructNamedMap.ints", theStructNamedMap.ints);
        assertAsString("theStructNamedMap.ints.get(\"Two\")", theStructNamedMap.ints.get("Two"));
    }
}

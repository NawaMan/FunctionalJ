package functionalj.kinds.functor;

import static java.util.stream.Collectors.joining;
import static java.util.stream.IntStream.range;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import functionalj.types.list.FuncList;
import lombok.val;

public class GeneratorFunctorMapToTupleToObject {
    
    @Test
    public void testGenerator() {
        assertEquals(
                "    public default <T1, T2> \n" + 
                "        Result<Tuple2<T1, T2>> map(\n" + 
                "                Function<? super DATA, ? extends T1> mapper1,\n" + 
                "                Function<? super DATA, ? extends T2> mapper2) {\n" + 
                "        return map(mapper1, mapper2,\n"+
                "                   (v1, v2) -> Tuple2.of(v1, v2));\n" + 
                "    }\n" +
                "    \n" +
                "    public default <T1, T2, T> \n" + 
                "        Result<T> map(\n" + 
                "                Function<? super DATA, ? extends T1> mapper1,\n" + 
                "                Function<? super DATA, ? extends T2> mapper2,\n" + 
                "                BiFunction<T1, T2, T> function) {\n" + 
                "        return map(each -> {\n" + 
                "            val v1 = mapper1.apply(each);\n" + 
                "            val v2 = mapper2.apply(each);\n" + 
                "            val v  = function.apply(v1, v2);\n" + 
                "            return v;\n" + 
                "        });\n" + 
                "    }", 
                generate("Result", 2));
    }
    
    public static void main(String[] args) {
        val typeName = "Result";
        range(2, 7)
        .mapToObj(size -> generate(typeName, size))
        .forEach(System.out::println);
    }
    
    private static String generate(String typeName, int tupleSize) {
        val newLine = "\n";
        val template = FuncList.of(
                "    public default <%3$s> \n" + 
                "        %1$s<Tuple%2$s<%3$s>> map(\n" + 
                "%4$s) {\n" + 
                "        return map(%5$s,\n" +
                "                   (%6$s) -> Tuple%2$s.of(%6$s));\n" + 
                "    }\n" +
                "    \n" +
                "    public default <%3$s, T> \n" + 
                "        %1$s<T> map(\n" + 
                "%4$s,\n" + 
                "                %7$s<%3$s, T> function) {\n" + 
                "        return map(each -> {\n" + 
                "%8$s" + 
                "            val v  = function.apply(%6$s);\n" + 
                "            return v;\n" + 
                "        });\n" + 
                "    }")
                .joining(newLine);
        
        return String.format(template,
                typeName,
                tupleSize,
                loopTemplate(tupleSize, "T%s", ", "),
                loopTemplate(tupleSize, "                Function<? super DATA, ? extends T%1$s> mapper%1$s", "," + newLine),
                loopTemplate(tupleSize, "mapper%1$s", ", "),
                loopTemplate(tupleSize, "v%1$s", ", "),
                (tupleSize == 2) ? "BiFunction" : "Func" + tupleSize,
                loopTemplate(tupleSize, "            val v%1$s = mapper%1$s.apply(each);\n", "")
            );
    }

    private static String loopTemplate(int tupleSize, String temp, String delimiter) {
        return range(1, tupleSize + 1)
                .mapToObj(n -> String.format(temp, n))
                .collect(joining(delimiter));
    }
    
}

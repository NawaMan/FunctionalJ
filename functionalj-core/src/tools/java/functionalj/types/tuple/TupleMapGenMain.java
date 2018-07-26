package functionalj.types.tuple;

import static functionalj.functions.StringFunctions.appendWith;
import static functionalj.functions.StringFunctions.prependWith;
import static functionalj.functions.StringFunctions.wrapWith;
import static functionalj.lens.Access.$I;
import static functionalj.lens.Access.$S;
import static functionalj.lens.Access.theBoolean;
import static java.util.stream.IntStream.range;

import java.util.Objects;
import java.util.function.BiFunction;

import functionalj.functions.Func;
import functionalj.functions.Func2;
import functionalj.functions.StringFunctions;
import functionalj.lens.Access;
import functionalj.types.list.FunctionalList;
import functionalj.types.result.Result;
import lombok.val;

public class TupleMapGenMain {
    
    
//    public default <NT2, NT3, NT4> Tuple4<T1, NT2, NT3, NT4> map(
//            Absent                    absent1, 
//            Function<? super T2, NT2> mapper2, 
//            Function<? super T3, NT3> mapper3, 
//            Function<? super T4, NT4> mapper4) {
//        val _1 = _1();
//        val _2 = _2();
//        val _3 = _3();
//        val _4 = _4();
//        val n1 = _1;
//        val n2 = mapper2.apply(_2);
//        val n3 = mapper3.apply(_3);
//        val n4 = mapper4.apply(_4);
//        return Tuple.of(n1, n2, n3, n4);
//    }
//    
    public static void main(String[] args) {
        val tupleSize = 6;
        val template =
                "    public default <%s> Tuple%d<%s> map(\n" + 
                "%s) {\n" + 
                "        return Tuple.of(\n" + 
                "%s);\n" +
                "    }";
        val typeParamDefTemplates  = Tuple.of((String)null, "NT%1$s");
        val tupleParamDefTemplates = Tuple.of("T%1$s",      "NT%1$s");
        val paramTemplates = Tuple.of("Absent                    absent%1$s", "Function<? super T%1$s, NT%1$s> mapper%1$s");
        val paramIndent    = "            ";
        
        val callTemplates  = Tuple.of("_%1$s()", "mapper%1$s.apply(_%1$s())");
        val callIndent     = "                ";
        
        range(0, (int)Math.pow(2, tupleSize)).mapToObj(i -> (Integer)i)
        .filter ($I.thatIsNot(0))
        .map    (index -> range(0, tupleSize).mapToObj(i -> !$I.bitAt(i).applyTo(index)))
        .map    (FunctionalList::ofStream)
        .forEach(flags -> {
            val typeParamDefs  = flags.map(generateEach(typeParamDefTemplates)).filter(Objects::nonNull).joining(", ");
            val tupleParamDefs = flags.map(generateEach(tupleParamDefTemplates)).filter(Objects::nonNull).joining(", ");
            
            val params = flags.map(generateEach(paramTemplates)).map(prependWith(paramIndent)).joining(",\n");
            val calls  = flags.map(generateEach(callTemplates)) .map(prependWith(callIndent)) .joining(",\n");
            
            System.out.printf(template, typeParamDefs, tupleSize, tupleParamDefs, params, calls);
            System.out.println();
            
            System.out.println("    ");
        });
    }
    
    private static Func2<Integer, Boolean, String> generateEach(Tuple2<String, String> templates) {
        val trueTemplate  = templates._1();
        val falseTemplate = templates._2();
        return Func.of((Integer i, Boolean e)->{ 
            return Result.of(e ? trueTemplate : falseTemplate)
                    .map(template -> String.format(template, i + 1))
                    .get();
        });
    }
    
}

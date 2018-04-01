package nawaman.functionalj.functions;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

import lombok.val;
import nawaman.functionalj.types.MayBe;

public class MFunc1Test {
    
    @Test
    public void test() {
        val strLength = MFunc1.of((String  s) -> MayBe.of(s.length()));
        val time2     = MFunc1.of((Integer i) -> MayBe.of(i * 2));
        val plus1     = MFunc1.of((Integer i) -> MayBe.of(i + 1));
        val strLengthTimes2Plus1 = strLength.chain(time2).chain(plus1);
        assertThat(strLengthTimes2Plus1.apply("Hello").toString()).isEqualTo("Just(11)");
    }
}

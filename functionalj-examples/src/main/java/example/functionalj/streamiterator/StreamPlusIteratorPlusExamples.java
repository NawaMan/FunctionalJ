package example.functionalj.streamiterator;

import static example.functionalj.streamiterator.StreamPlusIteratorPlusExamples.Hand.Paper;
import static example.functionalj.streamiterator.StreamPlusIteratorPlusExamples.Hand.Rock;
import static example.functionalj.streamiterator.StreamPlusIteratorPlusExamples.Hand.Scissors;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import functionalj.stream.StreamPlus;
import functionalj.stream.intstream.IntStreamPlus;
import lombok.val;


public class StreamPlusIteratorPlusExamples {
    
    enum Hand {
        Rock, Paper, Scissors;
        
        public int play(Hand anotherHand) {
            if (this == anotherHand)
                return 0;
            
          switch (this) {
              case Rock:     return (anotherHand == Paper)    ? -1 : 1;
              case Paper:    return (anotherHand == Scissors) ? -1 : 1;
              case Scissors: return (anotherHand == Rock)     ? -1 : 1;
          }
          throw new IllegalArgumentException(anotherHand + "!");
        }
    }
    
    @Test
    public void testCombine() {
        val player1 = StreamPlus.of(Paper, Paper, Rock);
        val player2 = StreamPlus.of(Rock,  Paper, Scissors);
        
        assertEquals(
                "Player1 score: 2",
                "Player1 score: " +  player1.zipWith(player2, Hand::play).mapToInt(Integer::intValue).sum());
    }
    
    @Test
    public void testVariantRead() {
        val bytes = IntStreamPlus.of(78, 97, 119, 97, 0, 2, 0, 1).boxed().iterator();
        val magicNumber = bytes.mapNext(4, bs -> new String(bs.toByteArray(Integer::byteValue)));
        val majorVersion = bytes.pullNext(2).get().toList().toString();
        val minorVersion = bytes.pullNext(2).get().toList().toString();
        assertEquals("Nawa", magicNumber.get());
        assertEquals("[0, 2]",     majorVersion);
        assertEquals("[0, 1]",     minorVersion);
    }
    
}

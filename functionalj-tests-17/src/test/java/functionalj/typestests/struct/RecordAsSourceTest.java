package functionalj.typestests.struct;

import static functionalj.TestHelper.assertAsString;
import static functionalj.typestests.struct.Dot.theDot;
import static functionalj.typestests.struct.Point.thePoint;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import functionalj.types.Self;
import functionalj.types.Struct;

public class RecordAsSourceTest {
    
    @Struct(name = "Point")
    static record PointSpec(int x, int y) {
        // Use `Self` as wrapper.  (It as needed for some older compiler).
        public static Self create(int x, int y) {
            return functionalj.types.Self.wrap(new Point(x, y));
        }
        // Use `Self` as wrapper.  (It as needed for some older compiler).
        public static double absolute(Self self, double factor) {
            var point = Self.<Point>unwrap(self);
            return factor * Math.sqrt(point.x()*point.x() + point.y()*point.y());
        }
    }
    
    @Struct(name = "Dot")
    static record DotSpec(int x, int y) {
        // Use the generated type `Dot` directly in the signature
        public static Dot create(int x, int y) {
            return new Dot(x, y);
        }
        // Use the generated type `Dot` directly in the signature
        public static double absolute(Dot self, double factor) {
            var dot = self;
            return factor * Math.sqrt(dot.x()*dot.x() + dot.y()*dot.y());
        }
    }
    
    @Test
    public void testPoint() {
        var point = new Point(3, 5);
        assertEquals(3, point.x());
        assertEquals(5, point.y());
        assertAsString("Point[x=3, y=5]", point);
        assertAsString("Point[x=4, y=5]", point.withX(4));
        assertEquals(5, point.withY(4).pipeTo(thePoint.x.square().plus(thePoint.y.square()).squareRoot().floorToInt()).intValue());
    }
    
    @Test
    public void testPointStaticMethod() {
        assertAsString("Point[x=3, y=4]",   Point.create(3, 4));
    }
    
    @Test
    public void testPointNonStaticMethod() {
        assertAsString("10.0",new Point(3, 4).absolute(2));
    }
    
    @Test
    public void testDot() {
        var dot = new Dot(3, 5);
        assertEquals(3, dot.x());
        assertEquals(5, dot.y());
        assertAsString("Dot[x=3, y=5]", dot);
        assertAsString("Dot[x=4, y=5]", dot.withX(4));
        assertEquals(5, dot.withY(4).pipeTo(theDot.x.square().plus(theDot.y.square()).squareRoot().floorToInt()).intValue());
    }
    
    @Test
    public void testDotStaticMethod() {
        assertAsString("Dot[x=3, y=4]",   Dot.create(3, 4));
    }
    
    @Test
    public void testDotNonStaticMethod() {
        assertAsString("10.0", new Dot(3, 4).absolute(2));
    }
    
}

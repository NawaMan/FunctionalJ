package functionalj.typestests.choice;

import static functionalj.typestests.choice.Temperature.Celsius;
import static functionalj.typestests.choice.Temperature.Fahrenheit;
import org.junit.Assert;
import org.junit.Test;
import functionalj.types.Choice;
import functionalj.types.choice.Self;
import lombok.val;

public class UnitOfMeasureTest {

    @Choice
    static interface TemperatureSpec {

        void Celsius(double celsius);

        void Fahrenheit(double fahrenheit);

        default Temperature.Fahrenheit toFahrenheit(Self self) {
            Temperature temp = self.unwrap();
            return temp.match().celsius(c -> Temperature.Fahrenheit(c.celsius() * 1.8 + 32.0)).fahrenheit(f -> f);
        }

        default Temperature.Celsius toCelsius(Self self) {
            Temperature temp = self.unwrap();
            return temp.match().celsius(c -> c).fahrenheit(f -> Temperature.Celsius((f.fahrenheit() - 32.0) / 1.8));
        }
    }

    @Test
    public void test() {
        val celsius = Celsius(0.0);
        Assert.assertEquals("Celsius(0.0)", "" + celsius);
        Assert.assertEquals("Fahrenheit(32.0)", "" + celsius.toFahrenheit());
        Assert.assertEquals("Celsius(0.0)", "" + celsius.toCelsius());
        val fahrenheit = Fahrenheit(0);
        Assert.assertEquals("Fahrenheit(0.0)", "" + fahrenheit);
        Assert.assertEquals("Fahrenheit(0.0)", "" + fahrenheit.toFahrenheit());
        Assert.assertEquals("Celsius(-17.77777777777778)", "" + fahrenheit.toCelsius());
    }
}

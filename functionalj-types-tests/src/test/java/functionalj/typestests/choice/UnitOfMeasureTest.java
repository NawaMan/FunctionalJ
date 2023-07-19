// ============================================================================
// Copyright (c) 2017-2023 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
// ----------------------------------------------------------------------------
// MIT License
// 
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
// 
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
// 
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.
// ============================================================================
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

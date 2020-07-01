// ============================================================================
// Copyright (c) 2017-2020 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package example.functionalj.function;

import static functionalj.function.Absent.__;
import static functionalj.function.Func.F;
import static functionalj.function.Func.f;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.function.Function;
import java.util.function.Supplier;

import org.junit.Test;

import functionalj.function.Func;
import functionalj.function.Func1;
import functionalj.list.FuncList;
import functionalj.map.FuncMap;
import functionalj.result.Result;
import functionalj.tuple.Tuple;
import lombok.val;

public class FunctionExamples {
    
    public int toInt(String str) {
        return Integer.parseInt(str);
    }
    
    public List<String> readLines(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName));
    }
    
    @Test
    public void example01_Function() {
        val toInt = (Func1<String, Integer>)this::toInt;
        assertEquals(42, (int)toInt.apply("42"));
    }
    
    @Test
    public void example02_Apply() {
        val toInt    = (Func1<String, List<String>>)this::readLines;
        val fileName = "FileNotExist.file";
        
        try {
            toInt.applyUnsafe(fileName);
            fail("Expect a problem.");
        } catch (Exception e) {
            assertEquals(
                    "java.nio.file.NoSuchFileException: FileNotExist.file",
                    e.toString());
        }
        
        try {
            toInt.apply(fileName);
            fail("Expect a problem.");
        } catch (Exception e) {
            assertEquals(
                    "functionalj.function.FunctionInvocationException: java.nio.file.NoSuchFileException: FileNotExist.file",
                    e.toString());
        }
        
        assertEquals(
                "Result:{ Exception: java.nio.file.NoSuchFileException: FileNotExist.file }",
                toInt.applySafely(fileName).toString());
    }
    
    @Test
    public void example03_Create() {
        val toInt = Func.of(this::toInt);
        assertEquals(42, (int)toInt.apply("42"));
    }
    
    @Test
    public void example04_Create() {
        val toInt = f(this::toInt);
        assertEquals(42, (int)toInt.apply("42"));
    }
    
    @Test
    public void example04_ToString() {
        val toInt = f(this::toInt);
        assertTrue(toInt.toString().startsWith("example.functionalj.function.FunctionExamples$$Lambda$"));
        // example.functionalj.accesslens.FunctionExamples$$Lambda$2/460332449@726f3b58
        // System.out.println(toInt.toString());
    }
    
    @Test
    public void example05_ToString_Name() {
        val toInt = f("Str2Int", this::toInt);
        assertEquals("F1::Str2Int", toInt.toString());
    }
    
    @Test
    public void example06_ToString_Stack() {
        val toInt = F(this::toInt);
        assertEquals("F1@example.functionalj.function.FunctionExamples#123", toInt.toString());
    }
    
    @Test
    public void example07_ToString_StackName() {
        val toInt = F("Str2Int", this::toInt);
        assertEquals("F1::Str2Int@example.functionalj.function.FunctionExamples#129", toInt.toString());
    }
    
    @Test
    public void example08_Safely() {
        val readLines = f(this::readLines).safely();
        val lines     = readLines.apply("FileNotFound.txt");
        assertEquals(
                "Result:{ Exception: java.nio.file.NoSuchFileException: FileNotFound.txt }",
                lines.toString());
    }
    
    @Test
    public void example08_Optionally() {
        val readLines = f(this::readLines).optionally();
        val lines     = readLines.apply("FileNotFound.txt");
        assertEquals("Optional.empty", lines.toString());
    }
    
    @Test
    public void example09_Async() throws InterruptedException {
        val lock = new CountDownLatch(1);
        
        val readLines = f(this::readLines).async();
        readLines
                .apply        ("FileNotFound.txt")
                .whenAbsentUse(FuncList.empty())
                .subscribe    (lines -> {
                    assertEquals("[]", lines.toString());
                    lock.countDown();
                });
        lock.await();
    }
    
    @Test
    public void example15_orElse() {
        val readLines = f(this::readLines);
        val lines     = readLines.orElse("FileNotFound.txt", FuncList.empty());
        assertEquals("[]", lines.toString());
    }
    
    @Test
    public void example16_DefaultReturn() {
        val readLines = f(this::readLines).whenAbsentUse(FuncList.empty());
        val lines     = readLines.apply("FileNotFound.txt");
        assertEquals("[]", lines.toString());
    }
    
    @Test
    public void example10_FlexibleInputs() {
        val toInt = f(this::toInt);
        
        assertEquals("42",      "" + toInt.apply("42"));
        assertEquals("[1, 42]", "" + toInt.applyTo(FuncList.of("1", "42")));
        
        assertEquals(
                "Result:{ Value: 42 }",
                "" + toInt.applyTo(Result  .valueOf("42")));
        
        assertEquals(
                "{One:1, Forty-Two:42}",
                "" + toInt.applyTo(FuncMap.of("One", "1", "Forty-Two", "42")));
        
        val supplier = (Supplier<String>)()->"42";
        assertEquals("42", "" + toInt.applyTo(supplier).get());
        
        val function = (Function<Integer, String>)(i->("" + i));
        assertEquals("42", "" + toInt.applyTo(function).apply(42));
    }
    
    @Test
    public void example11_Tuple() {
        val add = f((Integer a, Integer b)->a + b);
        val pair = Tuple.of(5, 7);
        assertEquals("12", "" + add.applyTo(pair));
    }
    
    @Test
    public void example12_Bind() {
        val add = f((Integer a, Integer b)->a + b);
        val addFive_1 = add.bind1(5);
        val addFive_2 = add.bind2(5);
        val addFive_3 = add.bind(__, 5);
        val addFive_4 = add.bind(5, __);
        assertTrue(addFive_1 instanceof Function);
        assertTrue(addFive_2 instanceof Function);
        assertTrue(addFive_3 instanceof Function);
        assertTrue(addFive_4 instanceof Function);
    }
    
    @Test
    public void example13_Currying() {
        val add = f((Integer a, Integer b)->a + b);
        assertEquals("12", "" + add.applyTo(5).apply(7));
    }
    
    @Test
    public void example14_Composition() {
        val add = f((Integer a, Integer b)->a + b);
        val sum = add.then(i -> "Sum: " + i);
        assertEquals("Sum: 12", "" + sum.apply(5, 7));
    }
    
}

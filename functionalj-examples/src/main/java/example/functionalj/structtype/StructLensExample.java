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
package example.functionalj.structtype;

import static example.functionalj.structtype.Company.theCompany;
import static example.functionalj.structtype.Personel.thePersonel;
import static functionalj.list.FuncList.listOf;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import functionalj.map.FuncMap;
import functionalj.types.Struct;
import lombok.val;

public class StructLensExample {
    
    @Struct
    void Personel(int id, String firstName, String lastName, double salary, boolean isOnSite) {}
    
    @Struct
    void Company(String name, FuncMap<Integer, Personel> employees) {}
    
    @Test
    public void testLensToMap() {
        val company = new Company("HighProfitCorp", listOf(
                    new Personel(1, "John", "Doe", 100_000, true),
                    new Personel(2, "Jane", "Smith", 150_000, false)
                )
                .toMap      (thePersonel.id)
                .sortedByKey(Integer::compare));
        assertEquals(
                "Company["
                + "name: HighProfitCorp, "
                + "employees: {"
                + "1:Personel[id: 1, firstName: John, lastName: Doe, salary: 100000.0, isOnSite: true], "
                + "2:Personel[id: 2, firstName: Jane, lastName: Smith, salary: 150000.0, isOnSite: false]"
                + "}"
                + "]", 
                company.toString());
        val changeJaneFamilyName
                = theCompany
                .employees
                .get(2)
                .lastName
                .changeTo("Skywalker");
        assertEquals(
                "Company["
                + "name: HighProfitCorp, "
                + "employees: {"
                + "1:Personel[id: 1, firstName: John, lastName: Doe, salary: 100000.0, isOnSite: true], "
                + "2:Personel[id: 2, firstName: Jane, lastName: Skywalker, salary: 150000.0, isOnSite: false]"
                + "}"
                + "]", 
                changeJaneFamilyName
                .apply(company)
                .withEmployees(e -> e.sortedByKey(Integer::compare))
                .toString());
    }
    
    // TODO - Must uncomment this.
//    @Test
//    public void testPrimitiveField() {
//        assertEquals("[2]", 
//                listOf(
//                        new Personel(1, "John", "Doe", 100_000, true),
//                        new Personel(2, "Jane", "Smith", 150_000, false)
//                )
//                .filter  (eachPersonel.isOnSite.negate())
//                .filter  (eachPersonel.salary.thatGreaterThan(120_000.0))
//                .mapToInt(eachPersonel.id)
//                .toImmutableList()
//                .toString());
//    }

}

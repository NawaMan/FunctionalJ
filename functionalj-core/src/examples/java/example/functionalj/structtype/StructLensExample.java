package example.functionalj.structtype;

import static example.functionalj.structtype.Company.theCompany;
import static example.functionalj.structtype.Personel.thePersonel;
import static functionalj.list.FuncList.listOf;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import functionalj.annotations.Struct;
import functionalj.map.FuncMap;
import lombok.val;

public class StructLensExample {
    
    @Struct
    void Personel(int id, String firstName, String lastName) {}
    
    @Struct
    void Company(String name, FuncMap<Integer, Personel> employees) {}
    
    @Test
    public void testLensToMap() {
        val company = new Company("HighProfitCorp", listOf(
                    new Personel(1, "John", "Doe"),
                    new Personel(2, "Jane", "Smith")
                ).toMap(thePersonel.id));
        assertEquals(
                "Company["
                + "name: HighProfitCorp, "
                + "employees: {"
                +   "1:Personel[id: 1, firstName: John, lastName: Doe], "
                +   "2:Personel[id: 2, firstName: Jane, lastName: Smith]"
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
                +   "1:Personel[id: 1, firstName: John, lastName: Doe], "
                +   "2:Personel[id: 2, firstName: Jane, lastName: Skywalker]"
                + "}"
                + "]", 
                changeJaneFamilyName.apply(company).toString());
    }

}

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
package functionalj.map;

import static java.util.Comparator.reverseOrder;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Comparator;
import java.util.Map;
import java.util.Objects;

import org.junit.Test;

import functionalj.list.FuncList;
import lombok.val;

public class ImmutableMapTest {

    @Test
    public void testGetFindSelect() {
        assertEquals("Two",        "" + FuncMap.of("1", "One", "2", "Two").get("2"));
        assertEquals("Three",      "" + FuncMap.of("1", "One", "2", "Two").getOrDefault("3", "Three"));
        assertEquals("Two",        "" + FuncMap.of("1", "One", "2", "Two").findBy("2").get());
        assertEquals("Three",      "" + FuncMap.of("1", "One", "2", "Two").findBy("3").orElse("Three"));
        assertEquals("[One, Two]", "" + FuncMap.of("1", "One", "2", "Two", "10", "Ten").select(key -> key.length() == 1));
    }
    
    @Test
    public void testToString() {
        assertEquals("[1, 2]",         "" + FuncMap.of("1", "One", "2", "Two").keys().sorted());
        assertEquals("[One, Two]",     "" + FuncMap.of("1", "One", "2", "Two").values().sorted());
        assertEquals("{1:One, 2:Two}", "" + FuncMap.of("1", "One", "2", "Two").sorted());
    }
    
    @Test
    public void testWith() {
        assertEquals(2, FuncMap.of("1", "One", "2", "Two").with("2", "Three").size());
        assertEquals(3, FuncMap.of("1", "One", "2", "Two").with("3", "Three").size());
        
        assertEquals("{1:One, 2:Two, 3:Three}", "" + FuncMap.of("1", "One", "2", "Two").with("3", "Three").sorted());
        assertEquals("{1:Un, 2:Two}",           "" + FuncMap.of("1", "One", "2", "Two").with("1", "Un").sorted());
        assertEquals("{1:One, 2:Du}",           "" + FuncMap.of("1", "One", "2", "Two").with("2", "Du").sorted());
    }
    
    @Test
    public void testSorted() {
        assertEquals("{1:One, 2:Two}", "" + FuncMap.of("1", "One", "2", "Two").sorted());
        assertEquals("{1:One, 2:Two}", "" + FuncMap.of("2", "Two", "1", "One").sorted());
    }
    
    @Test
    public void testDuplicateElement() {
        assertEquals("{1:Two}", "" + FuncMap.of("1", "One", "1", "Two").sorted());
    }
    
    @Test
    public void testWith_Full() {
        val orgMap = FuncMap.of("1", "One", "2", "Two");
        val newMap = orgMap.with("3", "Three");
        val repMap = orgMap.with("2", "Du");
        
        assertEquals("{1:One, 2:Two}",          orgMap.toString());
        assertEquals("{1:One, 2:Two, 3:Three}", newMap.toString());
        assertEquals("{1:One, 2:Du}",           repMap.toString());
        
        assertEquals(FuncMap.of("1", "One", "2", "Two"              ).hashCode(), orgMap.hashCode());
        assertEquals(FuncMap.of("1", "One", "2", "Two", "3", "Three").hashCode(), newMap.hashCode());
        assertEquals(FuncMap.of("1", "One", "2", "Du"               ).hashCode(), repMap.hashCode());
        
        assertEquals(2, orgMap.size());
        assertEquals(3, newMap.size());
        assertEquals(2, repMap.size());
        
        assertTrue(orgMap.hasKey("2"));
        assertTrue(orgMap.hasKey("2"::equals));
        assertTrue(newMap.hasKey("2"));
        assertTrue(newMap.hasKey("2"::equals));
        assertTrue(repMap.hasKey("2"));
        assertTrue(repMap.hasKey("2"::equals));
        
        assertFalse(orgMap.hasKey("3"));
        assertFalse(orgMap.hasKey("3"::equals));
        assertTrue (newMap.hasKey("3"));
        assertTrue (newMap.hasKey("3"::equals));
        assertFalse(repMap.hasKey("3"));
        assertFalse(repMap.hasKey("3"::equals));
        
        assertTrue(orgMap.containsKey("2"));
        assertTrue(newMap.containsKey("2"));
        assertTrue(repMap.containsKey("2"));
        
        assertFalse(orgMap.containsKey("3"));
        assertTrue (newMap.containsKey("3"));
        assertFalse(repMap.containsKey("3"));
        
        assertTrue(orgMap.hasValue("Two"));
        assertTrue(orgMap.hasValue("Two"::equals));
        assertTrue(newMap.hasValue("Two"));
        assertTrue(newMap.hasValue("Two"::equals));
        assertTrue(repMap.hasValue("Du"));
        assertTrue(repMap.hasValue("Du"::equals));
        
        assertFalse(orgMap.hasValue("Three"));
        assertFalse(orgMap.hasValue("Three"::equals));
        assertTrue (newMap.hasValue("Three"));
        assertTrue (newMap.hasValue("Three"::equals));
        assertFalse(repMap.hasValue("Three"));
        assertFalse(repMap.hasValue("Three"::equals));
        
        assertEquals("Optional[Two]",   orgMap.findBy("2").toString());
        assertEquals("Optional.empty",  orgMap.findBy("3").toString());
        assertEquals("Optional[Two]",   newMap.findBy("2").toString());
        assertEquals("Optional[Three]", newMap.findBy("3").toString());
        assertEquals("Optional[Du]",    repMap.findBy("2").toString());
        assertEquals("Optional.empty",  repMap.findBy("3").toString());
        
        assertEquals("Two",   "" + orgMap.get("2"));
        assertEquals("null",  "" + orgMap.get("3"));
        assertEquals("Two",   "" + newMap.get("2"));
        assertEquals("Three", "" + newMap.get("3"));
        assertEquals("Du",    "" + repMap.get("2"));
        assertEquals("null",  "" + repMap.get("3"));
        
        assertEquals("Two",   "" + orgMap.getOrDefault("2", "N/A"));
        assertEquals("N/A",   "" + orgMap.getOrDefault("3", "N/A"));
        assertEquals("Two",   "" + newMap.getOrDefault("2", "N/A"));
        assertEquals("Three", "" + newMap.getOrDefault("3", "N/A"));
        assertEquals("Du",    "" + repMap.getOrDefault("2", "N/A"));
        assertEquals("N/A",   "" + repMap.getOrDefault("3", "N/A"));
        
        val OneTwo   = FuncList.of("1", "2");
        val TwoThree = FuncList.of("2", "3");
        
        assertEquals("[One, Two]",   "" + orgMap.select(OneTwo::contains));
        assertEquals("[Two]",        "" + orgMap.select(TwoThree::contains));
        assertEquals("[One, Two]",   "" + newMap.select(OneTwo::contains));
        assertEquals("[Two, Three]", "" + newMap.select(TwoThree::contains));
        assertEquals("[One, Du]",    "" + repMap.select(OneTwo::contains));
        assertEquals("[Du]",         "" + repMap.select(TwoThree::contains));
        
        assertEquals("[1=One, 2=Two]",   "" + orgMap.selectEntry(OneTwo::contains));
        assertEquals("[2=Two]",          "" + orgMap.selectEntry(TwoThree::contains));
        assertEquals("[1=One, 2=Two]",   "" + newMap.selectEntry(OneTwo::contains));
        assertEquals("[2=Two, 3=Three]", "" + newMap.selectEntry(TwoThree::contains));
        assertEquals("[1=One, 2=Du]",    "" + repMap.selectEntry(OneTwo::contains));
        assertEquals("[2=Du]",           "" + repMap.selectEntry(TwoThree::contains));
        
        assertEquals("[1, 2]",         "" + orgMap.keys());
        assertEquals("[1, 2]",         "" + orgMap.keySet());
        assertEquals("[One, Two]",     "" + orgMap.values());
        assertEquals("[1=One, 2=Two]", "" + orgMap.entrySet());
        assertEquals("[1=One, 2=Two]", "" + orgMap.entries());
        
        assertEquals("[1, 2, 3]",               "" + newMap.keys());
        assertEquals("[1, 2, 3]",               "" + newMap.keySet());
        assertEquals("[One, Two, Three]",       "" + newMap.values());
        assertEquals("[1=One, 2=Two, 3=Three]", "" + newMap.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey)).collect(toList()));
        assertEquals("[1=One, 2=Two, 3=Three]", "" + newMap.entries());
        
        assertEquals("[1, 2]",        "" + repMap.keys());
        assertEquals("[1, 2]",        "" + repMap.keySet());
        assertEquals("[One, Du]",     "" + repMap.values());
        assertEquals("[1=One, 2=Du]", "" + repMap.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey)).collect(toList()));
        assertEquals("[1=One, 2=Du]", "" + repMap.entries());
        
        assertEquals("{1:One, 2:Two}",          "" + orgMap.sorted());
        assertEquals("{1:One, 2:Two, 3:Three}", "" + newMap.sorted());
        assertEquals("{1:One, 2:Du}",           "" + repMap.sorted());
        assertEquals("{2:Two, 1:One}",          "" + orgMap.sortedByKey(reverseOrder()));
        assertEquals("{3:Three, 2:Two, 1:One}", "" + newMap.sortedByKey(reverseOrder()));
        assertEquals("{2:Du, 1:One}",           "" + repMap.sortedByKey(reverseOrder()));
    }
    
    @Test
    public void testFilterKey_Full() {
        val orgMap = FuncMap.of("1", "One", "2", "Two", "3", "Three");
        val newMap = orgMap.filter(k -> !"3".equals(k));
        
        assertEquals("{1:One, 2:Two, 3:Three}", orgMap.toString());
        assertEquals("{1:One, 2:Two}",          newMap.toString());
        
        assertEquals(FuncMap.of("1", "One", "2", "Two", "3", "Three").hashCode(), orgMap.hashCode());
        assertEquals(FuncMap.of("1", "One", "2", "Two"              ).hashCode(), newMap.hashCode());
        
        assertEquals(3, orgMap.size());
        assertEquals(2, newMap.size());
        
        assertTrue(orgMap.hasKey("2"));
        assertTrue(orgMap.hasKey("2"::equals));
        assertTrue(newMap.hasKey("2"));
        assertTrue(newMap.hasKey("2"::equals));
        
        assertTrue (orgMap.hasKey("3"));
        assertTrue (orgMap.hasKey("3"::equals));
        assertFalse(newMap.hasKey("3"));
        assertFalse(newMap.hasKey("3"::equals));
        
        assertTrue(orgMap.containsKey("2"));
        assertTrue(newMap.containsKey("2"));
        
        assertTrue (orgMap.containsKey("3"));
        assertFalse(newMap.containsKey("3"));
        
        assertTrue(orgMap.hasValue("Two"));
        assertTrue(orgMap.hasValue("Two"::equals));
        assertTrue(newMap.hasValue("Two"));
        assertTrue(newMap.hasValue("Two"::equals));
        
        assertTrue (orgMap.hasValue("Three"));
        assertTrue (orgMap.hasValue("Three"::equals));
        assertFalse(newMap.hasValue("Three"));
        assertFalse(newMap.hasValue("Three"::equals));
        
        assertEquals("Optional[Two]",   orgMap.findBy("2").toString());
        assertEquals("Optional[Three]", orgMap.findBy("3").toString());
        assertEquals("Optional[Two]",   newMap.findBy("2").toString());
        assertEquals("Optional.empty",  newMap.findBy("3").toString());
        
        assertEquals("Two",   "" + orgMap.get("2"));
        assertEquals("Three", "" + orgMap.get("3"));
        assertEquals("Two",   "" + newMap.get("2"));
        assertEquals("null",  "" + newMap.get("3"));
        
        assertEquals("Two",   "" + orgMap.getOrDefault("2", "N/A"));
        assertEquals("Three", "" + orgMap.getOrDefault("3", "N/A"));
        assertEquals("Two",   "" + newMap.getOrDefault("2", "N/A"));
        assertEquals("N/A",   "" + newMap.getOrDefault("3", "N/A"));
        
        val OneTwo   = FuncList.of("1", "2");
        val TwoThree = FuncList.of("2", "3");
        
        assertEquals("[One, Two]",   "" + orgMap.select(OneTwo::contains));
        assertEquals("[Two, Three]", "" + orgMap.select(TwoThree::contains));
        assertEquals("[One, Two]",   "" + newMap.select(OneTwo::contains));
        assertEquals("[Two]",        "" + newMap.select(TwoThree::contains));
        
        assertEquals("[1=One, 2=Two]",   "" + orgMap.selectEntry(OneTwo::contains));
        assertEquals("[2=Two, 3=Three]", "" + orgMap.selectEntry(TwoThree::contains));
        assertEquals("[1=One, 2=Two]",   "" + newMap.selectEntry(OneTwo::contains));
        assertEquals("[2=Two]",          "" + newMap.selectEntry(TwoThree::contains));
        
        assertEquals("[1, 2, 3]",               "" + orgMap.keys());
        assertEquals("[1, 2, 3]",               "" + orgMap.keySet());
        assertEquals("[One, Two, Three]",       "" + orgMap.values());
        assertEquals("[1=One, 2=Two, 3=Three]", "" + orgMap.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey)).collect(toList()));
        assertEquals("[1=One, 2=Two, 3=Three]", "" + orgMap.entries());
        
        assertEquals("{1:One, 2:Two, 3:Three}", "" + orgMap.sorted());
        assertEquals("{1:One, 2:Two}",          "" + newMap.sorted());
        assertEquals("{3:Three, 2:Two, 1:One}", "" + orgMap.sortedByKey(reverseOrder()));
        assertEquals("{2:Two, 1:One}",          "" + newMap.sortedByKey(reverseOrder()));
    }
    
    @Test
    public void testFilterBoth_Full() {
        val orgMap = FuncMap.of("1", "One", "2", "Two", "3", "Three");
        val revMap = orgMap.filter((k, v) -> !(Objects.equals(k, "2") && Objects.equals(v, "Two")));
        val newMap = orgMap.filter((k, v) -> !(Objects.equals(k, "2") && Objects.equals(v, "Du")));
        
        assertEquals("{1:One, 2:Two, 3:Three}", orgMap.toString());
        assertEquals("{1:One, 3:Three}",        revMap.toString());
        assertEquals("{1:One, 2:Two, 3:Three}", newMap.toString());
        
        assertEquals(FuncMap.of("1", "One", "2", "Two", "3", "Three").hashCode(), orgMap.hashCode());
        assertEquals(FuncMap.of("1", "One",             "3", "Three").hashCode(), revMap.hashCode());
        assertEquals(FuncMap.of("1", "One", "2", "Two", "3", "Three").hashCode(), newMap.hashCode());
        
        assertEquals(3, orgMap.size());
        assertEquals(2, revMap.size());
        assertEquals(3, newMap.size());
        
        assertTrue (orgMap.hasKey("2"));
        assertTrue (orgMap.hasKey("2"::equals));
        assertFalse(revMap.hasKey("2"));
        assertFalse(revMap.hasKey("2"::equals));
        assertTrue (newMap.hasKey("2"));
        assertTrue (newMap.hasKey("2"::equals));
        
        assertTrue(orgMap.hasKey("3"));
        assertTrue(orgMap.hasKey("3"::equals));
        assertTrue(revMap.hasKey("3"));
        assertTrue(revMap.hasKey("3"::equals));
        assertTrue(newMap.hasKey("3"));
        assertTrue(newMap.hasKey("3"::equals));
        
        assertTrue (orgMap.containsKey("2"));
        assertFalse(revMap.containsKey("2"));
        assertTrue (newMap.containsKey("2"));
        
        assertTrue(orgMap.containsKey("3"));
        assertTrue(revMap.containsKey("3"));
        assertTrue(newMap.containsKey("3"));
        
        assertTrue (orgMap.hasValue("Two"));
        assertTrue (orgMap.hasValue("Two"::equals));
        assertFalse(revMap.hasValue("Two"));
        assertFalse(revMap.hasValue("Two"::equals));
        assertFalse(newMap.hasValue("Du"));
        assertFalse(newMap.hasValue("Du"::equals));
        
        assertTrue(orgMap.hasValue("Three"));
        assertTrue(orgMap.hasValue("Three"::equals));
        assertTrue(revMap.hasValue("Three"));
        assertTrue(revMap.hasValue("Three"::equals));
        assertTrue(newMap.hasValue("Three"));
        assertTrue(newMap.hasValue("Three"::equals));
        
        assertEquals("Optional[Two]",   orgMap.findBy("2").toString());
        assertEquals("Optional[Three]", orgMap.findBy("3").toString());
        assertEquals("Optional.empty",  revMap.findBy("2").toString());
        assertEquals("Optional[Three]", revMap.findBy("3").toString());
        assertEquals("Optional[Two]",   newMap.findBy("2").toString());
        assertEquals("Optional[Three]", newMap.findBy("3").toString());
        
        assertEquals("Two",   "" + orgMap.get("2"));
        assertEquals("Three", "" + orgMap.get("3"));
        assertEquals("null",  "" + revMap.get("2"));
        assertEquals("Three", "" + revMap.get("3"));
        assertEquals("Two",   "" + newMap.get("2"));
        assertEquals("Three", "" + newMap.get("3"));
        
        assertEquals("Two",   "" + orgMap.getOrDefault("2", "N/A"));
        assertEquals("Three", "" + orgMap.getOrDefault("3", "N/A"));
        assertEquals("N/A",   "" + revMap.getOrDefault("2", "N/A"));
        assertEquals("Three", "" + revMap.getOrDefault("3", "N/A"));
        assertEquals("Two",   "" + newMap.getOrDefault("2", "N/A"));
        assertEquals("Three", "" + newMap.getOrDefault("3", "N/A"));
        
        val OneTwo   = FuncList.of("1", "2");
        val TwoThree = FuncList.of("2", "3");
        
        assertEquals("[One, Two]",   "" + orgMap.select(OneTwo::contains));
        assertEquals("[Two, Three]", "" + orgMap.select(TwoThree::contains));
        assertEquals("[One]",        "" + revMap.select(OneTwo::contains));
        assertEquals("[Three]",      "" + revMap.select(TwoThree::contains));
        assertEquals("[One, Two]",   "" + newMap.select(OneTwo::contains));
        assertEquals("[Two, Three]", "" + newMap.select(TwoThree::contains));
        
        assertEquals("[1=One, 2=Two]",   "" + orgMap.selectEntry(OneTwo::contains));
        assertEquals("[2=Two, 3=Three]", "" + orgMap.selectEntry(TwoThree::contains));
        assertEquals("[1=One]",          "" + revMap.selectEntry(OneTwo::contains));
        assertEquals("[3=Three]",        "" + revMap.selectEntry(TwoThree::contains));
        assertEquals("[1=One, 2=Two]",   "" + newMap.selectEntry(OneTwo::contains));
        assertEquals("[2=Two, 3=Three]", "" + newMap.selectEntry(TwoThree::contains));
        
        assertEquals("[1, 2, 3]",               "" + orgMap.keys());
        assertEquals("[1, 2, 3]",               "" + orgMap.keySet());
        assertEquals("[One, Two, Three]",       "" + orgMap.values());
        assertEquals("[1=One, 2=Two, 3=Three]", "" + orgMap.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey)).collect(toList()));
        assertEquals("[1=One, 2=Two, 3=Three]", "" + orgMap.entries());
        
        assertEquals("[1, 3]",           "" + revMap.keys());
        assertEquals("[1, 3]",           "" + revMap.keySet());
        assertEquals("[One, Three]",     "" + revMap.values());
        assertEquals("[1=One, 3=Three]", "" + revMap.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey)).collect(toList()));
        assertEquals("[1=One, 3=Three]", "" + revMap.entries());
        
        assertEquals("[1, 2, 3]",               "" + newMap.keys());
        assertEquals("[1, 2, 3]",               "" + newMap.keySet());
        assertEquals("[One, Two, Three]",       "" + newMap.values());
        assertEquals("[1=One, 2=Two, 3=Three]", "" + newMap.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey)).collect(toList()));
        assertEquals("[1=One, 2=Two, 3=Three]", "" + newMap.entries());
        
        assertEquals("{1:One, 2:Two, 3:Three}", "" + orgMap.sorted());
        assertEquals("{1:One, 3:Three}",        "" + revMap.sorted());
        assertEquals("{1:One, 2:Two, 3:Three}", "" + newMap.sorted());
        assertEquals("{3:Three, 2:Two, 1:One}", "" + orgMap.sortedByKey(reverseOrder()));
        assertEquals("{3:Three, 1:One}",        "" + revMap.sortedByKey(reverseOrder()));
        assertEquals("{3:Three, 2:Two, 1:One}", "" + newMap.sortedByKey(reverseOrder()));
    }
    
    @Test
    public void testMap_Full() {
        val orgMap = FuncMap.of("1", "One", "2", "Two", "3", "Three");
        val mapMap = orgMap.map((k,v) -> ("2".equals(k) ? "Du" : v));
        
        assertEquals("{1:One, 2:Two, 3:Three}", orgMap.toString());
        assertEquals("{1:One, 2:Du, 3:Three}",  mapMap.toString());
        
        assertEquals(FuncMap.of("1", "One", "2", "Two", "3", "Three").hashCode(), orgMap.hashCode());
        assertEquals(FuncMap.of("1", "One", "2", "Du",  "3", "Three").hashCode(), mapMap.hashCode());
        
        assertEquals(3, orgMap.size());
        assertEquals(3, mapMap.size());
        
        assertTrue(orgMap.hasKey("2"));
        assertTrue(orgMap.hasKey("2"::equals));
        assertTrue(mapMap.hasKey("2"));
        assertTrue(mapMap.hasKey("2"::equals));
        
        assertTrue(orgMap.containsKey("2"));
        assertTrue(mapMap.containsKey("2"));
        
        assertTrue (orgMap.hasValue("Two"));
        assertTrue (orgMap.hasValue("Two"::equals));
        assertFalse(mapMap.hasValue("Two"));
        assertFalse(mapMap.hasValue("Two"::equals));
        
        assertFalse(orgMap.hasValue("Du"));
        assertFalse(orgMap.hasValue("Du"::equals));
        assertTrue (mapMap.hasValue("Du"));
        assertTrue (mapMap.hasValue("Du"::equals));
        
        assertEquals("Optional[Two]", orgMap.findBy("2").toString());
        assertEquals("Optional[Du]",  mapMap.findBy("2").toString());
        
        assertEquals("Two", "" + orgMap.get("2"));
        assertEquals("Du",  "" + mapMap.get("2"));
        
        assertEquals("Two", "" + orgMap.getOrDefault("2", "N/A"));
        assertEquals("Du",  "" + mapMap.getOrDefault("2", "N/A"));
        
        val OneTwo   = FuncList.of("1", "2");
        val TwoThree = FuncList.of("2", "3");
        
        assertEquals("[One, Two]",   "" + orgMap.select(OneTwo::contains));
        assertEquals("[Two, Three]", "" + orgMap.select(TwoThree::contains));
        assertEquals("[One, Du]",    "" + mapMap.select(OneTwo::contains));
        assertEquals("[Du, Three]",  "" + mapMap.select(TwoThree::contains));
        
        assertEquals("[1=One, 2=Two]",   "" + orgMap.selectEntry(OneTwo::contains));
        assertEquals("[2=Two, 3=Three]", "" + orgMap.selectEntry(TwoThree::contains));
        assertEquals("[1=One, 2=Du]",    "" + mapMap.selectEntry(OneTwo::contains));
        assertEquals("[2=Du, 3=Three]",  "" + mapMap.selectEntry(TwoThree::contains));
        
        assertEquals("[1, 2, 3]",               "" + orgMap.keys());
        assertEquals("[1, 2, 3]",               "" + orgMap.keySet());
        assertEquals("[One, Two, Three]",       "" + orgMap.values());
        assertEquals("[1=One, 2=Two, 3=Three]", "" + orgMap.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey)).collect(toList()));
        assertEquals("[1=One, 2=Two, 3=Three]", "" + orgMap.entries());
        
        assertEquals("[1, 2, 3]",              "" + mapMap.keys());
        assertEquals("[1, 2, 3]",              "" + mapMap.keySet());
        assertEquals("[One, Du, Three]",       "" + mapMap.values());
        assertEquals("[1=One, 2=Du, 3=Three]", "" + mapMap.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey)).collect(toList()));
        assertEquals("[1=One, 2=Du, 3=Three]", "" + mapMap.entries());
        
        assertEquals("{1:One, 2:Two, 3:Three}", "" + orgMap.sorted());
        assertEquals("{1:One, 2:Du, 3:Three}",  "" + mapMap.sorted());
        assertEquals("{3:Three, 2:Two, 1:One}", "" + orgMap.sortedByKey(reverseOrder()));
        assertEquals("{3:Three, 2:Du, 1:One}",  "" + mapMap.sortedByKey(reverseOrder()));
    }
    
    @Test
    public void testMap_thenFilter() {
        val orgMap = FuncMap.of("1", "One", "2", "Two", "3", "Three");
        val newMap = orgMap
                        .map   ((k, v) -> ("Two".equals(v) ? "Du" : v))
                        .filter((k, v) -> Objects.equals(k, "2") && Objects.equals(v, "Du"));
        
        assertEquals("{1:One, 2:Two, 3:Three}", orgMap.toString());
        assertEquals("{2:Du}",                  newMap.toString());
        
        assertEquals(FuncMap.of("1", "One", "2", "Two", "3", "Three").hashCode(), orgMap.hashCode());
        assertEquals(FuncMap.of(            "2", "Du"               ).hashCode(), newMap.hashCode());
        
        assertEquals(3, orgMap.size());
        assertEquals(1, newMap.size());
        
        assertTrue(orgMap.hasKey("2"));
        assertTrue(orgMap.hasKey("2"::equals));
        assertTrue(newMap.hasKey("2"));
        assertTrue(newMap.hasKey("2"::equals));
        
        assertTrue(orgMap.containsKey("2"));
        assertTrue(newMap.containsKey("2"));
        
        assertTrue (orgMap.hasValue("Two"));
        assertTrue (orgMap.hasValue("Two"::equals));
        assertFalse(newMap.hasValue("Two"));
        assertFalse(newMap.hasValue("Two"::equals));
        
        assertFalse(orgMap.hasValue("Du"));
        assertFalse(orgMap.hasValue("Du"::equals));
        assertTrue (newMap.hasValue("Du"));
        assertTrue (newMap.hasValue("Du"::equals));
        
        assertEquals("Optional[Two]", orgMap.findBy("2").toString());
        assertEquals("Optional[Du]",  newMap.findBy("2").toString());
        
        assertEquals("Two", "" + orgMap.get("2"));
        assertEquals("Du",  "" + newMap.get("2"));
        
        assertEquals("Two", "" + orgMap.getOrDefault("2", "N/A"));
        assertEquals("Du",  "" + newMap.getOrDefault("2", "N/A"));
        
        val OneTwo   = FuncList.of("1", "2");
        val TwoThree = FuncList.of("2", "3");
        
        assertEquals("[One, Two]",   "" + orgMap.select(OneTwo::contains));
        assertEquals("[Two, Three]", "" + orgMap.select(TwoThree::contains));
        assertEquals("[Du]",         "" + newMap.select(OneTwo::contains));
        assertEquals("[Du]",         "" + newMap.select(TwoThree::contains));
        
        assertEquals("[1=One, 2=Two]",   "" + orgMap.selectEntry(OneTwo::contains));
        assertEquals("[2=Two, 3=Three]", "" + orgMap.selectEntry(TwoThree::contains));
        assertEquals("[2=Du]",           "" + newMap.selectEntry(OneTwo::contains));
        assertEquals("[2=Du]",           "" + newMap.selectEntry(TwoThree::contains));
        
        assertEquals("[1, 2, 3]",               "" + orgMap.keys());
        assertEquals("[1, 2, 3]",               "" + orgMap.keySet());
        assertEquals("[One, Two, Three]",       "" + orgMap.values());
        assertEquals("[1=One, 2=Two, 3=Three]", "" + orgMap.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey)).collect(toList()));
        assertEquals("[1=One, 2=Two, 3=Three]", "" + orgMap.entries());
        
        assertEquals("[2]",                    "" + newMap.keys());
        assertEquals("[2]",                    "" + newMap.keySet());
        assertEquals("[Du]",                   "" + newMap.values());
        assertEquals("[2=Du]", "" + newMap.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey)).collect(toList()));
        assertEquals("[2=Du]", "" + newMap.entries());
        
        assertEquals("{1:One, 2:Two, 3:Three}", "" + orgMap.sorted());
        assertEquals("{2:Du}",                  "" + newMap.sorted());
        assertEquals("{3:Three, 2:Two, 1:One}", "" + orgMap.sortedByKey(reverseOrder()));
        assertEquals("{2:Du}",                 "" + newMap.sortedByKey(reverseOrder()));
        
    }
}

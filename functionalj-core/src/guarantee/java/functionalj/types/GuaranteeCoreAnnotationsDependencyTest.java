// ============================================================================
// Copyright(c) 2017-2020 Nawapunth Manusitthipol (NawaMan - http://nawaman.net)
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
package functionalj.types;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

import functionalj.types.processor.Core;
import functionalj.lens.BooleanLens;
import functionalj.lens.IPostConstruct;
import functionalj.lens.IntegerLens;
import functionalj.lens.LensSpec;
import functionalj.lens.ObjectLensImpl;
import functionalj.lens.StringLens;


/**
 * This test helps to guarantee that the core class names that the annotation project uses still matches.
 * This come from the (unreasonable? :-/) desired to have only one jar included by the annotation processing client
 *   as it is sometime not easy to set that up in IDEs.
 * 
 * Read the JavaDoc of {@link Core} for more information.
 * 
 * @author NawaMan -- nawa@nawaman.net
 */
public class GuaranteeCoreAnnotationsDependencyTest {
    
    private Set<Core> checkedCores = new TreeSet<>();
    
    @SuppressWarnings("javadoc")
    @Test
    public void guarantee() {
        assertDependency(Core.IntegerLens,    IntegerLens.class);
        assertDependency(Core.BooleanLens,    BooleanLens.class);
        assertDependency(Core.StringLens,     StringLens.class);
        assertDependency(Core.ObjectLensImpl, ObjectLensImpl.class);
        assertDependency(Core.IPostConstruct, IPostConstruct.class);
        assertDependency(Core.LensSpec,       LensSpec.class);
        assertAllChecked();
    }
    
    private void assertDependency(Core core, Class<?> clzz) {
        assertEquals(core.type().fullName(), clzz.getCanonicalName());
        checkedCores.add(core);
    }
    
    private void assertAllChecked() {
        val allCores = new TreeSet<Core>(asList(Core.values()));
        assertEquals(allCores.toString(), checkedCores.toString());
    }
    
}

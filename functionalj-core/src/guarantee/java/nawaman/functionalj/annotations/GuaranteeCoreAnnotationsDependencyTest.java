package nawaman.functionalj.annotations;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

import lombok.val;
import nawaman.functionalj.annotations.processor.Core;
import nawaman.functionalj.lens.BooleanLens;
import nawaman.functionalj.lens.IPostConstruct;
import nawaman.functionalj.lens.IntegerLens;
import nawaman.functionalj.lens.LensSpec;
import nawaman.functionalj.lens.ObjectLensImpl;
import nawaman.functionalj.lens.StringLens;

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

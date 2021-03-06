package example.functionalj.pipeable;

import static functionalj.functions.StrFuncs.replaceAll;
import static org.junit.Assert.assertEquals;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

import functionalj.list.FuncList;
import functionalj.pipeable.PipeLine;
import functionalj.pipeable.Pipeable;
import lombok.val;

public class PipeableExamples {
    
    @Test
    public void testPipeable() {
        val str = Pipeable.of("Hello world.")
                .pipeTo(
                    String::toUpperCase,
                    replaceAll("\\.", "!!")
                );
        assertEquals("HELLO WORLD!!", str);
    }
    
    @Test
    public void testPipeLine() {
        val readFile = PipeLine
                .of  (String.class)
                .then(Paths ::get)
                .then(Files ::readAllBytes)
                .then(String::new)
                .thenReturn();
          
        val fileNames = FuncList.of("file1.txt", "file2.txt");
        val fileContents = fileNames.map(readFile);
        // Notice that the error is suppressed.
        assertEquals("[null, null]", fileContents.toString());
    }
    
    public static class User implements Pipeable<User> {
        private String name;
        public User(String name) { this.name = name; }
        public String name() { return name; }
        @Override
        public User __data() throws Exception { return this; }
    }
    
    @Test
    public void testPipeableClass() {
        val user = new User("root");
        assertEquals("User name: root", user.pipeTo(User::name, "User name: "::concat));
    }
    
}

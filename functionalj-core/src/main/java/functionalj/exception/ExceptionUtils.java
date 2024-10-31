package functionalj.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import lombok.val;

public class ExceptionUtils {
    
    public static String toString(Throwable throwable) {
        return toString("", throwable);
    }
    
    public static String toString(String prefix, Throwable throwable) {
        val buffer = new StringBuffer();
        buffer.append((prefix != null) ? prefix : "");
        
        try (val string = new StringWriter();
             val writer = new PrintWriter(string)) {
             throwable.printStackTrace(writer);
             buffer.append(throwable.toString());
             buffer.append("\n");
             buffer.append(string.getBuffer().toString());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return buffer.toString();
    }
    
}

package functionalj.types;

import java.util.Optional;

import javax.annotation.processing.ProcessingEnvironment;

public class VersionUtils {
    
    public static JavaVersionInfo getJavaVersionInfo(ProcessingEnvironment processingEnv) {
        // 1. Try to get custom options (e.g., -Asource=17 -Atarget=17)
        String sourceOption = processingEnv.getOptions().get("source");
        String targetOption = processingEnv.getOptions().get("target");
        
        // 2. Extract the source version from options or fall back to the ProcessingEnvironment
        int sourceVersion = parseVersion(Optional.ofNullable(sourceOption)
                .orElse(processingEnv.getSourceVersion().name().replace("RELEASE_", "")));
        
        // 3. Extract the target version from options or fall back to the system JDK version
        int targetVersion = parseVersion(
                Optional.ofNullable(targetOption).orElse(System.getProperty("java.specification.version")));
        
        return new JavaVersionInfo(sourceVersion, targetVersion);
    }
    
    private static int parseVersion(String version) {
        try {
            return Integer.parseInt(version);
        } catch (NumberFormatException e) {
            System.err.println("Failed to parse version: " + version);
            return -1;
        }
    }
    
}

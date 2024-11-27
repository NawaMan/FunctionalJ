package functionalj.types;

/**
 * Configuration about the toString() method.
 **/
public enum StructToString {
    
    /** Do not generate toString method. */
    None,
    
    /**
     * Let FunctionaJ choose. That is Custom if toStringTemplate given, Legacy for JDK older than 17 and Record for 17 and newer.
     */
    Default,
    
    /** Use Java record toString format Point[x=3. y=4] */
    Record,
    
    /** Use Legacy FunctionaJ Struct toString format Point[x: 3. y: 4] */
    Legacy,
    
    /** Use Custom template format */
    Template
    
}

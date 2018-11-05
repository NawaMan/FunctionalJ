package functionalj.functions;

import java.util.function.Supplier;
import java.util.regex.Pattern;

public class RegExMatchResult implements java.util.regex.MatchResult {
    private final Supplier<String> sourceString;
    private final Pattern          pattern;
    private final int              index;
    private final java.util.regex.MatchResult result;
    
    public RegExMatchResult(Supplier<String> sourceString, Pattern pattern, int index, java.util.regex.MatchResult result) {
        this.sourceString = sourceString;
        this.pattern = pattern;
        this.index = index;
        this.result = result;
    }
    
    public String  sourceString()               { return sourceString.get(); }
    public Pattern pattern()                    { return pattern; }
    public int     index()                      { return index;   }
    public java.util.regex.MatchResult result() { return result;  }
    
    @Override public int    start()          { return result.start();      }
    @Override public int    start(int group) { return result.start(group); }
    @Override public int    end()            { return result.end();        }
    @Override public int    end(int group)   { return result.end(group);   }
    @Override public String group()          { return result.group();      }
    @Override public String group(int group) { return result.group(group); }
    @Override public int    groupCount()     { return result.groupCount(); }
    @Override public String toString()       { return group();             }
}
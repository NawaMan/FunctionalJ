package functionalj.annotations.uniontype.generator.model;

import static java.util.stream.Collectors.joining;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

import lombok.Value;
import lombok.val;
import lombok.experimental.Accessors;

@Value
@Accessors(fluent=true)
public class Method {
    
    public static enum Kind {
        DEFAULT, STATIC
    }
    
    public final String signature;
    
    public final Kind   kind;
    public final String name;
    public final Type   returnType;
    public final List<MethodParam> params;
    
    public Method(Kind kind, String name, Type returnType, List<MethodParam> params) {
        this.kind       = kind;
        this.name       = name;
        this.returnType = returnType;
        this.params     = params;
        this.signature  = 
                (Kind.STATIC.equals(kind) ? "static " : "")
                + returnType.toString() + " " + toString(param -> param.type.toString());
    }
    
    public String definition() {
        return returnType.toString() + " " + toString(param -> param.type.toString() + " " + param.name);
    }
    public String definitionForThis() {
        val isFirst = new AtomicBoolean(true);
        return returnType.toString() + " " + toString(param -> {
            val isFirstCall = isFirst.get();
            isFirst.set(false);
            return isFirstCall ? null : param.type.toString() + " " + param.name;
        });
    }
    public String call() {
        return toString(param -> param.name);
    }
    public String callForThis() {
        val isFirst = new AtomicBoolean(true);
        return toString(param ->  {
            val isFirstCall = isFirst.get();
            isFirst.set(false);
            return isFirstCall ? "this" : param.name;
        });
    }
    
    public String toString(Function<? super MethodParam, ? extends String> paramMapper) {
        val paramsStr 
                = params.stream()
                .map    (paramMapper)
                .filter (Objects::nonNull)
                .collect(joining(", "));
        return name + "(" + paramsStr + ")";
    }
}

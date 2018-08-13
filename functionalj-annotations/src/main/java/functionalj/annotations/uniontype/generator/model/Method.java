package functionalj.annotations.uniontype.generator.model;

import static java.lang.String.format;
import static java.util.stream.Collectors.joining;

import java.util.ArrayList;
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
    public final List<Generic>     generics;
    public final List<Type>        exceptions;
    
    public Method(Kind kind, String name, Type returnType, List<MethodParam> params) {
        this(kind, name, returnType, params, new ArrayList<>(), new ArrayList<>());
    }
    public Method(Kind kind, String name, Type returnType, List<MethodParam> params, List<Generic> generics, List<Type> exceptions) {
        this.kind       = kind;
        this.name       = name;
        this.returnType = returnType;
        this.params     = params;
        this.generics   = generics;
        this.exceptions = exceptions;
        this.signature  = 
                (Kind.STATIC.equals(kind) ? "static " : "")
                + returnType.toString() + " " + toString(param -> param.type.toString());
    }
    
    public String definition() {
        return returnType.toString() + " " + 
                toString(param -> param.type.toString() + " " + param.name) +
                (exceptions.isEmpty() ? "" : " throws " + exceptions.stream().map(e -> e.toString()).collect(joining(",")));
    }
    public String definitionForThis() {
        val isFirst = new AtomicBoolean(true);
        return returnType.toString() + " " +
                toString(param -> {
                    val isFirstCall = isFirst.get();
                    isFirst.set(false);
                    return isFirstCall ? null : param.type.toString() + " " + param.name;
                }) +
                (exceptions.isEmpty() ? "" : " throws " + exceptions.stream().map(e -> e.toString()).collect(joining(",")));
    }
    public String call() {
        return toString(param -> param.name);
    }
    public String callForThis(Type type) {
        val isFirst      = new AtomicBoolean(true);
        val genericCount = type.generics.size();
        val firstStr     = "this";
        return toString(param ->  {
            val isFirstCall = isFirst.get();
            isFirst.set(false);
            val prefix = param.type.toString().equals(type.toString()) ? format("Self%1$s.of(", (genericCount != 0) ? "" + genericCount : "") : "";
            val suffix = param.type.toString().equals(type.toString()) ? ")" : "";
            return prefix + (isFirstCall ? firstStr : param.name) + suffix;
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

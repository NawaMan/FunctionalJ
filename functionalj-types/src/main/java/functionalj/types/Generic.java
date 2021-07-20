package functionalj.types;

import static functionalj.types.choice.generator.Utils.toListCode;
import static functionalj.types.choice.generator.Utils.toStringLiteral;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.joining;

import java.util.List;

import lombok.val;


public class Generic {
    
    public final String name;
    public final String withBound;
    public final List<? extends Type> boundTypes;
    
    public Generic(String name) {
        this(name, name, asList(new Type(name)));
    }
    public Generic(Type type) {
        this(type.fullName(), null, asList(type));
    }
    public Generic(String name, String withBound, List<? extends Type> boundTypes) {
        this.name = name;
        this.withBound = withBound;//(withBound == null) ? name : withBound;
        this.boundTypes = (boundTypes == null) ? emptyList() : boundTypes;
    }
    
    public String getName() {
        return name;
    }
    
    public String withBound() {
        return withBound;
    }
    
    public List<? extends Type> getBoundTypes() {
        return boundTypes;
    }
    
    public Type toType() {
        if ((boundTypes != null) && (boundTypes.size() == 1))
            return boundTypes.get(0);
        return Type.OBJECT;
    }
    
    public String toCode() {
        val params = asList(
                toStringLiteral(name),
                toStringLiteral(withBound),
                toListCode     (boundTypes, Type::toCode)
        );
        return "new " + this.getClass().getCanonicalName() + "("
                + params.stream().collect(joining(", "))
                + ")";
    }
    @Override
    public String toString() {
        return "Generic [name=" + name + ", withBound=" + withBound + ", boundTypes=" + boundTypes + "]";
    }
    
}

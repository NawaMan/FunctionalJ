package functionalj.types.input;

import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.WildcardType;

public interface InputTypeArgument {

    public static InputTypeArgument of(Environment environment, TypeMirror typeMirror) {
        return new Impl(environment, typeMirror);
    }

    public static class Impl implements InputTypeArgument {

        private final InputType inputType;

        private final boolean isExtends;

        private final boolean isSuper;

        public Impl(Environment environment, TypeMirror typeMirror) {
            this(InputType.of(environment, typeMirror), (typeMirror instanceof WildcardType) ? ((WildcardType) typeMirror).getExtendsBound() != null : false, (typeMirror instanceof WildcardType) ? ((WildcardType) typeMirror).getSuperBound() != null : false);
        }

        public Impl(InputType inputType) {
            this(inputType, false, false);
        }

        public Impl(InputType inputType, boolean isExtends) {
            this(inputType, isExtends, false);
        }

        public Impl(InputType inputType, boolean isExtends, boolean isSuper) {
            this.inputType = inputType;
            this.isSuper = isSuper;
            this.isExtends = isExtends;
        }

        @Override
        public InputType inputType() {
            return inputType;
        }

        @Override
        public boolean isSuper() {
            return isSuper;
        }

        @Override
        public boolean isExtends() {
            return isExtends;
        }
    }

    public InputType inputType();

    public boolean isSuper();

    public boolean isExtends();
}

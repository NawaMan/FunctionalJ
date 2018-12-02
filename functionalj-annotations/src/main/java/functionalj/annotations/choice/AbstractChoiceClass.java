package functionalj.annotations.choice;

import java.util.Objects;

public abstract class AbstractChoiceClass<S> {
    
    public abstract S __switch();
    
    
    public static class $utils {
        public static <D> D notNull(D value) {
            return Objects.requireNonNull(value);
        }
        
        public static <S> S Switch(AbstractChoiceClass<S> choiceType) {
            return ChoiceTypes.Switch(choiceType);
        }
        
        public static boolean checkEquals(byte a, byte b) {
            return a == b;
        }
        public static boolean checkEquals(short a, short b) {
            return a == b;
        }
        public static boolean checkEquals(int a, int b) {
            return a == b;
        }
        public static boolean checkEquals(long a, long b) {
            return a == b;
        }
        public static boolean checkEquals(float a, float b) {
            return a == b;
        }
        public static boolean checkEquals(double a, double b) {
            return a == b;
        }
        public static boolean checkEquals(boolean a, boolean b) {
            return a == b;
        }
        public static boolean checkEquals(Object a, Object b) {
            return ((a == null) && (b == null)) || Objects.equals(a, b);
        }
    }
    
}

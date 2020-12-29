package functionalj.list.intlist;

import java.util.Arrays;

public class Main {
    
    public static void main(String[] args) {
        System.out.println(Arrays.toString("a;b;c;d".split("((?<=;)|(?=;))")));
        System.out.println(Arrays.toString("a;b;;c;d".split("((?<=;)|(?=;))")));
    }
    
}

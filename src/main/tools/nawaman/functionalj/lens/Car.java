package nawaman.functionalj.lens;

import nawaman.functionalj.lens.LensGenerator.ICar;
import java.lang.String;

public class Car implements ICar {

    private final String color;
    private final String make;
    private final String model;

    public Car() {
        this(null, null, null);
    }
    public Car(String color, String make, String model) {
        this.color = color;
        this.make = make;
        this.model = model;
    }

    public String color() {
        return color;
    }
    public String make() {
        return make;
    }
    public String model() {
        return model;
    }
    public Car withColor(String color) {
        return postProcess(new Car(color, make, model));
    }
    public Car withMake(String make) {
        return postProcess(new Car(color, make, model));
    }
    public Car withModel(String model) {
        return postProcess(new Car(color, make, model));
    }
    private Car postProcess(Car object) {
        if (object instanceof nawaman.functionalj.lens.IPostConstruct)
            ((nawaman.functionalj.lens.IPostConstruct)object).postConstruct();
        return object;
    }

    public static class CarLens<HOST> extends ObjectLensImpl<HOST, Car> {
    
        public final StringLens<HOST> color = createSubLens(Car::color, Car::withColor, spec->()->spec);
        public final StringLens<HOST> make = createSubLens(Car::make, Car::withMake, spec->()->spec);
        public final StringLens<HOST> model = createSubLens(Car::model, Car::withModel, spec->()->spec);
    
        public CarLens(LensSpec<HOST, Car> spec) {
            super(spec);
        }
    
    }

}

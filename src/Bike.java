package src;

public class Bike extends Vehicle{

    public Bike(String model, String colour, String registrationNumber ) {
        super(model, colour, registrationNumber, VehicleType.BIKE);
    }
}

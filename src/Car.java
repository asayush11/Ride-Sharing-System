package src;

public class Car extends Vehicle {
    private final int numberOfSeats ;

    public Car(String model, String colour, String registrationNumber, int numberOfSeats ) {
        super(model, colour, registrationNumber, VehicleType.CAR);
        this.numberOfSeats = numberOfSeats;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }
}

package src;

public abstract class Vehicle {
    private final String model;
    private final String color;
    private final String registrationNumber;
    private final VehicleType vehicleType;
    public Vehicle(String model, String color, String registrationNumber, VehicleType vehicleType) {
        this.model = model;
        this.color = color;
        this.registrationNumber = registrationNumber;
        this.vehicleType = vehicleType;
    }

    public String getModel() {
        return model;
    }

    public String getColor() {
        return color;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }


}

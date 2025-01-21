package src;

public class Driver {
    private final String name;
    private final String phoneNumber;
    private final String id;
    private Location location;
    private final Vehicle vehicle; // assuming he has one vehicle only
    private boolean isAvailable;

    public Driver(String name, String phoneNumber, String id, Vehicle vehicle) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.id = id;
        this.vehicle = vehicle;
        this.isAvailable = true;
    }
    public void updateLocation(Location location) {
        this.location = location;
        System.out.println("Driver location updated to: " + location.getLatitude() + ", " + location.getLongitude());
    }
    public void setAvailability(boolean availability) {
        isAvailable = availability;
        System.out.println("Driver availability updated to: " + availability);
    }

    public void cancelRide(RideSharingSystem rideSharingSystem) {
        // cancel the ride
        rideSharingSystem.cancelRideByDriver(this.id);
    }

    public double completeRide(RideSharingSystem rideSharingSystem) {
        // complete the ride
        System.out.println("Driver: " + this.name + " reached the destination.");
        return rideSharingSystem.completeRide(this.id);
    }
    public void startRide(RideSharingSystem rideSharingSystem) {
        // start the ride
        System.out.println("Driver: " + this.name + " reached the customer location.");
        rideSharingSystem.startRide(this.id);
    }

    public String getId() {
        return id;
    }

    public Location getLocation() {
        return location;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public String getName() {
        return name;
    }
}

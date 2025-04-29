package src;

public class Driver {
    private final String name;
    private final String phoneNumber;
    private final String license;
    private final String id;
    private Location location;
    private final Vehicle vehicle; // assuming he has one vehicle only
    private boolean isAvailable;

    public Driver(String name, String phoneNumber, String id, Vehicle vehicle, String license) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.id = id;
        this.vehicle = vehicle;
        this.isAvailable = true;
        this.license = license;
    }
    public void updateLocation(Location location) {
        this.location = location;
        System.out.println("Driver location updated to: " + location.latitude() + ", " + location.longitude());
    }
    public void setAvailability(boolean availability) {
        isAvailable = availability;
        System.out.println("Driver availability updated to: " + availability);
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getLicense() {
        return license;
    }
}

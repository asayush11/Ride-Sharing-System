package src;

public class Customer {
    private final String name;
    private final String phoneNumber;
    private final String id;
    private Location location;

    public Customer(String name, String phoneNumber, String id) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.id = id;
    }

    public void updateLocation(Location location) {
        this.location = location;
        System.out.println("Customer location updated to: " + location.latitude() + ", " + location.longitude());
    }

    public String getId() {
        return id;
    }

    public Location getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}

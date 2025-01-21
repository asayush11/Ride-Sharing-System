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
        System.out.println("Customer location updated to: " + location.getLatitude() + ", " + location.getLongitude());
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

    public Ride requestRide(Location destination, RideSharingSystem rideSharingSystem) {
        // request a ride
        return rideSharingSystem.createRide(this.id, this.location, destination);
    }

    public void cancelRide(RideSharingSystem rideSharingSystem) {
        // cancel the ride
        rideSharingSystem.cancelRide(this);
    }

    public void payForRide(Payment payment, double fare) {
        // pay for the ride
        payment.makePayment(fare);
    }


}

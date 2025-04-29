package src;

public class Ride {
    private final String id;
    private final Location startLocation;
    private final Location endLocation;
    private final Customer customer;
    private final Driver driver;
    private final double fare;
    private RideStatus status;

    public Ride(String id, Location startLocation, Location endLocation, Customer customer, double fare, Driver driver) {
        this.id = id;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.customer = customer;
        this.fare = fare;
        this.status = RideStatus.REQUESTED;
        this.driver = driver;
    }

    public void updateRideStatus(RideStatus status) {
        this.status = status;
        System.out.println("Ride status updated to: " + status);
    }

    public String getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Driver getDriver() {
        return driver;
    }

    public RideStatus getStatus() {
        return status;
    }

    public double getFare() {
        return fare;
    }

    public Location getStartLocation() {
        return startLocation;
    }

    public Location getEndLocation() {
        return endLocation;
    }
}

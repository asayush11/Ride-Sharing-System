package src;

import com.google.common.util.concurrent.AtomicDouble;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RideSharingSystem {
    private final Map<String, Customer> customers;
    private final Map<String, Driver> drivers;
    private final Map<String, Ride> rides;
    private static RideSharingSystem instance;

    private RideSharingSystem() {
        customers = new HashMap<>();
        drivers = new HashMap<>();
        rides = new HashMap<>();
    }

    public synchronized static RideSharingSystem getInstance() {
        if (instance == null) {
            synchronized (RideSharingSystem.class) {
                if (instance == null) {
                    instance = new RideSharingSystem();
                }
            }
        }
        return instance;
    }

    public Driver addDriver(String name, String phoneNumber, Vehicle vehicle, String license) {
        String driverId = generateDriverId();
        Driver driver = new Driver(name, phoneNumber, driverId, vehicle, license);
        drivers.put(driverId, driver);
        System.out.println("Driver added with ID: " + driverId + ", Name: " + name);
        return driver;
    }

    public void updateDriverLocation(String driverId, Location location) {
        Driver driver = drivers.get(driverId);
        if (driver == null) {
            System.out.println("Driver not found.");
            return;
        }
        driver.updateLocation(location);
    }

    public void displayVehiclesAvailable() {
        System.out.println("Vehicles available:");
        drivers.values().forEach(driver -> {
            if (driver.isAvailable()) {
                System.out.println("Driver: " + driver.getName() + ", Vehicle: " + driver.getVehicle().getModel() + ", " + driver.getVehicle().getVehicleType() + ", " + driver.getVehicle().getRegistrationNumber());
            }
        });
    }

    public Customer addCustomer(String name, String phoneNumber) {
        String customerId = generateCustomerId();
        Customer customer = new Customer(name, phoneNumber, customerId);
        customers.put(customerId, customer);
        System.out.println("Customer added with ID: " + customerId + ", Name: " + name);
        return customer;
    }

    public void updateCustomerLocation(String customerId, Location location) {
        Customer customer = customers.get(customerId);
        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }
        customer.updateLocation(location);
    }

    private String generateDriverId() {
        return "D" + UUID.randomUUID().toString().replace("-", "");
    }

    private String generateCustomerId() {
        return "C" + UUID.randomUUID().toString().replace("-", "");
    }

    public Ride createRide(Customer customer, Location from, Location to) {
        if (customer == null) {
            System.out.println("Customer not found.");
            return null;
        }
        System.out.println("Ride requested by: " + customer.getName());
        double fare = calculateFare(from, to);
        String rideId = generateRideId();
        Driver driver = assignDriver(to);
        if(driver == null) return null; // no driver available
        Ride ride = new Ride(rideId, from, to, customer, fare, driver);
        rides.put(rideId, ride);
        System.out.println("Ride created with ID: " + rideId + ", Fare: " + fare + ", Driver: " + driver.getName());
        return ride;
    }

    private double calculateFare(Location startLocation, Location endLocation) {
        double distance = Math.abs(startLocation.latitude() - endLocation.latitude()) + Math.abs(startLocation.longitude() - endLocation.longitude());
        return distance * 10; // $10 per mile
    }

    private String generateRideId() {
        return "R" + UUID.randomUUID().toString().replace("-", "");
    }

    public void cancelRideByCustomer(Customer customer) {
        // cancel the ride
        String customerId = customer.getId();
        rides.values().stream()
                .filter(ride -> ride.getCustomer().getId().equals(customerId) && ride.getStatus() == RideStatus.REQUESTED)
                .forEach(ride -> {
                    ride.updateRideStatus(RideStatus.CANCELLED);
                    ride.getDriver().setAvailability(true);
                    System.out.println("Ride cancelled by: " + customer.getName());
                });
    }

    public void startRide(Driver driver) {
        if (driver == null) {
            System.out.println("Driver not found.");
            return;
        }
        rides.values().stream()
                .filter(ride -> ride.getDriver().getId().equals(driver.getId()) && ride.getStatus() == RideStatus.REQUESTED)
                .forEach(ride -> {
                    ride.updateRideStatus(RideStatus.ONGOING);
                    driver.updateLocation(ride.getCustomer().getLocation());
                    System.out.println("Ride started by: " + driver.getName());
                });
    }

    public double completeRide(Driver driver) {
        if (driver == null) {
            System.out.println("Driver not found.");
            return 0.0;
        }
        AtomicDouble fare = new AtomicDouble(0.0);
        rides.values().stream()
                .filter(ride -> ride.getDriver().getId().equals(driver.getId()) && ride.getStatus() == RideStatus.ONGOING)
                .forEach(ride -> {
                    ride.updateRideStatus(RideStatus.COMPLETED);
                    driver.setAvailability(true);
                    driver.updateLocation(ride.getEndLocation());
                    ride.getCustomer().updateLocation(ride.getEndLocation());
                    fare.set(ride.getFare());
                    System.out.println("Ride completed with fare: " + ride.getFare());
                });
        return fare.get();
    }

    private Driver assignDriver(Location location) {
        return drivers.values().stream()
                .filter(Driver::isAvailable).min((d1, d2) -> {
                    long distance1 = getDistance(d1.getLocation(), location);
                    long distance2 = getDistance(d2.getLocation(), location);
                    return Long.compare(distance1, distance2);
                })
                .orElse(null);
    }

    public void cancelRideByDriver(Driver driver) {
        rides.values().stream()
                .filter(ride -> ride.getDriver().getId().equals(driver.getId()) && ride.getStatus() == RideStatus.REQUESTED)
                .forEach(ride -> {
                    ride.updateRideStatus(RideStatus.CANCELLED);
                    driver.setAvailability(true);
                    System.out.println("Ride cancelled by driver: " + driver.getName());
                });
    }

    public void makePayment(double amount, Customer customer, Payment paymentMethod) {
        paymentMethod.makePayment(amount);
        System.out.println("Payment of $" + amount + " made by customer: " + customer.getName());
    }
    private long getDistance(Location start, Location end) {
        return (long) Math.sqrt(Math.pow(start.latitude() - end.latitude(), 2) + Math.pow(start.longitude() - end.longitude(), 2));
    }
}

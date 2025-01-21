package src;

import java.util.HashMap;
import java.util.Map;

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
            instance = new RideSharingSystem();
        }
        return instance;
    }
    public Driver addDriver(String name, String phoneNumber, Vehicle vehicle) {
        String driverId = generateDriverId();
        Driver driver = new Driver(name, phoneNumber, driverId, vehicle);
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
        for (Driver driver : drivers.values()) {
            if (driver.isAvailable()) {
                System.out.println("Driver: " + driver.getName() + ", Vehicle: " + driver.getVehicle().getModel() + ", " + driver.getVehicle().getVehicleType() + ", " + driver.getVehicle().getRegistrationNumber());
            }
        }
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
        return "D" + drivers.size();
    }

    private String generateCustomerId() {
        return "C" + customers.size();
    }

    public Ride createRide(String customerId, Location from, Location to) {
        Customer customer = customers.get(customerId);
        if (customer == null) {
            System.out.println("Customer not found.");
            return null;
        }
        double fare = calculateFare(from, to);
        String rideId = generateRideId();
        Ride ride = new Ride(rideId, from, to, customer, fare);
        rides.put(rideId, ride);
        System.out.println("Ride created with ID: " + rideId);
        if(assignDriver(rideId)) return ride; // assigning a driver for now
        return null;
    }

    private double calculateFare(Location startLocation, Location endLocation) {
        double distance = Math.abs(startLocation.getLatitude() - endLocation.getLatitude()) + Math.abs(startLocation.getLongitude() - endLocation.getLongitude());
        return distance * 10; // $10 per mile
    }

    private String generateRideId() {
        return "R" + rides.size();
    }

    public void cancelRide(Customer customer) {
        // cancel the ride
        String customerId = customer.getId();
        for (Map.Entry<String, Ride> entry : rides.entrySet()) {
            Ride ride = entry.getValue();
            if (ride.getCustomer().getId().equals(customerId) && ride.getStatus() != RideStatus.REQUESTED && ride.getStatus() != RideStatus.COMPLETED) {
                ride.updateRideStatus(RideStatus.CANCELLED);
                if (ride.getDriver() == null) {
                    System.out.println("Ride cancelled by: " + customer.getName());
                    return;
                }
                String driverId = ride.getDriver().getId();
                Driver driver = drivers.get(driverId);
                driver.setAvailability(true);
                System.out.println("Ride cancelled by: " + customer.getName());
                return;
            }
        }
        System.out.println("Ride not found.");
    }

    public void startRide(String driverId) {
        Driver driver = drivers.get(driverId);
        if (driver == null) {
            System.out.println("Driver not found.");
            return;
        }
        for(Ride ride : rides.values()) {
            if (ride.getDriver().getId().equals(driverId) && ride.getStatus() == RideStatus.SCHEDULED) {
                ride.updateRideStatus(RideStatus.ONGOING);
                System.out.println("Ride started. Driver: " + driver.getName());
                return;
            }
        }
    }

    public double completeRide(String driverId) {
        Driver driver = drivers.get(driverId);
        if (driver == null) {
            System.out.println("Driver not found.");
            return 0.0;
        }
        for(Ride ride : rides.values()) {
            if (ride.getDriver().getId().equals(driverId) && ride.getStatus() == RideStatus.ONGOING) {
                ride.updateRideStatus(RideStatus.COMPLETED);
                driver.setAvailability(true);
                System.out.println("Ride completed. Driver: " + driver.getName());
                System.out.println("Fare: " + ride.getFare());
                return ride.getFare();
            }
        }
        return 0.0;
    }

    private boolean assignDriver(String rideId) {
        for (Driver driver : drivers.values()) {
            if (driver.isAvailable()) {
                Ride ride = rides.get(rideId);
                ride.assignDriver(driver);
                driver.setAvailability(false);
                ride.updateRideStatus(RideStatus.SCHEDULED);
                return true;
            }
        }
        System.out.println("No driver available for this ride.");
        rides.get(rideId).updateRideStatus(RideStatus.CANCELLED);
        return false;
    }

    public void cancelRideByDriver(String driverId) {
        Driver driver = drivers.get(driverId);
        if (driver == null) {
            System.out.println("Driver not found.");
            return;
        }
        for(Ride ride : rides.values()) {
            if (ride.getDriver().getId().equals(driverId) && ride.getStatus() == RideStatus.SCHEDULED) {
                ride.updateRideStatus(RideStatus.CANCELLED);
                driver.setAvailability(true);
                System.out.println("Ride cancelled by driver: " + driver.getName());
                return;
            }
        }
    }
}

package src;

public class Demo {
    public static void main(String[] args) {
        RideSharingSystem rideSharingSystem = RideSharingSystem.getInstance();

        // create some drivers
        Driver driver1 = rideSharingSystem.addDriver("John", "1234567890", new Car("Toyota", "Red", "ABC123", 4), "DL123456");
        Driver driver2 = rideSharingSystem.addDriver("Sam", "1234567891", new Car("Hyundai", "White", "ABC124", 4), "DL123457");
        Driver driver3 = rideSharingSystem.addDriver("Anna", "1234567892", new Bike("Honda", "Black", "ABC125"), "DL123458");

        // create some customers
        Customer customer1 = rideSharingSystem.addCustomer("Alice", "1234567893");
        Customer customer2 = rideSharingSystem.addCustomer("Bob", "1234567894");
        Customer customer3 = rideSharingSystem.addCustomer("Charlie", "1234567895");
        Customer customer4 = rideSharingSystem.addCustomer("David", "1234567896");

        // update driver locations
        rideSharingSystem.updateDriverLocation(driver1.getId(), new Location(12.9715987, 77.5945627));
        rideSharingSystem.updateDriverLocation(driver2.getId(), new Location(15.9715987, 80.5945627));
        rideSharingSystem.updateDriverLocation(driver3.getId(), new Location(13.9715987, 75.5945627));

        // display available vehicles
        rideSharingSystem.displayVehiclesAvailable();

        // request a ride
        rideSharingSystem.updateCustomerLocation(customer1.getId(), new Location(13.9715987, 75.5945627));
        Ride ride1 = rideSharingSystem.createRide(customer1, customer1.getLocation(), (new Location(12.9715987, 79.5945627)));

        // driver reaches the customer location
        rideSharingSystem.startRide(ride1.getDriver());

        // display available vehicles
        rideSharingSystem.displayVehiclesAvailable();

        // driver reaches the destination
        double fare = rideSharingSystem.completeRide(ride1.getDriver());
        rideSharingSystem.makePayment(fare, ride1.getCustomer(), new CardPayment());

        // display available vehicles
        rideSharingSystem.displayVehiclesAvailable();

        // request a ride
        rideSharingSystem.updateCustomerLocation(customer2.getId(), new Location(13.9715987, 75.5945627));
        Ride ride2 = rideSharingSystem.createRide(customer2, customer2.getLocation(), new Location(12.9715987, 79.5945627));

        rideSharingSystem.updateCustomerLocation(customer3.getId(), new Location(13.9715987, 75.5945627));
        Ride ride3 = rideSharingSystem.createRide(customer3, customer3.getLocation(), new Location(12.9715988, 79.5945628));

        rideSharingSystem.updateCustomerLocation(customer4.getId(), new Location(13.9715987, 75.5945627));
        Ride ride4 = rideSharingSystem.createRide(customer4, customer4.getLocation(), new Location(12.9715989, 79.5945629));

        // driver reaches the customer locationr
        rideSharingSystem.cancelRideByDriver(ride2.getDriver());
        rideSharingSystem.cancelRideByCustomer(ride3.getCustomer());

        // display available vehicles
        rideSharingSystem.displayVehiclesAvailable();
    }
}

package src;

public class main {
    public static void main(String[] args) {
        RideSharingSystem rideSharingSystem = RideSharingSystem.getInstance();

        // create some drivers
        Driver driver1 = rideSharingSystem.addDriver("John", "1234567890", new Car("Toyota", "Red", "ABC123", 4));
        Driver driver2 = rideSharingSystem.addDriver("Sam", "1234567891", new Car("Hyundai", "White", "ABC124", 4));
        Driver driver3 = rideSharingSystem.addDriver("Anna", "1234567892", new Bike("Honda", "Black", "ABC125"));

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
        Ride ride1 = customer1.requestRide(new Location(12.9715987, 79.5945627), rideSharingSystem);

        // driver reaches the customer location
        ride1.getDriver().startRide(rideSharingSystem);

        // display available vehicles
        rideSharingSystem.displayVehiclesAvailable();

        // driver reaches the destination
        double fare = ride1.getDriver().completeRide(rideSharingSystem);
        customer1.payForRide(new UPIPayment(), fare);

        // display available vehicles
        rideSharingSystem.displayVehiclesAvailable();

        // request a ride
        rideSharingSystem.updateCustomerLocation(customer2.getId(), new Location(13.9715987, 75.5945627));
        Ride ride2 = customer2.requestRide(new Location(12.9715987, 79.5945627), rideSharingSystem);

        rideSharingSystem.updateCustomerLocation(customer3.getId(), new Location(13.9715987, 75.5945627));
        Ride ride3 = customer3.requestRide(new Location(12.9715988, 79.5945628), rideSharingSystem);

        rideSharingSystem.updateCustomerLocation(customer4.getId(), new Location(13.9715987, 75.5945627));
        Ride ride4 = customer4.requestRide(new Location(12.9715989, 79.5945629), rideSharingSystem);

        // driver reaches the customer location
        ride2.getDriver().startRide(rideSharingSystem);
        ride2.getCustomer().cancelRide(rideSharingSystem);
        ride3.getDriver().cancelRide(rideSharingSystem);

        // display available vehicles
        rideSharingSystem.displayVehiclesAvailable();
    }
}

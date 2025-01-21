package src;

public class UPIPayment implements Payment {
    public void makePayment(double amount) {
        System.out.println("Payment of " + amount + " made using UPI");
    }
}

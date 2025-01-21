package src;

public class CardPayment implements Payment {
    @Override
    public void makePayment(double amount) {
        System.out.println("Payment of " + amount + " made using card");
    }
}

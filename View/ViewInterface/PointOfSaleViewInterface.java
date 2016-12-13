package View.ViewInterface;

/**
 * Created by Dino Cajic on 10/29/2016.
 *
 * To be implemented in PointOfSaleView
 */
public interface PointOfSaleViewInterface {

    /**
     * Displays the initial POS Screen where the user searches for a book
     */
    void displayInitialScreen();

    /**
     * Displays the payment form once the user clicks next after finding the books in the initial screen
     */
    void displayPaymentForm();

    /**
     * Displays the receipt after the user enters the payment details
     */
    void displayReceipt(String ccNumber, String ccName, String ccCode);

    /**
     * Displays the customer order if the customer is looking for a refund
     */
    void displaySearchForCustomerOrder();

    /**
     * Once the refund has been issued, the refund receipt is displayed
     */
    void displayRefundReceipt();
}

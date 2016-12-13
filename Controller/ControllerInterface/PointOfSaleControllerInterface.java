package Controller.ControllerInterface;

import java.util.ArrayList;

/**
 * Created by Dino Cajic on 10/29/2016.
 *
 * To be implemented in PointOfSaleController
 */
public interface PointOfSaleControllerInterface {

    /**
     * Displays the initial POS screen where the user can add items to the order.
     */
    void displayInitialPOSscreen();

    /**
     * Displays the screen that allows the user to search for a customer's order
     */
    void displaySearchForCustomerOrder();

    /**
     * Instantiates the PointOfSalesView and calls the display refund receipt
     */
    void displayRefundReceipt();

    /**
     * Returns the book. Searched by ISBN.
     * @param isbn The isbn of the book
     * @return String[] book
     */
    Object[][] getBook(String isbn);

    /**
     * Checks to see if the Credit Card is good
     * @param ccNumber The credit card number
     * @param ccName The name on the credit card
     * @param ccCode The Security code on the back of credit card
     * @return boolean true if ok, false otherwise
     */
    boolean checkCard(String ccNumber, String ccName, String ccCode);

    /**
     * Adds the book to the database
     * @param addedItems ISBNs of books
     * @param ccNumber The CC Number. Only save last 4
     * @param ccName The CC Name.
     * @param isCreditCardPayment True if person payed with credit card
     * @param orderNumber The order number of the order
     * @return String Success
     */
    String insertSale(ArrayList<String> addedItems,
                      String ccNumber,
                      String ccName,
                      boolean isCreditCardPayment,
                      String orderNumber);

    /**
     * Checks to see if order exists by that order number
     * @param orderNumber The order number of the order
     * @return Returns the order number
     */
    String getOrder(String orderNumber);

    /**
     * Issues the refund
     * @param orderNumber The order number of the order
     * @return String Success if successful
     */
    String issueRefund(String orderNumber);

    /**
     * Gets a list of books from the database based on the isbns
     * @param isbns The isbns of multiple books
     * @return Object[][]
     */
    Object[][] getBooks(ArrayList<String> isbns);
}
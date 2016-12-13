package Controller;

import Controller.ControllerInterface.PointOfSaleControllerInterface;
import GUIobjects.CMPanel;
import Model.PointOfSaleModel;
import View.PointOfSaleView;

import java.util.ArrayList;

/**
 * Created by Dino Cajic on 10/29/2016.
 *
 * Directs the communication between the PointOfSaleModel and PointOfSaleView
 */
public class PointOfSaleController implements PointOfSaleControllerInterface {

    CMPanel display;

    public PointOfSaleController() {}

    /**
     * @param display The display object that was instantiated in the ApplicationWindow class
     */
    public PointOfSaleController(CMPanel display) {
        this.display = display;
    }

    /**
     * Displays the initial POS screen where the user can add items to the order.
     */
    public void displayInitialPOSscreen() {
        PointOfSaleView view = new PointOfSaleView(this.display);
        view.displayInitialScreen();
    }

    /**
     * Displays the screen that allows the user to search for a customer's order
     */
    public void displaySearchForCustomerOrder() {
        PointOfSaleView view = new PointOfSaleView(this.display);
        view.displaySearchForCustomerOrder();
    }

    /**
     * Displays the refund receipt
     */
    public void displayRefundReceipt() {
        PointOfSaleView view = new PointOfSaleView(this.display);
        view.displayRefundReceipt();
    }

    /**
     * Returns the book. Searched by ISBN.
     * @param isbn The isbn of the book
     * @return String[] book
     */
    public Object[][] getBook(String isbn) {
        PointOfSaleModel model = new PointOfSaleModel();
        return model.getBook(isbn);
    }

    /**
     * Checks to see if the Credit Card is good
     * @param ccNumber The credit card number
     * @param ccName The name on the credit card
     * @param ccCode The Security code on the back of credit card
     * @return boolean true if ok, false otherwise
     */
    public boolean checkCard(String ccNumber, String ccName, String ccCode) {
        PointOfSaleModel model = new PointOfSaleModel();
        return model.checkCard(ccNumber, ccName, ccCode);
    }

    /**
     * Adds the book to the database
     * @param addedItems ISBNs of books
     * @param ccNumber The CC Number. Only save last 4
     * @param ccName The CC Name.
     * @param isCreditCardPayment True if person payed with credit card
     * @param orderNumber The order number of the order
     * @return String Success
     */
    public String insertSale(ArrayList<String> addedItems, String ccNumber, String ccName, boolean isCreditCardPayment, String orderNumber) {
        PointOfSaleModel model = new PointOfSaleModel();
        return model.insertSale(addedItems, ccNumber, ccName, isCreditCardPayment, orderNumber);
    }

    /**
     * Checks to see if order exists by that order number
     * @param orderNumber The order number of the order
     * @return Returns the order number
     */
    public String getOrder(String orderNumber) {
        PointOfSaleModel model = new PointOfSaleModel();
        return model.getOrder(orderNumber);
    }

    /**
     * Issues the refund
     * @param orderNumber The order number of the order
     * @return String Success if successful
     */
    public String issueRefund(String orderNumber) {
        PointOfSaleModel model = new PointOfSaleModel();
        return model.issueRefund(orderNumber);
    }

    /**
     * Gets a list of books from the database based on the isbns
     * @param isbns The isbns of multiple books
     * @return Object[][]
     */
    public Object[][] getBooks(ArrayList<String> isbns) {
        PointOfSaleModel model = new PointOfSaleModel();
        return model.getBooks(isbns);
    }
}
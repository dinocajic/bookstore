package Controller.ControllerInterface;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by Dino Cajic on 10/29/2016.
 *
 * To be implemented in the AddOrderToInventoryController
 */
public interface AddOrderToInventoryControllerInterface {

    /**
     * Once the Add Order to Inventory button is clicked in the menu, the method gets the
     * order details from the AddOrderToInventoryModel and calls the AddOrderToInventoryView
     * to display the form: the form for searching an order by order id.
     * @param order The order id that's being searched
     */
    void displayInitialScreen(String order);

    /**
     * Marks the order as received in the database. Index of orderFields matches index of orderQty.
     * @param orderFields The ISBNs of each book
     * @param orderQty The qty's of each book
     * @param orderNumber The order number of the order
     * @return String success message
     */
    String receiveOrder(ArrayList<JTextField> orderFields, ArrayList<JSpinner> orderQty, String orderNumber);

}
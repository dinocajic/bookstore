package Model.ModelInterface;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by Dino Cajic on 10/29/2016.
 *
 * To be implemented in AddOrderToInventoryModel
 */
public interface AddOrderToInventoryModelInterface {

    /**
     * Gets the details about the order. i.e. what's in the order for that order number.
     * @param orderNumber The order number associated with an order
     * @return String[][] Contents of an order
     */
    String[][] getOrderDetails(String orderNumber);


    /**
     * Marks the order as received in the database. Index of orderFields matches index of orderQty
     * @param orderFields The ISBNs of each book
     * @param orderQty The qty's of each book
     * @return String success message
     */
    String receiveOrder(ArrayList<JTextField> orderFields, ArrayList<JSpinner> orderQty, String orderNumber);
}
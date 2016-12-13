package Model;

import Model.ModelInterface.AddOrderToInventoryModelInterface;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by Dino Cajic on 10/29/2016.
 *
 * Gathers information from the database and performs other checks on code.
 */
public class AddOrderToInventoryModel implements AddOrderToInventoryModelInterface {

    /**
     * Gets the details about the order. i.e. what's in the order for that order number.
     * @param orderNumber The order number associated with an order
     * @return String[][] Contents of an order
     */
    public String[][] getOrderDetails(String orderNumber) {
        DatabaseModel dbm = new DatabaseModel();
        String[][] orderContent = dbm.getBookDetailsFromOrder(orderNumber);
        // if(orderContent == null) means orderNumber not found
        return orderContent;
    }

    /**
     * Marks the order as received in the database. Index of orderFields matches index of orderQty
     * @param orderFields The ISBNs of each book
     * @param orderQty The qty's of each book
     * @return String success message
     */
    public String receiveOrder(ArrayList<JTextField> orderFields, ArrayList<JSpinner> orderQty, String orderNumber) {
        DatabaseModel dbm = new DatabaseModel();

        for (int x = 0; x < orderFields.size(); x++){
            String isbn = orderFields.get(x).getText();
            Integer qty = (Integer) orderQty.get(x).getValue();
            dbm.reduceBookStock(isbn, (qty * -1));
        }

        return dbm.markOrderAsReceived(orderNumber, System.currentTimeMillis());
    }
}
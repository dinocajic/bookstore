package Model;

import Model.ModelInterface.PlaceNewOrderModelInterface;

import javax.swing.*;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Dino Cajic on 10/29/2016.
 * Modified by Zachary Shoults on 11/04/2016.
 *
 * Gathers information from the database and performs other checks on code.
 */
public class PlaceNewOrderModel implements PlaceNewOrderModelInterface {

    /**
     * Checks to see if the form fields are correct
     * @param orderFields The TextFields with the ISBN numbers
     * @param orderQty The Qty fields matching the ISBNs
     * @return String Error Message if something went wrong
     */
    public String checkFormFields(ArrayList<JTextField> orderFields, ArrayList<JSpinner> orderQty) {
        String errorMsg = "The following errors occurred: ";

        //For any field that contains text in the ISBN field, it must have a quantity
        for (int i = 0; i < orderFields.size(); i++) {
            if (!orderFields.get(i).getText().trim().equals("") && (Integer)orderQty.get(i).getValue() < 1) {
                errorMsg += "\n - You must enter quantities larger than 0.";
                break;
            }
        }

        DatabaseModel dbm = new DatabaseModel();

        for (int i = 0; i < orderFields.size(); i++) {
            String isbn = orderFields.get(i).getText().trim();

            if (isbn.equals("")) {
                continue;  // Ignore blank entries entirely
            }

            if (!dbm.isBookInInventory(isbn)) {
                errorMsg += String.format("\n - The ISBN provided is not in the inventory (%s).",isbn);
            }
        }

        return errorMsg;
    }

    /**
     * Adds the order to the database
     * @param orderFields The TextFields with the ISBN numbers
     * @param orderQty The Qty fields matching the ISBNs
     * @return String Success if successful, otherwise, error message
     */
    public String insertOrder(ArrayList<JTextField> orderFields, ArrayList<JSpinner> orderQty) {
        if (orderFields.size() == 0) {
            return "Success.";
        }

        if (orderFields.get(0).getText().trim().equals("")) {
            return "Must enter at least one item to start an order.";
        }

        // orderID, firstBookISBN, firstBookQuantity
        String[] init_order = new String[3];

        init_order[0] = UUID.randomUUID().toString().replace("-", "").substring(0,10);
        init_order[1] = orderFields.get(0).getText().trim();
        init_order[2] = orderQty.get(0).getValue().toString();

        orderFields.remove(0);
        orderQty.remove(0);

        DatabaseModel dbm      = new DatabaseModel();
        String[] running_order = dbm.createOrder(init_order);

        while(orderFields.size() > 0){
            // Pass over all ISBN fields after the first one that are blank
            if (orderFields.get(0).getText().trim().equals("")) {
                orderFields.remove(0);
                orderQty.remove(0);
                continue;
            }

            String[] next_book = new String[3];

            next_book[0] = running_order[0];
            next_book[1] = orderFields.get(0).getText().trim();
            next_book[2] = orderQty.get(0).getValue().toString();

            orderFields.remove(0);
            orderQty.remove(0);

            running_order = dbm.updateOrder(running_order, next_book);
        }

        // 86400000  milliseconds in a day
        java.util.Random r    = new java.util.Random();
        int days_til_delivery = r.nextInt(8) + 4; // 4 - 11 days
        String db_message     = dbm.finalizeOrder(running_order, System.currentTimeMillis(), (86400000 * days_til_delivery));

        if (db_message.equals("Order complete.")) {
            return "Success";
        } else {
            return "Order not created.";
        }
    }
}
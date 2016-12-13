package Controller;

import Controller.ControllerInterface.AddOrderToInventoryControllerInterface;
import GUIobjects.CMPanel;
import Model.AddOrderToInventoryModel;
import View.AddOrderToInventoryView;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by Dino Cajic on 10/29/2016.
 *
 * Directs the communication between the AddOrderToInventoryView and the AddOrderToInventoryModel
 */
public class AddOrderToInventoryController implements AddOrderToInventoryControllerInterface {

    CMPanel display;

    public AddOrderToInventoryController() {}

    /**
     * @param display The display object that was instantiated in the ApplicationWindow class
     */
    public AddOrderToInventoryController(CMPanel display) {
        this.display = display;
    }

    /**
     * Once the Add Order to Inventory button is clicked in the menu, the method gets the
     * order details from the AddOrderToInventoryModel and calls the AddOrderToInventoryView
     * to display the form: the form for searching an order by order id.
     * @param order The order id that's being searched
     */
    public void displayInitialScreen(String order) {
        AddOrderToInventoryModel model = new AddOrderToInventoryModel();
        String[][] orderDetails = model.getOrderDetails(order.trim());

        AddOrderToInventoryView addOrderToInventoryView = new AddOrderToInventoryView(this.display);
        addOrderToInventoryView.displayAddOrderForm(orderDetails, order);
    }

    /**
     * Marks the order as received in the database. Index of orderFields matches index of orderQty.
     * @param orderFields The ISBNs of each book
     * @param orderQty The qty's of each book
     * @param orderNumber The order number of the order
     * @return String success message
     */
    public String receiveOrder(ArrayList<JTextField> orderFields, ArrayList<JSpinner> orderQty, String orderNumber) {
        AddOrderToInventoryModel model = new AddOrderToInventoryModel();
        return model.receiveOrder(orderFields, orderQty, orderNumber);
    }
}
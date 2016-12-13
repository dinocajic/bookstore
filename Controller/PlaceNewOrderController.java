package Controller;

import Controller.ControllerInterface.PlaceNewOrderControllerInterface;
import GUIobjects.CMPanel;
import Model.PlaceNewOrderModel;
import View.PlaceNewOrderView;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by Dino Cajic on 10/29/2016.
 *
 * Direct the communication between the PlaceNewOrderModel and PlaceNewOrderView
 */
public class PlaceNewOrderController implements PlaceNewOrderControllerInterface {

    CMPanel display;

    public PlaceNewOrderController() {}

    /**
     * @param display The display object that was instantiated in the ApplicationWindow class
     */
    public PlaceNewOrderController(CMPanel display) {
        this.display = display;
    }

    /**
     * Once the New Order menu button has been clicked, the method calls the PlaceNewOrderView to show the initial
     * form that allows the user to add books to the order.
     */
    public void displayInitialScreen() {
        PlaceNewOrderView view = new PlaceNewOrderView(this.display);
        view.displayAddBookToOrderForm();
    }

    /**
     * Checks to see if the form fields are correct
     * @param orderFields The TextFields with the ISBN numbers
     * @param orderQty The Qty fields matching the ISBNs
     * @return String Error Message if something went wrong
     */
    public String checkFormFields(ArrayList<JTextField> orderFields, ArrayList<JSpinner> orderQty) {
        PlaceNewOrderModel model = new PlaceNewOrderModel();
        return model.checkFormFields(orderFields, orderQty);
    }

    /**
     * Adds the order to the database
     * @param orderFields The TextFields with the ISBN numbers
     * @param orderQty The Qty fields matching the ISBNs
     * @return String Success if successful, otherwise, error message
     */
    public String insertOrder(ArrayList<JTextField> orderFields, ArrayList<JSpinner> orderQty) {
        PlaceNewOrderModel model = new PlaceNewOrderModel();
        return model.insertOrder(orderFields, orderQty);
    }
}
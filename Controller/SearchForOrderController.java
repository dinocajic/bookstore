package Controller;

import Controller.ControllerInterface.SearchForOrderControllerInterface;
import GUIobjects.CMPanel;
import Model.SearchForOrderModel;
import View.SearchForOrderView;

/**
 * Created by Dino Cajic on 10/29/2016.
 *
 * Directs the communication between the SearchForOrderModel and the SearchForOrderView
 */
public class SearchForOrderController implements SearchForOrderControllerInterface {

    CMPanel display;

    public SearchForOrderController() {}

    /**
     * @param display The display object that was instantiated in the ApplicationWindow class
     */
    public SearchForOrderController(CMPanel display) {
        this.display = display;
    }

    /**
     * Once the Search Orders menu button has been clicked, it calls the SearchForOrderView to display
     * the initial search form.
     */
    public void displayInitialScreen() {
        SearchForOrderView view = new SearchForOrderView(this.display);
        view.displayInitialSearchForm();
    }

    /**
     * Checks to see if the date is within range
     * @param fromMonth The starting month
     * @param fromYear The starting year
     * @param toMonth The ending month
     * @param toYear The ending year
     * @return String error message
     */
    public String checkFormFields(int fromMonth, int fromYear, int toMonth, int toYear) {
        SearchForOrderModel model = new SearchForOrderModel();
        return model.checkFormFields(fromMonth, fromYear, toMonth, toYear);
    }

    /**
     * Grabs the available orders created between the date ranges.
     * @param fromMonthIndex The starting month
     * @param fromYear The starting year
     * @param toMonthIndex The ending month
     * @param toYear The ending year
     * @return String error message or order content
     */
    public String[][] getOrderContent(int fromMonthIndex, int fromYear, int toMonthIndex, int toYear) {
        SearchForOrderModel model = new SearchForOrderModel();
        return model.getOrderContent(fromMonthIndex, fromYear, toMonthIndex, toYear);
    }

    /**
     * Returns the order when searched by ID
     * @param orderID The ID of the order
     * @return String order details or error message
     */
    public String[] getOrderByID(String orderID) {
        SearchForOrderModel model = new SearchForOrderModel();
        return model.getOrderByID(orderID);
    }
}

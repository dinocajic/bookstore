package Controller.ControllerInterface;

/**
 * Created by Dino Cajic on 10/29/2016.
 *
 * To be implemented in the SearchForOrderController
 */
public interface SearchForOrderControllerInterface {

    /**
     * Once the Search Orders menu button has been clicked, it calls the SearchForOrderView to display
     * the initial search form.
     */
    void displayInitialScreen();

    /**
     * Checks to see if the date is within range
     * @param fromMonth The starting month
     * @param fromYear The starting year
     * @param toMonth The ending month
     * @param toYear The ending year
     * @return String error message
     */
    String checkFormFields(int fromMonth, int fromYear, int toMonth, int toYear);

    /**
     * Grabs the available orders created between the date ranges.
     * @param fromMonthIndex The starting month
     * @param fromYear The starting year
     * @param toMonthIndex The ending month
     * @param toYear The ending year
     * @return String error message
     */
    String[][] getOrderContent(int fromMonthIndex, int fromYear, int toMonthIndex, int toYear);

    /**
     * Returns the order when searched by ID
     * @param orderID The ID of the order
     * @return String order details or error message
     */
    String[] getOrderByID(String orderID);
}

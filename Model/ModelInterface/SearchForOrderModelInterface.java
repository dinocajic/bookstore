package Model.ModelInterface;

/**
 * Created by Dino Cajic on 10/29/2016.
 *
 * To be implemented in SearchForModel
 */
public interface SearchForOrderModelInterface {

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
     * @param fromMonth The starting month
     * @param fromYear The starting year
     * @param toMonth The ending month
     * @param toYear The ending year
     * @return String[] order contents
     */
    String[][] getOrderContent(int fromMonth, int fromYear, int toMonth, int toYear);

    /**
     * Returns the order based on the order id
     * @param orderID The ID of the order
     * @return Order details
     */
    String[] getOrderByID(String orderID);
}
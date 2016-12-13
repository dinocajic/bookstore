package View.ViewInterface;

/**
 * Created by Dino Cajic on 10/29/2016.
 *
 * To be implemented in the AddOrderToInventoryView
 */
public interface AddOrderToInventoryViewInterface {

    /**
     * Displays the add order form
     * @param orderDetails the details about the order
     * @param orderNumber the order number
     */
    void displayAddOrderForm(String[][] orderDetails, String orderNumber);
}
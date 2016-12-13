package Model.ModelInterface;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by Dino Cajic on 10/29/2016.
 *
 * To be implemented in PlaceNewOrderModel
 */
public interface PlaceNewOrderModelInterface {

    /**
     * Checks to see if the form fields are correct
     * @param orderFields The TextFields with the ISBN numbers
     * @param orderQty The Qty fields matching the ISBNs
     * @return String Error Message if something went wrong
     */
    String checkFormFields(ArrayList<JTextField> orderFields, ArrayList<JSpinner> orderQty);

    /**
     * Adds the order to the database
     * @param orderFields The TextFields with the ISBN numbers
     * @param orderQty The Qty fields matching the ISBNs
     * @return String Success if successful, otherwise, error message
     */
    String insertOrder(ArrayList<JTextField> orderFields, ArrayList<JSpinner> orderQty);
}
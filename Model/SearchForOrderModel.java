package Model;

import Model.ModelInterface.SearchForOrderModelInterface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Dino Cajic on 10/29/2016.
 * Modified by Zachary Shoults on 11/04/2016.
 *
 * Gathers information from the database and performs other checks on code.
 */
public class SearchForOrderModel implements SearchForOrderModelInterface {

    /**
     * Checks to see if the date is within range
     * @param fromMonth The starting month
     * @param fromYear The starting year
     * @param toMonth The ending month
     * @param toYear The ending year
     * @return String error message
     */
    public String checkFormFields(int fromMonth, int fromYear, int toMonth, int toYear) {
        String errorMsg = "The following errors occurred: ";

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");

        String fromDateStr = "01 " + fromMonth + " " + fromYear;
        String toDateStr   = "30 " + toMonth   + " " + toYear;

        try {
            Date fromDate = dateFormat.parse(fromDateStr);
            Date toDate   = dateFormat.parse(toDateStr);
            toDate.getTime();
            long diff     = toDate.getTime() - fromDate.getTime();

            if (diff < 0) {
                errorMsg += "\n - You must enter an end date that's later in time";
            }
        } catch(ParseException e) {
            e.printStackTrace();
        }

        return errorMsg;
    }

    /**
     * Grabs the available orders created between the date ranges.
     * @param fromMonth The starting month
     * @param fromYear The starting year
     * @param toMonth The ending month
     * @param toYear The ending year
     * @return String[] order contents
     */
    public String[][] getOrderContent(int fromMonth, int fromYear, int toMonth, int toYear) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");
        String fromDateStr = "01 " + fromMonth + " " + fromYear;
        String toDateStr   = "28 " + toMonth   + " " + toYear;

        String[] orders;

        try {
            Date fromDate   = dateFormat.parse(fromDateStr);
            Long fromMillis = fromDate.getTime()+2678400000l; // Ends in an "L" not a "one" ( 1 ) LONG not INT

            Date toDate   = dateFormat.parse(toDateStr);
            Long toMillis = toDate.getTime()+2678400000l;     // Ends in an "L" not a "one" ( 1 )  LONG not INT

            DatabaseModel dbm = new DatabaseModel();
            orders = dbm.getOrderDateRange(fromMillis, toMillis);
            String[][] ordersAndStatus = new String[orders.length][2];

            for (int x = 0; x < orders.length; x++) {
                ordersAndStatus[x][0] = orders[x];
                ordersAndStatus[x][1] = dbm.isOrderRecieved(orders[x]) ? "received" : "pending";
            }

            return ordersAndStatus;

        } catch(ParseException e) {
            e.printStackTrace();
            System.out.println("Parse exception trying to find order in date range.");
        }

        return null;  // should never call
    }

    /**
     * Returns the order based on the order id
     * @param orderID The ID of the order
     * @return Order details
     */
    public String[] getOrderByID(String orderID) {
        //Schema: OrderID, Books, DateOrdered, DateExpectedDelivery, DeliveredStatus
        DatabaseModel dbm = new DatabaseModel();
        return dbm.getOrder(orderID);
    }
}
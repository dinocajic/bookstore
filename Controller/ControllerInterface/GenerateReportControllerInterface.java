package Controller.ControllerInterface;

import java.util.ArrayList;

/**
 * Created by Dino Cajic on 10/29/2016.
 *
 * To be implemented in the GenerateReportController class
 */
public interface GenerateReportControllerInterface {

    /**
     * Instantiates the SearchReportView and calls the displaySalesReportView
     */
    void displaySalesReportSearch();

    /**
     * Returns the sales history
     * @param fromMonth From a specific month
     * @param fromYear From a specific year
     * @param toMonth To a specific month
     * @param toYear To a specific year
     * @return Sales history
     */
    ArrayList<String[][]> getSalesForDate(int fromMonth, int fromYear, int toMonth, int toYear);

    /**
     * Calls the view to display the results
     * @param results The actual results
     */
    void displaySalesReportResults(ArrayList<String[][]> results);
}
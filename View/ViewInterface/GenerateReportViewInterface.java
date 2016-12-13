package View.ViewInterface;

import java.util.ArrayList;

/**
 * Created by Dino Cajic on 10/29/2016.
 *
 * To be implemented in the GenerateReportView
 */
public interface GenerateReportViewInterface {

    /**
     * Displays a form to search for the sales report between date ranges
     */
    void displaySalesReportForm();

    /**
     * Displays the report using HTML. The HTML does not support many modern features so it's very simplistic.
     * @param results The report results
     */
    void displayReportResults(ArrayList<String[][]> results);
}
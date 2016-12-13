package Controller;

import Controller.ControllerInterface.GenerateReportControllerInterface;
import GUIobjects.CMPanel;
import Model.GenerateReportModel;
import View.GenerateReportView;

import java.util.ArrayList;

/**
 * Created by Dino Cajic on 10/29/2016.
 * Modified by Zachary Shoults on 11/12/2016
 *
 * Directs the communication between the GenerateReportModel and GenerateReportView
 */
public class GenerateReportController implements GenerateReportControllerInterface {

    CMPanel display;

    /**
     * @param display The display object that was instantiated in the ApplicationWindow class
     */
    public GenerateReportController(CMPanel display) {
        this.display = display;
    }

    /**
     * Instantiates the SearchReportView and calls the displaySalesReportForm method
     */
    public void displaySalesReportSearch() {
        GenerateReportView view2 = new GenerateReportView(this.display);
        view2.displaySalesReportForm();
    }

    /**
     * Returns the sales history
     * @param fromMonth From a specific month
     * @param fromYear From a specific year
     * @param toMonth To a specific month
     * @param toYear To a specific year
     * @return Sales history
     */
    public ArrayList<String[][]> getSalesForDate(int fromMonth, int fromYear, int toMonth, int toYear){
        GenerateReportModel model = new GenerateReportModel();
        return model.getSalesForDate(fromMonth, fromYear, toMonth, toYear);
    }

    /**
     * Calls the view to display the results
     * @param results The actual results
     */
    public void displaySalesReportResults(ArrayList<String[][]> results){
        GenerateReportView view3 = new GenerateReportView(this.display);
        view3.displayReportResults(results);
    }
}
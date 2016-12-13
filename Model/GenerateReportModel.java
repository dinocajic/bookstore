package Model;

import Model.ModelInterface.GenerateReportModelInterface;
import View.GenerateReportView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Dino Cajic on 10/29/2016.
 * Modified by Zachary Shoults on 11/04/2016.
 *
 * Gets sales information
 */
public class GenerateReportModel implements GenerateReportModelInterface {

    /**
     * Creates a sales report between date range
     * @param fromMonth The beginning search month
     * @param fromYear The beginning search year
     * @param toMonth The ending search month
     * @param toYear The ending search year
     * @return Sales for the specified date range
     */
    public ArrayList<String[][]> getSalesForDate(int fromMonth, int fromYear, int toMonth, int toYear) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");

        String fromDateStr = "01 " + fromMonth + " " + fromYear;
        String toDateStr   = "31 " + toMonth   + " " + toYear;
        ArrayList<String[][]> allSalesData = new ArrayList<>();

        try {
            Date fromDate   = dateFormat.parse(fromDateStr);
            Long fromMillis = fromDate.getTime() + 2678400000l; // ends in an "L" not a "one" ( 1 ) LONG not INT
            Date toDate     = dateFormat.parse(toDateStr);
            Long toMillis   = toDate.getTime()+2678400000l;
            Calendar cFrom  = Calendar.getInstance();

            cFrom.setTimeInMillis(fromMillis);
            Calendar cTo = Calendar.getInstance();
            cTo.setTimeInMillis(toMillis);

            GenerateReportView.fromDate = cFrom.getTime().toString().split(" ");
            GenerateReportView.toDate   = cTo.getTime().toString().split(" ");

            DatabaseModel dbm = new DatabaseModel();
            allSalesData      = dbm.getSalesInformation(fromMillis, toMillis);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return allSalesData;
    }
}
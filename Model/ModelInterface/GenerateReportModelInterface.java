package Model.ModelInterface;

import java.util.ArrayList;

/**
 * Created by Dino Cajic on 10/29/2016.
 *
 * To be implemented in GenerateReportModel
 */
public interface GenerateReportModelInterface {

    /**
     * Creates a sales report between date range
     * @param fromMonth The beginning search month
     * @param fromYear The beginning search year
     * @param toMonth The ending search month
     * @param toYear The ending search year
     * @return Sales for the specified date range
     */
    ArrayList<String[][]> getSalesForDate(int fromMonth, int fromYear, int toMonth, int toYear);
}
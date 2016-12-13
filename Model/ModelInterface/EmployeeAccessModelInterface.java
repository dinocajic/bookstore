package Model.ModelInterface;

/**
 * Created by Dino Cajic on 10/29/2016.
 *
 * To be implemented in EmployeeAccessModel
 */
public interface EmployeeAccessModelInterface {

    /**
     * Checks the form to make sure no errors occurred
     * @param employee The employee being added to the database
     * @return String Message for why not successful
     */
    String checkForm(String[] employee);

    /**
     * Adds the new employee to the database
     * @param employee The employee being added to the database
     * @return String Success or not
     */
    String insertEmployee(String[] employee);

    /**
     * Checks to see if the user can be removed and everything is good
     * @param type The admin level of user. 1 = admin, 2 = root admin
     * @param email The email address of the user to be removed
     * @return String Error message if any
     */
    String checkFormForRemove(int type, String email);

    /**
     * Removes the employee from the database
     * @param employeeEmail The employee email to be removed
     * @return String Success or error
     */
    String removeEmployee(String employeeEmail);
}
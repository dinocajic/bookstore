package Controller.ControllerInterface;

/**
 * Created by Dino Cajic on 10/29/2016.
 *
 * To be implemented in the EmployeeAccessController class
 */
public interface EmployeeAccessControllerInterface {

    /**
     * Instantiates the EmployeeAccessView and calls the addAdminForm method
     * @param type The type of account 1 = employee; 2 = admin
     */
    void displayAddAdminAccount(int type);

    /**
     * Instantiates the EmployeeAccessView and calls the addStaffForm method
     * @param type The type of account 1 = employee: 2 = admin
     */
    void displayAddStaffAccount(int type);

    /**
     * Instantiates the EmployeeAccessView and calls the deleteAccountForm method
     * @param type The type of account 1 = admin deleting employee; 2 = admin
     */
    void displayDeleteAccount(int type);

    /**
     * Checks the form to make sure no errors occurred.
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
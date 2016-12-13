package Controller;

import Controller.ControllerInterface.EmployeeAccessControllerInterface;
import GUIobjects.CMPanel;
import Model.EmployeeAccessModel;
import View.EmployeeAccessView;

/**
 * Created by Dino Cajic on 10/29/2016.
 *
 * Directs the communication between the EmployeeAccessModel and the EmployeeAccessView
 */
public class EmployeeAccessController implements EmployeeAccessControllerInterface {

    CMPanel display;

    public EmployeeAccessController() {}

    /**
     * @param display The display object that was instantiated in the ApplicationWindow class
     */
    public EmployeeAccessController(CMPanel display) {
        this.display = display;
    }

    /**
     * Instantiates the EmployeeAccessView and calls the addAdminForm method
     * @param type The type of account 1 = employee; 2 = admin
     */
    public void displayAddAdminAccount(int type) {
        EmployeeAccessView view = new EmployeeAccessView(this.display);
        view.displayAddAdminAccountForm(type);
    }

    /**
     * Instantiates the EmployeeAccessView and calls the addStaffForm method
     * @param type The type of account 1 = employee: 2 = admin
     */
    public void displayAddStaffAccount(int type) {
        EmployeeAccessView view2 = new EmployeeAccessView(this.display);
        view2.displayAddStaffAccountForm(type);
    }

    /**
     * Instantiates the EmployeeAccessView and calls the deleteAccountForm method
     * @param type The type of account 1 = admin deleting employee; 2 = admin
     */
    public void displayDeleteAccount(int type) {
        EmployeeAccessView view3 = new EmployeeAccessView(this.display);
        view3.displayDeleteAccountForm(type);
    }

    /**
     * Checks the form to make sure no errors occurred.
     * @param employee The employee being added to the database
     * @return String Message for why not successful
     */
    public String checkForm(String[] employee) {
        EmployeeAccessModel model = new EmployeeAccessModel();
        return model.checkForm(employee);
    }

    /**
     * Adds the new employee to the database
     * @param employee The employee being added to the database
     * @return String Success or not
     */
    public String insertEmployee(String[] employee) {
        EmployeeAccessModel model = new EmployeeAccessModel();
        return model.insertEmployee(employee);
    }

    /**
     * Checks to see if the user can be removed and everything is good
     * @param type The admin level of user. 1 = admin, 2 = root admin
     * @param email The email address of the user to be removed
     * @return String Error message if any
     */
    public String checkFormForRemove(int type, String email) {
        EmployeeAccessModel model = new EmployeeAccessModel();
        return model.checkFormForRemove(type, email);
    }

    /**
     * Removes the employee from the database
     * @param employeeEmail The employee email to be removed
     * @return String Success or error
     */
    public String removeEmployee(String employeeEmail) {
        EmployeeAccessModel model = new EmployeeAccessModel();
        return model.removeEmployee(employeeEmail);
    }
}
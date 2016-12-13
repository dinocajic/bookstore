package View.ViewInterface;

/**
 * Created by Dino Cajic on 10/29/2016.
 *
 * To be implemented in the EmployeeAccessView
 */
public interface EmployeeAccessViewInterface {

    /**
     * Displays the form for adding the administrative account. Includes all of the fields as staff account, and some
     * additional ones.
     * @param type The type of user. 1 = staff; 2 = admin
     */
    void displayAddAdminAccountForm(int type);

    /**
     * Displays the form for adding the staff account.
     * @param type The type of user. 1 = staff; 2 = admin
     */
    void displayAddStaffAccountForm(int type);

    /**
     * Displays a form to search for a user to delete.
     * @param type The type of user. 1 = staff; 2 = admin
     */
    void displayDeleteAccountForm(int type);
}
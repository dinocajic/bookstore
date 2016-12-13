package Controller.ControllerInterface;

/**
 * Created by Dino Cajic on 10/27/2016.
 *
 * To be used in the LoginController class
 */
public interface LoginControllerInterface {

    /**
     * Instantiates the LoginView and calls the initial method.
     */
    void displayInitialScreen();

    /**
     * Calls the LoginModel to see if the form fields are empty
     * @param username The username of the employee
     * @param password The password of the employee
     * @return String : Empty if username and password are filled in, otherwise Username or Password.
     */
    String checkFormFields(String username, String password);

    /**
     * Attempts to Log into the system
     * @param username The username of the employee
     * @param password The password of the employee
     * @return String : Success if successful. Otherwise, return 0.
     */
    int attemptLogin(String username, String password);

    /**
     * Attempts to look up if username exists.
     * @param username The username of the employee
     * @return boolean : True if username exists, false otherwise.
     */
    boolean searchForUsername(String username);

    /**
     * Displays the initial search for a username
     */
    void displaySearchForUserForm();

    /**
     * Instantiates the LoginView and calls the resetPasswordForm( method
     */
    void displayResetPasswordScreen();

    /**
     * Checks to see if the username is empty
     * @param username The username of the employee
     * @return boolean : True if empty
     */
    boolean checkForFieldsForUserSearch(String username);

    /**
     * Checks to see if the password field is empty
     * @param password The new password entered
     * @return boolean : True if password empty
     */
    boolean checkFormFieldsResetPassword(String password);

    /**
     * Reset the password for the particular username
     * @param username The username of the employee that needs the password reset
     * @param password The new password
     * @return boolean : True if password change was a success
     */
    boolean resetPassword(String username, String password);
}
package Controller;

import Controller.ControllerInterface.LoginControllerInterface;
import GUIobjects.CMPanel;
import GUIobjects.MenuPanel;
import GUIobjects.RightPanel;
import Model.LoginModel;
import View.LoginView;

/**
 * Created by Dino Cajic on 10/27/2016.
 *
 * Directs the communication between the LoginView and the LoginModel.
 */
public class LoginController implements LoginControllerInterface {

    CMPanel left;
    CMPanel display;
    RightPanel right;
    MenuPanel menu;

    public LoginController() {}

    /**
     * @param display The display object that was instantiated in the ApplicationWindow class
     * @param right The right object that was instantiated in the ApplicationWindow class
     * @param menu The menu object that was instantiated in the ApplicationWindow class
     */
    public LoginController(CMPanel left, CMPanel display, RightPanel right, MenuPanel menu) {
        this.left = left;
        this.display = display;
        this.right   = right;
        this.menu    = menu;
    }

    /**
     * @param display The display object that was instantiated in the ApplicationWindow class
     */
    public LoginController(CMPanel display) {
        this.display = display;
    }

    /**
     * Once the Login Button is clicked, the displayLogin method calls the LoginView to display
     * the initial Username and Password text fields.
     */
    public void displayInitialScreen() {
        LoginView view = new LoginView(this.left, this.display, this.right, this.menu);
        view.displayLoginForm();
    }

    /**
     * Calls the LoginModel to see if the form fields are empty
     * @param username The username of the employee
     * @param password The password of the employee
     * @return String : Empty if username and password are filled in, otherwise Username or Password.
     */
    public String checkFormFields(String username, String password) {
        LoginModel model = new LoginModel();
        return model.checkFormFields(username, password);
    }

    /**
     * Attempts to Log into the system
     * @param username The username of the employee
     * @param password The password of the employee
     * @return String : Success if successful. Otherwise, return 0.
     */
    public int attemptLogin(String username, String password) {
        LoginModel model = new LoginModel();
        int loginResult = model.attemptLogin(username, password);

        if(loginResult>0){
            HomepageController hpc = new HomepageController(LoginController.this.left, LoginController.this.display, LoginController.this.right);
            hpc.displayHomepage();
            LoginController.this.menu.currentUsernameLoggedIn = username;
        }

        return loginResult;
    }

    /**
     * Attempts to look up if username exists.
     * @param username The username of the employee
     * @return boolean : True if username exists, false otherwise.
     */
    public boolean searchForUsername(String username) {
        LoginModel model = new LoginModel();
        return model.searchForUsername(username);
    }

    /**
     * Displays the initial search for a username
     */
    public void displaySearchForUserForm() {
        LoginView view = new LoginView(this.left, this.display, this.right, this.menu);
        view.displaySearchForUsernameForm();
    }

    /**
     * Instantiates the LoginView and calls the resetPasswordForm( method
     */
    public void displayResetPasswordScreen() {
        LoginView view2 = new LoginView(this.left, this.display, this.right, this.menu);
        view2.displayResetPasswordForm();
    }

    /**
     * Checks to see if the username is empty
     * @param username The username of the employee
     * @return boolean : True if empty
     */
    public boolean checkForFieldsForUserSearch(String username) {
        LoginModel model = new LoginModel();
        return model.checkForFieldsForUserSearch(username);
    }

    /**
     * Checks to see if the password field is empty
     * @param password The new password entered
     * @return boolean : True if password empty
     */
    public boolean checkFormFieldsResetPassword(String password) {
        LoginModel model = new LoginModel();
        return model.checkFormFieldsResetPassword(password);
    }

    /**
     * Reset the password for the particular username
     * @param username The username of the employee that needs the password reset
     * @param password The new password
     * @return boolean : True if password change was a success
     */
    public boolean resetPassword(String username, String password) {
        LoginModel model = new LoginModel();
        return model.resetPassword(username, password);
    }
}
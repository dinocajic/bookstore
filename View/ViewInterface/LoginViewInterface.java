package View.ViewInterface;

/**
 * Created by Dino Cajic on 10/27/2016.
 *
 * To be implemented in the LoginView class
 */
public interface LoginViewInterface {

    /**
     * Displays the initial login screen.
     * Needs to include the Username, Password and Submit button
     */
    void displayLoginForm();

    /**
     * Displays the reset password form
     * Needs to include the Username, new password, repeat new password, and submit button.
     */
    void displaySearchForUsernameForm();

    /**
     * Displays the reset password form
     * Needs to include the Username, new password, repeat new password, and submit button.
     */
    void displayResetPasswordForm();
}
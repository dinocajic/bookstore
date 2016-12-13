package Model;

import GUIobjects.MenuPanel;
import Model.ModelInterface.LoginModelInterface;

/**
 * Created by Dino Cajic on 10/29/2016.
 * Modified by Zachary Shoults on 11/04/2016.
 *
 * Gathers information from the database and performs other checks on code.
 */
public class LoginModel implements LoginModelInterface {

    /**
     * Calls the LoginModel to see if the form fields are empty
     * @param username The username of the employee
     * @param password The password of the employee
     * @return String : Empty if username and password are filled in, otherwise Username or Password.
     */
    public String checkFormFields(String username, String password) {
        if (username.trim().equals("")) {
            return "Username";
        }

        if (password.trim().equals("")) {
            return "Password";
        }

        return "";
    }

    /**
     * Attempts to Log into the system
     * @param username The username of the employee
     * @param password The password of the employee
     * @return String : Success if successful. Otherwise, return 0.
     */
    public int attemptLogin(String username, String password) {
        DatabaseModel dbm     = new DatabaseModel();
        String database_reply = dbm.attemptLogin(username, password);

        switch(database_reply.toLowerCase()) {
            case "employee":
                return 1;
            case "admin":
                return 2;
            case "root":
                return 3;
            // Note: a value of 0 means "anonymous" user to the software. It says nothing about the reason why the user could not log in.
            default:
                return 0;
        }
    }

    /**
     * Attempts to look up if username exists.
     * @param username The username of the employee
     * @return boolean : True if username exists, false otherwise.
     */
    public boolean searchForUsername(String username) {
        DatabaseModel dbm = new DatabaseModel();
        return dbm.usernameExists(username);
    }

    /**
     * Checks to see if the username is empty
     * @param username The username of the employee
     * @return boolean : True if empty
     */
    public boolean checkForFieldsForUserSearch(String username) {
        return username.equals("");
    }

    /**
     * Checks to see if the password field is empty
     * @param password The new password entered
     * @return boolean : True if password empty
     */
    public boolean checkFormFieldsResetPassword(String password) {
        return password.equals("");
    }

    /**
     * Reset the password for the particular username
     * @param username The username of the employee that needs the password reset
     * @param password The new password
     * @return boolean : True if password change was a success
     */
    public boolean resetPassword(String username, String password) {
        DatabaseModel dbm = new DatabaseModel();
        Integer targetedUserLevel = 0;
        String targetedUserAccess = dbm.getUserAccessLevel(username);

        if (targetedUserAccess.trim().equalsIgnoreCase("employee")){
            targetedUserLevel = 1;
        } else if (targetedUserAccess.trim().equalsIgnoreCase("admin")) {
            targetedUserLevel = 2;
        } else if (targetedUserAccess.trim().equalsIgnoreCase("root")) {
            targetedUserLevel = 3;
        }

        if (MenuPanel.currentlyLoggedIn < targetedUserLevel) {
            return false;
        }

        String database_reply = dbm.updatePassword(username, password);
        return database_reply.equals("Password updated successfully.");
    }
}
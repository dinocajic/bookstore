package View;

import Config.Config;
import Controller.LoginController;
import GUIobjects.CMPanel;
import GUIobjects.MenuPanel;
import GUIobjects.RightPanel;
import Libraries.Layout;
import View.ViewInterface.LoginViewInterface;
import net.miginfocom.swing.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Dino Cajic on 10/27/2016.
 *
 * Displays the Login Screen
 */
public class LoginView implements LoginViewInterface {

    CMPanel left;
    CMPanel display;
    RightPanel right;
    MenuPanel menu;

    JLabel labelUsername;
    JLabel labelPassword;
    JLabel labelMain;
    JLabel message       = new JLabel("");

    JButton buttonLogin;
    JButton resetPassword;
    JButton searchReset;
    JTextField textUsername;
    JPasswordField fieldPassword;

    JPanel mainLabelPane = new JPanel(new MigLayout());

    Layout layout = new Layout();

    /**
     * @param display The display object that was instantiated in the ApplicationWindow class
     * @param right The right portion of the screen
     * @param menu The top menu bar
     */
    public LoginView(CMPanel left, CMPanel display, RightPanel right, MenuPanel menu) {
        this.left    = left;
        this.display = display;
        this.right   = right;
        this.menu    = menu;
    }

    /**
     * Displays the initial login screen. You're more than welcome to use any layout that you want.
     * I've created it with the BoxLayout.
     *
     * Creates 3 boxes. One to hold the username label and textfield
     * The other to hold the password label and textfield.
     * The third one holds the username and password boxes one right underneath the other, and it also holds the
     * Login Button. The vertical box is passed back to the CMPanel display object to modify the contents.
     */
    public void displayLoginForm() {
        this.display.resetToEmpty();

        this.initializeLoginProperties();
        this.adjustLoginPropertySizes();
        this.addLoginListeners();

        mainLabelPane.add(this.labelMain);

        // Add items to the panel
        JPanel panel = new JPanel(new MigLayout());
        panel.add(this.mainLabelPane, "span");
        panel.add(this.message,       "span");
        panel.add(this.labelUsername);
        panel.add(this.textUsername,  "wrap");
        panel.add(this.labelPassword);
        panel.add(this.fieldPassword, "wrap");
        panel.add(new JLabel(""));
        panel.add(this.buttonLogin,   "split 2");

        this.display.add(panel);
    }

    /**
     * Initializes the properties required for Login Screen
     */
    private void initializeLoginProperties() {
        this.labelUsername = new JLabel("Username: ");
        this.labelPassword = new JLabel("Password: ");
        this.labelMain     = new JLabel("Employee Login");
        this.textUsername  = new JTextField(39);
        this.fieldPassword = new JPasswordField(39);
        this.buttonLogin   = new JButton("Login");
        this.resetPassword = new JButton("Reset Password");
    }

    /**
     * Adjusts the sizes of the login properties
     */
    private void adjustLoginPropertySizes() {
        this.textUsername.setPreferredSize(new Dimension(39, 40));
        this.fieldPassword.setPreferredSize(new Dimension(39, 40));
        this.labelUsername.setFont(Config.default_font);
        this.labelPassword.setFont(Config.default_font);
        this.textUsername.setFont(Config.default_font);
        this.fieldPassword.setFont(Config.default_font);
        this.labelMain.setFont(Config.title_font);

        this.layout.formatMainButtons(this.buttonLogin);
        this.layout.formatMainButtons(this.resetPassword);
        this.layout.formatDisplayTitle(this.mainLabelPane);
    }

    /**
     * Adds the necessary listeners for the login form.
     */
    private void addLoginListeners() {
        // Reset password button listener
        this.resetPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginController controller = new LoginController(LoginView.this.display);
                controller.displaySearchForUserForm();
            }
        });

        // Login Button Listener
        this.buttonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = LoginView.this.textUsername.getText();
                String password = new String(LoginView.this.fieldPassword.getPassword());

                LoginController controller = new LoginController(LoginView.this.left, LoginView.this.display, LoginView.this.right, LoginView.this.menu);
                String check = controller.checkFormFields(username, password);

                // If previously set to warning color, reset to white.
                LoginView.this.textUsername.setBackground(Color.white);
                LoginView.this.textUsername.setBackground(Color.white);

                if (check.equals("Username")) {
                    LoginView.this.textUsername.setBackground(Config.warning_color);
                    return;
                }

                if (check.equals("Password")) {
                    LoginView.this.fieldPassword.setBackground(Config.warning_color);
                    return;
                }

                int attemptLogin = controller.attemptLogin(username, password);

                if (attemptLogin > 0) {
                    LoginView.this.menu.currentlyLoggedIn = attemptLogin;
                    LoginView.this.menu.updateUser(attemptLogin);
                } else {
                    LoginView.this.message.setText("Could not log you in");
                }
            }
        });

    }

    /**
     * Displays the reset password form
     * Needs to include the Username, new password, repeat new password, and submit button.
     */
    public void displaySearchForUsernameForm() {
        this.display.resetToEmpty();
        this.initializeSearchUserProperties();
        this.adjustSearchUserPropertySizes();
        this.addSearchUsersListeners();

        this.mainLabelPane.add(this.labelMain);

        JPanel panel = new JPanel(new MigLayout());
        panel.add(this.mainLabelPane, "span");
        panel.add(this.message,       "wrap");
        panel.add(this.textUsername,  "wrap");
        panel.add(searchReset);

        this.display.add(panel);
    }

    /**
     * Initializes the properties
     */
    private void initializeSearchUserProperties() {
        this.labelMain     = new JLabel("Reset User Password");
        this.labelUsername = new JLabel("Search for Username: ");
        this.textUsername  = new JTextField("Search For Username", 26);
        this.fieldPassword = new JPasswordField(26);
        this.resetPassword = new JButton("Reset Password");
        this.searchReset   = new JButton("Search");
    }

    /**
     * Adjusts the properties to fit the Reset Password View precisely
     */
    private void adjustSearchUserPropertySizes() {
        this.textUsername.setPreferredSize(new Dimension(26, 40));
        this.fieldPassword.setPreferredSize(new Dimension(26, 40));
        this.labelMain.setFont(Config.title_font);
        this.labelUsername.setFont(Config.default_font);
        this.layout.formatMainButtons(this.searchReset);
        this.layout.formatDisplayTitle(this.mainLabelPane);
    }

    /**
     * Adds the necessary button listeners for search and reset password
     */
    private void addSearchUsersListeners() {
        this.searchReset.addActionListener(new ActionListener() {

            /**
             * Check to see if username field is empty.
             * Check to see if username exists.
             * Call Password Reset Form if tests are passed
             */
            public void actionPerformed(ActionEvent e) {
                String username = LoginView.this.textUsername.getText();

                LoginController controller = new LoginController();

                boolean test = controller.checkForFieldsForUserSearch(username);

                if (test) {
                    LoginView.this.textUsername.setBackground(Config.warning_color);
                    return;
                }

                boolean search = controller.searchForUsername(username);

                if (!search) {
                    LoginView.this.message.setText("Username does not exist.");
                    return;
                }

                LoginView.this.message.setText("");
                LoginView.this.displayResetPasswordForm();
            }
        });

        // Adds a clear listener to field when mouse is clicked
        this.textUsername.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (LoginView.this.textUsername.getText().equals("Search For Username")) {
                    LoginView.this.textUsername.setText("");
                }
            }

            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
        });
    }

    /**
     * Form that allows the user to enter a new password
     */
    public void displayResetPasswordForm() {
        this.display.resetToEmpty();

        this.initializeResetPasswordProperties();
        this.adjustResetPasswordPropertySizes();
        this.addResetPasswordListeners();

        this.mainLabelPane.add(this.labelMain);

        JPanel panel = new JPanel(new MigLayout());
        panel.add(this.mainLabelPane, "span");
        panel.add(this.message,       "span");
        panel.add(this.labelUsername);
        panel.add(this.textUsername,  "wrap");
        panel.add(this.labelPassword);
        panel.add(this.fieldPassword);
        panel.add(this.resetPassword);

        this.display.add(panel);
    }

    /**
     * Initializes the properties required for password reset page
     */
    private void initializeResetPasswordProperties() {
        this.labelPassword = new JLabel("Enter New Password: ");
        this.textUsername  = new JTextField(this.textUsername.getText());
        this.textUsername.setEditable(false);
        this.labelUsername = new JLabel("Username: ");
    }

    /**
     * Adjusts the form field sizes on reset password screen
     */
    private void adjustResetPasswordPropertySizes() {
        this.fieldPassword.setPreferredSize(new Dimension(35, 40));
        this.labelUsername.setFont(Config.default_font);
        this.labelPassword.setFont(Config.default_font);

        this.layout.formatMainButtons(this.resetPassword);
        this.layout.formatDisplayTitle(this.mainLabelPane);
    }

    /**
     * Adds the listener to the reset password button.
     * Checks to make sure that the new password field isn't empty.
     * Resets the password.
     */
    private void addResetPasswordListeners() {
        this.resetPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String password = new String(LoginView.this.fieldPassword.getPassword());

                LoginController controller = new LoginController();
                boolean test = controller.checkFormFieldsResetPassword(password);

                if (test) {
                    LoginView.this.fieldPassword.setBackground(Config.warning_color);
                    return;
                }

                // Reset the color if it was set to red before
                LoginView.this.fieldPassword.setBackground(Color.white);

                String username = LoginView.this.textUsername.getText();
                boolean resetPassword = controller.resetPassword(username, password);

                if (resetPassword) {
                    LoginView.this.message.setText("Password has been reset successfully");
                    return;
                }

                LoginView.this.message.setText("Something went wrong with resetting the password");
            }
        });
    }
}
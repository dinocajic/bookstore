package View;

import Config.Config;
import Controller.EmployeeAccessController;
import GUIobjects.CMPanel;
import Libraries.Layout;
import View.ViewInterface.EmployeeAccessViewInterface;
import net.miginfocom.swing.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;

/**
 * Created by Dino Cajic on 10/29/2016.
 *
 * Displays the screen for adding and removing employee accounts
 */
public class EmployeeAccessView implements EmployeeAccessViewInterface {

    CMPanel display;
    JTextField firstName      = new JTextField("First Name", 25);
    JTextField middleInit     = new JTextField("M.I.", 10);
    JTextField lastName       = new JTextField("Last Name", 25);

    String[] genders          = {"Male","Female"};
    JComboBox gender          = new JComboBox(genders);

    JTextField primaryPhone   = new JTextField("Primary Phone", 25);
    JTextField secondaryPhone = new JTextField("Secondary Phone", 25);

    JTextField email          = new JTextField("Email Address", 25);
    JTextField username       = new JTextField("Username", 25);
    JTextField password       = new JTextField("Password", 25);

    JTextField emContact      = new JTextField("Emergency Contact Name", 25);
    JTextField emContactPh    = new JTextField("Emergency Contact Number", 25);

    JTextField street         = new JTextField("Street", 25);
    JTextField city           = new JTextField("City", 25);
    JTextField state          = new JTextField("ST", 5);
    JTextField zip            = new JTextField("Zip", 19);

    String[] months           = Config.months;
    String[] days             = new String[31];
    String[] years            = new String[100];
    String[] accountTypes     = {"Admin", "Employee"};

    JLabel labelMain          = new JLabel("Create Account");
    JPanel mainLabelPane      = new JPanel(new MigLayout());
    JLabel message            = new JLabel("");
    JLabel dobLabel           = new JLabel("DOB: ");

    JComboBox dobMonth;
    JComboBox dobDay;
    JComboBox dobYear;
    JComboBox acctTypes;

    JButton addEmployee       = new JButton("Create");
    JButton removeButton      = new JButton("Remove");

    Layout layout             = new Layout();

    /**
     * @param display The display object that was instantiated in the ApplicationWindow class
     */
    public EmployeeAccessView(CMPanel display) {
        this.display = display;
    }

    /**
     * Displays the form for adding the administrative account. Includes all of the fields as staff account, and some
     * additional ones.
     *
     * @param type The type of account 1 = employee; 2 = admin
     */
    public void displayAddAdminAccountForm(int type) {
        this.display.resetToEmpty();

        this.initializeProperties(type);
        this.adjustPropertySizes();
        this.addClearListeners();
        this.addEmployeeListener();

        this.mainLabelPane.add(this.labelMain);

        // Add items to the panel
        JPanel panel = new JPanel(new MigLayout());
        panel.add(this.mainLabelPane,  "span");
        panel.add(this.message,        "span");

        panel.add(this.firstName);
        panel.add(this.street,         "wrap");
        panel.add(this.middleInit);
        panel.add(this.city,           "wrap");
        panel.add(this.lastName);
        panel.add(this.state,          "split 2");
        panel.add(this.zip,            "wrap");
        panel.add(this.gender);

        JPanel dobPanel = new JPanel(new MigLayout());
        dobPanel.add(this.dobMonth);
        dobPanel.add(this.dobDay);
        dobPanel.add(this.dobYear);

        panel.add(this.dobLabel,       "split 2");
        panel.add(dobPanel,            "wrap");

        panel.add(this.primaryPhone);
        panel.add(this.secondaryPhone, "wrap");
        panel.add(this.email);
        panel.add(this.username,       "wrap");
        panel.add(this.emContact);
        panel.add(this.password,       "wrap");
        panel.add(this.emContactPh);
        panel.add(this.acctTypes,      "wrap");
        panel.add(this.addEmployee);

        this.display.add(panel);
    }

    /**
     * Initialize certain properties
     * @param type Employee type
     */
    private void initializeProperties(int type) {
        Calendar year = Calendar.getInstance();

        for (int i = 0; i < this.years.length; i++) {
            Integer yr = (year.get(Calendar.YEAR) - i);
            this.years[i] = yr.toString();
        }

        for (int i = 1; i <= this.days.length; i++) {
            this.days[i - 1] = "" + i;
        }

        this.dobDay    = new JComboBox<>(this.days);
        this.dobMonth  = new JComboBox<>(this.months);
        this.dobYear   = new JComboBox<>(this.years);

        this.acctTypes = new JComboBox<>(this.accountTypes);

        if (type == 1) {
            this.acctTypes.setSelectedItem("Employee");
            this.acctTypes.setEnabled(false);
        }
    }

    /**
     * Adjusts the sizes of certain properties
     */
    private void adjustPropertySizes() {
        this.labelMain.setFont(Config.title_font);
        this.labelMain.setBorder(new EmptyBorder(10, 10, 10, 10));

        this.layout.formatDisplayTitle(this.mainLabelPane);
        this.layout.formatMainButtons(this.addEmployee);

        this.firstName.setPreferredSize(new Dimension(25, 30));
        this.street.setPreferredSize(new Dimension(25,30));
        this.middleInit.setPreferredSize(new Dimension(25, 30));
        this.city.setPreferredSize(new Dimension(25, 30));
        this.lastName.setPreferredSize(new Dimension(25, 30));
        this.state.setPreferredSize(new Dimension(5, 30));
        this.zip.setPreferredSize(new Dimension(19, 30));
        this.gender.setPreferredSize(new Dimension(200, 30));
        this.gender.setMinimumSize(new Dimension(200, 30));
        this.dobLabel.setPreferredSize(new Dimension(5, 30));
        this.dobMonth.setPreferredSize(new Dimension(12, 30));
        this.dobDay.setPreferredSize(new Dimension(6, 30));
        this.dobYear.setPreferredSize(new Dimension(92, 30));
        this.dobYear.setMinimumSize(new Dimension(92, 30));

        this.primaryPhone.setPreferredSize(new Dimension(25, 30));
        this.secondaryPhone.setPreferredSize(new Dimension(25, 30));
        this.email.setPreferredSize(new Dimension(25, 30));
        this.username.setPreferredSize(new Dimension(25, 30));
        this.emContact.setPreferredSize(new Dimension(25, 30));
        this.password.setPreferredSize(new Dimension(25, 30));
        this.emContactPh.setPreferredSize(new Dimension(25, 30));
        this.acctTypes.setPreferredSize(new Dimension(250, 30));
    }

    /**
     * Clears the content if clicked
     */
    private void addClearListeners() {
        JTextField[] textFields = new JTextField[14];
        String[] textFieldTips  = {"First Name", "M.I.", "Last Name", "Street", "City", "ST", "Zip",
                "Primary Phone", "Secondary Phone", "Email Address", "Username", "Emergency Contact Name",
                "Password", "Emergency Contact Number"};

        textFields[0]  = this.firstName;
        textFields[1]  = this.middleInit;
        textFields[2]  = this.lastName;
        textFields[3]  = this.street;
        textFields[4]  = this.city;
        textFields[5]  = this.state;
        textFields[6]  = this.zip;
        textFields[7]  = this.primaryPhone;
        textFields[8]  = this.secondaryPhone;
        textFields[9]  = this.email;
        textFields[10] = this.username;
        textFields[11] = this.emContact;
        textFields[12] = this.password;
        textFields[13] = this.emContactPh;

        for (int i = 0; i < textFields.length; i++) {
            JTextField textField = textFields[i];
            String textFieldTip  = textFieldTips[i];

            textField.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (textField.getText().equals(textFieldTip)) {
                        textField.setText("");
                        textField.setToolTipText(textFieldTip);
                    }
                }

                public void mousePressed(MouseEvent e) {}
                public void mouseReleased(MouseEvent e) {}
                public void mouseEntered(MouseEvent e) {}
                public void mouseExited(MouseEvent e) {}
            });
        }
    }

    /**
     * Checks to see if the form is good
     * Inserts the employee into the database
     */
    private void addEmployeeListener() {
        this.addEmployee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] employee = new String[19];
                employee[0]  = EmployeeAccessView.this.firstName.getText();
                employee[1]  = EmployeeAccessView.this.middleInit.getText();
                employee[2]  = EmployeeAccessView.this.lastName.getText();
                employee[3]  = (String)EmployeeAccessView.this.gender.getSelectedItem();
                employee[4]  = EmployeeAccessView.this.street.getText();
                employee[5]  = EmployeeAccessView.this.city.getText();
                employee[6]  = EmployeeAccessView.this.state.getText();
                employee[7]  = EmployeeAccessView.this.zip.getText();
                employee[8]  = (String)EmployeeAccessView.this.dobMonth.getSelectedItem();
                employee[9]  = (String)EmployeeAccessView.this.dobDay.getSelectedItem();
                employee[10] = (String)EmployeeAccessView.this.dobYear.getSelectedItem();
                employee[11] = EmployeeAccessView.this.primaryPhone.getText();
                employee[12] = EmployeeAccessView.this.secondaryPhone.getText();
                employee[13] = EmployeeAccessView.this.email.getText();
                employee[14] = EmployeeAccessView.this.username.getText();
                employee[15] = EmployeeAccessView.this.password.getText();
                employee[16] = EmployeeAccessView.this.emContact.getText();
                employee[17] = EmployeeAccessView.this.emContactPh.getText();
                employee[18] = (String)EmployeeAccessView.this.acctTypes.getSelectedItem();

                EmployeeAccessController controller = new EmployeeAccessController();
                String check = controller.checkForm(employee);

                if (!check.equals("The following errors occurred: ")) {
                    EmployeeAccessView.this.message.setText(check);
                    return;
                }

                EmployeeAccessView.this.message.setText("");
                String added = controller.insertEmployee(employee);

                EmployeeAccessView.this.message.setText(added);
            }
        });
    }

    /**
     * Displays the form for adding the staff account.
     * @param type The employee type
     */
    public void displayAddStaffAccountForm(int type) {
        this.displayAddAdminAccountForm(1);
    }

    /**
     * Displays a form to search for a user to delete.
     *
     * @param type The type of user that's doing the deleting. 1 = staff; 2 = admin
     */
    public void displayDeleteAccountForm(int type) {
        this.display.resetToEmpty();

        this.addRemoveButtonListener(type);
        this.addClearEmailListener();

        this.layout.formatDisplayTitle(this.mainLabelPane);
        this.layout.formatMainButtons(this.removeButton);

        this.labelMain = new JLabel("Delete Account");
        this.labelMain.setFont(Config.title_font);
        this.mainLabelPane.add(this.labelMain);
        this.mainLabelPane.setPreferredSize(new Dimension(560, 0));

        // Add items to the panel
        JPanel panel = new JPanel(new MigLayout());
        panel.add(mainLabelPane, "span");
        panel.add(this.message, "span");

        this.email.setPreferredSize(new Dimension(25, 40));

        panel.add(this.email);
        panel.add(this.removeButton);

        this.display.add(panel);
    }

    /**
     * Removes the user from the database
     * @param type Privileges for deleting
     */
    private void addRemoveButtonListener(int type) {
        this.removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EmployeeAccessController controller = new EmployeeAccessController();
                String check = controller.checkFormForRemove(type, EmployeeAccessView.this.email.getText());

                if (!check.equals("The following errors occurred: ")) {
                    EmployeeAccessView.this.message.setText(check);
                    return;
                }

                String success = controller.removeEmployee(EmployeeAccessView.this.email.getText());

                EmployeeAccessView.this.message.setText(success);

                if (success.equals("Success")) {
                    EmployeeAccessView.this.removeButton.setEnabled(false);
                }
            }
        });
    }

    /**
     * Clears the email address from the delete account search
     */
    private void addClearEmailListener() {
        this.email.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (EmployeeAccessView.this.email.getText().equals("Email Address")) {
                    EmployeeAccessView.this.email.setText("");
                    EmployeeAccessView.this.email.setToolTipText("Email Address");
                }
            }

            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
        });
    }
}
package Model;

import Model.ModelInterface.EmployeeAccessModelInterface;

/**
 * Created by Dino Cajic on 10/29/2016.
 * Modified by Zachary Shoults on 11/04/2016.
 *
 * Gathers information from the database and performs other checks on code.
 */
public class EmployeeAccessModel implements EmployeeAccessModelInterface {

    /**
     * Checks the form to make sure no errors occurred
     * @param employee The employee being added to the database
     * @return String Message for why not successful
     */
    public String checkForm(String[] employee) {
        String errorMsg = "The following errors occurred: ";
                    
        if(employee[0].trim().equals(""))
            errorMsg += "\nFirst Name invalid";                   //= EmployeeAccessView.this.firstName.getText();
        if(employee[1].trim().equals(""))
            errorMsg += "\nMiddle Initial invalid";               //= EmployeeAccessView.this.middleInit.getText();
        if(employee[2].trim().equals(""))
            errorMsg += "\nLast Name invalid";                    //= EmployeeAccessView.this.lastName.getText();
        if(employee[3].trim().equals(""))
            errorMsg += "\nGender invalid";                       //= (String)EmployeeAccessView.this.gender.getSelectedItem();
        if(employee[4].trim().equals(""))
            errorMsg += "\nStreet Address invalid";               //= EmployeeAccessView.this.street.getText();
        if(employee[5].trim().equals(""))
            errorMsg += "\nCity Name invalid";                    //= EmployeeAccessView.this.city.getText();
        if(employee[6].trim().equals(""))
            errorMsg += "\nState Name invalid";                   //= EmployeeAccessView.this.state.getText();
        if(employee[7].trim().equals(""))
            errorMsg += "\nZip Code invalid";                     //= EmployeeAccessView.this.zip.getText();
        if(employee[11].trim().equals(""))
            errorMsg += "\nPhone Number invalid";                 //= EmployeeAccessView.this.primaryPhone.getText();
        if(employee[13].trim().equals(""))
            errorMsg += "\nEmail invalid";                        //= EmployeeAccessView.this.email.getText();
        if(employee[14].trim().equals(""))
            errorMsg += "\nUsername invalid";                     //= EmployeeAccessView.this.username.getText();
        if(employee[15].trim().equals(""))
            errorMsg += "\nPassword invalid";                     //= EmployeeAccessView.this.password.getText();
        if(employee[16].trim().equals(""))
            errorMsg += "\nEmergency Contact's Name invalid";     //= EmployeeAccessView.this.emContact.getText();
        if(employee[17].trim().equals(""))
            errorMsg += "\nEmergency Contatct's Phone invalid";   //= EmployeeAccessView.this.emContactPh.getText();
        if(!errorMsg.equals("The following errors occurred: "))
            return errorMsg;

        DatabaseModel dbm = new DatabaseModel();

        if(dbm.usernameExists(employee[14]))
            errorMsg += "\nUsername already exist.";
        if(dbm.emailExists(employee[13]))
            errorMsg += "\nEmail already in use.";

        return errorMsg;
    }

    /**
     * Adds the new employee to the database
     * @param employee The employee being added to the database
     * @return String Success or not
     */
    public String insertEmployee(String[] employee) {
        String fullName = employee[0].trim()+","+
                          employee[1].trim()+","+
                          employee[2].trim();

        String DOB = employee[8].trim()+","+
                     employee[9].trim()+","+
                     employee[10].trim();
        
        String[] user_info = new String[15];

        user_info[0] = employee[14].trim();  //username
        user_info[1] = employee[15].trim();  //password 1
        user_info[2] = employee[18].trim();  //access level - acct type
        user_info[3] = employee[13].trim();  //email address 3
        user_info[4] = fullName;             //first,middle,last
        user_info[5] = employee[4].trim();   //street 5
        user_info[6] = employee[5].trim();   //city
        user_info[7] = employee[6].trim();   //state 7
        user_info[8] = employee[7].trim();   //zip
        user_info[9] = employee[3].trim();   //gender 9
        user_info[10] = DOB;                 //dob
        user_info[11] = employee[11].trim(); //primary phone 11
        user_info[12] = employee[12].trim(); //secondary phone
        user_info[13] = employee[16].trim(); //emergency contact name 13
        user_info[14] = employee[17].trim(); //emergency phone

        for(String item : user_info){
            System.out.println(item);
        }

        // "Success" when all goes well, different message otherwise
        DatabaseModel dbm     = new DatabaseModel();
        return dbm.addAccount(user_info);
    }

    /**
     * Checks to see if the user can be removed and everything is good
     * @param type The admin level of user. 1 = admin, 2 = root admin
     * @param email The email address of the user to be removed
     * @return String Error message if any
     */
    public String checkFormForRemove(int type, String email) {
        System.out.println("Type is: "+type);
        String errorMsg = "The following errors occurred: ";

        if (type == 1) {
            errorMsg += "\nStaff users cannot erase accounts.";
            return errorMsg;
        }

        if (email.trim().equals("")) {
            errorMsg += "\nEnter an email address.";
        }

        DatabaseModel dbm   = new DatabaseModel();
        boolean emailExists = dbm.emailExists(email);

        if (!emailExists) {
            errorMsg += "\nNo account found with that email.";
            return errorMsg;
        }

        String username = dbm.getUsernameWithEmail(email);
        String access   = dbm.getUserAccessLevel(username);

        if (access.equalsIgnoreCase("admin") && type == 2) {
            errorMsg += "\nAdmins cannot delete other Admin accounts.";
        } else if (access.equalsIgnoreCase("root")) {
            errorMsg += "\nYou cannot delete the Root account.";
        }

        return errorMsg;
    }

    /**
     * Removes the employee from the database
     * @param employeeEmail The employee email to be removed
     * @return String Success or error
     */
    public String removeEmployee(String employeeEmail) {
        DatabaseModel dbm   = new DatabaseModel();
        boolean emailExists = dbm.emailExists(employeeEmail);

        if (!emailExists) {
            return "Email not found.";
        }

        String username = dbm.getUsernameWithEmail(employeeEmail);
        return dbm.deleteAccount(username);
    }
}
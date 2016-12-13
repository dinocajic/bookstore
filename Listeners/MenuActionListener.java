package Listeners;

import Config.Config;
import Controller.*;
import GUIobjects.CMPanel;
import GUIobjects.MenuPanel;
import GUIobjects.RightPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

/**
 * Created by Dino Cajic on 10/27/2016.
 *
 * These are the listeners that are used in the Menu Bar
 */
public class MenuActionListener implements ActionListener {

    CMPanel left;
    CMPanel display;
    CMPanel right;
    MenuPanel menu;
    RightPanel rightPanel;

    public MenuActionListener(CMPanel left, CMPanel display, CMPanel right, MenuPanel menu) {
        this.left    = left;
        this.display = display;
        this.right   = right;
        this.menu    = menu;
    }

    /**
     * @param left The Left Panel
     * @param display The display object that was instantiated in the ApplicationWindow class
     * @param right The Right CMPanel
     * @param menu The top menu
     * @param rightPanel The Right Panel
     */
    public MenuActionListener(CMPanel left, CMPanel display, CMPanel right, MenuPanel menu, RightPanel rightPanel) {
        this.left    = left;
        this.display = display;
        this.right   = right;
        this.menu    = menu;
        this.rightPanel = rightPanel;
    }

    /**
     * Makes the entire menu panel clickable
     * @param e The event
     */
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()) {
            case "Add New Book":
                this.resetLeftSide();
                AddBookToInventoryController addBookToInventoryController = new AddBookToInventoryController(this.display);
                addBookToInventoryController.displayAddBookPage();
                break;
            case "Modify Book Details/Qty":
                this.resetLeftSide();
                ModifyBookDetailsController modifyBookDetailsController = new ModifyBookDetailsController(this.display);
                modifyBookDetailsController.modifyBookDetails();
                break;
            case "Browse Books By Category":
                this.resetLeftSide();
                SearchForBooksController searchForBooksController = new SearchForBooksController(left, display, rightPanel);
                searchForBooksController.findBooks();
                break;
            case "Create New Administrator Account":
                this.resetLeftSide();
                EmployeeAccessController employeeAccessController = new EmployeeAccessController(this.display);
                employeeAccessController.displayAddAdminAccount(this.menu.currentlyLoggedIn);
                break;
            case "Create New Staff Account":
                this.resetLeftSide();
                EmployeeAccessController employeeAccessController1 = new EmployeeAccessController(this.display);
                employeeAccessController1.displayAddStaffAccount(this.menu.currentlyLoggedIn);
                break;
            case "Delete a Book":
                this.resetLeftSide();
                DeleteBookController deleteBookController = new DeleteBookController(this.display);
                deleteBookController.displayInitialScreen();
                break;
            case "Delete Administrator Account":
                this.resetLeftSide();
                EmployeeAccessController employeeAccessController2 = new EmployeeAccessController(this.display);
                employeeAccessController2.displayDeleteAccount(this.menu.currentlyLoggedIn);
                break;
            case "Delete Staff Account":
                this.resetLeftSide();
                EmployeeAccessController employeeAccessController3 = new EmployeeAccessController(this.display);
                employeeAccessController3.displayDeleteAccount(this.menu.currentlyLoggedIn);
                break;
            case "Generate Sales Report":
                this.resetLeftSide();
                GenerateReportController generateReportController1 = new GenerateReportController(this.display);
                generateReportController1.displaySalesReportSearch();
                break;
            case "<html><p style='margin:10;'>Homepage":
                HomepageController homepageController = new HomepageController(this.left, this.display, this.rightPanel);
                homepageController.displayHomepage();
                break;
            case "Issue Refund":
                this.resetLeftSide();
                PointOfSaleController pointOfSaleController = new PointOfSaleController(this.display);
                pointOfSaleController.displaySearchForCustomerOrder();
                break;
            case "<html><p style='margin:10;'>Log In":
                this.resetLeftSide();
                LoginController loginController = new LoginController(this.left, this.display, this.rightPanel, this.menu);
                loginController.displayInitialScreen();
                break;
            case "<html><p style='margin:10;'>Log Out":
                this.menu.updateUser(0);
                HomepageController homepageController2 = new HomepageController(this.left, this.display, this.rightPanel);
                homepageController2.displayHomepage();
                break;
            case "New Order":
                this.resetLeftSide();
                PlaceNewOrderController placeNewOrderController = new PlaceNewOrderController(this.display);
                placeNewOrderController.displayInitialScreen();
                break;
            case "New Sale":
                this.resetLeftSide();
                PointOfSaleController pointOfSaleController1 = new PointOfSaleController(this.display);
                pointOfSaleController1.displayInitialPOSscreen();
                break;
            case "Reset Password":
                this.resetLeftSide();
                LoginController loginController1 = new LoginController(this.left, this.display, this.rightPanel, this.menu);
                loginController1.displaySearchForUserForm();
                break;
            case "Accept Order Delivery":
            case "Search Orders":
                this.resetLeftSide();
                SearchForOrderController searchForOrderController = new SearchForOrderController(this.display);
                searchForOrderController.displayInitialScreen();
                break;
            case "View Company Information":
                this.resetLeftSide();
                AboutUsController aboutUsController = new AboutUsController(this.display);
                aboutUsController.displayAboutUsInformation();
                break;
            case "View Software Manual":
                this.resetLeftSide();
                this.displaySoftwareManual();
        }
    }

    /**
     * Reset the Left Panel to remove filtering checkboxes and restore the buttons to the default as
     * dictated in the Config settings.
     */
    private void resetLeftSide(){
        this.left.resetToEmpty();
        this.left.addLabelWithButtons("POPULAR GENRES", Config.leftPanelButtons, left, display, rightPanel);
        this.left.addLabelWithButtons("POPULAR AUTHORS", Config.authorsButtons, left, display, rightPanel);
    }

    /**
     * Open the web link to the software manual on the client's browser.
     */
    private void displaySoftwareManual(){
        try {
            Desktop.getDesktop().browse(new URL(Config.user_manual_link).toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
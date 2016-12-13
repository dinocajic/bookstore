package GUIobjects;

import Config.Config;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * Created by Zachary Shoults on 10/01/2016.
 * Modified by Dino Cajic on 10/27/2016.
 *
 * The MenuPanel is built based on the user that is currently logged in.  
 * It will be visible and fixed in the same position regardless of the other components of the Window.
 * For this reason, the MigLayout is hard-coded into the constructor.  The only parameter needed is the
 * user-type that is currently logged in.
 */

public class MenuPanel extends JPanel {

    /****************************************************************
     * IMPORTANT: ANY TIME YOU ADD A NEW JMenuItem                  *
     * YOU MUST ADD A LISTENER IN THE ApplicationWindow.java class  *
     * AND YOU MUST ADD A CASE IN THE MenuActionListener.java class *
     * **************************************************************
     */
    public JMenuItem logIn                  = formatMenuButton(new JMenuItem("<html><p style='margin:10;'>Log In"), "./src/img/icons/login.png");
    public JMenuItem logOut                 = formatMenuButton(new JMenuItem("<html><p style='margin:10;'>Log Out"), "./src/img/icons/login.png");
    public JMenuItem goToHomepage           = formatMenuButton(new JMenuItem("<html><p style='margin:10;'>Homepage"),"./src/img/icons/home.png");
    public JMenuItem resetPassword          = new JMenuItem("Reset Password");
    public JMenuItem viewCompanyInformation = new JMenuItem("View Company Information");
    public JMenuItem viewManual             = new JMenuItem("View Software Manual");
    public JMenuItem acceptOrderDelivery    = new JMenuItem("Accept Order Delivery");
    public JMenuItem newSale                = new JMenuItem("New Sale");
    public JMenuItem addNewBook             = new JMenuItem("Add New Book");
    public JMenuItem adjustABooksDetails    = new JMenuItem("Modify Book Details/Qty");
    public JMenuItem deleteABook            = new JMenuItem("Delete a Book");
    public JMenuItem newOrder               = new JMenuItem("New Order");
    public JMenuItem searchOrders           = new JMenuItem("Search Orders");
    public JMenuItem createNewStaffAccount  = new JMenuItem("Create New Staff Account");
    public JMenuItem deleteStaffAccount     = new JMenuItem("Delete Staff Account");
    public JMenuItem generateSalesReport    = new JMenuItem("Generate Sales Report");
    public JMenuItem issueRefund            = new JMenuItem("Issue Refund");
    public JMenuItem createNewAdminAccount  = new JMenuItem("Create New Administrator Account");
    public JMenuItem deleteAdminAccount     = new JMenuItem("Delete Administrator Account");

    public static int currentlyLoggedIn;
    public String currentUsernameLoggedIn   = "";

    private ArrayList<JMenuItem> fileOptions;
    private ArrayList<JMenuItem> posOptions;
    private ArrayList<JMenuItem> inventoryOptions;
    private ArrayList<JMenuItem> ordersOptions;
    private ArrayList<JMenuItem> adminOptions;
    private ArrayList<JMenuItem> reportsOptions;
    private ArrayList<JMenuItem> helpOptions;

    {   // Initialize the sizes for the menu items that will be buttons, not drop-downs
        logIn.setMaximumSize(new Dimension(85,50));
        logOut.setMaximumSize(new Dimension(90,50));
        goToHomepage.setMaximumSize(new Dimension(100,50));
    }

    /**
     * Create the original menu for an Anonymous user (zero)
     */
    public MenuPanel() {
        this.updateUser(0);
        this.setBackground(Config.menu_panel_color);
    }

    /**
     * Creates a menu button using the menuName parameter.  Under that button, the options in the 
     * given ArrayList populate the dropdown list.  
     * @param itemForButton The item that'll be displayed in the menu
     * @param image The image icon next to each item
     * @return Formatted menu item
     */
    private JMenuItem formatMenuButton(JMenuItem itemForButton, String image) {
        itemForButton.setFont(Config.default_font);
        itemForButton.setBackground(Config.menu_panel_color);
        itemForButton.setBorder(new MatteBorder(0, 0, 0, 1, new Color(200, 200, 200)));
        itemForButton.addMouseListener(new MouseListener(){
            public void mouseClicked(MouseEvent e) { }
            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}

            public void mouseEntered(MouseEvent e) {
                itemForButton.setBackground(Config.menu_hover_option);
                itemForButton.setBorder(new MatteBorder(1, 1, 0, 1, Color.gray));
            }

            public void mouseExited(MouseEvent e) {
                itemForButton.setBackground(Config.menu_panel_color);
                itemForButton.setBorder(new MatteBorder(0, 0, 0, 1, new Color(200, 200, 200)));
            }

        });

        if (image != null) {
            ImageIcon icon = new ImageIcon(image);
            itemForButton.setIcon(icon);
        }

        return itemForButton;
    }

    /**
     * Adds the necessary items to each menu item
     * @param menuName The name of the menu option
     * @param options The items that will go under the main menu option
     * @param separator To add a break between menu items
     * @param image To add an image
     * @return The menu
     */
    private JMenu addCategoryAndDropdown(String menuName, ArrayList<JMenuItem> options, JMenuItem separator, String image) {
        JMenu menu = new JMenu("<html><p style='margin:10;'>" + menuName);

        menu.setFont(Config.default_font);
        menu.addMouseListener(new MouseListener(){
            public void mouseClicked(MouseEvent e) { }
            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}

            public void mouseEntered(MouseEvent e) {
                menu.setSelected(true);
                menu.setBorder(new MatteBorder(1, 1, 0, 1, Color.gray)); //all but bottom
            }

            public void mouseExited(MouseEvent e) {
                menu.setSelected(false);
                if(!menuName.equals("Help"))
                    menu.setBorder(new MatteBorder(0, 0, 0, 1, new Color(200, 200, 200)));  //only right
                else
                    menu.setBorder(new MatteBorder(0, 0, 0, 0, new Color(200, 200, 200)));  //none for help
            }
        });

        if (image != null) {
            ImageIcon icon = new ImageIcon(image);
            menu.setIcon(icon);
        }

        if (!menuName.equals("Help")) {
            menu.setBorder(new MatteBorder(0, 0, 0, 1, new Color(200, 200, 200)));
        }else{
            menu.setBorder(new MatteBorder(0, 0, 0, 1, Config.menu_panel_color));
        }

        for (JMenuItem item : options) {
            item.setBackground(Config.menu_panel_color);
            item.setBorder(new EmptyBorder(10, 20, 10, 20));
            item.setFont(Config.default_font);
            menu.add(item);

            // Add separator to highlight "ROOT" user actions.
            if (item.equals(separator)) {
                menu.addSeparator();
            }
        }

        return menu;
    }

    /**
     * Used to update the menu bar for a particular user
     * @param userValue To be passed around to methods looking for what user is logged in (Delete account, etc)
     */
    public void updateUser(int userValue){
        currentlyLoggedIn = userValue;

        this.initializeDropdownCategories();

        switch(userValue) {
            case 1: // Staff User
                this.addOptionsForAnonymous();
                this.addOptionsForStaff();
                break;
            case 2: // Admin User
                this.addOptionsForAnonymous();
                this.addOptionsForStaff();
                this.addOptionsForAdmin();
                break;
            case 3: // Root User
                this.addOptionsForAnonymous();
                this.addOptionsForStaff();
                this.addOptionsForAdmin();
                this.addOptionsForRootAdmin();
                break;
            case 0: // Anonymous User
            default:
                this.currentUsernameLoggedIn = "";
                this.addOptionsForAnonymous();
        }

        JMenuBar menuBar = new JMenuBar();

        this.addCategoriesToDropDown(menuBar);
        this.createMenuBar(menuBar);
    }

    /**
     * Initializes the dropdown categories
     * Must be a flexible-sized data structure.
     * Arrays aren't optimal
     */
    private void initializeDropdownCategories() {
        this.fileOptions      = new ArrayList<>();
        this.posOptions       = new ArrayList<>();
        this.inventoryOptions = new ArrayList<>();
        this.ordersOptions    = new ArrayList<>();
        this.adminOptions     = new ArrayList<>();
        this.reportsOptions   = new ArrayList<>();
        this.helpOptions      = new ArrayList<>();
    }

    /**
     * Adds the menu options to the categories that need to be displayed for anonymous users.
     */
    private void addOptionsForAnonymous() {
        if (currentlyLoggedIn < 1) {
            this.fileOptions.add(goToHomepage);
            this.fileOptions.add(logIn);
        } else {
            this.fileOptions.add(goToHomepage);
        }

        this.helpOptions.add(viewCompanyInformation);
    }

    /**
     * Adds the menu options to the categories that need to be displayed for staff users.
     */
    private void addOptionsForStaff() {
        this.fileOptions.add(logOut);
        this.ordersOptions.add(acceptOrderDelivery);
        this.posOptions.add(newSale);
        this.helpOptions.add(viewManual);
    }

    /**
     * Adds the menu options to the categories that need to be displayed for admin users.
     */
    private void addOptionsForAdmin() {
        this.inventoryOptions.add(addNewBook);
        this.inventoryOptions.add(adjustABooksDetails);
        this.inventoryOptions.add(deleteABook);
        this.ordersOptions.add(newOrder);
        this.ordersOptions.add(searchOrders);
        this.adminOptions.add(resetPassword);
        this.adminOptions.add(deleteStaffAccount);
        this.adminOptions.add(createNewStaffAccount);
        this.reportsOptions.add(generateSalesReport);
        this.posOptions.add(issueRefund);
    }

    /**
     * Adds the menu options to the categories that need to be displayed for root admin users.
     */
    private void addOptionsForRootAdmin() {
        adminOptions.add(createNewAdminAccount);
        adminOptions.add(deleteAdminAccount);
    }

    /**
     * Adds the different categories to the menu bar
     */
    private void addCategoriesToDropDown(JMenuBar menuBar) {
        menuBar.setLayout(new MigLayout("", "", "14px!"));
        menuBar.setBackground(Config.menu_panel_color);

        menuBar.setForeground(Color.WHITE);
        menuBar.setBorder(new EmptyBorder(0, 0, 20, 0));

        if(this.fileOptions.size()>0) {
            for(JMenuItem option : this.fileOptions)
                 menuBar.add(option);
        }
        if(this.posOptions.size()>0) {
            menuBar.add(addCategoryAndDropdown("Sales",          this.posOptions,       null,                    "./src/img/icons/point-of-sale.png"));
        }
        if(this.inventoryOptions.size()>0) {
            menuBar.add(addCategoryAndDropdown("Inventory",      this.inventoryOptions, null,                    "./src/img/icons/inventory.png"));
        }
        if(this.ordersOptions.size()>0) {
            menuBar.add(addCategoryAndDropdown("Orders",         this.ordersOptions,    null,                    "./src/img/icons/orders.png"));
        }
        if(this.adminOptions.size()>0) {
            menuBar.add(addCategoryAndDropdown("Administration", this.adminOptions,     this.deleteStaffAccount, "./src/img/icons/admin.png"));
        }
        if(this.reportsOptions.size()>0) {
            menuBar.add(addCategoryAndDropdown("Reports",        this.reportsOptions,   null,                    "./src/img/icons/reports.png"));
        }
        if(this.helpOptions.size()>0) {
            menuBar.add(addCategoryAndDropdown("Help",           this.helpOptions,      null,                    "./src/img/icons/help-icon.png"));
        }
    }

    /**
     * Creates the Menu Bar and adds it to the Window
     * @param menuBar The top menu bar in the program
     */
    private void createMenuBar(JMenuBar menuBar) {
        //  Left-align | each column is as wide as window | each column is top-aligned, 50px tall at all times
        this.setLayout(new MigLayout("","[]:100sp:[]",""));
        this.removeAll();
        this.updateUI();
        this.add(menuBar);

        // CodeMonsters Logo in top right corner
        ImageIcon cmIcon = new ImageIcon(Config.cm_logo_50px);
        JLabel iconLabel = new JLabel("", cmIcon, JLabel.RIGHT);

        // Width, height. Icon==50x50 therefore 60x50 provides left margin
        iconLabel.setPreferredSize(new Dimension(60, 50));
        JLabel userNameLabel = new JLabel(currentUsernameLoggedIn, JLabel.LEFT);

        // Font-style, font-options, font-size
        userNameLabel.setFont(Config.logged_in_user_font);

        JPanel text_icon = new JPanel(new BorderLayout());

        // Text left side of div
        text_icon.add(userNameLabel, BorderLayout.WEST);

        // Icon right side of div
        text_icon.add(iconLabel, BorderLayout.EAST);

        // Transparent background
        text_icon.setOpaque(false);

        this.add(text_icon,"right");
        this.setOpaque(true);
    }
}
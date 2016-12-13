import Config.Config;
import Controller.HomepageController;
import GUIobjects.CMPanel;
import GUIobjects.MenuPanel;
import GUIobjects.RightPanel;
import Listeners.MenuActionListener;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * The main application window. This is what creates the initial layout.
 *
 * Created by Zachary Shoults on 10/01/2016.
 * Modified by Dino Cajic on 10/29/2016.
 */
public class ApplicationWindow {
    
    private final MigLayout WINDOW  = new MigLayout("wrap 3",
            "[200px!][:100sp:, grow, fill][250px:250px:250px, grow, fill]",
            "[top, 45px::]25[grow,fill][:100sp:,grow, fill]"
    );

    // A default layout that will allow horizontal size changes
    private final MigLayout LOGO    = new MigLayout("","[10sp::]","[grow,fill]");

    // Only one column of inputs, each fills width, 35px tall. 35px controls how much spacing between objects if it's bigger than height of objects
    private final MigLayout LEFT    = new MigLayout("wrap 1","[180px!, grow, fill]","[35px!]");

    // Same as LEFT but not controlling the height of the children
    private final MigLayout RIGHT   = new MigLayout("wrap 1","[]","[]");

    private final MigLayout DISPLAY = new MigLayout("wrap 1","[grow, fill]","[]");

    // Initializes the main frame with the company name in the title
    protected JFrame window         = new JFrame(Config.company_name);

    protected MenuPanel menu        = new MenuPanel();
    protected CMPanel logo          = new CMPanel(LOGO);
    protected CMPanel left          = new CMPanel(LEFT);
    protected CMPanel right         = new CMPanel(RIGHT);
    protected CMPanel display       = new CMPanel(DISPLAY);

    RightPanel rightPanel;

    // Cannot instantiate this before rightPanel is done being made
    HomepageController hpcontroller;

    /**
     * Runs the entire program
     */
    public ApplicationWindow(){
        // This needed to happen so that Right Panel can be altered from the Display Page
        this.rightPanel = new RightPanel(left, right, display);
        hpcontroller    = new HomepageController(left, display, rightPanel);

        // Sets the size  when taken out of full screen.
        window.setPreferredSize(Config.preferred_window_size);
        window.setMinimumSize(Config.minimum_window_size);

        // Open in full screen
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);

        ImageIcon frameIcon = new ImageIcon(Config.cm_logo_50px);
        window.setIconImage(frameIcon.getImage());

        // "config","columns","rows"
        window.setLayout(WINDOW);
        
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        
        logo.addImage(Config.company_logo);
        left.addLabelWithButtons("POPULAR GENRES", Config.leftPanelButtons, left, display, rightPanel);
        left.addLabelWithButtons("AUTHORS", Config.authorsButtons, left, display, rightPanel);

        // Display the right panel
        rightPanel.displayRightPanel();

        // Displays the homepage
        hpcontroller.displayHomepage();

        // Add the listeners to all menu buttons and the Search button
        // (Cannot do this before all CMPanels and RightPanel fully fleshed out above)
        this.addListeners();
        
        window.add(menu,"span 3");
        // Force the logo panel to fill the empty space along x-axis
        window.add(logo, "span 2, pushx, growx");

        // Force the right panel to fill the empty space below it
        window.add(right,"span 1 2, pushy, growy");

        // Automatically fills left so no need to give extra directions for placement
        window.add(left);

        // Automatically fills next to the left panel
        window.add(display);
        
        window.setVisible(true);
    }

    /**
     * Add the listeners for menu and search
     */
    private void addListeners() {
        this.menu.acceptOrderDelivery.addActionListener(new MenuActionListener(left, display, right, menu, rightPanel));
        this.menu.addNewBook.addActionListener(new MenuActionListener(left, display, right, menu, rightPanel));
        this.menu.adjustABooksDetails.addActionListener(new MenuActionListener(left, display, right, menu, rightPanel));
        this.menu.createNewAdminAccount.addActionListener(new MenuActionListener(left, display, right, menu, rightPanel));
        this.menu.createNewStaffAccount.addActionListener(new MenuActionListener(left, display, right, menu, rightPanel));
        this.menu.deleteABook.addActionListener(new MenuActionListener(left, display, right, menu, rightPanel));
        this.menu.deleteAdminAccount.addActionListener(new MenuActionListener(left, display, right, menu, rightPanel));
        this.menu.deleteStaffAccount.addActionListener(new MenuActionListener(left, display, right, menu, rightPanel));
        this.menu.goToHomepage.addActionListener(new MenuActionListener(left, display, right, menu, rightPanel));
        this.menu.generateSalesReport.addActionListener(new MenuActionListener(left, display, right, menu, rightPanel));
        this.menu.issueRefund.addActionListener(new MenuActionListener(left, display, right, menu, rightPanel));
        this.menu.logIn.addActionListener(new MenuActionListener(left, display, right, menu, rightPanel));
        this.menu.logOut.addActionListener(new MenuActionListener(left, display, right, menu, rightPanel));
        this.menu.newOrder.addActionListener(new MenuActionListener(left, display, right, menu, rightPanel));
        this.menu.newSale.addActionListener(new MenuActionListener(left, display, right, menu, rightPanel));
        this.menu.resetPassword.addActionListener(new MenuActionListener(left, display, right, menu, rightPanel));
        this.menu.searchOrders.addActionListener(new MenuActionListener(left, display, right, menu, rightPanel));
        this.menu.viewCompanyInformation.addActionListener(new MenuActionListener(left, display, right, menu, rightPanel));
        this.menu.viewManual.addActionListener(new MenuActionListener(left, display, right, menu, rightPanel));
    }
}
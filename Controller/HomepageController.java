package Controller;

import Controller.ControllerInterface.HomepageControllerInterface;
import GUIobjects.CMPanel;
import GUIobjects.RightPanel;
import Model.HomepageModel;
import View.HomepageView;

/**
 * Created by Dino on 11/3/2016.
 *
 * Directs the communication between the HomePageModel and HomePageView
 */
public class HomepageController implements HomepageControllerInterface {

    CMPanel left;
    CMPanel display;
    RightPanel right;

    /**
     * @param display The display object that was instantiated in the ApplicationWindow class
     * @param right The right panel that was instantiated in the ApplicationWindow class
     */
    public HomepageController(CMPanel left, CMPanel display, RightPanel right) {
        this.left    = left;
        this.display = display;
        this.right   = right;
    }

    /**
     * Gets the most popular books from the HomepageModel class.
     * Displays the books on the homepage.
     */
    public void displayHomepage() {
        HomepageModel model = new HomepageModel();
        String[][] books = model.getHomepageBooks();

        HomepageView view = new HomepageView(this.left, this.display, this.right);
        view.displayHomepage(books);
    }
}
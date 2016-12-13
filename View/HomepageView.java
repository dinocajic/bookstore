package View;

import Controller.SearchForBooksController;
import GUIobjects.CMPanel;
import GUIobjects.RightPanel;
import View.ViewInterface.HomepageViewInterface;

/**
 * Created by Dino on 11/3/2016.
 *
 * Displays the homepage
 */
public class HomepageView implements HomepageViewInterface {

    CMPanel left;
    CMPanel display;
    RightPanel right;

    /**
     * @param display The display object that was instantiated in the ApplicationWindow class
     * @param right The right panel that was instantiated in the ApplicationWindow class
     */
    public HomepageView(CMPanel left, CMPanel display, RightPanel right) {
        this.left    = left;
        this.display = display;
        this.right   = right;
    }

    /**
     * Displays the homepage
     * @param books The most popular books that need to be displayed on the homepage
     */
    public void displayHomepage(String[][] books) {
        SearchForBooksController controller = new SearchForBooksController(this.left, this.display, this.right);
        controller.displayResults(books, true);
    }
}
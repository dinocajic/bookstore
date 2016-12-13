package Controller;

import Controller.ControllerInterface.DetailsPageControllerInterface;
import GUIobjects.CMPanel;
import GUIobjects.RightPanel;
import Model.DetailsPageModel;
import View.DetailsPageView;

/**
 * Created by Dino Cajic on 11/3/2016.
 *
 * Directs the communication between the DetailsPageModel and DetailsPageView
 */
public class DetailsPageController implements DetailsPageControllerInterface {

    CMPanel left;
    CMPanel display;
    RightPanel right;

    /**
     * @param display The display object that was instantiated in the ApplicationWindow class
     * @param right The right panel that was instantiated in the ApplicationWindow class
     */
    public DetailsPageController(CMPanel left, CMPanel display, RightPanel right) {
        this.left = left;
        this.display = display;
        this.right   = right;
    }

    /**
     * Displays the details page about a particular book.
     * Retrieves the details about the book from the model.
     * @param isbn The ISBN of the book
     */
    public void displayDetailsPage(String isbn) {
        DetailsPageModel model = new DetailsPageModel();
        String[] book = model.getBook(isbn);

        DetailsPageView view = new DetailsPageView(this.left, this.display, this.right);
        view.displayPage(book);
    }
}
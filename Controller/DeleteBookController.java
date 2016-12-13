package Controller;

import Controller.ControllerInterface.DeleteBookControllerInterface;
import GUIobjects.CMPanel;
import Model.DeleteBookModel;
import View.DeleteBookView;

/**
 * Created by Dino Cajic on 10/29/2016.
 *
 * Directs the communication between the DeleteBookView and the DeleteBookModel
 */
public class DeleteBookController implements DeleteBookControllerInterface {

    CMPanel display;

    public DeleteBookController() {}

    /**
     * @param display The display object that was instantiated in the ApplicationWindow class
     */
    public DeleteBookController(CMPanel display) {
        this.display = display;
    }

    /**
     * Once the Delete Book button is clicked in the menu bar, the displayForm calls the LoginView to
     * display the initial screen.
     */
    public void displayInitialScreen() {
        DeleteBookView view = new DeleteBookView(this.display);
        view.displayDeleteBookForm();
    }

    /**
     * Calls the DatabaseModel to remove a book.
     * @param isbn The ISBN of the book
     * @return String Errors if any
     */
    public String removeBook(String isbn) {
        DeleteBookModel model = new DeleteBookModel();
        return model.removeBook(isbn);
    }

    /**
     * Calls the DeleteBookModel to search for a book.
     * @param isbn The ISBN of the book
     * @return Object[][] Book details that need to be displayed.
     */
    public Object[][] searchForBooks(String isbn) {
        DeleteBookModel model = new DeleteBookModel();
        return model.searchForBooks(isbn);
    }

    /**
     * Checks to make sure that the search field is not empty.
     * @param isbn The ISBN that's entered into the search field.
     * @return boolean : True if empty
     */
    public boolean checkFormFields(String isbn) {
        DeleteBookModel model = new DeleteBookModel();
        return model.checkFormFields(isbn);
    }
}
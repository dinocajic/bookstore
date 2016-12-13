package Controller;

import Controller.ControllerInterface.AddBookToInventoryControllerInterface;
import GUIobjects.CMPanel;
import Model.AddBookToInventoryModel;
import View.AddBookToInventoryView;


/**
 * Created by Dino Cajic on 10/27/2016.
 *
 * Directs the communication between the AddBookToInventoryModel and AddBookToInventoryView
 */
public class AddBookToInventoryController implements AddBookToInventoryControllerInterface {

    CMPanel display;

    public AddBookToInventoryController() {}

    /**
     * @param display The display object that was instantiated in the ApplicationWindow class
     */
    public AddBookToInventoryController(CMPanel display) {
        this.display = display;
    }

    /**
     * Calls the AddBookToInventoryView to display the main Add Book form page.
     */
    public void displayAddBookPage() {
        AddBookToInventoryView view = new AddBookToInventoryView(display);
        view.displayView();
    }

    /**
     * Calls the AddBookToInventoryModel to check the form fields.
     * Returns the results from the Model.
     * @param book The book array that needs to be inserted into the database.
     * @return String : Message stating the errors that were encountered.
     */
    public String checkFormFields(String[] book) {
        AddBookToInventoryModel model = new AddBookToInventoryModel();
        return model.checkFormFields(book);
    }

    /**
     * Calls the AddBookToInventoryModel to insert the book into the database
     * @param book The book array that needs to be inserted into the database.
     * @return String : A message stating whether the insert was a success or not.
     */
    public String insertBook(String[] book, String imageFilePath) {
        AddBookToInventoryModel model = new AddBookToInventoryModel();
        return model.insertBook(book, imageFilePath);
    }

    /**
     * Adds an image
     * @return image
     */
    public String addImageFromFileExplorer() {
        AddBookToInventoryModel model = new AddBookToInventoryModel();
        return model.addImageFromFileExplorer();
    }
}
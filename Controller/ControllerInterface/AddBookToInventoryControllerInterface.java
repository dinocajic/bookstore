package Controller.ControllerInterface;

/**
 * Created by Dino Cajic on 10/27/2016.
 *
 * To be implemented in the AddBookToInventoryController class
 */
public interface AddBookToInventoryControllerInterface {

    /**
     * Instantiates the AddBookToInventoryView and calls the displayView method.
     */
    void displayAddBookPage();

    /**
     * Calls the AddBookToInventoryModel to check the form fields.
     * Returns the results from the Model.
     * @param book The book array that needs to be inserted into the database.
     * @return String : Message stating the errors that were encountered.
     */
    String checkFormFields(String[] book);

    /**
     * Calls the AddBookToInventoryModel to insert the book into the database.
     * @param book The book array that needs to be inserted into the database.
     * @param imageFilePath The relative path of the book's image for display.
     * @return String : A message stating whether the insert was a success or not.
     */
    String insertBook(String[] book, String imageFilePath);
}

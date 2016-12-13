package Controller.ControllerInterface;

/**
 * Created by Dino Cajic on 10/29/2016.
 *
 * To be used in the DeleteABookController
 */
public interface DeleteBookControllerInterface {

    /**
     * Instantiates the DeleteABookView and calls the displayView method.
     */
    void displayInitialScreen();

    /**
     * Calls the DatabaseModel to remove a book.
     * @param isbn The ISBN of the book
     * @return String Errors if any
     */
    String removeBook(String isbn);

    /**
     * Calls the DeleteBookModel to search for a book.
     * @param isbn The ISBN of the book
     * @return Object[][] Book details that need to be displayed.
     */
    Object[][] searchForBooks(String isbn);

    /**
     * Checks to make sure that the search field is not empty.
     * @param isbn The ISBN that's entered into the search field.
     * @return boolean : True if empty
     */
    boolean checkFormFields(String isbn);
}
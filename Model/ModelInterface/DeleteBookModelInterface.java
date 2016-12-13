package Model.ModelInterface;

/**
 * Created by Dino Cajic on 10/29/2016.
 *
 * To be implemented in DeleteBookModel
 */
public interface DeleteBookModelInterface {

    /**
     * Calls the DatabaseModel to search for books. Updates the data property in the DeleteBookView class
     * @return String
     */
    String removeBook(String isbn);

    /**
     * Accesses the DatabaseModel and searches for the book.
     * Modifies the DeleteBookView data property
     */
    Object[][] searchForBooks(String isbn);

    /**
     * Checks to see if the isbn is empty
     * @param isbn The isbn that's entered into the search field
     * @return boolean : True if empty
     */
    boolean checkFormFields(String isbn);
}
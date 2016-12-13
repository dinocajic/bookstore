package Controller.ControllerInterface;

/**
 * Created by Dino Cajic on 10/29/2016.
 *
 * To be implemented in the ModifyBookDetailsController
 */
public interface ModifyBookDetailsControllerInterface {

    /**
     * Display Modify Book Details Form
     */
    void modifyBookDetails();

    /**
     * Display Adjust Book Details Form
     */
    void adjustBookQty();

    /**
     * Checks to see if the form that was filled out is empty
     * @param isbn The ISBN of the book
     * @return boolean : True if empty field
     */
    boolean checkIfSearchFormEmpty(String isbn);

    /**
     * Retrieves the book details
     * @param isbn The ISBN of the book
     * @return String[] book details or null if no results.
     */
    String[] getBook(String isbn);

    /**
     * Checks to see if any errors occurred on entry, such as not selecting the appropriate fields.
     * @param book The book array
     * @return String Error message if any
     */
    String checkFormFields(String[] book);

    /**
     * Applies the updated changes
     * @param book The book array
     * @return String success message
     */
    String insertBookChanges(String[] book, String newImagePath);
}
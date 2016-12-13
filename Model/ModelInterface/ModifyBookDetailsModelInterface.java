package Model.ModelInterface;

/**
 * Created by Dino Cajic on 10/29/2016.
 *
 * To be implemented in ModifyBookDetailsModel
 */
public interface ModifyBookDetailsModelInterface {

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
     * Since the check is identical, I just utilized the AddBookToInventoryModel class
     * @param book The book array that needs to be checked
     * @return String error message if any
     */
    String checkFormFields(String[] book);

    /**
     * Since the adding of the book to the inventory is the same as AddBookToInventory, I just utilized that class
     * @param book The details of the book
     * @param newImagePath the relative path to the book's image
     * @return String success message
     */
    String insertBookChanges(String[] book, String newImagePath);
}
package Model.ModelInterface;

/**
 * Created by Dino Cajic on 10/27/2016.
 *
 * To be implemented in AddBookToInventoryModel
 */
public interface AddBookToInventoryModelInterface {

    /**
     * Checks to make sure the user didn't forget to insert any of the required fields
     * @param book The book array that needs to be inserted into the database.
     */
    String checkFormFields(String[] book);

    /**
     * Calls the DatabaseModel class to insert the book array into the database
     * @param book The book array that needs to be inserted into the database.
     * @param imageFilePath The location of the book's image.
     * @return String : The DatabaseModel returns a message stating whether the insert was a success.
     */
    String insertBook(String[] book, String imageFilePath);
}
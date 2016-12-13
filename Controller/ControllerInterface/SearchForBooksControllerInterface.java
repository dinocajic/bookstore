package Controller.ControllerInterface;

/**
 * Created by Dino Cajic on 10/29/2016.
 *
 * To be implemented in the SearchForBookController
 */
public interface SearchForBooksControllerInterface {

    /**
     * Search for the books and display them
     * @param null
     */
    void findBooks();

    /**
     * Calls the model to search for the books
     * @param keyword  The keyword that the person either entered or clicked on
     * @return String[] The results that are found in the database
     */
    String[][] processSearch(String keyword);

    /**
     * Once the results are returned, display them
     * @param results The results are found in the database
     */
    void displayResults(String[][] results, boolean preventCheckboxes);
}
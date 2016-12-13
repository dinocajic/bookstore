package View.ViewInterface;

/**
 * Created by Dino Cajic on 10/29/2016.
 *
 * To be implemented in the SearchForBooksView
 */
public interface SearchForBooksViewInterface {

    /**
     * Displays the results.
     *
     * @param results The books that are retrieved from the database
     */
    void displayResults(String[][] results, String[] genresOfBooks, boolean preventCheckboxes);
}
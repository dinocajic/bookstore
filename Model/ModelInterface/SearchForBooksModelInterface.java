package Model.ModelInterface;

/**
 * Created by Dino Cajic on 10/29/2016.
 *
 * To be implemented in SearchForBooksModel
 */
public interface SearchForBooksModelInterface {

    /**
     * Gets the books from the database
     * @param keyword The keyword to search
     * @return String[][] Books
     */
    String[][] getBooks(String keyword);

    /**
     * Find all the different genres amongst the result set of books.
     * Used to populate the checkboxes for filtering.
     * @return Genres
     */
    String[] getFilterGenres();

    /**
     * Show the books from the full result set that contain the genres the
     * user has checked.
     * @return books to be displayed
     */
    String[][] generateDisplayResultsFromGenres();
}
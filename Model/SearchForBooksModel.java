package Model;

import Model.ModelInterface.SearchForBooksModelInterface;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Dino Cajic on 10/29/2016.
 * Modified by Zachary Shoults on 11/04/2016.
 *
 * Gathers information from the database and performs other checks on code.
 */
public class SearchForBooksModel implements SearchForBooksModelInterface {

    // Keep copy of the full result set to modify with filter
    public static ArrayList<String[]> fullResultSet = new ArrayList<>();
    public static ArrayList<String> genreFilters    = new ArrayList<>();
    public static boolean newSearchOccured          = false;

    /**
     * Gets the books from the database
     * @param keyword The keyword to search
     * @return String[][] Books
     */
    public String[][] getBooks(String keyword) {
        fullResultSet                = new ArrayList<>();
        DatabaseModel dbm            = new DatabaseModel();
        ArrayList<String[]> dbmBooks = dbm.getBooks(keyword);

        if(dbmBooks.size() == 0) {
            String condensed_keyword = "";

            if (keyword.trim().contains(" ")) {
                String[] exploded = keyword.split(" ");

                for (String term : exploded) {
                    if (!term.trim().equals("")) {
                        condensed_keyword += term + " ";
                    }
                }

                dbmBooks.addAll(dbm.getBooks(condensed_keyword.trim()));
            }
        }

        // 0:isbn  1:title  2:author  3:price  4:genres  5:summary  6:stock  7:cost  8:image
        String[][] books = new String[dbmBooks.size()][8];

        for (int book = 0; book < dbmBooks.size(); book++) {
            books[book][0] = (dbmBooks.get(book)[1].length()>20)? dbmBooks.get(book)[1].substring(0,19): dbmBooks.get(book)[1]; // Book title
            books[book][1] = (dbmBooks.get(book)[2].length()>20)? dbmBooks.get(book)[2].substring(0,19): dbmBooks.get(book)[2]; // Author
            books[book][2] = dbmBooks.get(book)[0];      // ISBN
            books[book][3] = (dbmBooks.get(book)[5].length()>20)? dbmBooks.get(book)[5].substring(0,19): dbmBooks.get(book)[5]; // Short Summary
            books[book][4] = dbmBooks.get(book)[3];      // Price of item
            books[book][5] = dbmBooks.get(book)[6];      // Qty in stock
            books[book][6] = dbmBooks.get(book)[8];      // Image Location
            books[book][7] = dbmBooks.get(book)[4];      // GENRE, not used in the display result, but kept for filtering

            fullResultSet.add(books[book]);
        }

        // Performed a new search, means new checkboxes to filter with
        newSearchOccured = true;

        return books;
    }

    /**
     * Find all the different genres amongst the result set of books.
     * Used to populate the checkboxes for filtering.
     * @return Genres
     */
    public String[] getFilterGenres() {
        if (fullResultSet.size() == 0) {
            return null; // Only do this for a result set of decent size.
        }

        genreFilters                        = new ArrayList<>();
        HashMap<String, Integer> genreCount = new HashMap<>();

        for(String[] book : fullResultSet) {
            String[] bkGenres = book[7].split(",");

            for (String genre : bkGenres) {
                if (genreCount.containsKey(genre)) {
                    Integer currCount = genreCount.get(genre) + 1;
                    genreCount.put(genre, currCount);
                } else {
                    genreCount.put(genre, 1);
                }
            }
        }

        String[] genreReturn = new String[8];
        Object[] temp        = genreCount.keySet().toArray();

        int index = 0;
        for(Object genre : temp){
            genreReturn[index] = (String) temp[index];
            index += 1;

            // Only want maximum 8 genres for checkboxes
            if(index > 7) {
                break;
            }
        }

        return genreReturn;

    }

    /**
     * Show the books from the full result set that contain the genres the
     * user has checked.
     * @return books to be displayed
     */
    public String[][] generateDisplayResultsFromGenres() {
        ArrayList<String[]> displayedResultSet = new ArrayList<>();
        String[][] booksForDisplay;

        if (genreFilters.size() != 0) {
            for (String genre : genreFilters) {
                for (String[] book : fullResultSet) {
                    if (book[7].contains(genre) && !displayedResultSet.contains(book)) {
                        displayedResultSet.add(book);
                    }
                }
            }

            booksForDisplay = new String[displayedResultSet.size()][8];

            for (int i = 0; i < displayedResultSet.size(); i++) {
                booksForDisplay[i] = displayedResultSet.get(i);
            }

        } else {
            booksForDisplay = new String[fullResultSet.size()][8];

            for (int i = 0; i < fullResultSet.size(); i++) {
                booksForDisplay[i] = fullResultSet.get(i);
            }
        }

        return booksForDisplay;
    }
}
package Model;

import Model.ModelInterface.HomepageModelInterface;

/**
 * Created by Dino on 11/3/2016.
 * Modified by Zachary Shoults on 11/04/2016.
 *
 * Gathers information from the database and performs other checks on code.
 */
public class HomepageModel implements HomepageModelInterface {

    /**
     * Gets the home page books
     * @return String[][] Books
     */
    public String[][] getHomepageBooks() {
        SearchForBooksModel model = new SearchForBooksModel();
        return model.getBooks("fiction");
    }
}
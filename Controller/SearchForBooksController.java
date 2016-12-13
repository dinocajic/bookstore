package Controller;

import Controller.ControllerInterface.SearchForBooksControllerInterface;
import GUIobjects.CMPanel;
import GUIobjects.RightPanel;
import Model.SearchForBooksModel;
import View.SearchForBooksView;

/**
 * Created by Dino Cajic on 10/29/2016.
 *
 * Directs the communication between the SearchForBooksView and the SearchForBooksModel
 */
public class SearchForBooksController implements SearchForBooksControllerInterface {

    CMPanel left;
    CMPanel display;
    RightPanel right;

    /**
     * @param left The left panel
     * @param display The display object that was instantiated in the ApplicationWindow class
     * @param right The right panel
     */
    public SearchForBooksController(CMPanel left, CMPanel display, RightPanel right) {
        this.left = left;
        this.right = right;
        this.display = display;
    }

    /**
     * Search for the books and display them
     */
    public void findBooks() {
        String searchTerms = this.right.searchText.getText();  //this is used instead of parameter, for now 11-13-2016
        String[][] results = this.processSearch(searchTerms);
        this.displayResults(results, false);
    }

    /**
     * Calls the model to search for the books
     * @param keyword  The keyword that the person either entered or clicked on
     * @return String[] The results that are found in the database
     */
    public String[][] processSearch(String keyword) {
        SearchForBooksModel model = new SearchForBooksModel();
        return model.getBooks(keyword);
    }

    /**
     * Once the results are returned, display them
     * @param results The results are found in the database
     */
    public void displayResults(String[][] results, boolean preventCheckboxes) {
        SearchForBooksModel model = new SearchForBooksModel();
        SearchForBooksView view   = new SearchForBooksView(this.left, this.display, this.right);

        if (SearchForBooksModel.newSearchOccured) {
            // getFilterGenres() clears the filter list being applied to the current set of books being displayed.
            // Therefore we only want this method to be called if the 'results' parameter is from a brand-new search.
            // Otherwise checkboxes engaged during a new search will be remembered and cause "No-results" displays
            String[] genresOfResults = model.getFilterGenres();
            view.displayResults(results, genresOfResults, preventCheckboxes);
        } else {
            view.displayResults(results, null, preventCheckboxes);
        }
    }
}
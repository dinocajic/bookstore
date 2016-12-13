package View.ViewInterface;

/**
 * Created by Dino Cajic on 11/3/2016.
 *
 * To be implemented in DetailsPageView
 */
public interface DetailsPageViewInterface {

    /**
     * Displays the details page about a particular book
     * @param book The ISBN of a particular book
     */
    void displayPage(String[] book);
}
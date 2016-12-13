package View.ViewInterface;

/**
 * Created by Dino on 11/3/2016.
 *
 * To be implemented in the HomepageView
 */
public interface HomepageViewInterface {

    /**
     * Displays the homepage
     * @param books The most popular books that need to be displayed on the homepage
     */
    void displayHomepage(String[][] books);
}
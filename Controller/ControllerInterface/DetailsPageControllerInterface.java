package Controller.ControllerInterface;

/**
 * Created by Dino Cajic on 11/3/2016.
 *
 * To be implemented in DetailsPageController
 */
public interface DetailsPageControllerInterface {

    /**
     * Displays the details page about a particular book.
     * Retrieves the details about the book from the model.
     * @param isbn The ISBN of the book
     */
    void displayDetailsPage(String isbn);
}
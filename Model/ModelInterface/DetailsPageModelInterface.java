package Model.ModelInterface;

/**
 * Created by Dino Cajic on 11/3/2016.
 *
 * To be implemented in DetailsPageModel
 */
public interface DetailsPageModelInterface {

    /**
     * Retrives the book based on the isbn
     * @param isbn The ISBN of the book
     * @return String[] book details
     */
    String[] getBook(String isbn);
}
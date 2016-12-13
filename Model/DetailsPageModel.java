package Model;

import Model.ModelInterface.DetailsPageModelInterface;

/**
 * Created by Dino Cajic on 11/3/2016.
 * Modified by Zachary Shoults on 11/04/2016.
 *
 * Gathers information from the database and performs other checks on code.
 */
public class DetailsPageModel implements DetailsPageModelInterface {

    /**
     * Retrives the book based on the isbn
     * @param isbn The ISBN of the book
     * @return String[] book details
     */
    public String[] getBook(String isbn) {
        DatabaseModel dbm = new DatabaseModel();
        String[] dbBook   = dbm.getBookByISBN(isbn);

        if (dbBook == null){
            String[] emptyBook = {"ISBN not found","","","","","","","",""};
            return emptyBook;
        }

        String[] book = new String[9];

        book[0] = dbBook[1];                        // Book title
        book[1] = dbBook[2];                        // Author
        book[2] = dbBook[0];                        // ISBN
        book[3] = dbBook[5].substring(0, 42)+"..."; // Short Summary
        book[4] = dbBook[5];                        // Long Summary
        book[5] = "$" + dbBook[3];                  // Price of item
        book[6] = dbBook[6];                        // Qty in stock
        book[7] = dbBook[8];                        // Image Location
        book[8] = "1996";                           // Year

        return book;
    }
}
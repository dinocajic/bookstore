package Model;

import Model.ModelInterface.ModifyBookDetailsModelInterface;

/**
 * Created by Dino Cajic on 10/29/2016.
 * Modified by Zachary Shoults on 11/04/2016.
 *
 * Gathers information from the database and performs other checks on code.
 */
public class ModifyBookDetailsModel implements ModifyBookDetailsModelInterface {

    private static String currentBookImagePath;  // Static to persist for different methods
    
    /**
     * Checks to see if the form that was filled out is empty
     * @param isbn The ISBN of the book
     * @return boolean : True if empty field
     */
    public boolean checkIfSearchFormEmpty(String isbn) {
        return isbn.equals("");
    }

    /**
     * Retrieves the book details
     * @param isbn The ISBN of the book
     * @return String[] book details or null if no results.
     */
    public String[] getBook(String isbn) {
        DatabaseModel dbm = new DatabaseModel();
        String[] dbBook   = dbm.getBookByISBN(isbn);

        if (dbBook == null){
            return null;
        }

        // Genres/categories in order of primary to subcategories
        String[] genres = dbm.getBookCategories(isbn);
        String[] book   = new String[14];
        
        book[0]  = dbBook[1];
        book[1]  = dbBook[2];
        book[2]  = dbBook[0];
        book[3]  = (dbBook[5].length()>45)? dbBook[5].substring(0,44):dbBook[5];
        book[4]  = dbBook[5];
        book[5]  = genres[0]; // main genre/category. Adding a book requires at least one main category and one subcategory
        book[6]  = (genres.length>2)? genres[2]: "Any";
        book[7]  = (genres.length>3)? genres[3]: "Any";
        book[8]  = genres[1]; // secondary genre/category. Adding a book requires at least one main category and one subcategory
        book[9]  = (genres.length>4)? genres[4]: "Any";
        book[10] = (genres.length>5)? genres[5]: "Any";
        book[11] = dbBook[6];
        book[12] = dbBook[3]; // price
        book[13] = dbBook[7]; // cost

        currentBookImagePath = dbBook[8];

        return book;
    }

    /**
     * Since the check is identical, I just utilized the AddBookToInventoryModel class
     * @param book The book array that needs to be checked
     * @return String error message if any
     */
    public String checkFormFields(String[] book) {
        AddBookToInventoryModel model = new AddBookToInventoryModel();
        return model.checkFormFields(book);
    }

    /**
     * Since the adding of the book to the inventory is the same as AddBookToInventory, I just utilized that class
     * @param book The details of the book
     * @param newImagePath the relative path of the book's image.
     * @return String success message
     */
    public String insertBookChanges(String[] book, String newImagePath) {
        String [] dbBook = new String[9]; 
        //DB needs : String[] of   isbn, title, author (comma separated string), price,
        //                         genre (comma separated string), summary, stock, cost   ?image
        String genres = book[5].trim(); //build the genre string from the selections on GUI

        for (int x = 6; x <= 10; x++) {
            if (!book[x].equalsIgnoreCase("any")) {
                genres += "," + book[x];
            }
        }
        DatabaseModel dbm     = new DatabaseModel();
        dbBook[0] = book[2].trim();     //isbn
        dbBook[1] = book[0].trim();     //title
        dbBook[2] = book[1].trim();     //authors
        dbBook[3] = book[12].trim();    //price 
        dbBook[4] = genres;             //genres/categories
        dbBook[5] = book[4].trim();     //summary
        dbBook[6] = book[11].trim();    //number books added (Stock)
        dbBook[7] = book[13].trim();    //cost
        dbBook[8] = newImagePath==null ? dbm.getBookByISBN(book[2].trim())[8] : newImagePath;
 

        String database_reply = dbm.updateBook(dbBook);

        if (database_reply.equalsIgnoreCase("Book successfully updated.")) {
            return "Success";
        } else {
            return database_reply;
        }
    }
}
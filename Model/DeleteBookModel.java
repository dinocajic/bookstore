package Model;

import Model.ModelInterface.DeleteBookModelInterface;

/**
 * Created by Dino Cajic on 10/29/2016.
 *
 * Gathers information from the database and performs other checks on code.
 */
public class DeleteBookModel implements DeleteBookModelInterface {

    /**
     * Calls the DatabaseModel to search for a book
     * @return String
     */
    public String removeBook(String isbn) {
        DatabaseModel dbm     = new DatabaseModel();
        String database_reply = dbm.deleteBook(isbn);

        if (database_reply.equals("Book successfully deleted.")){
            return "Success";
        } else {
            return database_reply;
        }
    }

    /**
     * Accesses the DatabaseModel and searches for the book.
     * @param isbn The isbn of the book
     */
    public Object[][] searchForBooks(String isbn) {
        DatabaseModel dbm = new DatabaseModel();
        String[] book     = dbm.getBookByISBN(isbn);

        // Avoids null-pointer exceptions in eventListeners
        Object[][] data   = {{"","","","","","",""}};

        if (book == null){  // getBookByISBN() will return null if no book of that isbn is found
            data[0][1] = "No results";
            return data;
        }

        int num_ordered = dbm.getBookOnOrderQty(isbn);

        data[0][0] = book[1];
        data[0][1] = book[0];
        data[0][2] = book[6];
        data[0][3] = num_ordered;
        data[0][4] = book[3];
        data[0][5] = "Remove";

        return data;
    }

    /**
     * Checks to see if the isbn is empty
     * @param isbn The isbn that's entered into the search field
     * @return boolean : True if empty
     */
    public boolean checkFormFields(String isbn) {
        return isbn.equals("");
    }
}
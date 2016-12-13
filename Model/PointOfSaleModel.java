package Model;

import Model.ModelInterface.PointOfSaleModelInterface;

import java.util.ArrayList;

/**
 * Created by Dino Cajic on 10/29/2016.
 * Modified by Zachary Shoults on 11/04/2016.
 *
 * Gathers information from the database and performs other checks on code.
 */
public class PointOfSaleModel implements PointOfSaleModelInterface {

    /**
     * Returns the book. Search by ISBN.
     * @param isbn The isbn of the book
     * @return String[] book
     */
    public Object[][] getBook(String isbn) {
        Object[][] book   = {{"","","","","",""}};
        DatabaseModel dbm = new DatabaseModel();
        String[] dbBook   = dbm.getBookByISBN(isbn);

        if (dbBook == null){
            book[0][1] = "ISBN not found";
            return book;
        }

        Integer dbBookOrdered = dbm.getBookOnOrderQty(isbn);
        
        book[0][0] = dbBook[1];
        book[0][1] = dbBook[0]; // ISBN
        book[0][2] = dbBook[6]; // Qty in stock
        book[0][3] = dbBookOrdered.toString();
        book[0][4] = dbBook[3]; // Price
        book[0][5] = "Add";

        return book;
    }

    /**
     * Checks to see if the Credit Card is good
     * @param ccNumber The credit card number
     * @param ccName The name on the credit card
     * @param ccCode The Security code on the back of credit card
     * @return boolean true if ok, false otherwise
     */
    public boolean checkCard(String ccNumber, String ccName, String ccCode) {
        ccNumber.replace(" ", "");      //no spaces between numbers i.e. '2345 2345 2345 2345'
        ccNumber.replace("-", "");      //no dashes between numbers i.e. '2345-2345-2345-2345'
        return ccNumber.length() == 16; //to parse the last 4 digits in other methods, need .substr(8)
    }

    /**
     * Adds the book to the database
     * @param addedItems ISBNs of books
     * @param ccNumber The CC Number. Only save last 4
     * @param ccName The CC Name.
     * @param isCreditCardPayment True if person payed with credit card
     * @param orderNumber The order number of the order
     * @return String Success
     */
    public String insertSale(ArrayList<String> addedItems, String ccNumber, String ccName, boolean isCreditCardPayment, String orderNumber) {
        DatabaseModel dbm   = new DatabaseModel();
        int numberItems     = addedItems.size();
        String[] updateSale = new String[3];

        if (numberItems > 0) {
            updateSale[0] = orderNumber;
            updateSale[1] = addedItems.get(0);
            updateSale[2] = "1";
        }

        // Create Sale
        String[] sale = dbm.createSale(updateSale);

        if (sale == null) {
            System.out.println("ISBN not found: " + updateSale[1]);
            return "Failure";
        }

        dbm.reduceBookStock(updateSale[1],1);

        for (int i = 1; i < numberItems; i++){
            updateSale[0] = orderNumber;
            updateSale[1] = addedItems.get(i);
            updateSale[2] = "1";

            // Add each item to sale
            sale = dbm.updateSale(sale, updateSale);

            if (sale == null) {
                System.out.println("ISBN not found: " + updateSale[1]);
                return "Failure";
            }

            dbm.reduceBookStock(updateSale[1], 1);
        }

        String subtotal = dbm.getSaleSubtotal(sale);

        String paymentInfo = (isCreditCardPayment)? subtotal + " " + ccName + " cc: **** **** **** " + ccNumber.substring(12) : subtotal + " cash";
        dbm.addPaymentToSale(sale, paymentInfo, System.currentTimeMillis());

        return "Success";
    }

    /**
     * Checks to see if order exists by that order number
     * @param orderNumber The order number of the order
     * @return Returns the order number
     */
    public String getOrder(String orderNumber) {
        DatabaseModel dbm = new DatabaseModel();
        return dbm.saleID_Exists(orderNumber)? orderNumber : null;
    }

    /**
     * Issues the refund
     * @param orderNumber The order number of the order
     * @return String Success if successful
     */
    public String issueRefund(String orderNumber) {
        DatabaseModel dbm = new DatabaseModel();

        // The full string containing the payment details from the specific sale
        String refundAmount = dbm.issueRefund(orderNumber);
        return refundAmount != null ? "Success" : "Order number was not found.";
    }

    /**
     * Gets a list of books from the database based on the isbns
     * @param isbns The isbns of multiple books
     * @return Object[][] Books
     */
    public Object[][] getBooks(ArrayList<String> isbns) {
        Object[][] book   = new Object[1][6];
        DatabaseModel dbm = new DatabaseModel();

        if (isbns.size() == 0){
            book[0][1] = "No books selected";
            return book;
        }

        book = new Object[isbns.size()][6];

        Integer bookNum = 0;

        for(String isbn : isbns){
            String[] dbBook       = dbm.getBookByISBN(isbn);
            Integer dbBookOrdered = dbm.getBookOnOrderQty(isbn);

            book[bookNum][0] = dbBook[1];
            book[bookNum][1] = dbBook[0]; // ISBN
            book[bookNum][2] = dbBook[6]; // Qty in stock
            book[bookNum][3] = dbBookOrdered.toString();
            book[bookNum][4] = dbBook[3]; // Price
            book[bookNum][5] = "Add";

            ++bookNum;
        }

        return book;
    }
}
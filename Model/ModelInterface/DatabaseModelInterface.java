package Model.ModelInterface;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by: Dino Cajic on 10/24/2016.
 * Modified by: Zachary Shoults on 10/26/2016.
 *
 * Interface class for use with DatabaseModel class
 * I've started off the markup. This is basic. Please finish up with the following:
 * - Add additional methods
 * - Add parameters or modify parameters
 * - Modify the return type. If we're returning String[]'s, then leave as is, otherwise, modify to appropriate data structure.
 */
public interface DatabaseModelInterface {

    /**
     * Provides an array of multiple books.
     *
     * @param keyword : The keyword that a user may type in or click on such as clicking on a particular category.
     * @return String[] of multiple books.
     */
    public ArrayList<String[]> getBooks(String keyword);

    /**
     * Provide book details for particular book based on keyword.
     *
     * @param keyword : The keyword that a user may type in or click on such as clicking on a particular category.
     * @return String[] details of 1 book.
     */
    public String[] getBookByISBN(String isbn);

    /**
     * Retrieves the book's name.
     *
     * @param id : The id of the book in the JSON file.
     * @return String book name.
     */
    public String getBookName(String isbn);

    /**
     * Retrieve the book's authors as an array.
     *
     * @param id : The id of the book in the JSON file.
     * @return String[] authors.
     */
    public String[] getAuthor(String isbn);

    /**
     * Retrieves the book's description.
     *
     * @param id     : The id of the book in the JSON file.
     * @param length : The number of characters to return.
     *               i.e. for a short description, enter 100. For a full description, enter -1.
     * @return String description.
     */
    public String getDescription(String isbn, int length);

    /**
     * Retrieves the book's category(s)
     *
     * @param id : The id of the book in the JSON file.
     * @return String[] category(s) of the book.
     */
    public String[] getBookCategories(String isbn);

    /**
     * Retrieves the book's sub-category(s)
     *
     * @param id : The id of the book in the JSON file.
     * @return String[] sub-category(s) of the book.
     */
    public String[] getBookSubCategories(String isbn);

    /**
     * Retrieves the number of books currently in stock for the particular book id.
     *
     * @param id : The id of the book in the JSON file.
     * @return int number of books currently in stock.
     */
    public Integer getBookInStockQty(String isbn);

    /**
     * Retrieves the number of books currently on order for the particular book id.
     *
     * @param id : The id of the book in the JSON file.
     * @return int number of books currently on order.
     */
    public int getBookOnOrderQty(String isbn);

    /**
     * Add new book to current in store inventory.
     *
     * @param book : The details for the particular book
     * @return String. If added, return empty string, otherwise return message why it couldn't be added.
     */
    public String addBook(String[] book);

    /**
     * Updates any detail of the book.
     *
     * @param book : The new book details.
     * @return String. If update was successful, return empty string, otherwise return message why it couldn't be updated.
     */
    public String updateBook(String[] book);

    /**
     * Deletes a book from in store inventory.
     *
     * @param id : The id of the book
     * @return String. If deleted successfully, return empty string, otherwise return message why it couldn't be deleted.
     */
    public String deleteBook(String isbn);

    /**
     * Updates the in-stock inventory for a particular book.
     *
     * @param id : The id of the book.
     * @param qty : The new qty of the book.
     * @return String. If updated, return empty string, otherwise return message why it couldn't be updated.
     */
    public String updateBookStock(String isbn, int qty);

    /**
     * Update the existing stocking order.
     *
     * @param orderId : The order id for the particular order.
     * @param books : The ISBN's and qty's.
     * @return String[]. If all of the items were updated, return empty string, otherwise,
     *                   return the ISBN's that need to be added to the inventory first.
     */
    public String[] updateOrder(String[] order, String[] Id_ISBN_Quant);

    /**
     * Delete an order.
     *
     * @param orderId : The id of the order.
     * @return String. If deleted, return empty string, otherwise, return why the order couldn't be deleted.
     */
    public String deleteOrder(String orderID);

    /**
     * Retrieve all of the active orders.
     *
     * @return String[]. The active orders and their content.
     */
    public ArrayList<String[]> getActiveOrders();

    /**
     * Get the active orders between a date range.
     *
     * @param start : The start date.
     * @param end : The end date.
     * @return String[]. The active orders and their content.
     */
    public String[] getActiveOrders(Date start, Date end);

    /**
     * Get the orders that have been added to the inventory already.
     *
     * @return String[]. The previous orders and their content.
     */
    public String[] getPreviousOrders();

    /**
     * Get the orders that have been added to the inventory between a specific date range.
     *
     * @param start : The start date.
     * @param end : The end date.
     * @return String[]. The previous orders and their content.
     */
    public String[] getOrderDateRange(Long start, Long end);

    /**
     * Get the content of an order.
     *
     * @param orderId : The id of a particular order.
     * @return String[]. Contents of a particular order.
     */
    public String[] getOrder(String orderID);

    /**
     * Add an employee to the system.
     *
     * @param employee : Details about the employee.
     * @return String. If added, return empty string, otherwise, return details as to why the employee couldn't be added.
     */
    public String addAccount(String[] employee);

    /**
     * Update existing employee details.
     *
     * @param employee : Employee details.
     * @return String. If updated, return empty string, otherwise, return details as to why the employee couldn't be updated.
     */
    public String updateAccount(String[] employee);

    /**
     * Delete an employee from the system. Not really deleted, just marked as inactive.
     *
     * @param employeeId : The id of a particular employee.
     * @return String. If deleted, return empty string, otherwise, return details as to why the employee couldn't be deleted.
     */
    public String deleteAccount(String username);  

    /**
     * Retrieve all of the employees, active and inactive.
     *
     * @return String[]. Details about each employee.
     */
    public ArrayList<String[]> getAllAccounts();

    /**
     * Get details about a particular employee.
     *
     * @param id : The employee id.
     * @return String[]. Details about the employee.
     */
    public String[] getAccount(String username);

    /**
     * Attempt to login into the system.
     *
     * @param username : The username that a user entered.
     * @param password : The password that a user entered.
     * @return String. If successful, return empty string, otherwise, return reason as to why the user couldn't log in.
     */
    public String attemptLogin(String username, String password);

    /**
     * Update a user password.
     *
     * @param id : The user id.
     * @param newPassword : The new password for the user.
     * @return String. If successful, return empty string, otherwise, return why the new password couldn't be updated.
     */
    public String updatePassword(String username, String newPassword);

    /**
     * Create a new sale in the system.
     *
     * @param books : The books that will be added to it.
     * @return String. Return id of new sale if successful, otherwise, return message as to why it couldn't be added.
     */
    public String[] createSale(String[] Id_ISBN_Quant_Date);

    /**
     * Adds the payment details to the sale.
     *
     * @param saleId : The id of the sale.
     * @param paymentDetails : The payment details of the customer. i.e. CC, Name, etc.
     * @return String. If payment successfully appended to sale, return sales id, otherwise, return details as to why
     *                 the payment details couldn't be added.
     */
    public String addPaymentToSale(String[] sale, String paymentDetails, long dateMillis);

    /**
     * Updates how many books
     *
     * @param saleId : The id of the sale
     * @param details : The details that need to be updated. I.e. qty, price, payment, etc. If qty 0, remove item from sale.
     * @return String. If update was success, return id of sale, otherwise, return the reason as to why it wasn't successful.
     */
    public String[] updateSale(String[] sale, String[] Id_ISBN_Quant);

    /**
     * Marks the status of a sale as "refunded"
     *
     * @param saleId : The id of a sale.
     * @return String. If refund was a success, return empty string, otherwise, return the details as to why the refund
     *                 was unsuccessful.
     */
    public String issueRefund(String saleId);

    /**
     * Return all of the sales between a particular date range. Doesn't necessarily need to return details about everything.
     *
     * @param start : The starting date.
     * @param end : The ending date.
     * @return String[]. Sales details between the date range.
     */
    public ArrayList<String[]> getSalesReport(Date start, Date end);
}
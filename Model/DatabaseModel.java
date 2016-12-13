package Model;

import Config.Config;
import Model.ModelInterface.DatabaseModelInterface;
import java.io.*;
import java.util.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Date;

/**
 * Created by Dino Cajic on 10/23/2016.
 * Modified by Zachary Shoults on 10/26/2016.
 *
 * Copy your InventoryManager class here
 */
public class DatabaseModel implements DatabaseModelInterface {

    private String INVENTORY_FILEPATH          = Config.INVENTORY_FILEPATH;  //the location of the inventory file
    private String ORDERS_FILEPATH             = Config.ORDERS_FILEPATH;     //the location of the orders file
    private String SALES_FILEPATH              = Config.SALES_FILEPATH;
    private String LOG_IN_CREDENTIALS_FILEPATH = Config.LOG_IN_CREDENTIALS_FILEPATH; //location of user's log-in info

    private LinkedList<JSONObject> inventory_contents = new LinkedList<>(); //inventory in memory for processing
    private LinkedList<JSONObject> orders_contents = new LinkedList<>();
    private LinkedList<JSONObject> users_contents     = new LinkedList<>();
    private LinkedList<JSONObject> sales_contents     = new LinkedList<>();

    private Scanner s = new Scanner(System.in);
    
    public DatabaseModel(){}
    /**
    * Helper Method to append items to the end of a file.  Can be used
    * for either Inventory, Orders, or Users
    * @param fileName
    * @param toAppend  TRUE = append ||| FALSE = write from beginning of file
    * @return 
    */
    private PrintWriter openFileToEdit(String fileName, boolean toAppend){
       PrintWriter pw = null;
       try{
           FileWriter fw = new FileWriter(fileName, toAppend);
           BufferedWriter bw = new BufferedWriter(fw);
           pw = new PrintWriter(bw);
           return pw;
       } catch (IOException ex) {
           ex.printStackTrace();
           System.out.println("File was not found at: "+ fileName);
           return pw; 
       }
    }
    
    
    
    
/*****************************************************************************
                                INVENTORY
*****************************************************************************/
   
    private String[] convertJsonBookToStringArray(JSONObject JSONBook){
        //ISBN, Title, Author, Price, Genre, Summary, Stock, Cost
        String[] book = {        
            (String)JSONBook.get(BookSchema.ISBN),
            (String)JSONBook.get(BookSchema.TITLE),
            (String)JSONBook.get(BookSchema.AUTHOR),
            (String)JSONBook.get(BookSchema.PRICE),
            (String)JSONBook.get(BookSchema.GENRE),
            (String)JSONBook.get(BookSchema.SUMMARY),
            (String)JSONBook.get(BookSchema.STOCK),
            (String)JSONBook.get(BookSchema.COST),
            (String)JSONBook.get(BookSchema.IMAGE)
            };        
        return book;
    }
    
    /**
     * Helper Method for converting a book as a String[] to JSON.
     * --> If the schema for our books changes, this code must change <--
     * @param book
     * @return 
     */
    private JSONObject convertStringArrayToJsonBook(String[] book){ //public for testing
        JSONObject JSONBook = new JSONObject();
        JSONBook.put(BookSchema.ISBN, book[0]);
        JSONBook.put(BookSchema.TITLE, book[1]);
        JSONBook.put(BookSchema.AUTHOR, book[2]);
        JSONBook.put(BookSchema.PRICE, book[3]);
        JSONBook.put(BookSchema.GENRE, book[4]);
        JSONBook.put(BookSchema.SUMMARY, book[5]);
        JSONBook.put(BookSchema.STOCK, book[6]);
        JSONBook.put(BookSchema.COST, book[7]);
        JSONBook.put(BookSchema.IMAGE, book[8]);
        return JSONBook;
    }
       
    /**
     * Internal method to get inventory into the LinkedList.  Only used within
     * other methods that query the inventory.
     */
    public void readInventoryToMemory(){  //public only for testing        
        inventory_contents = new LinkedList<>();  //empty
        try (Scanner file = new Scanner(new File(INVENTORY_FILEPATH))) {
            JSONParser jsonParser = new JSONParser();
            while(file.hasNextLine()){
                JSONObject x = (JSONObject)jsonParser.parse(file.nextLine());
                inventory_contents.add(x);
            }
        }catch(FileNotFoundException ex) {
            ex.printStackTrace();
            System.out.println("Inventory file not found at: "+this.INVENTORY_FILEPATH);
        }catch(ParseException ex){
            ex.printStackTrace();
            System.out.println("Parse exception reading inventory file. "
                    + "An entry may not be in correct \"JSON\" format.");
        }
    } 

    /**
     * A test for if a book's ISBN is in the inventory
     * @param isbn
     * @return 
     */
    public boolean isBookInInventory(String isbn){  //public for testing
        this.readInventoryToMemory();  
        for(JSONObject book : inventory_contents){
            if(book.get(BookSchema.ISBN).equals(isbn))
                return true;
        }
        return false;
    }
    
    /**
     * Find every book containing the parameter in any value of the key-value 
     * pairs of the inventory listing.  
     * @param keyword
     * @return 
     */
    @Override
    public ArrayList<String[]> getBooks(String keyword) {
        ArrayList<JSONObject> JSONmatches = new ArrayList<>();
        this.readInventoryToMemory();
        for(JSONObject book : inventory_contents){
            String currBook = book.toJSONString();
            if(currBook.toLowerCase().contains(keyword.toLowerCase())){
                    JSONmatches.add(book);
                    continue;
                }
            }
        ArrayList<String[]> results = new ArrayList<>();
        JSONmatches.stream().forEach((match)->{
            results.add(convertJsonBookToStringArray(match));
        });
        return results;
    }
    
    /**
     * Retrieve the inventory listing as it appears in the inventory, converted
     * to a JSON object instead of its JSONString in the file.  A private method
     * to facilitate editing/updating data.
     * @param ISBN
     * @return 
     */
    private JSONObject getBookJsonByISBN(String ISBN){
        readInventoryToMemory();
        for(JSONObject book : inventory_contents){
            String currBookISBN = (String) book.get(BookSchema.ISBN);
            if(currBookISBN.equalsIgnoreCase(ISBN)){
                return book;
            }
        }
        return null;
    }
    
    /**
     * Returns the book in String[]-form for use in other models.
     * @param isbn
     * @return 
     */
    @Override
    public String[] getBookByISBN(String isbn){
        JSONObject jBook = getBookJsonByISBN(isbn);
        if(jBook==null)
            return null;
        return this.convertJsonBookToStringArray(jBook);
    }
   
    /**
     * Get the String value stored in the JSONObject for the provided key.  
     * @param isbn
     * @param bookSchemaConstant
     * @return 
     */
    private String getBookValue(String isbn, String bookSchemaConstant){
        JSONObject jsonBook = getBookJsonByISBN(isbn);
        if(jsonBook==null)
            return null;    //isbn not valid, no match
        if(!BookSchema.FULL_BOOK_SCHEMA_LIST.contains(bookSchemaConstant))
            return null;    //if the key parameter is not valid, return null
        String value = (String) jsonBook.get(bookSchemaConstant);
        return value;
    }
    
    /**
     * Returns the title of the book with the given ISBN passed as a string.
     * @param isbn
     * @return 
     */
    @Override
    public String getBookName(String isbn) {
        String title = getBookValue(isbn, BookSchema.TITLE);
        if(title!=null)
            return title;
        return "Book not found.  Check the ISBN.";  //title == null => bad isbn
    }

    /**
     * Returns all authors credited for a book with the given ISBN.
     * @param isbn
     * @return 
     */
    @Override
    public String[] getAuthor(String isbn) {
        String authors = getBookValue(isbn, BookSchema.AUTHOR);
        if(authors!=null)
            return authors.split(",");
        return null;  //authors == null => bad isbn
    }    

    /**
     * Get a full or partial summary of the book.  If the partial summary length
     * requested is longer than the full summary, the entire summary will be returned.
     * @param isbn
     * @param length
     * @return 
     */
    @Override
    public String getDescription(String isbn, int length) {
        String description = getBookValue(isbn, BookSchema.SUMMARY);
        if(description!=null){
            if(description.length()>=length)
                return description;    
            return description.substring(0, length-1);
        }
        return "Book not found.  Check the ISBN.";
    }

    /**
     * All categories of the book are returned in a list ordered with most-significant
     * category in the 0th index. 
     * @param isbn
     * @return 
     */
    @Override
    public String[] getBookCategories(String isbn) {
        String categories = getBookValue(isbn, BookSchema.GENRE);
        if(categories!=null)
            return categories.split(",");
        return null;  //categories == null => bad isbn
    }

    /**
     * All categories except for the primary category are returned.  If the categories
     * were "Fiction, Drama, Teen, Fantasy" where "Fiction" was the primary category,
     * the return would be ["Drama", "Teen", "Fantasy"]
     * @param isbn
     * @return 
     */
    @Override
    public String[] getBookSubCategories(String isbn) {
        String[] allCategories  = getBookCategories(isbn);
        if(allCategories!=null){
            String[] subCategories = new String[allCategories.length-1];
            for(int x=1; x<allCategories.length; x++)
                subCategories[x-1] = allCategories[x];
            return subCategories;
        }
        return null;            
    }

    /**
     * Returns the stock quantity if the book is in the inventory.  
     * @param isbn
     * @return non-negative integer if the book is in the inventory. Returns -1
     * if the id does not match any book in the inventory.
     */
    @Override
    public Integer getBookInStockQty(String isbn) {
        String stock = getBookValue(isbn, BookSchema.STOCK);
        if(stock!=null)
            return Integer.parseInt(stock);
        return null;  //stock == null => bad isbn
    }
    
    /**
     * Assumes the parameter is in this order:
     * ISBN, Title, Author, Price, Genre, Summary, Stock, Cost
     * The values for AUTHOR and GENRE must be separated by an agreed-upon delimiter:
     * for this example, I use a comma ","
     * @param book
     * @return 
     */
    @Override
    public String addBook(String[] book) {
        if(isBookInInventory(book[0])) //check ISBN to make sure the book is update-able
            return "This book is already in the inventory. Check the ISBN.";
        PrintWriter pw = openFileToEdit(this.INVENTORY_FILEPATH, true);
        if(pw==null)
            return "Unable to access inventory. Book not added.";
        JSONObject JSONBook = this.convertStringArrayToJsonBook(book);
        try{
            pw.write(JSONBook.toJSONString()+"\n");
            return("Book successfully added.");    //DONT CHANGE THIS TEXT, CHECKED ELSEWHERE .equals()
        }finally{
        pw.close();
        }
    }
           
    /**
     * Takes a valid, fully filled-out book[], converts it to JSONObject,
     * and adds it to the inventory file, replacing the book in the file that
     * shares the same ISBN.  If no book is in the inventory with that ISBN, 
     * no changes are made to the file.
     * @param book
     * @return 
     */
    @Override
    public String updateBook(String[] book) {
        ArrayList<JSONObject> modifiedInventory = new ArrayList<>();
        if(!isBookInInventory(book[0])) //check ISBN to make sure the book is update-able
            return "Book not found in inventory. Check ISBN.";
        inventory_contents.stream().forEach((JSONbook)-> {
            String currBookISBN = (String)JSONbook.get(BookSchema.ISBN);
            if(currBookISBN.equalsIgnoreCase(book[0])){
                JSONObject updated = this.convertStringArrayToJsonBook(book);
                modifiedInventory.add(updated);
            }else{
                modifiedInventory.add(JSONbook);
            }      
        });
        PrintWriter pw = this.openFileToEdit(this.INVENTORY_FILEPATH, false);
        if(pw==null)
            return "Unable to access inventory. Book not updated.";
        try{
            for(JSONObject JSONbook : modifiedInventory){
                pw.write(JSONbook.toJSONString()+"\n");
            }
            return "Book successfully updated.";  //DONT CHANGE THIS TEXT, CHECKED ELSEWHERE .equals()
        }finally{
            pw.close();
        }
    }

    /**
     * Finds the book with the given ISBN in the inventory and re-writes the file
     * except for that specific book.
     * @param isbn
     * @return 
     */
    @Override
    public String deleteBook(String isbn) {
        if(!isBookInInventory(isbn))
            return "Book not found in inventory. Check ISBN.";
        PrintWriter pw = this.openFileToEdit(this.INVENTORY_FILEPATH, false);
        if(pw==null)
            return "Unable to access inventory. Book not deleted.";
        try{
            inventory_contents.stream().forEach((JSONbook)-> {
                if(JSONbook.get(BookSchema.ISBN).equals(isbn)){
                    try{
                        File file = new File((String)JSONbook.get(BookSchema.IMAGE));
                        if(file.delete()){
                            //erase the book's image
                        }else{
                            System.out.println("Delete operation is failed.");
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }else{
                    pw.write(JSONbook.toJSONString()+"\n");
                } 
            });
            return "Book successfully deleted.";    //DONT CHANGE THIS TEXT, CHECKED ELSEWHERE .equals()
        }finally{
            pw.close();
        }
    }
    
    /**
     * Finds the book with the given ISBN and rewrites the inventory to reflect
     * the change in that specific book's quantity.
     * @param isbn
     * @param qty
     * @return 
     */
    @Override
    public String updateBookStock(String isbn, int qty) {
        if(!isBookInInventory(isbn)) //check ISBN to make sure the book is update-able
            return "Book not found in inventory. Check ISBN.";
        PrintWriter pw = this.openFileToEdit(this.INVENTORY_FILEPATH, false);
        if(pw==null)
            return "Unable to access inventory. Book quantity not updated.";
        try{
            inventory_contents.stream().forEach((JSONbook)-> {
                String currBookISBN = (String) JSONbook.get(BookSchema.ISBN);
                if(currBookISBN.equalsIgnoreCase(isbn)){
                    String[] currBook = this.convertJsonBookToStringArray(JSONbook);
                    currBook[6] = Integer.toString(qty);
                    JSONObject updated = this.convertStringArrayToJsonBook(currBook);
                    pw.write(updated.toJSONString()+"\n");
                }else{
                    pw.write(JSONbook.toJSONString()+"\n");
                } 
            });
            return "Book quantity successfully updated.";
        }finally{
            pw.close();
        }
    }
    
    /**
     * Reduce the book's stock by the given amount.  Will not reduce
     * below zero.
     * @param isbn
     * @param qty
     * @return 
     */
    public String reduceBookStock(String isbn, int qty) {
        if(!isBookInInventory(isbn)) //check ISBN to make sure the book is update-able
            return "Book not found in inventory. Check ISBN.";
        PrintWriter pw = this.openFileToEdit(this.INVENTORY_FILEPATH, false);
        if(pw==null)
            return "Unable to access inventory. Book quantity not updated.";
        try{
            inventory_contents.stream().forEach((JSONbook)-> {
                String currBookISBN = (String) JSONbook.get(BookSchema.ISBN);
                if(currBookISBN.equalsIgnoreCase(isbn)){
                    String[] currBook = this.convertJsonBookToStringArray(JSONbook);
                    int updatedStockQuantity = Integer.parseInt(currBook[6])-qty;
                    if(updatedStockQuantity < 0)
                        updatedStockQuantity = 0;
                    currBook[6] = Integer.toString(updatedStockQuantity);
                    JSONObject updated = this.convertStringArrayToJsonBook(currBook);
                    pw.write(updated.toJSONString()+"\n");
                }else{
                    pw.write(JSONbook.toJSONString()+"\n");
                } 
            });
            return "Book quantity successfully updated.";
        }finally{
            pw.close();
        }
    }
    
    /**
     * Returns all books in the inventory that have the given String as a part of
     * the book's value in the "Genre":"value" key-value pair
     * @param category
     * @return 
     */
    public ArrayList<String[]> findBooksByCategory(String category){
        this.readInventoryToMemory();
        ArrayList<String[]> matches = new ArrayList<>();
        for(JSONObject book : inventory_contents){
            String categories = (String) book.get(BookSchema.GENRE); //"a,b,c,d"
            if(categories.toLowerCase().contains(category.toLowerCase()))
                matches.add(this.convertJsonBookToStringArray(book));
        }
        return matches;
    }
   
    
    
    
    
    
    
    
    
    
    
    
    
    
    
///----------------------START SALES DATABASE METHODS------------------------///
/*A saleItem consists of:
    SaleID,
    Book ISBN,
    Book Quantity.
    
    String[] firstItem = {"12345", "222333888", "1"};
    JSONsale = {"SaleID":"12345", "Books":"{...};{...};{...}", "DateSold":"1478055689088"};
    
    
*/    
    //=======================SALES' HELPER METHODS=======================//
    private void readSalesToMemory(){  //public only for testing
        sales_contents = new LinkedList<>();  //empty
        try (Scanner file = new Scanner(new File(SALES_FILEPATH))) {
            JSONParser jsonParser = new JSONParser();
            while(file.hasNextLine()){
                String nextLine = file.nextLine();
                if(nextLine.trim().equalsIgnoreCase("")){continue;}
                JSONObject x = (JSONObject)jsonParser.parse(nextLine);
                sales_contents.add(x);
            }
        }catch(FileNotFoundException ex) {
            ex.printStackTrace();
            System.out.println("Inventory file not found at: "+this.INVENTORY_FILEPATH);
        }catch(ParseException ex){
            ex.printStackTrace();
            System.out.println("Parse exception reading inventory file. "
                    + "An entry may not be in correct \"JSON\" format.");
        }
    } 
    
    /**
     * Save all changes made to the 'sales_contents' LinkedList by writing over
     * the current Sales file entirely.
     */
    private void updateSalesFile(){
        PrintWriter pw = openFileToEdit(this.SALES_FILEPATH, false);
        try{           
            for(JSONObject sale : sales_contents){
                pw.write(sale.toJSONString()+"\n");
            }
        }finally{
            pw.close();
        }
    }
        
    /**
     * Returns if saleID already exists or not.
     * @param saleId
     * @return 
     */
    public boolean saleID_Exists(String saleId){
        readSalesToMemory();
        for(JSONObject sale : sales_contents){
            String currSaleID = (String) sale.get(SaleSchema.SALEID);
            if(currSaleID.equalsIgnoreCase(saleId.trim())){
                return true;
            }
        }
        return false;
    }
    
    
    private String[] convertJsonSaleToStringArray(JSONObject sale){
        String[] saleAry = new String[3];       
        saleAry[0] = (String) sale.get(SaleSchema.SALEID);
        JSONObject[] jBooks = getBooksFromSale((String) sale.get(SaleSchema.SALEID));
        String books = "";
        int x = 0;
        double subtotal = 0d;
        for(JSONObject jBook : jBooks){
            String bookISBN = (String) jBook.get(BookSchema.ISBN);
            String bookQuan = (String) jBook.get(BookSchema.STOCK);
            String bookPric = (String) jBook.get(BookSchema.PRICE);
            String bookTitle = (String) jBook.get(BookSchema.TITLE);
            books += ("ISBN: "+ bookISBN + "   Title: " + bookTitle +"   Quantity: "+ bookQuan);
            if(x != jBooks.length-1)
                books+="\n";
            x++;
            subtotal += (Integer.parseInt(bookQuan) * Double.parseDouble(bookPric));
        }
        saleAry[1] = books;
        String subtotalSTR = String.format("%.2f", subtotal);
        saleAry[2] = subtotalSTR;
        return saleAry;        
    }     
    
    /**
     * To start a sale, enter a book's ISBN, quantity, and provide a saleID.
     * This will create a sale instance and return it.  Use it to feed into 
     * the "updateSale()" method to build a full sale.
     * @param Id_ISBN_Quant
     * @return 
     */
    @Override
    public String[] createSale(String[] Id_ISBN_Quant) {
        //sale = {"SaleID", "books_JSONStrings"}
        if(saleID_Exists(Id_ISBN_Quant[0])){
            System.out.println("Sale ID alread exists!");
            return null;
        }
        String[] sale = new String[2];  //The sale array will have order number and books strings
        JSONObject firstBook = this.getBookJsonByISBN(Id_ISBN_Quant[1]);  //get the book JSON
        if(firstBook==null){return null;} //Means ISBN was not found in inventory
        firstBook.put(BookSchema.STOCK, Id_ISBN_Quant[2]); //Change "Stock" to be quantity sold
        sale[0] = Id_ISBN_Quant[0];  //Sale ID will be directly from parameter
        sale[1] = firstBook.toJSONString(); //Books will begin as the first book
        return sale;        
    }

    /**
     * Update a sale and maintain it in memory until all items are added.  Once a
     * sale is ready to be paid, a "getSaleSubtotal(sale)" can be requested.  When 
     * payment is submitted, the sale will be saved to disk.
     * @param sale
     * @param Id_ISBN_Quant
     * @return 
     */
    @Override
    public String[] updateSale(String[] sale, String[] Id_ISBN_Quant) {
        JSONObject nextBook = this.getBookJsonByISBN(Id_ISBN_Quant[1]);  //get the book JSON
        if(nextBook==null){return null;} //Means ISBN was not found in inventory
        nextBook.put(BookSchema.STOCK, Id_ISBN_Quant[2]); //Change "Stock" to be quantity sold
        String books = sale[1];
        books+=";";
        books+=nextBook.toJSONString();
        sale[1] = books;
        return sale;
    }
    
    /**
     * This method returns a subtotal for all books in a sale array.  When the customer
     * is ready to pay, submit the sale instance and relevant payment information into 
     * the "addPaymentToSale(..) method.  This will save the sale into the sales file.
     * @param sale
     * @return 
     */
    public String getSaleSubtotal(String[] sale){
        JSONParser jsonParser = new JSONParser();
        double subtotal = 0d;
        try{
            for(String jsonBook : sale[1].split(";")){
                int numBooks;
                double price;
                JSONObject book = (JSONObject) jsonParser.parse(jsonBook);
                String strNum = (String) book.get(BookSchema.STOCK);
                numBooks = Integer.parseInt(strNum);
                String strPrice = (String) book.get(BookSchema.PRICE);
                price = Double.parseDouble(strPrice);
                subtotal += (numBooks * price);
            }
            return String.format("%.2f",subtotal);
        }catch(ParseException e){
            e.printStackTrace();
            System.out.println("Parse exception.  The book contained in the sale"
                    + "is not being parsed correctly.");
            return null;
        }
    }

    /**
     * This is the last step in a customer's purchase.  Once payment is added,
     * the sale is saved to file.  This FINALIZES the sale.
     * @param sale
     * @param paymentDetails
     * @param dateMillis
     * @return 
     */
    @Override
    public String addPaymentToSale(String[] sale, String paymentDetails, long dateMillis) {  //finalizeSale
        JSONObject finalSale = new JSONObject();
        finalSale.put(SaleSchema.SALEID, sale[0]);
        finalSale.put(SaleSchema.BOOKS, sale[1]);
        finalSale.put(SaleSchema.DATE, Long.toString(dateMillis));
        finalSale.put(SaleSchema.PAYMENT, paymentDetails);
        PrintWriter pw = openFileToEdit(this.SALES_FILEPATH, true);
        ////remove items from inventory.....
        try{
            pw.write(finalSale.toJSONString()+"\n");
            return "Sale complete.";
        }finally{
            pw.close();
        }
    }
    
    /**
     * This method will remove a sale from the sale file.  It returns the "Payment
     * details" submitted from the "addPaymentToSale(..) method from when it was 
     * originally saved to file.  This return includes the amount the customer paid
     * so the appropriate value can be refunded.
     * @param saleId
     * @return 
     */
    @Override
    public String issueRefund(String saleId) {
        if(!saleID_Exists(saleId)){
            System.out.println("Sale ID Not found!");
            return null;
        }
        JSONObject[] booksFromRefund = getBooksFromSale(saleId);
        for(JSONObject jbook : booksFromRefund){
            String isbn = (String)jbook.get(BookSchema.ISBN);
            int qty = Integer.parseInt((String)jbook.get(BookSchema.STOCK));
            reduceBookStock(isbn, (qty*-1));
        }
        String refundAmount="";
        for(JSONObject sale : sales_contents){
            String currSaleID = (String) sale.get(SaleSchema.SALEID);
            if(currSaleID.equalsIgnoreCase(saleId)){
                refundAmount = (String) sale.get(SaleSchema.PAYMENT);
                sales_contents.remove(sale);
                break;
            }
        }
        updateSalesFile();
        return refundAmount;
    }

    public ArrayList<String[][]> getSalesInformation(long beginning, long end){
        ArrayList<String[][]> salesInfo = new ArrayList<>();
        this.readSalesToMemory();
        for(JSONObject sale : sales_contents){
            Long currSaleDate = Long.parseLong((String) sale.get(SaleSchema.DATE));
            if(currSaleDate<=end && currSaleDate>=beginning){
                JSONObject[] books = getBooksFromSale((String) sale.get(SaleSchema.SALEID));
                String[][] result = new String[books.length+1][9];
                String[] resultMetaData = new String[9];  //to be of appropriate size, even though just using 3
                resultMetaData[0] = (String) sale.get(SaleSchema.SALEID);
                resultMetaData[1] = (String) sale.get(SaleSchema.DATE);
                resultMetaData[2] = (String) sale.get(SaleSchema.PAYMENT);
                result[0] = resultMetaData;
                for(int x=1; x<books.length+1; x++) {
                    String[] bookAsString = this.convertJsonBookToStringArray(books[x - 1]);
                    result[x] = bookAsString;
                }
                salesInfo.add(result);
            }
        }
        return salesInfo;
    }

    /**
     * Get all book objects from a completed sale.  
     * @param saleID
     * @return 
     */
    private JSONObject[] getBooksFromSale(String saleID){
        if(!saleID_Exists(saleID)){
            System.out.println("Sale ID Not found!");
            return null;
        }
        JSONObject jsonSale = new JSONObject();
        JSONParser jsonParser = new JSONParser();
        for(JSONObject sale : sales_contents){
            String currOrderID = (String) sale.get(SaleSchema.SALEID);
            if(currOrderID.equalsIgnoreCase(saleID)){
                jsonSale = sale;
                break;
            }
        }
        try{
            String books = (String) jsonSale.get(SaleSchema.BOOKS);
            String[] booksAry = books.split(";");
            JSONObject[] jsonBooks = new JSONObject[booksAry.length];
            int x = 0;
            for(String book : booksAry){
                jsonBooks[x] = (JSONObject) jsonParser.parse(book);
                x++;
            }
            return jsonBooks;
        }catch(ParseException e){
            e.printStackTrace();
            System.out.println("Could not parse book string from order.");
            return null;
        }
    }
    
    /**
     * Returns an ArrayList of all sales that fall between the given dates.
     * ----Right now does not work with "Date" objects, just in a testing state
     * The return includes String arrays that look like the following: 
     * 
            sale124
            ISBN: 123222333   Title: The Best Book   Quantity: 2
            ISBN: 123333444   Title: The Other Book   Quantity: 1
            64.45
     
     * first line SaleID, next lines are book data from sale, last line amt paid
     * 
     * databases.
     * @param start
     * @param end
     * @return 
     */
    @Override
    public ArrayList<String[]> getSalesReport(Date start, Date end) {
        this.readSalesToMemory();
        LinkedList<JSONObject> matches = new LinkedList<>();
        for(JSONObject sale : sales_contents){
            String saleMillis = (String) sale.get(SaleSchema.DATE);
            Long dateOfSale = Long.parseLong(saleMillis);
            Date saleDate = new Date(dateOfSale);
            if(saleDate.before(end)&& start.before(saleDate))
                matches.add(sale);
        }
        ArrayList<String[]> salesReport = new ArrayList<>();
        for(JSONObject sale: matches){
            salesReport.add(convertJsonSaleToStringArray(sale));
        }
        return salesReport;
    }
    /*
    Parse a date in the form MM.DD.YYYY to milliseconds:
        String someDate = "05.10.2011";
        SimpleDateFormat sdf = new SimpleDateFormat("MM.dd.yyyy");
        Date date = sdf.parse(someDate);
        System.out.println(date.getTime());
    
    */


    /************************************************************************************
     *                                       ORDERS
     ***********************************************************************************/

 //Schema: OrderID, Books, DateOrdered, DateExpectedDelivery, DeliveredStatus
    
    ///====================PRIVATE METHODS==========================///

    private String[] convertJsonOrderToStringArray(JSONObject order){
        String[] orderAry = new String[5];       
        orderAry[0] = (String) order.get(OrderSchema.ORDERID);
        JSONObject[] jBooks = getBooksFromOrder((String) order.get(OrderSchema.ORDERID));
        String books = "";
        int x = 0;
        for(JSONObject jBook : jBooks){
            String bookISBN = (String) jBook.get(BookSchema.ISBN);
            String bookQuan = (String) jBook.get(BookSchema.STOCK);
            String bookTitle = (String) jBook.get(BookSchema.TITLE);
            books += ("ISBN: "+ bookISBN + " Title: " + bookTitle +" Quantity: "+ bookQuan);
            if(x != jBooks.length-1)
                books+="\n";
            x++;
        }
        orderAry[1] = books;
        String ordered = (String) order.get(OrderSchema.ORDER_DATE);
        orderAry[2] = new Date(Long.parseLong(ordered)).toString();
        String expDeliv = (String) order.get(OrderSchema.DELIVERY_Date);
        orderAry[3] = new Date(Long.parseLong(expDeliv)).toString();
        orderAry[4] = (String) order.get(OrderSchema.IS_DELIVERED);
        return orderAry;        
    }   
        
    private void readOrdersToMemory() {  //public only for testing
        if(orders_contents.size()>0){
            orders_contents = new LinkedList<>();}
        try (Scanner file = new Scanner(new File(this.ORDERS_FILEPATH))) {
            JSONParser jsonParser = new JSONParser();
            while (file.hasNextLine()) {
                String nextLine = file.nextLine();
                if(nextLine.trim().equals("")){continue;}
                JSONObject x = (JSONObject) jsonParser.parse(nextLine);
                orders_contents.add(x);
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            System.out.println("Order file not found at: " + this.ORDERS_FILEPATH);
        } catch (ParseException ex) {
            ex.printStackTrace();
            System.out.println("Parse exception reading inventory file. "
                    + "An entry may not be in correct \"JSON\" format.");
        }
    }
    
    private boolean orderId_Exists(String orderId){
        readOrdersToMemory();
        for(JSONObject order : orders_contents){
            String currOrderId = (String) order.get(OrderSchema.ORDERID);
            if(currOrderId.equalsIgnoreCase(orderId)){
                return true;
            }
        }
        return false;
    }

    public boolean isOrderRecieved(String orderID){
        readOrdersToMemory();
        for(JSONObject order : orders_contents) {
            String currOrderId = (String) order.get(OrderSchema.ORDERID);
            if (currOrderId.equalsIgnoreCase(orderID)) {
                return !order.get(OrderSchema.IS_DELIVERED).equals("NOT_delivered");
            }
        }
        return false;
    }
    
    private JSONObject[] getBooksFromOrder(String orderID){
        if(!orderId_Exists(orderID)){
            return null;
        }
        JSONObject jsonOrder = new JSONObject();
        JSONParser jsonParser = new JSONParser();
        for(JSONObject order : orders_contents){
            String currOrderID = (String) order.get(OrderSchema.ORDERID);
            if(currOrderID.equalsIgnoreCase(orderID)){
                jsonOrder = order;
                break;
            }
        }
        try{
            String books = (String) jsonOrder.get(OrderSchema.BOOKS);  //get ";"-separated list of JSONObject books
            String[] booksAry = books.split(";");
            JSONObject[] jsonBooks = new JSONObject[booksAry.length];   //create a JSONObject array for the books
            int x = 0;
            for(String book : booksAry){
                jsonBooks[x] = (JSONObject) jsonParser.parse(book);
                x++;
            }
            return jsonBooks;
        }catch(ParseException e){
            e.printStackTrace();
            System.out.println("Could not parse book string from order.");
            return null;
        }
    }
    
    private void updateOrdersFile(){  //overwrites the entire file with the contents of the sales_contents linked list
        PrintWriter pw = openFileToEdit(this.ORDERS_FILEPATH, false);
        try{
            for(JSONObject order : orders_contents){
                pw.write(order.toJSONString()+"\n");
            }
        }finally{
            pw.close();
        }
    }

    public String markOrderAsReceived(String orderID, Long dateReceived){
        readOrdersToMemory();
        orderID=orderID.trim();
        for(JSONObject order : orders_contents) {
            String currOrderId = (String) order.get(OrderSchema.ORDERID);
            if (currOrderId.equalsIgnoreCase(orderID)) {
                String orderStatus = (String) order.get(OrderSchema.IS_DELIVERED);
                if(orderStatus.equals("NOT_delivered")){
                    order.put(OrderSchema.IS_DELIVERED, Long.toString(dateReceived));
                    updateOrdersFile();
                    return "Success";
                }
                Date receivedDate = new Date(Long.parseLong(orderStatus));
                return "Order has already been received on "+receivedDate.toString();
            }
        }
        return null;
    }

    public String[][] getBookDetailsFromOrder(String orderID){
        JSONObject[] books = this.getBooksFromOrder(orderID);
        if(books==null)
            return null;  //ORDER id does not exist.
        String[][] booksDetails = new String[books.length+1][3];
        if(this.isOrderRecieved(orderID)){
            booksDetails[0][0] = "Order Received";
        }else{
            booksDetails[0][0] = "Order Not Received";
        }
        booksDetails[0][1] = "0";
        booksDetails[0][2] = "0";
        for(int x=1; x<books.length+1; x++){
            booksDetails[x][0] = (String) books[x-1].get(BookSchema.ISBN);
            booksDetails[x][1] = (String) books[x-1].get(BookSchema.STOCK);
            booksDetails[x][2] = (String) books[x-1].get(BookSchema.STOCK);
        }
        return booksDetails;
    }
    
    
    /**
     * User provides the Order's ID, the ISBN of the first book to order, and the
     * quantity of that book for the order.  The method returns an order array
     * that will be appended to via the "updateOrder(..)" method.
     * @param Id_ISBN_Quant
     * @return 
     */
    public String[] createOrder(String[] Id_ISBN_Quant) {
        if(orderId_Exists(Id_ISBN_Quant[0])){
            System.out.println("Order ID alread exists!");
            return null;
        }
        String[] order = new String[2];  //The sale array will have order number and books strings
        JSONObject firstBook = this.getBookJsonByISBN(Id_ISBN_Quant[1]);  //get the book JSON
        if(firstBook==null){return null;} //Means ISBN was not found in inventory
        firstBook.put(BookSchema.STOCK, Id_ISBN_Quant[2]); //Change "Stock" to be quantity sold
        order[0] = Id_ISBN_Quant[0];  //Sale ID will be directly from parameter
        order[1] = firstBook.toJSONString(); //Books will begin as the first book
        return order;        
    }
    
    /**
     * Add a book and quantity to the order array.  Return the appended order array
     * for further additions.
     * @param order
     * @param Id_ISBN_Quant
     * @return 
     */
    @Override
    public String[] updateOrder(String[] order, String[] Id_ISBN_Quant) {
        JSONObject nextBook = this.getBookJsonByISBN(Id_ISBN_Quant[1]);  //get the book JSON
        if(nextBook==null){return null;} //Means ISBN was not found in inventory
        nextBook.put(BookSchema.STOCK, Id_ISBN_Quant[2]); //Change "Stock" to be quantity sold
        String books = order[1];
        books+=";";
        books+=nextBook.toJSONString();
        order[1] = books.toString();
        return order;
    }
    
    /**
     * Finalizing an order locks in all changes, adds the order and delivery dates 
     * and saves the order to file.
     * @param order
     * @param dateOrdered
     * @param expectedDeliveryDate
     * @return 
     */
    public String finalizeOrder(String[] order, long dateOrdered, long expectedDeliveryDate){
        JSONObject finalizeOrder = new JSONObject();
        finalizeOrder.put(OrderSchema.ORDERID, order[0]);
        finalizeOrder.put(OrderSchema.BOOKS, order[1]);
        finalizeOrder.put(OrderSchema.ORDER_DATE, Long.toString(dateOrdered));
        finalizeOrder.put(OrderSchema.DELIVERY_Date, Long.toString(expectedDeliveryDate));
        finalizeOrder.put(OrderSchema.IS_DELIVERED, "NOT_delivered");
        PrintWriter pw = openFileToEdit(this.ORDERS_FILEPATH, true); //appends the order to the end of the file
        try{
            pw.write(finalizeOrder.toJSONString()+"\n");
            return "Order complete.";
        }finally{
            pw.close();
        }
    }
    
    /**
     * Returns the total number of a certain book that are waiting to be delivered.
     * @param isbn
     * @return 
     */
    @Override
    public int getBookOnOrderQty(String isbn) {
        readOrdersToMemory();
        int booksOnOrder = 0;
        for(JSONObject order : orders_contents){
            String arrived = (String) order.get(OrderSchema.IS_DELIVERED);
            if(!arrived.equalsIgnoreCase("NOT_delivered")){continue;}  //dont count orders that are delivered
            String orderID = (String) order.get(OrderSchema.ORDERID);
            JSONObject[] books = getBooksFromOrder(orderID);
            for(JSONObject book : books){
                String currBookISBN = (String)book.get(BookSchema.ISBN);
                if(currBookISBN.equalsIgnoreCase(isbn)){
                    String currBooksOrdered = (String) book.get(BookSchema.STOCK);
                    booksOnOrder += Integer.parseInt(currBooksOrdered);
                }
            }
        }
        return booksOnOrder;
    }
    
    /**
     * Deletes the order if it exists.
     * @param orderID
     * @return 
     */
    @Override
    public String deleteOrder(String orderID) {
        if(!orderId_Exists(orderID)){
            System.out.println("Order ID Not found!");
            return "Order Not Found";
        }
        for(JSONObject order : orders_contents){
            String currOrderId = (String) order.get(OrderSchema.ORDERID);
            if(currOrderId.equalsIgnoreCase(orderID)){
                orders_contents.remove(order);
                break;
            }
        }
        updateOrdersFile();
        return "Order Deleted";
    }

    /**
     * returns an ArrayList of order details.  Each listing of order details includes 
     * the orders ID, the ISBN + quantity of each book on the order, and the expected
     * delivery date of the order.
     * @return 
     */
    @Override
    public ArrayList<String[]> getActiveOrders() {
        this.readOrdersToMemory();
        ArrayList<String[]> allActiveOrderDetails = new ArrayList<>();
        ArrayList<String> activeOrderIDs = new ArrayList<>();
        for(JSONObject order : sales_contents){
            String currOrderStatus = (String) order.get(OrderSchema.IS_DELIVERED);
            if(currOrderStatus.equalsIgnoreCase("notDelivered")){
                String orderID = (String) order.get(OrderSchema.ORDERID);
                activeOrderIDs.add(orderID);
            }
        }     
        for(String orderID : activeOrderIDs){
            allActiveOrderDetails.add(getOrder(orderID));
        }
        return allActiveOrderDetails;
    }

    @Override
    public String[] getActiveOrders(Date start, Date end) {        
        return new String[0];
    }

    @Override
    public String[] getPreviousOrders() {
        return new String[0];
    }

    @Override
    public String[] getOrderDateRange(Long start, Long end) {
        this.readOrdersToMemory();
        ArrayList<String> matches = new ArrayList<>();
        for(JSONObject order : orders_contents){
            String currOrderDate = (String) order.get(OrderSchema.ORDER_DATE);
            Long currOrderDateLONG = Long.parseLong(currOrderDate);
            if(currOrderDateLONG<=end && currOrderDateLONG>=start){
                matches.add((String) order.get(OrderSchema.ORDERID));
            }
        }
        String[] validOrderIDs = new String[matches.size()];
        for(int x=0; x<matches.size(); x++)
            validOrderIDs[x]=matches.get(x);
        return validOrderIDs;
    }

    /**
     * returns the details of the specific order.
     * @param orderId
     * @return 
     */
    @Override
    public String[] getOrder(String orderId) {
        if(!orderId_Exists(orderId)){
            System.out.println("Order Not Found.");
            return null;
        }
        for(JSONObject order : orders_contents){
            String currOrderId = (String) order.get(OrderSchema.ORDERID);
            if(currOrderId.equalsIgnoreCase(orderId)){
                return convertJsonOrderToStringArray(order);
            }
        }
        return null;
    }


/************************************************************************************
 *                                       USERS
 ***********************************************************************************/
    /**
     * Puts all contents of the user's file into memory for querying
     */
    private void readUsersToMemory(){
        if(users_contents.size()>0){users_contents = new LinkedList<JSONObject>();}
        try (Scanner file = new Scanner(new File(LOG_IN_CREDENTIALS_FILEPATH))) {
            //open file to read
            JSONParser jsonParser = new JSONParser();
            while(file.hasNextLine()){
                String nextLine = file.nextLine();
                JSONObject currUser = (JSONObject)jsonParser.parse(nextLine);
                users_contents.add(currUser);
            }
        }catch(FileNotFoundException ex) {
            ex.printStackTrace();
            System.out.println("Users' credentials file not found at: "+LOG_IN_CREDENTIALS_FILEPATH);
        }catch(ParseException ex){
            ex.printStackTrace();
            System.out.println("Parse exception reading credentials file. "
                    + "An entry may not be in correct \"JSON\" format.");
        }
    }
    
    /**
     * Quick check to see if a username is taken already.
     * @param username
     * @return 
     */
    public boolean usernameExists(String username){
        readUsersToMemory();
        for(JSONObject user : users_contents){
            String currUsername = (String) user.get(UserSchema.USER);
            if(currUsername.equalsIgnoreCase(username))
                return true;
        }
        return false;
    }
    
    public boolean emailExists(String email){
        readUsersToMemory();
        for(JSONObject user : users_contents){
            String currEmail = (String) user.get("Email");
            if(currEmail.equalsIgnoreCase(email))
                return true;
        }
        return false;
    }
    
    //convertStringArrayToJsonBook
    /**
     * Converts the correctly arranged String[] containing:
     * user's Username, Password, and Access Level
     * @param user_info
     * @return 
     */
   
    public JSONObject convertStringArrayToJsonUser(String[] user_info){
        try{
        JSONObject user = new JSONObject();
        user.put("Username", user_info[0]);
        user.put("Password", user_info[1]);
        user.put("Access", user_info[2]);
        user.put("Email", user_info[3]);
        user.put("Name", user_info[4]);   // "first,middle,last"
        user.put("Street", user_info[5]);
        user.put("City",user_info[6]);
        user.put("State", user_info[7]);
        user.put("Zip", user_info[8]);
        user.put("Gender", user_info[9]);
        user.put("DOB", user_info[10]);
        user.put("PhonePrimary", user_info[11]);
        user.put("PhoneSecondary", user_info[12]);
        user.put("EmergencyName", user_info[13]);
        user.put("EmergencyPhone", user_info[14]);

        return user;
        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println("The user String does not have enough entries.");
            System.out.println("You must provide all information");
            return null;
        }        
    }
    
    /**
     * Returns the JSONObject from the file as a String[]
     * @param user
     * @return 
     */
    private String[] convertJsonUserToStringArray(JSONObject user){
        String[] user_info = new String[15];
        user_info[0] = (String) user.get("Username");
        user_info[1] = (String) user.get("Password");
        user_info[2] = (String) user.get("Access");
        user_info[3] = (String) user.get("Email");
        user_info[4] = (String) user.get("Name");
        user_info[5] = (String) user.get("Street");
        user_info[6] = (String) user.get("City");
        user_info[7] = (String) user.get("State");
        user_info[8] = (String) user.get("Zip");
        user_info[9] = (String) user.get("Gender");
        user_info[10] = (String) user.get("DOB");
        user_info[11] = (String) user.get("PhonePrimary");
        user_info[12] = (String) user.get("PhoneSecondary");
        user_info[13] = (String) user.get("EmergencyName");
        user_info[14] = (String) user.get("EmergencyPhone");
        return user_info;
    }
        
    /**
     * Get a particular user's access level from the file.
     * @param username
     * @return 
     */
    public String getUserAccessLevel(String username){
        readUsersToMemory();
        for(JSONObject user : users_contents){
            String currUsername = (String) user.get(UserSchema.USER);
            if(currUsername.equalsIgnoreCase(username))
                return (String) user.get(UserSchema.ACCESS);
        }
        return null;
    }


///------------------------END USER HELPER METHODS---------------------------///    
    
    /**
     * Create an account.  Will check to see that:
     * 1) the username submitted is unique
     * 2) the logged in user making this request has the access to create the account
     * @param employee
     * @return 
     */
    @Override
    public String addAccount(String[] employee) {
        PrintWriter pw = openFileToEdit(LOG_IN_CREDENTIALS_FILEPATH, true);
        try{
            pw.write(convertStringArrayToJsonUser(employee).toJSONString()+"\n");  
            return "Success";
        }finally{
            pw.close();
        }
    }

    /**
     * Modify an account.  Will check to see that:
     * 1) the username submitted is actually on-file
     * 2) the logged in user making this request has the access to modify the account
     * @param employee
     * @return 
     */
    @Override
    public String updateAccount(String[] employee) {
        readUsersToMemory();
        for(JSONObject user : users_contents){
            String username = (String) user.get(UserSchema.USER);
            if(username.equalsIgnoreCase(employee[0])){
                users_contents.remove(user);
                users_contents.add(convertStringArrayToJsonUser(employee)); //convert the account information into a JSON and replace the old JSON
            }
        }
        PrintWriter pw = openFileToEdit(LOG_IN_CREDENTIALS_FILEPATH, false); 
        try{            
            pw.write(users_contents.get(0).toJSONString());
            users_contents.remove(0);  //last line in file cannot be "\n"
            for(JSONObject user : users_contents)
                pw.write(user.toJSONString()+"\n"); //save all contents back into the file
            return "User updated successfully.";
        }finally{
            pw.close();
        }
    }

    /**
     * Delete an account.  Will check to see that:
     * 1) the username submitted is actually on-file
     * 2) the logged in user making this request has the access to delete the account
     * @param username
     * @return 
     */
    @Override
    public String deleteAccount(String username) {
        readUsersToMemory();
        for(JSONObject user : users_contents){
            String currUsername = (String) user.get(UserSchema.USER);
            if(currUsername.equalsIgnoreCase(username)){
                users_contents.remove(user);
            }
        }
        PrintWriter pw = openFileToEdit(LOG_IN_CREDENTIALS_FILEPATH, false); 
        try{
            for(JSONObject user : users_contents)
                pw.write(user.toJSONString()+"\n");
            return "Success";  //dont change this return.  test for .equals("Success") elsewhere.
        }finally{
            pw.close();
        }
    }
    
    
    public String deleteAccountByEmail(String email) {
        readUsersToMemory();
        for(JSONObject user : users_contents){
            String currEmail = (String) user.get("Email");
            if(currEmail.equalsIgnoreCase(email)){
                users_contents.remove(user);
            }
        }
        PrintWriter pw = openFileToEdit(LOG_IN_CREDENTIALS_FILEPATH, false); 
        try{
            for(JSONObject user : users_contents)
                pw.write(user.toJSONString()+"\n");
            return "Success";
        }finally{
            pw.close();
        }
    }

    /**
     * returns an arraylist of all accounts in the form of String[]
     * @return 
     */
    @Override
    public ArrayList<String[]> getAllAccounts() {
        readUsersToMemory();
        ArrayList<String[]> allUsers = new ArrayList<>();
        for(JSONObject user : users_contents){
            allUsers.add(this.convertJsonUserToStringArray(user));
        }
        return allUsers;
    }

    /**
     * returns the account details of the given username, if it exists
     * @param username
     * @return 
     */
    @Override
    public String[] getAccount(String username) {
        if(usernameExists(username)){
            for(JSONObject user : users_contents){
                String currUsername = (String) user.get(UserSchema.USER);
                if(currUsername.equalsIgnoreCase(username))
                    return this.convertJsonUserToStringArray(user);
            }
        }
        return null;
    }
    
    public String getUsernameWithEmail(String email) {
        this.readUsersToMemory();
        for(JSONObject user : users_contents){
            String currEmail = (String) user.get("Email");
            if(currEmail.equalsIgnoreCase(email))
                return (String) user.get("Username");
        }
        return null;
    }

    /**
     * The String returns will be messages about if the log-in was successful or not.
     * @param username
     * @param password
     * @return 
     */
    @Override
    public String attemptLogin(String username, String password) {
        if(usernameExists(username)){
            for(JSONObject user : users_contents){
                String currUsername = (String) user.get(UserSchema.USER);
                if(currUsername.equalsIgnoreCase(username)){
                    String currPass = (String) user.get(UserSchema.PASS);
                    if(currPass.equalsIgnoreCase(password)){
                        return (String) user.get(UserSchema.ACCESS);
                    }else{
                        return "Password incorrect.";
                    }
                }
            }
        }
        return "Username not found.";
    }

    @Override
    /**
     * This method does not check the caller's access level.  It is assumed that
     * the controllers calling this method will auto-fill the username so that
     * only a user could change their own password.  
     */
    public String updatePassword(String username, String newPassword) {
        if(usernameExists(username)){
            for(JSONObject user : users_contents){
                String currUsername = (String) user.get(UserSchema.USER);
                if(currUsername.equalsIgnoreCase(username)){
                    users_contents.remove(user);
                    user.put(UserSchema.PASS, newPassword);
                    users_contents.add(user);
                    break;
                }
            }
            PrintWriter pw = openFileToEdit(LOG_IN_CREDENTIALS_FILEPATH, false); 
            try{            
                pw.write(users_contents.get(0).toJSONString());
                users_contents.remove(0);  //last line in file cannot be "\n"
                for(JSONObject user : users_contents)
                    pw.write("\n"+user.toJSONString()); //save all contents back into the file
                return "Password updated successfully.";
            }finally{
                pw.close();
            }
        }
        return "Username not found.";
    }

    
    ///===============Static Class Constants for JSONs=====================///
    
    static class BookSchema{
        //ISBN, Title, Author, Price, Genre, Summary, Stock, Cost
        protected static final String ISBN = "ISBN";
        protected static final String TITLE = "Title";
        protected static final String AUTHOR = "Author";
        protected static final String PRICE = "Price";
        protected static final String GENRE = "Genre";
        protected static final String SUMMARY = "Summary";
        protected static final String STOCK = "Stock";
        protected static final String COST = "Cost";
        protected static final String IMAGE = "Image";
        private static String[] book_schema_array = {ISBN,TITLE,AUTHOR,PRICE,GENRE,
                                                    SUMMARY,STOCK,COST,IMAGE};
        protected static final List<String> FULL_BOOK_SCHEMA_LIST =
                Collections.unmodifiableList(Arrays.asList(book_schema_array));
    }
    
    static class UserSchema{
        //Username, Password, Access Level
        protected static final String USER = "Username";
        protected static final String PASS = "Password";
        protected static final String ACCESS = "Access";
        protected static final String STAFF_ACCESS = "employee";
        protected static final String ADMIN_ACCESS = "admin";
        protected static final String ROOT_ACCESS = "root";
        private static String[] levels = {"employee","admin","root"};
        protected static final List<String> ACCESS_LEVELS =
                Collections.unmodifiableList(Arrays.asList(levels));
//        user_info[0] = (String) user.get(UserSchema.USER);
//        user_info[1] = (String) user.get(UserSchema.PASS);
//        user_info[2] = (String) user.get(UserSchema.ACCESS);
//        user_info[2] = (String) user.get("Email");
//        user_info[2] = (String) user.get("Name");
//        user_info[2] = (String) user.get("Street");
//        user_info[2] = (String) user.get("City");
//        user_info[2] = (String) user.get("State");
//        user_info[2] = (String) user.get("Zip");
//        user_info[2] = (String) user.get("Gender");
//        user_info[2] = (String) user.get("DOB");
//        user_info[2] = (String) user.get("PhonePrimary");
//        user_info[2] = (String) user.get("PhoneSecondary");
//        user_info[2] = (String) user.get("EmergencyName");
//        user_info[2] = (String) user.get("EmergencyPhone");
        
    }
    
    static class SaleSchema{
        //SaleID, Books, DateSold, PaymentDetails
        protected static final String SALEID = "SaleID";
        protected static final String BOOKS = "Books";
        protected static final String DATE = "DateSold";
        protected static final String PAYMENT = "PaymentDetails";
    }
    
    static class OrderSchema{
        //OrderID, Books, DateOrdered, DateExpectedDelivery, DeliveredStatus
        protected static final String ORDERID = "OrderID";
        protected static final String BOOKS = "Books";
        protected static final String ORDER_DATE = "DateOrdered";
        protected static final String DELIVERY_Date = "DateExpectedDelivery";
        protected static final String IS_DELIVERED = "IsDelivered";
    }
    
}

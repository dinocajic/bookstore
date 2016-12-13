package Model;

import Model.ModelInterface.AddBookToInventoryModelInterface;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.regex.Pattern;

/**
 * Created by Dino Cajic on 10/27/2016.
 *
 * Gathers information from the AddBookToInventoryView and performs operations on the content submitted
 */
public class AddBookToInventoryModel implements AddBookToInventoryModelInterface {

    /**
     * Checks to make sure the user didn't forget to insert any of the required fields
     * @param book The book array that needs to be inserted into the database.
     */
    public String checkFormFields(String[] book) {
        String errorMsg = "The following errors occurred: ";

        if (book[0].trim().equals("") || book[0].equals("Book Title")) {
            errorMsg += "\n - You Must enter a Book Title.";
        }

        if (book[1].trim().equals("") || book[1].equals("Author")) {
            errorMsg += "\n - You Must enter at least one author.";
        }

        if (book[2].trim().equals("") || book[2].equals("ISBN")) {
            errorMsg += "\n - You Must enter an ISBN.";
        }

        if (book[3].trim().equals("")) {
            errorMsg += "\n - You Must enter a short summary.";
        }

        if (book[4].trim().equals("")) {
            errorMsg += "\n - You Must enter a long summary.";
        }

        if (book[5].trim().equals("Any") && book[6].equals("Any")) {
            errorMsg += "\n - You Must select an least one genre.";
        }

        if (book[7].trim().equals("Any") && book[8].equals("Any") && book[9].equals("Any") && book[10].equals("Any")) {
            errorMsg += "\n - You Must select an least one sub-genre.";
        }
        
        if(book[11].trim().equals("")){
            errorMsg += "\n - You must enter a valid Quantity of books to add. (0 or more)";
        }
        
        if(book[12].trim().equals("") || !this.canBeParsedAsDouble(book[12])){
            errorMsg += "\n - You must enter a valid Price for the book. (no \"$\")";  //z
        }
        
        if(book[13].trim().equals("") || !this.canBeParsedAsDouble(book[13])){
            errorMsg += "\n - You must enter a valid Cost for the book. (no \"$\")";    //z
        }

        return errorMsg;
    }

    /**
     * Calls the DatabaseModel class to insert the book array into the database
     * @param book The book array that needs to be inserted into the database.
     * @return String : The DatabaseModel returns a message stating whether the insert was a success.
     */
    public String insertBook(String[] book, String bookImagePath) {   //z
        
       String [] dbBook = new String[9]; 
        //DB needs : String[] of   isbn, title, author (comma separated string), price,
        //                         genre (comma separated string), summary, stock, cost   ?image
        String genres = book[5].trim();  //build the genre string from the selections on GUI

        for (int x = 6; x <= 10; x++) {
            if (!book[x].equalsIgnoreCase("any")) {
                genres += "," + book[x];
            }
        }
 
        dbBook[0] = book[2].trim();     //isbn
        dbBook[1] = book[0].trim();     //title
        dbBook[2] = book[1].trim();     //authors
        dbBook[3] = book[12].trim();    //price 
        dbBook[4] = genres;             //genres/categories
        dbBook[5] = book[4].trim();     //summary
        dbBook[6] = book[11].trim();    //number books added (Stock)
        dbBook[7] = book[13].trim();    //cost
        dbBook[8] = bookImagePath == null ? "src/img/books/default.png" : bookImagePath;
 
        DatabaseModel dbm = new DatabaseModel();
        String database_reply = dbm.addBook(dbBook);

        if (database_reply.equalsIgnoreCase("Book successfully added.")){
            return "Success";
        } else {
            return database_reply;
        }
    }

    /**
     * Adds an image
     * @return image
     */
    public String addImageFromFileExplorer(){
        JFileChooser FileChooser       = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG PNG","jpg", "png");
        FileChooser.setFileFilter(filter);

        int returnValue = FileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile       = FileChooser.getSelectedFile();
            String filePath         = selectedFile.getAbsolutePath();  // Full location of the desired image
            InputStream inStream    = null;
            OutputStream outStream  = null;

            try {
                File source         = new File(filePath);
                String relativePath = "src/img/books/" + selectedFile.getName();
                File dest           = new File(System.getProperty("user.dir") + "/" + relativePath);
                inStream            = new FileInputStream(source);
                outStream           = new FileOutputStream(dest);

                byte[] buffer       = new byte[1024*4];

                int length;

                while ((length = inStream.read(buffer)) > 0){
                    outStream.write(buffer, 0, length);
                }

                if (inStream != null) {
                    inStream.close();
                }

                if (outStream != null) {
                    outStream.close();
                }

                if(relativePath!=null) {
                    return relativePath;  //successfully grabbed file
                }

            } catch(IOException e1) {
                e1.printStackTrace();
            }
        }

        return null; //failure
    }
    
    /**
     * This is a copy-paste method from the internet using REGEX to determine if a string can be parsed as a double.
     * This is to make sure all of our prices/costs are to the 2nd decimal place for each book added.
     * @param str The string to check if it can be parsed
     * @return boolean True if it can be
     */
    private boolean canBeParsedAsDouble(String str) {
        final String Digits = "(\\p{Digit}+)";
        final String HexDigits = "(\\p{XDigit}+)";
        // an exponent is 'e' or 'E' followed by an optionally 
        // signed decimal integer.
        final String Exp = "[eE][+-]?" + Digits;
        final String fpRegex
                = ("[\\x00-\\x20]*"
                + // Optional leading "whitespace"
                "[+-]?("
                + // Optional sign character
                "NaN|"
                + // "NaN" string
                "Infinity|"
                + // "Infinity" string
                // A decimal floating-point string representing a finite positive
                // number without a leading sign has at most five basic pieces:
                // Digits . Digits ExponentPart FloatTypeSuffix
                // 
                // Since this method allows integer-only strings as input
                // in addition to strings of floating-point literals, the
                // two sub-patterns below are simplifications of the grammar
                // productions from the Java Language Specification, 2nd 
                // edition, section 3.10.2.
                // Digits ._opt Digits_opt ExponentPart_opt FloatTypeSuffix_opt
                "(((" + Digits + "(\\.)?(" + Digits + "?)(" + Exp + ")?)|"
                + // . Digits ExponentPart_opt FloatTypeSuffix_opt
                "(\\.(" + Digits + ")(" + Exp + ")?)|"
                + // Hexadecimal strings
                "(("
                + // 0[xX] HexDigits ._opt BinaryExponent FloatTypeSuffix_opt
                "(0[xX]" + HexDigits + "(\\.)?)|"
                + // 0[xX] HexDigits_opt . HexDigits BinaryExponent FloatTypeSuffix_opt
                "(0[xX]" + HexDigits + "?(\\.)" + HexDigits + ")"
                + ")[pP][+-]?" + Digits + "))"
                + "[fFdD]?))"
                + "[\\x00-\\x20]*");// Optional trailing "whitespace"

        return Pattern.matches(fpRegex, str);
    }
}
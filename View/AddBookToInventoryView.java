package View;

import Config.Config;
import Controller.AddBookToInventoryController;
import GUIobjects.CMPanel;
import Libraries.Layout;
import View.ViewInterface.AddBookToInventoryViewInterface;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

import net.miginfocom.swing.MigLayout;

/**
 * Created by Dino Cajic on 10/27/2016.
 *
 * Displays the Add Book screens
 */
public class AddBookToInventoryView implements AddBookToInventoryViewInterface {

    CMPanel display;
    JPanel mainLabelPane                = new JPanel(new MigLayout());

    JLabel mainLabel                    = new JLabel("Add New Book to Inventory");
    JLabel categoryLabel                = new JLabel("Categories");
    JLabel subCategoryLabel             = new JLabel("Sub-Categories");
    JLabel shortSummaryLabel            = new JLabel("Short Summary");
    JLabel longSummaryLabel             = new JLabel("Long Summary");
    JLabel message                      = new JLabel("");
    JLabel pricingLabel                 = new JLabel("Pricing Details");   //zach's addition
    JLabel imageAddedLabel              = new JLabel("none selected");
    String imageFilePath                = null;

    JTextField isbn                     = new JTextField("ISBN", 20);
    JTextField bookTitle                = new JTextField("Book Title", 25);
    JTextField bookAuthor               = new JTextField("Author", 25);
    JTextField textShortSummary         = new JTextField(45);
    JTextField bookPrice                = new JTextField("Price",10);       //z
    JTextField bookCost                 = new JTextField("Cost", 10);       //z

    JTextArea textSummary               = new JTextArea(5,45);

    JComboBox<String> textGenre1        = new JComboBox<>(Config.category);
    JComboBox<String> textGenre2        = new JComboBox<>(Config.category);
    JComboBox<String> textSubCategory1  = new JComboBox<>(Config.category);
    JComboBox<String> textSubCategory2  = new JComboBox<>(Config.category);
    JComboBox<String> textSubCategory3  = new JComboBox<>(Config.category);
    JComboBox<String> textSubCategory4  = new JComboBox<>(Config.category);

    JSpinner qty                        = new JSpinner();

    ImageIcon infoButton                = new ImageIcon(Config.info_button);
    JLabel infoLabel                    = new JLabel("", infoButton, JLabel.CENTER);

    JButton buttonAddBook               = new JButton("Add Book");
    JButton inputImageButton            = new JButton("Choose Image");

    /**
     * @param display The display object that was instantiated in the ApplicationWindow class
     */
    public AddBookToInventoryView(CMPanel display) {
        this.display = display;
    }

    /**
     * Displays the Add Book To Inventory Form
     *
     * Modified by Anh Pham on 10/29/2016.
     * Modified by Dino Cajic on 10/30/2016.
     * Redone by Dino Cajic on 10/31/2016.
     */
    public void displayView() {
        this.display.resetToEmpty();

        // Main Display Label
        this.mainLabelPane.add(this.mainLabel);

        this.modifySizes();
        this.addClearListeners();
        this.addButtonListener();
        this.initImageButton();
        this.addToolTips();

        // Add the items to the panel
        JPanel panel = new JPanel(new MigLayout());
        panel.add(this.mainLabelPane,     "span");

        JScrollPane scrollPane = new JScrollPane(panel);

        // Just added to display messages that the system might reply
        panel.add(this.message,           "span");

        panel.add(this.bookTitle);
        panel.add(this.isbn,              "wrap");
        panel.add(this.bookAuthor);
        panel.add(this.infoLabel,         "wrap");
        panel.add(this.categoryLabel,     "span");
        panel.add(this.textGenre1,        "split 2");
        panel.add(this.textGenre2);

        panel.add(new JLabel("Qty: "),    "split 2");
        panel.add(this.qty,               "wrap");

        panel.add(this.subCategoryLabel,  "span");
        panel.add(this.textSubCategory1);
        panel.add(this.textSubCategory2,  "wrap");
        panel.add(this.textSubCategory3);
        panel.add(this.textSubCategory4,  "wrap");
        panel.add(this.pricingLabel,      "wrap");       //z
        panel.add(this.bookPrice,         "split 2");    //z
        panel.add(this.bookCost);                        //z
        panel.add(this.inputImageButton,  "split 2");
        panel.add(this.imageAddedLabel,   "wrap");
        panel.add(this.shortSummaryLabel, "span");
        panel.add(this.textShortSummary,  "span");
        panel.add(this.longSummaryLabel,  "span");
        panel.add(this.textSummary,       "span");
        panel.add(this.buttonAddBook);

        this.display.add(scrollPane);
    }

    /**
     * Modifies the sizes of the different form fields
     */
    private void modifySizes() {
        Layout layout = new Layout();
        layout.formatDisplayTitle(this.mainLabelPane);

        this.mainLabel.setFont(Config.title_font);
        this.mainLabel.setBorder(new EmptyBorder(10, 10, 10, 10));

        EmptyBorder border = new EmptyBorder(10, 0, 10, 10);

        this.categoryLabel.setBorder(border);
        this.subCategoryLabel.setBorder(border);
        this.shortSummaryLabel.setBorder(border);
        this.longSummaryLabel.setBorder(border);
        this.textSummary.setBorder(BorderFactory.createLineBorder(new Color(119, 119, 119)));
        this.pricingLabel.setBorder(border); //z
        this.imageAddedLabel.setFont(Config.default_font);

        this.isbn.setPreferredSize(new Dimension(20, 30));
        this.bookTitle.setPreferredSize(new Dimension(20, 30));
        this.bookAuthor.setPreferredSize(new Dimension(20, 30));
        this.textShortSummary.setPreferredSize(new Dimension(20, 30));
        this.textSummary.setPreferredSize(new Dimension(500, 100));
        this.textSummary.setMaximumSize(new Dimension(500, 100));
        this.qty.setPreferredSize(new Dimension(20, 30));
        this.qty.setMinimumSize(new Dimension(50, 30));

        ((SpinnerNumberModel)this.qty.getModel()).setMinimum(0);

        this.bookPrice.setMinimumSize(new Dimension(20, 30)); //z
        this.bookCost.setMinimumSize(new Dimension(20, 30));  //z

        Dimension genreDimension = new Dimension(135, 30);

        this.textGenre1.setPreferredSize(genreDimension);
        this.textGenre1.setMinimumSize(genreDimension);
        this.textGenre2.setPreferredSize(genreDimension);
        this.textGenre2.setMinimumSize(genreDimension);

        Dimension subCatLgDimension = new Dimension(280, 30);
        Dimension subCatSmDimension = new Dimension(210, 30);

        this.textSubCategory1.setPreferredSize(subCatLgDimension);
        this.textSubCategory1.setMinimumSize(subCatLgDimension);
        this.textSubCategory3.setPreferredSize(subCatLgDimension);
        this.textSubCategory3.setMinimumSize(subCatLgDimension);

        this.textSubCategory2.setPreferredSize(subCatSmDimension);
        this.textSubCategory2.setMinimumSize(subCatSmDimension);
        this.textSubCategory4.setPreferredSize(subCatSmDimension);
        this.textSubCategory4.setMinimumSize(subCatSmDimension);

        this.textGenre1.setBackground(Config.dropdown_color);
        this.textGenre2.setBackground(Config.dropdown_color);
        this.textSubCategory1.setBackground(Config.dropdown_color);
        this.textSubCategory2.setBackground(Config.dropdown_color);
        this.textSubCategory3.setBackground(Config.dropdown_color);
        this.textSubCategory4.setBackground(Config.dropdown_color);

        Component c = this.qty.getEditor().getComponent(0);
        c.setBackground(Config.dropdown_color);

        layout.formatMainButtons(this.buttonAddBook);
        layout.formatMainButtons(this.inputImageButton);
    }

    /**
     * Adds Listeners to clear content from text fields.
     */
    private void addClearListeners() {
        // Clear book title
        this.bookTitle.addMouseListener(new MouseListener() {

            public void mouseClicked(MouseEvent e) {
                if (AddBookToInventoryView.this.bookTitle.getText().equals("Book Title")) {
                    AddBookToInventoryView.this.bookTitle.setText("");
                }
            }

            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
        });

        // Clear book author
        this.bookAuthor.addMouseListener(new MouseListener() {

            public void mouseClicked(MouseEvent e) {
                if (AddBookToInventoryView.this.bookAuthor.getText().equals("Author")) {
                    AddBookToInventoryView.this.bookAuthor.setText("");
                }
            }

            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
        });

        // Clear ISBN
        this.isbn.addMouseListener(new MouseListener() {

            public void mouseClicked(MouseEvent e) {
                if (AddBookToInventoryView.this.isbn.getText().equals("ISBN")) {
                    AddBookToInventoryView.this.isbn.setText("");
                }
            }

            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
        });

        // Clear Price
        this.bookPrice.addMouseListener(new MouseListener() { //z

            public void mouseClicked(MouseEvent e) {
                if (AddBookToInventoryView.this.bookPrice.getText().equals("Price")) {
                    AddBookToInventoryView.this.bookPrice.setText("");
                }
            }

            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
        });

        // Clear Cost
        this.bookCost.addMouseListener(new MouseListener() { //z

            public void mouseClicked(MouseEvent e) {
                if (AddBookToInventoryView.this.bookCost.getText().equals("Cost")) {
                    AddBookToInventoryView.this.bookCost.setText("");
                }
            }

            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
        });
    }

    /**
     * Initializes the image button
     * Created by Zachary Shoults
     */
    private void initImageButton(){
        this.inputImageButton.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e){
                AddBookToInventoryController controller = new AddBookToInventoryController(AddBookToInventoryView.this.display);
                String relativeFilePath = controller.addImageFromFileExplorer();
                if(relativeFilePath==null){
                    AddBookToInventoryView.this.imageAddedLabel.setText("could not get image");
                }else{
                    String[] parts = relativeFilePath.split("/");
                    AddBookToInventoryView.this.imageFilePath = relativeFilePath;
                    AddBookToInventoryView.this.imageAddedLabel.setText(parts[parts.length-1]);
                }
            }
        });
    }

    /**
     * Adds hover tool-tips.
     * Since certain text-fields have the content cleared, the user can't tell what was there before.
     */
    private void addToolTips() {
        this.infoLabel.setToolTipText("To add multiple authors, separate with a comma (,)");
        this.bookTitle.setToolTipText("Book Title");
        this.bookAuthor.setToolTipText("Author");
        this.isbn.setToolTipText("ISBN");
        this.bookPrice.setToolTipText("Price"); //z
        this.bookCost.setToolTipText("Cost");   //z
    }

    /**
     * Executed once the Add Book button is clicked.
     * Stores the entire form into a String[] book array.
     * Calls the AddBookToInventoryController to check if the form fields are correct and to insert the book
     * into the database.
     */
    private void addButtonListener() {
        this.buttonAddBook.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                String[] book = new String[14];
                book[0]  = AddBookToInventoryView.this.bookTitle.getText();
                book[1]  = AddBookToInventoryView.this.bookAuthor.getText();
                book[2]  = AddBookToInventoryView.this.isbn.getText();
                book[3]  = AddBookToInventoryView.this.textShortSummary.getText();
                book[4]  = AddBookToInventoryView.this.textSummary.getText();
                book[5]  = (String)AddBookToInventoryView.this.textGenre1.getSelectedItem();
                book[6]  = (String)AddBookToInventoryView.this.textGenre2.getSelectedItem();
                book[7]  = (String)AddBookToInventoryView.this.textSubCategory1.getSelectedItem();
                book[8]  = (String)AddBookToInventoryView.this.textSubCategory2.getSelectedItem();
                book[9]  = (String)AddBookToInventoryView.this.textSubCategory3.getSelectedItem();
                book[10] = (String)AddBookToInventoryView.this.textSubCategory4.getSelectedItem();
             
                Integer qty = (Integer)AddBookToInventoryView.this.qty.getValue();
                book[11] = qty.toString();
                
                book[12] = AddBookToInventoryView.this.bookPrice.getText();
                book[13] = AddBookToInventoryView.this.bookCost.getText();

                AddBookToInventoryController controller = new AddBookToInventoryController();
                String err = controller.checkFormFields(book);

                if (!err.equals("The following errors occurred: ")) {
                    // Display errors
                    AddBookToInventoryView.this.message.setText("<html>" + err.replaceAll("\n", "<br>"));
                    return;
                }

                // Insert the book
                String success = controller.insertBook(book, imageFilePath);

                if (success.equals("Success")) {
                    AddBookToInventoryView.this.buttonAddBook.setEnabled(false);
                    AddBookToInventoryView.this.message.setText("The book has been inserted successfully.");
                } else {
                    AddBookToInventoryView.this.buttonAddBook.setEnabled(true);
                    AddBookToInventoryView.this.message.setText(success);
                }
            }
        });
    }
}
package View;

import Config.Config;
import Controller.AddBookToInventoryController;
import Controller.ModifyBookDetailsController;
import GUIobjects.CMPanel;
import Libraries.Layout;
import View.ViewInterface.ModifyBookDetailsViewInterface;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

import net.miginfocom.swing.MigLayout;

/**
 * Created by Dino Cajic on 10/29/2016.
 *
 * Displays the Modify Book Details screens
 */
public class ModifyBookDetailsView implements ModifyBookDetailsViewInterface {
    CMPanel display;
    JPanel mainLabelPane                 = new JPanel(new MigLayout());

    JLabel mainLabel                     = new JLabel("Modify Book Details");
    JLabel categoryLabel                 = new JLabel("Categories");
    JLabel subCategoryLabel              = new JLabel("Sub-Categories");
    JLabel shortSummaryLabel             = new JLabel("Short Summary");
    JLabel longSummaryLabel              = new JLabel("Long Summary");
    JLabel message                       = new JLabel("");
    JLabel pricingLabel                  = new JLabel("Pricing Details");   //z
    JLabel imageAddedLabel               = new JLabel("not changed");
    String changedImagePath              = null;

    JTextField isbn                      = new JTextField("ISBN", 38);
    JTextField bookTitle                 = new JTextField("Book Title", 25);
    JTextField bookAuthor                = new JTextField("Author", 25);
    JTextField textShortSummary          = new JTextField(45);
    JTextField bookPrice                 = new JTextField("Price",10);       //z
    JTextField bookCost                  = new JTextField("Cost", 10);       //z

    JTextArea textSummary                = new JTextArea(5,45);

    String[] category                    = Config.category;
    JComboBox<String> textGenre1         = new JComboBox<>(category);
    JComboBox<String> textGenre2         = new JComboBox<>(category);
    JComboBox<String> textSubCategory1   = new JComboBox<>(category);
    JComboBox<String> textSubCategory2   = new JComboBox<>(category);
    JComboBox<String> textSubCategory3   = new JComboBox<>(category);
    JComboBox<String> textSubCategory4   = new JComboBox<>(category);

    JSpinner qty                         = new JSpinner();
    ImageIcon infoButton                 = new ImageIcon("./src/img/info-button.png");
    JLabel infoLabel                     = new JLabel("", infoButton, JLabel.CENTER);
    JButton buttonAddBook                = new JButton("Modify Details");
    JButton searchButton                 = new JButton("Search");
    JButton inputImageButton             = new JButton("Change Image");

    Layout layout                        = new Layout();

    /**
     * @param display The display object that was instantiated in the ApplicationWindow class
     */
    public ModifyBookDetailsView(CMPanel display) {
        this.display = display;
    }

    /**
     * Displays the form that allows the particular book's details to be modified
     */
    public void modifyBookDetailsForm() {
        this.display.resetToEmpty();
        // Main Display Label
        this.mainLabelPane.add(this.mainLabel);

        this.modifySearchForBookPropertySizes();
        this.addSearchForBookListener();
        this.addClearISBNListener();

        //give image search button functionality
        this.initImageButton();

        // Add the items to the panel
        JPanel panel = new JPanel(new MigLayout());
        panel.add(this.mainLabelPane, "span");

        // Just added to display messages that the system might reply
        panel.add(this.message, "span");
        panel.add(this.isbn);
        panel.add(this.searchButton);

        this.display.add(panel);
    }

    /**
     * Modifies the sizes of the properties on the main search for book page before populating the modify details page.
     */
    private void modifySearchForBookPropertySizes() {
        this.layout.formatDisplayTitle(this.mainLabelPane);
        this.mainLabel.setFont(Config.title_font);
        this.mainLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.isbn.setPreferredSize(new Dimension(38, 40));
        layout.formatMainButtons(this.searchButton);
    }

    /**
     * Adds a listener to the search button
     */
    private void addSearchForBookListener() {
        this.searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String isbn = ModifyBookDetailsView.this.isbn.getText();

                ModifyBookDetailsController controller = new ModifyBookDetailsController();
                boolean test = controller.checkIfSearchFormEmpty(isbn);

                if (test) {
                    ModifyBookDetailsView.this.isbn.setBackground(Config.warning_color);
                    return;
                }

                String[] book = controller.getBook(isbn);

                if (book==(null)) {
                    ModifyBookDetailsView.this.message.setText("Book ISBN does not exist");
                    return;
                }
                ModifyBookDetailsView.this.message.setText("");
                ModifyBookDetailsView.this.displayModifyBookPage(book);
            }
        });
    }

    /**
     * Clears the content from form
     */
    private void addClearISBNListener() {
        this.isbn.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ModifyBookDetailsView.this.isbn.setText("");
                ModifyBookDetailsView.this.isbn.setToolTipText("ISBN");
            }

            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
        });
    }

    /**
     * Displays the book form to be modified
     * @param book The book details
     */
    private void displayModifyBookPage(String[] book) {
        this.display.resetToEmpty();

        // Main Display Label
        this.mainLabelPane.add(this.mainLabel);

        this.modifyBookPropertySizes();
        this.initializeProperties(book);
        this.addModifyBookListener();
        this.addToolTips();

        // Add the items to the panel
        JPanel panel = new JPanel(new MigLayout());
        panel.add(this.mainLabelPane,     "span");

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setPreferredSize(Config.display_panel_size);
        scrollPane.getVerticalScrollBar().setUnitIncrement(14);  //make scrolling faster

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
        panel.add(this.pricingLabel,      "wrap");    //z
        panel.add(this.bookPrice,         "split 2"); //z
        panel.add(this.bookCost);                     //z
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
     * Modifies the Modify Book property sizes
     */
    private void modifyBookPropertySizes() {
        this.layout.formatDisplayTitle(this.mainLabelPane);

        this.mainLabel.setFont(Config.title_font);
        this.mainLabel.setBorder(new EmptyBorder(10, 10, 10, 10));

        this.categoryLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 10));
        this.subCategoryLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 10));
        this.shortSummaryLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 10));
        this.longSummaryLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 10));
        this.textSummary.setBorder(BorderFactory.createLineBorder(new Color(119, 119, 119)));

        this.isbn = new JTextField("ISBN", 20);
        this.isbn.setPreferredSize(new Dimension(20, 30));
        this.bookTitle.setPreferredSize(new Dimension(20, 30));
        this.bookAuthor.setPreferredSize(new Dimension(20, 30));
        this.textShortSummary.setPreferredSize(new Dimension(20, 30));
        this.textSummary.setPreferredSize(new Dimension(20, 100));
        this.textSummary.setLineWrap(true);
        this.pricingLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 10)); //z
        this.qty.setPreferredSize(new Dimension(20, 30));
        this.qty.setMinimumSize(new Dimension(50, 30));
        this.bookPrice.setMinimumSize(new Dimension(20, 30)); //z
        this.bookCost.setMinimumSize(new Dimension(20, 30));  //z

        ((SpinnerNumberModel)this.qty.getModel()).setMinimum(0);

        this.bookPrice.setMinimumSize(new Dimension(20, 30));         //z
        this.bookCost.setMinimumSize(new Dimension(20, 30));          //z

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

        this.imageAddedLabel.setFont(Config.default_font);
        this.layout.formatMainButtons(this.inputImageButton);
        this.layout.formatMainButtons(this.buttonAddBook);
    }

    /**
     * Iitializes the properties so that they have pre-filled information
     * @param book The book to be modified
     */
    private void initializeProperties(String[] book) {
        this.bookTitle.setText(book[0]);
        this.bookAuthor.setText(book[1]);
        this.isbn.setText(book[2]);
        this.textShortSummary.setText(book[3]);
        this.textSummary.setText(book[4]);
        this.textGenre1.setSelectedItem(book[5]);
        this.textGenre2.setSelectedItem(book[6]);
        this.textSubCategory1.setSelectedItem(book[8]);
        this.textSubCategory2.setSelectedItem(book[7]);
        this.textSubCategory3.setSelectedItem(book[9]);
        this.textSubCategory4.setSelectedItem(book[10]);
        this.qty.setValue(Integer.parseInt(book[11]));
        this.bookPrice.setText(book[12]);
        this.bookCost.setText(book[13]);
        this.bookPrice.setToolTipText("Price"); //z
        this.bookCost.setToolTipText("Cost");   //z
        
    }

    /**
     * Adds a listener to the modify button
     */
    private void addModifyBookListener() {
        this.buttonAddBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] book = new String[14];
                book[0]  = ModifyBookDetailsView.this.bookTitle.getText();
                book[1]  = ModifyBookDetailsView.this.bookAuthor.getText();
                book[2]  = ModifyBookDetailsView.this.isbn.getText();
                book[3]  = ModifyBookDetailsView.this.textShortSummary.getText();
                book[4]  = ModifyBookDetailsView.this.textSummary.getText();
                book[5]  = (String)ModifyBookDetailsView.this.textGenre1.getSelectedItem();
                book[6]  = (String)ModifyBookDetailsView.this.textGenre2.getSelectedItem();
                book[7]  = (String)ModifyBookDetailsView.this.textSubCategory1.getSelectedItem();
                book[8]  = (String)ModifyBookDetailsView.this.textSubCategory2.getSelectedItem();
                book[9]  = (String)ModifyBookDetailsView.this.textSubCategory3.getSelectedItem();
                book[10] = (String)ModifyBookDetailsView.this.textSubCategory4.getSelectedItem();

                Integer qty = (Integer)ModifyBookDetailsView.this.qty.getValue();
                book[11] = qty.toString();
                
                book[12] = (String)ModifyBookDetailsView.this.bookPrice.getText();
                book[13] = (String)ModifyBookDetailsView.this.bookCost.getText();

                ModifyBookDetailsController controller = new ModifyBookDetailsController();
                String err = controller.checkFormFields(book);

                if (!err.equals("The following errors occurred: ")) {
                    // Display errors
                    ModifyBookDetailsView.this.message.setText("<html>" + err.replaceAll("\n", "<br>"));
                    return;
                }

                // Insert the book
                String success = controller.insertBookChanges(book, ModifyBookDetailsView.this.changedImagePath);
                ModifyBookDetailsView.this.message.setText(success);

            }
        });
    }

    /**
     * Adds the necessary hover tips
     */
    private void addToolTips() {
        this.bookTitle.setToolTipText("Book Title");
        this.bookAuthor.setToolTipText("Author");
        this.isbn.setToolTipText("ISBN");
        this.bookPrice.setToolTipText("Price");
        this.bookCost.setToolTipText("Cost");
        this.infoLabel.setToolTipText("To add multiple authors, separate with a comma (,)");
    }

    /**
     * Initializes the upload image button
     */
    private void initImageButton(){
        inputImageButton.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                AddBookToInventoryController controller = new AddBookToInventoryController(ModifyBookDetailsView.this.display);
                String relativeFilePath = controller.addImageFromFileExplorer();
                if(relativeFilePath==null){
                    ModifyBookDetailsView.this.imageAddedLabel.setText("could not get image");
                }else{
                    String[] parts = relativeFilePath.split("/");
                    ModifyBookDetailsView.this.imageAddedLabel.setText(parts[parts.length-1]);
                    ModifyBookDetailsView.this.changedImagePath = relativeFilePath;
                }
            }
        });
    }

    /**
     * Displays the form that allows the particular book qty's to be modified.
     */
    public void modifyBookQtyForm() {
        this.modifyBookDetailsForm();
    }
}
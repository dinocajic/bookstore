package View;

import Config.Config;
import Controller.DetailsPageController;
import GUIobjects.CMPanel;
import GUIobjects.RightPanel;
import Libraries.Layout;
import Model.SearchForBooksModel;
import View.ViewInterface.SearchForBooksViewInterface;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Dino Cajic on 10/29/2016.
 *
 * Displays the search results
 */
public class SearchForBooksView implements SearchForBooksViewInterface {

    CMPanel left;
    CMPanel display;
    RightPanel right;

    JPanel mainPanel = new JPanel(new MigLayout());
    JPanel panel     = new JPanel(new MigLayout());
    Layout layout    = new Layout();

    JScrollPane scrollPane;
    JPanel bookPanel;
    JLabel imageLabel;
    JLabel bookTitle;
    JLabel bookAuthor;
    JLabel bookIsbn;
    JLabel bookCost;
    JLabel bookQty;
    JButton saveButton;
    JButton viewButton;

    /**
     * @param display The display object that was instantiated in the ApplicationWindow class
     * @param right The right panel object that was instantiated in the ApplicationWindow class
     */
    public SearchForBooksView(CMPanel left, CMPanel display, RightPanel right) {
        this.left    = left;
        this.display = display;
        this.right   = right;
    }

    /**
     * Displays the results on the page
     * Since homepage displays the same results,
     * @param books The books that are retrieved from the database
     */
    public void displayResults(String[][] books, String[] genresOfBooks, boolean preventCheckboxes) {
        this.display.resetToEmpty();
        this.scrollPane = new JScrollPane(this.panel);
        this.scrollPane.getVerticalScrollBar().setUnitIncrement(14);  //make scrolling faster
        this.modifyMainProperties();

        int i = 0; // i = used to display a number of books per row

        for(String[] book: books) {
            this.initialize(book);
            this.modifyInLoopProperties();

            this.bookPanel.add(imageLabel, "wrap");
            this.bookPanel.add(bookTitle,  "wrap");
            this.bookPanel.add(bookAuthor, "wrap");
            this.bookPanel.add(bookIsbn,   "wrap");
            this.bookPanel.add(bookCost,   "split 2");
            this.bookPanel.add(bookQty,    "wrap");
            this.bookPanel.add(saveButton, "split 2");
            this.bookPanel.add(viewButton);

            // Displays 6 books per row
            if (i % 6 == 0 && i != 0) {
                this.panel.add(this.bookPanel, "wrap");
                i = 0;
            } else {
                this.panel.add(this.bookPanel);
                i++;
            }

            this.addButtonListeners(book);
        }

        if (books.length == 0) {
            ImageIcon bookImage = new ImageIcon(".\\src\\img\\no-results.png");
            this.imageLabel     = new JLabel(bookImage);
            this.panel.add(this.imageLabel);
        }

        this.mainPanel.add(this.scrollPane);
        this.display.add(this.mainPanel);

        // Resets the left side to the default if no search results
        if (preventCheckboxes) {
            if(SearchForBooksModel.newSearchOccured) {
                left.resetToEmpty();
                left.addLabelWithButtons("POPULAR GENRES", Config.leftPanelButtons, left, display, right);
                left.addLabelWithButtons("POPULAR AUTHORS", Config.authorsButtons, left, display, right);

                //make sure not to re-draw buttons/checkboxes on left panel until new search is performed
                SearchForBooksModel.newSearchOccured = false;
            }

            return;
        }

        if(SearchForBooksModel.newSearchOccured) {
            left.resetToEmpty();
            left.addLabelWithButtons("POPULAR GENRES", Config.leftPanelButtons, left, display, right);
            left.addResultFilterCheckboxes("Filter by Genre", genresOfBooks, left, display, right);

            //make sure not to re-draw buttons/checkboxes on left panel until new search is performed
            SearchForBooksModel.newSearchOccured = false;
        }
    }

    /**
     * Initializes in loop properties
     * @param book The particular book
     */
    private void initialize(String[] book) {
        this.bookPanel  = new JPanel(new MigLayout());
        this.bookTitle  = new JLabel(book[0]);
        this.bookAuthor = new JLabel(book[1]);
        this.bookIsbn   = new JLabel(book[2]);
        this.bookCost   = new JLabel("$" + book[4] + "  ");
        this.bookQty    = new JLabel("Qty: " + book[5]);
        this.saveButton = new JButton("Save");
        this.viewButton = new JButton("Details");

        ImageIcon bookImage = new ImageIcon(book[6]);
        Image scaleThumb    = bookImage.getImage().getScaledInstance(120, 179, Image.SCALE_DEFAULT);
        this.imageLabel     = new JLabel(new ImageIcon(scaleThumb));

        this.imageLabel.setToolTipText(book[3]);
    }

    /**
     * Modifies the look of the main properties
     */
    private void modifyMainProperties() {
        this.scrollPane.setPreferredSize(Config.display_panel_size);
        this.scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
    }

    /**
     * Modifies the look of in-loop properties
     */
    private void modifyInLoopProperties() {
        this.bookPanel.setBackground(Config.panel_saved_color);
        this.bookPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        this.layout.formatMainButtons(saveButton);
        this.layout.formatMainButtons(viewButton);
        this.layout.formatSearchBookTitle(bookTitle);
        this.layout.formatSearchBookDesc(bookAuthor);
        this.layout.formatSearchBookDesc(bookIsbn);
    }

    /**
     * Adds the listeners to the buttons
     * @param book The particular book
     */
    private void addButtonListeners(String[] book) {

        // Adds a listener to the save button so that a user can save to right panel
        this.saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] rtBook = new String[3];
                rtBook[0] = book[0];
                rtBook[1] = book[6];
                rtBook[2] = book[2];

                SearchForBooksView.this.right.addBooks(rtBook);
            }
        });

        // Adds a listener to the view button so that the user can view details page
        this.viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DetailsPageController dpController = new DetailsPageController(SearchForBooksView.this.left, SearchForBooksView.this.display, SearchForBooksView.this.right);
                dpController.displayDetailsPage(book[2]);
            }
        });
    }
}
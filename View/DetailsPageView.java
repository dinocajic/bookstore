package View;

import Controller.SearchForBooksController;
import GUIobjects.CMPanel;
import GUIobjects.RightPanel;
import Libraries.Layout;
import Model.SearchForBooksModel;
import View.ViewInterface.DetailsPageViewInterface;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import net.miginfocom.swing.MigLayout;

/**
 * Created by Dino Cajic on 11/3/2016.
 *
 * Displays the Details screen
 */
public class DetailsPageView implements DetailsPageViewInterface {

    CMPanel left;
    CMPanel display;
    RightPanel right;

    ImageIcon imageIcon;
    JButton save;
    JButton goBack;
    JLabel imageLabel;
    JLabel bookTitle;
    JLabel bookAuthor;
    JLabel isbn;
    JLabel shortSummary;
    JLabel longSummary;
    JLabel cost;
    JLabel qty;
    JLabel pubDate;
    JPanel panel   = new JPanel(new MigLayout());
    JPanel empty   = new JPanel();
    JPanel summary = new JPanel(new MigLayout());

    /**
     * @param display The display object that was instantiated in the ApplicationWindow class
     * @param right The right panel
     */
    public DetailsPageView(CMPanel left, CMPanel display, RightPanel right) {
        this.left    = left;
        this.display = display;
        this.right   = right;
    }

    /**
     * Displays the details page about a particular book
     * @param book The ISBN of a particular book
     */
    public void displayPage(String[] book) {
        this.display.resetToEmpty();

        this.initialize(book);
        this.modifyProperties();
        this.addSaveListener(book);

        this.panel.add(this.bookTitle,  "wrap");
        this.panel.add(this.bookAuthor, "wrap");
        this.panel.add(this.isbn,       "wrap");
        this.panel.add(this.pubDate,    "wrap");
        this.panel.add(this.empty,      "wrap");
        this.panel.add(this.cost,       "wrap");
        this.panel.add(this.qty,        "wrap");
        this.panel.add(this.save,       "wrap");
        this.panel.add(this.imageLabel, "west");

        this.summary.add(this.longSummary);

        this.display.add(this.panel);
        this.display.add(this.summary);
        this.display.add(this.goBack);
    }

    /**
     * Initializes the properties
     * @param book The book retrieved from the database
     */
    private void initialize(String[] book) {
        this.imageIcon    = new ImageIcon(book[7]);

        Image scaleThumb  = this.imageIcon.getImage().getScaledInstance(196, 293, Image.SCALE_DEFAULT);
        this.imageLabel   = new JLabel(new ImageIcon(scaleThumb));

        this.bookTitle    = new JLabel("<html>" + book[0] + "</html>");
        this.bookAuthor   = new JLabel("Author(s): " + book[1]);
        this.isbn         = new JLabel("ISBN-13: " + book[2]);
        this.shortSummary = new JLabel(book[3]);
        this.longSummary  = new JLabel("<html>" + book[4].replaceAll("\\n", "<br />") + "</html>");
        this.cost         = new JLabel(book[5]);
        this.qty          = new JLabel("Stock: " + book[6]);
        this.pubDate      = new JLabel("Published On: " + book[8]);
        this.save         = new JButton("Save");
        this.goBack       = new JButton("Go back to results");
    }

    /**
     * Modify the look of some of the properties
     */
    private void modifyProperties() {
        this.imageLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 10));
        this.bookTitle.setFont(new Font("Sans-serif", Font.BOLD, 30));
        this.bookAuthor.setFont(new Font("Sans-serif", Font.BOLD, 20));
        this.isbn.setFont(new Font("Sans-serif", Font.PLAIN, 15));
        this.pubDate.setFont(new Font("Sans-serif", Font.PLAIN, 15));
        this.cost.setFont(new Font("Sans-serif", Font.BOLD, 20));
        this.qty.setFont(new Font("Sans-serif", Font.PLAIN, 15));
        this.longSummary.setFont(new Font("Sans-serif", Font.PLAIN, 12));
        this.empty.setBorder(BorderFactory.createEmptyBorder(28, 50, 40, 50));
        this.summary.setMaximumSize(new Dimension(800, 500));
        this.goBack.setMaximumSize(new Dimension(200,50));

        Layout layout = new Layout();
        layout.formatMainButtons(this.save);
        layout.formatMainButtons(this.goBack);
    }

    /**
     * Adds a listener to the save button
     * @param book The book that was retrieved from the database
     */
    private void addSaveListener(String[] book) {
        // Allows the user to save the book to the right panel
        this.save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] rtBook = new String[3];
                rtBook[0] = book[0];
                rtBook[1] = book[7];
                rtBook[2] = book[2];

                try {
                    DetailsPageView.this.right.addBooks(rtBook);
                } catch(NullPointerException ex) {
                    JOptionPane.showMessageDialog(null, "Already saved.");
                }
            }
        });

        // Allows the user to go back to the previous results
        this.goBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SearchForBooksModel model           = new SearchForBooksModel();
                String[][] lastBooks                = model.generateDisplayResultsFromGenres();

                SearchForBooksController controller = new SearchForBooksController(left, display, right);
                controller.displayResults(lastBooks, false);
            }
        });
    }
}
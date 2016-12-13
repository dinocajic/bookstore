package GUIobjects;

import Config.Config;
import Controller.DetailsPageController;
import Controller.SearchForBooksController;
import Libraries.Layout;
import net.miginfocom.swing.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Dino Cajic on 11/3/2016.
 *
 * The right portion of the screen
 */
public class RightPanel {

    CMPanel left;
    CMPanel right;
    CMPanel display;
    RightPanel rightPanel;

    ImageIcon facebook;
    ImageIcon google;
    ImageIcon instagram;
    ImageIcon linkedIn;
    ImageIcon pinterest;
    ImageIcon twitter;

    private static ArrayList<String[]> books = new ArrayList<>();
    private static ArrayList<JLabel> images  = new ArrayList<>();

    public JButton searchButton              = new JButton("Search");
    public JTextField searchText             = new JTextField("Search for Books", 50);
    public JButton saved                     = new JButton("Saved");

    /**
     * @param right The right CMPanel. The right portion of the screen
     * @param display The main display.
     */
    public RightPanel(CMPanel left, CMPanel right, CMPanel display) {
        this.left    = left;
        this.right   = right;
        this.display = display;
    }

    /**
     * Adds a book to the books table
     * @param book The book to be added to the saved list
     *             book[0] = title
     *             book[1] = image
     *             book[2] = isbn
     */
    public void addBooks(String[] book) {
        if (books.size() == 5) {
            JOptionPane.showMessageDialog(null, "Max number reached. Remove to add more.");
            return;
        }

        for(String[] isbn: books) {
            if (isbn[2].equals(book[2])) {
                JOptionPane.showMessageDialog(null, "Book already saved.");
                return;
            }
        }

        // Since it gets generated again in displayRightPanel()
        images.clear();
        books.add(book);

        this.displayRightPanel();
    }

    /**
     * Removes the book from the saved list
     * @param book The book to be removed
     */
    public void removeBook(String[] book) {
        books.remove(book);

        // Since it gets generated again in displayRightPanel()
        images.clear();
        this.displayRightPanel();
    }

    /**
     * Displays the right portion of the screen
     */
    public void displayRightPanel() {
        this.right.resetToEmpty();

        this.initializeWebLinks();
        this.modifySizes();
        this.addClearListener();

        this.right.add(this.searchText);
        this.right.add(this.searchButton);
        this.right.add(this.saved);

        JPanel savedPanelWithBorder = new JPanel(new MigLayout());
        savedPanelWithBorder.setBackground(Config.panel_saved_color);
        savedPanelWithBorder.setPreferredSize(new Dimension(250, 500));

        Layout buttonFormat = new Layout();

        for(String[] book: books) {
            JPanel savedPanel = new JPanel(new MigLayout());
            savedPanel.setBackground(Config.panel_saved_color);
            // Load the image
            ImageIcon bookImage  = new ImageIcon(book[1]);
            // Resize the image
            Image     scaleThumb     = bookImage.getImage().getScaledInstance(50, 75, Image.SCALE_DEFAULT);
            // Create a JLabel: only accepts ImageIcon, so have to change back from Image
            JLabel    imageLabel = new JLabel(new ImageIcon(scaleThumb));
            imageLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 10));

            JLabel title   = new JLabel(book[0]);
            buttonFormat.formatSavedTitleFont(title);

            JTextField isbn = new JTextField(book[2]);
            buttonFormat.formatSavedDescFont(isbn);

            JButton remove = new JButton("Remove");
            buttonFormat.formatSavedButtons(remove);

            JButton details = new JButton("Details");
            buttonFormat.formatSavedButtons(details);

            savedPanel.add(title,      "wrap");
            savedPanel.add(isbn,       "wrap");
            savedPanel.add(remove,     "split 2");
            savedPanel.add(details,    "wrap");
            savedPanel.add(imageLabel, "dock west");

            remove.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    RightPanel.this.removeBook(book);
                }
            });

            // Used to be RightPanel.this.RightPanel... the one with no left !
            details.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    DetailsPageController dpController = new DetailsPageController(RightPanel.this.left, RightPanel.this.display, RightPanel.this);
                    dpController.displayDetailsPage(book[2]);
                }
            });

            savedPanelWithBorder.add(savedPanel, "wrap");
        }

        if(searchButton.getActionListeners().length < 1) {  //add the listener to the button if it's missing
            searchButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    SearchForBooksController searchForBooksController = new SearchForBooksController(RightPanel.this.left, RightPanel.this.display, RightPanel.this);
                    searchForBooksController.findBooks();
                }
            });
        }

        this.right.add(savedPanelWithBorder);

        JPanel linksPanel = new JPanel(new MigLayout());
        linksPanel.setPreferredSize(new Dimension(250, 200));

        int i = 0;
        for (JLabel image: images) {
            addBrowserListeners(image);

            if (i % 2 == 0 && i != 0) {
                linksPanel.add(image, "wrap");
                i = 0;
            } else {
                linksPanel.add(image);
                i++;
            }
        }

        this.right.add(linksPanel);

    }

    /**
     * Initializes the properties
     * Sets the images as labels
     */
    private void initializeWebLinks() {
        this.facebook  = new ImageIcon(Config.facebook_icon);
        this.google    = new ImageIcon(Config.google_icon);
        this.instagram = new ImageIcon(Config.instagram_icon);
        this.linkedIn  = new ImageIcon(Config.linked_in_icon);
        this.pinterest = new ImageIcon(Config.pinterest_icon);
        this.twitter   = new ImageIcon(Config.twitter_icon);

        images.add(new JLabel("", this.facebook,  JLabel.CENTER));
        images.add(new JLabel("", this.google,    JLabel.CENTER));
        images.add(new JLabel("", this.instagram, JLabel.CENTER));
        images.add(new JLabel("", this.linkedIn,  JLabel.CENTER));
        images.add(new JLabel("", this.pinterest, JLabel.CENTER));
        images.add(new JLabel("", this.twitter,   JLabel.CENTER));
    }

    /**
     * Modifies the size of the search attribute
     */
    private void modifySizes() {
        this.searchText.setPreferredSize(new Dimension(50, 36));

        Layout layout = new Layout();
        layout.formatMainButtons(this.searchButton);
        layout.formatHeadings(this.saved, 250);

        for (int i = 0; i <= 5; i++) {
            images.get(i).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        }
    }

    /**
     * Adds a listener to clear the content before starting a search on mouse click
     */
    private void addClearListener() {
        this.searchText.addMouseListener(new MouseListener() {

            public void mouseClicked(MouseEvent e) {
                if (RightPanel.this.searchText.getText().equals("Search for Books")) {
                    RightPanel.this.searchText.setText("");
                }
            }

            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
        });
    }

    /**
     * Adds listeners to the images
     */
    private void addBrowserListeners(JLabel label) {
        label.addMouseListener(new MouseListener() {

            public void mouseClicked(MouseEvent e) {
                switch(label.getIcon().toString()) {
                    case Config.facebook_icon:
                        RightPanel.this.openBrowser(Config.facebook);
                        break;
                    case Config.twitter_icon:
                        RightPanel.this.openBrowser(Config.twitter);
                        break;
                    case Config.instagram_icon:
                        RightPanel.this.openBrowser(Config.instagram);
                        break;
                    case Config.google_icon:
                        RightPanel.this.openBrowser(Config.google);
                        break;
                    case Config.pinterest_icon:
                        RightPanel.this.openBrowser(Config.pinterest);
                        break;
                    case Config.linked_in_icon:
                        RightPanel.this.openBrowser(Config.linkedin);
                        break;
                }
            }

            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
        });
    }

    /**
     * Opens the browser page
     * @param urlString The web-address
     */
    private void openBrowser(String urlString) {
        try {
            Desktop.getDesktop().browse(new URL(urlString).toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
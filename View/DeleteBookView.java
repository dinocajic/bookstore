package View;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import Config.Config;
import Controller.DeleteBookController;
import GUIobjects.CMPanel;
import Libraries.Layout;
import View.ViewInterface.DeleteBookViewInterface;

import javax.swing.JTable;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import net.miginfocom.swing.MigLayout;

/**
 * Created by Dino Cajic on 10/29/2016.
 *
 * Displays the Delete Book screens
 */
public class DeleteBookView implements DeleteBookViewInterface {

    CMPanel display;
    JPanel  mainLabelPane = new JPanel(new MigLayout());

    Layout layout         = new Layout();

    JLabel mainLabel      = new JLabel("Remove Book from Inventory");
    JTextField search     = new JTextField("Search for Books to Delete by ISBN");

    JButton searchButton  = new JButton("Search");

    DefaultTableModel dm  = new DefaultTableModel();
    JTable table          = new JTable(dm);

    String[] columnNames  = {"Book Title",
            "ISBN",
            "Availability",
            "On Order Qty",
            "Price",
            "Delete?"};

    public Object[][] data;

    /**
     * @param display The display object that was instantiated in the ApplicationWindow class
     */
    public DeleteBookView(CMPanel display) {
        this.display = display;
    }

    /**
     * Displays the initial form when trying to delete a book.
     * Modified by Anh Pham on 10/29/2016.
     * Redone by Dino Cajic on 11/01/2016.
     */
    public void displayDeleteBookForm() {
        this.display.resetToEmpty();

        this.modifySizes();

        // START: Table
        dm.setDataVector(data, columnNames );
        JScrollPane ScrollPane = new JScrollPane(table);
        ScrollPane.setPreferredSize(new Dimension(509,50));
        // END: Table

        this.addSearchListener();
        this.addTableListener();
        this.addSearchFieldListener();

        // Main Display Label
        this.mainLabelPane.add(this.mainLabel);

        JPanel panel = new JPanel(new MigLayout());

        panel.add(this.mainLabelPane, "dock north");
        panel.add(this.search,        "split 2");
        panel.add(this.searchButton,  "wrap");
        panel.add(ScrollPane);

        this.display.add(panel);
    }

    /**
     * Modifies the sizes of the form fields
     */
    private void modifySizes() {
        this.mainLabel.setFont(Config.title_font);
        this.mainLabel.setBorder(new EmptyBorder(10, 10, 10, 10));

        this.layout.formatDisplayTitle(this.mainLabelPane);
        this.layout.formatMainButtons(this.searchButton);

        this.search.setPreferredSize(new Dimension(430, 36));
    }

    /**
     * Adds a mouse click listener to the table.
     * If the user clicks on the Remove button, the method calls the DeleteBookController to delete the specific ISBN.
     */
    private void addTableListener() {
        table.addMouseListener(new MouseListener() {

            /**
             * If the mouse has been clicked on the table, it grabs the row and column and can pass data to the model
             * @param e Mouse Click event
             */
            public void mouseClicked(MouseEvent e) {
                JTable target = (JTable)e.getSource();
                int row       = target.getSelectedRow();
                int column    = target.getSelectedColumn();

                String remove = "";

                try {
                    remove = (String)DeleteBookView.this.data[row][column];
                } catch (ArrayIndexOutOfBoundsException eAr) {
                    // do nothing
                } catch (NullPointerException eNul) {
                    // do nothing
                }

                if (column == 5 && remove.equals("Remove")) {
                    String isbn = (String)DeleteBookView.this.data[row][1];

                    DeleteBookController controller = new DeleteBookController();
                    String success = controller.removeBook(isbn);

                    if (success.equals("Success")) {
                        DeleteBookView.this.data[row] = null;
                        DeleteBookView.this.displayDeleteBookForm();
                        DeleteBookView.this.search.setText(success);
                    }else{
                        DeleteBookView.this.search.setText(success);
                    }
                }
            }

            // These methods are needed
            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
        });
    }

    /**
     * Adds a listener to the search button. If the search is empty, change to red.
     * Otherwise, search for books to display them in the table.
     */
    private void addSearchListener() {
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String isbn = DeleteBookView.this.search.getText();
                DeleteBookController controller = new DeleteBookController();

                if (controller.checkFormFields(isbn)) {
                    DeleteBookView.this.search.setBackground(Config.warning_color);
                } else {
                    DeleteBookView.this.search.setBackground(Color.white);
                    DeleteBookView.this.data = controller.searchForBooks(isbn);
                    DeleteBookView.this.displayDeleteBookForm();
                }
            }
        });
    }

    /**
     * Clears the content of the search bar on click
     */
    private void addSearchFieldListener() {
        this.search.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                DeleteBookView.this.search.setText("");
            }

            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
        });
    }
}
package GUIobjects;

import Config.Config;
import Controller.SearchForBooksController;
import Libraries.Layout;
import Model.SearchForBooksModel;
import net.miginfocom.swing.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Zachary Shoults on 10/01/2016.
 *
 * The "Code Monster's Panel" exists to reduce the multi-step complexity of creating JPanels and
 * have shared methods between the three sections of the GUI:  Left, Display, Right.  
 * Setting Layout is done at instantiation instead of a second step.  Some other convenient methods are here.
 */

public class CMPanel extends JPanel{

    Layout layout = new Layout();
        
    /**
     *Instantiate the Panel with the given layout.
     * @param layout The layout that's to be used
     */
    public CMPanel(MigLayout layout){
        this.setLayout(layout); 
    }
    
    /**
     * Adding an image involves 2 objects and an add() call. Combined them into one method.
     * This will display the image in its native size. If it's larger than the parent, it will get cut off.
     * Likewise, it will not expand to fit the JLabel it is in.  (Maybe can set the layout for the JLabel...)
     * @param filepath The location of the image (.png preferred).
     */
    public void addImage(String filepath){
        ImageIcon cmIcon = new ImageIcon(filepath); 
        JLabel label = new JLabel("", cmIcon, JLabel.CENTER);
        this.add(label);
    }
    
    /**
     * To add the menu-like options on the left side, this method makes a label and its corresponding
     * buttons all in one line.
     * @param label  The text for the label to display
     * @param buttons The text for the buttons to display
     */
    public void addLabelWithButtons(String label, String[] buttons, CMPanel left_display, CMPanel main_display, RightPanel right_panel){
        JButton title = new JButton(label.toUpperCase());
        this.layout.formatHeadings(title, 250);

        this.add(title);

        for (String button : buttons) {
            JButton newButton = new JButton(button.toUpperCase());
            this.layout.formatLeftButtons(newButton);

            newButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String searchText = newButton.getText();

                    SearchForBooksController controller = new SearchForBooksController(left_display, main_display, right_panel);
                    controller.displayResults(controller.processSearch(searchText), false);
                }
            });

            this.add(newButton);
        }
    }

    /**
     * Adds checkboxes underneath the left side category buttons to help narrow down results
     * @param label Checkbox title
     * @param genreOptions The different genres
     * @param left_display The left portion of the screen
     * @param main_display The main display
     * @param right_panel The right portion of the screen
     */
    public void addResultFilterCheckboxes(String label, String[] genreOptions, CMPanel left_display, CMPanel main_display, RightPanel right_panel){
        // Don't make the "Filter by Genre" label if there are no checkboxes
        if(genreOptions == null || genreOptions[0].equals("")) {
            return;
        }

        JButton title = new JButton(label);
        title.setEnabled(false);
        this.layout.formatHeadings(title, 250);

        this.add(title);

        // Will be restricted by SearchForBooksModel to max of 8
        for (String option : genreOptions) {
            if (option == null || option.equals("")) {
                continue;
            }

            JCheckBox checkOption = new JCheckBox(option);
            checkOption.setBackground(Config.panel_saved_color);
            checkOption.setBorder(new EmptyBorder(10, 10, 10, 10));
            checkOption.setFont(Config.default_font);
            checkOption.setIcon(new ImageIcon("./src/img/icons/checkbox_empty_icon.png"));
            checkOption.setSelectedIcon(new ImageIcon("./src/img/icons/checkbox_full_icon.png"));
            checkOption.setRolloverIcon(new ImageIcon("./src/img/icons/checkbox_rollover_icon.png"));

            checkOption.addItemListener(e -> {
                // If checkbox is checked
                if (e.getStateChange() == 1) {
                    SearchForBooksModel.genreFilters.add(checkOption.getText());
                } else {
                    SearchForBooksModel.genreFilters.remove(checkOption.getText());
                }

                SearchForBooksModel model = new SearchForBooksModel();
                String[][] filteredBooks  = model.generateDisplayResultsFromGenres();
                SearchForBooksController controller = new SearchForBooksController(left_display, main_display, right_panel);
                controller.displayResults(filteredBooks, false);
            });

            this.add(checkOption);
        }
    }

    /**
     * This static method is used to clear the left panel when doing activities that are not displaying
     * the results of an inventory search.
     * @param label Checkbox title
     * @param buttons The different buttons on the left
     * @param left_display The left portion of the screen
     * @param main_display The main display
     * @param right_panel The right portion of the screen
     */
    public static void refreshLeftButtons(String label, String[] buttons, CMPanel left_display, CMPanel main_display, RightPanel right_panel){
        Layout buttonFormat = new Layout();

        JButton title = new JButton(label.toUpperCase());
        buttonFormat.formatHeadings(title, 250);

        left_display.add(title);

        for (String button : buttons) {
            JButton newButton = new JButton(button.toUpperCase());
            buttonFormat.formatLeftButtons(newButton);

            newButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String searchText = newButton.getText();

                    SearchForBooksController controller = new SearchForBooksController(left_display, main_display, right_panel);
                    controller.displayResults(controller.processSearch(searchText), false);
                }
            });

            left_display.add(newButton);
        }
    }

    /**
     * Clears the CMPanel object
     */
    public void resetToEmpty(){
        this.removeAll();
        this.updateUI();
    }
}
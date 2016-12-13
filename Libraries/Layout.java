package Libraries;

import Config.Config;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Formats the look of certain portions on the screen
 *
 * Created by Dino on 11/14/2016.
 */
public class Layout {

    /**
     * Formats the look of the buttons
     * @param button The button to be displayed
     */
    public void formatMainButtons(JButton button) {
        button.setBackground(Config.main_buttons);
        button.setForeground(Color.WHITE);
        button.setFont(Config.default_font);
        button.setBorder(new EmptyBorder(10, 20, 10, 20));
    }

    /**
     * Formats the look of the left panel buttons
     * @param button The button on the left side of the screen.
     */
    public void formatLeftButtons(JButton button) {
        button.setForeground(Color.WHITE);
        button.setBackground(Config.left_button_color);
        button.setFont(Config.default_font);
        Border line = new LineBorder(Color.GRAY);
        Border margin = new EmptyBorder(20, 20, 20, 20);
        Border compound = new CompoundBorder(line, margin);
        button.setBorder(compound);
    }

    /**
     * Formats the heading titles
     * @param button The heading button (disabled)
     * @param width The width of the button
     */
    public void formatHeadings(JButton button, int width) {
        this.formatMainButtons(button);
        button.setEnabled(false);
        button.setText("<html><font color='#FFFFFF'>" + button.getText().toUpperCase() + "</font></html>");
        button.setPreferredSize(new Dimension(width, 36));
    }

    /**
     * Formats the look of the buttons located in the right panel
     * @param button The button to be formated
     */
    public void formatSavedButtons(JButton button) {
        this.formatMainButtons(button);
        button.setBorder(new EmptyBorder(11, 10, 10, 11));
        button.setFont(new Font("Sans-serif", Font.PLAIN, 10));
    }

    /**
     * Formats the look of the titles in the saved portion
     * @param title The title of the book
     */
    public void formatSavedTitleFont(JLabel title) {
        title.setFont(Config.default_bolt_font);
    }

    /**
     * Formats the look of the text right below the title in the saved right panel
     * @param isbn The ISBN of the book
     */
    public void formatSavedDescFont(JTextField isbn) {
        isbn.setFont(Config.default_font);
        isbn.setEditable(false);
        isbn.setBorder(BorderFactory.createEmptyBorder());
    }

    /**
     * Formats the look of the book title in the search
     * @param title The title of the book
     */
    public void formatSearchBookTitle(JLabel title) {
        this.formatSavedTitleFont(title);
    }

    /**
     * Formats the look of the description in the search
     * @param desc The description
     */
    public void formatSearchBookDesc(JLabel desc) {
        desc.setFont(Config.default_font);
    }

    /**
     * Formats the display title heading on each page
     * @param title The title of the heading
     */
    public void formatDisplayTitle(JPanel title) {
        title.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Config.heading_color));
        title.setPreferredSize(Config.display_panel_titles);
    }

    /**
     * Formats the look of the checkboxes
     * @param radioButton The checkbox
     */
    public void formatRadioButton(JRadioButton radioButton) {
        radioButton.setBorder(new EmptyBorder(10, 10, 10, 10));
        radioButton.setFont(Config.default_font);
        radioButton.setIcon(new ImageIcon("./src/img/icons/checkbox_empty_icon.png"));
        radioButton.setSelectedIcon(new ImageIcon("./src/img/icons/checkbox_full_icon.png"));
        radioButton.setRolloverIcon(new ImageIcon("./src/img/icons/checkbox_rollover_icon.png"));
    }
}
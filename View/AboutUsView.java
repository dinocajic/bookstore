package View;

import Config.Config;
import GUIobjects.CMPanel;
import net.miginfocom.swing.MigLayout;
import View.ViewInterface.*;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Zachary Shoults on 11/22/2016.
 *
 * Creates the About Us page
 */
public class AboutUsView implements AboutUsViewInterface {

    private CMPanel display;
    private JLabel companyIcon             = new JLabel("", new ImageIcon(Config.company_logo),  JLabel.LEFT);
    private JTextPane companyInformation   = new JTextPane();
    private JLabel developerIcon           = new JLabel("", new ImageIcon(Config.cm_logo_70px),  JLabel.LEFT);
    private JTextPane developerInformation = new JTextPane();

    /**
     * @param display_panel The main display
     */
    public AboutUsView(CMPanel display_panel) {
        this.display = display_panel;
    }

    /**
     * Print to the main display the contact information for both the client and
     * the developers.  Information will include names, addresses, and phone numbers.
     */
    @Override
    public void displayAboutUsDetails() {
        /*Note:  the MigLayouts for each panel are identical, but if they are stored in a variable, and then each
          panel is instantiated with the same variable, the formatting messes up and information is cut off.   */

        this.display.resetToEmpty();

        JPanel companyPanel = new JPanel();
        companyPanel.setLayout(new MigLayout("wrap 1","100px[650px!]","")); //left space of 100px, container width of 600px
        this.formatTextPane(this.companyInformation); //make text pane accept HTML and formatting

        companyInformation.setText(this.getCompanyDetails()); //get the text string and apply

        companyPanel.add(companyIcon);        //add the icon
        companyPanel.add(companyInformation); //add the text

        JPanel developerPanel = new JPanel();
        developerPanel.setLayout(new MigLayout("wrap 1","100px[650px!]","")); //left space of 100px, container width of 600px
        this.formatTextPane(this.developerInformation); //make text pane accept HTML and formatting

        developerInformation.setText(this.getDeveloperDetails());

        developerPanel.add(developerIcon);
        developerPanel.add(developerInformation);

        this.display.add(companyPanel);
        this.display.add(developerPanel);
    }

    /**
     * Formats the company details
     * @return Company details
     */
    private String getCompanyDetails() {
        return String.format(
            "<font size=5><b>%s</b></font> <br>  " + //comp name
            "%s <br>" +                              //person name
            "%s <br>" +                              //phone
            "%s <br>" +                              //street
            "%s, %s %s <br>&nbsp",                   //city,st,zip
            Config.company_name,
            Config.comp_contact_alias1,
            Config.comp_contact_phone,
            Config.comp_address_street,
            Config.comp_address_city,
            Config.comp_address_state,
            Config.comp_address_zip
        );
    }

    /**
     * Formats the developer details
     * @return Developer details
     */
    private String getDeveloperDetails() {
        return String.format(
            "<font size=5><b>%s</b></font> <br>  " + //comp name
            "%s <br>" +                              //dev name1
            "%s <br>" +                              //dev name2
            "%s <br>" +                              //dev name3
            "%s <br>" +                              //phone
            "%s <br>" +                              //street
            "%s, %s %s",                             //city,st,zip
            Config.software_developers,
            Config.dev_contact_alias1,
            Config.dev_contact_alias2,
            Config.dev_contact_alias3,
            Config.dev_contact_phone,
            Config.dev_address_street,
            Config.dev_address_city,
            Config.dev_address_state,
            Config.dev_address_zip
        );
    }

    /**
     * Formats the look of the pane
     * @param tp The text pane
     */
    private void formatTextPane(JTextPane tp) {
        tp.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,23));
        tp.setContentType("text/html");
        tp.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, true); //enables font settings to apply in "html-mode"
        tp.setEditable(false);   //able to highlight
        tp.setBackground(null);  //transparent
        tp.setBorder(null);      //transparent
    }
}
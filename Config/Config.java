package Config;

import java.awt.*;

/**
 * Created by Dino Cajic on 11/3/2016.
 *
 * Contains Company information to be used throughout the system.
 */
public class Config {

    // Company Information *********************************************************************************************
    public static String company_name = "CM Bookstore";

    public static String comp_address_street    = "9793 Studebaker Lane";
    public static String comp_address_city      = "Wisconsin Rapids";
    public static String comp_address_state     = "WI";
    public static String comp_address_zip       = "54494";

    public static String comp_contact_alias1    = "Valerius Mattie, Owner";
    public static String comp_contact_phone     = "(715) 237-2010";

    // Software Development information ********************************************************************************
    public static String software_developers    = "Code Monsters, Inc.";
    public static String dev_address_street     = "75 Piedmont Avenue, Suite 1190";
    public static String dev_address_city       = "Atlanta";
    public static String dev_address_state      = "GA";
    public static String dev_address_zip        = "30302";

    public static String dev_contact_alias1     = "Dino Cajic, Team Lead & Developer";
    public static String dev_contact_alias2     = "Zachary Shoults, Developer";
    public static String dev_contact_alias3     = "Anh Pham , Quality Assurance";
    public static String dev_contact_phone      = "(404) 893-2958";

    public static String user_manual_link       = "https://www.dropbox.com/s/tbep8qy9h2dgft7/User-Manual.pdf?dl=0";

    // Images **********************************************************************************************************
    public static String cm_logo_50px = "./src/img/CM_50pxTall.png";
    public static String cm_logo_70px  = "./src/img/CM_70pxTall.png";
    public static String company_logo = "./src/img/bookstore.png";
    public static String info_button  = "./src/img/info-button.png";

    // Social Media ****************************************************************************************************
    public static final String facebook  = "https://facebook.com/firstchoicewheelsandtires";
    public static final String twitter   = "https://twitter.com/fcwt";
    public static final String pinterest = "https://www.pinterest.com/FCWT/";
    public static final String linkedin  = "https://www.linkedin.com/company/first-choice-wheels-and-tires";
    public static final String google    = "https://plus.google.com/+Firstchoicewheelsandtires";
    public static final String instagram = "https://instagram.com/firstchoicewheelsandtires";

    public static final String facebook_icon  = "./src/img/logos/facebook.jpg";
    public static final String google_icon    = "./src/img/logos/google_plus.jpg";
    public static final String instagram_icon = "./src/img/logos/instagram.jpg";
    public static final String linked_in_icon = "./src/img/logos/linked_in.jpg";
    public static final String pinterest_icon = "./src/img/logos/pinterest.jpg";
    public static final String snapchat_icon  = "./src/img/logos/snapchat.jpg";
    public static final String twitter_icon   = "./src/img/logos/twitter.jpg";

    // Return Policy ***************************************************************************************************
    public static String returnPolicy = "Regular orders can be returned within 7 days " +
            "\nof receipt in their original packaging. Please notify the store by phone " +
            "\nor email before the return. The client is responsible for returning the " +
            "\nitem(s) pre-paid and insured to the Store. Credit issued will not " +
            "\ninclude original inbound shipping and insurance charges. We reserve the " +
            "\nright to not accept a return and may require a re-stocking or repair fee " +
            "\nif the item has been at all damaged. While our policies are firm for " +
            "\nobvious reasons, we will do everything we can to work with you and make " +
            "\nsure your experience with us is positive.";

    // File Paths ******************************************************************************************************
    public static String PRE_FILEPATH = "src/";
    public static String INVENTORY_DIRECTORY         = Config.PRE_FILEPATH + "inventory";
    public static String INVENTORY_FILEPATH          = Config.INVENTORY_DIRECTORY + "/allBooks";
    public static String ORDERS_FILEPATH             = Config.PRE_FILEPATH + "orders/allOrders";
    public static String SALES_FILEPATH              = Config.PRE_FILEPATH + "sales/allSales";
    public static String LOG_IN_CREDENTIALS_FILEPATH = Config.PRE_FILEPATH + "login/credentials";

    // Fonts ***********************************************************************************************************
    public static Font default_font        = new Font("Sans-serif", Font.PLAIN, 11);
    public static Font default_bolt_font   = new Font("Sans-serif", Font.BOLD, 11);
    public static Font logged_in_user_font = new Font("Sans-serif", Font.BOLD, 14);
    public static Font title_font          = new Font("Sans-Serif", Font.BOLD, 16);

    // Colors **********************************************************************************************************
    public static Color main_buttons      = new Color(44, 61, 79);
    public static Color left_button_color = new Color(127, 140, 141);
    public static Color menu_panel_color  = new Color(224, 224, 224);
    public static Color menu_hover_option = new Color(163, 184, 204);  //same color as JMenu setSelected(true)
    public static Color panel_saved_color = new Color(224, 224, 224);
    public static Color warning_color     = left_button_color;
    public static Color heading_color     = left_button_color;
    public static Color dropdown_color    = new Color(228, 228, 228);  //java default app color: (238,238,238)
    public static Color pos_active_panel  = left_button_color;

    // Sizes ***********************************************************************************************************
    public static Dimension preferred_window_size  = new Dimension(1615, 940);
    public static Dimension minimum_window_size    = new Dimension(800, 800);
    public static Dimension display_panel_size     = new Dimension(1300, 660);
    public static Dimension display_panel_titles   = new Dimension(500, 50);

    // Book categories *************************************************************************************************
    public static String[] category = {"Any", "Non-Fiction", "Humor",
            "Biography", "Cookbooks", "Cooking Method",
            "US Regional", "Fiction", "Graphic Novel",
            "International", "Horror", "History", "Literacy",
            "Diet", "Fantasy", "Textbooks", "Politics",
            "Social Sciences", "Religion", "Sports", "Christianity",
            "Business", "Others", "Geography", "Science", "Math",
            "Education", "Computers", "Technology", "Buddhism"};

    public static String[] leftPanelButtons = {category[1], category[2], category[3], category[4], category[7], category[9], category[15], category[19]};

    public static String[] authorsButtons   = {"JK Rowling", "Phil Collins", "Bill OReilly", "Tim Tigner", "Bill Martin Jr", "Erin Stanton", "Jeff Kinney"};

    // Random **********************************************************************************************************
    public static String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
}
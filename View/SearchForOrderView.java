package View;

import Config.Config;
import Controller.AddOrderToInventoryController;
import Controller.SearchForOrderController;
import GUIobjects.CMPanel;
import Libraries.Layout;
import View.ViewInterface.SearchForOrderViewInterface;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Calendar;

import net.miginfocom.swing.MigLayout;

/**
 * Created by Dino Cajic on 10/29/2016.
 *
 * Displays the searchDate for oder screens
 */
public class SearchForOrderView implements SearchForOrderViewInterface {

    CMPanel display;

    JComboBox<String> fromMonth;
    JComboBox<String> fromYear;
    JComboBox<String> toMonth;
    JComboBox<String> toYear;

    String[] months             = Config.months;
    String[] years              = new String[50];
    JLabel labelMain            = new JLabel("Search for Order");
    JLabel message              = new JLabel("");
    JLabel dateRange            = new JLabel("");
    JButton searchDate          = new JButton("Search by Date");
    JTextField orderID          = new JTextField("Order ID");
    JButton searchID            = new JButton("Search by ID");
    ArrayList<JTextField> order = new ArrayList<>();
    Layout layout               = new Layout();
    JPanel mainLabelPane        = new JPanel(new MigLayout());

    /**
     * @param display The display object that was instantiated in the ApplicationWindow class
     */
    public SearchForOrderView(CMPanel display) {
        this.display = display;
    }

    /**
     * Displays the initial searchDate form. Should include a date range. I.e. searchDate from this date to that date
     */
    public void displayInitialSearchForm() {
        this.display.resetToEmpty();

        this.initializeProperties();
        this.adjustPropertySizes();
        this.addSearchListener();
        this.addSearchIDListener();
        this.addClearListener();

        JPanel panel = new JPanel(new MigLayout());
        this.mainLabelPane.add(this.labelMain);

        panel.add(this.mainLabelPane, "span");
        panel.add(this.fromMonth);
        panel.add(this.fromYear);

        panel.add(new JLabel(" - "));

        panel.add(this.toMonth);
        panel.add(this.toYear);
        panel.add(this.searchDate);
        panel.add(new JLabel(" - OR - "));
        panel.add(this.orderID,    "span 2");
        panel.add(this.searchID,   "wrap, span");
        panel.add(this.message,    "span");
        panel.add(this.dateRange,  "span");

        JPanel orderPanel = new JPanel(new MigLayout());

        JScrollPane scrollPane = new JScrollPane(orderPanel);
        scrollPane.setPreferredSize(new Dimension(600, 530));

        // Add the orders to the panel
        for (JTextField anOrder : this.order) {
            JLabel orderNumber = new JLabel("Order Number: ");
            orderNumber.setFont(Config.default_font);

            orderPanel.add(orderNumber);
            orderPanel.add(anOrder);

            JButton viewOrder = new JButton("View Order");
            layout.formatMainButtons(viewOrder);

            orderPanel.add(viewOrder, "wrap");
            viewOrder.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    AddOrderToInventoryController addController = new AddOrderToInventoryController(SearchForOrderView.this.display);
                    addController.displayInitialScreen(anOrder.getText());
                }
            });
        }

        panel.add(scrollPane, "dock south");

        this.display.add(panel);
    }

    /**
     * Initializes the properties
     */
    private void initializeProperties() {
        Calendar year = Calendar.getInstance();

        for (int i = 0; i < this.years.length; i++) {
            Integer yr = (year.get(Calendar.YEAR) - i);
            this.years[i] = yr.toString();
        }

        this.fromMonth = new JComboBox<>(months);
        this.fromYear  = new JComboBox<>(years);
        this.toMonth   = new JComboBox<>(months);
        this.toYear    = new JComboBox<>(years);
        this.fromMonth.setSelectedIndex(Calendar.getInstance().get(Calendar.MONTH) - 1); //start last month
        this.toMonth.setSelectedIndex(Calendar.getInstance().get(Calendar.MONTH));  //to next
    }

    /**
     * Adjusts the sizes of certain properties
     */
    private void adjustPropertySizes() {
        this.layout.formatDisplayTitle(this.mainLabelPane);

        this.labelMain.setFont(Config.title_font);
        this.labelMain.setBorder(new EmptyBorder(10, 10, 10, 10));

        this.dateRange.setBorder(new EmptyBorder(10, 0, 10, 10));
        this.fromMonth.setBackground(Config.dropdown_color);
        this.fromYear.setBackground(Config.dropdown_color);
        this.toMonth.setBackground(Config.dropdown_color);
        this.toYear.setBackground(Config.dropdown_color);
        this.orderID.setPreferredSize(new Dimension(150, 40));
        this.orderID.setMinimumSize(new Dimension(150, 40));

        layout.formatMainButtons(this.searchDate);
        layout.formatMainButtons(this.searchID);

    }

    /**
     * Adds a listener to the searchDate button
     * Modified by Zachary Shoults
     */
    private void addSearchListener() {
        this.searchDate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fromMonth      = (String)SearchForOrderView.this.fromMonth.getSelectedItem();
                int    fromMonthIndex = SearchForOrderView.this.fromMonth.getSelectedIndex();
                String fromYearStr    = (String)SearchForOrderView.this.fromYear.getSelectedItem();
                int    fromYear       = Integer.parseInt(fromYearStr);

                String toMonth        = (String)SearchForOrderView.this.toMonth.getSelectedItem();
                int    toMonthIndex   = SearchForOrderView.this.toMonth.getSelectedIndex();
                String toYearStr      = (String)SearchForOrderView.this.toYear.getSelectedItem();
                int    toYear         = Integer.parseInt(toYearStr);

                SearchForOrderController controller = new SearchForOrderController();
                String check = controller.checkFormFields(fromMonthIndex, fromYear, toMonthIndex, toYear);

                if (!check.equals("The following errors occurred: ")) {
                    SearchForOrderView.this.message.setText("<html>" + check.replaceAll("\n", "<br />") + "</html>");
                    return;
                }

                // Clear the errors if any
                SearchForOrderView.this.message.setText("");

                String[][] orderContent = controller.getOrderContent(fromMonthIndex, fromYear, toMonthIndex, toYear);

                for(String[] order: orderContent) {

                    JTextField orderField = new JTextField(order[0], 25);
                    orderField.setEditable(false);
                    if(order[1].equals("received")){
                        orderField.setBackground(Color.decode("#93DB70"));
                    }
                    orderField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
                    orderField.setFont(new Font("Sans-serif", Font.BOLD, 11));
                    orderField.setPreferredSize(new Dimension(25, 30));

                    boolean inOrder = false;

                    for (JTextField test: SearchForOrderView.this.order) {
                        if (test.getText().equals(orderField.getText())) {
                            inOrder = true;
                        }
                    }

                    if (inOrder) {
                        continue;
                    }

                    SearchForOrderView.this.order.add(orderField);
                }

                SearchForOrderView.this.displayInitialSearchForm();

            }
        });
    }

    /**
     * Adds a listener to the search by ID field
     * Created by Zachary Shoults
     */
    private void addSearchIDListener() {
        this.searchID.addActionListener(e -> {
            String order_id = SearchForOrderView.this.orderID.getText().trim();
            SearchForOrderController controller = new SearchForOrderController();
            String[] orderDetails = controller.getOrderByID(order_id);

            if (orderDetails == null) {
                SearchForOrderView.this.message.setText("<html> Order not found.</html>");
                return;
            }

            //Schema: OrderID, Books, DateOrdered, DateExpectedDelivery, DeliveredStatus
            SearchForOrderView.this.message.setText("");
            String[][] orderContent = new String[1][2];

            orderContent[0][0] = order_id;
            orderContent[0][1] = orderDetails[4].contains("NOT")?"pending":"received";

            for(String[] order1 : orderContent) {
                JTextField orderField = new JTextField(order1[0], 25);
                orderField.setEditable(false);

                if (order1[1].equals("received")) {
                    orderField.setBackground(Color.decode("#93DB70"));
                }

                orderField.setBorder(BorderFactory.createEmptyBorder());
                orderField.setFont(new Font("Sans-serif", Font.BOLD, 11));
                orderField.setPreferredSize(new Dimension(25, 30));

                boolean inOrder = false;

                for (JTextField test: SearchForOrderView.this.order) {
                    if (test.getText().equals(orderField.getText())) {
                        inOrder = true;
                    }
                }

                if (inOrder) {
                    continue;
                }

                SearchForOrderView.this.order.add(orderField);
            }

            SearchForOrderView.this.displayInitialSearchForm();
        });
    }

    /**
     * Adds a mouse click listener to clear the contents of text-field
     */
    private void addClearListener() {
        this.orderID.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SearchForOrderView.this.orderID.getText().equals("Order ID")) {
                    SearchForOrderView.this.orderID.setText("");
                    SearchForOrderView.this.orderID.setToolTipText("Order ID");
                }
            }

            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
        });
    }
}
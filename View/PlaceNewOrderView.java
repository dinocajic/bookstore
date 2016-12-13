package View;

import Config.Config;
import Controller.PlaceNewOrderController;
import GUIobjects.CMPanel;
import Libraries.Layout;
import View.ViewInterface.PlaceNewOrderViewInterface;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

/**
 * Created by Dino Cajic on 10/29/2016.
 *
 * Displays the Place New Order screens.
 */
public class PlaceNewOrderView implements PlaceNewOrderViewInterface {

    CMPanel display;
    JLabel labelMain;
    JLabel isbnLabel                  = new JLabel("Enter ISBN");
    JLabel message                    = new JLabel("");
    ArrayList<JTextField> orderFields = new ArrayList<>();
    ArrayList<JSpinner> orderQty      = new ArrayList<>();
    JButton placeOrder                = new JButton("Place Order");
    Layout layout                     = new Layout();

    /**
     * @param display The display object that was instantiated in the ApplicationWindow class
     */
    public PlaceNewOrderView(CMPanel display) {
        this.display = display;
    }

    /**
     * Displays the initial form that allows the user to add books
     * Modified by Anh Pham on 10/29/2016.
     */
    public void displayAddBookToOrderForm() {
        this.display.resetToEmpty();

        this.initializeProperties();
        this.adjustPropertySizes();
        this.addPlaceOrderListener();

        // Create Main Label
        JPanel mainLabelPane = new JPanel(new MigLayout());
        mainLabelPane.add(this.labelMain);

        this.layout.formatDisplayTitle(mainLabelPane);

        // Add items to the panel
        JPanel mainPanel = new JPanel(new MigLayout());
        mainPanel.add(mainLabelPane, "span");

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPreferredSize(new Dimension(550, 600));

        JPanel panel = new JPanel(new MigLayout());
        panel.add(this.message, "wrap");
        panel.add(this.isbnLabel, "wrap");

        for (int i = 0; i < this.orderFields.size(); i++) {
            panel.add(this.orderFields.get(i));

            if (i == this.orderFields.size() - 1) {
                JButton addMore = new JButton("Add More");
                this.layout.formatMainButtons(addMore);

                addMore.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        PlaceNewOrderView.this.addAnotherTextField();
                        PlaceNewOrderView.this.displayAddBookToOrderForm();
                    }
                });

                ((SpinnerNumberModel)this.orderQty.get(i).getModel()).setMinimum(0);

                panel.add(this.orderQty.get(i), "split 2");
                panel.add(addMore, "wrap");
                continue;
            }

            panel.add(this.orderQty.get(i), "wrap");
        }

        panel.add(this.placeOrder);
        scrollPane.add(panel);
        mainPanel.add(scrollPane);

        this.display.add(mainPanel);
    }

    /**
     * Initializes the properties
     */
    private void initializeProperties() {
        this.labelMain = new JLabel("Create New Stocking Order");

        // If empty, add at least one text-field
        if (this.orderFields.isEmpty()) {
            this.orderFields.add(new JTextField("", 32));
            this.orderQty.add(new JSpinner());
        }
    }

    /**
     * Adjusts the sizes of certain properties
     */
    private void adjustPropertySizes() {
        this.labelMain.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.labelMain.setFont(Config.title_font);

        this.layout.formatMainButtons(this.placeOrder);

        for(int i = 0; i < this.orderFields.size(); i++) {
            this.orderFields.get(i).setPreferredSize(new Dimension(32, 30));
            this.orderQty.get(i).setPreferredSize(new Dimension(51, 30));
            this.orderQty.get(i).setMinimumSize(new Dimension(51, 30));
        }
    }

    /**
     * Adds another textfield
     */
    private void addAnotherTextField() {
        this.orderFields.add(new JTextField("", 32));
        this.orderQty.add(new JSpinner());
    }

    /**
     * Adds a listener to the place order button
     * Checks to make sure that the form isn't empty
     * Adds the item to the database.
     */
    private void addPlaceOrderListener() {
        this.placeOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlaceNewOrderController controller = new PlaceNewOrderController();
                String check = controller.checkFormFields(PlaceNewOrderView.this.orderFields, PlaceNewOrderView.this.orderQty);

                if (!check.equals("The following errors occurred: ")) {
                    PlaceNewOrderView.this.message.setText("<html>" + check.replaceAll("\n", "<br />") + "</html>");
                    return;
                }

                // Clear any previous messages
                PlaceNewOrderView.this.message.setText("");

                String success = controller.insertOrder(PlaceNewOrderView.this.orderFields, PlaceNewOrderView.this.orderQty);

                if (success.equals("Success")) {
                    PlaceNewOrderView.this.placeOrder.setEnabled(false);
                    PlaceNewOrderView.this.message.setText("Order has been successfully placed");
                } else {
                    PlaceNewOrderView.this.message.setText(success);
                }
            }
        });
    }
}
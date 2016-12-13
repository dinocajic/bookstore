package View;

import Controller.AddOrderToInventoryController;
import GUIobjects.CMPanel;
import Libraries.Layout;
import View.ViewInterface.AddOrderToInventoryViewInterface;

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
 * Displays the add order screens
 */
public class AddOrderToInventoryView implements AddOrderToInventoryViewInterface {

    CMPanel display;
    JLabel labelMain;
    JLabel isbnLabel                  = new JLabel("Book ISBN: ");
    JLabel message                    = new JLabel("");
    ArrayList<JTextField> orderFields = new ArrayList<>();
    ArrayList<JSpinner> orderQty      = new ArrayList<>();
    ArrayList<JLabel> orderedNum      = new ArrayList<>();
    JButton received                  = new JButton("Mark Received");
    JPanel mainLabelPane              = new JPanel(new MigLayout());

    /**
     * @param display The display object that was instantiated in the ApplicationWindow class
     */
    public AddOrderToInventoryView(CMPanel display) {
        this.display = display;
    }

    /**
     * Displays the add order form
     * @param orderDetails the details about the order
     * @param orderNumber the order number
     */
    public void displayAddOrderForm(String[][] orderDetails, String orderNumber) {
        this.display.resetToEmpty();

        this.initializeProperties(orderDetails);
        this.adjustPropertySizes();
        this.addMarkOrderAsReceivedListener(orderNumber);

        this.mainLabelPane.add(this.labelMain);

        // Add items to the panel
        JPanel mainPanel = new JPanel(new MigLayout());
        mainPanel.add(mainLabelPane, "span");

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPreferredSize(new Dimension(550, 600));

        JPanel panel = new JPanel(new MigLayout());
        panel.add(this.message, "wrap");
        panel.add(this.isbnLabel, "wrap");

        for(int i = 0; i < this.orderFields.size(); i++) {
            panel.add(this.orderFields.get(i));
            panel.add(this.orderQty.get(i));
            panel.add(this.orderedNum.get(i), "wrap");
        }

        panel.add(this.received, "wrap");
        scrollPane.add(panel);
        mainPanel.add(scrollPane);

        this.display.add(mainPanel);
    }

    /**
     * Initializes the properties
     * @param orderDetails the order details
     */
    private void initializeProperties(String[][] orderDetails) {
        this.labelMain = new JLabel("Add Order to Inventory");

        for(String[] orderContent : orderDetails) {
            if (orderContent[0].equals("Order Received")) {
                this.received.setEnabled(false);
                continue;
            }

            if (orderContent[0].equals("Order Not Received")) {
                continue;
            }

            JTextField orderField = new JTextField(orderContent[0], 32);
            orderField.setEnabled(false);

            JSpinner qtyField   = new JSpinner();
            int qty = Integer.parseInt(orderContent[1]);
            qtyField.setValue(new Integer(qty));

            ((SpinnerNumberModel)qtyField.getModel()).setMinimum(0);

            this.orderFields.add(orderField);
            this.orderQty.add(qtyField);
            this.orderedNum.add(new JLabel("Ordered: " + orderContent[2]));
        }
    }

    /**
     * Adjusts the sizes of certain properties
     */
    private void adjustPropertySizes() {
        this.labelMain.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.labelMain.setFont(new Font("Sans-Serif", Font.BOLD, 16));

        Layout layout = new Layout();
        layout.formatMainButtons(this.received);

        for(int i = 0; i < this.orderFields.size(); i++) {
            this.orderFields.get(i).setPreferredSize(new Dimension(32, 30));
            this.orderQty.get(i).setPreferredSize(new Dimension(51, 30));
            this.orderQty.get(i).setMinimumSize(new Dimension(51, 30));
        }
    }

    /**
     * Marks the order as received
     * @param orderNumber The order number of the order
     */
    private void addMarkOrderAsReceivedListener(String orderNumber) {
        this.received.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddOrderToInventoryController controller = new AddOrderToInventoryController();
                String success = controller.receiveOrder(AddOrderToInventoryView.this.orderFields, AddOrderToInventoryView.this.orderQty, orderNumber);

                if (success.equals("Success")) {
                    AddOrderToInventoryView.this.message.setText("Order Successfully Received");
                    AddOrderToInventoryView.this.received.setEnabled(false);
                    return;
                }

                AddOrderToInventoryView.this.message.setText(success);
            }
        });
    }
}
package View;

import Config.Config;
import Controller.PointOfSaleController;
import GUIobjects.CMPanel;
import Libraries.Layout;
import View.ViewInterface.PointOfSaleViewInterface;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.print.PrinterException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import net.miginfocom.swing.MigLayout;

/**
 * Created by Dino Cajic on 10/29/2016.
 *
 * Displays the Point of Sale screens
 */
public class PointOfSaleView implements PointOfSaleViewInterface {

    private CMPanel display;

    public Object[][] data;

    ArrayList<String> addedItems  = new ArrayList<>();

    String[] columnNames          = {"Book Title", "ISBN", "Availability", "On Order Qty", "Price", "Add?"};

    DefaultTableModel dm          = new DefaultTableModel();
    JTable table                  = new JTable(dm);
    JPanel mainLabelPane          = new JPanel(new MigLayout());
    JPanel cartPane               = new JPanel(new MigLayout());
    JPanel cartContent            = new JPanel(new MigLayout());
    JPanel paymentPane            = new JPanel(new MigLayout());
    JLabel cartLabel              = new JLabel("Items in Cart");
    JLabel paymentLabel           = new JLabel("Payment");
    JLabel message                = new JLabel("");
    JLabel items                  = new JLabel("<html>");
    JLabel totalCost              = new JLabel("");
    JLabel mainLabel              = new JLabel("Point of Sales");
    JTextField search             = new JTextField("Search by ISBN");
    JTextField dispResultLabel    = new JTextField("Display Result", 10);
    JTextField paymentInfoLabel   = new JTextField("Payment Info", 10);
    JTextField receiptLabel       = new JTextField("Receipt", 10);
    JButton searchButton          = new JButton("Search");
    JButton proceedToPayment      = new JButton("Proceed to Payment");
    JRadioButton creditCard       = new JRadioButton("Credit Card");
    JRadioButton cash             = new JRadioButton("Cash");
    JLabel ccNumberLabel          = new JLabel("Card Number: ");
    JLabel ccNameLabel            = new JLabel("Name on Card: ");
    JLabel ccCodeLabel            = new JLabel("Security Code: ");
    JTextField ccNumber           = new JTextField("", 30);
    JTextField ccName             = new JTextField("", 30);
    JTextField ccCode             = new JTextField("", 10);
    JButton processPayment        = new JButton("Process Payment");
    JLabel receiptPageLabel       = new JLabel("RECEIPT");
    JLabel returnPolicy           = new JLabel("<html>" + Config.returnPolicy.replaceAll("\n", "<br />") + "</html>");
    String orderNumber;
    JButton refundOrder           = new JButton("Refund Order");
    int runs                      = 0;  // Trying to stop multiple order runs
    Layout layout                 = new Layout();
    JButton printButton           = new JButton("Print");

    /**
     * @param display The display object that was instantiated in the ApplicationWindow class
     */
    public PointOfSaleView(CMPanel display) {
        this.display = display;
    }

    /**
     * Displays the initial POS Screen where the user searches for a book
     */
    public void displayInitialScreen() {
        this.display.resetToEmpty();

        this.modifySizesForInitialScreen();

        // START: Table
        dm.setDataVector(data, columnNames );
        JScrollPane ScrollPane = new JScrollPane(table);
        ScrollPane.setPreferredSize(new Dimension(700, 40));
        // END: Table

        this.addSearchListener();
        this.addClearListener();
        this.addTableListener();
        this.addProceedToPaymentListener();

        // Main Display Label
        this.mainLabelPane.add(this.mainLabel);
        this.cartPane.add(this.cartLabel);

        JPanel panel = new JPanel(new MigLayout());

        panel.add(this.mainLabelPane, "dock north");

        JPanel topBar = new JPanel(new MigLayout());
        topBar.add(this.search);
        topBar.add(this.searchButton);
        topBar.add(this.dispResultLabel);
        topBar.add(this.paymentInfoLabel);
        topBar.add(this.receiptLabel);

        panel.add(topBar,                "wrap");
        panel.add(this.message,          "wrap");
        panel.add(ScrollPane,            "wrap");
        panel.add(this.proceedToPayment, "wrap");
        panel.add(this.cartPane,         "wrap");
        panel.add(this.cartContent,      "wrap");

        this.display.add(panel);
    }

    /**
     * Modifies the sizes of the form fields
     */
    private void modifySizesForInitialScreen() {
        this.layout.formatDisplayTitle(this.mainLabelPane);
        this.layout.formatDisplayTitle(this.cartPane);
        this.layout.formatMainButtons(this.searchButton);
        this.layout.formatMainButtons(this.proceedToPayment);

        this.mainLabel.setFont(Config.title_font);
        this.mainLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.cartLabel.setFont(Config.title_font);
        this.cartLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.cartContent.setPreferredSize(new Dimension(700, 300));
        this.search.setPreferredSize(new Dimension(200, 40));
        this.dispResultLabel.setPreferredSize(new Dimension(10, 35));
        this.paymentInfoLabel.setPreferredSize(new Dimension(10, 35));
        this.receiptLabel.setPreferredSize(new Dimension(10, 35));
        this.dispResultLabel.setEditable(false);
        this.dispResultLabel.setEnabled(false);
        this.paymentInfoLabel.setEnabled(false);
        this.receiptLabel.setEnabled(false);
        this.dispResultLabel.setBackground(Config.pos_active_panel);
        this.dispResultLabel.setForeground(Color.white);
        this.paymentInfoLabel.setBackground(Color.white);
        this.paymentInfoLabel.setForeground(Color.black);

        this.dispResultLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        this.paymentInfoLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        this.receiptLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
    }

    /**
     * Clears the input on the ISBN screen
     */
    private void addClearListener() {
        this.search.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                PointOfSaleView.this.search.setText("");
            }

            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
        });
    }

    /**
     * Listener for when the user clicks on the search button to add books to the table
     */
    private void addSearchListener() {
        if (this.searchButton.getActionListeners().length > 0) {
            return;
        }

        this.searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PointOfSaleController controller = new PointOfSaleController();
                PointOfSaleView.this.data = controller.getBook(PointOfSaleView.this.search.getText());
                PointOfSaleView.this.displayInitialScreen();
            }
        });
    }

    /**
     * Adds a table listener so that the user can add items to the order.
     */
    private void addTableListener() {
        this.table.addMouseListener(new MouseListener() {
            @Override
            public void mousePressed(MouseEvent e) {
                JTable target = (JTable)e.getSource();
                int row = target.getSelectedRow();
                int column = target.getSelectedColumn();

                String add = "";

                try {
                    add = (String)PointOfSaleView.this.data[row][column];
                } catch (ArrayIndexOutOfBoundsException eAr) {
                    // Do nothing
                } catch (NullPointerException eNul) {
                    // Do nothing
                }

                if (column == 5 && add.equals("Add")) {
                    String isbn = (String)PointOfSaleView.this.data[row][1];

                    if (!PointOfSaleView.this.addedItems.contains(isbn)) {
                        PointOfSaleView.this.addedItems.add(isbn);
                        PointOfSaleView.this.displayCartContent();
                    }
                }
            }

            public void mouseReleased(MouseEvent e) {}
            public void mouseClicked(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
        });
    }

    /**
     * Displays the cart content
     */
    private void displayCartContent() {
        DefaultTableModel dm          = new DefaultTableModel();
        JTable table                  = new JTable(dm);

        PointOfSaleView.this.populateTable();

        String[] columnNames = PointOfSaleView.this.columnNames;
        columnNames[5] = "Remove?";

        Object[][] data = PointOfSaleView.this.data;

        for (Object[] book : data) {
            if (book[0] == null) {
                book[5] = "Please Search again.";
                continue;
            }

            book[5] = "Remove";
        }

        dm.setDataVector(data, columnNames);
        JScrollPane ScrollPane = new JScrollPane(table);
        ScrollPane.setPreferredSize(new Dimension(730,100));

        PointOfSaleView.this.cartContent.removeAll();
        PointOfSaleView.this.cartContent.add(ScrollPane);
        PointOfSaleView.this.cartContent.validate();
        PointOfSaleView.this.cartContent.repaint();

        table.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JTable removeTarget = (JTable)e.getSource();
                int row = removeTarget.getSelectedRow();
                int column = removeTarget.getSelectedColumn();

                String remove = "";

                try {
                    remove = (String)data[row][column];
                } catch (ArrayIndexOutOfBoundsException eAr) {
                    // do nothing
                } catch (NullPointerException eNul) {
                    // Do nothing as well
                }

                if (column == 5 && remove.equals("Remove")) {
                    PointOfSaleView.this.addedItems.remove(row);
                    PointOfSaleView.this.populateTable();
                    PointOfSaleView.this.displayCartContent();
                }
            }

            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
        });
    }

    /**
     * Listener for the Proceed to Payment button
     */
    public void addProceedToPaymentListener() {
        this.proceedToPayment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (PointOfSaleView.this.addedItems.size() == 0) {
                    PointOfSaleView.this.message.setText("You must add at least one item");
                    return;
                }

                PointOfSaleView.this.message.setText("");
                PointOfSaleView.this.displayPaymentForm();
            }
        });
    }

    /**
     * Displays the payment form once the user clicks next after finding the books in the initial screen
     */
    public void displayPaymentForm() {
        this.display.resetToEmpty();

        this.populateTable();
        this.modifyPropertiesForPayment();
        this.removeAddFromTable();
        this.calculateTotalCost();
        this.addPaymentOptionListeners();
        this.addProcessPaymentListener();

        // START: Table
        String[] columnNames = new String[5];
        // Remove last column
        System.arraycopy(this.columnNames, 0, columnNames, 0, 5);

        dm.setDataVector(data, columnNames );
        JScrollPane ScrollPane = new JScrollPane(table);
        ScrollPane.setPreferredSize(new Dimension(700,100));
        // END: Table

        // Main Display Label
        this.mainLabelPane.add(this.mainLabel);

        JPanel panel = new JPanel(new MigLayout());

        panel.add(this.mainLabelPane, "dock north");

        JPanel topBar = new JPanel(new MigLayout());
        topBar.add(this.search);
        topBar.add(this.searchButton);
        topBar.add(this.dispResultLabel);
        topBar.add(this.paymentInfoLabel);
        topBar.add(this.receiptLabel);

        panel.add(topBar,            "wrap");
        panel.add(this.message,      "wrap");
        panel.add(ScrollPane,        "wrap");
        panel.add(totalCost,         "wrap");

        this.paymentPane.add(this.paymentLabel);
        panel.add(this.paymentPane,  "wrap");

        panel.add(this.creditCard,   "split 2");
        panel.add(this.cash,         "wrap");

        JPanel ccPanel = new JPanel(new MigLayout());
        ccPanel.add(this.ccNumberLabel);
        ccPanel.add(this.ccNumber,   "wrap");
        ccPanel.add(this.ccNameLabel);
        ccPanel.add(this.ccName,     "wrap");
        ccPanel.add(this.ccCodeLabel);
        ccPanel.add(this.ccCode,     "wrap");

        panel.add(ccPanel,           "wrap");
        panel.add(this.processPayment);

        this.display.add(panel);
    }

    /**
     * Populates the table with books
     */
    private void populateTable() {
        PointOfSaleController controller = new PointOfSaleController();
        PointOfSaleView.this.data = controller.getBooks(this.addedItems);
    }

    /**
     * Change the Color of the topbar buttons
     */
    private void modifyPropertiesForPayment() {
        this.dispResultLabel.setBackground(Color.white);
        this.dispResultLabel.setForeground(Color.black);
        this.paymentInfoLabel.setBackground(Config.pos_active_panel);
        this.paymentInfoLabel.setForeground(Color.white);

        this.totalCost.setFont(new Font("Sans-serif", Font.BOLD, 14));
        this.totalCost.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 10));

        this.paymentLabel.setFont(Config.title_font);
        this.paymentLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 10));

        this.ccNumber.setPreferredSize(new Dimension(30, 30));
        this.ccName.setPreferredSize(new Dimension(30, 30));
        this.ccCode.setPreferredSize(new Dimension(10, 30));

        this.layout.formatMainButtons(this.processPayment);
        this.layout.formatRadioButton(this.creditCard);
        this.layout.formatRadioButton(this.cash);
        this.layout.formatDisplayTitle(this.paymentPane);
    }

    /**
     * Removes the Add at the end of the screen
     */
    private void removeAddFromTable() {
        for(Object[] book: this.data) {
            book[5] = "";
        }
    }

    /**
     * Calculates the total cost of the items
     */
    private void calculateTotalCost() {
        Double total = 0.00;
        Double current;

        for (Object[] book: this.data) {
            String cost = (String)book[4];
            current = Double.parseDouble(cost);
            total += current;
        }

        String strTotal = String.format("%.2f",total);
        this.totalCost.setText("Total Cost: $" + strTotal);
    }

    /**
     * Adds a listener to payment option radio button clicks
     */
    private void addPaymentOptionListeners() {
        // Mouse Listener for credit card clicks
        this.creditCard.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                PointOfSaleView.this.creditCard.setSelected(true);
                PointOfSaleView.this.cash.setSelected(false);

                PointOfSaleView.this.ccNumber.setEnabled(true);
                PointOfSaleView.this.ccName.setEnabled(true);
                PointOfSaleView.this.ccCode.setEnabled(true);
            }

            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
        });

        // Mouse Listener for cash clicks
        this.cash.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                PointOfSaleView.this.cash.setSelected(true);
                PointOfSaleView.this.creditCard.setSelected(false);

                PointOfSaleView.this.ccNumber.setEnabled(false);
                PointOfSaleView.this.ccName.setEnabled(false);
                PointOfSaleView.this.ccCode.setEnabled(false);
            }

            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
        });
    }

    /**
     * What happens when the user presses process payment button
     */
    private void addProcessPaymentListener() {
        this.processPayment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PointOfSaleView.this.runs += 1;
                String ccNumber = PointOfSaleView.this.ccNumber.getText();
                String ccName   = PointOfSaleView.this.ccName.getText();
                String ccCode   = PointOfSaleView.this.ccCode.getText();

                PointOfSaleController controller = new PointOfSaleController();

                if (PointOfSaleView.this.creditCard.isSelected()) {
                    boolean isCCok = controller.checkCard(ccNumber, ccName, ccCode);

                    if (!isCCok) {
                        PointOfSaleView.this.message.setText("Something went wrong with the Credit Card Payment");
                        PointOfSaleView.this.runs = 0;
                        return;
                    }

                    // If this was connected to PayPay or FirstData, this is where the code to add the payment would go
                }

                PointOfSaleView.this.message.setText("");
                PointOfSaleView.this.orderNumber = UUID.randomUUID().toString();
                
                if(PointOfSaleView.this.runs==1){  //ONLY process a sale one time.  Prevents duplicate sales and over-removal of stock
                    String insert = controller.insertSale(PointOfSaleView.this.addedItems, ccNumber, ccName, PointOfSaleView.this.creditCard.isSelected(), PointOfSaleView.this.orderNumber);


                    if (!insert.equals("Success")) {
                        PointOfSaleView.this.message.setText(insert);
                        return;
                    }


                    PointOfSaleView.this.displayReceipt(ccNumber, ccName, ccCode);

                }
            }
        });
    }

    /**
     * Displays the receipt after the user enters the payment details
     */
    public void displayReceipt(String ccNumber, String ccName, String ccCode) {
        this.display.resetToEmpty();
        this.modifyPropertiesForReceipt();

        // START: Table
        String[] columnNames = new String[5];
        // Remove last column
        System.arraycopy(this.columnNames, 0, columnNames, 0, 5);

        dm.setDataVector(data, columnNames );
        JScrollPane ScrollPane = new JScrollPane(table);
        ScrollPane.setPreferredSize(new Dimension(500,100));
        // END: Table

        // Main Display Label
        this.mainLabelPane.add(this.mainLabel);

        JPanel panel = new JPanel(new MigLayout());

        panel.add(this.mainLabelPane, "dock north");

        JPanel topBar = new JPanel(new MigLayout());
        topBar.add(this.search);
        topBar.add(this.searchButton);
        topBar.add(this.dispResultLabel);
        topBar.add(this.paymentInfoLabel);
        topBar.add(this.receiptLabel);

        panel.add(topBar, "wrap");
        panel.add(this.receiptPageLabel, "wrap");
        panel.add(this.returnPolicy, "wrap");

        panel.add(ScrollPane, "wrap");

        panel.add(new JLabel("Order Number: " + this.orderNumber), "wrap");

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date now = new Date(System.currentTimeMillis());
        String strDate = dateFormat.format(now);

        JLabel dateOrdered = new JLabel("Date: " + strDate);
        panel.add(dateOrdered, "wrap");

        if (!ccNumber.equals("")) {
            panel.add(new JLabel("Credit Card Number: ****" + ccNumber.substring(ccNumber.length() - 4)), "wrap");
        }

        if (!ccName.equals("")) {
            panel.add(new JLabel("Name on Card: " + ccName), "wrap");
        }

        panel.add(new JLabel(totalCost.getText().replace("Total Cost: ", "Paid: ")), "wrap");

        this.printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextPane textPane = new JTextPane();
                String textPaneContent = Config.company_name + " Receipt: ";
                textPaneContent += strDate;
                textPaneContent += "\n\n";
                textPaneContent += Config.returnPolicy;
                textPaneContent += "\n\n";

                textPaneContent += "Order Number: " + PointOfSaleView.this.orderNumber;
                textPaneContent += "\n\n";

                textPaneContent += "Items Ordered:";
                textPaneContent += "\n";

                for (Object[] book : PointOfSaleView.this.data) {
                    textPaneContent += book[1] + " - " + book[0] + " - $" + book[4];
                    textPaneContent += "\n";
                }

                textPaneContent += "\n";

                if (!ccNumber.equals("")) {
                    textPaneContent += "Credit Card Number: ****" + ccNumber.substring(ccNumber.length() - 4);
                    textPaneContent += "\n";
                }

                if (!ccName.equals("")) {
                    textPaneContent += "Name on Card: " + ccName;
                    textPaneContent += "\n";
                }

                textPaneContent += PointOfSaleView.this.totalCost.getText().replace("Total Cost: ", "Paid: ");

                textPane.setText(textPaneContent);

                try {
                    textPane.print();
                } catch(PrinterException pe) {
                    System.out.println("Nothing");
                }
            }
        });

        panel.add(printButton);
        this.display.add(panel);
    }

    /**
     * Modifies some properties that are needed on the receipt page
     */
    private void modifyPropertiesForReceipt() {
        this.dispResultLabel.setBackground(Color.white);
        this.dispResultLabel.setForeground(Color.black);
        this.paymentInfoLabel.setBackground(Color.white);
        this.paymentInfoLabel.setForeground(Color.black);
        this.receiptLabel.setBackground(Config.pos_active_panel);
        this.receiptLabel.setForeground(Color.white);

        this.receiptPageLabel.setFont(new Font("Sans-serif", Font.BOLD, 20));
        this.returnPolicy.setPreferredSize(new Dimension(500, 30));
        this.returnPolicy.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 10));

        this.searchButton.setEnabled(false);
        this.search.setEnabled(false);
        this.layout.formatMainButtons(this.printButton);
    }

    // FOR REFUNDS //

    /**
     * Displays the customer order if the customer is looking for a refund
     */
    public void displaySearchForCustomerOrder() {
        this.display.resetToEmpty();
        this.search = new JTextField("Search by Order #", 25);

        // Main Display Label
        this.mainLabel = new JLabel("Refund");

        this.modifySizesForRefund();
        this.addClearListener();
        this.addSearchOrderListener();

        this.mainLabelPane.add(this.mainLabel);

        JPanel panel = new JPanel(new MigLayout());

        panel.add(this.mainLabelPane, "dock north");
        panel.add(new JLabel(""),     "wrap");
        panel.add(this.search,        "split 2");
        panel.add(this.searchButton,  "wrap");
        panel.add(this.message,       "wrap");
        panel.add(this.refundOrder,   "wrap");

        this.display.add(panel);
    }

    /**
     * Modify certain sizes
     */
    private void modifySizesForRefund() {
        this.mainLabel.setFont(Config.title_font);
        this.mainLabel.setBorder(new EmptyBorder(10, 10, 10, 10));

        layout.formatDisplayTitle(this.mainLabelPane);

        this.search.setPreferredSize(new Dimension(200, 30));
        this.layout.formatMainButtons(this.searchButton);
        this.layout.formatMainButtons(this.refundOrder);

        this.refundOrder.setEnabled(false);
    }

    /**
     * Searches for an order by order id
     */
    private void addSearchOrderListener() {
        this.searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PointOfSaleController controller = new PointOfSaleController();
                String orderNumber = controller.getOrder(PointOfSaleView.this.search.getText());
                
                if (orderNumber == null) {
                    PointOfSaleView.this.refundOrder.setText("Order Does Not Exist");
                    return;
                }

                PointOfSaleView.this.refundOrder.setEnabled(true);
                PointOfSaleView.this.refundOrder.setText("Proceed to refund");

                PointOfSaleView.this.refundOrder.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        PointOfSaleView.this.orderNumber = PointOfSaleView.this.search.getText();
                        String success = controller.issueRefund(PointOfSaleView.this.search.getText());

                        if (!success.equals("Success")) {
                            PointOfSaleView.this.refundOrder.setText(success);
                            return;
                        }

                        PointOfSaleView.this.displayRefundReceipt();
                    }
                });
            }
        });
    }

    /**
     * Once the refund has been issued, the refund receipt is displayed
     */
    public void displayRefundReceipt() {
        this.display.resetToEmpty();

        // Main Display Label
        this.mainLabelPane.add(this.mainLabel);

        JPanel panel = new JPanel(new MigLayout());

        panel.add(mainLabelPane, "wrap");
        this.returnPolicy.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 20));
        panel.add(returnPolicy, "wrap");

        panel.add(new JLabel("Your order has been refunded"), "wrap");
        panel.add(new JLabel("Order Number: " + this.orderNumber), "wrap");

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date now = new Date();
        String strDate = dateFormat.format(now);

        panel.add(new JLabel("Date: " + strDate), "wrap");

        this.printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextPane textPane = new JTextPane();
                String textPaneContent = Config.company_name + " Refund Receipt: " + strDate;
                textPaneContent += "\n\n";
                textPaneContent += Config.returnPolicy;
                textPaneContent += "\n\n";
                textPaneContent += "Your order has been refunded in full.";
                textPaneContent += "\n\n";
                textPaneContent += "Order Number: " + PointOfSaleView.this.orderNumber;

                textPane.setText(textPaneContent);

                try {
                    textPane.print();
                } catch(PrinterException pe) {
                    System.out.println("Nothing");
                }
            }
        });

        this.layout.formatMainButtons(this.printButton);
        panel.add(this.printButton);

        this.display.add(panel);
    }
}
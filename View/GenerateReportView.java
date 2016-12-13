package View;

import Config.Config;
import Controller.GenerateReportController;
import GUIobjects.CMPanel;
import Libraries.Layout;
import View.ViewInterface.GenerateReportViewInterface;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Dino Cajic on 10/29/2016.
 *
 * Displays the Report screen
 */
public class GenerateReportView implements GenerateReportViewInterface {

    private CMPanel display;

    JComboBox<String> fromMonth;
    JComboBox<String> fromYear;
    JComboBox<String> toMonth;
    JComboBox<String> toYear;

    String[] months      = Config.months;
    String[] years       = new String[50];

    JLabel labelMain     = new JLabel("Search for Sales Information");
    JLabel message       = new JLabel("");
    JLabel dateRange     = new JLabel("");
    JTextPane results    = new JTextPane();
    JScrollPane pane     = new JScrollPane(results);
    JButton searchDate   = new JButton("Search by Date");
    JPanel mainLabelPane = new JPanel(new MigLayout());
    Layout layout        = new Layout();

    StringBuilder printable = new StringBuilder();
    JButton printReport     = new JButton("Print Report");

    public static String[] fromDate;
    public static String[] toDate;

    /**
     * @param display The display object that was instantiated in the ApplicationWindow class
     */
    public GenerateReportView(CMPanel display) {
        this.display = display;
    }

    /**
     * Displays a form to search for the sales report between date ranges
     */
    public void displaySalesReportForm() {
        this.display.resetToEmpty();

        this.initializeProperties();
        this.adjustPropertySizes();
        this.addSearchListener();
        JPanel panel = new JPanel(new MigLayout());

        // Create Main Label
        mainLabelPane.add(this.labelMain);

        panel.add(mainLabelPane, "span");
        panel.add(this.fromMonth);
        panel.add(this.fromYear);

        panel.add(new JLabel(" - "));

        panel.add(this.toMonth);
        panel.add(this.toYear);
        panel.add(this.searchDate, "wrap");
        panel.add(this.message,    "span");
        panel.add(this.dateRange,  "span");

        this.display.add(panel);
    }

    /**
     * Initializes the necessary properties
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

        this.fromMonth.setSelectedIndex(Calendar.getInstance().get(Calendar.MONTH)-1); // Start last month
        this.toMonth.setSelectedIndex(Calendar.getInstance().get(Calendar.MONTH));     // To 28th of this month
    }

    /**
     * Modifies the sizes of certain items
     */
    private void adjustPropertySizes() {
        this.labelMain.setFont(Config.title_font);
        this.labelMain.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.dateRange.setBorder(new EmptyBorder(10, 0, 10, 10));

        this.layout.formatDisplayTitle(this.mainLabelPane);
        this.layout.formatMainButtons(this.searchDate);
        this.pane.setPreferredSize(Config.display_panel_size);
        this.pane.setMinimumSize(Config.display_panel_size);
    }

    /**
     * Adds a listener to the search button
     */
    private void addSearchListener() {
        this.searchDate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fromMonth   = (String) GenerateReportView.this.fromMonth.getSelectedItem();
                int fromMonthIndex = GenerateReportView.this.fromMonth.getSelectedIndex();
                String fromYearStr = (String) GenerateReportView.this.fromYear.getSelectedItem();
                int fromYear       = Integer.parseInt(fromYearStr);

                String toMonth     = (String) GenerateReportView.this.toMonth.getSelectedItem();
                int toMonthIndex   = GenerateReportView.this.toMonth.getSelectedIndex();
                String toYearStr   = (String) GenerateReportView.this.toYear.getSelectedItem();
                int toYear         = Integer.parseInt(toYearStr);

                GenerateReportController control = new GenerateReportController(GenerateReportView.this.display);
                ArrayList<String[][]> salesData  = control.getSalesForDate(fromMonthIndex, fromYear, toMonthIndex, toYear);

                control.displaySalesReportResults(salesData);
            }
        });
    }

    /**
     * Adds a listener to the print button
     */
    private void addPrintListener() {
        this.printReport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextPane textPane = new JTextPane();
                textPane.setContentType("text/html");
                textPane.setText(GenerateReportView.this.printable.toString());

                try {
                    textPane.print();
                } catch(PrinterException pe) {
                    System.out.println("Nothing");
                }
            }
        });
    }

    /**
     * Displays the report using HTML. The HTML does not support many modern features so it's very simplistic.
     * @param results The report results
     */
    public void displayReportResults(ArrayList<String[][]> results) {
        this.display.resetToEmpty();
        this.pane.setBorder(new EmptyBorder(0, 0, 0, 0));

        pane.getVerticalScrollBar().setUnitIncrement(14);  //make scrolling faster

        this.results.setFont(new Font(Font.MONOSPACED,Font.PLAIN,15));
        this.results.setContentType("text/html");
        this.results.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, true); //enables font settings to apply in "html-mode"
        this.results.setEditable(false);
        this.results.setBackground(Color.WHITE);
        this.results.setBorder(null);

        StringBuilder html = new StringBuilder("<html>");

        double grossSale = 0.00d;
        double grossCost = 0.00d;

        Calendar c = Calendar.getInstance();
        String[] now = c.getTime().toString().split(" ");

        html.append("<font size=7><center><b>Sales Report " + now[0] + ", " + now[1] + " " + now[2] + ", " + now[5] + "</b></center></font><br>");
        html.append("<font size=6><center><b>For the period: " + fromDate[1] + ", " + fromDate[5] + " to "  + toDate[1] + ", " + toDate[5] + "</b></center></font><hr><br><br>");

        String tab = "&nbsp&nbsp&nbsp ";
        html.append("<table border=2 cellspacing=0><tr>" +
                "<th width=230><b><u>Sale ID</th>" +
                "<th><b><u>Date of Sale</th>" +
                "<th width=300><b><u>Payment Information</th>" +
                "<th><b><u>Book ISBN</th>" +
                "<th><b><u>Book Title</th>" +
                "<th><b><u>Price</th>" +
                "<th><b><u>Qty</th></tr>");

        for(int i = 0; i < results.size(); i++){
            String[][] currResult = results.get(i);

            c.setTimeInMillis(Long.parseLong(currResult[0][1]));

            html.append("<tr><td width=230><b><center>"+currResult[0][0].substring(0,18)+" "+currResult[0][0].substring(19)+"</center></b></td><td>"+   //order number
                    c.getTime().toString().substring(0,10)+c.getTime().toString().substring(c.getTime().toString().length()-5)+"</td><td width=300>$"+  //date ordered
                    currResult[0][2]+"</td>");                                                                                                          //payment

            for(int b = 1; b < currResult.length; b++){
                if(b!=1) {
                    html.append("<tr><td><br><br></td><td></td><td></td>");
                }

                html.append("<td>"+ currResult[b][0] +"</td><td>"+                                                                  //isbn
                                    (currResult[b][1].length()>28?currResult[b][1].substring(0,28):currResult[b][1])+"</td><td>$"+  //title
                                    currResult[b][3]+"</td><td>"+                                                                   //price
                                    currResult[b][6]+"</td></tr>");                                                                 //qty

                grossSale+=(Double.parseDouble(currResult[b][3])*Double.parseDouble(currResult[b][6]));
                grossCost+=(Double.parseDouble(currResult[b][6])*Double.parseDouble(currResult[b][7]));
            }
        }

        html.append("</table><br><b><hr><hr></b><br><font size=5>"+tab);
        html.append("<b>Gross Sales: ........$"+round(grossSale,2)+"<br>"+tab);  //11x decimals to align
        html.append("Inventory Costs: ....$"+round(grossCost, 2)+"<br>"+tab);       //4x decimals to align
        html.append("Estimated profit: ...<font color=#004C00>$"+round((grossSale-grossCost),2)+"</font></b><br><br><br>"); //3x decimals to align
        html.append("</html>");

        printable.append(html);

        this.results.setText(html.toString());
        this.results.setCaretPosition(0);

        this.layout.formatMainButtons(this.printReport);
        this.display.add(this.printReport, "span");
        this.addPrintListener();
        this.display.add(this.pane);
    }

    /**
     * Takes the float parameter and outputs it rounded to the given number of places after the decimal.
     * Corrects floating point errors.
     * @param value The decimal to be rounded
     * @param places How many places after the decimal to round
     * @return Rounded decimal
     */
    private double round(double value, int places) {
        if (places < 0) {
            return (int) value;
        }

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);

        return bd.doubleValue();
    }
}
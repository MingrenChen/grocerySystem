import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Created by wangj397 on 02/08/17.
 * This class manage cashier interface, including all jframe components.
 */
public class CashierBox extends JFrame{
    /**
     * This field indicates the panel for cashier.
     */
    private JPanel cashierPanel;
    /**
     * This field indicates the command cashier can choose in the combobox.
     */
    private JComboBox comboBox;
    /**
     * This field indicates the editor pane for cashier to input upc.
     */
    private JEditorPane upcInput;
    /**
     * This field indicates enter a command.
     */
    private JButton okButton1;
    /**
     * This field indicates add a item.
     */
    private JButton addButton;
    /**
     * This field indicates all the options cashier can choose from combobox.
     */
    private JComboBox comboBox1;
    /**
     * This field indicates the panel for upc.
     */
    private JPanel upcPanel;
    /**
     * This field indicates the table for cashier.
     */
    private JTable table1;
    /**
     * This field indicates the information panel for cashier.
     */
    private JPanel infoPanel;
    /**
     * This field indicates to cancel a command.
     */
    private JButton cancleButton;
    /**
     * This field indicates to check out after hit the button.
     */
    private JButton checkoutButton;
    /**
     * This field indicates the editor pane for cashier to input a upc.
     */
    private JEditorPane upcinput2;
    /**
     * This field indicates ok.
     */
    private JButton okButton3;
    /**
     * This field is the panel for upc.
     */
    private JPanel upcPanel2;
    /**
     * This field indicates the option of payment in a combobox.
     */
    private JComboBox paymentinput;
    /**
     * This field indicates to logout of the system.
     */
    private JButton logoutButton;
    /**
     * This field indicates the quit button.
     */
    private JButton quitButton;
    /**
     * This field indicates the cashier user.
     */
    private Cashier user;


    /**
     * Constructor for cashierbox.
     * An interface for cashier to do their command and view some information.
     */
    CashierBox(){
        super("Reshelver Interface");
        setContentPane(cashierPanel);
        setSize(800,700);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        CashierBox CashierBoxFrame = this;
        upcPanel2.setVisible(false);
        upcPanel.setVisible(false);
        infoPanel.setVisible(false);

        StoreRecord record = Start.getDomain();

        user = (Cashier) (Login.getUser());

        okButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = (String) comboBox1.getSelectedItem();
                if (command.equalsIgnoreCase("scan items")) {
                    upcPanel.setVisible(true);
                } else if (command.equalsIgnoreCase("view discount")){
                    upcPanel.setVisible(false);
                    infoPanel.setVisible(false);
                    upcPanel2.setVisible(true);
                }else if (command.equalsIgnoreCase("view all products")){
                    DefaultTableModel table = ComponentCreater.getInstance().getTableOfAll(user.getEveryThing());
                    table1.setModel(table);
                    infoPanel.setVisible(true);
                    upcPanel.setVisible(false);
                }
            }

        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String upc = upcInput.getText();
                String command = (String) comboBox1.getSelectedItem();
                if (command.equalsIgnoreCase("scan items")){
                    user.scan(upc);
                    PayList receipt = user.getReceipt();
                    setProductData(receipt);
                    infoPanel.setVisible(true);
                    String m = "\n" +Login.getUsername() + " added a product to cart: "
                            + user.getProduct(upc);
                    WriteFactory.writeLog(m);
                }
            }
        });
        cancleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String upc = upcInput.getText();
                user.cancelOne(upc);
                PayList receipt = user.getReceipt();
                setProductData(receipt);
                infoPanel.setVisible(true);
                String m = "\n" +Login.getUsername() + " cancelled a product to cart for checkout: "
                        + user.getProduct(upc);
                WriteFactory.writeLog(m);
            }
        });
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PayList receipt = user.getReceipt();
                String payment = (String) paymentinput.getSelectedItem();
                int type=-1;

                if (receipt != null){
                    Double total = receipt.getTotalPrice();
                    if (payment.equalsIgnoreCase("cash")){
                        type=2;
                        user.checkOut(2,-1);
                        JOptionPane.showMessageDialog(null,"complete transaction.");
                        String m = "\n" +Login.getUsername() + " completed transaction with cash.";
                        WriteFactory.writeLog(m);
                    }else if (payment.equalsIgnoreCase("credit card")){
                        type = 1;
                        int num = Integer.decode(JOptionPane.showInputDialog("$" + total + " in total. Please input card number:"));
                        user.checkOut(1,-1);
                        String m = "\n" +"\n" + Login.getUsername() + " completed transaction with credit card. ";
                        WriteFactory.writeLog(m);

                    }else if (payment.equalsIgnoreCase("debit card")){
                        type = 0;
                        int num = Integer.decode(JOptionPane.showInputDialog("$" + total + " in total. Please input card number:"));
                        user.checkOut(0,-1);
                        String m = "\n" +Login.getUsername() + " completed transaction with debit card. ";
                        WriteFactory.writeLog(m);
                    }
                    infoPanel.setVisible(false);
                    upcPanel.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null,"nothing in the cart");
                    String m = "\n" +Login.getUsername() + " completed transaction with cash: ";
                    WriteFactory.writeLog(m);
                }

            }
        });
        okButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String upc = upcinput2.getText();
                String message = user.viewDiscount(upc);
                if (message !=null){
                    JOptionPane.showMessageDialog(null,message);
                    String m = "\n" +Login.getUsername() + " viewed discount for product: " + user.getProduct(upc);
                    WriteFactory.writeLog(m);
                }else {
                    JOptionPane.showMessageDialog(null,"no discount is found for this product");
                    String m = "\n" +Login.getUsername() + " failed to viewed discount for product: " + user.getProduct(upc);
                    WriteFactory.writeLog(m);
                }

            }
        });
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login log = null;
                try {
                    log = new Login();
                    String m = "\n" +Login.getUsername() + " logged out. ";
                    WriteFactory.writeLog(m);
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                log.setVisible(true);
                dispose();
            }
        });
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ManageStoreRecords.save();
                ManageStoreRecords.getStoreAll().getStoreRecords().get(0).setStoreRecord(record);
                dispose();
                String m = "\n" +Login.getUsername() + " exit the system. ";
                WriteFactory.writeLog(m);
            }
        });
    }
    public static void main (String[] args) throws IOException, ClassNotFoundException {
        new CashierBox().setVisible(true);
    }
    void setProductData(PayList plist){
        HashMap<Product, Integer> productList = plist.getPayList();
        ArrayList<String> info = new ArrayList<>();
        for (Product p: productList.keySet()){
            String upc = p.getUpc();
            String name = p.getName();
            String price = String.valueOf(p.getPrice());
            String num = String.valueOf(user.getReceipt().getPayList().get(p));
            info.add(upc);
            info.add(name);
            info.add(price);
            info.add(num);


        }
        String[][] row = new String[productList.size()][4];
        for (int i =0;i<productList.keySet().size();i++){
            for (int j= 0;j<4;j++){
                row[i][j] = info.get(j+(i*4));
            }
        }
        String[] col = new String[] {"upc", "name","price","item num"};
        table1.setModel(new DefaultTableModel(row,col));

    }




}
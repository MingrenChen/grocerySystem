import sun.reflect.generics.tree.Tree;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.time.LocalDate;

/**
 * Created by wangj397 on 02/08/17.
 * This class manage the interface for managers, including jframe managerbox.
 */
public class ManagerBox extends JFrame {
    /**
     * This is a panel for manager.
     */
    private JPanel managerPanel;
    /**
     * This is the button for ok.
     */
    private JButton okButton1;
    /**
     * This is the editor panel for managers to enter.
     */
    private JEditorPane editorPane1;
    /**
     * This is the button ok.
     */
    private JButton okButton;
    /**
     * This is the combobox for managers to choose from.
     */
    private JComboBox comboBox1;
    /**
     * This indicates the panel that has upc.
     */
    private JPanel upcPanel;
    /**
     * This is the panel that has the information.
     */
    private JPanel infoPanel;
    /**
     * this is a table for managers.
     */
    private JTable table1;
    /**
     * This is the button for relogin.
     */
    private JButton reLoginButton;
    /**
     * This is the tree in jframe for managers.
     */
    private JTree tree1;
    /**
     * This is the panel for tree.
     */
    private JPanel TreePanel;
    /**
     * This is the button for quit.
     */
    private JButton quitButton;
    /**
     *  This is the user manager.
     */
    private Manager user;

    /**
     * This is the constructor for managerbox interface.
     * @throws IOException it throws exception when the file for managers don't exist.
     * @throws ClassNotFoundException it throws ecxeption when the class cannot be found.
     */
    ManagerBox() throws IOException, ClassNotFoundException {
        super("Manager Interface");
        setContentPane(managerPanel);
        setSize(500, 500);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        upcPanel.setVisible(false);
        infoPanel.setVisible(false);
        TreePanel.setVisible(false);

        ManagerBox ManagerBoxFrame = this;
        StoreRecord record = Start.getDomain();
        String name = Login.getUsername();
        String pass = Login.getUserpwd();
        user = (Manager) (Login.getUser());

        okButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = (String) comboBox1.getSelectedItem();
                if (command.equalsIgnoreCase("Set Price")) {
                    TreePanel.setVisible(false);
                    JTextField upcInput = new JTextField();
                    JTextField priceInput = new JTextField();
                    Object[] message = {
                            "Input Product's UPC:", upcInput,
                            "Input new price:", priceInput,
                    };
                    int option = JOptionPane.showConfirmDialog(ManagerBoxFrame, message,
                            "Enter all your data", JOptionPane.OK_CANCEL_OPTION);
                    if (option == JOptionPane.OK_OPTION) {
                        try {
                            String upc = upcInput.getText();
                            double price = Double.parseDouble(priceInput.getText());
                            user.setPrice(upc, price);
                            String m = "\n" +Login.getUsername() + "\n set up new price for "
                                    + user.getProduct(upc);
                            WriteFactory.writeLog(m);
                        } catch (NumberFormatException e1) {
                            JOptionPane.showMessageDialog(null,
                                    "Price should be a number.");
                            String m = "\n" +Login.getUsername() + "failed to set up new price: wrong number";
                            WriteFactory.writeLog(m);
                        } catch (NullPointerException e2) {
                            JOptionPane.showMessageDialog(null,
                                    "There's no such product.");
                            String m = "\n" +Login.getUsername() + "failed to set up new price: no such product.";
                            WriteFactory.writeLog(m);
                        }
                    }
                } else if (command.equalsIgnoreCase("set discount")) {
                    TreePanel.setVisible(false);
                    JTextField upcInput = new JTextField();
                    JTextField discountInput = new JTextField();
                    JTextField starttime = new JTextField();
                    JTextField endtime = new JTextField();
                    Object[] message = {
                            "Input Product's UPC: ", upcInput,
                            "Input new discount: ", discountInput,
                            "Discount start time(1970/12/31): ", starttime,
                            "Discount end time(1979/12/20): ", endtime
                    };
                    int option = JOptionPane.showConfirmDialog(ManagerBoxFrame, message,
                            "Enter all your data", JOptionPane.OK_CANCEL_OPTION);
                    if (option == JOptionPane.OK_OPTION) {
                        try {
                            String upc = upcInput.getText();
                            double discount = Double.parseDouble(discountInput.getText());
                            String sdate[] = starttime.getText().split("/");
                            LocalDate startdate = LocalDate.of(Integer.parseInt(sdate[0]),
                                    Integer.parseInt(sdate[1]), Integer.parseInt(sdate[2]));
                            String edate[] = endtime.getText().split("/");
                            LocalDate enddate = LocalDate.of(Integer.parseInt(edate[0]),
                                    Integer.parseInt(edate[1]), Integer.parseInt(edate[2]));
                            user.setNewDiscount(upc, discount, startdate, enddate);
                            String m = "\n" +Login.getUsername() + " set up new discount for " + user.getProduct(upc);
                            WriteFactory.writeLog(m);
                        } catch (NumberFormatException e1) {
                            JOptionPane.showMessageDialog(null,
                                    "Discount should be a number.");
                            String m = "\n" +Login.getUsername() + " failed to set up new discount.";
                            WriteFactory.writeLog(m);
                        } catch (NullPointerException e2) {
                            JOptionPane.showMessageDialog(null,
                                    "There's no such product.");
                            String m = "\n" +Login.getUsername() + " failed to set up new discount.";
                            WriteFactory.writeLog(m);
                        }
                    }
                } else if (command.equalsIgnoreCase("view profit")) {
                    TreePanel.setVisible(false);
                    JOptionPane.showMessageDialog(ManagerBoxFrame,
                            "total profit is " + user.viewProfit());
                    String m = "\n" +Login.getUsername() + " viewed the profit of the store.";
                    WriteFactory.writeLog(m);
                } else if (command.equalsIgnoreCase("view revenue")) {
                    TreePanel.setVisible(false);
                    JOptionPane.showMessageDialog(ManagerBoxFrame,
                            "total revenue is " + user.viewRevenue());
                    String m = "\n" +Login.getUsername() + " viewed the revenue of the store.";
                    WriteFactory.writeLog(m);
                } else if (command.equalsIgnoreCase("view product's pending order")) {
                    TreePanel.setVisible(false);
                    String upc = JOptionPane.showInputDialog(null,
                            "Product's UPC: ");
                    JOptionPane.showMessageDialog(null, user.viewPending(upc));
                    String m = "\n" +Login.getUsername() + " viewed the pending order of "
                            + user.getProduct(upc);
                    WriteFactory.writeLog(m);
                } else if (command.equalsIgnoreCase("set refund")) {
                    TreePanel.setVisible(false);

                    JTextField upcInput = new JTextField();
                    JTextField idInput = new JTextField();
                    Object[] message = {
                            "Input Product's UPC:", upcInput,
                            "Input Receipt's ID:", idInput,
                    };
                    int option = JOptionPane.showConfirmDialog(ManagerBoxFrame, message,
                            "Enter all your data", JOptionPane.OK_CANCEL_OPTION);
                    if (option == JOptionPane.OK_OPTION) {
                        try {
                            String upc = upcInput.getText();
                            int id = Integer.parseInt(idInput.getText());
                            user.returnProduct(upc, id);
                            String m = "\n" +Login.getUsername() + " return product: " + user.getProduct(upc);
                            WriteFactory.writeLog(m);
                        } catch (NumberFormatException e1) {
                            JOptionPane.showMessageDialog(null,
                                    "ID and UPC should be a number.");
                            String m = "\n" +Login.getUsername() + " failed to return product: number format";
                            WriteFactory.writeLog(m);
                        } catch (NullPointerException e2) {
                            String m = "\n" +Login.getUsername() + " failed to return product: number format";
                            WriteFactory.writeLog(m);

                        } catch (OperationFailedException e1) {
                            JOptionPane.showMessageDialog(null,
                                    "You can't return this.");
                            String m = "\n" +Login.getUsername() + " failed to return product: number format";
                            WriteFactory.writeLog(m);
                        }
                    }
                } else if (command.equalsIgnoreCase("add section")) {
                    TreePanel.setVisible(true);
                    JTextField sectionName = new JTextField();
                    JTextField parentName = new JTextField();
                    Object[] message = {
                            "Input new section's name:", sectionName,
                            "Input parent section's name: ", parentName,
                    };
                    int option = JOptionPane.showConfirmDialog(ManagerBoxFrame, message,
                            "Enter all your data", JOptionPane.OK_CANCEL_OPTION);
                    if (option == JOptionPane.OK_OPTION) {
                        String sectionNameText = sectionName.getText();
                        String parentNameText = parentName.getText();
                        try {
                            user.addSection(sectionNameText, parentNameText);
                            String m = "\n" +Login.getUsername() + " add section: " + sectionNameText
                                    + " under " + parentNameText;
                            WriteFactory.writeLog(m);
                        } catch (OperationFailedException e1) {
                            JOptionPane.showMessageDialog(null,
                                    e1.getMessage());
                            String m = "\n" +Login.getUsername() + " failed to add section";
                            WriteFactory.writeLog(m);
                        }
                    }
                } else if (command.equalsIgnoreCase("view sections")) {
                    DefaultMutableTreeNode root = ComponentCreater.getInstance().getTreeModel(record.getRoot());
                    tree1.setModel(new DefaultTreeModel(root));
                    TreePanel.setVisible(true);
                    String m = "\n" +Login.getUsername() + " view section.";
                    WriteFactory.writeLog(m);
                } else if (command.equalsIgnoreCase("onsale report")){
                    TreePanel.setVisible(false);
                    WriteFactory.writeOnSale(record);
                    String m = "\n" +Login.getUsername() + " create onsale report.";
                    WriteFactory.writeLog(m);
                } else if (command.equalsIgnoreCase("reorder all necessary")) {
                    TreePanel.setVisible(false);
                    WriteFactory.writeLog(Login.getUsername() + " reorder all necessary products.");
                    user.reOrderAll();
                    JOptionPane.showMessageDialog(null, "ReOrder Succeed!");
                } else if (command.equalsIgnoreCase("reorder report")) {
                    TreePanel.setVisible(false);
                    String m = "\n" +Login.getUsername() + " create reorder report.";
                    WriteFactory.writeLog(m);
                    JOptionPane.showMessageDialog(null, "Succeed!");
                    WriteFactory.writePending(user.userManagement.getStore().getProducts().getNeedReorder());
                } else if (command.equalsIgnoreCase("all products report")) {
                    TreePanel.setVisible(false);
                    String m = "\n" +Login.getUsername() + " create all products report.";
                    WriteFactory.writeLog(m);
                    JOptionPane.showMessageDialog(null, "Succeed!");
                    WriteFactory.writeAll(user.getEveryThing());
                }else if (command.equalsIgnoreCase("view all products")){
                    DefaultTableModel table = ComponentCreater.getInstance().getTableOfAll(user.getEveryThing());
                    table1.setModel(table);
                    infoPanel.setVisible(true);
                    upcPanel.setVisible(false);

                }
            }
        });
        reLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login log = null;
                try {
                    log = new Login();
                    String m = "\n" +Login.getUsername() + "logged out.";
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
                String m = "\n" +Login.getUsername() + "exit the system.";
                WriteFactory.writeLog(m);
                ManageStoreRecords.getStoreAll().getStoreRecords().get(0).setStoreRecord(record);
                ManageStoreRecords.save();
                dispose();
            }
        });
    }


}
import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.TableView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

/**
 * Created by wangj397 on 02/08/17.
 * This is a class that manage the interface for Receiver.
 */
public class ReceiverBox extends JFrame{
    /**
     * This field is the panel for receivers.
     */
    private JPanel receiverPanel;
    /**
     * This field is the combobox for receivers to choose a command.
     */
    private JComboBox comboBox1;
    /**
     * This field is the button for ok.
     */
    private JButton okButton1;
    /**
     * This is the panel for information for receivers.
     */
    private JPanel infoPanel;
    /**
     * This field is the editor pane for receivers to enter upc.
     */
    private JEditorPane upcInput;
    /**
     * This field is another button for ok.
     */
    private JButton okButton2;
    /**
     * This is a panel for upc.
     */
    private JPanel upcPanel;
    /**
     * This is the table for receiver.
     */
    private JTable table1 = null;
    /**
     * This field is the panel for the table.
     */
    private JScrollPane tablePanel;
    /**
     * This is the text field for receivers to enter their name.
     */
    private JTextField nameArea;
    /**
     * This is the text field for receivers to enter the cost.
     */
    private JTextField costArea;
    /**
     * This field is the text field for receivers to enter the price.
     */
    private JTextField priceArea;
    /**
     * This is the text field for receivers to enter the section.
     */
    private JTextField sectionArea;
    /**
     * This is the text field for receivers to enter the aisle.
     */
    private JTextField aisleArea;
    /**
     * This is the text field for receivers to enter the threshold.
     */
    private JTextField threshold;
    /**
     * This is the text field for receivers to enter the distributor.
     */
    private JTextField distributorArea;
    /**
     * This is the panel for new product.
     */
    private JPanel newProductPanel;
    /**
     * This is the button for adding a product.
     */
    private JButton addProductButton;
    /**
     * This is the text field for enter a threshold.
     */
    private JTextField thresholdArea;
    /**
     * This is the button for receivers to log out.
     */
    private JButton logoutButton;
    /**
     * This is the button for receivers to quit.
     */
    private JButton quitButton;
    /**
     * This is the tree in receiverbox.
     */
    private JTree tree1;
    /**
     * This is the panel for tree.
     */
    private JScrollPane TreePanel;
    /**
     * This indicates the user as a receiver.
     */
    private Receiver user;

    /**
     * This is the constructor of receiverbox interface.
     * @throws IOException it throws exception if the file don't exist.
     * @throws ClassNotFoundException it throws exception if the class can not be found.
     */
    ReceiverBox() throws IOException, ClassNotFoundException {
        super("Receiver Interface");
        infoPanel.setVisible(false);
        upcPanel.setVisible(false);
        newProductPanel.setVisible(false);
        TreePanel.setVisible(false);
        tree1.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);

        setContentPane(receiverPanel);
        setSize(750,1000);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        StoreRecord record = Start.getDomain();

        ManageUser mUser = record.getUsers();
        String name = Login.getUsername();
        String pass = Login.getUserpwd();
        user = new Receiver(name,pass,mUser);
        ReceiverBox ReceiverFrame = this;
        okButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = (String) comboBox1.getSelectedItem();
                if (command.equals("Scan in product")){
                    upcPanel.setVisible(true);
                    newProductPanel.setVisible(false);
                }else if (command.equals("Add product")){
                    upcPanel.setVisible(false);
                    infoPanel.setVisible(false);
                    newProductPanel.setVisible(true);
                    DefaultMutableTreeNode root = ComponentCreater.getInstance().getTreeModel(record.getRoot());
                    tree1.setModel(new DefaultTreeModel(root));
                    TreePanel.setVisible(true);
                } else if (command.equals("View location")){
                    upcPanel.setVisible(true);
                    infoPanel.setVisible(true);
                    TreePanel.setVisible(false);
                    newProductPanel.setVisible(false);
                } else if (command.equals("view price history")){
                    upcPanel.setVisible(true);
                    infoPanel.setVisible(true);
                    TreePanel.setVisible(false);
                    newProductPanel.setVisible(false);
                } else if (command.equals("view price")){
                    upcPanel.setVisible(true);
                    infoPanel.setVisible(true);
                    TreePanel.setVisible(false);
                    newProductPanel.setVisible(false);
                } else if (command.equalsIgnoreCase("view product number")){
                    infoPanel.setVisible(false);
                    upcPanel.setVisible(false);
                    TreePanel.setVisible(false);
                    newProductPanel.setVisible(false);
                    String message = "The product number in the store is: "+ user.getProNum();
                    JOptionPane.showMessageDialog(null,message);
                    String m = Login.getUsername() + " view product number ";
                    WriteFactory.writeLog(m);
                }else if (command.equalsIgnoreCase("view all products")){
                    infoPanel.setVisible(true);
                    upcPanel.setVisible(false);
                    TreePanel.setVisible(false);
                    newProductPanel.setVisible(false);
                    DefaultTableModel table = ComponentCreater.getInstance().getTableOfAll(user.getEveryThing());
                    table1.setModel(table);
                }
            }
        });
        okButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = (String) comboBox1.getSelectedItem();
                String upc = upcInput.getText();
                Product p = user.getProduct(upc);
                if (command.equals("Scan in product")){
                    user.addNew(upc);
                    DefaultTableModel table = ComponentCreater.getInstance().getTableByType("all",p);
                    table1.setModel(table);
                    infoPanel.setVisible(true);
                    TreePanel.setVisible(false);
                    String m = Login.getUsername() + " scanned in product: "
                            + user.getProduct(upc);
                    WriteFactory.writeLog(m);
                }else if (command.equals("View location")){
                    DefaultTableModel table = ComponentCreater.getInstance().getTableByType(command,p);
                    table1.setModel(table);
                    TreePanel.setVisible(false);
                    infoPanel.setVisible(true);
                    String m = Login.getUsername() + " viewed loation of product: "
                            + user.getProduct(upc);
                    WriteFactory.writeLog(m);
                }else if (command.equals("View price") || command.equals("View cost") ){
                    DefaultTableModel table = ComponentCreater.getInstance().getTableByType(command,p);
                    table1.setModel(table);
                    infoPanel.setVisible(true);
                    TreePanel.setVisible(false);
                    String m = Login.getUsername() + " viewed cost and price of product: "
                            + user.getProduct(upc);
                    WriteFactory.writeLog(m);
                }else if (command.equalsIgnoreCase("view price history")){
                    DefaultTableModel table = ComponentCreater.getInstance().getTableByType(command,p);
                    table1.setModel(table);
                    infoPanel.setVisible(true);
                    String m = Login.getUsername() + " viewed price history of product: "
                            + user.getProduct(upc);
                    WriteFactory.writeLog(m);
                    TreePanel.setVisible(false);
                }
            }
        });
        addProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                String name = nameArea.getText();
                Double cost = Double.valueOf(costArea.getText());
                Double price = Double.valueOf(priceArea.getText());
                String sec = sectionArea.getText();
                mSection section = record.getRoot().find(sec);
                if (section == null){
                    JOptionPane.showMessageDialog(null,"invalid section name");
                }
                int aisle= Integer.valueOf(aisleArea.getText());
                int thre= Integer.valueOf(thresholdArea.getText());
                String distributor = distributorArea.getText();
                Double[] pricecost = {cost,price};
                user.addProduct(name,pricecost,section,aisle,thre,distributor,record.getProducts());
                JOptionPane.showMessageDialog(null,"Added successfully.");
                String m = Login.getUsername() + " add new product: "
                        + name;
                WriteFactory.writeLog(m);

                } catch (Exception e1) {

                }
            }
        });
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login log = null;
                try {
                    log = new Login();
                    String m = Login.getUsername() + " logged out. ";
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
                ManageStoreRecords.getStoreAll().getStoreRecords().get(0).setStoreRecord(record);
                ManageStoreRecords.save();
                dispose();
                String m = Login.getUsername() + " exit the system. ";
                WriteFactory.writeLog(m);
            }
        });
        tree1.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                        tree1.getLastSelectedPathComponent();
                if (node == null)
                    return;
                Object sectionName = node.getUserObject();
                String section = (String)sectionName;
                sectionArea.setText(section);

            }
        });
    }
    public static void main (String[] args) throws IOException, ClassNotFoundException {
        new ReceiverBox().setVisible(true);
    }
}

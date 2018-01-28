import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Created by wangj397 on 02/08/17.
 * This class manage the interface for reshelverbox, including jframe components.
 */
public class ReshelverBox extends JFrame{
    /**
     * This field indicates panel for reshelver.
     */
    private JPanel reshelverPanel;
    /**
     * This field indicates the combobox for reshelver to choose command.
     */
    private JComboBox comboBox1;
    /**
     * This field is the editor pane for reshelvers to enter upc.
     */
    private JEditorPane upcinput;
    /**
     * This field is the button for ok.
     */
    private JButton okButton1;
    /**
     * This is another button for ok.
     */
    private JButton okButton2;
    /**
     * This is the panel for upc.
     */
    private JPanel upcPanel;
    /**
     * This is the table for reshelvers.
     */
    private JTable table1;
    /**
     * This field if the ok button.
     */
    private JButton okButton;
    /**
     * This is the panel for some information.
     */
    private JPanel infoPanel;
    /**
     * This is the text field for reshelver to change the aisle number.
     */
    private JTextField aisleOneArea;
    /**
     * The second text field to enter the aisle number that need to change with the first one.
     */
    private JTextField aisleTwoArea;
    /**
     * This is the panel for aisle.
     */
    private JPanel aislePanel;
    /**
     * This is the button for logout.
     */
    private JButton logoutButton;
    /**
     * This is the button for quit.
     */
    private JButton quitButton;
    /**
     * This is the usertype reshelver.
     */
    private Reshelver user;

    /**
     * This is the constructor for reshelverbox interface.
     */
    ReshelverBox(){
        super("Reshelver Interface");
        setContentPane(reshelverPanel);
        setSize(700,500);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        infoPanel.setVisible(false);
        upcPanel.setVisible(false);
        aislePanel.setVisible(false);
        StoreRecord record = Start.getDomain();

        ManageUser rUser = record.getUsers();
        String name = Login.getUsername();
        String pass = Login.getUserpwd();
        user = new Reshelver(name,pass,rUser);

        okButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = (String) comboBox1.getSelectedItem();
                if (command.equalsIgnoreCase("view location")){
                    upcPanel.setVisible(true);
                    aislePanel.setVisible(false);
                } else if (command.equalsIgnoreCase("view section")){
                    upcPanel.setVisible(true);
                    aislePanel.setVisible(false);

                } else if (command.equalsIgnoreCase("view order history")){
                    upcPanel.setVisible(true);
                    aislePanel.setVisible(false);

                } else if (command.equalsIgnoreCase("view product item num")){
                    upcPanel.setVisible(true);
                    aislePanel.setVisible(false);

                } else if (command.equalsIgnoreCase("change aisles")){
                    upcPanel.setVisible(false);
                    infoPanel.setVisible(false);
                    aislePanel.setVisible(true);
                }else if (command.equalsIgnoreCase("view all products")){
                    DefaultTableModel table = ComponentCreater.getInstance().getTableOfAll(user.getEveryThing());
                    table1.setModel(table);
                    infoPanel.setVisible(true);
                    aislePanel.setVisible(false);
                    upcPanel.setVisible(false);

                }

            }
        });
        okButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = (String) comboBox1.getSelectedItem();
                String upc = upcinput.getText();
                Product p = user.getProduct(upc);
                if (command.equalsIgnoreCase("View section")) {
                    DefaultTableModel table = ComponentCreater.getInstance().getTableByType(command,p);
                    table1.setModel(table);
                    infoPanel.setVisible(true);
                    aislePanel.setVisible(false);
                    String m = "\n" +Login.getUsername() + " viewed section of product: " + user.getProduct(upc);
                    WriteFactory.writeLog(m);
                } else if (command.equalsIgnoreCase("View location")) {
                    DefaultTableModel table = ComponentCreater.getInstance().getTableByType(command,p);
                    table1.setModel(table);
                    infoPanel.setVisible(true);
                    aislePanel.setVisible(false);
                    String m = "\n" +Login.getUsername() + " viewed loation of product: " + user.getProduct(upc);
                    WriteFactory.writeLog(m);
                } else if (command.equalsIgnoreCase("View order history")) {
                    DefaultTableModel table = ComponentCreater.getInstance().getTableByType(command,p);
                    table1.setModel(table);
                    infoPanel.setVisible(true);
                    aislePanel.setVisible(false);
                    String m = "\n" +Login.getUsername() + " viewed order history of product: " + user.getProduct(upc);
                    WriteFactory.writeLog(m);
                } else if (command.equalsIgnoreCase("View item number")) {
                    DefaultTableModel table = ComponentCreater.getInstance().getTableByType(command,p);
                    table1.setModel(table);
                    infoPanel.setVisible(true);
                    aislePanel.setVisible(false);
                    String m = "\n" +Login.getUsername() + " viewed item number of product: " + user.getProduct(upc);
                    WriteFactory.writeLog(m);
                } else if (command.equalsIgnoreCase("Change aisles")) {
                    int aisle1 = Integer.valueOf(aisleOneArea.getText());
                    int aisle2 = Integer.valueOf(aisleTwoArea.getText());
                    user.changeAisle(aisle1,aisle2);
                    JOptionPane.showMessageDialog(null,"complete change: "
                            + aisle1 + " and aisle" + aisle2);
                    String m = "\n" +Login.getUsername() + " changed aisle: " + aisle1 + " and aisle" + aisle2;
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
                    String m = "\n" +Login.getUsername() + " logged out.";
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
                String m = "\n" +Login.getUsername() + " exit the system.";
                WriteFactory.writeLog(m);
            }
        });
    }
}
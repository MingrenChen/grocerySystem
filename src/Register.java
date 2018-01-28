import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by wangj397 on 05/08/17.
 * This class managa register interface, including the jframe components.
 */
public class Register extends JFrame{
    /**
     * This field indicates the botton for ok.
     */
    private JButton okButton;
    /**
     * This field indicates the text field for registerer to enter their username.
     */
    private JTextField usernameArea;
    /**
     * This field is the text field for registerers to enter their password.
     */
    private JTextArea passwordArea;
    /**
     * This field is the combobox for registerer to choose their usertype.
     */
    private JComboBox usertypeArea;
    /**
     * This field id the panel for register.
     */
    private JPanel registerPanel;
    /**
     * This field indicates the button for registerer to login.
     */
    private JButton loginButton;
    /**
     * The field is the button for quitting.
     */
    private JButton quitButton;
    /**
     * This means the user.
     */
    private User user;
    StoreRecord domain = Start.getDomain();

    /**
     * This is the constructor for register interface.
     * It will come out when the user don't have an account before.
     */
    public Register() {
        super("Register");
        setContentPane(registerPanel);
        setSize(500,500);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String username = usernameArea.getText();
                String password = passwordArea.getText();
                String type = (String) usertypeArea.getSelectedItem();
                ManageUser usertype = domain.getUsers();
                switch (type){
                    case "Manager":
                        user = new Manager(username,password,usertype);
                        break;
                    case "Receiver":
                        user = new Receiver(username,password,usertype);
                        break;
                    case "Cashier":
                        user = new Cashier(username,password,usertype);
                        break;
                    case "Reshelver":
                        user = new Reshelver(username,password,usertype);
                        break;
                    default:
                        break;
                }
                JOptionPane.showMessageDialog(null,
                        "Welcome " +username +" !"+
                                "\nPlease click login button next.");
            }
        });
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StoreRecord domain = Start.getDomain();
                Login log = null;
                try {
                    log = new Login();
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
                ManageStoreRecords.getStoreAll().getStoreRecords().get(0).setStoreRecord(domain);
                ManageStoreRecords.save();
                dispose();
            }
        });
    }
    public static void main (String[] args) throws IOException, ClassNotFoundException {
        new Register().setVisible(true);
    }
}

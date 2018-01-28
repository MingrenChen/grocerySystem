import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by wangj397 on 02/08/17.
 * This is a class that has the interface for users to login.
 */
public class Login extends JFrame{
    /**
     * This field means the user type.
     */
    private static User user;
    /**
     * This field means the username in string.
     */
    private static String username = null;
    /**
     * This field indicates the password for users.
     */
    private static String userpwd= null;
    /**
     * This field indicates the button for users to login.
     */
    private JButton loginButton;
    /**
     * This field indicates the text field for users to enter their password.
     */
    private JTextField Pass;
    /**
     * This field means the panel for login.
     */
    private JPanel loginPanel;
    /**
     * This field means the combobox for users to choose their usertype.
     */
    private JComboBox usertype;
    /**
     * This field means the text field for users to indicates their name.
     */
    private JTextField userName;
    /**
     * This field means the button for users to register.
     */
    private JButton registerButton;
    /**
     * This field means the button for users to quit.
     */
    private JButton quitButton;

    /**
     * This is the construtor for login interface.
     * @throws ClassNotFoundException it throws exception when the class don't exist.
     * @throws IOException it throws exception when the file of user don't exist.
     */
    public Login() throws ClassNotFoundException, IOException {
        super("Login");
        setContentPane(loginPanel);
        setSize(500,500);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        StoreRecord domain = Start.getDomain();
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String UserType = ((String) usertype.getSelectedItem());
                ManageUser mUser = domain.getUsers();
                username = userName.getText();
                userpwd = Pass.getText();
                try {
                    user = domain.getUsers().getUserByName(username);
                    if (user.checkCorrect(userpwd)) {
                        switch (UserType) {
                            case "Manager":
                                JOptionPane.showMessageDialog(null,
                                        "Logined as Manager");
                                ManagerBox managerBox = null;
                                managerBox = new ManagerBox();
                                managerBox.setVisible(true);
                                dispose();
                                break;
                            case "Cashier":
                                JOptionPane.showMessageDialog(null,
                                        "Logined as Cashier");
                                CashierBox cashierBox = new CashierBox();
                                cashierBox.setVisible(true);
                                dispose();
                                break;
                            case "Reshelver":
                                JOptionPane.showMessageDialog(null,
                                        "Logined as Reshelver");
                                ReshelverBox reshelverBox = new ReshelverBox();
                                reshelverBox.setVisible(true);
                                dispose();
                                break;
                            case "Receiver":
                                JOptionPane.showMessageDialog(null,
                                        "Logined as Receiver");
                                ReceiverBox receiverBox = new ReceiverBox();
                                receiverBox.setVisible(true);
                                dispose();
                                break;
                        }
                        String message = "\n"+username + " logined as " + UserType;
                        WriteFactory.writeLog(message);

                    }
                } catch (OperationFailedException e1) {
                    JOptionPane.showMessageDialog(null,
                            e1.getMessage());
                    String message = "\n"+username + " failed to login as " + UserType;
                    WriteFactory.writeLog(message);
                } catch (IOException e1) {
                    e1.printStackTrace();
                    String message = "\n"+username + " failed to login as " + UserType;
                    WriteFactory.writeLog(message);
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                    String message = "\n"+username + " failed to login as " + UserType;
                    WriteFactory.writeLog(message);
                } catch (NullPointerException e1) {
                    JOptionPane.showMessageDialog(null,
                            "No Such user!");
                    String message = "\n"+username + " failed to login as " + UserType;
                    WriteFactory.writeLog(message);
                } catch (ClassCastException e1) {
                    JOptionPane.showMessageDialog(null,
                            "UserType is uncorrected!");
                    String message = "\n"+username + " failed to login as " + UserType;
                    WriteFactory.writeLog(message);
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Register register = new Register();
                register.setVisible(true);
                dispose();
                String message = "\nUser choose to register on Workplace: "+ domain.getName();
                WriteFactory.getInstance().writeLog(message);
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

    public static String getUserpwd() {
        return userpwd;
    }

    public static String getUsername() {

        return username;
    }

    public static User getUser() {
        return user;
    }

    public static void main (String[] args) throws IOException, ClassNotFoundException {
        new Login().setVisible(true);
    }


}

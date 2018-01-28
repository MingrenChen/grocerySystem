
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by wangj397 on 05/08/17.
 * This is an interface for users to start their login or register.
 */
public class Start extends JFrame{
    /**
     * This field indicates the store record.
     */
    private static StoreRecord domain = null;
    /**
     * This field indicates the panel for users to start.
     */
    private JPanel startPanel;
    /**
     * This field indicates the combobox of options for users.
     */
    private JComboBox domainBox;
    /**
     * This field indicates the button to login.
     */
    private JButton loginButton;
    /**
     * This field indicates the button to register.
     */
    private JButton registerButton;

    /**
     * Contructor of startbox.
     * An interface for users to start the GUI form.
     */
    public Start() {
        super("Start");
        setContentPane(startPanel);
        setSize(500,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text  = (String) domainBox.getSelectedItem();
                for (StoreRecord record: ManageStoreRecords.getStoreAll().getStoreRecords()){
                    if (record.getName().equals(text)){
                        domain = record;
                    }
                }
                if (domain == null){
                    domain = new StoreRecord(text);
                }

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
                String message = "User choose to login on Workplace: "+ domain.getName();
                WriteFactory.getInstance().writeLog(message);
                ManageStoreRecords.getStoreAll().getStoreRecords().get(0).setStoreRecord(domain);
                ManageStoreRecords.save();


            }
        });
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text  = (String) domainBox.getSelectedItem();
                for (StoreRecord record: ManageStoreRecords.getStoreAll().getStoreRecords()) {
                    if (record.getName().equals(text)) {
                        domain = record;
                    }
                }
                if (domain == null){
                    domain = new StoreRecord(text);
                }
                Register register = new Register();
                register.setVisible(true);
                dispose();
                String message = "User choose to register on Workplace: "+ domain.getName();
                WriteFactory.getInstance().writeLog(message);
                ManageStoreRecords.getStoreAll().getStoreRecords().get(0).setStoreRecord(domain);
                ManageStoreRecords.save();
            }
        });
    }
    public static void main (String[] args) throws IOException, ClassNotFoundException {
        new Start().setVisible(true);
    }
    public static StoreRecord getDomain() {
        return domain;
    }



}

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by chenmi84 on 04/08/17.
 * This class is meant to manage users.
 */
public class ManageUser implements Serializable {

    private static final long serialVersionUID = 1000057289;
    /**
     * This create a new users in arraylist.
     */
    private ArrayList<User> users = new ArrayList<User>();
    /**
     * This is the store record of the store.
     */
    private StoreRecord Store;

    /**
     * Manage the users in this store.
     * @param thisStore this store
     */
    ManageUser(StoreRecord thisStore) {
        Manager manager = new Manager("Manager","Manager" , this);
        Cashier cashier = new Cashier("Cashier","Cashier" , this);
        Reshelver reshelver = new Reshelver("Reshelver","Reshelver" , this);
        Receiver receiver = new Receiver("Receiver","Receiver" , this);
        Store = thisStore;
    }

    /**
     * Add a user in the store.
     * @param u the user
     */
    void addUser(User u){
        users.add(u);
    }

    /**
     * Return the store record.
     * @return store record
     */
    StoreRecord getStore() {
        return Store;
    }

    /**
     * Set a new password of a user.
     * @param username the username
     * @param pwd a new password
     * @param usertype type of user
     */
    public void setNewPassword(String username, String pwd, String usertype){
        User user = getUserByName(username);
        if (user != null){
            user.setPassword(pwd);
            ManageStoreRecords.save();
        }else {
            switch (usertype){
                case "Manager":
                    user = new Manager(username, pwd,this);
                    break;
                case "Receiver":
                    user = new Receiver(username, pwd,this);
                    break;
                case "Cashier":
                    user = new Cashier(username, pwd,this);
                    break;
                case "Reshelver":
                    user = new Reshelver(username, pwd,this);
                    break;
                default:
                    break;
            }
            ManageStoreRecords.save();
        }
    }

    /**
     * Return the user's identity in the system.
     * @param userName user's name
     * @return user type
     */
    User getUserByName(String userName) {
        for (User user : users) {
            if (user.getUserName().equals(userName)) {
                return user;
            }
        }
        return null;
    }

}

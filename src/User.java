import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Penny on 2017-07-15.
 * A class that is for users.
 */
public abstract class User implements Serializable {
  private static final long serialVersionUID = 1057457982;
  /**
   * four types: manager, cashier, reshelier, receiver.
   */
  ManageUser userManagement;
  /**
   * A user's name
   */
  String userName;
  /**
   * User's password.
   */
  String userPassword;

  public String getUserName() {
    return userName;
  }

  /**
   * A user's setting information
   * @param name the name
   * @param pass the password
   * @param type the type
   * @param manage the manage
   */
  User(String name, String pass,String type,ManageUser manage) {
    userName = name;
    userPassword = pass;
    userManagement = manage;
    userManagement.addUser(this);

  }

  /**
   * Return the store we want.
   * @return the store
   */
  StoreRecord getStore() {
    return userManagement.getStore();
  }

  /**
   * Return a product.
   * @param UPC the upc
   * @return a product
   */
  Product getProduct(String UPC) {
    return getStore().getProducts().getProduct(UPC);
  }

  /**
   * re-order all the products that need to be reorder.
   */
  void reOrderAll() {
    for (Product product : getStore().getProducts().getNeedReorder()) {
      product.getOrder().requestReorder();
    }
  }

  /**
   * Set the password for the user.
   * @param password the password
   */
  void setPassword(String password){
    userPassword = password;
  }


  /**
   * check if username and password is correct.
   * @param password
   * @return
   */
  boolean checkCorrect(String password) throws OperationFailedException {
    if (password.equals(userPassword)) {
      return true;
    }
    throw new OperationFailedException("Wrong Password!");
  }

  /**
   * return all the products in the store
   * @return ArrayList/<Products/> ArrayList of all the products.
   */
  ArrayList<Product> getEveryThing() {
    return userManagement.getStore().getProducts().getProducts();
  }

  /**
   * Return if one user in the same store and they are the same identity.
   * @param user the user
   * @return return true if two users are equal
   */
  public boolean equals(User user) {
    if (!user.getStore().equals(getStore())) {
      return false;
    }
    if (this instanceof Cashier) {
      return user instanceof Cashier;
    } else if (this instanceof Manager) {
      return user instanceof Manager;
    } else if (this instanceof Receiver) {
      return user instanceof Receiver;
    } else if (this instanceof Reshelver) {
      return user instanceof Reshelver;
    }
    return false;
  }
}


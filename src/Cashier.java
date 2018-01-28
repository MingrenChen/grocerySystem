import java.io.IOException;
import java.io.Serializable;

/**
 * Created by chenmi84 on 18/07/17.
 * cashier class contains methods that a cashier needs.
 */

public class Cashier extends User implements Serializable{

  private static final long serialVersionUID = 1000057482;
  /**
   * This field is to set the paylist to null.
   */
  private PayList receipt = null;

  /**
   * This helps check the password when cashier logs into the system.
   */
  Cashier(String name, String pass,ManageUser user) {
    super(name,pass,"Cashier",user);
  }

  /**
   * Return thr receipt of paylist.
   * @return the receipt
   */
  PayList getReceipt() {
        return receipt;
    }

    /**
   * This method helps cashier scan items into the system when
   * customers buy things.
   * @param upc the universal product code
   */
  void scan(String upc)  {
    if (receipt != null) {
      receipt.addItem(upc);
    } else {
      receipt = new PayList(userManagement.getStore().getPayList());
      receipt.addItem(upc);
    }
  }

  /**
   * This helps record customers' payment information at checkout.
   * @param type the type of their card
   * @param cnum cardnumber
   */
  void checkOut(int type, int cnum) {
    System.out.println("Receipt id:" + receipt.getId());
    getStore().getPayList().addPayList(receipt);
    receipt.purchase(type,cnum);
    receipt = null;
  }

  /**
   * It cancels items on a purchase.
   * @param upc the universal product code
   */
  void cancelOne(String upc) {
    try {
      receipt.cancelItem(upc);
    } catch (OperationFailedException e) {
      e.printStackTrace();
    }

  }

  /**
   * It checks a discount and its effective date.
   * @param upc the universal product code
   */
  String viewDiscount(String upc) {
    Discount discount = getProduct(upc).getDiscounts();
    return "Discount started at: " + discount.getStartDate() + "\n"
        + "Discount ended at: " + discount.getEndDate();
  }

  /**
   * It cancels the entire purchase.
   */
  void cancelAll() {
    receipt = null;
  }

  /**
   * Return a string of cashier
   * @return a string
   */
  public String toString(){
    return "Cashier: " + userName;
  }

}

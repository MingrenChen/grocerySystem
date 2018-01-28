import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;

/**
 * This class indicate a receipt when a customer wants to proceed check out.
 */
public class PayList implements Serializable {
  private ManagePayList managePaylist;

  private static final long serialVersionUID = 1000047893;
  public void setPayList(HashMap<Product, Integer> payList) {
    this.payList = payList;
  }

  /**
   * things need to be pay.
   */
  private HashMap<Product, Integer> payList;
  /**
   * The local date
   */
  private LocalDate date;

  /**
   * Return the date of local date.
   * @return the date of local date
   */
  public LocalDate getDate() {
    return date;
  }

  /**
   * Return the hashmap of the paylist
   * @return the paylist
   */
  public HashMap<Product, Integer> getPayList() {
    return payList;
  }

  /**
   * Set Revenue to be o
   */
  private double totalRevenue = 0.0;

  /**
   * prize for this pay list.
   */
  private double totalPrice = 0;

  /**
   * Payment type of this pay list. -1 for not pay yet,
   * 0 for credit, 1 for debit, 2 for cash.
   */
  private int paymentType = -1;

  /**
   * card number of this payment if pay by card or -1
   * for not paid yet and 0 for cash.
   */
  private int cardNumber = -1;

  /**
   * Return the id.
   * @return the id
   */
  public int getId() {
    return id;
  }

  /**
   * Return the total price of a paylist.
   * @return the total price
   */
  double getTotalPrice() {
    return totalPrice;
  }

  /**
   * This field is the id for paylist.
   */
  private int id;

  /**
   * manage all paylist.
   * @param mpaylist manage paylist
   */
  PayList(ManagePayList mpaylist) {
    managePaylist = mpaylist;
    mpaylist.addPayList(this);
    getStore().IDadd();
    int nId = getStore().getNextID();
    payList =  new HashMap<Product, Integer>();
    id = nId;
  }

  /**
   * Return a store record.
   * @return the store record
   */
  StoreRecord getStore(){
    return managePaylist.getStore();
  }
  /**
   * Add items to the paylist for the customers.
   * @param upc the universal product code
   */
  void addItem(String upc){
    int num;
    Product item = getStore().getProducts().getProduct(upc);
    if (!payList.containsKey(item)) {
      payList.put(item, 1);
    } else {
      num = payList.get(item);
      payList.put(item, num + 1);
    }
    totalPrice = 0;
    for (Product product : payList.keySet()) {
      totalPrice += product.checkout(payList.get(product));
    }
  }

  /**
   * Cancel items in the paylist for a customer.
   * @param upc the universal product code
   * @throws OperationFailedException when upc does not exist,throw exception.
   */
  void cancelItem(String upc) throws OperationFailedException {
    int num;
    Product item = getStore().getProducts().getProduct(upc);
    if (!payList.containsKey(item)) {
      throw new OperationFailedException("You didn't scan this item.");
    } else {
      num = payList.get(item);
      payList.put(item, num - 1);
    }
    totalPrice = 0;
    for (Product product : payList.keySet()) {
      totalPrice += product.checkout(payList.get(product));
    }
  }

  /**
   * Check the information of a certain
   * purchase, it includes the cardnumber,
   * items they bought, price of the items
   * , date and time.
   * @param type payment type
   * @param cnum the card number
   */
  void purchase(int type, int cnum) {
    int price = 0;
    paymentType = type;
    cardNumber = cnum;
    for (Product product : payList.keySet()) {
      product.removeItem(payList.get(product));
      price += product.getCost() * payList.get(product);
      totalRevenue += product.getPrice() * payList.get(product);
    }
    getStore().addProfit(totalPrice - price);
    getStore().addRevenue(totalPrice);
  }

  /**
   * Set the id of a paylist
   * @param id the id
   */
  public void setId(int id) {
    this.id = id;
  }
}

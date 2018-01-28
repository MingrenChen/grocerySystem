import javax.swing.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.time.temporal.ChronoUnit;
import java.util.List;


/**
 * One kind of the product in the store.
 */
public class Product implements Serializable {
    /**
     * This field indicates the manageproducts.
     */
    private ManageProducts manageProducts;

    private static final long serialVersionUID = 1000057982;
 /**
  * This field indicates the date of today.
  */
  private LocalDate today = LocalDate.now();
  /**
   * This field checks the price of an item on a certain date.
   */
  private HashMap<LocalDate, Double> pricehistory = new HashMap<>();
  /**
   * This indicates the name of a product.
   */
  private String name;
  /**
   * This indicates the price of a product.
   */
  private double price;
    /**
     * The start discount is null.
     */
  private Discount discounts = null;

  /**
   * This indicates the cost of a product.
   */

  private double cost;
  /**
   * This indicates the upc number of a product.
   */
  private String upc;
  /**
   * This indicates the item number of a product.
   */
  private int itemNum;
    /**
     * This indicates the location of a product.
     */
  private Location location;
    /**
     * This indicates the order of a product.
     */
  private Order order;

    /**
     * A product's information that is being stored.
     * @param productName name of product
     * @param CostPrice the cost of it
     * @param inputLocation the location of the product.
     * @param inputOrder the order of the product
     * @param mproduct the manage of product
     */
  Product(String productName,Double[] CostPrice, Location inputLocation, Order inputOrder, ManageProducts mproduct) {
    mproduct.getStore().UPCadd();
    upc = Integer.toString(mproduct.getStore().getNextUPC());
    int zeroNum = 12 - upc.length();
    for (int i = zeroNum; i > 0; i--) {
      upc = "0" + upc;
    }
    System.out.print(productName + " : " + upc);
    cost = CostPrice[0];
    name = productName;
    price = CostPrice[1];
    location = inputLocation;
    order = inputOrder;
    manageProducts = mproduct;
    order.requestReorder();
    manageProducts.addProduct(this);
  }

    /**
     * Return the store record of a store
     * @return the store record
     */
    StoreRecord getStore() {
        return manageProducts.getStore();}

  /**
   * Return the price history of an item.
   * @return return price history
   */

  HashMap<LocalDate, Double> getPriceHistory(){
      return pricehistory;
        }


  /**
   * Return name of an item.
   * @return return the name of an item
   */
  public String getName() {
    return name;
  }

    /**
     * Return the manage of product.
     * @return product manage
     */
    ManageProducts getManageProducts() {
        return manageProducts;
    }

    /**
   * This method is used to get price of product.
   * @return price of product
   */
  public double getPrice() {
    if (discounts != null) {
      if (today.isBefore(discounts.getEndDate())) {
        price = this.checkout(1);
      }
    }
    return price;
  }

  /**
   * Set the price of a product.
   * @param price the price
   */
  public void setPrice(double price) {
    this.price = price;
  }

  /**
   * Return the discounts of a product in current time.
   * @return all the discounts of a product
   */
  Discount getDiscounts() {
    return discounts;
  }

  /**
   * Return the cost of a product.
   * @return cost of a product
   */
  double getCost() {
    return cost;
  }

  /**
   * Return the upc number of an item.
   * @return upc number
   */
  public String getUpc() {
    return upc;
  }

  /**
   * Return the item number
   * @return item number
   */
  int getItemNum() {
    return itemNum;
  }

    /**
     * Add certain num of the product.
     * @param num number of product.
     */
  void addNewbyNum(int num){
      itemNum += num;
  }

  /**
   * Return null, since the method just remove the item from the shopping cart.
   * @param num number of items the customer buy.
   */
  void removeItem(int num) {
    itemNum -= num;
    int threshold = order.getThreshold();
    if (itemNum < threshold && !order.isNeedReOrder()) {
      order.setNeedReOrder(true);
      JOptionPane.showMessageDialog(null, name + " needs reorder!");
    }
  }

    /**
     * Set the item number
     * @param itemNum item number
     */
  void setItemNum(int itemNum) {
    this.itemNum = itemNum;
  }

  /**
   * Add a discount to a product between a period of time,
   * after that, the price of the product will follow the discount
   * plan during that period of time.
   * @param d the discount
   * @param start the date the discount starts
   * @param end the date the discount ends
   */
  void addDiscount(double d, LocalDate start, LocalDate end) {
    discounts = new Discount(d,start,end);
    if (pricehistory.containsKey(start)) {
      pricehistory.replace(start,this.checkout(1));
      pricehistory.put(end.plus(1,ChronoUnit.DAYS),price);
    } else {
      pricehistory.put(start,this.checkout(1));
      pricehistory.put(end.plus(1,ChronoUnit.DAYS),price);
    }
  }


  /**
   * Return the price that the customer needs to pay at checkout.
   * @param num the number of items the customer buy
   * @return the price in total
   */
  double checkout(int num) {
    Double returnPrice = price * num;
    if (discounts != null) {
      if (today.isBefore(discounts.getEndDate())) {
        returnPrice = discounts.getPrice(price,num);
      }
    }
    return returnPrice;
  }

    /**
     * Return the price of a product on a date
     * @param d the date
     * @return the price
     */
  double getPriceByDate(LocalDate d){
      List<LocalDate> dateHistory = new ArrayList<>(pricehistory.keySet());
      if (!(dateHistory.isEmpty())){
          dateHistory.sort(Comparator.comparing(LocalDate::atStartOfDay));
          if (d.isAfter(dateHistory.get(-1))){
              return pricehistory.get(dateHistory.get(-1));
          } else{
              for (LocalDate date: dateHistory){
                  if (d.isBefore(date)){
                      LocalDate priceDate = dateHistory.get(dateHistory.indexOf(date)-1);
                      return pricehistory.get(priceDate);
                  }
              }
          }
      }
      return this.checkout(1);
  }
  /**
   * Add a new product into the store.
   */
  void addNew() {
    int num = getItemNum();
    num += getOrder().getThreshold() * 3;
    setItemNum(num);
    getOrder().setPendingNumber(0);
    getOrder().setNeedReOrder(false);
    getOrder().getOrderHistory().add(today);
  }

    /**
     * Return a string of a product
     * @return a string
     */
  @Override
  public String toString() {
    return name;
  }

    /**
     * Return the loation of a product
     * @return the location
     */
  public Location getLocation() {
    return location;
  }

    /**
     * Return the order of a product.
     * @return the order
     */
  public Order getOrder() {
    return order;
  }

}

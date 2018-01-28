import javax.swing.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Created by Penny on 2017-08-03.
 * This class have the information of all orders.
 */
public class Order implements Serializable {

  private static final long serialVersionUID = 777057982;
  /**
   * This field indicates the date of today.
   */
  private LocalDate today = LocalDate.now();
  /**
   * This indicates the lowest number of a product before we reorder it.
   */
  private int threshold;
  /**
   * This indicates the name of the distributor.
   */
  private String distributor;
  /**
   * This indicates the pending number of a product when we reorder it.
   */
  private int pendingNumber = 0;

  private boolean needReOrder = true;
  /**
   * This field indicates the order history of the products in the store.
   */
  private ArrayList<LocalDate> orderHistory = new ArrayList<LocalDate>();

  Order(int r,String company){
    threshold = r;
    distributor = company;
  }

  /**
   * Return the threshold number of a product.
   * @return the threshold number
   */
  int getThreshold() {
    return threshold;
  }

  /**
   * Return the pending number of an item that is in order.
   * @return the pending number
   */
  int getPendingNumber() {
    return pendingNumber;
  }

  /**
   * Return the order history in one day.
   * @return order history in one day
   */
  ArrayList<LocalDate> getOrderHistory() {
    return orderHistory;
  }

  /**
   * Return today's date
   * @return the date
   */
  public LocalDate getToday() {
    return today;
  }

  /**
   * Set the date of today.
   * @param today date of today
   */
  public void setToday(LocalDate today) {
    this.today = today;
  }

  /**
   * Set the order history on a day
   * @param orderHistory the order history
   */
  public void setOrderHistory(ArrayList<LocalDate> orderHistory) {

    this.orderHistory = orderHistory;
  }

  /**
   * Set the pending number of a order.
   * @param pendingNumber the pending number
   */
  void setPendingNumber(int pendingNumber) {

    this.pendingNumber = pendingNumber;
  }

  /**
   * Set a product to be reordered
   * @param needReOrder if this need to be reorder
   */
  void setNeedReOrder(boolean needReOrder) {

    this.needReOrder = needReOrder;
  }

  /**
   * Return the name of the distributor
   * @return the distributor
   */
  String getDistributor() {

    return distributor;
  }

  /**
   * Set the name of distributor
   * @param distributor the distributor
   */
  public void setDistributor(String distributor) {
    this.distributor = distributor;
  }


  /**
   * When a product's amount reaches the threshold number and we need to reorder.
   */
  void requestReorder() {
    needReOrder = false;
    pendingNumber = threshold * 3;
  }

  /**
   * Return ture if a product need to be reordered.
   * @return true if a product need to be reordered
   */
  boolean isNeedReOrder() {
    return needReOrder;
  }


}

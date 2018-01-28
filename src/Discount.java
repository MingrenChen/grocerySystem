import java.io.Serializable;
import java.time.LocalDate;

/**
 * Created by Harry on 2017/7/9.
 * Discount is an ArrayList contains the discount
 * of a specific product.
 */
public class Discount implements Serializable {

  private static final long serialVersionUID = 1000057789;
  /**
   * The start date of the discount.
   */
  private LocalDate startDate;
  /**
   * The end date of the discount.
   */
  private LocalDate endDate;

  /**
   * The type of a discount.
   * There are three type of discount:
   * 0 for i% off,
   * 1 for buy x get next y i% off,
   * 2 for buy x get everything i% percent off.
   */
  private int discountType;


  /**
   * Discount of a product.
   */
  private double discount;

  /**
   * The condition that you buy x items.
   */
  private int buyX;

  /**
   * The condition that you can get next Y items with discount.
   */
  private int nextY;

  /**
   * Constructor for type 0 discount.
   * This kind of discount will apply to all products in the store.
   * @param off double the percent of the discount
   */

  Discount(double off, LocalDate start, LocalDate end) {
    startDate = start;
    endDate = end;
    discountType = 0;
    discount = off;
  }

  /**
   * Return the discount.
   * @return return the discount percent.
   */

  LocalDate getStartDate() {
    return startDate;
  }

  /**
   * Return the end date of discount.
   * @return return the end date.
   */
  LocalDate getEndDate() {
    return endDate;
  }

  /**
   * Return the final price when applying this discount.
   * @param price double original price for the product
   * @param number int amount of product
   * @return double the final price
   */
  double getPrice(double price, int number) {
    if (discountType == 0) {
      return number * price * discount;
    } else if (discountType == 1) {
      if (number >= buyX) {
        return
                buyX * price + nextY * price * (1 - discount)
                    + this.getPrice(price, number - buyX - nextY);
      } else {
        return number * price;
      }
    } else {
      if (number >= buyX) {
        return number * price * discount;
      } else {
        return number * price;
      }
    }
  }

  /**
   * Return a string of a discount
   * @return a string
   */
  @Override
  public String toString() {
    return String.valueOf(discount * 100) + "%";
  }
}

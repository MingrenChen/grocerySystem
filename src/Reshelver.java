import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Created by chenmi84 on 18/07/17.
 * A class is created for reshelver.
 */
public class Reshelver extends User implements Serializable{

  private static final long serialVersionUID = 1007777982;
  /**
   * create a new Reshelver.
   */
  Reshelver(String name, String pass,ManageUser user) {
    super(name,pass,"Reshelver",user);
  }

  /**
   * view location of a product by upc.
   * @param upc String
   */
  int viewLocation(String upc) {
    return getProduct(upc).getLocation().getAisle();
  }

  /**
   * view the section of a product is at.
   * @param upc the universal product code
   */
  String viewSection(String upc) {
    return ("The product is under section: " + getProduct(upc).getLocation().getSection().toString());
  }

  /**
   * View the Order History of a product by UPC.
   * @param upc String
   */
  ArrayList<LocalDate> viewOrderHistory(String upc) {
    ArrayList<LocalDate> list = getProduct(upc).getOrder().getOrderHistory();
    for (LocalDate date : list) {
      list.add(date);
    }
    return list;
  }

  /**
   * return the number of a specific product.
   * @param upc String
   */
  int viewItemNum(String upc) {
    return(getProduct(upc).getItemNum());
  }

  /**
   * exchange the prodcuts on two aisles.
   * @param aisle1 int
   * @param aisle2 int
   */
  void changeAisle(int aisle1, int aisle2) {
    getStore().getProducts().changeAisle(aisle1, aisle2);
  }

  /**
   * Return a string about a reshelver
   * @return a string
   */
  public String toString(){
    return "Reshelver: " + userName;
  }
}
import java.io.*;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Mingren Chen on 2017/7/10.
 * StoreRecord class contains all paylists and product information in the store.
 */
public class StoreRecord implements Serializable{
  private static final long serialVersionUID = 1000066982;
  /**
   * This field indicate total revenue.
   */
  private double totalRevenue = 0.0;

  /**
   * the root section of the store.
   */
  private mSection root;
  /**
   * This field indicates the total profit.
   */
  private double totalProfit = 0;
  /**
   * This field indicates the name of a product.
   */
  private String name;

  /**
   * This field indicates all products.
   */
  private ManageProducts products;
  /**
   * This field indicates all the paylists.
   */
  private ManagePayList payList;
  /**
   * This field indicates all the users.
   */
  private ManageUser Users;

  private int nextID = 0;

  private int nextUPC = 0;

  /**
   * Return the parent of a subsection.
   * @return the parent section
   */
  mSection getRoot() {
    return root;
  }

  /**
   * The information in the store record.
   * @param Sname the name of a store
   */
  StoreRecord(String Sname) {
    name = Sname;
    root = new mSection(this,"Store");
    products = new ManageProducts(this);
    payList = new ManagePayList(this);
    Users = new ManageUser(this);
    ManageStoreRecords.getStoreAll().addStoreRecord(this);
    root.addSeven();
  }

  /**
   * Return the paylist
   * @return the paylist
   */
  ManagePayList getPayList() {
    return payList;
  }

  /**
   * Return the products of manage product.
   * @return the products on the paylist
   */
  ManageProducts getProducts() {
    return products;
  }

  /**
   * Return the users.
   * @return the users
   */
  ManageUser getUsers() {
    return Users;
  }

  /**
   * Get the total revenue of a store.
   * @return the total revenue
   */
  double getTotalRevenue() {
    return totalRevenue;
  }

  /**
   * Set thr total revenue of a store.
   * @param totalRevenue the total revenue
   */
  void setTotalRevenue(double totalRevenue) {
    this.totalRevenue = totalRevenue;
  }

  /**
   * Return the total profit of a store.
   * @return the total profit
   */
  double getTotalProfit() {
    return totalProfit;
  }

  /**
   * Set the total profit of a store
   * @param totalProfit the total profit
   */
  void setTotalProfit(double totalProfit) {
    this.totalProfit = totalProfit;
  }

  /**
   * Return the next id number.
   * @return the next id
   */
  int getNextID() {
    return nextID;

  }

  /**
   * add upc by one.
   */
  void UPCadd() {
    nextUPC ++;
  }

  /**
   * add id by one.
   */
  void IDadd() {
    nextID++;
  }

  /**
   * Return the product which is the next UPC on the list.
   *
   * @return the product of the next UPC
   */
  int getNextUPC() {
    return nextUPC;
  }

  /**
   * Return the total revenue.
   *
   * @return the total amount of revenue
   */
  double getRevenue() {
    return totalRevenue;
  }

  /**
   * Return the name of a product.
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Add a certain extra amount of profit to the current profit.
   *
   * @param profit the extra amount of profit needed to be add
   */
  void addProfit(double profit) {
    totalProfit += profit;
  }


  /**
   * Return the profit.
   *
   * @return the total amount of profit.
   */
  double getProfit() {
    return totalProfit;
  }


  /**
   * Add revenue to the current totalRevenue.
   *
   * @param revenue The amount of revenue needed to be add
   */
  void addRevenue(double revenue) {
    totalRevenue += revenue;
  }

  void setStoreRecord(StoreRecord sr) {
    this.totalProfit = sr.getTotalProfit();
    this.products = sr.getProducts();
    this.root = sr.getRoot();
    this.payList = sr.getPayList();
    this.nextID = sr.getNextID();
    this.Users = sr.getUsers();
    this.nextUPC = sr.getNextUPC();
    this.totalRevenue = sr.getTotalRevenue();
  }

  ArrayList<Product> productsNeedReOrder() {
    ArrayList<Product> allNeedReorder = new ArrayList<>();
    for (Product product: products.getProducts()) {
      if (product.getOrder().isNeedReOrder()) {
        allNeedReorder.add(product);
      }
    }
    return allNeedReorder;
  }

  @Override
  public String toString() {
    return name;
  }
}
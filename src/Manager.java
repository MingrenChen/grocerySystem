import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Created by Harru on 2017/7/21.
 * A class contains method that a manager needs.
 */
public class Manager extends User implements Serializable{

    private static final long serialVersionUID = 1770057982;
    /**
     * Mananger login
     */
    Manager(String name, String pass,ManageUser user){
        super(name,pass,"Manager", user);
    }

    /**
     * This method helps to see the products in pending and pending
     * information.
     * @param upc the universal product code
     */
    int viewPending(String upc) {
        return getProduct(upc).getOrder().getPendingNumber();
    }

    /**
     * This method helps to see the profit.
     */
    double viewProfit() {
        return getStore().getProfit();
    }

    /**
     * This method helps to see all the order this is in pending.
     */
    void viewAllPendingOrder() {
        System.out.println("All pending order(s):\n "
                + getStore().getProducts().getPendingList());;
    }

    /**
     * This method helps to check the revenue.
     */
    double viewRevenue() {
        return getStore().getRevenue();
    }

    /**
     * Set the price for a product.
     * @param upc the universal product code
     * @param price the price
     */
    void setPrice(String upc, double price) {
        getProduct(upc).setPrice(price);
        System.out.println("You set up new price ( " + price + " ) for "
                + getProduct(upc).getName());
    }

    /**
     * Set a new discount to a product;
     * @param upc the universal product code
     * @param d discount
     * @param s start date
     * @param e end date
     */
    void setNewDiscount(String upc, double d, LocalDate s, LocalDate e) {
        getProduct(upc).addDiscount(d,s,e);
        System.out.println("You set up new discount for "
                + getProduct(upc).getName());

    }

    /**
     * Get the product that needs to be managed.
     * @param upc the universal product code
     * @param id the id
     * @throws OperationFailedException it throws exception if the upc doesn't exist
     */
    void returnProduct(String upc, int id) throws OperationFailedException {
        getStore().getPayList().returnProduct(id,upc,1);
        getStore().getPayList().refund(id,upc,1);
    }

    /**
     * Return the a  string of the manager
     * @return a string
     */
    public String toString(){
        return "Manager: " + userName;
    }

    /**
     * Add section in a store.
     * @param sectionName the name of the section
     * @param parentName the name of the parent
     * @throws OperationFailedException it throws exception if parent name doesn't exist
     */
    void addSection(String sectionName, String parentName) throws OperationFailedException {
        userManagement.getStore().getRoot().addSubGivenParent(sectionName, parentName);
    }
}

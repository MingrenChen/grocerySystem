import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;

/**
 * Created by chenmi84 on 18/07/17.
 * A class have methods for receivers.
 */
public class Receiver extends User implements Serializable {


    private static final long serialVersionUID = 1445057982;
    /**
     * Enter a password for receivers to log in.
     */
    Receiver(String name, String pass,ManageUser user) {
        super(name,pass,"Receiver",user);
    }

    /**
     * return the number of products in inventory.
     */
    int getProNum() {
                return getStore().getProducts().getNum();
    }

    /**
     * add new product to the inventory.
     * @param UPC String
     */
    void addNew(String UPC){
        Product product = getProduct(UPC);
        product.addNew();
    }
    /**
     * View the cost of a product by UPC.
     * @param UPC String
     * @throws OperationFailedException when the upc does not exist, throw exception
     */
    void viewCost(String UPC) throws OperationFailedException {
        Product product = getProduct(UPC);
        System.out.println("cost of " + product +
                " is " + product.getCost());
    }
    /**
     * View the price History of a product by UPC.
     * @param UPC String
     */
    String viewPriceHistory(String UPC) {
        String result = "";
        HashMap<LocalDate, Double> map =  getProduct(UPC).getPriceHistory();
        for (LocalDate date: map.keySet()) {
            result += date + " :" + map.get(date);
        }
        return result;
    }
    /**
     * View the price of a product by UPC.
     * @param UPC String
     * @throws OperationFailedException when the upc does not exist, throw exception
     */
    void viewPrice(String UPC) throws OperationFailedException {
        Product product = getProduct(UPC);
        System.out.println(product + "'s price is" + product.getPrice());
    }


    /**
     * Add a new product to inventory.
     * @param name String
     * @param CostPrice Double[]
     * @param section mSection
     * @param aisle int
     * @param threshold int
     * @param distributor String
     */
    void addProduct(String name, Double CostPrice[], mSection section,
                    int aisle, int threshold, String distributor, ManageProducts mProduct) {
        Location location = new Location(section, aisle);
        Order order = new Order(threshold, distributor);
        new Product(name,CostPrice, location, order,mProduct);
    }

    /**
     * request reorder for a single product.
     * @param upc String
     * @throws OperationFailedException e
     */
    void reOrder(String upc) throws OperationFailedException {
        Product product = getProduct(upc);
        product.getOrder().requestReorder();
    }

    /**
     * Return a string of a receiver
     * @return a string
     */
    public String toString(){
        return "Receiver: " + userName;
    }
}

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by chenmi84 on 04/08/17.
 * This is the class store all products information in store.
 */
public class ManageProducts implements Serializable {

    private static final long serialVersionUID = 1000057999;
    /**
     * This means create a new arraylist of products.
     */
    ArrayList<Product> products = new ArrayList<Product>();
    /**
     * This store record of this store.
     */
    StoreRecord store;

    /**
     * Manage the products in the store.
     * @param Thisstore the store record
     */
    ManageProducts(StoreRecord Thisstore) {
        store = Thisstore;
    }

    /**
     * return all the products.
     * @return products
     */
    public ArrayList<Product> getProducts() {
        return products;
    }

    /**
     * Return the store.
     * @return store
     */
    public StoreRecord getStore() {
        return store;
    }

    /**
     * This method is used to get product by UPC.
     *
     * @param UPC UPC for product
     * @return found product by UPC
     */
    public Product getProduct(String UPC) {
        for (Product product : products) {
            if (product.getUpc().equals(UPC)) {
                return product;
            }
        }
        return null;
    }

    /**
     * Return an arraylist of the name of all products on a specific section in the store.
     *
     * @param sec the section
     * @return ArrayList
     */
    public ArrayList getProductBySection(mSection sec) {
        ArrayList<Product> result = new ArrayList<>();
        for (Product product : products) {
            if (product.getLocation().getSection().equals(sec)) {
                result.add(product);
            }
        }
        return result;
    }

    /**
     * Return an array list of the name of all the
     * products on a certain aisle. The result is an
     * array list of all the product on an aisle.
     *
     * @param a the number label on an aisle
     * @return the products on an specific aisle
     */
    ArrayList<Product> getProductByAisle(int a) {
        ArrayList<Product> result = new ArrayList<>();
        for (Product product : products) {
            if (product.getLocation().getAisle() == a) {
                result.add(product);
            }
        }
        return result;
    }

    /**
     * Switch the location of two products on the aisle, a and b are
     * the amount of two products that need to be switched.
     * This method can help us to change the location of the products.
     *
     * @param a the amount of product A
     * @param b the amount of product B
     */
    void changeAisle(int a, int b) {
        ArrayList products1 = getProductByAisle(a);
        ArrayList products2 = getProductByAisle(b);
        for (Object pa : products1) {
            ((Product) pa).getLocation().setAisle(b);
        }
        for (Object pb : products2) {
            ((Product) pb).getLocation().setAisle(a);
        }
    }

    /**
     * Return the string of all products and numbers that been request but not arrive.
     *
     * @return String
     */
    String getPendingList() {
        String result = "";
        for (Product product : products) {
            if (!(product.getOrder().getPendingNumber() == 0)) {
                result += product.getName() + " : " + product.getOrder().getPendingNumber() + "\n";
            }
        }
        return result;
    }

    /**
     * Return the number of the products on the productlist.
     * @return size of the productlist
     */
    int getNum() {
        return products.size();
    }

    /**
     * Add a product to the ProductList of the store.
     *
     * @param p Product the product
     */
    void addProduct(Product p) {
        products.add(p);
    }

    /**
     * Return all sale products in the store.
     * @return all sale products
     */
    ArrayList<Product> getAllOnSale() {
        ArrayList<Product> allOnSale= new ArrayList<>();
        for (Product product : products) {
            if (product.getDiscounts() != null) {
                allOnSale.add(product);
            }
        }
        return allOnSale;
    }

    /**
     * Return the orders that needs to be reordered.
     * @return reorderlist
     */
    ArrayList<Product> getNeedReorder() {
        ArrayList<Product> reorderList = new ArrayList<>();
        for (Product product : products) {
            if (product.getOrder().isNeedReOrder()) {
                reorderList.add(product);
            }
        }
        return reorderList;
    }
}

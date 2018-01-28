import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by chenmi84 on 04/08/17.
 * This class manage all the paylists in a store.
 */
public class ManagePayList implements Serializable {
    /**
     * This is to create a new paylist.
     */
    ArrayList<PayList> payLists = new ArrayList<PayList>();

    private static final long serialVersionUID = 1000045982;
    /**
     * The storerecord of the store
     */
    StoreRecord Store;

    /**
     * Manage the paylist in this store
     * @param thisStore the store record in the store
     */
    ManagePayList(StoreRecord thisStore) {
        Store = thisStore;
    }

    /**
     * This field means all paylists in the store.
     */
    private ArrayList<PayList> allPayList = new ArrayList<PayList>();

    /**
     * Return a store in the Storerecord.
     * @return a store
     */
    public StoreRecord getStore() {
        return Store;
    }

    /**
     * Return the paylist within all the paylists.
     * @param id the id of the paylist
     * @return the paulist
     */
    PayList getPaylist(int id) {
        for(PayList pl: allPayList){
            if (pl.getId() == id) {
                return pl;
            }
        }
        return null;
    }

    /**
     * Add a paylist to all paylist lists.
     * @param pl the paylist
     */
    void addPayList(PayList pl){allPayList.add(pl);
    }

    /**
     * Get a product in a paylist.
     * @param payListID the paylist id
     * @param upc the universal product code
     * @param num number of product
     * @throws OperationFailedException we throw exception if the product doesn't exist.
     */
    void returnProduct(int payListID, String upc, int num) throws OperationFailedException {
        PayList p = getPaylist(payListID);
        HashMap<Product,Integer> plist = p.getPayList();
        Product rePro = Store.getProducts().getProduct(upc);
        if (plist.keySet().contains(rePro)){
            Integer itemNum = plist.get(rePro);
            if (!(itemNum < num)){
                itemNum -= num;
                plist.put(rePro,itemNum);
                p.setPayList(plist);
            }else {
                throw new OperationFailedException("No more product can be returned");
            }
        } else {
            throw new OperationFailedException("Receipt does not contain this product");
        }
    }

    /**
     * Get the refund of a paylist.
     * @param payListID the paylist id
     * @param upc the universal product code
     * @param num number of product
     * @throws OperationFailedException it throws exception if the paylist or the upc doesn't exist.
     */
    void refund(int payListID, String upc, int num) throws OperationFailedException {
        Store.getProducts().getProduct(upc).addNewbyNum(num);
        PayList paylist = getPaylist(payListID);
        Product pro = Store.getProducts().getProduct(upc);
        LocalDate date = paylist.getDate();
        double price = pro.getPriceByDate(date);
        Double currentProfit = Store.getTotalProfit();
        currentProfit-= num * (price - pro.getCost());
        Store.setTotalProfit(currentProfit);
        Double currentRevenue = Store.getTotalRevenue();
        currentRevenue -= num * price;
        Store.setTotalRevenue(currentRevenue);
    }
}

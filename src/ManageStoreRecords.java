import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by chenmi84 on 04/08/17.
 * This class is used to manage all the store records.
 */
public class ManageStoreRecords implements Serializable {

    private static final long serialVersionUID = 1000963982;
    /**
     * This means create a managestorerecords to store all records.
     */
    private static ManageStoreRecords StoreAll = new ManageStoreRecords();
    /**
     * This means create a new store record.
     */
    private ArrayList<StoreRecord> storeRecords = new ArrayList<>();

    /**
     * Return the whole store
     * @return the store
     */
    static ManageStoreRecords getStoreAll() {
        return StoreAll;
    }

    /**
     * Return a arraylist of all store records.
     * @return all store records
     */
    ArrayList<StoreRecord> getStoreRecords() {
        return storeRecords;
    }

    /**
     * This field is to manage all store records.
     */
    private ManageStoreRecords() {

    }

    /**
     * Add a store record to a store.
     * @param storeRecord the store record
     */
    void addStoreRecord(StoreRecord storeRecord){
        if (storeRecords.contains(storeRecord)){
            storeRecords.set(storeRecords.indexOf(storeRecord),storeRecord);
        }else {
            storeRecords.add(storeRecord);
        }
    }

    /**
     * Set the store record.
     * @param records the record
     */
    void setStoreRecords(ArrayList<StoreRecord> records) {
        storeRecords = records;
    }

    /**
     * Save store record information.
     * It catches exception if fail to run the try block.
     */
    static void save() {
        String dir = "inventory.out";
        ObjectOutputStream inventory = null;
        try {
            inventory = new ObjectOutputStream(new FileOutputStream(dir));
            inventory.writeObject(ManageStoreRecords.getStoreAll());
            inventory.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Read a store record.
     * It catches exception if try block fails.
     */
    static void read() {
        String dir = "inventory.out";
        ObjectInputStream inventory = null;
        try {
            inventory = new ObjectInputStream(new FileInputStream(dir));
            StoreAll = (ManageStoreRecords) (inventory.readObject());
            inventory.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            try {
                ObjectOutputStream inventoryout = new ObjectOutputStream(new FileOutputStream(dir));
                inventoryout.writeObject(ManageStoreRecords.getStoreAll());
                inventoryout.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }

    }
}

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangj397 on 31/07/17.
 * This class record the subsection of all products.
 */
public class mSection implements Serializable{

    private static final long serialVersionUID = 1000098742;
    /**
     * This indicates a new arraylist of subsections.
     */
    private ArrayList<mSection> subsection = new ArrayList<mSection>();
    /**
     * This is the name of a product
     */
    private String name;
    /**
     * This is the store record.
     */
    private StoreRecord storeRecord;

    /**
     * This field record the subsection of a product.
     * @param record the store record
     * @param sName the name of subsection
     */
    mSection(StoreRecord record, String sName){
        storeRecord = record;
        name = sName;
    }

    /**
     * Return the subsection of a product.
     * @return the subsection
     */
    ArrayList<mSection> getSubsection() {
        return subsection;
    }

    /**
     * Add section to the store as default.
     */
    void addSeven() {
        String[] produces = {"dairy", "meat", "packaged products", "frozen food",
                "cleaning supplies", "pet food", "kitchen wares", "seasonal products"};
        for (String produce : produces) {
            addSubsection(produce);
        }
    }

    /**
     * Get the name for a section.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Add parent for a subsection.
     * @param sectionName the section name
     * @param parentName the parent name
     * @throws OperationFailedException it throws exception if the parent name don't exist.
     */
    void addSubGivenParent(String sectionName, String parentName) throws OperationFailedException {
        mSection parent = storeRecord.getRoot().find(parentName);
        if (parent != null){
            parent.addSubsection(sectionName);
        } else {
            throw new OperationFailedException("No such parent product.");
        }

    }

    /**
     * Add subsection to a product.
     * @param newName the name of subsection
     */
    private void addSubsection(String newName){
        mSection newSection = new mSection(storeRecord, newName);
        subsection.add(newSection);
    }

    /**
     * Return true or false if an object matches the section.
     * @param obj the subsection
     * @return if a product equals the subsection
     */
    boolean equals(mSection obj) {
        return name.equals(obj.getName());
    }

    /**
     * Return the section of a product.
     * @param findName the name of the product
     * @return the matching subsection
     */
    mSection find(String findName) {
        mSection section = null;
        if (name.equals(findName)){
            return this;
        }
        for (mSection section1 : subsection) {
            section = section1.find(findName);
            if (section!= null) {
                return section;
            }
        }return null;
    }

    /**
     * Return a string of a product about the subsection.
     * @return a string
     */
    @Override
    public String toString() {
        return name;
    }


}
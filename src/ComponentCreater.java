import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by chenmi84 on 09/08/17.
 * Create component in Jframe.
 */
public class ComponentCreater {
    /**
     * Create an instance of a new component.
     */
    private static ComponentCreater ourInstance = new ComponentCreater();

    /**
     * Getter for singleton class.
     * @return a singleton class
     */
    public static ComponentCreater getInstance() {
        return ourInstance;
    }

    /**
     * Constructor of componentcreater.
     */
    private ComponentCreater() {
    }

    /**
     * Return the tree of a section.
     * @param secRoot root of the section
     * @return the tree
     */
    public DefaultMutableTreeNode getTreeModel(mSection secRoot) {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Store");
        addSub(root, secRoot);
        return root;
    }

    /**
     * Return a table of a type that user ask for.
     * @param type the type
     * @param product the product
     * @return a table
     */
    public DefaultTableModel getTableByType(String type, Product product) {
        String name = product.getName();
        String cost =String.valueOf(product.getCost());
        String price =String.valueOf(product.getPrice());
        String section = product.getLocation().getSection().toString();
        String loc = "aisle " + String.valueOf(product.getLocation().getAisle());
        String thr = String.valueOf(product.getOrder().getThreshold());
        String dis = product.getOrder().getDistributor();
        String itemNum = String.valueOf(product.getItemNum());
        String store = product.getManageProducts().getStore().getName();
        HashMap<LocalDate, Double> priceHistory = product.getPriceHistory();
        ArrayList<LocalDate> orderHistory = product.getOrder().getOrderHistory();
        if (type.equalsIgnoreCase("all")) {
            String[] col = new String[] {"upc", "name","cost","price","section",
                    "location","threshold","distributor","item number","domain"};
            String[][] row = new String[][] {{product.getUpc(),name,cost,price,section,loc,thr,dis,itemNum,store}};
            DefaultTableModel table = new DefaultTableModel(row,col);
            return table;
        }else if (type.equalsIgnoreCase("view price")||type.equalsIgnoreCase("view cost")){
            String[] col = new String[] {"upc", "name","cost","price"};
            String[][] row = new String[][] {{product.getUpc(),name,cost,price}};
            DefaultTableModel table = new DefaultTableModel(row,col);
            return table;
        }else if (type.equalsIgnoreCase("view section")){
            String[] col = new String[] {"upc", "name","section"};
            String[][] row = new String[][] {{product.getUpc(),name,section}};
            DefaultTableModel table = new DefaultTableModel(row,col);
            return table;
        }else if (type.equalsIgnoreCase("view location")){
            String[] col = new String[] {"upc", "name", "location","store"};
            String[][] row = new String[][] {{product.getUpc(),name,loc, store}};
            DefaultTableModel table = new DefaultTableModel(row,col);
            return table;
        }else if (type.equalsIgnoreCase("view price history")){
            String[] col = new String[] {"upc", "name","price history"};
            Object[][] row = new Object[][] {{product.getUpc(),name,priceHistory}};
            DefaultTableModel table = new DefaultTableModel(row,col);
            return table;
        }else if (type.equalsIgnoreCase("view order history")){
            String[] col = new String[] {"upc", "name","order history"};
            Object[][] row = new Object[][] {{product.getUpc(),name,orderHistory}};
            DefaultTableModel table = new DefaultTableModel(row,col);
            return table;
        }else if (type.equalsIgnoreCase("view item number")){
            String[] col = new String[] {"upc", "name","item number"};
            String[][] row = new String[][] {{product.getUpc(),name,itemNum}};
            DefaultTableModel table = new DefaultTableModel(row,col);
            return table;
        }
        return null;
    }

    /**
     * Return the table of all the products
     * @param allProduct all products
     * @return a table
     */
    public DefaultTableModel getTableOfAll(ArrayList<Product> allProduct){
        int pSize = allProduct.size();
        String[][] row = new String[pSize][10];
        int i = 0;
        for (Product product : allProduct) {
            String name = product.getName();
            String cost = String.valueOf(product.getCost());
            String price = String.valueOf(product.getPrice());
            String section = product.getLocation().getSection().toString();
            String loc = "aisle " + String.valueOf(product.getLocation().getAisle());
            String thr = String.valueOf(product.getOrder().getThreshold());
            String dis = product.getOrder().getDistributor();
            String itemNum = String.valueOf(product.getItemNum());
            String store = product.getManageProducts().getStore().getName();

            String[] oneProduct = new String[]{product.getUpc(), name, cost, price, section, loc, thr, dis, itemNum, store};
            for (int j = 0; j < 10; j++) {
                row[i][j] = oneProduct[j];
            }
            i++;
        }

        String[] col = new String[] {"upc", "name","cost","price","section",
                "location","threshold","distributor","item number","domain"};
        DefaultTableModel table = new DefaultTableModel(row,col);
        return table;

    }


    /**
     * Add a subsection to a tree.
     * @param node the node
     * @param section the section
     */
    private void addSub(DefaultMutableTreeNode node, mSection section) {
        for (mSection child : section.getSubsection()) {
            if (child != null) {
                DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(child.getName());
                node.add(newNode);
                addSub(newNode, child);
            }
        }
    }
}

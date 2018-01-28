import java.io.BufferedWriter;
import java.io.FileWriter;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Created by chenmi84 on 08/08/17.
 * This class handle all the reporting.
 */
public class WriteFactory {
    /**
     * This indicates a new report being created.
     */
    private static WriteFactory ourInstance = new WriteFactory();

    static WriteFactory getInstance() {
        return ourInstance;
    }

    /**
     * Write a report.
     */
    private WriteFactory() {
    }

    /**
     * This record the report on sale products
     * @param store the storerecord
     */
    static void writeOnSale(StoreRecord store) {
        BufferedWriter currentOnSale = null;
        try {
            currentOnSale = new BufferedWriter(new FileWriter("CurrentOnSale.txt"));
            currentOnSale.write(LocalDate.now().toString()+"\n");
            ArrayList<Product> onsale = store.getProducts().getAllOnSale();
            for (Product product: onsale) {
                currentOnSale.write(product.toString() + " : " + product.getDiscounts().toString() + "\n");
            }
            currentOnSale.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This report contains all the log in message.
     * @param message the message
     */
    static void writeLog(String message) {
        BufferedWriter record = null;
        try {
            record = new BufferedWriter(new FileWriter("Log.txt",true));
            record.append(message);
            record.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * write every products which already send reorder request
     * and in pending now.
     * @param products asdf
     */
    static void writePending(ArrayList<Product> products) {
        BufferedWriter PendingWriter;
        try {
            PendingWriter = new BufferedWriter(new FileWriter("Pending.txt"));
            PendingWriter.write(LocalDate.now().toString());
            for (Product product: products) {
                PendingWriter.write(product+ ": " + product.getOrder().getThreshold() * 3);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * write all the products in the store.
     * @param products asd
     */
    static void writeAll(ArrayList<Product> products) {
        BufferedWriter PendingWriter;
        try {
            PendingWriter = new BufferedWriter(new FileWriter("Products.txt"));
            PendingWriter.write(LocalDate.now().toString());
            for (Product product: products) {
                PendingWriter.write(product.getUpc() + "-" + product.getName()+ ": " + product.getItemNum());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

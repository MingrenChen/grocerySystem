import javax.swing.*;
import java.io.*;
import java.util.Scanner;

public class shopSimulation extends JFrame {

  /**
   * This main method is used to simulate shopping process.
   * @param args argments
   * @throws OperationFailedException exception
   * @throws IOException exception
   * @throws ClassNotFoundException exception
   */
  public static void main(String[] args) throws
          OperationFailedException, IOException, ClassNotFoundException {
//    StoreRecord a = new StoreRecord("711");
    ManageStoreRecords.read();
    Start store = new Start();
    store.setVisible(true);


  }

}
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Harru on 2017/7/10.
 * This class is created for throwing exception in other classes.
 */
public class OperationFailedException extends Exception {

  String msg = "";

  /**
   * An exception that we can throw when something went wrong with a message in a method.
   *
   * @param FailedType the type of failure
   */
  OperationFailedException(String FailedType) {
    System.out.println(FailedType);
    msg = FailedType;
  }

  /**
   * An exception that we can throw when something went wrong with the user in a method.
   *
   * @param user the user
   */
  OperationFailedException(User user) throws IOException {
    System.out.println("Authority Failed.\n");
    if (user instanceof Receiver) {
      BufferedWriter record = new BufferedWriter(new FileWriter("Log.txt", true));
      record.append("\nReceiver used wrong command: Authority Failed.");
      record.close();
      System.out.println("You login as a receiver. " +
          "Please choose from the following command: " +
          "\n- Scan in product \n- Add product \n- View Location" +
          " \n- Prize history \n- View price \n- View cost \n- View num" +
          "\n- Reorder\n");
    } else if (user instanceof Manager) {
      BufferedWriter record = new BufferedWriter(new FileWriter("Log.txt", true));
      record.append("\nManager used wrong command: Authority Failed.");
      record.close();
      System.out.println("You login as a manager. " +
          "Please choose from the following command: \n- View pending"
          + "\n- Pending orders \n- View revenue \n- View profit"
          + "\n- Set discount \n- Set price\n");
    } else if (user instanceof Cashier) {
      BufferedWriter record = new BufferedWriter(new FileWriter("Log.txt", true));
      record.append("\nCashier used wrong command: Authority Failed.");
      record.close();
      System.out.println("You login as  Cashier. " +
          "Please choose from the following command: " +
          "\n- Scan \n- Purchase \n- Cancel " +
          "item \n- Cancel \n- View discount\n");
    } else if (user instanceof Reshelver) {
      BufferedWriter record = new BufferedWriter(new FileWriter("Log.txt", true));
      record.append("\nReshelver used wrong command: Authority Failed.");
      record.close();
      System.out.println("You login as a reshelver. " +
          "Please choose from the following command: " +
          "\n- View location \n- Change aisles" +
          "\n- View section \n- Order history \n- Item number\n");
    }
  }

  @Override
  public String getMessage() {
    return msg;
  }
}


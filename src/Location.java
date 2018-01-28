import java.io.Serializable;

/**
 * Created by Penny on 2017-08-03.
 * Location class contains the location information about a product.
 */
public class Location implements Serializable{
  /**
   * This indicates the section a product belongs to.
   */
  private mSection section;
  /**
   * This field indicates the subsection a product belongs to.
   */
  private int aisle;

  private static final long serialVersionUID = 1000004482;

  /**
   * The location of a product.
   * @param pSection the section
   * @param aisleNum the aisle number
   */
  Location(mSection pSection,int aisleNum){
    section = pSection;
    aisle = aisleNum;
  }
  /**
   * Return section of an item.
   * @return return the section of an item
   */
  public mSection getSection() {
    return section;
  }

  /**
   *  Return aisle a item is being placed.
   * @return return the aisle of an item
   */
  int getAisle() {
    return aisle;
  }

  /**
   * Set the aisle of an item.
   * @param aisle the aisle number
   */
  void setAisle(int aisle) {
    this.aisle = aisle;
  }

  /**
   * Set the section of an item.
   * @param section the section of a product
   */
  public void setSection(mSection section) {
    this.section = section;
  }
}

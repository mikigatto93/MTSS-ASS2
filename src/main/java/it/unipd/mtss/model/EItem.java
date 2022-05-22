////////////////////////////////////////////////////////////////////
// [Ruth Genevieve] [Bousapnamene] [1192088]
// [Michele] [Gatto] [1224458]
////////////////////////////////////////////////////////////////////

package it.unipd.mtss.model;

public class EItem {
    private EItemType itemType;
    private String name;
    private double price;

    public EItem(EItemType itemType, String name, double price) 
        throws IllegalArgumentException
    {
        if (price > 0) {
            this.itemType = itemType;
            this.name = name;
            this.price = price;
        } else {
            throw new IllegalArgumentException("The price cannot be negative");
        }
    }

    public EItemType getItemType() {
        return this.itemType;
    }

    public String getName() {
        return this.name;
    }

    public double getPrice() {
        return this.price;
    }
}

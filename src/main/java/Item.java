public class Item {
    private String itemCode;
    private String itemName;
    private int stock;
    private String pictureName;
    private float price;

    public Item(String itemCode, String itemName, int stock, String pictureName, float price) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.stock = stock;
        this.pictureName = pictureName;
        this.price = price;
    }

    public Item(String itemName, int stock, String pictureName, float price) {
        this.itemCode = null;
        this.itemName = itemName;
        this.stock = stock;
        this.pictureName = pictureName;
        this.price = price;
    }

    public Item(String[] valueArr)
    {
        this.itemCode = valueArr[0];
        this.itemName = valueArr[1];
        this.stock = Integer.parseInt(valueArr[2]);
        this.pictureName = valueArr[3];
        this.price = Float.parseFloat(valueArr[4]);
    }

    // Getter and Setter

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPicturePath(String picturePath) {
        this.pictureName = picturePath;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

}


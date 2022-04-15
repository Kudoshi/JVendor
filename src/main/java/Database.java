import javax.xml.crypto.Data;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
public class Database {

    //Create
    //Get (read)
    //Update
    //Delete

//    public static void

    /** Creates a record with the following item details
     *  It checks for duplicated item code
     *
     * @param item Item object that contains the information of the item filled in
     * @return boolean Returns true or false indicating the success of creating a new item in the database
     */
    public static boolean ItemCreate(Item item){
        //Check for duplicated item
        Item existingItem = ItemGetByCode(item.getItemCode());

        if (existingItem != null)
        {
            return false;
        }
        //Append to file
        try
        {
            FileWriter writer = new FileWriter("./src/main/resources/item.txt", true);
            BufferedWriter buffer = new BufferedWriter(writer);
            String textData = item.getItemCode()+";"+item.getItemName()+";"+item.getStock()+";"+item.getPictureName()+";"+
                    item.getCategory()+";"+item.getPrice()+"\n";

            buffer.write(textData);
            buffer.close();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Returns a list of items
     * @return ArrayList<Item> list of items. Returns null if no item is found
     */
    public static ArrayList<Item> ItemGetAll(){
        ArrayList<Item> itemList = new ArrayList<Item>();

        File file = new File("./src/main/resources/item.txt");
        try {

            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] itemData = line.strip().split(";");
                Item item = new Item(itemData[0], itemData[1], Integer.parseInt(itemData[2]), itemData[3],
                        ItemCategory.valueOf(itemData[4]), Float.parseFloat(itemData[5]));
                itemList.add(item);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (itemList.size() == 0)
        {
            itemList = null;
        }

        return itemList;
    }

    /**
     * Get an item by the code
     * @param itemCode Code of the item
     * @return Returns the Item found or null if no item is found
     */
    public static Item ItemGetByCode(String itemCode){
        ArrayList<Item> itemList = null;

        itemList = ItemGetAll();

        if (itemList == null)
        {
            return null;
        }

        Item item = null;

        for (Item i : itemList)
        {
            if (i.getItemCode().equals(itemCode))
            {
                item = i;
                break;
            }
        }
        if (item == null)
        {
            return null;
        }

        return item;
    }

    /**
     * Updates the item details by overwriting the details in the database
     * @param updateItem The item with the new details along with the appropriate itemCode
     * @return boolean Boolean indicating the success of updating the item with new data
     */
    public static boolean ItemUpdate(Item updateItem)
    {
        //Check if in database
        Item oriItem = ItemGetByCode(updateItem.getItemCode());
        if (oriItem == null)
        {
            return false;
        }

        // Get item lsit
        ArrayList<Item> itemList = ItemGetAll();
        if (itemList == null)
        {
            return false;
        }

        //Update item list
        boolean isUpdated = false;
        for (int i = 0; i < itemList.size(); i++) {
            if (itemList.get(i).getItemCode().equals(updateItem.getItemCode()))
            {
                itemList.set(i, updateItem);
                isUpdated = true;
                break;
            }
        }

        if (isUpdated == false)
        {
            return false;
        }

        //Write to file
        try
        {
            FileWriter writer = new FileWriter("./src/main/resources/item.txt");
            BufferedWriter buffer = new BufferedWriter(writer);
            for (Item i : itemList)
            {
                String textData = i.getItemCode()+";"+i.getItemName()+";"+i.getStock()+";"+i.getPictureName()+";"+
                        i.getCategory()+";"+i.getPrice()+"\n";
                buffer.write(textData);
            }

            buffer.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        Item item = new Item("BF245", "Gugu420" , 420, "stock.png",
                ItemCategory.MAIN, 24.45f);

        boolean isUpdate = ItemUpdate(item);
        System.out.println(isUpdate);
//        ItemCreate(item);
//        Item item = ItemGetByCode("BF240");
    }

}


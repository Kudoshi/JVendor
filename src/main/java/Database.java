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
     * @param item item object that contains all the details
     */
    public static DbProcessStatus ItemCreate(Item item)
    {
        //Check for duplicated item
        Item existingItem = ItemGetByCode(item.getItemCode());

        if (existingItem != null)
        {
            return DbProcessStatus.RaiseError("Item already exist");
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
            return DbProcessStatus.RaiseError("Unable to create new item in database");
        }
        return DbProcessStatus.RaiseSuccess("Item successfully created", item);
    }

    /**
     * Returns a list of items
     * @return DbProcessStatus containing ArrayList<Item> data
     */
    public static DbProcessStatus ItemGetAll()
    {
        ArrayList<Item> itemList = new ArrayList<Item>();

        try {
            File file = new File("./src/main/resources/item.txt");
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
            return DbProcessStatus.RaiseError("Error retrieving item list");
        }

        return DbProcessStatus.RaiseSuccess("Successfully returning all items", itemList);
    }

    /**
     * Get an item by the coe
     * @param itemCode Code of the item
     * @return Returns the Item found or null if no item is found
     */
    public static DbProcessStatus ItemGetByCode(String itemCode)
    {
        ArrayList<Item> itemList = (ArrayList<Item>) ItemGetAll().getData();
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
            return DbProcessStatus.RaiseError("Item not found");
        }

        return DbProcessStatus.RaiseSuccess("Item found", item);
    }

    /**
     * Updates the item details by overwriting the details in the database
     * @param item The item with the new details along with the appropriate itemCode
     * @return DbProcessStatus: Status only. No data
     */
    public static DbProcessStatus ItemUpdate(Item item)
    {
        String itemCode = item.getItemCode();

        //Find if item is in database

        DbProcessStatus returnStatus = ItemGetByCode(itemCode);

        //If unable to find item
        if (!returnStatus.isStatusSuccess())
        {
            return DbProcessStatus.RaiseError(returnStatus.getStatusMessage());
        }

        //Get item list
        DbProcessStatus getItemListStatus = ItemGetAll();

        if (!return )
        ArrayList<Item> itemList = (ArrayList<Item>) ItemGetAll().getData();
        if


    }

    public static void main(String[] args) {
        Item item = new Item("BF240", "Gugu" , 24, "stock.png",
                ItemCategory.MAIN, 24.45f);
        ItemCreate(item);
    }

}


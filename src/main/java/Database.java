import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class Database {

    /**
     * An enum that stores the type of database file and the file location of the database
     *
     */
    enum FileType
    {
        CART("Database/cart.txt"), ITEM("Database/item.txt"), ITEM_STATISTIC("Database/item_statistic.txt"),
        TRANSACTION("Database/transaction.txt"), STATISTIC("Database/statistic.txt");
        private URL fileLocation;

        FileType(String fileLocation) {
            URL url = Database.class.getResource(fileLocation);
            this.fileLocation = url;
        }

        public URI getFileLocationURI()
        {
            try {
                return this.fileLocation.toURI();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            return null;
        }

    }

    //Create
    //Get (read)
    //Update
    //Delete

//    public static void

    /** Creates a record with the following item details
     *  It checks for duplicated item code
     *  NOTE: The first index array of data will be replaced with a generated id code. Leave the first index blank
     * @param fileType FileType of the data you want to create
     * @param createData Array of strings that contains the information of the data filled in
     * @return boolean Returns true or false indicating the success of creating a new data record in the database
     */
    public static boolean TextFileCreate(FileType fileType, String[] createData){
        //Check for duplicated item
        String[] searchData = TextFileGetByID(fileType, createData[0]);

        if (searchData != null)
        {
            return false;
        }
        createData[0] = GenerateID(fileType);

        //Append to file
        File file = new File(fileType.getFileLocationURI());
        try
        {

            FileWriter writer = new FileWriter(file, true);
            BufferedWriter buffer = new BufferedWriter(writer);

            String textData = String.join(";",createData);
            textData += "\n";

            buffer.write(textData);
            buffer.close();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Returns an array list of string data
     *
     * @param fileType FileType of the data you want to get
     * @return ArrayList<String[]> Contains an array of string array. Returns null if no item is found
     * {[],[]},{[],[]} : A
     */
    public static ArrayList<String[]> TextFileGetAll(FileType fileType){
        ArrayList<String[]> dataList = new ArrayList<String[]>();

        File file = new File(fileType.getFileLocationURI());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] dataRecords = line.strip().split(";");
                dataList.add(dataRecords);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (dataList.size() == 0)
        {
            return null;
        }
        else
        {
            return dataList;
        }

    }
    /**
     * Get a record of data from the text file that corresponds to the idCode (first index of the data record) given
     * TL;DR Search by idCode
     * @param fileType FileType of the data you want to get
     * @param idCode The first id of the record.
     * @return String[] The data record found with the first initial id code or null if no record is found with the id
     */
    public static String[] TextFileGetByID(FileType fileType, String idCode){

        ArrayList<String[]> dataList = TextFileGetAll(fileType);

        if (dataList == null)
        {
            return null;
        }

        String[] dataFind = null;

        for (String[] i : dataList)
        {
            if (i[0].equals(idCode))
            {
                dataFind = i;
                break;
            }
        }

        if (dataFind == null)
        {
            return null;
        }

        return dataFind;
    }
//
    /**
     * Updates the information of the specified data record.
     * The data record's first index (id code) cannot be changed
     *
     * @param fileType The file type wishes to update the item
     * @param updateData The record with the new details along with the appropriate id code (The first index)
     * @return boolean Indicating the success of updating the item with new data
     */
    public static boolean TextFileUpdateData(FileType fileType, String[] updateData)
    {
        //Check if in database
        String[] oriData = TextFileGetByID(fileType, updateData[0]);
        if (oriData == null)
        {
            return false;
        }

        // Get data list
        ArrayList<String[]> dataList = TextFileGetAll(fileType);
        if (dataList == null)
        {
            return false;
        }

        //Update item list
        boolean isUpdated = false;
        for (int i = 0; i < dataList.size(); i++) {
            if (dataList.get(i)[0].equals(updateData[0]))
            {
                //Ensures data to be updated has same amt of attributes as the one in database
                if (updateData.length != dataList.get(i).length ) {
                    isUpdated = false;
                    break;
                }
                dataList.set(i, updateData);
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
            File file = new File(fileType.getFileLocationURI());
            FileWriter writer = new FileWriter(file);
            BufferedWriter buffer = new BufferedWriter(writer);
            String textData = "";
            for (String[] dataRecord : dataList)
            {
                textData += String.join(";", dataRecord);
                textData+="\n";
            }
            buffer.write(textData);
            buffer.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * Updates the information of the specified data record.
     * The data record's first index (id code) cannot be changed
     *
     * @param fileType The file type wishes to update the item
     * @param updateData The record with the new details along with the appropriate id code (The first index)
     * @return boolean Indicating the success of updating the item with new data
     */
    public static boolean TextFileDelete(FileType fileType, String[] updateData)
    {
        //Check if in database
        String[] oriData = TextFileGetByID(fileType, updateData[0]);
        if (oriData == null)
        {
            return false;
        }

        // Get data list
        ArrayList<String[]> dataList = TextFileGetAll(fileType);
        if (dataList == null)
        {
            return false;
        }

        //Update item list
        boolean isUpdated = false;
        for (int i = 0; i < dataList.size(); i++) {
            if (dataList.get(i)[0].equals(updateData[0]))
            {
                //Ensures data to be updated has same amt of attributes as the one in database
                if (updateData.length != dataList.get(i).length ) {
                    isUpdated = false;
                    break;
                }
                dataList.set(i, updateData);
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
            File file = new File(fileType.getFileLocationURI());
            FileWriter writer = new FileWriter(file);
            BufferedWriter buffer = new BufferedWriter(writer);
            String textData = "";
            for (String[] dataRecord : dataList)
            {
                textData += String.join(";", dataRecord);
                textData+="\n";
            }
            buffer.write(textData);
            buffer.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    // Utility

    public static String GenerateID(FileType fileType)
    {
        ArrayList<String[]> dataList = TextFileGetAll(fileType);

        int lastID = 0;
        for(String [] i: dataList)
        {
            lastID = Integer.parseInt(i[0]);
        }

        int newID = lastID + 1;
        return String.format("%05d", newID);
    }
    
    public static void main(String[] args) {
//        String[] item = {"ULT002","DUBAI21","1","scuba.png","MAIN","22.45"};
//        boolean createdData = TextFileCreate(FileType.ITEM);
//
//        System.out.println(createdData);





//        Item item = new Item("BF245", "Gugu420" , 420, "stock.png",
//                ItemCategory.MAIN, 24.45f);
//
//        boolean isUpdate = ItemUpdate(item);
//        System.out.println(isUpdate);
//        ItemCreate(item);
//        Item item = ItemGetByCode("BF240");


    }

}


import javax.swing.*;
import java.io.*;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {

    /**
     * An enum that stores the type of database file and the file location of the database text file
     *
     */
    enum FileType
    {
        ITEM("Database/item.txt", Item.class),
        TRANSACTION("Database/transaction.txt", Transaction.class);
        private URL fileLocation;
        private Class<?> classType;

        FileType(String fileLocation, Class<?> classType) {
            URL url = Database.class.getResource(fileLocation);
            this.fileLocation = url;
            this.classType = classType;
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

        public Class<?> getClassType() {
            return classType;
        }
    }


    /** Creates a record with the following item details
     *  It checks for duplicated item code
     *  NOTE: The first index array of data will be replaced with a generated id code. Leave the first index blank
     *  NOTE: Item's 3rd index array of data should be the full path to source image.
     *  It will be replaced with the proper file name in resource folder later when saved
     * @param fileType FileType of the data you want to create
     * @param createData Array of strings or array of objects that contains the information of the data filled in
     * @return boolean Returns true or false indicating the success of creating a new data record in the database
     */
    public static <T> boolean TextFileCreate(FileType fileType, Object[] createData){
        boolean validateIncomingData = ValidateIncomingData(fileType, createData);

        if (validateIncomingData == false)
        {
            return false;
        }

        createData[0] = GenerateID(fileType);

        //Upload item picture if its file type item
        if (fileType.equals(FileType.ITEM))
        {
            Path originalPath = Paths.get((String)createData[3]);

            String newFileName = createData[0]+"_"+createData[1].toString().replaceAll(" ","") + ".png";
            createData[3] = newFileName;
            String newFilePath = Database.class.getResource("Item/").toString() + newFileName;
            Path targetPath = null;


            try {
                targetPath = Paths.get(new URI(newFilePath));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            try
            {
                Files.copy(originalPath, targetPath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }

        }

        //Append to file
        File file = new File(fileType.getFileLocationURI());
        try
        {

            FileWriter writer = new FileWriter(file, true);
            BufferedWriter buffer = new BufferedWriter(writer);
            String textData = "";
            for (Object i: createData) {
                textData += String.valueOf(i) + ";";
            }
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


    /**
     * Updates the information of the specified data record.
     * The data record's first index (id code) cannot be changed
     *
     * @param fileType The file type wishes to update the item
     * @param updateData The record with the new details along with the appropriate id code (The first index)
     * @return boolean Indicating the success of updating the item with new data
     */
    public static boolean TextFileUpdateData(FileType fileType, Object[] updateData)
    {
        boolean validateIncomingData = ValidateIncomingData(FileType.ITEM, updateData);

        if (validateIncomingData == false)
        {
            return false;
        }

        //Check if in database
        String[] oriData = TextFileGetByID(fileType, (String) updateData[0]);
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

        //Upload item picture if its file type item
        if (fileType.equals(FileType.ITEM))
        {
            // Check if given file name is equals to the original file name
            // If the same no need to overwrite or upload new image
            if (! updateData[3].toString().equals(oriData[3]))
            {
                Path originalPath = Paths.get((String)updateData[3]);

                updateData[3] = oriData[3];
                String originalSrcPath = Database.class.getResource("Item/").toString() + oriData[3];
                Path targetPath = null;


                try {
                    targetPath = Paths.get(new URI(originalSrcPath));
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

                try
                {
                    Files.copy(originalPath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }

            }


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
                ArrayList<String> stringList = new ArrayList<String>();
                for (int j = 0; j < updateData.length; j++)
                {
                    stringList.add(String.valueOf(updateData[j]));
                }
                String[] stringArray = stringList.toArray(new String[0]);
                dataList.set(i, stringArray);
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
     * Delete the data record from the database based on the dataCode given
     *
     * @param fileType The file type wishes to update the item
     * @param dataCode The code of the data to be deleted. The first index of the data record.
     * @return boolean Indicating the success of deleting the item
     */
    public static boolean TextFileDelete(FileType fileType, String dataCode)
    {
        //Check if in database
        String[] oriData = TextFileGetByID(fileType,dataCode);
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

        //Delete Item List
        int indexToBeRemoved = -1;
        for (int i = 0; i < dataList.size(); i++) {
            if (dataList.get(i)[0].equals(dataCode))
            {
                indexToBeRemoved = i;
                break;
            }
        }

        //Delete if is item picture

        if (indexToBeRemoved == -1)
        {
            return false;
        }

        dataList.remove(indexToBeRemoved);

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

    private static String GenerateID(FileType fileType)
    {
        ArrayList<String[]> dataList = TextFileGetAll(fileType);

        int lastID = 0;

        if (dataList != null)
        {
            for(String [] i: dataList)
            {
                lastID = Integer.parseInt(i[0]);
            }
        }

        int newID = lastID + 1;
        return String.format("%05d", newID);
    }

    /**
     * Validates the incoming dataRecord data type with the file type class's variables data type
     *
     * @param fileType The file type of the data
     * @param dataRecord An object array consisting of the data in its proper data type for the appropriate fileType
     * @return The boolean on whether the incoming data is in its appropriate data type with the file type's class variables
     */
    private static boolean ValidateIncomingData(FileType fileType, Object[] dataRecord)
    {
        Field[] fields = fileType.getClassType().getDeclaredFields();

        if (dataRecord.length != fields.length)
        {
            return false;
        }

        //Goes through each and every field in the file type class validating whether the incoming data record matches
        for (int i = 0; i < fields.length; i++) {

            // Check primitive type variable
            if (fields[i].getType().isPrimitive())
            {
                //Try change the incoming data with the file type class' original variable data type
                try {
                    switch (fields[i].getType().toString()) {
                        case "int":
                            int checkInt = Integer.parseInt(String.valueOf(dataRecord[i]));
                            break;
                        case "float":
                            float checkFloat = Float.parseFloat(String.valueOf(dataRecord[i]));

                            break;
                        case "boolean":
                            boolean checkBoolean = Boolean.parseBoolean(String.valueOf(dataRecord[i]));
                            break;
                        case "double":
                            double checkDouble = Double.parseDouble(String.valueOf(dataRecord[i]));
                            break;
                        default:
                            throw new Exception("Non-supported primitive data types detected");
                    }
                } catch (Exception e){
                    return false;
                }
            }
            // Check object type variables
            else {
                if (fields[i].getType().isAssignableFrom(dataRecord[i].getClass()))
                {
                    continue;
                }
                else
                {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Retrieves URI path of the image icon.
     * Image Icon has name that starts with ICON_
     * @param iconName Name of the icon without ICON_
     * @return URI path of the icon. Null if no uri/file is found
     */
    public static URI RetrieveIconPathURI(String iconName)
    {
        URI pathURI = null;
        try {
            pathURI = Database.class.getResource("Icon/"+ iconName+".png").toURI();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        if (pathURI != null)
        {
            return pathURI;
        }
        else
        {
            return null;
        }
    }

    /**
     * Retrieves URI path of the image icon.
     * Image Icon has name that starts with ICON_
     * @param itemFilename Name of the icon without ICON_
     * @return URI path of the icon. Null if no uri/file is found
     */
    public static URI RetrieveItemImgURI(String itemFilename)
    {
        //Item name are in the form of 00025_ItemName


        URI pathURI = null;
        try {

            pathURI = Database.class.getResource("Item/"+ itemFilename).toURI();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        if (pathURI != null)
        {
            return pathURI;
        }
        else
        {
            return null;
        }
    }
/**
     * Retrieves URI path of the GIF
     * @param itemFilename Name of the icon without .GIF
     * @return URI path of the icon. Null if no uri/file is found
     */
    public static URI RetrieveGifURI(String itemFilename)
    {
        //Item name are in the form of 00025_ItemName


        URI pathURI = null;
        try {

            pathURI = Database.class.getResource("Gif/"+ itemFilename+".gif").toURI();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        if (pathURI != null)
        {
            return pathURI;
        }
        else
        {
            return null;
        }
    }


}


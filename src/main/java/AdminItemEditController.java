import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AdminItemEditController  implements IPageController
{
    private JFrame window;
    private AdminItemEditView view;
    private AdminItemEditController controller;
    private App appInstance;

    private String imageLocation = "";
    private Item itemEditing;

    @Override
    public void InitController(App appInstance, JFrame window) {
        this.appInstance = appInstance;
        this.window = window;
        this.controller = this;
        initComponents();
    }
    private void initComponents() {
        //Get data from App
        String itemCode = String.valueOf(appInstance.GetGlobalVar("ItemCode"));

        this.itemEditing = new Item(Database.TextFileGetByID(Database.FileType.ITEM, itemCode));
        this.imageLocation = itemEditing.getPictureName();

        view = new AdminItemEditView(controller, window);
    }

    public void OnBack()
    {
        appInstance.RemoveGlobalVar("ItemCode");
        appInstance.ChangePage(App.AppPage.ADMIN_ITEM_LISTING_PAGE.getPageController());
    }

    public void OnSubmit(String itemName, String itemPrice) {
        itemName = itemName.strip();
        itemPrice = itemPrice.strip();

        //Check for empty

        if (itemName.equals("") || itemPrice.equals("") || imageLocation.equals(""))
        {
            view.TriggerErrorDialogue("Incomplete Form", "Please fill in all the fields");
            return;
        }

        //Validation
        if (!itemName.matches("^[A-Za-z0-9 ]*$"))
        {
            view.TriggerErrorDialogue("Invalid special character", "Item name can only contain alphanumeric characters and spaces");
            return;
        }

        if (Paths.get(imageLocation).getFileName().toString().equals(itemEditing.getPictureName()) &&
                itemEditing.getItemName().equals(itemName) && itemEditing.getPrice() == Float.parseFloat(itemPrice))
        {
            view.TriggerErrorDialogue("Edit Item Fail", "No changes were made");
            return;
        }

        try{

            float itemPriceFloat = Float.parseFloat(itemPrice);
            if (itemPrice.contains("d") || itemPrice.contains("f"))
            {
                throw new NumberFormatException();
            }

            if (Float.isInfinite(itemPriceFloat) || Float.isNaN(itemPriceFloat))
            {
                throw new NumberFormatException();
            }

            String[] checkDecimalPlace = itemPrice.split("\\.");
            if (checkDecimalPlace.length == 2 && checkDecimalPlace[1].length()>2)
            {
                throw new NumberFormatException();
            }

            if (itemPriceFloat <= 0)
            {
                throw new NumberFormatException();

            }
        }catch(NumberFormatException e){
            view.TriggerErrorDialogue("Invalid price", "Item price should be:\n" +
                    "- Numerical\n- More than 0\n- 2 Decimal place");
            return;
        }

//        Upload to database
        float itemPriceFloat = Float.parseFloat(itemPrice);
        String[] itemValues = new String[] {itemEditing.getItemCode(), itemName, String.valueOf(itemEditing.getStock()), imageLocation, String.format("%.2f", itemPriceFloat)  };
        boolean isSuccessCreate = Database.TextFileUpdateData(Database.FileType.ITEM, itemValues);

        if (isSuccessCreate)
        {
            Icon icon = ResizeImageUtility.ResizeImageUtility("GreenTick", new Dimension(50,50), ImageType.ICON);
            JOptionPane.showOptionDialog(null, "Item successfully edited", "Success!",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon, new String[]{"Okay"}, true);
            appInstance.ChangePage(App.AppPage.ADMIN_ITEM_EDIT_PAGE.getPageController());
            return;
        }
        else
        {
            view.TriggerErrorDialogue("Error Creating Item", "Error encountered trying to create item");
            return;
        }

    }

    public Item GetItem()
    {
        return itemEditing;
    }

    public String GetItemPictureName() {
        String fileName = itemEditing.getPictureName();
        return fileName.split("_")[1];
    }

    public void OnSelectImage() {
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

        int returnValue = jfc.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            String fileName = selectedFile.getName();
            String mediaType = null;
            //Validate image
            try {
                mediaType = Files.probeContentType(Paths.get(selectedFile.getAbsolutePath()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (mediaType!= null && mediaType.equals("image/png"))
            {
                imageLocation = selectedFile.getAbsolutePath();
                view.SetUIImageFileName(fileName);
                view.SetUIItemIcon(ResizeImageUtility.ResizeImageUtility(imageLocation, new Dimension(110,110), ImageType.OUTSIDE_IMAGE));
            }
            else
            {
                view.SetUIImageFileName(Paths.get(imageLocation).getFileName().toString());
                view.TriggerErrorDialogue("Error Uploading Image",
                        "Format of image uploaded is not compatible.\n\nImage must be in png format");
            }
        }
    }

    public void OnAddStock() {
        view.SetUIStock();
    }


    public void OnSubmitStock(CustomJPanel container, String amount) {

        amount = amount.strip();

        //Validation
        if (amount.equals("") || amount.equals("0"))
        {
            view.TriggerErrorDialogue("Error Adding Stock", "Please enter amount of stock to add");
            return;
        }

        boolean isInt = amount.matches("\\d+");
        if (Float.isInfinite(Float.parseFloat(amount)) || Float.isNaN(Float.parseFloat(amount)))
        {
            isInt = false;
        }


        if (!isInt)
        {
            view.TriggerErrorDialogue("Error Adding Stock", "Please enter a valid amount of stock to add");
            return;
        }

        //Upload to database
        int newStockAmount = itemEditing.getStock() + Integer.parseInt(amount);
        String[] itemValues = new String[] { itemEditing.getItemCode(), itemEditing.getItemName(), String.valueOf(newStockAmount),
        itemEditing.getPictureName(), String.valueOf(itemEditing.getPrice())};
        boolean isSuccess = Database.TextFileUpdateData(Database.FileType.ITEM, itemValues);

        if (isSuccess)
        {
            Window optionPane = SwingUtilities.getWindowAncestor(container);
            optionPane.setVisible(false);

            Icon icon = ResizeImageUtility.ResizeImageUtility("GreenTick", new Dimension(50,50), ImageType.ICON);
            JOptionPane.showOptionDialog(null, "Item restocked", "Success",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon, new String[]{"Okay"}, true);
            appInstance.ChangePage(App.AppPage.ADMIN_ITEM_EDIT_PAGE.getPageController());
            return;
        }
        else
        {
            view.TriggerErrorDialogue("Error Adding Stock", "Error encountered trying to add stock");
            return;
        }


    }

    public void OnDeleteItem() {
        String deleteWarningMsg = "<html><p style='text-align: center; " +
                "color: red;'> " +
                "WARNING: Item will be permanently deleted<br><br>" +
                "Are you sure you want to delete this item?</p></html>";
        int userDecision = JOptionPane.showOptionDialog(null, deleteWarningMsg, "Delete Item", JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE, null, null, null);

        if (userDecision == JOptionPane.YES_OPTION)
        {
            boolean isSuccess = Database.TextFileDelete(Database.FileType.ITEM, itemEditing.getItemCode());
            if (isSuccess)
            {
                Icon icon = ResizeImageUtility.ResizeImageUtility("GreenTick", new Dimension(50,50), ImageType.ICON);
                JOptionPane.showOptionDialog(null, "Item deleted successfully", "Success",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon, new String[]{"Okay"}, true);
                appInstance.ChangePage(App.AppPage.ADMIN_ITEM_LISTING_PAGE.getPageController());
            }
            else
            {
                view.TriggerErrorDialogue("Error Deleting Item", "Error encountered trying to delete item");
                return;
            }
        }
    }
}

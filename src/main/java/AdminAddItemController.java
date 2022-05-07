import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class AdminAddItemController implements IPageController
{
    private JFrame window;
    private AdminAddItemView view;
    private AdminAddItemController controller;
    private App appInstance;

    private String imageLocation = "";

    @Override
    public void InitController(App appInstance, JFrame window) {
        this.appInstance = appInstance;
        this.window = window;
        this.controller = this;
        initComponents();
    }

    private void initComponents() {
        view = new AdminAddItemView(controller, window);
    }

    public void OnAdminPage() {
        appInstance.ChangePage(App.AppPage.ADMIN_PAGE.getPageController());
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

        try{

            float itemPriceFloat = Float.parseFloat(itemPrice);
            if (itemPrice.contains("d") || itemPrice.contains("f"))
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

        //Upload to database
        String[] itemValues = new String[] {"", itemName, "0", imageLocation, itemPrice };
        boolean isSuccessCreate = Database.TextFileCreate(Database.FileType.ITEM, itemValues);

        if (isSuccessCreate)
        {
            Icon icon = ResizeImageUtility.ResizeImageUtility("GreenTick", new Dimension(50,50), ImageType.ICON);
            JOptionPane.showOptionDialog(null, "Item successfully created", "Success!",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon, new String[]{"Okay"}, true);
            appInstance.ChangePage(App.AppPage.ADMIN_PAGE.getPageController());
            return;
        }
        else
        {
            view.TriggerErrorDialogue("Error Creating Item", "Error encountered trying to create item");
            return;
        }

    }

    public void OnSelectImgBtn() {
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
            }
            else
            {
                imageLocation = "";
                view.SetUIImageFileName(imageLocation);
                view.TriggerErrorDialogue("Error Uploading Image",
                        "Format of image uploaded is not compatible.\n\nImage must be in png format");
            }
        }

    }

}

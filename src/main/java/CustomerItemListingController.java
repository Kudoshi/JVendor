import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class CustomerItemListingController implements IPageController{
    private JFrame window;
    private CustomerItemListingView view;
    private CustomerItemListingController controller;
    private App appInstance;

    @Override
    public void InitController(App appInstance, JFrame window) {
        this.appInstance = appInstance;
        this.window = window;
        this.controller = this;
        initComponents();
    }

    private void initComponents() {
        view = new CustomerItemListingView(controller, window);
    }

    public void OnBackArrow() {
        appInstance.ChangePage(App.AppPage.MAIN_PAGE.getPageController());
    }

    public ArrayList<String[]> GetItemList()
    {
        ArrayList<String[]> itemList = Database.TextFileGetAll(Database.FileType.ITEM);

        for (int i = itemList.size()-1; i > -1; i--)
        {
            int itemStock = Integer.parseInt(itemList.get(i)[2]);
            if (itemStock == 0) {
                itemList.remove(i);
            }
        }
        return itemList;
    }

    public void OnItemClick(String[] itemValues) {
        Item item = new Item(itemValues);
        view.SetUIPayment(item);
    }

    public void PopupPayPanel(CustomJPanel paymentParent, Item item) {

        // Simulate waiting for payment on the POS
        int randomNum = ThreadLocalRandom.current().nextInt(2000, 7000);


        Timer timer = new Timer(randomNum, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                Window optionPane = SwingUtilities.getWindowAncestor(paymentParent);

                if (optionPane != null) {
                    optionPane.setVisible(false);
                    PaymentSuccess(item);
                }
            }
        });
        timer.setRepeats(false);
        timer.start();

        JOptionPane.showOptionDialog(null, paymentParent, "Payment", JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, new String[] {"Cancel"}, null);

    }

    private void PaymentSuccess(Item item) {
        //Update data
        String[] itemValues = new String[] { item.getItemCode(), item.getItemName(), String.valueOf(item.getStock()-1), item.getPictureName(), String.valueOf(item.getPrice()) };
        boolean isUpdated = Database.TextFileUpdateData(Database.FileType.ITEM, itemValues);

        if (!isUpdated)
        {
            view.TriggerErrorDialogue("Error", "Error dispensing item");
        }

        String pattern = "HH-mm-MM-dd-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        Database.TextFileCreate(Database.FileType.TRANSACTION, new String[] { "", item.getItemCode(), date, String.valueOf(item.getPrice())});

        Icon icon = ResizeImageUtility.ResizeImageUtility("GreenTick", new Dimension(50,50), ImageType.ICON);
        JOptionPane.showOptionDialog(null,
                "<html><h2>Payment Successful</h2><br><br><b>Thank you!</b><br><br>" +
                        "Take your item from the vending machine</html>", "Payment Success",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon, new String[]{"Okay"}, true);
        appInstance.ChangePage(App.AppPage.MAIN_PAGE.getPageController());
        return;
    }
}

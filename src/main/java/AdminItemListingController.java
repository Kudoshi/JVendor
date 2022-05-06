import javax.swing.*;
import java.util.ArrayList;

public class AdminItemListingController implements IPageController {
    private JFrame window;
    private AdminItemListingView view;
    private AdminItemListingController controller;
    private App appInstance;

    @Override
    public void InitController(App appInstance, JFrame window) {
        this.appInstance = appInstance;
        this.window = window;
        this.controller = this;
        initComponents();
    }

    private void initComponents() {
        view = new AdminItemListingView(controller, window);
    }

    public void OnAdminPage() {
        appInstance.ChangePage(App.AppPage.ADMIN_PAGE.getPageController());
    }

    public void OnEditItemPage(String itemCode)
    {
        appInstance.SetGlobalVar("ItemCode", itemCode);
        appInstance.ChangePage(App.AppPage.ADMIN_ITEM_EDIT_PAGE.getPageController());
    }

    public ArrayList<String[]> GetItemList()
    {
       return Database.TextFileGetAll(Database.FileType.ITEM);
    }

}

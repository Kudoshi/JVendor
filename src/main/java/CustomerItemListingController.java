import javax.swing.*;
import java.util.ArrayList;

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
        
        return Database.TextFileGetAll(Database.FileType.ITEM);
    }
}

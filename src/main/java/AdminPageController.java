import javax.swing.*;

public class AdminPageController implements IPageController
{
    private JFrame window;
    private AdminPageView view;
    private AdminPageController controller;
    private App appInstance;

    @Override
    public void SetWindowInstance(JFrame windowInstance) {
        this.window = windowInstance;
    }

    @Override
    public void InitController(App appInstance, JFrame window) {
        this.appInstance = appInstance;
        this.window = window;
        this.controller = this;
        initComponents();
    }

    private void initComponents() {
        view = new AdminPageView(controller, window);
    }

    //Events

    public void OnBackToHomepage()
    {
        this.appInstance.ChangePage(App.AppPage.MAIN_PAGE.getPageController());
    }

    public void OnAddNewItemPage()
    {
        this.appInstance.ChangePage(App.AppPage.ADMIN_ADD_ITEM_PAGE.getPageController());
    }

    public void OnItemListingPage()
    {
        this.appInstance.ChangePage(App.AppPage.ADMIN_ITEM_LISTING_PAGE.getPageController());
    }
}

import javax.swing.*;

public class AdminAddItemController implements IPageController
{
    private JFrame window;
    private AdminAddItemView view;
    private AdminAddItemController controller;
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
        view = new AdminAddItemView(controller, window);
    }

    public void OnAdminPage() {
        appInstance.ChangePage(App.AppPage.ADMIN_PAGE.getPageController());
    }

    //Events
}

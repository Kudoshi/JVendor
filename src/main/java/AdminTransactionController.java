import javax.swing.*;
import java.util.ArrayList;

public class AdminTransactionController implements IPageController  {
    private JFrame window;
    private AdminTransactionView view;
    private AdminTransactionController controller;
    private App appInstance;

    @Override
    public void InitController(App appInstance, JFrame window) {
        this.appInstance = appInstance;
        this.window = window;
        this.controller = this;
        initComponents();
    }

    private void initComponents() {
        view = new AdminTransactionView(controller, window);
    }

    public void OnAdminPage() {
        appInstance.ChangePage(App.AppPage.ADMIN_PAGE.getPageController());
    }

    public ArrayList<String[]> GetTransactionList() {
        return Database.TextFileGetAll(Database.FileType.TRANSACTION);
    }
}

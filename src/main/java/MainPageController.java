import javax.swing.*;
import java.awt.*;

public class MainPageController implements IPageController{
    private JFrame window;
    private MainPageView view;
    private MainPageController controller;
    private App appInstance;

    @Override
    public void InitController(App appInstance, JFrame window) {
        this.appInstance = appInstance;
        this.window = window;
        this.controller = this;
        initComponents();
    }

    private void initComponents() {
        view = new MainPageView(controller, window);
    }

    public void OnBuyButtonClicked() {
        appInstance.ChangePage(App.AppPage.CUSTOMER_ITEM_LISTING_PAGE.getPageController());
    }

    public void ShowAdminPanel() {
        view.ShowAdminOptionPane();
    }

    public void VerifyAdminCode(JPanel panel, String codeInput) {
        if (codeInput.equals("12345"))
        {
            Window optionPane = SwingUtilities.getWindowAncestor(panel);
            optionPane.setVisible(false);

            appInstance.ChangePage(App.AppPage.ADMIN_PAGE.getPageController());
        }
    }
}

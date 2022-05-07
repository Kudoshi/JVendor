import javax.swing.*;
import java.util.HashMap;
import java.util.Scanner;

public class App implements IGUIStyle{

    public JFrame window;
    public IPageController currentPage;

    private HashMap<String, Object> globalVar;

    public static void main(String[] args) {
        //Check dependencies text file first

        //Clear cart

        //Open main window
        App appInstance = new App();
    }
    enum AppPage
    {
        MAIN_PAGE(WelcomePage.class), ADMIN_PAGE(AdminPageController.class),
        ADMIN_ADD_ITEM_PAGE(AdminAddItemController.class), ADMIN_ITEM_LISTING_PAGE(AdminItemListingController.class),
        CUSTOMER_ITEM_LISTING_PAGE(CustomerItemListingController.class), ADMIN_ITEM_EDIT_PAGE(AdminItemEditController.class);

        private Class<?> pageController;

        AppPage(Class<?> pageController)
        {
            this.pageController = pageController;
        }

        public Class<?> getPageController()
        {
            return this.pageController;
        }

    }

    public App() {
        globalVar = new HashMap<String, Object>();
        window = new JFrame();

        //Frame initialization
        window.setSize(WINDOW_MAX_WIDTH,WINDOW_MAX_HEIGHT);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBackground(IGUIStyle.CustomColor.WHITE_NORMAL);
        window.setLayout(null);
        window.setLocationRelativeTo(null);
        window.setResizable(false);


//        WelcomePage welcomePage = new WelcomePage();
//        welcomePage.InitController(this, window);
//        currentPage = welcomePage;
        ChangePage(AppPage.CUSTOMER_ITEM_LISTING_PAGE.getPageController());

        window.setVisible(true);
    }

    public void ChangePage(Class<?> pageController)
    {
        try {
            window.getContentPane().removeAll();
            IPageController newPageController = (IPageController) pageController.newInstance();
            currentPage = newPageController;
            currentPage.InitController(this, window);
            window.revalidate();
            window.repaint();

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public Object GetGlobalVar(String key) {
        return globalVar.get(key);
    }
    public void SetGlobalVar(String key, Object value) {
        this.globalVar.put(key,value);
    }
    public void RemoveGlobalVar(String key)
    {
        this.globalVar.remove(key);
    }
    public void ClearGlobalVar()
    {
        this.globalVar.clear();
    }
}

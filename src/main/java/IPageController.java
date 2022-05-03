import javax.swing.*;

interface IPageController {
    public void SetWindowInstance(JFrame windowInstance);
    public void InitController(App appInstance, JFrame window);
}

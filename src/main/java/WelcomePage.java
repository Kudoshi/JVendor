import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The UI of the first page welcome screen
 */
public class WelcomePage extends CustomJPanel implements IGUIStyle, IPageController {
    private App appInstance;
    private JFrame window;

    private JOptionPane adminParentPanel;


    @Override
    public void SetWindowInstance(JFrame windowInstance) {
        this.window = windowInstance;
    }

    @Override
    public void InitController(App appInstance, JFrame window) {
        this.appInstance = appInstance;
        this.window = window;
        initComponents();
    }

    private void initComponents()
    {
        FlatLightLaf.setup();
        //TopBar
        JPanel topBar = new JPanel();
        topBar.setBackground(CustomColor.BLUE_BRAND);
        topBar.setBounds(0,0,WINDOW_MAX_WIDTH, 120);
        topBar.setLayout(null);
        topBar.setVisible(true);

        CustomJLabel brandName = new CustomJLabel("JVENDOR", new Font("Calibri", Font.BOLD, 65));
        brandName.setForeground(CustomColor.WHITE_NORMAL);
        brandName.setBounds(170,18, 400, 100);

        //MidText
        CustomJLabel midTextHeader = new CustomJLabel("Treat yourself better", new Font("Calibri", Font.BOLD, 30));
        midTextHeader.setBounds(165, 100, 400, 100);
        midTextHeader.setForeground(CustomColor.BLUE_BRAND);
        CustomJLabel midTextBody = new CustomJLabel(FontSize.BODY,"Food, Beverage, and other goodies just for you");
        midTextBody.setBounds(150, 140, 400, 100);
        JPanel midTextLine = new JPanel();
        midTextLine.setBounds(160, 165, 270,3);
        midTextLine.setBackground(CustomColor.PINK_ACCENT);

        //MidPanel
        RoundedButton roundedButton = new RoundedButton(ROUNDED_BORDER_RADIUS, CustomColor.BLACK_MAIN, CustomColor.BLACK_MAIN);
        roundedButton.setBounds(140,250, 300,325);
        roundedButton.setLayout(new BorderLayout());
        roundedButton.setVisible(true);
        roundedButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("To Item List");
            }
        });

        CustomJLabel buyNowBtn = new CustomJLabel("ShopBag", new Dimension(150,150), ImageType.ICON, false);
        CustomJLabel buyNowLabel = new CustomJLabel(FontSize.HEADER2, "Buy Now");
        buyNowLabel.setHorizontalAlignment(JLabel.CENTER);
        buyNowLabel.setForeground(CustomColor.GREEN_ATTENTION);
        buyNowLabel.setBackground(Color.BLUE);

        //Bottom text
        CustomJLabel tradeMarkTxt = new CustomJLabel(FontSize.HIDE, "<html>&emsp;&emsp;&ensp;JVendor 2022 ©<br/>Software created by Brenden</html>");
        tradeMarkTxt.setHorizontalAlignment(SwingConstants.CENTER);
        tradeMarkTxt.setVerticalAlignment(SwingConstants.CENTER);
        tradeMarkTxt.setBounds(150,690,300,100);
        tradeMarkTxt.setForeground(CustomColor.BLACK_HIDE);

        //Corner admin button
        CustomJButton settingBtn = new CustomJButton("Settings");
        settingBtn.setForeground(CustomColor.BLACK_HIDE);
        settingBtn.setBounds(0,700,100,100);
        settingBtn.setBorder(BorderFactory.createEmptyBorder());
        settingBtn.setContentAreaFilled(false);
        settingBtn.setBorderPainted(false);
        settingBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ShowAdminCodePanel();
            }
        });

        setBounds(0,0, WINDOW_MAX_WIDTH, WINDOW_MAX_HEIGHT);

        //Add Components
        add(topBar);
        add(midTextHeader);
        add(midTextBody);
        add(midTextLine);
        topBar.add(brandName);
        add(roundedButton);
        roundedButton.add(buyNowBtn, BorderLayout.NORTH);
        roundedButton.add(buyNowLabel, BorderLayout.CENTER);
        add(tradeMarkTxt);
        add(settingBtn);
        setVisible(true);
        window.add(this);

    }

    private void ShowAdminCodePanel()
    {
        FlatLightLaf.setup();
        messageContainer = new CustomJPanel();
        messageContainer.setLayout(new BoxLayout(messageContainer,BoxLayout.Y_AXIS));
        CustomJLabel message = new CustomJLabel(FontSize.HEADER2, "Enter Admin Code");

        JTextField codeInput = new JTextField();
        codeInput.setPreferredSize(new Dimension(75,35));
        codeInput.setHorizontalAlignment(SwingConstants.CENTER);
        codeInput.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                CheckAdminCode(codeInput.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                CheckAdminCode(codeInput.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                CheckAdminCode(codeInput.getText());
            }


        });

        messageContainer.add(message);
        messageContainer.add(codeInput);


        adminParentPanel = new JOptionPane();
        adminParentPanel.showOptionDialog(window, messageContainer, "Admin Code Panel",
                -1, JOptionPane.PLAIN_MESSAGE,null, new String[]{"OK"}, null);

    }
    private JPanel messageContainer;
    private void CheckAdminCode(String codeInput)
    {
        if (codeInput.equals("12345"))
        {
          messageContainer.removeAll();

          CustomJLabel successMsg = new CustomJLabel(FontSize.BODY, "Success. Entering admin dashboard");
          successMsg.setForeground(CustomColor.GREEN_ATTENTION);
          messageContainer.add(successMsg);
          messageContainer.revalidate();
          messageContainer.repaint();
          appInstance.ChangePage(App.AppPage.ADMIN_PAGE.getPageController());
        }
    }
}

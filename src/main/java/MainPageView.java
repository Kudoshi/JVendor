import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPageView extends JPanel implements IGUIStyle{
    private JFrame window;
    private MainPageController controller;

    public MainPageView(MainPageController controller, JFrame window)
    {
        this.window = window;
        this.controller = controller;
        InitView();
    }

    private void InitView() {
        FlatLightLaf.setup();

        setLayout(null);
        setBounds(0,0, WINDOW_MAX_WIDTH, WINDOW_MAX_HEIGHT);

        //TopBar
        JPanel topBar = new JPanel();
        topBar.setBackground(IGUIStyle.CustomColor.BLUE_BRAND);
        topBar.setBounds(0,0,WINDOW_MAX_WIDTH, 120);
        topBar.setLayout(null);
        topBar.setVisible(true);

        CustomJLabel brandName = new CustomJLabel("JVENDOR", new Font("Calibri", Font.BOLD, 65));
        brandName.setForeground(IGUIStyle.CustomColor.WHITE_NORMAL);
        brandName.setBounds(170,18, 400, 100);

        //MidText
        CustomJLabel midTextHeader = new CustomJLabel("Treat yourself better", new Font("Calibri", Font.BOLD, 30));
        midTextHeader.setBounds(165, 100, 400, 100);
        midTextHeader.setForeground(IGUIStyle.CustomColor.BLUE_BRAND);
        CustomJLabel midTextBody = new CustomJLabel(IGUIStyle.FontSize.BODY,"Food, Beverage, and other goodies just for you");
        midTextBody.setBounds(150, 140, 400, 100);
        JPanel midTextLine = new JPanel();
        midTextLine.setBounds(160, 165, 270,3);
        midTextLine.setBackground(IGUIStyle.CustomColor.PINK_ACCENT);

        //MidPanel
        RoundedButton roundedButton = new RoundedButton(ROUNDED_BORDER_RADIUS, IGUIStyle.CustomColor.BLACK_MAIN, IGUIStyle.CustomColor.BLACK_MAIN);
        roundedButton.setBounds(140,250, 300,325);
        roundedButton.setLayout(new BorderLayout());
        roundedButton.setVisible(true);
        roundedButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.OnBuyButtonClicked();
            }
        });

        CustomJLabel buyNowBtn = new CustomJLabel("ShopBag", new Dimension(150,150), ImageType.ICON, false);
        CustomJLabel buyNowLabel = new CustomJLabel(IGUIStyle.FontSize.HEADER2, "Buy Now");
        buyNowLabel.setHorizontalAlignment(JLabel.CENTER);
        buyNowLabel.setForeground(IGUIStyle.CustomColor.GREEN_ATTENTION);
        buyNowLabel.setBackground(Color.BLUE);

        //Bottom text
        CustomJLabel tradeMarkTxt = new CustomJLabel(IGUIStyle.FontSize.HIDE, "<html>&emsp;&emsp;&ensp;JVendor 2022 ©<br/>Software created by Brenden</html>");
        tradeMarkTxt.setHorizontalAlignment(SwingConstants.CENTER);
        tradeMarkTxt.setVerticalAlignment(SwingConstants.CENTER);
        tradeMarkTxt.setBounds(150,690,300,100);
        tradeMarkTxt.setForeground(IGUIStyle.CustomColor.BLACK_HIDE);

        //Corner admin button
        CustomJButton settingBtn = new CustomJButton("Settings");
        settingBtn.setForeground(IGUIStyle.CustomColor.BLACK_HIDE);
        settingBtn.setBounds(0,700,100,100);
        settingBtn.setBorder(BorderFactory.createEmptyBorder());
        settingBtn.setContentAreaFilled(false);
        settingBtn.setBorderPainted(false);
        settingBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.ShowAdminPanel();
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

    public void ShowAdminOptionPane() {
        JPanel messageContainer = new CustomJPanel();
        messageContainer.setLayout(new BoxLayout(messageContainer,BoxLayout.Y_AXIS));
        CustomJLabel message = new CustomJLabel(FontSize.HEADER2, "Enter Admin Code");

        JTextField codeInput = new JTextField();
        codeInput.setPreferredSize(new Dimension(75,35));
        codeInput.setHorizontalAlignment(SwingConstants.CENTER);
        codeInput.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                controller.VerifyAdminCode(messageContainer, codeInput.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                controller.VerifyAdminCode(messageContainer, codeInput.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                controller.VerifyAdminCode(messageContainer, codeInput.getText());
            }


        });

        messageContainer.add(message);
        messageContainer.add(codeInput);



        JOptionPane.showOptionDialog(window, messageContainer, "Admin Code Panel",
                -1, JOptionPane.PLAIN_MESSAGE,null, new String[]{"OK"}, null);

    }
}

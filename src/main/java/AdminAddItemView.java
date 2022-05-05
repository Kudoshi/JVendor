import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class AdminAddItemView extends JPanel implements IGUIStyle{
    private JFrame window;
    private AdminAddItemController controller;

    private CustomJLabel imageFileName;


    public AdminAddItemView(AdminAddItemController controller, JFrame window) {
        this.window = window;
        this.controller = controller;
        InitView();
    }

    private void InitView() {
        FlatLightLaf.setup();


        setLayout(null);
        setBounds(0,0, WINDOW_MAX_WIDTH, WINDOW_MAX_HEIGHT);

        // Top Banner
        CustomJPanel brandBannerParent = new CustomJPanel();
        brandBannerParent.setBounds(0,0, WINDOW_MAX_WIDTH, 70);
        brandBannerParent.setBackground(CustomColor.BLUE_BRAND);
        brandBannerParent.setLayout(new BorderLayout());

        CustomJLabel brandBannerTitle = new CustomJLabel(FontSize.HEADER1, "Admin Panel", Font.BOLD);
        brandBannerTitle.setHorizontalAlignment(SwingConstants.CENTER);
        brandBannerTitle.setForeground(CustomColor.WHITE_NORMAL);

        CustomJButton backBtn = new CustomJButton("←");
        backBtn.setFont(new Font(FONT_TYPE, Font.PLAIN, 35));
        backBtn.setBounds(10,15,50,50);
        backBtn.DisableBorderStyling();
        backBtn.setForeground(CustomColor.WHITE_NORMAL);
        backBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.OnAdminPage();
            }
        });

        // Item category banner

        CustomJLabel addNewItemBanner = new CustomJLabel(FontSize.HEADER2, "Add New Item", Font.BOLD);
        addNewItemBanner.setBounds(0,70, WINDOW_MAX_WIDTH, 50);
        addNewItemBanner.setHorizontalAlignment(SwingConstants.CENTER);
        addNewItemBanner.setVerticalAlignment(SwingConstants.CENTER);
        addNewItemBanner.setBackground(CustomColor.BLACK_MAIN);
        addNewItemBanner.setOpaque(true);
        addNewItemBanner.setForeground(CustomColor.WHITE_NORMAL);


        //>> Form

        CustomJPanel formContainer = new CustomJPanel(20, CustomColor.WHITE_NORMAL, CustomColor.WHITE_NORMAL);
        formContainer.setBounds(50,150, 500,500);
        formContainer.setLayout(null);

        formContainer.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println(e.getX() + "," + e.getY());
            }
        });

        //Item Pic
        CustomJLabel formPic = new CustomJLabel(FontSize.BODY, "Item Picture:", Font.BOLD);
        formPic.setBounds(40, 40, 150, 30);

        CustomJButton selectImgBtn = new CustomJButton(FontSize.BODY, "Select Image", Font.PLAIN);
        selectImgBtn.setBounds(150, 40, 125, 30);
        selectImgBtn.setBackground(CustomColor.BLUE_BRAND);
        selectImgBtn.setForeground(CustomColor.WHITE_NORMAL);
        selectImgBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.OnSelectImgBtn();
            }
        });

        CustomJLabel selectImgWarning = new CustomJLabel(FontSize.HIDE, "Only PNG image file allowed", Font.PLAIN);
        selectImgWarning.setBounds(290, 40, 200, 30);

        imageFileName = new CustomJLabel(FontSize.HIDE, "", Font.PLAIN);
        imageFileName.setBounds(155, 75, 300, 30);

        //Item Name
        CustomJLabel formItemName = new CustomJLabel(FontSize.BODY, "Item Name:", Font.BOLD);
        formItemName.setBounds(40, 110, 150, 30);

        JTextField fieldItemName = new JTextField();
        fieldItemName.setBounds(150, 110, 250, 30);

        //Item Price
        CustomJLabel formItemPrice = new CustomJLabel(FontSize.BODY, "Item Price:", Font.BOLD);
        formItemPrice.setBounds(40, 165, 150, 30);

        JTextField fieldItemPrice = new JTextField();
        fieldItemPrice.setBounds(150, 165, 125, 30);

        CustomJLabel warningItemPrice = new CustomJLabel(FontSize.HIDE, "E.g. 25.50");
        warningItemPrice.setBounds(290, 165, 200,30);

        //Item Stock
        CustomJLabel formItemStock = new CustomJLabel(FontSize.BODY, "Item Stock:", Font.BOLD);
        formItemStock.setBounds(40, 220, 150, 30);

        JTextField fieldItemStock = new JTextField("0");
        fieldItemStock.setBounds(150, 220, 125, 30);
        fieldItemStock.setHorizontalAlignment(SwingConstants.CENTER);
        fieldItemStock.setEditable(false);

        CustomJLabel warningItemStock = new CustomJLabel(FontSize.HIDE, "* Add stock in the item listing later");
        warningItemStock.setForeground(CustomColor.BLACK_HIDE);
        warningItemStock.setBounds(290, 220, 200,30);

        //>> Bottom Menu Bar
        CustomJPanel menuBar = new CustomJPanel();
        menuBar.setBounds(0, 690, WINDOW_MAX_WIDTH,110);
        menuBar.setBackground(CustomColor.BLACK_MAIN);

        CustomJButton submitBtn = new CustomJButton(FontSize.BODY,"Submit", Font.PLAIN);
        submitBtn.setBackground(CustomColor.GREEN_ATTENTION);
        submitBtn.setHorizontalAlignment(SwingConstants.CENTER);
        submitBtn.setVerticalAlignment(SwingConstants.CENTER);
        submitBtn.setForeground(CustomColor.WHITE_NORMAL);
        submitBtn.setBounds(260,20, 80,30);
        submitBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.OnSubmit(fieldItemName.getText(),fieldItemPrice.getText());
            }
        });

        //Add Item
        brandBannerParent.add(brandBannerTitle, BorderLayout.CENTER);
        menuBar.add(submitBtn);
        formContainer.add(formPic);
        formContainer.add(selectImgBtn);
        formContainer.add(selectImgWarning);
        formContainer.add(imageFileName);
        formContainer.add(formItemName);
        formContainer.add(fieldItemName);
        formContainer.add(formItemPrice);
        formContainer.add(fieldItemPrice);
        formContainer.add(warningItemPrice);
        formContainer.add(formItemStock);
        formContainer.add(fieldItemStock);
        formContainer.add(warningItemStock);


        add(backBtn);
        add(brandBannerParent);
        add(addNewItemBanner);
        add(formContainer);
        add(menuBar);

        window.add(this);
    }

    public void SetUIImageFileName(String fileName)
    {
        imageFileName.setText(fileName);
    }

    public void TriggerErrorDialogue(String title, String errorMessage)
    {
        JOptionPane.showMessageDialog(null, errorMessage, title, JOptionPane.ERROR_MESSAGE);
    }

}

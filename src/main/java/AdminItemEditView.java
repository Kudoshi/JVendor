import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdminItemEditView extends JPanel implements IGUIStyle{
    private JFrame window;
    private AdminItemEditController controller;

    private CustomJLabel imageFileName;
    private CustomJLabel itemIcon;

    public AdminItemEditView(AdminItemEditController controller, JFrame window) {
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
                controller.OnBack();
            }
        });

        // Item category banner

        CustomJLabel addNewItemBanner = new CustomJLabel(FontSize.HEADER2, "Edit Item", Font.BOLD);
        addNewItemBanner.setBounds(0,70, WINDOW_MAX_WIDTH, 50);
        addNewItemBanner.setHorizontalAlignment(SwingConstants.CENTER);
        addNewItemBanner.setVerticalAlignment(SwingConstants.CENTER);
        addNewItemBanner.setBackground(CustomColor.BLACK_MAIN);
        addNewItemBanner.setOpaque(true);
        addNewItemBanner.setForeground(CustomColor.WHITE_NORMAL);


        //>> Form

        Item itemEditing = controller.GetItem();

        CustomJPanel formContainer = new CustomJPanel(20, CustomColor.WHITE_NORMAL, CustomColor.WHITE_NORMAL);
        formContainer.setBounds(50,150, 500,500);
        formContainer.setLayout(null);
        formContainer.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println(e.getX() + "," + e.getY());
            }
        });

        //Item Icon
        itemIcon = new CustomJLabel(itemEditing.getPictureName(), new Dimension(110,110)
                ,ImageType.ITEM_IMAGE, true);
        itemIcon.setBounds(190,25, 110,110);


        //Item Select Image Button
        CustomJLabel formPic = new CustomJLabel(FontSize.BODY, "Item Picture:", Font.BOLD);
        formPic.setBounds(40, 170, 150, 30);

        CustomJButton selectImgBtn = new CustomJButton(FontSize.BODY, "Select Image", Font.PLAIN);
        selectImgBtn.setBounds(150, 170, 125, 30);
        selectImgBtn.setBackground(CustomColor.BLUE_BRAND);
        selectImgBtn.setForeground(CustomColor.WHITE_NORMAL);
        selectImgBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.OnSelectImage();
            }
        });

        CustomJLabel selectImgWarning = new CustomJLabel(FontSize.HIDE, "Only PNG image file allowed", Font.PLAIN);
        selectImgWarning.setBounds(290, 170, 200, 30);

        imageFileName = new CustomJLabel(FontSize.HIDE, controller.GetItemPictureName(), Font.PLAIN);
        imageFileName.setBounds(155, 205, 300, 30);

        //Item Name
        CustomJLabel formItemName = new CustomJLabel(FontSize.BODY, "Item Name:", Font.BOLD);
        formItemName.setBounds(40, 240, 150, 30);

        JTextField fieldItemName = new JTextField(itemEditing.getItemName());
        fieldItemName.setBounds(150, 240, 250, 30);

        //Item Price
        CustomJLabel formItemPrice = new CustomJLabel(FontSize.BODY, "Item Price:", Font.BOLD);
        formItemPrice.setBounds(40, 295, 150, 30);

        JTextField fieldItemPrice = new JTextField(String.format("%.2f", itemEditing.getPrice()));
        fieldItemPrice.setBounds(150, 295, 125, 30);

        CustomJLabel warningItemPrice = new CustomJLabel(FontSize.HIDE, "E.g. 25.50");
        warningItemPrice.setBounds(290, 295, 200,30);

        //Item Stock
        CustomJLabel formItemStock = new CustomJLabel(FontSize.BODY, "Item Stock:", Font.BOLD);
        formItemStock.setBounds(40, 350, 150, 30);

        JTextField fieldItemStock = new JTextField(String.valueOf(itemEditing.getStock()));
        fieldItemStock.setBounds(150, 350, 125, 30);
        fieldItemStock.setHorizontalAlignment(SwingConstants.CENTER);
        fieldItemStock.setEditable(false);

        CustomJButton addStockBtn = new CustomJButton(FontSize.BODY, "Add Stock", Font.PLAIN);
        addStockBtn.setBounds(290, 350, 125,30);
        addStockBtn.setBackground(CustomColor.BLUE_BRAND);
        addStockBtn.setForeground(CustomColor.WHITE_NORMAL);
        addStockBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.OnAddStock();
            }
        });

        CustomJButton deleteBtn = new CustomJButton("Delete", new Dimension(25,25), ImageType.ICON,true);
        deleteBtn.setBounds(450, 25, 25, 25);
        deleteBtn.setBorder(BorderFactory.createEmptyBorder());
        deleteBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.OnDeleteItem();
            }
        });

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
        formContainer.add(itemIcon);
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
        formContainer.add(addStockBtn);
        formContainer.add(deleteBtn);


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

    public void SetUIItemIcon(Icon icon) {
        itemIcon.setIcon(icon);
    }

    public void TriggerErrorDialogue(String title, String errorMessage)
    {

        JOptionPane.showMessageDialog(null, errorMessage, title, JOptionPane.ERROR_MESSAGE);
    }

    public void SetUIStock() {
        Item item = controller.GetItem();


        CustomJPanel container = new CustomJPanel();
        container.setLayout(null);
        container.setPreferredSize(new Dimension(500,500));

        CustomJLabel panelOpenIcon = new CustomJLabel("RestockPanel", new Dimension(200,200),  ImageType.ICON, false);
        panelOpenIcon.setBounds(160,0,200,200);


        CustomJLabel restockTitle = new CustomJLabel(FontSize.HEADER1, "Restock Item", Font.BOLD);
        restockTitle.setBounds(160,240,200,40);

        CustomJLabel restockDescription = new CustomJLabel(FontSize.HIDE, "<html><p style='text-align: center;'>" +
                "1. Input amount of stock to add<br>2. Put the items into the restock panel</p></html>");
        restockDescription.setHorizontalAlignment(SwingConstants.CENTER);
        restockDescription.setBounds(30,280,450,50);

        JTextField restockAmt = new JTextField("0");
        restockAmt.setBounds(175,350,150,40);
        restockAmt.setHorizontalAlignment(SwingConstants.CENTER);
        restockAmt.setBorder(BorderFactory.createLineBorder(CustomColor.GREEN_ATTENTION, 2));

        CustomJButton confirmbtn = new CustomJButton(FontSize.BODY, "Confirm Restock", Font.PLAIN);
        confirmbtn.setBounds(180,450,140,30);
        confirmbtn.setForeground(CustomColor.WHITE_NORMAL);
        confirmbtn.setBackground(CustomColor.GREEN_ATTENTION);
        confirmbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.OnSubmitStock(container,restockAmt.getText());
            }
        });

        container.add(panelOpenIcon);
        container.add(restockTitle);
        container.add(restockDescription);
        container.add(restockAmt);
        container.add(confirmbtn);

        JOptionPane.showOptionDialog(null, container, "Restock Item",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{}, null);
    }
}

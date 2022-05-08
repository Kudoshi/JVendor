import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AdminItemListingView extends JPanel implements IGUIStyle{
    private JFrame window;
    private AdminItemListingController controller;

    public AdminItemListingView(AdminItemListingController controller, JFrame window) {
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

        CustomJButton backBtn = new CustomJButton("<");
        backBtn.setFont(new Font(FONT_TYPE, Font.PLAIN, 35));
        backBtn.setBounds(10,15,50,50);
        backBtn.DisableBorderStyling();
        backBtn.setForeground(CustomColor.WHITE_NORMAL);
        backBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.OnAdminPage();
            }
        });

        // Item listing banner

        CustomJLabel itemListingBanner = new CustomJLabel(FontSize.HEADER2, "Item Listing", Font.BOLD);
        itemListingBanner.setBounds(0,70, WINDOW_MAX_WIDTH, 50);
        itemListingBanner.setHorizontalAlignment(SwingConstants.CENTER);
        itemListingBanner.setVerticalAlignment(SwingConstants.CENTER);
        itemListingBanner.setBackground(CustomColor.BLACK_MAIN);
        itemListingBanner.setOpaque(true);
        itemListingBanner.setForeground(CustomColor.WHITE_NORMAL);


        //Item listing
        ArrayList<String[]> itemList = controller.GetItemList();
        int containerHeight = 575; //575 in accordance with JScrollPane height
        if (itemList != null)
        {
            int preferredHeight = itemList.size() * 55;
            if (preferredHeight > containerHeight)
            {
                containerHeight = preferredHeight;
            }
        }

        CustomJPanel itemListingContainer = new CustomJPanel();
        itemListingContainer.setLayout(new FlowLayout());
        itemListingContainer.setPreferredSize(new Dimension(500, containerHeight));

        //Inserting items into listing container
        if (itemList != null && itemList.size() > 0)
        {
            for (String[] item: itemList)
            {
                CustomJPanel itemRecordContainer = new CustomJPanel();
                itemRecordContainer.setBackground(CustomColor.WHITE_NORMAL);
                itemRecordContainer.setPreferredSize(new Dimension(500, 50));


                CustomJLabel itemPic = new CustomJLabel(item[3], new Dimension(50,50), ImageType.ITEM_IMAGE, true);
                itemPic.setBounds(-7,0,50,50);
                itemPic.setBackground(CustomColor.WHITE_DARK_2);
                itemPic.setOpaque(true);

                CustomJLabel itemName = new CustomJLabel(FontSize.BODY, item[1]);
                itemName.setBackground(CustomColor.WHITE_NORMAL);
                itemName.setBounds(50,5,350,50);
                itemName.setOpaque(true);

                CustomJLabel itemStock = new CustomJLabel(FontSize.BODY, "x "+item[2], Font.BOLD);
                itemStock.setBounds(400,5, 45, 50);
                itemStock.setBackground(CustomColor.WHITE_NORMAL);
                itemStock.setHorizontalAlignment(SwingConstants.CENTER);
                itemStock.setOpaque(true);

                CustomJButton itemEdit = new CustomJButton("Pen", new Dimension(25,25), ImageType.ICON, true);
                itemEdit.setBounds(450, 5, 40,40);
                itemEdit.setBackground(CustomColor.WHITE_NORMAL);
                itemEdit.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                itemEdit.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        controller.OnEditItemPage(item[0]);
                    }
                });
                itemRecordContainer.add(itemPic);
                itemRecordContainer.add(itemName);
                itemRecordContainer.add(itemStock);
                itemRecordContainer.add(itemEdit);
                itemListingContainer.add(itemRecordContainer);

            }
        }


        JScrollPane itemScrollPane = new JScrollPane(itemListingContainer);
        itemScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        itemScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        itemScrollPane.setBounds(50,150, 500,575);



        //Add Item
        brandBannerParent.add(brandBannerTitle, BorderLayout.CENTER);



        add(itemScrollPane);
        add(backBtn);
        add(brandBannerParent);
        add(itemListingBanner);
        window.add(this);
    }
}

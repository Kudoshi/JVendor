import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class CustomerItemListingView extends JPanel implements IGUIStyle {
    private JFrame window;
    private CustomerItemListingController controller;

    public CustomerItemListingView(CustomerItemListingController controller, JFrame window) {
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
        brandBannerParent.setBackground(IGUIStyle.CustomColor.BLUE_BRAND);
        brandBannerParent.setLayout(new BorderLayout());

        CustomJLabel brandBannerTitle = new CustomJLabel(IGUIStyle.FontSize.HEADER1, "JVendor", Font.BOLD);
        brandBannerTitle.setHorizontalAlignment(SwingConstants.CENTER);
        brandBannerTitle.setForeground(IGUIStyle.CustomColor.WHITE_NORMAL);

        CustomJButton backBtn = new CustomJButton("←");
        backBtn.setFont(new Font(FONT_TYPE, Font.PLAIN, 35));
        backBtn.setBounds(10,15,50,50);
        backBtn.DisableBorderStyling();
        backBtn.setForeground(IGUIStyle.CustomColor.WHITE_NORMAL);
        backBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.OnBackArrow();
            }
        });

        // Item listing banner

        CustomJLabel itemListingBanner = new CustomJLabel(IGUIStyle.FontSize.HEADER2, "Item Listing", Font.BOLD);
        itemListingBanner.setBounds(0,70, WINDOW_MAX_WIDTH, 50);
        itemListingBanner.setHorizontalAlignment(SwingConstants.CENTER);
        itemListingBanner.setVerticalAlignment(SwingConstants.CENTER);
        itemListingBanner.setBackground(IGUIStyle.CustomColor.BLACK_MAIN);
        itemListingBanner.setOpaque(true);
        itemListingBanner.setForeground(IGUIStyle.CustomColor.WHITE_NORMAL);

        //Item List Container

        ArrayList<String[]> itemList = controller.GetItemList();
        int containerHeight = 638; //660 in accordance with JScrollPane height
        if (itemList != null)
        {
            int rows = (int) (Math.ceil(itemList.size() / 3.0f));
            int preferredHeight = rows * 255;
            System.out.println("Item List: " + itemList.size());
            System.out.println(rows);
            if (preferredHeight > containerHeight)
            {
                containerHeight = preferredHeight;
            }
        }

        CustomJPanel itemListingContainer = new CustomJPanel();
        itemListingContainer.setLayout(new FlowLayout(FlowLayout.LEFT, 30,30));
        itemListingContainer.setPreferredSize(new Dimension(585, containerHeight));

        if (itemList != null && itemList.size() > 0) {
            for (String[] item : itemList) {
                if (Integer.parseInt(item[2]) == 0)
                {
                    continue;
                }
                CustomJButton itemContainer = new CustomJButton();
                itemContainer.setPreferredSize(new Dimension(150,220));
                itemContainer.setBackground(CustomColor.WHITE_NORMAL);
                itemContainer.setBorder(new LineBorder(CustomColor.BLACK_MAIN));
                itemContainer.setLayout(null);

                CustomJLabel itemPicture = new CustomJLabel(item[3], new Dimension(110,110),
                        ImageType.ITEM_IMAGE, false);
                itemPicture.setBackground(CustomColor.WHITE_DARK_2);
                itemPicture.setBounds(20,10, 110,110);
                itemPicture.setOpaque(true);

                CustomJPanel itemLine = new CustomJPanel();
                itemLine.setBackground(CustomColor.BLACK_MAIN);
                itemLine.setBounds(10,130, 130, 1);

                CustomJLabel itemName = new CustomJLabel(FontSize.BODY,
                        "<html><p style='width:100px'>"+item[1]+"</p></html>", Font.PLAIN);
                itemName.setBounds(10, 135, 130, 55);

                CustomJLabel itemPrice = new CustomJLabel(FontSize.HEADER2, "RM"+ String.format("%.2f",
                        Float.parseFloat(item[4])), Font.PLAIN);
                itemPrice.setBounds(10, 195, 130, 25);
                itemPrice.setForeground(CustomColor.GREEN_ATTENTION);

                itemContainer.add(itemPicture);
                itemContainer.add(itemLine);
                itemContainer.add(itemName);
                itemContainer.add(itemPrice);

                itemListingContainer.add(itemContainer);
            }
        }

        JScrollPane itemScrollPane = new JScrollPane(itemListingContainer);
        itemScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        itemScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        itemScrollPane.setBounds(0,120, 585,641);

        //Add

        brandBannerParent.add(brandBannerTitle);


        add(itemScrollPane);

        add(backBtn);
        add(brandBannerParent);
        add(itemListingBanner);
        window.add(this);
    }
}

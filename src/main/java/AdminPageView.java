import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminPageView extends JPanel implements IGUIStyle {

    private JFrame window;
    private AdminPageController controller;

    public AdminPageView(AdminPageController controller, JFrame window) {
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

        CustomJButton backBtn = new CustomJButton("X");
        backBtn.setFont(new Font(FONT_TYPE, Font.PLAIN, 35));
        backBtn.setBounds(10,15,50,50);
        backBtn.DisableBorderStyling();
        backBtn.setForeground(CustomColor.WHITE_NORMAL);
        backBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.OnBackToHomepage();
            }
        });

        // Item category banner

        CustomJLabel itemCategoryBanner = new CustomJLabel(FontSize.HEADER2, "Items", Font.BOLD);
        itemCategoryBanner.setBounds(0,70, WINDOW_MAX_WIDTH, 50);
        itemCategoryBanner.setHorizontalAlignment(SwingConstants.CENTER);
        itemCategoryBanner.setVerticalAlignment(SwingConstants.CENTER);
        itemCategoryBanner.setBackground(CustomColor.BLACK_MAIN);
        itemCategoryBanner.setOpaque(true);
        itemCategoryBanner.setForeground(CustomColor.WHITE_NORMAL);

        //Middle Button Left

        CustomJButton newItemBtn = new CustomJButton();
        newItemBtn.setLayout(null);
        newItemBtn.setBorder(new LineBorder(CustomColor.BLUE_BRAND));
        newItemBtn.setBounds(100,150,150,180);
        newItemBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.OnAddNewItemPage();
            }
        });

        CustomJLabel newItemIcon = new CustomJLabel("PlusSign", new Dimension(75,75), ImageType.ICON, true);
        newItemIcon.setBounds(35,25,75,75);

        CustomJLabel newItemTitle = new CustomJLabel(FontSize.BODY,"Add New Item");
        newItemTitle.setLayout(null);
        newItemTitle.setBounds(30,130,100,50);
        newItemTitle.setForeground(CustomColor.BLUE_BRAND);

        //Middle Button Right

        CustomJButton itemListingBtn = new CustomJButton();
        itemListingBtn.setLayout(null);
        itemListingBtn.setBorder(new LineBorder(CustomColor.BLUE_BRAND));
        itemListingBtn.setBounds(320,150,150,180);
        itemListingBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.OnItemListingPage();
            }
        });

        CustomJLabel itemListingIcon = new CustomJLabel("ItemListing", new Dimension(75,75), ImageType.ITEM_IMAGE, true);
        itemListingIcon.setBounds(35,25,75,75);

        CustomJLabel itemListingTitle = new CustomJLabel(FontSize.BODY,"Item Listing");
        itemListingTitle.setLayout(null);
        itemListingTitle.setBounds(40,130,100,50);
        itemListingTitle.setForeground(CustomColor.BLUE_BRAND);

        // Item category banner

        CustomJLabel othersCategoryBanner = new CustomJLabel(FontSize.HEADER2, "Others", Font.BOLD);
        othersCategoryBanner.setBounds(0,400, WINDOW_MAX_WIDTH, 50);
        othersCategoryBanner.setHorizontalAlignment(SwingConstants.CENTER);
        othersCategoryBanner.setVerticalAlignment(SwingConstants.CENTER);
        othersCategoryBanner.setBackground(CustomColor.BLACK_MAIN);
        othersCategoryBanner.setOpaque(true);
        othersCategoryBanner.setForeground(CustomColor.WHITE_NORMAL);


        // Add components

        brandBannerParent.add(brandBannerTitle, BorderLayout.CENTER);
        newItemBtn.add(newItemIcon);
        newItemBtn.add(newItemTitle);
        itemListingBtn.add(itemListingIcon);
        itemListingBtn.add(itemListingTitle);

        add(backBtn);
        add(brandBannerParent);
        add(itemCategoryBanner);
        add(newItemBtn);
        add(itemListingBtn);
        add(othersCategoryBanner);

        window.add(this);
    }
}

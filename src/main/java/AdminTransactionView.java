import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class AdminTransactionView extends JPanel implements IGUIStyle {
    private JFrame window;
    private AdminTransactionController controller;

    public AdminTransactionView(AdminTransactionController controller, JFrame window) {
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

        // Transaction banner

        CustomJLabel transactionBanner = new CustomJLabel(FontSize.HEADER2, "Transaction History", Font.BOLD);
        transactionBanner.setBounds(0,70, WINDOW_MAX_WIDTH, 50);
        transactionBanner.setHorizontalAlignment(SwingConstants.CENTER);
        transactionBanner.setVerticalAlignment(SwingConstants.CENTER);
        transactionBanner.setBackground(CustomColor.BLACK_MAIN);
        transactionBanner.setOpaque(true);
        transactionBanner.setForeground(CustomColor.WHITE_NORMAL);


        //Transaction listing
        ArrayList<String[]> transactionList = controller.GetTransactionList();
        if (transactionList != null) {
            Collections.reverse(transactionList);
        }
        int containerHeight = 575; //575 in accordance with JScrollPane height
        if (transactionList != null)
        {
            int preferredHeight = transactionList.size() * 55;
            if (preferredHeight > containerHeight)
            {
                containerHeight = preferredHeight;
            }
        }

        CustomJPanel transactionListingContainer = new CustomJPanel();
        transactionListingContainer.setLayout(new FlowLayout());
        transactionListingContainer.setPreferredSize(new Dimension(500, containerHeight));

        //Inserting transaction record into listing container
        if (transactionList != null && transactionList.size() > 0)
        {
            for (String[] transactionRecord: transactionList)
            {
                // TO DO: Null the name of the item i guess

                //Date: HH-mm-MM-dd-yyyy
                String[] dateTime = transactionRecord[2].split("-");
                String[] itemValues = Database.TextFileGetByID(Database.FileType.ITEM, transactionRecord[1]);
                Item item = new Item(itemValues);

                CustomJPanel transactionRecordContainer = new CustomJPanel();
                transactionRecordContainer.setBackground(CustomColor.WHITE_NORMAL);
                transactionRecordContainer.setPreferredSize(new Dimension(500, 50));

                CustomJLabel transactionTime = new CustomJLabel(FontSize.BODY, dateTime[0] + ":" + dateTime[1]);
                transactionTime.setBackground(CustomColor.WHITE_NORMAL);
                transactionTime.setBounds(10,5,50,50);
                transactionTime.setHorizontalAlignment(SwingConstants.CENTER);

                CustomJLabel transactionDate = new CustomJLabel(FontSize.BODY, dateTime[2] + "/" + dateTime[3] + "/" + dateTime[4]);
                transactionDate.setBackground(CustomColor.WHITE_NORMAL);
                transactionDate.setBounds(60,5,100,50);
                transactionDate.setHorizontalAlignment(SwingConstants.CENTER);

                CustomJLabel itemName = new CustomJLabel(FontSize.BODY, item.getItemName());
                itemName.setBackground(CustomColor.WHITE_NORMAL);
                itemName.setBounds(165,5,210,50);

                CustomJLabel itemPrice = new CustomJLabel(FontSize.BODY, "RM "+String.valueOf(item.getPrice()));
                itemPrice.setBackground(CustomColor.WHITE_NORMAL);
                itemPrice.setBounds(380,5,100,50);
                itemPrice.setHorizontalAlignment(SwingConstants.CENTER);

                transactionRecordContainer.add(transactionTime);
                transactionRecordContainer.add(transactionDate);
                transactionRecordContainer.add(itemName);
                transactionRecordContainer.add(itemPrice);
                transactionListingContainer.add(transactionRecordContainer);

            }
        }


        JScrollPane itemScrollPane = new JScrollPane(transactionListingContainer);
        itemScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        itemScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        itemScrollPane.setBounds(50,150, 500,575);



        //Add Item
        brandBannerParent.add(brandBannerTitle, BorderLayout.CENTER);



        add(itemScrollPane);
        add(backBtn);
        add(brandBannerParent);
        add(transactionBanner);
        window.add(this);
    }

}

import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;

/**
 * Changes the default font and color
 * Allows for resizing of icons
 */
public class CustomJLabel extends JLabel {

    private String defaultFont ="Calibri";

    public CustomJLabel(String title)
    {
        setLayout(null);
        setText(title);
        Font font = new Font(defaultFont, Font.PLAIN, getFont().getSize());
        setFont(font);
        setForeground(Color.BLACK);
    }

    public CustomJLabel(int size, String title)
    {
        setLayout(null);
        setText(title);
        Font font = new Font(defaultFont, Font.PLAIN, size);
        setFont(font);
        setForeground(Color.BLACK);
    }
    public CustomJLabel(int size, String title, int fontStyle)
    {
        setLayout(null);
        setText(title);
        Font font = new Font(defaultFont, fontStyle, size);
        setFont(font);
        setForeground(Color.BLACK);
    }

    public CustomJLabel(String title, Font font)
    {
        setLayout(null);
        setText(title);
        setFont(font);
        setForeground(Color.BLACK);
    }

    public CustomJLabel(String title, String fontName)
    {
        setLayout(null);
        setText(title);
        Font font = new Font(fontName, Font.PLAIN, getFont().getSize());
        setFont(font);
        setForeground(Color.BLACK);
    }


    public CustomJLabel(String imageName, Dimension targetSize, ImageType imageType, boolean haveBorderStyling)
    {
        Icon icon = ResizeImageUtility.ResizeImageUtility(imageName,targetSize, imageType);

        setText(null);
        setIcon((Icon) icon);
        setHorizontalAlignment(CENTER);
        updateUI();
        setAlignmentX(LEFT_ALIGNMENT);
        if (!haveBorderStyling)
            setBorder(BorderFactory.createEmptyBorder());
    }
}


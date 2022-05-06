import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;

/**
 * Allows resizing of icons in JButton
 */
public class CustomJButton extends JButton {

    private String defaultFont ="Calibri";


    public CustomJButton() {
        Font font = new Font(defaultFont, Font.PLAIN, getFont().getSize());
        setFont(font);
        setForeground(Color.BLACK);
    }

    public CustomJButton(String text) {
        setLayout(null);
        setText(text);
        Font font = new Font(defaultFont, Font.PLAIN, getFont().getSize());
        setFont(font);
        setForeground(Color.BLACK);
    }
    public CustomJButton(int size, String text, int fontStyle) {

        setLayout(null);
        setText(text);
        Font font = new Font(defaultFont, fontStyle, size);
        setFont(font);
        setForeground(Color.BLACK);
    }


    public CustomJButton(String imageName, Dimension targetSize, ImageType imageType, boolean haveBorderStyling)
    {
        Icon icon = ResizeImageUtility.ResizeImageUtility(imageName,targetSize, imageType);

        setModel(new DefaultButtonModel());
        init(null, (Icon) icon);

        // Remove button styling
        setFocusPainted(false);
        if (!haveBorderStyling)
        {
            setBorder(BorderFactory.createEmptyBorder());
            setContentAreaFilled(false);
            setBorderPainted(false);
        }
    }

    public void DisableBorderStyling()
    {
        setBorder(BorderFactory.createEmptyBorder());
        setContentAreaFilled(false);
        setBorderPainted(false);
    }

}

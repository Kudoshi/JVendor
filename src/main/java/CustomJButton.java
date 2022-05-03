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

    public CustomJButton(Icon icon) {
        super(icon);
    }

    public CustomJButton(String text) {
        setLayout(null);
        setText(text);
        Font font = new Font(defaultFont, Font.PLAIN, getFont().getSize());
        setFont(font);
        setForeground(Color.BLACK);
    }


    public CustomJButton(String text, Icon icon) {
        super(text, icon);
        Font font = new Font(defaultFont, Font.PLAIN, getFont().getSize());
        setFont(font);
        setForeground(Color.BLACK);
    }

    public CustomJButton(String imageName, Dimension targetSize, ImageType imageType, boolean haveBorderStyling)
    {
        BufferedImage resizedIcon = null;
        URI filePath;
        if (imageType.equals(ImageType.ICON))
        {
            filePath = Database.RetrieveIconPathURI(imageName);
        }
        else
        {
            filePath = Database.RetrieveItemImgURI(imageName);
        }

        // Resize image
        try {
            BufferedImage oriImg = ImageIO.read(new File(filePath));
            resizedIcon = Thumbnails.of(oriImg).size(targetSize.width,targetSize.height).asBufferedImage();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create button
        ImageIcon imgIcon =  new ImageIcon(resizedIcon);
        setModel(new DefaultButtonModel());
        init(null, (Icon) imgIcon);

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

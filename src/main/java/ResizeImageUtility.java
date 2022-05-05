import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;

public class ResizeImageUtility {

    public static Icon ResizeImageUtility(String imageName, Dimension targetSize, ImageType imageType) {
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
            resizedIcon = Thumbnails.of(oriImg).size(targetSize.width,targetSize.height).keepAspectRatio(false).asBufferedImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageIcon imgIcon =  new ImageIcon(resizedIcon);
        return (Icon) imgIcon;
    }
}

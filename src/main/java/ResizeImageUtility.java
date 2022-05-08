import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Paths;

public class ResizeImageUtility {

    public static Icon ResizeImageUtility(String imageName, Dimension targetSize, ImageType imageType) {
        BufferedImage resizedIcon = null;
        URI filePath = null;
        if (imageType.equals(ImageType.ICON))
        {
            filePath = Database.RetrieveIconPathURI(imageName);
        }
        else if (imageType.equals(ImageType.ITEM_IMAGE))
        {
            filePath = Database.RetrieveItemImgURI(imageName);
        }
        else
        {
            filePath = Paths.get(imageName).toUri();
        }
        // Resize image
        try {
            BufferedImage oriImg = ImageIO.read(filePath.toURL());
            resizedIcon = Thumbnails.of(oriImg).size(targetSize.width,targetSize.height).keepAspectRatio(false).asBufferedImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageIcon imgIcon =  new ImageIcon(resizedIcon);
        return (Icon) imgIcon;
    }
}

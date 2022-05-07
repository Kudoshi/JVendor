import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class GifPanel extends JLabel {

    Image image;

    public GifPanel(String gifName) {
        String filePath = String.valueOf(Database.RetrieveGifURI(gifName));

        URL url = null;
        try {
            url = Database.RetrieveGifURI(gifName).toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Icon icon = new ImageIcon(url);


        setText(null);
        setIcon(icon);
        setHorizontalAlignment(CENTER);
        updateUI();
        setAlignmentX(LEFT_ALIGNMENT);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, this);
        }
    }
}

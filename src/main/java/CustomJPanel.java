import javax.swing.*;
import java.awt.*;

public class CustomJPanel extends JPanel {

    private boolean hasRoundedBorder = false;
    private Dimension arcDimension;
    private Color fillColor = Color.WHITE;
    private Color borderColor = Color.BLACK;

    /**
     * For JPanel with rounded borders
     * @param radius Curviness radius of the corners
     * @param fillColor Inside color of the JPanel
     * @param borderColor Border color of the JPanel
     */
    public CustomJPanel(int radius, Color fillColor, Color borderColor)
    {
        this.arcDimension = new Dimension(radius, radius);
        this.fillColor = fillColor;
        this.borderColor = borderColor;
        hasRoundedBorder = true;
        setOpaque(false);
    }

    public CustomJPanel()
    {
        setLayout(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (hasRoundedBorder)
        {
            int width = getWidth();
            int height = getHeight();
            Graphics2D graphics = (Graphics2D) g;
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


            //Draws the rounded panel with borders.
            graphics.setColor(fillColor);
            graphics.fillRoundRect(0, 0, width-1, height-1, arcDimension.width, arcDimension.height);//paint background
            graphics.setColor(getForeground());
            g.setColor(borderColor);
            graphics.drawRoundRect(0, 0, width-1, height-1, arcDimension.width, arcDimension.height);//paint border
        }
    }

}

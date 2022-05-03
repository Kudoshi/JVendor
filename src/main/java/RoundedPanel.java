import javax.swing.*;
import java.awt.*;


/** Creates a rounded JPanel
 *
 */
public class RoundedPanel extends JPanel {
    private Dimension arcDimension;
    private Color fillColor = Color.WHITE;
    private Color borderColor = Color.BLACK;

    //Constructor

    public RoundedPanel (int width, int height)
    {
        this.arcDimension = new Dimension(width, height);
    }

    public RoundedPanel(int radius)
    {
        this.arcDimension = new Dimension(radius, radius);
    }

    public RoundedPanel(int radius, Color fillColor, Color borderColor)
    {
        this.arcDimension = new Dimension(radius, radius);
        this.fillColor = fillColor;
        this.borderColor = borderColor;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
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



    //Getters and setters
    public Dimension getArcDimension() {
        return arcDimension;
    }

    public void setArcDimension(Dimension arcDimension) {
        this.arcDimension = arcDimension;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }
    
}

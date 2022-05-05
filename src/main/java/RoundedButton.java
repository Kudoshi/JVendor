import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class RoundedButton extends JButton {
    private Dimension arcDimension;
    private Color fillColor = Color.WHITE;
    private Color borderColor = Color.BLACK;

    //Constructor
    public RoundedButton(int radius, Color fillColor, Color borderColor)
    {
        this.arcDimension = new Dimension(radius, radius);
        this.fillColor = fillColor;
        this.borderColor = borderColor;
        setOpaque(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
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


}

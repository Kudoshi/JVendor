import javax.swing.border.Border;
import java.awt.*;

public class RoundedBorder implements Border {
    private int borderRadius;
    private boolean isOpaque;

    public RoundedBorder(int borderRadius, boolean isOpaque)
    {
        this.borderRadius = borderRadius;
        this.isOpaque = isOpaque;
    }

    public RoundedBorder(int borderRadius)
    {
        this.borderRadius = borderRadius;
        this.isOpaque = false;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.drawRoundRect(x,y,width-1,height-1, borderRadius, borderRadius);
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(this.borderRadius+1,this.borderRadius+1,this.borderRadius+2, this.borderRadius);
    }

    @Override
    public boolean isBorderOpaque() {
        return this.isOpaque;
    }
}

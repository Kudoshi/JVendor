import java.awt.*;

public interface IGUIStyle {
    int WINDOW_MAX_HEIGHT = 800;
    int WINDOW_MAX_WIDTH = 600;
    int ROUNDED_BORDER_RADIUS = 60;
    String FONT_TYPE = "Calibri";

    public class CustomColor
    {
        public static final Color BLUE_BRAND = new Color(35, 116, 171);
        public static final Color BLACK_MAIN = new Color(34, 38, 41);
        public static final Color GREEN_ATTENTION = new Color(134, 194, 50);
        public static final Color PINK_ACCENT = new Color(255, 132, 132);
        public static final Color BLACK_HIDE = new Color(153, 153, 153);
        public static final Color WHITE_NORMAL = Color.white;
        public static final Color WHITE_DARK_1 = new Color(246, 246, 246);
        public static final Color WHITE_DARK_2 = new Color(196, 196, 196);
    }

    public class FontSize
    {
        public static final int HEADER1 = 34;
        public static final int HEADER2 = 20;
        public static final int BODY = 15;
        public static final int HIDE = 12;
    }
}

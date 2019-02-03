package grapher;

import java.awt.Color;

public interface NumberToColorConverter {
    Color getColor(double number);
    double getLowest();
    double getHighest();
    Color getLowestColor();
    Color getHighestColor();
}

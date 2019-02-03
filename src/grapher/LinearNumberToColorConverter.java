package grapher;

import java.awt.Color;

public class LinearNumberToColorConverter implements NumberToColorConverter{

    private double lowest;
    private double highest;
    
    private final Color TRANSPARANT = new Color(0,0,0,0);
    private final Color LOWEST_COLOR = Color.BLACK;
    private final Color HIGHEST_COLOR = Color.getHSBColor(0.85f, 1.0f, 1.0f);
    
    public LinearNumberToColorConverter(double lowest, double highest){
        this.lowest = lowest;
        this.highest = highest;
    }
    
    @Override
    public Color getColor(double number) {
        if(number<lowest){
            return TRANSPARANT;
        }else if(number>highest){
            return TRANSPARANT;
        }else{
            float hue = (float) ((number-lowest)/(highest-lowest));//hue is currently percent through range
            hue-=.15;//so bright purple is the highest color, not almost red
            if(hue<0.0f){
                return Color.getHSBColor(0.0f, 1.0f, 1-hue/-0.15f);//instead of the more purple part of the spectrum, put red, but make darker
            }else{
                return Color.getHSBColor(hue, 1.0f, 1.0f);
            }
        }
    }

    @Override
    public double getLowest() {
        return lowest;
    }

    @Override
    public double getHighest() {
        return highest;
    }

    @Override
    public Color getLowestColor() {
        return LOWEST_COLOR;
    }

    @Override
    public Color getHighestColor() {
        return HIGHEST_COLOR;
    }

}

package grapher;

import java.awt.Color;

import world.WorldPainter;

public class AnswerDisplay {
    
    private static final double BOX_SHIFT_X = 3;
    private static final double BOX_SHIFT_Y = 3;
    private static final double TEXT_SHIFT_X = 6;
    private static final double TEXT_SHIFT_Y = 9;
    
    private double mouseX;//world coordinates of mouse cursor. these are saved for the function world object's draw method
    private double mouseY;//world coordinates of mouse cursor
    private boolean enabled;
    private String contentString;
    private double displayBoxWidth;//in pixles
    private double displayBoxHeight;//in pixles
    private Color color;
    
    public AnswerDisplay(){
        this.mouseX=0;
        this.mouseY=0;
        this.enabled = false;
        this.contentString = "";
        this.displayBoxHeight = 20;
        this.color = Color.BLACK;
    }

    public void enable() {
        this.enabled = true;
    }

    public void disable() {
        this.enabled = false;
    }

    public void setMouseX(double x) {
        this.mouseX = x;
    }

    public void setMouseY(double y) {
        this.mouseY = y;
    }
    
    public double getMouseX(){
        return mouseX;
    }
    
    public double getMouseY(){
        return mouseY;
    }
    
    public void setContentString(String newString){
        this.contentString = newString;
        this.displayBoxWidth = getLengthOfStringInPixles(contentString);
    }
    
    public void setColor(Color color2) {
        this.color = color2;
    }

    private static double getLengthOfStringInPixles(String string) {
        return string.length()*10;
    }

    public void draw(WorldPainter wp) {
        if(enabled){
            wp.setColor(color);
            wp.fillRectangle(mouseX+wp.getZoom()*BOX_SHIFT_X, mouseY+wp.getZoom()*BOX_SHIFT_Y,displayBoxWidth*wp.getZoom(), displayBoxHeight*wp.getZoom());
            wp.setColor(getTextColorFromBackgroundColor(color));
            wp.drawText(contentString, mouseX+wp.getZoom()*TEXT_SHIFT_X, mouseY+wp.getZoom()*TEXT_SHIFT_Y, 12, WorldPainter.ReletiveThickness.PIXLE);
        }
        
    }

    //from stack overflow        https://stackoverflow.com/questions/3942878/how-to-decide-font-color-in-white-or-black-depending-on-background-color
    private static Color getTextColorFromBackgroundColor(Color backgroundColor) {
        int red = backgroundColor.getRed();
        int green = backgroundColor.getGreen();
        int blue = backgroundColor.getBlue();
        if(red*0.299 + green*0.587 + blue*0.114 > 186){
            return Color.BLACK;
        }else{
            return Color.WHITE;
        }
    }

    public Color getColor() {
        return color;
    }

    

}

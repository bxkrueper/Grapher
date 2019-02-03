package grapher;

import java.awt.Color;

import world.WorldPainter;
import world.WorldPainter.ReletiveThickness;


public class LineDrawer {
    
    private double prevX;
    private double prevY;
    private Color color;
    private int stroke;
    
    public LineDrawer(Color color, int stroke){
        this.color = color;
        this.stroke = stroke;
        this.prevX = Double.NaN;
        this.prevY = Double.NaN;
    }
    
    public void setColor(Color color){
        this.color = color;
    }
    
    public void setStroke(int stroke){
        this.stroke = stroke;
    }
    
    //call this first before using. clears the previous coordinates so it doesn't draw from them
    public void getReadyToDraw(WorldPainter wp){
        wp.setColor(color);
        wp.setStroke(stroke, ReletiveThickness.PIXLE);
        this.prevX = Double.NaN;
        this.prevY = Double.NaN;
    }
    
    public void setWorldPainterColor(WorldPainter wp){
        wp.setColor(color);
    }
    
    public void setWorldPainterStroke(WorldPainter wp){
        wp.setStroke(stroke, ReletiveThickness.PIXLE);
    }
    
    public void drawTo(double x, double y, WorldPainter wp) {
        if(Double.isFinite(prevX) && Double.isFinite(prevY) && Double.isFinite(x) && Double.isFinite(y)){
            wp.drawLine(prevX, prevY,x,y);
        }
        prevX = x;
        prevY = y;
    }
}

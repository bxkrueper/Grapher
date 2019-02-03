package grapher;

import java.awt.Color;

import function.Constant;
import function.Substitution;
import function.Undefined;
import world.WorldPainter;
import worldFunctionality.WorldDrawable;

public class NullFunctionWorldObject extends FunctionWorldObject implements WorldDrawable{
    
    private static NullFunctionWorldObject instance;
    
    private NullFunctionWorldObject(){
        super();
        this.setSelected(false);
    }
    
    public static NullFunctionWorldObject getInstance(){
        if(instance == null){
            instance = new NullFunctionWorldObject();
        }
        return instance;
    }
    
    @Override
    public void setColor(Color color) {
    }

    @Override
    public void draw(WorldPainter wp) {
    }
    
    public boolean isGoodForGraphing() {
        return false;
    }
    
    @Override
    public void reactToMouseMoved(double x, double y) {
    }

    @Override
    public Constant getMouseAnwer(double x, double y) {
        return Undefined.getInstance();
    }

    @Override
    public void setSubstitution(Substitution sub) {
        
    }
    

}

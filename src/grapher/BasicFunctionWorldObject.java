package grapher;

import java.awt.Color;
import java.util.List;
import java.util.Set;

import function.Constant;
import function.Function;
import function.RealNumber;
import function.Substitution;
import function.Undefined;
import function.Variable;
import myAlgs.MyMath;
import world.WorldPainter;
import worldFunctionality.WorldDrawable;


//y=f(x)
public class BasicFunctionWorldObject extends FunctionWorldObject implements WorldDrawable{

    private Function function;
    private Variable x;
    private LineDrawer lineDrawer;
    
    
    public BasicFunctionWorldObject(Function function, Color color){
        this.function = function;
        function.simplify();
        this.x = new Variable('x');
        function.setVariable(x,Undefined.getInstance());
        this.lineDrawer = new LineDrawer(color,2);
        getAnswerDisplay().setColor(color);
    }
    
    public BasicFunctionWorldObject(Function function){
        this(function,Color.BLUE);
    }
    
    @Override
    public void setColor(Color color) {
        this.lineDrawer.setColor(color);
        getAnswerDisplay().setColor(color);
    }

    @Override
    public void draw(WorldPainter wp) {
        double xEnd = wp.getRightCoordinate();
        double zoom = wp.getZoom();
        lineDrawer.getReadyToDraw(wp);
        
        for(double xValue = wp.getLeftCoordinate();xValue<=xEnd;xValue+=zoom){
            function.setVariable(x,xValue);
            double yValue = getYValueDouble();
            lineDrawer.drawTo(xValue, yValue, wp);
        }
        
        if(isSelected()){
            drawLineToMouse(wp);
            getAnswerDisplay().draw(wp);
        }
        
    }
    
    private void drawLineToMouse(WorldPainter wp) {
        wp.setColor(getAnswerDisplay().getColor());
        wp.drawLine(getAnswerDisplay().getMouseX(), getAnswerDisplay().getMouseY(),getAnswerDisplay().getMouseX(),((RealNumber) getMouseHoverAnswer()).getValue());
    }
    //uses the current value stored for x
    //returns a new Real Number with NaN if not a valid number
    private RealNumber getYValueRealNumber() {
        Constant potentialRealNumber =  function.evaluate().convertToInstanceOf(RealNumber.additiveIdentity);
        if(potentialRealNumber instanceof RealNumber){
            return (RealNumber) potentialRealNumber;
        }else{
            return new RealNumber(Double.NaN);
        }
    }
    private double getYValueDouble() {
        return getYValueRealNumber().getValue();
    }
    
    public boolean isGoodForGraphing() {
        return function.variablesComputable();
    }
    
    
    
    
    @Override
    public Constant getMouseAnwer(double x, double y) {
        function.setVariable(this.x,x);
        return getYValueRealNumber();
    }
    
    //x and y may be off slightly
    @Override
    public void reactToMouseMoved(double x, double y) {
        getAnswerDisplay().setMouseX(x);
        getAnswerDisplay().setMouseY(y);
        setMouseHoverAnswer(getMouseAnwer(x,y));
        getAnswerDisplay().setContentString("f(" + MyMath.toNiceString(x,5) + ")= " + getMouseHoverAnswer().toDisplayString());
        this.repaintWorld();
    }
    

    @Override
    public String toString() {
        return "BasicFunctionWorldObject [function=" + function + ", x=" + x + ", lineDrawer=" + lineDrawer + "]";
    }

    @Override
    public void setSubstitution(Substitution sub) {
        function.setSubstitution(sub);
    }

    
    
    

}

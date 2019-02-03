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

//z=f(x,y)
public class ColorFunctionWorldObject extends FunctionWorldObject implements WorldDrawable{

    private Function function;
    private NumberToColorConverter colorConverter;
    private Variable x;
    private Variable y;
    
    public ColorFunctionWorldObject(Function function, NumberToColorConverter colorConverter){
        this.function = function;
        this.function.simplify();
        this.colorConverter = colorConverter;
        this.x = new Variable('x');
        this.y = new Variable('y');
        function.setVariable(x,Undefined.getInstance());
        function.setVariable(y,Undefined.getInstance());
    }
    
    @Override
    public void setSubstitution(Substitution sub) {
        function.setSubstitution(sub);
    }
    
    @Override
    public void doOnAdd() {
        sendToBack();
    }
    
    ///////////dead method
    @Override
    public void setColor(Color color) {
    }

    @Override
    public void draw(WorldPainter wp) {
        double zoom = wp.getZoom();
        
        double xValueEnd = wp.panelXToWorldX(wp.getPanelWidth());
        double yValueStart = wp.panelYToWorldY(wp.getPanelHeight());
        double yValueEnd = wp.panelYToWorldY(0);
        
        for(double xValue = wp.panelXToWorldX(0);xValue<=xValueEnd;xValue+=zoom){
            
            for(double yValue = yValueStart;yValue<=yValueEnd;yValue+=zoom){
                function.setVariable(x,xValue);
                function.setVariable(y,yValue);
                double zValue = getZValueDouble();
                Color color = colorConverter.getColor(zValue);
                wp.setColor(color);
                wp.drawPixleContainingWorldCoordinate(xValue, yValue);
            }
        }
        
        if(isSelected()){
            getAnswerDisplay().draw(wp);
        }
    }
    
    private RealNumber getZValueRealNumber() {
        Constant potentialRealNumber =  function.evaluate().convertToInstanceOf(RealNumber.additiveIdentity);
        if(potentialRealNumber instanceof RealNumber){
            return (RealNumber) potentialRealNumber;
        }else{
            return new RealNumber(Double.NaN);
        }
    }
    private double getZValueDouble() {
        return getZValueRealNumber().getValue();
    }
    
    /////same as BasicFunctionWorldObject's method
    public boolean isGoodForGraphing() {
        Set<Variable> expressionSet = function.getVariablesInExpression();
        List<Variable> argList = function.getVariablesInFunctionArgs();
        for(Variable v:expressionSet){
            if(!argList.contains(v) && !(function.getVariableValue(v) instanceof Constant)){
                return false;
            }
        }
        return true;
    }
    

    @Override
    public Constant getMouseAnwer(double x, double y) {
        function.setVariable(this.x,x);
        function.setVariable(this.y,y);
        return getZValueRealNumber();
    }
    
    @Override
    public void reactToMouseMoved(double x, double y) {
        getAnswerDisplay().setMouseX(x);
        getAnswerDisplay().setMouseY(y);
        RealNumber answer = (RealNumber) getMouseAnwer(x,y);
        setMouseHoverAnswer(answer);
        double answerDouble = answer.getValue();
        Color backgroundColor = colorConverter.getColor(answerDouble);
        if(answerDouble<colorConverter.getLowest()){
            backgroundColor = colorConverter.getLowestColor();
        }else if(answerDouble>colorConverter.getHighest()){
            backgroundColor = colorConverter.getHighestColor();
        }
        getAnswerDisplay().setColor(backgroundColor);
        getAnswerDisplay().setContentString("f(" + MyMath.toNiceString(x,5) + "," + MyMath.toNiceString(y,5) + ")= " + getMouseHoverAnswer().toDisplayString());
        this.repaintWorld();
    }

}

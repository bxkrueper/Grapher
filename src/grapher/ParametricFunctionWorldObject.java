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
import function.Vector;
import myAlgs.MyMath;
import world.WorldPainter;
import worldFunctionality.WorldDrawable;


//x=f(t)
//y=g(t)
public class ParametricFunctionWorldObject extends FunctionWorldObject implements WorldDrawable{

  private Function xFunction;
  private Function yFunction;
  private Variable t;
  private LineDrawer lineDrawer;
  
  private double start;
  private double stop;
  private double step;
  
  private double mouseT;
  
  //stores Vectors in mouseHoverAnswer
  ///////todo: fix stop not being calculated because of adding errors
  public ParametricFunctionWorldObject(Function xFunction,Function yFunction,double start,double stop,double numSteps, Color color){
      this.xFunction = xFunction;
      this.xFunction.simplify();
      this.yFunction = yFunction;
      this.yFunction.simplify();
      
      this.t = new Variable('t');
      xFunction.setVariable(t,Undefined.getInstance());
      yFunction.setVariable(t,Undefined.getInstance());
      
      this.start = start;
      this.stop = stop;
      this.step = (stop-start)/numSteps;
      
      this.mouseT = 0.0;
      
      this.lineDrawer = new LineDrawer(color,2);
      getAnswerDisplay().setColor(color);
      
      setMouseHoverAnswer(new Vector(RealNumber.additiveIdentity,RealNumber.additiveIdentity));
  }
  @Override
  public void setSubstitution(Substitution sub) {
      xFunction.setSubstitution(sub);
      yFunction.setSubstitution(sub);
  }
  public ParametricFunctionWorldObject(Function xFunction,Function yFunction,double start,double stop,double numSteps){
      this(xFunction,yFunction,start,stop,numSteps,Color.BLUE);
  }
  
  @Override
  public void setColor(Color color) {
      this.lineDrawer.setColor(color);
      getAnswerDisplay().setColor(color);
  }

  @Override
  public void draw(WorldPainter wp) {
      double zoom = wp.getZoom();
      
      lineDrawer.getReadyToDraw(wp);
      for(double tValue=start;tValue<=stop;tValue+=step){
          xFunction.setVariable(t,tValue);
          double xValue = getXValue();
          yFunction.setVariable(t,tValue);
          double yValue = getYValue();
          lineDrawer.drawTo(xValue, yValue, wp);
      }
      
      if(isSelected()){
          drawLineFromMouseToTAnswer(wp);
          getAnswerDisplay().draw(wp);
      }
  }
  
  private void drawLineFromMouseToTAnswer(WorldPainter wp) {
      wp.setColor(getAnswerDisplay().getColor());
      wp.drawLine(getAnswerDisplay().getMouseX(), getAnswerDisplay().getMouseY(),getMouseTAnswerX(),getMouseTAnswerY());
  }
  //uses the current value stored for x and y
  //returns NaN if not a valid number
  private double getXValue() {
      Constant cValue = xFunction.evaluate();
      if(cValue.convertToInstanceOf(RealNumber.additiveIdentity)==Undefined.getInstance()){
          return Double.NaN;
      }else{
          
          return ((RealNumber) cValue).getValue();
      }
  }
  
  //uses the current value stored for x and y
  //returns NaN if not a valid number
  private double getYValue() {
      Constant cValue = yFunction.evaluate();
      if(cValue.convertToInstanceOf(RealNumber.additiveIdentity)==Undefined.getInstance()){
          return Double.NaN;
      }else{
          
          return ((RealNumber) cValue).getValue();
      }
  }
  
    public boolean isGoodForGraphing() {
        Set<Variable> xExpressionSet = xFunction.getVariablesInExpression();
        List<Variable> xArgList = xFunction.getVariablesInFunctionArgs();
        for(Variable v:xExpressionSet){
            if(!xArgList.contains(v) && !(xFunction.getVariableValue(v) instanceof Constant)){
                return false;
            }
        }
        Set<Variable> yExpressionSet = yFunction.getVariablesInExpression();
        List<Variable> yArgList = yFunction.getVariablesInFunctionArgs();
        for(Variable v:yExpressionSet){
            if(!yArgList.contains(v) && !(yFunction.getVariableValue(v) instanceof Constant)){
                return false;
            }
        }
        return true;
    }
    @Override
    public Constant getMouseAnwer(double x, double y) {
        double[] tArray = getTClosestTo(x,y);
        this.mouseT = tArray[0];
        return new Vector(new RealNumber(tArray[1]),new RealNumber(tArray[2]));
    }
    
    //returns a double array [tval that is closest to answer,x of that t, y of that t]
    private double[] getTClosestTo(double x, double y){
        double closestT = start;
        double closestTAnswerX = 0;
        double closestTAnswerY = 0;
        double closestDistance = Double.POSITIVE_INFINITY;
        for(double tValue=start;tValue<=stop;tValue+=step){
            xFunction.setVariable(t,tValue);
            double xValue = getXValue();
            yFunction.setVariable(t,tValue);
            double yValue = getYValue();
            double distance = Math.hypot(xValue-x, yValue-y);
            if(distance<closestDistance){
                closestT = tValue;
                closestDistance = distance;
                closestTAnswerX = xValue;
                closestTAnswerY = yValue;
            }
        }
        return new double[]{closestT,closestTAnswerX,closestTAnswerY};
    }
    
    @Override
    public void reactToMouseMoved(double x, double y) {
        getAnswerDisplay().setMouseX(x);
        getAnswerDisplay().setMouseY(y);
        setMouseHoverAnswer(getMouseAnwer(x,y));
        getAnswerDisplay().setContentString("f(" + MyMath.toNiceString(mouseT,5) + ")= " + getMouseHoverAnswer());
        this.repaintWorld();
    }
    
    public double getMouseTAnswerX(){
        Vector constantList = (Vector) this.getMouseHoverAnswer();
        return ((RealNumber) constantList.get(0)).getValue();
    }
    public double getMouseTAnswerY(){
        Vector constantList = (Vector) this.getMouseHoverAnswer();
        return ((RealNumber) constantList.get(1)).getValue();
    }
    
  

}


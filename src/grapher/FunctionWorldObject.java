package grapher;

import java.awt.Color;
import java.util.List;
import java.util.Set;

import coordinate.Coordinate;
import function.Constant;
import function.RealNumber;
import function.Substitution;
import world.WorldObject;
import worldFunctionality.MouseButtonReact.ButtonPressType;
import worldFunctionality.MouseButtonReact.ButtonType;
import worldFunctionality.MouseMovedReact;
import worldFunctionality.MouseScrolledOnReact;


public abstract class FunctionWorldObject extends WorldObject implements MouseMovedReact, MouseScrolledOnReact{
    
    private boolean selected;
    private AnswerDisplay answerDisplay;
    private Constant mouseHoverAnswer;
    
    public FunctionWorldObject(){
        this.selected = false;
        this.answerDisplay = new AnswerDisplay();
        this.mouseHoverAnswer = RealNumber.additiveIdentity;
    }

    public AnswerDisplay getAnswerDisplay() {
        return answerDisplay;
    }

    public void setAnswerDisplay(AnswerDisplay answerDisplay) {
        this.answerDisplay = answerDisplay;
    }

    public Constant getMouseHoverAnswer() {
        return mouseHoverAnswer;
    }

    public void setMouseHoverAnswer(Constant mouseHoverAnswer) {
        this.mouseHoverAnswer = mouseHoverAnswer;
    }

    public void setSelected(boolean selected){
        this.selected = selected;
    }
    
    public boolean isSelected(){
        return selected;
    }
    
    public void enable() {
        answerDisplay.enable();
    }
    public void disable() {
        answerDisplay.disable();
    }
    
    
    
    
    public abstract Constant getMouseAnwer(double mouseX, double mouseY);

    @Override
    public void reactToMouseEntered(double x, double y) {
        enable();
    }
    
    @Override
    public void reactToMouseExited(double x, double y) {
        disable();
        this.repaintWorld();
    }
    
    @Override
    public void reactToMouseScrolledOn(ButtonPressType bpt, double x, double y) {
        reactToMouseMoved(x,y);////////setting display to previous coordinates
    }
    @Override
    public boolean acceptTarget(ButtonType bt) {
        return true;
    }
    @Override
    public boolean consumeTargetable(ButtonType bt) {
        return false;
    }
    @Override
    public Coordinate getPosition() {
        return null;
    }
    @Override
    public boolean occupies(double x, double y) {
        return true;
    }
    
    @Override
    public void doOnAdd() {
    }

    @Override
    public void doOnDelete() {
    }
    
    public abstract boolean isGoodForGraphing();

    public abstract void setColor(Color color);

    public abstract void setSubstitution(Substitution sub);
}

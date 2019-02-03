package function;

import java.util.List;

public abstract class Expression {
    //make sure all expressions work with java's object methods by calling a separate method
    @Override
    public final String toString(){
        return toDisplayString();
    }
    public abstract String toDisplayString();
    public abstract String toDebugString();
    
    @Override
    public final boolean equals(Object o){
        return isEqualTo(o);
    }
    public abstract boolean isEqualTo(Object o);
    
    public abstract Expression copy();
    
    
    
    //returns a simplified version of what the expression represents (ex: 0+2 simplifies to 2)
    //may just return itself
    //normal use: ex = ex.simplify()
    public abstract Expression simplify();
    
    
    
    //return true if it can be evaluated with no variable substitutions
    //variable overrides this to return false;
    public abstract boolean computable();
    
    //normal use: ex = ex.substitute(substitution)
    //replaces variables without doing anything else.
    //Variables override this method and return their values if they are in the substitution. others just return themselves
    public abstract Expression substitute(Substitution substitution);
    
    public abstract Expression getDerivative(Variable v);
    
    public abstract Constant evaluate(Substitution substitution);//returns the answer. returns undefined if there are any unspecified variables
//    public abstract Constant operate(List<Constant> constList);//returns the answer if these constants were its children
    
    
    
    public final void getVisitedBy(ExpressionVisitor visitor){
        visitor.visit(this);
    }
    
    
}

//public interface Expression {
//    
//    String toDisplayString();//make sure all directly implementing classes override java's toString with this method
//    boolean isEqualTo(Object o);//make sure all directly implementing classes override java's equals with this method
//    Expression copy();
//    
//    //returns a simplified version of what the expression represents (ex: 0+2 simplifies to 2)
//    //may just return itself
//    //normal use: ex = ex.simplify()
//    Expression simplify();
//    void simplifyChildren();
//    
//    //normal use: ex = ex.substitute(substitution)
//    Expression substitute(Substitution substitution);//replaces variables without doing anything else. variables return their values if they are in the substitution. others just return themselves
//    default Expression getSubstitute(Substitution substitution){//returns and expression that is substituted, leaving the original intact
//        return this.copy().substitute(substitution);
//    }
//    
//    Expression getDerivative(Variable v);
//    
//    //////////returns one answer?
//    Constant evaluate(Substitution substitution);//returns the answer. returns undefined if there are any unspecified variables
//    Constant operate(List<Constant> constList);//returns the answer if these constants were its children
//    
//    
//    int getNumberOfChildren();
//    Expression getChild(int position);//may return null if the child position is out of bounds
//    
//    default void getVisitedBy(ExpressionVisitor visitor){
//        visitor.visit(this);
//    }
//    
//    
//}

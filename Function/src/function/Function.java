package function;

import java.util.List;
import java.util.Set;

public interface Function {
    
    public Expression getExpression();
    public Substitution getSubstitution();
    public void setSubstitution(Substitution sub);
    
    public Constant evaluate();
    
    void setVariable(Variable variable,Expression expression);
    void setVariable(Variable variable,double realNumber);
    void setVariable(char variable,int subscript,Expression expression);
    void setVariable(char variable,int subscript,double realNumber);
    void setVariable(char variable,Expression expression);
    void setVariable(char variable,double realNumber);
    
    Expression getVariableValue(Variable variable);
    Set<Variable> getVariablesInExpression();
    List<Variable> getVariablesInFunctionArgs();
    boolean allVariablesDefined();//returns true if the substitution map has values defined for all variables in the expression
    public String toDisplayString();
    public String toDebugString();
    public void substitute();//replaces variables without doing anything else
    public void simplify();
    
    Function getDerivative(Variable v);//v: variable do get derivative with respect to. Other variables are treated as constants
    public boolean variablesComputable();
    
    
}

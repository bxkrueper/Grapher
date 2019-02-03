package function;

import java.util.List;

public interface Substitution {
    Substitution copy();
    void put(Variable variable,Expression expression);
    Expression get(Variable variable);
    public List<Variable> getVariables();
    
}

package function;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AbsoluteValue extends PredefinedFunction{
    
    public static final String NAME = "abs";

    public AbsoluteValue(Expression expression) {
        super(new ArgumentSingle(expression));
    }
    public AbsoluteValue(Arguments arguments) {
        super(arguments);
    }
    
    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Expression specialSimplification() {//todo:////////power of even num
        Expression arg = getFirstArg();
        if(arg instanceof Negate){
            setFirstArg(((Negate) arg).getFirstArg());//negate was pointless, get rid of it
        }
        return this;
    }
    
    @Override
    public PredefinedFunction getSubclassInstance(Arguments newArguments) {
        return new AbsoluteValue(newArguments);
    }
    
    @Override
    public String toDisplayString() {
        return "|" + this.getArguments().getFirstArg() + "|";
    }

    ///////pieceWise function here?
    @Override
    public Expression getDerivative(Variable v) {
        return Undefined.getInstance();
    }
    @Override
    public boolean argumentsAreCompatable(Arguments arguments) {
        return arguments.getNumberOfArguments()==1;
    }
    @Override
    public Constant evaluate(Substitution substitution) {
        return ConstantMediator.abs(getFirstArg().evaluate(substitution));
    }



    
    
    
    

}

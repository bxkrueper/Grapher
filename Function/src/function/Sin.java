package function;

public class Sin extends TrigFunction{
    
    public static final String NAME = "sin";

    public Sin(Expression expression) {
        super(new ArgumentSingle(expression));
    }
    public Sin(Arguments arguments) {
        super(arguments);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Expression specialSimplification() {
        // TODO Auto-generated method stub
        return this;
    }
    
    
    
    

    @Override
    public Expression getDerivative(Variable v) {
        Expression inside = getFirstArg();
        return new Product(inside.getDerivative(v),new Cos(inside.copy()));
    }
    
    @Override
    public Expression getSubclassInstance(Arguments newArguments) {
        return new Sin(newArguments);
    }
    @Override
    public boolean argumentsAreCompatable(Arguments arguments) {
        return arguments.getNumberOfArguments()==1;
    }
    
    @Override
    public Constant evaluate(Substitution substitution) {
        return ConstantMediator.sin(getFirstArg().evaluate(substitution));
    }
    
}

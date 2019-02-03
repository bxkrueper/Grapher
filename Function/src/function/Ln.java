package function;

public class Ln extends Log{
    
    public static final String NAME = "ln";
    
    public Ln(Arguments arguments) {
        super(arguments,Math.E);
    }
    public Ln(Expression expression) {
        this(new ArgumentSingle(expression));
    }
    
    @Override
    public String getName() {
        return NAME;
    }
    
    @Override
    public String toDisplayString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getName());
        sb.append("(");
        sb.append(getFirstArg().toDisplayString());
        sb.append(")");
        return sb.toString();
    }
    
    @Override
    public Expression getDerivative(Variable v) {
        Expression fx = getFirstArg().copy();
        
        Expression answer = new Divide(fx.getDerivative(v),fx);
        return answer;
    }
    
    

}

package function;

public abstract class PrefixExpression extends UnaryExpression{

    public PrefixExpression(Arguments arguments) {
        super(arguments);
    }

    @Override
    public String toDisplayString() {
        if(getFirstArg() instanceof ChainOperation){
            return getSymbol() + "(" + getFirstArg().toDisplayString() + ")";
        }else{
            return getSymbol() + getFirstArg().toDisplayString();
        }
        
    }

}

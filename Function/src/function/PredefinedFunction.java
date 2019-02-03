package function;

import java.util.ArrayList;
import java.util.List;

import myAlgs.MyAlgs;

public abstract class PredefinedFunction extends Operation{
    
    public PredefinedFunction(Arguments arguments) {
        super(arguments);
    }

    
    
//    public PredefinedFunction(List<Expression> paramList) {
//        if(thisNumberOfParametersIsGood(paramList.size())){
//            parameters = paramList;
//        }else{
//            throw new RuntimeException("wrong number of parameters for " + getName());
//        }
//        
//    }
    
    
//    @Override
//    public Expression getDerivative(Variable v) {
//        return new Product(getEx().getDerivative(v),getBasicDerivative(v));
//    }
//    
//    //returns the first part of the chain rule (for example, sin(x^2) will just return cos(x^2)
//    public abstract Expression getBasicDerivative(Variable v);
    
    
    @Override
    public final boolean isEqualTo(Object o) {
        if(o instanceof PredefinedFunction){
            PredefinedFunction p2 = (PredefinedFunction) o;
            if(this.getName().equals(p2.getName())){
                return this.getArguments().isEqualTo(((PredefinedFunction) o).getArguments());
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
    
    //abs and square root override this (ex: |x| instead of abs(x))
    @Override
    public String toDisplayString() {
        return getName() + getArguments().toDisplayString();
    }
    
    
    
    
}

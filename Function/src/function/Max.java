package function;

import java.util.List;

public class Max extends PredefinedFunction{
    
    public static final String NAME = "max";
    
    public Max(List<Expression> exList) {
        super(new ArgumentsList(exList));
    }
    public Max(Arguments arguments) {
        super(arguments);
    }

    @Override
    public String getName() {
        return NAME;
    }
    
    
    

    @Override
    public Expression specialSimplification() {
        Arguments paramList = this.getArguments();
        Constant max = null;
        int indexOfMax = -1;
        for(int i=0;i<getNumberOfArguments();i++){
            Expression canidate = getArg(i);
            if(canidate instanceof Constant){
                Constant canConstant = (Constant) canidate;
                if(max==null || canConstant.compareTo(max)==1){//if it is bigger
                    //delete old max constant
                    if(max!=null){
                        removeArg(indexOfMax);
                        i--;
                    }
                    //update max
                    max = canConstant;
                    indexOfMax = i;
                }else if((max!=null && canConstant.compareTo(max)<1)){//if it is smaller
                    //delete current
                    removeArg(i);
                    i--;
                }
            }
            
            
        }
        
        if(getNumberOfArguments()==1){
            return getFirstArg();
        }else{
            return this;
        }
        
    }
    
    @Override
    public Constant evaluate(Substitution substitution) {
        Constant max = getFirstArg().evaluate(substitution);
        if(max==Undefined.getInstance()){
            return Undefined.getInstance();
        }
        for(int i=1;i<getNumberOfArguments();i++){
            Constant canidate = getArg(i).evaluate(substitution);
            if(canidate==Undefined.getInstance()){
                return Undefined.getInstance();
            }
            if(canidate.compareTo(max)==1){
                max=canidate;
            }
        }
        return max;
    }
    
    @Override
    public Expression getSubclassInstance(Arguments newArguments) {
        return new Max(newArguments);
    }
    @Override
    public boolean argumentsAreCompatable(Arguments arguments) {
        return arguments.getNumberOfArguments()>=1;
    }
    @Override
    public Expression getDerivative(Variable v) {
        return Undefined.getInstance();
    }
    
    
    

}

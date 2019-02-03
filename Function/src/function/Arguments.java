package function;

import java.util.List;

public interface Arguments extends Iterable<Expression>{

    void simplify();

    boolean isComputable();

    void substitute(Substitution substitution);

    Arguments copy();
    
    List<Expression> getExList();
    
    Expression getArg(int index);
    Expression getFirstArg();
    Expression getSecondArg();
    
    void setArg(int index, Expression ex);
    void setFirstArg(Expression ex);
    void setSecondArg(Expression ex);
    
    void removeArg(int index);
    
    int getNumberOfArguments();
    
    public default boolean isEqualTo(Arguments arg2){
        if(getNumberOfArguments()!=arg2.getNumberOfArguments()){
            return false;
        }
        for(int i=0;i<getNumberOfArguments();i++){
            if(!getArg(i).equals(arg2.getArg(i))){
                return false;
            }
        }
      
        return true;
    }
    boolean isEqualToOrderDoesntMatter(Arguments arg2);

    //subclasses should override toString
    public String toDisplayString();
    public String toDebugString();

    

    

    

}

package function;

import java.util.Stack;

public abstract class PrefixSymbolToken implements Token{
    
    public void handleAssembleStacks(Stack<Expression> unitStack,Stack<BinarySymbolToken> binaryOperatorStack,Stack<PrefixSymbolToken> prefixOperatorStack){
        prefixOperatorStack.push(this);
    }
    
    public abstract UnaryExpression makeExpression(Expression ex);

}

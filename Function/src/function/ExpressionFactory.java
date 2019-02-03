/*
 * converts a string to an expression object
 */

package function;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Stack;

public class ExpressionFactory {
    
    private static List<Tokenizer> parseOrder;
    
    public static Expression getExpression(String str){
        if(parseOrder==null){
            makeParseOrder();
        }
        
        List<Token> tokenList = tokenizeExpression(str);//converts a string to a list of tokens
        clarifySymbolList(tokenList);//edits token list to make it more compatible for assembly
        Expression ex = assemble(tokenList);
//        ex = ex.simplify();
        return ex;
    }
    
    //uses the reference order list to guess at the next token from the string and add it to the list
    private static List<Token> tokenizeExpression(String exp){
        List<Token> tokenList = new LinkedList<>();
        StringParser sp = new StringParser(exp);
        
        while(sp.hasNext()){
            Token token = null;
            Iterator<Tokenizer> it = parseOrder.iterator();
            while(token==null && it.hasNext()){
                token=it.next().tryTokenize(sp);
            }
            if(token==null){//no tokens could be pulled from the next part of the string
                throw new RuntimeException("couldn't parse expression: no tokens could be pulled from the next part of the string: " + sp);
            }else{
                tokenList.add(token);
            }
        }
        return tokenList;
    }

    private static void clarifySymbolList(List<Token> tokenedExpressionList) {
        handleSubtractNegate(tokenedExpressionList);
        handleInvisibleMultiplication(tokenedExpressionList);
    }

    //uses stacks to assemble expressions, binary oparators, and unary operators in the right order
    //unary operators are given priority
    private static Expression assemble(List<Token> tokenList) {
        Stack<Expression> unitStack = new Stack<>();
        Stack<BinarySymbolToken> binaryOperatorStack = new Stack<>();
        Stack<PrefixSymbolToken> prefixOperatorStack = new Stack<>();
        
        //main part
        for(Token t:tokenList){
            t.handleAssembleStacks(unitStack,binaryOperatorStack,prefixOperatorStack);
//            System.out.println("unitStack: " + unitStack + "opStack: " + operatorStack);
        }
        //next: finish up 
        
        //there shouldn't be any prefixes left
        if(!prefixOperatorStack.isEmpty()){
            throw new RuntimeException("dangling prefix operator");
        }
        
        //use up the rest of the binary tokens
        while(!binaryOperatorStack.isEmpty()){
            Expression ex2 = unitStack.pop();
            Expression ex1;
            if(unitStack.isEmpty()){
                throw new RuntimeException("unit stack is empty: not enough expressions for binary operator");
            }else{
                ex1 = unitStack.pop();
            }
            
            Expression bex = binaryOperatorStack.pop().makeExpression(ex1,ex2);
            unitStack.push(bex);
        }
        
        //there should be one expression left in the stack: the answer
        if(unitStack.isEmpty()){
            throw new RuntimeException("unit stack is empty!");
        }else{
            Expression ex = unitStack.pop();
            if(!unitStack.isEmpty()){
                throw new RuntimeException("unit stack is not empty at end of assembly! _" + unitStack.pop().toDisplayString() + "_ ");
            }
            return ex;
        }
        
    }
    
    //inserts a sum token before every negate token if it if after a complete expression
    private static void handleSubtractNegate(List<Token> tokenedExpressionList){
        ListIterator<Token> it = tokenedExpressionList.listIterator();
        Token prev = null;
        while(it.hasNext()){
            Token current = it.next();
            if((current instanceof TokenizerFactory.NegateTokenizer.NegateToken) && (prev instanceof CompleteExpressionToken)){
                it.previous();
                it.add(new TokenizerFactory.SumTokenizer.SumToken());
                it.next();
            }
            prev=current;
        }
    }
    
    //inserts a multiplication token between consecutive expression tokens
    private static void handleInvisibleMultiplication(List<Token> tokenedExpressionList){
        ListIterator<Token> it = tokenedExpressionList.listIterator();
        Token prev = null;
        while(it.hasNext()){
            Token current = it.next();
            if((current instanceof CompleteExpressionToken) && (prev instanceof CompleteExpressionToken)){
                it.previous();
                it.add(new TokenizerFactory.ProductTokenizer.ProductToken());
                it.next();
            }
            prev=current;
        }
    }

    //constructs the list of tokenizers in the order they should be used to tokenize the expression string
    //order matters here. 
    private static void makeParseOrder(){
        parseOrder = new ArrayList<>();
        parseOrder.add(new TokenizerFactory.WhiteSpaceIgnorerTokenizer());
        parseOrder.add(new TokenizerFactory.ParenthesesTokenizer());
        parseOrder.add(new TokenizerFactory.VectorTokenizer());
        parseOrder.add(new TokenizerFactory.ExpressionGroupTokenizer());
        parseOrder.add(new TokenizerFactory.RealNumberTokenizer());
        parseOrder.add(new TokenizerFactory.AbsoluteValueTokenizer());
        parseOrder.add(new TokenizerFactory.SqrtTokenizer());
        parseOrder.add(new TokenizerFactory.ITokenizer());
        parseOrder.add(new TokenizerFactory.ETokenizer());
        parseOrder.add(new TokenizerFactory.PITokenizer());
        parseOrder.add(new TokenizerFactory.MaxTokenizer());
        parseOrder.add(new TokenizerFactory.SinTokenizer());
        parseOrder.add(new TokenizerFactory.CosTokenizer());
        parseOrder.add(new TokenizerFactory.TanTokenizer());
        parseOrder.add(new TokenizerFactory.LogTokenizer());
        parseOrder.add(new TokenizerFactory.LnTokenizer());
        parseOrder.add(new TokenizerFactory.VariableTokenizer());//put after all other things that have letters in them
        parseOrder.add(new TokenizerFactory.SumTokenizer());
        parseOrder.add(new TokenizerFactory.NegateTokenizer());////negates will be turned into subtracts where appropriate later
        parseOrder.add(new TokenizerFactory.ProductTokenizer());
        parseOrder.add(new TokenizerFactory.DivideTokenizer());
        parseOrder.add(new TokenizerFactory.PowerTokenizer());
        parseOrder.add(new TokenizerFactory.FactorialTokenizer());
        
    }
    

}
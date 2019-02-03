package function;

import java.util.ArrayList;
import java.util.List;

public class TokenizerFactory {
    
    public static final int SUM_PRIORITY = 1;
    public static final int MULTIPLY_PRIORITY = 2;
    public static final int DIVIDE_PRIORITY = 2;
    public static final int POWER_PRIORITY = 3;
    
    
    public static abstract class SingleArgFunctionTokenizer implements Tokenizer{

        @Override
        public final Token tryTokenize(StringParser sp) {
            if(sp.parseString(getName())){
                Expression exp = sp.parseParenthesesExpression('(', ')');
                if(exp==null){
                    throw new RuntimeException("malformed/null parameter list for " + getName());
                }else{
                    return new CompleteExpressionToken(getSingleArgFunction(exp));
                }
            }else{
                return null;
            }
            
        }
        
        public abstract String getName();

        public abstract PredefinedFunction getSingleArgFunction(Expression exp);
    }
    
    ////////add test arglist for format method if certain types of expressions are needed for certain args
    public static abstract class MultiArgFunctionTokenizer implements Tokenizer{
        
        @Override
            public final Token tryTokenize(StringParser sp) {
                if(sp.parseString(getName())){
                    List<Expression> paramList= sp.parseParameterList('(',')');
                    if(paramList==null){
                        throw new RuntimeException("malformed/null parameter list for " + getName());
                    }else{
                        return new CompleteExpressionToken(getMultiArgFunction(paramList));
                    }
                }else{
                    return null;
                }
                
            }
        
        public abstract String getName();

        public abstract PredefinedFunction getMultiArgFunction(List<Expression> paramList);
        
    }
    
    
    
    
    
    
    
    
    public static class AbsoluteValueTokenizer extends SingleArgFunctionTokenizer{

        @Override
        public String getName() {
            return AbsoluteValue.NAME;
        }

        @Override
        public PredefinedFunction getSingleArgFunction(Expression exp) {
            return new AbsoluteValue(exp);
        }

        
        
    }
    
    
    public static class CosTokenizer extends SingleArgFunctionTokenizer{
        
        @Override
        public String getName() {
            return Cos.NAME;
        }

        @Override
        public PredefinedFunction getSingleArgFunction(Expression exp) {
            return new Cos(exp);
        }
        
    }
    
    
    public static class DivideTokenizer implements Tokenizer{
        
        @Override
            public Token tryTokenize(StringParser sp) {
                if(sp.parseString("/")){
                    return new BinarySymbolToken(){

                        @Override
                        public int getPriority() {
                            return DIVIDE_PRIORITY;
                        }

                        @Override
                        public BinaryOperation makeExpression(Expression ex1, Expression ex2) {
                            return new Divide(ex1,ex2);
                        }
                        
                    };
                }else{
                    return null;
                }
            }
        
    }
    
    
    public static class ETokenizer implements Tokenizer{
        
        @Override
        public Token tryTokenize(StringParser sp) {
            if(sp.parseString("e")){
                return new CompleteExpressionToken(new E());
            }else{
                return null;
            }
          
        }
        
    }
    
    public static class ExpressionGroupTokenizer implements Tokenizer{
        
        @Override
        public Token tryTokenize(StringParser sp) {
            List<Expression> expList = sp.parseParameterList('{', '}');
            if(expList==null){
                return null;
            }else{
                ExpressionGroup eg = new ExpressionGroup(expList);
                if(eg.computable()){
                    return new CompleteExpressionToken(new ConstantSet(eg));
                }else{
                    return new CompleteExpressionToken(new ExpressionGroup(expList));
                }
                
            }
        }
        
    }
    
    public static class FactorialTokenizer implements Tokenizer{
        
        @Override
            public Token tryTokenize(StringParser sp) {
                if(sp.parseString("!")){
                    return new PostfixSymbolToken(){

                        @Override
                        public PostfixExpression makeExpression(Expression ex) {
                            return new Factorial(ex);
                        }
                        
                    };
                }else{
                    return null;
                }
            }
        
    }
    
    //complex numbers start out as just I, then get combined with any added or multiplied real numbers
    public static class ITokenizer implements Tokenizer{
        
        @Override
        public Token tryTokenize(StringParser sp) {
            if(sp.parseString("i")){
                return new CompleteExpressionToken(new I());
            }else{
                return null;
            }
          
        }
        
    }
    
    public static class LnTokenizer extends SingleArgFunctionTokenizer{
        
        @Override
        public String getName() {
            return Ln.NAME;
        }

        @Override
        public PredefinedFunction getSingleArgFunction(Expression exp) {
            return new Ln(exp);
        }
        
    }
    
    
    public static class LogTokenizer implements Tokenizer{
        
        @Override
            public Token tryTokenize(StringParser sp) {
                if(sp.parseString("log")){
                    Expression baseExpression = sp.parseParenthesesExpression('[', ']');
                    double baseReal;
                    if(baseExpression==null){//no []?
                        baseReal = 10.0;
                    }else{
                        if(baseExpression instanceof RealNumber){
                            baseReal = ((RealNumber) baseExpression).getValue();
                        }else{
                            throw new RuntimeException("log base should be a number!  base: " + baseExpression);
                        }
                        
                    }
                    
                    
                    Expression exp = sp.parseParenthesesExpression('(', ')');
                    if(exp==null){
                        throw new RuntimeException("malformed/null parameter list for " + this.getClass().getName());
                    }else{
                        return new CompleteExpressionToken(new Log(exp,baseReal));
                    }
                }else{
                    return null;
                }
                
            }
        
    }
    
    
    public static class MaxTokenizer extends MultiArgFunctionTokenizer{

        @Override
        public String getName() {
            return Max.NAME;
        }

        @Override
        public PredefinedFunction getMultiArgFunction(List<Expression> paramList) {
            return new Max(paramList);
        }
        
        
        
    }
    
    public static class NegateTokenizer implements Tokenizer{
        
        @Override
        public Token tryTokenize(StringParser sp) {
            if(sp.parseString("-")){
                return new NegateToken();
            }else{
                return null;
            }
        }
        
        public static class NegateToken extends PrefixSymbolToken{
            @Override
            public UnaryExpression makeExpression(Expression ex) {
                return new Negate(ex);
            }
        }
        
    }
    
    
    //parentheses are dealt with recursively
    public static class ParenthesesTokenizer implements Tokenizer{
        
        @Override
            public Token tryTokenize(StringParser sp) {
                Expression ex = sp.parseParenthesesExpression('(',')');
                if(ex==null){
                    return null;
                }else{
                    return new CompleteExpressionToken(ex);
                }
            }
        
    }
    
    
    
    public static class PITokenizer implements Tokenizer{
        
        @Override
        public Token tryTokenize(StringParser sp) {
            if(sp.parseString("PI")){
                return new CompleteExpressionToken(new PI());
            }else{
                return null;
            }
          
        }
        
    }
    
    
    public static class PowerTokenizer implements Tokenizer{
        
        @Override
            public Token tryTokenize(StringParser sp) {
                if(sp.parseString("^")){
                    return new BinarySymbolToken(){

                        @Override
                        public int getPriority() {
                            return POWER_PRIORITY;
                        }

                        @Override
                        public BinaryOperation makeExpression(Expression ex1, Expression ex2) {
                            return new Power(ex1,ex2);
                        }
                        
                    };
                }else{
                    return null;
                }
            }
        
    }
    
    
    public static class ProductTokenizer implements Tokenizer{
        
        @Override
            public Token tryTokenize(StringParser sp) {
                if(sp.parseString("*")){
                    return new ProductToken();
                }else{
                    return null;
                }
            }
        
        public static class ProductToken extends BinarySymbolToken{
            @Override
            public int getPriority() {
                return MULTIPLY_PRIORITY;
            }

            @Override
            public Expression makeExpression(Expression ex1, Expression ex2) {
                return new Product(ex1,ex2);
            }
        }
        
    }
    
    public static class RealNumberTokenizer implements Tokenizer{
        
        @Override
            public Token tryTokenize(StringParser sp) {
                Double d = sp.parseDouble();
                if(d==null){
                    return null;
                }else{
                    return new CompleteExpressionToken(new RealNumber(d));
                }
            }
        
    }
    
    public static class SinTokenizer extends SingleArgFunctionTokenizer{
        
        @Override
        public String getName() {
            return Sin.NAME;
        }

        @Override
        public PredefinedFunction getSingleArgFunction(Expression exp) {
            return new Sin(exp);
        }
        
    }
    
    
    public static class SqrtTokenizer extends SingleArgFunctionTokenizer{
        
        @Override
        public String getName() {
            return SquareRoot.NAME;
        }

        @Override
        public PredefinedFunction getSingleArgFunction(Expression exp) {
            return new SquareRoot(exp);
        }
        
    }
    
    public static class SumTokenizer implements Tokenizer{
        
        @Override
        public Token tryTokenize(StringParser sp) {
            if(sp.parseString("+")){
                return new SumToken();
            }else{
                return null;
            }
        }
        
        public static class SumToken extends BinarySymbolToken{
            @Override
            public int getPriority() {
                return SUM_PRIORITY;
            }

            @Override
            public Expression makeExpression(Expression ex1, Expression ex2) {
                return new Sum(ex1,ex2);
            }
        }
        
    }
    
    public static class TanTokenizer extends SingleArgFunctionTokenizer{
        
        @Override
        public String getName() {
            return Tan.NAME;
        }

        @Override
        public PredefinedFunction getSingleArgFunction(Expression exp) {
            return new Tan(exp);
        }
        
    }
    
    
    public static class VariableTokenizer implements Tokenizer{
        
        @Override
        public Token tryTokenize(StringParser sp) {
            if(isVariablePiece(sp.peek())){
                char letter = sp.pop();
                if(!sp.hasNext() || sp.peek()!='['){/////short circuit
                    return new CompleteExpressionToken(new Variable(letter,-1));
                }
                
                sp.pop();//pops '['
                Integer subscript = sp.parseInteger();
                if(subscript==null){
                    throw new RuntimeException("no integer for subscript for " + letter);
                }
                if(!sp.hasNext() || sp.pop()!=']'){/////short circuit
                    throw new RuntimeException("no ']' after subscript " + subscript);
                }
                return new CompleteExpressionToken(new Variable(letter,subscript));
            }else{
                return null;
            }
        }
        
        //only a-z and A-Z
        //don't allow e and i?
        private boolean isVariablePiece(char ch){
            if(ch=='e' || ch=='i'){
                return false;
            }
            return (ch>=65 && ch<=90) || (ch>=97 && ch<=122);
        }
        
    }
    
    
    public static class VectorTokenizer implements Tokenizer{
        
        @Override
            public Token tryTokenize(StringParser sp) {
                List<Expression> expList = sp.parseParameterList('<', '>');
                if(expList==null){
                    return null;
                }else{
                    List<Constant> constList = new ArrayList<>(expList.size());
                    for(Expression ex:expList){
                        if(ex instanceof Constant){
                            constList.add((Constant) ex);
                        }else{
                            throw new RuntimeException("non-constant in vector!" + expList);
                        }
                    }
                    return new CompleteExpressionToken(new Vector(constList));
                }
            }
        
    }
    
    //skips all white spaces in the string parser. use first
    public static class WhiteSpaceIgnorerTokenizer implements Tokenizer{
        
        @Override
            public Token tryTokenize(StringParser sp) {
                while(Character.isWhitespace(sp.peek())){
                    sp.pop();
                }
                return null;
                
            }
        
    }
    
}

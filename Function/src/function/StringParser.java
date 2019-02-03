package function;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class StringParser{
    private final String str;
    private int index;
    
    public StringParser(String str){
        this.str = str.trim();
        index=0;
    }
    
    public boolean hasNext(){
        return index<str.length();
    }
    
    public Double parseDouble(){
        char currentChar = str.charAt(index);
        if(!isNumberPiece(currentChar)){
            return null;
        }
        
        int indexEnd = index;
        do{
            indexEnd++;
        }while(indexEnd<str.length() && isNumberPiece(str.charAt(indexEnd)));//////short circuit?
        
        double number;
        try{
            number=Double.parseDouble(str.substring(index, indexEnd));
        }catch(Exception e){
            throw new RuntimeException("trouble parsing double! " + str.substring(index, indexEnd));
        }
        
        index = indexEnd;
        return number;
    }
    
    public Integer parseInteger(){
        char currentChar = str.charAt(index);
        if(!isDigit(currentChar)){
            return null;
        }
        
        int indexEnd = index;
        do{
            indexEnd++;
        }while(indexEnd<str.length() && isDigit(str.charAt(indexEnd)));//////short circuit?
        
        int number;
        try{
            number=Integer.parseInt(str.substring(index, indexEnd));
        }catch(Exception e){
            throw new RuntimeException("trouble parsing integer! " + str.substring(index, indexEnd));
        }
        
        index = indexEnd;
        return number;
    }
    
    public char peek(){
        return str.charAt(index);
    }
    
    public char pop(){
        return str.charAt(index++);
    }
    
//    public boolean parseSymbol(char symbol){
//        char currentChar = str.charAt(index);
//        if(symbol!=currentChar){
//            return false;
//        }else{
//            index++;
//            return true;
//        }
//    }
    
    //if true, advances
    public boolean parseString(String s){
        int endIndex = index+s.length();
        if(endIndex>str.length()){
            return false;
        }
        if(str.substring(index, endIndex).equals(s)){
            index=endIndex;
            return true;
        }else{
            return false;
        }
    }
    
    public Expression parseParenthesesExpression(char open,char close){
        String subExpression = parseParentheses(open,close);
        if(subExpression==null){
            return null;//no ( at start
        }
        if(subExpression.equals("")){
            throw new RuntimeException("empty Parentheses");
        }
        Expression ex;
        try{
            ex=ExpressionFactory.getExpression(subExpression);
        }catch(Exception e){
            throw new RuntimeException(e.getMessage() + "trouble parsing expression!" + "_" + subExpression + "_");
        }
        return ex;
        
    }
    
    public List<Expression> parseParameterList(char open,char close){
        String subExpression = parseParentheses(open,close);
        if(subExpression==null){
            return null;//no ( at start
        }
        if(subExpression.equals("")){
            throw new RuntimeException("empty Parentheses");
        }
        return parseParameterList(subExpression,open,close);
    }
    
    //returns what is in the parentheses. expected to use () <> [] or {} for parameters
    //stops at the right closeing parentheses
    private String parseParentheses(char open,char close){
        if(!hasNext()){
            return null;
        }
        char currentChar = str.charAt(index);
        if(currentChar!=open){
            return null;
        }
        
        int indexEnd = index;
        int depth = 1;
        do{
            indexEnd++;
            currentChar = str.charAt(indexEnd);
            if(currentChar==close){
                depth--;
            }else if(currentChar==open){
                depth++;
            }
        }while(indexEnd<str.length()-1 && depth>0);//////short circuit?
        
        if(depth>0){
            throw new RuntimeException("missing '" + close + "'");
        }
        
        
        String subString = str.substring(index+1,indexEnd);
        index = indexEnd+1;
        return subString;
    }
    
    //sub expression is without open and close. open and close are for telling what characters affect the depth
    private List<Expression> parseParameterList(String subExpression,char open,char close){
        if(subExpression==null){
            return null;//no open at start
        }
        
        List<Expression> expressionList = new LinkedList<>();
        int startIndex = 0;
        int depth = 0;
        for(int endIndex=0;endIndex<=subExpression.length();endIndex++){
            if(endIndex==subExpression.length() || (subExpression.charAt(endIndex)==','&& depth==0)){
                expressionList.add(ExpressionFactory.getExpression(subExpression.substring(startIndex, endIndex)));
                startIndex = endIndex+1;
            }else if(subExpression.charAt(endIndex)==open){
                depth++;
            }else if(subExpression.charAt(endIndex)==close){
                depth--;
            }
        }
        
        return expressionList;
//        //convert to array
//        Expression[] array = new Expression[expressionList.size()];
//        int i=0;
//        for(Expression ex:expressionList){
//            array[i] = ex;
//            i++;
//        }
//        return array;
        
        
        
//        List<Integer> cutoffIndexList = new LinkedList<>();//records ,'s and the last ) indexes
//        do{
//            indexEnd++;
//            currentChar = str.charAt(indexEnd);
//            if(currentChar==')'){
//                depth--;
//            }else if(currentChar=='('){
//                depth++;
//            }else if(currentChar==',' && depth==1){
//                cutoffIndexList.add(indexEnd);
//            } 
//        }while(indexEnd<str.length()-1 && depth>0);//////short circuit?
//        
//        if(depth>0){
//            throw new RuntimeException("missing ')'");
//        }
//        
//        if(indexEnd>index+1){//makes an empty parameter list () return a empty list
//            cutoffIndexList.add(indexEnd);//normally count the end ) as the last cut off
//        }
//        
//        List<Expression> parameterList = new ArrayList<>(cutoffIndexList.size());
//        int oldCommaPosition = index;//this is actually at a '(' at first
//        for(Integer currentCommaPosition:cutoffIndexList){
//            String subExpression = str.substring(oldCommaPosition+1,currentCommaPosition);
//            try{
//                Expression ex = ExpressionFactory.getExpression(subExpression);
//                parameterList.add(ex);
//            }catch(Exception e){
//                throw new RuntimeException(e.getMessage() + "trouble parsing expression!" + subExpression);
//            }
//            
//            
//            oldCommaPosition = currentCommaPosition;
//        }
//        
//        index = indexEnd+1;
//        return parameterList;
        
    }
    
    
    private boolean isNumberPiece(char ch){
        return (ch>=48 && ch<=57) || ch==46;
    }
    
    private boolean isDigit(char ch){
        return ch>=48 && ch<=57;
    }
    

    @Override
    public String toString() {
        return "StringParser [str=" + str + ", index=" + index + "]";
    }
    
}

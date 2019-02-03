package function;

import function.BasicFunction;
import function.Expression;
import function.ExpressionFactory;
import function.Function;
import function.Variable;
import myAlgs.MyAlgs;
import myAlgs.MyMath;

public class Tester {

    public static void main(String[] args) {
//        String expString = "(-b+sqrt(b^2-4ac))/(2a)";
//        Variable x = new Variable('x');
//        Variable a = new Variable('a');
//        Variable b = new Variable('b');
//        Variable c = new Variable('c');
//        
//        
//        
//        Function function = new BasicFunction("{1+x,2}+{3,2}");
//        
//        function.setVariable(x, ExpressionFactory.getExpression("3"));
//        function.setVariable(a, 1);
//        function.setVariable(b, 2);
//        function.setVariable(c, 3);
//        System.out.println("Function: " + function.toDisplayString());
//        
//        
//        function.simplify();
//        System.out.println("Simplified: " + function.toDisplayString()); 
//        
//        function.substitute();
//        System.out.println("substituted: " + function.toDisplayString());
//        
//        
//        
//        Constant answer = function.evaluate();
//        System.out.println("Answer: " + answer);
        
        
        double[] testNums = new double[]{0,1,10,100,1000,10000,100000,1000000,
                0.1,0.01,0.001,0.0001,
                0.000123456,0.00123456,0.0123456,0.123456,1.23456,12.3456,123.456,1234.56,12345.6};
        for(double num:testNums){
            System.out.println(num + " :" + MyMath.toNiceString(num, 3));
        }
        
//        System.out.println(MyMath.roundToNearestMultipleOf(1006,4));
        
        
//        Function derivative = function.getDerivative(x);
//        System.out.println("Derivative: " + derivative);
        
        
//        double angle = 0;
//        for(angle= -3.2;angle<Math.PI;angle+=.1){
//            System.out.println("angle: " + angle + " tan2: " + MyMath.getPrincipleDirectionFromOrigin(Math.cos(angle), Math.sin(angle)));
//        }
//        double xv = 0;
//        double y = -1;
//        System.out.println("x: " + xv + " y: " +y+ " tan2: " +  Math.atan2(y,xv));
        
        
    }
    
    

    

}

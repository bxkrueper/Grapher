package function;

import myAlgs.MyAlgs;
import myAlgs.MyMath;

public class ComplexMT extends Complex{
    
    private double magnitude;
    private double theta;//keep theta between -pi and pi
    
    public ComplexMT(double magnitude, double theta){
        this.magnitude = magnitude;
        this.theta = standardizeTheta(theta);
    }
    
    public double getReal(){
        return magnitude*Math.cos(theta);
    }
    
    public double getImaginary(){
        return magnitude*Math.sin(theta);
    }
    
    public double getMagnitude(){
        return magnitude;
    }
    
    public double getTheta(){
        return theta;
    }
    
    public static double standardizeTheta(double theta){
        double PI2 = Math.PI*2;
        if(theta>Math.PI){
            return ((theta+Math.PI)%PI2)-Math.PI;
        }else if(theta<=-Math.PI){
            return -(((-theta+Math.PI)%PI2)-Math.PI);
        }else{
            return theta;
        }
    }

    @Override
    public String toDisplayString() {
        return MyMath.toNiceString(magnitude,5) + "*e^(" + MyMath.toNiceString(theta,5) + "i)";
    }

    @Override
    public Expression simplify() {
        if(theta==0.0){
            return new RealNumber(magnitude);
        }else if(theta == Math.PI){
            return new RealNumber(-magnitude);
        }else{
            return this;
        }
    }
    
    @Override
    public Complex getConjugate() {
        return new ComplexMT(magnitude,-theta);
    }
}

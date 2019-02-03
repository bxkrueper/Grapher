package function;

import myAlgs.MyAlgs;
import myAlgs.MyMath;

public class RealNumber extends Number{

    private double value;
    
    public RealNumber(double value) {
        this.value = value;
    }
    
    public double getValue(){
        return value;
    }
    
    @Override
    public String toDisplayString() {
        return MyMath.toNiceString(value,5);
    }
    
    
    
    
    
    
    @Override
    public Expression simplify() {
        return this;
    }
    

    @Override
    public double getReal() {
        return value;
    }

    @Override
    public double getImaginary() {
        return 0.0;
    }

    @Override
    public double getMagnitude() {
        return value;
    }

    //angle to real number line from 0+0i  in the complex plane
    @Override
    public double getTheta() {
        return value<0?Math.PI:0.0;
    }

    
    
}

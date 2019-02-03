package function;

import myAlgs.MyMath;

public class ComplexRI extends Complex{
    
    private double real;
    private double imaginary;
    
    public ComplexRI(double real, double imaginary){
        this.real = real;
        this.imaginary = imaginary;
    }
    
    public double getReal(){
        return real;
    }
    
    public double getImaginary(){
        return imaginary;
    }
    
    public double getMagnitude(){
        return Math.hypot(real, imaginary);
    }
    
    public double getTheta(){
        double theta = MyMath.getDirectionFromOrigin(real, imaginary);
        if(theta>Math.PI){
            theta-=Math.PI*2;/////////////needed for  log ^of complex number principle value (between pi and -pi)
        }
        return theta;
    }

    @Override
    public String toDisplayString() {
        if(imaginary==0.0){
            return MyMath.toNiceString(real,5);
        }else if(real==0.0){
            if(imaginary==1.0){
                return "i";
            }else{
                return MyMath.toNiceString(imaginary,5) + "i";
            }
        }else{
            if(imaginary==1.0){
                return "(" + MyMath.toNiceString(real,5) + "+" + "i" + ")";
            }else if(imaginary==-1.0){
                return "(" + MyMath.toNiceString(real,5) + "-" + "i" + ")";
            }else{
                if(imaginary>0){
                    return "(" + MyMath.toNiceString(real,5) + "+" + MyMath.toNiceString(imaginary,5) + "i" + ")";
                }else{//imaginary<0
                    return "(" + MyMath.toNiceString(real,5) + MyMath.toNiceString(imaginary,5) + "i" + ")";
                }
                
            }
        }
    }

    @Override
    public Expression simplify() {
        if(imaginary==0.0){
            return new RealNumber(real);
        }else{
            return this;
        }
    }

    @Override
    public Complex getConjugate() {
        return new ComplexRI(real,-imaginary);
    }
}

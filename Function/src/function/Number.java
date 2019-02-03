package function;

//all numbers: complex or real
public abstract class Number extends Constant{
    
    public static final Number additiveIdentity = new RealNumber(0.0);
    public static final Number multiplictiveIdentity = new RealNumber(1.0);
    
    public abstract double getReal();
    
    public abstract double getImaginary();
    
    public abstract double getMagnitude();
    
    public abstract double getTheta();

    @Override
    public Constant getAdditiveIdentity() {
        return additiveIdentity;
    }

    @Override
    public Constant getMultiplictiveIdentity() {
        return multiplictiveIdentity;
    }
    
    @Override
    public Expression getDerivative(Variable v) {
        return new RealNumber(0.0);
    }
}

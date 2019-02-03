//package function;
//
//public class ProxyConstant extends Constant{
//
//    private Constant constant;
//    private Expression expression;/////need to copy?
//    private Substitution substitution;//this is a copy in case original is changed before proxy is computed
//    public ProxyConstant(Expression expression,Substitution sub){
//        this.expression = expression;
//        this.substitution = sub.copy();
//    }
//    public ProxyConstant(Expression expression){
//        this(expression,NullSubstitution.getInstance());
//    }
//    
//    private void generateConstant(){
//System.out.println("generating constant in proxy");
//        this.constant = expression.evaluate(substitution);
//        this.expression = null;
//        this.substitution = NullSubstitution.getInstance();
//    }
//    
//    
//    
//    @Override
//    public String toDisplayString() {
//        if(constant==null){
//            generateConstant();
//        }
//        return constant.toDisplayString();
//    }
//    @Override
//    public boolean isEqualTo(Object o) {
//        if(constant==null){
//            generateConstant();
//        }
//        return constant.isEqualTo(o);
//    }
//
//    @Override
//    public Expression simplify() {
//        if(constant==null){
//            generateConstant();
//        }
//        return constant.simplify();
//    }
//
//    @Override
//    public Expression getDerivative(Variable v) {
//        if(constant==null){
//            generateConstant();
//        }
//        return constant.getDerivative(v);
//    }
//
//    @Override
//    public Constant getAdditiveIdentity() {
//        if(constant==null){
//            generateConstant();
//        }
//        return constant.getAdditiveIdentity();
//    }
//
//    @Override
//    public Constant getMultiplictiveIdentity() {
//        if(constant==null){
//            generateConstant();
//        }
//        return constant.getMultiplictiveIdentity();
//    }
//    
//    
//    @Override
//    public Constant add(Constant c2){
//        if(constant==null){
//            expression = new Sum(expression,c2);
//            return this;
//        }else{
//            expression = new Sum(constant,c2);
//            constant = null;
//            return this;
//        }
//    }
//    @Override
//    public Constant subtract(Constant c2){
//        if(constant==null){
//            expression = new Sum(expression,new Negate(c2));
//            return this;
//        }else{
//            expression = new Sum(constant,new Negate(c2));
//            constant = null;
//            return this;
//        }
//    }
//    @Override
//    public Constant multiply(Constant c2){
//        if(constant==null){
//            expression = new Product(expression,c2);
//            return this;
//        }else{
//            expression = new Product(constant,c2);
//            constant = null;
//            return this;
//        }
//    }
//    @Override
//    public Constant divide(Constant c2){
//        if(constant==null){
//            expression = new Divide(expression,c2);
//            return this;
//        }else{
//            expression = new Divide(constant,c2);
//            constant = null;
//            return this;
//        }
//    }
//    @Override
//    public Constant power(Constant c2){
//        if(constant==null){
//            expression = new Power(expression,c2);
//            return this;
//        }else{
//            expression = new Power(constant,c2);
//            constant = null;
//            return this;
//        }
//    }
//    @Override
//    public Constant convertToInstanceOf(Constant c2){
//        if(constant==null){
//            return this;//////////?
//        }else{
//            constant = constant.convertToInstanceOf(c2);
//            return this;
//        }
//    }
//    @Override
//    public Constant abs(){
//        if(constant==null){
//            expression = new AbsoluteValue(expression);
//            return this;
//        }else{
//            expression = new AbsoluteValue(constant);
//            constant = null;
//            return this;
//        }
//    }
//    @Override
//    public int compareTo(Constant c2){
//        if(constant==null){
//            generateConstant();
//        }
//        return constant.compareTo(c2);
//    }
//    @Override
//    public Constant gamma(){
//        if(constant==null){
//            expression = new Factorial(expression);
//            return this;
//        }else{
//            expression = new Factorial(constant);
//            constant = null;
//            return this;
//        }
//    }
//    @Override
//    public Constant negate(){
//        if(constant==null){
//            expression = new Negate(expression);
//            return this;
//        }else{
//            expression = new Negate(constant);
//            constant = null;
//            return this;
//        }
//    }
//
//}

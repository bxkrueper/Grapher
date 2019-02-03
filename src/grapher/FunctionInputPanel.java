package grapher;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

import function.BasicFunction;
import function.Expression;
import function.ExpressionFactory;
import function.Function;
import function.Substitution;
import myAlgs.MyEvent;
import myAlgs.MyListener;


//////use different types of Panels for  different functions?
public class FunctionInputPanel extends JPanel implements DocumentListener, ActionListener, ChangeListener{
    
    private static final Color SELECTED_COLOR = Color.YELLOW;
    private static final Color DESELECTED_COLOR = Color.WHITE;
    private JLabel label;
    private JTextField textField;
    private MyListener listener;
    private FunctionWorldObject functionWorldObject;
    private JPanel closeAddPanel;
    private JButton closeButton;
    private JButton addButton;
    private Color color;
    private JButton colorButton;
    JColorChooser tcc;
    private Substitution sharedSubstitution;
    
    public FunctionInputPanel(MyListener listener,Color color, Substitution sharedSubstitution){
        this.listener = listener;
        this.color = color;
        this.sharedSubstitution = sharedSubstitution;
        this.setBackground(SELECTED_COLOR);
        this.functionWorldObject = NullFunctionWorldObject.getInstance();
        this.label = new JLabel("f(x)=");
        this.textField = new JTextField(15);
        textField.setForeground(Color.BLACK);
        textField.getDocument().addDocumentListener(this);
        textField.addFocusListener(new FocusListener(){

            @Override
            public void focusGained(FocusEvent arg0) {/////////this is being called after everything has been deleted and re added
                listener.eventHappened(new MyEvent(this,"Deselect All",null));
                select();
            }

            @Override
            public void focusLost(FocusEvent arg0) {
            }
            
        });
        this.closeAddPanel = new JPanel();
        closeAddPanel.setLayout(new BoxLayout(closeAddPanel,BoxLayout.Y_AXIS));
        this.closeButton = new JButton("X");
        closeButton.setBackground(Color.RED);
        closeButton.setPreferredSize(new Dimension(15,15));
        closeButton.setBorder(BorderFactory.createEmptyBorder());
        closeButton.addActionListener(this);
        this.addButton = new JButton("+");
        addButton.setBackground(Color.CYAN);
        addButton.setPreferredSize(new Dimension(15,15));
        addButton.setBorder(BorderFactory.createEmptyBorder());
        addButton.addActionListener(this);
        closeAddPanel.add(closeButton);
        closeAddPanel.add(addButton);
        
        this.colorButton = new JButton("C");
        colorButton.setPreferredSize(new Dimension(20,20));
        colorButton.setBorder(BorderFactory.createEmptyBorder());
        colorButton.addActionListener(this);
        colorButton.setBackground(color);
        
        this.setPreferredSize(new Dimension(300,100));
        this.setMaximumSize(this.getPreferredSize());
        
        add(label);
        add(textField);
        add(closeAddPanel);
        add(colorButton);
    }
    
    public FunctionInputPanel(MyListener listener,Substitution sharedSubstitution){
        this(listener,Color.BLUE,sharedSubstitution);//blue is default
    }
    
    public FunctionWorldObject getFunctionWorldObject() {
        return functionWorldObject;
    }
    
    public Color getColor(){
        return color;
    }
    
    public void setColor(Color color){
        this.color = color;
    }
    
    public void select(){
        functionWorldObject.setSelected(true);
        this.setBackground(SELECTED_COLOR);
    }
    
    public void deselect(){
        functionWorldObject.setSelected(false);
        this.setBackground(DESELECTED_COLOR);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        docUpdate(e);
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        docUpdate(e);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        docUpdate(e);
    }
    
    public void docUpdate(DocumentEvent de){
        updateFunction();
    }
    
    //call when text for variable values have changed
    public void updateFunction(){
        //delete old function
        listener.eventHappened(new MyEvent(this,"Delete Function",functionWorldObject));
        
        //get text in text field
        String textFieldText;
        textFieldText=textField.getText();//de.getDocument().getText(0, de.getDocument().getLength());
        
        //get function it represents. If there is a problem with parsing or the function is not graphable, function is set to NullFunctionWorldObject
        Expression expression;
        try{
            expression = ExpressionFactory.getExpression(textFieldText);
        }catch(Exception e){
            badFunction();
            return;
        }
        Function function = new BasicFunction(expression);
        FunctionWorldObject newFunctionWorldObject = new BasicFunctionWorldObject(function,color);
        functionWorldObject = newFunctionWorldObject;
        functionWorldObject.setSubstitution(sharedSubstitution);
        if(!newFunctionWorldObject.isGoodForGraphing()){
            badFunction();
            return;
        }
        functionWorldObject.setSelected(true);
        
        textField.setForeground(Color.BLACK);
        listener.eventHappened(new MyEvent(this,"Add Function",functionWorldObject));
    }

    private void badFunction() {
        textField.setForeground(Color.RED);
        functionWorldObject = NullFunctionWorldObject.getInstance();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource()==closeButton){
            listener.eventHappened(new MyEvent(this,"Close Function Input",null));
        }else if(ae.getSource()==addButton){
            listener.eventHappened(new MyEvent(this,"Add Function after",null));
        }else if(ae.getSource()==colorButton){
            displayColorChooserFrame();
        }
    }
//    https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ColorChooserDemoProject/src/components/ColorChooserDemo.java
    private void displayColorChooserFrame() {
      //Create and set up the window.
        JFrame frame = new JFrame("ColorChooser");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
 
        //Create and set up the content pane.
        JPanel newContentPane = new JPanel(new BorderLayout());
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
 
        //Set up color chooser for setting text color
        tcc = new JColorChooser(Color.yellow);
        tcc.getSelectionModel().addChangeListener(this);
        tcc.setBorder(BorderFactory.createTitledBorder(
                                             "Choose Text Color"));
 
        frame.add(tcc, BorderLayout.PAGE_END);
        
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public void enableXButton() {
        closeButton.setEnabled(true);
    }

    public void disableXButton() {
        closeButton.setEnabled(false);
    }

    @Override
    public void stateChanged(ChangeEvent arg0) {
        setColor(tcc.getColor());
        functionWorldObject.setColor(color);
        colorButton.setBackground(color);
        listener.eventHappened(new MyEvent(this,"Repaint",null));
    }

    public void requestFocusForTextBox() {
        textField.requestFocusInWindow();
    }
    
    
    
}

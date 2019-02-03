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

import function.Constant;
import function.Expression;
import function.ExpressionFactory;
import function.NullSubstitution;
import function.Undefined;
import function.Variable;
import myAlgs.MyEvent;
import myAlgs.MyListener;


public class VariableInputPanel extends JPanel implements DocumentListener, ActionListener, ChangeListener{
    
    private JLabel label;
    private JTextField variableTextField;
    private JTextField valueTextField;
    private MyListener listener;
    private JPanel closeAddPanel;
    private JButton closeButton;
    private JButton addButton;
    
    private Variable variable;
    
    public VariableInputPanel(MyListener listener){
        this.listener = listener;
        this.label = new JLabel("=");
        this.variable = null;
        
        this.variableTextField = new JTextField(3);
        variableTextField.setForeground(Color.BLACK);
        variableTextField.getDocument().addDocumentListener(this);
        
        this.valueTextField = new JTextField(6);
        valueTextField.setForeground(Color.BLACK);
        valueTextField.getDocument().addDocumentListener(this);
        
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
        
        
        this.setPreferredSize(new Dimension(300,100));
        this.setMaximumSize(this.getPreferredSize());
        
        add(variableTextField);
        add(label);
        add(valueTextField);
        add(closeAddPanel);
        
    }
    
    public Variable getVariable() {
        return variable;
    }
    
    public Constant getValue() {
        String textFieldText = valueTextField.getText();
        if(textFieldText.equals("")){
            badValue();
            return null;
        }
        Expression expression;
        try{
            expression = ExpressionFactory.getExpression(textFieldText);
        }catch(Exception e){
            badValue();
            return null;
        }
        
        Constant value = expression.evaluate(NullSubstitution.getInstance());
        if((value instanceof Undefined)){
            badValue();
            return null;
        }
        valueTextField.setForeground(Color.BLACK);
        return value;
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
        listener.eventHappened(new MyEvent(this,"Delete Variable",variable));
        readVariable();
        listener.eventHappened(new MyEvent(this,"Add Variable",new Object[] {variable,getValue()}));
    }

    private void readVariable() {
        String textFieldText = variableTextField.getText();
        Expression expression;
        try{
            expression = ExpressionFactory.getExpression(textFieldText);
        }catch(Exception e){
            badVariable();
            return;
        }
        
        if(!(expression instanceof Variable)){
            badVariable();
            return;
        }
        variable = (Variable) expression;
        if(variable.getCharacter()=='x'){
            badVariable();
            return;
        }
        variable = (Variable) expression;
        
        variableTextField.setForeground(Color.BLACK);
        return;
    }

    private void badVariable() {
        variableTextField.setForeground(Color.RED);
        variable = null;
    }
    private void badValue() {
        valueTextField.setForeground(Color.RED);
    }


    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource()==closeButton){
            listener.eventHappened(new MyEvent(this,"Close Function Input",null));
        }else if(ae.getSource()==addButton){
            listener.eventHappened(new MyEvent(this,"Add Function after",null));
        }
    }


    public void enableXButton() {
        closeButton.setEnabled(true);
    }

    public void disableXButton() {
        closeButton.setEnabled(false);
    }

    @Override
    public void stateChanged(ChangeEvent arg0) {
        
    }

    public void requestFocusForTextBox() {
        valueTextField.requestFocusInWindow();
    }
    
    
    
}

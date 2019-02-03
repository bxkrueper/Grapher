package grapher;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import function.Expression;
import function.RealNumber;
import function.Substitution;
import function.SubstitutionMap;
import function.Variable;
import myAlgs.MyEvent;
import myAlgs.MyListener;

public class VariablePanel extends JPanel implements MyListener{
    
    private MyListener listener;
    private JPanel panelInScrollPane;
    private List<VariableInputPanel> inputPanelList;
    private JScrollPane scrollPane;
    private Substitution substitution;//all functions share this substitution
    
    
    public VariablePanel(MyListener listener){
        this.listener = listener;
        Substitution sub = new SubstitutionMap();
        sub.put(new Variable('x'), new RealNumber(0));
        this.setSubstitution(sub);
        this.setBackground(Color.BLACK);
        this.setLayout(new BorderLayout());//so the scroll pane will fill the panel
        
        this.panelInScrollPane = new JPanel();
        panelInScrollPane.setLayout(new BoxLayout(panelInScrollPane,BoxLayout.Y_AXIS));
        
        
        this.inputPanelList = new ArrayList<>();
        VariableInputPanel firstVIP = new VariableInputPanel(this);
        firstVIP.disableXButton();//can't delete the only one
        addInput(firstVIP,0);
        
        
        this.scrollPane = new JScrollPane(panelInScrollPane);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        
        add(scrollPane);
        
    }

    @Override
    public void eventHappened(MyEvent e) {
        if(e.getCommand().equals("Close Function Input")){
            VariableInputPanel vip = (VariableInputPanel) e.getSource();
            deleteInput(vip);
        }else if(e.getCommand().equals("Add Function after")){
            VariableInputPanel vip = (VariableInputPanel) e.getSource();
            int indexOfVIP = getIndexOfPanel(vip);
            VariableInputPanel newVip = new VariableInputPanel(this);
            addInput(newVip,indexOfVIP+1);
            newVip.requestFocusForTextBox();
        }else if(e.getCommand().equals("Delete Variable")){
            substitution.put((Variable) e.getData(), null);
            listener.eventHappened(new MyEvent(this,"Repaint",null));
        }else if(e.getCommand().equals("Add Variable")){
            substitution.put((Variable) ((Object[]) e.getData())[0], (Expression) ((Object[]) e.getData())[1]);
            listener.eventHappened(new MyEvent(this,"Variables Changed",null));
            listener.eventHappened(new MyEvent(this,"Repaint",null));
        }else{
            listener.eventHappened(e);
        }
    }

    
    
    private int getIndexOfPanel(VariableInputPanel vip){
        return inputPanelList.indexOf(vip);
    }

    
    private void addInput(VariableInputPanel newVip,int index) {
        inputPanelList.add(index, newVip);
        //if there is more than one, enable all x buttons
        if(inputPanelList.size()>1){
            for(VariableInputPanel vip: inputPanelList){
                vip.enableXButton();
            }
        }
        resetFunctions();
    }
    private void deleteInput(VariableInputPanel vip) {
        inputPanelList.remove(vip);
        substitution.put(vip.getVariable(), null);
        
        //if there is only one left, disable x button
        if(inputPanelList.size()==1){
            inputPanelList.get(0).disableXButton();
        }
        resetFunctions();
    }

    public Substitution getSubstitution() {
        return substitution;
    }

    private void setSubstitution(Substitution substitution) {
        this.substitution = substitution;
    }
    
    private void resetFunctions(){
        //delete
        panelInScrollPane.removeAll();
        //add
        for(int i=0;i<inputPanelList.size();i++){
            VariableInputPanel vip = inputPanelList.get(i);
            panelInScrollPane.add(vip);
        }
        revalidate();
        repaint();
    }
    
    
    
}


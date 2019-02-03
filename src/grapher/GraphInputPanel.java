package grapher;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import function.Substitution;
import myAlgs.MyEvent;
import myAlgs.MyListener;


public class GraphInputPanel extends JPanel implements MyListener{
    
    private MyListener listener;
    private JPanel panelInScrollPane;
    private List<FunctionInputPanel> inputPanelList;
    private JScrollPane scrollPane;
    private Substitution sharedSubstitution;
    
    
    public GraphInputPanel(MyListener listener,Substitution sharedSubstitution){
        this.listener = listener;
        this.setBackground(Color.BLACK);
        this.setLayout(new BorderLayout());//so the scroll pane will fill the panel
        this.sharedSubstitution = sharedSubstitution;
        
        this.panelInScrollPane = new JPanel();
        panelInScrollPane.setLayout(new BoxLayout(panelInScrollPane,BoxLayout.Y_AXIS));
        
        
        this.inputPanelList = new ArrayList<>();
        FunctionInputPanel firstFIP = new FunctionInputPanel(this,sharedSubstitution);
        firstFIP.disableXButton();//can't delete the only one
        addInput(firstFIP,0);
        
        
//        panelInScrollPane.setPreferredSize(new Dimension(200,600));
        
        this.scrollPane = new JScrollPane(panelInScrollPane);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        
        add(scrollPane);
    }

    @Override
    public void eventHappened(MyEvent e) {
        if(e.getCommand().equals("Close Function Input")){
            FunctionInputPanel fip = (FunctionInputPanel) e.getSource();
            deleteInput(fip);
        }else if(e.getCommand().equals("Add Function after")){
            FunctionInputPanel fip = (FunctionInputPanel) e.getSource();
            int indexOfFIP = getIndexOfPanel(fip);
            FunctionInputPanel newFip = new FunctionInputPanel(this,sharedSubstitution);
            addInput(newFip,indexOfFIP+1);
            newFip.requestFocusForTextBox();
        }else if(e.getCommand().equals("Deselect All")){
            deselectAll();
        }else{
            listener.eventHappened(e);
        }
    }

    private void deselectAll() {
        for(FunctionInputPanel fip: inputPanelList){
            fip.deselect();
        }
    }
    
    
    
    private int getIndexOfPanel(FunctionInputPanel fip){
        return inputPanelList.indexOf(fip);
    }

    
    private void addInput(FunctionInputPanel newFip,int index) {
        inputPanelList.add(index, newFip);
        resetFunctions();
        //if there is more than one, enable all x buttons
        if(inputPanelList.size()>1){
            for(FunctionInputPanel fip: inputPanelList){
                fip.enableXButton();
            }
        }
    }
    private void deleteInput(FunctionInputPanel fip) {
        inputPanelList.remove(fip);
        FunctionWorldObject fwo = fip.getFunctionWorldObject();
        listener.eventHappened(new MyEvent(this,"Delete Function",fwo));
        resetFunctions();
        
        //if there is only one left, disable x button
        if(inputPanelList.size()==1){
            inputPanelList.get(0).disableXButton();
        }
    }
    
    //deletes all function world objects and panels and re-adds based on the current input panel list with the right colors
    private void resetFunctions(){
        //delete
        panelInScrollPane.removeAll();
        for(FunctionInputPanel fip:inputPanelList){
            FunctionWorldObject fwo = fip.getFunctionWorldObject();
            listener.eventHappened(new MyEvent(this,"Delete Function",fwo));
        }
        //add
        for(int i=0;i<inputPanelList.size();i++){
            FunctionInputPanel fip = inputPanelList.get(i);
            panelInScrollPane.add(fip);
            FunctionWorldObject fwo = fip.getFunctionWorldObject();
            listener.eventHappened(new MyEvent(this,"Add Function",fwo));
        }
        revalidate();
        repaint();
    }

    public void recheckFunctionValidity() {
        for(FunctionInputPanel fip: inputPanelList){
            fip.updateFunction();
        }
    }
    
    
    
}

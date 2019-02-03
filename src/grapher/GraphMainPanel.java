package grapher;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import myAlgs.MyEvent;
import myAlgs.MyListener;
import structure.WorldPanel;


public class GraphMainPanel extends JPanel implements MyListener{
    
    GraphWorld graphWorld;
    JPanel graphWorldPanel;
    JPanel sidePanel;
    GraphInputPanel graphInputPanel;
    VariablePanel variablePanel;
    
    public GraphMainPanel(){
        this.graphWorld = new GraphWorld();
        this.graphWorldPanel = new WorldPanel(graphWorld);
        
        this.sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel,BoxLayout.Y_AXIS));
        this.variablePanel = new VariablePanel(this);
        this.graphInputPanel = new GraphInputPanel(this,variablePanel.getSubstitution());
        sidePanel.add(graphInputPanel);
        sidePanel.add(variablePanel);
        
        
        
        
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = .9;
        c.weighty = .5;
        add(graphWorldPanel,c);
        
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = .3;
        c.weighty = .5;
        add(sidePanel,c);
        
//        c.fill = GridBagConstraints.BOTH;
//        c.gridx = 2;
//        c.gridy = 0;
//        c.gridwidth = 1;
//        c.gridheight = 1;
//        c.weightx = .3;
//        c.weighty = .5;
//        add(variablePanel,c);
    }

    @Override
    public void eventHappened(MyEvent e) {
        if(e.getCommand().equals("Delete Function")){
            FunctionWorldObject fwo = (FunctionWorldObject) e.getData();
            if(fwo==NullFunctionWorldObject.getInstance()){//null functions were never added
                return;
            }
            graphWorld.remove(fwo);
            graphWorld.repaint();
        }else if(e.getCommand().equals("Add Function")){
            FunctionWorldObject fwo = (FunctionWorldObject) e.getData();
            fwo.setSubstitution(variablePanel.getSubstitution());// all functions share the same substitution
            if(fwo==NullFunctionWorldObject.getInstance()){//don't bother adding null functions
                return;
            }
            graphWorld.add(fwo);
            graphWorld.repaint();
        }else if(e.getCommand().equals("Variables Changed")){
            graphInputPanel.recheckFunctionValidity();
        }else if(e.getCommand().equals("Repaint")){
            graphWorld.repaint();
        }
    }

    public void paintAgain() {
        graphWorld.repaint();
    }

    
}

package grapher;

import java.awt.Dimension;

import javax.swing.JFrame;


public class GraphFrame extends JFrame{
    private GraphMainPanel graphMainPanel;
    private static GraphFrame instance;
    
    private GraphFrame(){
        setTitle("Graph Frame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(650,650));
        
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.graphMainPanel = new GraphMainPanel();
        add(graphMainPanel);
        
        
        pack();
        graphMainPanel.paintAgain();
        setLocationRelativeTo(null);
        
        setVisible(true);
    }
    
    public static GraphFrame getInstance(){
        if(instance==null){
            instance = new GraphFrame();
        }
        
        return instance;
    }
}
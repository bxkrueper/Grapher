package grapher;

import java.awt.Color;
import java.awt.event.KeyEvent;

import camera.Camera;
import camera.ZoomingCamera;
import coordinate.Coordinate;
import coordinate.Coordinate2DDouble;
import myAlgs.MyEvent;
import myAlgs.MyListener;
import structure.WorldPanel;
import world.World;
import world.WorldObject;
import worldFunctionality.KeyReact;
import worldFunctionality.MouseButtonReact;
import worldFunctionality.MouseButtonReact.ButtonType;
import worldFunctionality.MouseGrabbable;
import worldFunctionality.MouseScrolledOnReact;
import worldUtility.CameraDragManager;
import worldUtility.DebugInputDisplay;
import worldUtility.Grid;


public class GraphWorld extends World implements MyListener{
    
    private ZoomingCamera camera;
    private GraphWorldLogic swl;
    private Grid grid;
    
    public GraphWorld() {
        this.swl = new GraphWorldLogic();
        add(swl);
        this.camera = new ZoomingCamera(0.0,0.0,0.02);
        add(camera);
        
        
        add(new DebugInputDisplay(this));
        add(new CameraDragManager(camera,ButtonType.MIDDLE));
        add(new GraphZoomScrollManager(camera,-2,2));
        
        this.grid = new Grid();
        add(grid);
    }
    
    @Override
    public void eventHappened(MyEvent e) {
    }
    
    
    
    
    
    @Override
    public void initialize(WorldPanel wp){
        super.initialize(wp);
    }

    @Override
    public Camera getCamera() {
        return camera;
    }

    @Override
    public int getInitialFramePeriod() {
        return 0;
    }

    
    
    private class GraphWorldLogic extends WorldObject implements KeyReact, MouseScrolledOnReact, MouseGrabbable, MouseButtonReact{
        @Override
        public void reactToKey(int keyCode, PressType t) {
            if(keyCode==KeyEvent.VK_F3 && t==KeyReact.PressType.RELEASED){
                setDeBugMode(!isDeBugMode());
                repaint();
            }
//            if(keyCode==KeyEvent.VK_0 && t==KeyReact.PressType.RELEASED){
//                setFramePeriod(0);
//            }
            if(keyCode==KeyEvent.VK_1 && t==KeyReact.PressType.RELEASED){
                setFramePeriod(16);
            }
            if(keyCode==KeyEvent.VK_2 && t==KeyReact.PressType.RELEASED){
                setFramePeriod(1000);
            }
        }

        @Override
        public void doOnAdd() {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void doOnDelete() {
            // TODO Auto-generated method stub
            
        }

        @Override
        public boolean acceptTarget(ButtonType bt) {
            return true;
        }

        @Override
        public boolean consumeTargetable(ButtonType bt) {
            return false;
        }

        @Override
        public Coordinate getPosition() {
            // TODO Auto-generated method stub
            return Coordinate2DDouble.zero;/////////////
        }

        @Override
        public boolean occupies(double x, double y) {
            return true;
        }

        @Override
        public boolean acceptBeingGrabbed(ButtonType bt) {
            return true;
        }

        @Override
        public void unGrabbed() {
        }

        //repaint when camera is moved
        @Override
        public void actOnGrab(Coordinate grabLocation, Coordinate grabSorce, Coordinate objectOrigionalLocation) {
            repaint();
        }

        //repaint when camera is moved
        @Override
        public void reactToMouseScrolledOn(ButtonPressType bpt, double x, double y) {
            repaint();
        }

        @Override
        public void reactToMouseButton(ButtonType bt, ButtonPressType bpt, double x, double y) {
            repaint();
        }
    }


    

    

}
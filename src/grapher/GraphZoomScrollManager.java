package grapher;

import camera.ZoomingCamera;
import worldFunctionality.MouseButtonReact.ButtonPressType;
import worldUtility.CameraZoomScrollManager;

//makes sure the zoom is a nice number
//zoom values are set to this pattern: ...0.1 0.2 0.5 1 2 5 10 20 50 100 200 500 1000....
public class GraphZoomScrollManager extends CameraZoomScrollManager{

    //zoom is calculated by 10^exponent*times
    private int exponent;
    private int times;
    
    public GraphZoomScrollManager(ZoomingCamera camera, int exponent, int times) {
        super(camera);
        this.exponent = exponent;
        this.times = times;
    }
    
    private void zoomUp(){
        switch(times){
        case 1:
            times=2;
            break;
        case 2:
            times=5;
            break;
        case 5:
            exponent+=1;
            times=1;
            break;
        }
    }
    private void zoomDown(){
        switch(times){
        case 1:
            exponent-=1;
            times=5;
            break;
        case 2:
            times=1;
            break;
        case 5:
            times=2;
            break;
        }
    }
    
    private double calculateZoom(){
        return Math.pow(10, exponent)*times;
    }
    
    @Override
    public void reactToMouseScrolledOn(ButtonPressType bpt, double x, double y) {
        if(bpt==ButtonPressType.UP){
            zoomDown();
            getCamera().setZoom(calculateZoom());
        }else{
            zoomUp();
            getCamera().setZoom(calculateZoom());
        }
    }
    

}

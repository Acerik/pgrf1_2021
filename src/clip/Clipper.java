package clip;

import model.Point;
import raster.Raster;

import java.util.List;

public abstract class Clipper {
    protected Raster raster;
    public Clipper(Raster raster){
        this.raster = raster;
    }

    List<Point> clip(List<Point> points, List<Point> cutPoints){
        return null;
    } // přednáška lze zkopírovat
}

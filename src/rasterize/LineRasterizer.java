package rasterize;

import model.Line;
import raster.Raster;

public abstract class LineRasterizer {
    protected Raster raster;

    public LineRasterizer(Raster raster) {
        this.raster = raster;
    }

    public Raster getRaster(){
        return this.raster;
    }

    public void rasterize(int x1, int y1, int x2, int y2){

    }
    public void rasterize(int x1, int y1, int x2, int y2, int color){

    }
    public void rasterize(Line line){
        rasterize(line.getX1(),line.getY1(),line.getX2(),line.getY2());
    }
    public void rasterize(Line line, int color){
        rasterize(line.getX1(),line.getY1(),line.getX2(),line.getY2(), color);
    }
}

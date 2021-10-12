package rasterize;

import raster.Raster;
import raster.RasterBufferedImage;

import java.awt.image.BufferedImage;

public class LineRasterizeBufferImage extends LineRasterizer{

    public LineRasterizeBufferImage(Raster raster) {
        super(raster);
    }

    @Override
    public void rasterize(int x1, int y1, int x2, int y2) {
        BufferedImage img = ((RasterBufferedImage)raster).getImg();
        img.getGraphics().drawLine(x1,y1,x2,y2);
    }

    public void setRaster(Raster raster){
        this.raster = raster;
    }
}

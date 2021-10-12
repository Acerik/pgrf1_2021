package rasterize;

import raster.Raster;

public class LineRasterizeTrivial extends LineRasterizer{

    public LineRasterizeTrivial(Raster raster) {
        super(raster);
    }

    @Override
    public void rasterize(int x1, int y1, int x2, int y2) {
        /*
        float k,q;

        k=((float)(y2-y1)/(x2-x1));
        q=y1-(k*x1);
        for (int x = x1; x <= x2; x++){
            float y= (k*x)+q;
            //img.setRGB(x, (int)y, 0xffff00);
            raster.setPixel(x, (int) y,0xffff00);
        }
        */
        float k,q;
        k = ((float)(y2-y1)/(x2-x1));
        if(Math.abs(y2-y1) < Math.abs(x2-x1)){ // zjištění řídící osy
            if(x2 < x1){ // výměna koncových bodů
                int temp = x1;
                x1 = x2;
                x2 = temp;
                temp = y1;
                y1 = y2;
                y2 = temp;
            }
            q=y1-(k*x1);
            for (int x = x1; x <= x2; x++){ // vykreslení - řídící osa X
                float y= (k*x)+q;
                //img.setRGB(x, (int)y, 0xffff00);
                raster.setPixel(x, (int) y,0xffff00);
            }
        } else {
            if(y2<y1){ // výměna koncových bodů
                int temp = x1;
                x1 = x2;
                x2 = temp;
                temp = y1;
                y1 = y2;
                y2 = temp;
            }
            q=y1-(k*x1);
            for (int y = y1; y <= y2; y++){ // vykreslení pomocí řídící osy Y
                float x= (y-q)/k;
                //img.setRGB(x, (int)y, 0xffff00);
                raster.setPixel((int) x, (int) y,0xffff00);
            }
        }

    }
}

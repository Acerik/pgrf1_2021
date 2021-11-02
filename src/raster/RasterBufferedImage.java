package raster;

import java.awt.*;
import java.awt.image.BufferedImage;

public class RasterBufferedImage implements Raster{

    private BufferedImage img;

    public RasterBufferedImage(BufferedImage img){
        this.img = img;
    }

    private int background;

    public RasterBufferedImage(int width, int height){
        img = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
    }

    @Override
    public void setPixel(int x, int y, int color) {
        if(checkPoint(x,y)) {
            img.setRGB(x, y, color);
        }
    }

    @Override
    public int getPixel(int x, int y) {
        if(checkPoint(x,y)) {
            return img.getRGB(x, y);
        }
        return background;
    }

    public boolean checkPoint(int x, int y){
        return (x < getWidth() && x>= 0 && y>=0 && y < getHeight());
    }

    @Override
    public int getWidth() {
        return img.getWidth();
    }

    @Override
    public int getHeight() {
        return img.getHeight();
    }

    @Override
    public void clear() {
        Graphics gr = img.getGraphics();
        gr.setColor(new Color(0x2f2f2f));
        gr.fillRect(0, 0, img.getWidth(), img.getHeight());
    }

    public void present(Graphics graphics){
        graphics.drawImage(img, 0, 0, null);
    }

    public BufferedImage getImg(){
        return img;
    }
}

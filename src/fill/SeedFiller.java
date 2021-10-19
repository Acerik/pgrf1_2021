package fill;

import raster.Raster;

public class SeedFiller extends Filler{

    private int backgroundColor = 0;
    private int seedX;
    private int seedY;

    public SeedFiller(Raster raster) {
        super(raster);
    }

    @Override
    public void fill() {
        seed(seedX, seedY, 0x00FFFF);
    }

    private void seed(int x, int y, int color){
        if(isInside(x,y)) {
            raster.setPixel(x, y, color);
            seed(x+1, y, color);
            seed(x-1, y, color);
            seed(x, y+1, color);
            seed(x, y-1, color);

        }
    }

    private boolean isInside(int x, int y){
        return raster.getPixel(x,y) == backgroundColor;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getSeedX() {
        return seedX;
    }

    public void setSeedX(int seedX) {
        this.seedX = seedX;
    }

    public int getSeedY() {
        return seedY;
    }

    public void setSeedY(int seedY) {
        this.seedY = seedY;
    }
}

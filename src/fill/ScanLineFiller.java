package fill;


import model.Point;
import raster.Raster;

import java.util.ArrayList;
import java.util.List;

public class ScanLineFiller extends Filler {

    private List<ScanLineLine> polygon;

    public ScanLineFiller(Raster raster){
        super(raster);
    }

    @Override
    public void fill() {

    }

    public void setPolygon(List<Point> points){
        polygon = new ArrayList<>();
        /*
        ScanLineLine scanLineLine = new ScanLineLine(points.get(0).getX(), points.get(0).getY(),
                points.get(1).getX(), points.get(1).getY());
        polygon.add(scanLineLine);
         */
        int yMin = Integer.MAX_VALUE;
        int yMax = Integer.MIN_VALUE;
        for(int i = 0; i < points.size() - 1; i++){
            int idx = (i+1) % points.size();

            ScanLineLine line = new ScanLineLine(points.get(i).getX(), points.get(i).getY(),
                    points.get(idx).getX(), points.get(idx).getY());

            if(!line.isHorizontal()) {
                line.orientate();
                if(yMin > line.y1){
                    yMin = line.y1;
                }
                if(yMax < line.y2){
                    yMax = line.y2;
                }
                polygon.add(line);
            }
        }
    }

    private class ScanLineLine{
        int x1, y1, x2, y2;

        public ScanLineLine(int x1, int y1, int x2, int y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }

        public boolean isHorizontal(){
            return (y1 == y2);
        }

        public void orientate(){
            if(y1 > y2){
                int temp = x1;
                x1 = x2;
                x2 = temp;
                temp = y1;
                y1 = y2;
                y2 = temp;
            }
        }
    }
}
